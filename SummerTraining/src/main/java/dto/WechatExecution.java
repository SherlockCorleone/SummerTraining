package dto;


import entity.Wechat;
import enums.WechatStateEnum;



public class WechatExecution {
	private int state;
	private String stateInfo;
	private Wechat wechat;
	public WechatExecution() {
	}
	public WechatExecution(WechatStateEnum wechatStateEnum) {
		state=wechatStateEnum.getState();
		stateInfo=wechatStateEnum.getStateInfo();
	}
	public WechatExecution(WechatStateEnum wechatStateEnum,Wechat wechat) {
		state=wechatStateEnum.getState();
		stateInfo=wechatStateEnum.getStateInfo();
		this.wechat=wechat;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public Wechat getWechat() {
		return wechat;
	}
	public void setWechat(Wechat wechat) {
		this.wechat = wechat;
	}
	
}
