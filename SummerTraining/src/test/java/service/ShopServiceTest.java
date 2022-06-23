package service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Date;

import org.apache.commons.fileupload.FileItem;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import dto.ShopExecution;
import entity.Area;
import entity.Shop;
import entity.ShopCategory;
import entity.UserInfo;
import enums.ShopStateEnum;
import test.BaseTest;
import test.FileToCommonsMultipartFile;



public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService shopService;
	
	@Test
	@Ignore
	public void testAddShop() {
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
		shop.setShopName("增添测试1");
		shop.setShopDesc("测试1");
		shop.setShopAddress("测试1");
		shop.setShopPhone("测试1");
		shop.setShopImageAddress("测试1");
		shop.setCreateDate(new Date());
		shop.setStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		File file=new File("E:\\大二暑期实训/Astralis.jpg");
		FileItem fileItem = FileToCommonsMultipartFile.createFileItem(file, "Astralisnew.jpg");
	    CommonsMultipartFile shopImg = new CommonsMultipartFile(fileItem);
		ShopExecution se=shopService.addShop(shop, shopImg);

		assertEquals( ShopStateEnum.SUCCESS.getState(),se.getState());
	}
	@Test
	@Ignore
	public void testUpdateShop(){
		Shop shop=shopService.getShopById(15L);
		shop.setShopPhone("18323260787");
	    CommonsMultipartFile shopImg = null;
		ShopExecution se=shopService.updateShop(shop, shopImg);
		assertEquals( ShopStateEnum.SUCCESS.getState(),se.getState());
	}
	
	@Test
	public void testGetShopList() {
		Shop shop=new Shop();
		UserInfo owner=new UserInfo();
		owner.setUserId(2L);
		shop.setOwner(owner);
		ShopExecution se= shopService.getShopList(shop, 1, 2);
		
		System.out.println(se.getShopList().size());
		System.out.println(se.getCount());
	}
}
