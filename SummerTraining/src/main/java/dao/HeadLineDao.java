package dao;

import java.util.List;

import entity.HeadLine;

public interface HeadLineDao {
	List<HeadLine> queryHeadLine(HeadLine headLine);
}
