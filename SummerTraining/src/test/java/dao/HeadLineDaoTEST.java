package dao;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import entity.HeadLine;
import test.BaseTest;

public class HeadLineDaoTEST extends BaseTest{
	@Autowired
	private HeadLineDao headLineDao;
	@Test
	public void testquery() {
		HeadLine x=new HeadLine();
//		x.setStatus(1);
		 headLineDao.queryHeadLine(x);
		
	}
	
}
