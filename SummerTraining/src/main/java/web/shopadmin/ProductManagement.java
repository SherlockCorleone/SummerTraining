package web.shopadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.ProductExecution;
import entity.Product;
import entity.ProductCategory;
import entity.Shop;
import enums.ProductStateEnum;
import service.ProductCategoryService;
import service.ProductService;
import util.CodeUtil;
import util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagement {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	// 上传的最大图片数
	private static final int MAX = 6;

	// 添加商品信息
	@RequestMapping(value = "/insertproduct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertProduct(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		// 验证码
		if (!util.CodeUtil.checkVerifyCode(request)) {
			map.put("success", false);
			map.put("errMsg", "验证码错误");
			return map;
		}
		// 接受信息
		ObjectMapper mapper = new ObjectMapper();
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		Product product;
		// 将信息转化为实体类实例
		try {
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", e.getMessage());
			return map;
		}
		// 处理图片文件流数据
		MultipartHttpServletRequest multipartHttpServletRequest;
		CommonsMultipartFile productImg = null;// 缩略图对象
		List<CommonsMultipartFile> productImgList = new ArrayList<>();// 详情图列表对象
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			// 判断图片文件流是否存在，并取出来
			if (commonsMultipartResolver.isMultipart(request)) {
				multipartHttpServletRequest = (MultipartHttpServletRequest) request;
				// 缩略图
				productImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg");
				if (productImg == null) {
					map.put("success", false);
					map.put("errMsg", "缩略图为空");
					return map;
				}
				// 详情图
				for (int i = 0; i < MAX; i++) {
					CommonsMultipartFile temp = (CommonsMultipartFile) multipartHttpServletRequest
							.getFile("productImgList" + i);
					if (temp != null) {
						productImgList.add(temp);
					} else {
						break;
					}
				}
				if (productImgList.size() <= 0) {
					map.put("success", false);
					map.put("errMsg", "详情图为空");
					return map;
				}
			} else {
				map.put("success", false);
				map.put("errMsg", "没有上传图片");
				return map;
			}

		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", e.getMessage());
			return map;
		}
		// 添加商品 操作数据库
		if (product != null) {
			try {
				Shop curShop = (Shop) request.getSession().getAttribute("curShop");
				product.setShop(curShop);
				ProductExecution pe = productService.insertProduct(product, productImg, productImgList);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					map.put("success", true);
				} else {
					map.put("success", false);
					map.put("errMsg", pe.getStateInfo());
				}
			} catch (Exception e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
				return map;
			}
		}
		return map;
	}

	// 更新
	@RequestMapping(value = "/updateproduct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateProduct(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean changeStatus = HttpServletRequestUtil.getBoolean(request, "changeStatus");
		if (!changeStatus && !CodeUtil.checkVerifyCode(request)) {
			map.put("success", false);
			map.put("errMsg", "验证码错误");
			return map;
		}
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		// 将信息转化为实体类
		try {
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", e.getMessage());
			return map;
		}
		// 处理图片文件流数据
		MultipartHttpServletRequest multipartHttpServletRequest;
		CommonsMultipartFile productImg = null;// 缩略图对象
		List<CommonsMultipartFile> productImgList = new ArrayList<>();// 详情图列表对象
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			// 判断图片文件流是否存在，并取出来
			if (commonsMultipartResolver.isMultipart(request)) {
				multipartHttpServletRequest = (MultipartHttpServletRequest) request;
				// 缩略图
				productImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg");
				// 详情图
				for (int i = 0; i < MAX; i++) {
					CommonsMultipartFile temp = (CommonsMultipartFile) multipartHttpServletRequest
							.getFile("productImgList" + i);
					if (temp != null) {
						productImgList.add(temp);
					} else {
						break;
					}
				}
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", e.getMessage());
			return map;
		}
		// 添加商品 操作数据库
		if (product != null) {
			try {
				Shop curShop = (Shop) request.getSession().getAttribute("curShop");
				product.setShop(curShop);
				ProductExecution pe = productService.updateProduct(product, productImg, productImgList);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					map.put("success", true);
				} else {
					map.put("success", false);
					map.put("errMsg", pe.getStateInfo());
				}
			} catch (Exception e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
				return map;
			}
		}
		return map;
	}

	// 查询
	@RequestMapping(value = "/getproductbyproductid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getProductByProductId(@RequestParam Long productId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (productId != null && productId > 0) {
			try {
				Product product = productService.getProductByProductId(productId);
				List<ProductCategory> productCategoryList = productCategoryService
						.getProductCategoryListByShopId(product.getShop().getShopId());
				map.put("product", product);
				map.put("productCategoryList", productCategoryList);
				map.put("success", true);
			} catch (Exception e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
			}
		} else {
			map.put("success", false);
			map.put("errMsg", "非法访问！没有获取到productId！");
		}
		return map;
	}

	// 模糊查询
	@RequestMapping(value = "/getproductlist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getProductList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize =  HttpServletRequestUtil.getInt(request, "pageSize");
		Shop curShop = (Shop) request.getSession().getAttribute("curShop");
		if(pageIndex>-1&&pageSize>-1&& curShop!=null&&curShop.getShopId()!=null) {
			Long productCategoryId=HttpServletRequestUtil.getLong(request, "productCategoryId");
			String productName=HttpServletRequestUtil.getString(request, "productName");
			
			Product product=new Product();
			product.setShop(curShop);
			
			if(productCategoryId!=null&&productCategoryId>0) {
				ProductCategory productCategory=new ProductCategory();
				productCategory.setProductCategoryId(productCategoryId);
				product.setProductCategory(productCategory);
			}
			
			if(productName!=null) {
				product.setProductName(productName);
			}
			ProductExecution pe=productService.getProductList(product, pageIndex, pageSize);
			map.put("productList",pe.getProductList());
			map.put("count",pe.getCount());
			map.put("success",true);
		}else {
			map.put("success",false);
			map.put("errMsg","shopId为空");
		}
		return map;
	}
}
