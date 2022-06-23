package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import entity.ProductImg;
import test.BaseTest;

public class ProductImgDaoTest extends BaseTest{
	@Autowired
	ProductImgDao productImgDao;
	@Test
	@Ignore
	public void testBatchInsertImgList() {
		List<ProductImg> list=new ArrayList<ProductImg>();
		ProductImg productImg=new ProductImg();
		productImg.setImgAddress("测试图片地址1");
		productImg.setImgDesc("测试图片1");
		productImg.setCreateDate(new Date());
		productImg.setPriority(11);
		productImg.setProductId(1L);
		list.add(productImg);
		productImg=new ProductImg();
		productImg.setImgAddress("测试图片地址2");
		productImg.setImgDesc("测试图片2");
		productImg.setCreateDate(new Date());
		productImg.setPriority(12);
		productImg.setProductId(1L);
		list.add(productImg);
		int e= productImgDao.batchInsertProductImgList(list);
		System.out.println(e);
	}
	@Test
	@Ignore
	public void testDelete() {
		Long productId=1L;
		int e=productImgDao.deleteAllProductImgByProductId(productId);
		System.out.print(e);
	}
	@Test
	public void testSelect() {
		Long productId=8L;
		List<ProductImg> productImg=productImgDao.queryProductImgByProductId(productId);
		System.out.print(productImg.get(0).getImgAddress());
	}
}
