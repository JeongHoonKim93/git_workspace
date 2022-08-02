package risk.admin.membergroup;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import risk.DBconn.DBconn;
import risk.util.Log;

public class membergroupMng {
	
   private static membergroupMng instance = new membergroupMng();


    public static membergroupMng getInstance() {
        return instance;
    }

	public membergroupMng() {

	}

    //멤버그룹을 가져온다.
    public List getMGList() throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
		List MGList = null;
		StringBuffer sb = new StringBuffer();

	        try {
	        	conn  = new DBconn();
	       	 	conn.getDBCPConnection();
				sb.append("SELECT MG_SEQ, MG_XP, MG_SITE, MG_MENU, MG_NAME ");
				sb.append("FROM MEMBER_GROUP WHERE MG_NAME != 'system'");
				sb.append("ORDER BY MG_NAME ASC");
				
	            pstmt = conn.createPStatement( sb.toString() );
	            rs = conn.executeQuery(pstmt);

	            MGList = new ArrayList();
	            while (rs.next()) {
	            	membergroupBean mg = new membergroupBean();

	                mg.setMGseq(rs.getString("MG_SEQ"));
	                mg.setMGxp(rs.getString("MG_XP"));
	                mg.setMGsite(rs.getString("MG_SITE"));
	                mg.setMGmenu(rs.getString("MG_MENU"));
	                mg.setMGname(rs.getString("MG_NAME"));

	                MGList.add(mg);
	            }
	        } catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	        }
		return MGList;
    }

    //사용자그룹을 가져온다.
    public String getMG(String MGseq)
    throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
        PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		String MGval = "";

        try {
        	conn  = new DBconn();
        	conn.getDBCPConnection();
			stmt = conn.createStatement();

			sb.append("SELECT MG_NAME FROM MEMBER_GROUP WHERE MG_SEQ="+MGseq);
            
			pstmt = conn.createPStatement( sb.toString() );
			rs = conn.executeQuery(pstmt);
			if( rs.next() ) {
				MGval = rs.getString("MG_NAME");
            }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return MGval;
    }
    
    //사용자그룹을 추가한다.
    public void insertMG(String MGval)
    throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
        PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

        try {
        	conn  = new DBconn();
        	conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb.append("INSERT INTO MEMBER_GROUP(MG_NAME,MG_SITE,MG_XP,MG_MENU) VALUES(?,'','','') ");
            
			pstmt = conn.createPStatement( sb.toString() );
			pstmt.setString(1, MGval);
			
            pstmt.executeUpdate();
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
    }
    
    //사용자그룹을 수정한다.
    public void updateMG(String MGseq, String MGval)
    throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
        PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

        try {
        	conn  = new DBconn();
        	conn.getDBCPConnection();
			stmt = conn.createStatement();

			sb.append("UPDATE MEMBER_GROUP SET MG_NAME=? WHERE MG_SEQ=? ");
            
			pstmt = conn.createPStatement( sb.toString() );
			pstmt.setString(1, MGval);
			pstmt.setString(2, MGseq);
			
            pstmt.executeUpdate();
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
    }
    
    //사용자그룹을 삭제한다.
    public int deleteMG(String MGseq)
    throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
        PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		int MGct = 0;

        try {
        	conn  = new DBconn();
        	conn.getDBCPConnection();
			stmt = conn.createStatement();

            pstmt = conn.createPStatement("SELECT COUNT(M_SEQ) MGCT FROM MEMBER WHERE MG_SEQ="+MGseq);
            rs = pstmt.executeQuery();
            if( rs.next() ) {
            	MGct = rs.getInt("MGCT");
            }
            
            if( MGct == 0 ) {
				sb.append("DELETE FROM MEMBER_GROUP WHERE MG_SEQ=? ");
		            
				pstmt = conn.createPStatement( sb.toString() );
				pstmt.setString(1, MGseq);
					
	            pstmt.executeUpdate();
            }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return MGct;
    }

    //사용자그룹의 설정을 가져온다.
    public membergroupBean getMGBean( String MGseq ) throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		membergroupBean mg = null;

	        try {
	        	conn  = new DBconn();
	       	 	conn.getDBCPConnection();
				sb.append("SELECT MG_SEQ, MG_XP, MG_SITE, MG_MENU, MG_NAME, MG_COMPANY \n");
				sb.append("FROM MEMBER_GROUP \n");
				sb.append("WHERE MG_SEQ="+MGseq+" \n");
				
				System.out.println(sb.toString());
	            pstmt = conn.createPStatement( sb.toString() );
	            rs = conn.executeQuery(pstmt);

            	mg = new membergroupBean();
	            if(rs.next()) {
	                mg.setMGseq(rs.getString("MG_SEQ"));
	                mg.setMGxp(rs.getString("MG_XP"));
	                mg.setMGsite(rs.getString("MG_SITE"));
	                mg.setMGmenu(rs.getString("MG_MENU"));
	                mg.setMGname(rs.getString("MG_NAME"));
	                mg.setMGcompany(rs.getString("MG_COMPANY"));
	            }

	        } catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	        }
		return mg;
    }
    
    //사용자그룹 설정을 수정한다.
    public void updateMGset(String MGseq, String MGmenu, String MGxp, String MGsite, String MGcompany)
    throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
        PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

        try {
        	conn  = new DBconn();
        	conn.getDBCPConnection();
			stmt = conn.createStatement();

			sb.append("UPDATE MEMBER_GROUP SET MG_MENU='"+MGmenu+"',MG_XP='"+MGxp+"', MG_SITE='"+MGsite+"', MG_COMPANY='"+MGcompany+"' WHERE MG_SEQ='"+MGseq+"' \n");

			//System.out.print( sb.toString() );
			pstmt = conn.createPStatement( sb.toString() );
            pstmt.executeUpdate();
            
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
    }
}
