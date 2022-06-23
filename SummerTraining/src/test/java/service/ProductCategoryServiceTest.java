package service;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import entity.ProductCategory;
import test.BaseTest;

public class ProductCategoryServiceTest extends BaseTest{
	@Autowired
	ProductCategoryService productCategoryService;
	@Test
	@Ignore
	public void testGetProductCategoryListByShopId() {
		System.out.println(productCategoryService);
		List<ProductCategory> productCategoryList=productCategoryService.getProductCategoryListByShopId(15L);
		System.out.print(productCategoryList.size());
	}
	@Test 
	public void testDelete() {
		productCategoryService.deleteProductCategory(22l, 15l);
	}
}
