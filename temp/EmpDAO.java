package temp;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class EmpDAO {
	private EmpDAO() throws Exception{
		// 1. 드라이버 로딩
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("EmpDao 객체 생성 - 드라이버로딩");

	}
	
	static EmpDAO empDAO = null; // empDAO에는 주소값이 담겨있음.
	public static EmpDAO getInstance() throws Exception{
		// EmpDAO 클래스를 객체 생성 없이 사용하기 위해
		if(empDAO == null)
		empDAO = new EmpDAO();	
		return empDAO;
	}	// 메모리의 단 한개의 값만 들어올 수 있게

	public void insert(EmpVO vo) throws Exception{
		// vo 데이터를 받음.
		// 2. 연결객체 얻어오기

		try {
			String ur1 = "jdbc:oracle:thin:@192.168.0.36:1521:xe";
			String user = "scott";
			String pass = "tiger";
			Connection con = DriverManager.getConnection(ur1, user, pass);
			System.out.println("디비 연결 성공");

			// 3. SQL 문장
			String sql = "INSERT INTO emp(EMPNO, ENAME , DEPTNO ,JOB , SAL) "
					+ " VALUES( ? , ? , ? , ? ,?)";	// 한줄 내려서 작성 할 경우 공백 필수 
			System.out.println(sql);
			// 4. SQL 전송객체
			PreparedStatement stmt = con.prepareStatement(sql);

			// 5. SQL 전송
			/*		- ResultSET executeQuery() : SELECT
			 * 		- int형 (입력/삭제/수정 값을 리턴하기 때문에 int형) executeUpdate() : INSERT / DELETE / UPDATE
			 */
			stmt.setInt(1,vo.getEmpno());
			stmt.setString(2,vo.getEname());
			stmt.setInt(3,vo.getDeptno());
			stmt.setString(4,vo.getJob());
			stmt.setInt(5,vo.getSal());

			stmt.executeUpdate();
		}finally {	
			// 6. 닫기 (순서 필수) : 
		

		} 

	}	
}