package com.my.nacos.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;

/**
 * 基础Dao，操作数据库
 * @author wmk
 *
 */
public interface BaseDao<T> extends BaseMapper<T>,Mapper<T>{
}
