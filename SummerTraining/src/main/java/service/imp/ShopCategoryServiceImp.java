package service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.ShopCategoryDao;
import entity.ShopCategory;
import service.ShopCategoryService;
@Service
public class ShopCategoryServiceImp implements ShopCategoryService {
	@Autowired
	ShopCategoryDao shopCategoryDao;
	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategory) {
		return shopCategoryDao.queryShopCategory(shopCategory);
	}

}
