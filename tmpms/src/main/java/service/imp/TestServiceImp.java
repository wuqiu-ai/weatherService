package service.imp;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.TestService;
import dao.TestMapper;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 */
@Service("testService")
public class TestServiceImp<T> extends BaseServiceImp<T> implements TestService<T>{
	@Autowired
    private TestMapper<T> mapper;
		
	public TestMapper<T> getMapper() {
	    return mapper;
	}
	

}
