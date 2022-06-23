package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.ProductCategory;

public interface ProductCategoryDao {
	List<ProductCategory> queryProductCategoryByShopId(Long shopId);
	int batchInsertProductCategoryList(List<ProductCategory> productCategoryList);//批量增加
	int deleteProductCategory(@Param("productCategoryId") Long productCategoryId,@Param("shopId") Long shopId);
}
