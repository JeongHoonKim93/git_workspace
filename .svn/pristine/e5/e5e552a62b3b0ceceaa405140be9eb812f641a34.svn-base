package risk.admin.tier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class TierSiteMgr {
	private TierSiteBean tsBean = null;
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs    = null;	
	private StringBuffer sb = null;	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	private int startNum;
	private int endNum;
	private int totalCnt;
	
	
	
    public ArrayList getTs_type()    
    {  	    	
    	ArrayList resultList = new ArrayList();
    	    	
    	try{ 
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		stmt = dbconn.createStatement();    		
    		sb = new StringBuffer();
    		sb.append(" SELECT TS_TYPE FROM TIER_GROUP	\n");
    		
			rs = stmt.executeQuery(sb.toString());					
			System.out.println(sb.toString());
			while(rs.next()){
				resultList.add(rs.getInt("TS_TYPE"));
			}		
			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    return resultList;
	 }
    
    public String InsertTierGroup(String tier, String m_seq){  	    	
    	String result = "";
    	
    	try{ 
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		stmt = dbconn.createStatement();
    		sb = new StringBuffer();
    		sb.append(" SELECT COUNT(0) AS CNT FROM TIER_GROUP WHERE TS_TYPE = "+tier+"	\n");
    		rs = stmt.executeQuery(sb.toString());	
    		if(rs.next()){
    			if(rs.getInt("CNT") > 0){
        			result = "중복된 TIER 입니다.";
        		} else {
        			sb = null;
        			rs = null;
        			sb = new StringBuffer();
            		sb.append(" INSERT INTO TIER_GROUP (TS_TYPE, TS_REGDATE, M_SEQ) VALUES ("+tier+", NOW(), "+m_seq+")		\n");
            		System.out.println(sb.toString());
            		stmt.executeUpdate(sb.toString());
        		}
    		}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			ex.printStackTrace();
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    return result;
	 }
    
    public ArrayList getTs_type_data()    
    {  	    	
    	ArrayList resultList = new ArrayList();
    	    	
    	try{ 
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();  	
    		stmt = dbconn.createStatement();  	
    		sb = new StringBuffer();
    		sb.append(" SELECT DISTINCT TS_TYPE FROM TIER_SITE	ORDER BY TS_TYPE ASC \n");
			rs = stmt.executeQuery(sb.toString());					
			System.out.println(sb.toString());
			while(rs.next()){
				resultList.add(rs.getInt("TS_TYPE"));
			}		
			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    return resultList;
	 }
    
    public ArrayList getTierList(String searchKey, int ts_type)    
    {  	    	
    	ArrayList resultList = new ArrayList();
    	
    	try{ 
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		stmt = dbconn.createStatement();    		
    		sb = new StringBuffer();
    		sb.append(" SELECT TS_SEQ, TS_NAME, TS_TYPE, TS_RANK FROM TIER_SITE \n");
    		sb.append(" WHERE 1=1 \n");
    		if(ts_type != 0){
    			sb.append("	  AND TS_TYPE = "+ts_type+"\n");
    		}
    		if(!searchKey.equals("")){
    			sb.append("AND TS_NAME LIKE '%"+searchKey+"%' \n");
    		}
    		sb.append(" ORDER BY TS_RANK	\n");
    		
    		
    		
			rs = stmt.executeQuery(sb.toString());					
			System.out.println(sb.toString());
			
			while(rs.next()){
				tsBean = new TierSiteBean();
				tsBean.setTs_seq(rs.getString("TS_SEQ"));
				tsBean.setTs_name(rs.getString("TS_NAME"));
				tsBean.setTs_type(rs.getString("TS_TYPE"));
				tsBean.setTs_rank(rs.getString("TS_RANK"));
				resultList.add(tsBean);
			}		
			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    return resultList;
	 }
    
    
    public ArrayList RightMove(String s_seqs, String ts_type){  	    	
    	ArrayList resultList = new ArrayList();
    	
    	try{ 
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		stmt = dbconn.createStatement();
    		
    		sb = new StringBuffer();
    		sb.append("INSERT INTO TIER_SITE (SELECT S_SEQ, S_NAME, "+ts_type+", S_SEQ FROM SG_S_RELATION WHERE S_SEQ IN ("+s_seqs+"))\n");
    		
    		System.out.println(sb.toString());
    		stmt.executeUpdate(sb.toString());					
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    return resultList;
	 }
    
    public ArrayList LeftMove(String s_seqs){  	    	
    	ArrayList resultList = new ArrayList();
    	
    	try{ 
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		stmt = dbconn.createStatement();
    		
    		sb = new StringBuffer();
    		sb.append("DELETE FROM TIER_SITE WHERE TS_SEQ IN ("+s_seqs+");\n");
    		
    		System.out.println(sb.toString());
    		stmt.executeUpdate(sb.toString());					
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    return resultList;
	 }
    
    public ArrayList ChangeOrder(String ts_seq1, String ts_seq2){  	    	
    	ArrayList resultList = new ArrayList();
    	
    	try{ 
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		stmt = dbconn.createStatement();
    		
    		sb = new StringBuffer();
    		//sb.append("DELETE FROM TIER_SITE WHERE TS_SEQ IN ("+s_seqs+");\n");
    		
    		sb.append("SELECT (SELECT TS_RANK FROM TIER_SITE WHERE TS_SEQ = "+ts_seq1+" ) AS TS_RANK1 \n"); 
    		sb.append("     , (SELECT TS_RANK FROM TIER_SITE WHERE TS_SEQ = "+ts_seq2+" ) AS TS_RANK2 \n");
    		
    		String ts_rank1 = "";
    		String ts_rank2 = "";

    		System.out.println(sb.toString());
    		rs = stmt.executeQuery(sb.toString());
    		
    		while(rs.next()){
    			ts_rank1 = rs.getString("TS_RANK1");
    			ts_rank2 = rs.getString("TS_RANK2");
    		}
    		
    		sb = new StringBuffer();
    		sb.append(" UPDATE TIER_SITE SET TS_RANK = "+ts_rank2+" WHERE TS_SEQ = "+ts_seq1+"	\n");
    		stmt.executeUpdate(sb.toString());
    		
    		sb = new StringBuffer();
    		sb.append(" UPDATE TIER_SITE SET TS_RANK = "+ts_rank1+" WHERE TS_SEQ = "+ts_seq2+"	\n");
    		stmt.executeUpdate(sb.toString());
    		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    return resultList;
	 }
    
    public String getSolrTierS_seq(String tiers){  	    	
    	String result = "";
    	
    	try{ 
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		stmt = dbconn.createStatement();
    		
    		sb = new StringBuffer();
    		sb.append("SELECT GROUP_CONCAT(TS_SEQ ORDER BY TS_SEQ SEPARATOR ' OR ') AS TS_SEQS FROM TIER_SITE WHERE TS_TYPE IN ("+tiers+")\n");

    		System.out.println(sb.toString());
    		rs = stmt.executeQuery(sb.toString());
    		
    		if(rs.next()){
    			result = rs.getString("TS_SEQS");  
    		}
    		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    return result;
	}
    
    /**
     * 매체순위 삭제
     * @param tiers (TS_TYPE)
     * @return
     */
    public String deleteTierGroup(String tier){
    	String result = "";
    	
    	try{ 
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		stmt = dbconn.createStatement();
    		
    		sb = new StringBuffer();
    		sb.append("DELETE FROM TIER_GROUP WHERE TS_TYPE = "+tier+"\n");
    		System.out.println(sb.toString());
    		
    		if(stmt.executeUpdate(sb.toString()) > 0){
    			sb = null;
    			sb = new StringBuffer();
        		sb.append("DELETE FROM TIER_SITE WHERE TS_TYPE = "+tier+"\n");
        		System.out.println(sb.toString());
        		stmt.executeUpdate(sb.toString());
        		result = "삭제 완료되었습니다.";
    		}
    		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    return result;
	}
    
    public ArrayList getTierGroupList(){  	    	
    	ArrayList rList = new ArrayList();
    	
    	try{ 
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		stmt = dbconn.createStatement();
    		
    		sb = new StringBuffer();
    		sb.append("	SELECT TS_TYPE, TS_DESCRIPTION 			\n");
    		sb.append("	FROM TIER_GROUP							\n");

    		System.out.println(sb.toString());
    		rs = stmt.executeQuery(sb.toString());
    		
    		while(rs.next()){
    			Map elem = new HashMap();
    			elem.put("TS_TYPE", rs.getString("TS_TYPE"));
    			elem.put("TS_DESCRIPTION", rs.getString("TS_DESCRIPTION"));
    			rList.add(elem);
    		}
    		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    return rList;
	}
}
