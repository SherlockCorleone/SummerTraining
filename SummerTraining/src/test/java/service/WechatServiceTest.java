package service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import dto.WechatExecution;
import entity.UserInfo;
import entity.Wechat;
import test.BaseTest;

public class WechatServiceTest extends BaseTest{
	@Autowired
	WechatService wechatService;
	@Test
	public void testRegister(){
		Wechat wechat =new Wechat();
		UserInfo user= new UserInfo();
		String openId="1234321";
		user.setCreateDate(new Date());
		user.setUserName("测试2");
		user.setType(1);
		wechat.setUser(user);
		wechat.setOpenId(openId);
		wechat.setCreateDate(new Date());
		WechatExecution we=wechatService.regiter(wechat);
		System.out.print(we.getStateInfo());
		wechat =wechatService.getWechatByOpenId("1234321");
		System.out.print(wechat.getUser().getUserName());
	}
}
