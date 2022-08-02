package risk.statistics;

import java.io.*;
import java.sql.*;
import java.util.*;

import risk.DBconn.DBconn;
import risk.util.Log;
import risk.util.DateUtil;
import risk.util.StringUtil;
import risk.search.MetaMgr;
import risk.statistics.StatisticsBean;
import risk.admin.site.SiteBean;
import risk.issue.IssueDataBean;

	public class StatisticsIssueMgr {
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private ResultSet rs2    = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = null;	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
    
    private StatisticsBean statisticsBean = null;
    
    
 
	public ArrayList getIssueData1(String sdate, String edate, String stime, String etime) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsSuperBean superBean = new StatisticsSuperBean();
		StatisticsSuperBean.StatisticsIssue childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("SELECT A.TYPE16 AS IC_CODE														\n");
			sb.append("     , FN_ISSUE_NAME(A.TYPE16, 16) AS IC_NAME									\n");
			sb.append("     , SUM(IF(A.TYPE9 = 1, A.CNT, 0)) AS POS										\n");
			sb.append("     , SUM(IF(A.TYPE9 = 2, A.CNT, 0)) AS NEG										\n");
			sb.append("     , SUM(IF(A.TYPE9 = 3, A.CNT, 0)) AS NEU										\n");
			sb.append("  FROM (																			\n");
			sb.append("        SELECT B.IC_CODE AS TYPE16												\n");
			sb.append("             , C.IC_CODE AS TYPE9												\n");
			sb.append("             , COUNT(*) AS CNT													\n");
			sb.append("          FROM ISSUE_DATA A														\n");
			sb.append("             , ISSUE_DATA_CODE B													\n");
			sb.append("             , ISSUE_DATA_CODE C													\n");
			sb.append("         WHERE A.ID_SEQ = B.ID_SEQ												\n");
			sb.append("           AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'	\n");
			sb.append("           AND B.IC_TYPE = 16													\n");
			sb.append("           AND A.ID_SEQ = C.ID_SEQ												\n");
			sb.append("           AND C.IC_TYPE = 9														\n");
			sb.append("         GROUP BY B.IC_CODE, C.IC_CODE											\n"); 
			sb.append("       )A GROUP BY A.TYPE16														\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
				
				
				childBean = superBean.new StatisticsIssue();
				childBean.setIc_code(rs.getString("IC_CODE"));
				childBean.setIc_name(rs.getString("IC_NAME"));
				childBean.setPositive(rs.getString("POS"));
				childBean.setNegative(rs.getString("NEG"));
				childBean.setNeutral(rs.getString("NEU"));
				arrResult.add(childBean);
				
				
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	public ArrayList getIssueData2(String sdate, String edate, String stime, String etime, String codes) {
		
		ArrayList arrResult = new ArrayList();
		//StatisticsSuperBean superBean = new StatisticsSuperBean();
		//StatisticsSuperBean.StatisticsIssue childBean = null;
		String[] info = null;
		try {
			
			
			Long diff = du.DateDiff(edate.replaceAll("-", ""), sdate.replaceAll("-", ""));
			String[] ar_code = codes.split(",");
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			
			sb.append("SELECT A.MD_DATE																									\n");
			
			for(int i =0; i < ar_code.length; i++){
				sb.append("     , IFNULL(B.CODE"+ar_code[i]+",0) AS CODE"+ar_code[i]+"													\n");
			}
			sb.append("  FROM (																											\n");
			sb.append("        SELECT DATE_FORMAT(DATE_ADD('"+du.addDay_v2(edate, 1) +" "+etime+"',INTERVAL -(@ROW:= @ROW + 1) DAY) ,'%Y%m%d') AS MD_DATE	\n");
			sb.append("          FROM KEYWORD A																							\n");
			sb.append("             ,(SELECT @ROW := 0)R																				\n");
			sb.append("         LIMIT "+(diff +1)+" 																								\n");
			sb.append("       )A LEFT OUTER JOIN (																						\n");
			sb.append("        SELECT DATE_FORMAT(MD_DATE, '%Y%m%d') AS MD_DATE															\n");
			
			for(int i =0; i < ar_code.length; i++){
				sb.append("             , SUM(IF(B.IC_CODE = "+ar_code[i]+", 1, 0)) AS CODE"+ar_code[i]+"								\n");
			}
			
			sb.append("          FROM ISSUE_DATA A																						\n");
			sb.append("             , ISSUE_DATA_CODE B     																			\n");
			sb.append("         WHERE A.ID_SEQ = B.ID_SEQ																				\n");
			sb.append("           AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'									\n");
			sb.append("           AND B.IC_TYPE = 16																					\n");
			sb.append("           AND B.IC_CODE IN ("+codes+")																			\n");
			sb.append("         GROUP BY DATE_FORMAT(MD_DATE, '%Y%m%d')																	\n");
			sb.append("       )B ON A.MD_DATE = B.MD_DATE ORDER BY A.MD_DATE ASC														\n");
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			
			
			String str = "";
			while( rs.next() ) {
				
				
				
				info = new String[ar_code.length + 1];

				info[0] = rs.getString("MD_DATE");
				
				for(int i =0; i < ar_code.length; i++){
					info[i+1] =  rs.getString("CODE" + ar_code[i]);
				}
				
				arrResult.add(info);
			}
			
			
			
			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	
	public ArrayList getIssueData3(String sdate, String edate, String stime, String etime, String codes) {
		
		ArrayList arrResult = new ArrayList();
		
		try {
			
			Long diff = du.DateDiff(edate.replaceAll("-", ""), sdate.replaceAll("-", ""));
			
			
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("SELECT A.MD_DATE																					\n");
			sb.append("     , IFNULL(B.POS,0) AS POS																	\n");
			sb.append("     , IFNULL(B.NEG,0) AS NEG																	\n");
			sb.append("     , IFNULL(B.NEU,0) AS NEU																	\n");
			sb.append("  FROM (																							\n");
			sb.append("        SELECT DATE_FORMAT(DATE_ADD('"+du.addDay_v2(edate, 1)+" "+etime+"',INTERVAL -(@ROW:= @ROW + 1) DAY) ,'%Y%m%d') AS MD_DATE	\n");
			sb.append("          FROM KEYWORD A																			\n");
			sb.append("             , (SELECT @ROW := 0)R																\n");
			sb.append("         LIMIT "+(diff + 1)+" 																			\n");
			sb.append("       )A LEFT OUTER JOIN (																		\n");
			sb.append("        SELECT A.MD_DATE																			\n");
			sb.append("             , SUM(IF(A.IC_CODE = 1, A.CNT, 0)) AS POS											\n");
			sb.append("             , SUM(IF(A.IC_CODE = 2, A.CNT, 0)) AS NEG											\n");
			sb.append("             , SUM(IF(A.IC_CODE = 3, A.CNT, 0)) AS NEU											\n");
			sb.append("          FROM (																					\n");
			sb.append("                SELECT DATE_FORMAT(A.MD_DATE,'%Y%m%d') AS MD_DATE								\n");
			sb.append("                     , B.IC_CODE AS IC_CODE														\n");
			sb.append("                     , COUNT(*) AS CNT															\n");
			sb.append("                  FROM ISSUE_DATA A																\n");
			sb.append("                     , ISSUE_DATA_CODE B															\n");
			sb.append("                     , ISSUE_DATA_CODE C															\n");
			sb.append("                 WHERE A.ID_SEQ = B.ID_SEQ														\n");
			sb.append("                   AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'			\n");
			sb.append("                   AND B.IC_TYPE = 9   															\n");
			sb.append("                   AND A.ID_SEQ = C.ID_SEQ														\n");
			sb.append("                   AND C.IC_TYPE = 16															\n");
			sb.append("                   AND C.IC_CODE IN ("+codes+")													\n");
			sb.append("                 GROUP BY DATE_FORMAT(A.MD_DATE, '%Y%m%d'), B.IC_CODE							\n");
			sb.append("               )A GROUP BY A.MD_DATE ORDER BY A.MD_DATE ASC										\n");
			sb.append("        )B ON A.MD_DATE = B.MD_DATE ORDER BY A.MD_DATE ASC										\n");
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			
			String md_date = "";
			String positive = "";
			String negative = "";
			String neutral = "";
			
			while( rs.next() ) {
				
				if(md_date.equals("")){
					md_date = rs.getString("MD_DATE");
				}else{
					md_date += "," + rs.getString("MD_DATE");
				}
				
				if(positive.equals("")){
					positive = rs.getString("POS");
				}else{
					positive += "," + rs.getString("POS");
				}
				
				if(negative.equals("")){
					negative = rs.getString("NEG");
				}else{
					negative += "," + rs.getString("NEG");
				}
				
				if(neutral.equals("")){
					neutral = rs.getString("NEU");
				}else{
					neutral += "," + rs.getString("NEU");
				}

			}
			arrResult.add(md_date);
			arrResult.add(positive);
			arrResult.add(negative);
			arrResult.add(neutral);
			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
}
