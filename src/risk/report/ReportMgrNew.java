package risk.report;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

import risk.DBconn.*;
import risk.util.*;

public class ReportMgrNew {

	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private PreparedStatement pstmt = null;
	private int total_count = 0;
	public int getTotalCount() {
		return this.total_count;
	}
	
	 /*
	  * SK그룹 차트2 날짜별 정보량
	  * TM, SK그룹, 관계사 들만
	  * 
	 */
	
	 public ArrayList getChartCountForDate(String ir_sdate
			 								, String ir_stime
			 								, String ir_edate
			 								, String ir_etime
			 								, int diff_sch
			 								, String a_month_ago
			 								, int diff_avg
			 								, boolean sixNonCountChk
			 								, String sixMonthCount
			 								) {
			
			ArrayList arrResult = new ArrayList();
			Map dataMap = new HashMap();
			StringBuffer sb = new StringBuffer();
			
			try {
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				
				sb = new StringBuffer();			
				sb.append(" ## 날짜별 정보량 구하기 																												\n");
				sb.append("  SELECT A.MD_DATE, IFNULL(B.CNT, 0) AS CNT																					\n");
				
				if(sixNonCountChk)
					sb.append("  , C.AVG																													\n");
				
				sb.append("    FROM (                                                                                                                   \n");
				sb.append("         SELECT 1 AS PK, DATE_FORMAT(DATE_ADD('"+ir_edate+" "+ir_etime+"', interval @ROW:=@ROW-1 HOUR),'%Y-%m-%d %H') AS MD_DATE 			\n");	
				sb.append("  		      FROM KEYWORD A, (SELECT @ROW:= 1)R ORDER BY MD_DATE DESC LIMIT "+diff_sch+" 		                            \n");
				sb.append("       ) A LEFT OUTER JOIN (                                                                                                 \n");
				sb.append("         SELECT DATE_FORMAT(ID.MD_DATE, '%Y-%m-%d %H') AS MD_DATE, COUNT(0) AS CNT                                           \n");
				sb.append("         FROM ISSUE_DATA ID                                                                                                  \n");
				sb.append("             ,ISSUE_DATA_CODE_DETAIL IDCD                                                                                    \n");
				sb.append("         WHERE ID.ID_SEQ = IDCD.ID_SEQ                                                                                        \n");
				sb.append("           AND ID.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'					            \n");
				sb.append("           AND IDCD.IC_TYPE = 9			                                                                                   \n");
				sb.append("           AND IDCD.IC_PTYPE IN (2, 3, 4, 5)                                                                                  \n");
				sb.append("         GROUP BY DATE_FORMAT(ID.MD_DATE, '%Y-%m-%d %H')                                                                     \n");
				sb.append("       ) B ON A.MD_DATE = B.MD_DATE                                                                             		 		\n");
				
				if(sixNonCountChk){
					sb.append("       INNER JOIN (                                                              											\n");
					sb.append("         SELECT 1 AS PK, ROUND(COUNT(0) / "+diff_avg+") AS AVG                                                               \n");
					sb.append("           FROM ISSUE_DATA ID                                                                                                \n");
					sb.append("               ,ISSUE_DATA_CODE_DETAIL IDCD                                                                                  		\n");
					sb.append("          WHERE ID.ID_SEQ = IDCD.ID_SEQ                                                                                       \n");
					sb.append("            AND ID.MD_DATE BETWEEN '"+a_month_ago+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'					        \n");
					sb.append("            AND IDCD.IC_TYPE = 9				                                                                                 \n");
					sb.append("            AND IDCD.IC_PTYPE IN (2, 3, 4, 5)                                                                                 \n");
					sb.append("       ) C ON A.PK = C.PK                                                                                                    \n");
				}
				sb.append("    ORDER BY MD_DATE                                                                                                         \n");
				
				System.out.println(sb.toString());
				pstmt =null; rs =null;
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					dataMap = new HashMap();
					dataMap.put("CATEGORY", rs.getString("MD_DATE"));
					dataMap.put("CNT", rs.getString("CNT"));
					if(sixNonCountChk) dataMap.put("AVG", rs.getString("AVG"));
					else dataMap.put("AVG", sixMonthCount);
					arrResult.add(dataMap);
				}
				arrResult = (ArrayList) reFactoringData(arrResult, ir_edate);
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
	 * SK 일일 보고서 출처별 정보량 (TM+관계사)
	 */
	public List getChartCountForSite(String ir_sdate, String ir_stime, String ir_edate, String ir_etime) {
			
			//ArrayList arrResult = new ArrayList();
			List rList = new ArrayList();
			StringBuffer sb = new StringBuffer();
			
			try {
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				
				sb.append(" # SK 일일 보고서 출처별 정보량 																				\n");
				sb.append("     SELECT 																							\n");
				sb.append("           SG.SG_SEQ						                                                            \n");
				sb.append("           , SG.SG_NAME					                                                            \n");
				sb.append("           , IFNULL(ID.CNT, 0) AS SG_CNT			                                                    \n");
				sb.append("      FROM (                                                                                         \n");
				sb.append("       SELECT SG_SEQ								                                                    \n");
				sb.append("             , IF(SG_SEQ = 20, '기타', SG_NAME) AS SG_NAME                                        		\n");
				sb.append("         FROM SITE_GROUP		                                                                        \n");
				sb.append("         WHERE SG_SEQ IN (42, 17, 43, 18, 25, 19, 20)					  							\n");
				sb.append("      ) SG LEFT OUTER JOIN (															  	            \n");
				sb.append("        SELECT SG_SEQ, COUNT(SG_SEQ) AS CNT											  	            \n");
				sb.append("      		FROM ISSUE_DATA ID															  	        \n");
				sb.append("      	INNER JOIN ISSUE_DATA_CODE_DETAIL IDCD ON ID.ID_SEQ = IDCD.ID_SEQ		  	            	\n");
				sb.append("      		AND IDCD.IC_TYPE = 9 AND IDCD.IC_PTYPE IN (2,3,4,5)						  	            \n");
				sb.append("        	WHERE ID.MD_DATE BETWEEN '"+ ir_sdate +" "+ ir_stime +"' AND '"+ir_edate+" "+ir_etime+"'	\n");
				sb.append("      	GROUP BY SG_SEQ														  	            		\n");
				sb.append("      ) ID ON SG.SG_SEQ = ID.SG_SEQ					                           		                \n");
				sb.append("      ORDER BY CNT DESC				                           		           					    \n");
				//sb.append("      ORDER BY CNT ASC				                           		           					    \n");
				pstmt = dbconn.createPStatement(sb.toString());
				System.out.println(pstmt);
				rs = pstmt.executeQuery();
				
				total_count = 0;
				int i=1;
				while( rs.next() ) {
					Map dataMap = new HashMap();
					dataMap.put("SG_SEQ", rs.getString("SG_SEQ"));
					dataMap.put("SG_NAME", rs.getString("SG_NAME"));
					dataMap.put("SG_CNT", rs.getString("SG_CNT"));
					total_count += rs.getInt("SG_CNT");
					rList.add(dataMap);
					// 언론만 별도로 수량과 상관 없이 맨 앞으로 보낸다.
					if("42".equals(rs.getString("SG_SEQ"))){
						rList.add(0, dataMap);
					} else {
						rList.add(i++, dataMap);
					}
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
			return rList;
	} 
	
	/*
	 * SK그룹 보고서 첫번째 테이블 포탈 데이터 리스트
	 * 
	 */
	public List getRelationKeywordForReport(String ir_sdate, String ir_stime, String ir_edate, String ir_etime) {
				
		List rList = new ArrayList();
		StringBuffer sb = null;
		pstmt = null; 
		rs = null;
			
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();			
			sb.append(" ## 보고서 연관키워드										\n");
			sb.append("  SELECT												\n");
			sb.append("  	RK.RK_SEQ,										\n");
			sb.append("  	RK.RK_NAME,										\n");
			sb.append("  	COUNT(RK.RK_NAME) AS CNT						\n");
			sb.append("  FROM ISSUE_DATA ID									\n");
			sb.append("  	, RELATION_KEYWORD_MAP RKM						\n");
			sb.append("  	, RELATION_KEYWORD_R RK   						\n");
			sb.append("  WHERE ID.MD_DATE BETWEEN ? AND ?					\n");
			sb.append("  	AND ID.ID_SEQ = RKM.ID_SEQ 						\n");
			sb.append("  	AND RKM.RK_SEQ = RK.RK_SEQ						\n");
			sb.append("  	AND RK.RK_USE = 'Y'								\n");
			sb.append("  GROUP BY RK.RK_NAME 								\n");
			sb.append("  ORDER BY COUNT(RK.RK_NAME) DESC					\n");
			sb.append("  LIMIT 5 					 						\n");
			pstmt = dbconn.createPStatement(sb.toString());
			pstmt.setString(1, ir_sdate+" "+ir_stime);
			pstmt.setString(2, ir_edate+" "+ir_etime);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Map dataMap = new HashMap();
				dataMap.put("RK_SEQ", rs.getString("RK_SEQ"));
				dataMap.put("RK_NAME", rs.getString("RK_NAME"));
				dataMap.put("CNT", rs.getString("CNT"));
				rList.add(dataMap);
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
		return rList;
	}    
	
	/*
	 * SK그룹 보고서 첫번째 테이블 포탈 데이터 리스트
	 * 
	 */
	public List getPortalReport(String reply_id_sqs, List reply_list) {
				
		List arrResult = new ArrayList();
		StringBuffer sb = new StringBuffer();
		pstmt =null; 
		rs =null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();			
			sb.append(" ## 포탈기사 데이터																					\n");
			sb.append("  SELECT																							\n");
			sb.append("  	A.ID_SEQ																					\n");
			sb.append("  	, S_SEQ																						\n");
			sb.append("  	, MD_SITE_NAME																				\n");
			sb.append("  	, ID_TITLE																					\n");
			sb.append("  	, ID_URL																					\n");
			sb.append("  	, DATE_FORMAT(MD_DATE, '%Y-%m-%d') AS MD_DATE												\n");
			sb.append("  	, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = B.IC_PTYPE AND IC_CODE = 0) AS GUBUN		\n");
			sb.append("  FROM ISSUE_DATA A																				\n");
			sb.append("  INNER JOIN ISSUE_DATA_CODE_DETAIL B ON A.ID_SEQ = B.ID_SEQ AND B.IC_TYPE = 9					\n");
			sb.append("  WHERE A.ID_SEQ IN ("+reply_id_sqs+")															\n");
			sb.append("  AND A.S_SEQ IN (2196, 2199, 3883)																\n");
			sb.append("  LIMIT 10																						\n");
			System.out.println(sb.toString());

			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Map dataMap = new HashMap();
				dataMap.put("ID_SEQ", rs.getString("ID_SEQ"));
				String site_name = "";
				if(rs.getString("S_SEQ").equals("2196")){ //네이버
					site_name = "네이버_"+rs.getString("MD_SITE_NAME").substring(1);
				}else if(rs.getString("S_SEQ").equals("2199")){ //다음
					site_name = "다음_"+rs.getString("MD_SITE_NAME").substring(1);
				}else if(rs.getString("S_SEQ").equals("3883")){ //네이트
					site_name = "네이트_"+rs.getString("MD_SITE_NAME").substring(1);
				}else{
					site_name = rs.getString("MD_SITE_NAME");
				}
				dataMap.put("MD_SITE_NAME", site_name);
				dataMap.put("ID_TITLE", rs.getString("ID_TITLE"));
				dataMap.put("ID_URL", rs.getString("ID_URL"));
				dataMap.put("MD_DATE", rs.getString("MD_DATE"));
				dataMap.put("GUBUN", rs.getString("GUBUN"));
				for(int i=0; i < reply_list.size(); i++){
					Map elem = (Map) reply_list.get(i);
					if(rs.getString("ID_SEQ").equals(elem.get("ID_SEQ").toString())) {
						dataMap.put("REPLY_COUNT", elem.get("COUNT").toString());
						break;
					} else 
						dataMap.put("REPLY_COUNT", "0");
				}
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
	 * SK그룹 보고서 첫번째 테이블 포탈 데이터 리스트
	 * 
	 */
	public List getPrivateMediaReport(String reply_id_sqs, List reply_list) {
				
			List arrResult = new ArrayList();
			StringBuffer sb = new StringBuffer();
			pstmt =null; 
			rs =null;
			
			try {
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				
				sb = new StringBuffer();			
				sb.append(" ## 개인미디어 데이터																						\n");
				sb.append("  SELECT																								\n");
				sb.append("  	A.ID_SEQ																						\n");
				sb.append("  	, S_SEQ																							\n");
				sb.append("  	, MD_SITE_NAME																					\n");
				sb.append("  	, ID_TITLE																						\n");
				sb.append("  	, ID_URL																						\n");
				sb.append("  	, DATE_FORMAT(MD_DATE, '%Y-%m-%d') AS MD_DATE													\n");
				sb.append("  	, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = B.IC_PTYPE AND IC_CODE = 0) AS GUBUN			\n");
				sb.append("  FROM ISSUE_DATA A																					\n");
				sb.append("  INNER JOIN ISSUE_DATA_CODE_DETAIL B ON A.ID_SEQ = B.ID_SEQ AND B.IC_TYPE = 9						\n");
				sb.append("  INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ = C.ID_SEQ AND C.IC_TYPE = 6	AND C.IC_CODE IN (2,3,4,5,6)  \n");
				sb.append("  WHERE A.ID_SEQ IN ("+reply_id_sqs+")																\n");
				sb.append("  LIMIT 10																							\n");
				System.out.println(sb.toString());

				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					Map dataMap = new HashMap();
					dataMap.put("ID_SEQ", rs.getString("ID_SEQ"));
					String site_name = "";
					if(rs.getString("S_SEQ").equals("2196")){ //네이버
						site_name = "네이버_"+rs.getString("MD_SITE_NAME").substring(1);
					}else if(rs.getString("S_SEQ").equals("2199")){ //다음
						site_name = "다음_"+rs.getString("MD_SITE_NAME").substring(1);
					}else if(rs.getString("S_SEQ").equals("3883")){ //네이트
						site_name = "네이트_"+rs.getString("MD_SITE_NAME").substring(1);
					}else{
						site_name = rs.getString("MD_SITE_NAME");
					}
					dataMap.put("MD_SITE_NAME", site_name);
					dataMap.put("ID_TITLE", rs.getString("ID_TITLE"));
					dataMap.put("ID_URL", rs.getString("ID_URL"));
					dataMap.put("MD_DATE", rs.getString("MD_DATE"));
					dataMap.put("GUBUN", rs.getString("GUBUN"));
					for(int i=0; i < reply_list.size(); i++){
						Map elem = (Map) reply_list.get(i);
						if(rs.getString("ID_SEQ").equals(elem.get("ID_SEQ").toString())) {
							dataMap.put("REPLY_COUNT", elem.get("COUNT").toString());
							break;
						} else 
							dataMap.put("REPLY_COUNT", "0");
					}
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
	
	/**
	 * SK그룹은 날짜 계산이 살짝 다릅니다.
	 * ex) 2017.01.23 16시 ~ 2017.01.24 15시 데이터는 2017.01.24에 해당하는 데이터입니다.
	 * 이거에 맞춰서 새로 데이터를 매핑해서 입력할 것입니다.
	 * 수정하실 방도가 있으시면 말씀주세요.
	 * 
	 * @return
	 */
	public List reFactoringData(List inputList, String ir_edate){
		
		List rList = new ArrayList();
		DateUtil du = new DateUtil();
		
		if(inputList.size() > 0 ){
			String tmp_date = "";
			String tmp_time = "";
			int cnt = 0;
			Map rMap = new HashMap();
			for(int i=0; i < inputList.size(); i++){
				Map dataMap = (Map)inputList.get(i);
				if("".equals(tmp_time) || "16".equals( dataMap.get("CATEGORY").toString().substring(11, 13))
					)
				{
						tmp_date = du.addDay_v2(dataMap.get("CATEGORY").toString().substring(0, 10), 1);
						tmp_time = dataMap.get("CATEGORY").toString().substring(11, 13);
						if(i!=0) rList.add(rMap);
						rMap = new HashMap();
						rMap.put("CATEGORY", tmp_date);
						rMap.put("AVG", dataMap.get("AVG").toString());
						cnt = Integer.parseInt(dataMap.get("CNT").toString());
				}else{
					cnt += Integer.parseInt(dataMap.get("CNT").toString());
					rMap.put("CNT", String.valueOf(cnt));
					if( i == inputList.size()-1 ) {
						rList.add(rMap);
					}
				}
			}
				
		}
		
/*		for(int i=0; i < rList.size(); i++){
			Map rMap = (Map) rList.get(i);
			System.out.print("Category:"+rMap.get("CATEGORY"));
			System.out.print(" / CNT:"+rMap.get("CNT"));
			System.out.println(" / AVG:"+rMap.get("AVG"));
		}*/
		
		return rList;
	}
	
	public List sortList(List unsortList, String key) {
		MapComparator comp = new MapComparator(key);
		Collections.sort(unsortList, comp);
		return unsortList;
	}
}
