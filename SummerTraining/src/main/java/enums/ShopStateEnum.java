package enums;

//定义店铺操作的枚举类 并全部封装起来
public enum ShopStateEnum {
	CHECK(0, "审核中"), OFFLINE(-1, "非法"), SUCCESS(1, "成功"), PASS(2, "审核通过"), INNER_ERROR(-1002, "系统内部出错"),
	NULL_SHOPID(-1003, "shop_id为空"), NULL_SHOP(-1004, "商店信息为空");

	private int state;
	private String stateInfo;

	private ShopStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public static ShopStateEnum stateOf(int state) {
		for (ShopStateEnum stateEnum : values()) {
			if (stateEnum.getState() == state)
				return stateEnum;
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

}
