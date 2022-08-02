package risk.dashboard.view.popup;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class PopupMgr {
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = null;
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	
	
	public String[] getPrevDate(String sdate, String edate){			
		String[] result = new String[2];
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();				
			sb.append("SELECT DATE_ADD(DATE_ADD('"+sdate+" 00:00:00', INTERVAL -1 DAY), INTERVAL -(TIMESTAMPDIFF( DAY, '"+sdate+"  00:00:00', '"+edate+"  00:00:00') + 1) DAY) AS PEV_SDATE    \n");
			sb.append("       , DATE_ADD('"+sdate+"  00:00:00', INTERVAL -1 DAY) AS PEV_EDATE                                                                                                   \n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				result[0] = rs.getString("PEV_SDATE");
				result[1] = rs.getString("PEV_EDATE");
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
	
	
	public JSONObject getPopDataList(String sdate, String edate, String ic_code, String senti, String sg_seq, int nowPage, int rowCnt, String searchkey ){
		JSONObject result = new JSONObject();
		JSONArray arr = new JSONArray();
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			
			sb = new StringBuffer();                                                                                                                                           
			sb.append("SELECT COUNT(*) AS CNT                        										 \n");
			sb.append("  FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC                                             \n");
			sb.append(" WHERE ID.ID_SEQ = IDC.ID_SEQ                                             \n");
			sb.append("   AND MD_DATE BETWEEN '"+sdate+" 00:00:00' AND '"+edate+" 23:59:59'                                           \n");
			sb.append("   AND IDC.IC_TYPE = 7                                            \n");
			sb.append("   AND IDC.IC_CODE = "+ic_code+" #선택된 보도자료명 IC_CODE                                               \n");
			if(!"".equals(sg_seq)){
			sb.append("   AND ID.SG_SEQ IN ("+sg_seq+") #채널선택시                                         \n");
			}
			if(!"".equals(senti)){
			sb.append("   AND ID.ID_SENTI IN ( "+senti+")  #성향선택시                                      \n");
			}
			if(!"".equals(searchkey)){
				sb.append("  AND ID_TITLE LIKE '%"+searchkey+"%' 										\n");			
			}
			sb.append("GROUP BY IDC.IC_CODE                                      \n");
			sb.append("ORDER BY MD_DATE                                       \n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			int cnt = 0;
			if(rs.next()){
				cnt = rs.getInt("CNT");
				result.put("count",cnt);
			}
			
			result.put("nowPage", nowPage);
			result.put("rowCnt", rowCnt);
			
			
			sb = new StringBuffer();
			sb.append("SELECT DATE_FORMAT(MD_DATE,'%Y-%m-%d') AS MD_DATE #일자                                                                                                                                    \n");
			sb.append("		, (SELECT SG_NAME FROM SITE_GROUP SG WHERE USEYN = 'Y' AND SG.SG_SEQ =ID.SG_SEQ) AS CHANNEL #채널    																																	 \n");
			sb.append("		, ID.ID_TITLE #제목 																																 \n");
			sb.append("		, ID.ID_URL  																																 \n");
			sb.append("		, ID.MD_SITE_NAME  																																	 \n");
			sb.append("		, IDC.IC_TYPE 	 																																 \n");
			sb.append("		, IDC.IC_CODE  																														 \n");
			sb.append("		, MD_SITE_NAME #최초출처  	 																																 \n");
			sb.append("		, ID.S_SEQ    																																 \n");
			sb.append("		, ID.MD_DATE      																																 \n");
			sb.append("		, IFNULL(ID.MD_WRITER,'') AS MD_WRITER																																 \n");
			sb.append("		, CASE ID.ID_SENTI WHEN 1 THEN '긍정'       																																 \n");
			sb.append("						   WHEN 2 THEN '부정'				    																																 \n");
			sb.append("						   WHEN 3 THEN '중립'					    																																 \n");
			sb.append("		  ELSE '' END ID_SENTI #성향   																																 \n");
			sb.append("  FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC                                                                                                                                              \n");
			sb.append(" WHERE ID.ID_SEQ = IDC.ID_SEQ                                                                                                                                                       \n");
			sb.append("   AND MD_DATE BETWEEN '"+sdate+" 00:00:00' AND '"+edate+" 23:59:59'                                                                                                                                              \n");
			sb.append("   AND IDC.IC_TYPE = 7                                                                                                                                                  \n");
			sb.append("   AND IDC.IC_CODE = "+ic_code+" #선택된 보도자료명 IC_CODE                                                                                                                                                 \n");
			if(!"".equals(sg_seq)){
			sb.append("   AND ID.SG_SEQ IN ("+sg_seq+") #채널선택시                                                                                                                                                 \n");
			}
			if(!"".equals(senti)){
			sb.append("   AND ID.ID_SENTI IN ( "+senti+")  #성향선택시                                                                                                                                                  \n");
			}
			if(!"".equals(searchkey)){
			sb.append("   AND ID_TITLE LIKE '%"+searchkey+"%' 										\n");			
			} 
			sb.append(" GROUP BY ID.ID_SEQ, IDC.IC_CODE                                                                                                                                                 \n");
			sb.append(" ORDER BY MD_DATE                                                                                                                                               \n");
			if(rowCnt > 0){
			sb.append("     LIMIT "+((nowPage-1)*rowCnt)+","+rowCnt+"                                                                                                             \n");
			}
			
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());
			
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("MD_DATE", rs.getString("MD_DATE"));
				obj.put("CHANNEL", rs.getString("CHANNEL"));
				obj.put("ID_TITLE", rs.getString("ID_TITLE"));
				obj.put("ID_URL", rs.getString("ID_URL"));
				obj.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
				obj.put("MD_WRITER", su.toHtmlString(rs.getString("MD_WRITER")));
				obj.put("ID_SENTI", rs.getString("ID_SENTI"));
				obj.put("S_SEQ", rs.getString("S_SEQ"));
				
				arr.put(obj);
				
			}
			
			result.put("list", arr);
			
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
	
	
	public ArrayList<Object[]> getPopDataList_excel(String sDate, String eDate, String ic_code, String senti, String sg_seq, String searchkey, int size){
		ArrayList<Object[]>  result = new ArrayList<Object[]>();
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT DATE_FORMAT(MD_DATE,'%Y-%m-%d') AS MD_DATE #일자                                                                                                                                    \n");
			sb.append("		, (SELECT SG_NAME FROM SITE_GROUP SG WHERE USEYN = 'Y' AND SG.SG_SEQ =ID.SG_SEQ) AS CHANNEL #채널    																																	 \n");
			sb.append("		, ID.ID_TITLE #제목 																																 \n");
			sb.append("		, ID.ID_URL  																																 \n");
			sb.append("		, ID.MD_SITE_NAME  																																	 \n");
			sb.append("		, IDC.IC_TYPE 	 																																 \n");
			sb.append("		, IDC.IC_CODE  																														 \n");
			sb.append("		, MD_SITE_NAME #최초출처  	 																																 \n");
			sb.append("		, ID.S_SEQ    																																 \n");
			sb.append("		, ID.MD_DATE      																																 \n");
			sb.append("		, IFNULL(ID.MD_WRITER,'') AS MD_WRITER																																 \n");
			sb.append("		, CASE ID.ID_SENTI WHEN 1 THEN '긍정'       																																 \n");
			sb.append("						   WHEN 2 THEN '부정'				    																																 \n");
			sb.append("						   WHEN 3 THEN '중립'					    																																 \n");
			sb.append("		  ELSE '' END ID_SENTI #성향   																																 \n");
			sb.append("  FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC                                                                                                                                              \n");
			sb.append(" WHERE ID.ID_SEQ = IDC.ID_SEQ                                                                                                                                                       \n");
			sb.append("   AND MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'                                                                                                                                              \n");
			sb.append("   AND IDC.IC_TYPE = 7                                                                                                                                                  \n");
			sb.append("   AND IDC.IC_CODE = "+ic_code+" #선택된 보도자료명 IC_CODE                                                                                                                                                 \n");
			if(!"".equals(sg_seq)){
			sb.append("   AND ID.SG_SEQ IN ("+sg_seq+") #채널선택시                                                                                                                                                 \n");
			}
			if(!"".equals(senti)){
			sb.append("   AND ID.ID_SENTI IN ( "+senti+")  #성향선택시                                                                                                                                                  \n");
			}
			if(!"".equals(searchkey)){
			sb.append("   AND ID_TITLE LIKE '%"+searchkey+"%' 										\n");			
			} 
			sb.append(" GROUP BY ID.ID_SEQ, IDC.IC_CODE                                                                                                                                                 \n");
			sb.append(" ORDER BY MD_DATE                                                                                                                                               \n");
			
			
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());
			
			Object[] item = new Object[size];
			int idx = 0;
			while(rs.next()){				
				idx = 0;
				item = new Object[size];
				item[idx++] = rs.getString("MD_SITE_NAME");
				item[idx++] = rs.getString("ID_TITLE");
				item[idx++] = rs.getString("ID_URL");
				item[idx++] = rs.getString("MD_DATE");
				item[idx++] = rs.getString("ID_SENTI");
				
				result.add(item);
				
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
	
	public JSONObject getPopRelationKeywordList(String sDate, String eDate, String pre_sDate, String pre_eDate, String ic_code, String senti, String sg_seq, String searchkey){
		JSONObject result = new JSONObject();
		JSONArray arr = new JSONArray();
		
		/*
		HashMap<String, String> colorMap = new HashMap<String, String>();
		
		colorMap.put("긍정", "#5ba1e0");  
		colorMap.put("부정", "#ea7070"); 
		colorMap.put("중립", "#b4b4b4");  
		*/
		
		//String[] preDate = getPrevDate(sDate, eDate);
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();                                                                                                                                                                             
			sb.append("SELECT A2.RK_SEQ                                                                                                                                                                         \n");
			sb.append("     , A2.CNT_D                                                                                                                                                                      \n");
			sb.append("     , A2.CNT_P                                                                                                                                                                       \n");
			sb.append("     , A2.RK_NAME                                                                                                                                                                           \n");
			sb.append("     , IF((A2.CNT_P - A2.CNT_D) = 0, 0, IF((A2.CNT_P - A2.CNT_D) > 0, -1, 1)) AS FACTOR_POINT                                                                                                                                                                       \n");
			sb.append("     , ROUND(IFNULL((ABS(A2.CNT_D - A2.CNT_P) / A2.CNT_P * 100),0),1) AS FACTOR_PER                                                                                                                                                                       \n");
			sb.append("  FROM (                                                                                                                                                                                  \n");
			sb.append("        SELECT A.*                                                                                                                                                                      \n");
			sb.append("             , (                                                                                                                                                              \n");
			sb.append("          	    SELECT IFNULL(COUNT(*),0)                                                                                                                                                                     \n");
			sb.append("             	  FROM ISSUE_DATA A                                                                                                                                                        \n");
			sb.append("             	 	   INNER JOIN RELATION_KEYWORD_MAP T  ON A.ID_SEQ = T.ID_SEQ                                                                                                                                                          \n");
			sb.append("         			 , ISSUE_DATA_CODE IDC                                                                                                                                       \n");
			sb.append("           		 WHERE A.MD_DATE BETWEEN '"+pre_sDate+" 00:00:00' AND '"+pre_eDate+" 23:59:59'                                                                                                                                                           \n");
			sb.append("           		   AND A.ID_SEQ = IDC.ID_SEQ                                                                                                                                                       \n");
			sb.append("           		   AND IDC.IC_TYPE = 7                                                                                                                                                     \n");
			sb.append("           		   AND IDC.IC_CODE = "+ic_code+"                                                                                                                                                    \n");
			if(!"".equals(senti)){
				sb.append("   			   AND ID_SENTI IN ("+senti+")   										\n");	
			}
			if(!"".equals(sg_seq)){
				sb.append("   			   AND SG_SEQ IN ("+sg_seq+") 										\n");	
			}
			if(!"".equals(searchkey)){
				sb.append("   			   AND ID_TITLE LIKE '%"+searchkey+"%'   										\n");	
			}
			sb.append("           		   AND RK_SEQ = A.RK_SEQ                                                                                                                                                      \n");
			sb.append("           	  ) AS CNT_P                                                                                                                                                      \n");
			sb.append("          FROM (                                                                               \n");
			sb.append("          	    SELECT R.RK_NAME                                                                            \n");
			sb.append("          	    	 , R.RK_SEQ                                                                            \n");
			sb.append("          	   		 , COUNT(*) AS CNT_D                                                                            \n");
			sb.append("          	      FROM ISSUE_DATA_CODE IDC                                                                          \n");
			sb.append("          	         , ISSUE_DATA A                                                                          \n");
			sb.append("          	    	   INNER JOIN RELATION_KEYWORD_MAP T  ON A.ID_SEQ = T.ID_SEQ                                                                            \n");
			sb.append("          	    	   INNER JOIN RELATION_KEYWORD_R R ON T.RK_SEQ = R.RK_SEQ                                                                            \n");
			sb.append("          	    WHERE 1=1                                                                            \n");
			sb.append("   			  	  AND A.ID_SEQ = IDC.ID_SEQ  										\n");	
			sb.append("   			  	  AND IDC.IC_TYPE = 7 										\n");	
			sb.append("   			  	  AND IDC.IC_CODE = "+ic_code+"									\n");	
			sb.append("   			  	  AND A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' 								\n");	
			if(!"".equals(senti)){
				sb.append("   			  AND ID_SENTI IN ("+senti+")   										\n");	
			}
			if(!"".equals(sg_seq)){
				sb.append("   			  AND SG_SEQ IN ("+sg_seq+") 										\n");	
			}
			if(!"".equals(searchkey)){
				sb.append("   			  AND ID_TITLE LIKE '%"+searchkey+"%'   										\n");	
			}
			sb.append("          		GROUP BY R.RK_SEQ                                                                                                                                                               \n");
			sb.append("          		ORDER BY CNT_D DESC                                                                                                                                                               \n");
			sb.append("          		LIMIT 30                                                                                                                                                               \n");
			sb.append("       		  )A                                                                                                                                                            \n");
			sb.append("       ) A2                                                                                                     \n");
			
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("RK_SEQ", rs.getString("RK_SEQ"));
				obj.put("RK_NAME", rs.getString("RK_NAME"));
				obj.put("CNT_D", rs.getString("CNT_D"));
				obj.put("CNT_P", rs.getString("CNT_P"));
				obj.put("FACTOR_POINT", rs.getString("FACTOR_POINT"));
				obj.put("FACTOR_PER", rs.getString("FACTOR_PER"));
				//obj.put("COLOR", colorMap.get(rs.getString("CODE_NAME")));
				
				arr.put(obj);
				
			}
			
			result.put("list", arr);
			
			
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

	public ArrayList<Object[]>  getPopRelationKeywordList_excel(String sDate, String eDate, String pre_sDate, String pre_eDate,String ic_code, String senti, String sg_seq, String searchkey, int size){
		ArrayList<Object[]>  result = new ArrayList<Object[]>();
		
		//String[] preDate = getPrevDate(sDate, eDate);
	
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();                                                                                                                                                                             
			sb = new StringBuffer();                                                                                                                                                                             
			sb.append("SELECT A2.RK_SEQ                                                                                                                                                                         \n");
			sb.append("     , A2.CNT_D                                                                                                                                                                      \n");
			sb.append("     , A2.RK_NAME                                                                                                                                                                           \n");
			sb.append("     , ROUND(IFNULL((ABS(A2.CNT_D - A2.CNT_P) / A2.CNT_P * 100),0),1) AS FACTOR_PER                                                                                                                                                                       \n");
			sb.append("  FROM (                                                                                                                                                                                  \n");
			sb.append("        SELECT A.*                                                                                                                                                                      \n");
			sb.append("             , (                                                                                                                                                              \n");
			sb.append("          	    SELECT IFNULL(COUNT(*),0)                                                                                                                                                                     \n");
			sb.append("             	  FROM ISSUE_DATA A                                                                                                                                                        \n");
			sb.append("             	 	   INNER JOIN RELATION_KEYWORD_MAP T  ON A.ID_SEQ = T.ID_SEQ                                                                                                                                                          \n");
			sb.append("         			 , ISSUE_DATA_CODE IDC                                                                                                                                       \n");
			sb.append("           		 WHERE A.MD_DATE BETWEEN '"+pre_sDate+" 00:00:00' AND '"+pre_eDate+" 23:59:59'                                                                                                                                                           \n");
			sb.append("           		   AND A.ID_SEQ = IDC.ID_SEQ                                                                                                                                                       \n");
			sb.append("           		   AND IDC.IC_TYPE = 7                                                                                                                                                     \n");
			sb.append("           		   AND IDC.IC_CODE = "+ic_code+"                                                                                                                                                    \n");
			if(!"".equals(senti)){
				sb.append("   			   AND ID_SENTI IN ("+senti+")   										\n");	
			}
			if(!"".equals(sg_seq)){
				sb.append("   			   AND SG_SEQ IN ("+sg_seq+") 										\n");	
			}
			if(!"".equals(searchkey)){
				sb.append("   			   AND ID_TITLE LIKE '%"+searchkey+"%'   										\n");	
			}
			sb.append("           		   AND RK_SEQ = A.RK_SEQ                                                                                                                                                      \n");
			sb.append("           	  ) AS CNT_P                                                                                                                                                      \n");
			sb.append("          FROM (                                                                               \n");
			sb.append("          	    SELECT R.RK_NAME                                                                            \n");
			sb.append("          	    	 , R.RK_SEQ                                                                            \n");
			sb.append("          	   		 , COUNT(*) AS CNT_D                                                                            \n");
			sb.append("          	      FROM ISSUE_DATA_CODE IDC                                                                          \n");
			sb.append("          	         , ISSUE_DATA A                                                                          \n");
			sb.append("          	    	   INNER JOIN RELATION_KEYWORD_MAP T  ON A.ID_SEQ = T.ID_SEQ                                                                            \n");
			sb.append("          	    	   INNER JOIN RELATION_KEYWORD_R R ON T.RK_SEQ = R.RK_SEQ                                                                            \n");
			sb.append("          	    WHERE 1=1                                                                            \n");
			sb.append("   			  	  AND A.ID_SEQ = IDC.ID_SEQ  										\n");	
			sb.append("   			  	  AND IDC.IC_TYPE = 7 										\n");	
			sb.append("   			  	  AND IDC.IC_CODE = "+ic_code+"									\n");	
			sb.append("   			  	  AND A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' 								\n");	
			if(!"".equals(senti)){
				sb.append("   			  AND ID_SENTI IN ("+senti+")   										\n");	
			}
			if(!"".equals(sg_seq)){
				sb.append("   			  AND SG_SEQ IN ("+sg_seq+") 										\n");	
			}
			if(!"".equals(searchkey)){
				sb.append("   			  AND ID_TITLE LIKE '%"+searchkey+"%'   										\n");	
			}
			sb.append("          		GROUP BY R.RK_SEQ                                                                                                                                                               \n");
			sb.append("          		ORDER BY CNT_D DESC                                                                                                                                                               \n");
			sb.append("          		LIMIT 30                                                                                                                                                               \n");
			sb.append("       		  )A                                                                                                                                                            \n");
			sb.append("       ) A2                                                                                                     \n");
			
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());
			
			Object[] item = new Object[size];
			int idx = 0;
			
			while(rs.next()){
				idx = 0;
				item = new Object[size];
				item[idx++] = rs.getString("RK_NAME");
				item[idx++] = rs.getString("CNT_D");
				item[idx++] = rs.getString("FACTOR_PER");
				
				result.add(item);
				
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
	
	
	public JSONObject getPopChartList(String sDate, String eDate, String ic_code, String senti, String sg_seq, String searchkey, String chart_type){
		JSONObject result = new JSONObject();
		JSONArray arr = new JSONArray();
		
		int dayDiff = Integer.parseInt(getDayDiff(sDate, eDate));
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			//주별
			if("week".equals(chart_type)) {
				                                                                                                                                                                                 
				sb.append("SELECT CONCAT(A.MYWEEK,'주차') AS DATE                                                                                                                                 \n");
				sb.append("     , IFNULL(B.CNT, 0) AS CNT                                                                                                                                         \n");
				sb.append("  FROM (                                                                                                                                                               \n");
				sb.append("        SELECT DATE_FORMAT(A.DATE, '%U') - DATE_FORMAT('"+sDate+"', '%U') + 1 AS MYWEEK /*시작일자*/                                                                  \n");
				sb.append("           FROM (                                                                                                                                                      \n");
				sb.append("                 SELECT DATE_FORMAT(DATE_ADD('"+sDate+" 00:00:00', INTERVAL @ROW:=@ROW+1 DAY),'%Y-%m-%d')  AS DATE  /*시작일자*/                                      \n");
				sb.append("            		   FROM KEYWORD A                                                                                                                                  \n");
				sb.append("            			    , (SELECT @ROW:=-1)R                                                                                                                          \n");
				sb.append("            		  LIMIT "+dayDiff+" /*일자차이값 넣기*/                                                                                                                        \n");
				sb.append("                ) A                                                                                                                                                    \n");
				sb.append("          GROUP BY DATE_FORMAT(A.DATE, '%U')                                                                                                                           \n");
				sb.append("       )A LEFT OUTER JOIN (                                                                                                                                            \n");
				sb.append("       SELECT DATE_FORMAT(MD_DATE,'%U') - DATE_FORMAT('"+sDate+"', '%U') + 1 AS MYWEEK                                                                                \n");
				sb.append("            , COUNT(*) AS CNT                                                                                                                                          \n");
				sb.append("         FROM ISSUE_DATA ID                                                                                                                                                   \n");
				sb.append("         	 INNER JOIN ISSUE_DATA_CODE IDC ON (                                                                                                                                              \n");
				sb.append("         	 	   ID.ID_SEQ = IDC.ID_SEQ AND IDC.IC_TYPE = 7  AND IDC.IC_CODE = "+ic_code+" #선택된 보도자료명 IC_CODE                                                                                                                                           \n");
				sb.append("         	 )                                                                                                                                          \n");
				sb.append("        WHERE ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' /*일별 및 주별 조건*/                                                                                  \n");
				if(!"".equals(senti)){
					sb.append("   AND ID_SENTI IN ("+senti+") /*감성 조건*/  										\n");	
				}
				if(!"".equals(sg_seq)){
					sb.append("   AND SG_SEQ IN ("+sg_seq+") /*채널 조건*/  										\n");	
				}
				if(!"".equals(searchkey)) {
					sb.append("   AND ID_TITLE LIKE '%"+searchkey+"%'  										\n");	
				}
				sb.append("         GROUP BY DATE_FORMAT(MD_DATE, '%U')                                                                                                                           \n");
				sb.append("       ) B ON A.MYWEEK = B.MYWEEK                                                                                                                                      \n");
				sb.append("       ORDER BY 1                                                                                                                                    \n");
				
			//일별				
			}else if("day".equals(chart_type)) {
				                                                                                                                                                                                
				sb.append("SELECT A.DATE                                                                                                                                                        \n");
				sb.append("     , IFNULL(B.CNT,0) AS CNT                                                                                                                                        \n");
				sb.append("  FROM (SELECT DATE_FORMAT(DATE_ADD('"+sDate+" 00:00:00', INTERVAL @ROW:=@ROW+1 DAY),'%Y-%m-%d')  AS DATE  /*시작일자*/                                             \n");
				sb.append("    		   FROM ISSUE_CODE A                                                                                                                                        \n");
				sb.append("    			    , (SELECT @ROW:=-1)R                                                                                                                                \n");
				sb.append("    		  LIMIT "+dayDiff+" /*일자차이값 넣기*/                                                                                                                              \n");
				sb.append("       ) A LEFT OUTER JOIN (                                                                                                                                         \n");
				sb.append("        SELECT DATE_FORMAT(MD_DATE, '%Y-%m-%d') AS DATE                                                                                                              \n");
				sb.append("             , COUNT(*) AS CNT                                                                                                                                       \n");
				sb.append("         FROM ISSUE_DATA ID                                                                                                                                                   \n");
				sb.append("         	 INNER JOIN ISSUE_DATA_CODE IDC ON (                                                                                                                                              \n");
				sb.append("         	 	   ID.ID_SEQ = IDC.ID_SEQ AND IDC.IC_TYPE = 7  AND IDC.IC_CODE = "+ic_code+" #선택된 보도자료명 IC_CODE                                                                                                                                           \n");
				sb.append("         	 )                                                                                                                                          \n");
				sb.append("        WHERE ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' /*일별 및 주별 조건*/                                                                                  \n");
				if(!"".equals(senti)){
					sb.append("   AND ID_SENTI IN ("+senti+") /*감성 조건*/  										\n");	
				}
				if(!"".equals(sg_seq)){
					sb.append("   AND SG_SEQ IN ("+sg_seq+") /*채널 조건*/  										\n");	
				}
				if(!"".equals(searchkey)) {
					sb.append("   AND ID_TITLE LIKE '%"+searchkey+"%'  										\n");	
				}
				sb.append("         GROUP BY DATE_FORMAT(MD_DATE, '%Y-%m-%d')                                                                                                                   \n");
				sb.append("       ) B ON A.DATE = B.DATE                                                                                                                                        \n");
				sb.append("       ORDER BY 1                                                                                                                                    \n");
				
			} else if("month".equals(chart_type)) {
				sb.append("SELECT A.MYMONTH  AS DATE                                                                                                                                                      \n");
				sb.append("     , IFNULL(B.CNT,0) AS CNT                                                                                                                                        \n");
				sb.append("  FROM ( SELECT DATE_FORMAT(A.DATE, '%Y-%m') AS MYMONTH /*시작일자*/                                            \n");
				sb.append("    		  FROM (                                                                                                                                       \n");
				sb.append("    			     SELECT DATE_FORMAT(DATE_ADD('"+sDate+" 00:00:00', INTERVAL @ROW:=@ROW+1 DAY),'%Y-%m-%d')  AS DATE  /*시작일자*/                                                                                                                              \n");
				sb.append("    			       FROM KEYWORD A                                                                                                                              \n");
				sb.append("    			       	  , (SELECT @ROW:=-1)R                                                                                                                               \n");
				sb.append("    		    	  LIMIT "+dayDiff+" /*일자차이값 넣기*/                                                                                                                              \n");
				sb.append("    		       ) A   	                                                                                                                               \n");
				sb.append("    		 GROUP BY DATE_FORMAT(A.DATE, '%Y-%m')                                                                                                                                   \n");
				sb.append("       ) A LEFT OUTER JOIN (                                                                                                                                         \n");
				sb.append("        SELECT DATE_FORMAT(MD_DATE, '%Y-%m') AS DATE                                                                                                              \n");
				sb.append("             , COUNT(*) AS CNT                                                                                                                                       \n");
				sb.append("         FROM ISSUE_DATA ID                                                                                                                                                   \n");
				sb.append("         	 INNER JOIN ISSUE_DATA_CODE IDC ON (                                                                                                                                              \n");
				sb.append("         	 	   ID.ID_SEQ = IDC.ID_SEQ AND IDC.IC_TYPE = 7  AND IDC.IC_CODE = "+ic_code+" #선택된 보도자료명 IC_CODE                                                                                                                                           \n");
				sb.append("         	 )                                                                                                                                          \n");
				sb.append("        WHERE ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' /*일별 및 주별 조건*/                                                                                  \n");
				if(!"".equals(senti)){
					sb.append("   AND ID_SENTI IN ("+senti+") /*감성 조건*/  										\n");	
				}
				if(!"".equals(sg_seq)){
					sb.append("   AND SG_SEQ IN ("+sg_seq+") /*채널 조건*/  										\n");	
				}
				if(!"".equals(searchkey)) {
					sb.append("   AND ID_TITLE LIKE '%"+searchkey+"%'  										\n");	
				}
				sb.append("         GROUP BY DATE_FORMAT(MD_DATE, '%Y-%m')                                                                                                                   \n");
				sb.append("       ) B ON A.MYMONTH = B.DATE                                                                                                                                         \n");
				sb.append("       GROUP BY MYMONTH                                                                                                                                        \n");
				sb.append("       ORDER BY 1                                                                                                                                    \n");
			}
			
			
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());
			
			
			arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("DATE", rs.getString("DATE"));
				obj.put("CNT", rs.getString("CNT"));
				
				arr.put(obj);
				
			}
			
			result.put("list", arr);
			
			
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
	
	public ArrayList<Object[]> getPopChartList_excel(String sDate, String eDate, String ic_code, String senti, String sg_seq, String searchkey, String chart_type, int size){
		ArrayList<Object[]>  result = new ArrayList<Object[]>();
		
		int dayDiff = Integer.parseInt(getDayDiff(sDate, eDate));
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			//주별
			if("week".equals(chart_type)) {
				                                                                                                                                                                                 
				sb.append("SELECT CONCAT(A.MYWEEK,'주차') AS DATE                                                                                                                                 \n");
				sb.append("     , IFNULL(B.CNT, 0) AS CNT                                                                                                                                         \n");
				sb.append("  FROM (                                                                                                                                                               \n");
				sb.append("        SELECT DATE_FORMAT(A.DATE, '%U') - DATE_FORMAT('"+sDate+"', '%U') + 1 AS MYWEEK /*시작일자*/                                                                  \n");
				sb.append("           FROM (                                                                                                                                                      \n");
				sb.append("                 SELECT DATE_FORMAT(DATE_ADD('"+sDate+" 00:00:00', INTERVAL @ROW:=@ROW+1 DAY),'%Y-%m-%d')  AS DATE  /*시작일자*/                                      \n");
				sb.append("            		   FROM KEYWORD A                                                                                                                                  \n");
				sb.append("            			    , (SELECT @ROW:=-1)R                                                                                                                          \n");
				sb.append("            		  LIMIT "+dayDiff+" /*일자차이값 넣기*/                                                                                                                        \n");
				sb.append("                ) A                                                                                                                                                    \n");
				sb.append("          GROUP BY DATE_FORMAT(A.DATE, '%U')                                                                                                                           \n");
				sb.append("       )A LEFT OUTER JOIN (                                                                                                                                            \n");
				sb.append("       SELECT DATE_FORMAT(MD_DATE,'%U') - DATE_FORMAT('"+sDate+"', '%U') + 1 AS MYWEEK                                                                                \n");
				sb.append("            , COUNT(*) AS CNT                                                                                                                                          \n");
				sb.append("         FROM ISSUE_DATA ID                                                                                                                                                   \n");
				sb.append("         	 INNER JOIN ISSUE_DATA_CODE IDC ON (                                                                                                                                              \n");
				sb.append("         	 	   ID.ID_SEQ = IDC.ID_SEQ AND IDC.IC_TYPE = 7  AND IDC.IC_CODE = "+ic_code+" #선택된 보도자료명 IC_CODE                                                                                                                                           \n");
				sb.append("         	 )                                                                                                                                          \n");
				sb.append("        WHERE ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' /*일별 및 주별 조건*/                                                                                  \n");
				if(!"".equals(senti)){
					sb.append("   AND ID_SENTI IN ("+senti+") /*감성 조건*/  										\n");	
				}
				if(!"".equals(sg_seq)){
					sb.append("   AND SG_SEQ IN ("+sg_seq+") /*채널 조건*/  										\n");	
				}
				if(!"".equals(searchkey)) {
					sb.append("   AND ID_TITLE LIKE '%"+searchkey+"%'  										\n");	
				}
				sb.append("         GROUP BY DATE_FORMAT(MD_DATE, '%U')                                                                                                                           \n");
				sb.append("       ) B ON A.MYWEEK = B.MYWEEK                                                                                                                                      \n");
				sb.append("       ORDER BY 1                                                                                                                                    \n");
				
			//일별				
			}else if("day".equals(chart_type)) {
				                                                                                                                                                                                
				sb.append("SELECT A.DATE                                                                                                                                                        \n");
				sb.append("     , IFNULL(B.CNT,0) AS CNT                                                                                                                                        \n");
				sb.append("  FROM (SELECT DATE_FORMAT(DATE_ADD('"+sDate+" 00:00:00', INTERVAL @ROW:=@ROW+1 DAY),'%Y-%m-%d')  AS DATE  /*시작일자*/                                             \n");
				sb.append("    		   FROM ISSUE_CODE A                                                                                                                                        \n");
				sb.append("    			    , (SELECT @ROW:=-1)R                                                                                                                                \n");
				sb.append("    		  LIMIT "+dayDiff+" /*일자차이값 넣기*/                                                                                                                              \n");
				sb.append("       ) A LEFT OUTER JOIN (                                                                                                                                         \n");
				sb.append("        SELECT DATE_FORMAT(MD_DATE, '%Y-%m-%d') AS DATE                                                                                                              \n");
				sb.append("             , COUNT(*) AS CNT                                                                                                                                       \n");
				sb.append("         FROM ISSUE_DATA ID                                                                                                                                                   \n");
				sb.append("         	 INNER JOIN ISSUE_DATA_CODE IDC ON (                                                                                                                                              \n");
				sb.append("         	 	   ID.ID_SEQ = IDC.ID_SEQ AND IDC.IC_TYPE = 7  AND IDC.IC_CODE = "+ic_code+" #선택된 보도자료명 IC_CODE                                                                                                                                           \n");
				sb.append("         	 )                                                                                                                                          \n");
				sb.append("        WHERE ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' /*일별 및 주별 조건*/                                                                                  \n");
				if(!"".equals(senti)){
					sb.append("   AND ID_SENTI IN ("+senti+") /*감성 조건*/  										\n");	
				}
				if(!"".equals(sg_seq)){
					sb.append("   AND SG_SEQ IN ("+sg_seq+") /*채널 조건*/  										\n");	
				}
				if(!"".equals(searchkey)) {
					sb.append("   AND ID_TITLE LIKE '%"+searchkey+"%'  										\n");	
				}
				sb.append("         GROUP BY DATE_FORMAT(MD_DATE, '%Y-%m-%d')                                                                                                                   \n");
				sb.append("       ) B ON A.DATE = B.DATE                                                                                                                                        \n");
				sb.append("       ORDER BY 1                                                                                                                                    \n");
				
			} else if("month".equals(chart_type)) {
				sb.append("SELECT A.MYMONTH  AS DATE                                                                                                                                                      \n");
				sb.append("     , IFNULL(B.CNT,0) AS CNT                                                                                                                                        \n");
				sb.append("  FROM ( SELECT DATE_FORMAT(A.DATE, '%Y-%m') AS MYMONTH /*시작일자*/                                            \n");
				sb.append("    		  FROM (                                                                                                                                       \n");
				sb.append("    			     SELECT DATE_FORMAT(DATE_ADD('"+sDate+" 00:00:00', INTERVAL @ROW:=@ROW+1 DAY),'%Y-%m-%d')  AS DATE  /*시작일자*/                                                                                                                              \n");
				sb.append("    			       FROM KEYWORD A                                                                                                                              \n");
				sb.append("    			       	  , (SELECT @ROW:=-1)R                                                                                                                               \n");
				sb.append("    		    	  LIMIT "+dayDiff+" /*일자차이값 넣기*/                                                                                                                              \n");
				sb.append("    		       ) A   	                                                                                                                               \n");
				sb.append("    		 GROUP BY DATE_FORMAT(A.DATE, '%Y-%m')                                                                                                                                   \n");
				sb.append("       ) A LEFT OUTER JOIN (                                                                                                                                         \n");
				sb.append("        SELECT DATE_FORMAT(MD_DATE, '%Y-%m') AS DATE                                                                                                              \n");
				sb.append("             , COUNT(*) AS CNT                                                                                                                                       \n");
				sb.append("         FROM ISSUE_DATA ID                                                                                                                                                   \n");
				sb.append("         	 INNER JOIN ISSUE_DATA_CODE IDC ON (                                                                                                                                              \n");
				sb.append("         	 	   ID.ID_SEQ = IDC.ID_SEQ AND IDC.IC_TYPE = 7  AND IDC.IC_CODE = "+ic_code+" #선택된 보도자료명 IC_CODE                                                                                                                                           \n");
				sb.append("         	 )                                                                                                                                          \n");
				sb.append("        WHERE ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' /*일별 및 주별 조건*/                                                                                  \n");
				if(!"".equals(senti)){
					sb.append("   AND ID_SENTI IN ("+senti+") /*감성 조건*/  										\n");	
				}
				if(!"".equals(sg_seq)){
					sb.append("   AND SG_SEQ IN ("+sg_seq+") /*채널 조건*/  										\n");	
				}
				if(!"".equals(searchkey)) {
					sb.append("   AND ID_TITLE LIKE '%"+searchkey+"%'  										\n");	
				}
				sb.append("         GROUP BY DATE_FORMAT(MD_DATE, '%Y-%m')                                                                                                                   \n");
				sb.append("       ) B ON A.MYMONTH = B.DATE                                                                                                                                         \n");
				sb.append("       GROUP BY MYMONTH                                                                                                                                        \n");
				sb.append("       ORDER BY 1                                                                                                                                    \n");
			}
			
			
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());
			
			
			Object[] item = new Object[size];
			int idx = 0;			
			 
			while(rs.next()){
				idx = 0;
				item = new Object[size];
				item[idx++] = rs.getString("DATE");
				item[idx++] = rs.getString("CNT");
				
				result.add(item);
				
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

	public String getDayDiff(String sDate, String eDate){		
		String result = "0";
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();				
			sb.append("\n").append("SELECT TIMESTAMPDIFF( DAY,'"+sDate+"', '"+eDate+"')+1 AS DIFF ");

			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result =  rs.getString("DIFF");
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
	

	public String[] weekCalendar(String yyyymmdd) throws Exception{
		  
		  Calendar cal = Calendar.getInstance();
		  int toYear = 0;
		  int toMonth = 0;
		  int toDay = 0;
		  int yy =Integer.parseInt(yyyymmdd.substring(0, 4));
		  int mm =Integer.parseInt(yyyymmdd.substring(4, 6))-1;
		  int dd =Integer.parseInt(yyyymmdd.substring(6, 8));
		  cal.set(yy, mm,dd);
		  String[] arrYMD = new String[7];
		  
		  int inYear = cal.get(cal.YEAR); 
		  int inMonth = cal.get(cal.MONTH);
		  int inDay = cal.get(cal.DAY_OF_MONTH);
		  int yoil = cal.get(cal.DAY_OF_WEEK); //요일나오게하기(숫자로)
		  if(yoil != 7){   //해당요일이 일요일이 아닌경우
		      yoil = yoil-1;
		   }else{           //해당요일이 일요일인경우
		      yoil = 1;
		   }
		  inDay = inDay-yoil;
		  for(int i = 0; i < 7;i++){
		   cal.set(inYear, inMonth, inDay+i);  //
		   String y = Integer.toString(cal.get(cal.YEAR)); 
		   String m = Integer.toString(cal.get(cal.MONTH)+1);
		   String d = Integer.toString(cal.get(cal.DAY_OF_MONTH));
		   if(m.length() == 1) m = "0" + m;
		   if(d.length() == 1) d = "0" + d;
		   
		   arrYMD[i] = y+m +d;
		   //System.out.println("ymd ="+ y+m+d);
		   
		  }
		  
		  return arrYMD;
	}
	
	
}
