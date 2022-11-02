package guest.model;

// 오라클 db에서 생성한 테이블의 요소들의 값을 받을 클래스 생성
public class Message {
		// 오라클 db에 생성 된 테이블의 컬럼 명칭이랑 변수 이름이 동일해야함.
	private int    messageId;
	private String guestName;
	private String password;
	private String message;
	
	public Message() {}

	public Message(int messageId, String guestName, String password, String message) {
		super();
		this.messageId = messageId;
		this.guestName = guestName;
		this.password = password;
		this.message = message;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
