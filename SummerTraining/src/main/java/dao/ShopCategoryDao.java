package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.ShopCategory;

public interface ShopCategoryDao {
	/**
	 * 查找同级别的类别（即parent相同） 如果parent=null就查找这个表
	 * @param shopCategory
	 * @return
	 */
	List<ShopCategory> queryShopCategory(@Param("shopCategory")ShopCategory shopCategory);
}
