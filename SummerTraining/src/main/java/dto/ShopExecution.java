package dto;

import java.util.List;

import entity.Shop;
import enums.ShopStateEnum;


//返回店铺操作的状态信息
public class ShopExecution {

	private int state;// 结果状态
	private String stateInfo;// 状态标识
	private int count;// 店铺数量？
	private Shop shop;// 正在操作的店铺
	private List<Shop> shopList;// 所有要操作的店铺？

	public ShopExecution() {
	}

	// 店铺操作失败时的构造函数
	public ShopExecution(ShopStateEnum shopStateEnum) {
		this.state = shopStateEnum.getState();
		this.stateInfo = shopStateEnum.getStateInfo();
	}

	// 店铺操作成功时的构造函数
	public ShopExecution(ShopStateEnum shopStateEnum, Shop shop) {
		this.state = shopStateEnum.getState();
		this.stateInfo = shopStateEnum.getStateInfo();
		this.shop = shop;
	}

	// 店铺操作成功时的构造函数
	public ShopExecution(ShopStateEnum shopStateEnum, List<Shop> shopList) {
		this.state = shopStateEnum.getState();
		this.stateInfo = shopStateEnum.getStateInfo();
		this.shopList = shopList;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
}
