package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import entity.ProductCategory;
import test.BaseTest;

public class ProductCategoryDaoTest extends BaseTest{
	@Autowired
	ProductCategoryDao productCategoryDao;
	@Test
	@Ignore
	public void testQueryProductCategoryById() {
		List<ProductCategory> productCategories=productCategoryDao.queryProductCategoryByShopId(15L);
		System.out.println(productCategories.size());
		
	}
	@Test
	
	public void testBatchInsertProductCategory(){
		ProductCategory productCategory=new ProductCategory();
		productCategory.setCreateDate(new Date());
		productCategory.setPriority(12);
		productCategory.setProductCategoryName("测试1");
		productCategory.setShopId(15L);
		List<ProductCategory> list=new ArrayList<ProductCategory>();
		list.add(productCategory);
		productCategory=new ProductCategory();
		productCategory.setCreateDate(new Date());
		productCategory.setPriority(10);
		productCategory.setProductCategoryName("测试2");
		productCategory.setShopId(13L); 
		list.add(productCategory);
		int e=productCategoryDao.batchInsertProductCategoryList(list);
		System.out.print(e);
	}
	@Test
	@Ignore
	public void testDeleteProductCatgory() {
		int e=productCategoryDao.deleteProductCategory(18L,15L);
		System.out.print(e);
	}
}
