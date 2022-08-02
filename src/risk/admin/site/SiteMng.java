package risk.admin.site;

import java.sql.*;
import java.util.*;
import java.lang.String;

import risk.admin.member.MemberBean;
import risk.admin.membergroup.membergroupBean;
import risk.util.DateUtil;
import risk.util.Log;
import risk.DBconn.DBconn;

public class SiteMng {
	String query;
	
	StringBuffer sb = null;
	
	DateUtil du = new DateUtil();
	//String StrSiteExclude = " AND NOT (S_CODE1=1 AND S_CODE2=6) ";
	
	String StrSiteExclude = "";
	
	
	
	
	// 김태완 임시
	/*
	public List get46List( String key, int sg_seq , int l_seq) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;
		String search_key = null;
		
        search_key = key;
        StringBuffer sql = new StringBuffer();

		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    sql.append(" SELECT A.S_SEQ, A.S_NAME, A.l_seq FROM SITE A WHERE NOT EXISTS (SELECT 1 FROM SG_S_RELATION WHERE S_SEQ=A.S_SEQ)  ");
	    if(!search_key.equals("")){
	    	sql.append(" AND LOWER(S_NAME) LIKE '%"+search_key.toLowerCase()+"%' " );
	    }
	    if(sg_seq != 0){
	    	sql.append(" AND S_CODE1="+sg_seq );
	    }
	    
	    if(l_seq > 0 && l_seq != 99){
	    	sql.append(" AND A.l_seq="+l_seq );
	    }else if (l_seq == 99){
	    	sql.append(" AND A.l_seq > 2");
	    }
	    sql.append(" ORDER BY S_NAME ASC");
	    		
		rs = stmt.executeQuery(sql.toString());	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SiteBean site = new SiteBean();
	    	site.set_gsn(rs.getInt("S_SEQ"));
	    	site.set_name(rs.getString("S_NAME"));
	    	site.setL_seq(rs.getInt("l_seq"));
	    	list.add(site);
	    }
		dbconn.close();
		rs.close();
		stmt.close();
	    return list;
	}
	*/
	
	
	
	

	public List get46List(String key, int sg_seq, String language, String order, String type){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    ResultSet rs = null;	
	    List list = null;
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    System.out.println(key);
		    
		    sb = new StringBuffer();
		   
		    sb.append("SELECT A.S_SEQ   \n");
		    sb.append("    , IF(DATE_FORMAT(FROM_UNIXTIME(S_REG_TIMESTAMP),'%Y%m%d') >= DATE_FORMAT(DATE_ADD(NOW(), INTERVAL -7 DAY),'%Y%m%d'), CONCAT(A.S_NAME,'(최신)'), A.S_NAME) AS S_NAME	\n");
		    sb.append("    , S_ACTIVE	\n");
		    sb.append("     , FROM_UNIXTIME(A.S_REG_TIMESTAMP, '%Y-%m-%d') AS S_REG_DATE 										\n");
		    sb.append(" FROM SITE A    \n");
		    if(type.equals("sg")){
		    	sb.append("WHERE NOT EXISTS (SELECT 1 FROM SG_S_RELATION WHERE S_SEQ=A.S_SEQ)    \n");
		    }else{
		    	sb.append("WHERE A.S_SEQ = A.S_SEQ		 \n");
		    }
		    sb.append("AND S_ACTIVE = 1    \n");	
		    if(!key.equals("")){
		    	sb.append("AND LOWER(S_NAME) LIKE '%"+key.toLowerCase()+"%'    \n");	
		    }
		    if(sg_seq > 0){
		    	sb.append("AND S_CODE1= "+sg_seq+"   \n");	
		    }
		    if(!"".equals(language)){
		    	sb.append("AND S_LANG = '"+language+"'   \n");	
		    }
		    if(order.equals("kor")){
		    	sb.append("ORDER BY A.S_NAME ASC    \n");
		    }else{
		    	sb.append("ORDER BY A.S_REG_TIMESTAMP DESC    \n");
		    }
		    
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    list = new java.util.ArrayList();
		    
		    while( rs.next() ) {
		    	SiteBean site = new SiteBean();
		    	site.set_gsn(rs.getInt("S_SEQ"));
		    	site.set_name(rs.getString("S_NAME"));
		    	site.setS_active(rs.getString("S_ACTIVE"));
		    	site.setS_reg_date(rs.getNString("S_REG_DATE"));
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

	
	

	public ArrayList<SiteBean> getSiteList(String key, int sg_seq, String language, String order, String type){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    ResultSet rs = null;	
	    ArrayList<SiteBean> list = new ArrayList<SiteBean>();
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    System.out.println(key);
		    
		    sb = new StringBuffer();
		   
		    sb.append("SELECT A.S_SEQ   \n");
		    sb.append("    , IF(DATE_FORMAT(FROM_UNIXTIME(S_REG_TIMESTAMP),'%Y%m%d') >= DATE_FORMAT(DATE_ADD(NOW(), INTERVAL -7 DAY),'%Y%m%d'), CONCAT(A.S_NAME,'(최신)'), A.S_NAME) AS S_NAME	\n");
		    sb.append("    , S_ACTIVE	\n");
		    sb.append("     , FROM_UNIXTIME(A.S_REG_TIMESTAMP, '%Y-%m-%d') AS S_REG_DATE 										\n");
		    sb.append(" FROM SITE A    \n");
		    if(type.equals("sg")){
		    	sb.append("WHERE NOT EXISTS (SELECT 1 FROM SG_S_RELATION WHERE S_SEQ=A.S_SEQ)    \n");
		    }else{
		    	sb.append("WHERE A.S_SEQ = A.S_SEQ		 \n");
		    }
		    sb.append("AND S_ACTIVE = 1    \n");	
		    if(!key.equals("")){
		    	sb.append("AND LOWER(S_NAME) LIKE '%"+key.toLowerCase()+"%'    \n");	
		    }
		    if(sg_seq > 0){
		    	sb.append("AND S_CODE1= "+sg_seq+"   \n");	
		    }
		    if(!"".equals(language)){
		    	sb.append("AND S_LANG = '"+language+"'   \n");	
		    }
		    if(order.equals("kor")){
		    	sb.append("ORDER BY A.S_NAME ASC    \n");
		    }else{
		    	sb.append("ORDER BY A.S_REG_TIMESTAMP DESC    \n");
		    }
		    
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    SiteBean site = null;
		    while( rs.next() ) {
		    	site = new SiteBean();
		    	site.set_gsn(rs.getInt("S_SEQ"));
		    	site.set_name(rs.getString("S_NAME"));
		    	site.setS_active(rs.getString("S_ACTIVE"));
		    	site.setS_reg_date(rs.getString("S_REG_DATE"));
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

	
	//SITE A 테이블에서 SG_S_RELATION 테이블을 뺀 리스트에서 키워드에 의해 검색
	/*
	public List get46List( String key, int sg_seq ) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;
		String search_key = null;

//		search_key = new String( key.getBytes("8859_1"), "euc-kr" );
                search_key = key;

		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT A.S_SEQ, A.S_NAME FROM SITE A WHERE NOT EXISTS (SELECT 1 FROM SG_S_RELATION WHERE S_SEQ=A.S_SEQ)  AND LOWER(S_NAME) LIKE '%"+search_key.toLowerCase()+"%' AND S_CODE1="+sg_seq+" " +
	    		StrSiteExclude +
	    		" ORDER BY S_NAME ASC";
		System.out.println(query+"\n");
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SiteBean site = new SiteBean();
	    	site.set_gsn(rs.getInt("S_SEQ"));
	    	site.set_name(rs.getString("S_NAME"));
	    	list.add(site);
	    }
		dbconn.close();
		
//		2007.10.12
		rs.close();
		stmt.close();
		
	    return list;
	}
	*/

	//SITE A 테이블에서 SG_S_RELATION 테이블을 뺀 리스트에서 사이트그룹으로 검색
	/*
	public List get46List( int sg_seq ) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;		
		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT A.S_SEQ, A.S_NAME FROM SITE A WHERE NOT EXISTS (SELECT 1 FROM SG_S_RELATION WHERE S_SEQ=A.S_SEQ)   AND S_CODE1="+sg_seq+" " +
	    		StrSiteExclude +
	    		" ORDER BY S_SEQ, S_NAME ";
		System.out.println(query+"\n");
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SiteBean site = new SiteBean();
	    	site.set_gsn(rs.getInt("S_SEQ"));
	    	site.set_name(rs.getString("S_NAME"));
	    	list.add(site);
	    }
		dbconn.close();
		
//		2007.10.12
		rs.close();
		stmt.close();
		
		
	    return list;
	}	
	*/
	//SITE A 테이블에서 SG_S_RELATION 테이블을 뺀 리스트에서 키워드에 의해 검색
	/*
	public List get46List( String key ) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;
		String search_key = null;

//		search_key = new String( key.getBytes("8859_1"), "euc-kr" );
                search_key = key;

		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT A.S_SEQ, A.S_NAME FROM SITE A WHERE NOT EXISTS (SELECT 1 FROM SG_S_RELATION WHERE S_SEQ=A.S_SEQ) AND LOWER(S_NAME) LIKE '%"+search_key.toLowerCase()+"%' " +
	    		StrSiteExclude +
	    		" ORDER BY S_NAME ";
		System.out.println(query+"\n");
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SiteBean site = new SiteBean();
	    	site.set_gsn(rs.getInt("S_SEQ"));
	    	site.set_name(rs.getString("S_NAME"));
	    	list.add(site);
	    }
		dbconn.close();
		
//		2007.10.12
		rs.close();
		stmt.close();
		
		
	    return list;
	}
	*/
	
	
	
	
	public List getList(String key, int sg_seq, String language){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    ResultSet rs = null;	
	    List list = null;
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    System.out.println(key);
		    
		    sb = new StringBuffer();
		    
		    
		    sb.append("SELECT A.S_SEQ										\n");
		    sb.append("     , A.S_NAME 										\n");
		    sb.append("     , B.S_ACTIVE 										\n");
		    sb.append("     , FROM_UNIXTIME(B.S_REG_TIMESTAMP, '%Y-%m-%d') AS S_REG_DATE 										\n");
		    sb.append("  FROM SG_S_RELATION A								\n");
		    sb.append("     , SITE B										\n");
		    sb.append(" WHERE A.S_SEQ = B.S_SEQ 							\n");
		    if(!key.equals("")){
		    	sb.append("   AND LOWER(A.S_NAME) LIKE '%"+key.toLowerCase()+"%'	\n");
		    }
		    if(sg_seq > 0){
		    	sb.append("   AND A.SG_SEQ="+sg_seq+"							\n");
		    }
		    if(!"".equals(language)){
		    	sb.append("   AND B.S_LANG = '"+language+"'					\n");
		    }
		    sb.append(" ORDER BY S_NAME ASC									\n");
		    
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    list = new java.util.ArrayList();
		    
		    while( rs.next() ) {
		    	SiteBean site = new SiteBean();
		    	site.set_gsn(rs.getInt("S_SEQ"));
		    	site.set_name(rs.getString("S_NAME"));
		    	site.setS_active(rs.getString("S_ACTIVE"));
		    	site.setS_reg_date(rs.getString("S_REG_DATE"));
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
	
	
	public List getAllienceList(String key, int sga_seq, int language){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    ResultSet rs = null;	
	    List list = null;
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    System.out.println(key);
		    
		    sb = new StringBuffer();
		    
		    
		    sb.append("SELECT A.S_SEQ										\n");
		    sb.append("     , A.S_NAME 										\n");
		    sb.append("     , B.S_ACTIVE 										\n");
		    sb.append("  FROM SGA_S_RELATION A								\n");
		    sb.append("     , SITE B										\n");
		    sb.append(" WHERE A.S_SEQ = B.S_SEQ 							\n");
		    if(!key.equals("")){
		    	sb.append("   AND LOWER(A.S_NAME) LIKE '%"+key.toLowerCase()+"%'	\n");
		    }
		    if(sga_seq > 0){
		    	sb.append("   AND A.SGA_SEQ="+sga_seq+"							\n");
		    }
		    if(language > 0){
		    	sb.append("   AND B.L_SEQ = "+language+"					\n");
		    }
		    sb.append(" ORDER BY S_NAME ASC									\n");
		    
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    list = new java.util.ArrayList();
		    
		    while( rs.next() ) {
		    	SiteBean site = new SiteBean();
		    	site.set_gsn(rs.getInt("S_SEQ"));
		    	site.set_name(rs.getString("S_NAME"));
		    	site.setS_active(rs.getString("S_ACTIVE"));
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
	
	
	public List getList_tier(String key, int sg_seq, int language){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    ResultSet rs = null;	
	    List list = null;
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    System.out.println(key);
		    
		    sb = new StringBuffer();
		    
		    
		    sb.append("SELECT A.S_SEQ														\n");
		    sb.append("     , A.S_NAME 														\n");
		    sb.append("  FROM SG_S_RELATION A												\n");
		    sb.append("     , SITE B														\n");
		    sb.append(" WHERE A.S_SEQ = B.S_SEQ 											\n");
		    sb.append("   AND NOT EXISTS (SELECT 1 FROM TIER_SITE WHERE TS_SEQ = A.S_SEQ)	\n");
		    if(!key.equals("")){
		    	sb.append("   AND LOWER(A.S_NAME) LIKE '%"+key.toLowerCase()+"%'	\n");
		    }
		    if(sg_seq > 0){
		    	sb.append("   AND A.SG_SEQ="+sg_seq+"							\n");
		    }
		    if(language > 0){
		    	sb.append("   AND B.L_SEQ = "+language+"					\n");
		    }
		    sb.append(" ORDER BY S_NAME ASC									\n");
		    
		    
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
	
	
	
	
	
	
	//SG_S_RELATION 테이블 리스트
	public List getList() throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;		
		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT S_SEQ,S_NAME FROM SG_S_RELATION ORDER BY S_NAME ";		
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SiteBean site = new SiteBean();
	    	site.set_gsn(rs.getInt("S_SEQ"));
	    	site.set_name(rs.getString("S_NAME"));
	    	list.add(site);
	    }
		dbconn.close();
		
//		2007.10.12
		rs.close();
		stmt.close();
		
		
	    return list;
	}

	//SG_S_RELATION 테이블 리스트 검색
	public List getList(String sch, int sg_seq) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;
	    
        dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT A.S_SEQ, A.S_NAME FROM SG_S_RELATION A WHERE LOWER(S_NAME) LIKE '%"+sch.toLowerCase()+"%' AND SG_SEQ="+sg_seq+" ORDER BY S_NAME ASC";
		
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SiteBean site = new SiteBean();
	    	site.set_gsn(rs.getInt("S_SEQ"));
	    	site.set_name(rs.getString("S_NAME"));
	    	list.add(site);
	    }
		dbconn.close();
		
//		2007.10.12
		rs.close();
		stmt.close();
		
	    return list;
	}

	
	//SG_S_RELATION 테이블 리스트 검색
	public List getList(String sch) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;

		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT A.S_SEQ, A.S_NAME FROM SG_S_RELATION A WHERE LOWER(S_NAME) LIKE '%"+sch.toLowerCase()+"%'ORDER BY S_NAME ASC";
		
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SiteBean site = new SiteBean();
	    	site.set_gsn(rs.getInt("S_SEQ"));
	    	site.set_name(rs.getString("S_NAME"));
	    	list.add(site);
	    }
		dbconn.close();
		
//		2007.10.12
		rs.close();
		stmt.close();
		
		
	    return list;
	}
	
	//SG_S_RELATION 테이블 리스트 검색
	public List getSelectList(String sdGsns) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;

		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT A.S_SEQ, A.S_NAME FROM SG_S_RELATION A WHERE S_SEQ IN ("+sdGsns+") ORDER BY S_NAME ASC";
		System.out.println(query);
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SiteBean site = new SiteBean();
	    	site.set_gsn(rs.getInt("S_SEQ"));
	    	site.set_name(rs.getString("S_NAME"));
	    	list.add(site);
	    }
		dbconn.close();
		
//		2007.10.12
		rs.close();
		stmt.close();
		
		
	    return list;
	}
	
	//SG_S_RELATION 테이블  그룹리스트
	public List getList(int sg_seq) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;		
		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT A.S_SEQ, A.S_NAME FROM SG_S_RELATION WHERE SG_SEQ="+sg_seq+" ORDER BY S_NAME ";
		
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

		while( rs.next() ) {
			SiteBean site = new SiteBean();
			site.set_gsn(rs.getInt("S_SEQ"));
			site.set_name(rs.getString("S_NAME"));
			list.add(site);
		}
		dbconn.close();
		
		//		2007.10.12
		rs.close();
		stmt.close();
		
	    return list;
	}
	
	//SITE_GROUP 그룹리스트
	public List getSGList() throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;		
		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    //query = " SELECT SG_SEQ, SG_NAME FROM SITE_GROUP ";
	    query = " SELECT SG_SEQ, SG_NAME FROM SITE_GROUP WHERE USEYN='Y' ORDER BY SG_ORDER ASC ";
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SitegroupBean sitegroup = new SitegroupBean();
	    	sitegroup.set_seq(rs.getInt("SG_SEQ"));
	    	sitegroup.set_name(rs.getString("SG_NAME"));
	    	//sitegroup.setDate(rs.getNString("SG_REG_DATE"));
	    	list.add(sitegroup);
	    }
		dbconn.close();
		
//		2007.10.12
		rs.close();
		stmt.close();
		
	    return list;
	}
	
	//SITE_GROUP_ALLIENCE 그룹리스트
	public List getSGAList() throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;		
		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    query = " SELECT SGA_SEQ, SGA_NAME FROM SITE_GROUP_ALLIENCE ";
	    rs = stmt.executeQuery(query);
	    
	    List list = new java.util.ArrayList();

	    while( rs.next() ) {
	    	SitegroupBean sitegroup = new SitegroupBean();
	    	sitegroup.set_seq(rs.getInt("SGA_SEQ"));
	    	sitegroup.set_name(rs.getString("SGA_NAME"));
	    	list.add(sitegroup);
	    }
		dbconn.close();
		
//		2007.10.12
		rs.close();
		stmt.close();
		
	    return list;
	}
	
	//사이트그룹 생성
	public void SGinsert(String sg_name, String type) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;
		String insert_name = null;
		int sg_order = 0;

//		insert_name = new String( sg_name.getBytes("8859_1"), "euc-kr" );
                insert_name = sg_name;
		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    
	    // SITE_GROUP테이블 마지막 SG_ORDER 구하기
	    if(type.equals("sg")){
	    	query = " SELECT SG_ORDER FROM SITE_GROUP ORDER BY SG_ORDER DESC LIMIT 1 ";
	    	
	    	rs = stmt.executeQuery(query);
	    	
	    	if( rs.next() ) {
	    		sg_order += rs.getInt("SG_ORDER")+1;
	    	}
	    	
	    	query = "";
	    	rs.close();
	    }
	    
	    //2007.10.12 수정자:이경환
	    //query = " INSERT INTO SITE_GROUP VALUES(SEQ_SG_SEQ.NEXTVAL, '"+sg_name+"') ";
	    
	    //type sg(SITE_GROUP) sga(SITE_GROUP_ALLIENCE)
	    if(type.equals("sg")){
	    	query = " INSERT INTO SITE_GROUP(SG_NAME,SG_REGDATE,SG_ORDER) VALUES('"+sg_name+"','"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"',"+sg_order+") ";
	    }else if(type.equals("sga")){
	    	query = " INSERT INTO SITE_GROUP_ALLIENCE(SGA_NAME,SGA_REGDATE) VALUES('"+sg_name+"','"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"') ";
	    }
	    
	    stmt.executeUpdate(query);
		dbconn.close();
		
		// 2007.10.12
		stmt.close();

	}
	
	//사이트그룹 변경
	public void SGupdate(int sg_seq, String sg_name, String type) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
	    ResultSet rs;

		String insert_name = null;
//		insert_name = new String( sg_name.getBytes("8859_1"), "euc-kr" );
        insert_name = sg_name;

		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    
	    //type sg(SITE_GROUP) sga(SITE_GROUP_ALLIENCE)
	    if(type.equals("sg")){
	    	query = " UPDATE SITE_GROUP SET SG_NAME='"+insert_name+"' WHERE SG_SEQ="+sg_seq;
	    }else if(type.equals("sga")){
	    	query = " UPDATE SITE_GROUP_ALLIENCE SET SGA_NAME='"+insert_name+"' WHERE SGA_SEQ="+sg_seq;
	    }
	    
	    stmt.executeUpdate(query);
		dbconn.close();
		
//		 2007.10.12
		stmt.close();
		
	}
	
	//사이트그룹 삭제
	public void SGdelete(int sg_seq, String type) throws SQLException, ClassNotFoundException, Exception {
	    DBconn  dbconn  = null;     //DB 연결을 위한 Object 선언
	    Statement stmt  = null; 
		dbconn = new DBconn();
	    dbconn.getDBCPConnection();
	    stmt = dbconn.createStatement();
	    
	    //type sg(SG_S_RELATION) sga(SGA_S_RELATION)
	    if(type.equals("sg")){
	    	query = "DELETE FROM SG_S_RELATION WHERE SG_SEQ="+sg_seq;
	    }else if(type.equals("sga")){
	    	query = "DELETE FROM SGA_S_RELATION WHERE SGA_SEQ="+sg_seq;
	    }
	    stmt.executeUpdate(query);
	    
	    //type sg(SITE_GROUP) sga(SITE_GROUP_ALLIENCE)
	    if(type.equals("sg")){
	    	query = "DELETE FROM SITE_GROUP WHERE SG_SEQ="+sg_seq;
	    }else if(type.equals("sga")){
	    	query = "DELETE FROM SITE_GROUP_ALLIENCE WHERE SGA_SEQ="+sg_seq;
	    }
	    stmt.executeUpdate(query);
		dbconn.close();
		
//		 2007.10.12
		stmt.close();
	}

    //사이트그룹을 가져온다.
    /**
     * @param SGseq
     * @return
     * @throws ClassNotFoundException
     * @throws Exception
     */
    public String getSG(String SGseq, String type)
    throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
        PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		String SGval = "";

        try {
        	conn  = new DBconn();
        	conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			//type sg(SITE_GROUP) sga(SITE_GROUP_ALLIENCE)
		    if(type.equals("sg")){
		    	sb.append("SELECT SG_NAME FROM SITE_GROUP WHERE SG_SEQ="+SGseq);
		    }else if(type.equals("sga")){
		    	sb.append("SELECT SGA_NAME FROM SITE_GROUP_ALLIENCE WHERE SGA_SEQ="+SGseq);
		    }

            
			pstmt = conn.createPStatement( sb.toString() );
			rs = conn.executeQuery(pstmt);
			if( rs.next() ) {
				if(type.equals("sg")){
					SGval = rs.getString("SG_NAME");
			    }else if(type.equals("sga")){
			    	SGval = rs.getString("SGA_NAME");
			    }
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
        return SGval;
    }
    
    public void insertGSN(String SGseq, String GSN, String type)
    throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();

        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();

       	 	//2007.10.15 수정 : 이경환
       	 	//sb.append(" INSERT INTO SG_S_RELATION SELECT A.S_SEQ, "+SGseq+", S_NAME FROM SITE A WHERE A.S_SEQ IN ("+GSN+") ");
       	 	if(type.equals("sg")) sb.append(" INSERT INTO SG_S_RELATION(SG_SEQ,S_SEQ,S_NAME) SELECT "+SGseq+", A.S_SEQ,A.S_NAME FROM SITE A WHERE A.S_SEQ IN ("+GSN+") ");
       	 	if(type.equals("sga")) sb.append(" INSERT INTO SGA_S_RELATION(SGA_SEQ,S_SEQ,S_NAME) SELECT "+SGseq+", A.S_SEQ,A.S_NAME FROM SITE A WHERE A.S_SEQ IN ("+GSN+") ");
       	 	
       	 	pstmt = conn.createPStatement(sb.toString());
            conn.executeUpdate(pstmt);

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
            
            
        }
    }
    
    public void deleteGSN(String SGseq, String GSN, String type)
    throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();

        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	
       	 	if(type.equals("sg")) sb.append(" DELETE FROM SG_S_RELATION WHERE S_SEQ IN ("+GSN+") ");
       	 	if(type.equals("sga")) sb.append(" DELETE FROM SGA_S_RELATION WHERE S_SEQ IN ("+GSN+") AND SGA_SEQ = "+SGseq+"");
       	 	
       	 	System.out.println(sb.toString());
            pstmt = conn.createPStatement( sb.toString());
            conn.executeUpdate(pstmt);

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
            
        }
    }
    
	//SITE_GROUP, SG_S_RELATION 테이블 의 데이터를 html 형태로 반환한다.
	public String getSiteHtml(String type) {
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
		    
		    if(type.equals("sg")){
		    	sb.append(" SELECT A.SG_SEQ, A.SG_NAME, B.S_NAME, B.S_SEQ, C.s_url, C.S_LANG  \n");
		    	sb.append(" FROM SITE_GROUP A, SG_S_RELATION B, SITE C   \n");
		    	sb.append(" WHERE A.SG_SEQ=B.SG_SEQ AND B.s_seq = C.s_seq \n");		   
		    	sb.append(" ORDER BY A.SG_SEQ ASC  \n");
		    }else{
		    	sb.append(" SELECT A.SGA_SEQ, A.SGA_NAME, B.S_NAME, B.S_SEQ, C.S_URL, C.S_LANG  \n");
		    	sb.append("	FROM SITE_GROUP_ALLIENCE A, SGA_S_RELATION B, SITE C  \n");
		    	sb.append(" WHERE A.SGA_SEQ=B.SGA_SEQ AND B.S_SEQ = C.S_SEQ  \n");
		    	sb.append("	ORDER BY A.SGA_SEQ ASC  \n");
		    }
		    
		    
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
            
            if(type.equals("sg")){
            	while( rs.next() ) {
            		sbHtml.append("<tr>		\n");
            		if( tempSgseq != rs.getInt("SG_SEQ") ) {
            			sbHtml.append("	<td align='center'>"+rs.getString("SG_NAME")+"</td>		\n");
            		}else {
            			sbHtml.append("	<td align='center'>&nbsp;</td>		\n");
            		}
            		tempSgseq = rs.getInt("SG_SEQ");
            		sbHtml.append("	<td align='center'>"+rs.getString("S_NAME")+"</td>		\n");
            		sbHtml.append("	<td align='center'>"+rs.getString("S_LANG")+"</td>		\n");
            		sbHtml.append("	<td align='left'>"+rs.getString("s_url")+"</td>		\n");
            		sbHtml.append("</tr>		\n");
            	}
            }else{
            	while( rs.next() ) {
            		sbHtml.append("<tr>		\n");
            		if( tempSgseq != rs.getInt("SGA_SEQ") ) {
        				sbHtml.append("	<td align='center'>"+rs.getString("SGA_NAME")+"</td>		\n");
        			}else {
        				sbHtml.append("	<td align='center'>&nbsp;</td>		\n");
        			}
        			tempSgseq = rs.getInt("SGA_SEQ");
            		sbHtml.append("	<td align='center'>"+rs.getString("S_NAME")+"</td>		\n");
            		sbHtml.append("	<td align='center'>"+rs.getString("S_LANG")+"</td>		\n");
            		sbHtml.append("	<td align='left'>"+rs.getString("s_url")+"</td>		\n");
            		sbHtml.append("</tr>		\n");
            	}
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
	
	//SITE_GROUP, SG_S_RELATION 테이블 의 데이터를 html 형태로 반환한다.
	public String getAllSiteHtml( String type ) {
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
		    
		    sb.append(" SELECT S_CODE1, A.S_SEQ, S_NAME, S_LANG, s_url  \n");
		    sb.append(" FROM SITE A  \n");
		    if(type.equals("sg")){
		    	sb.append(" WHERE NOT EXISTS (SELECT 1 FROM SG_S_RELATION WHERE S_SEQ=A.S_SEQ) ORDER BY S_NAME \n");
		    }
		    
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
            String SGNAME = "";
            
			while( rs.next() ) {
				switch( rs.getInt("S_CODE1") )
				{
					case 1: SGNAME = "언론사";     break;
					case 2: SGNAME = "정부/공공";  break;
					case 3: SGNAME = "금융";       break;
					case 4: SGNAME = "NGO";        break;
					case 5: SGNAME = "기업/단체";  break;
					case 6: SGNAME = "기타";       break;
					default : SGNAME = "전체";       break;
				}
				sbHtml.append("<tr>		\n");
				sbHtml.append("	<td align='center'>"+SGNAME+"</td>		\n");
    			sbHtml.append("	<td align='center'>"+rs.getString("S_NAME")+"</td>		\n");
    			sbHtml.append("	<td align='center'>"+rs.getString("S_LANG")+"</td>		\n");
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
