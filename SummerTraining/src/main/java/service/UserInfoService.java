package service;

import entity.UserInfo;

public interface UserInfoService {
	UserInfo getUserInfoByUserId(Long userId);
}
