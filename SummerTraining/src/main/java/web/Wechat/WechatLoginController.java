package web.Wechat;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dto.UserAccessToken;
import dto.WechatExecution;
import dto.WechatUser;
import entity.UserInfo;
import entity.Wechat;
import enums.WechatStateEnum;
import service.UserInfoService;
import service.WechatService;
import util.WechatUtil;

@Controller
@RequestMapping("wechatlogin")
/**
 * 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=您的appId&redirect_uri=http://8.142.9.172/SummerTraining/wechatlogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 * 则这里将会获取到code,之后再可以通过code获取到access_token 进而获取到用户信息
 */
public class WechatLoginController {

	@Autowired
	private WechatService wechatService;
	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		// 获取微信公众号传输过来的code,通过code可获取access_token,进而获取用户信息
		String code = request.getParameter("code");
		// 判断是店家 还是用户
		String type = request.getParameter("state");
		WechatUser user = null;
		Wechat wechat = null;
		String openId = null;
		if (code != null) {
			UserAccessToken token;
			try {
				// 通过code获取access_token
				token = WechatUtil.getUserAccessToken(code);
				// 通过token获取accessToken
				String accessToken = token.getAccessToken();
				// 通过token获取openId
				openId = token.getOpenId();
				// 通过access_token和openId获取用户昵称等信息
				user = WechatUtil.getUserInfo(accessToken, openId);
				request.getSession().setAttribute("openId", openId);
				wechat = wechatService.getWechatByOpenId(openId);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (wechat == null) {
			UserInfo userInfo = new UserInfo();
			userInfo.setUserName(user.getNickName());
			userInfo.setUserGender(user.getSex() + "");
			userInfo.setUserImageAddress(user.getHeadimgurl());
			userInfo.setStatus(1);
			if (type.equals("1")) {
				userInfo.setType(1);// 用户
			} else {
				userInfo.setType(2);//店家
			}
			wechat = new Wechat();
			wechat.setOpenId(openId);
			wechat.setUser(userInfo);
			WechatExecution we = wechatService.regiter(wechat);
			if (we.getState() != WechatStateEnum.SUCCESS.getState()) {
				return  null;
			}
		}
		UserInfo u= userInfoService.getUserInfoByUserId(wechat.getUser().getUserId());
		request.getSession().setAttribute("user", u);
		if (wechat.getUser().getType()==1) {
			// 获取到微信验证的信息后返回到指定的路由
			return "frontend/mainpage";
		} else {
			return "shop/shoplist";
		}
	}
}
