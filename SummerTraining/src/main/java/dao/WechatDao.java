package dao;

import entity.Wechat;

public interface WechatDao {
	Wechat queryWechatByOpenId(String openId);
	int insertWechat(Wechat wechat);
}
