package guest.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import member.beans.MemberException;

public class MessageDao {

	// Single Pattern 
	private static MessageDao instance;
	
	// DB 연결시  관한 변수 
	private static final String 	dbDriver	=	"oracle.jdbc.driver.OracleDriver";
	private static final String		dbUrl		=	"jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private static final String		dbUser		=	"scott";
	private static final String		dbPass		=	"tiger";
	
	
	
	//--------------------------------------------
	//#####	 객체 생성하는 메소드 
	public static MessageDao	getInstance() throws MessageException
	{
		if( instance == null )
		{
			instance = new MessageDao();
		}
		return instance;
	}
	
	private MessageDao() throws MessageException
	{
	
		try{
			
			/********************************************
				1. 오라클 드라이버를 로딩
					( DBCP 연결하면 삭제할 부분 )
			*/
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("MemberDAO 객체 생성 - 드라이버로딩");
			
	
		}catch( Exception ex ){
			throw new MessageException("방명록 ) DB 연결시 오류  : " + ex.toString() );	
		}
		
	}
	
	 
	/*
	 * 메세지를 입력하는 함수
	 */
	public void insert(Message rec) throws MessageException
	{
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try{
			// 1. 연결객체(Connection) 얻어오기
			String ur1 = "jdbc:oracle:thin:@192.168.0.36:1521:xe";
			String user = "scott";
			String pass = "tiger";
			con = DriverManager.getConnection(ur1, user, pass);
			System.out.println("디비 연결 성공");
			
			// 2. sql 문장 만들기
			String sql = "INSERT INTO guesttb(message_id, guest_name , password,message)  "
					+ " VALUES(seq_guestTb_messageId.nextval ,?,?,?)";
			// 3. 전송객체 얻어오기
			ps = con.prepareStatement(sql);
			
			// 4. 전송하기
			ps.setString(1, rec.getGuestName());
			ps.setString(2, rec.getPassword());
			ps.setString(3, rec.getMessage());
			
			ps.executeUpdate();
			
				
		}catch( Exception ex ){
			throw new MessageException("방명록 ) DB에 입력시 오류  : " + ex.toString() );	
		
		} finally{
			if( ps   != null ) { try{ ps.close();  } catch(SQLException ex){} }
			if( con  != null ) { try{ con.close(); } catch(SQLException ex){} }
		}
	
	}
	
	/*
	 * 메세지 목록 전체를 얻어올 때
	 */
	public List<Message> selectList() throws MessageException
	{
		Connection	 		con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Message> mList = new ArrayList<Message>();	// 메세지를 컬럼대로 저장할 배열 객체
		boolean isEmpty = true;
		
		try{
			
			String ur1 = "jdbc:oracle:thin:@192.168.0.36:1521:xe";
			String user = "scott";
			String pass = "tiger";
			con = DriverManager.getConnection(ur1, user, pass);
			System.out.println("디비 연결 성공");
			
			// 1. sql 문장 만들기
			String sql = "SELECT * FROM guesttb";
			
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();  
			
			while(rs.next()) {
				Message m = new Message();
				m.setMessageId(rs.getInt("MESSAGE_ID"));
				m.setGuestName(rs.getString("GUEST_NAME"));
				m.setMessage(rs.getString("MESSAGE"));
				m.setPassword(rs.getString("PASSWORD"));
				
				mList.add(m);
				isEmpty = false;
			}

			
			if( isEmpty ) return Collections.emptyList();
			
			return mList;
		}catch( Exception ex ){
			throw new MessageException("방명록 ) DB에 목록 검색시 오류  : " + ex.toString() );	
		} finally{
			if( rs   != null ) { try{ rs.close();  } catch(SQLException ex){} }
			if( ps   != null ) { try{ ps.close();  } catch(SQLException ex){} }
			if( con  != null ) { try{ con.close(); } catch(SQLException ex){} }
		}		
	}
	

	/* -------------------------------------------------------
	 * 현재 페이지에 보여울 메세지 목록  얻어올 때
	 */
	public List<Message> selectList(int firstRow, int endRow) throws MessageException
	{
		Connection	 		con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Message> mList = new ArrayList<Message>();
		boolean isEmpty = true;
		
		try{
			String ur1 = "jdbc:oracle:thin:@192.168.0.36:1521:xe";
			String user = "scott";
			String pass = "tiger";
			con = DriverManager.getConnection(ur1, user, pass);
			System.out.println("디비 연결 성공");
		/* 페이징
		 * 	- 추후에 프레임워크(springboot/mybatis 등)에 페이징 기법을 쉽게 사용
		 *  - 오라클 : hint / row_number()
		 *  
		 *  [1]단계
		 *  SELECT rownum, empno FROM emp; (PK인 경우)
		 *  SELECT rownum, empno FROM emp; (PK가 아닌 경우)
		 */
			// 1. sql 문장 만들기
			String sql = " SELECT *\r\n"
					+ "FROM   guesttb\r\n"
					+ "WHERE  message_id IN( SELECT message_id\r\n"
					+ "                      FROM ( SELECT rownum as rnum, message_id\r\n"
					+ "                             FROM (SELECT message_id FROM guesttb\r\n"
					+ "                             ORDER BY message_id DESC))\r\n"
					+ "                      WHERE  rnum>=? AND rnum<=?         \r\n"
					+ ")\r\n"
					+ "ORDER BY message_id DESC  ";
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, firstRow);
			ps.setInt(2, endRow);
			
			rs = ps.executeQuery();  
			

			while(rs.next()) {
				Message m = new Message();
				m.setMessageId(rs.getInt("MESSAGE_ID"));
				m.setGuestName(rs.getString("GUEST_NAME"));
				m.setMessage(rs.getString("MESSAGE"));
				m.setPassword(rs.getString("PASSWORD"));
				
				mList.add(m);
				isEmpty = false;
				
			}
			if( isEmpty ) return Collections.emptyList();
			
			return mList;
		}catch( Exception ex ){
			throw new MessageException("방명록 ) DB에 목록 검색시 오류  : " + ex.toString() );	
		} finally{
			if( rs   != null ) { try{ rs.close();  } catch(SQLException ex){} }
			if( ps   != null ) { try{ ps.close();  } catch(SQLException ex){} }
			if( con  != null ) { try{ con.close(); } catch(SQLException ex){} }
		}		
	}
	
	
	
	/* -------------------------------------------------------
	 * 메세지 전체 레코드 수를 검색
	 */
	
	public int getTotalCount() throws MessageException{
		Connection	 		con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;

		try{
			String ur1 = "jdbc:oracle:thin:@192.168.0.36:1521:xe";
			String user = "scott";
			String pass = "tiger";
			con = DriverManager.getConnection(ur1, user, pass);
			System.out.println("디비 연결 성공");
			
			String sql = "SELECT count(*) as cnt FROM guesttb";
			
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();  
			
			if(rs.next()) {
			
			count=rs.getInt(1);
			//count = rs.getRow(); 
			System.out.println(count);
			}
			return  count;
			
		}catch( Exception ex ){
			throw new MessageException("방명록 ) DB에 목록 검색시 오류  : " + ex.toString() );	
		} finally{
			if( rs   != null ) { try{ rs.close();  } catch(SQLException ex){} }
			if( ps   != null ) { try{ ps.close();  } catch(SQLException ex){} }
			if( con  != null ) { try{ con.close(); } catch(SQLException ex){} }
		}			
	}
	
	/*
	 * 메세지 번호와 비밀번호에 의해 메세지 삭제
	 */
	public int delete( int messageId, String password ) throws MessageException
	{
		int result = 0;
		Connection	 		con = null;
		PreparedStatement ps = null;
		try{

			String ur1 = "jdbc:oracle:thin:@192.168.0.36:1521:xe";
			String user = "scott";
			String pass = "tiger";
			con = DriverManager.getConnection(ur1, user, pass);
			System.out.println("디비 연결 성공");
			
			String sql = "DELETE FROM guesttb  "
					+  " WHERE message_id=? AND password=?";
			
			 ps = con.prepareStatement(sql);
			 
			 ps.setInt(1, messageId);
			 ps.setString(2, password);
			 
			 result = ps.executeUpdate();	// result : 몇행이 삭제되었는지 조회	
		//	 System.out.println(result);
			 
			 return result;			// result 값을 반환
			
		}catch( Exception ex ){
			throw new MessageException("방명록 ) DB에 삭제시 오류  : " + ex.toString() );	
		} finally{
			if( ps   != null ) { try{ ps.close();  } catch(SQLException ex){} }
			if( con  != null ) { try{ con.close(); } catch(SQLException ex){} }
		}		
	}
}
