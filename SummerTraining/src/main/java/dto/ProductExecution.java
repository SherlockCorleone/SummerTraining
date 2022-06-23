package dto;

import java.util.List;

import entity.Product;
import enums.ProductStateEnum;


public class ProductExecution {
	private int state;
	private String stateInfo;
	private List<Product> productList;
	private Product product;
	private int count;
	public ProductExecution() {
	}
	public ProductExecution(ProductStateEnum productStateEnum) {
		state=productStateEnum.getState();
		stateInfo=productStateEnum.getStateInfo();
	}
	public ProductExecution(ProductStateEnum productStateEnum,Product product) {
		state=productStateEnum.getState();
		stateInfo=productStateEnum.getStateInfo();
		this.product=product;
	}
	public ProductExecution(ProductStateEnum productStateEnum, List<Product> list) {
		state=productStateEnum.getState();
		stateInfo=productStateEnum.getStateInfo();
		productList=list;
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
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
