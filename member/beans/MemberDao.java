package member.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import member.dao.MemberDAO;

public class MemberDao {

	
	// DB 연결시  관한 변수 

	private static final String 	dbDriver	=	"oracle.jdbc.driver.OracleDriver";
	private static final String		dbUrl		=	"jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private static final String		dbUser		=	"scott";
	private static final String		dbPass		=	"tiger";

		
	private static MemberDao memberDao;
	
	public static MemberDao getInstance() throws MemberException
	{
		if( memberDao == null )
		{
			memberDao =  new MemberDao();
		}
		return memberDao;
	}
	

	private MemberDao() throws MemberException
	{
			
		try{
			/********************************************
				1. 드라이버를 로딩
				
			*/
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("MemberDAO 객체 생성 - 드라이버로딩");
			
		}catch( Exception ex ){
			throw new MemberException("DB 연결시 오류  : " + ex.toString() );	
		}
	}
	
	/*******************************************
	 * * 회원관리테이블 MEMBERTEST 에  회원정보를 입력하는 함수
	 * @param rec
	 * @throws MemberException
	 */
	

	public void insertMember( Member rec ) throws MemberException
	{
		try {
			 // 0. 연결 객체 얻어오기	
			Connection con = null;
			PreparedStatement stmt = null;
			
			String ur1 = "jdbc:oracle:thin:@192.168.0.36:1521:xe";
			String user = "scott";
			String pass = "tiger";
			con = DriverManager.getConnection(ur1, user, pass);
			System.out.println("디비 연결 성공");
			
			 // 1. sql 문장 만들기 ( insert문 )
			String sql = "INSERT INTO memberTest(id, password , name , tel, addr) "
					+ " VALUES( ? , ? , ? , ? , ?)";	
			 // 2. sql 전송 객체 만들기
			stmt = con.prepareStatement(sql);
			 // 3. sql 전송
			stmt.setString(1, rec.getId());
			stmt.setString(2, rec.getPassword());
			stmt.setString(3, rec.getName());
			stmt.setString(4, rec.getTel());
			stmt.setString(5, rec.getAddr());
			
			stmt.executeUpdate();
			
			 // 4. 객체 닫기
			stmt.close();
			con.close();
		} catch ( Exception ex ){
			throw new MemberException("멤버 입력시 오류  : " + ex.toString() );			
		}			
	}
	
	/**********************************************************
	 * * 회원관리테이블 MEMBERTEST에서 기존의 id값과 중복되는지 확인하는 함수
	 */
	public boolean isDuplicatedId( String id ) throws MemberException
	{
		boolean flag = false;
			// CheckId.jsp 에서 리턴 할 값
		
		try{
			
			Connection con = null;
			PreparedStatement ps = null;
			
			String ur1 = "jdbc:oracle:thin:@192.168.0.36:1521:xe";
			String user = "scott";
			String pass = "tiger";
			con = DriverManager.getConnection(ur1, user, pass);
			System.out.println("디비 연결 성공");
			
			
			String sql = "SELECT * FROM membertest "
					+ "  WHERE id=?";
				// membertest 테이블에서 id 값을 찾음
			
			ps = con.prepareStatement(sql);
				// sql문을 실행하고 결과를 리턴하여 ps에 담음
			
			ps.setString(1, id);
				// 	입력한 id 값을 가져옴.
			ResultSet rs = ps.executeQuery();
				// ps에 담겨진 데이터들을 가져와 rs 에 대입
			
			if(rs.next()) {	// 테이블에 데이터에가 있을때
							// 해당하는 id가 있는지 없는지 확인하기 위해 if문
				flag = true;	// true를 반환
			}
			rs.close();
			ps.close();
			con.close();
			
		}catch( Exception ex ){
			throw new MemberException("중복아이디 검사시 오류  : " + ex.toString() );			
		}
			
		return flag;	// CheckId.jsp에 결과를 반환
	}


		public boolean checkLogin(String id, String password)
		
		throws Exception{
			boolean result = false;
			
			try{
				
				Connection con = null;
				PreparedStatement ps = null;
				
				String ur1 = "jdbc:oracle:thin:@192.168.0.36:1521:xe";
				String user = "scott";
				String pass = "tiger";
				con = DriverManager.getConnection(ur1, user, pass);
				System.out.println("디비 연결 성공");
				
				
				String sql = "SELECT * FROM membertest "
						+ "  WHERE id=? AND password=?";
					// membertest 테이블에서 id 값을 찾음
				
				ps = con.prepareStatement(sql);
					// sql문을 실행하고 결과를 리턴하여 ps에 담음
				
				ps.setString(1, id);
				ps.setString(2, password);
					// 	입력한 id 값을 가져옴.
				ResultSet rs = ps.executeQuery();
					// ps에 담겨진 데이터들을 가져와 rs 에 대입
				
				if(rs.next()) {	// 테이블에 데이터에가 있을때
								// 해당하는 id가 있는지 없는지 확인하기 위해 if문 , true를 반환
					result = true;	// true를 반환
				}
				rs.close();
				ps.close();
				con.close();
				
			}catch( Exception ex ){
				throw new MemberException("중복아이디 검사시 오류  : " + ex.toString() );			
			}
			return result;
			
			
			
		}
	
		


}







