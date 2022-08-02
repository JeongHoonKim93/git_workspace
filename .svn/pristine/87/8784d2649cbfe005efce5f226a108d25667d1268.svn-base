package risk.admin.alarm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import risk.DBconn.DBconn;
import risk.util.Log;

public class alarmMgr{
	public ArrayList getAlarmList(int nowpage, int rowcnt, HttpServletRequest request){
		ArrayList result = new ArrayList();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		int liststart = (nowpage - 1) * rowcnt;
		int listend = rowcnt;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("SELECT COUNT(*) AS CNT			\n");
			sb.append("  FROM ALARM_RULE A, KEYWORD B	\n");
			sb.append(" WHERE A.K_SEQ = B.K_SEQ			\n");
			sb.append(" ORDER BY AR_REGDATE DESC		\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				request.setAttribute("totalcnt", rs.getString("CNT"));
			}
			pstmt.close();
			
			sb = new StringBuffer();
			
			sb.append("SELECT A.AR_SEQ, A.AR_REGDATE, A.AR_TYPE, A.AR_TIME, A.AR_VALUE, A.AR_RECEIVER, A.AR_SENDTYPE, A.AR_AVG_CNT, A.K_SEQ	\n");
			sb.append("  FROM ALARM_RULE A, KEYWORD B	\n");
			sb.append(" WHERE A.K_SEQ = B.K_SEQ			\n");
			sb.append(" ORDER BY A.AR_REGDATE DESC		\n");
			sb.append("  LIMIT "+liststart+","+listend+"\n");
			
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				alarmBean ab = new alarmBean();
				ab.setAr_seq(rs.getString("AR_SEQ"));
				ab.setAr_regdate(rs.getString("AR_REGDATE"));
				ab.setAr_type(rs.getString("AR_TYPE"));
				ab.setAr_type(rs.getString("AR_TYPE"));
				ab.setAr_time(rs.getString("AR_TIME"));
				ab.setAr_value(rs.getString("AR_VALUE"));
				ab.setAr_receiver(rs.getString("AR_RECEIVER"));
				ab.setAr_sendtype(rs.getString("AR_SENDTYPE"));
				ab.setAr_avg_cnt(rs.getString("AR_AVG_CNT"));
				ab.setK_seq(rs.getString("K_SEQ"));
				result.add(ab);
			}
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		return result;
	}
	
	public alarmBean getAlarmInfo(String ar_seq){
		alarmBean ab = null;
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT AR_SEQ, AR_REGDATE, AR_TYPE, AR_TIME, AR_VALUE, AR_RECEIVER, AR_SENDTYPE, AR_AVG_CNT, K_SEQ\n");
			sb.append("FROM ALARM_RULE\n");
			sb.append("WHERE AR_SEQ = "+ar_seq+"\n");
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				ab = new alarmBean();
				ab.setAr_seq(rs.getString("AR_SEQ"));
				ab.setAr_regdate(rs.getString("AR_REGDATE"));
				ab.setAr_type(rs.getString("AR_TYPE"));
				ab.setAr_type(rs.getString("AR_TYPE"));
				ab.setAr_time(rs.getString("AR_TIME"));
				ab.setAr_value(rs.getString("AR_VALUE"));
				ab.setAr_receiver(rs.getString("AR_RECEIVER"));
				ab.setAr_sendtype(rs.getString("AR_SENDTYPE"));
				ab.setAr_avg_cnt(rs.getString("AR_AVG_CNT"));
				ab.setK_seq(rs.getString("K_SEQ"));
			}
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		return ab;
	}
	
	public int regAlarmData(alarmBean ab){
		int result = 0;
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		String ar_seq = "";
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("SELECT IFNULL(MAX(AR_SEQ)+1, 1) AS AR_SEQ FROM ALARM_RULE\n");
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				ar_seq = rs.getString("AR_SEQ");
			}
			pstmt.close();
			
			sb = new StringBuffer();
			sb.append("INSERT INTO ALARM_RULE(AR_SEQ,AR_REGDATE,AR_TYPE,AR_TIME,AR_VALUE,AR_RECEIVER,AR_SENDTYPE,AR_AVG_CNT,K_SEQ) VALUES\n");
			sb.append("(\n");
			sb.append(""+ar_seq+"\n");
			sb.append(",NOW()\n");
			sb.append(","+ab.getAr_type()+"\n");
			sb.append(","+ab.getAr_time()+"\n");
			sb.append(","+ab.getAr_value()+"\n");
			sb.append(",'"+ab.getAr_receiver()+"'\n");
			sb.append(","+ab.getAr_sendtype()+"\n");
			sb.append(","+ab.getAr_avg_cnt()+"\n");
			sb.append(","+ab.getK_seq()+"\n");
			sb.append(")\n");
			pstmt = dbconn.createPStatement(sb.toString());
			result = pstmt.executeUpdate();
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		return result;
	}
	
	public int updateAlarmData(alarmBean ab){
		int result = 0;
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		String ar_seq = "";
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("UPDATE ALARM_RULE SET\n");
			sb.append("AR_REGDATE = NOW()\n");
			sb.append(", AR_TYPE = "+ab.getAr_type()+"\n");
			sb.append(", AR_TIME = "+ab.getAr_time()+"\n");
			sb.append(", AR_VALUE = "+ab.getAr_value()+"\n");
			sb.append(", AR_RECEIVER = '"+ab.getAr_receiver()+"'\n");
			sb.append(", AR_SENDTYPE = "+ab.getAr_sendtype()+"\n");
			sb.append(", AR_AVG_CNT = "+ab.getAr_avg_cnt()+"\n");
			sb.append(", K_SEQ = "+ab.getK_seq()+"\n");
			sb.append("WHERE AR_SEQ = "+ab.getAr_seq()+"\n");
			pstmt = dbconn.createPStatement(sb.toString());
			result = pstmt.executeUpdate();
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		return result;
	}
	
	public int delAlarmData(String ar_seqs){
		int result = 0;
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		String ar_seq = "";
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("DELETE FROM ALARM_RULE WHERE AR_SEQ IN ("+ar_seqs+")\n");
			pstmt = dbconn.createPStatement(sb.toString());
			result = pstmt.executeUpdate();
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		return result;
	}
}