package service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import dto.ProductExecution;
import entity.Product;
import entity.ProductCategory;
import entity.Shop;
import enums.ProductStateEnum;
import test.BaseTest;
import test.FileToCommonsMultipartFile;

public class ProductServiceTest extends BaseTest{
	@Autowired
	ProductService productService;
	@Test
	@Ignore
	public void testInsertProduct() {
		Product product=new Product();
		product.setPriority(12);
		product.setProductName("香蕉");
		product.setNormalPrice("10$");
		ProductCategory productCategory=new ProductCategory();
		productCategory.setProductCategoryId(21L);
		product.setProductCategory(productCategory);
		product.setStatus(1);
		product.setProductDesc("还行");
		product.setPromotionPrice("10$");
		Shop shop=new Shop();
		shop.setShopId(15L);
		product.setShop(shop);
		File file=new File("E:\\大二暑期实训/Astralis.jpg");
		FileItem fileItem = FileToCommonsMultipartFile.createFileItem(file, "Astralisnew.jpg");
	    CommonsMultipartFile productImg = new CommonsMultipartFile(fileItem);
	    file=new File("E:\\大二暑期实训/dev1ce.jpg");
	    fileItem = FileToCommonsMultipartFile.createFileItem(file, "dev1ce.jpg");
	    List<CommonsMultipartFile> list=new ArrayList<>();
	    list.add(new CommonsMultipartFile(fileItem));
	    file=new File("E:\\大二暑期实训/device.jpg");
	    fileItem = FileToCommonsMultipartFile.createFileItem(file, "dev1ce.jpg");
	    list.add(new CommonsMultipartFile(fileItem));
	    ProductExecution pe=productService.insertProduct(product, productImg, list);
	    assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	}
	@Test
	public void testUpdateProduct() {
		Product product=new Product();
		product.setProductId(2L);
		product.setPriority(12);
		product.setProductName("Service层修改测试商品");
		product.setNormalPrice("10$");
		ProductCategory productCategory=new ProductCategory();
		productCategory.setProductCategoryId(21L);
		product.setProductCategory(productCategory);
		product.setStatus(1);
		product.setProductDesc("修改测试");
		product.setPromotionPrice("10$");
		Shop shop=new Shop();
		shop.setShopId(15L);
		product.setShop(shop);
		File file=new File("E:\\大二暑期实训/Astralis.jpg");
		FileItem fileItem = FileToCommonsMultipartFile.createFileItem(file, "Astralisnew.jpg");
	    CommonsMultipartFile productImg = new CommonsMultipartFile(fileItem);
	    file=new File("E:\\大二暑期实训/dev1ce.jpg");
	    fileItem = FileToCommonsMultipartFile.createFileItem(file, "dev1ce.jpg");
	    List<CommonsMultipartFile> list=new ArrayList<>();
	    list.add(new CommonsMultipartFile(fileItem));
	    file=new File("E:\\大二暑期实训/device.jpg");
	    fileItem = FileToCommonsMultipartFile.createFileItem(file, "dev1ce.jpg");
	    list.add(new CommonsMultipartFile(fileItem));
	    ProductExecution pe=productService.updateProduct(product, productImg, list);
	    assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	}
}
