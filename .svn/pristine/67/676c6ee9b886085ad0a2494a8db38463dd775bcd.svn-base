/**
========================================================
주 시 스 템 : RSN
서브 시스템 :
프로그램 ID : siteDataInfo
프로그램 명 : 사이트그룹 데이터 class
프로그램개요 : 사이트그룹 Data Beans
작 성 자 : 윤석준
작 성 일 : 2006. 04. 11
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.search;
/// SSL
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import risk.DBconn.DBconn;
import risk.admin.bookmark.BookMarkMgr;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class MetaMgr {

    DBconn dbconn   = null;
    Statement stmt  = null;
    ResultSet rs    = null;
    
    StringBuffer sb = null;    
    String sQuery   = "";
    String BookMarkQuery = null;
    
    private BookMarkMgr bmMgr = null;
    

	private int bookMarkPage = 0;
    private String bookMarkNum = null;
    
    public  MetaBean mBean  = null;
    public int mBeanTotalCnt   = 0;
    public int mBeanTotalCntSame   = 0;
    public int mBeanPageCnt    = 0;
    public int mBeanDataCnt    = 0;
    public int mBeanIssueTotalCnt    = 0;
    public int mBeanTempTotalCnt    = 0;
    public int mBeanAllTotalCnt    = 0;
    
    public int portalCnt       = 0;
    public int miTotalCnt      = 0;
    
    
    ArrayList arrSourceCnt = new ArrayList();

    public ArrayList getArrSourceCnt() {
		return arrSourceCnt;
	}


	public String msMinNo   = "";   //검색 시작 MD_SEQ
    public String msMaxNo   = "";   //검색 마지막 MD_SEQ
    public String msKeyTitle= "";   //검색 그룹 한글명


    public int mBeanTotalPageCount = 0;

    public MetaMgr() {

    }
    
    public int getBookMarkPage() {
		return bookMarkPage;
	}


	public String getBookMarkNum() {
		return bookMarkNum;
	}


    public ArrayList getSiteGroup( ) {
        return getSiteGroup( "" );
    }


    public ArrayList getSiteGroup(String psMgSite ) {

        ArrayList arrlist = new ArrayList();

        try {

            sb = new StringBuffer();
  
            /*sb.append("SELECT A.SG_SEQ                       			\n");
            sb.append("     , A.SG_NAME                       			\n");
            sb.append("  FROM SITE_GROUP A 								\n");
            sb.append("     , IC_S_RELATION B							\n");
           // if ( !psMgSite.equals("0") )
            sb.append(" WHERE A.SG_SEQ IN ("+psMgSite+")				\n");
            sb.append("   AND A.SG_SEQ = B.S_SEQ						\n");
            sb.append(" ORDER BY B.IC_ORDER ASC  						\n");*/
            
            // 18.07.05 고기범 수정
            sb.append(" SELECT A.SG_SEQ                       				\n");
            sb.append("      , A.SG_NAME                       			 	\n");
            sb.append("   FROM SITE_GROUP A 								\n");
            if(!psMgSite.equals(""))sb.append("  WHERE A.SG_SEQ IN ("+psMgSite+")					\n");
            sb.append("  ORDER BY A.SG_ORDER ASC 							\n");
            
            
            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            
            System.out.println(sb.toString());
            
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());

            while( rs.next() ) {
                arrlist.add( new siteGroupInfo( rs.getInt("SG_SEQ"), rs.getString("SG_NAME") ) );
            }

            sb = null;

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return arrlist;
    }

    /**
    *   사이트 데이터 조회
    */

    public ArrayList getSiteData(String psSiteGroup ) {

        ArrayList arrlist = new ArrayList();

        try {
            if ( !psSiteGroup.equals("") ) {
                sb = new StringBuffer();

                sb.append("SELECT S_SEQ,                   \n");
                sb.append("       SG_SEQ,                   \n");
                sb.append("       S_NAME                   \n");
                sb.append("  FROM SG_S_RELATION                 \n");
                sb.append(" WHERE SG_SEQ IN (" + psSiteGroup +") \n");
                sb.append(" ORDER BY S_NAME                \n");

                dbconn = new DBconn();
                dbconn.getDBCPConnection();

                stmt = dbconn.createStatement();
                rs = stmt.executeQuery(sb.toString());

                while( rs.next() ) {
                    arrlist.add( new siteDataInfo(  rs.getInt("S_SEQ")     ,
                                                    rs.getInt("SG_SEQ")     ,
                                                    rs.getString("S_NAME") )
                               );
                }

                sb = null;
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
        return arrlist;
    }


    public void getMaxMinNo( String psSDate, String psEdate) {
    	getMaxMinNo(psSDate, psEdate, "00", "23");
    }
    
    public void getMaxMinNo( String psSDate, String psEdate, String psSTime, String psETime) {
    	getMaxMinNo(psSDate, psEdate, psSTime, psETime, "");
    }

    public void getMaxMinNo( String psSDate, String psEdate, String psSTime, String psETime, String stname ) {
    	
    	DateUtil du = new DateUtil();
    	
    	if(psSTime.length() == 1){
    		psSTime = "0" + psSTime;  
    	}
    	if(psETime.length() == 1){
    		psETime = "0" + psETime;  
    	}

        try {
        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
             
            sb = new StringBuffer();                    
            sb.append(" SELECT (SELECT MD_SEQ FROM "+stname+"META WHERE MD_DATE BETWEEN '"+psSDate+" "+psSTime+":00:00' AND '"+psSDate+" 23:59:59' ORDER BY MD_DATE ASC LIMIT 1) MIN_NO \n");
            sb.append("        ,(SELECT MD_SEQ FROM "+stname+"META WHERE MD_DATE BETWEEN '"+psEdate+" 00:00:00' AND '"+psEdate+" "+psETime+":59:59' ORDER BY MD_DATE DESC LIMIT 1) MAX_NO \n");
                       

            Log.debug(sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());

            if ( rs.next() ) {
                msMinNo = rs.getString("MIN_NO");
                msMaxNo = rs.getString("MAX_NO");
                
                if(msMinNo==null && msMaxNo == null){
                	msMinNo = "0";
                	msMaxNo = "0";
                }else{
                    if(msMinNo==null || msMinNo.equals("null")){
                    	
                    	if(Integer.parseInt(psSDate.replaceAll("-", "") + psSTime) >= Integer.parseInt(du.getDate("yyyyMMddHH"))){
                    		msMinNo = msMaxNo+1;
                    	}else{
                    		msMinNo = "0";
                    	}
                    	
                    }
                    if(msMaxNo==null || msMaxNo.equals("null")){
                    	msMaxNo = "999999999";
                    }               	
                }
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
    }
    
 public String getNotSameChk_In_MapMetaSeq() {

	 	String result = "";
        try {
        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
             
            sb = new StringBuffer();                    
            sb.append("	SELECT MD_SEQ							\n");
            sb.append("		FROM MAP_META_SEQ					\n");
            sb.append("		WHERE SAME_CHK = 0					\n");
            sb.append("		ORDER BY MD_SEQ ASC					\n");

            Log.debug(sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());

            while ( rs.next() ) {
            	if(result.equals("")){
            		result = rs.getString("MD_SEQ");
            	}else{
            		result += ","+rs.getString("MD_SEQ");
            	}
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

    public String IntconvTypeCodeToSQL( int psTypeCode ) {

        String sTypeCondition = "";
        
        if ( psTypeCode==1 ) {
            sTypeCondition = "  AND MD_TYPE = 1 ";
        } else if ( psTypeCode==2 ) {
            sTypeCondition = "  AND MD_TYPE = 2 ";
        } else if ( psTypeCode==3 ) {
            sTypeCondition = "  AND MD_TYPE = 3 ";
        } else if ( psTypeCode==4 ) {
            sTypeCondition = "  AND MD_TYPE <= 2 ";
        } else if ( psTypeCode==5 ) {
            sTypeCondition = "  AND (MD_TYPE = 1 OR MD_TYPE = 3) ";
        } else if ( psTypeCode==6 ) {
            sTypeCondition = "  AND MD_TYPE >= 2 ";
        } else if ( psTypeCode==7 ) {
            sTypeCondition = "";
        }
        return sTypeCondition;
    }
    
    public String convTypeCodeToSQL( String psTypeCode ) {

        String sTypeCondition = "";

        if ( psTypeCode.equals("1") ) {
            sTypeCondition = "  AND B.MD_TYPE = 1 ";
        } else if ( psTypeCode.equals("2") ) {
            sTypeCondition = "  AND B.MD_TYPE = 2 ";
        } else if ( psTypeCode.equals("3") ) {
            sTypeCondition = "  AND B.MD_TYPE <= 2 ";
        } else if ( psTypeCode.equals("4") ) {
            sTypeCondition = "  AND B.MD_TYPE = 3 ";
        } else if ( psTypeCode.equals("5") ) {
            sTypeCondition = "  AND (B.MD_TYPE = 1 OR B.MD_TYPE = 3) ";
        } else if ( psTypeCode.equals("6") ) {
            sTypeCondition = "  AND B.MD_TYPE >= 2 ";
        } else if ( psTypeCode.equals("7") ) {
            sTypeCondition = "";
        }

        return sTypeCondition;
	}
    
    public MetaBean getMetaData(String md_seq){
    	return getMetaData(md_seq, ""); 
    }
    
    public MetaBean getMetaData(String md_seq, String stName)
    {
    	ArrayList arrlist = new ArrayList();
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();

            sb = new StringBuffer();

            sb.append("SELECT M.MD_SEQ         ,                                   \n");
            sb.append("       M.S_SEQ        ,                                   \n");
            sb.append("       M.SG_SEQ        ,                                   \n");
            sb.append("       (SELECT SG_NAME FROM SITE_GROUP WHERE SG_SEQ = M.SG_SEQ) AS SG_NAME       ,                                   \n");
            sb.append("       M.MD_SITE_NAME       ,                                   \n");
            sb.append("       M.MD_MENU_NAME       ,                                   \n");
            sb.append("       M.MD_TYPE       ,                                   \n");
            sb.append("       DATE_FORMAT(M.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE,          \n");
            sb.append("       M.MD_SAME_COUNT    ,                                   \n");
            sb.append("       M.MD_PSEQ        ,                                   \n");
            sb.append("       M.MD_TITLE      ,                                   \n");
            sb.append("       M.MD_URL        ,                                   \n");
            sb.append("       M.MD_RELATION        ,                                   \n");
            if(stName.equals("")){
            	sb.append("       FN_GET_KEYWORD(M.MD_SEQ) AS K_VALUE,                  \n");
            }else if(!stName.equals("PORTAL_SEARCH_")){
            	sb.append("       (SELECT EK_VALUE FROM EXCEPTION_KEYWORD WHERE EK_SEQ = M.EK_SEQ) AS K_VALUE,                  \n");
            }
            sb.append("       M.MD_IMG        ,                                   \n");
            if(stName.equals("PORTAL_SEARCH_")){
            	sb.append("       M.MD_CONTENT    ,                                     \n");
            }else{
            	sb.append("       M.MD_CONTENT    ,                                     \n");
            	
            }
            sb.append("       M.L_ALPHA                                           \n");
            if(stName.equals("PORTAL_SEARCH_")){
            	sb.append("  FROM "+stName+"META M, (SELECT  MD_SEQ FROM "+stName+"IDX WHERE MD_SEQ = "+md_seq+"  GROUP BY MD_SEQ) I \n");
            }else{
            	sb.append("  FROM "+stName+"META M, (SELECT  MD_SEQ FROM "+stName+"IDX WHERE MD_SEQ = "+md_seq+"  GROUP BY MD_SEQ) I \n");
            }
            sb.append(" WHERE                           \n");                      				  
            sb.append("   	  M.MD_SEQ = I.MD_SEQ                          \n");
            //if(!stName.equals("PORTAL_SEARCH_")) sb.append("   	AND M.MD_SEQ = D.MD_SEQ                         \n");
            sb.append(" ORDER BY M.MD_DATE ASC                                    \n");

            Log.debug(sb.toString() );
            rs = stmt.executeQuery(sb.toString());
            
            if( rs.next() ) {
            	 
	        	 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setS_seq(rs.getString("S_SEQ"));
	        	 mBean.setSg_seq(rs.getString("SG_SEQ"));
	        	 mBean.setSg_name(rs.getString("SG_NAME"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_type(rs.getString("MD_TYPE"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_pseq(rs.getString("MD_PSEQ"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_img(rs.getString("MD_IMG"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_content(rs.getString("MD_CONTENT"));
	        	 mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));
	        	 mBean.setMd_relation(rs.getString("MD_RELATION"));
	        	 if(!stName.equals("PORTAL_SEARCH_")) mBean.setK_value(rs.getString("K_VALUE"));
	        	 mBean.setL_alpha(rs.getString("L_ALPHA"));
	        
            } // end while                        

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return mBean;
    }
    
    public MetaBean getWarningMetaData(String md_seq, String stName)
    {
    	ArrayList arrlist = new ArrayList();
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();

            sb = new StringBuffer();

            sb.append("SELECT M.MD_SEQ         ,                                   \n");
            sb.append("       M.S_SEQ        ,                                   \n");
            sb.append("       M.SG_SEQ        ,                                   \n");
            sb.append("       (SELECT SG_NAME FROM SITE_GROUP WHERE SG_SEQ = M.SG_SEQ) AS SG_NAME       ,                                   \n");
            sb.append("       M.MD_SITE_NAME       ,                                   \n");
            sb.append("       M.MD_MENU_NAME       ,                                   \n");
            sb.append("       M.MD_TYPE       ,                                   \n");
            sb.append("       DATE_FORMAT(M.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE,          \n");
            sb.append("       M.MD_SAME_COUNT    ,                                   \n");
            sb.append("       M.MD_PSEQ        ,                                   \n");
            sb.append("       M.MD_TITLE      ,                                   \n");
            sb.append("       M.MD_URL        ,                                   \n");
            if(stName.equals("")){
            	sb.append("       FN_GET_KEYWORD_CATEGORY(M.MD_SEQ) AS K_VALUE,                  \n");
            }else if(!stName.equals("PORTAL_SEARCH_")){
            	sb.append("       (SELECT EK_VALUE FROM EXCEPTION_KEYWORD WHERE EK_SEQ = M.EK_SEQ) AS K_VALUE,                  \n");
            }
            sb.append("       M.MD_IMG        ,                                   \n");
            if(stName.equals("PORTAL_SEARCH_")){
            	sb.append("       M.MD_CONTENT    ,                                     \n");
            }else{
            	sb.append("       M.MD_CONTENT    ,                                     \n");
            	
            }
            sb.append("       M.L_ALPHA                                           \n");
            if(stName.equals("PORTAL_SEARCH_")){
            	sb.append("  FROM "+stName+"META M, (SELECT  MD_SEQ FROM "+stName+"IDX WHERE MD_SEQ = "+md_seq+"  GROUP BY MD_SEQ) I \n");
            }else{
            	sb.append("  FROM "+stName+"META M, (SELECT  MD_SEQ FROM "+stName+"IDX WHERE MD_SEQ = "+md_seq+"  GROUP BY MD_SEQ) I \n");
            }
            sb.append(" WHERE                           \n");                      				  
            sb.append("   	  M.MD_SEQ = I.MD_SEQ                          \n");
            //if(!stName.equals("PORTAL_SEARCH_")) sb.append("   	AND M.MD_SEQ = D.MD_SEQ                         \n");
            sb.append(" ORDER BY M.MD_DATE ASC                                    \n");

            Log.debug(sb.toString() );
            rs = stmt.executeQuery(sb.toString());
            
            if( rs.next() ) {
            	 
	        	 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setS_seq(rs.getString("S_SEQ"));
	        	 mBean.setSg_seq(rs.getString("SG_SEQ"));
	        	 mBean.setSg_name(rs.getString("SG_NAME"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_type(rs.getString("MD_TYPE"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_pseq(rs.getString("MD_PSEQ"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_img(rs.getString("MD_IMG"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_content(rs.getString("MD_CONTENT"));
	        	 mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));
	        	 if(!stName.equals("PORTAL_SEARCH_")) mBean.setK_value(rs.getString("K_VALUE"));
	        	 mBean.setL_alpha(rs.getString("L_ALPHA"));
	        
            } // end while                        

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return mBean;
    }
    
    public MetaBean getTopData(String t_seq)
    {
    	ArrayList arrlist = new ArrayList();
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();

            sb = new StringBuffer();

            
            sb.append("SELECT T_SEQ      AS MD_SEQ			\n");
            sb.append("     , S_SEQ      AS S_SEQ			\n");
            sb.append("     , 30         AS SG_SEQ			\n");
            sb.append("     , T_SITE     AS MD_SITE_NAME	\n");
            sb.append("     , T_BOARD    AS MD_MENU_NAME	\n");
            sb.append("     , 1          AS MD_TYPE			\n");
            sb.append("     , T_DATETIME AS MD_DATE			\n");
            sb.append("     , 0          AS MD_SAME_COUNT 	\n");
            sb.append("     , T_SEQ      AS MD_PSEQ			\n");
            sb.append("     , T_TITLE    AS MD_TITLE		\n");
            sb.append("     , T_URL      AS MD_URL			\n");
            sb.append("     , ''         AS K_VALUE			\n");
            sb.append("     , ''         AS MD_IMG			\n");
            sb.append("     , ''         AS MD_CONTENT		\n");
            sb.append("     , 'KOR'      AS L_ALPHA			\n");
            sb.append("  FROM TOP 							\n");
            sb.append(" WHERE T_SEQ = "+t_seq+"				\n");
            
            Log.debug(sb.toString() );
            rs = stmt.executeQuery(sb.toString());
            
            if( rs.next() ) {
            	 
	        	 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setS_seq(rs.getString("S_SEQ"));
	        	 mBean.setSg_seq(rs.getString("SG_SEQ"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_type(rs.getString("MD_TYPE"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_pseq(rs.getString("MD_PSEQ"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_img(rs.getString("MD_IMG"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_content(rs.getString("MD_CONTENT"));
	        	 mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));
	        	 mBean.setK_value(rs.getString("K_VALUE"));
	        	 mBean.setL_alpha(rs.getString("L_ALPHA"));
	        
            } // end while                        

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return mBean;
    }
    
    public ArrayList getK_XPData(String md_seq){
    	return getK_XPData(md_seq, "");
    }
    
    public ArrayList getK_XPData(String md_seq, String subMode)
    {
    	ArrayList arrlist = new ArrayList();
    	risk.admin.keyword.KeywordBean keyBean = null;
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();

            
            if(subMode.equals("TOP")){
            	if(md_seq.equals("All")){
                	sb = new StringBuffer();
                    sb.append("SELECT T_XP AS K_XP, T_VALUE AS K_VALUE FROM TOP_KEYWORD WHERE T_YP =0 AND T_ZP =0 	\n");
                }else{
                	sb = new StringBuffer();
                    sb.append("SELECT DISTINCT A.K_XP																							\n");
                    sb.append("     , (SELECT T_VALUE FROM TOP_KEYWORD WHERE T_XP = A.K_XP AND T_YP =0 AND T_ZP =0) AS K_VALUE			\n");
                    sb.append("  FROM TOP_IDX A 																						\n");
                    sb.append(" WHERE A.T_SEQ = "+md_seq+"																				\n");	
                }
            	
            }else{
            	if(md_seq.equals("All")){
                	sb = new StringBuffer();
                    sb.append("SELECT K_XP, K_VALUE FROM KEYWORD WHERE K_YP =0 AND K_ZP =0 AND K_USEYN = 'Y' \n");
                }else{
                	sb = new StringBuffer();
                    sb.append("SELECT DISTINCT A.K_XP																									\n");
                    sb.append("     , (SELECT K_VALUE FROM KEYWORD WHERE K_XP = A.K_XP AND K_YP =0 AND K_ZP =0 AND K_USEYN = 'Y') AS K_VALUE	\n");
                    sb.append("  FROM IDX A 																									\n");
                    sb.append(" WHERE A.MD_SEQ = "+md_seq+"																						\n");	
                }
            }

            Log.debug(sb.toString() );
            rs = stmt.executeQuery(sb.toString());
            
            while( rs.next() ) {
            	 
            	keyBean  = new risk.admin.keyword.KeywordBean();
            	keyBean.setKGxp(rs.getString("K_XP"));
            	keyBean.setKGvalue(rs.getString("K_VALUE"));
            	arrlist.add(keyBean);
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
        return arrlist;
    }
    
    public ArrayList getK_YPData(String md_seq, String k_xp){
    	return getK_YPData(md_seq, k_xp, "");
    }
    
    public ArrayList getK_YPData(String md_seq, String k_xp, String subMode)
    {
    	ArrayList arrlist = new ArrayList();
    	risk.admin.keyword.KeywordBean keyBean = null;
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            
            if(subMode.equals("TOP")){
            	if(md_seq.equals("All")){
                	sb = new StringBuffer();
                    sb.append("SELECT T_YP AS K_YP, T_VALUE AS K_VALUE FROM TOP_KEYWORD WHERE T_XP = "+k_xp+" AND T_ZP = 0\n");
                }else{
    	            sb = new StringBuffer();
    	            sb.append("SELECT DISTINCT A.K_YP																					\n");
    	            sb.append("     , (SELECT T_VALUE FROM TOP_KEYWORD WHERE T_XP = "+k_xp+" AND T_YP =A.K_YP AND T_ZP =0) AS K_VALUE	\n");
    	            sb.append("  FROM TOP_IDX A 																						\n");
    	            sb.append(" WHERE A.T_SEQ = "+md_seq+"																				\n");
    	            sb.append("   AND A.K_XP = "+k_xp+"																					\n");
                }
            }else{
            	if(md_seq.equals("All")){
                	sb = new StringBuffer();
                    sb.append("SELECT K_YP, K_VALUE FROM KEYWORD WHERE K_XP = "+k_xp+" AND K_ZP = 0 AND K_USEYN = 'Y' \n");
                }else{
    	            sb = new StringBuffer();
    	            sb.append("SELECT DISTINCT A.K_YP																								\n");
    	            sb.append("     , (SELECT K_VALUE FROM KEYWORD WHERE K_XP = "+k_xp+" AND K_YP =A.K_YP AND K_ZP =0 AND K_USEYN = 'Y') AS K_VALUE	\n");
    	            sb.append("  FROM IDX A 																										\n");
    	            sb.append(" WHERE A.MD_SEQ = "+md_seq+"																							\n");
    	            sb.append("   AND K_XP = "+k_xp+"																								\n");
                }
            }
            
            

            Log.debug(sb.toString() );
            rs = stmt.executeQuery(sb.toString());
            
            while( rs.next() ) {
            	 
            	keyBean  = new risk.admin.keyword.KeywordBean();
            	keyBean.setKGyp(rs.getString("K_YP"));
            	keyBean.setKGvalue(rs.getString("K_VALUE"));
            	arrlist.add(keyBean);
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
        return arrlist;
    }
    
    public ArrayList getMetaDataList(String md_seq){
    	return getMetaDataList(md_seq,"");
    }
    
    public ArrayList getMetaDataList(String md_seq,String mode)
    {
    	ArrayList arrlist = new ArrayList();
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            sb.append("SELECT M.MD_SEQ         ,                                   \n");
            sb.append("       M.S_SEQ        ,                                   \n");
            sb.append("       M.SG_SEQ        ,                                   \n");
            sb.append("       M.MD_SITE_NAME       ,                                   \n");
            sb.append("       M.MD_MENU_NAME       ,                                   \n");
            sb.append("       M.MD_TYPE       ,                                   \n");
            sb.append("       DATE_FORMAT(M.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE,          \n");
            sb.append("       M.MD_SAME_COUNT    ,                                   \n");
            sb.append("       M.MD_PSEQ        ,                                   \n");
            sb.append("       M.MD_TITLE      ,                                   \n");
            sb.append("       M.MD_URL        ,                                   \n");
            sb.append("       FN_GET_KEYWORD(M.MD_SEQ) AS K_VALUE,                  \n");
            sb.append("       M.MD_IMG        ,                                   \n");
            sb.append("       M.MD_RELATION        ,                                   \n");
            sb.append("       M.JSON_DATA        ,                                   \n");
            if(mode.equals("PORTAL_SEARCH_")){
            	sb.append("       M.MD_CONTENT    ,                                       \n");
            }else{
            	sb.append("       M.MD_CONTENT    ,                                       \n");
            }
            sb.append("       M.L_ALPHA                                           \n");
            if(mode.equals("PORTAL_SEARCH_")){
            	sb.append("  FROM PORTAL_SEARCH_META M,(SELECT  MD_SEQ, SG_SEQ FROM PORTAL_SEARCH_IDX WHERE MD_SEQ IN ("+md_seq+")  GROUP BY MD_SEQ) I \n");
            	sb.append(" WHERE                           \n");                      				  
            	sb.append("   	    M.MD_SEQ = I.MD_SEQ                          \n");
            }else{
            	sb.append("  FROM META M, (SELECT MD_SEQ FROM IDX WHERE MD_SEQ IN ("+md_seq+")  GROUP BY MD_SEQ) I \n");
            	sb.append(" WHERE                           \n");                      				  
            	sb.append("                             \n");
            	sb.append("   	 M.MD_SEQ = I.MD_SEQ                          \n");
            }
            sb.append(" ORDER BY M.MD_DATE ASC                                    \n");

            Log.debug(sb.toString() );
            rs = stmt.executeQuery(sb.toString());
            
            while( rs.next() ) {
            	 
	        	 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setS_seq(rs.getString("S_SEQ"));
	        	 mBean.setSg_seq(rs.getString("SG_SEQ"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_type(rs.getString("MD_TYPE"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_pseq(rs.getString("MD_PSEQ"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_img(rs.getString("MD_IMG"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_content(rs.getString("MD_CONTENT"));
	        	 mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));
	        	 mBean.setK_value(rs.getString("K_VALUE"));
	        	 mBean.setL_alpha(rs.getString("L_ALPHA"));
	        	 mBean.setMd_relation(rs.getString("MD_RELATION"));
	        	 mBean.setJson_data(rs.getString("JSON_DATA"));
	        	 arrlist.add(mBean);
            } // end while                        

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return arrlist;
    }
    
    public ArrayList getMetaDataList2(String md_seq)
    {
    	ArrayList arrlist = new ArrayList();
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            sb.append(" SELECT ID_SEQ 								\n");
            sb.append("        ,MD_SEQ 								\n");
            sb.append("        ,ID_TITLE 							\n");
            sb.append("        ,ID_URL 								\n");
            sb.append("        ,ID_CONTENT 							\n");
            sb.append("        ,MD_SITE_NAME 						\n");
            sb.append("        ,MD_SITE_MENU 						\n");
            sb.append("        ,S_SEQ 								\n");
            sb.append("        ,SG_SEQ 								\n");
            sb.append("        ,MD_DATE 							\n");
            sb.append("        ,MD_TYPE 							\n");
            sb.append("        ,MD_SAME_CT 							\n");
            sb.append("        ,L_ALPHA 							\n");
            sb.append("        ,MD_PSEQ 							\n");
            sb.append("        ,REG_TYPE 							\n");
            sb.append(" FROM ISSUE_DATA 							\n");
            sb.append(" WHERE MD_PSEQ IN ("+md_seq+")  				\n");
            sb.append(" ORDER BY MD_DATE ASC 						\n");

            Log.debug(sb.toString() );
            rs = stmt.executeQuery(sb.toString());
            
            while( rs.next() ) {
            	 
	        	 mBean  = new MetaBean();
	        	 mBean.setId_seq(rs.getString("ID_SEQ"));
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setS_seq(rs.getString("S_SEQ"));
	        	 mBean.setSg_seq(rs.getString("SG_SEQ"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setMd_site_menu(rs.getString("MD_SITE_MENU"));
	        	 mBean.setMd_type(rs.getString("MD_TYPE"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_pseq(rs.getString("MD_PSEQ"));
	        	 mBean.setTemp1(rs.getString("REG_TYPE"));
	        	 mBean.setMd_title(rs.getString("ID_TITLE"));
	        	 //mBean.setMd_img(rs.getString("MD_IMG"));
	        	 mBean.setMd_url(rs.getString("ID_URL"));
	        	 mBean.setMd_content(rs.getString("ID_CONTENT"));
	        	 mBean.setMd_same_count(rs.getString("MD_SAME_CT"));
	        	 //mBean.setK_value(rs.getString("K_VALUE"));
	        	 mBean.setL_alpha(rs.getString("L_ALPHA"));
	        	 arrlist.add(mBean);
            } // end while                        

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return arrlist;
    }



    /**
    * 유사 기사 리스트 검색
    */
    public ArrayList getSameList( String md_pseq, String md_seq  )
    {
        ArrayList arrlist = new ArrayList();

        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            /*
            sb.append("SELECT M.MD_SEQ         ,                                   \n");
            sb.append("       M.S_SEQ        ,                                   \n");            
            sb.append("       M.MD_SITE_NAME       ,                                   \n");
            sb.append("       M.MD_MENU_NAME       ,                                   \n");
            sb.append("       M.MD_TYPE       ,                                   \n");
            sb.append("       DATE_FORMAT(M.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE,          \n");
            sb.append("       M.MD_SAME_COUNT    ,                                   \n");
            sb.append("       M.MD_PSEQ        ,                                   \n");
            sb.append("       M.MD_TITLE      ,                                   \n");
            sb.append("       M.MD_URL        ,                                   \n");
            sb.append("       FN_GET_KEYWORD(M.MD_SEQ) AS K_VALUE,                \n");
            sb.append("       (SELECT DISTINCT ISSUE_CHECK FROM IDX WHERE MD_SEQ =M.MD_SEQ) AS ISSUE_CHECK,                \n");
            sb.append("       D.MD_CONTENT                                        \n");
            sb.append("FROM META M, DATA D	                             \n");
            sb.append("WHERE M.MD_SEQ = D.MD_SEQ          \n");           
            sb.append("   AND M.MD_PSEQ = " + md_pseq + "                          \n");
            sb.append(" ORDER BY M.MD_DATE ASC                                    										\n");
            */
            
            
            
            //sb.append("## 유사리스트 																						\n");
            //sb.append("SELECT A.* 																						\n");
            //sb.append("     , (SELECT SG_NAME FROM SITE_GROUP WHERE SG_SEQ = A.SG_SEQ) AS SG_NAME																			\n");
            ////sb.append(" 	, (SELECT GROUP_CONCAT(SGA_NAME) FROM SGA_S_RELATION C, SITE_GROUP_ALLIENCE D WHERE  A.S_SEQ = C.S_SEQ AND C.SGA_SEQ = D.SGA_SEQ) SGA_NAME 		\n");
            //sb.append(" 	, IF(A.S_SEQ=2196 OR A.S_SEQ=2199 OR A.S_SEQ=3883, ( SELECT GROUP_CONCAT(SGA_NAME) FROM SGA_S_RELATION C, SITE_GROUP_ALLIENCE D WHERE C.SGA_SEQ = D.SGA_SEQ AND C.S_NAME = SUBSTRING(A.md_site_name,2,LENGTH(A.md_site_name))), (SELECT GROUP_CONCAT(SGA_NAME) FROM SGA_S_RELATION C, SITE_GROUP_ALLIENCE D WHERE  A.S_SEQ = C.S_SEQ AND C.SGA_SEQ = D.SGA_SEQ)) SGA_NAME \n");
            //sb.append(" 	, (SELECT GROUP_CONCAT(DISTINCT D.K_VALUE) FROM IDX C, KEYWORD D WHERE C.MD_SEQ = A.MD_SEQ AND C.K_XP  = D.K_XP AND C.K_YP  = D.K_YP AND C.K_ZP  = D.K_ZP AND D.K_USEYN='Y' AND D.K_TYPE = 2) AS K_VALUE2 																																			  \n");
            //sb.append("  FROM (																							\n");
            //sb.append("        SELECT A.* 																				\n");
            //sb.append("             , FN_GET_KEYWORD(A.MD_SEQ) AS K_VALUE												\n");
            ////sb.append("             , (SELECT DISTINCT ISSUE_CHECK FROM IDX WHERE MD_SEQ =A.MD_SEQ) AS ISSUE_CHECK		\n");
            //sb.append("             , (SELECT SG_SEQ FROM SG_S_RELATION WHERE S_SEQ = A.S_SEQ) AS SG_SEQ				\n");
            //sb.append("          FROM (																					\n");
            //sb.append("                SELECT M.MD_SEQ         ,														\n");                                   
            //sb.append("                       M.S_SEQ        ,        													\n");                           
            //sb.append("                       M.MD_SITE_NAME       ,       												\n");                            
            //sb.append("                       M.MD_MENU_NAME       ,            										\n");                       
            //sb.append("                       M.MD_TYPE       ,                      									\n");             
            //sb.append("                       DATE_FORMAT(M.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE,					\n");          
            //sb.append("                       M.MD_SAME_COUNT    ,                                   					\n");
            //sb.append("                       M.MD_PSEQ        ,                                   						\n");
            //sb.append("                       M.MD_TITLE      ,                                   						\n");
            //sb.append("                       M.MD_URL        ,                                   						\n");
            //sb.append("                       '' as MD_IMPORTANT  ,                                   						\n");
            //sb.append("                       '' as PORTAL_CHECK  ,                                   						\n");
            //sb.append("                       M.MD_CONTENT   ,                                        						\n");
            //sb.append("                       M.ISSUE_CHECK                                        						\n");
            //sb.append("                  FROM META M	                             							\n");
            //sb.append("                 WHERE M.MD_PSEQ = "+md_pseq+"  													\n");                      
            //sb.append("               )A )A , IC_S_RELATION B	 														\n");
            //sb.append(" WHERE A.SG_SEQ = B.S_SEQ 																		\n");
            //sb.append(" ORDER BY A.SG_SEQ, A.MD_DATE ASC																\n");
            
            sb.append("\n").append("SELECT A.* 																						");
		    sb.append("\n").append("        , (SELECT SG_NAME FROM SITE_GROUP WHERE SG_SEQ = A.SG_SEQ) AS SG_NAME					");	
		    sb.append("\n").append("        , IFNULL((SELECT MD_REPLY_TOTAL_CNT FROM META_REPLY_CNT WHERE MD_SEQ = A.MD_SEQ LIMIT 1),0) AS REPLY_CNT					");	
     		sb.append("\n").append(", (SELECT IF(COUNT(*)>0,'Y','N')AS IS_STORAGE  FROM MAP_META_STORAGE WHERE MD_SEQ = A.MD_SEQ ) AS IS_STORAGE\n");
		    sb.append("\n").append("     FROM (																						");	
		    sb.append("\n").append("           SELECT A.* 																			");	
		    sb.append("\n").append("                , FN_GET_KEYWORD(A.MD_SEQ) AS K_VALUE											");	
		    sb.append("\n").append("               # , A.ISSUE_CHECK AS ISSUE_CHECK                                                 ");
		    sb.append("\n").append("                , (SELECT SG_SEQ FROM SG_S_RELATION WHERE S_SEQ = A.S_SEQ) AS SG_SEQ			");	
		    sb.append("\n").append("             FROM (																				");	
		    sb.append("\n").append("                   SELECT M.MD_SEQ         ,													");	
		    sb.append("\n").append("                          M.S_SEQ        ,        												");	
		    sb.append("\n").append("                          M.MD_SITE_NAME       ,       											");	
		    sb.append("\n").append("                          M.MD_MENU_NAME       ,            									");	
		    sb.append("\n").append("                          M.MD_TYPE       ,                      								");	
		    sb.append("\n").append("                          DATE_FORMAT(M.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE,				");	
		    sb.append("\n").append("                          M.MD_SAME_COUNT    ,                                   				");	
		    sb.append("\n").append("                          M.MD_PSEQ        ,                                   					");	
		    sb.append("\n").append("                          M.MD_TITLE      ,                                   					");	
		    sb.append("\n").append("                          M.MD_URL        ,                                   					");	
		    sb.append("\n").append("                          M.MD_CONTENT    ,                                                     ");
		    sb.append("\n").append("                          M.ISSUE_CHECK                                                         ");
		    sb.append("\n").append("                     FROM META M	                             							    ");
		    sb.append("\n").append("                    WHERE M.MD_PSEQ = "+md_pseq+"  													");
		    sb.append("\n").append("                  )A )A																			");	
		    sb.append("\n").append("    ORDER BY A.SG_SEQ, A.MD_DATE ASC																		");
            

            Log.debug(sb.toString() );
            rs = stmt.executeQuery(sb.toString());

            while( rs.next() ) {
            	
            	 mBean  = new MetaBean();
            	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setS_seq(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_type(rs.getString("MD_TYPE"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_pseq(rs.getString("MD_PSEQ"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_content(rs.getString("MD_CONTENT"));
	        	 mBean.setIssue_check(rs.getString("ISSUE_CHECK"));
	        	 mBean.setK_value(rs.getString("K_VALUE"));
	        	 mBean.setS_seq(rs.getString("S_SEQ"));
	        	 mBean.setSg_seq(rs.getString("SG_SEQ"));
	        	 mBean.setSg_name(rs.getString("SG_NAME"));
	        	 mBean.setMd_reply_count(rs.getString("REPLY_CNT"));
	        	 mBean.setIs_storage(rs.getString("IS_STORAGE"));
	        	
                arrlist.add(mBean);

            } // end while

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }

        return arrlist;
    }
    
    /**
     * 파일저장을 위한 조회
     */
     public ArrayList getSearchSaveList(    String psOrder   ,
                                            String md_seqs )
     {
         ArrayList arrlist = new ArrayList();

         try {

             dbconn = new DBconn();
             dbconn.getDBCPConnection();

             //stmt = dbn.createScrollStatement();
             stmt = dbconn.createStatement();

             sb = new StringBuffer();

             sb.append("SELECT M.MD_SEQ         ,                                   \n");
             sb.append("       M.S_SEQ        ,                                   \n");            
             sb.append("       M.MD_SITE_NAME       ,                                   \n");
             sb.append("       M.MD_MENU_NAME       ,                                   \n");
             sb.append("       M.MD_TYPE       ,                                   \n");
             sb.append("       M.MD_DATE  AS MD_DATE,          \n");
             sb.append("       M.MD_SAME_COUNT    ,                                   \n");
             sb.append("       M.MD_PSEQ        ,                                   \n");
             sb.append("       M.MD_TITLE      ,                                   \n");
             sb.append("       M.MD_URL        ,                                   \n");
             sb.append("       FN_GET_KEYWORD(M.MD_SEQ) AS K_VALUE,                  \n");
             sb.append("       D.MD_CONTENT                                           \n");
             sb.append("  FROM META M, DATA D                                        \n");          
             sb.append(" WHERE M.MD_SEQ IN (" + md_seqs + ")                           \n");
             sb.append("   AND M.MD_SEQ = D.MD_SEQ                                         \n");
             sb.append(" ORDER BY M." + psOrder + "                                      \n");

             //Log.debug(sb.toString() );
             rs = stmt.executeQuery(sb.toString());

             while( rs.next() ) {
                 mBeanDataCnt++;               
                 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setS_seq(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_type(rs.getString("MD_TYPE"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_pseq(rs.getString("MD_PSEQ"));
	        	 mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_content(rs.getString("MD_CONTENT"));
	        	 mBean.setK_value(rs.getString("K_VALUE"));
            	
                arrlist.add(mBean);             
             } // end while

         } catch (SQLException ex ) {
             Log.writeExpt(ex, sb.toString() );

         } catch (Exception ex ) {
             Log.writeExpt(ex);

         } finally {
             try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
             try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
             try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
         }

         return arrlist;
     }    
    
     public ArrayList getAllSearchList(      int    piNowpage ,
								             int    piRowCnt  ,
								             String psXp      ,
								             String psYp      ,
								             String psZp      ,
								             String psSGseq   ,
								             String psSDgsn   ,
								             String psDateFrom,
								             String psDateTo  ,
								             String psKeyWord ,
								             String psType    ,
								             String psOrder   ,
								             String psMode    )
{
    	 return getAllSearchList(piNowpage,piRowCnt,psXp,psYp,psZp,psSGseq,psSDgsn,psDateFrom,psDateTo,psKeyWord,psType,psOrder,psMode,"");
     }

    /**
    * 전체 DB 그룹별 검색
    */
    public ArrayList getAllSearchList(     int    piNowpage ,
                                           int    piRowCnt  ,
                                           String psXp      ,
                                           String psYp      ,
                                           String psZp      ,
                                           String psSGseq   ,
                                           String psSDgsn   ,
                                           String psDateFrom,
                                           String psDateTo  ,
                                           String psKeyWord ,
                                           String psType    ,
                                           String psOrder   ,
                                           String psMode    ,
                                           String mode		)
    {
        ArrayList arrlist = new ArrayList();
        String notSameChk_Mdseq = "";
        try {

            //StringUtil  su = new StringUtil();

            getMaxMinNo( psDateFrom, psDateTo, "00", "23" );

            String MinMtNo= msMinNo;
            String MaxMtNo= msMaxNo;
            
            //psKeyWord = psKeyWord.replaceAll("[ ]{1,}|[ ]{1,}&\\?[ ]{1,}", "&");
            
            

            String sTypeCondition = convTypeCodeToSQL(psType);

            if ( psOrder == null || psOrder.equals("" ) ) {
                psOrder  = "MD_DATE";
            }


            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            //stmt = dbn.createScrollStatement();
            stmt = dbconn.createStatement();

            //총개시물 건수를 구한다.
            sb = new StringBuffer();

            //검색조건이 하나도 없을경우
            if( (psXp + psYp + psZp + psSGseq + psSDgsn + psKeyWord + sTypeCondition).equals("") ) {

                sb.append("SELECT COUNT(1) AS TOTAL_CNT                 \n");
                sb.append("  FROM "+mode+"META                                  \n");
                sb.append(" WHERE MD_SEQ = MD_PSEQ                        \n");
                sb.append("   AND MD_SEQ BETWEEN " + MinMtNo     +   "   \n");
                sb.append("                 AND " + MaxMtNo     +   "   \n");



            } else {
                sb.append("SELECT /*+ORDERED*/                              \n");
                sb.append("       COUNT(1) AS TOTAL_CNT                     \n");
                if(mode.equals("PORTAL_SEARCH_")){
                	sb.append("  FROM META B                                      \n");
                	sb.append(" WHERE B.MD_SEQ = B.MD_SEQ                            \n");
                }else{
                	sb.append("  FROM META B, DATA A                                      \n");
                	sb.append(" WHERE B.MD_SEQ = A.MD_SEQ                            \n");
                }
                sb.append(" AND B.MD_SEQ = B.MD_PSEQ                            \n");
                sb.append(" AND B.MD_SEQ BETWEEN " + MinMtNo     +   "       \n");
                sb.append("                 AND " + MaxMtNo     +   "       \n");
               
                if ( !psSDgsn.equals("") )
                sb.append("   AND S_SEQ = " + psSDgsn + "                  \n");
                if ( !psKeyWord.equals("") )
                sb.append("   AND MD_TITLE LIKE '%" + psKeyWord + "%'       \n");
             
                if ( !sTypeCondition.equals("") )
                sb.append("   " + sTypeCondition   +                     "  \n");
            }

            Log.debug(sb.toString() );
            rs = stmt.executeQuery(sb.toString());
            if ( rs.next() ) {
                mBeanTotalCnt  = rs.getInt("TOTAL_CNT");
                mBeanPageCnt   = mBeanTotalCnt / piRowCnt ;
            }

            rs.close();
            rs = null;
            sb = null;

            String sXpValue = "";
            String sYpValue = "";
            String sZpValue = "";
               
            if ( !(psXp + psYp + psZp).equals("") ) {

                sb = new StringBuffer();



                sb.append("SELECT MAX(MAIN) MAIN , MAX(MIDLE) MIDLE, MAX(BOTTOM) BOTTOM     \n");
                sb.append("  FROM (                                                         \n");
                sb.append("        SELECT K_VALUE AS  MAIN , '' AS MIDLE , '' AS BOTTOM     \n");
                sb.append("          FROM "+mode+"KEYWORD                                           \n");
                sb.append("         WHERE K_XP = " + psXp + "                               \n");
                sb.append("           AND K_YP = 0                                          \n");
                sb.append("           AND K_ZP = 0                                          \n");
                sb.append("           AND K_TYPE < 3                                        \n");


                if ( !psYp.equals("") ) {
                    sb.append("        UNION                                                    \n");
                    sb.append("        SELECT '' , K_VALUE MAIN , ''                            \n");
                    sb.append("          FROM "+mode+"KEYWORD                                           \n");
                    sb.append("         WHERE K_XP = " + psXp + "                               \n");
                    sb.append("           AND K_YP = " + psYp + "                               \n");
                    sb.append("           AND K_ZP = 0                                          \n");
                    sb.append("           AND K_TYPE < 3                                        \n");
                }

                if ( !psZp.equals("") ) {
                    sb.append("        UNION                                                    \n");
                    sb.append("        SELECT '' , '' , K_VALUE MAIN                            \n");
                    sb.append("          FROM "+mode+"KEYWORD                                           \n");
                    sb.append("         WHERE K_XP = " + psXp + "                               \n");
                    sb.append("           AND K_YP = " + psYp + "                               \n");
                    sb.append("           AND K_ZP = " + psZp + "                               \n");
                    sb.append("           AND K_TYPE < 3                                        \n");
                }

                sb.append("        ) ");



                Log.debug(sb.toString() );
                rs = stmt.executeQuery(sb.toString());

                if ( rs.next() ) {
                    sXpValue = rs.getString("MAIN")   == null ? "" : rs.getString("MAIN")   ;
                    sYpValue = rs.getString("MIDLE")  == null ? "" : rs.getString("MIDLE")  ;
                    sZpValue = rs.getString("BOTTOM") == null ? "" : rs.getString("BOTTOM") ;
                }

                msKeyTitle = sXpValue;
                if ( !sYpValue.equals("") ) {
                    msKeyTitle += "-" + sYpValue;
                }

                if ( !sZpValue.equals("") ) {
                    msKeyTitle += "-" + sZpValue;
                }
            } else {
                if(psMode.equals("ALLDB")){
                	msKeyTitle = "전체DB검색";
                	if(psKeyWord.length()>0){
                		msKeyTitle += "-"+psKeyWord;
                	}
                }else {
                	msKeyTitle = "전체키워드";
                }
            }

            //게시물 리스트를 구한다.
            if ( mBeanTotalCnt > 0 ) {

                sb = new StringBuffer();                
                
                
                int liststart;
             	int listend;             	

        		liststart = (piNowpage-1) * piRowCnt;
        		listend = piRowCnt;                     
	         
	           sb.append("                 SELECT B.MD_SEQ         ,                                    \n");
	           sb.append("                        B.S_SEQ        ,                                    \n");                
	           sb.append("                        B.MD_SITE_NAME       ,                                    \n");
	           sb.append("                        B.MD_MENU_NAME       ,                                    \n");
	           sb.append("                        B.MD_TYPE       ,                                    \n");
	           sb.append("                        DATE_FORMAT(B.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE,                                \n");	           
	           sb.append("                        B.MD_SAME_COUNT    ,                                    \n");
	           sb.append("                        B.MD_PSEQ        ,                                    \n");
	           sb.append("                        B.MD_TITLE      ,                                    \n");
	           sb.append("                        B.MD_URL  ,                                             \n");
	           if(mode.equals("PORTAL_SEARCH_")){
	        	   sb.append("                        B.MD_CONTENT                                             \n");
	        	   sb.append("                  FROM META B                                              \n");
	           }else{
	        	   sb.append("                        D.MD_CONTENT                                             \n");
	        	   sb.append("                  FROM META B, DATA D                                              \n");
	           }
	           sb.append("                  WHERE                                   \n");
	           sb.append("                       B.MD_SEQ BETWEEN " + MinMtNo + "                      \n");	           
	           sb.append("                                    AND " + MaxMtNo + "                      \n");
	           if(mode.equals("PORTAL_SEARCH_")){
	        	   sb.append("                       AND B.MD_SEQ = B.MD_SEQ                    \n");
	           }else{
	        	   sb.append("                       AND B.MD_SEQ = D.MD_SEQ                    \n");
	           }
	           sb.append("                       AND B.MD_SEQ = B.MD_PSEQ                    \n");
	           if ( !psSDgsn.equals("") )
	           sb.append("                       AND B.S_SEQ = " + psSDgsn + "                           \n");
	           if ( !psKeyWord.equals("") )
	           sb.append("                       AND B.MD_TITLE LIKE '%" + psKeyWord + "%'              \n");
	           if ( !sTypeCondition.equals("") )
	           sb.append("                   " + sTypeCondition   +                      "             \n");
	           sb.append("                   ORDER BY B."+psOrder+"             \n");         	          
	           sb.append("LIMIT "+liststart+","+listend+"                                                  \n");
              	           	
	           Log.debug(sb.toString() );
	           rs = stmt.executeQuery(sb.toString());	           	        	  
	
                while( rs.next() ) {
                    mBeanDataCnt++;

                    String sD_html  = "";
                

                    if ( rs.getCharacterStream("MD_CONTENT") != null ) {

                        StringBuffer output = new StringBuffer();
                        Reader       input  = rs.getCharacterStream("MD_CONTENT");
                        char[]       buffer = new char[2048];

                        int byteRead;
                        while( (byteRead=input.read(buffer,0,1024)) != -1  ){
                           output.append(buffer,0,byteRead);
                        }
                        input.close();

                        sD_html = output.toString();

                    }
                    
                    mBean  = new MetaBean();
					mBean.setMd_seq(rs.getString("MD_SEQ"));
					mBean.setS_seq(rs.getString("S_SEQ"));
					mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
					mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
					mBean.setMd_type(rs.getString("MD_TYPE"));
					mBean.setMd_date(rs.getString("MD_DATE"));
					mBean.setMd_pseq(rs.getString("MD_PSEQ"));
					mBean.setMd_title(rs.getString("MD_TITLE"));
					mBean.setMd_url(rs.getString("MD_URL"));
					mBean.setMd_content(rs.getString("MD_CONTENT"));
					mBean.setK_value("");
					mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));                    
	   	        	arrlist.add(mBean);

                } // end while
            } // end if

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }

        return arrlist;
    }
    
    
    
    /**
     * 키워드 그룹별 검색 오버로딩 (모바일 웹용)
     */
    public ArrayList getKeySearchList(     int    piNowpage ,
								            int    piRowCnt  ,
								            String psXp      ,
								            String psYp      ,
								            String psZp      ,
								            String psSGseq   ,
								            String psSDgsn   ,
								            String psDateFrom,
								            String psDateTo  ,
								            String psKeyWord ,
								            String psType    ,
								            String psOrder   ,
								            String psMode	,
								            String psMname,
								            boolean siteGroupCheck,
								            String storageName
								           
								            
										   )
    {
    	return getKeySearchList(         piNowpage ,
					                     piRowCnt  ,
						                 psXp      ,
						                 psYp      ,
						                 psZp      ,
						                 psSGseq   ,
						                 psSDgsn   ,
						                 psDateFrom,
						                 psDateTo  ,
						                 psKeyWord ,
						                 psType    ,
						                 psOrder   ,
						                 psMode	,
						                 psMname   ,
						                 "N",
						                 "",
						                 "",
						                 "00",
						                 "23",
						                 "",
						                 "",
						                 "",
						                 "",
						                 "",
						                 siteGroupCheck,
						                 storageName
						                 
								   );
    }
    
    public ArrayList getKeySearchList(     int    piNowpage ,
            int    piRowCnt  ,
            String psXp      ,
            String psYp      ,
            String psZp      ,
            String psSGseq   ,
            String psSDgsn   ,
            String psDateFrom,
            String psDateTo  ,
            String psKeyWord ,
            String psType    ,
            String psOrder   ,
            String psMode	,
            String psMname   ,
            String bookMarkYn,
            String ts_type,
            String psMseq,
            String stime,
            String etime,
            String stName,
            String issue_check,
            String keywordKind,
            String reply_check,
            boolean siteGroupCheck
		   )
	{	return getKeySearchList(         piNowpage ,
	        piRowCnt  ,
	        psXp      ,
	        psYp      ,
	        psZp      ,
	        psSGseq   ,
	        psSDgsn   ,
	        psDateFrom,
	        psDateTo  ,
	        psKeyWord ,
	        psType    ,
	        psOrder   ,
	        psMode	,
	        psMname   ,
	        bookMarkYn,
	        ts_type,
	        psMseq,
	        stime,
	        etime,
	        stName,
	        issue_check,
	        keywordKind,
	        reply_check,
	        "",
	        siteGroupCheck,
	        ""	        
	  );
	}
    
    public ArrayList getKeySearchList(     int    piNowpage ,
            int    piRowCnt  ,
            String psXp      ,
            String psYp      ,
            String psZp      ,
            String psSGseq   ,
            String psSDgsn   ,
            String psDateFrom,
            String psDateTo  ,
            String psKeyWord ,
            String psType    ,
            String psOrder   ,
            String psMode	,
            String psMname   ,
            String bookMarkYn,
            String ts_type,
            String psMseq,
            String stime,
            String etime,
            String stName,
            String issue_check,
            String keywordKind,
            String reply_check,
            boolean siteGroupCheck,
            String storageName
		   )
	{	return getKeySearchList(         piNowpage ,
	        piRowCnt  ,
	        psXp      ,
	        psYp      ,
	        psZp      ,
	        psSGseq   ,
	        psSDgsn   ,
	        psDateFrom,
	        psDateTo  ,
	        psKeyWord ,
	        psType    ,
	        psOrder   ,
	        psMode	,
	        psMname   ,
	        bookMarkYn,
	        ts_type,
	        psMseq,
	        stime,
	        etime,
	        stName,
	        issue_check,
	        keywordKind,
	        reply_check,
	        "",
	        siteGroupCheck,
	        storageName
	        
	  );
	}
    
    public ArrayList getKeySearchList(     int    piNowpage ,
            int    piRowCnt  ,
            String psXp      ,
            String psYp      ,
            String psZp      ,
            String psSGseq   ,
            String psSDgsn   ,
            String psDateFrom,
            String psDateTo  ,
            String psKeyWord ,
            String psType    ,
            String psOrder   ,
            String psMode	,
            String psMname   ,
            String bookMarkYn,
            String ts_type,
            String psMseq,
            String stime,
            String etime,
            String stName,
            String issue_check,
            String keywordKind,
            String reply_check,
            String smnCodes,
            boolean siteGroupCheck,
            String storageName
		   )
	{	return getKeySearchList(         piNowpage ,
	        piRowCnt  ,
	        psXp      ,
	        psYp      ,
	        psZp      ,
	        psSGseq   ,
	        psSDgsn   ,
	        psDateFrom,
	        psDateTo  ,
	        psKeyWord ,
	        psType    ,
	        psOrder   ,
	        psMode	,
	        psMname   ,
	        bookMarkYn,
	        ts_type,
	        psMseq,
	        stime,
	        etime,
	        stName,
	        issue_check,
	        keywordKind,
	        reply_check,
	        smnCodes,
	        "",
	        siteGroupCheck,
	        storageName
	  );
	}
    

	/**
	* 키워드 그룹별 검색(정보 검색용 :북마크 추가)
	*/
	public ArrayList getKeySearchList(     int    piNowpage ,
	                                       int    piRowCnt  ,
	                                       String psXp      ,
	                                       String psYp      ,
	                                       String psZp      ,
	                                       String psSGseq   ,
	                                       String psSDgsn   ,
	                                       String psDateFrom,
	                                       String psDateTo  ,
	                                       String psKeyWord ,
	                                       String psType    ,
	                                       String psOrder   ,
	                                       String psMode	,
	                                       String psMname   ,
	                                       String bookMarkYn,
	                                       String ts_type,
	                                       String psMseq,
	                                       String stime,
	                                       String etime,
	                                       String stName,
	                                       String issue_check,
	                                       String keywordKind,
	                                       String reply_check,
	                                       String smnCodes,
	                                       String twitterian,
	                                       boolean siteGroupCheck,
	                                       String storageName
									   )
	
	
	{	
		ArrayList arrlist = new ArrayList();
		//String notSameChkSeq = "";
		if(psXp.length()>2){ msKeyTitle = "전체키워드"; }			
		
	    try {
	
	    	getMaxMinNo( psDateFrom, psDateTo, stime, etime, stName );
	    	//notSameChkSeq = getNotSameChk_In_MapMetaSeq();
	    	//System.out.println("notSameChk: "+notSameChkSeq);
	    	String keywordFunction = "";
	        String MinMtNo= msMinNo;
	        String MaxMtNo= msMaxNo;
	             
	        String sTypeCondition = convTypeCodeToSQL(psType);
	        
	        String[] arrKeyWord = null;
			arrKeyWord = psKeyWord.split("&");
		
			
			/*
			String addQuery = ""; 
	        if(psMode.equals("DELIDX")){
	        	addQuery = " AND B.I_STATUS = 'T'";
	        	if(psMname.length()>0){
	        //		addQuery += " AND A.M_SEQ = "+psMname;
		        }  		        	
	        }else{
	        	addQuery = " AND B.I_STATUS = 'N'";
	        }
	        */
	      
	        dbconn = new DBconn();
	        dbconn.getDBCPConnection();
	
	        //stmt = dbn.createScrollStatement();
	        stmt = dbconn.createStatement();
	        /*
            sb = new StringBuffer();
            sb.append("  SELECT ROWNUM                                    								 		  \n");
	        sb.append("  FROM                                     												  \n");
            sb.append("  (                                     													  \n");
	        sb.append("  SELECT                                     											  \n");
	        sb.append("         md_seq, @RNUM:=@RNUM+1 AS ROWNUM                               					  \n");
	        sb.append("  FROM   (                              													  \n");	       
	        sb.append("                           SELECT				                               			  \n");
            sb.append("                                MIN(DISTINCT A.MD_SEQ) AS MD_SEQ, (SELECT @RNUM:=0) ROW    \n");		   
            sb.append("                           FROM "+stName+"IDX A, "+stName+"META B		                  \n");
            sb.append("                           WHERE A.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"              \n");
            sb.append("                                 AND A.MD_SEQ = B.MD_SEQ                            		  \n");
            
            if ( !psSDgsn.equals("") )
            sb.append("                                 AND B.S_SEQ = '" + psSDgsn + "'               \n");
            if ( !psXp.equals("") )
            sb.append("                                 AND A.K_XP    IN (" + psXp + ")               \n");
            if ( !psYp.equals("") )
            sb.append("                                 AND A.K_YP    = " + psYp + "               	  \n");
            if ( !psZp.equals("") )
            sb.append("                                 AND A.K_ZP    = " + psZp + "                  \n");
            if ( !psSGseq.equals("") )
	        sb.append("                                 AND B.SG_SEQ  IN (" + psSGseq + ")            \n");
            sb.append("                                 AND B.MD_SEQ = B.MD_SEQ                       \n");
            if(!issue_check.equals("")){
                sb.append("                                 AND B.ISSUE_CHECK = '"+issue_check+"'      \n");
            }
            if(!ts_type.equals("")){
            	sb.append("                                 AND B.TS_TYPE IN ("+ts_type+")             \n");
            }
            
            sb.append("                                 "+addQuery+"                   				   \n");
            
            if ( !sTypeCondition.equals("") )
	        sb.append("                                 " + sTypeCondition   + "					   \n");
            if(psMode.equals("DELIDX")){
	        	sb.append("                        GROUP BY B.MD_SEQ                                   \n");
	        }else{
	        	sb.append("                        GROUP BY B.MD_PSEQ                                  \n");
	        }
            sb.append("  						   ORDER BY MD_SEQ DESC								   \n");
            sb.append("  			)RESULT                              							   \n");
            sb.append("    )RESULT                              									   \n");
            sb.append("    WHERE  MD_SEQ IN :BOOKMARK                             					   \n");
            
            BookMarkQuery = sb.toString();
            */
            
            //북마크찾기 일시 페이지 값
            /*
            bmMgr = new BookMarkMgr();
            if(bookMarkYn.equals("Y")){            	
            	bookMarkNum = bmMgr.getBookMarkNum("search");
            	
            	//현재 리스트 카운트 기준이 MD_SEQ가 아닌 MD_PSEQ이기 때문에 번호대역대별로 차이 수가 존재하므로  차이를 계산해줘야함           	
            	bookMarkPage = bmMgr.getPageNum("search", piRowCnt, BookMarkQuery);
            	 
            	//북마크 번호가 시작 날짜의 MD_SEQ 최소값 보다 적으면 페이지 설정 초기화
            	if(Integer.parseInt(bookMarkNum)<Integer.parseInt(MinMtNo)) bookMarkPage = 1;
            	piNowpage = bookMarkPage;
            	
            	System.out.println("bookMarkPage:"+bookMarkPage);
            }
            */
            String user_id = "";
            String user_nick = "";
            /*
            if(psMode.equals("SNS")){
            	sb = new StringBuffer();
            	sb.append("  SELECT U_ID FROM SNSUSER            \n");
            	rs = stmt.executeQuery(sb.toString());
            	while ( rs.next() ) {
            		if(user_id.equals("")){
            			if(!rs.getString("U_ID").equals("")){
            				user_id = "'"+rs.getString("U_ID")+"'";
            			}
            		}else{
            			if(!rs.getString("U_ID").equals("")){
            				user_id += ","+"'"+rs.getString("U_ID")+"'";
            			}
            		}
            	}
            	sb = new StringBuffer();
            	sb.append("  SELECT U_NICK FROM SNSUSER            \n");
            	rs = stmt.executeQuery(sb.toString());
            	while ( rs.next() ) {
            		if(user_nick.equals("")){
            			if(!rs.getString("U_NICK").equals("")){
            				user_nick = "'"+rs.getString("U_NICK")+"'";
            			}
            		}else{
            			if(!rs.getString("U_NICK").equals("")){
            				user_nick += ","+"'"+rs.getString("U_NICK")+"'";
            			}
            		}
            	}
            }
            */
            
            
            
            
	        //총개시물 건수를 구한다.
	        sb = new StringBuffer();
	        
	        
	        
	        if (msKeyTitle == "전체키워드") {
	        	if(psMode.equals("DELIDX")){
		        	sb.append("SELECT COUNT(DISTINCT B.MD_SEQ) AS TOTAL_CNT\n");
		        }else{
		        	sb.append("SELECT COUNT(DISTINCT B.MD_PSEQ) AS TOTAL_CNT\n");
		        }
		        sb.append("     , COUNT(DISTINCT B.MD_SEQ) AS TOTAL_CNT2\n");
		        sb.append("  FROM "+stName+"IDX A, "+stName+"META B\n");
		        if(!"".equals(storageName)) {
		        	sb.append("  INNER JOIN MAP_META_STORAGE MS ON B.MD_SEQ = MS.MD_SEQ 	\n");
		        }
		        sb.append(" WHERE B.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"\n");
		        sb.append("   AND A.MD_SEQ = B.MD_SEQ\n");	
	        }else {
	        	if(psMode.equals("DELIDX")){
		        	sb.append("SELECT COUNT(DISTINCT B.MD_SEQ) AS TOTAL_CNT\n");
		        }else{
		        	sb.append("SELECT COUNT(DISTINCT B.MD_PSEQ) AS TOTAL_CNT\n");
		        }
		        sb.append("     , COUNT(DISTINCT B.MD_SEQ) AS TOTAL_CNT2\n");
		        sb.append("  FROM "+stName+"IDX A, "+stName+"META B\n");
		        sb.append(" WHERE B.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"\n");
		        sb.append("   AND A.MD_SEQ = B.MD_SEQ\n");	
	        }
	        
	        
	        
	        
	        
	        
	        
        	if ( !psSDgsn.equals("") )
            sb.append("                                 AND B.S_SEQ = '" + psSDgsn + "'                   \n");
        	//if (msKeyTitle != "전체키워드") {
        	if(!psXp.equals("") )
        	sb.append("                                 AND A.K_XP    IN (" + psXp + ")               \n");	
        	//}
            if ( !psYp.equals("") )
            sb.append("                                 AND A.K_YP    = " + psYp + "               \n");
            if ( !psZp.equals("") )
            sb.append("                                 AND A.K_ZP    = " + psZp + "               \n");
            if (siteGroupCheck == false) {
            if ( !psSGseq.equals("") ) {	                	
	      	sb.append("                                 AND B.SG_SEQ  IN (" + psSGseq + ")                   \n");			                		                
            }}
            if(!ts_type.equals("")){
            	sb.append("                                 AND B.TS_TYPE IN ("+ts_type+")                   \n");
            }
            if(!issue_check.equals("")){
                sb.append("                                 AND B.ISSUE_CHECK = '"+issue_check+"'                   \n");
            }
			if(!"".equals(storageName)) {
		        	sb.append("  									AND MS.STO_SEQ = "+storageName+" 		\n");
		     }
            if(!smnCodes.equals("")){
        		if(smnCodes.substring(0,1).equals("Y")){
        			sb.append("                                 AND B.S_SEQ IN("+smnCodes.substring(2,smnCodes.length())+")                   \n");
        		}else if(smnCodes.substring(0,1).equals("N")){
        			sb.append("                                 AND B.S_SEQ NOT IN("+smnCodes.substring(2,smnCodes.length())+")                   \n");
        		}
        	}
            
            if(!"".equals(keywordKind) && !"".equals(psKeyWord)){

            	
            	
            	String reKeyword = "";
            	for(int i =0; i<arrKeyWord.length; i++) {
            		reKeyword += " +\"" + arrKeyWord[i] + "\"";
            	}
            	if("1".equals(keywordKind)){
            		//sb.append(" AND MATCH(B.MD_TITLE) AGAINST( '"+reKeyword+"' IN BOOLEAN MODE)\n");
            		for(int i = 0; i < arrKeyWord.length; i++){
	            		sb.append("            AND B.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");				
					}
            		
	            }else if("2".equals(keywordKind)){
	            	//sb.append(" AND MATCH(B.MD_TITLE, B.MD_CONTENT) AGAINST( '"+reKeyword+"' IN BOOLEAN MODE)\n");
	            	for(int i = 0; i < arrKeyWord.length; i++){
	            		sb.append("            AND CONCAT(B.MD_TITLE, B.MD_CONTENT) LIKE '%" + arrKeyWord[i] + "%'        \n");				
					}
	            	
	            	
	            }else{
	            	for(int i = 0; i < arrKeyWord.length; i++){
	            		sb.append("            AND B.MD_SITE_NAME LIKE '%" + arrKeyWord[i] + "%'        \n");				
					}
	            }
            	
            	
            	
            	/*
	            if("1".equals(keywordKind)){
	            	for(int i = 0; i < arrKeyWord.length; i++){
						sb.append("            AND  B.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");				
					}
	            }else if("2".equals(keywordKind)){
	            	for(int i = 0; i < arrKeyWord.length; i++){
	            		sb.append("            AND CONCAT(B.MD_TITLE, B.MD_CONTENT) LIKE '%" + arrKeyWord[i] + "%'        \n");
					}	            	
	            }else{
	            	for(int i = 0; i < arrKeyWord.length; i++){
	            		sb.append("            AND B.MD_SITE_NAME LIKE '%" + arrKeyWord[i] + "%'        \n");				
					}
	            }*/
            }
            
	        //sb.append("                                 "+addQuery+"                   \n");
	        if ( !sTypeCondition.equals("") )
		        sb.append("                                 " + sTypeCondition   +                      "      \n");
	        
	        
	        
	        
	        /*
	        sb.append("  SELECT                                     \n");
	        sb.append("         COUNT(1) AS TOTAL_CNT                              \n");
	        sb.append("  FROM   (                              \n");
	        if(!"".equals(keywordKind) && !"".equals(psKeyWord)){
		        sb.append("  			SELECT AMD.MD_SEQ                              \n");
		        sb.append(" 			FROM (													\n");
	        }
	        sb.append("                           SELECT				                               \n");
            sb.append("                                MIN(DISTINCT A.MD_SEQ) AS MD_SEQ , B.MD_TITLE , B.MD_SITE_NAME, B.MD_CONTENT   \n");
            if(stName.equals("PORTAL_SEARCH_")) sb.append("                                , B.MD_CONTENT   \n");
            if(psMode.equals("SNS")){
            	sb.append("                                , B.USER_NICK , B.USER_ID   \n");
            }
            sb.append("                           FROM "+stName+"IDX A, "+stName+"META B		                   \n");
            if(psMode.equals("TWITTER")){
            	sb.append("                           "+stName+", TWEET T	                   \n");
            }
            if(!reply_check.equals("")){
            	sb.append("INNER JOIN MAP_META_SEQ C ON B.MD_SEQ = C.MD_SEQ  \n");
            }
            
            sb.append("                           WHERE A.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"                             \n");
            sb.append("                                 AND A.MD_SEQ = B.MD_SEQ                            \n");
            
            if(psMode.equals("SNS")){
            	if(!user_id.equals("")){
            		sb.append("                                 AND B.USER_ID IN ("+user_id+")                            \n");
            	}
            	if(!user_nick.equals("")){
            		sb.append("                                 AND B.USER_NICK IN ("+user_nick+")                            \n");
            	}
            }
            if("EXCEPTION_".equals(stName)){
            	if ( !psKeyWord.equals("") ){
	            	for(int i = 0; i < arrKeyWord.length; i++){
						sb.append("            AND B.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");
					}
            	}
            }
            
            if ( !psSDgsn.equals("") )
            sb.append("                                 AND B.S_SEQ = '" + psSDgsn + "'                   \n");
            if ( !psXp.equals("") )
            sb.append("                                 AND A.K_XP    IN (" + psXp + ")               \n");
            if ( !psYp.equals("") )
            sb.append("                                 AND A.K_YP    = " + psYp + "               \n");
            if ( !psZp.equals("") )
            sb.append("                                 AND A.K_ZP    = " + psZp + "               \n");
            if ( !psSGseq.equals("") )
	        sb.append("                                 AND B.SG_SEQ  IN (" + psSGseq + ")                   \n");
            if(psMode.equals("TWITTER")){
            	sb.append("                              AND B.MD_SEQ = T.MD_SEQ   \n");
            	sb.append("                              AND T.T_USER_ID IN (SELECT T_ID FROM TWITTERIAN WHERE T_USEYN='Y')   \n");
            	if( !twitterian.equals("") ){
                	sb.append("                              AND T.T_USER_ID NOT IN ('"+twitterian+"')   \n");
                }
            }
            
            if(!ts_type.equals("")){
            	sb.append("                                 AND B.TS_TYPE IN ("+ts_type+")                   \n");
            }
            if(!issue_check.equals("")){
                sb.append("                                 AND B.ISSUE_CHECK = '"+issue_check+"'                   \n");
            }
            sb.append("                                 AND B.MD_SEQ = B.MD_SEQ                   \n");
        	if(!smnCodes.equals("")){
        		if(smnCodes.substring(0,1).equals("Y")){
        			sb.append("                                 AND B.S_SEQ IN("+smnCodes.substring(2,smnCodes.length())+")                   \n");
        		}else if(smnCodes.substring(0,1).equals("N")){
        			sb.append("                                 AND B.S_SEQ NOT IN("+smnCodes.substring(2,smnCodes.length())+")                   \n");
        		}
        	}
        	
            sb.append("                                 "+addQuery+"                   \n");
            if ( !sTypeCondition.equals("") )
	        sb.append("                                 " + sTypeCondition   +                      "      \n");
            if(psMode.equals("DELIDX")){
	        	sb.append("                        GROUP BY B.MD_SEQ                                  \n");
	        }else{
	        	sb.append("                        GROUP BY B.MD_PSEQ                                  \n");
	        }
            if(!"".equals(keywordKind) && !"".equals(psKeyWord)){
            	if(stName.equals("PORTAL_SEARCH_")){
            		sb.append("  	)AMD                             	 \n");
            		sb.append("  	 WHERE AMD.MD_SEQ = AMD.MD_SEQ                          \n");
            	}else{
            		sb.append("  	)AMD                              \n");
            		sb.append("  	 WHERE AMD.MD_SEQ = AMD.MD_SEQ                          \n");
            	}
	            if("1".equals(keywordKind)){
	            	for(int i = 0; i < arrKeyWord.length; i++){
						sb.append("            AND  AMD.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");				
					}
	            }else if("2".equals(keywordKind)){
	            	for(int i = 0; i < arrKeyWord.length; i++){
	            		if(stName.equals("PORTAL_SEARCH_")){
	            			sb.append("            AND CONCAT(AMD.MD_TITLE, AMD.MD_CONTENT) LIKE '%" + arrKeyWord[i] + "%'        \n");
	            		}else{
	            			sb.append("            AND CONCAT(AMD.MD_TITLE, AMD.MD_CONTENT) LIKE '%" + arrKeyWord[i] + "%'        \n");				
	            		}
					}	            	
	            }else{
	            	for(int i = 0; i < arrKeyWord.length; i++){
	            		sb.append("            AND AMD.MD_SITE_NAME LIKE '%" + arrKeyWord[i] + "%'        \n");				
					}
	            }
            }
            sb.append("  			)RESULT                              \n");
            */
            
            
            
            
	        Log.debug(sb.toString() );
	        System.out.println(sb.toString() );
	        rs = stmt.executeQuery(sb.toString());
	        if ( rs.next() ) {
	            mBeanTotalCnt  = rs.getInt("TOTAL_CNT");
	            mBeanTotalCntSame  = rs.getInt("TOTAL_CNT2");
	            mBeanPageCnt   = mBeanTotalCnt / piRowCnt ;
	        }
	        
	        
	        //이슈데이터 건수 저장
	        sb = new StringBuffer();
	        
	        if (msKeyTitle == "전체키워드") {
		        sb.append("  SELECT COUNT(DISTINCT B.MD_SEQ) AS ISSUE_TOTAL_CNT2\n");
		        sb.append("  FROM "+stName+"IDX A,"+stName+"META B\n");
		        if(!"".equals(storageName)) {
		        	sb.append("  INNER JOIN MAP_META_STORAGE MS ON B.MD_SEQ = MS.MD_SEQ 	\n");
		        }
		        sb.append(" WHERE B.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"\n");
		        sb.append("    AND A.MD_SEQ = B.MD_SEQ\n");	
		        sb.append("    AND B.ISSUE_CHECK = 'Y'\n");
	        }else {
		        sb.append("  SELECT COUNT(DISTINCT B.MD_SEQ) AS ISSUE_TOTAL_CNT2\n");
		        sb.append("  FROM "+stName+"IDX A, "+stName+"META B\n");
		        sb.append(" WHERE B.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"\n");
		        sb.append("   AND A.MD_SEQ = B.MD_SEQ\n");	
		        sb.append("    AND B.ISSUE_CHECK = 'Y'\n");	
	        }
    	        
	        Log.debug(sb.toString() );
	        System.out.println(sb.toString() );
	        
	        rs = stmt.executeQuery(sb.toString());
	        if ( rs.next() ) {
	        	mBeanIssueTotalCnt  = rs.getInt("ISSUE_TOTAL_CNT2");
	        }
	        
	        //임시등록데이터 건수 저장
	        sb = new StringBuffer();
	        
	        if (msKeyTitle == "전체키워드") {
		        sb.append("  SELECT COUNT(DISTINCT B.MD_SEQ) AS TEMP_TOTAL_CNT2\n");
		        sb.append("  FROM "+stName+"IDX A,"+stName+"META B\n");
		        if(!"".equals(storageName)) {
		        	sb.append("  INNER JOIN MAP_META_STORAGE MS ON B.MD_SEQ = MS.MD_SEQ 	\n");
		        }
		        sb.append(" WHERE B.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"\n");
		        sb.append("   AND A.MD_SEQ = B.MD_SEQ\n");
		        sb.append("   AND B.ISSUE_CHECK = 'T' \n");	
	        }else {
		        sb.append("  SELECT COUNT(DISTINCT B.MD_SEQ) AS TEMP_TOTAL_CNT2\n");
		        sb.append("  FROM "+stName+"IDX A, "+stName+"META B\n");
		        sb.append(" WHERE B.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"\n");
		        sb.append("   AND A.MD_SEQ = B.MD_SEQ\n");	
		        sb.append("   AND B.ISSUE_CHECK = 'T' \n");	
	        }
	        
	        Log.debug(sb.toString() );
	        System.out.println(sb.toString() );
	        
	        rs = stmt.executeQuery(sb.toString());
	        if ( rs.next() ) {
	        	mBeanTempTotalCnt  = rs.getInt("TEMP_TOTAL_CNT2");
	        }
	        
	        //총 데이터 건수 저장
	        sb = new StringBuffer();
	        
	        if (msKeyTitle == "전체키워드") {
		        sb.append("  SELECT COUNT(DISTINCT B.MD_SEQ) AS ALL_TOTAL_CNT2\n");
		        sb.append("  FROM "+stName+"IDX A,"+stName+"META B\n");
		        sb.append(" WHERE B.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"\n");
		        sb.append("   AND A.MD_SEQ = B.MD_SEQ\n");
	        }else {
		        sb.append("  SELECT COUNT(DISTINCT B.MD_SEQ) AS ALL_TOTAL_CNT2\n");
		        sb.append("  FROM "+stName+"IDX A, "+stName+"META B\n");
		        sb.append(" WHERE B.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"\n");
		        sb.append("   AND A.MD_SEQ = B.MD_SEQ\n");	
	        }
	        
	        Log.debug(sb.toString() );
	        System.out.println(sb.toString() );
	        
	        rs = stmt.executeQuery(sb.toString());
	        if ( rs.next() ) {
	        	mBeanAllTotalCnt  = rs.getInt("ALL_TOTAL_CNT2");
	        }
	        
	      //출처별 전체 건수 구하기~(유사포함.)
	        /*
	        sb = new StringBuffer();
	        
	        sb.append("SELECT A.SG_SEQ 																\n");
	        sb.append("     , (SELECT SG_NAME FROM SITE_GROUP WHERE SG_SEQ = A.SG_SEQ) AS SG_NAME	\n");
	        sb.append("     , A.CNT																	\n");
	        sb.append("FROM (																		\n");
	        
	        sb.append("  SELECT A.SG_SEQ                                    \n");
	        sb.append("       , COUNT(1) AS CNT                              \n");
	        sb.append("  FROM   (                              \n");
	        if(!"".equals(keywordKind) && !"".equals(psKeyWord)){
		        sb.append(" SELECT AMD.MD_SEQ , AMD.SG_SEQ			\n");
		        sb.append(" FROM(									\n");
	        }
	        sb.append("                           SELECT				                               \n");
            sb.append("                                MIN(DISTINCT A.MD_SEQ) AS MD_SEQ                     \n");
            sb.append("                                , MIN(B.SG_SEQ) AS SG_SEQ  , MD_TITLE , MD_SITE_NAME, MD_CONTENT   \n");
            sb.append("                           FROM "+stName+"IDX A		                   					\n");
            sb.append("                              , "+stName+"META B		                   					\n");
            
            
            if(!reply_check.equals("")){
            	sb.append("INNER JOIN MAP_META_SEQ C ON B.MD_SEQ = C.MD_SEQ  \n");
            }
            
            
            sb.append("                           WHERE A.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"                             \n");
            sb.append("                                 AND A.MD_SEQ = B.MD_SEQ                            \n");
            if("EXCEPTION_".equals(stName)){
            	if ( !psKeyWord.equals("") ){
	            	for(int i = 0; i < arrKeyWord.length; i++){
						sb.append("            AND B.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");
					}
            	}
            }	            
            if ( !psSDgsn.equals("") )
            sb.append("                                 AND B.S_SEQ = '" + psSDgsn + "'                   \n");
            if ( !psXp.equals("") )
            sb.append("                                 AND A.K_XP    IN (" + psXp + ")               \n");
            if ( !psYp.equals("") )
            sb.append("                                 AND A.K_YP    = " + psYp + "               \n");
            if ( !psZp.equals("") )
            sb.append("                                 AND A.K_ZP    = " + psZp + "               \n");
            if ( !psSGseq.equals("") )
	        sb.append("                                 AND B.SG_SEQ  IN (" + psSGseq + ")                   \n");
            if(!ts_type.equals("")){
            	sb.append("                                 AND B.TS_TYPE IN ("+ts_type+")                   \n");
            }
            sb.append("                                 AND B.MD_SEQ = B.MD_SEQ                   \n");
            sb.append("                                 "+addQuery+"                   \n");
            if ( !sTypeCondition.equals("") )
	        sb.append("                                 " + sTypeCondition   +                      "      \n");     
        	sb.append("                        GROUP BY B.MD_SEQ                                  \n");
        	
        	if(!"".equals(keywordKind) && !"".equals(psKeyWord) ){
        		sb.append("  	)AMD                               \n");
        		sb.append("  	 WHERE  AMD.MD_SEQ = AMD.MD_SEQ                            \n");
	            if("1".equals(keywordKind)){
	            	for(int i = 0; i < arrKeyWord.length; i++){
						sb.append("            AND  AMD.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");				
					}
	            }else if("2".equals(keywordKind)){
	            	for(int i = 0; i < arrKeyWord.length; i++){
            			sb.append("            AND CONCAT(AMD.MD_TITLE, AMD.MD_CONTENT) LIKE '%" + arrKeyWord[i] + "%'        \n");				
					}	            	
	            }else{
	            	for(int i = 0; i < arrKeyWord.length; i++){
	            		sb.append("            AND AMD.MD_SITE_NAME LIKE '%" + arrKeyWord[i] + "%'        \n");				
					}
	            }
            }
        	
            sb.append("  			)A GROUP BY A.SG_SEQ    )A                         \n");
            sb.append("             , IC_S_RELATION B WHERE A.SG_SEQ = B.S_SEQ ORDER BY B.IC_ORDER		\n");
    
    
	        Log.debug(sb.toString() );
	        rs = stmt.executeQuery(sb.toString());
	        
	        int totCnt = 0;
	        while ( rs.next() ) {
	        	totCnt += rs.getInt("CNT");
	        	
	            arrSourceCnt.add(rs.getString("SG_NAME") + "," + rs.getString("CNT")); 
	        }
	        arrSourceCnt.add("전체" + "," + String.valueOf(totCnt));
	        */
	
	        rs.close();
	        rs = null;
	        sb = null;
	
	        String sXpValue = "";
	        String sYpValue = "";
	        String sZpValue = "";
	
	        if ( !(psXp + psYp + psZp).equals("") ) {
	
	            sb = new StringBuffer();	
	            sb.append("SELECT MAX(MAIN) MAIN , MAX(MIDLE) MIDLE, MAX(BOTTOM) BOTTOM     \n");
	            sb.append("  FROM (                                                         \n");
	            sb.append("        SELECT K_VALUE AS  MAIN , '' AS MIDLE , '' AS BOTTOM     \n");
	            sb.append("          FROM KEYWORD                                           \n");
	            sb.append("         WHERE K_XP in ( " + psXp + " )                               \n");
	            sb.append("           AND K_YP = 0                                          \n");
	            sb.append("           AND K_ZP = 0                                          \n");
	            sb.append("           AND K_TYPE < 3                                        \n");
	            sb.append("  		  AND K_USEYN='Y'                                              \n");
	            if ( !psYp.equals("") ) {
	                sb.append("        UNION                                                    \n");
	                sb.append("        SELECT '' , K_VALUE MAIN , ''                            \n");
	                sb.append("          FROM KEYWORD                                           \n");
	                sb.append("         WHERE K_XP = " + psXp + "                               \n");
	                sb.append("           AND K_YP = " + psYp + "                               \n");
	                sb.append("           AND K_ZP = 0                                          \n");
	                sb.append("           AND K_TYPE < 3                                        \n");
	                sb.append("  		  AND K_USEYN='Y'                                              \n");
	            }
	
	            if ( !psZp.equals("") ) {
	                sb.append("        UNION                                                    \n");
	                sb.append("        SELECT '' , '' , K_VALUE MAIN                            \n");
	                sb.append("          FROM KEYWORD                                           \n");
	                sb.append("         WHERE K_XP = " + psXp + "                               \n");
	                sb.append("           AND K_YP = " + psYp + "                               \n");
	                sb.append("           AND K_ZP = " + psZp + "                               \n");
	                sb.append("           AND K_TYPE < 3                                        \n");
	                sb.append("  		  AND K_USEYN='Y'                                              \n");
	            }
	            sb.append("       )TEMP                                                     \n");
	
	            Log.debug(sb.toString() );
	            rs = stmt.executeQuery(sb.toString());
	
	            if ( rs.next() ) {
	                sXpValue = rs.getString("MAIN")   == null ? "" : rs.getString("MAIN")   ;
	                sYpValue = rs.getString("MIDLE")  == null ? "" : rs.getString("MIDLE")  ;
	                sZpValue = rs.getString("BOTTOM") == null ? "" : rs.getString("BOTTOM") ;
	            }
	
	            msKeyTitle = sXpValue;
	            if ( !sYpValue.equals("") ) {
	                msKeyTitle += "-" + sYpValue;
	            }
	
	            if ( !sZpValue.equals("") ) {
	                msKeyTitle += "-" + sZpValue;
	            }
	            
				
				  
	            
	            //키워드 펑션 생성 
	            if(stName.equals("")){
	            	if(psXp.length()>0){
		            	if(psXp.split(",").length>1)
		            	{
		            		keywordFunction = "FN_GET_KEYWORD(A.MD_SEQ)";
		            	}else{
		            		keywordFunction = "FN_GET_KEYWORD2(A.MD_SEQ,"+psXp+")";
		            	}
		            }
		            if(psYp.length()>0) keywordFunction = "FN_GET_KEYWORD3(A.MD_SEQ,"+psXp+","+psYp+")";
		            if(psZp.length()>0) keywordFunction = "FN_GET_KEYWORD4(A.MD_SEQ,"+psXp+","+psYp+","+psZp+")";
	            }else{
	            	keywordFunction = "(SELECT EK_VALUE FROM EXCEPTION_KEYWORD WHERE EK_SEQ = A.EK_SEQ LIMIT 1)";
	            }
	           
	        } else {
	            msKeyTitle = "전체키워드";
	            keywordFunction = "FN_GET_KEYWORD(A.MD_SEQ)";
	        }
	        if(psMode.equals("DELIDX")){
	        	msKeyTitle = "휴지통";
	        }
	        // 좌측 메뉴 키워드 그룹별 키워드 강조
	        String KeywordGroup="";
	        if(!psXp.equals("")){
	      	 	sb = null;
	 	        sb = new StringBuffer();
	            
		        if(psYp.equals("") && psZp.equals("") ){
		        	sb.append("  SELECT *                                                \n");
		        	sb.append("  FROM KEYWORD                                                 \n");
		        	sb.append("  WHERE K_XP in ( " + psXp + " )                                                 \n");
		        	sb.append("  AND K_YP > 0                                                 \n");
		        	sb.append("  AND K_ZP > 0                                                \n");
		        	sb.append("  AND K_TYPE < 3                                                \n");
		        	sb.append("  AND K_USEYN='Y'                                              \n");		        	
			        rs = stmt.executeQuery(sb.toString());
			        while(rs.next()){
			        	if(KeywordGroup.equals("")){
			        		KeywordGroup = rs.getString("K_VALUE");
			        	}else{
			        		KeywordGroup += " "+rs.getString("K_VALUE");
			        	}		        	
			        }            
		        }else if(!psYp.equals("") && psZp.equals("") ){
		        	sb.append("  SELECT *                                                \n");
		        	sb.append("  FROM KEYWORD                                                 \n");
		        	sb.append("  WHERE K_XP = " + psXp + "                                                 \n");
		        	sb.append("  AND K_YP   = " + psYp + "                                                \n");
		        	sb.append("  AND K_ZP > 0                                                \n");
		        	sb.append("  AND K_TYPE < 3                                                \n");
		        	sb.append("  AND K_USEYN='Y'                                              \n");       	
			        rs = stmt.executeQuery(sb.toString());
			        while(rs.next()){
			        	if(KeywordGroup.equals("")){
			        		KeywordGroup = rs.getString("K_VALUE");
			        	}else{
			        		KeywordGroup += " "+rs.getString("K_VALUE");
			        	}		        	
			        }
	            }	   
		    }

            sb = new StringBuffer();
	        String Keyword = "";
			if(psXp.length()>2){ msKeyTitle = "전체키워드"; }			

	        //mBeanTotalCnt = 10;
	        //게시물 리스트를 구한다.
	        if ( mBeanTotalCnt > 0 ) {
	            sb = new StringBuffer();
	            
	            int liststart;
	         	int listend;
	         	         	
	         	if (piNowpage == 0) 
	         		piNowpage = 1;
	         	else {
	         		liststart = (piNowpage-1) * piRowCnt;
	         		listend =    piRowCnt;
	         		

	         		sb.append("SELECT A.*													\n");
	         		sb.append("     , "+keywordFunction+" AS K_VALUE						\n");
	         		sb.append("     , FN_GET_COMFIRM("+(psMseq.equals("") ? "null" : psMseq) +",A.MD_SEQ) AS COMFIRM\n");
	         		sb.append(", IFNULL((SELECT MD_REPLY_TOTAL_CNT FROM META_REPLY_CNT WHERE MD_SEQ = A.MD_SEQ LIMIT 1),0) AS REPLY_CNT\n");
	         		sb.append(", (SELECT IF(COUNT(*)>0,'Y','N')AS IS_STORAGE  FROM MAP_META_STORAGE WHERE MD_SEQ = A.MD_SEQ ) AS IS_STORAGE\n");
	         		sb.append("FROM ( 														\n");
	         		
	         		
	         		sb.append("SELECT '' AS T_FOLLOWERS,									\n");																		
	         		sb.append("       '' AS K_VALUE2,										\n");
	         		sb.append("       A.MD_SEQ,             								\n");                       
	         		sb.append("       A.SG_SEQ,                   							\n");                        
	         		sb.append("       A.S_SEQ        ,                 						\n");                   
	         		sb.append("       A.MD_SITE_NAME       ,                				\n");                    
	         		sb.append("       A.MD_MENU_NAME       ,                     			\n");               
	         		sb.append("       A.MD_TYPE       ,                               		\n");     
	         		sb.append("       DATE_FORMAT(A.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE,\n"); 						        
	         		sb.append("       A.MD_SAME_COUNT  ,                                    \n");
	         		sb.append("       A.MD_PSEQ        ,                                    \n");
	         		sb.append("       A.MD_TITLE       ,                                    \n");
	         		sb.append("       A.MD_URL         ,                                    \n");
	         		sb.append("       A.JSON_DATA         ,                                    \n");
	         		
	         		if(!stName.equals("EXCEPTION_")){
	         			sb.append("      '' AS MD_IMPORTANT   ,                                    \n");
		         		sb.append("      '' AS PORTAL_CHECK   ,                                    \n");
		            }
	         		sb.append("       A.ISSUE_CHECK,                                 \n");
	         		
	         		//if(addQuery.length()>0){
			            sb.append("                        '' AS I_DELDATE,  	\n");
						sb.append("                        '' AS M_SEQ,   		\n");
						sb.append("                        '' AS M_NAME, 		\n");            
	         		//}
	         		
	         		
	         		sb.append("      A.MD_IMG  ,        \n");                                   
	         		sb.append("      A.MD_CONTENT  \n");
	         		
	         		if(!stName.equals("") && !stName.equals("PORTAL_SEARCH_")){
		            	sb.append("                                ,A.EK_SEQ   \n");
		            }
	         		

					if(stName.equals("EXCEPTION_")){ 
					sb.append(" FROM EXCEPTION_META A\n"); }
					 else {
	         		sb.append(" FROM META A\n");
	         		}
					
	         		sb.append("    , (\n");
	         		
	         		
	         		if (msKeyTitle == "전체키워드") {
	         			sb.append("       SELECT	MIN(B.MD_SEQ) AS MD_SEQ\n");			                               
		         		sb.append("  FROM "+stName+"IDX A,"+stName+"META B\n");
				        if(!"".equals(storageName)) {
				        	sb.append("  INNER JOIN MAP_META_STORAGE MS ON B.MD_SEQ = MS.MD_SEQ 	\n");
				        }
		    	        sb.append(" WHERE B.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"\n");
		    	        sb.append("   AND A.MD_SEQ = B.MD_SEQ\n");
	         		}else {
	         			sb.append("       SELECT	MIN(A.MD_SEQ) AS MD_SEQ\n");			                               
		         		sb.append("  FROM "+stName+"IDX A, "+stName+"META B\n");
		    	        sb.append(" WHERE A.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"\n");
		    	        sb.append("   AND A.MD_SEQ = B.MD_SEQ\n");	
	         		}
	         			
	         		
	            	
	    	        
	    	        if ( !psSDgsn.equals("") )
	                sb.append("                                 AND B.S_SEQ = '" + psSDgsn + "'                   \n");	
	            	//if (msKeyTitle != "전체키워드") {
	            	if(!psXp.equals("") )
	            	sb.append("                                 AND A.K_XP    IN (" + psXp + ")               \n");	
	            	//}
	                if ( !psYp.equals("") )
	                sb.append("                                 AND A.K_YP    = " + psYp + "               \n");
	                if ( !psZp.equals("") )
	                sb.append("                                 AND A.K_ZP    = " + psZp + "               \n");
	                if (siteGroupCheck == false) {
	                if ( !psSGseq.equals("") ) {	                	
			      	sb.append("                                 AND B.SG_SEQ  IN (" + psSGseq + ")                   \n");			                		                
	                }}
	                if(!ts_type.equals("")){
	                	sb.append("                                 AND B.TS_TYPE IN ("+ts_type+")                   \n");
	                }
	                if(!issue_check.equals("")){
	                    sb.append("                                 AND B.ISSUE_CHECK = '"+issue_check+"'                   \n");
	                }
	    			if(!"".equals(storageName)) {
			        	sb.append("  									AND MS.STO_SEQ = "+storageName+" 		\n");
	    			}
	                if(!smnCodes.equals("")){
	            		if(smnCodes.substring(0,1).equals("Y")){
	            			sb.append("                                 AND B.S_SEQ IN("+smnCodes.substring(2,smnCodes.length())+")                   \n");
	            		}else if(smnCodes.substring(0,1).equals("N")){
	            			sb.append("                                 AND B.S_SEQ NOT IN("+smnCodes.substring(2,smnCodes.length())+")                   \n");
	            		}
	            	}
	                
	                if(!"".equals(keywordKind) && !"".equals(psKeyWord)){

	                	
	                	String reKeyword = "";
	                	for(int i =0; i<arrKeyWord.length; i++) {
	                		reKeyword += " +\"" + arrKeyWord[i] + "\"";
	                	}
	                	if("1".equals(keywordKind)){
	                		//sb.append(" AND MATCH(B.MD_TITLE) AGAINST( '"+reKeyword+"' IN BOOLEAN MODE)\n");
	                		for(int i = 0; i < arrKeyWord.length; i++){
	    	            		sb.append("            AND B.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");				
	    					}
	                		
	    	            }else if("2".equals(keywordKind)){
	    	            	//sb.append(" AND MATCH(B.MD_TITLE, B.MD_CONTENT) AGAINST( '"+reKeyword+"' IN BOOLEAN MODE)\n");
	    	            	for(int i = 0; i < arrKeyWord.length; i++){
	    	            		sb.append("            AND CONCAT(B.MD_TITLE, B.MD_CONTENT) LIKE '%" + arrKeyWord[i] + "%'        \n");				
	    					}
	    	            	
	    	            	
	    	            }else{
	    	            	for(int i = 0; i < arrKeyWord.length; i++){
	    	            		sb.append("            AND B.MD_SITE_NAME LIKE '%" + arrKeyWord[i] + "%'        \n");				
	    					}
	    	            }
	                	
	                	/*
	    	            if("1".equals(keywordKind)){
	    	            	for(int i = 0; i < arrKeyWord.length; i++){
	    						sb.append("            AND  B.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");				
	    					}
	    	            }else if("2".equals(keywordKind)){
	    	            	for(int i = 0; i < arrKeyWord.length; i++){
	    	            		sb.append("            AND CONCAT(B.MD_TITLE, B.MD_CONTENT) LIKE '%" + arrKeyWord[i] + "%'        \n");
	    					}	            	
	    	            }else{
	    	            	for(int i = 0; i < arrKeyWord.length; i++){
	    	            		sb.append("            AND B.MD_SITE_NAME LIKE '%" + arrKeyWord[i] + "%'        \n");				
	    					}
	    	            }
	    	            */
	                }
	                
	    	        //sb.append("                                 "+addQuery+"                   \n");
	    	        if ( !sTypeCondition.equals("") )
	    		        sb.append("                                 " + sTypeCondition   +                      "      \n");
	         		if(psMode.equals("DELIDX")){
			        	sb.append("                        GROUP BY B.MD_SEQ                                  \n");
			        }else{
			        	sb.append("                        GROUP BY B.MD_PSEQ                                  \n");
			        }
	         		sb.append("         	     ORDER BY " + psOrder + "                                                 \n");
		            sb.append("         		 LIMIT "+liststart+","+listend+"                                 \n");
	         		sb.append("      ) B\n");
	         		sb.append("WHERE A.MD_SEQ = B.MD_SEQ\n");
	         		sb.append(")A\n");
	         		
	         		
	         		/*
	         		sb.append("SELECT A.*    											\n");
	         		if(!stName.equals("PORTAL_SEARCH_")) sb.append("     , "+keywordFunction+" AS K_VALUE  					\n");
	         		sb.append("     , FN_GET_COMFIRM("+(psMseq.equals("") ? "null" : psMseq) +",A.MD_SEQ) AS COMFIRM  	\n");
	         		sb.append("		, '' AS T_FOLLOWERS																									\n");
	         		if(!stName.equals("EXCEPTION_")) sb.append(" 	, (SELECT GROUP_CONCAT(DISTINCT C.K_VALUE) FROM "+stName+"IDX B, "+stName+"KEYWORD C WHERE B.MD_SEQ = A.MD_SEQ AND B.K_XP  = C.K_XP AND B.K_YP  = C.K_YP AND B.K_ZP  = C.K_ZP AND C.K_USEYN='Y' AND C.K_TYPE = 2) AS K_VALUE2 																																			  \n");
	         		sb.append("  FROM (  												\n");  
		            sb.append("                 SELECT 					                                     \n");
		            sb.append("                        A.MD_SEQ         ,                                    \n");
		            sb.append("                        A.SG_SEQ,                                           \n");
		            sb.append("                        A.S_SEQ        ,                                    \n");
		            sb.append("                        A.MD_SITE_NAME       ,                                    \n");	
		            sb.append("                        A.MD_MENU_NAME       ,                                    \n");		            
		            sb.append("                        A.MD_TYPE       ,                                    \n");
		            sb.append("                        DATE_FORMAT(A.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE, 						        \n");
		            sb.append("                        A.MD_SAME_COUNT  ,                                    \n");
		            sb.append("                        A.MD_PSEQ        ,                                    \n");
		            sb.append("                        A.MD_TITLE       ,                                    \n");
		            sb.append("                        A.MD_URL         ,                                    \n");
		            if(!stName.equals("EXCEPTION_")){
		            	sb.append("                        A.MD_IMPORTANT   ,                                    \n");
		            	sb.append("                        A.PORTAL_CHECK   ,                                    \n");
		            }
		            if(stName.equals("PORTAL_SEARCH_")){
		            	sb.append("                        A.ISSUE_CHECK,                   \n");
		            }else{
		            	sb.append("                        A.ISSUE_CHECK,                                 \n");
		            }
		            if(psMode.equals("SNS")){
		            	sb.append("                        A.USER_ID,                                 \n");
		            	sb.append("                        A.USER_NICK,                                 \n");
		            }
		            if(addQuery.length()>0){
		            sb.append("                        '' AS I_DELDATE,  \n");
					sb.append("                        '' AS M_SEQ,   \n");
					sb.append("                        '' AS M_NAME, \n");            
		            }
		            sb.append("                        A.MD_IMG  ,                                           \n");
		            if(stName.equals("PORTAL_SEARCH_")){
		            	sb.append("                        A.MD_CONTENT                                             \n");
		            }else{
		            	sb.append("                        A.MD_CONTENT                                             \n");
		            }
		            if(!stName.equals("") && !stName.equals("PORTAL_SEARCH_")){
		            	sb.append("                                ,A.EK_SEQ   \n");
		            }
		            sb.append("                   FROM (                                                    \n");
		            sb.append("                           SELECT				                               \n");
		            sb.append("                                MIN(DISTINCT A.MD_SEQ) AS MD_SEQ ,B.S_SEQ ,B.MD_SITE_NAME ,B.MD_MENU_NAME ,B.MD_TYPE ,B.MD_DATE ,B.MD_IMG  \n");
		            sb.append("                                ,B.MD_SAME_COUNT ,B.MD_PSEQ ,B.MD_TITLE ,B.MD_URL , SG_SEQ, B.ISSUE_CHECK , B.MD_CONTENT  \n");
		            if(stName.equals("PORTAL_SEARCH_")){
		            	sb.append("                                , B.MD_CONTENT   \n");
		            }
		            if(!stName.equals("EXCEPTION_")){
		            	sb.append("                                , '' AS MD_IMPORTANT   \n");
		            	sb.append("                                , '' AS PORTAL_CHECK   \n");
		            }
		            if(psMode.equals("SNS")){
		            	sb.append("                                , B.USER_NICK , B.USER_ID   \n");
		            }
		            if(!stName.equals("") && !stName.equals("PORTAL_SEARCH_")){
		            	sb.append("                                ,B.EK_SEQ   \n");
		            }

		            sb.append("                           FROM "+stName+"IDX A 		                   					\n");
		            sb.append("                              , "+stName+"META B		                   					\n");
		            
		            
		            if(psMode.equals("TWITTER")){
		            	sb.append("                           "+stName+", TWEET T		                   \n");
		            }
		            if(!reply_check.equals("")){
		            	sb.append("INNER JOIN MAP_META_SEQ C ON B.MD_SEQ = C.MD_SEQ \n");
		            }
		            
		            sb.append("                           WHERE A.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"                            \n");
		            sb.append("                                 AND A.MD_SEQ = B.MD_SEQ                            \n");
		            if(psMode.equals("SNS")){
		            	if(!user_id.equals("")){
		            		sb.append("                                 AND B.USER_ID IN ("+user_id+")                            \n");
		            	}
		            	if(!user_nick.equals("")){
		            		sb.append("                                 AND B.USER_NICK IN ("+user_nick+")                            \n");
		            	}
		            }
		            if("EXCEPTION_".equals(stName)){
		            	if ( !psKeyWord.equals("") ){
			            	for(int i = 0; i < arrKeyWord.length; i++){
								sb.append("            AND B.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");
							}
		            	}
		            }
		            if ( !psSDgsn.equals("") )
		            sb.append("                                 AND B.S_SEQ = '" + psSDgsn + "'                   \n");
		            if ( !psXp.equals("") )
		            sb.append("                                 AND A.K_XP   IN ( " + psXp + " )               \n");
		            if ( !psYp.equals("") )
		            sb.append("                                 AND A.K_YP    = " + psYp + "               \n");
		            if ( !psZp.equals("") )
		            sb.append("                                 AND A.K_ZP    = " + psZp + "               \n");
		            if ( !psSGseq.equals("") )
			        sb.append("                                 AND B.SG_SEQ IN (" + psSGseq + ")                   \n");
		            if(psMode.equals("TWITTER")){
		            	sb.append("                              AND B.MD_SEQ = T.MD_SEQ   \n");
		            	sb.append("                              AND T.T_USER_ID IN (SELECT T_ID FROM TWITTERIAN WHERE T_USEYN='Y')   \n");	
		            }
		            if( !twitterian.equals("") ){
		            	sb.append("                              AND T.T_USER_ID NOT IN ('"+twitterian+"')   \n");
		            }
		            if(!issue_check.equals("")){
		                sb.append("                                 AND B.ISSUE_CHECK = '"+issue_check+"'                   \n");
		            }
		            if(!ts_type.equals("")){
		            	sb.append("                                 AND B.TS_TYPE IN ("+ts_type+")                   \n");
		            }
		            sb.append("                                 AND B.MD_SEQ = B.MD_SEQ                   \n");
		            if(!smnCodes.equals("")){
		        		if(smnCodes.substring(0,1).equals("Y")){
		        			sb.append("                                 AND B.S_SEQ IN("+smnCodes.substring(2,smnCodes.length())+")                   \n");
		        		}else if(smnCodes.substring(0,1).equals("N")){
		        			sb.append("                                 AND B.S_SEQ NOT IN("+smnCodes.substring(2,smnCodes.length())+")                   \n");
		        		}
		        	}
		            sb.append("                                 "+addQuery+"                   \n");
		            if ( !sTypeCondition.equals("") )
		            sb.append("                            " + sTypeCondition   +                      "      \n");
		            
		            if(psMode.equals("DELIDX")){
			        	sb.append("                        GROUP BY B.MD_SEQ                                  \n");
			        }else{
			        	sb.append("                        GROUP BY B.MD_PSEQ                                  \n");
			        }
		            
		            sb.append("                        ) A                                                \n");
		            if(stName.equals("PORTAL_SEARCH_")){
	            		sb.append("  	 WHERE  A.MD_SEQ = A.MD_SEQ                            \n");
		            }else{
		            	sb.append("  	 WHERE  A.MD_SEQ = A.MD_SEQ                            \n");
		            }
		            if(!"".equals(keywordKind) && !"".equals(psKeyWord) ){
		            	if("1".equals(keywordKind)){
		            		for(int i = 0; i < arrKeyWord.length; i++){
		            			sb.append("            AND A.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");
							}
		            	}else if("2".equals(keywordKind)){
		            		for(int i = 0; i < arrKeyWord.length; i++){
		            			if(stName.equals("PORTAL_SEARCH_")){
		            				sb.append("         AND   CONCAT(A.MD_TITLE, A.MD_CONTENT) LIKE '%" + arrKeyWord[i] + "%'        \n");
			            		}else{
			            			sb.append("         AND   CONCAT(A.MD_TITLE, A.MD_CONTENT) LIKE '%" + arrKeyWord[i] + "%'        \n");
			            		}
							}
		            	}else{
		            		for(int i = 0; i < arrKeyWord.length; i++){
								sb.append("            AND A.MD_SITE_NAME LIKE '%" + arrKeyWord[i] + "%'        \n");
							}
		            	}
		            }
		            sb.append("         	     ORDER BY " + psOrder + "                                                 \n");
		            sb.append("         		 LIMIT "+liststart+","+listend+"                                 \n");
		            
		            
		            sb.append(" )A      \n");
		            */
		            
		            
	         	}
	         	Log.debug(sb.toString() );
	         	rs = stmt.executeQuery(sb.toString());
          	          
	            while( rs.next() ) {
	                mBeanDataCnt++;
	
	                String sD_html  = "";
	
	                if ( rs.getCharacterStream("MD_CONTENT") != null ) {
	
	                    StringBuffer output = new StringBuffer();
	                    Reader       input  = rs.getCharacterStream("MD_CONTENT");
	                    char[]       buffer = new char[2048];
	
	                    int byteRead;
	                    while( (byteRead=input.read(buffer,0,1024)) != -1  ){
	                       output.append(buffer,0,byteRead);
	                    }
	                    input.close();
	
	                    sD_html = output.toString();
	
	                }
	              
	                if(psMode.equals("DELIDX")){
	                	
						mBean  = new MetaBean();
						mBean.setMd_seq(rs.getString("MD_SEQ"));
						mBean.setS_seq(rs.getString("S_SEQ"));
						mBean.setSg_seq("SG_SEQ");
						mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
						mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
						mBean.setMd_type(rs.getString("MD_TYPE"));
						mBean.setMd_date(rs.getString("MD_DATE"));
						mBean.setMd_pseq(rs.getString("MD_PSEQ"));
						mBean.setMd_title(rs.getString("MD_TITLE"));
						mBean.setMd_url(rs.getString("MD_URL"));
						mBean.setMd_content(rs.getString("MD_CONTENT"));
						if(!stName.equals("PORTAL_SEARCH_")) mBean.setK_value(rs.getString("K_VALUE"));
						mBean.setK_value2(rs.getString("K_VALUE2"));
						mBean.setMd_same_count(rs.getString("MD_SAME_COUNT")); 
						mBean.setM_seq(rs.getString("M_SEQ"));
						mBean.setI_deldate(rs.getString("I_DELDATE"));
						mBean.setM_name(rs.getString("M_NAME"));
						mBean.setMd_comfirm(rs.getString("COMFIRM"));
						mBean.setT_followers(rs.getString("T_FOLLOWERS"));
						mBean.setMd_reply_count(rs.getString("REPLY_CNT"));
						mBean.setIs_storage(rs.getString("IS_STORAGE"));
						if(!stName.equals("EXCEPTION_")){
							mBean.setMd_important(rs.getString("MD_IMPORTANT"));
							mBean.setPortal_check(rs.getString("PORTAL_CHECK"));
						}
						//mBean.setSga_name(rs.getString("SGA_NAME"));
						//mBean.setReply_cnt(rs.getString("REPLY_CNT"));
						arrlist.add(mBean);					
	                	
      		        }else{
      		        	mBean  = new MetaBean();
						mBean.setMd_seq(rs.getString("MD_SEQ"));
						mBean.setS_seq(rs.getString("S_SEQ"));
						mBean.setSg_seq("SG_SEQ");
						mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
						mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
						mBean.setMd_type(rs.getString("MD_TYPE"));
						mBean.setMd_date(rs.getString("MD_DATE"));
						mBean.setMd_pseq(rs.getString("MD_PSEQ"));
						mBean.setMd_title(rs.getString("MD_TITLE"));
						mBean.setMd_url(rs.getString("MD_URL"));
						mBean.setMd_content(rs.getString("MD_CONTENT"));
						mBean.setJson_data(rs.getString("JSON_DATA"));
						if(stName.equals("PORTAL_SEARCH_")){
							mBean.setK_value(rs.getString("K_VALUE2"));
						}else{
							mBean.setK_value(rs.getString("K_VALUE"));
						}
						if(!stName.equals("EXCEPTION_")) mBean.setK_value2(rs.getString("K_VALUE2"));
						mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));
						mBean.setIssue_check(rs.getString("ISSUE_CHECK"));
						mBean.setMd_comfirm(rs.getString("COMFIRM"));
						mBean.setT_followers(rs.getString("T_FOLLOWERS"));
						mBean.setMd_reply_count(rs.getString("REPLY_CNT"));
						mBean.setIs_storage(rs.getString("IS_STORAGE"));
						if(!stName.equals("EXCEPTION_")){
							mBean.setMd_important(rs.getString("MD_IMPORTANT"));
							mBean.setPortal_check(rs.getString("PORTAL_CHECK"));
						}
						//mBean.setSga_name(rs.getString("SGA_NAME"));
						//mBean.setReply_cnt(rs.getString("REPLY_CNT"));
						arrlist.add(mBean);			
      		        }
	
	            } // end while
	        } // end if
	
	    } catch (SQLException ex ) {
	        Log.writeExpt(ex, sb.toString() );
	
	    } catch (Exception ex ) {
	        Log.writeExpt(ex);
	
	    } finally {
	        try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	        try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	        try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	    }
	
	    return arrlist;
	}
	
	
	public ArrayList getExcelSearchList(     
								            String psXp      ,
								            String psYp      ,
								            String psZp      ,
								            String psSGseq   ,
								            String psS_seq   ,
								            String psDateFrom,
								            String psDateTo  ,
								            String psKeyWord ,
								            String psType    ,
								            String psOrder   ,
								            String ts_type,
								            String stime,
								            String etime,
								            String stName,
								            int totalCnt
										   )


	{	
		ArrayList arrlist = new ArrayList();
	
		try {
			getMaxMinNo( psDateFrom, psDateTo, stime, etime, stName );
	        String MinMtNo= msMinNo;
	        String MaxMtNo= msMaxNo;
	        String sTypeCondition = convTypeCodeToSQL(psType);
	        
	        String[] arrKeyWord = null;
			arrKeyWord = psKeyWord.split("&");
		   
	        dbconn = new DBconn();
	        dbconn.getDBCPConnection();
	
	        stmt = dbconn.createStatement();	     

            sb = new StringBuffer();
	        String Keyword = "";
	        //게시물 리스트를 구한다.
	        if ( totalCnt > 0 ) {
	            sb = new StringBuffer();
	            
	            int liststart;
	         	int listend;
  
	         	sb.append("				SELECT A.*											\n");
	         	//sb.append("                   , (SELECT GROUP_CONCAT(SGA_NAME) FROM SGA_S_RELATION B, SITE_GROUP_ALLIENCE C WHERE  A.S_SEQ = B.S_SEQ AND B.SGA_SEQ = C.SGA_SEQ) SGA_NAME\n");
	         	sb.append(" 				   , IF(A.S_SEQ=2196 OR A.S_SEQ=2199 OR A.S_SEQ=3883, ( SELECT GROUP_CONCAT(SGA_NAME) FROM SGA_S_RELATION B, SITE_GROUP_ALLIENCE C WHERE B.SGA_SEQ = C.SGA_SEQ AND B.S_NAME = SUBSTRING(A.md_site_name,2,LENGTH(A.md_site_name))), (SELECT GROUP_CONCAT(SGA_NAME) FROM SGA_S_RELATION B, SITE_GROUP_ALLIENCE C WHERE  A.S_SEQ = B.S_SEQ AND B.SGA_SEQ = C.SGA_SEQ)) SGA_NAME \n");
	         	sb.append("             	FROM(											\n");
	            sb.append("                           SELECT				                               \n");
	            sb.append("                                MIN(DISTINCT A.MD_SEQ) AS MD_SEQ ,B.S_SEQ ,B.MD_SITE_NAME ,B.MD_MENU_NAME ,B.MD_TYPE ,B.MD_DATE ,B.MD_IMG		  \n");
	            sb.append("                                ,B.MD_SAME_COUNT ,B.MD_PSEQ ,B.MD_TITLE ,B.MD_URL , B.SG_SEQ, B.ISSUE_CHECK, '' AS I_DELDATE, '' AS M_SEQ, '' AS MD_IMPORTANT, '' AS PORTAL_CHECK		    \n");
	            if(!stName.equals("")){
	            	if(!stName.equals("PORTAL_SEARCH_")){
	            		sb.append("                                ,(SELECT * FROM EXCEPTION_KEYWORD WHERE EK_SEQ = B.EK_SEQ LIMIT 1) AS EK_NAME   \n");
	            	}else{
	            		sb.append("                                ,B.PORTAL_URL   \n");
	            	}
	            }
	            sb.append("                           FROM "+stName+"IDX A, "+stName+"META B		                   \n");
	            sb.append("                           WHERE A.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"                            \n");
	            sb.append("                                 AND A.MD_SEQ = B.MD_SEQ                            \n");
	            if ( !psKeyWord.equals("") ){
	            	for(int i = 0; i < arrKeyWord.length; i++){
						sb.append("            AND B.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");
					}
	            }		            
	            if ( !psS_seq.equals("") )
	            sb.append("                                 AND B.S_SEQ = '" + psS_seq + "'                   \n");
	            if ( !psXp.equals("") )
	            sb.append("                                 AND A.K_XP   IN ( " + psXp + " )               \n");
	            if ( !psYp.equals("") )
	            sb.append("                                 AND A.K_YP    = " + psYp + "               \n");
	            if ( !psZp.equals("") )
	            sb.append("                                 AND A.K_ZP    = " + psZp + "               \n");
	            if ( !psSGseq.equals("") )
		        sb.append("                                 AND B.SG_SEQ IN (" + psSGseq + ")                   \n");
	            if(!ts_type.equals("")){
	            	sb.append("                                 AND B.TS_TYPE IN ("+ts_type+")                   \n");
	            }
	            sb.append("                                 AND B.MD_SEQ = B.MD_SEQ                   \n");
	            
	            if ( !sTypeCondition.equals("") )
	            sb.append("                            " + sTypeCondition   +                      "      \n");
	            
	        	sb.append("                        GROUP BY B.MD_PSEQ                                  \n");
	        	sb.append("						)A 															\n");
	            sb.append("         	     ORDER BY " + psOrder + "                                                 \n");
	            if(totalCnt > 50000){ 
	            sb.append("         		 LIMIT 50000                                 \n");
	            }
	                
	         	
	         	Log.debug(sb.toString() );
	         	rs = stmt.executeQuery(sb.toString());
          	          
	            while( rs.next() ) {
  		        	mBean  = new MetaBean();
					mBean.setMd_seq(rs.getString("MD_SEQ"));
					mBean.setS_seq(rs.getString("S_SEQ"));
					mBean.setSg_seq("SG_SEQ");
					mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
					mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
					mBean.setMd_type(rs.getString("MD_TYPE"));
					mBean.setMd_date(rs.getString("MD_DATE"));
					mBean.setMd_pseq(rs.getString("MD_PSEQ"));
					mBean.setMd_title(rs.getString("MD_TITLE"));
					mBean.setMd_url(rs.getString("MD_URL"));
					mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));
					mBean.setSga_name(rs.getString("SGA_NAME"));
					mBean.setMd_important(rs.getString("MD_IMPORTANT"));
					mBean.setPortal_check(rs.getString("PORTAL_CHECK"));
					if(stName.equals("PORTAL_SEARCH_")){
						mBean.setPortal_url(rs.getString("PORTAL_URL"));
					}
					arrlist.add(mBean);			
	
	            } // end while
	        } // end if
		
		} catch (SQLException ex ) {
			Log.writeExpt(ex, sb.toString() );
		
		} catch (Exception ex ) {
			Log.writeExpt(ex);
		
		} finally {
			try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
		}
	
		return arrlist;
	}
	
	/**
	* 휴지통에 들어있는 기사 카운트
	*/
    public String getIdxDelCNT(String m_seq){
    	return getIdxDelCNT(m_seq,"");
    }
	public String getIdxDelCNT(String m_seq, String stName){
		String IdxDelCNT ="0";
		
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();

            //게시물 리스트를 구한다.

            sb = new StringBuffer();

            sb.append("SELECT COUNT(MD_SEQ) AS TOTAL_CNT FROM "+stName+"META WHERE I_STATUS = 'T' "); // AND M_SEQ="+m_seq+" \n");
                   
            rs = stmt.executeQuery(sb.toString());

            while( rs.next() ) {

            	IdxDelCNT = rs.getString("TOTAL_CNT");

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
		
		return IdxDelCNT;
	}
	
	/**
	* IDX  truncate:휴지통 , revert:복원, del:영구삭제, delAll:휴지통비우기
	*/
	
	public boolean idxProcess(String mode, String key, String m_seq){
		return idxProcess(mode, key, m_seq, "");
	}
	public boolean idxProcess(String mode, String key, String m_seq, String stName)
	{
		DateUtil du = new DateUtil();
		boolean result = false;
		 try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            //md_pseq를 구한다
            sb = new StringBuffer();
            String Tempno = "";
            //sb.append("SELECT MD_SEQ FROM META WHERE MD_PSEQ = (SELECT MD_PSEQ FROM META WHERE MD_SEQ = "+key+")");
            
            
            if(key.length()>0){
	            sb.append(" SELECT M1.MD_SEQ               \n");
	            sb.append("   FROM "+stName+"META M1, "+stName+"META M2        \n"); 
	            sb.append("  WHERE M1.MD_PSEQ = M2.MD_PSEQ \n");
	            sb.append("    AND M2.MD_SEQ IN ("+ key +")\n");
	            
	            rs = stmt.executeQuery(sb.toString());
	            while(rs.next()){
	            	if(Tempno.equals("")){
	            		Tempno = rs.getString("MD_SEQ");
	            	}else{
	            		Tempno += ","+rs.getString("MD_SEQ");
	            	}
	            }
            }
            //게시물 리스트를 구한다.
            

            sb = new StringBuffer();
            if(mode.equals("truncate")){
            	sb.append("UPDATE "+stName+"META SET I_STATUS='T'  WHERE MD_SEQ IN ("+Tempno+")");
				System.out.println(sb.toString());		

            }else if(mode.equals("revert")){
            	sb.append("UPDATE "+stName+"META SET I_STATUS='N' WHERE MD_SEQ IN ("+Tempno+")");
            }else if(mode.equals("del")){
            	sb.append("DELETE FROM "+stName+"META WHERE I_STATUS='T' ");
            }else if(mode.equals("delAll")){
            	sb.append("DELETE FROM "+stName+"META WHERE I_STATUS='T' ");
            }          
                   
            if(stmt.executeUpdate(sb.toString())>0) result = true;

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {       
        	sb=null;
        	try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
		return result;
	}
	
	public boolean restoreProcess(String m_seq)
	{
		DateUtil du = new DateUtil();
		boolean result = false;
		 try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            //md_pseq를 구한다
            sb = new StringBuffer();
            sb.append("INSERT INTO META (md_seq,md_pseq,md_site_name,md_menu_name,md_date,md_type,md_title,md_url,md_same_count,sg_seq,s_seq,sb_seq,ek_seq,md_img,l_alpha,ts_type,ts_rank,md_content,issue_check,json_data,i_status)\n");
            sb.append("          (SELECT md_seq,md_pseq,md_site_name,md_menu_name,md_date,md_type,md_title,md_url,md_same_count,sg_seq,s_seq,sb_seq,ek_seq,md_img,l_alpha,ts_type,ts_rank,md_content,issue_check,json_data,i_status FROM EXCEPTION_META WHERE MD_SEQ = "+m_seq+")\n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());

            /*
            sb = new StringBuffer();
            sb.append("INSERT INTO DATA (MD_SEQ,MD_CONTENT)\n");
            sb.append("          (SELECT MD_SEQ,MD_CONTENT FROM EXCEPTION_DATA WHERE MD_SEQ = "+m_seq+")\n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            */
            
            
            
            sb = new StringBuffer();
            sb.append("INSERT INTO IDX (md_seq,k_xp,k_yp,k_zp)\n");
            sb.append("         (SELECT md_seq,k_xp,k_yp,k_zp FROM EXCEPTION_IDX WHERE MD_SEQ = "+m_seq+")\n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            
            /*
            sb = new StringBuffer();
            sb.append("INSERT INTO TWEET (MD_SEQ,T_TWEET_ID,T_USER_ID,T_IS_RT,T_FOLLOWERS,T_FOLLOWING,T_TWEET,T_SOURCE,L_ALPHA,MD_TITLE,MD_DATE)\n");
            sb.append("           (SELECT MD_SEQ,T_TWEET_ID,T_USER_ID,T_IS_RT,T_FOLLOWERS,T_FOLLOWING,T_TWEET,T_SOURCE,L_ALPHA,MD_TITLE,MD_DATE FROM EXCEPTION_TWEET WHERE MD_SEQ = "+m_seq+")\n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            */
            
            sb = new StringBuffer();
            sb.append("DELETE FROM EXCEPTION_META WHERE MD_SEQ = "+m_seq+"\n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            
            /*
            sb = new StringBuffer();
            sb.append("DELETE FROM EXCEPTION_DATA WHERE MD_SEQ = "+m_seq+"\n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            */
            
            sb = new StringBuffer();
            sb.append("DELETE FROM EXCEPTION_IDX WHERE MD_SEQ = "+m_seq+"\n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            
            /*
            sb = new StringBuffer();
            sb.append("DELETE FROM EXCEPTION_TWEET WHERE MD_SEQ = "+m_seq+"\n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            */		
            

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
	
	/**
	 * 모바일 웹
	 * 키워드 정보를 가져온다
	 */
	public ArrayList getMobileKeyword(String psXp, String psYp, String psZp ) {

        ArrayList arrlist = new ArrayList();

        try {

            sb = new StringBuffer();
            sb.append("SELECT K_SEQ                       \n");
            sb.append("       ,K_XP                       \n");
            sb.append("       ,K_YP                       \n");
            sb.append("       ,K_ZP                       \n");
            sb.append("       ,K_VALUE                       \n");
            sb.append("  FROM KEYWORD                    \n");
            sb.append(" WHERE K_TYPE < 11  \n");
            if ( psXp.length()>0 )
            	sb.append(" AND K_XP IN ("+psXp+")                   \n");
            if ( psYp.length()>0 )
            	sb.append(" AND K_YP IN ("+psYp+")                   \n");
            
            if ( psZp.length()>0 )
            	sb.append(" AND K_ZP IN ("+psZp+")                   \n");
            else
            	sb.append(" AND K_ZP = 0                   \n");
            
            sb.append(" ORDER BY K_OP, K_XP, K_YP, K_ZP, K_VALUE                   \n");
            System.out.println(sb.toString());
            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());

            while( rs.next() ) {
            	keywordInfo kBean = new keywordInfo();
            	kBean.setK_Seq(rs.getString("K_SEQ"));
            	kBean.setK_Xp(rs.getString("K_XP"));
            	kBean.setK_Yp(rs.getString("K_YP"));
            	kBean.setK_Zp(rs.getString("K_ZP"));
            	kBean.setK_Value(rs.getString("K_VALUE"));
                arrlist.add( kBean );
            }

            sb = null;

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return arrlist;
    }

	public HashMap<String,String> getAutoMap(ArrayList<String[]> lastMapList){		
		HashMap<String,String> result  = new  HashMap<String,String>();
		try {
    		dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();			
	    	
	    	String[] tmp_pCode = null;
	    	String[] tmp = new String[2];
	    	for(int i=0; i<lastMapList.size(); i++){
	    		tmp = new String[2];
	    		 
	    		tmp  = lastMapList.get(i);    
	    		result.put(tmp[0], tmp[1]); //최하위 뎁스 저장
	    		//부모뎁스 저장 - 최하위뎁스의 부모뎁스	    		 
	    		while(true){
	    			tmp_pCode = getParentTypeCodeList(tmp); 
	    			if(null == tmp_pCode || "0".equals(tmp_pCode[0])){
	    				break;
	    			}
	    			result.put(tmp_pCode[0], tmp_pCode[1]);
	    			tmp[0] = tmp_pCode[0];
	    			tmp[1] = tmp_pCode[1];
	    		}
	    		
	    	 }   
	    	
	    	
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		    if (sb != null) sb = null;
		}
    	
    	 return result;    	 
    }
    
    
    public String[] getParentTypeCodeList(String[] type_code)throws SQLException, Exception{
    	String[] result = null;
    	try {    		
    		sb = new StringBuffer();
    		sb.append(" SELECT IC_PTYPE, IC_PCODE 		                                   \n");
    		sb.append("   FROM ISSUE_CODE                                   				\n");
			sb.append("  WHERE  IC_USEYN = 'Y'			                            	    \n");
			sb.append("  	 AND  IC_TYPE = "+type_code[0]+"                               \n");
			sb.append("  	 AND  IC_CODE = "+type_code[1]+" ;                              \n");
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				result = new String[2];
				result[0] = rs.getString("IC_PTYPE");
				result[1] = rs.getString("IC_PCODE");						
			}
			
			
    	} catch(SQLException ex) {
			throw ex;
		} catch(Exception ex) {
			throw ex;
		}
    	return result;
    }
    
    
    public ArrayList<String[]> getLastDepthMapList(String md_seq){
    	ArrayList<String[]> result = new ArrayList<String[]>();
    	try {
    		dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
    		sb = new StringBuffer();
    		sb.append(" SELECT IC_TYPE, IC_CODE 		                                   \n");
    		sb.append("   FROM MAP_KEYWORD_ISSUE_CODE MAP                                  \n");
			sb.append("  INNER JOIN IDX I ON I.K_XP = MAP.K_XP AND I.K_YP = MAP.K_YP       \n");
			sb.append("     WHERE I.MD_SEQ = "+md_seq+"                                   \n");
			sb.append("     	GROUP BY IC_TYPE                                 		  \n");
			 
					
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			
			String[] tmp = new String[2];
			while(rs.next()){
				tmp = new String[2];
				tmp[0] = rs.getString("IC_TYPE");
				tmp[1] = rs.getString("IC_CODE");
				//System.out.println("tmp[0]>>"+tmp[0]);
				//System.out.println("tmp[1]>>"+tmp[1]);
				result.add(tmp);
			}
			
			/*for(int i=0; i<result.size(); i++){
				System.out.println("[0]:"+result.get(i)[0]);
				System.out.println("[1]:"+result.get(i)[1]);
			}*/
			
    	} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		    if (sb != null) sb = null;
		}
    	
    	
    	return result;
    }
    
	public ArrayList Get_portal_total_list(String sd_gsn, int nowpage, int piRowCnt, String keyword, String sDateFrom, String sDateTo) {
  	  ArrayList list = new ArrayList();
  	  //sDateFrom = sDateFrom.replaceAll("-","");
  	  //sDateTo = sDateTo.replaceAll("-","");
  	  //System.out.println("sd_gsn : "+sd_gsn);
  	  try {
  		  
  		  
  		  
  		  
  		int liststart = 0;
   	 	int listend = 0 ;
     	
     	if (nowpage == 0) 
     		nowpage = 1;
     	else {
     		liststart = (nowpage-1) * piRowCnt;
     		listend =    piRowCnt;
     	}
  		  
  		  
  		  dbconn = new DBconn();
  		  dbconn.getDBCPConnection();
  		  stmt = dbconn.createStatement();
  		  String sXpValue = "";
	        String sYpValue = "";
	        String sZpValue = "";
	
	        
	        msKeyTitle = "초기면 전체";
			if(keyword.length()>0){
				msKeyTitle += "-"+keyword;
			}
	        
  		  sb = new StringBuffer();
  
  		  sb.append("SELECT COUNT(*) AS CNT\n");
  		  sb.append("  FROM TOP	A			\n");
  		  sb.append("     , PORTAL_IDX B				\n");
  		  sb.append(" WHERE A.T_STAMP BETWEEN UNIX_TIMESTAMP('"+sDateFrom+" 00:00:00') AND UNIX_TIMESTAMP('"+sDateTo+" 23:59:59')\n");  
  		  sb.append("	AND A.T_SEQ = B.P_SEQ  \n");
  		  if(!sd_gsn.equals("0")){
			  sb.append("	    AND A.S_SEQ IN ("+sd_gsn+")   																								\n");
		  }
		  if(!keyword.equals("")){
			  sb.append("	    AND A.T_TITLE LIKE '%"+keyword+"%'   																							\n");
		  }
  		  sb.append(" ORDER BY A.T_STAMP DESC\n");
  		  
  		  
  		  
  		  //System.out.println(sb.toString());
  		  Log.writeExpt(sb.toString());
  		  rs = stmt.executeQuery(sb.toString());
  		  if(rs.next()){
  			  miTotalCnt = rs.getInt("CNT");
  		  }
  		  
  		  sb = new StringBuffer();

  		  sb.append("SELECT A.T_SEQ ,A.S_SEQ, A.T_SITE, FROM_UNIXTIME(A.T_STAMP) AS STIME , A.T_URL, A.T_TITLE, A.T_STAMP, '' AS ISSUE_CHECK \n");
  		  sb.append("	  FROM TOP        A\n");
  		  sb.append("		   , PORTAL_IDX B\n");
  		  sb.append("WHERE A.T_STAMP BETWEEN UNIX_TIMESTAMP('"+sDateFrom+" 00:00:00') AND UNIX_TIMESTAMP('"+sDateTo+" 23:59:59')\n");  
  		  sb.append("AND A.T_SEQ = B.P_SEQ \n");
  		  if(!sd_gsn.equals("0")){
			  sb.append("	    AND A.S_SEQ IN ("+sd_gsn+")   																								\n");
		  }
		  if(!keyword.equals("")){
			  sb.append("	    AND A.T_TITLE LIKE '%"+keyword+"%'   																							\n");
		  }
  		  sb.append("ORDER BY A.T_STAMP DESC\n");
  		  sb.append("LIMIT "+liststart+", "+listend+"\n");
  		  
  		  Log.writeExpt(sb.toString());
  		  rs = stmt.executeQuery(sb.toString());
  		  while(rs.next()) {
				    			  list.add(new SearchPortalBean(
					  						rs.getString("T_SEQ"),
					  						rs.getString("S_SEQ"),
					  						rs.getString("T_SITE"),
					  						rs.getString("STIME"),
					  						rs.getString("T_URL"),
					  						rs.getString("T_TITLE"),
					  						rs.getString("T_STAMP"),
					  						rs.getString("T_STAMP"),
					  						rs.getString("ISSUE_CHECK"),
					  						""
					  						)
				    			  );
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
	
	/**
	 * 열어본 페이지 저장 
	 */
	public ArrayList insertComfirm(String m_seq, String md_seq) {

        ArrayList arrlist = new ArrayList();

        try {

            sb = new StringBuffer();
            
            sb.append("INSERT INTO MEMBER_COMFIRM ( M_SEQ, MD_SEQ, M_REGDATE)		\n");
            sb.append("                    VALUES ( "+m_seq+", "+md_seq+", NOW())	\n");
            sb.append("ON DUPLICATE KEY												\n");
            sb.append("UPDATE M_REGDATE = NOW()										\n");
            
            System.out.println(sb.toString());
            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            stmt.executeUpdate(sb.toString());

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return arrlist;
    }
	
	/**
	 * 열어본 페이지 저장 
	 */
	public String deleteComfirm(String m_seq, String md_seqs) {

        String result = "";

        try {

        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
        	
            sb = new StringBuffer();
            sb.append("DELETE FROM MEMBER_COMFIRM WHERE M_SEQ = "+m_seq+" AND MD_SEQ IN( "+md_seqs+")\n");
            
            System.out.println(sb.toString());            
            stmt.executeUpdate(sb.toString());
            
            sb = new StringBuffer();
            sb.append("SELECT MD_SEQ FROM MEMBER_COMFIRM WHERE MD_SEQ IN ("+md_seqs+")\n");
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            while(rs.next()){
            	if(result.equals("")){
            		result = rs.getString("MD_SEQ");
            	}else{
            		result += "," + rs.getString("MD_SEQ");
            	}
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
	
	
	/**
	* 대표 유사를 변경한다.
	*/
	public boolean alterList(String md_seq, String md_pseq)
	{
		
		boolean result = false;
		 try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
          
            sb = new StringBuffer();
            sb.append("UPDATE META SET MD_PSEQ = "+md_seq+", MD_SAME_COUNT = 0 WHERE MD_SEQ = "+md_seq+" \n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            
            sb = new StringBuffer();
            sb.append("UPDATE META SET MD_SAME_COUNT = MD_SAME_COUNT - 1 WHERE MD_PSEQ = "+md_pseq+" \n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            
            
            sb = new StringBuffer();
            sb.append("SELECT COUNT(*) AS CNT FROM ISSUE_DATA WHERE MD_SEQ = "+md_seq+" \n");
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            int cnt = 0;
            if(rs.next()){
            	cnt = rs.getInt("CNT");
            }
            
            if(cnt > 0){
            	sb = new StringBuffer();
                sb.append("UPDATE ISSUE_DATA SET MD_PSEQ = "+md_seq+", MD_SAME_CT = 0 WHERE MD_SEQ = "+md_seq+" \n");
                System.out.println(sb.toString());
                stmt.executeUpdate(sb.toString());
                
                sb = new StringBuffer();
                sb.append("UPDATE ISSUE_DATA SET MD_SAME_CT = MD_SAME_CT - 1 WHERE MD_PSEQ = "+md_pseq+" \n");
                System.out.println(sb.toString());
                stmt.executeUpdate(sb.toString());
            }
            
            

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {            
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
		return result;
	}
	
	public String Alter_mdSeq_mdPseq(String md_seq){
		return Alter_mdSeq_mdPseq(md_seq,"");
	}
	
	public String Alter_mdSeq_mdPseq(String md_seq, String mode)    
    {    	
    	String result = "";
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();
			if(mode.equals("PORTAL_SEARCH_")){
				sb.append(" SELECT DISTINCT MD_PSEQ FROM PORTAL_SEARCH_META WHERE MD_SEQ IN ("+md_seq+") 	\n");
			}else{
				sb.append(" SELECT DISTINCT MD_PSEQ FROM META WHERE MD_SEQ IN ("+md_seq+") 	\n");
			}
			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				if(result.equals("")){
					result = rs.getString("MD_PSEQ");	
				}else{
					result += "," +  rs.getString("MD_PSEQ");
				}
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
	public String Alter_mdSeq_mdPseq2(String md_seq)    
    {    	
    	String result = "";
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();				
			sb.append(" SELECT DISTINCT MD_PSEQ FROM ISSUE_DATA WHERE MD_SEQ IN ("+md_seq+") 	\n");
			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				if(result.equals("")){
					result = rs.getString("MD_PSEQ");	
				}else{
					result += "," +  rs.getString("MD_PSEQ");
				}
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
	
	public String Alter_idSeq_mdPseq2(String id_seqs)    
    {    	
    	String result = "";
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
					
			sb = new StringBuffer();				
			sb.append(" SELECT DISTINCT MD_PSEQ FROM ISSUE_DATA WHERE ID_SEQ IN ("+id_seqs+") 	\n");
			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				if(result.equals("")){
					result = rs.getString("MD_PSEQ");	
				}else{
					result += "," +  rs.getString("MD_PSEQ");
				}
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
	
	/*
	 * 유사묶기 관련 메소드 시작
	 */
	public int getPackGubun(String md_seqs)    
    {    	
    	int result = 0;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();				
			sb.append(" SELECT COUNT(*) AS CNT FROM ISSUE_DATA WHERE MD_SEQ IN ("+md_seqs+") 	\n");
			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				result = rs.getInt("CNT");
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
	
    
    public String getSamePseq(String md_seqs)    
    {    	
    	String result = "";
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();				
			
			sb.append("SELECT MD_PSEQ FROM META WHERE MD_SEQ IN ("+md_seqs+") ORDER BY MD_PSEQ ASC\n");
			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
	
			while(rs.next()){
				if(result.equals("")){
					result = rs.getString("MD_PSEQ");
				}else{
					result += "," + rs.getString("MD_PSEQ");
				}
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
	
	public void UpdateSamePseq(String md_pseqs)    
    {    	
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();				
			sb.append("UPDATE META SET MD_PSEQ = "+md_pseqs.split(",")[0]+" WHERE MD_PSEQ IN ("+md_pseqs+")\n");
			
			System.out.println(sb.toString());				
			int cnt = stmt.executeUpdate(sb.toString());
			
			sb = new StringBuffer();
			sb.append("UPDATE META SET MD_SAME_COUNT = "+String.valueOf(cnt-1)+" WHERE MD_PSEQ = "+md_pseqs.split(",")[0]+"\n");
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
	 }
	
	public void UpdateSameIssuePseq(String md_pseqs)    
    {    	
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();				
			sb.append("UPDATE ISSUE_DATA SET MD_PSEQ = "+md_pseqs.split(",")[0]+" WHERE MD_PSEQ IN ("+md_pseqs+")\n");
			
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
	 }
	
	public void DeleteSamePseq(String md_pseqs)    
    {    	
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			String[] arTemp = md_pseqs.split(",");
			String new_md_pseqs = "";
			for(int i = 1; i < arTemp.length; i++){
				if(new_md_pseqs.equals("")){
					new_md_pseqs = arTemp[i];
				}else{
					new_md_pseqs += "," + arTemp[i];
				}
			}
			
			sb = new StringBuffer();
			sb.append("DELETE FROM SAME_FILTER WHERE MD_SEQ IN ("+new_md_pseqs+")\n");
			
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
	 }
	
	
	public String getIssueSeq(String md_seqs, String type)    
    {    	
		String result = "";
		try {
			
			String not = "";
			if(type.equals("YES")){
				not = "";
			}else if(type.equals("NO")){
				not = "NOT";
			}
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();
			
			sb.append("SELECT MD_SEQ 																		\n");
			sb.append("  FROM META 																			\n");
			sb.append(" WHERE MD_SEQ IN ("+md_seqs+")														\n");
			sb.append("   AND MD_SEQ "+not+" IN (SELECT MD_SEQ FROM ISSUE_DATA WHERE MD_SEQ IN ("+md_seqs+"))	\n");
			
		
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
	
			while(rs.next()){
				if(result.equals("")){
					result = rs.getString("MD_SEQ");
				}else{
					result += "," + rs.getString("MD_SEQ");
				}
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
	/*
	 * 유사묶기 관련 메소드 끝
	 */
	
	
	/*
	 * 중요 체크 메소드 2018.02.09 고기범
	 * important_val 0 == 중요x , 1 == 중요
	 */
	public void updateImportant(String md_seq,String important_val, String mode){    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();
			if(mode.equals("PORTAL_SEARCH_")){
				sb.append("UPDATE PORTAL_SEARCH_META 					\n");
			}else{
				sb.append("UPDATE META 									\n");
			}
			sb.append("SET MD_IMPORTANT = "+ important_val +" 		\n");
			sb.append("WHERE MD_SEQ IN ("+ md_seq +")				\n");
			
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
	}
	
	public ArrayList getImportantNewsList(String check_mdSeq){
		return getImportantNewsList(check_mdSeq,"");
	}
	
	/*
	 * 기사공유 리스트 2018.02.13 고기범
	 */
     public ArrayList getImportantNewsList(String check_mdSeq, String mode){
         ArrayList arrlist = new ArrayList();

         try {

             dbconn = new DBconn();
             dbconn.getDBCPConnection();
             stmt = dbconn.createStatement();

             sb = new StringBuffer();

             sb.append("SELECT A.MD_SEQ								\n");
             sb.append("         ,A.MD_IMPORTANT					\n");
             sb.append("         ,A.MD_SITE_NAME					\n");
             sb.append("         ,A.MD_TITLE						\n");
             sb.append("         ,A.MD_DATE							\n");
             sb.append("         ,A.MD_URL							\n");
             if(mode.equals("PORTAL_SEARCH_")){
            	 sb.append("         ,A.MD_CONTENT						\n");
            	 sb.append("  FROM PORTAL_SEARCH_META A					\n");
            	 sb.append("  WHERE A.MD_SEQ IN ("+ check_mdSeq +")		\n");
             }else{
            	 sb.append("         ,B.MD_CONTENT						\n");
            	 sb.append("  FROM META A								\n");
            	 sb.append("          ,DATA B							\n");
            	 sb.append("WHERE A.MD_SEQ = B.MD_SEQ					\n");
            	 sb.append("  AND A.MD_SEQ IN ("+ check_mdSeq +")		\n");
             }
             
             System.out.println(sb.toString());
             rs = stmt.executeQuery(sb.toString());

             while( rs.next() ) {
                 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setMd_important(rs.getString("MD_IMPORTANT"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_content(rs.getString("MD_CONTENT"));
                arrlist.add(mBean);             
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

         return arrlist;
     }
     
     public String getPortalSearchKXP(){
         String result = "";

         try {
             dbconn = new DBconn();
             dbconn.getDBCPConnection();
             stmt = dbconn.createStatement();

             sb = new StringBuffer();
             sb.append(" SELECT DISTINCT K_XP FROM PORTAL_SEARCH_KEYWORD \n");
             
             //System.out.println(sb.toString());
             rs = stmt.executeQuery(sb.toString());
             
             int cnt = 1;
             while( rs.next() ) {
            	 if(cnt == 1){
            		 result += rs.getString("K_XP");
            	 }else{
            		 result += "," + rs.getString("K_XP");
            	 }
            	 cnt++;
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
     
     public void updateTempIssueReg(String md_seqs)    
     {    	
 		try {
 			
 			dbconn = new DBconn();
 			dbconn.getDBCPConnection();
 			stmt = dbconn.createStatement();
 			
 			sb = new StringBuffer();
 			sb.append("UPDATE META SET ISSUE_CHECK = 'T' WHERE MD_SEQ IN ("+md_seqs+")	\n");
 			
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
 	 }
     
    //보관함에 데이터 추가 
 	public boolean insertStorage( String md_seq, String sto_seq )
 	{
 		DBconn  conn  = null;
         Statement stmt = null;
         ResultSet rs = null;
         StringBuffer sb = new StringBuffer();
         boolean rebln = false;
         DateUtil du = new DateUtil();
         
         int chkcount = 0;
         int chkPosition = 0;
         
         try {
         	conn  = new DBconn();
        	 	conn.getDBCPConnection();
        	 	stmt = conn.createStatement();
        	
     	 	sb = new StringBuffer();	        
 			sb.append(" INSERT INTO MAP_META_STORAGE (MD_SEQ, STO_SEQ) \n");
 		 	sb.append(" VALUES ("+md_seq+","+sto_seq+") \n");
 	 	
 		 	System.out.println(sb.toString());
 		 	stmt.executeUpdate(sb.toString());
 		 	rebln = true;
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
	
    public ArrayList getKeywordSearchInfo(String md_seqs, String k_xp, String k_yp, String k_zp){

    	ArrayList result = new ArrayList();
    	
    	ResultSet rs = null;
    	StringBuffer sb = null;
    	try{
			dbconn = new DBconn();
	        dbconn.getDBCPConnection();
	        stmt = dbconn.createStatement();
	
	        sb = new StringBuffer();
    	 	
    		sb.append("SELECT I.MD_SEQ, K.K_XP, K.K_VALUE AS K_VALUE1, K1.K_YP, K1.K_VALUE AS K_VALUE2, K2.K_ZP, K2.K_VALUE AS K_VALUE3, K2.K_OP\n");
    		sb.append("FROM IDX I\n");
    		sb.append("INNER JOIN (SELECT K_XP, K_VALUE FROM KEYWORD WHERE K_YP = 0) K\n");
    		sb.append("ON I.K_XP = K.K_XP\n");
    		sb.append("INNER JOIN (SELECT K_XP, K_YP, K_VALUE FROM KEYWORD WHERE K_ZP = 0) K1\n");
    		sb.append("ON I.K_XP = K1.K_XP AND I.K_YP = K1.K_YP\n");
    		sb.append("INNER JOIN KEYWORD K2\n");
    		sb.append("ON I.K_XP = K2.K_XP AND I.K_YP = K2.K_YP AND I.K_ZP = K2.K_ZP\n");
    		sb.append("WHERE I.MD_SEQ IN ("+md_seqs+")\n");
    		if(k_xp != null && !k_xp.equals("")){
    		sb.append("AND K2.K_XP IN ("+k_xp+") \n");
    		}
    		if(k_yp != null && !k_yp.equals("")){
    		sb.append("AND K2.K_YP = "+k_yp+"\n");
    		}
    		if(k_zp != null && !k_zp.equals("")){
    		sb.append("AND K2.K_ZP = "+k_zp+"\n");
    		}
    		sb.append("AND K2.K_TYPE = 2\n");
    		sb.append("AND K2.K_PRC = 1\n");

    		System.out.println(sb.toString());
    		rs = stmt.executeQuery(sb.toString());
    		  
            
    		while(rs.next()){
    			HashMap bean = new HashMap();
    			bean.put("MD_SEQ", rs.getString("MD_SEQ"));
    			bean.put("K_XP", rs.getString("K_XP"));
    			bean.put("K_VALUE1", rs.getString("K_VALUE1"));
    			bean.put("K_YP", rs.getString("K_YP"));
    			bean.put("K_VALUE2", rs.getString("K_VALUE2"));
    			bean.put("K_ZP", rs.getString("K_ZP"));
    			bean.put("K_VALUE3", rs.getString("K_VALUE3"));
    			bean.put("K_OP", rs.getString("K_OP"));
    			result.add(bean);
    		}
    	
    	}catch(SQLException e){
    		e.printStackTrace();
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		sb = null;
    		try{if(dbconn != null)dbconn.close();}catch(Exception e){}
    		try{if(rs != null)rs.close();}catch(Exception e){}
    		try{if(stmt != null)stmt.close();}catch(Exception e){}
    	}
    	return result;
    }
    
    public String cutKey( String psBody, String psKeyword, int piLen, String tColor ){
    	String result = null;
    	
		StringUtil su = new StringUtil();
		
		int idxPoint = -1; 
		try{
			psBody = su.ChangeString( psBody.toLowerCase() );
			
			if (!su.nvl(psKeyword,"").equals("")) {
				String[] arrBFKey = su.getUniqeArray( psKeyword.split(" ") );			// 강조키워드 배열
				String[] arrAFKey = new String[arrBFKey.length];	// 강조키워드 html 배열
			
				for( int i=0 ; i<arrBFKey.length ; i++ )
				{
					arrBFKey[i] = arrBFKey[i].toLowerCase();
					if(!tColor.equals("")){
						arrAFKey[i] = "<font color='"+tColor+"'><b>"+arrBFKey[i]+"</b></font>";
					}else{
						arrAFKey[i] = arrBFKey[i];
					}
					
					if( idxPoint == -1 ) {
						idxPoint = psBody.indexOf(arrBFKey[i]);
					}
				}
				// piLen*2 길이만큼 본문내용 자르기
				int currPoint = 0;
				int startCut = 0;
				int endCut = 0;
				String startDot = "";
				String endDot = "";
		
				if( idxPoint >= 0 ) currPoint = idxPoint;
				// 내용자르기의 시작포인트
				if( currPoint >= piLen ) {
					startDot = "...";
					startCut = currPoint - piLen;
				} else {
					startCut = 0;
				}
				
				// 내용자르기의 마지막포인트
				if( psBody.length() >= (startCut + piLen*2) ) {
					endDot = "...";
					endCut = currPoint + piLen;
				} else {
					endCut = psBody.length()-1;
				}
					
				result = startDot+psBody.substring(startCut, endCut)+endDot;
				if( idxPoint > 0 ) {
					if (arrBFKey.length>0) {
						for( int i=0 ; i<arrBFKey.length ; i++  )
						{
							result = result.replaceAll(arrBFKey[i], arrAFKey[i]);
						}
					}
				}
			} else {
				// piLen*2 길이만큼 본문내용 자르기
				int currPoint = 0;
				int startCut = 0;
				int endCut = 0;
				String startDot = "";
				String endDot = "";
		
				if( idxPoint >= 0 ) currPoint = idxPoint;
				// 내용자르기의 시작포인트
				if( currPoint >= piLen ) {
					startDot = "...";
					startCut = currPoint - piLen;
				} else {
					startCut = 0;
				}
				
				// 내용자르기의 마지막포인트
				if( psBody.length() >= (startCut + piLen*2) ) {
					endDot = "...";
					endCut = currPoint + piLen;
				} else {
					endCut = psBody.length()-1;
				}
					
				result = startDot+psBody.substring(startCut, endCut)+endDot;
			}
    	} catch( Exception ex ) {
    		Log.writeExpt(ex);
		}
		
		return result;
    }
    
	public ArrayList getSameListTop(String xp){
		ArrayList result = new ArrayList();
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		DBconn dbconn = null;
		DateUtil du = new DateUtil();
		
		getMaxMinNo(du.getCurrentDate("yyyy-MM-dd"), du.getCurrentDate("yyyy-MM-dd"));
		String MinMtNo= msMinNo;
		String MaxMtNo= msMaxNo;
		try{
			dbconn = new DBconn();
	        dbconn.getDBCPConnection();
	        stmt = dbconn.createStatement();
	
			sb = new StringBuffer();
			
			sb.append("SELECT M.MD_TITLE, M.MD_URL, M.MD_SAME_COUNT						\n");
			sb.append("FROM META M														\n");
			sb.append("INNER JOIN														\n");
			sb.append("(																\n");
			sb.append("		SELECT MIN(A.MD_SEQ) AS MD_SEQ								\n");                        
			sb.append("		FROM														\n");
			sb.append("		(															\n");
			sb.append("			SELECT DISTINCT (MD_SEQ)								\n");
			sb.append("			FROM IDX I,KEYWORD K									\n");
			sb.append("			WHERE I.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"		\n");
			sb.append("			AND I.K_XP = K.K_XP										\n");
			sb.append("			AND I.K_YP = K.K_YP										\n");
			sb.append("			AND I.K_ZP = K.K_ZP										\n");
			//sb.append("			AND I.K_XP IN ("+xp+")								\n");
			//sb.append("			AND K.K_PRC = 1										\n");
			sb.append("		) A , META B												\n");
			sb.append("		WHERE A.MD_SEQ = B.MD_SEQ									\n");
			sb.append("		GROUP BY B.MD_PSEQ											\n");
			sb.append(") I																\n");
			sb.append("ON M.MD_SEQ = I.MD_SEQ											\n");
			sb.append("AND M.MD_DATE > CURRENT_DATE()									\n");
			sb.append("GROUP BY M.MD_TITLE, M.MD_URL, M.MD_SAME_COUNT					\n");
			sb.append("ORDER BY M.MD_SAME_COUNT DESC									\n");
			sb.append("LIMIT 10															\n");
    		System.out.println(sb.toString());
    		rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				MetaBean mBean = new MetaBean();
				mBean.setMd_title(rs.getString("MD_TITLE"));
				mBean.setMd_url(rs.getString("MD_URL"));
				mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));
				result.add(mBean);
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){};
			try{if(stmt != null)stmt.close();}catch(Exception e){};
			try{if(rs != null)rs.close();}catch(Exception e){};
		}
		return result;
	}
	
    public String Deletedatacnt(String mode, String key, String stName)
    { 
		ResultSet rs = null;
    	String result = null;
		try {
			dbconn  = new DBconn();
			dbconn.getDBCPConnection();
	        stmt = dbconn.createStatement();

		        if(mode.equals("truncate")){
		        	
		        	sb = new StringBuffer();
		        	String Tempno = "";
		        	if(key.length()>0){
		        		sb.append(" SELECT M1.MD_SEQ               \n");
		        		sb.append("   FROM "+stName+"META M1, "+stName+"META M2        \n"); 
		        		sb.append("  WHERE M1.MD_PSEQ = M2.MD_PSEQ \n");
		        		sb.append("    AND M2.MD_SEQ IN ("+ key +")\n");
		        		
		        		System.out.println(sb.toString());		
		        		rs = stmt.executeQuery(sb.toString());
		        		
		        		while(rs.next()){
		        			if(Tempno.equals("")){
		        				Tempno = rs.getString("MD_SEQ");
		        			}else{
		        				Tempno += ","+rs.getString("MD_SEQ");
		        			}
		        		}
		        		
		        	sb = new StringBuffer();
		        	sb.append("#META 테이블 내 데이터 삭제                                      \n");                
		        	sb.append("DELETE FROM META WHERE MD_SEQ IN ("+Tempno+")                           \n");
		        	
		        	System.out.println(sb.toString());		
		        	stmt.executeUpdate(sb.toString());
		        	
		        	sb = new StringBuffer();
		        	sb.append("#IDX 테이블 내 데이터 삭제                         \n");
		        	sb.append("DELETE FROM IDX WHERE MD_SEQ IN ("+Tempno+")              \n");
 	
		        	System.out.println(sb.toString());		
		        	stmt.executeUpdate(sb.toString());
		        	
		        	sb = new StringBuffer();
		        	sb.append("#ZP CNT 제거                                                                                                            \n");
		        	sb.append("UPDATE META_ANA_KEYWORD A                                                                                               \n");
		        	sb.append("     , (SELECT DATE_FORMAT(A.MD_DATE, '%Y%m%d') AS DATE                                                                 \n");
		        	sb.append("             , B.K_XP                                                                                                   \n");
		        	sb.append("             , B.K_YP                                                                                                   \n");
		        	sb.append("             , B.K_ZP                                                                                                   \n");
		        	sb.append("             , COUNT(*) AS CNT                                                                                          \n");
		        	sb.append("          FROM META A                                                                                                   \n");
		        	sb.append("             , IDX B                                                                                                    \n");
		        	sb.append("         WHERE A.MD_SEQ = B.MD_SEQ                                                                                      \n");
		        	sb.append("           AND A.MD_SEQ IN ("+Tempno+")                                                                                 \n");
		        	sb.append("         GROUP BY DATE_FORMAT(A.MD_DATE, '%Y%m%d'), B.K_XP, B.K_YP, B.K_ZP)B                                            \n");
		        	sb.append("   SET A.MAK_CNT = A.MAK_CNT - CNT                                                                                      \n");
		        	sb.append(" WHERE A.MAK_DATE = B.DATE                                                                                              \n");
		        	sb.append("   AND A.K_XP = B.K_XP                                                                                                  \n");
		        	sb.append("   AND A.K_YP = B.K_YP                                                                                                  \n");
		        	sb.append("   AND A.K_ZP = B.K_ZP                                                                                                  \n");
                                                                                                                                                       		        	
		        	System.out.println(sb.toString());		
		        	stmt.executeUpdate(sb.toString());
		        	
		        	sb = new StringBuffer();
		        	sb.append("#YP CNT 제거                                                                                                                  										       \n");
		        	sb.append("UPDATE META_ANA_KEYWORD A                                                                                               \n");
		        	sb.append("     , (SELECT DATE_FORMAT(A.MD_DATE, '%Y%m%d') AS DATE                                                                 \n");
		        	sb.append("             , B.K_XP                                                                                                   \n");
		        	sb.append("             , B.K_YP                                                                                                   \n");
		        	sb.append("             , 0 AS K_ZP                                                                                                \n");
		        	sb.append("             , COUNT(*) AS CNT                                                                                          \n");
		        	sb.append("          FROM META A                                                                                                   \n");
		        	sb.append("             , IDX B                                                                                                    \n");
		        	sb.append("         WHERE A.MD_SEQ = B.MD_SEQ                                                                                      \n");
		        	sb.append("           AND A.MD_SEQ IN ("+Tempno+")          																	   \n");
		        	sb.append("         GROUP BY DATE_FORMAT(A.MD_DATE, '%Y%m%d'), B.K_XP, B.K_YP)B                                                    \n");
		        	sb.append("   SET A.MAK_CNT = A.MAK_CNT - CNT                                                                                      \n");
		        	sb.append(" WHERE A.MAK_DATE = B.DATE                                                                                              \n");
		        	sb.append("   AND A.K_XP = B.K_XP                                                                                                  \n");
		        	sb.append("   AND A.K_YP = B.K_YP                                                                                                  \n");
		        	sb.append("   AND A.K_ZP = B.K_ZP                                                                                                  \n");
                                                                                                                                                                                                                		        	
		        	System.out.println(sb.toString());		
		        	stmt.executeUpdate(sb.toString());
		        	
		        	sb = new StringBuffer();
		        	sb.append("#XP CNT 제거                                                                                                             								     		   \n");
		        	sb.append("UPDATE META_ANA_KEYWORD A                                                                                               \n");
		        	sb.append("     , (SELECT DATE_FORMAT(A.MD_DATE, '%Y%m%d') AS DATE                                                                 \n");
		        	sb.append("             , B.K_XP                                                                                                   \n");
		        	sb.append("             , 0 AS K_YP                                                                                                \n");
		        	sb.append("             , 0 AS K_ZP                                                                                                \n");
		        	sb.append("             , COUNT(*) AS CNT                                                                                          \n");
		        	sb.append("          FROM META A                                                                                                   \n");
		        	sb.append("             , IDX B                                                                                                    \n");
		        	sb.append("         WHERE A.MD_SEQ = B.MD_SEQ                                                                                      \n");
		        	sb.append("           AND A.MD_SEQ IN ("+Tempno+")           																	   \n");
		        	sb.append("         GROUP BY DATE_FORMAT(A.MD_DATE, '%Y%m%d'), B.K_XP)B                                                            \n");
		        	sb.append("   SET A.MAK_CNT = A.MAK_CNT - CNT                                                                                      \n");
		        	sb.append(" WHERE A.MAK_DATE = B.DATE                                                                                              \n");
		        	sb.append("   AND A.K_XP = B.K_XP                                                                                                  \n");
		        	sb.append("   AND A.K_YP = B.K_YP                                                                                                  \n");
		        	sb.append("   AND A.K_ZP = B.K_ZP                                                                                                  \n");
		        	                                                                                                                                    
		        	System.out.println(sb.toString());		
		        	stmt.executeUpdate(sb.toString());
		        	}
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

	
	public String[] getChartData(String date, String xp, String yp, String zp){
		String[] result = new String[2];
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		DBconn dbconn = null;
		DateUtil du = new DateUtil();
		
		String sdate = "";
		String edate = "";
		
		String tempResult1 = "";
		String result1 = "";
		String result2 = "";
		
		String tempDate = "";
		HashMap data = new HashMap();
		try{
			edate = date;
			System.out.println("edate>>" + edate);
			sdate = du.addDay(edate, -14);
			System.out.println("sdate>>" + sdate);
			tempDate = sdate;
			
			dbconn = new DBconn();
	        dbconn.getDBCPConnection();
	        stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT DATE_FORMAT(MAK_DATE,'%Y-%m-%d') AS MAK_DATE, MAK_CNT\n");
			sb.append("FROM META_ANA_KEYWORD\n");
			sb.append("WHERE\n");
			if(xp.equals("") && yp.equals("") && zp.equals(""))
			sb.append("K_XP = 0 AND K_YP = 0 AND K_ZP = 0 AND	\n");
			if(!xp.equals("") && yp.equals("") && zp.equals(""))
			sb.append("K_XP IN ("+xp+") AND K_YP = 0 AND K_ZP = 0 AND\n");
			if(!xp.equals("") && !yp.equals("") && zp.equals(""))
			sb.append("K_XP IN ("+xp+") AND K_YP = "+yp+" AND K_ZP = 0 AND\n");
			if(!xp.equals("") && !yp.equals("") && !zp.equals(""))
			sb.append("K_XP IN ("+xp+") AND K_YP = "+yp+" AND K_ZP = "+zp+" AND\n");
			//if(!sg_seq.equals(""))
			//sb.append("SG_SEQ IN ("+sg_seq+") AND\n");	
			sb.append("MAK_DATE BETWEEN '"+sdate.replace("-", "")+"' AND '"+edate.replace("-", "")+"'	\n");
			sb.append("GROUP BY DATE_FORMAT(MAK_DATE,'%Y-%m-%d')	\n");
			sb.append("ORDER BY DATE_FORMAT(MAK_DATE,'%Y-%m-%d')	\n");
			System.out.println(sb.toString());
			
    		rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				data.put(rs.getString("MAK_DATE"), rs.getString("MAK_CNT"));
			}
			while(true){
				if(tempResult1.equals("")){
					tempResult1 = tempDate;
				}else{
					tempDate = du.addDay(tempDate, 1);
					tempResult1 += ","+tempDate;
				}
				if(edate.equals(tempDate)){
					break;
				}
			}
			for(int i = 0; i < tempResult1.split(",").length; i++){
				for(int j = 0; j < data.size(); j++){
					if(data.containsKey(tempResult1.split(",")[i])){
						if(result2.equals("")){
							result2 = (String) data.get(tempResult1.split(",")[i]);
							break;
						}else{
							result2 += ","+(String) data.get(tempResult1.split(",")[i]);
							break;
						}
					}else{
						if(result2.equals("")){
							result2 = "0";
							break;
						}else{
							result2 += ",0";
							break;
						}
					}
				}
				if(result1.equals("")){
					result1 = "'"+tempResult1.split(",")[i]+"'";
				}else{
					result1 += ",'"+tempResult1.split(",")[i]+"'";
				}
			}
			result[0] = result1;
			result[1] = result2;
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){};
			try{if(stmt != null)stmt.close();}catch(Exception e){};
			try{if(rs != null)rs.close();}catch(Exception e){};
		}
		return result;
	}
	
}
