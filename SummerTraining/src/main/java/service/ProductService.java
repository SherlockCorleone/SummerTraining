package service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import dto.ProductExecution;
import entity.Product;
import myException.ProductExecuteException;

public interface ProductService {
	ProductExecution insertProduct(Product product,CommonsMultipartFile productImg,List<CommonsMultipartFile> productImgList) throws ProductExecuteException;
	ProductExecution updateProduct(Product product,CommonsMultipartFile productImg,List<CommonsMultipartFile> productImgList) throws ProductExecuteException;
	Product getProductByProductId(Long productId);
	ProductExecution deleteProductByProductIdAndShopId(Long productId,Long shopId) throws ProductExecuteException;
	ProductExecution getProductList(Product product,int pageIndex,int pageSize);
}