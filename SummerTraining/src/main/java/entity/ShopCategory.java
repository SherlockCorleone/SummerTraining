package entity;

import java.util.Date;

public class ShopCategory {
	private Long shopCategoryId;
	private String shopCategoryName;
	private String shopCategoryDesc;// 描述
	private String shopCategoryImageAddress;
	private Integer priority;
	private Date createDate;
	private Date lastModifyDate;
	private ShopCategory parent;// 父级目录
	
	public Long getShopCategoryId() {
		return shopCategoryId;
	}
	public void setShopCategoryId(Long shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}
	public String getShopCategoryName() {
		return shopCategoryName;
	}
	public void setShopCategoryName(String shopCategoryName) {
		this.shopCategoryName = shopCategoryName;
	}
	public String getShopCategoryDesc() {
		return shopCategoryDesc;
	}
	public void setShopCategoryDesc(String shopCategoryDesc) {
		this.shopCategoryDesc = shopCategoryDesc;
	}
	public String getShopCategoryImageAddress() {
		return shopCategoryImageAddress;
	}
	public void setShopCategoryImageAddress(String shopCategoryImageAddress) {
		this.shopCategoryImageAddress = shopCategoryImageAddress;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public ShopCategory getParent() {
		return parent;
	}
	public void setParent(ShopCategory parent) {
		this.parent = parent;
	}

	
}
