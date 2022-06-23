package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.Shop;

public interface ShopDao {
	int insertShop(Shop shop);//增加店铺
	int updateShop(Shop shop);//更新店铺
	Shop queryShopById(Long shopId);//查询店铺信息
	/**
	 * 模糊查询：店名、店铺状态、店铺类别、店铺主人、区域
	 * @param shop 
	 * @param rowIndex 从哪里开始取数据
	 * @param pageSize 取多少数据
	 * @return
	 */
	List<Shop> queryShop(@Param("shop") Shop shop,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	/**
	 * 因为上面的没有取出所有数据，这个函数返回所有的结果的综合
	 * @param shop
	 * @return
	 */
	int queryShopCount(@Param("shop") Shop shop);
}
