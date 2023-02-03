import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.net.aso.e;

public class ConnectionDB {
	private Connection conn = null;
	private String jdbc_url = "jdbc:oracle:thin:@34.215.33.142:1521:kibwa";
	private String db_id = "neon";
	private String db_pwd = "pass";

	public void connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(jdbc_url, db_id, db_pwd);
			System.out.println("성공적으로 로딩되었음");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("해당 드라이버를 찾을 수 없습니다");
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("해당 드라이버를 찾을 수 없습니다");
		}
	}

	public static void main(String[] args) {
		new ConnectionDB().connect();
	}

}
