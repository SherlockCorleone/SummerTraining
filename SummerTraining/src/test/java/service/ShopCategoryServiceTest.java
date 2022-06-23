package service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import entity.ShopCategory;
import test.BaseTest;

public class ShopCategoryServiceTest extends BaseTest {
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Test
	public void testGetAreaList() {
//		ShopCategory x=new ShopCategory();
//		ShopCategory y=new ShopCategory();
//		x.setShopCategoryId(1L);
//		y.setParent(x);
		List<ShopCategory> shopCategoryList=shopCategoryService.getShopCategoryList(new ShopCategory());
		assertEquals(2,shopCategoryList.size());
	}
}