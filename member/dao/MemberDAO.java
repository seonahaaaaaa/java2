package member.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


	public class MemberDAO {
		private MemberDAO() throws Exception{
			/* 1) 접근제한자를 private 으로 설정하여 해당 클래스를 다른 클래스에서 
			 new 연산자를 통해 생성을 하지 못하므로 사용 불가 
			 *** 생성자를 외부에서 호출 할 수 없도록 막기 위해 private 사용 ***
			 */
			
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("MemberDAO 객체 생성 - 드라이버로딩");

		}
		
		static MemberDAO memberDAO = null;
		 // getInstance 함수가 호출 될 때마다 객체가 생성되고 드라이버 로딩이 되는 구조로
		 // 드라이버 로딩이 한번만 로딩 되기 위해 memberDAO 변수가 null 값을 깔고감.
		public static MemberDAO getInstance() throws Exception{
			// 2) 외부 클래스에서 getInstance() 인스턴스 값을 보내기 위해 외부 파일에 공유 가능한 
			// public static 접근 제한자를 두어 
			if(memberDAO == null)
				memberDAO = new MemberDAO();	
			return memberDAO;
		}	// 메모리가 여러값이 올라올때 단 한개의 값만 들어올 수 있게

		public void insert(MemberVO vo) throws Exception{
			// vo 데이터를 받음.
			// 2. 연결객체 얻어오기
			Connection con = null;
			PreparedStatement stmt = null;
	
			try {
				String ur1 = "jdbc:oracle:thin:@192.168.0.26:1521:xe";
				String user = "scott";
				String pass = "tiger";
				con = DriverManager.getConnection(ur1, user, pass);
				System.out.println("디비 연결 성공");

				// 3. SQL 문장
				String sql = "INSERT INTO form(realname, nickname , email ,age) "
						+ " VALUES( ? , ? , ? , ? )";	// 한줄 내려서 작성 할 경우 공백 필수 
				System.out.println(sql);
				// 4. SQL 전송객체
				stmt = con.prepareStatement(sql);

				// 5. SQL 전송
				/*		- ResultSET executeQuery() : SELECT
				 * 		- int형 (입력/삭제/수정 값을 리턴하기 때문에 int형) executeUpdate() : INSERT / DELETE / UPDATE
				 */
				stmt.setString(1,vo.getRealname());
				stmt.setString(2,vo.getNickname());
				stmt.setString(3,vo.getMyemail());
				stmt.setInt(4,vo.getMyage());

				stmt.executeUpdate();
				
			}finally {	
				// 6. 닫기 (순서 필수) :
				stmt.close();
				con.close();
			
		
			} 

		}	
	}
	

