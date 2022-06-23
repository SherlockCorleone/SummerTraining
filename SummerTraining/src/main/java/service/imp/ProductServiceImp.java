package service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import dao.ProductDao;
import dao.ProductImgDao;
import dto.ProductExecution;
import entity.Product;
import entity.ProductImg;
import enums.ProductStateEnum;
import myException.ProductExecuteException;
import service.ProductService;
import util.ImageUtil;
import util.PageIndexAndRowIndexUtil;
import util.PathUtil;

@Service
public class ProductServiceImp implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;

	// 增
	@Override
	@Transactional
	public ProductExecution insertProduct(Product product, CommonsMultipartFile productImg,
			List<CommonsMultipartFile> productImgList) throws ProductExecuteException {
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null
				&& product.getShop().getShopId() > 0 && product.getProductName() != null) {
			// 默认值
			if (product.getPriority() == null)
				product.setPriority(0);
			product.setCreateDate(new Date());
			product.setLastModifyDate(new Date());
			// 商品是否上架
			product.setStatus(1);
			try {
				// 添加
				int e = productDao.insertProduct(product);
				if (e <= 0) {
					throw new ProductExecuteException("添加商品信息失败");
				}
			} catch (ProductExecuteException e) {
				throw new ProductExecuteException("商品添加失败:" + e.getMessage());
			}
			// 缩略图是否存在
			if (productImg != null) {
				try {
					addProductImg(product, productImg);
					productDao.updateProduct(product);
				} catch (Exception e) {
					throw new ProductExecuteException("图片添加失败：缩略图添加失败");
				}

			}
			// 详情图片是否存在
			if (productImgList != null && productImgList.size() > 0) {
				try {
					addProductImgList(product, productImgList);
				} catch (Exception e) {
					throw new ProductExecuteException("图片添加失败：" + e.getMessage());
				}
			}
			return new ProductExecution(ProductStateEnum.SUCCESS);
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}

	}

	// 添加详情图 保存图
	private void addProductImgList(Product product, List<CommonsMultipartFile> productImgDetailList) {
		String dest = PathUtil.getProductImageDetailPath(product);
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		// 处理详情图片并 创建初始化productImg对象 保存图
		for (CommonsMultipartFile p : productImgDetailList) {
			String imgAddr = ImageUtil.clearThumbnail(p, dest);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddress(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateDate(new Date());
			productImgList.add(productImg);
		}
		// 操作数据库保存记录
		if (productImgList.size() > 0) {
			try {
				int e = productImgDao.batchInsertProductImgList(productImgList);
				if (e <= 0) {
					throw new ProductExecuteException("添加详情商品图片失败！");
				}
			} catch (Exception e) {
				throw new ProductExecuteException(e.getMessage());
			}
		} else {
			throw new ProductExecuteException("商品图片为空！");
		}
	}

	// 添加缩略图图 保存图
	private void addProductImg(Product product, CommonsMultipartFile productImg) {
		String dest = PathUtil.getProductImagePath(product);
		String addr = ImageUtil.generateThumbnail(productImg, dest);
		product.setProductImgAddress(addr);
	}

	// 改
	@Override
	@Transactional
	public ProductExecution updateProduct(Product product, CommonsMultipartFile productImg,
			List<CommonsMultipartFile> productImgList) throws ProductExecuteException {

		// 判断空值
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 修改时间
			product.setLastModifyDate(new Date());

			// 处理缩略图
			// 只允许存在一张缩略图，上传后将原来的删除
			if (productImg != null) {
				Product temp = productDao.queryProductByProductId(product.getProductId());
				// 删除原来的文件
				if (temp.getProductImgAddress() != null) {
					ImageUtil.deleteFile(temp.getProductImgAddress());
				}
				// 添加新图片
				addProductImg(product, productImg);
			}

			// 处理详情图 上传后将原来的全部删除
			if (productImgList != null && productImgList.size() > 0) {
				deleteProductImgList(product.getProductId());
				addProductImgList(product, productImgList);
			}

			// 操作数据库
			try {
				int e = productDao.updateProduct(product);
				if (e <= 0) {
					throw new ProductExecuteException("更新商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new ProductExecuteException("更新操作失败：" + e.getMessage());
			}
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	// 删除详情图 删除文件和数据库记录
	private void deleteProductImgList(Long productId) {
		List<ProductImg> productImgList = productImgDao.queryProductImgByProductId(productId);
		// 删除数据库记录
		productImgDao.deleteAllProductImgByProductId(productId);
		// 删除文件
		for (ProductImg x : productImgList) {
			ImageUtil.deleteFile(x.getImgAddress());
		}
	}

	@Override
	public Product getProductByProductId(Long productId) {
		return productDao.queryProductByProductId(productId);
	}
	//删
	@Override
	@Transactional
	public ProductExecution deleteProductByProductIdAndShopId(Long productId, Long shopId)
			throws ProductExecuteException {
		if (productId != null && shopId != null) {
			try {
				Product temp = productDao.queryProductByProductId(productId);
				// 删除原来的文件
				if (temp.getProductImgAddress() != null) {
					ImageUtil.deleteFile(temp.getProductImgAddress());
				}
				deleteProductImgList(productId);
				ImageUtil.deleteFile(PathUtil.getProductAllImagePath(temp));
				int e = productDao.deleteProductByProductIdAndShopId(productId, shopId);
				if (e <= 0) {
					throw new ProductExecuteException("删除失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new ProductExecuteException("删除失败:" + e.getMessage());
			}
		}else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	@Override
	public ProductExecution getProductList(Product product, int pageIndex, int pageSize) {
		int rowIndex=PageIndexAndRowIndexUtil.pageIndexToRowIndex(pageIndex, pageSize);
		List<Product> productList=productDao.queryProduct(product, rowIndex, pageSize);
		int count=productDao.queryProductCount(product);
		ProductExecution pe=new ProductExecution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}
}
