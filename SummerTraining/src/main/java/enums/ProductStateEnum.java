package enums;

public enum ProductStateEnum {
	SUCCESS(1,"操作成功"),INNER_ERROR(-1001,"操作失败"),EMPTY(-1002,"请输入所有信息");
	private int state;
	private String stateInfo;
	
	private ProductStateEnum(int s,String si) {
		state=s;
		stateInfo=si;
	}

	public int getState() {
		return state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public static ProductStateEnum stateOf(int index) {
		for(ProductStateEnum x:values()) {
			if(x.getState()==index) {
				return x;
			}
		}
		return null;
	}
	
}
