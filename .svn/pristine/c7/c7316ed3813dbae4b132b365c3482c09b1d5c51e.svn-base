package risk.admin.portal_keyword;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.admin.aekeyword.ExceptionKeywordBean;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class PortalKeywordMgr {
	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	
	public PortalKeywordBean[] getKeywordList(String SearchKey, String Oredered )
	{
		DBconn  conn  = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        PortalKeywordBean[] arrPortalKeyword= null;
        PortalKeywordBean PortalKeyword = null;
        int rowCnt = 0;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	
       	 	sb = new StringBuffer();
       	 	
       		sb.append(" SELECT PK_SEQ, PK_VALUE, DATE_FORMAT(PK_REGDATE,'%Y.%m.%d') PK_REGDATE, IFNULL(DATE_FORMAT(PK_UPDATE,'%Y.%m.%d'),'없음') PK_UPDATE, 	\n");
    	 	sb.append(" 	   PK_WRITER,  IFNULL(PK_UPDATER,'없음') PK_UPDATER 																				\n");
			sb.append(" 	FROM PORTAL_KEYWORD \n");
			
       	 	if( SearchKey.length() > 0 )
       	 	sb.append(" WHERE PK_VALUE LIKE '%"+SearchKey+"%' \n");
       	 	if( !Oredered.equals("") )
			sb.append(" ORDER BY "+Oredered+" 			  \n");
       	 	
       	 	Log.debug(sb.toString());
       	 	
       	 	pstmt = conn.createPStatement( sb.toString() );
       	 	rs = pstmt.executeQuery();
       	 	
       	 	rs.last();
       	 	if(rs.getRow()>0) arrPortalKeyword = new PortalKeywordBean[rs.getRow()];
       	 	rs.beforeFirst();
            
            while (rs.next()) {
            	PortalKeyword = new PortalKeywordBean();
            	PortalKeyword.setPkSeq(rs.getString("PK_SEQ"));
            	PortalKeyword.setPkValue(rs.getString("PK_VALUE"));
            	PortalKeyword.setPkWriter(rs.getString("PK_WRITER"));
            	PortalKeyword.setPkUpdater(rs.getString("PK_UPDATER"));
            	PortalKeyword.setPkDate(rs.getString("PK_REGDATE"));
            	PortalKeyword.setPkUpdate(rs.getString("PK_UPDATE"));
            	
            	arrPortalKeyword[rowCnt] =	PortalKeyword;    		
            	rowCnt++;
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
        return arrPortalKeyword;
    }
	
	public String[] getPortalKeyword(String pkSeq)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        String[] result = null;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	
       	 	sb = new StringBuffer();
       	 	
       		sb.append(" SELECT PK_VALUE																	\n");
       		sb.append("			, IFNULL(PK_UPDATER, PK_WRITER) PK_WRITER								\n");
       		sb.append("			, DATE_FORMAT(IFNULL(PK_UPDATE, PK_REGDATE), '%Y-%m-%d') PK_DATE 		\n");
			sb.append(" 	FROM PORTAL_KEYWORD 														\n");
			sb.append(" 	WHERE PK_SEQ = "+ pkSeq +"													\n");
			
       	 	Log.debug(sb.toString());
       	 	pstmt = conn.createPStatement( sb.toString() );
       	 	rs = pstmt.executeQuery();   
            
            if (rs.next()) {
                result = new String[3];
            	result[0] = rs.getString("PK_VALUE");
            	result[1] = rs.getString("PK_WRITER");
            	result[2] = rs.getString("PK_DATE");
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
        return result;
    }
	
	public boolean insertKeyword( String pkValue, String mName )
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        boolean rebln = false;
        
        int chkcount = 0;
        int chkPosition = 0;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();
       	 	
       	 	sb = new StringBuffer();
       	 	sb.append("SELECT COUNT(1) FROM PORTAL_KEYWORD WHERE PK_VALUE='"+pkValue+"' \n");
            
            rs = stmt.executeQuery(sb.toString());

            if (rs.next()) {
            	chkcount = rs.getInt(1);
            }
            
           
            if( chkcount > 0 ) {
            	rebln = false;
            }else {            	
            	
	       	 	sb = new StringBuffer();	        
				sb.append(" INSERT INTO PORTAL_KEYWORD (PK_VALUE, PK_WRITER, PK_REGDATE) \n");
			 	sb.append(" VALUES ('"+pkValue+"', '"+mName+"', '"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"') \n");
	   	 	
			 	//Log.debug(sb.toString());
			 	stmt.executeUpdate(sb.toString());
			 	rebln = true;
            }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return rebln;
	
	}
	
	public boolean updateKeyword( String pkSeq, String pkValue, String mName)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        boolean rebln = false;
        
        int chkcount = 0;
        String chkWord = null;
        String chkWeight = null;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();       	 	
       	 	
       	 	sb = new StringBuffer();
    	 	sb.append("SELECT COUNT(1) FROM PORTAL_KEYWORD WHERE PK_VALUE='"+pkValue+"' \n");
	         
	        rs = stmt.executeQuery(sb.toString());
	
	        if (rs.next()) {
            	chkcount = rs.getInt(1);
            }      
	         
	        if( chkcount > 0 ) {
            	rebln = false;
            }else {
            	
	        	sb = new StringBuffer();
	            
				sb.append(" UPDATE PORTAL_KEYWORD												\n");
				sb.append(" SET PK_VALUE='"+pkValue+"', 										\n");
				sb.append(" 	PK_UPDATE= '"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"', 		\n");		
				sb.append(" 	PK_UPDATER='"+mName+"' 											\n");			
			 	sb.append(" WHERE PK_SEQ="+pkSeq+" 												\n");
	   	 	
			 	Log.debug(sb.toString());
			 	stmt.executeUpdate(sb.toString());
			 	rebln = true;
	        }
            
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
			return false;
        } catch(Exception ex) {
			Log.writeExpt(ex);
			return false;
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return rebln;
	
	}
	
	public boolean delKeyword(String delList )
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        String[] arrList = null;
        boolean rebln = false;
        
        try {       	      	
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();	      
        	
        	arrList = delList.split(",");
        	
        	if(delList.length()>1000)
        	{
        		rebln = false;
        	}else{
	        	sb = new StringBuffer();
	            
	        	sb.append("DELETE FROM PORTAL_KEYWORD		\n");
			 	sb.append("WHERE PK_SEQ IN ("+delList+")    		\n");
			 	System.out.println(sb.toString());
			 	Log.debug(sb.toString());
			 	stmt.executeUpdate(sb.toString());
			 	rebln = true;
        	}
       	 	
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return rebln;
	
	}

}
