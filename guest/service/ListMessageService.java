package guest.service;

import guest.model.Message;
import guest.model.MessageDao;
import guest.model.MessageException;

import java.util.List;

public class ListMessageService {

	//-------------------------------------------------------------------
	private int totalRecCount;		// 전체 레코드 수	
	private int pageTotalCount;		// 전체 페이지 수
	private int countPerPage = 3;	// 한페이지당 레코드 수
	
	private static ListMessageService instance;
	
	public static ListMessageService	getInstance() throws MessageException
	{
		if( instance == null )
		{
			instance = new ListMessageService();
		}
		return instance;
	}
	
	private ListMessageService()
	{
		
	}
	
	public List <Message> getMessageList(String pNum) throws MessageException
	{
		int pageNum = 1; // 초기화면은 페이지 값이 측정이 되지 않아 null 값으로 들어옴. 
		if(pNum != null) //   페이지 값을 눌렀을때 페이지 값이 조회가 되면 넘어온 데이터를 정수형으로 형변환 하여 	
			pageNum = Integer.parseInt(pNum);	// pageNum에 대입
		
		
		/*
		 *			페이지 번호 		시작레코드번호			끝레코드번호 
		 *
		 */
		
		int startRow = countPerPage*pageNum-2;

		int endRow = pageNum * countPerPage;
		
		// 전체 레코드를 검색해 온다면
		List <Message> mList = MessageDao.getInstance().selectList(startRow,endRow);			
		return mList;
	}
	
	public int getTotalPage() throws MessageException
	{
		// 전체 레코드수
		totalRecCount = MessageDao.getInstance().getTotalCount();
		/*
		 * 	전체레코드수 	 페이지수
		 * 		10			4
		 * 		9			3
		 * 		11			4		
		 * 		12			4	
		 * 		13			5		
		 */
		int pageTotalCount = totalRecCount/countPerPage;
		
		if(pageTotalCount%totalRecCount > 0) {	// 전체 레코드를 한페이지당 레코드 수로 나눴을때 나머지가 1 이상일 경우, 페이지 수를 +1 해줌
			pageTotalCount++;
		}
		
		// 전체 레코드 수에 따른 전체 페이지 수 
		return pageTotalCount;
	}
}
