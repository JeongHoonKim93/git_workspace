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

import org.apache.xalan.templates.WhiteSpaceInfo;

import risk.DBconn.DBconn;
import risk.admin.bookmark.BookMarkMgr;
import risk.admin.keyword.KeywordBean;
import risk.util.DateUtil;
import risk.util.Log;

public class PortalMgr {

    DBconn dbconn   = null;
    Statement stmt  = null;
    ResultSet rs    = null;

    StringBuffer sb = null;    
    String sQuery   = "";
    
    public  PortalBean pBean  = null;
    public int pBeanTotalCnt   = 0;
    public int pBeanPageCnt    = 0;
    public int pBeanDataCnt    = 0;
    
    public int portalCnt       = 0;
    public int miTotalCnt      = 0;
    
    
    ArrayList arrSourceCnt = new ArrayList();

    public ArrayList getArrSourceCnt() {
		return arrSourceCnt;
	}

	public String psMinNo   = "";   //검색 시작 PM_SEQ
    public String psMaxNo   = "";   //검색 마지막 PM_SEQ
    public String psKeyTitle= "";   //검색 그룹 한글명

    public int pBeanTotalPageCount = 0;

    public PortalMgr() {

    }
	
    public ArrayList getSearchPortalKeyword(
    		int    piNowpage,
    		int    piRowCnt,
    		String keywordKind,
    		String psKeyWord,
    		String sOrder, 
    		String sOrderAlign, 
    		String sDate, 
    		String eDate, 
    		String sTime, 
    		String eTime) {

	 	ArrayList arrlist = new ArrayList();
	 	
        try {
             
            getMaxMinNo(sDate, eDate, sTime, eTime);
            String MinPtNo= psMinNo;
            String MaxPtNo= psMaxNo;
            String[] arrKeyWord = null;
			arrKeyWord = psKeyWord.split("&");
            
        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
            
            sb = new StringBuffer();                    
            sb.append("	SELECT COUNT(1) AS TOTAL_CNT													\n");
            sb.append("		FROM PORTAL_META															\n");
            sb.append("		WHERE PM_SEQ BETWEEN "+ MinPtNo +" AND "+ MaxPtNo +"						\n");
            if(!keywordKind.equals("") && !psKeyWord.equals("")){
            	
	            if("1".equals(keywordKind)){
	            	for(int i = 0; i < arrKeyWord.length; i++){
						sb.append("            AND PM_TITLE LIKE '%" + arrKeyWord[i] + "%'        		\n");				
					}
	            }else{
	            	for(int i = 0; i < arrKeyWord.length; i++){
	            		sb.append("            AND PM_SITE LIKE '%" + arrKeyWord[i] + "%'        		\n");				
					}
	            }
            }
            Log.debug(sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());

	        if ( rs.next() ) {
	            pBeanTotalCnt  = rs.getInt("TOTAL_CNT");
	            pBeanPageCnt   = pBeanTotalCnt / piRowCnt ;
	        }
	        stmt.close();
	        if ( pBeanTotalCnt > 0 ) {
	        	
	        	sb = new StringBuffer();
	            
	            int liststart;
	         	int listend;
	         	
	         	if (piNowpage == 0) 
	         		piNowpage = 1;
	         	else {
	         		liststart = (piNowpage-1) * piRowCnt;
	         		listend =    piRowCnt;
	         	                 
		            sb.append("	SELECT PM_SEQ, PM_SITE, PM_BOARD, PM_TITLE, PM_URL, PM_DATE					\n");
		            sb.append("		FROM PORTAL_META														\n");
		            sb.append("		WHERE PM_SEQ BETWEEN "+ MinPtNo +"	AND "+ MaxPtNo +"					\n");
		            if(!keywordKind.equals("") && !psKeyWord.equals("")){
		            	
			            if("1".equals(keywordKind)){
			            	for(int i = 0; i < arrKeyWord.length; i++){
								sb.append("            AND PM_TITLE LIKE '%" + arrKeyWord[i] + "%'        	\n");				
							}
			            }else{
			            	for(int i = 0; i < arrKeyWord.length; i++){
			            		sb.append("            AND PM_SITE LIKE '%" + arrKeyWord[i] + "%'        	\n");				
							}
			            }
		            }
		            sb.append("  		ORDER BY " + sOrder + " "+ sOrderAlign +"                  			\n");
		            sb.append("   		LIMIT "+liststart+","+listend+"                            			\n");
	         	}
	         	System.out.println(sb.toString());
	            stmt = dbconn.createStatement();
	            rs = stmt.executeQuery(sb.toString());
	            
	            while( rs.next() ) {
	            	pBeanDataCnt++;
	            	
	            	pBean  = new PortalBean();
	            	pBean.setPm_seq(rs.getInt("PM_SEQ"));
	            	pBean.setPm_site(rs.getString("PM_SITE"));
	            	pBean.setPm_board(rs.getString("PM_BOARD"));
	            	pBean.setPm_title(rs.getString("PM_TITLE"));
	            	pBean.setPm_url(rs.getString("PM_URL"));
	            	pBean.setPm_date(rs.getString("PM_DATE"));
	            	
					arrlist.add(pBean);	
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
		return arrlist;
    }

    
    public void getMaxMinNo( String psSDate, String psEdate, String psSTime, String psETime ) {
    	
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
            sb.append(" SELECT (SELECT PM_SEQ FROM PORTAL_META WHERE PM_DATE BETWEEN '"+psSDate+" "+psSTime+":00:00' AND '"+psSDate+" 23:59:59' ORDER BY PM_DATE ASC LIMIT 1) MIN_NO \n");
            sb.append("        ,(SELECT PM_SEQ FROM PORTAL_META WHERE PM_DATE BETWEEN '"+psEdate+" 00:00:00' AND '"+psEdate+" "+psETime+":59:59' ORDER BY PM_DATE DESC LIMIT 1) MAX_NO \n");
                       

            Log.debug(sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());

            if ( rs.next() ) {
                psMinNo = rs.getString("MIN_NO");
                psMaxNo = rs.getString("MAX_NO");
                
                if(psMinNo==null || psMinNo.equals("null")){
                	
                	if(Integer.parseInt(psSDate.replaceAll("-", "") + psSTime) >= Integer.parseInt(du.getDate("yyyyMMddHH"))){
                		psMinNo = psMaxNo+1;
                	}else{
                		psMinNo = "0";
                	}
                	
                }
                if(psMaxNo==null || psMaxNo.equals("null")){
                	psMaxNo = "999999999";
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
    
	public ArrayList getExcelSearchList(     
            String psDateFrom,
            String psDateTo  ,
            String keywordKind ,
            String psKeyWord ,
    		String psOrder , 
            String stime ,
            String etime ,
            int totalCnt
		   )
	{	
		ArrayList arrlist = new ArrayList();
		
		try {
		getMaxMinNo( psDateFrom, psDateTo, stime, etime);
		String MinPtNo= psMinNo;
		String MaxPtNo= psMaxNo;
		
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

            sb.append("	SELECT PM_SITE, PM_BOARD, PM_TITLE, PM_URL, PM_DATE					\n");
            sb.append("		FROM PORTAL_META														\n");
            sb.append("		WHERE PM_SEQ BETWEEN "+ MinPtNo +"	AND "+ MaxPtNo +"					\n");
            if(!keywordKind.equals("") && !psKeyWord.equals("")){
            	
	            if("1".equals(keywordKind)){
	            	for(int i = 0; i < arrKeyWord.length; i++){
						sb.append("            AND PM_TITLE LIKE '%" + arrKeyWord[i] + "%'        	\n");				
					}
	            }else{
	            	for(int i = 0; i < arrKeyWord.length; i++){
	            		sb.append("            AND PM_SITE LIKE '%" + arrKeyWord[i] + "%'        	\n");				
					}
	            }
            }
            sb.append("  		ORDER BY " + psOrder +"                  							\n");
            if(totalCnt > 50000){ 
	            sb.append("     LIMIT 50000                                 						\n");
	        }
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
			while( rs.next() ) {
				pBean  = new PortalBean();
				pBean.setPm_site(rs.getString("PM_SITE"));
				pBean.setPm_board(rs.getString("PM_BOARD"));
				pBean.setPm_title(rs.getString("PM_TITLE"));
				pBean.setPm_url(rs.getString("PM_URL"));
				pBean.setPm_date(rs.getString("PM_DATE"));
				arrlist.add(pBean);			
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
	}
