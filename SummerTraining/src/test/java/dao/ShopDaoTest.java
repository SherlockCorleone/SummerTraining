package dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import entity.Area;
import entity.Shop;
import entity.ShopCategory;
import entity.UserInfo;
import test.BaseTest;

public class ShopDaoTest extends BaseTest{
	@Autowired
	private ShopDao shopDao;
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop=new Shop();
		UserInfo owner=new UserInfo();
		Area area=new Area();
		ShopCategory shopCategory=new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("晚安本杰明");
		shop.setShopDesc("test1");
		shop.setShopAddress("test1");
		shop.setShopPhone("test1");
		shop.setShopImageAddress("test1");
		shop.setCreateDate(new Date());
		shop.setStatus(1);
		shop.setAdvice("审核中");
		int effectedNum=shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
	}
	@Test
	@Ignore
	public void testUpdateShop() {
		Shop shop=new Shop();
		shop.setShopId(2L);
		ShopCategory shopCategory=new ShopCategory();
		shopCategory.setShopCategoryId(2L);
		shop.setLastModifyDate(new Date());
		shop.setShopCategory(shopCategory);
		int effectedNum=shopDao.updateShop(shop);
		assertEquals(1, effectedNum);
	}
	@Test
	@Ignore
	public void testQueryShopById() {
		Shop s=shopDao.queryShopById(1l);
		System.out.println(s.getShopName());
		System.out.println(s.getAdvice().toString());
		System.out.println(s.getArea().getAreaName());
		System.out.println(s.getOwner().getUserName());
	}
	@Test
	public void testQueryShop() {
		Shop shop=new Shop();
		ShopCategory shopCategory=new ShopCategory();
		ShopCategory parentCategory2=new ShopCategory();
		shopCategory.setShopCategoryId(2L);
		parentCategory2.setShopCategoryId(7L);
		shopCategory.setParent(parentCategory2);
		shop.setShopCategory(shopCategory);
		shopDao.queryShop(shop,0,11);
	}
}
