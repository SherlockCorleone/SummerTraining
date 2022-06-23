package dao;

import entity.UserInfo;

public interface UserInfoDao {
	UserInfo queryUserInfoByUserId(Long userId);
	int insertUserInfo(UserInfo user);
}
