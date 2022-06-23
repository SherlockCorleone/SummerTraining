package service;

import java.util.List;

import dto.ProductCategoryExecution;
import entity.ProductCategory;
import myException.ProductCategoryExecuteException;

public interface ProductCategoryService {
	//查、批增、删
	
	List<ProductCategory> getProductCategoryListByShopId(Long shopId);
	ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> list) throws ProductCategoryExecuteException;
	ProductCategoryExecution deleteProductCategory(Long productCategoryId,Long shopId)throws ProductCategoryExecuteException;
}
