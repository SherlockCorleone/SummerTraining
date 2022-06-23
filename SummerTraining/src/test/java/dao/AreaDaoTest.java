package dao;



import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;



import entity.Area;
import test.BaseTest;

public class AreaDaoTest extends BaseTest{
	@Autowired 
	private AreaDao areaDao;
	@Test
	public void testQueryArea() {
		List<Area>areaList=areaDao.queryArea();
		assertEquals(5,areaList.size());
	}
}
