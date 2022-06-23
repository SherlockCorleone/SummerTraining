package service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.UserInfoDao;
import entity.UserInfo;
import service.UserInfoService;

@Service
public class UserInfoImp implements UserInfoService {

	@Autowired
	UserInfoDao userInfoDao;
	@Override
	public UserInfo getUserInfoByUserId(Long userId) {
		return userInfoDao.queryUserInfoByUserId(userId);
	}

}
