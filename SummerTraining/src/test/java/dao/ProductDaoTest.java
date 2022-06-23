package dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import entity.Product;
import entity.ProductCategory;
import entity.Shop;
import test.BaseTest;

public class ProductDaoTest extends BaseTest{
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ShopDao shopDao;
	@Test
	@Ignore
	public void testInsertProduct() {
		Product product=new Product();
		product.setCreateDate(new Date());
		product.setLastModifyDate(new Date());
		product.setProductName("插入测试2");
		ProductCategory productCategory=new ProductCategory();
		productCategory.setProductCategoryId(21L);
		product.setProductCategory(productCategory);
		product.setStatus(1);
		product.setShop(shopDao.queryShopById(15L));
		int e=productDao.insertProduct(product);
		System.out.print(e);
	}
	@Test
	@Ignore
	public void testQueryProductById() {
		Product product=productDao.queryProductByProductId(8L);
		System.out.print(product.getCreateDate());
	}
	@Test
	@Ignore
	public void testUpdeateProduct() {
		Product product=new Product();
		product.setCreateDate(new Date());
		product.setLastModifyDate(new Date());
		product.setPriority(11);
		product.setProductName("更改测试");
		product.setNormalPrice("11$");
		ProductCategory productCategory=new ProductCategory();
		productCategory.setProductCategoryId(21L);
		product.setProductCategory(productCategory);
		product.setStatus(1);
		product.setProductDesc("难吃");
		product.setPromotionPrice("10$");
		Shop shop=new Shop();
		shop.setShopId(15L);
		product.setShop(shop);
		product.setProductId(9l);
		productDao.updateProduct(product);
	}
	@Test
	@Ignore
	public void delete() {
		productDao.deleteProductByProductIdAndShopId(9l, 15L);
	}
	@Test
	public void query() {
		Product product=new Product();
		
		product.setStatus(1);
		List<Product> l= productDao.queryProduct(product, 0, 1);
		System.out.print(l.get(0).getProductImgAddress());
	}
}
