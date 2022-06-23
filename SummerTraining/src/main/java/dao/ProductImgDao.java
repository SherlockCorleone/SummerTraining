package dao;

import java.util.List;

import entity.ProductImg;

public interface ProductImgDao {
	int batchInsertProductImgList(List<ProductImg> productImgList);
	List<ProductImg> queryProductImgByProductId(Long productId);
	int deleteAllProductImgByProductId(Long productId);
}
