package entity;

import java.util.Date;

public class Area {
	private Integer areaId;// 区域ID
	private String areaName;// 区域名称
	private Integer areaPriority;// 区域优先级
	private Date createDate;// 区域创建时间
	private Date lastModifyDate;// 区域最后修改时间
	
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getAreaPriority() {
		return areaPriority;
	}
	public void setAreaPriority(Integer areaPriority) {
		this.areaPriority = areaPriority;
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

	

}
