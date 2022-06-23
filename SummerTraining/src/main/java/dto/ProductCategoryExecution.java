package dto;

import java.util.List;

import entity.ProductCategory;
import enums.ProductCategoryStateEnum;

public class ProductCategoryExecution {
	//操作后的结果状态
	private int state;
	private String stateInfo;
	private List<ProductCategory> productCategoryList;
	
	public ProductCategoryExecution() {	
	}
	
	public ProductCategoryExecution(ProductCategoryStateEnum state) {
		this.state=state.getState();
		this.stateInfo=state.getStateInfo();
	}
	
	public ProductCategoryExecution(ProductCategoryStateEnum state,List<ProductCategory>list) {
		this.state=state.getState();
		this.stateInfo=state.getStateInfo();
		productCategoryList=list;
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

	public List<ProductCategory> getProductCategoryList() {
		return productCategoryList;
	}

	public void setProductCategoryList(List<ProductCategory> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}
	
}
