package com.feixing.bean;

/**
 * 用户信息
 * @author J~杰
 *
 */
public class User {

		private String userName;//用户名
		private String password;//密码
		private String area;//地理区域---1：杭州（暂时只是支持杭州地区）
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		
		
	
}
