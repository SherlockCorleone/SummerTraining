package dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import entity.ShopCategory;
import test.BaseTest;

public class ShopCategoryDaoTest extends BaseTest {
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	@Test
	public void testQueryShopCategory() {
		
		List<ShopCategory> shopCategoryList=shopCategoryDao.queryShopCategory(new ShopCategory());
		assertEquals(2, shopCategoryList.size());
		ShopCategory x= new ShopCategory();
		x.setShopCategoryId(3L);
		ShopCategory y=new ShopCategory();
		y.setParent(x);
		shopCategoryList=shopCategoryDao.queryShopCategory(null);
		
	}
}
