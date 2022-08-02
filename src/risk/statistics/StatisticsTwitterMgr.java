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

public class StatisticsTwitterMgr {
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private ResultSet rs2    = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = null;	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
    
    private StatisticsBean statisticsBean = null;
    
    private int totalCnt = 0;    
    public int getTotalCnt() {
		return totalCnt;
	}

	public String getTypeCodeSql(String sdate, String edate, String stime,  String etime, String typeCode){
    	
    	String result = "";
    	
    	if(!typeCode.equals("")){
    		String[] type = typeCode.split("@");
    		String[] code = null;
    		
    		StringBuffer sb = new StringBuffer();
    		sb.append("INNER JOIN (SELECT DISTINCT A.ID_SEQ\n");
    		sb.append("              FROM ISSUE_DATA A   \n");
    		sb.append("                 , ISSUE_DATA_CODE B   \n");
    		sb.append("             WHERE A.ID_SEQ = B.ID_SEQ        \n");
    		sb.append("               AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'        \n");
    		sb.append("               AND (               \n");
    		
    		for(int i =0; i < type.length; i++){
    			code = type[i].split(",");
    			if(i != 0){ sb.append(" OR ");}
    			sb.append(" (IC_TYPE="+code[0]+" AND IC_CODE="+code[1]+")\n");	
    		}
    		sb.append("                    )\n");
    		sb.append("             GROUP BY ID_SEQ \n");
    		sb.append("            HAVING COUNT(0)="+ type.length +")Z ON A.ID_SEQ = Z.ID_SEQ\n");
    		
    		
    		result = sb.toString();
    	}
    	
    	return result;
    }
    
    
    public ArrayList getMaxWriter(String sdate, String edate, String stime,  String etime, String type, String typeCode, String searchKey) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsTwitterSuperBean superBean = new StatisticsTwitterSuperBean();
		StatisticsTwitterSuperBean.WriterBean childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			
			sb.append("SELECT B.T_USER_ID														\n");
			sb.append("     , B.T_NAME															\n");
			sb.append("     , B.T_PROFILE_IMAGE													\n");
			sb.append("     , IF((SELECT COUNT(*) FROM ISSUE_TWITTER_IMPORT WHERE T_USER_ID = B.T_USER_ID) > 0,'Y','N') AS INTEREST \n");
			sb.append("     , B.T_FOLLOWERS														\n");
			sb.append("     , B.T_FOLLOWING														\n");
			sb.append("     , B.T_TWEET															\n");
			sb.append("     , COUNT(*) AS CNT													\n");
			sb.append("     , SUM(IF(C.IC_CODE = 1,1,0)) AS POS									\n");
			sb.append("     , SUM(IF(C.IC_CODE = 2,1,0)) AS NEG									\n");
			sb.append("     , SUM(IF(C.IC_CODE = 3,1,0)) AS NEU									\n");
			sb.append("  FROM ISSUE_DATA A														\n");
			
			//분류체계검색
			sb.append(getTypeCodeSql(sdate, edate, stime, etime, typeCode) + "\n");
			
			sb.append("     , ISSUE_TWITTER B													\n");
			sb.append("     , ISSUE_DATA_CODE C													\n");
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ												\n");
			sb.append("   AND A.ID_SEQ = C.ID_SEQ												\n");
			sb.append("   AND C.IC_TYPE = 9														\n");
			sb.append("   AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'	\n");
			
			// 키워드 검색
			if( !searchKey.equals("") ) {
				String[] arrSchKey = searchKey.split(" ");
				for(int i =0; i < arrSchKey.length; i++){
					sb.append("		AND	A.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  					\n");
				}
			}
			
			
			if(type.equals("ptwit")){
				sb.append(" AND B.T_IS_RT = 'T'  \n");	
			}
			
			sb.append(" GROUP BY B.T_USER_ID													\n");
			
			if(type.equals("max")){
				sb.append(" ORDER BY COUNT(*) DESC  \n");	
			}else if(type.equals("ptwit")){
				sb.append(" ORDER BY COUNT(*) DESC  \n");
			}else if(type.equals("tweet")){
				sb.append(" ORDER BY B.T_TWEET DESC  \n");
			}else if(type.equals("followers")){
				sb.append(" ORDER BY B.T_FOLLOWERS DESC  \n");
			}
			sb.append(" LIMIT 10																\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
				
				childBean = superBean.new WriterBean();
				childBean.setT_user_id(rs.getString("T_USER_ID"));
				childBean.setT_name(rs.getString("T_NAME"));
				childBean.setT_profile_image(rs.getString("T_PROFILE_IMAGE"));
				childBean.setInterest(rs.getString("INTEREST"));
				childBean.setT_followers(rs.getString("T_FOLLOWERS"));
				childBean.setT_following(rs.getString("T_FOLLOWING"));
				childBean.setT_tweet(rs.getString("T_TWEET"));
				childBean.setCnt(rs.getString("CNT"));
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
    
    public ArrayList getMaxWriter_v2(String sdate, String edate, String stime,  String etime, String type, String typeCode, String searchKey, String createType, String inter) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsTwitterSuperBean superBean = new StatisticsTwitterSuperBean();
		StatisticsTwitterSuperBean.WriterBean childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			
			sb.append("SELECT B.T_USER_ID														\n");
			sb.append("     , B.T_NAME															\n");
			sb.append("     , B.T_PROFILE_IMAGE													\n");
			sb.append("     , IF((SELECT COUNT(*) FROM ISSUE_TWITTER_IMPORT WHERE T_USER_ID = B.T_USER_ID) > 0,'Y','N') AS INTEREST \n");
			sb.append("     , B.T_FOLLOWERS														\n");
			sb.append("     , B.T_FOLLOWING														\n");
			sb.append("     , B.T_TWEET															\n");
			sb.append("     , COUNT(*) AS CNT													\n");
			sb.append("     , SUM(IF(C.IC_CODE = 1,1,0)) AS POS									\n");
			sb.append("     , SUM(IF(C.IC_CODE = 2,1,0)) AS NEG									\n");
			sb.append("     , SUM(IF(C.IC_CODE = 3,1,0)) AS NEU									\n");
			sb.append("  FROM ISSUE_DATA A														\n");
			
			//분류체계검색
			sb.append(getTypeCodeSql(sdate, edate, stime, etime, typeCode) + "\n");
			
			sb.append("     , ISSUE_TWITTER B													\n");
			sb.append("     , ISSUE_DATA_CODE C													\n");
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ												\n");
			sb.append("   AND A.ID_SEQ = C.ID_SEQ												\n");
			sb.append("   AND C.IC_TYPE = 9														\n");
			sb.append("   AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'	\n");
			
			//생성기준
			if(!createType.equals("")){
				sb.append("   AND B.T_IS_RT = '"+createType+"'																				\n");
			}
			
			// 키워드 검색
			if( !searchKey.equals("") ) {
				String[] arrSchKey = searchKey.split(" ");
				for(int i =0; i < arrSchKey.length; i++){
					sb.append("		AND	A.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  					\n");
				}
			}
			
			
			//관심정보
			if(!inter.equals("")){
				if(inter.equals("Y")){
					sb.append("   AND EXISTS (SELECT 1 FROM ISSUE_TWITTER_IMPORT WHERE T_USER_ID = B.T_USER_ID)				\n");
				}else{
					sb.append("   AND NOT EXISTS (SELECT 1 FROM ISSUE_TWITTER_IMPORT WHERE T_USER_ID = B.T_USER_ID)				\n");
				}
			}
			
			sb.append(" GROUP BY B.T_USER_ID													\n");
			
			if(type.equals("max")){
				sb.append(" ORDER BY COUNT(*) DESC  \n");	
			}else if(type.equals("tweet")){
				sb.append(" ORDER BY B.T_TWEET DESC  \n");
			}else if(type.equals("followers")){
				sb.append(" ORDER BY B.T_FOLLOWERS DESC  \n");
			}
			sb.append(" LIMIT 10																\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			rs.last();
			int length = rs.getRow();
			rs.beforeFirst();
			
			StatisticsTwitterSuperBean.WriterBean[] arrWriter = new StatisticsTwitterSuperBean.WriterBean[length]; 
			
			
			int cnt = 0;
			String steamUserId = "";
			while( rs.next() ) {
				
				childBean = superBean.new WriterBean();
				childBean.setT_user_id(rs.getString("T_USER_ID"));
				childBean.setT_name(rs.getString("T_NAME"));
				childBean.setCnt(rs.getString("CNT"));
				childBean.setPositive(rs.getString("POS"));
				childBean.setNegative(rs.getString("NEG"));
				childBean.setNeutral(rs.getString("NEU"));
				childBean.setInterest(rs.getString("INTEREST"));
				childBean.setT_followers(rs.getString("T_FOLLOWERS"));
				childBean.setT_following(rs.getString("T_FOLLOWING"));
				childBean.setT_tweet(rs.getString("T_TWEET"));
				childBean.setT_profile_image(rs.getString("T_PROFILE_IMAGE"));
				
				arrWriter[cnt++] =  childBean;
			
				if(steamUserId.equals("")){
					steamUserId = "'"+ rs.getString("T_USER_ID") + "'";
				}else{
					steamUserId += "," + "'" + rs.getString("T_USER_ID")+"'";
				}
				 
				
			}
			
			if(!steamUserId.equals("")){
				
				sb = new StringBuffer();
				sb.append("SELECT B.P_USER_ID														\n");
				sb.append("     , COUNT(*) RT_CNT													\n");
				sb.append("  FROM ISSUE_DATA A														\n");
				sb.append("     , ISSUE_TWITTER_PIONEER B											\n");
				sb.append(" WHERE A.ID_SEQ = B.ID_SEQ 												\n");
				sb.append("   AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'	\n");	
				sb.append("   AND P_USER_ID IN ("+steamUserId+") 									\n");
				sb.append(" GROUP BY  P_USER_ID														\n");
				
				
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				
				rs.last();
				length = rs.getRow();
				rs.beforeFirst();
				
				StatisticsTwitterSuperBean.WriterBean[] arrWriter2 = new StatisticsTwitterSuperBean.WriterBean[length];
				cnt = 0;
				while( rs.next() ) {
					
					childBean = superBean.new WriterBean();
					childBean.setT_user_id(rs.getString("P_USER_ID"));
					childBean.setRt_cnt(rs.getString("RT_CNT"));
					arrWriter2[cnt++] =  childBean;
				}
				
				
				
				for(int i = 0; i < arrWriter.length; i++){
					for(int j = 0; j < arrWriter2.length; j++){
						if(arrWriter[i].getT_user_id().equals(arrWriter2[j].getT_user_id())){
							arrWriter[i].setRt_cnt(arrWriter2[j].getRt_cnt());
						}
					}
				}
				
				
				for(int i =0; i < arrWriter.length; i++){
					arrResult.add(arrWriter[i]);
				}
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
    
    public ArrayList getPioneerWriter(String sdate, String edate, String stime, String etime, String typeCode, String searchKey) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsTwitterSuperBean superBean = new StatisticsTwitterSuperBean();
		StatisticsTwitterSuperBean.WriterBean childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT B.P_USER_ID														\n");
			sb.append("     , B.P_NAME															\n");
			sb.append("     , COUNT(*) AS CNT													\n");
			sb.append("     , SUM(IF(C.IC_CODE = 1,1,0)) AS POS									\n");
			sb.append("     , SUM(IF(C.IC_CODE = 2,1,0)) AS NEG									\n");
			sb.append("     , SUM(IF(C.IC_CODE = 3,1,0)) AS NEU									\n");
			sb.append("     , IF((SELECT COUNT(*) FROM ISSUE_TWITTER_IMPORT WHERE T_USER_ID = B.P_USER_ID) > 0,'Y','N') AS INTEREST \n");
			sb.append("  FROM ISSUE_DATA A														\n");
			
			//분류체계검색
			sb.append(getTypeCodeSql(sdate, edate, stime, etime, typeCode) + "\n");
			
			sb.append("     , ISSUE_TWITTER_PIONEER B											\n");
			sb.append("     , ISSUE_DATA_CODE C													\n");
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ												\n");
			sb.append("   AND A.ID_SEQ = C.ID_SEQ												\n");
			sb.append("   AND C.IC_TYPE = 9														\n");
			sb.append("   AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'	\n");
			
			// 키워드 검색
			if( !searchKey.equals("") ) {
				String[] arrSchKey = searchKey.split(" ");
				for(int i =0; i < arrSchKey.length; i++){
					sb.append("		AND	A.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  					\n");
				}
			}
			
			sb.append(" GROUP BY B.P_USER_ID													\n");
			sb.append(" ORDER BY COUNT(*) DESC  												\n");
			sb.append(" LIMIT 10  																\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			rs.last();
			int length = rs.getRow();
			rs.beforeFirst();
			
			StatisticsTwitterSuperBean.WriterBean[] arrWriter = new StatisticsTwitterSuperBean.WriterBean[length]; 
			
			
			int cnt = 0;
			String steamUserId = "";
			while( rs.next() ) {
				
				childBean = superBean.new WriterBean();
				childBean.setT_user_id(rs.getString("P_USER_ID"));
				childBean.setT_name(rs.getString("P_NAME"));
				childBean.setCnt(rs.getString("CNT"));
				childBean.setPositive(rs.getString("POS"));
				childBean.setNegative(rs.getString("NEG"));
				childBean.setNeutral(rs.getString("NEU"));
				childBean.setInterest(rs.getString("INTEREST"));
				
				arrWriter[cnt++] =  childBean;
			
				if(steamUserId.equals("")){
					steamUserId = "'"+ rs.getString("P_USER_ID") + "'";
				}else{
					steamUserId += "," + "'" + rs.getString("P_USER_ID")+"'";
				}
				 
				
			}
			
			if(!steamUserId.equals("")){
				
				sb = new StringBuffer();
				sb.append("SELECT DISTINCT T_USER_ID		\n");
				sb.append("     , T_FOLLOWERS				\n");
				sb.append("     , T_FOLLOWING				\n");
				sb.append("     , T_TWEET 					\n");
				sb.append("     , T_PROFILE_IMAGE			\n");
				sb.append("  FROM (SELECT T_USER_ID			\n");
				sb.append("             , T_FOLLOWERS		\n");
				sb.append("             , T_FOLLOWING		\n");
				sb.append("             , T_TWEET			\n");
				sb.append("             , T_PROFILE_IMAGE	\n");
				sb.append("          FROM ISSUE_TWITTER 	\n");
				sb.append("         WHERE T_USER_ID IN ("+steamUserId+") 	\n");
				sb.append("         ORDER BY ID_SEQ DESC)A	\n");
				
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				
				rs.last();
				length = rs.getRow();
				rs.beforeFirst();
				
				StatisticsTwitterSuperBean.WriterBean[] arrWriter2 = new StatisticsTwitterSuperBean.WriterBean[length];
				cnt = 0;
				while( rs.next() ) {
					
					childBean = superBean.new WriterBean();
					childBean.setT_user_id(rs.getString("T_USER_ID"));
					childBean.setT_followers(rs.getString("T_FOLLOWERS"));
					childBean.setT_following(rs.getString("T_FOLLOWING"));
					childBean.setT_tweet(rs.getString("T_TWEET"));
					childBean.setT_profile_image(rs.getString("T_PROFILE_IMAGE"));
					
					arrWriter2[cnt++] =  childBean;
				}
				
				
				
				for(int i = 0; i < arrWriter.length; i++){
					for(int j = 0; j < arrWriter2.length; j++){
						if(arrWriter[i].getT_user_id().equals(arrWriter2[j].getT_user_id())){
							arrWriter[i].setT_followers(arrWriter2[j].getT_followers());
							arrWriter[i].setT_following(arrWriter2[j].getT_following());
							arrWriter[i].setT_tweet(arrWriter2[j].getT_tweet());
							arrWriter[i].setT_profile_image(arrWriter2[j].getT_profile_image());
						}
					}
				}
				
				
				for(int i =0; i < arrWriter.length; i++){
					arrResult.add(arrWriter[i]);
				}
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
    
    
    public ArrayList getPioneerWriter_v2(String sdate, String edate, String stime, String etime, String typeCode, String searchKey, String createType, String inter) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsTwitterSuperBean superBean = new StatisticsTwitterSuperBean();
		StatisticsTwitterSuperBean.WriterBean childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT B.T_USER_ID																							\n");
			sb.append("     , B.T_NAME																								\n");
			sb.append("     , C.RT_CNT																								\n");
			sb.append("     , COUNT(*) AS CNT																						\n");
			sb.append("     , SUM(IF(D.IC_CODE = 1,1,0)) AS POS																		\n");									
			sb.append("     , SUM(IF(D.IC_CODE = 2,1,0)) AS NEG																		\n");								
			sb.append("     , SUM(IF(D.IC_CODE = 3,1,0)) AS NEU																		\n");							
			sb.append("     , IF((SELECT COUNT(*) FROM ISSUE_TWITTER_IMPORT WHERE T_USER_ID = B.T_USER_ID) > 0,'Y','N') AS INTEREST	\n"); 
			sb.append("     , MAX(B.T_FOLLOWERS) AS T_FOLLOWERS																		\n");
			sb.append("     , MAX(B.T_FOLLOWING) AS T_FOLLOWING																		\n");
			sb.append("     , MAX(B.T_TWEET) AS T_TWEET																				\n");
			sb.append("     , MAX(B.T_PROFILE_IMAGE) AS T_PROFILE_IMAGE																\n");
			sb.append("  FROM ISSUE_DATA A																							\n");

			//분류체계검색
			sb.append(getTypeCodeSql(sdate, edate, stime, etime, typeCode) + "\n");
			
			sb.append("     , ISSUE_TWITTER B																						\n");
			sb.append("     , (SELECT B.P_USER_ID																					\n");													
			sb.append("             , COUNT(*) AS RT_CNT																			\n");													
			sb.append("          FROM ISSUE_DATA A																					\n");										
			sb.append("             , ISSUE_TWITTER_PIONEER B																		\n");									
			sb.append("         WHERE A.ID_SEQ = B.ID_SEQ																			\n");																			
			sb.append("           AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'								\n");	
			sb.append("         GROUP BY B.P_USER_ID																				\n");
			sb.append("         ORDER BY COUNT(*) DESC)C																			\n");
			sb.append("     , ISSUE_DATA_CODE D																						\n");
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ																					\n");
			sb.append("   AND A.ID_SEQ = D.ID_SEQ																					\n");
			sb.append("   AND B.T_USER_ID = C.P_USER_ID																				\n");
			
			//생성기준
			if(!createType.equals("")){
				sb.append("   AND B.T_IS_RT = '"+createType+"'																				\n");
			}
			
			sb.append("   AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'										\n");	
			sb.append("   AND D.IC_TYPE = 9																							\n");
			
			// 키워드 검색
			if( !searchKey.equals("") ) {
				String[] arrSchKey = searchKey.split(" ");
				for(int i =0; i < arrSchKey.length; i++){
					sb.append("		AND	A.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  					\n");
				}			
			}
			
			//관심정보
			if(!inter.equals("")){
				if(inter.equals("Y")){
					sb.append("   AND EXISTS (SELECT 1 FROM ISSUE_TWITTER_IMPORT WHERE T_USER_ID = B.T_USER_ID)				\n");
				}else{
					sb.append("   AND NOT EXISTS (SELECT 1 FROM ISSUE_TWITTER_IMPORT WHERE T_USER_ID = B.T_USER_ID)				\n");
				}
			}
			
			sb.append(" GROUP BY T_USER_ID																							\n");
			sb.append(" ORDER BY C.RT_CNT DESC, T_FOLLOWERS DESC																	\n");
			sb.append(" LIMIT 10																									\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
				
				childBean = superBean.new WriterBean();
				childBean.setT_user_id(rs.getString("T_USER_ID"));
				childBean.setT_name(rs.getString("T_NAME"));
				childBean.setT_profile_image(rs.getString("T_PROFILE_IMAGE"));
				childBean.setInterest(rs.getString("INTEREST"));
				childBean.setT_followers(rs.getString("T_FOLLOWERS"));
				childBean.setT_following(rs.getString("T_FOLLOWING"));
				childBean.setT_tweet(rs.getString("T_TWEET"));
				childBean.setCnt(rs.getString("CNT"));
				childBean.setPositive(rs.getString("POS"));
				childBean.setNegative(rs.getString("NEG"));
				childBean.setNeutral(rs.getString("NEU"));
				childBean.setRt_cnt(rs.getString("RT_CNT"));
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
    
    
    public ArrayList getPioneerWriter_date(ArrayList data, String sdate, String edate, String stime, String etime, String type, String typeCode, String searchKey, String createType) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsTwitterSuperBean superBean = new StatisticsTwitterSuperBean();
		StatisticsTwitterSuperBean.WriterBean wBean = null;
		String[] arr_Top = null;
		
		String streamStr = "";
		for(int i = 0; i < data.size(); i++){
			wBean = (StatisticsTwitterSuperBean.WriterBean) data.get(i);
			if(streamStr.equals("")){
				streamStr = "'" + wBean.getT_user_id() + "'";  
			}else{
				streamStr += "," + "'" + wBean.getT_user_id() + "'";
			}
		}
		
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			
			sb.append("SELECT 0 AS MD_DATE																\n");
			
			for(int i = 0; i < data.size(); i++){
				wBean = (StatisticsTwitterSuperBean.WriterBean) data.get(i);
				
			sb.append("     , '"+wBean.getT_user_id()+"' AS TOP"+(i+1)+"								\n");
			}
			sb.append(" UNION ALL																		\n");
			sb.append("SELECT A.MD_DATE																	\n");
			
			for(int i = 0; i < data.size(); i++){
				wBean = (StatisticsTwitterSuperBean.WriterBean) data.get(i);
			sb.append("     , SUM(IF(T_USER_ID = '"+wBean.getT_user_id()+"', CNT, 0)) AS TOP"+(i+1)+"	\n");
			}
			
			sb.append("  FROM (																			\n");
			
			sb.append("        SELECT DATE_FORMAT(MD_DATE,'%Y%m%d') AS MD_DATE							\n");
			sb.append("             , T_USER_ID	AS T_USER_ID											\n"); 
			sb.append("             , COUNT(*) AS CNT													\n");
			sb.append("          FROM ISSUE_DATA A														\n");
			
			//분류체계검색
			sb.append(getTypeCodeSql(sdate, edate, stime, etime, typeCode) + "\n");
			
			sb.append("             , ISSUE_TWITTER B											\n");
			sb.append("         WHERE A.ID_SEQ = B.ID_SEQ												\n");
			sb.append("           AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'	\n"); 
			sb.append("           AND B.T_USER_ID IN ("+streamStr+")									\n");
			
			//생성기준
			if(!createType.equals("")){
				sb.append("   AND B.T_IS_RT = '"+createType+"'																				\n");
			}
			
			// 키워드 검색
			if( !searchKey.equals("") ) {
				String[] arrSchKey = searchKey.split(" ");
				for(int i =0; i < arrSchKey.length; i++){
					sb.append("		AND	A.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  					\n");
				}
			}
			
			sb.append("         GROUP BY DATE_FORMAT(MD_DATE,'%Y%m%d'), T_USER_ID 						\n");
			
			
			sb.append("		   )A GROUP BY A.MD_DATE DESC												\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			
			
			
			while( rs.next() ) {
				
				arr_Top = new String[data.size() + 1];
				
				arr_Top[0] = rs.getString("MD_DATE");
				for(int i = 1; i < arr_Top.length; i++){
					arr_Top[i] = rs.getString("TOP" + i);
				}
				arrResult.add(arr_Top);
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
    
public ArrayList getBestRt(String sdate, String edate, String stime, String etime, String typeCode, String searchKey) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsTwitterSuperBean superBean = new StatisticsTwitterSuperBean();
		StatisticsTwitterSuperBean.WriterBean childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT B.P_USER_ID														\n");
			sb.append("     , B.P_NAME															\n");
			sb.append("     , B.P_TITLE															\n");
			sb.append("     , A.ID_URL															\n");
			sb.append("     , COUNT(*) AS CNT													\n");
			sb.append("     , SUM(IF(C.IC_CODE = 1,1,0)) AS POS									\n");									
			sb.append("     , SUM(IF(C.IC_CODE = 2,1,0)) AS NEG									\n");									
			sb.append("     , SUM(IF(C.IC_CODE = 3,1,0)) AS NEU									\n");
			sb.append("     , IF((SELECT COUNT(*) FROM ISSUE_TWITTER_IMPORT WHERE T_USER_ID = B.P_USER_ID) > 0,'Y','N') AS INTEREST \n");
			sb.append("     , SUM(D.T_FOLLOWERS) AS FULL_FOLLOWERS								\n");
			sb.append("  FROM ISSUE_DATA A														\n");
			
			//분류체계검색
			sb.append(getTypeCodeSql(sdate, edate, stime, etime, typeCode) + "\n");
			
			sb.append("     , ISSUE_TWITTER_PIONEER B											\n");
			sb.append("     , ISSUE_DATA_CODE C													\n");
			sb.append("     , ISSUE_TWITTER D													\n");
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ												\n");
			sb.append("   AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'	\n");	
			sb.append("   AND A.ID_SEQ = C.ID_SEQ												\n");
			sb.append("   AND C.IC_TYPE = 9														\n");
			
			// 키워드 검색
			if( !searchKey.equals("") ) {
				String[] arrSchKey = searchKey.split(" ");
				for(int i =0; i < arrSchKey.length; i++){
					sb.append("		AND	A.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  					\n");
				}
			}
			sb.append("   AND A.ID_SEQ = D.ID_SEQ												\n");
			sb.append(" GROUP BY LEFT(P_TITLE,100)												\n");
			sb.append(" ORDER BY COUNT(*) DESC													\n");
			sb.append(" LIMIT 10																\n");
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			rs.last();
			int length = rs.getRow();
			rs.beforeFirst();
			
			StatisticsTwitterSuperBean.WriterBean[] arrWriter = new StatisticsTwitterSuperBean.WriterBean[length]; 
			
			
			int cnt = 0;
			String steamUserId = "";
			while( rs.next() ) {
				
				childBean = superBean.new WriterBean();
				childBean.setT_user_id(rs.getString("P_USER_ID"));
				childBean.setT_name(rs.getString("P_NAME"));
				childBean.setT_title(rs.getString("P_TITLE"));
				childBean.setId_url(rs.getString("ID_URL"));
				childBean.setCnt(rs.getString("CNT"));
				childBean.setPositive(rs.getString("POS"));
				childBean.setNegative(rs.getString("NEG"));
				childBean.setNeutral(rs.getString("NEU"));
				childBean.setInterest(rs.getString("INTEREST"));
				childBean.setFull_followers(rs.getString("FULL_FOLLOWERS"));
				
				arrWriter[cnt++] =  childBean;
			
				if(steamUserId.equals("")){
					steamUserId = "'"+ rs.getString("P_USER_ID") + "'";
				}else{
					steamUserId += "," + "'" + rs.getString("P_USER_ID")+"'";
				}
				 
				
			}
			
			if(!steamUserId.equals("")){
				
				sb = new StringBuffer();
				sb.append("SELECT DISTINCT T_USER_ID		\n");
				sb.append("     , T_FOLLOWERS				\n");
				sb.append("     , T_FOLLOWING				\n");
				sb.append("     , T_TWEET 					\n");
				sb.append("     , T_PROFILE_IMAGE			\n");
				sb.append("  FROM (SELECT T_USER_ID			\n");
				sb.append("             , T_FOLLOWERS		\n");
				sb.append("             , T_FOLLOWING		\n");
				sb.append("             , T_TWEET			\n");
				sb.append("             , T_PROFILE_IMAGE	\n");
				sb.append("          FROM ISSUE_TWITTER 	\n");
				sb.append("         WHERE T_USER_ID IN ("+steamUserId+") 	\n");
				sb.append("         ORDER BY ID_SEQ DESC)A	\n");
				
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				
				rs.last();
				length = rs.getRow();
				rs.beforeFirst();
				
				StatisticsTwitterSuperBean.WriterBean[] arrWriter2 = new StatisticsTwitterSuperBean.WriterBean[length];
				cnt = 0;
				while( rs.next() ) {
					
					childBean = superBean.new WriterBean();
					childBean.setT_user_id(rs.getString("T_USER_ID"));
					childBean.setT_followers(rs.getString("T_FOLLOWERS"));
					childBean.setT_following(rs.getString("T_FOLLOWING"));
					childBean.setT_tweet(rs.getString("T_TWEET"));
					childBean.setT_profile_image(rs.getString("T_PROFILE_IMAGE"));
					
					arrWriter2[cnt++] =  childBean;
				}
				
				
				
				for(int i = 0; i < arrWriter.length; i++){
					for(int j = 0; j < arrWriter2.length; j++){
						if(arrWriter[i].getT_user_id().equals(arrWriter2[j].getT_user_id())){
							arrWriter[i].setT_followers(arrWriter2[j].getT_followers());
							arrWriter[i].setT_following(arrWriter2[j].getT_following());
							arrWriter[i].setT_tweet(arrWriter2[j].getT_tweet());
							arrWriter[i].setT_profile_image(arrWriter2[j].getT_profile_image());
						}
					}
				}
				
				
				for(int i =0; i < arrWriter.length; i++){
					arrResult.add(arrWriter[i]);
				}
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

	public ArrayList getBestRt_date(ArrayList data, String sdate, String edate, String stime, String etime, String typeCode, String searchKey) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsTwitterSuperBean superBean = new StatisticsTwitterSuperBean();
		StatisticsTwitterSuperBean.WriterBean wBean = null;
		String[] arr_Top = null;
		
		String streamStr = "";
		for(int i = 0; i < data.size(); i++){
			wBean = (StatisticsTwitterSuperBean.WriterBean) data.get(i);
			if(streamStr.equals("")){
				streamStr = "'" + wBean.getT_user_id() + "'";  
			}else{
				streamStr += "," + "'" + wBean.getT_user_id() + "'";
			}
		}
		
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			
			sb.append("SELECT 0 AS MD_DATE																\n");
			
			for(int i = 0; i < data.size(); i++){
				wBean = (StatisticsTwitterSuperBean.WriterBean) data.get(i);
				
			sb.append("     , '"+wBean.getT_user_id()+"' AS TOP"+(i+1)+"								\n");
			}
			sb.append(" UNION ALL																		\n");
			sb.append("SELECT A.MD_DATE																	\n");
			
			for(int i = 0; i < data.size(); i++){
				wBean = (StatisticsTwitterSuperBean.WriterBean) data.get(i);
			sb.append("     , SUM(IF(T_USER_ID = '"+wBean.getT_user_id()+"', CNT, 0)) AS TOP"+(i+1)+"	\n");
			}
			
			sb.append("  FROM (																			\n");
				
				sb.append("SELECT DATE_FORMAT(MD_DATE,'%Y%m%d') AS MD_DATE							\n");							
				sb.append("     , B.P_USER_ID	AS T_USER_ID										\n");											
				sb.append("     , COUNT(*) AS CNT													\n");
				sb.append("  FROM ISSUE_DATA A														\n");
				
				//분류체계검색
				sb.append(getTypeCodeSql(sdate, edate, stime, etime, typeCode) + "\n");
				
				sb.append("     , ISSUE_TWITTER_PIONEER B											\n");
				sb.append(" WHERE A.ID_SEQ = B.ID_SEQ												\n");
				sb.append("   AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'	\n");	
				sb.append("   AND B.P_USER_ID IN ("+streamStr+")									\n");
				
				// 키워드 검색
				if( !searchKey.equals("") ) {
					String[] arrSchKey = searchKey.split(" ");
					for(int i =0; i < arrSchKey.length; i++){
						sb.append("		AND	A.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  					\n");
					}
				}
				
				sb.append(" GROUP BY DATE_FORMAT(MD_DATE,'%Y%m%d'), LEFT(P_TITLE,100)				\n");
			
			
			sb.append("		   )A GROUP BY A.MD_DATE DESC												\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			
			
			
			while( rs.next() ) {
				
				arr_Top = new String[data.size() + 1];
				
				arr_Top[0] = rs.getString("MD_DATE");
				for(int i = 1; i < arr_Top.length; i++){
					arr_Top[i] = rs.getString("TOP" + i);
				}
				arrResult.add(arr_Top);
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
	
	
	public int insertImportTwitter(StatisticsTwitterSuperBean.ImportTwitterBean bean) {
		
		int result = 0;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("INSERT INTO ISSUE_TWITTER_IMPORT( T_USER_ID			\n");
			sb.append("                                , T_JOB				\n");
			sb.append("                                , T_MAIL				\n");
			sb.append("                                , T_IMPORT			\n");
			sb.append("                                , T_MEMO				\n");
			sb.append("                                , T_NAME				\n");
			sb.append("                                , T_PROFILE_IMAGE	\n");
			sb.append("                                , T_REGDATE			\n");
			sb.append("                        )VALUES ( ?					\n");
			sb.append("                                , ?					\n");
			sb.append("                                , ?					\n");
			sb.append("                                , ?					\n");
			sb.append("                                , ?					\n");
			sb.append("                                , ?					\n");
			sb.append("                                , ?					\n");
			sb.append("                                , NOW()				\n");
			sb.append("								   )					\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			
			int idx = 0;
			pstmt.setString(++idx,bean.getT_user_id());
			pstmt.setString(++idx,bean.getT_job());
			pstmt.setString(++idx,bean.getT_mail());
			pstmt.setString(++idx,bean.getT_import());
			pstmt.setString(++idx,bean.getT_memo());
			pstmt.setString(++idx,bean.getT_name());
			pstmt.setString(++idx,bean.getT_profile_image());
			
			result = pstmt.executeUpdate();
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
    
	
	public int UpdateImportTwitter(StatisticsTwitterSuperBean.ImportTwitterBean bean) {
		
		int result = 0;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("UPDATE ISSUE_TWITTER_IMPORT SET T_JOB = ?			\n");
			sb.append("                              , T_MAIL = ?			\n");
			sb.append("                              , T_IMPORT = ?			\n");
			sb.append("                              , T_MEMO = ?			\n");
			sb.append("                              , T_REGDATE = NOW()	\n");
			sb.append("                          WHERE T_USER_ID = ?		\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			
			int idx = 0;
			
			pstmt.setString(++idx,bean.getT_job());
			pstmt.setString(++idx,bean.getT_mail());
			pstmt.setString(++idx,bean.getT_import());
			pstmt.setString(++idx,bean.getT_memo());
			pstmt.setString(++idx,bean.getT_user_id());
			
			
			result = pstmt.executeUpdate();
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	public ArrayList getinterestData(int pageNum, int rowCnt) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsTwitterSuperBean superBean = new StatisticsTwitterSuperBean();
		StatisticsTwitterSuperBean.WriterBean childBean = null;
		
		try {
			
			int startNum =0;
			int endNum =0;
			
			//리스트 시작, 끝번호 
			if(rowCnt>0) startNum =(pageNum-1)*rowCnt;
			endNum = rowCnt;
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT COUNT(*) AS CNT FROM ISSUE_TWITTER_IMPORT \n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if( rs.next() ) {
				totalCnt  = rs.getInt("CNT");
			}
				
			rs.close();
			
			sb = new StringBuffer();
			sb.append("SELECT B.T_USER_ID									\n");
			sb.append("     , B.T_NAME										\n");
			sb.append("     , B.T_PROFILE_IMAGE								\n");
			sb.append("     , B.T_FOLLOWERS									\n");											
			sb.append("     , B.T_FOLLOWING									\n");										
			sb.append("     , B.T_TWEET										\n");
			sb.append("     , COUNT(*) AS CNT								\n");
			sb.append("     , SUM(IF(A.IC_CODE = 1,1,0)) AS POS				\n");									
			sb.append("     , SUM(IF(A.IC_CODE = 2,1,0)) AS NEG				\n");							
			sb.append("     , SUM(IF(A.IC_CODE = 3,1,0)) AS NEU				\n");
			sb.append("     , C.T_IMPORT									\n");
			sb.append("     , C.T_JOB										\n");
			sb.append("  FROM ISSUE_DATA_CODE A								\n");
			sb.append("     , ISSUE_TWITTER B								\n");
			sb.append("     , ISSUE_TWITTER_IMPORT C						\n");
			sb.append(" WHERE B.T_USER_ID = C.T_USER_ID						\n");
			sb.append("   AND A.ID_SEQ = B.ID_SEQ							\n");
			sb.append("   AND A.IC_TYPE = 9									\n");
			sb.append(" GROUP BY B.T_USER_ID								\n");
			sb.append(" ORDER BY C.T_IMPORT , CNT DESC, B.T_FOLLOWERS DESC	\n");
			sb.append(" LIMIT "+startNum+","+endNum+"						\n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
				
				childBean = superBean.new WriterBean();
				childBean.setT_user_id(rs.getString("T_USER_ID"));
				childBean.setT_name(rs.getString("T_NAME"));
				childBean.setT_profile_image(rs.getString("T_PROFILE_IMAGE"));
				childBean.setT_followers(rs.getString("T_FOLLOWERS"));
				childBean.setT_following(rs.getString("T_FOLLOWING"));
				childBean.setT_tweet(rs.getString("T_TWEET"));
				childBean.setCnt(rs.getString("CNT"));
				childBean.setPositive(rs.getString("POS"));
				childBean.setNegative(rs.getString("NEG"));
				childBean.setNeutral(rs.getString("NEU"));
				childBean.setT_import(rs.getString("T_IMPORT"));
				childBean.setT_job(rs.getString("T_JOB"));
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
	
	
	
	public StatisticsTwitterSuperBean.ImportTwitterBean getInterest(String userId) {
		
		StatisticsTwitterSuperBean superBean = new StatisticsTwitterSuperBean();
		StatisticsTwitterSuperBean.ImportTwitterBean childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			
			sb.append("SELECT T_USER_ID					\n");
			sb.append("     , T_JOB						\n");
			sb.append("     , T_MAIL					\n");
			sb.append("     , T_MEMO					\n");
			sb.append("     , T_NAME					\n");
			sb.append("     , T_PROFILE_IMAGE			\n");
			sb.append("     , T_REGDATE					\n");
			sb.append("     , T_IMPORT					\n");
			sb.append("  FROM ISSUE_TWITTER_IMPORT		\n"); 
			sb.append(" WHERE T_USER_ID = '"+userId+"'	\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if( rs.next() ) {
				
				childBean = superBean.new ImportTwitterBean();
				childBean.setT_user_id(rs.getString("T_USER_ID"));
				childBean.setT_job(rs.getString("T_JOB"));
				childBean.setT_mail(rs.getString("T_MAIL"));
				childBean.setT_memo(rs.getString("T_MEMO"));
				childBean.setT_name(rs.getString("T_NAME"));
				childBean.setT_profile_image(rs.getString("T_PROFILE_IMAGE"));
				childBean.setT_regdate(rs.getString("T_REGDATE"));
				childBean.setT_import(rs.getString("T_IMPORT"));
				
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
		return childBean;
	}

	public int DeleteInterest(String users) {
		
		int result = 0;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			String[] ar_user = users.split(",");
			
			for(int i =0; i <ar_user.length; i++){
				sb = new StringBuffer();
				sb.append(" DELETE FROM ISSUE_TWITTER_IMPORT WHERE T_USER_ID = '"+ar_user[i]+"'\n");
				System.out.println(sb.toString());
				stmt.executeUpdate(sb.toString());
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
		return result;
	}
	
	public ArrayList getTwitterList(int pageNum, int rowCnt, String sdate, String edate, String stime, String etime, String typeCode, String searchKey, String user, String tendency, String getDate, String createType) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsTwitterSuperBean superBean = new StatisticsTwitterSuperBean();
		StatisticsTwitterSuperBean.TwitterList childBean = null;
		
		try {
			
			int startNum =0;
			int endNum =0;
			
			//리스트 시작, 끝번호 
			if(rowCnt>0) startNum =(pageNum-1)*rowCnt;
			endNum = rowCnt;
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT COUNT(*) AS CNT													\n");
			sb.append("  FROM ISSUE_DATA A 														\n");
			
			//분류체계검색
			sb.append(getTypeCodeSql(sdate, edate, stime, etime, typeCode) + "\n");
			
			sb.append("     , ISSUE_TWITTER B													\n");
			
			//성향검색
			if(!tendency.equals("")){
				sb.append("     INNER JOIN ISSUE_DATA_CODE C ON B.ID_SEQ = C.ID_SEQ AND IC_TYPE = 9 AND IC_CODE ="+tendency+"\n");
			}
			
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ 												\n");
			
			// 누적일때만 적용
			if(!sdate.equals("")){
				sb.append("   AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'	\n");
			}
			
			
			//일자별 그래프
			if(!getDate.equals("")){
				sb.append("   AND A.MD_DATE BETWEEN '"+du.getDate(getDate,"yyyy-MM-dd")+" 00:00:00' AND '"+du.getDate(getDate,"yyyy-MM-dd")+" 23:59:59'	\n");
			}
			
			sb.append("   AND T_USER_ID = '"+user+"' 											\n");
			
			// 키워드 검색
			if( !searchKey.equals("") ) {
				String[] arrSchKey = searchKey.split(" ");
				for(int i =0; i < arrSchKey.length; i++){
					sb.append("		AND	A.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  					\n");
				}
			}
			
			//생성기준
			if(!createType.equals("")){
				sb.append("   AND B.T_IS_RT = '"+createType+"'																				\n");
			}
		
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if ( rs.next() ) {
            	totalCnt  = rs.getInt("CNT");
            }
			
			rs.close();                     			
			sb = new StringBuffer();
			
			sb.append("SELECT A.*\n");
			sb.append("     , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 9) AS TYPE9									\n");
			sb.append("     , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 10) AS TYPE10								\n");
			sb.append("  FROM ( SELECT A.ID_SEQ																\n");
			
			sb.append("              , B.T_USER_ID															\n");
			sb.append("              , A.ID_TITLE															\n");
			sb.append("              , A.ID_URL																\n");
			sb.append("              , A.MD_DATE 															\n");
			sb.append("           FROM ISSUE_DATA A 														\n");
			
			//분류체계검색
			sb.append(getTypeCodeSql(sdate, edate, stime, etime, typeCode) + "\n");
			
			sb.append("              , ISSUE_TWITTER B														\n");
			
			//성향검색
			if(!tendency.equals("")){
				sb.append("     INNER JOIN ISSUE_DATA_CODE C ON B.ID_SEQ = C.ID_SEQ AND IC_TYPE = 9 AND IC_CODE ="+tendency+"\n");
			}
			sb.append("          WHERE A.ID_SEQ = B.ID_SEQ 													\n");
			
			// 누적일때만 적용
			if(!sdate.equals("")){
				sb.append("            AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'	\n");
			}
			
			//일자별 그래프
			if(!getDate.equals("")){
				sb.append("   AND A.MD_DATE BETWEEN '"+du.getDate(getDate,"yyyy-MM-dd")+" 00:00:00' AND '"+du.getDate(getDate,"yyyy-MM-dd")+" 23:59:59'	\n");
			}
			
			sb.append("            AND T_USER_ID = '"+user+"' 												\n");
			// 키워드 검색
			if( !searchKey.equals("") ) {
				String[] arrSchKey = searchKey.split(" ");
				for(int i =0; i < arrSchKey.length; i++){
					sb.append("		AND	A.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  					\n");
				}
			}
			
			//생성기준
			if(!createType.equals("")){
				sb.append("   AND B.T_IS_RT = '"+createType+"'																				\n");
			}
			
			sb.append("          LIMIT "+startNum+","+endNum+"												\n");
			sb.append("       )A ORDER BY MD_DATE DESC														\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while ( rs.next() ) {
				
				childBean = superBean.new TwitterList();
				
            	childBean.setId_seq(rs.getString("ID_SEQ"));
            	childBean.setT_user_id(rs.getString("T_USER_ID"));
            	childBean.setId_title(rs.getString("ID_TITLE"));
            	childBean.setId_url(rs.getString("ID_URL"));
            	childBean.setMd_date(rs.getString("MD_DATE"));
            	childBean.setType1(rs.getString("TYPE9"));
            	childBean.setType2(rs.getString("TYPE10"));
            	
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
	
	public ArrayList getTwitterList_RT(int pageNum, int rowCnt, String sdate, String edate, String stime, String etime, String typeCode, String searchKey, String user, String tendency, String getDate, String createType, String html) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsTwitterSuperBean superBean = new StatisticsTwitterSuperBean();
		StatisticsTwitterSuperBean.TwitterList childBean = null;
		
		try {
			
			int startNum =0;
			int endNum =0;
			
			//리스트 시작, 끝번호 
			if(rowCnt>0) startNum =(pageNum-1)*rowCnt;
			endNum = rowCnt;
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT COUNT(*) AS CNT													\n");
			sb.append("  FROM ISSUE_DATA A 														\n");
			
			//분류체계검색
			sb.append(getTypeCodeSql(sdate, edate, stime, etime, typeCode) + "\n");
			
			sb.append("     , ISSUE_TWITTER_PIONEER B											\n");
			sb.append("     , ISSUE_TWITTER C													\n");
			
			//성향검색
			if(!tendency.equals("")){
				sb.append("     INNER JOIN ISSUE_DATA_CODE C ON B.ID_SEQ = C.ID_SEQ AND IC_TYPE = 9 AND IC_CODE ="+tendency+"\n");
			}
			
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ 												\n");
			sb.append("   AND A.ID_SEQ = C.ID_SEQ 												\n");
			
			// 누적일때만 적용
			if(!sdate.equals("")){
				sb.append("   AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'	\n");
			}
			
			//일자별 그래프
			if(!getDate.equals("")){
				sb.append("   AND A.MD_DATE BETWEEN '"+du.getDate(getDate,"yyyy-MM-dd")+" 00:00:00' AND '"+du.getDate(getDate,"yyyy-MM-dd")+" 23:59:59'	\n");
			}
			
			//트위터 ID
			if(!user.equals("")){
				sb.append("   AND P_USER_ID = '"+user+"' 											\n");
			}
			
			// 키워드 검색
			if( !searchKey.equals("") ) {
				String[] arrSchKey = searchKey.split(" ");
				for(int i =0; i < arrSchKey.length; i++){
					sb.append("		AND	A.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  					\n");
				}
			}
			
			// 제목 검색
			if( !html.equals("") ) {
				sb.append("		AND	LEFT(B.P_TITLE,100) = LEFT('"+su.dbString(html)+"',100)  					\n");
			}
			
			
			
			//생성기준
			if(!createType.equals("")){
				sb.append("   AND B.T_IS_RT = '"+createType+"'																				\n");
			}
		
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if ( rs.next() ) {
            	totalCnt  = rs.getInt("CNT");
            }
			
			rs.close();                     			
			sb = new StringBuffer();
			
			sb.append("SELECT A.*\n");
			sb.append("     , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 9) AS TYPE9									\n");
			sb.append("     , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 10) AS TYPE10								\n");
			sb.append("  FROM ( SELECT A.ID_SEQ																\n");
			
			sb.append("              , C.T_USER_ID															\n");
			sb.append("              , A.ID_TITLE															\n");
			sb.append("              , A.ID_URL																\n");
			sb.append("              , A.MD_DATE 															\n");
			sb.append("           FROM ISSUE_DATA A 														\n");
			
			//분류체계검색
			sb.append(getTypeCodeSql(sdate, edate, stime, etime, typeCode) + "\n");
			
			sb.append("              , ISSUE_TWITTER_PIONEER B												\n");
			sb.append("              , ISSUE_TWITTER C													\n");
			//성향검색
			if(!tendency.equals("")){
				sb.append("     INNER JOIN ISSUE_DATA_CODE C ON B.ID_SEQ = C.ID_SEQ AND IC_TYPE = 9 AND IC_CODE ="+tendency+"\n");
			}
			sb.append("          WHERE A.ID_SEQ = B.ID_SEQ 													\n");
			sb.append("            AND A.ID_SEQ = C.ID_SEQ 												\n");
			
			// 누적일때만 적용
			if(!sdate.equals("")){
				sb.append("            AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'	\n");
			}
			
			//일자별 그래프
			if(!getDate.equals("")){
				sb.append("   AND A.MD_DATE BETWEEN '"+du.getDate(getDate,"yyyy-MM-dd")+" 00:00:00' AND '"+du.getDate(getDate,"yyyy-MM-dd")+" 23:59:59'	\n");
			}
			
			//트위터 ID
			if(!user.equals("")){
				sb.append("   AND P_USER_ID = '"+user+"' 											\n");
			}
			
			// 키워드 검색
			if( !searchKey.equals("") ) {
				String[] arrSchKey = searchKey.split(" ");
				for(int i =0; i < arrSchKey.length; i++){
					sb.append("		AND	A.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  					\n");
				}
			}
			
			// 제목 검색
			if( !html.equals("") ) {
				sb.append("		AND	LEFT(B.P_TITLE,100) = LEFT('"+su.dbString(html)+"',100)  					\n");
			}
			
			//생성기준
			if(!createType.equals("")){
				sb.append("   AND B.T_IS_RT = '"+createType+"'																				\n");
			}
			
			sb.append("          LIMIT "+startNum+","+endNum+"												\n");
			sb.append("       )A ORDER BY MD_DATE DESC														\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while ( rs.next() ) {
				
				childBean = superBean.new TwitterList();
				
            	childBean.setId_seq(rs.getString("ID_SEQ"));
            	childBean.setT_user_id(rs.getString("T_USER_ID"));
            	childBean.setId_title(rs.getString("ID_TITLE"));
            	childBean.setId_url(rs.getString("ID_URL"));
            	childBean.setMd_date(rs.getString("MD_DATE"));
            	childBean.setType1(rs.getString("TYPE9"));
            	childBean.setType2(rs.getString("TYPE10"));
            	
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
}
