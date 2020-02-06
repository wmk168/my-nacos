package com.my.nacos.user.api;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.my.nacos.base.api.BaseControllerApi;
import com.my.nacos.base.vo.ResultResVo;
import com.my.nacos.user.fallback.UserApiFallback;
import com.my.nacos.user.po.UserPo;
import com.my.nacos.user.vo.UserRegReqVo;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

//测试支持Http From提交，没有办法兼容两种方式，测试也未通过，暂时不使用该方式
@FeignClient(name = "user-provider", path = "userFrom"//,configuration = UserFeignFormApi.FormSupportConfig.class
)
public interface UserFeignFormApi extends BaseControllerApi<UserPo>{
	
	//@RequestBody必须要配置，支持这样才能支持Feign
	@RequestMapping(path = "userReg",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
			,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResultResVo<Integer> userReg(UserRegReqVo req);
	
	@Configuration
	class FormSupportConfig {
        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;
        // new一个form编码器，实现支持form表单提交
        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }
        
    }

}
