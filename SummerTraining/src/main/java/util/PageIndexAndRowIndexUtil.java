package util;

public class PageIndexAndRowIndexUtil {
	public static int pageIndexToRowIndex(int pageIndex,int pageSize) {
		if(pageIndex>0) {
			return (pageIndex-1)*pageSize;
		}
		else {
			return 0;
		}
	}
}
