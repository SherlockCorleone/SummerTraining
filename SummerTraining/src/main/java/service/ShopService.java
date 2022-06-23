package service;


import org.springframework.web.multipart.commons.CommonsMultipartFile;

import dto.ShopExecution;
import entity.Shop;
import myException.ShopExecuteException;

public interface ShopService {
	ShopExecution addShop(Shop shop,CommonsMultipartFile shopImg) throws ShopExecuteException;//添加店铺
	Shop getShopById(Long shopId);//通过id查询店铺
	ShopExecution updateShop(Shop shop,CommonsMultipartFile shopImg) throws ShopExecuteException;//更新店铺
	ShopExecution getShopList(Shop shop,int pageIndex,int pageSize);//返回
}
