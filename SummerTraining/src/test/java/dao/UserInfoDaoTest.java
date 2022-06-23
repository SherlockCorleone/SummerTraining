package dao;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import entity.UserInfo;
import test.BaseTest;

public class UserInfoDaoTest extends BaseTest{
	@Autowired
	UserInfoDao userInfoDao;
	@Test
	@Ignore
	public void testInsert() {
		UserInfo u=new UserInfo();
		u.setCreateDate(new Date());
		u.setLastModifyDate(new Date());
		u.setStatus(1);
		u.setType(1);
		u.setUserGender("男");
		u.setUserName("gg");
		u.setUserEmail("test1");
		u.setUserImageAddress("test1");
		userInfoDao.insertUserInfo(u);
	}
	@Test
	public void testQuery() {
		userInfoDao.queryUserInfoByUserId(1L);
	}
}
