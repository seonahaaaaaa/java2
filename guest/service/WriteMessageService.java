package guest.service;

import guest.model.Message;
import guest.model.MessageDao;
import guest.model.MessageException;

public class WriteMessageService {
	// WriteMessageService : MessageDao 와 saveMessage의 중간 매체 역할
	// 	=> 	MessageDao & saveMessage 의 기존 코드를 최대한 수정하지 않기 위해 중간에 클래스를 두어 사용 (유지보수에 편리 & 가독성 용이)
	//		출력되는 화면이 아니고 데이터를 관리하는 클래스

	private static WriteMessageService instance;
	
	public static WriteMessageService	getInstance() throws MessageException
	{
		if( instance == null )
		{
			instance = new WriteMessageService();
		}
		return instance;
	}
	
	private WriteMessageService()
	{
		
	}
	
	public void write( Message rec ) throws MessageException
	{	
		MessageDao mDao = MessageDao.getInstance();
		mDao.insert(rec);
	
	}
}
