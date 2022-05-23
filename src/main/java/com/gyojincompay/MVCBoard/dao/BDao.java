package com.gyojincompay.MVCBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.gyojincompay.MVCBoard.dto.BDto;

public class BDao {
	
	
	DataSource dataSource;
	
	public BDao() {
		super();
		// TODO Auto-generated constructor stub		
		
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	public ArrayList<BDto> list() {
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String query = "select * from mvc_board order by bgroup desc, bstep asc";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				 int bId = rs.getInt("bid");
				 String bName = rs.getString("bname");
				 String bTitle = rs.getString("btitle");
				 String bContent = rs.getString("bcontent");
				 Timestamp bDate = rs.getTimestamp("bdate");
				 int bHit = rs.getInt("bhit");
				 int bGroup = rs.getInt("bgroup");
				 int bStep = rs.getInt("bstep");
				 int bIndent = rs.getInt("bindent");
				 
				 BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				 dtos.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dtos;
	}
	
	public void write(String bname, String btitle, String bcontent) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;		
		
		try {
			conn = dataSource.getConnection();
			String query = "INSERT INTO mvc_board(bid, bname, btitle, bcontent, bhit, bgroup, bstep, bindent) VALUES (mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0)";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, bname);
			pstmt.setString(2, btitle);
			pstmt.setString(3, bcontent);
			
			pstmt.executeUpdate();//데이터 삽입에 성공하면 1이 반환
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		
	}
	
	
	
	
	
	
	
	
}
