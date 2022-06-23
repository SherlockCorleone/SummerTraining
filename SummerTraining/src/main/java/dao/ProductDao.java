package dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.Product;


public interface ProductDao {
	int insertProduct(Product product);
	int updateProduct(Product product);
	Product queryProductByProductId(Long productId);
	List<Product> queryProduct(@Param("product") Product product,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	int queryProductCount(@Param("product") Product product);
	int deleteProductByProductIdAndShopId(@Param("productId")Long productId,@Param("shopId")Long shopId);
}
