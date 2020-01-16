
package com.my.nacos.cloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "user")
public class UserConfig {

	    private String name;
	    private Integer age;
		/**
		 * getter
		 * @return 返回 name
		 */
		public String getName() {
			return name;
		}
		/**
		 * setter
		 * @param name 进行赋值
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * getter
		 * @return 返回 age
		 */
		public Integer getAge() {
			return age;
		}
		/**
		 * setter
		 * @param age 进行赋值
		 */
		public void setAge(Integer age) {
			this.age = age;
		}
}
