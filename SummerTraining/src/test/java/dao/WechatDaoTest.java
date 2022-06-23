package dao;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import entity.UserInfo;
import entity.Wechat;
import test.BaseTest;

public class WechatDaoTest extends BaseTest{
	@Autowired
	WechatDao wechatDao;
	@Autowired 
	UserInfoDao userInfoDao;
	@Test
	@Ignore
	public void testInsert() {
		UserInfo u=userInfoDao.queryUserInfoByUserId(3L);
		Wechat wechat=new Wechat();
		wechat.setUser(u);
		wechat.setCreateDate(new Date());
		wechat.setOpenId("123");
		wechatDao.insertWechat(wechat);
	}
	@Test
	public void testQuery() {
		wechatDao.queryWechatByOpenId(null);
	}
}
