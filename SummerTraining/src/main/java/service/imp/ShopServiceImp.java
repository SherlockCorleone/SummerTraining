package service.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import dao.ShopDao;
import dto.ShopExecution;
import entity.Shop;
import enums.ShopStateEnum;
import myException.ShopExecuteException;
import service.ShopService;
import util.ImageUtil;
import util.PageIndexAndRowIndexUtil;
import util.PathUtil;

@Service
public  class ShopServiceImp implements ShopService {
	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) throws ShopExecuteException {
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			shop.setStatus(0);
			shop.setCreateDate(new Date());
			shop.setLastModifyDate(new Date());
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum <= 0) {
				throw new ShopExecuteException("店铺注册失败");
			} else {
				if (shopImg != null) {
					try {
						addShopImg(shop, shopImg);// 添加店铺图片并加水印保存在对应路径下
					} catch (Exception e) {
						throw new ShopExecuteException("添加店铺图片失败");
					}
					effectedNum = shopDao.updateShop(shop);// 更新图片地址
					if (effectedNum <= 0) {
						throw new ShopExecuteException("更新图片地址失败");
					}
				}
			}
			return new ShopExecution(ShopStateEnum.SUCCESS, shop);
		} catch (Exception e) {
			throw new ShopExecuteException("添加店铺失败:" + e.getMessage());
		}

	}

	private void addShopImg(Shop shop, CommonsMultipartFile shopImg) {
		// 获取shop图片目录相对路径
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		// 压缩图片并保存在该相对路径下
		String shopImageAddress = ImageUtil.generateThumbnail(shopImg, dest);
		// 设置对象的shopImageAddress成员变量
		shop.setShopImageAddress(shopImageAddress);

	}

	@Override
	public Shop getShopById(Long shopId) {
		return shopDao.queryShopById(shopId);
	}

	@Override
	@Transactional
	public ShopExecution updateShop(Shop shop, CommonsMultipartFile shopImg) throws ShopExecuteException {
		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			if (shopImg != null) {
				try {
					String str=shopDao.queryShopById(shop.getShopId()).getShopImageAddress();
					if(str!=null)
					ImageUtil.deleteFile(str);// 删除原有文件
					addShopImg(shop, shopImg);// 添加店铺图片并加水印保存在对应路径下
				} catch (Exception e) {
					throw new ShopExecuteException("更新店铺图片失败");
				}
			}

			shop.setLastModifyDate(new Date());
			int effectedNum = shopDao.updateShop(shop);
			if (effectedNum <= 0) {
				throw new ShopExecuteException("店铺更新失败");
			}
			return new ShopExecution(ShopStateEnum.SUCCESS, shop);
		} catch (Exception e) {
			throw new ShopExecuteException("更新店铺失败:" + e.getMessage());
		}

	}

	@Override
	public ShopExecution getShopList(Shop shop, int pageIndex, int pageSize) {
		int rowIndex=PageIndexAndRowIndexUtil.pageIndexToRowIndex(pageIndex, pageSize);
		List<Shop> shopList=shopDao.queryShop(shop, rowIndex, pageSize);
		int count=shopDao.queryShopCount(shop);
		ShopExecution se=new ShopExecution();
		if(shopList!=null) {
			se.setShopList(shopList);
			se.setCount(count);
			se.setState(ShopStateEnum.SUCCESS.getState());
		}else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
		
	}

}
