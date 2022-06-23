package service.imp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.UserInfoDao;
import dao.WechatDao;
import dto.WechatExecution;
import entity.UserInfo;
import entity.Wechat;
import enums.WechatStateEnum;
import service.WechatService;

@Service
public class WechatServiceImp implements WechatService {

	@Autowired
	private WechatDao wechatDao;
	@Autowired
	private UserInfoDao userinfoDao;
	@Override
	public Wechat getWechatByOpenId(String openId) {
		return wechatDao.queryWechatByOpenId(openId);
	}

	@Override
	@Transactional
	public WechatExecution regiter(Wechat wechat) throws RuntimeException {
		if(wechat==null||wechat.getOpenId()==null) {
			return new WechatExecution(WechatStateEnum.EMPTY);
		}
		try {
			wechat.setCreateDate(new Date());
			//第一次使用本平台
			if(wechat.getUser()!=null&&wechat.getUser().getUserId()==null)
			{
				try {
					wechat.getUser().setCreateDate(new Date());
					wechat.getUser().setLastModifyDate(new Date());
					wechat.getUser().setStatus(1);
					UserInfo userInfo=wechat.getUser();
					int e=userinfoDao.insertUserInfo(userInfo);
					if(e<=0) {
						throw new RuntimeException("注册失败");
					}
					//获取到userID后再更新信息
					wechat.setUser(userInfo);
				} catch (Exception e) {
					throw new RuntimeException("注册失败："+e.getMessage());
				}
			}
			int e=wechatDao.insertWechat(wechat);
			if(e<=0) {
				throw new RuntimeException("注册失败");
			}
			return new WechatExecution(WechatStateEnum.SUCCESS,wechat);
		}catch (Exception e) {
			throw new RuntimeException("注册失败："+e.getMessage());
		}
	}

}
