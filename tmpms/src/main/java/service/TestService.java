package service;

import dao.TestMapper;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由 TestServiceImp来实现 私有的 <br>
 */
public interface TestService<T>  extends BaseService<T> , TestMapper<T> {

}
