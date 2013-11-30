package com.system.service.impl;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.service.TbsUserService;
import com.system.dao.TbsUserMapper;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 */
@Service("tbsUserService")
public class TbsUserServiceImp<T> extends BaseServiceImp<T> implements TbsUserService<T>{
	@Autowired
    private TbsUserMapper<T> mapper;
		
	public TbsUserMapper<T> getMapper() {
	    return mapper;
	}
	

	public java.util.List<java.util.Map<String,Object>> selectByRoleUrls(java.util.Map<?,?> map) throws Exception{
	    return mapper.selectByRoleUrls(map);
    }
}
