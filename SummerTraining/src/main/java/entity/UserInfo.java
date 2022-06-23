package entity;

import java.util.Date;

public class UserInfo {
	private Long userId;//用户ID
	private String userName;//用户昵称
	private String userImageAddress;//用户头像文件地址
	private String userEmail;//用户邮箱
	private String userGender;//用户性别
	private Integer status;//用户是否被BAN
	private Integer type;//用户类型 1顾客 2.店家 3.超级管理员
	private Date createDate;//创建时间
	private Date lastModifyDate;//修改时间
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserImageAddress() {
		return userImageAddress;
	}
	public void setUserImageAddress(String userImageAddress) {
		this.userImageAddress = userImageAddress;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	
}
