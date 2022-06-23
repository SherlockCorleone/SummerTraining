package service;

import dto.WechatExecution;
import entity.Wechat;

public interface WechatService {
	Wechat getWechatByOpenId(String openId);
	WechatExecution regiter(Wechat wechat) throws RuntimeException;
}
