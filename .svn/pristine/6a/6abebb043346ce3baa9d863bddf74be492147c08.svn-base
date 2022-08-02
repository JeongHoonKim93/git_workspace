package risk.report;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.ConfigUtil;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;
import sun.misc.BASE64Decoder;

public class ReportMgr_hycard {

	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private PreparedStatement pstmt = null;
	private int total_count = 0;
	StringUtil su = new StringUtil();
	
	public int getTotalCount() {
		return this.total_count;
	}
	
    int FullCnt = 0;
    public int getFullCnt() {
		return FullCnt;
	}
	
	public ArrayList getDailyTopIssueList_card(String ir_sdate, String ir_edate) {
		
		ArrayList arrResult = new ArrayList();
		Map dataMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();			
			sb.append(" ## 일일보고서 TOP 이슈 리스트	- 현대카드										\n");
			sb.append("SELECT A.* FROM (											\n");
			sb.append("SELECT ID.ID_SEQ												\n");
			sb.append("     , ID.ID_TITLE											\n");
			sb.append("     , ID.ID_URL												\n");
			sb.append("     , ID.S_SEQ											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= 14 ),'') AS SENTI_CODE #긍부정 		\n");
			sb.append("     , MD_DATE												\n");
			sb.append("     , DATE_FORMAT(MD_DATE,'%Y-%m-%d %H:%') AS DATE_FORMAT	\n");
			sb.append("     , MD_SITE_NAME										\n");
			sb.append("     , ID.ID_CONTENT											\n");
			sb.append("     , IDC.IC_CODE											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= 16 ),'') AS LEVEL					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 14) AS SENTI_NAME	#긍부정 			\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 16) AS LEVEL_NAME																\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 12) AS IC_NAME 			\n");
			sb.append("  FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC											\n");
			/*추가*/sb.append("     , ISSUE_DATA_CODE B											\n");
			sb.append(" WHERE ID.ID_SEQ = IDC.ID_SEQ								\n");
			/*추가*/sb.append("   AND ID.ID_SEQ = B.ID_SEQ									\n");
			sb.append(" AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간								\n");							
			sb.append(" AND SG_SEQ IN (95,97) #상단 검색 조건 - 채널 							\n");		
			sb.append(" AND IDC.IC_TYPE = 12 #(12:현카드 /22:현캐피/ 32:현커머)								\n");
			/*추가*/sb.append(" AND B.IC_TYPE = 14 #(14:현카드 /24:현캐피/ 34:현커머)								\n");
			/*추가*/sb.append(" AND IDC.IC_CODE != 7 #주제-발급한도(12-7:현카드 /22-7:현캐피/ 32-7:현커머)									\n");
			sb.append(" GROUP BY ID.ID_SEQ								\n");							
			/*추가*/sb.append(" ORDER BY FIELD(IDC.IC_CODE,8) DESC, ID.ID_REPLYCOUNT DESC, B.IC_CODE ASC, ID.MD_DATE DESC, IDC.IC_CODE 												\n"); //감성/주제/시간 순서요청 - 2021.03.23
			
			/*추가*/sb.append(" )	AS A UNION SELECT B.* FROM(			\n");
			
			//주제-발급한도만 가장뒤로 - 2021.03.23 고객사 요청 
			sb.append("SELECT ID.ID_SEQ																												\n");
			sb.append("     , ID.ID_TITLE																											\n");
			sb.append("     , ID.ID_URL																												\n");
			sb.append("     , ID.S_SEQ											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= 14 ),'') AS SENTI_CODE #긍부정 		\n");
			sb.append("     , MD_DATE												\n");
			sb.append("     , DATE_FORMAT(MD_DATE,'%Y-%m-%d %H:%') AS DATE_FORMAT	\n");
			sb.append("     , MD_SITE_NAME										\n");
			sb.append("     , ID.ID_CONTENT											\n");
			sb.append("     , IDC.IC_CODE											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= 16 ),'') AS LEVEL					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 14) AS SENTI_NAME	#긍부정 			\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 16) AS LEVEL_NAME																\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 12) AS IC_NAME 			\n");
			sb.append("  FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC											\n");
			/*추가*/sb.append("     , ISSUE_DATA_CODE B											\n");
			sb.append(" WHERE ID.ID_SEQ = IDC.ID_SEQ								\n");
			/*추가*/sb.append("   AND ID.ID_SEQ = B.ID_SEQ									\n");
			sb.append(" AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간								\n");							
			sb.append(" AND SG_SEQ IN (95,97) #상단 검색 조건 - 채널 							\n");		
			sb.append(" AND IDC.IC_TYPE = 12 #(12:현카드 /22:현캐피/ 32:현커머)								\n");
			/*추가*/sb.append(" AND B.IC_TYPE = 14 #(14:현카드 /24:현캐피/ 34:현커머)								\n");
			/*추가*/sb.append(" AND IDC.IC_CODE = 7 #주제-발급한도(12-7:현카드 /22-7:현캐피/ 32-7:현커머)								\n"); //2021.03.23 고객사 요청(발급한도가 가장뒤로)			
			sb.append(" GROUP BY ID.ID_SEQ								\n");							
			/*추가*/sb.append(" ORDER BY FIELD(IDC.IC_CODE,8) DESC, ID.ID_REPLYCOUNT DESC, B.IC_CODE ASC, ID.MD_DATE DESC, IDC.IC_CODE 						\n"); //감성/주제/시간 순서요청 - 2021.03.23 //+ 주제분류 FRAUD 1순위 노출 > 댓글 수 > 감성 > 최신 > 주제 분류 발급/한도 후순위 - 2021.06.17 강보명 과장님 메일 요청
			/*추가*/sb.append(" )AS B			\n");
			
			
			
			sb.append(" LIMIT 15							\n");							
			//sb.append(" ORDER BY IMPORTANT ASC										\n");
			
			System.out.println(sb.toString());
			pstmt =null; rs =null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dataMap = new HashMap();
				dataMap.put("ID_SEQ", rs.getString("ID_SEQ"));
				dataMap.put("ID_TITLE", su.toHtmlString(rs.getString("ID_TITLE")));
				dataMap.put("ID_URL", rs.getString("ID_URL"));
				dataMap.put("SENTI_CODE", rs.getString("SENTI_CODE"));
				dataMap.put("MD_DATE", rs.getString("MD_DATE"));
				dataMap.put("DATE_FORMAT", rs.getString("DATE_FORMAT"));
				dataMap.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
				//dataMap.put("ID_CONTENT", cutKey(su.toHtmlString(rs.getString("ID_CONTENT")), rs.getString("K_VALUE")));
				dataMap.put("ID_CONTENT", rs.getString("ID_CONTENT"));
				dataMap.put("IC_CODE", rs.getString("IC_CODE"));
				dataMap.put("S_SEQ", rs.getString("S_SEQ"));
				dataMap.put("LEVEL", rs.getString("LEVEL"));
				dataMap.put("SENTI_NAME", rs.getString("SENTI_NAME"));
				dataMap.put("LEVEL_NAME", rs.getString("LEVEL_NAME"));
				dataMap.put("IC_NAME", rs.getString("IC_NAME"));


				arrResult.add(dataMap);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	
	public ArrayList getDailyTopIssueList_capital(String ir_sdate, String ir_edate) {
		
		ArrayList arrResult = new ArrayList();
		Map dataMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();			
			sb.append(" ## 일일보고서 TOP 이슈 리스트	- 현대캐피탈										\n");
			sb.append("SELECT A.* FROM ( 												\n");
			sb.append("SELECT ID.ID_SEQ												\n");
			sb.append("     , ID.ID_TITLE											\n");
			sb.append("     , ID.ID_URL												\n");
			sb.append("     , ID.S_SEQ											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= 24 ),'') AS SENTI_CODE #긍부정(IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머) 		\n");
			sb.append("     , MD_DATE												\n");
			sb.append("     , DATE_FORMAT(MD_DATE,'%Y-%m-%d %H:%') AS DATE_FORMAT	\n");
			sb.append("     , MD_SITE_NAME										\n");
			sb.append("     , ID.ID_CONTENT											\n");
			sb.append("     , IDC.IC_CODE											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= 26 ),'') AS LEVEL #정보등급( IC_TYPE >> 16:현카드 /26:현캐피/ 36:현커머)					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 24) AS SENTI_NAME	#긍부정 			\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 26) AS LEVEL_NAME																\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 22) AS IC_NAME 			\n");
			sb.append("  FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC											\n");
			sb.append("  	, ISSUE_DATA_CODE B											\n");
			sb.append(" WHERE ID.ID_SEQ = IDC.ID_SEQ								\n");							
			sb.append(" AND   ID.ID_SEQ = B.ID_SEQ									\n");							
			//sb.append(" AND MD_DATE BETWEEN '2021-02-01 00:00:00' AND '2021-02-16 23:59:59'  #상단 검색 조건 - 기간								\n");	
			sb.append(" AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간								\n");	
			sb.append(" AND SG_SEQ IN (94,95,97) #상단 검색 조건 - 채널 							\n");
			sb.append(" AND IDC.IC_TYPE = 22 #(12:현카드 /22:현캐피/ 32:현커머)								\n");							
			sb.append(" AND B.IC_TYPE = 24 #(14:현카드 /24:현캐피/ 34:현커머)									\n");
			sb.append(" AND IDC.IC_CODE != 7 #주제-발급한도(12-7:현카드 /22-7:현캐피/ 32-7:현커머)				\n");
			sb.append(" GROUP BY ID.ID_SEQ							\n");							
			//sb.append(" ORDER BY ID.MD_DATE DESC 								\n");							
			//sb.append(" ORDER BY B.IC_CODE ASC, ID.MD_DATE DESC, IDC.IC_CODE 									\n");
			/*추가*/sb.append(" ORDER BY ID.ID_REPLYCOUNT DESC, B.IC_CODE ASC, ID.MD_DATE DESC, IDC.IC_CODE 						\n"); //감성/주제/시간 순서요청 - 2021.03.23
			
			//주제-발급한도만 가장뒤로 - 2021.03.23 고객사 요청
			/*추가*/sb.append(" )	AS A UNION SELECT B.* FROM(			\n");
			
			sb.append("SELECT ID.ID_SEQ												\n");
			sb.append("     , ID.ID_TITLE											\n");
			sb.append("     , ID.ID_URL												\n");
			sb.append("     , ID.S_SEQ											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= 24 ),'') AS SENTI_CODE #긍부정(IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머) 		\n");
			sb.append("     , MD_DATE												\n");
			sb.append("     , DATE_FORMAT(MD_DATE,'%Y-%m-%d %H:%') AS DATE_FORMAT	\n");
			sb.append("     , MD_SITE_NAME										\n");
			sb.append("     , ID.ID_CONTENT											\n");
			sb.append("     , IDC.IC_CODE											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= 26 ),'') AS LEVEL #정보등급( IC_TYPE >> 16:현카드 /26:현캐피/ 36:현커머)					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 24) AS SENTI_NAME	#긍부정 			\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 26) AS LEVEL_NAME																\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 22) AS IC_NAME 			\n");
			sb.append("  FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC											\n");
			sb.append("  	, ISSUE_DATA_CODE B											\n");
			sb.append(" WHERE ID.ID_SEQ = IDC.ID_SEQ								\n");							
			sb.append(" AND   ID.ID_SEQ = B.ID_SEQ									\n");							
			//sb.append(" AND MD_DATE BETWEEN '2021-02-01 00:00:00' AND '2021-02-16 23:59:59'  #상단 검색 조건 - 기간								\n");	
			sb.append(" AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간								\n");	
			sb.append(" AND SG_SEQ IN (94,95,97) #상단 검색 조건 - 채널 							\n");
			sb.append(" AND IDC.IC_TYPE = 22 #(12:현카드 /22:현캐피/ 32:현커머)								\n");							
			sb.append(" AND B.IC_TYPE = 24 #(14:현카드 /24:현캐피/ 34:현커머)									\n");
			sb.append(" AND IDC.IC_CODE = 7 #주제-발급한도(12-7:현카드 /22-7:현캐피/ 32-7:현커머)						\n");
			sb.append(" GROUP BY ID.ID_SEQ							\n");							
			//sb.append(" ORDER BY ID.MD_DATE DESC 								\n");							
			//sb.append(" ORDER BY B.IC_CODE ASC, ID.MD_DATE DESC, IDC.IC_CODE 									\n");
			/*추가*/sb.append(" ORDER BY ID.ID_REPLYCOUNT DESC, B.IC_CODE ASC, ID.MD_DATE DESC, IDC.IC_CODE					\n"); //감성/주제/시간 순서요청 - 2021.03.23
			
			sb.append(")AS B 												\n");
			
			sb.append(" LIMIT 15							\n");							
			//sb.append(" ORDER BY IMPORTANT ASC										\n");
			
			System.out.println(sb.toString());
			pstmt =null; rs =null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dataMap = new HashMap();
				dataMap.put("ID_SEQ", rs.getString("ID_SEQ"));
				dataMap.put("ID_TITLE", su.toHtmlString(rs.getString("ID_TITLE")));
				dataMap.put("ID_URL", rs.getString("ID_URL"));
				dataMap.put("SENTI_CODE", rs.getString("SENTI_CODE"));
				dataMap.put("MD_DATE", rs.getString("MD_DATE"));
				dataMap.put("DATE_FORMAT", rs.getString("DATE_FORMAT"));
				dataMap.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
				//dataMap.put("ID_CONTENT", cutKey(su.toHtmlString(rs.getString("ID_CONTENT")), rs.getString("K_VALUE")));
				dataMap.put("ID_CONTENT", rs.getString("ID_CONTENT"));
				dataMap.put("IC_CODE", rs.getString("IC_CODE"));
				dataMap.put("LEVEL", rs.getString("LEVEL"));
				dataMap.put("S_SEQ", rs.getString("S_SEQ"));
				dataMap.put("SENTI_NAME", rs.getString("SENTI_NAME"));
				dataMap.put("LEVEL_NAME", rs.getString("LEVEL_NAME"));
				dataMap.put("IC_NAME", rs.getString("IC_NAME"));
				arrResult.add(dataMap);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	public ArrayList getDailyTopIssueList_commercial(String ir_sdate, String ir_edate) {
		
		ArrayList arrResult = new ArrayList();
		Map dataMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();			
			sb.append(" ## 일일보고서 TOP 이슈 리스트	- 현대캐미셜										\n");
			
			sb.append("SELECT A.* FROM(												\n");
			sb.append("SELECT ID.ID_SEQ												\n");
			sb.append("     , ID.ID_TITLE											\n");
			sb.append("     , ID.ID_URL												\n");
			sb.append("     , ID.S_SEQ											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= 34 ),'') AS SENTI_CODE #긍부정(IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머) 		\n");
			sb.append("     , MD_DATE												\n");
			sb.append("     , DATE_FORMAT(MD_DATE,'%Y-%m-%d %H:%') AS DATE_FORMAT	\n");
			sb.append("     , MD_SITE_NAME										\n");
			sb.append("     , ID.ID_CONTENT											\n");
			sb.append("     , IDC.IC_CODE											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= 36 ),'') AS LEVEL #정보등급( IC_TYPE >> 16:현카드 /26:현캐피/ 36:현커머)					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 34) AS SENTI_NAME	#긍부정 			\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 36) AS LEVEL_NAME																\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 32) AS IC_NAME 			\n");
			sb.append("  FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC											\n");
			sb.append("  	, ISSUE_DATA_CODE B											\n");
			sb.append(" WHERE ID.ID_SEQ = IDC.ID_SEQ								\n");							
			sb.append(" AND   ID.ID_SEQ = B.ID_SEQ								\n");							
			//sb.append(" AND MD_DATE BETWEEN '2021-02-01 00:00:00' AND '2021-02-16 23:59:59'  #상단 검색 조건 - 기간								\n");	
			sb.append(" AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간								\n");
			sb.append(" AND SG_SEQ IN (94,95,97,99,100) #상단 검색 조건 - 채널 (21.10.12 트위터,페이스북 추가) 							\n");
			sb.append(" AND IDC.IC_TYPE = 32 #(12:현카드 /22:현캐피/ 32:현커머)								\n");							
			sb.append(" AND B.IC_TYPE = 34 #(14:현카드 /24:현캐피/ 34:현커머)								\n");
			sb.append(" AND IDC.IC_CODE != 7 #주제-발급한도(12-7:현카드 /22-7:현캐피/ 32-7:현커머)						\n");
			sb.append(" GROUP BY ID.ID_SEQ								\n");							
			//sb.append(" ORDER BY ID.MD_DATE DESC 								\n");							
			//sb.append(" ORDER BY B.IC_CODE ASC, ID.MD_DATE DESC, IDC.IC_CODE								\n");
			/*추가*/sb.append(" ORDER BY ID.ID_REPLYCOUNT DESC, B.IC_CODE ASC, IDC.IC_CODE, ID.MD_DATE DESC 						\n"); //감성/주제/시간 순서요청 - 2021.03.23
			
			//주제-발급한도만 가장뒤로 - 2021.03.23 고객사 요청
			/*추가*/sb.append(" )	AS A UNION SELECT B.* FROM(			\n");
			
			sb.append("SELECT ID.ID_SEQ												\n");
			sb.append("     , ID.ID_TITLE											\n");
			sb.append("     , ID.ID_URL												\n");
			sb.append("     , ID.S_SEQ											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= 34 ),'') AS SENTI_CODE #긍부정(IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머) 		\n");
			sb.append("     , MD_DATE												\n");
			sb.append("     , DATE_FORMAT(MD_DATE,'%Y-%m-%d %H:%') AS DATE_FORMAT	\n");
			sb.append("     , MD_SITE_NAME										\n");
			sb.append("     , ID.ID_CONTENT											\n");
			sb.append("     , IDC.IC_CODE											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= 36 ),'') AS LEVEL #정보등급( IC_TYPE >> 16:현카드 /26:현캐피/ 36:현커머)					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 34) AS SENTI_NAME	#긍부정 			\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 36) AS LEVEL_NAME																\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, 32) AS IC_NAME 			\n");
			sb.append("  FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC											\n");
			sb.append("  	, ISSUE_DATA_CODE B											\n");
			sb.append(" WHERE ID.ID_SEQ = IDC.ID_SEQ								\n");							
			sb.append(" AND   ID.ID_SEQ = B.ID_SEQ								\n");							
			//sb.append(" AND MD_DATE BETWEEN '2021-02-01 00:00:00' AND '2021-02-16 23:59:59'  #상단 검색 조건 - 기간								\n");	
			sb.append(" AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간								\n");
			sb.append(" AND SG_SEQ IN (94,95,97,99,100) #상단 검색 조건 - 채널 	 (21.10.12 트위터,페이스북 추가)						\n");
			sb.append(" AND IDC.IC_TYPE = 32 #(12:현카드 /22:현캐피/ 32:현커머)								\n");							
			sb.append(" AND B.IC_TYPE = 34 #(14:현카드 /24:현캐피/ 34:현커머)								\n");
			sb.append(" AND IDC.IC_CODE = 7 #주제-발급한도(12-7:현카드 /22-7:현캐피/ 32-7:현커머)						\n");
			sb.append(" GROUP BY ID.ID_SEQ								\n");							
			//sb.append(" ORDER BY ID.MD_DATE DESC 								\n");							
			//sb.append(" ORDER BY B.IC_CODE ASC, ID.MD_DATE DESC, IDC.IC_CODE								\n");
			/*추가*/sb.append(" ORDER BY ID.ID_REPLYCOUNT DESC, B.IC_CODE ASC, IDC.IC_CODE, ID.MD_DATE DESC 						\n"); //감성/주제/시간 순서요청 - 2021.03.23
			
			
			sb.append(")AS B 											\n");
			
			sb.append(" LIMIT 15							\n");							
			//sb.append(" ORDER BY IMPORTANT ASC										\n");
			
			System.out.println(sb.toString());
			pstmt =null; rs =null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dataMap = new HashMap();
				dataMap.put("ID_SEQ", rs.getString("ID_SEQ"));
				dataMap.put("ID_TITLE", su.toHtmlString(rs.getString("ID_TITLE")));
				dataMap.put("ID_URL", rs.getString("ID_URL"));
				dataMap.put("S_SEQ", rs.getString("S_SEQ"));
				dataMap.put("SENTI_CODE", rs.getString("SENTI_CODE"));
				dataMap.put("MD_DATE", rs.getString("MD_DATE"));
				dataMap.put("DATE_FORMAT", rs.getString("DATE_FORMAT"));
				dataMap.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
				//dataMap.put("ID_CONTENT", cutKey(su.toHtmlString(rs.getString("ID_CONTENT")), rs.getString("K_VALUE")));
				dataMap.put("ID_CONTENT", rs.getString("ID_CONTENT"));
				dataMap.put("IC_CODE", rs.getString("IC_CODE"));
				dataMap.put("LEVEL", rs.getString("LEVEL"));
				dataMap.put("SENTI_NAME", rs.getString("SENTI_NAME"));
				dataMap.put("LEVEL_NAME", rs.getString("LEVEL_NAME"));
				dataMap.put("IC_NAME", rs.getString("IC_NAME"));

				arrResult.add(dataMap);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	// ##VOC 감성 비중
	public ArrayList getSentiRatio_card(String ir_sdate, String ir_edate) {
		
		ArrayList arrResult = new ArrayList();
		Map dataMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();			
			sb.append("##VOC 감성 비중 - CARD 										\n");
			sb.append("SELECT   A.IC_CODE												\n");
			sb.append("     ,   A.CNT											\n");
			sb.append("     ,	IFNULL( ROUND(A.CNT/B.TOTAL_CNT*100,0),0) AS `PER`												\n");
			sb.append("FROM ( 															\n");
			sb.append("     SELECT A.IC_CODE, IFNULL(B.CNT, 0) AS CNT												\n");
			sb.append("     FROM (	\n");
			sb.append("     	  SELECT IC_CODE FROM ISSUE_CODE WHERE IC_CODE > 0 AND IC_TYPE = 14  #(긍부정 IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머)										\n");
			sb.append("     )A LEFT OUTER JOIN (											\n");
			sb.append("     		SELECT  IFNULL(COUNT(ID.ID_SEQ),0) AS CNT											\n");
			sb.append("     			 , IDC.IC_CODE					\n");
			sb.append("  			FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC											\n");
			sb.append(" 			WHERE ID.ID_SEQ = IDC.ID_SEQ								\n");							
			sb.append(" 			AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간								\n");
			sb.append(" 			AND SG_SEQ IN (95,97) #상단 검색 조건 - 채널 							\n");
			sb.append(" 			AND IDC.IC_TYPE = 14 #(긍부정 IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머) 								\n");							
			sb.append(" 			GROUP BY IC_CODE								\n");							
			sb.append(" 	)B ON A.IC_CODE = B.IC_CODE 								\n");							
			sb.append(" 	GROUP BY A.IC_CODE								\n");							
			sb.append(")A,										\n");
			sb.append("(										\n");
			sb.append("		SELECT   IFNULL(COUNT(ID.ID_SEQ),0) AS TOTAL_CNT										\n");
			sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC										\n");
			sb.append("		WHERE ID.ID_SEQ = IDC.ID_SEQ										\n");
			sb.append("		AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간										\n");
			sb.append("		AND SG_SEQ IN (95,97) #상단 검색 조건 - 채널 									\n");
			sb.append("		AND IDC.IC_TYPE = 14 #(긍부정 IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머) 										\n");
			sb.append(")B									\n");
			
			System.out.println(sb.toString());
			pstmt =null; rs =null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dataMap = new HashMap();
				dataMap.put("IC_CODE", rs.getString("IC_CODE"));
				dataMap.put("CNT", su.toHtmlString(rs.getString("CNT")));
				dataMap.put("PER", rs.getString("PER"));
				
				arrResult.add(dataMap);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	// ##VOC 감성 비중
	public ArrayList getSentiRatio_capital(String ir_sdate, String ir_edate) {
		
		ArrayList arrResult = new ArrayList();
		Map dataMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();			
			sb.append("##VOC 감성 비중 - capital 										\n");
			sb.append("SELECT   A.IC_CODE												\n");
			sb.append("     ,   A.CNT											\n");
			sb.append("     ,	IFNULL( ROUND(A.CNT/B.TOTAL_CNT*100,0),0) AS `PER`												\n");
			sb.append("FROM ( 															\n");
			sb.append("     SELECT A.IC_CODE, IFNULL(B.CNT, 0) AS CNT												\n");
			sb.append("     FROM (	\n");
			sb.append("     	  SELECT IC_CODE FROM ISSUE_CODE WHERE IC_CODE > 0 AND IC_TYPE = 24  #(긍부정 IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머)										\n");
			sb.append("     )A LEFT OUTER JOIN (											\n");
			sb.append("     		SELECT  IFNULL(COUNT(ID.ID_SEQ),0) AS CNT											\n");
			sb.append("     			 , IDC.IC_CODE					\n");
			sb.append("  			FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC											\n");
			sb.append(" 			WHERE ID.ID_SEQ = IDC.ID_SEQ								\n");							
			sb.append(" 			AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간								\n");
			sb.append(" 			AND SG_SEQ IN (94,95,97) #상단 검색 조건 - 채널 							\n");
			sb.append(" 			AND IDC.IC_TYPE = 24 #(긍부정 IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머) 								\n");							
			sb.append(" 			GROUP BY IC_CODE								\n");							
			sb.append(" 	)B ON A.IC_CODE = B.IC_CODE 								\n");							
			sb.append(" 	GROUP BY A.IC_CODE								\n");							
			sb.append(")A,										\n");
			sb.append("(										\n");
			sb.append("		SELECT   IFNULL(COUNT(ID.ID_SEQ),0) AS TOTAL_CNT										\n");
			sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC										\n");
			sb.append("		WHERE ID.ID_SEQ = IDC.ID_SEQ										\n");
			sb.append("		AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간										\n");
			sb.append("		AND SG_SEQ IN (94,95,97) #상단 검색 조건 - 채널 									\n");
			sb.append("		AND IDC.IC_TYPE = 24 #(긍부정 IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머) 										\n");
			sb.append(")B									\n");
			
			System.out.println(sb.toString());
			pstmt =null; rs =null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dataMap = new HashMap();
				dataMap.put("IC_CODE", rs.getString("IC_CODE"));
				dataMap.put("CNT", su.toHtmlString(rs.getString("CNT")));
				dataMap.put("PER", rs.getString("PER"));
				
				arrResult.add(dataMap);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	
	// ##VOC 감성 비중
	public ArrayList getSentiRatio_commercial(String ir_sdate, String ir_edate) {
		
		ArrayList arrResult = new ArrayList();
		Map dataMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();			
			sb.append("##VOC 감성 비중 - commercial										\n");
			sb.append("SELECT   A.IC_CODE												\n");
			sb.append("     ,   A.CNT											\n");
			sb.append("     ,	IFNULL( ROUND(A.CNT/B.TOTAL_CNT*100,0),0) AS `PER`												\n");
			sb.append("FROM ( 															\n");
			sb.append("     SELECT A.IC_CODE, IFNULL(B.CNT, 0) AS CNT												\n");
			sb.append("     FROM (	\n");
			sb.append("     	  SELECT IC_CODE FROM ISSUE_CODE WHERE IC_CODE > 0 AND IC_TYPE = 34  #(긍부정 IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머)										\n");
			sb.append("     )A LEFT OUTER JOIN (											\n");
			sb.append("     		SELECT  IFNULL(COUNT(ID.ID_SEQ),0) AS CNT											\n");
			sb.append("     			 , IDC.IC_CODE					\n");
			sb.append("  			FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC											\n");
			sb.append(" 			WHERE ID.ID_SEQ = IDC.ID_SEQ								\n");							
			sb.append(" 			AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간								\n");
			sb.append(" 			AND SG_SEQ IN (94,95,97,99,100) #상단 검색 조건 - 채널 (21.10.12 트위터,페이스북 추가)							\n");
			sb.append(" 			AND IDC.IC_TYPE = 34 #(긍부정 IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머) 								\n");							
			sb.append(" 			GROUP BY IC_CODE								\n");							
			sb.append(" 	)B ON A.IC_CODE = B.IC_CODE 								\n");							
			sb.append(" 	GROUP BY A.IC_CODE								\n");							
			sb.append(")A,										\n");
			sb.append("(										\n");
			sb.append("		SELECT   IFNULL(COUNT(ID.ID_SEQ),0) AS TOTAL_CNT										\n");
			sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC										\n");
			sb.append("		WHERE ID.ID_SEQ = IDC.ID_SEQ										\n");
			sb.append("		AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간										\n");
			sb.append("		AND SG_SEQ IN (94,95,97,99,100) #상단 검색 조건 - 채널 	(21.10.12 트위터,페이스북 추가)								\n");
			sb.append("		AND IDC.IC_TYPE = 34 #(긍부정 IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머) 										\n");
			sb.append(")B									\n");
			
			System.out.println(sb.toString());
			pstmt =null; rs =null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dataMap = new HashMap();
				dataMap.put("IC_CODE", rs.getString("IC_CODE"));
				dataMap.put("CNT", su.toHtmlString(rs.getString("CNT")));
				dataMap.put("PER", rs.getString("PER"));
				
				arrResult.add(dataMap);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	//VOC 더보기 클릭 시 해당 데이터 모두 출력 - 카드(12), 캐피탈(22), 카머셜(32)
	//public ArrayList getDailyTotalIssueList(int nowpage, int rowCnt, String ir_sdate, String ir_edate, String type, String mode) {
	//키워드 및 감성 검색기능 추가 - 고객사 요청사항(2021.04.22)
	public ArrayList getDailyTotalIssueList(int nowpage, int rowCnt, String ir_sdate, String ir_edate, String type, String mode, String keyword, String senti_type ) {
		
		ArrayList arrResult = new ArrayList();
		Map dataMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		 int rowcount = 0;
		String explain = "현대카머셜";
		String senti_code = "34";
		String level_code = "36";
		String sg_seqs = "94,95,97,99,100";
		if(type.equals("12")) {
			explain = "현대카드";
			senti_code = "14";
			level_code = "16";
			sg_seqs = "95,97";
		} else if(type.equals("22")) {
			explain = "현대캐피탈";
			senti_code = "24";
			level_code = "26";
			sg_seqs = "94,95,97";
		}
		int index = 0;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
       	 	sb = new StringBuffer();
       	 	sb.append("SELECT COUNT(*) AS CNT 	\n");
       	 	sb.append("FROM (SELECT A.* FROM 	\n");
       	 	sb.append("		 (SELECT ID.ID_SEQ 	\n");
       	 	sb.append("		 FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE B	 	\n");
       	 	sb.append("		 WHERE ID.ID_SEQ = IDC.ID_SEQ 	\n");
       	 	sb.append("		  AND ID.ID_SEQ = B.ID_SEQ	 	\n");
       	 	sb.append("		  AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간	\n");
       	 	sb.append("		  AND SG_SEQ IN ("+sg_seqs+") #상단 검색 조건 - 채널 	\n");
       	 	//검섹 조건은 AND
       	 	if(!keyword.equals("")) {
	       	String[] keyword_split = keyword.split("\\s+");
	       	sb.append(" AND (ID.ID_TITLE LIKE '%"+keyword_split[0]+"%'	\n");
	       		for(int i = 1; i < keyword_split.length; i++) {
	       			sb.append(" AND ID.ID_TITLE LIKE '%"+keyword_split[i]+"%'	\n");
	       		}
	       	sb.append("  )                      												\n");
	       	} 
       	 	sb.append(" 	  AND IDC.IC_TYPE = "+type+" #(12:현카드 /22:현캐피/ 32:현커머)								\n");							
			sb.append(" 	  AND B.IC_TYPE = "+senti_code+"	 #(14:현카드 /24:현캐피/ 34:현커머)							\n");
			if(!senti_type.equals("")) {
				sb.append("   AND B.IC_CODE = "+senti_type+"			\n");	
			}
			sb.append(" 	  AND IDC.IC_CODE != 7 #주제-발급한도(12-7:현카드 /22-7:현캐피/ 32-7:현커머)						\n");
       	 	sb.append("		  GROUP BY ID.ID_SEQ	\n");
       	 	sb.append("		  ORDER BY ID.ID_REPLYCOUNT DESC	\n");
	     	sb.append("   ) AS A UNION SELECT B.* FROM(								\n");
	    	sb.append("		 SELECT ID.ID_SEQ 	\n");
       	 	sb.append("		 FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE B	 	\n");
       	 	sb.append("		 WHERE ID.ID_SEQ = IDC.ID_SEQ 	\n");
       	 	sb.append("		  AND ID.ID_SEQ = B.ID_SEQ	 	\n");
       	 	sb.append("		  AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간	\n");
       	 	sb.append("		  AND SG_SEQ IN ("+sg_seqs+") #상단 검색 조건 - 채널 	\n");
       	 	//검섹 조건은 AND
       	 	if(!keyword.equals("")) {
	       	String[] keyword_split = keyword.split("\\s+");
	       	sb.append(" AND (ID.ID_TITLE LIKE '%"+keyword_split[0]+"%'	\n");
	       		for(int i = 1; i < keyword_split.length; i++) {
	       			sb.append(" AND ID.ID_TITLE LIKE '%"+keyword_split[i]+"%'	\n");
	       		}
	       	sb.append("  )                      												\n");
	       	} 
       	 	sb.append(" 	  AND IDC.IC_TYPE = "+type+" #(12:현카드 /22:현캐피/ 32:현커머)								\n");							
			sb.append(" 	  AND B.IC_TYPE = "+senti_code+"	 #(14:현카드 /24:현캐피/ 34:현커머)							\n");
			if(!senti_type.equals("")) {
				sb.append("   AND B.IC_CODE = "+senti_type+"			\n");	
			}
			sb.append(" 	  AND IDC.IC_CODE = 7 #주제-발급한도(12-7:현카드 /22-7:현캐피/ 32-7:현커머)						\n");
       	 	sb.append("		  GROUP BY ID.ID_SEQ	\n");
       	 	sb.append("		  ORDER BY ID.ID_REPLYCOUNT DESC	\n");
       	 	sb.append("	 )AS B			\n");
       	 	sb.append("	 )AS C			\n");
       	 	
       	 	System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
	     	rs = pstmt.executeQuery();      
        	
            if(rs.next()){
            	FullCnt = rs.getInt("CNT");
            }
            
            if(mode.equals("")) {
            	index = (nowpage-1) * rowCnt;
            }
			sb = new StringBuffer();			
			sb.append(" ## 일일보고서 팝업 리스트	- "+explain+"									\n");
			sb.append("SELECT A.* FROM (											\n");
			sb.append("SELECT ID.ID_SEQ												\n");
			sb.append("     , ID.ID_TITLE											\n");
			sb.append("     , ID.ID_URL												\n");
			sb.append("     , ID.S_SEQ											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+senti_code+" ),'') AS SENTI_CODE #긍부정 		\n");
			sb.append("     , MD_DATE												\n");
			sb.append("     , DATE_FORMAT(MD_DATE,'%Y-%m-%d %H:%') AS DATE_FORMAT	\n");
			sb.append("     , MD_SITE_NAME										\n");
			sb.append("     , ID.ID_CONTENT											\n");
			sb.append("     , IDC.IC_CODE											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ),'') AS LEVEL					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") AS SENTI_NAME	#긍부정					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+level_code+") AS LEVEL_NAME					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+type+") AS IC_NAME					\n");
			sb.append("  FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC											\n");
			sb.append("  	, ISSUE_DATA_CODE B											\n");
			sb.append(" WHERE ID.ID_SEQ = IDC.ID_SEQ								\n");							
			sb.append(" AND ID.ID_SEQ = B.ID_SEQ										\n");							
			sb.append(" AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간								\n");							
			//sb.append(" AND MD_DATE BETWEEN '2021-02-01 00:00:00' AND '2021-02-16 23:59:59'	#상단 검색 조건 - 기간							\n");		
			sb.append(" AND SG_SEQ IN ("+sg_seqs+") #상단 검색 조건 - 채널 							\n");
			//검섹 조건은 AND
			if(!keyword.equals("")) {
	       	String[] keyword_split = keyword.split("\\s+");
	       	sb.append(" AND (ID.ID_TITLE LIKE '%"+keyword_split[0]+"%'	\n");
	       		for(int i = 1; i < keyword_split.length; i++) {
	       			sb.append(" AND ID.ID_TITLE LIKE '%"+keyword_split[i]+"%'	\n");
	       		}
	       	sb.append("  )                      												\n");
	       	} 
			sb.append(" AND IDC.IC_TYPE = "+type+" #(12:현카드 /22:현캐피/ 32:현커머)								\n");							
			sb.append(" AND B.IC_TYPE = "+senti_code+"	 #(14:현카드 /24:현캐피/ 34:현커머)							\n");
			if(!senti_type.equals("")) {
				sb.append("   AND B.IC_CODE = "+senti_type+"			\n");	
			}
			sb.append(" AND IDC.IC_CODE != 7 #주제-발급한도(12-7:현카드 /22-7:현캐피/ 32-7:현커머)						\n");
			sb.append(" GROUP BY ID.ID_SEQ								\n");							
			if(type.equals("12")) {
				sb.append(" ORDER BY FIELD(IDC.IC_CODE,8) DESC, ID.ID_REPLYCOUNT DESC, B.IC_CODE ASC, ID.MD_DATE DESC , IDC.IC_CODE 								\n");
			}else {
				sb.append(" ORDER BY ID.ID_REPLYCOUNT DESC, B.IC_CODE ASC, ID.MD_DATE DESC , IDC.IC_CODE 								\n");
			}
			
			/*추가*/sb.append(" )	AS A UNION SELECT B.* FROM(			\n");
			//주제-발급한도만 가장뒤로 - 2021.03.23 고객사 요청
			sb.append("SELECT ID.ID_SEQ												\n");
			sb.append("     , ID.ID_TITLE											\n");
			sb.append("     , ID.ID_URL												\n");
			sb.append("     , ID.S_SEQ											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+senti_code+" ),'') AS SENTI_CODE #긍부정 		\n");
			sb.append("     , MD_DATE												\n");
			sb.append("     , DATE_FORMAT(MD_DATE,'%Y-%m-%d %H:%') AS DATE_FORMAT	\n");
			sb.append("     , MD_SITE_NAME										\n");
			sb.append("     , ID.ID_CONTENT											\n");
			sb.append("     , IDC.IC_CODE											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ),'') AS LEVEL					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") AS SENTI_NAME	#긍부정					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+level_code+") AS LEVEL_NAME					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+type+") AS IC_NAME					\n");
			sb.append("  FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC											\n");
			sb.append("  	, ISSUE_DATA_CODE B											\n");
			sb.append(" WHERE ID.ID_SEQ = IDC.ID_SEQ								\n");							
			sb.append(" AND ID.ID_SEQ = B.ID_SEQ										\n");							
			sb.append(" AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간								\n");							
			//sb.append(" AND MD_DATE BETWEEN '2021-02-01 00:00:00' AND '2021-02-16 23:59:59'	#상단 검색 조건 - 기간							\n");		
			sb.append(" AND SG_SEQ IN ("+sg_seqs+") #상단 검색 조건 - 채널 							\n");		
			//검섹 조건은 AND
			if(!keyword.equals("")) {
		       	String[] keyword_split = keyword.split("\\s+");
		       	sb.append(" AND (ID.ID_TITLE LIKE '%"+keyword_split[0]+"%'	\n");
		       		for(int i = 1; i < keyword_split.length; i++) {
		       			sb.append(" AND ID.ID_TITLE LIKE '%"+keyword_split[i]+"%'	\n");
		       		}
		       	sb.append("  )                      												\n");
		    } 
			sb.append(" AND IDC.IC_TYPE = "+type+" #(12:현카드 /22:현캐피/ 32:현커머)								\n");							
			sb.append(" AND B.IC_TYPE = "+senti_code+"	 #(14:현카드 /24:현캐피/ 34:현커머)							\n");
			if(!senti_type.equals("")) {
				sb.append("   AND B.IC_CODE = "+senti_type+"			\n");	
			}
			sb.append(" AND IDC.IC_CODE = 7 #주제-발급한도(12-7:현카드 /22-7:현캐피/ 32-7:현커머)						\n");
			sb.append(" GROUP BY ID.ID_SEQ								\n");							
			//sb.append(" ORDER BY ID.MD_DATE DESC 								\n");							
			//sb.append(" ORDER BY IMPORTANT ASC										\n");
			//sb.append(" ORDER BY B.IC_CODE ASC, ID.MD_DATE DESC , IDC.IC_CODE 								\n");
			
			if(type.equals("12")) {
				sb.append(" ORDER BY FIELD(IDC.IC_CODE,8) DESC, ID.ID_REPLYCOUNT DESC, B.IC_CODE ASC, ID.MD_DATE DESC , IDC.IC_CODE 								\n");
			}else {
				sb.append(" ORDER BY ID.ID_REPLYCOUNT DESC, B.IC_CODE ASC, ID.MD_DATE DESC , IDC.IC_CODE 								\n");
			}
			sb.append(" )AS B							\n");

			if(mode.equals("")) {
			sb.append(" LIMIT "+index+", "+rowCnt+"						\n");	
			}
			System.out.println(sb.toString());
			pstmt =null; rs =null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dataMap = new HashMap();
				dataMap.put("ID_SEQ", rs.getString("ID_SEQ"));
				dataMap.put("ID_TITLE", su.toHtmlString(rs.getString("ID_TITLE")));
				dataMap.put("ID_URL", rs.getString("ID_URL"));
				dataMap.put("S_SEQ", rs.getString("S_SEQ"));
				dataMap.put("SENTI_CODE", rs.getString("SENTI_CODE"));
				dataMap.put("MD_DATE", rs.getString("MD_DATE"));
				dataMap.put("DATE_FORMAT", rs.getString("DATE_FORMAT"));
				dataMap.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
				//dataMap.put("ID_CONTENT", cutKey(su.toHtmlString(rs.getString("ID_CONTENT")), rs.getString("K_VALUE")));
				dataMap.put("ID_CONTENT", rs.getString("ID_CONTENT"));
				dataMap.put("IC_CODE", rs.getString("IC_CODE"));
				dataMap.put("LEVEL", rs.getString("LEVEL"));
				dataMap.put("IC_NAME", rs.getString("IC_NAME"));
				dataMap.put("SENTI_NAME", rs.getString("SENTI_NAME"));
				dataMap.put("LEVEL_NAME", rs.getString("LEVEL_NAME"));
				arrResult.add(dataMap);
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	public JSONArray getRelationAnalysis_arr(String s_date, String e_date, String group_code) {
		
		JSONObject arrResult = new JSONObject();
		JSONArray result = null;
		Map dataMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		String sg_seqs = "";
		
		if(group_code.equals("1")){
			sg_seqs = "95,97";
		} else if(group_code.equals("2")){
			sg_seqs = "94,95,97";			
		} else if(group_code.equals("3")){
			sg_seqs = "94,95,97,99,100";			
		}
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();			
			sb.append(" ## 연관키워드 분석 (자동연관어)										\n");
			sb.append("SELECT A.RK_NAME, COUNT(*) AS CNT , A.RK_SEQ												\n");
			sb.append("FROM (											\n");
			sb.append("      SELECT R.RK_NAME, MAP.ID_SEQ, R.RK_SEQ												\n");
			sb.append("      FROM RELATION_KEYWORD_R R , AUTO_RELATION_KEYWORD_MAP MAP  		\n");
			sb.append("      WHERE R.RK_SEQ = MAP.RK_SEQ												\n");
			sb.append("      AND R.RK_USE = 'Y' 	\n");
			sb.append(")A INNER JOIN (										\n");
			sb.append("      SELECT ID.ID_SEQ FROM ISSUE_DATA ID , ISSUE_DATA_CODE IDC  											\n");
			sb.append("      WHERE ID.ID_SEQ = IDC.ID_SEQ											\n");
			sb.append("      AND MD_DATE BETWEEN '"+s_date+" 00:00:00' AND '"+e_date+" 23:59:59'  #상단 검색 조건 - 기간       					\n");
			sb.append("  	 AND SG_SEQ IN ("+sg_seqs+") #채널 											\n");
			sb.append(" 	 AND IDC.IC_TYPE = 1								\n");							
			sb.append(" 	 AND IDC.IC_CODE = "+group_code+" #IC_CODE >> (1:현카드 /2:현캐피/ 3:현커머)								\n");
			sb.append(")B ON A.ID_SEQ = B.ID_SEQ						\n");
			sb.append("GROUP BY A.RK_SEQ								\n");							
			sb.append("ORDER BY CNT DESC								\n");							
			sb.append("LIMIT 30 								\n");							
			
			System.out.println(sb.toString());
			pstmt =null; 
			rs =null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			result = new JSONArray();
			JSONObject obj = new JSONObject();
			while(rs.next()){
				obj = new JSONObject();
				obj.put("RK_NAME", rs.getString("RK_NAME"));
				obj.put("CNT", rs.getString("CNT"));
				obj.put("RK_SEQ", rs.getString("RK_SEQ"));
				result.put(obj);
				
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	
	
public JSONArray getRelationAnalysis_arr_color(String s_date, String e_date, String company_code, String senti_type, String level_type) {
		
		JSONObject arrResult = new JSONObject();
		JSONArray result = null;
		Map dataMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		String sg_seqs = "";
		
		if(company_code.equals("1")){
			sg_seqs = "95,97";
		} else if(company_code.equals("2")){
			sg_seqs = "94,95,97";			
		} else if(company_code.equals("3")){
			sg_seqs = "94,95,97,99,100";			
		}

		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();			
			sb.append(" ## 연관키워드 분석 (자동연관어&수동연관어)																					\n");
			//자동연관어
			sb.append("SELECT RK_NAME                                                                                                                                \n");
			sb.append("		, SUM(CNT) AS CNT                                                                                                                            \n");
			sb.append("		, RK_SEQ                                                                                                                             \n");
			sb.append("		, SENTI_1                                                                                                                                \n");
			sb.append("		, SENTI_2                                                                                                                                \n");
			sb.append("		, SENTI_3                                                                                                                                \n");
			sb.append("		, LEVEL_1                                                                                                                                \n");
			sb.append("		, LEVEL_2                                                                                                                                \n");
			sb.append("		, LEVEL_3                                                                                                                                \n");
			sb.append("		, LEVEL_4                                                                                                                                \n");
			sb.append("	FROM (                                                                                                                                \n");
			sb.append("			 (                                                                                                                                \n");			
			sb.append("				SELECT 	B.RK_NAME                                                                                                                               \n");
			sb.append("		   			 , COUNT(*) AS CNT                                                                                                                                \n");
			sb.append("		  	   		 ,  A.RK_SEQ                                                                                                                                \n");
			sb.append("       	   		 ,  SUM(SENTI_1) AS SENTI_1                                                                                                                              \n");
			sb.append("        	   		 ,  SUM(SENTI_2) AS SENTI_2                                                                                                                              \n");
			sb.append("       	   		 ,  SUM(SENTI_3) AS SENTI_3                                                                                                                              \n");
			sb.append("        	   		 ,  SUM(LEVEL_1) AS LEVEL_1                                                                                                                              \n");
			sb.append("       	   		 ,  SUM(LEVEL_2) AS LEVEL_2                                                                                                                              \n");
			sb.append("       	   		 ,  SUM(LEVEL_3) AS LEVEL_3                                                                                                                              \n");
			sb.append("        	   		 ,  SUM(LEVEL_4) AS LEVEL_4                                                                                                                              \n");
			sb.append("  			  FROM AUTO_RELATION_KEYWORD_MAP A	                                                                                                                                                      \n");
			sb.append("      			 , RELATION_KEYWORD_R B                                                                                                                   \n");
			//자동연관어 맵핑테이블
			sb.append("           		 , ( SELECT ID.ID_SEQ   	                                                                                           \n");
			sb.append("           				  , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_type+") = '긍정', 1,0) AS SENTI_1 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)                                                    \n");
			sb.append("           				  , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_type+") = '부정', 1,0) AS SENTI_2 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)                                                    \n");
			sb.append("           				  , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_type+") = '중립', 1,0) AS SENTI_3 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)                                                    \n");
			sb.append("          				  , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_type+" ) = '1', 1,0) AS LEVEL_1 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)     \n");        
			sb.append("           				  , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_type+" ) = '2', 1,0) AS LEVEL_2 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)     \n");                   
			sb.append("            				  , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_type+" ) = '3', 1,0) AS LEVEL_3 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)     \n");                   
			sb.append("            				  , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_type+" ) = '4', 1,0) AS LEVEL_4 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)     \n");                                           
			sb.append("      				FROM ISSUE_DATA ID , ISSUE_DATA_CODE IDC                                                                                                                   \n");
			sb.append("       			   WHERE ID.ID_SEQ = IDC.ID_SEQ                                                                                                                             \n");
			sb.append("        					 AND MD_DATE BETWEEN '"+s_date+" 00:00:00' AND '"+e_date+" 23:59:59'  #상단 검색 조건 - 기간                                                                     \n");
			sb.append("        					 AND SG_SEQ IN ("+sg_seqs+") #채널                                                                                                                   \n");
			sb.append("         				 AND IDC.IC_TYPE = 1                                                                                                                                     \n");
			sb.append("        					 AND IDC.IC_CODE = "+company_code+" #IC_CODE >> (1:현카드 /2:현캐피/ 3:현커머)                                                                                                   \n");
			sb.append("       				 GROUP BY ID.ID_SEQ                                                                                                                                      \n");
			sb.append("   			 		)C                                                                                                                                    \n");
			sb.append("   			 WHERE A.RK_SEQ = B.RK_SEQ	                                                                                                                                           \n");
			sb.append("    			   AND A.ID_SEQ = C.ID_SEQ	                                                                                                                                           \n");
			sb.append("    			   AND B.RK_USE = 'Y' 			                                                                                                                                           \n");
			sb.append("    			 GROUP BY A.RK_SEQ                                                                                                                                            \n");
			sb.append("    			 ORDER BY COUNT(*) DESC 	                                                                                                                                           \n");
			sb.append("    			 LIMIT 100                                                                                                                                                     \n");
			//수동연관어
			sb.append(")  UNION  (                                                                                                                                                 \n");
			sb.append("				SELECT 	B.RK_NAME                                                                                                                               \n");
			sb.append("		   			, COUNT(*) AS CNT                                                                                                                                \n");
			sb.append("		  	   		,  A.RK_SEQ                                                                                                                                \n");
			sb.append("       	   		,  SUM(SENTI_1) AS SENTI_1                                                                                                                              \n");
			sb.append("        	   		,  SUM(SENTI_2) AS SENTI_2                                                                                                                              \n");
			sb.append("       	   		,  SUM(SENTI_3) AS SENTI_3                                                                                                                              \n");
			sb.append("        	   		,  SUM(LEVEL_1) AS LEVEL_1                                                                                                                              \n");
			sb.append("       	   		,  SUM(LEVEL_2) AS LEVEL_2                                                                                                                              \n");
			sb.append("       	   		,  SUM(LEVEL_3) AS LEVEL_3                                                                                                                              \n");
			sb.append("        	   		,  SUM(LEVEL_4) AS LEVEL_4                                                                                                                              \n");
			sb.append("  			  FROM RELATION_KEYWORD_MAP A	                                                                                                                                                 \n");
			sb.append("      			 , RELATION_KEYWORD_R B		                                                                                                                     \n");
			//수동연관어 맵핑테이블
			sb.append("     			 , ( SELECT ID.ID_SEQ                                                                                                                                           \n");
			sb.append("           				  , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_type+") = '긍정', 1,0) AS SENTI_1 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)                                                    \n");
			sb.append("           				  , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_type+") = '부정', 1,0) AS SENTI_2 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)                                                    \n");
			sb.append("           				  , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_type+") = '중립', 1,0) AS SENTI_3 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)                                                    \n");
			sb.append("          				  , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_type+" ) = '1', 1,0) AS LEVEL_1 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)     \n");        
			sb.append("           				  , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_type+" ) = '2', 1,0) AS LEVEL_2 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)     \n");                   
			sb.append("            				  , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_type+" ) = '3', 1,0) AS LEVEL_3 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)     \n");                   
			sb.append("            				  , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_type+" ) = '4', 1,0) AS LEVEL_4 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)     \n");                                           
			sb.append("      				 FROM ISSUE_DATA ID , ISSUE_DATA_CODE IDC                                                                                                                   \n");
			sb.append("       			    WHERE ID.ID_SEQ = IDC.ID_SEQ                                                                                                                             \n");
			sb.append("        			      AND MD_DATE BETWEEN '"+s_date+" 00:00:00' AND '"+e_date+" 23:59:59'  #상단 검색 조건 - 기간                                                                     \n");
			sb.append("        				  AND SG_SEQ IN ("+sg_seqs+") #채널                                                                                                                 \n");
			sb.append("         			  AND IDC.IC_TYPE = 1                                                                                                                                     \n");
			sb.append("        				  AND IDC.IC_CODE = "+company_code+" #IC_CODE >> (1:현카드 /2:현캐피/ 3:현커머)                                                                                                   \n");
			sb.append("       				GROUP BY ID.ID_SEQ                                                                                                                                      \n");
			sb.append("   			 		)C                                                                                                                                   \n");
			sb.append("   			  WHERE A.RK_SEQ = B.RK_SEQ                                                                                                                                            \n");
			sb.append("    			    AND A.ID_SEQ = C.ID_SEQ		                                                                                                                                           \n");
			sb.append("    			    AND B.RK_USE = 'Y'	                                                                                                                                           \n");
			sb.append("    			  GROUP BY A.RK_SEQ 	                                                                                                                                           \n");
			sb.append("    			  ORDER BY COUNT(*) DESC		                                                                                                                                           \n");
			sb.append("    			  LIMIT 100                                                                                                                                                     \n");
			sb.append("))A	                                                                                                                                                   \n");
			sb.append(" GROUP BY RK_NAME                                                                                                                                                 \n");
			sb.append(" ORDER BY CNT DESC , RK_NAME                                                                                                                                                 \n");
			sb.append(" LIMIT 30                                                                                                                                             \n");
			
			
			System.out.println(sb.toString());
			pstmt =null; 
			rs =null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			result = new JSONArray();
			JSONObject obj = new JSONObject();
			while(rs.next()){
				obj = new JSONObject();
				obj.put("RK_NAME", rs.getString("RK_NAME"));
				obj.put("CNT", rs.getString("CNT"));
				obj.put("RK_SEQ", rs.getString("RK_SEQ"));
				obj.put("WORD_COLOR", relationKeyword_color(new int[]{rs.getInt("SENTI_1"),rs.getInt("SENTI_2"),rs.getInt("SENTI_3")}, rs.getInt("LEVEL_1"),  rs.getInt("LEVEL_2"),  rs.getInt("LEVEL_3"),  rs.getInt("LEVEL_4") ));
				result.put(obj);
				
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	public ArrayList getRelationAnalysis(int nowpage, int rowCnt, String ir_sdate, String ir_edate, String group_code, boolean popup) {
		
		ArrayList arrResult = new ArrayList();
		Map dataMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		String explain = "현대커머셜";
		String senti_code = "34";
		String level_code = "36";
		String sg_seqs = "94,95,97,99,100";
		if(group_code.equals("1")) {
			explain = "현대카드";
			senti_code = "14";
			level_code = "16";
			sg_seqs = "95,97";
		} else if(group_code.equals("2")) {
			explain = "현대캐피탈";
			senti_code = "24";
			level_code = "26";
			sg_seqs = "94,95,97";
		}
		int index = 0;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			//연관어 분석 팝업에서 연관어 리스트 팝업 띄울때 페이지 수 조회 쿼리
			if(popup) {
       	 	sb = new StringBuffer();
       	 	sb.append("SELECT COUNT(*) AS CNT 	\n");
       	 	sb.append("  FROM ( 	\n");
       	 	sb.append("	  SELECT RK_NAME 	\n");
       	 	sb.append("		FROM (     	\n");
       	 	sb.append("		  	 (SELECT MAP.RK_SEQ, R.RK_NAME, COUNT(*) AS CNT			\n");
			sb.append("				FROM RELATION_KEYWORD_R R									\n");
			sb.append("				    , AUTO_RELATION_KEYWORD_MAP MAP											\n");
			sb.append("					, ( SELECT ID.ID_SEQ											\n");
			sb.append("						  FROM ISSUE_DATA ID , ISSUE_DATA_CODE IDC											\n");
			sb.append("						 WHERE ID.ID_SEQ = IDC.ID_SEQ												\n");
			sb.append("      				   AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간       						\n");
			sb.append("  					   AND SG_SEQ IN ("+sg_seqs+") #채널 																			\n");
			sb.append(" 					   AND IDC.IC_TYPE = 1																								\n");							
			sb.append(" 		 			   AND IDC.IC_CODE = "+group_code+" #IC_CODE >> (1:현카드 /2:현캐피/ 3:현커머)												\n");
			sb.append(" 	 				 GROUP BY ID.ID_SEQ																								\n");
			sb.append("					  ) C																								 	\n");
			sb.append("			   WHERE R.RK_SEQ = MAP.RK_SEQ																			\n");							
			sb.append("			 	 AND MAP.ID_SEQ = C.ID_SEQ																			\n");							
			sb.append("				 AND R.RK_USE = 'Y' 																		\n");
			sb.append("			   GROUP BY MAP.RK_SEQ																			\n");
			sb.append("			   ORDER BY CNT DESC 																		\n");
			sb.append("			   LIMIT 100 																\n");
			
			//수동연관어
			sb.append("  )UNION (												\n");
			sb.append("			  SELECT R.RK_NAME, MAP.RK_SEQ, COUNT(*) AS CNT  											\n");
			sb.append("				FROM RELATION_KEYWORD_R R 												\n");
			sb.append("				   , RELATION_KEYWORD_MAP MAP												\n");
			sb.append("				   , ( SELECT ID.ID_SEQ												\n");
			sb.append("						 FROM ISSUE_DATA ID , ISSUE_DATA_CODE IDC  												\n");
			sb.append("					    WHERE ID.ID_SEQ = IDC.ID_SEQ												\n");
			sb.append("      				  AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간       						\n");
			sb.append("  					  AND SG_SEQ IN ("+sg_seqs+") #채널																		\n");
			sb.append(" 					  AND IDC.IC_TYPE = 1																								\n");							
			sb.append(" 		 			  AND IDC.IC_CODE = "+group_code+" #IC_CODE >> (1:현카드 /2:현캐피/ 3:현커머)												\n");
			sb.append(" 	 				GROUP BY ID.ID_SEQ																								\n");
			sb.append("					 ) C																								 	\n");
			sb.append("			  WHERE MAP.RK_SEQ = R.RK_SEQ																		\n");							
			sb.append("			    AND MAP.ID_SEQ = C.ID_SEQ																			\n");							
			sb.append("				AND R.RK_USE = 'Y'																		\n");
			sb.append("			  GROUP BY MAP.RK_SEQ																		\n");
			sb.append("			  ORDER BY CNT DESC  																		\n");
			sb.append("			  LIMIT 100																		\n");
			sb.append("))A	                                                                                                                                                   \n");
			sb.append(" GROUP BY RK_NAME                                                                                                                                                 \n");
	     	sb.append(" ORDER BY CNT DESC							\n");
	     	sb.append("   )A 							\n");
       	 	
	     	System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
	     	rs = pstmt.executeQuery();      
        	
            if(rs.next()){
            	FullCnt = rs.getInt("CNT");
            }
            
            if(nowpage!=0) {
            	index = (nowpage-1) * rowCnt;
            	//System.out.println(index + "<<index");
            }
			}
			sb = new StringBuffer();			
			sb.append(" ## 연관키워드 분석 (자동연관어)										\n");
			//sb.append("SELECT A.RK_SEQ, LEFT(A.RK_NAME,5) AS RK_NAME, COUNT(*) AS CNT												\n");
			
			//자동연관어 & 수동연관어 추가
			//자동연관어
			/*
			sb.append("SELECT RK_NAME                                                                                                                                \n");
			sb.append("		, SUM(CNT) AS CNT                                                                                                                            \n");
			sb.append("		, RK_SEQ                                                                                                                             \n");
			sb.append("		, SENTI_1                                                                                                                                \n");
			sb.append("		, SENTI_2                                                                                                                                \n");
			sb.append("		, SENTI_3                                                                                                                                \n");
			sb.append("		, LEVEL_1                                                                                                                                \n");
			sb.append("		, LEVEL_2                                                                                                                                \n");
			sb.append("		, LEVEL_3                                                                                                                                \n");
			sb.append("		, LEVEL_4                                                                                                                                \n");
			sb.append("	FROM (                                                                                                                                \n");
			sb.append("			 (                                                                                                                                \n");
			sb.append("				SELECT A.RK_SEQ, B.RK_NAME, COUNT(*) AS CNT												\n");
			sb.append("						, SUM(SENTI_1) AS SENTI_1												\n");
			sb.append("						, SUM(SENTI_2) AS SENTI_2												\n");
			sb.append("						, SUM(SENTI_3) AS SENTI_3												\n");
			sb.append("						, SUM(LEVEL_1) AS LEVEL_1												\n");
			sb.append("						, SUM(LEVEL_2) AS LEVEL_2 												\n");
			sb.append("						, SUM(LEVEL_3) AS LEVEL_3 												\n");
			sb.append("						, SUM(LEVEL_4) AS LEVEL_4												\n");
			sb.append("					FROM  AUTO_RELATION_KEYWORD_MAP A													\n");
			sb.append("      				 , RELATION_KEYWORD_R B												\n");
			sb.append("      				 , ( SELECT ID.ID_SEQ   		\n");
			sb.append("      	 			 		  , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") = '긍정', 1,0) AS SENTI_1 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)   											\n");
			sb.append("      	  			 		  , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") = '부정', 1,0) AS SENTI_2 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)   											\n");
			sb.append("      	  			 		  , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") = '중립', 1,0) AS SENTI_3 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)    											\n");
			sb.append("      	  			 		  , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ) = '1', 1,0) AS LEVEL_1 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)    	\n");
			sb.append("      	 			 		  , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ) = '2', 1,0) AS LEVEL_2 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)  											\n");
			sb.append("      	 				 	  , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ) = '3', 1,0) AS LEVEL_3 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)   											\n");
			sb.append("      	 			 		  , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ) = '4', 1,0) AS LEVEL_4 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)  											\n");
			sb.append("      					FROM ISSUE_DATA ID , ISSUE_DATA_CODE IDC 																			\n");
			sb.append("     			 		WHERE ID.ID_SEQ = IDC.ID_SEQ																						\n");
			sb.append("      					  AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간       						\n");
			sb.append("  						  AND SG_SEQ IN ("+sg_seqs+") #채널 																			\n");
			sb.append(" 						  AND IDC.IC_TYPE = 1																								\n");							
			sb.append(" 		 				  AND IDC.IC_CODE = "+group_code+" #IC_CODE >> (1:현카드 /2:현캐피/ 3:현커머)												\n");
			sb.append(" 	 					GROUP BY ID.ID_SEQ																								\n");
			sb.append("			 )C																								 	\n");
			sb.append("		WHERE A.RK_SEQ = B.RK_SEQ																								 	\n");
			sb.append("		  AND A.ID_SEQ = C.ID_SEQ																			\n");							
			sb.append("		  AND B.RK_USE = 'Y' 																			\n");							
			sb.append("		GROUP BY A.RK_SEQ   																			\n");							
			sb.append("		ORDER BY COUNT(*) DESC 																			\n");							
			sb.append("			LIMIT 100 																		\n");
			
			//수동연관어
			sb.append(")UNION (												\n");
			sb.append("				SELECT A.RK_SEQ, B.RK_NAME, COUNT(*) AS CNT												\n");
			sb.append("						, SUM(SENTI_1) AS SENTI_1												\n");
			sb.append("						, SUM(SENTI_2) AS SENTI_2												\n");
			sb.append("						, SUM(SENTI_3) AS SENTI_3												\n");
			sb.append("						, SUM(LEVEL_1) AS LEVEL_1												\n");
			sb.append("						, SUM(LEVEL_2) AS LEVEL_2 												\n");
			sb.append("						, SUM(LEVEL_3) AS LEVEL_3 												\n");
			sb.append("						, SUM(LEVEL_4) AS LEVEL_4												\n");
			sb.append("					FROM  RELATION_KEYWORD_MAP A						\n");
			sb.append("      				 , RELATION_KEYWORD_R B											\n");
			sb.append("      				 , ( SELECT ID.ID_SEQ  		\n");
			sb.append("      	 			 	 	  , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") = '긍정', 1,0) AS SENTI_1 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)   											\n");
			sb.append("      	  			 		  , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") = '부정', 1,0) AS SENTI_2 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)   											\n");
			sb.append("      	  			 		  , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") = '중립', 1,0) AS SENTI_3 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)    											\n");
			sb.append("      	  			 		  , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ) = '1', 1,0) AS LEVEL_1 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)    	\n");
			sb.append("      	 			 		  , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ) = '2', 1,0) AS LEVEL_2 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)  											\n");
			sb.append("      	 			 		  , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ) = '3', 1,0) AS LEVEL_3 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)   											\n");
			sb.append("      	 			 		  , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ) = '4', 1,0) AS LEVEL_4 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)  											\n");
			sb.append("      			FROM ISSUE_DATA ID , ISSUE_DATA_CODE IDC 																			\n");
			sb.append("     			 WHERE ID.ID_SEQ = IDC.ID_SEQ																						\n");
			sb.append("      				AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간       						\n");
			sb.append("  					AND SG_SEQ IN ("+sg_seqs+") #채널 																			\n");
			sb.append(" 					AND IDC.IC_TYPE = 1																								\n");							
			sb.append(" 		 			AND IDC.IC_CODE = "+group_code+" #IC_CODE >> (1:현카드 /2:현캐피/ 3:현커머)												\n");
			sb.append(" 	 				GROUP BY ID.ID_SEQ																								\n");
			sb.append("			)C 																							 	\n");
			sb.append("		WHERE A.RK_SEQ = B.RK_SEQ 																							 	\n");
			sb.append("		  AND A.ID_SEQ = C.ID_SEQ																			\n");							
			sb.append("		  AND B.RK_USE = 'Y'																			\n");							
			sb.append("		GROUP BY A.RK_SEQ  																		\n");							
			sb.append("		ORDER BY COUNT(*) DESC																			\n");							
			sb.append("		LIMIT 100 																		\n");
			sb.append("))A	                                                                                                                                                   \n");
			sb.append(" GROUP BY RK_NAME                                                                                                                                                 \n");
			sb.append(" ORDER BY CNT DESC , RK_NAME                                                                                                                                                 \n");
			if(nowpage == 0) {
			sb.append(" LIMIT 30                                                                                                                                          \n");
			}  else {
				sb.append(" LIMIT "+index+", "+rowCnt+"                                                                                                                                          \n");
			}
			*/
			
			sb.append("## 연관키워드 분석 (자동연관어 및 수동연관어)                                                                                                                                                                                        \n");
			sb.append("SELECT                                                                                                                                                                                                                               \n");
			sb.append("                RK_NAME                                                                                                                                                                                                              \n");
			sb.append("                , SUM(CNT) AS CNT                                                                                                                                                                                                    \n");
			sb.append("                , RK_SEQ                                                                                                                                                                                                             \n");
			sb.append("                , SENTI_1                                                                                                                                                                                                            \n");
			sb.append("                , SENTI_2                                                                                                                                                                                                            \n");
			sb.append("                , SENTI_3                                                                                                                                                                                                            \n");
			sb.append("                , LEVEL_1                                                                                                                                                                                                            \n");
			sb.append("                , LEVEL_2                                                                                                                                                                                                            \n");
			sb.append("                , LEVEL_3                                                                                                                                                                                                            \n");
			sb.append("                , LEVEL_4                                                                                                                                                                                                            \n");
			sb.append("        FROM (                                                                                                                                                                                                                       \n");
			sb.append("                        (                                                                                                                                                                                                            \n");
			sb.append("             SELECT A.RK_SEQ                                                                                                                                                                                                         \n");
			sb.append("                  , B.RK_NAME                                                                                                                                                                                                        \n");
			sb.append("                  , SUM(SENTI_1) AS SENTI_1                                                                                                                                                                                          \n");
			sb.append("                  , SUM(SENTI_2) AS SENTI_2                                                                                                                                                                                          \n");
			sb.append("                  , SUM(SENTI_3) AS SENTI_3                                                                                                                                                                                          \n");
			sb.append("                  , SUM(LEVEL_1) AS LEVEL_1                                                                                                                                                                                          \n");
			sb.append("                  , SUM(LEVEL_2) AS LEVEL_2                                                                                                                                                                                          \n");
			sb.append("                  , SUM(LEVEL_3) AS LEVEL_3                                                                                                                                                                                          \n");
			sb.append("                  , SUM(LEVEL_4) AS LEVEL_4                                                                                                                                                                                          \n");
			sb.append("                  , COUNT(*) AS CNT                                                                                                                                                                                                  \n");
			sb.append("               FROM AUTO_RELATION_KEYWORD_MAP A                                                                                                                                                                                      \n");
			sb.append("                  , RELATION_KEYWORD_R B                                                                                                                                                                                             \n");
			sb.append("                  , ( SELECT ID.ID_SEQ                                                                                                                                                                                               \n");
			sb.append("                           , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") = '긍정', 1,0) AS SENTI_1 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)                                                                                           			\n");
			sb.append("                           , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") = '부정', 1,0) AS SENTI_2 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)                                                                                           			\n");
			sb.append("                           , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") = '중립', 1,0) AS SENTI_3 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)                                                                                           			\n");
			sb.append("                           , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ) = '1', 1,0) AS LEVEL_1 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)                                                 		\n");
			sb.append("                           , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ) = '2', 1,0) AS LEVEL_2 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)                                                 		\n");
			sb.append("                           , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ) = '3', 1,0) AS LEVEL_3 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)                                                 		\n");
			sb.append("                           , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ) = '4', 1,0) AS LEVEL_4 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)                                                 		\n");
			sb.append("                        FROM ISSUE_DATA ID , ISSUE_DATA_CODE IDC                                                                                                                                                                     \n");
			sb.append("                       WHERE ID.ID_SEQ = IDC.ID_SEQ                                                                                                                                                                                  \n");
			sb.append("                         AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간                                                                                                                 														\n");
			sb.append("                         AND SG_SEQ IN ("+sg_seqs+") #채널 (보고서 - 카페,커뮤니티 고정)                                                                                                                                  			\n");
			sb.append("                         AND IDC.IC_TYPE = 1                                                                                                                                                                                         \n");
			sb.append("                         AND IDC.IC_CODE = "+group_code+" #IC_CODE >> (1:현카드 /2:현캐피/ 3:현커머)                                                                                                                                              		\n");
			sb.append("                       GROUP BY ID.ID_SEQ                                                                                                                                                                                            \n");
			sb.append("                    )C                                                                                                                                                                                                               \n");
			sb.append("             WHERE A.RK_SEQ = B.RK_SEQ                                                                                                                                                                                               \n");
			sb.append("               AND A.ID_SEQ = C.ID_SEQ                                                                                                                                                                                               \n");
			sb.append("               AND B.RK_USE = 'Y'                                                                                                                                                                                                    \n");
			sb.append("             GROUP BY A.RK_SEQ                                                                                                                                                                                                       \n");
			sb.append("             ORDER BY COUNT(*) DESC                                                                                                                                                                                                  \n");
			sb.append("             LIMIT 100                                                                                                                                                                                                               \n");
			sb.append(" )  UNION  (                                                                                                                                                                                                                         \n");
			
			//수동연관어
			sb.append("             SELECT A.RK_SEQ                                                                                                                                                                                                         \n");
			sb.append("                  , B.RK_NAME                                                                                                                                                                                                        \n");
			sb.append("                  , SUM(SENTI_1) AS SENTI_1                                                                                                                                                                                          \n");
			sb.append("                  , SUM(SENTI_2) AS SENTI_2                                                                                                                                                                                          \n");
			sb.append("                  , SUM(SENTI_3) AS SENTI_3                                                                                                                                                                                          \n");
			sb.append("                  , SUM(LEVEL_1) AS LEVEL_1                                                                                                                                                                                          \n");
			sb.append("                  , SUM(LEVEL_2) AS LEVEL_2                                                                                                                                                                                          \n");
			sb.append("                  , SUM(LEVEL_3) AS LEVEL_3                                                                                                                                                                                          \n");
			sb.append("                  , SUM(LEVEL_4) AS LEVEL_4                                                                                                                                                                                          \n");
			sb.append("                  , COUNT(*) AS CNT                                                                                                                                                                                                  \n");
			sb.append("               FROM RELATION_KEYWORD_MAP A                                                                                                                                                                                           \n");
			sb.append("                  , RELATION_KEYWORD_R B                                                                                                                                                                                             \n");
			sb.append("                  , ( SELECT ID.ID_SEQ                                                                                                                                                                                               \n");
			sb.append("                           , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") = '긍정', 1,0) AS SENTI_1 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)                                                                                           			\n");
			sb.append("                           , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") = '부정', 1,0) AS SENTI_2 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)                                                                                           			\n");
			sb.append("                           , IF(FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") = '중립', 1,0) AS SENTI_3 #긍부정 >> (14:현카드 /24:현캐피/ 34:현커머)                                                                                           			\n");
			sb.append("                           , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ) = '1', 1,0) AS LEVEL_1 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)                                                		\n");
			sb.append("                           , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ) = '2', 1,0) AS LEVEL_2 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)                                            	        \n");
			sb.append("                           , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ) = '3', 1,0) AS LEVEL_3 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)                                           	        \n");
			sb.append("                           , IF((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ) = '4', 1,0) AS LEVEL_4 #IC_TYPE >> (16:현카드 /26:현캐피/ 36:현커머)                                           	        \n");
			sb.append("                        FROM ISSUE_DATA ID , ISSUE_DATA_CODE IDC                                                                                                                                                                     \n");
			sb.append("                       WHERE ID.ID_SEQ = IDC.ID_SEQ                                                                                                                                                                                  \n");
			sb.append("                         AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간                                                                                                             														    \n");
			sb.append("                         AND SG_SEQ IN ("+sg_seqs+") #채널 (보고서 - 카페,커뮤니티 고정)                                                                                                                     			            \n");
			sb.append("                         AND IDC.IC_TYPE = 1                                                                                                                                                                                         \n");
			sb.append("                         AND IDC.IC_CODE = "+group_code+" #IC_CODE >> (1:현카드 /2:현캐피/ 3:현커머)                                                                                                                                  		            \n");
			sb.append("                       GROUP BY ID.ID_SEQ                                                                                                                                                                                            \n");
			sb.append("                    )C                                                                                                                                                                                                               \n");
			sb.append("             WHERE A.RK_SEQ = B.RK_SEQ                                                                                                                                                                                               \n");
			sb.append("               AND A.ID_SEQ = C.ID_SEQ                                                                                                                                                                                               \n");
			sb.append("               AND B.RK_USE = 'Y'                                                                                                                                                                                                    \n");
			sb.append("             GROUP BY A.RK_SEQ                                                                                                                                                                                                       \n");
			sb.append("             ORDER BY COUNT(*) DESC                                                                                                                                                                                                  \n");
			sb.append("             LIMIT 100                                                                                                                                                                                                               \n");
			sb.append(" )) A                                                                                                                                                                                                                                \n");
			sb.append(" GROUP BY RK_NAME                                                                                                                                                                                                                    \n");
			sb.append(" ORDER BY CNT DESC , RK_NAME                                                                                                                                                                                                         \n");
			if(nowpage == 0) {
			sb.append(" LIMIT 30                                                                                                                                          \n");
			}  else {
				sb.append(" LIMIT "+index+", "+rowCnt+"                                                                                                                                          \n");
			}			

			System.out.println(sb.toString());
			pstmt =null; rs =null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				dataMap = new HashMap();
				dataMap.put("RK_SEQ", rs.getString("RK_SEQ"));
				dataMap.put("RK_NAME", rs.getString("RK_NAME"));
				dataMap.put("CNT", rs.getString("CNT"));
				dataMap.put("WORD_COLOR",relationKeyword_color(new int[]{rs.getInt("SENTI_1"),rs.getInt("SENTI_2"),rs.getInt("SENTI_3")}, rs.getInt("LEVEL_1"),  rs.getInt("LEVEL_2"),  rs.getInt("LEVEL_3"),  rs.getInt("LEVEL_4") ));
				arrResult.add(dataMap);
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	
	/*
	 * 연관어 키워드 색상 고정 ( 감성 색상 , Level 색상) - 2021.03.16 배태환 차장님 확인
	 * 색상 우선 순위 : level > 감성
	 * level정보가 하나라도 있다면, level 색상으로 표시 
	 * 
	 * **/
	public String relationKeyword_color(int[] SENTI_ARR , int LEVEL_1, int LEVEL_2, int LEVEL_3, int LEVEL_4){
		String result = "";
		HashMap<String, String> color_map = new HashMap<String, String>();
		color_map.put("LEVEL_4", "#d96392");
		color_map.put("LEVEL_3", "#cd79da");
		color_map.put("LEVEL_2", "#788dd9");
		color_map.put("LEVEL_1", "#81afba");
		color_map.put("SENTI_1", "#5BA1E0");
		color_map.put("SENTI_2", "#EA7070");
		color_map.put("SENTI_3", "#B0B0B0");
		
		
		if(LEVEL_4 >0){
			result = color_map.get("LEVEL_4");
		}else if(LEVEL_3 >0){
			result = color_map.get("LEVEL_3");
		}else if(LEVEL_2 >0){
			result = color_map.get("LEVEL_2");
		}else if(LEVEL_1 >0){
			result = color_map.get("LEVEL_1");
		}else{
			int tmp_max = 0;
			int tmp_max_idx = 0;
			for(int i=0; i<SENTI_ARR.length; i++){
				if(SENTI_ARR[i]>tmp_max){
					tmp_max = SENTI_ARR[i];
					tmp_max_idx = i;
				}
			}
			
			result = color_map.get("SENTI_"+(tmp_max_idx+1));			
			
		}
		
		return result;
		
	}
	
	 public String saveChartImage(String name, String binary, String SS_M_NO){
	    	
	    	String result = "";
	    	
	    	DateUtil du = new DateUtil();
	    	ConfigUtil cu = new ConfigUtil();
	    	String savePath	= cu.getConfig("CHARTPATH");
	    	String urlPath	= cu.getConfig("CHARTURL");
	    	
	    	String fileName = name+"_"+SS_M_NO+"_"+du.getCurrentDate("yyyyMMddHHmmss")+".png";
	    	
			try {
				BASE64Decoder decoder = new BASE64Decoder();
				byte[] imgBytes = decoder.decodeBuffer(binary.split(",")[1]);
				//byte[] imgBytes = decoder.decodeBuffer(binary);
				System.out.println(binary + "<<binary");
				BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imgBytes));
				File imgOutFile = new File(savePath+fileName);
			
				ImageIO.write(bufImg, "png", imgOutFile);
				
				result = urlPath+fileName;
				//System.out.println(result + "<<result");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = "";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = "";
			}
		
	    	
	    	return result;
	    }
	 
	 	//소비자보호뉴스
		public ArrayList getConsumerNews(String ir_sdate, String ir_edate) {
			
			ArrayList arrResult = new ArrayList();
			Map dataMap = new HashMap();
			StringBuffer sb = new StringBuffer();
			
			try {
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				
				sb = new StringBuffer();			
				sb.append(" ## 소비자뉴스 - 2021.03.18 ( 검색기간내 유사율 높은 기사는 하나만 출력) 									\n");
			/*	sb.append("SELECT md_seq												\n");
				sb.append("     , md_pseq											\n");
				sb.append("     , md_title											\n");
				sb.append("     , md_site_name											\n");
				sb.append("     , md_url 		\n");
				sb.append("     , MD_DATE												\n");
				sb.append("     , DATE_FORMAT(MD_DATE,'%Y-%m-%d %H:%i' )AS 'MD_DATE1'	\n");
				sb.append("FROM WARNING										\n");
				sb.append("  WHERE  MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간 											\n");
				sb.append("  AND SB_SEQ NOT IN 											\n");
				sb.append("     (71271,71277,71278,71279,5096456,5096457,5096458,5096459,5096460,5096461,5096462,5096463,5096464,5096465,5096466,5096467,5096468,5096469,5096470,5096471,5096472,5096473		\n");
				sb.append("      ,5096474,5096475,5096476,5096477,5096478,5096479,5096480,5096481,5096482,5096483,5151794,5188565,518856671921,74460,74463,5101791)											\n");
				sb.append("  GROUP BY MD_PSEQ							\n");							
				sb.append("  ORDER BY MD_DATE DESC 							\n");							
				sb.append("  LIMIT 5								\n");*/							
				
				sb.append(" SELECT A.* 												\n");
				sb.append(" FROM ( 												\n");
				sb.append("			SELECT md_seq												\n");
				sb.append("     		, md_pseq											\n");
				sb.append("     		, md_title											\n");
				sb.append("     		, md_site_name											\n");
				sb.append("    			, md_url 		\n");
				sb.append("     		, MD_DATE												\n");
				sb.append("    			 , DATE_FORMAT(MD_DATE,'%Y-%m-%d %H:%i' )AS 'MD_DATE1'	\n");
				sb.append("    			 , W.S_SEQ	\n");
				//기사 노출 순서 조정을 위한 컬럼 추가 - 2021.05.14
				sb.append("    			 , TS_RANK	\n");
				sb.append("			FROM WARNING W													\n");
				sb.append("			 ,(                                                             \n");
				sb.append("			      SELECT S_SEQ                                              \n");
				sb.append("			        FROM SITE                                               \n");
				sb.append("			      WHERE S_TAG NOT LIKE '%지역%'                               \n");
				sb.append("  				AND S_SEQ NOT IN (456, 5025376, 784, 5118, 9343, 32, 11125, 5031886,5018223, 901, 131)											\n"); //특정 일간지 제외 - 고객사 요청				
				sb.append("			       UNION ALL                                               \n");
				sb.append("			     SELECT S_SEQ                                               \n");
				sb.append("			       FROM SITE                                             	\n");
				sb.append("			      WHERE S_SEQ IN (34,755,783,835,843,845,846,847,848,849,850,851,852,853,855,856,857,860,861,863,864,867,868,910,1029,1031,1066,1067,1068,1069,1070,1071,1072,1073,1075,1076,1081,1082,1828,1832,4085,4164,4176,4268,4974,4975,5115,7991,8318,5023503,5023504)                                       \n"); //일간지 중 미제외 요청 리스트 - 2021.05.18 강보명 과장님 확인
				sb.append("			   )AS S                                                           \n"); //고객사 요청 및 내부 협의 - 지역신문, 일간지 노출 제외
				sb.append("  WHERE  MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간 											\n");
				sb.append("  	AND S.S_SEQ = W.S_SEQ											\n");
				sb.append("  AND SB_SEQ NOT IN 											\n");
				sb.append("     (71271,71277,71278,71279,5096456,5096457,5096458,5096459,5096460,5096461,5096462,5096463,5096464,5096465,5096466,5096467,5096468,5096469,5096470,5096471,5096472,5096473		\n");
				sb.append("      ,5096474,5096475,5096476,5096477,5096478,5096479,5096480,5096481,5096482,5096483,5151794,5188565,5188566,71921,74460,74463,5101791)											\n");
				sb.append("  GROUP BY MD_PSEQ							\n");
				sb.append("  )A												\n");
				sb.append("  GROUP BY SUBSTRING( REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(md_title,'…',''),'·',''),' ',''),'.',''),' ',''),1,20) 							\n");
				//sb.append("  ORDER BY MD_DATE DESC 							\n");
				sb.append("  ORDER BY  TS_RANK ASC, FIELD(S_SEQ, 7566, 103) DESC, MD_DATE DESC 							\n"); //고객사 우선순위 요청 (금감원, 금융회) - 2021.04.30
				sb.append("  LIMIT 5								\n");	
			
			
				
				System.out.println(sb.toString());
				pstmt =null; rs =null;
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					dataMap = new HashMap();
					dataMap.put("md_seq", rs.getString("md_seq"));
					dataMap.put("md_title", su.toHtmlString(rs.getString("md_title")));
					dataMap.put("md_site_name", rs.getString("md_site_name"));
					dataMap.put("md_url", rs.getString("md_url"));
					dataMap.put("MD_DATE", rs.getString("MD_DATE"));
					dataMap.put("MD_DATE1", rs.getString("MD_DATE1"));
					
					arrResult.add(dataMap);
				}
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			return arrResult;
		} 

		
	//연관어 분석 클릭 시 해당 데이터 모두 출력 - 카드(12), 캐피탈(22), 커머셜(32)
	public String getSelectedKeyword(String seq) {
				
		String result = "";
		StringBuffer sb = new StringBuffer();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			//쿼리문 확인
			sb = new StringBuffer();			
			sb.append(" ## 선택한 연관어 키워드									\n");
			sb.append("SELECT R.RK_NAME     												\n");
			sb.append("FROM RELATION_KEYWORD_R R , AUTO_RELATION_KEYWORD_MAP MAP		\n");
			sb.append("WHERE R.RK_SEQ = MAP.RK_SEQ												\n");
			sb.append("AND R.RK_USE = 'Y'     	\n");
			sb.append("AND MAP.RK_SEQ = "+seq+" 											\n");
			sb.append("GROUP BY R.RK_NAME 											\n");
											
			System.out.println(sb.toString());
			pstmt =null; rs =null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
					
			if(rs.next()){
				result = rs.getString("RK_NAME");
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	
	//연관어 분석 클릭 시 해당 데이터 모두 출력 - 카드(12), 캐피탈(22), 커머셜(32)
	public String getSelectedKeyword_name(String seq) {
				
		String result = "";
		StringBuffer sb = new StringBuffer();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			//쿼리문 확인
			sb = new StringBuffer();			
			sb.append(" ## 선택한 연관어 키워드의 NAME 조회									\n");
			sb.append("SELECT R.RK_NAME    												\n");
			sb.append("FROM RELATION_KEYWORD_R R		\n");
			sb.append("WHERE R.RK_USE = 'Y'     												\n");
			sb.append("AND R.RK_SEQ = '"+seq+"'     	\n");
			sb.append("GROUP BY R.RK_SEQ 											\n");
											
			System.out.println(sb.toString());
			pstmt =null; rs =null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
					
			if(rs.next()){
				result = rs.getString("RK_NAME");
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
		
	//연관어 분석 클릭 시 해당 데이터 모두 출력 - 카드(12), 캐피탈(22), 커머셜(32)
	public ArrayList getRelationKeywordDataList(int nowpage, int rowCnt, String ir_sdate, String ir_edate, String type, String seq, String keyword, String senti_type) {
			
		ArrayList arrResult = new ArrayList();
		Map dataMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		int rowcount = 0;
		String explain = "현대카머셜";
		int index = 0;
		String senti_code = "34";
		String level_code = "36";
		String sg_seqs = "94,95,97,99,100";
		if(type.equals("12")) {
			explain = "현대카드";
			senti_code = "14";
			sg_seqs = "95,97";
		} else if(type.equals("22")) {
			explain = "현대캐피탈";
			senti_code = "24";
			sg_seqs = "94,95,97";
		}
	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
	       	sb = new StringBuffer();
	      /*  sb.append("SELECT COUNT(*) AS CNT 	\n");
	       	sb.append("FROM ISSUE_DATA ID 	\n");
	       	sb.append("	  , AUTO_RELATION_KEYWORD_MAP MAP 	\n");
	       	sb.append("	  , ISSUE_DATA_CODE IDC 	\n");
	       	sb.append("WHERE MD_DATE BETWEEN '"+ir_sdate+" 00:00:00'  AND '"+ir_edate+" 23:59:59'  	\n");
	       	sb.append("AND ID.ID_SEQ = IDC.ID_SEQ	\n");
	       	sb.append("AND ID.ID_SEQ = MAP.ID_SEQ	\n");
	       	sb.append("AND ID.SG_SEQ IN (95, 97)	\n");
	       	sb.append("AND MAP.RK_SEQ = "+seq+"		\n");
	       	if(!keyword.equals("")) {
	       	sb.append(" AND ID.ID_TITLE LIKE '%"+keyword+"%'	\n");
	       	} 
			if(!senti_type.equals("")) {
			sb.append("   AND IDC.IC_TYPE = "+senti_code+" #(14:현카드 /24:현캐피/ 34:현커머)							\n");	
			sb.append("   AND IDC.IC_CODE = "+senti_type+"			\n");	
			} else {
			sb.append("   AND IDC.IC_TYPE = "+type+"						\n");	
			}*/
	       	
	        sb.append("SELECT SUM(A.CNT+B.CNT) AS CNT FROM ( 	\n");
	        //자동연관어
	        sb.append("SELECT COUNT(*) AS CNT 	\n");
	       	sb.append("FROM ISSUE_DATA ID 	\n");
	       	sb.append("	  , AUTO_RELATION_KEYWORD_MAP MAP 	\n");
	       	sb.append("	  , ISSUE_DATA_CODE IDC 	\n");
	       	sb.append("WHERE MD_DATE BETWEEN '"+ir_sdate+" 00:00:00'  AND '"+ir_edate+" 23:59:59'  	\n");
	       	sb.append("AND ID.ID_SEQ = IDC.ID_SEQ	\n");
	       	sb.append("AND ID.ID_SEQ = MAP.ID_SEQ	\n");
	       	sb.append("AND ID.SG_SEQ IN ("+sg_seqs+")	\n");
	       	sb.append("AND MAP.RK_SEQ= "+seq+"		\n");
	       	//검섹 조건은 AND
	       	if(!keyword.equals("")) {
		       	String[] keyword_split = keyword.split("\\s+");
		       	sb.append(" AND (ID.ID_TITLE LIKE '%"+keyword_split[0]+"%'	\n");
		       		for(int i = 1; i < keyword_split.length; i++) {
		       			sb.append(" AND ID.ID_TITLE LIKE '%"+keyword_split[i]+"%'	\n");
		       		}
		       	sb.append("  )                      												\n");
		    } 
			if(!senti_type.equals("")) {
			sb.append("   AND IDC.IC_TYPE = "+senti_code+" #(14:현카드 /24:현캐피/ 34:현커머)							\n");	
			sb.append("   AND IDC.IC_CODE = "+senti_type+"			\n");	
			} else {
			sb.append("   AND IDC.IC_TYPE = "+type+"						\n");	
			}
			//수동연관어
			sb.append(" )A, (											\n");			
			sb.append("SELECT COUNT(*) AS CNT 							\n");
	       	sb.append("FROM ISSUE_DATA ID 								\n");
	       	sb.append("	  , RELATION_KEYWORD_MAP MAP 				\n");
	       	sb.append("	  , ISSUE_DATA_CODE IDC 							\n");
	       	sb.append("WHERE MD_DATE BETWEEN '"+ir_sdate+" 00:00:00'  AND '"+ir_edate+" 23:59:59'  	\n");
	       	sb.append("AND ID.ID_SEQ = IDC.ID_SEQ								\n");
	       	sb.append("AND ID.ID_SEQ = MAP.ID_SEQ	\n");
	       	sb.append("AND ID.SG_SEQ IN ("+sg_seqs+")	\n");
	       	sb.append("AND MAP.RK_SEQ = "+seq+"		\n");
	       	//검섹 조건은 AND
	       	if(!keyword.equals("")) {
		       	String[] keyword_split = keyword.split("\\s+");
		       	sb.append(" AND (ID.ID_TITLE LIKE '%"+keyword_split[0]+"%'	\n");
		       		for(int i = 1; i < keyword_split.length; i++) {
		       			sb.append(" AND ID.ID_TITLE LIKE '%"+keyword_split[i]+"%'	\n");
		       		}
		       	sb.append("  )                      												\n");
		    } 
			if(!senti_type.equals("")) {
			sb.append("   AND IDC.IC_TYPE = "+senti_code+" #(14:현카드 /24:현캐피/ 34:현커머)							\n");	
			sb.append("   AND IDC.IC_CODE = "+senti_type+"			\n");	
			} else {
			sb.append("   AND IDC.IC_TYPE = "+type+"						\n");	
			}
			sb.append(" )B					\n");
			
			System.out.println(sb.toString()); 	
			pstmt = dbconn.createPStatement(sb.toString());
		    rs = pstmt.executeQuery();      
	        	
	        if(rs.next()){
	           FullCnt = rs.getInt("CNT");
	        }
	        

	        index = (nowpage-1) * rowCnt;
	        System.out.println(index + "<<index");
	        
			
			
			//쿼리문 확인
			sb = new StringBuffer();			
			sb.append(" ## 연관어 분석 팝업 리스트 - "+explain+"									\n");
			sb.append("SELECT A.* FROM  (      												\n");
			
			sb.append("	SELECT ID.ID_SEQ      												\n");
			//sb.append("     , DATE_FORMAT(MD_DATE,'%Y-%m-%d %H:%i' )AS 'MD_DATE' 		\n");
			//sb.append("     , DATE_FORMAT(MD_DATE,'%Y-%m-%d %H:%') AS 'MD_DATE' 		\n");
			sb.append("     , ID.MD_DATE 		\n");
			sb.append("     , ID.MD_SITE_NAME												\n");
			sb.append("     , ID.ID_TITLE     	\n");
			sb.append("     , ID.ID_URL 											\n");
			sb.append("     , ID.S_SEQ 											\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") AS 'SENTI' #(IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머)		\n");
			sb.append("     ,(SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+senti_code+") AS SENTI_CODE #긍부정 (IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머)		\n");
			sb.append("FROM ISSUE_DATA ID								\n");							
			sb.append("	  , ISSUE_DATA_CODE IDC							\n");							
			sb.append("   , AUTO_RELATION_KEYWORD_MAP MAP							\n");		
			sb.append("WHERE ID.ID_SEQ = IDC.ID_SEQ							\n");							
			sb.append("   AND ID.ID_SEQ = MAP.ID_SEQ								\n");							
			sb.append("   AND ID.MD_DATE BETWEEN '"+ir_sdate+" 00:00:00'  AND '"+ir_edate+" 23:59:59'  								\n");							
			sb.append("   AND MAP.RK_SEQ = "+seq+"  					\n");	
			sb.append("   AND ID.SG_SEQ IN ("+sg_seqs+")						\n");	
			//검섹 조건은 AND
			if(!keyword.equals("")) {
		       	String[] keyword_split = keyword.split("\\s+");
		       	sb.append(" AND (ID.ID_TITLE LIKE '%"+keyword_split[0]+"%'	\n");
		       		for(int i = 1; i < keyword_split.length; i++) {
		       			sb.append(" AND ID.ID_TITLE LIKE '%"+keyword_split[i]+"%'	\n");
		       		}
		       	sb.append("  )                      												\n");
		    }
			if(!senti_type.equals("")) {
			sb.append("   AND IDC.IC_TYPE = "+senti_code+" 		#(14:현카드 /24:현캐피/ 34:현커머)							\n");	
			sb.append("   AND IDC.IC_CODE = "+senti_type+"			\n");	
			} else {
			sb.append("   AND IDC.IC_TYPE = "+type+"						\n");	
			}
			sb.append("GROUP BY IDC.ID_SEQ							\n");	
			
			//수동연관어 데이터
			sb.append("	UNION       												\n");
			sb.append("	SELECT ID.ID_SEQ      												\n");
			//sb.append("     , DATE_FORMAT(MD_DATE,'%Y-%m-%d %H:%i' )AS 'MD_DATE' 		\n");
			//sb.append("     , DATE_FORMAT(MD_DATE,'%Y-%m-%d %H:%') AS 'MD_DATE' 		\n");
			sb.append("     , ID.MD_DATE 		\n");
			sb.append("     , ID.MD_SITE_NAME												\n");
			sb.append("     , ID.ID_TITLE     	\n");
			sb.append("     , ID.ID_URL 											\n");
			sb.append("     , ID.S_SEQ 											\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") AS 'SENTI' #(IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머)		\n");
			sb.append("     ,(SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+senti_code+") AS SENTI_CODE #긍부정 (IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머)		\n");
			sb.append("FROM ISSUE_DATA ID								\n");							
			sb.append("	  , ISSUE_DATA_CODE IDC							\n");							
			sb.append("   , RELATION_KEYWORD_MAP MAP							\n");		
			sb.append("WHERE ID.ID_SEQ = IDC.ID_SEQ							\n");							
			sb.append("   AND ID.ID_SEQ = MAP.ID_SEQ								\n");							
			sb.append("   AND ID.MD_DATE BETWEEN '"+ir_sdate+" 00:00:00'  AND '"+ir_edate+" 23:59:59'  								\n");							
			sb.append("   AND MAP.RK_SEQ = "+seq+"  					\n");	
			sb.append("   AND ID.SG_SEQ IN ("+sg_seqs+")						\n");	
			//검섹 조건은 AND
			if(!keyword.equals("")) {
		       	String[] keyword_split = keyword.split("\\s+");
		       	sb.append(" AND (ID.ID_TITLE LIKE '%"+keyword_split[0]+"%'	\n");
		       		for(int i = 1; i < keyword_split.length; i++) {
		       			sb.append(" AND ID.ID_TITLE LIKE '%"+keyword_split[i]+"%'	\n");
		       		}
		       	sb.append("  )                      												\n");
		    }
			if(!senti_type.equals("")) {
			sb.append("   AND IDC.IC_TYPE = "+senti_code+" 		#(14:현카드 /24:현캐피/ 34:현커머)							\n");	
			sb.append("   AND IDC.IC_CODE = "+senti_type+"			\n");	
			} else {
			sb.append("   AND IDC.IC_TYPE = "+type+"						\n");	
			}
			sb.append("GROUP BY IDC.ID_SEQ							\n");	
			
			
			sb.append(")A 											\n");
			sb.append("ORDER BY MD_DATE DESC						\n");
			//sb.append("LIMIT "+index+", 10							\n");
			
			sb.append("LIMIT "+index+", "+rowCnt+"							\n");	
			
			System.out.println(sb.toString());
			pstmt =null; rs =null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
				
			while(rs.next()){
				dataMap = new HashMap();
				dataMap.put("ID_SEQ", rs.getString("ID_SEQ"));
				dataMap.put("MD_DATE", rs.getString("MD_DATE"));
				dataMap.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
				dataMap.put("ID_TITLE", su.toHtmlString(rs.getString("ID_TITLE")));
				dataMap.put("ID_URL", rs.getString("ID_URL"));
				dataMap.put("SENTI", rs.getString("SENTI"));
				dataMap.put("SENTI_CODE", rs.getString("SENTI_CODE"));
				dataMap.put("S_SEQ", rs.getString("S_SEQ"));
				//dataMap.put("ID_CONTENT", cutKey(su.toHtmlString(rs.getString("ID_CONTENT")), rs.getString("K_VALUE")));

				arrResult.add(dataMap);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
			return arrResult;
		}	
		
	 //소비자보호뉴스 - 더보기
	//public ArrayList getConsumerNews_Popup(int nowpage, int rowCnt, String ir_sdate,  String ir_edate) {
	//검색기능 추가 - 고객사 요청사항 (2021.04.20)
	public ArrayList getConsumerNews_Popup(int nowpage, int rowCnt, String ir_sdate,  String ir_edate, String keyword) {
			
		ArrayList arrResult = new ArrayList();
		Map dataMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
       	 	sb = new StringBuffer();
       	 	/*sb.append("SELECT COUNT(DISTINCT(MD_PSEQ)) AS CNT 	\n");
       	 	sb.append("FROM WARNING 	\n");
       	 	sb.append("WHERE  MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간 	\n");
       	 	sb.append("AND SB_SEQ NOT IN  	\n");
       	 	sb.append("	   (71271,71277,71278,71279,5096456,5096457,5096458,5096459,5096460,5096461,5096462,5096463,5096464,5096465,5096466,5096467,5096468,5096469,5096470,5096471,5096472,5096473	\n");
       	 	sb.append("		,5096474,5096475,5096476,5096477,5096478,5096479,5096480,5096481,5096482,5096483,5151794,5188565,518856671921,74460,74463,5101791)	\n");
       	 	*/

       	 	//임시 유사처리용
       	 	sb.append("  SELECT COUNT(*) AS CNT 																																												\n");
       	 	sb.append("    FROM (                                                                                                                                                                                               \n");
       	 	sb.append("      SELECT COUNT(*) AS CNT                                                                                                                                                                             \n");
       	 	sb.append("        FROM (                                                                                                                                                                                           \n");
       	 	sb.append("           SELECT  md_title                                                                                                                                                                              \n");
       	 	sb.append("			FROM WARNING W													\n");
			sb.append("			 ,(                                                             \n");
			sb.append("			      SELECT S_SEQ                                              \n");
			sb.append("			        FROM SITE                                               \n");
			sb.append("			      WHERE S_TAG NOT LIKE '%지역%'                               \n");
			sb.append("  				AND S_SEQ NOT IN (456, 5025376, 784, 5118, 9343, 32, 11125, 5031886,5018223, 901, 131)											\n"); //특정 일간지 제외 - 고객사 요청				
			sb.append("			       UNION ALL                                               \n");
			sb.append("			     SELECT S_SEQ                                               \n");
			sb.append("			       FROM SITE                                             	\n");
			sb.append("			      WHERE S_SEQ IN (34,755,783,835,843,845,846,847,848,849,850,851,852,853,855,856,857,860,861,863,864,867,868,910,1029,1031,1066,1067,1068,1069,1070,1071,1072,1073,1075,1076,1081,1082,1828,1832,4085,4164,4176,4268,4974,4975,5115,7991,8318,5023503,5023504)                                       \n"); //일간지 중 미제외 요청 리스트 - 2021.05.18 강보명 과장님 확인
			sb.append("			   )AS S                                                           \n"); //고객사 요청 및 내부 협의 - 지역신문, 일간지 노출 제외
       	 	sb.append("          WHERE  MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간                                                                                                    																	 \n");
       	 	sb.append("  		 AND S.S_SEQ = W.S_SEQ											\n");
       	 	sb.append("          AND SB_SEQ NOT IN (71271,71277,71278,71279,5096456,5096457,5096458,5096459,5096460,5096461,5096462,5096463,5096464,5096465,5096466,5096467,5096468,5096469                                     \n");
       	 	sb.append("          				,5096470,5096471,5096472,5096473,5096474,5096475,5096476,5096477,5096478,5096479,5096480,5096481,5096482,5096483,5151794,5188565,5188566,71921,74460,74463,5101791)              \n");
			//검섹 조건은 AND
			if(!keyword.equals("")) {
		       	String[] keyword_split = keyword.split("\\s+");
		       	sb.append(" 	AND (MD_TITLE LIKE '%"+keyword_split[0]+"%'	\n");
		       		for(int i = 1; i < keyword_split.length; i++) {
		       			sb.append(" AND MD_TITLE LIKE '%"+keyword_split[i]+"%'	\n");
		       		}
		       	sb.append("  		)                      												\n");
		    }
       	 	sb.append("          GROUP BY MD_PSEQ                                                                                                                                                                               \n");
       	 	sb.append("          )A                                                                                                                                                                                             \n");
       	 	sb.append("   GROUP BY SUBSTRING( REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(md_title,'…',''),'·',''),' ',''),'.',''),' ',''),1,20)                                                                                    \n");
       	 	sb.append("   ) A                                                                                                                                                                                                   \n");
       				
			pstmt = dbconn.createPStatement(sb.toString());
	     	rs = pstmt.executeQuery();      
        	
            if(rs.next()){
            	FullCnt = rs.getInt("CNT");
            	System.out.println(FullCnt + "<<FullCnt"); 
            }
            
            int index = (nowpage-1) * rowCnt;
			System.out.println(index + "<<index");
			
			sb = new StringBuffer();			
			sb.append(" ## 소비자뉴스 - 더보기									\n");
			/*sb.append("SELECT md_seq												\n");
			sb.append("		, md_pseq												\n");
			sb.append("     , md_title											\n");
			sb.append("     , md_site_name											\n");
			sb.append("     , md_url 		\n");
			sb.append("     , MD_DATE												\n");
			sb.append("     , DATE_FORMAT(MD_DATE,'%Y-%m-%d %H:%i' )AS 'MD_DATE'	\n");
			sb.append("FROM WARNING										\n");
			sb.append("  WHERE  MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간 											\n");
			sb.append("  AND SB_SEQ NOT IN 											\n");
			sb.append("     (71271,71277,71278,71279,5096456,5096457,5096458,5096459,5096460,5096461,5096462,5096463,5096464,5096465,5096466,5096467,5096468,5096469,5096470,5096471,5096472,5096473		\n");
			sb.append("      ,5096474,5096475,5096476,5096477,5096478,5096479,5096480,5096481,5096482,5096483,5151794,5188565,518856671921,74460,74463,5101791)											\n");
			sb.append("  GROUP BY MD_PSEQ 								\n");							
			sb.append("  ORDER BY MD_DATE DESC 								\n");							
			sb.append(" LIMIT "+index+", 8									\n");			*/		
			
			//임시처리
			sb.append("SELECT A.* 												\n");
			sb.append("FROM (												\n");
			sb.append("		SELECT md_seq												\n");
			sb.append("			, md_pseq												\n");
			sb.append("     	, md_title											\n");
			sb.append("    		, md_site_name											\n");
			sb.append("    		, md_url 		\n");
			sb.append("     	, MD_DATE												\n");
			sb.append("     	, DATE_FORMAT(MD_DATE,'%Y-%m-%d %H:%i' )AS 'MD_DATE1'	\n");
			sb.append("     	, W.S_SEQ	\n");
			//순위조정을 위한 ts_rank 추가 - 2021.05.14
			sb.append("     	, TS_RANK	\n");
			sb.append("			FROM WARNING W													\n");
			sb.append("			 ,(                                                             \n");
			sb.append("			      SELECT S_SEQ                                              \n");
			sb.append("			        FROM SITE                                               \n");
			sb.append("			      WHERE S_TAG NOT LIKE '%지역%'                               \n");
			sb.append("  				AND S_SEQ NOT IN (456, 5025376, 784, 5118, 9343, 32, 11125, 5031886,5018223, 901, 131)											\n"); //특정 일간지 제외 - 고객사 요청				
			sb.append("			       UNION ALL                                               \n");
			sb.append("			     SELECT S_SEQ                                               \n");
			sb.append("			       FROM SITE                                             	\n");
			sb.append("			      WHERE S_SEQ IN (34,755,783,835,843,845,846,847,848,849,850,851,852,853,855,856,857,860,861,863,864,867,868,910,1029,1031,1066,1067,1068,1069,1070,1071,1072,1073,1075,1076,1081,1082,1828,1832,4085,4164,4176,4268,4974,4975,5115,7991,8318,5023503,5023504)                                       \n"); //일간지 중 미제외 요청 리스트 - 2021.05.18 강보명 과장님 확인
			sb.append("			   )AS S                                                           \n"); //고객사 요청 및 내부 협의 - 지역신문, 일간지 노출 제외
			sb.append("  		WHERE MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간 											\n");
			sb.append("  		AND S.S_SEQ = W.S_SEQ											\n");
			sb.append("  		AND SB_SEQ NOT IN 											\n");
			sb.append("     	(71271,71277,71278,71279,5096456,5096457,5096458,5096459,5096460,5096461,5096462,5096463,5096464,5096465,5096466,5096467,5096468,5096469,5096470,5096471,5096472,5096473		\n");
			sb.append("     	 ,5096474,5096475,5096476,5096477,5096478,5096479,5096480,5096481,5096482,5096483,5151794,5188565,5188566,71921,74460,74463,5101791)											\n");
			//검섹 조건은 AND
			if(!keyword.equals("")) {
		       	String[] keyword_split = keyword.split("\\s+");
		       	sb.append(" 	AND (MD_TITLE LIKE '%"+keyword_split[0]+"%'	\n");
		       		for(int i = 1; i < keyword_split.length; i++) {
		       			sb.append(" AND MD_TITLE LIKE '%"+keyword_split[i]+"%'	\n");
		       		}
		       	sb.append("  		)                      												\n");
		    }
			sb.append("  		GROUP BY MD_PSEQ 								\n");							
			sb.append("  )A 								\n");
			sb.append(" GROUP BY SUBSTRING( REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(md_title,'…',''),'·',''),' ',''),'.',''),' ',''),1,20) 								\n");
			//sb.append("  ORDER BY MD_DATE DESC 								\n");
			//순위조정을 위한 ts_rank 추가 - 2021.05.14
			sb.append("  ORDER BY TS_RANK ASC, FIELD(S_SEQ, 7566, 103) DESC,MD_DATE DESC 								\n"); //고객사 우선순위 요청 (금감원, 금융회) - 2021.04.30
			sb.append(" LIMIT "+index+", "+rowCnt+"									\n");	
       	 	                       
							
				
			System.out.println(sb.toString());
			pstmt =null; rs =null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
				
			while(rs.next()){
				dataMap = new HashMap();
				dataMap.put("md_seq", rs.getString("md_seq"));
				dataMap.put("md_pseq", rs.getString("md_pseq"));
				dataMap.put("md_title", su.toHtmlString(rs.getString("md_title")));
				dataMap.put("md_site_name", rs.getString("md_site_name"));
				dataMap.put("md_url", rs.getString("md_url"));
				dataMap.put("MD_DATE", rs.getString("MD_DATE"));
				dataMap.put("MD_DATE1", rs.getString("MD_DATE"));
				dataMap.put("TS_RANK", rs.getString("TS_RANK"));
					
				arrResult.add(dataMap);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	} 	
	
	//소비자뉴스 기사 삭제 : mode del
	public boolean DeleteConsumerNews(String md_seqs) 
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        boolean result = false;
        String[] md_seq = md_seqs.split(",");
        String md_pseq = "";
        try {       	      	
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();	      
       	 	
	        for(int i=0; i < md_seq.length; i++) {
	        	sb = new StringBuffer();
	        	
	        	sb.append(" SELECT MD_PSEQ FROM WARNING						\n");
	        	sb.append("	WHERE MD_SEQ = "+md_seq[i]+" 		\n");
	        	
	       	 	Log.debug(sb.toString()); 
		        rs = stmt.executeQuery(sb.toString());
		
		        if (rs.next()) {
		        	md_pseq = rs.getString("MD_PSEQ");
	            }
		        
		        sb = new StringBuffer();
		        
		        sb.append(" DELETE FROM WARNING						\n");
				sb.append("		WHERE MD_PSEQ = "+md_pseq+" 		\n");
		        
				Log.debug(sb.toString());
				stmt.executeUpdate(sb.toString());
			 	result = true;
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
        return result;
	}
	
	
	//소비자보효뉴스 상단고정 버튼 클릭 시 ts_rank에 사용자가 넣은 랭크값 update (상위노출) : mode update
 	public boolean UpdateConsumerNews(String md_seqs, String rank_list)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        boolean result = false;
        String[] md_seq = md_seqs.split(",");
        String[] rank = rank_list.split(",");
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement(); 
		    
       	 	//MD_SEQ 하나하나 마다
		    for(int i = 0; i < md_seq.length; i++) {
		    	sb = new StringBuffer();

	    		//사용자가 입력한 RANK값이 기존의 RANK값에 있으면 RANK + 1(999제외)
	    		sb.append(" UPDATE WARNING	\n");
				sb.append(" SET  		\n");
				sb.append(" 	  TS_RANK= TS_RANK + 1 	\n");		
			 	sb.append(" WHERE TS_RANK BETWEEN "+rank[i]+" AND 998 		\n");
		    		
			 	Log.debug(sb.toString());
			 	System.out.println(sb.toString());
			 	stmt.executeUpdate(sb.toString());
			 	
		    	//사용자가 선택한 MD_SEQ에 RANK값 업데이트
		    	sb = new StringBuffer();

		    	sb.append(" UPDATE WARNING	\n");
				sb.append(" SET  		\n");
				sb.append(" 	  TS_RANK= "+rank[i]+" 	\n");		
			 	sb.append(" WHERE MD_SEQ = "+md_seq[i]+"		\n");
			 	
			 	
			 	Log.debug(sb.toString());
			 	System.out.println(sb.toString());
			 	stmt.executeUpdate(sb.toString());
		    }

		 	result = true;
		        
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
        return result;
	
	}	
 	
	//상단고정 해제 버튼 클릭 시 ts_rank 999로 돌림 : mode down
 	public boolean RestoreConsumerNews(String md_seqs)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        boolean result = false;
        int chkcount = 0;
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();       	 	
       	 		
		    sb = new StringBuffer();
		    
			sb.append(" UPDATE WARNING	\n");
			sb.append(" SET  		\n");
			sb.append(" 	  TS_RANK= 999 	\n");		
		 	sb.append(" WHERE MD_SEQ IN ("+md_seqs+") 			\n");
   	 		
		 	Log.debug(sb.toString());
		 	System.out.println(sb.toString());
		 	stmt.executeUpdate(sb.toString());

		 	result = true;
				 	
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
        return result;
	
	}	
	
	//연관어 분석 - 엑셀 다운로드
	public ArrayList<Object[]> getRelationData_excel(String ir_sdate, String ir_edate, String type, String seq, String keyword, String senti_type, int size) {
		
		ArrayList<Object[]>  result = new ArrayList<Object[]> ();
		StringBuffer sb = new StringBuffer();
		String explain = "현대카머셜";
		String senti_code = "34";
		String level_code = "36";
		String sg_seqs = "94,95,97,99,100";
		if(type.equals("12")) {
			explain = "현대카드";
			senti_code = "14";
			sg_seqs = "95,97";
		} else if(type.equals("22")) {
			explain = "현대캐피탈";
			senti_code = "24";
			sg_seqs = "94,95,97";
		}
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();
			sb.append(" ## 연관어 분석 팝업 엑셀 다운로드 - "+explain+"									\n");
			sb.append("SELECT A.* FROM  (  	  												\n");
			sb.append("		SELECT ID.ID_SEQ 												\n");
			sb.append("     	 , DATE_FORMAT(ID.MD_DATE,'%y/%m/%d %H:%i') AS MD_DATE    								\n");
			sb.append("     	 , ID.MD_SITE_NAME  											\n");
			sb.append("     	 , ID.ID_TITLE 											\n");
			sb.append("     	 , ID.ID_CONTENT 											\n");
			sb.append("     	 , ID.ID_URL  											\n");
			sb.append("     	 , ID.S_SEQ 											\n");
			sb.append("     	 , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") AS 'SENTI' #(IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머)											\n");
			sb.append("     	 ,(SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+senti_code+") AS SENTI_CODE #긍부정 (IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머)		 											\n");
			sb.append("FROM ISSUE_DATA ID								\n");							
			sb.append("	  , ISSUE_DATA_CODE IDC							\n");							
			sb.append("   , AUTO_RELATION_KEYWORD_MAP MAP							\n");		
			sb.append("WHERE ID.ID_SEQ = IDC.ID_SEQ							\n");							
			sb.append("   AND ID.ID_SEQ = MAP.ID_SEQ								\n");							
			sb.append("   AND ID.MD_DATE BETWEEN '"+ir_sdate+" 00:00:00'  AND '"+ir_edate+" 23:59:59'  								\n");							
			sb.append("   AND MAP.RK_SEQ = "+seq+"  					\n");	
			sb.append("   AND ID.SG_SEQ IN ("+sg_seqs+")						\n");	
			if(!keyword.equals("")) {
		       	String[] keyword_split = keyword.split("\\s+");
		       	sb.append(" AND (ID.ID_TITLE LIKE '%"+keyword_split[0]+"%'	\n");
		       		for(int i = 1; i < keyword_split.length; i++) {
		       			sb.append(" AND ID.ID_TITLE LIKE '%"+keyword_split[i]+"%'	\n");
		       		}
		       	sb.append("  )                      												\n");
		    }
			if(!senti_type.equals("")) {
			sb.append("   AND IDC.IC_TYPE = "+senti_code+" 		#(14:현카드 /24:현캐피/ 34:현커머)							\n");	
			sb.append("   AND IDC.IC_CODE = "+senti_type+"			\n");	
			} else {
			sb.append("   AND IDC.IC_TYPE = "+type+"						\n");	
			}
			sb.append("GROUP BY IDC.ID_SEQ							\n");	
			sb.append("UNION						\n");	
			sb.append("SELECT ID.ID_SEQ						\n");	
			sb.append("		, DATE_FORMAT(ID.MD_DATE,'%y/%m/%d %H:%i') AS MD_DATE						\n");	
			sb.append("		, ID.MD_SITE_NAME						\n");	
			sb.append("		, ID.ID_TITLE 						\n");	
			sb.append("		, ID.ID_CONTENT					\n");	
			sb.append("		, ID.ID_URL 						\n");	
			sb.append("		, ID.S_SEQ						\n");	
			sb.append("		, FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") AS 'SENTI' #(IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머)							\n");	
			sb.append("		,(SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+senti_code+") AS SENTI_CODE #긍부정 (IC_TYPE >> 14:현카드 /24:현캐피/ 34:현커머)						\n");	
			sb.append("FROM ISSUE_DATA ID							\n");	
			sb.append("	  , ISSUE_DATA_CODE IDC							\n");	
			sb.append("	  , RELATION_KEYWORD_MAP MAP						\n");	
			sb.append("WHERE ID.ID_SEQ = IDC.ID_SEQ							\n");	
			sb.append("	 AND ID.ID_SEQ = MAP.ID_SEQ							\n");	
			sb.append("	 AND ID.MD_DATE BETWEEN '"+ir_sdate+" 00:00:00'  AND '"+ir_edate+" 23:59:59'							\n");	
			sb.append("  AND MAP.RK_SEQ = "+seq+"							\n");	
			sb.append("  AND ID.SG_SEQ IN ("+sg_seqs+")						\n");	
			if(!keyword.equals("")) {
		       	String[] keyword_split = keyword.split("\\s+");
		       	sb.append(" AND (ID.ID_TITLE LIKE '%"+keyword_split[0]+"%'	\n");
		       		for(int i = 1; i < keyword_split.length; i++) {
		       			sb.append(" AND ID.ID_TITLE LIKE '%"+keyword_split[i]+"%'	\n");
		       		}
		       	sb.append("  )                      												\n");
		    }
			if(!senti_type.equals("")) {
			sb.append("   AND IDC.IC_TYPE = "+senti_code+" 		#(14:현카드 /24:현캐피/ 34:현커머)							\n");	
			sb.append("   AND IDC.IC_CODE = "+senti_type+"			\n");	
			} else {
			sb.append("   AND IDC.IC_TYPE = "+type+"						\n");	
			}					
			sb.append("GROUP BY IDC.ID_SEQ						\n");	
			sb.append(")A						\n");	
			sb.append("ORDER BY MD_DATE DESC						\n");	
			pstmt =null; rs =null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());
			
			Object[] item = new Object[size];
			int idx = 0;
			int currentCnt = 0;
			int gap = 0;
			while(rs.next()) {
				idx = 0;
				item = new Object[size];
				item[idx++] = rs.getString("SENTI");
				item[idx++] = rs.getString("ID_TITLE");
				//item[idx++] = rs.getString("ID_CONTENT");
				item[idx++] = rs.getString("ID_URL");
				item[idx++] = rs.getString("MD_SITE_NAME");
				item[idx++] = rs.getString("MD_DATE");
				result.add(item);	
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	
	//voc 팝업 - 엑셀 다운로드
	public ArrayList<Object[]> getDailyVocData_excel(String ir_sdate, String ir_edate, String type, String mode, String keyword, String senti_type, int size) {
		
		ArrayList<Object[]>  result = new ArrayList<Object[]> ();
		StringBuffer sb = new StringBuffer();
		String explain = "현대카머셜";
		String senti_code = "34";
		String level_code = "36";
		String sg_seqs = "94,95,97,99,100";
		if(type.equals("12")) {
			explain = "현대카드";
			senti_code = "14";
			level_code = "16";
			sg_seqs = "95,97";
		} else if(type.equals("22")) {
			explain = "현대캐피탈";
			senti_code = "24";
			level_code = "26";
			sg_seqs = "94,95,97";
		}
		
		int index = 0;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();			
			sb.append(" ## 일일보고서 팝업 리스트	- "+explain+"									\n");
			sb.append("SELECT A.* FROM (											\n");
			sb.append("SELECT ID.ID_SEQ												\n");
			sb.append("     , ID.ID_TITLE											\n");
			sb.append("     , ID.ID_URL												\n");
			sb.append("     , ID.S_SEQ											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+senti_code+" ),'') AS SENTI_CODE #긍부정 		\n");
			sb.append("     , DATE_FORMAT(MD_DATE,'%y/%m/%d %H:%i') AS MD_DATE											\n");
			sb.append("     , MD_SITE_NAME										\n");
			sb.append("     , ID.ID_CONTENT											\n");
			sb.append("     , IDC.IC_CODE											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ),'') AS LEVEL					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") AS SENTI_NAME	#긍부정					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+level_code+") AS LEVEL_NAME					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+type+") AS IC_NAME					\n");
			sb.append("  FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC											\n");
			sb.append("  	, ISSUE_DATA_CODE B											\n");
			sb.append(" WHERE ID.ID_SEQ = IDC.ID_SEQ								\n");							
			sb.append(" AND ID.ID_SEQ = B.ID_SEQ										\n");							
			sb.append(" AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간								\n");							
			//sb.append(" AND MD_DATE BETWEEN '2021-02-01 00:00:00' AND '2021-02-16 23:59:59'	#상단 검색 조건 - 기간							\n");		
			sb.append(" AND SG_SEQ IN ("+sg_seqs+") #상단 검색 조건 - 채널 							\n");
			if(!keyword.equals("")) {
		       	String[] keyword_split = keyword.split("\\s+");
		       	sb.append(" AND (ID.ID_TITLE LIKE '%"+keyword_split[0]+"%'	\n");
		       		for(int i = 1; i < keyword_split.length; i++) {
		       			sb.append(" AND ID.ID_TITLE LIKE '%"+keyword_split[i]+"%'	\n");
		       		}
		       	sb.append("  )                      												\n");
		    }  
			sb.append(" AND IDC.IC_TYPE = "+type+" #(12:현카드 /22:현캐피/ 32:현커머)								\n");							
			sb.append(" AND B.IC_TYPE = "+senti_code+"	 #(14:현카드 /24:현캐피/ 34:현커머)							\n");
			if(!senti_type.equals("")) {
				sb.append("   AND B.IC_CODE = "+senti_type+"			\n");	
			}
			sb.append(" AND IDC.IC_CODE != 7 #주제-발급한도(12-7:현카드 /22-7:현캐피/ 32-7:현커머)						\n");
			sb.append(" GROUP BY ID.ID_SEQ								\n");							
			//sb.append(" ORDER BY ID.MD_DATE DESC 								\n");							
			//sb.append(" ORDER BY IMPORTANT ASC										\n");
			//sb.append(" ORDER BY B.IC_CODE ASC, ID.MD_DATE DESC , IDC.IC_CODE 								\n");
			
			if(type.equals("12")) {
				sb.append(" ORDER BY FIELD(IDC.IC_CODE,8) DESC, ID.ID_REPLYCOUNT DESC, B.IC_CODE ASC, ID.MD_DATE DESC , IDC.IC_CODE 								\n");
			}else {
				sb.append(" ORDER BY ID.ID_REPLYCOUNT DESC, B.IC_CODE ASC, ID.MD_DATE DESC , IDC.IC_CODE 								\n");
			}
			
			/*추가*/sb.append(" )	AS A UNION SELECT B.* FROM(			\n");
			//주제-발급한도만 가장뒤로 - 2021.03.23 고객사 요청
			sb.append("SELECT ID.ID_SEQ												\n");
			sb.append("     , ID.ID_TITLE											\n");
			sb.append("     , ID.ID_URL												\n");
			sb.append("     , ID.S_SEQ											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+senti_code+" ),'') AS SENTI_CODE #긍부정 		\n");
			sb.append("     , DATE_FORMAT(MD_DATE,'%y/%m/%d %H:%i') AS MD_DATE													\n");
			sb.append("     , MD_SITE_NAME										\n");
			sb.append("     , ID.ID_CONTENT											\n");
			sb.append("     , IDC.IC_CODE											\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = ID.ID_SEQ AND IC_TYPE= "+level_code+" ),'') AS LEVEL					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+senti_code+") AS SENTI_NAME	#긍부정					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+level_code+") AS LEVEL_NAME					\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(ID.ID_SEQ, "+type+") AS IC_NAME					\n");
			sb.append("  FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC											\n");
			sb.append("  	, ISSUE_DATA_CODE B											\n");
			sb.append(" WHERE ID.ID_SEQ = IDC.ID_SEQ								\n");							
			sb.append(" AND ID.ID_SEQ = B.ID_SEQ										\n");							
			sb.append(" AND MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간								\n");							
			//sb.append(" AND MD_DATE BETWEEN '2021-02-01 00:00:00' AND '2021-02-16 23:59:59'	#상단 검색 조건 - 기간							\n");		
			sb.append(" AND SG_SEQ IN ("+sg_seqs+") #상단 검색 조건 - 채널 							\n");		
			if(!keyword.equals("")) {
		       	String[] keyword_split = keyword.split("\\s+");
		       	sb.append(" AND (ID.ID_TITLE LIKE '%"+keyword_split[0]+"%'	\n");
		       		for(int i = 1; i < keyword_split.length; i++) {
		       			sb.append(" AND ID.ID_TITLE LIKE '%"+keyword_split[i]+"%'	\n");
		       		}
		       	sb.append("  )                      												\n");
		    } 
			sb.append(" AND IDC.IC_TYPE = "+type+" #(12:현카드 /22:현캐피/ 32:현커머)								\n");							
			sb.append(" AND B.IC_TYPE = "+senti_code+"	 #(14:현카드 /24:현캐피/ 34:현커머)							\n");
			if(!senti_type.equals("")) {
				sb.append("   AND B.IC_CODE = "+senti_type+"			\n");	
			}
			sb.append(" AND IDC.IC_CODE = 7 #주제-발급한도(12-7:현카드 /22-7:현캐피/ 32-7:현커머)						\n");
			sb.append(" GROUP BY ID.ID_SEQ								\n");							
			//sb.append(" ORDER BY ID.MD_DATE DESC 								\n");							
			//sb.append(" ORDER BY IMPORTANT ASC										\n");
			//sb.append(" ORDER BY B.IC_CODE ASC, ID.MD_DATE DESC , IDC.IC_CODE 								\n");
			
			if(type.equals("12")) {
				sb.append(" ORDER BY FIELD(IDC.IC_CODE,8) DESC, ID.ID_REPLYCOUNT DESC, B.IC_CODE ASC, ID.MD_DATE DESC , IDC.IC_CODE 								\n");
			}else {
				sb.append(" ORDER BY ID.ID_REPLYCOUNT DESC, B.IC_CODE ASC, ID.MD_DATE DESC , IDC.IC_CODE 								\n");
			}
			sb.append(" )AS B							\n");
			
			System.out.println(sb.toString());
			pstmt =null; rs =null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			Object[] item = new Object[size];
			int idx = 0;
			int currentCnt = 0;
			int gap = 0;
			while(rs.next()){
				idx = 0;
				item = new Object[size];
				item[idx++] = rs.getString("SENTI_NAME");
				item[idx++] = rs.getString("ID_TITLE");
				//item[idx++] = rs.getString("ID_CONTENT");
				item[idx++] = rs.getString("ID_URL");
				item[idx++] = rs.getString("IC_NAME");
				item[idx++] = rs.getString("LEVEL_NAME");
				item[idx++] = rs.getString("MD_SITE_NAME");
				item[idx++] = rs.getString("MD_DATE");

				result.add(item);
			}
		
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	//소비자뉴스 팝업 - 엑셀 다운로드
	public ArrayList<Object[]> getConsumerNews_excel(String ir_sdate, String ir_edate, String keyword, int size) {
		
		ArrayList<Object[]>  result = new ArrayList<Object[]> ();
		StringBuffer sb = new StringBuffer();
		
		int index = 0;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();			
			sb.append("SELECT A.* 												\n");
			sb.append("FROM (												\n");
			sb.append("		SELECT md_seq												\n");
			sb.append("			, md_pseq												\n");
			sb.append("     	, md_title											\n");
			sb.append("     	, md_content											\n");
			sb.append("    		, md_site_name											\n");
			sb.append("    		, md_url 		\n");
			sb.append("     	, DATE_FORMAT(MD_DATE,'%y/%m/%d') AS MD_DATE		\n");
			sb.append("			FROM WARNING W													\n");
			sb.append("			 ,(                                                             \n");
			sb.append("			      SELECT S_SEQ                                              \n");
			sb.append("			        FROM SITE                                               \n");
			sb.append("			      WHERE S_TAG NOT LIKE '%지역%'                               \n");
			sb.append("  				AND S_SEQ NOT IN (456, 5025376, 784, 5118, 9343, 32, 11125, 5031886,5018223, 901, 131)											\n"); //특정 일간지 제외 - 고객사 요청				
			sb.append("			       UNION ALL                                               \n");
			sb.append("			     SELECT S_SEQ                                               \n");
			sb.append("			       FROM SITE                                             	\n");
			sb.append("			      WHERE S_SEQ IN (34,755,783,835,843,845,846,847,848,849,850,851,852,853,855,856,857,860,861,863,864,867,868,910,1029,1031,1066,1067,1068,1069,1070,1071,1072,1073,1075,1076,1081,1082,1828,1832,4085,4164,4176,4268,4974,4975,5115,7991,8318,5023503,5023504)                                       \n"); //일간지 중 미제외 요청 리스트 - 2021.05.18 강보명 과장님 확인
			sb.append("			   )AS S                                                           \n"); //고객사 요청 및 내부 협의 - 지역신문, 일간지 노출 제외
			sb.append("  		WHERE  MD_DATE BETWEEN '"+ir_sdate+" 00:00:00' AND '"+ir_edate+" 23:59:59'  #상단 검색 조건 - 기간 											\n");
			sb.append("  		AND S.S_SEQ = W.S_SEQ											\n");
			sb.append("  		AND SB_SEQ NOT IN 											\n");
			sb.append("     	(71271,71277,71278,71279,5096456,5096457,5096458,5096459,5096460,5096461,5096462,5096463,5096464,5096465,5096466,5096467,5096468,5096469,5096470,5096471,5096472,5096473		\n");
			sb.append("     	 ,5096474,5096475,5096476,5096477,5096478,5096479,5096480,5096481,5096482,5096483,5151794,5188565,5188566,71921,74460,74463,5101791)											\n");
			//검섹 조건은 AND
			if(!keyword.equals("")) {
		       	String[] keyword_split = keyword.split("\\s+");
		       	sb.append(" 	AND (MD_TITLE LIKE '%"+keyword_split[0]+"%'	\n");
		       		for(int i = 1; i < keyword_split.length; i++) {
		       			sb.append(" AND MD_TITLE LIKE '%"+keyword_split[i]+"%'	\n");
		       		}
		       	sb.append("  		)                      												\n");
		    }
			sb.append("  		GROUP BY MD_PSEQ 								\n");							
			sb.append("  )A 								\n");
			sb.append(" GROUP BY SUBSTRING( REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(md_title,'…',''),'·',''),' ',''),'.',''),' ',''),1,20) 								\n");
			sb.append("  ORDER BY MD_DATE DESC 								\n");
			
			System.out.println(sb.toString());
			pstmt =null; rs =null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			Object[] item = new Object[size];
			int idx = 0;
			int currentCnt = 0;
			int gap = 0;
			while(rs.next()){
				idx = 0;
				item = new Object[size];
				item[idx++] = rs.getString("MD_TITLE");
				//item[idx++] = rs.getString("MD_CONTENT");
				item[idx++] = rs.getString("MD_URL");
				item[idx++] = rs.getString("MD_SITE_NAME");
				item[idx++] = rs.getString("MD_DATE");

				result.add(item);
			}
		
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
		
	public String cutKey( String psBody, String psKeyword)
  	{
  		return cutKey(psBody, psKeyword, 100, "");
  	}
	
	public String cutKey( String psBody, String psKeyword, int piLen, String tColor )
    {
    	String result = null;
    	
		StringUtil su = new StringUtil();
		
		int idxPoint = -1; 

		try{
			if(psBody==null || psBody.length()==0){
				return "";
			}
				
			psBody = su.ChangeString( psBody.toLowerCase() );
			if (!su.nvl(psKeyword,"").equals("")) {
				
				String[] arrBFKey = su.getUniqeArray( psKeyword.split(" ") );			// 강조키워드 배열
				arrBFKey = searchGumunKey( arrBFKey );
				String[] arrAFKey = new String[arrBFKey.length];	// 강조키워드 html 배열
			
				for( int i=0 ; i<arrBFKey.length ; i++ )
				{
			    	//System.out.println("Keyword : "+arrBFKey[i]);
					arrBFKey[i] = arrBFKey[i].toLowerCase();
					arrAFKey[i] = "<font color='"+tColor+"'><b>"+arrBFKey[i]+"</b></font>";
					 
					
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
					endCut = psBody.length();
				}
				if(startCut<0){
					startCut = 0;
				}
				if(endCut>psBody.length()-1){
					endCut = psBody.length();
				}
				result = startDot+psBody.substring(startCut, endCut)+endDot;
				
				/*
				if( idxPoint >= 0 ) {
					if (arrBFKey.length>0) {
						for( int i=0 ; i<arrBFKey.length ; i++  )
						{
							result = result.replaceAll( "(?is)"+arrBFKey[i], arrAFKey[i]);
						}
					}
				}
				*/
				
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
    		System.out.println(psBody+"/psKeyword:"+psKeyword+"/piLen:"+piLen+"/tColor:"+tColor);
		}
		
		return result;
    }
	
	/**
     * 구문 키워드를 찾아 키워드를 재배열한다.
     * @param key
     * @return
     */
    public String[] searchGumunKey(String[] key)
    {
    	int indexCnt = 0;
		int firstWordIndex = 0;
		int endWordIndex = 0;
		int gumunCount = 0;
		
		String notGumnunKey = "";
		String gumnunKeyword = "";
		String[] tempKey = null;
		String[] lastKey = null;
	
		
		//구문 인덱스 범위를 찾는다.
		for(int i =0 ; i<key.length ; i++){
			notGumnunKey += notGumnunKey.equals("") ? key[i] : " "+ key[i];
			
			if(key[i].indexOf("\"")>-1)
			{
					if(indexCnt==0) firstWordIndex = i;					
					if(indexCnt>0) endWordIndex = i;	
					indexCnt++;
			}			
		}
		
		if(endWordIndex>0)
		{
			gumunCount = endWordIndex -	firstWordIndex;	
			
			for(int i = firstWordIndex ; i<gumunCount+1 ; i++){
				
				gumnunKeyword += gumnunKeyword.equals("") ? key[i] : " "+ key[i];	
			}
			
			//구문 제외한 키워드
			notGumnunKey = notGumnunKey.replaceAll(gumnunKeyword,"");
			notGumnunKey = notGumnunKey.replaceAll("  ", " ");
			
						
			//구문 제외한 키워드 배열
			if(notGumnunKey.equals(" "))tempKey = notGumnunKey.split(" ");			
			if(tempKey!=null)
			{
				lastKey = new String[tempKey.length+1];
				for(int i =0 ; i<tempKey.length ; i++)
				{					
					lastKey[i] = tempKey[i];
				}
				lastKey[tempKey.length] = gumnunKeyword;
		
			}else{
				lastKey = new String[1];
				lastKey[0] = gumnunKeyword;
			}
		}else{
			lastKey = key;
		}
		for(int i =0 ; i<lastKey.length ; i++){
			lastKey[i] = lastKey[i].replaceAll("\"","");
		}
		
		
    	return lastKey;
    }
}
