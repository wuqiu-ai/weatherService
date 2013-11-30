package model;

public class TbsUserModel extends BaseModel {

    private String id; //主键
    private String username; //用户名
    private String password; //密码
    private java.sql.Timestamp createTime; //时间
    private String ip; //ip
    private Integer count; //次数
    private Integer isLock; //锁定
    private java.sql.Timestamp lockTime; //锁定时间
    private Integer failCount; //失败次数
    private Integer isAdmin; //权限类型
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>主键<br>
	 * @return id
	 */
		
	public String getId(){
	   return id;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>主键<br>
	 * @param id
	 */
	public void setId(String id){
	   this.id=id;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>用户名<br>
	 * @return username
	 */
		
	public String getUsername(){
	   return username;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>用户名<br>
	 * @param username
	 */
	public void setUsername(String username){
	   this.username=username;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>密码<br>
	 * @return password
	 */
		
	public String getPassword(){
	   return password;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>密码<br>
	 * @param password
	 */
	public void setPassword(String password){
	   this.password=password;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>时间<br>
	 * @return createTime
	 */
	@com.alibaba.fastjson.annotation.JSONField(format="yyyy-MM-dd HH:mm:ss")	
	public java.sql.Timestamp getCreateTime(){
	   return createTime;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>时间<br>
	 * @param createTime
	 */
	public void setCreateTime(java.sql.Timestamp createTime){
	   this.createTime=createTime;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>ip<br>
	 * @return ip
	 */
		
	public String getIp(){
	   return ip;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>ip<br>
	 * @param ip
	 */
	public void setIp(String ip){
	   this.ip=ip;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>次数<br>
	 * @return count
	 */
		
	public Integer getCount(){
	   return count;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>次数<br>
	 * @param count
	 */
	public void setCount(Integer count){
	   this.count=count;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>锁定<br>
	 * @return isLock
	 */
		
	public Integer getIsLock(){
	   return isLock;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>锁定<br>
	 * @param isLock
	 */
	public void setIsLock(Integer isLock){
	   this.isLock=isLock;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>锁定时间<br>
	 * @return lockTime
	 */
	@com.alibaba.fastjson.annotation.JSONField(format="yyyy-MM-dd HH:mm:ss")	
	public java.sql.Timestamp getLockTime(){
	   return lockTime;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>锁定时间<br>
	 * @param lockTime
	 */
	public void setLockTime(java.sql.Timestamp lockTime){
	   this.lockTime=lockTime;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>失败次数<br>
	 * @return failCount
	 */
		
	public Integer getFailCount(){
	   return failCount;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>失败次数<br>
	 * @param failCount
	 */
	public void setFailCount(Integer failCount){
	   this.failCount=failCount;
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>权限类型<br>
	 * @return isAdmin
	 */
		
	public Integer getIsAdmin(){
	   return isAdmin;
	}
	/**
	 * 
	 * <br>
	 * <b>功能：</b>权限类型<br>
	 * @param isAdmin
	 */
	public void setIsAdmin(Integer isAdmin){
	   this.isAdmin=isAdmin;
	}
    
	/**
	 * 
	 * <br>
	 * <b>功能：</b>重写<br>
	 * @return
	 */
    public String toString(){
	  return com.alibaba.fastjson.JSON.toJSONString(this);
	  // return ${SQL.toString}
    }
	
	///////////////////////////增加/////////////////////////
	
   

}
