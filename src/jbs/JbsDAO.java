package jbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class JbsDAO {
	private Connection conn;
	private ResultSet rs;
	
	public JbsDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/JBS?serverTimezone=UTC";
			String dbID = "root";
			String dbPassword = "1234";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDate() { //�ۼ��ϴ� �ð� 
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public int getNext() { //�� ���� id �� �ο��ϴ� �޼ҵ� 
		String SQL = "SELECT jbsID FROM JBS ORDER BY jbsID DESC"; //ū ���ڰ� �� ���� ������
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) { //ù���� �Խù��� ������ 
				return rs.getInt(1) + 1;
			}
			
			return 1; //ù���� �Խù� 
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int write(String jbsTitle, String userID, String jbsContent) {
		String SQL = "INSERT INTO JBS VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, jbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setNString(5, jbsContent);
			pstmt.setInt(6, 1);
			
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //�����ͺ��̽� ����
	}
	
	public ArrayList<Jbs> getList(int pageNumber) {
		//jbsAvailable�� 1�̸� ���� ���� ����. �ѹ��� 10���� ������
		String SQL = "SELECT * FROM JBS WHERE jbsID < ? AND jbsAvailable = 1 ORDER BY jbsID DESC LIMIT 10";
		
		ArrayList<Jbs> list = new ArrayList<Jbs>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber-1) * 10); //getNext()�� ���� �Խù� , 31 �Խù��̸� getNext - 3 * 10 
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Jbs jbs = new Jbs();
				
				jbs.setJbsID(rs.getInt(1));
				jbs.setJbsTitle(rs.getString(2));
				jbs.setUserID(rs.getString(3));
				jbs.setJbsDate(rs.getString(4));
				jbs.setJbsContent(rs.getString(5));
				jbs.setJbsAvailable(rs.getInt(6));
				
				list.add(jbs);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public boolean nextPage(int pageNumber) {
		String SQL = "SELECT * FROM JBS WHERE jbsID < ? AND jbsAvailable = 1";
		
		ArrayList<Jbs> list = new ArrayList<Jbs>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber-1) * 10); //getNext()�� ���� �Խù� , 31 �Խù��̸� getNext - 3 * 10 
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}

			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Jbs getJbs(int jbsID) {
String SQL = "SELECT * FROM JBS WHERE jbsID = ?";
		
		ArrayList<Jbs> list = new ArrayList<Jbs>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, jbsID); //getNext()�� ���� �Խù� , 31 �Խù��̸� getNext - 3 * 10 
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Jbs jbs = new Jbs();
				
				jbs.setJbsID(rs.getInt(1));
				jbs.setJbsTitle(rs.getString(2));
				jbs.setUserID(rs.getString(3));
				jbs.setJbsDate(rs.getString(4));
				jbs.setJbsContent(rs.getString(5));
				jbs.setJbsAvailable(rs.getInt(6));
				
				return jbs;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}

	
