package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import model.rec.VideoVO;

public class VideoDAO {

	Connection conn;
	String jdbc_url = "jdbc:oracle:thin:@192.168.0.67:1521:kibwa";
	String db_id = "videoshop";
	String db_pwd = "pass";
	Statement stmt = null;
	PreparedStatement ps = null;
	String driver = "oracle.jdbc.driver.OracleDriver";

	public VideoDAO() throws Exception {
		/*
		 * ============================================ 생성자 함수 - DB 연결 1. 드라이버를 로딩 2.
		 * Connection 얻어오기
		 */
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

	public void videoInsert(VideoVO vo, int count) throws Exception {
		/*
		 * ============================================ 비디오 정보 입력
		 * 
		 * 1. sql 작성하기 ( insert 문 작성 : 멤버필드를 변수로 지정하여 ) [ MS-SQL ] INSERT INTO videoTab(
		 * videoJanre,videoTitle, videoActor, videoDirector,vodeoContent) values( ?, ?,
		 * ?, ?, ?, getDate() )
		 * 
		 * [ ORACLE ] INSERT INTO videoTab values( videoNumSeq.nextval, ?, ?, ?, ?, ?,
		 * sysdate )
		 * 
		 * 2. sql 전송객체 얻어오기 ( PreparedStatement가 더 적합할 듯 ) 3. sql 전송 ( 전송전에 ?로 지정 ) ##
		 * count 만큼 반복 4. 닫기 ( Connection은 닫으면 안됨 )
		 */
		String sql = "INSERT INTO "
				+ "videoTab(videoNum, videoJanre, videoTitle, videoDirector, videoActor, videoContent, videoRegDate)"
				+ "values(videoNumSeq.nextval, ?, ?, ?, ?, ?, sysdate)";

		ps = conn.prepareStatement(sql);
		ps.setString(1, vo.getGenre());
		ps.setString(2, vo.getTitle());
		ps.setString(3, vo.getDirector());
		ps.setString(4, vo.getActor());
		ps.setString(5, vo.getContent());
		for (int i = 0; i < count; i++) {
			ps.executeUpdate();
		}
		ps.close();
	}

	public void videoModify(VideoVO vo, int count) throws Exception {
		
		String sql = "update videoTab set "
				+ "videoJanre = ?, videoTitle = ? , videoDirector = ?, videoActor = ?, videoContent = ?, videoRegDate = sysdate "
				+ "where videoNum = ?";

		ps = conn.prepareStatement(sql);
		ps.setString(1, vo.getGenre());
		ps.setString(2, vo.getTitle());
		ps.setString(3, vo.getDirector());
		ps.setString(4, vo.getActor());
		ps.setString(5, vo.getContent());
		ps.setString(6, vo.getVideono());
		for (int i = 0; i < count; i++) {
			ps.executeUpdate();
		}
		ps.close();

	}

	public void videoDelete() throws Exception {

	}

	public ArrayList videoSearch(int key, String word) throws Exception {
		/*
		 * ============================================ 비디오 검색하기
		 * 
		 * 1. sql 작성하기 ( select 문 작성 : 조건 지정하여 ) # SELECT videoNum, videoJanre,
		 * videoTitle, videoDirector, videoActor, videoRegDate FROM videoTab WHERE key값
		 * like '%word값%'
		 * 
		 * 2. sql 전송객체 얻어오기 ( Statement여야함 ) 3. sql 전송 ( executeQuery() 이용 ) ( 결과 받아
		 * Vector 에 지정 ) 4. 닫기 ( Connection은 닫으면 안됨 )
		 */
		String searchText[] = { "VIDEOTITLE", "VIDEODIRECTOR", "VIDEOACTOR" };
		String sql = "SELECT " + "videoNum, videoTitle, videoJanre, videoDirector, "
				+ "videoActor, videoContent, videoRegDate " + "FROM videoTab " + "WHERE " + searchText[key] + " like '%"
				+ word + "%'";
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		ArrayList list = new ArrayList();
		while (rs.next()) {
			ArrayList temp = new ArrayList();
			temp.add(rs.getInt("VIDEONUM"));
			temp.add(rs.getString("VIDEOTITLE"));
			temp.add(rs.getString("VIDEOJANRE"));
			temp.add(rs.getString("VIDEODIRECTOR"));
			temp.add(rs.getString("VIDEOACTOR"));
			temp.add(rs.getString("VIDEOCONTENT"));
			temp.add(rs.getString("VIDEOREGDATE"));
			list.add(temp);
		}

		return list;
	}

	public VideoVO findByNum(int vnum) throws Exception {
		/*
		 * =================================== 
		 * 해당하는 비디오번호에 의한 비디오 검색하기
		 * 
		 * 1. sql 작성하기 ( select 문 작성 : 조건 지정하여 ) 
		 * # SELECT * FROM videoTab WHERE
		 * videoNum=?
		 * 
		 * 2. sql 전송객체 얻어오기 
		 * 3. sql 전송 ( executeQuery() 이용 ) ( 결과 받아 멤버필드에 지정 ) 
		 * 4. 닫기 (
		 * Connection은 닫으면 안됨 )
		 * 
		 */
		VideoVO vo = new VideoVO();
		String sql = 
	    "SELECT videoNum, videoTitle, videoJanre, videoDirector, videoActor, videoContent FROM videoTab WHERE videoNum =" + vnum;
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		// 번호 장르 제목 감독 배우 설명 
		if (rs.next()) {
			vo.setVideono(rs.getString("VIDEONUM"));
			vo.setTitle(rs.getString("VIDEOTITLE"));
			vo.setGenre(rs.getString("VIDEOJANRE"));
			vo.setDirector(rs.getString("VIDEODIRECTOR"));
			vo.setActor(rs.getString("VIDEOACTOR"));
			vo.setContent(rs.getString("VIDEOCONTENT"));
		}

		return vo;
	}

}
