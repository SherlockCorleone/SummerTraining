package service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.HeadLineDao;
import entity.HeadLine;
import service.HeadLineService;

@Service
public class HeadLineServiecImp implements HeadLineService {
	@Autowired
	private HeadLineDao headLineDao;
	
	@Override
	public List<HeadLine> getHeadLine(HeadLine headLine)  {
		return headLineDao.queryHeadLine(headLine);
	}

}
