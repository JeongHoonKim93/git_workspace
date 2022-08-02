package risk.admin.site_type;

import java.sql.*;
import java.util.*;
import java.lang.String;

import risk.admin.site.SiteBean;
import risk.admin.site.SitegroupBean;
import risk.util.DateUtil;
import risk.util.Log;
import risk.DBconn.DBconn;

public class SiteTypeMng {
	String query;
	
	StringBuffer sb = null;
	
	DateUtil du = new DateUtil();
	//String StrSiteExclude = " AND NOT (S_CODE1=1 AND S_CODE2=6) ";
	
	String StrSiteExclude = "";
	
	
	public List getSgRelation(String key, int sg_seq){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    ResultSet rs = null;	
	    List list = null;
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb = new StringBuffer();
		    sb.append("SELECT S_SEQ																	\n");
		    sb.append("     , S_NAME																\n");
		    sb.append("  FROM SG_S_RELATION A														\n");
		    sb.append(" WHERE 1=1																	\n");
		    //sb.append("   AND NOT EXISTS (SELECT 1 FROM SG_S_RELATION_TYPE WHERE S_SEQ = A.S_SEQ)	\n");
		    
		    if(sg_seq > 0){
		    	sb.append("  AND SG_SEQ = "+sg_seq+"												\n");
		    }
		    if(!key.equals("")){
		    	sb.append("  AND S_NAME LIKE '%"+key+"%'											\n");
		    }
		    
		    sb.append(" ORDER BY S_NAME																\n");
		    
		    
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    list = new java.util.ArrayList();
		    
		    while( rs.next() ) {
		    	SiteBean site = new SiteBean();
		    	site.set_gsn(rs.getInt("S_SEQ"));
		    	site.set_name(rs.getString("S_NAME"));
		    	list.add(site);
		    }
	    } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
	    
		return list;
	}
	
	public List getSgRelationType(String key, int sgt_seq){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    ResultSet rs = null;	
	    List list = null;
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb = new StringBuffer();
		    sb.append("SELECT DISTINCT S_SEQ, S_NAME 			\n");
		    sb.append("  FROM SG_S_RELATION_TYPE A		\n");
		    sb.append(" WHERE 1=1						\n");
		    
		    
		    
		    if(sgt_seq > 0){
		    	sb.append("  AND SGT_SEQ = "+sgt_seq+"												\n");
		    }
		    if(!key.equals("")){
		    	sb.append("  AND S_NAME LIKE '%"+key+"%'											\n");
		    }
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    list = new java.util.ArrayList();
		    
		    while( rs.next() ) {
		    	SiteBean site = new SiteBean();
		    	site.set_gsn(rs.getInt("S_SEQ"));
		    	site.set_name(rs.getString("S_NAME"));
		    	list.add(site);
		    }
	    } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
	    
		return list;
	}
	
	public int insertSgRelationType(String sgt_seq, String s_seq){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    
	    int result = 0;
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb = new StringBuffer();
		    sb.append("INSERT INTO SG_S_RELATION_TYPE (																											\n");
		    sb.append("                                SELECT "+sgt_seq+"																						\n");
		    sb.append("                                     , A.S_SEQ																							\n");
		    sb.append("                                     , A.S_NAME																							\n");
		    sb.append("                                  FROM SG_S_RELATION  A																					\n");
		    sb.append("                                 WHERE A.S_SEQ IN ( "+s_seq+" )				\n");
		    sb.append("                                   AND NOT EXISTS (																						\n");
		    sb.append("                                                   SELECT 1																				\n");
		    sb.append("                                                     FROM SG_S_RELATION_TYPE																\n");
		    sb.append("                                                    WHERE S_SEQ IN ("+s_seq+")	\n");
		    sb.append("                                                      AND SGT_SEQ = "+sgt_seq+" 															\n");
		    sb.append("                                                      AND S_SEQ = A.S_SEQ																\n");
		    sb.append("                                                   )																						\n");
		    sb.append("                                )																										\n");
		    
		    
		    System.out.println(sb.toString());
		    result = stmt.executeUpdate(sb.toString());
		    
	    } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
	    
		return result;
	}
	
	public int deleteSgRelationType(String sgt_seq, String s_seq){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    
	    int result = 0;
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb = new StringBuffer();
		    
		    sb.append(" DELETE FROM SG_S_RELATION_TYPE WHERE S_SEQ IN ("+s_seq+")\n");
		    
		    if(!sgt_seq.equals("0") && !sgt_seq.equals("")){
		    	sb.append("  AND SGT_SEQ = "+sgt_seq+" \n");
		    }
		    
		    System.out.println(sb.toString());
		    result = stmt.executeUpdate(sb.toString());
		    
	    } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
	    
		return result;
	}
	
	public ArrayList getSiteGroup(){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    ResultSet rs = null;	
	    ArrayList result = new ArrayList();
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb = new StringBuffer();
		    sb.append("SELECT SG_SEQ, SG_NAME FROM SITE_GROUP\n");
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    SitegroupBean sitegroup = null;
		    while( rs.next() ) {
		    	sitegroup = new SitegroupBean();
		    	sitegroup.set_seq(rs.getInt("SG_SEQ"));
		    	sitegroup.set_name(rs.getString("SG_NAME"));
		    	result.add(sitegroup);
		    }
	    } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
	    
		return result;
	}
	
	
	public ArrayList getSiteGroupType(){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    ResultSet rs = null;	
	    ArrayList result = new ArrayList();
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb = new StringBuffer();
		    sb.append("SELECT SGT_SEQ			\n");
		    sb.append("     , SGT_NAME 			\n");
		    sb.append("  FROM SITE_GROUP_TYPE 	\n");
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    SitegroupBean sitegroup = null;
		    while( rs.next() ) {
		    	sitegroup = new SitegroupBean();
		    	sitegroup.set_seq(rs.getInt("SGT_SEQ"));
		    	sitegroup.set_name(rs.getString("SGT_NAME"));
		    	result.add(sitegroup);
		    }
	    } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
	    
		return result;
	}
	
	public String getSiteGroupType(String seq){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    ResultSet rs = null;	
	    String result = "";
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb = new StringBuffer();
		    sb.append("SELECT SGT_NAME 			\n");
		    sb.append("  FROM SITE_GROUP_TYPE 	\n");
		    sb.append(" WHERE SGT_SEQ = "+seq+" 	\n");
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    if( rs.next() ) {
		    	result = rs.getString("SGT_NAME");
		    }
	    } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
	    
		return result;
	}
	
	public int insertSiteGroupType(String name){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null;
	    
	    ResultSet rs = null;	
	    int result = 0;
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb = new StringBuffer();
		    
		    sb.append("INSERT INTO SITE_GROUP_TYPE ( SGT_NAME 		\n");
		    sb.append("                            , SGT_REGDATE	\n");
		    sb.append("                            ) VALUES 		\n");
		    sb.append("                            ( '"+name+"'		\n");
		    sb.append("                            , NOW()			\n");
		    sb.append("                            )				\n");
		    
		    
		    System.out.println(sb.toString());
		    result = stmt.executeUpdate(sb.toString());
		    
	    } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
	    
		return result;
	}
	
	public int updateSiteGroupType(int seq, String name){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null;
	    
	    ResultSet rs = null;	
	    int result = 0;
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb = new StringBuffer();
		    sb.append("UPDATE SITE_GROUP_TYPE 		\n");
		    sb.append("   SET SGT_NAME = '"+name+"'	\n");
		    sb.append("     , SGT_REGDATE = NOW()	\n");
		    sb.append(" WHERE SGT_SEQ = "+seq+"		\n");
		    
		    System.out.println(sb.toString());
		    result = stmt.executeUpdate(sb.toString());
		    
	    } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
	    
		return result;
	}
	
	public int deleteSiteGroupType(int seq){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null;
	    
	    ResultSet rs = null;	
	    int result = 0;
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb = new StringBuffer();
		    sb.append(" DELETE FROM SITE_GROUP_TYPE WHERE SGT_SEQ = "+seq+"	\n");
		    System.out.println(sb.toString());
		    result = stmt.executeUpdate(sb.toString());
		    
		    sb = new StringBuffer();
		    sb.append(" DELETE FROM SG_S_RELATION_TYPE WHERE SGT_SEQ = "+seq+"	\n");
		    System.out.println(sb.toString());
		    result = stmt.executeUpdate(sb.toString());
		    
		    
	    } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        
        return result;
	}
	
	public String getSiteHtml() {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs = null;
	    StringBuffer sb = null;//new StringBuffer();
	    StringBuffer sbHtml = null;
	    try {
	    	sb = new StringBuffer();
			dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb.append(" SELECT A.SG_SEQ, A.SG_NAME, B.S_NAME, B.S_SEQ, C.s_url, C.l_seq  \n");
		    sb.append(" FROM SITE_GROUP A, SG_S_RELATION B, SITE C   \n");
		    sb.append(" WHERE A.SG_SEQ=B.SG_SEQ AND B.s_seq = C.s_seq \n");		   
		    sb.append("   AND NOT EXISTS (SELECT 1 FROM SG_S_RELATION_TYPE WHERE S_SEQ = B.S_SEQ)\n");
		    sb.append(" ORDER BY A.SG_SEQ ASC  \n");
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    sbHtml = new StringBuffer();
            
            sbHtml.append("<table border='1'>		\n");
            sbHtml.append("	<tr>		\n");
            sbHtml.append("		<td align='center' bgcolor='CCCCCC'><strong>사이트 그룹</strong></td>		\n");
            sbHtml.append("		<td align='center' bgcolor='CCCCCC'><strong>사이트</strong></td>		\n");
            sbHtml.append("		<td align='center' bgcolor='CCCCCC'><strong>언어코드</strong></td>		\n");
            sbHtml.append("		<td align='center' bgcolor='CCCCCC'><strong>URL</strong></td>		\n");
            sbHtml.append("	</tr>		\n");
            
            int tempSgseq = 0;
            
			while( rs.next() ) {
				sbHtml.append("<tr>		\n");
				if( tempSgseq != rs.getInt("SG_SEQ") ) {
					sbHtml.append("	<td align='center'>"+rs.getString("SG_NAME")+"</td>		\n");
				}else {
					sbHtml.append("	<td align='center'>&nbsp;</td>		\n");
				}
				tempSgseq = rs.getInt("SG_SEQ");
    			sbHtml.append("	<td align='center'>"+rs.getString("S_NAME")+"</td>		\n");
    			sbHtml.append("	<td align='center'>"+rs.getString("l_seq")+"</td>		\n");
    			sbHtml.append("	<td align='left'>"+rs.getString("s_url")+"</td>		\n");
    			sbHtml.append("</tr>		\n");
			}
			 sbHtml.append("</table>		\n");
	    } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
            
        }
	    return sbHtml.toString();
	}
	
	public String getSiteTypeHtml() {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs = null;
	    StringBuffer sb = null;//new StringBuffer();
	    StringBuffer sbHtml = null;
	    try {
	    	sb = new StringBuffer();
			dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb.append(" SELECT A.SGT_SEQ, A.SGT_NAME, B.S_NAME, B.S_SEQ, C.s_url, C.l_seq  \n");
		    sb.append(" FROM SITE_GROUP_TYPE A, SG_S_RELATION_TYPE B, SITE C   \n");
		    sb.append(" WHERE A.SGT_SEQ=B.SGT_SEQ AND B.s_seq = C.s_seq \n");		   
		    sb.append(" ORDER BY A.SGT_SEQ ASC  \n");
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    sbHtml = new StringBuffer();
            
            sbHtml.append("<table border='1'>		\n");
            sbHtml.append("	<tr>		\n");
            sbHtml.append("		<td align='center' bgcolor='CCCCCC'><strong>사이트 그룹</strong></td>		\n");
            sbHtml.append("		<td align='center' bgcolor='CCCCCC'><strong>사이트</strong></td>		\n");
            sbHtml.append("		<td align='center' bgcolor='CCCCCC'><strong>언어코드</strong></td>		\n");
            sbHtml.append("		<td align='center' bgcolor='CCCCCC'><strong>URL</strong></td>		\n");
            sbHtml.append("	</tr>		\n");
            
            int tempSgseq = 0;
            
			while( rs.next() ) {
				sbHtml.append("<tr>		\n");
				if( tempSgseq != rs.getInt("SGT_SEQ") ) {
					sbHtml.append("	<td align='center'>"+rs.getString("SGT_NAME")+"</td>		\n");
				}else {
					sbHtml.append("	<td align='center'>&nbsp;</td>		\n");
				}
				tempSgseq = rs.getInt("SGT_SEQ");
    			sbHtml.append("	<td align='center'>"+rs.getString("S_NAME")+"</td>		\n");
    			sbHtml.append("	<td align='center'>"+rs.getString("l_seq")+"</td>		\n");
    			sbHtml.append("	<td align='left'>"+rs.getString("s_url")+"</td>		\n");
    			sbHtml.append("</tr>		\n");
			}
			 sbHtml.append("</table>		\n");
	    } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
            
        }
	    return sbHtml.toString();
	}
	
}
