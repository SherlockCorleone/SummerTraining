package service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.ProductCategoryDao;
import dto.ProductCategoryExecution;
import dto.ProductExecution;
import entity.Product;
import entity.ProductCategory;
import enums.ProductCategoryStateEnum;
import myException.ProductCategoryExecuteException;
import service.ProductCategoryService;
import service.ProductService;

@Service
public class ProductCategoryServiceImp implements ProductCategoryService {
	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Autowired
	private ProductService productService;
	@Override
	public List<ProductCategory> getProductCategoryListByShopId(Long shopId) {
		return productCategoryDao.queryProductCategoryByShopId(shopId);

	}

	@Override
	@Transactional
	public ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> list)
			throws ProductCategoryExecuteException {
		if (list == null || list.size() == 0) {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		} else {
			try {

				int effectNum = productCategoryDao.batchInsertProductCategoryList(list);
				if (effectNum <= 0) {
					throw new ProductCategoryExecuteException("商品类别添加失败");
				} else if (effectNum != list.size()) {
					return new ProductCategoryExecution(ProductCategoryStateEnum.WARNING);
				} else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS,list);
				}
			} catch (Exception e) {
				throw new ProductCategoryExecuteException("批量添加出错：" + e.getMessage());
			}
		}
	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(Long productCategoryId, Long shopId)
			throws ProductCategoryExecuteException {
			
		try {
			//将商品删除	
			Product product=new Product();
			ProductCategory productCategory=new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			product.setProductCategory(productCategory);
			ProductExecution pe= productService.getProductList(product, 0, 0);
			pe=productService.getProductList(product, 0, pe.getCount());
			for(Product x:pe.getProductList())
			productService.deleteProductByProductIdAndShopId(x.getProductId(), shopId);
			
			
			int effectedNum=productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if(effectedNum<=0) {
				throw new ProductCategoryExecuteException("商品类别删除失败或要删除的类别不存在！");
			}else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		}catch (Exception e) {
			throw new ProductCategoryExecuteException("deleteErrorMsg:"+e.getMessage());
		}

	}
}
