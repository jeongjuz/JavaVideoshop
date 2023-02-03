package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RentDAO {

	// ###########################################################
	// DB control method
	Connection conn;
	String jdbc_url = "jdbc:oracle:thin:@192.168.0.67:1521:kibwa";
	String db_id = "videoshop";
	String db_pwd = "pass";
	Statement stmt = null;
	PreparedStatement ps = null;
	String driver = "oracle.jdbc.driver.OracleDriver";

	public RentDAO() throws Exception {
		// =========================================
		// 1. 드라이버를 메모리에 로딩
		// 2. Connection 얻어오기]
		connectDB();

	}

	void connectDB() throws Exception {
		/*
		 * 1. 드라이버를 드라이버메니저에 등록 2. 연결 객체 얻어오기
		 */
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(jdbc_url, db_id, db_pwd);
		System.out.println("성공적으로 로딩되었음");

	}

	public String findCustName(String custTel) throws Exception {

		String custName = null;

		// =========================================
		// 1. sql 쿼리 만들기
		// ` 입력받은 전화번호의 고객명을 검색하라
		// ` select custName from customerTab where custTel=넘겨받은인자값
		// 2. sql 전송 객체 얻어오기
		// 3. sql 전송하기
		// 4. 결과값을 받아 custName 변수에 지정
		// 5. sql 닫기
		String sql = "select custName from customertab where custtel = '" + custTel + "'";

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		if (rs.next()) {
			custName = rs.getString("custname");
		}

		return custName;
	}

	public void videoRent(String custTel, String custName, int videoNum) throws Exception {
		String sql = "insert into rentTab"
				+ "(RENTNUM, RENTCUSTTEL, RENTCUSTNAME, RENTVIDEONUM, RENTDATE, RETURNSCHEDULED, RETURNFLAG, RENTCHARGE)"
				+ "values" + "(rentNumSeq.nextval, ?, ?, ?, sysdate, sysdate+3,'N', 500)";
		//
		// 2. sql 전송 객체 얻어오기
		// 3. sql 전송하기
		// 5. sql 닫기
		ps = conn.prepareStatement(sql);
		ps.setString(1, custTel);
		ps.setString(2, custName);
		ps.setInt(3, videoNum);
		
		ps.executeUpdate();
		ps.close();

	}

	public void videoReturn(int videoNum) throws Exception {
		// =========================================
		// 1. sql 쿼리 만들기
		// ` 입력받은 비디오번호로 rentTab 테이블의 returnFlag 컬럼을 true로 변경하라
		// ` update rentTab set returnFlag='false', returndate=sysdate where
		// videoNum=넘겨받은인자값 AND returnFlag='true'
		//
		// 2. sql 전송 객체 얻어오기
		// 3. sql 전송하기
		// 4. sql 닫기
		
		String sql = "update renttab set returnflag = 'Y', returndate = sysdate "
				+ "where RENTVIDEONUM = ?"
				+ "and returnflag = 'N'";
		//
		// 2. sql 전송 객체 얻어오기
		// 3. sql 전송하기
		// 5. sql 닫기
		ps = conn.prepareStatement(sql);
		ps.setInt(1, videoNum);
		
		ps.executeUpdate();
		ps.close();

	}

	public ArrayList recentList() throws Exception {
		ArrayList list = new ArrayList();

		// =========================================
		// 1. sql 쿼리 만들기
		// ` 비디오테이블과 대여테이블에서 조인하여 비디오제목, 감독, 배우, 반납예정일, 반납여부를 검색하여 최근 목록 중 10개 이내로 검색하라
		// # oracle
		// ` select v.videoTitle, v.videoDirector, v.videoActor, r.returnScheduled,
		// r.returnFlag from rentTab r , videoTab v where r.videoNum(+)=v.videoNum and
		// rownum<=10 order by v.videoNum desc;
		// # MS-SQL
		// ` select top 10 v.videoTitle, v.videoDirector, v.videoActor,
		// r.returnScheduled, r.returnFlag from rentTab r right outer join videoTab v on
		// r.videoNum=v.videoNum order by v.videoNum desc;
		// 2. sql 전송 객체 얻어오기
		// 3. sql 전송하기
		// 4. 결과값을 받아 ArrayList 변수에 지정
		// 5. sql 닫기
		Statement stmt = conn.createStatement();
		String sql = "SELECT " + "v.VIDEONUM VIDEONUM, " + "v.VIDEOTITLE TITLE, " + "cus.CUSTNAME CUSTNAME, "
				+ "cus.CUSTTEL CUSTTEL, " + "rent.RENTDATE RENTDATE, " + "DECODE(rent.RETURNFLAG, 'N', '미납') RETURN "
				+ "FROM videotab v, customertab cus, renttab rent " + "WHERE rent.RETURNFLAG = 'N' "
				+ "and cus.CUSTTEL = rent.RENTCUSTTEL " + "and rent.RENTVIDEONUM = v.VIDEONUM";

		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			System.out.println(rs.getString("VIDEONUM"));
			ArrayList temp = new ArrayList();
			temp.add(rs.getInt("VIDEONUM"));
			temp.add(rs.getString("TITLE"));
			temp.add(rs.getString("CUSTNAME"));
			temp.add(rs.getString("CUSTTEL"));
			temp.add(rs.getString("RENTDATE").substring(0, 8)
					+ String.valueOf(Integer.parseInt(rs.getString("RENTDATE").substring(8, 10)) + 3));
			temp.add(rs.getString("RETURN"));
			list.add(temp);
		}
		rs.close();
		stmt.close();
		return list;
	}

	public boolean isPossible(int videoNum) throws SQLException {
		String sql = "select RETURNFLAG " + "from renttab " + "where RETURNFLAG = 'N' " + "and rentVideoNum = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, videoNum);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			System.out.println(rs.getString("RETURNFLAG"));
			return false;
		}
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isRentPossiblie(int videoNum) throws Exception{
		String sql = "select RETURNFLAG " + "from renttab " + "where RETURNFLAG = 'N' " + "and rentVideoNum = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, videoNum);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			System.out.println(rs.getString("RETURNFLAG"));
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

}
