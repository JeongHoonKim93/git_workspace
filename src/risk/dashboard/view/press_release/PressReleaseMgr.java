package risk.dashboard.view.press_release;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;



public class PressReleaseMgr {

	private DateUtil du = new DateUtil();
	private StringUtil su = new StringUtil();
	
	private String topic_type1 = "1";
	private String topic_type2 = "4";
	private HashMap<String, String > siteGroup_map = new HashMap<String, String>(){};
	
	{
		siteGroup_map.put("AI_MEDIA", "93"); 
		siteGroup_map.put("AI_BLOG", "94"); //블로그
		siteGroup_map.put("AI_CAFE", "95"); //카페
		siteGroup_map.put("AI_INTELLECT" , "96"); //지식인
		siteGroup_map.put("AI_COMMUNITY", "97"); //커뮤니티
		siteGroup_map.put("AI_PUBLIC", "98"); //공공기관
		siteGroup_map.put("AI_TWITTER", "99"); //트위터
		siteGroup_map.put("AI_FACEBOOK", "100"); //페이스북
		siteGroup_map.put("AI_INSTAGRAM", "101"); //인스타그램
		siteGroup_map.put("AI_YOUTUBE", "102"); //유튜브
		siteGroup_map.put("AI_OVERSEAS", "105"); //해외
	}
	
	
	private HashMap<String, String > company_map = new HashMap<String, String>(){};
	
	{
		company_map.put("CJ", "1"); 
		company_map.put("DAESANG", "2"); //블로그
		company_map.put("PULMUONE", "3"); //카페
		company_map.put("OTTOGI" , "4"); //지식인
		company_map.put("DONGWON", "5"); //커뮤니티
	}
	

	DBconn conn = null;
	Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    StringBuffer sb = null;
    
    

    public ArrayList<String[]> getSiteGroupList(){
    	conn = new DBconn();
    	
    	ArrayList<String[]> result = new ArrayList<String[]>();    	
    	try {

			conn.getDBCPConnection();
			sb = new StringBuffer();
		    sb.append("SELECT SG_SEQ, SG_NAME                     \n");
		    sb.append(" FROM SITE_GROUP                             \n");
		    sb.append("WHERE USEYN = 'Y'                 \n");
		    sb.append("ORDER BY SG_ORDER                 \n");
			pstmt = conn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			System.out.println(sb.toString());

			String[] sub_info = new String[2];
	    	while(rs.next()){
	    		sub_info = new String[2];
	    		sub_info[0] = rs.getString("SG_SEQ");
	    		sub_info[1] = rs.getString("SG_NAME");
	    		
	    		result.add(sub_info);
	    	}

		}catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
   	 	return result;
    }
    
    
    public String[] getPressReleaseDate(String ic_code) {
    	String[] result = new String[2];
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			if(!ic_code.equals("")) {
				
				sb = new StringBuffer();                                                                                   
				sb.append("\n").append("SELECT DATE_FORMAT(MAX(MD_DATE),'%Y-%m-%d') AS MAXDATE                                                           			");
				sb.append("\n").append(" 	 , DATE_FORMAT(MIN(MD_DATE),'%Y-%m-%d') AS MINDATE                                                              	");
				sb.append("\n").append("  FROM ISSUE_DATA ID                                                              	");
				sb.append("\n").append(" INNER JOIN ISSUE_DATA_CODE IDC ON (                                                               	");
				sb.append("\n").append("  		    ID.ID_SEQ = IDC.ID_SEQ AND IDC.IC_TYPE = 7                                                               	");
				sb.append("\n").append("  		    AND IDC.IC_CODE = "+ic_code+" #선택된 보도자료명 IC_CODE                                                               	");
				sb.append("\n").append("  		  	)                                                                	");
				
				rs = stmt.executeQuery(sb.toString());
				System.out.println(sb.toString());
				
				if(rs.next()) {
					result[0] = rs.getString("MINDATE");
					result[1] = rs.getString("MAXDATE");
				}
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
    }
    
    
    
    public int getPressDataListCount(String sDate, String eDate){
    	int result = 0;

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
		
			sb = new StringBuffer();                                                                                   
			sb.append("\n").append("SELECT COUNT(*) AS CNT #확산량                                                             			");
			sb.append("\n").append("  FROM (                                                                	");
			sb.append("\n").append("	    SELECT  *                                                              	");
			sb.append("\n").append("  		  FROM ISSUE_CODE                                                                	");
			sb.append("\n").append("  		 WHERE IC_USEYN = 'Y'                                                                	");
			sb.append("\n").append("  		   AND IC_TYPE = 7                                                               	");
			sb.append("\n").append("  		   AND IC_CODE > 0                                                           	");
			sb.append("\n").append("  		   AND IC_REGDATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'                ");
			sb.append("\n").append("  )A,(                                                             	");
			sb.append("\n").append("  		SELECT  IDC.*                                                              	");
			sb.append("\n").append("  		  FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC                                                             	");
			sb.append("\n").append("  		 WHERE ID.ID_SEQ = IDC.ID_SEQ                                                               	");
			sb.append("\n").append(" 		   AND MD_DATE > '"+sDate+" 00:00:00'                                                               	");
			sb.append("\n").append(" 		   AND IDC.IC_TYPE = 7										 ");
			sb.append("\n").append("   		 GROUP BY IDC.IC_CODE															");
			sb.append("\n").append("   		 ORDER BY MD_DATE 															");
			sb.append("\n").append("  )B WHERE A.IC_TYPE = B.IC_TYPE AND A.IC_CODE = B.IC_CODE															");
			
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			if(rs.next()) {
				result = rs.getInt("CNT");
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
    }
    
    
    
    public ArrayList<HashMap<String, String>> getPressDataList(String sDate,String eDate, int sLimit){
    	ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
		
			sb = new StringBuffer();                                                                                   
			sb.append("\n").append("SELECT A.PART #담당부서                                                                                                      ");
			sb.append("\n").append("     , DATE_FORMAT(IC_REGDATE,'%Y-%m-%d') AS REG_DATE  #배포일자                                                                                               ");
			sb.append("\n").append("     , IC_REGDATE                                                                                                 ");
			sb.append("\n").append("     , A.IC_NAME #보도자료명                                                                                         ");
			sb.append("\n").append("     , B.MD_SITE_NAME #최초출처                                                                                        ");
			sb.append("\n").append("     , COUNT(*) AS CNT #확산량                                                                                      ");
			sb.append("\n").append("     , A.IC_TYPE                                                                                       ");
			sb.append("\n").append("     , A.IC_CODE                                                                                        ");
			sb.append("\n").append("  FROM (                                                                                                          ");
			sb.append("\n").append("        SELECT IC_REGDATE                                                                                          ");
			sb.append("\n").append("             , IC_DESCRIPTION AS PART   #담당부서                                                                               ");
			sb.append("\n").append("             , IC_TYPE                                                                                ");
			sb.append("\n").append("             , IC_CODE                                                                                   ");
			sb.append("\n").append("             , IC_NAME                                                                                   ");
			sb.append("\n").append("          FROM ISSUE_CODE                                                                                 ");
			sb.append("\n").append("         WHERE IC_USEYN = 'Y'			               ");
			sb.append("\n").append("           AND IC_TYPE = 7                                                                             ");
			sb.append("\n").append("           AND IC_CODE > 0                                                                           ");
			sb.append("\n").append("           AND IC_REGDATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'                                                                          ");
			sb.append("\n").append("  )A,  (                                                                             ");
			sb.append("\n").append("        SELECT IDC.IC_TYPE                                                                     ");
			sb.append("\n").append("        	 , IDC.IC_CODE                                                                     ");
			sb.append("\n").append("        	 , MD_SITE_NAME #최초출처                                                                          ");
			sb.append("\n").append("          FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC                                                                     ");
			sb.append("\n").append("         WHERE ID.ID_SEQ = IDC.ID_SEQ                                                                  ");
			sb.append("\n").append("           AND MD_DATE > '"+sDate+" 00:00:00'                                                               ");
			sb.append("\n").append("           AND IDC.IC_TYPE = 7                                                                 ");
			sb.append("\n").append("         GROUP BY ID.ID_SEQ, IDC.IC_CODE                                                                  ");
			sb.append("\n").append("         ORDER BY MD_DATE                                                                  ");
			sb.append("\n").append("  )B                                                             ");
			sb.append("\n").append(" WHERE A.IC_TYPE = B.IC_TYPE AND A.IC_CODE = B.IC_CODE                                                                  ");
			sb.append("\n").append(" GROUP BY B.IC_CODE                                                                ");
			sb.append("\n").append(" ORDER BY A.IC_REGDATE DESC                                                                 ");
			sb.append("\n").append(" LIMIT "+sLimit+", 10                                                                                     ");
			
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			HashMap<String, String> item = new HashMap<String, String>();
			while(rs.next()) {
				item = new HashMap<String, String>();
				item.put("PART", rs.getString("PART"));
				item.put("REG_DATE", rs.getString("REG_DATE"));
				item.put("IC_REGDATE", rs.getString("IC_REGDATE"));
				item.put("IC_NAME", rs.getString("IC_NAME"));
				item.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
				item.put("CNT", rs.getString("CNT"));
				item.put("IC_TYPE", rs.getString("IC_TYPE"));
				item.put("IC_CODE", rs.getString("IC_CODE"));
				
				result.add(item);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
    }
    
    
    public ArrayList<Object[]> getPressDataList_excel(String sDate, String eDate, int size){
    	ArrayList<Object[]> result = new ArrayList<Object[]>();

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
		
			sb = new StringBuffer();                                                                                   
			sb = new StringBuffer();                                                                                   
			sb.append("\n").append("SELECT IFNULL(A.PART, '') AS PART #담당부서                                                                                                      ");
			sb.append("\n").append("     , DATE_FORMAT(IC_REGDATE,'%Y-%m-%d') AS REG_DATE  #배포일자                                                                                               ");
			sb.append("\n").append("     , IC_REGDATE                                                                                                 ");
			sb.append("\n").append("     , A.IC_NAME #보도자료명                                                                                         ");
			sb.append("\n").append("     , B.MD_SITE_NAME #최초출처                                                                                        ");
			sb.append("\n").append("     , COUNT(*) AS CNT #확산량                                                                                      ");
			sb.append("\n").append("     , A.IC_TYPE                                                                                       ");
			sb.append("\n").append("     , A.IC_CODE                                                                                        ");
			sb.append("\n").append("  FROM (                                                                                                          ");
			sb.append("\n").append("        SELECT IC_REGDATE                                                                                          ");
			sb.append("\n").append("             , IC_DESCRIPTION AS PART   #담당부서                                                                               ");
			sb.append("\n").append("             , IC_TYPE                                                                                ");
			sb.append("\n").append("             , IC_CODE                                                                                   ");
			sb.append("\n").append("             , IC_NAME                                                                                   ");
			sb.append("\n").append("          FROM ISSUE_CODE                                                                                 ");
			sb.append("\n").append("         WHERE IC_USEYN = 'Y'			               ");
			sb.append("\n").append("           AND IC_TYPE = 7                                                                             ");
			sb.append("\n").append("           AND IC_CODE > 0                                                                           ");
			sb.append("\n").append("           AND IC_REGDATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'                                                                          ");
			sb.append("\n").append("  )A,  (                                                                             ");
			sb.append("\n").append("        SELECT IDC.IC_TYPE                                                                     ");
			sb.append("\n").append("        	 , IDC.IC_CODE                                                                     ");
			sb.append("\n").append("        	 , MD_SITE_NAME #최초출처                                                                          ");
			sb.append("\n").append("          FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC                                                                     ");
			sb.append("\n").append("         WHERE ID.ID_SEQ = IDC.ID_SEQ                                                                  ");
			sb.append("\n").append("           AND MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'                                                               ");
			sb.append("\n").append("           AND IDC.IC_TYPE = 7                                                                 ");
			sb.append("\n").append("         GROUP BY ID.ID_SEQ, IDC.IC_CODE                                                                  ");
			sb.append("\n").append("         ORDER BY MD_DATE                                                                  ");
			sb.append("\n").append("  )B                                                             ");
			sb.append("\n").append(" WHERE A.IC_TYPE = B.IC_TYPE AND A.IC_CODE = B.IC_CODE                                                                  ");
			sb.append("\n").append(" GROUP BY B.IC_CODE                                                                ");
			sb.append("\n").append(" ORDER BY A.IC_REGDATE DESC                                                                 ");
			
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			Object[] item = new Object[size];
			int idx = 0;
			while(rs.next()) {
				idx = 0;
				item = new Object[size];
				item[idx++] = rs.getString("PART");
				item[idx++] = rs.getString("REG_DATE");
				item[idx++] = rs.getString("IC_NAME");
				item[idx++] = rs.getString("MD_SITE_NAME");
				item[idx++] = rs.getInt("CNT");
				
				result.add(item);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
    }
    
    public ArrayList<HashMap<String, String>> getPortalDataList(String sDate , String eDate, int sLimit){
    	ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
		
			sb = new StringBuffer();                                                                                   
			sb.append("\n").append("SELECT MD_SEQ                                                                      ");
			sb.append("\n").append("     , MD_SITE_NAME                                                                ");
			sb.append("\n").append("     , MD_MENU_NAME                                                                ");
			sb.append("\n").append("     , MD_TITLE                                                                    ");
			sb.append("\n").append("     , MD_URL                                                                      ");
			sb.append("\n").append("     , MD_DATE                                                                     ");
			sb.append("\n").append("     , S_SEQ                                                                     ");
			sb.append("\n").append("  FROM TOP                                                                         ");
			sb.append("\n").append(" WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 00:00:00'       		   ");
			sb.append("\n").append(" ORDER BY MD_SEQ DESC                                                                   ");
			sb.append("\n").append(" LIMIT "+sLimit+",10                                                                  ");
			
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			HashMap<String, String> item = new HashMap<String, String>();
			while(rs.next()) {
				item = new HashMap<String, String>();
				item.put("MD_SEQ", rs.getString("MD_SEQ"));
				item.put("SITE_NAME", rs.getString("MD_SITE_NAME"));
				item.put("MENU_NAME", rs.getString("MD_MENU_NAME"));
				item.put("TITLE", rs.getString("MD_TITLE"));
				item.put("URL", rs.getString("MD_URL"));
				item.put("DATE", rs.getString("MD_DATE"));
				item.put("S_SEQ", rs.getString("S_SEQ"));
				
				result.add(item);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
    }
    public ArrayList<Object[]> getPortalDataList_excel(String sDate , String eDate, int sLimit){
    	ArrayList<Object[]> result = new ArrayList<Object[]>();

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
		
			sb = new StringBuffer();                                                                                   
			sb.append("\n").append("SELECT MD_SEQ                                                                      ");
			sb.append("\n").append("     , MD_SITE_NAME                                                                ");
			sb.append("\n").append("     , MD_MENU_NAME                                                                ");
			sb.append("\n").append("     , MD_TITLE                                                                    ");
			sb.append("\n").append("     , MD_URL                                                                      ");
			sb.append("\n").append("     , MD_DATE                                                                     ");
			sb.append("\n").append("     , S_SEQ                                                                     ");
			sb.append("\n").append("  FROM TOP                                                                         ");
			sb.append("\n").append(" WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 00:00:00'       		   ");
			sb.append("\n").append(" ORDER BY MD_SEQ DESC                                                                   ");
			
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			Object[] item = new Object[sLimit];
			int idx = 0;
			while(rs.next()) {
				idx = 0;
				item = new Object[sLimit];				
				item[idx++] = rs.getString("MD_SITE_NAME");
				item[idx++] = rs.getString("MD_MENU_NAME");
				item[idx++] = rs.getString("MD_TITLE");
				item[idx++] = rs.getString("MD_URL");				
				item[idx++] = rs.getString("MD_DATE");
				
				result.add(item);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
    }   
    
    
    
    public ArrayList<HashMap<String, String>> getClaimTopDataList(String company_code, String department, String sDate , String eDate, String psDate, String peDate, String ptype, String pcode, String type, int rowLimit){
    	ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
		
			sb = new StringBuffer();                                                                                   
			sb.append("\n").append("SELECT A.*                                                                                                                        ");
			sb.append("\n").append("	 , A.CNT_D                                                                                                                        ");
			sb.append("\n").append("     , ABS(A.CNT_P - A.CNT_D) AS FACTOR_CNT /*증감 건수*/                                                                             ");
			sb.append("\n").append("     , IF((A.CNT_P - A.CNT_D) = 0, 0, IF((A.CNT_P - A.CNT_D) > 0, -1, 1)) AS FACTOR_POINT /*증감 화살표*/                              ");
			sb.append("\n").append("     , ROUND(IFNULL((ABS(A.CNT_P - A.CNT_D) / A.CNT_P * 100),0),1) AS FACTOR_PER /*증감율*/                                          ");
			sb.append("\n").append("     , IFNULL((SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+ptype+" AND IC_CODE = A.IC_PCODE),'') AS IC_PNAME                         ");
			sb.append("\n").append("  FROM (                                                                                                                          ");
			sb.append("\n").append("        SELECT A.IC_CODE                                                                                                          ");
			sb.append("\n").append("             , A.IC_NAME                                                                                                       ");
			sb.append("\n").append("             , A.IC_PCODE                                                                                                         ");
			sb.append("\n").append("             , IFNULL(B.CNT_D,0) AS CNT_D                                                                                         ");
			sb.append("\n").append("             , IFNULL(C.CNT_P,0) AS CNT_P                                                                                         ");
			sb.append("\n").append("          FROM (                                                                                                                  ");
			sb.append("\n").append("                SELECT IC_CODE, IC_NAME, IC_PCODE FROM ISSUE_CODE WHERE IC_PTYPE = "+ptype+" AND IC_PCODE IN ("+pcode+")              ");
			sb.append("\n").append("               )A LEFT OUTER JOIN (                                                                                           ");
			sb.append("\n").append("                 SELECT AI_CODE2                                                                                                  ");
			//sb.append("\n").append("                       , COUNT(*) AS CNT_D                                                                                        ");
			sb.append("\n").append("                         , SUM(AI_TOTAL)AS CNT_D                                                                                        ");
			sb.append("\n").append("                    FROM ANA_MAIN_DEPT"+department+"_COMPANY_VOCD2                                                                             ");
			sb.append("\n").append("                   WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' /*검색기간*/                                                       ");
			sb.append("\n").append("                     AND AI_TYPE1 = "+topic_type1+"                                                                                              ");
			sb.append("\n").append("                     AND AI_CODE1 = "+company_code+" /*업체*/                                                                                      ");
			sb.append("\n").append("                     AND AI_TYPE2 = "+type+"                                                                                             ");
			sb.append("\n").append("                   GROUP BY AI_CODE2                                                                                              ");
			sb.append("\n").append("               )B ON A.IC_CODE = B.AI_CODE2 LEFT OUTER JOIN (                                                                     ");
			sb.append("\n").append("                 SELECT AI_CODE2                                                                                                  ");
			//sb.append("\n").append("                       , COUNT(*) AS CNT_P                                                                                        ");
			sb.append("\n").append("                         , SUM(AI_TOTAL)AS CNT_P                                                                                        ");
			sb.append("\n").append("                    FROM ANA_MAIN_DEPT"+department+"_COMPANY_VOCD2                                                                             ");
			sb.append("\n").append("                   WHERE AI_DATE BETWEEN '"+psDate.replaceAll("-", "")+"' AND '"+peDate.replaceAll("-", "")+"' /*검색기간*/                                                       ");
			sb.append("\n").append("                     AND AI_TYPE1 = "+topic_type1+"                                                                                              ");
			sb.append("\n").append("                     AND AI_CODE1 = "+company_code+" /*업체*/                                                                                      ");
			sb.append("\n").append("                     AND AI_TYPE2 = "+type+"                                                                                             ");
			sb.append("\n").append("                   GROUP BY AI_CODE2                                                                                         ");
			sb.append("\n").append("               )C ON A.IC_CODE = C.AI_CODE2                                                                                       ");
			sb.append("\n").append("         ORDER BY B.CNT_D DESC                                                                                                    ");
			sb.append("\n").append("         LIMIT "+rowLimit+"                                                                                                                  ");
			sb.append("\n").append("       )A                                                                                                                         ");
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			HashMap<String, String> item = new HashMap<String, String>();
			String point = "";
			while(rs.next()) {
				point = "";
				item = new HashMap<String, String>();
				item.put("FACTOR_CNT", rs.getString("FACTOR_CNT"));
				
				//증감표시
				point = rs.getString("FACTOR_POINT");
				if("0".equals(point)){
					point = "none";
				}else if("1".equals(point)){
					point = "up";
				}else if ("-1".equals(point)){
					point = "dn";
				}
				
				item.put("FACTOR_POINT", point);				
				
				item.put("IC_CODE", rs.getString("IC_CODE"));
				item.put("IC_PCODE", rs.getString("IC_PCODE"));
				item.put("CNT_D", rs.getString("CNT_D"));
				
				//수량이 0건일 경우, -표시
				if(rs.getInt("CNT_D")>0){
					item.put("IC_PNAME", rs.getString("IC_PNAME"));
					item.put("IC_NAME", rs.getString("IC_NAME"));
					item.put("FACTOR_PER", rs.getString("FACTOR_PER"));
					
				}else{
					item.put("IC_PNAME", "-");
					item.put("IC_NAME", "-");
					item.put("FACTOR_PER", "0");
				}
				
				
				result.add(item);
			}
			
			//그래프가 행단위라서, 값 없는 경우 -표시
			int tmp_length = result.size();
			
			if(tmp_length<rowLimit){				
				tmp_length = rowLimit-tmp_length;
				for(int i=0; i<tmp_length; i++){
					item = new HashMap<String, String>();
					item.put("FACTOR_CNT", "-");
					item.put("FACTOR_POINT", "none");
					item.put("CNT_D", "0");
					item.put("FACTOR_PER", "-");
					item.put("IC_PNAME", "-");
					item.put("IC_CODE", "-");
					item.put("IC_NAME", "-");
					item.put("IC_PCODE", "-");
					
					result.add(item);
				}
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
    }
    
    public ArrayList<Object[]> getClaimTopDataList_excel(String company_code, String department, String sDate , String eDate, String psDate, String peDate, String ptype, String pcode, String type, int rowLimit, int size){
    	ArrayList<Object[]> result = new ArrayList<Object[]>();
    	for (int i = 1; i < 6; i++) {	
    		company_code = Integer.toString(i);
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();                                                                                   
			sb.append("\n").append("SELECT A.*                                                                                                                        ");
			sb.append("\n").append("	 , A.CNT_D                                                                                                                        ");
			sb.append("\n").append("     , ABS(A.CNT_P - A.CNT_D) AS FACTOR_CNT /*증감 건수*/                                                                             ");
			sb.append("\n").append("     , IF((A.CNT_P - A.CNT_D) = 0, 0, IF((A.CNT_P - A.CNT_D) > 0, -1, 1)) AS FACTOR_POINT /*증감 화살표*/                              ");
			sb.append("\n").append("     , ROUND(IFNULL((ABS(A.CNT_P - A.CNT_D) / A.CNT_P * 100),0),1) AS FACTOR_PER /*증감율*/                                          ");
			sb.append("\n").append("     , IFNULL((SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+ptype+" AND IC_CODE = A.IC_PCODE),'') AS IC_PNAME                         ");
			sb.append("\n").append("     , IFNULL((SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 1 AND IC_CODE = "+company_code+"),'') AS IC_COMPANY_NAME                          ");
			sb.append("\n").append("  FROM (                                                                                                                          ");
			sb.append("\n").append("        SELECT A.IC_CODE                                                                                                          ");
			sb.append("\n").append("             , A.IC_NAME                                                                                                       ");
			sb.append("\n").append("             , A.IC_PCODE                                                                                                         ");
			sb.append("\n").append("             , IFNULL(B.CNT_D,0) AS CNT_D                                                                                         ");
			sb.append("\n").append("             , IFNULL(C.CNT_P,0) AS CNT_P                                                                                         ");
			sb.append("\n").append("          FROM (                                                                                                                  ");
			sb.append("\n").append("                SELECT IC_CODE, IC_NAME, IC_PCODE FROM ISSUE_CODE WHERE IC_PTYPE = "+ptype+" AND IC_PCODE IN ("+pcode+")              ");
			sb.append("\n").append("               )A LEFT OUTER JOIN (                                                                                           ");
			sb.append("\n").append("                 SELECT AI_CODE2                                                                                                  ");
			//sb.append("\n").append("                       , COUNT(*) AS CNT_D                                                                                        ");
			sb.append("\n").append("                         , SUM(AI_TOTAL)AS CNT_D                                                                                        ");
			sb.append("\n").append("                    FROM ANA_MAIN_DEPT"+department+"_COMPANY_VOCD2                                                                             ");
			sb.append("\n").append("                   WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' /*검색기간*/                                                       ");
			sb.append("\n").append("                     AND AI_TYPE1 = "+topic_type1+"                                                                                              ");
			sb.append("\n").append("                     AND AI_CODE1 = "+company_code+" /*업체*/                                                                                      ");
			sb.append("\n").append("                     AND AI_TYPE2 = "+type+"                                                                                             ");
			sb.append("\n").append("                   GROUP BY AI_CODE2                                                                                              ");
			sb.append("\n").append("               )B ON A.IC_CODE = B.AI_CODE2 LEFT OUTER JOIN (                                                                     ");
			sb.append("\n").append("                 SELECT AI_CODE2                                                                                                  ");
			//sb.append("\n").append("                       , COUNT(*) AS CNT_P                                                                                        ");
			sb.append("\n").append("                         , SUM(AI_TOTAL)AS CNT_P                                                                                        ");
			sb.append("\n").append("                    FROM ANA_MAIN_DEPT"+department+"_COMPANY_VOCD2                                                                             ");
			sb.append("\n").append("                   WHERE AI_DATE BETWEEN '"+psDate.replaceAll("-", "")+"' AND '"+peDate.replaceAll("-", "")+"' /*검색기간*/                                                       ");
			sb.append("\n").append("                     AND AI_TYPE1 = "+topic_type1+"                                                                                              ");
			sb.append("\n").append("                     AND AI_CODE1 = "+company_code+" /*업체*/                                                                                      ");
			sb.append("\n").append("                     AND AI_TYPE2 = "+type+"                                                                                             ");
			sb.append("\n").append("                   GROUP BY AI_CODE2                                                                                         ");
			sb.append("\n").append("               )C ON A.IC_CODE = C.AI_CODE2                                                                                       ");
			sb.append("\n").append("         ORDER BY B.CNT_D DESC                                                                                                    ");
			sb.append("\n").append("         LIMIT "+rowLimit+"                                                                                                                  ");
			sb.append("\n").append("       )A                                                                                                                         ");
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			Object[] item = new Object[size];
			int idx = 0;
			String point = "";
			while(rs.next()) {
				item = new Object[size];
				idx = 0;
					item[idx++] = rs.getString("IC_COMPANY_NAME");
					if(rs.getInt("CNT_D") == 0){
						item[idx++] = "-";
						item[idx++] = "-";
						item[idx++] = "-";
					}else {
						item[idx++] = rs.getString("IC_PNAME");
						item[idx++] = rs.getString("IC_NAME");
						item[idx++] = rs.getInt("CNT_D");
					}
					result.add(item);	
			}

		} 
		catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		} }
		return result;
    }
    
    public int getCoverindDataCount(String sDate, String eDate, String ic_code, String sg_seq, String senti){
    	int result = 0;

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
		
			if(!ic_code.equals("")) {
				sb = new StringBuffer();                                                                                   
				sb.append("\n").append("SELECT COUNT(*) AS CNT #수량                                                              			");
				sb.append("\n").append("  FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC                                                                 	");
				sb.append("\n").append(" WHERE ID.ID_SEQ = IDC.ID_SEQ  										 ");
				sb.append("\n").append("   AND MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'															");
				sb.append("\n").append("   AND IDC.IC_TYPE = 7															");
				sb.append("\n").append("   AND IDC.IC_CODE = "+ic_code+" #선택된 보도자료명 IC_CODE															");
				if(!sg_seq.equals("")) {
				sb.append("\n").append("   AND ID.SG_SEQ IN ("+sg_seq+") #채널선택시                                                                                ");
				}
				if(!senti.equals("")) {
				sb.append("\n").append("   AND ID.ID_SENTI IN ( "+senti+")  #성향선택시                                                                         ");
				}
				sb.append("\n").append(" GROUP BY IDC.IC_CODE															");
				sb.append("\n").append(" ORDER BY MD_DATE 															");
				
				
				rs = stmt.executeQuery(sb.toString());
				System.out.println(sb.toString());
				if(rs.next()) {
					result = rs.getInt("CNT");
				}
			
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
    }
    
    public ArrayList<HashMap<String, String>> getCoverindDataList(String sDate, String eDate, String ic_code, String sg_seq, String senti, int sLimit){
    	ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
		
			
			if(!ic_code.equals("")) {
				sb = new StringBuffer();                                                                                   
				sb.append("\n").append("SELECT DATE_FORMAT(MD_DATE,'%Y-%m-%d') AS MD_DATE #일자                                                                                                   ");
				sb.append("\n").append("     , (SELECT SG_NAME FROM SITE_GROUP SG WHERE USEYN = 'Y' AND SG.SG_SEQ =ID.SG_SEQ) AS CHANNEL #채널                                                                                           ");
				sb.append("\n").append("     , ID.ID_TITLE #제목                                                                                                 ");
				sb.append("\n").append("     , ID.ID_URL                                                                                       ");
				sb.append("\n").append("     , ID.MD_SITE_NAME                                                                                        ");
				sb.append("\n").append("     , IDC.IC_TYPE                                                                                     ");
				sb.append("\n").append("     , IDC.IC_CODE                                                                                      ");
				sb.append("\n").append("     , MD_SITE_NAME #최초출처                                                                                         ");
				sb.append("\n").append("     , ID.S_SEQ                                                                                      ");
				sb.append("\n").append("     , ID.MD_DATE                                                                                   ");
				sb.append("\n").append("     , IFNULL(ID.MD_WRITER,'') AS MD_WRITER                                                                                      ");
				sb.append("\n").append("     , CASE ID.ID_SENTI WHEN 1 THEN '긍정'                                                                                      ");
				sb.append("\n").append("     					WHEN 2 THEN '부정'				                                                                                      ");
				sb.append("\n").append("     					WHEN 3 THEN '중립'				                                                                                      ");
				sb.append("\n").append("       ELSE '' END ID_SENTI #성향                                                                                        ");
				sb.append("\n").append("  FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC                                                                                                        ");
				sb.append("\n").append(" WHERE ID.ID_SEQ = IDC.ID_SEQ                                                                                     ");
				sb.append("\n").append("   AND MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'                                                                          ");
				sb.append("\n").append("   AND IDC.IC_TYPE = 7                                                                                ");
				sb.append("\n").append("   AND IDC.IC_CODE = "+ic_code+" #선택된 보도자료명 IC_CODE                                                                             ");
				if(!sg_seq.equals("")) {
				sb.append("\n").append("   AND ID.SG_SEQ IN ("+sg_seq+") #채널선택시                                                                                ");
				}
				if(!senti.equals("")) {
				sb.append("\n").append("   AND ID.ID_SENTI IN ( "+senti+")  #성향선택시                                                                         ");
				}
				sb.append("\n").append(" GROUP BY ID.ID_SEQ, IDC.IC_CODE		               ");
				sb.append("\n").append(" ORDER BY MD_DATE DESC                                                                           ");
				sb.append("\n").append(" LIMIT "+sLimit+", 10                                                                                     ");
				
				
				rs = stmt.executeQuery(sb.toString());
				System.out.println(sb.toString());
				HashMap<String, String> item = new HashMap<String, String>();
				while(rs.next()) {
					item = new HashMap<String, String>();
					item.put("MD_DATE", rs.getString("MD_DATE"));
					item.put("CHANNEL", rs.getString("CHANNEL"));
					item.put("ID_TITLE", rs.getString("ID_TITLE"));
					item.put("ID_URL", rs.getString("ID_URL"));
					item.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
					item.put("S_SEQ", rs.getString("S_SEQ"));
					item.put("MD_WRITER", rs.getString("MD_WRITER"));
					item.put("ID_SENTI", rs.getString("ID_SENTI"));
					
					result.add(item);
				}
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
    }
    
    
	public ArrayList<Object[]> getCoverindDataList_excel(String sDate, String eDate, String ic_code, String sg_seq, String senti, int size) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();                                                                                   
			sb.append("\n").append("SELECT DATE_FORMAT(MD_DATE,'%Y-%m-%d') AS MD_DATE #일자                                                                                                   ");
			sb.append("\n").append("     , (SELECT SG_NAME FROM SITE_GROUP SG WHERE USEYN = 'Y' AND SG.SG_SEQ =ID.SG_SEQ) AS CHANNEL #채널                                                                                           ");
			sb.append("\n").append("     , ID.ID_TITLE #제목                                                                                                 ");
			sb.append("\n").append("     , ID.ID_URL                                                                                       ");
			sb.append("\n").append("     , ID.MD_SITE_NAME                                                                                        ");
			sb.append("\n").append("     , IDC.IC_TYPE                                                                                     ");
			sb.append("\n").append("     , IDC.IC_CODE                                                                                      ");
			sb.append("\n").append("     , MD_SITE_NAME #최초출처                                                                                         ");
			sb.append("\n").append("     , ID.MD_DATE                                                                                   ");
			sb.append("\n").append("     , IFNULL(ID.MD_WRITER,'') AS MD_WRITER #기자명(작성자)                                                                                        ");
			sb.append("\n").append("     , CASE ID.ID_SENTI WHEN 1 THEN '긍정'                                                                                      ");
			sb.append("\n").append("     					WHEN 2 THEN '부정'				                                                                                      ");
			sb.append("\n").append("     					WHEN 3 THEN '중립'				                                                                                      ");
			sb.append("\n").append("       ELSE '' END ID_SENTI #성향                                                                                        ");
			sb.append("\n").append("  FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC                                                                                                        ");
			sb.append("\n").append(" WHERE ID.ID_SEQ = IDC.ID_SEQ                                                                                     ");
			sb.append("\n").append("   AND MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'                                                                           ");
			sb.append("\n").append("   AND IDC.IC_TYPE = 7                                                                                ");
			sb.append("\n").append("   AND IDC.IC_CODE = "+ic_code+" #선택된 보도자료명 IC_CODE                                                                             ");
			if(!sg_seq.equals("")) {
			sb.append("\n").append("   AND ID.SG_SEQ IN ("+sg_seq+") #채널선택시                                                                                ");
			}
			if(!senti.equals("")) {
			sb.append("\n").append("   AND ID.ID_SENTI IN ( "+senti+")  #성향선택시                                                                         ");
			}
			sb.append("\n").append(" GROUP BY ID.ID_SEQ, IDC.IC_CODE		               ");
			sb.append("\n").append(" ORDER BY MD_DATE  DESC                                                                          ");

			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			Object[] item = new Object[size];
			int idx = 0;
			while(rs.next()) {
				idx = 0;
				item = new Object[size];
				item[idx++] = rs.getString("MD_DATE");
				item[idx++] = rs.getString("CHANNEL");
				item[idx++] = rs.getString("ID_TITLE");
				item[idx++] = rs.getString("ID_URL");
				item[idx++] = rs.getString("MD_SITE_NAME");
				item[idx++] = rs.getString("MD_WRITER");
				item[idx++] = rs.getString("ID_SENTI");
				result.add(item);
				
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
    
    
    
    public int getPortalCount(String sDate , String eDate){
    	int result = 0;

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
		
			sb = new StringBuffer();                                                                                   
			sb.append("\n").append("SELECT COUNT(*)AS CNT                                                              ");
			sb.append("\n").append("  FROM TOP                                                                         ");
			sb.append("\n").append(" WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 00:00:00'             ");
			
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			if(rs.next()) {
				result = rs.getInt("CNT");
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
    }
    
    
    public HashMap<String, String> getTopic_ToTalCountDetail(String departType, String sDate, String eDate, String t1_code) {
    	return getTopic_ToTalCountDetail( departType,  sDate,  eDate,  t1_code, "");
    }

	public ArrayList<Object[]> getTopic_ToTalCountDetail_excel1(String departType, String sDate, String eDate, String t1_code, int size) {
		ArrayList<Object[]>  result = new ArrayList<Object[]> ();
		
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();
			sb.append("\n").append("		#전체 정보량																	");
			sb.append("\n").append("		SELECT  IFNULL(SUM(AI_TOTAL),0) AS AI_TOTAL                                            ");
			sb.append("\n").append("               , IFNULL(SUM(IF(AI_CODE1 = 1 , AI_TOTAL, 0 )),0) AS CJ                                                               ");
			sb.append("\n").append("               , IFNULL(SUM(IF(AI_CODE1 = 2 , AI_TOTAL, 0 )),0) AS DAESANG                                                          ");
			sb.append("\n").append("               , IFNULL(SUM(IF(AI_CODE1 = 3 , AI_TOTAL, 0 )),0) AS PULMUONE                                                         ");
			sb.append("\n").append("               , IFNULL(SUM(IF(AI_CODE1 = 4 , AI_TOTAL, 0 )),0) AS OTTOGI                                                           ");
			sb.append("\n").append("               , IFNULL(SUM(IF(AI_CODE1 = 5 , AI_TOTAL, 0 )),0) AS DONGWON                                                          ");
			sb.append("\n").append("		    , IF(SUM(AI_TOTAL)IS NULL,0,100) AS TOTAL_PER                                                 ");
			sb.append("\n").append("		    , IFNULL(SUM(AI_POS),0) AS AI_POS                                                     ");
			sb.append("\n").append("		    , ROUND(IFNULL(ABS(SUM(AI_POS)) / SUM(AI_TOTAL) * 100,0),1)  AS POS_PER                                                ");
			sb.append("\n").append("		    , IFNULL(SUM(AI_NEG),0) AS AI_NEG                                                 ");
			sb.append("\n").append("		    , ROUND(IFNULL(ABS(SUM(AI_NEG)) / SUM(AI_TOTAL) * 100,0),1)  AS NEG_PER                                                 ");
			sb.append("\n").append("		    , IFNULL(SUM(AI_NEU),0) AS AI_NEU                                                    ");
			sb.append("\n").append("		     , ROUND(IFNULL(ABS(SUM(AI_NEU)) / SUM(AI_TOTAL) * 100,0),1)  AS NEU_PER    	                                            "); 
			sb.append("\n").append("		 FROM ANA_MAIN_DEPT"+departType+"_COMPANY_VOCD 	/*부서선택*/               ");
			sb.append("\n").append("		WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' 	/*기간설정*/               ");
		//	sb.append("\n").append("		  AND AI_TYPE1 = "+topic_type1+"                                            ");
		//	sb.append("\n").append("		  AND AI_TYPE2 = "+topic_type2+"                                            ");
			sb.append("\n").append("		  AND AI_CODE1 IN ("+t1_code+")                                                ");
					
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			Object[] item = new Object[size];
			int idx = 0;
			int currentCnt = 0;
			int gap = 0;
			if(rs.next()) 
				idx = 0;
				item = new Object[size];
				item[idx++] = "CJ제일제당";
				item[idx++] = rs.getInt("CJ");
				result.add(item);

				idx = 0;
				item = new Object[size];
				item[idx++] = "대상";
				item[idx++] = rs.getInt("DAESANG");
				result.add(item);		

				idx = 0;
				item = new Object[size];
				item[idx++] = "풀무원";
				item[idx++] = rs.getInt("PULMUONE");
				result.add(item);

				idx = 0;
				item = new Object[size];
				item[idx++] = "오뚜기";
				item[idx++] = rs.getInt("OTTOGI");
				result.add(item);

				idx = 0;
				item = new Object[size];
				item[idx++] = "동원";
				item[idx++] = rs.getInt("DONGWON");
				result.add(item);
					
		}
		  catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
    
	/**
	 * MAin - 주제구분
	 * @param sDate	시작일
	 * @param eDate	종료일
	 * @return
	 */
	public HashMap<String, String> getTopic_ToTalCountDetail(String departType, String sDate, String eDate, String t1_code, String t2_code) {
		HashMap<String, String> result = new HashMap<String, String>();
		
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();
			sb.append("\n").append("		#전체 정보량																	");
			sb.append("\n").append("		SELECT SUM(AI_TOTAL) AS AI_TOTAL                                            ");
			sb.append("\n").append("		    , SUM(AI_POS) AS AI_POS                                                 ");
			sb.append("\n").append("		    , SUM(AI_NEG) AS AI_NEG                                                 ");
			sb.append("\n").append("		    , SUM(AI_NEU) AS AI_NEU                                                 ");
			sb.append("\n").append("		 FROM ANA_MAIN_DEPT"+departType+"_COMPANY_TOPIC 	/*부서선택*/               ");
			sb.append("\n").append("		WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' 	/*기간설정*/               ");
			sb.append("\n").append("		  AND AI_TYPE1 = "+topic_type1+"                                            ");
			sb.append("\n").append("		  AND AI_TYPE2 = "+topic_type2+"                                            ");
			sb.append("\n").append("		  AND AI_CODE1 = "+t1_code+"                                                ");
			sb.append("\n").append("".equals(t2_code)?"": "AND AI_CODE2 = "+t2_code+"                                   ");
			
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			if(rs.next()) {
				result.put("TOTAL", (rs.getString("AI_TOTAL")==null)?"0":rs.getString("AI_TOTAL"));
				result.put("POS", (rs.getString("AI_POS")==null)?"0":rs.getString("AI_POS"));
				result.put("NEG", (rs.getString("AI_NEG")==null)?"0":rs.getString("AI_NEG"));
				result.put("NEU", (rs.getString("AI_NEU")==null)?"0":rs.getString("AI_NEU"));
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
	
	public HashMap<String, String> getTopic_ToTalCountPreCurrent(String departType, String sDate, String eDate, String psDate, String peDate, String t1_code) {
		HashMap<String, String> result = new HashMap<String, String>();
		
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();
			sb.append("\n").append("	SELECT A.CNT_D /*당일건수*/                                                                                      ");
			sb.append("\n").append("	     , A.CNT_P /*전일건수*/                                                                                      ");
			sb.append("\n").append("	     , ABS(A.CNT_P - A.CNT_D) AS FACTOR_CNT /*증감 건수*/                                                        ");
			sb.append("\n").append("	     , IF((A.CNT_P - A.CNT_D) = 0, 0, IF((A.CNT_P - A.CNT_D) > 0, -1, 1)) AS FACTOR_POINT /*증감 화살표*/         ");
			sb.append("\n").append("	     , ROUND(IFNULL((ABS(A.CNT_P - A.CNT_D) / A.CNT_P * 100),0),1) AS FACTOR_PER /*증감율*/                     ");
			sb.append("\n").append("	  FROM (                                                                                                     ");
			sb.append("\n").append("	         SELECT (                                                                                            ");
			sb.append("\n").append("	                SELECT IFNULL(SUM(AI_TOTAL),0)                                                               ");
			sb.append("\n").append("	                  FROM ANA_MAIN_DEPT"+departType+"_COMPANY_VOCD A                                                         ");
			sb.append("\n").append("	                 WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' /*검색기간*/                                    ");
			sb.append("\n").append("	                   AND A.AI_TYPE1 = "+topic_type1+"                                                                        ");
			sb.append("\n").append("	                   AND A.AI_CODE1 = "+t1_code+" /*1:CJ제일제당,2:대상,3:풀무원,4:오뚜기,5:동원*/                                     ");
			sb.append("\n").append("	                   AND A.AI_TYPE2 = 7                                       ");
			//sb.append("\n").append("	                   AND A.AI_CODE2 = 3                                      ");
			sb.append("\n").append("	               ) AS CNT_D                                                                                    ");
			sb.append("\n").append("	               , (                                                                                           ");
			sb.append("\n").append("	                  SELECT IFNULL(SUM(AI_TOTAL),0)                                                             ");
			sb.append("\n").append("	                    FROM ANA_MAIN_DEPT"+departType+"_COMPANY_VOCD A                                                       ");
			sb.append("\n").append("	                   WHERE AI_DATE BETWEEN '"+psDate.replaceAll("-", "")+"' AND '"+peDate.replaceAll("-", "")+"' /*이전기간*/                                  ");
			sb.append("\n").append("	                     AND A.AI_TYPE1 = "+topic_type1+"                                                                      ");
			sb.append("\n").append("	                     AND A.AI_CODE1 = "+t1_code+" /*1:CJ제일제당,2:대상,3:풀무원,4:오뚜기,5:동원*/                                   ");
			sb.append("\n").append("	                   AND A.AI_TYPE2 = 7                                       ");
			//sb.append("\n").append("	                   AND A.AI_CODE2 = 3                                      ");
			sb.append("\n").append("	               ) AS CNT_P                                                                                    ");
			sb.append("\n").append("	        )A                                                                                                   ");
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			if(rs.next()) {
				result.put("CNT", rs.getString("CNT_D"));
				result.put("pCNT", rs.getString("CNT_P"));
				result.put("GAP", rs.getString("FACTOR_CNT"));
				result.put("PER", rs.getString("FACTOR_PER"));
				result.put("UPDOWN", rs.getString("FACTOR_POINT"));
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
	
	public int getTopic_ToTalCount(String departType, String sDate, String eDate, String t1_code) {
	    	return getTopic_ToTalCount( departType,  sDate,  eDate,  t1_code, "");
	}
	    
	
	public int getTopic_ToTalCount(String departType, String sDate, String eDate, String t1_code, String t2_code) {
		int result = 0;
		
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();
			sb.append("\n").append("		#전체 정보량																	");
			sb.append("\n").append("		SELECT SUM(AI_TOTAL) AS AI_TOTAL                                            ");
			sb.append("\n").append("			 FROM ANA_MAIN_DEPT"+departType+"_COMPANY_TOPIC 	/*부서선택*/               ");
			sb.append("\n").append("		WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' 	/*기간설정*/               ");
			sb.append("\n").append("		  AND AI_TYPE1 = "+topic_type1+"                                            ");
			sb.append("\n").append("		  AND AI_TYPE2 = "+topic_type2+"                                            ");
			sb.append("\n").append("		  AND AI_CODE1 = "+t1_code+"                                                ");
			sb.append("\n").append("".equals(t2_code)?"": "AND AI_CODE2 = "+t2_code+"                                   ");
			
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			if(rs.next()) {
				result = rs.getInt("AI_TOTAL");
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
	
/*	public ArrayList<Object[]> getTopic_ToTalCountDetail_excel(String departType, String sDate, String eDate, String t1_code, int pCnt, int size) {
		ArrayList<Object[]>  result = new ArrayList<Object[]> ();
		
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();
			sb.append("\n").append("		#전체 정보량																	");
			sb.append("\n").append("		SELECT  IFNULL(SUM(AI_TOTAL),0) AS AI_TOTAL                                            ");
			sb.append("\n").append("		    , IF(SUM(AI_TOTAL)IS NULL,0,100) AS TOTAL_PER                                                 ");
			sb.append("\n").append("		    , IFNULL(SUM(AI_POS),0) AS AI_POS                                                     ");
			sb.append("\n").append("		    , ROUND(IFNULL(ABS(SUM(AI_POS)) / SUM(AI_TOTAL) * 100,0),1)  AS POS_PER                                                ");
			sb.append("\n").append("		    , IFNULL(SUM(AI_NEG),0) AS AI_NEG                                                 ");
			sb.append("\n").append("		    , ROUND(IFNULL(ABS(SUM(AI_NEG)) / SUM(AI_TOTAL) * 100,0),1)  AS NEG_PER                                                 ");
			sb.append("\n").append("		    , IFNULL(SUM(AI_NEU),0) AS AI_NEU                                                    ");
			sb.append("\n").append("		     , ROUND(IFNULL(ABS(SUM(AI_NEU)) / SUM(AI_TOTAL) * 100,0),1)  AS NEU_PER                                                ");
			sb.append("\n").append("		 FROM ANA_MAIN_DEPT"+departType+"_COMPANY_TOPIC 	              ");
			sb.append("\n").append("		WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' 	             ");
			sb.append("\n").append("		  AND AI_TYPE1 = "+topic_type1+"                                            ");
			sb.append("\n").append("		  AND AI_TYPE2 = "+topic_type2+"                                            ");
			sb.append("\n").append("		  AND AI_CODE1 = "+t1_code+"                                                ");
			
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			Object[] item = new Object[size];
			int idx = 0;
			int currentCnt = 0;
			int gap = 0;
			if(rs.next()) {
				idx = 0;
				item = new Object[size];
				item[idx++] = "전체";
				item[idx++] = rs.getString("AI_TOTAL");
				currentCnt = rs.getInt("AI_TOTAL");
				item[idx++] = rs.getString("TOTAL_PER");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "긍정";
				item[idx++] = rs.getString("AI_POS");
				item[idx++] = rs.getString("POS_PER");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "부정";
				item[idx++] = rs.getString("AI_NEG");
				item[idx++] = rs.getString("NEG_PER");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "중립";
				item[idx++] = rs.getString("AI_NEU");
				item[idx++] = rs.getString("NEU_PER");
				result.add(item);
				
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "이전 기간 대비";
				gap = currentCnt-pCnt; 
				if(gap>0){
					item[idx++] = "증감";
				}else if(gap<0){
					item[idx++] = "감소";
				}else{
					item[idx++] = "-";
				}				
				item[idx++] = Math.abs(gap);
				result.add(item);
				
			}
			
			
			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}*/
	
	
	
	public ArrayList<Object[]> getTopic_ToTalCountDetail_excel(String departType, String sDate, String eDate, String t1_code, int size) {
		ArrayList<Object[]>  result = new ArrayList<Object[]> ();
		
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();
			sb.append("\n").append("		#전체 정보량																	");
			sb.append("\n").append("		SELECT  IFNULL(SUM(AI_TOTAL),0) AS AI_TOTAL                                            ");
			sb.append("\n").append("		    , IF(SUM(AI_TOTAL)IS NULL,0,100) AS TOTAL_PER                                                 ");
			sb.append("\n").append("		    , IFNULL(SUM(AI_POS),0) AS AI_POS                                                     ");
			sb.append("\n").append("		    , ROUND(IFNULL(ABS(SUM(AI_POS)) / SUM(AI_TOTAL) * 100,0),1)  AS POS_PER                                                ");
			sb.append("\n").append("		    , IFNULL(SUM(AI_NEG),0) AS AI_NEG                                                 ");
			sb.append("\n").append("		    , ROUND(IFNULL(ABS(SUM(AI_NEG)) / SUM(AI_TOTAL) * 100,0),1)  AS NEG_PER                                                 ");
			sb.append("\n").append("		    , IFNULL(SUM(AI_NEU),0) AS AI_NEU                                                    ");
			sb.append("\n").append("		     , ROUND(IFNULL(ABS(SUM(AI_NEU)) / SUM(AI_TOTAL) * 100,0),1)  AS NEU_PER                                                ");
			sb.append("\n").append("		 FROM ANA_MAIN_DEPT"+departType+"_COMPANY_TOPIC 	/*부서선택*/               ");
			sb.append("\n").append("		WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' 	/*기간설정*/               ");
			sb.append("\n").append("		  AND AI_TYPE1 = "+topic_type1+"                                            ");
			sb.append("\n").append("		  AND AI_TYPE2 = "+topic_type2+"                                            ");
			sb.append("\n").append("		  AND AI_CODE1 = "+t1_code+"                                                ");
			
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			Object[] item = new Object[size];
			int idx = 0;
			int currentCnt = 0;
			int gap = 0;
			if(rs.next()) {
				idx = 0;
				item = new Object[size];
				item[idx++] = "전체";
				item[idx++] = rs.getInt("AI_TOTAL");
				currentCnt = rs.getInt("AI_TOTAL");
				item[idx++] = rs.getString("TOTAL_PER");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "긍정";
				item[idx++] = rs.getInt("AI_POS");
				item[idx++] = rs.getString("POS_PER");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "부정";
				item[idx++] = rs.getInt("AI_NEG");
				item[idx++] = rs.getString("NEG_PER");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "중립";
				item[idx++] = rs.getInt("AI_NEU");
				item[idx++] = rs.getString("NEU_PER");
				result.add(item);
				
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
	
	/**
	 * @param sDate			시작일(필수)
	 * @param eDate			종료일(필수)
	 * @param IC_PTYPE		부모타입
	 * @param IC_PCODE		부모코드
	 * @param TYPE			집계할 코드의 타입(필수)
	 * @return	코드별 수치 집계해서 돌려줌
	 */
	public ArrayList getTopic_EachTypeCount(String department, String sDate, String eDate, String company_code,  String type) {
		ArrayList result = new ArrayList();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();
			sb.append("\n").append("SELECT A.IC_CODE                                                                                                                ");
			sb.append("\n").append("     , A.IC_NAME                                                                                                                ");
			sb.append("\n").append("     , IFNULL(B.AI_TOTAL,0) AS AI_TOTAL                                                                                         ");
			sb.append("\n").append("  FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+type+" AND IC_CODE > 0 AND IC_USEYN = 'Y') A                         ");
			sb.append("\n").append("       LEFT OUTER JOIN(                                                                                                         ");
			sb.append("\n").append("         SELECT AI_CODE2 AS IC_CODE                                                                                             ");
			sb.append("\n").append("              , SUM(AI_TOTAL) AS AI_TOTAL                                                                                       ");
			sb.append("\n").append("           FROM ANA_MAIN_DEPT"+department+"_COMPANY_TOPIC /*부서선택*/                                                                           ");
			sb.append("\n").append("          WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' /*기간설정*/                                                              ");
			sb.append("\n").append("            AND AI_TYPE1 = "+topic_type1+"                                                                                                     ");
			sb.append("\n").append("            AND AI_TYPE2 = "+topic_type2+"                                                                                                     ");
			sb.append("\n").append("            AND AI_CODE1 = "+company_code+"                                                                                                    ");
			sb.append("\n").append("          GROUP BY AI_CODE2                                                                                                     ");
			sb.append("\n").append("       )B ON A.IC_CODE = B.IC_CODE                                                                                              ");
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			while(rs.next()) {
				HashMap<String,String> item = new HashMap<String,String>();
				item.put("IC_NAME", rs.getString("IC_NAME"));
				item.put("IC_CODE", rs.getString("IC_CODE"));
				item.put("CNT", rs.getString("AI_TOTAL"));
				result.add(item);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
	public ArrayList getTopic_EachTypeCount_excel(String department, String sDate, String eDate, String company_code,  String type, int size) {
		ArrayList result = new ArrayList();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();
			sb.append("\n").append("SELECT A.IC_CODE                                                                                                                ");
			sb.append("\n").append("     , A.IC_NAME                                                                                                                ");
			sb.append("\n").append("     , IFNULL(B.AI_TOTAL,0) AS AI_TOTAL                                                                                         ");
			sb.append("\n").append("  FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+type+" AND IC_CODE > 0 AND IC_USEYN = 'Y') A                         ");
			sb.append("\n").append("       LEFT OUTER JOIN(                                                                                                         ");
			sb.append("\n").append("         SELECT AI_CODE2 AS IC_CODE                                                                                             ");
			sb.append("\n").append("              , SUM(AI_TOTAL) AS AI_TOTAL                                                                                       ");
			sb.append("\n").append("           FROM ANA_MAIN_DEPT"+department+"_COMPANY_TOPIC /*부서선택*/                                                                           ");
			sb.append("\n").append("          WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' /*기간설정*/                                                              ");
			sb.append("\n").append("            AND AI_TYPE1 = "+topic_type1+"                                                                                                     ");
			sb.append("\n").append("            AND AI_TYPE2 = "+topic_type2+"                                                                                                     ");
			sb.append("\n").append("            AND AI_CODE1 = "+company_code+"                                                                                                    ");
			sb.append("\n").append("          GROUP BY AI_CODE2                                                                                                     ");
			sb.append("\n").append("       )B ON A.IC_CODE = B.IC_CODE                                                                                              ");
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			Object[] item = new Object[size];
			int idx = 0;
			while(rs.next()) {
				idx = 0;
				item = new Object[size];
				item[idx++] = rs.getString("IC_NAME");
				item[idx++] = rs.getString("IC_CODE");
				item[idx++] = rs.getInt("AI_TOTAL");
				result.add(item);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}

	
	
	
   public ArrayList<HashMap<String, String>> getTopic_PortalReplyPer(String md_seq) {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();                                                                                                    
			sb.append("\n").append("	SELECT                                                                                          ");
			sb.append("\n").append("	  MD_REPLY_TOTAL_CNT AS TOTAL_CNT												                  ");
			sb.append("\n").append("	  , IFNULL(MD_REPLY_POS_CNT,0) AS POS_CNT								                   ");
			sb.append("\n").append("	  , IFNULL(MD_REPLY_NEG_CNT,0) AS NEG_CNT                 ");
			sb.append("\n").append("	  , IFNULL(MD_REPLY_NEU_CNT,0) AS NEU_CNT                 ");
			sb.append("\n").append("	  FROM META_REPLY_CNT                                                                           ");
			sb.append("\n").append("	WHERE MD_SEQ = "+md_seq+"                                                                          ");
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			//통계테이블이 필드별로 되어있어서, 아래와 같이 출력
			HashMap<String,String> item = new HashMap<String,String>();
			if(rs.next()) {
				//0번째는 전체 수량
				item = new HashMap<String,String>();
				item.put("TOTAL_CNT", rs.getString("TOTAL_CNT"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("TYPE", "긍정");
				item.put("SENTI", "1");
				item.put("CNT", rs.getString("POS_CNT"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("TYPE", "부정");
				item.put("SENTI", "2");
				item.put("CNT", rs.getString("NEG_CNT"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("TYPE", "중립");
				item.put("SENTI", "3");
				item.put("CNT", rs.getString("NEU_CNT"));
				result.add(item);
				
				
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
   
   public ArrayList<Object[]> getTopic_PortalReplyPer_excel(String md_seq, int size) {
	   ArrayList<Object[]> result = new ArrayList<Object[]>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();                                                                                                    
			sb.append("\n").append("	SELECT                                                                                          ");
			sb.append("\n").append("	  MD_REPLY_TOTAL_CNT AS TOTAL_CNT												                  ");
			sb.append("\n").append("	  , IFNULL(MD_REPLY_POS_CNT,0) AS POS_CNT								                   ");
			sb.append("\n").append("	  , IFNULL(MD_REPLY_NEG_CNT,0) AS NEG_CNT                 ");
			sb.append("\n").append("	  , IFNULL(MD_REPLY_NEU_CNT,0) AS NEU_CNT                 ");
			sb.append("\n").append("	  FROM META_REPLY_CNT                                                                           ");
			sb.append("\n").append("	WHERE MD_SEQ = "+md_seq+"                                                                          ");
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			//통계테이블이 필드별로 되어있어서, 아래와 같이 출력
			Object[] item = new Object[size];
			int idx = 0;
			if(rs.next()) {
				//0번째는 전체 수량
				idx = 0;
				item = new Object[size];				
				item[idx++] = "전체";
				item[idx++] = rs.getInt("TOTAL_CNT");
				result.add(item);
				
				idx = 0;
				item = new Object[size];				
				item[idx++] = "긍정";
				item[idx++] = rs.getInt("POS_CNT");
				result.add(item);
				
				idx = 0;
				item = new Object[size];				
				item[idx++] = "부정";
				item[idx++] = rs.getInt("NEG_CNT");
				result.add(item);
				
				idx = 0;
				item = new Object[size];				
				item[idx++] = "중립";
				item[idx++] = rs.getInt("NEU_CNT");
				result.add(item);

			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
   
   
	public ArrayList<HashMap<String, String>> getRelatedKeywordList(String md_seq) {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();                                                                                                                      
			sb.append("\n").append(" #연관어 클라우드								  ");
			sb.append("\n").append("  SELECT A.PAT_SEQ                            ");
			sb.append("\n").append("     , PAT_WORD                               ");
			sb.append("\n").append("     , A.PAT_SENTI                            ");
			sb.append("\n").append("     , IFNULL(B.CNT,0) AS CNT                 ");
			sb.append("\n").append("  FROM AUTO_RELATION_KEYWORD A                ");
			sb.append("\n").append("     , (                                      ");
			sb.append("\n").append("        SELECT PAT_SEQ                        ");
			sb.append("\n").append("             , COUNT(*) AS CNT                ");
			sb.append("\n").append("          FROM REPLY_RELATION_KEYWORD         ");
			sb.append("\n").append("         WHERE MD_SEQ_META = "+md_seq+"       ");
			sb.append("\n").append("         GROUP BY PAT_SEQ                     ");
			sb.append("\n").append("         ORDER BY COUNT(*) DESC               ");
			sb.append("\n").append("         LIMIT 20                             ");
			sb.append("\n").append("       )B                                     ");
			sb.append("\n").append(" WHERE A.PAT_SEQ = B.PAT_SEQ                  ");
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			//통계테이블이 필드별로 되어있어서, 아래와 같이 출력
			HashMap<String,String> item = new HashMap<String,String>();
			while(rs.next()) {
				item = new HashMap<String,String>();
				item.put("SEQ",  rs.getString("PAT_SEQ"));
				item.put("CNT", rs.getString("CNT"));
				item.put("WORD", rs.getString("PAT_WORD"));
				item.put("SENTI", rs.getString("PAT_SENTI"));
				result.add(item);
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
	public ArrayList<Object[]> getRelatedKeywordList_excel(String md_seq, int size) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();                                                                                                                      
			sb.append("\n").append(" #연관어 클라우드								  ");
			sb.append("\n").append("  SELECT A.PAT_SEQ                            ");
			sb.append("\n").append("     , PAT_WORD                               ");
			sb.append("\n").append("     , A.PAT_SENTI                            ");
			sb.append("\n").append("     , IFNULL(B.CNT,0) AS CNT                 ");
			sb.append("\n").append("  FROM AUTO_RELATION_KEYWORD A                ");
			sb.append("\n").append("     , (                                      ");
			sb.append("\n").append("        SELECT PAT_SEQ                        ");
			sb.append("\n").append("             , COUNT(*) AS CNT                ");
			sb.append("\n").append("          FROM REPLY_RELATION_KEYWORD         ");
			sb.append("\n").append("         WHERE MD_SEQ_META = "+md_seq+"       ");
			sb.append("\n").append("         GROUP BY PAT_SEQ                     ");
			sb.append("\n").append("         ORDER BY COUNT(*) DESC               ");
			sb.append("\n").append("         LIMIT 20                             ");
			sb.append("\n").append("       )B                                     ");
			sb.append("\n").append(" WHERE A.PAT_SEQ = B.PAT_SEQ                  ");
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			//통계테이블이 필드별로 되어있어서, 아래와 같이 출력
			Object[] item = new Object[size];
			int idx = 0;
			while(rs.next()) {
				idx = 0;
				item = new Object[size];
				item[idx++] = rs.getString("PAT_SEQ");
				item[idx++] = rs.getString("PAT_WORD");
				item[idx++] = rs.getInt("CNT");
			if(rs.getString("PAT_SENTI").equals("1")){
				item[idx++] = "긍정";
			}else if(rs.getString("PAT_SENTI").equals("2")){
				item[idx++] = "부정";
			}else if(rs.getString("PAT_SENTI").equals("3")){
				item[idx++] = "중립";	
			}
			result.add(item);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}	
	
	public ArrayList<HashMap<String, String>> getTopic_EachTypeSgCount(String department, String sDate, String eDate, String company_code) {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();
			sb.append("\n").append("SELECT SUM(AI_MEDIA) AS AI_MEDIA                                          ");
			sb.append("\n").append("     , SUM(AI_BLOG) AS AI_BLOG                                            ");
			sb.append("\n").append("     , SUM(AI_CAFE) AS AI_CAFE                                            ");
			sb.append("\n").append("     , SUM(AI_INTELLECT) AS AI_INTELLECT                                  ");
			sb.append("\n").append("     , SUM(AI_COMMUNITY) AS AI_COMMUNITY                                  ");
			sb.append("\n").append("     , SUM(AI_PUBLIC) AS AI_PUBLIC                                        ");
			sb.append("\n").append("     , SUM(AI_TWITTER) AS AI_TWITTER                                      ");
			sb.append("\n").append("     , SUM(AI_FACEBOOK) AS AI_FACEBOOK                                    ");
			sb.append("\n").append("     , SUM(AI_INSTAGRAM) AS AI_INSTAGRAM                                  ");
			sb.append("\n").append("     , SUM(AI_YOUTUBE) AS AI_YOUTUBE                                      ");
			sb.append("\n").append("     , SUM(AI_OVERSEAS) AS AI_OVERSEAS                                    ");
			sb.append("\n").append("  FROM ANA_MAIN_DEPT"+department+"_COMPANY_TOPIC /*부서선택*/                              ");
			sb.append("\n").append(" WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' /*기간설정*/                 ");
			sb.append("\n").append("       AND AI_TYPE1 = "+topic_type1+"                                                                                                     ");
			sb.append("\n").append("       AND AI_TYPE2 = "+topic_type2+"                                                                                                     ");
			sb.append("\n").append("       AND AI_CODE1 = "+company_code+"                                                                                                    ");
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			//통계테이블이 필드별로 되어있어서, 아래와 같이 출력
			HashMap<String,String> item = new HashMap<String,String>();
			if(rs.next()) {
				item = new HashMap<String,String>();
				item.put("IC_NAME", "언론");
				item.put("CHANNEL", siteGroup_map.get("AI_MEDIA"));
				item.put("CNT", rs.getString("AI_MEDIA"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("IC_NAME", "블로그");
				item.put("CHANNEL", siteGroup_map.get("AI_BLOG"));
				item.put("CNT", rs.getString("AI_BLOG"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("IC_NAME", "카페");
				item.put("CHANNEL", siteGroup_map.get("AI_CAFE"));
				item.put("CNT", rs.getString("AI_CAFE"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("IC_NAME", "지식인");
				item.put("CHANNEL", siteGroup_map.get("AI_INTELLECT"));
				item.put("CNT", rs.getString("AI_INTELLECT"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("IC_NAME", "커뮤니티");
				item.put("CHANNEL", siteGroup_map.get("AI_COMMUNITY"));
				item.put("CNT", rs.getString("AI_COMMUNITY"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("IC_NAME", "트위터");
				item.put("CHANNEL", siteGroup_map.get("AI_TWITTER"));
				item.put("CNT", rs.getString("AI_TWITTER"));
				result.add(item);

				item = new HashMap<String,String>();
				item.put("IC_NAME", "페이스북");
				item.put("CHANNEL", siteGroup_map.get("AI_FACEBOOK"));
				item.put("CNT", rs.getString("AI_FACEBOOK"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("IC_NAME", "인스타");
				item.put("CHANNEL", siteGroup_map.get("AI_INSTAGRAM"));
				item.put("CNT", rs.getString("AI_INSTAGRAM"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("IC_NAME", "유튜브");
				item.put("CHANNEL", siteGroup_map.get("AI_YOUTUBE"));
				item.put("CNT", rs.getString("AI_YOUTUBE"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("IC_NAME", "해외사이트");
				item.put("CHANNEL", siteGroup_map.get("AI_OVERSEAS"));
				item.put("CNT", rs.getString("AI_OVERSEAS"));
				result.add(item);
				
				item = new HashMap<String,String>();			
				item.put("IC_NAME", "기관");
				item.put("CHANNEL", siteGroup_map.get("AI_PUBLIC"));
				item.put("CNT", rs.getString("AI_PUBLIC"));
				result.add(item);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
	
	public ArrayList<Object[]> getTopic_EachTypeSgCount_excel(String department, String sDate, String eDate, String company_code, int size) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();
			sb.append("\n").append("SELECT IFNULL(SUM(AI_MEDIA),0) AS AI_MEDIA                                          ");
			sb.append("\n").append("     , IFNULL(SUM(AI_BLOG),0) AS AI_BLOG                                            ");
			sb.append("\n").append("     , IFNULL(SUM(AI_CAFE),0) AS AI_CAFE                                            ");
			sb.append("\n").append("     , IFNULL(SUM(AI_INTELLECT),0) AS AI_INTELLECT                                  ");
			sb.append("\n").append("     , IFNULL(SUM(AI_COMMUNITY),0) AS AI_COMMUNITY                                  ");
			sb.append("\n").append("     , IFNULL(SUM(AI_PUBLIC),0) AS AI_PUBLIC                                        ");
			sb.append("\n").append("     , IFNULL(SUM(AI_TWITTER),0) AS AI_TWITTER                                      ");
			sb.append("\n").append("     , IFNULL(SUM(AI_FACEBOOK),0) AS AI_FACEBOOK                                    ");
			sb.append("\n").append("     , IFNULL(SUM(AI_INSTAGRAM),0) AS AI_INSTAGRAM                                  ");
			sb.append("\n").append("     , IFNULL(SUM(AI_YOUTUBE),0) AS AI_YOUTUBE                                      ");
			sb.append("\n").append("     , IFNULL(SUM(AI_OVERSEAS),0) AS AI_OVERSEAS                                    ");
			sb.append("\n").append("  FROM ANA_MAIN_DEPT"+department+"_COMPANY_TOPIC /*부서선택*/                              ");
			sb.append("\n").append(" WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' /*기간설정*/                 ");
			sb.append("\n").append("       AND AI_TYPE1 = "+topic_type1+"                                                                                                     ");
			sb.append("\n").append("       AND AI_TYPE2 = "+topic_type2+"                                                                                                     ");
			sb.append("\n").append("       AND AI_CODE1 = "+company_code+"                                                                                                    ");
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			//통계테이블이 필드별로 되어있어서, 아래와 같이 출력
			Object[] item = new Object[size];
			int idx = 0;
			if(rs.next()) {
				idx = 0;
				item = new Object[size];
				item[idx++] = "언론";
				item[idx++] = rs.getInt("AI_MEDIA");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "블로그";
				item[idx++] = rs.getInt("AI_BLOG");
				result.add(item);

				idx = 0;
				item = new Object[size];
				item[idx++] = "카페";
				item[idx++] = rs.getInt("AI_CAFE");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "지식인";
				item[idx++] = rs.getInt("AI_INTELLECT");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "커뮤니티";
				item[idx++] = rs.getInt("AI_COMMUNITY");
				result.add(item);
				
				idx = 0;
				item = new Object[size];	
				item[idx++] = "기관";
				item[idx++] = rs.getInt("AI_PUBLIC");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "트위터";
				item[idx++] = rs.getInt("AI_TWITTER");
				result.add(item);

				idx = 0;
				item = new Object[size];
				item[idx++] = "페이스북";
				item[idx++] = rs.getInt("AI_FACEBOOK");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "인스타";
				item[idx++] = rs.getInt("AI_INSTAGRAM");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "유튜브";
				item[idx++] = rs.getInt("AI_YOUTUBE");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "해외사이트";
				item[idx++] = rs.getInt("AI_OVERSEAS");
				result.add(item);
				
				
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
	
	public ArrayList<HashMap<String, String>> getTopic_EachTypeTrendCountBySiteGroup(String department, String sDate, String eDate, String company_code,  String type) {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();
			sb.append("\n").append("SELECT SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',1),':',-1)) AS POS_MEDIA                      ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',2),':',-1)) AS POS_BLOG                       ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',3),':',-1)) AS POS_CAFE                       ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',4),':',-1)) AS POS_INTELLECT                  ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',5),':',-1)) AS POS_COMMUNITY                  ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',6),':',-1)) AS POS_PUBLIC                     ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',7),':',-1)) AS POS_TWITTER                    ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',8),':',-1)) AS POS_FACEBOOK                   ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',9),':',-1)) AS POS_INSTAGRAM                  ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',10),':',-1)) AS POS_YOUTUBE                   ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',11),':',-1)) AS POS_OVERSEAS                  ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',12),':',-1)) AS NEG_MEDIA                     ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',13),':',-1)) AS NEG_BLOG                      ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',14),':',-1)) AS NEG_CAFE                      ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',15),':',-1)) AS NEG_INTELLECT                 ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',16),':',-1)) AS NEG_COMMUNITY                 ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',17),':',-1)) AS NEG_PUBLIC                    ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',18),':',-1)) AS NEG_TWITTER                   ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',19),':',-1)) AS NEG_FACEBOOK                  ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',20),':',-1)) AS NEG_INSTAGRAM                 ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',21),':',-1)) AS NEG_YOUTUBE                   ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',22),':',-1)) AS NEG_OVERSEAS                  ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',23),':',-1)) AS NEU_MEDIA                     ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',24),':',-1)) AS NEU_BLOG                      ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',25),':',-1)) AS NEU_CAFE                      ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',26),':',-1)) AS NEU_INTELLECT                 ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',27),':',-1)) AS NEU_COMMUNITY                 ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',28),':',-1)) AS NEU_PUBLIC                    ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',29),':',-1)) AS NEU_TWITTER                   ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',30),':',-1)) AS NEU_FACEBOOK                  ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',31),':',-1)) AS NEU_INSTAGRAM                 ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',32),':',-1)) AS NEU_YOUTUBE                   ");
			sb.append("\n").append("     , SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',33),':',-1)) AS NEU_OVERSEAS                  ");
			sb.append("\n").append("   FROM ANA_MAIN_DEPT"+department+"_COMPANY_TOPIC /*부서선택*/                                                            ");
			sb.append("\n").append("       WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' /*기간설정*/                                                          ");
			sb.append("\n").append("           AND AI_TYPE1 = "+topic_type1+"                                                                                    ");
			sb.append("\n").append("           AND AI_TYPE2 = "+topic_type2+"                                                                                    ");
			sb.append("\n").append("           AND AI_CODE1 = "+company_code+"                                                                                   ");

			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			//통계테이블이 필드별로 되어있어서, 아래와 같이 출력
			HashMap<String,String> item = new HashMap<String,String>();
			if(rs.next()) {
				item = new HashMap<String,String>();
				item.put("IC_NAME", "언론");
				item.put("CHANNEL", siteGroup_map.get("AI_MEDIA"));
				item.put("POS", rs.getString("POS_MEDIA"));
				item.put("NEG", rs.getString("NEG_MEDIA"));
				item.put("NEU", rs.getString("NEU_MEDIA"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("IC_NAME", "블로그");
				item.put("CHANNEL", siteGroup_map.get("AI_BLOG"));
				item.put("POS", rs.getString("POS_BLOG"));
				item.put("NEG", rs.getString("NEG_BLOG"));
				item.put("NEU", rs.getString("NEU_BLOG"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("IC_NAME", "카페");
				item.put("CHANNEL", siteGroup_map.get("AI_CAFE"));
				item.put("POS", rs.getString("POS_CAFE"));
				item.put("NEG", rs.getString("NEG_CAFE"));
				item.put("NEU", rs.getString("NEU_CAFE"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("IC_NAME", "지식인");
				item.put("CHANNEL", siteGroup_map.get("AI_INTELLECT"));
				item.put("POS", rs.getString("POS_INTELLECT"));
				item.put("NEG", rs.getString("NEG_INTELLECT"));
				item.put("NEU", rs.getString("NEU_INTELLECT"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("IC_NAME", "커뮤니티");
				item.put("CHANNEL", siteGroup_map.get("AI_COMMUNITY"));
				item.put("POS", rs.getString("POS_COMMUNITY"));
				item.put("NEG", rs.getString("NEG_COMMUNITY"));
				item.put("NEU", rs.getString("NEU_COMMUNITY"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("IC_NAME", "트위터");
				item.put("CHANNEL", siteGroup_map.get("AI_TWITTER"));
				item.put("POS", rs.getString("POS_TWITTER"));
				item.put("NEG", rs.getString("NEG_TWITTER"));
				item.put("NEU", rs.getString("NEU_TWITTER"));
				result.add(item);

				item = new HashMap<String,String>();
				item.put("IC_NAME", "페이스북");
				item.put("CHANNEL", siteGroup_map.get("AI_FACEBOOK"));
				item.put("POS", rs.getString("POS_FACEBOOK"));
				item.put("NEG", rs.getString("NEG_FACEBOOK"));
				item.put("NEU", rs.getString("NEU_FACEBOOK"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("IC_NAME", "인스타");
				item.put("CHANNEL", siteGroup_map.get("AI_INSTAGRAM"));
				item.put("POS", rs.getString("POS_INSTAGRAM"));
				item.put("NEG", rs.getString("NEG_INSTAGRAM"));
				item.put("NEU", rs.getString("NEU_INSTAGRAM"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("IC_NAME", "유튜브");
				item.put("CHANNEL", siteGroup_map.get("AI_YOUTUBE"));
				item.put("POS", rs.getString("POS_YOUTUBE"));
				item.put("NEG", rs.getString("NEG_YOUTUBE"));
				item.put("NEU", rs.getString("NEU_YOUTUBE"));
				result.add(item);
				
				item = new HashMap<String,String>();
				item.put("IC_NAME", "해외사이트");
				item.put("CHANNEL", siteGroup_map.get("AI_OVERSEAS"));
				item.put("POS", rs.getString("POS_OVERSEAS"));
				item.put("NEG", rs.getString("NEG_OVERSEAS"));
				item.put("NEU", rs.getString("NEU_OVERSEAS"));
				result.add(item);
				
				item = new HashMap<String,String>();			
				item.put("IC_NAME", "기관");
				item.put("CHANNEL", siteGroup_map.get("AI_PUBLIC"));
				item.put("POS", rs.getString("POS_PUBLIC"));
				item.put("NEG", rs.getString("NEG_PUBLIC"));
				item.put("NEU", rs.getString("NEU_PUBLIC"));
				result.add(item);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
	
	public ArrayList<Object[]> getTopic_EachTypeTrendCountBySiteGroup_excel(String department, String sDate, String eDate, String company_code,  String type, int size) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();
			sb.append("\n").append("SELECT IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',1),':',-1)),0) AS POS_MEDIA                      ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',2),':',-1)),0) AS POS_BLOG                       ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',3),':',-1)),0) AS POS_CAFE                       ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',4),':',-1)),0) AS POS_INTELLECT                  ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',5),':',-1)),0) AS POS_COMMUNITY                  ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',6),':',-1)),0) AS POS_PUBLIC                     ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',7),':',-1)),0) AS POS_TWITTER                    ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',8),':',-1)),0) AS POS_FACEBOOK                   ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',9),':',-1)),0) AS POS_INSTAGRAM                  ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',10),':',-1)),0) AS POS_YOUTUBE                   ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',11),':',-1)),0) AS POS_OVERSEAS                  ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',12),':',-1)),0) AS NEG_MEDIA                     ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',13),':',-1)),0) AS NEG_BLOG                      ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',14),':',-1)),0) AS NEG_CAFE                      ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',15),':',-1)),0) AS NEG_INTELLECT                 ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',16),':',-1)),0) AS NEG_COMMUNITY                 ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',17),':',-1)),0) AS NEG_PUBLIC                    ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',18),':',-1)),0) AS NEG_TWITTER                   ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',19),':',-1)),0) AS NEG_FACEBOOK                  ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',20),':',-1)),0) AS NEG_INSTAGRAM                 ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',21),':',-1)),0) AS NEG_YOUTUBE                   ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',22),':',-1)),0) AS NEG_OVERSEAS                  ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',23),':',-1)),0) AS NEU_MEDIA                     ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',24),':',-1)),0) AS NEU_BLOG                      ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',25),':',-1)),0) AS NEU_CAFE                      ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',26),':',-1)),0) AS NEU_INTELLECT                 ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',27),':',-1)),0) AS NEU_COMMUNITY                 ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',28),':',-1)),0) AS NEU_PUBLIC                    ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',29),':',-1)),0) AS NEU_TWITTER                   ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',30),':',-1)),0) AS NEU_FACEBOOK                  ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',31),':',-1)),0) AS NEU_INSTAGRAM                 ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',32),':',-1)),0) AS NEU_YOUTUBE                   ");
			sb.append("\n").append("     , IFNULL(SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',33),':',-1)),0) AS NEU_OVERSEAS                  ");
			sb.append("\n").append("   FROM ANA_MAIN_DEPT"+department+"_COMPANY_TOPIC /*부서선택*/                                                            ");
			sb.append("\n").append("       WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' /*기간설정*/                                                          ");
			sb.append("\n").append("           AND AI_TYPE1 = "+topic_type1+"                                                                                    ");
			sb.append("\n").append("           AND AI_TYPE2 = "+topic_type2+"                                                                                    ");
			sb.append("\n").append("           AND AI_CODE1 = "+company_code+"                                                                                   ");
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			//통계테이블이 필드별로 되어있어서, 아래와 같이 출력
			
			Object[] item = new Object[size];
			int idx = 0;
			if(rs.next()) {
				idx = 0;
				item = new Object[size];
				item[idx++] = "언론";
				item[idx++] = rs.getInt("POS_MEDIA");
				item[idx++] = rs.getInt("NEG_MEDIA");
				item[idx++] = rs.getInt("NEU_MEDIA");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "블로그";
				item[idx++] = rs.getInt("POS_BLOG");
				item[idx++] = rs.getInt("NEG_BLOG");
				item[idx++] = rs.getInt("NEU_BLOG");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "카페";
				item[idx++] = rs.getInt("POS_CAFE");
				item[idx++] = rs.getInt("NEG_CAFE");
				item[idx++] = rs.getInt("NEU_CAFE");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "지식인";
				item[idx++] = rs.getInt("POS_INTELLECT");
				item[idx++] = rs.getInt("NEG_INTELLECT");
				item[idx++] = rs.getInt("NEU_INTELLECT");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "커뮤니티";
				item[idx++] = rs.getInt("POS_COMMUNITY");
				item[idx++] = rs.getInt("NEG_COMMUNITY");
				item[idx++] = rs.getInt("NEU_COMMUNITY");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "기관";
				item[idx++] = rs.getInt("POS_PUBLIC");
				item[idx++] = rs.getInt("NEG_PUBLIC");
				item[idx++] = rs.getInt("NEU_PUBLIC");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "트위터";
				item[idx++] = rs.getInt("POS_TWITTER");
				item[idx++] = rs.getInt("NEG_TWITTER");
				item[idx++] = rs.getInt("NEU_TWITTER");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "페이스북";
				item[idx++] = rs.getInt("POS_FACEBOOK");
				item[idx++] = rs.getInt("NEG_FACEBOOK");
				item[idx++] = rs.getInt("NEU_FACEBOOK");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "인스타";
				item[idx++] = rs.getInt("POS_INSTAGRAM");
				item[idx++] = rs.getInt("NEG_INSTAGRAM");
				item[idx++] = rs.getInt("NEU_INSTAGRAM");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "유튜브";
				item[idx++] = rs.getInt("POS_YOUTUBE");
				item[idx++] = rs.getInt("NEG_YOUTUBE");
				item[idx++] = rs.getInt("NEU_YOUTUBE");
				result.add(item);
				
				idx = 0;
				item = new Object[size];
				item[idx++] = "해외사이트";
				item[idx++] = rs.getInt("POS_OVERSEAS");
				item[idx++] = rs.getInt("NEG_OVERSEAS");
				item[idx++] = rs.getInt("NEU_OVERSEAS");
				result.add(item);
				
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
	
	public ArrayList<HashMap<String, String>> getPressReleaseChannel(String sDate, String eDate, String ic_code) {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			if(!ic_code.equals("")) {
				sb = new StringBuffer();
				sb.append("\n").append("SELECT  SG.SG_NAME AS CHANNEL                                                                                                               ");
				sb.append("\n").append("     , SG.SG_SEQ                                                                                         ");
				sb.append("\n").append("     , COUNT(*) AS CNT #전체                                                                                          ");
				sb.append("\n").append("     , SUM(A.ID_SENTI = 1) AS 'POS'  #긍                                                                                        ");
				sb.append("\n").append("     , SUM(A.ID_SENTI = 2) AS 'NEG'  #부                                                                                     ");
				sb.append("\n").append("     , SUM(A.ID_SENTI = 3) AS 'NEU'  #중                                                                                    ");
				sb.append("\n").append("  FROM ISSUE_DATA A	                                                                                                                      ");
				sb.append("\n").append("      , ISSUE_DATA_CODE B1	           ");
				sb.append("\n").append("      , SITE_GROUP SG                                                                                             ");
				sb.append("\n").append("  WHERE A.ID_SEQ = B1.ID_SEQ                                                                                    ");
				sb.append("\n").append("    AND A.SG_SEQ = SG.SG_SEQ                                                                                 ");
				sb.append("\n").append("    AND SG.USEYN = 'Y'                                                                                                  ");
				sb.append("\n").append("    AND A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'                                                                                                      ");
				sb.append("\n").append("    AND A.ID_USEYN = 'Y'                                                                                       ");
				sb.append("\n").append("    AND B1.IC_TYPE = 7                                                                                      ");
				sb.append("\n").append("    AND B1.IC_CODE = "+ic_code+"   #선택된 보도자료명 IC_CODE                                                                                      ");
				sb.append("\n").append("  GROUP BY A.SG_SEQ                                                                                  ");
				sb.append("\n").append("  ORDER BY SG.SG_ORDER                                                    ");
	
				rs = stmt.executeQuery(sb.toString());
				System.out.println(sb.toString());
				HashMap<String,String> item = new HashMap<String,String>();
				while(rs.next()) {
					item = new HashMap<String,String>();
					item.put("CHANNEL", rs.getString("CHANNEL"));
					item.put("SG_SEQ", rs.getString("SG_SEQ"));
					item.put("POS", rs.getString("POS"));
					item.put("NEG", rs.getString("NEG"));
					item.put("NEU", rs.getString("NEU"));
					item.put("CNT", rs.getString("CNT"));
					result.add(item);
				}
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	

	public ArrayList<Object[]> getPressReleaseChannel_excel(String sDate, String eDate, String ic_code,  int size) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			if(!ic_code.equals("")) {
				sb = new StringBuffer();
				sb.append("\n").append("SELECT  SG.SG_NAME AS CHANNEL                                                                                                               ");
				sb.append("\n").append("     , SG.SG_SEQ                                                                                         ");
				sb.append("\n").append("     , COUNT(*) AS CNT #전체                                                                                          ");
				sb.append("\n").append("     , SUM(A.ID_SENTI = 1) AS 'POS'  #긍                                                                                        ");
				sb.append("\n").append("     , SUM(A.ID_SENTI = 2) AS 'NEG'  #부                                                                                     ");
				sb.append("\n").append("     , SUM(A.ID_SENTI = 3) AS 'NEU'  #중                                                                                    ");
				sb.append("\n").append("  FROM ISSUE_DATA A	                                                                                                                      ");
				sb.append("\n").append("      , ISSUE_DATA_CODE B1	           ");
				sb.append("\n").append("      , SITE_GROUP SG                                                                                             ");
				sb.append("\n").append("  WHERE A.ID_SEQ = B1.ID_SEQ                                                                                    ");
				sb.append("\n").append("    AND A.SG_SEQ = SG.SG_SEQ                                                                                 ");
				sb.append("\n").append("    AND SG.USEYN = 'Y'                                                                                                  ");
				sb.append("\n").append("    AND A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'                                                                                                   ");
				sb.append("\n").append("    AND A.ID_USEYN = 'Y'                                                                                       ");
				sb.append("\n").append("    AND B1.IC_TYPE = 7                                                                                      ");
				sb.append("\n").append("    AND B1.IC_CODE = "+ic_code+"   #선택된 보도자료명 IC_CODE                                                                                      ");
				sb.append("\n").append("  GROUP BY A.SG_SEQ                                                                                  ");
				sb.append("\n").append("  ORDER BY SG.SG_ORDER                                                    ");
	
				rs = stmt.executeQuery(sb.toString());
				System.out.println(sb.toString());
				Object[] item = new Object[size];
				int idx = 0;
				while(rs.next()) {
					idx = 0;
					item = new Object[size];
					item[idx++] = rs.getString("CHANNEL");
					item[idx++] = rs.getInt("POS");
					item[idx++] = rs.getInt("NEG");
					item[idx++] = rs.getInt("NEU");
					item[idx++] = rs.getInt("CNT");
					result.add(item);
					
				}
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
	public ArrayList<HashMap<String, String>> getPressReleaseAmount(String sDate, String ic_code, String dayDiff) {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			if(!ic_code.equals("")) {
				sb = new StringBuffer();
				sb.append("\n").append("SELECT AA.MD_DATE AS `MD_DATE`                                                                                                               ");
				sb.append("\n").append("     , DATE_FORMAT(AA.MD_DATE, '%m-%d') AS `SHORT_DATE`                                                                                        ");
				sb.append("\n").append("     , IFNULL(BB.TOTAL_CNT,0) AS `TOTAL_CNT` #전체                                                                                        ");
				sb.append("\n").append("     , IFNULL(BB.CODE_1,0) AS `POS` #긍정                                                                                      ");
				sb.append("\n").append("     , IFNULL(BB.CODE_2,0) AS `NEG` #부정                                                                                    ");
				sb.append("\n").append("     , IFNULL(BB.CODE_3,0) AS `NEU` #중립                                                                                     ");
				sb.append("\n").append("  FROM (													  ");
				sb.append("\n").append("  		SELECT DATE_FORMAT(DATE_ADD('"+sDate+" 00:00:00', INTERVAL @ROW:=@ROW+1 DAY ),'%Y-%m-%d') AS MD_DATE , @ROW           ");
				sb.append("\n").append("      	  FROM KEYWORD A, (SELECT @ROW:=-1 )R                                                                                             ");
				sb.append("\n").append("  		 LIMIT "+dayDiff+" ) AA #검색시작일자 기준으로 현재까지                                                                                  ");
				sb.append("\n").append("    	LEFT OUTER JOIN (                                                                              ");
				sb.append("\n").append("    	SELECT DATE_FORMAT(ID.MD_DATE,'%Y-%m-%d') AS `MD_DATE`                                                                                                ");
				sb.append("\n").append("    	 	 , COUNT(*) AS `TOTAL_CNT`                                                                                                     ");
				sb.append("\n").append("    		 , SUM(IF(ID.ID_SENTI = 1,1,0)) AS `CODE_1`                                                                                      ");
				sb.append("\n").append("    		 , SUM(IF(ID.ID_SENTI = 2,1,0)) AS `CODE_2`                                                                                     ");
				sb.append("\n").append("    		 , SUM(IF(ID.ID_SENTI = 3,1,0)) AS `CODE_3`                                                                                     ");
				sb.append("\n").append("  		  FROM ISSUE_DATA ID                                                                                 ");
				sb.append("\n").append("     	 INNER JOIN ISSUE_DATA_CODE IDC ON (                                                    ");
				sb.append("\n").append("     		   ID.ID_SEQ = IDC.ID_SEQ AND IDC.IC_TYPE = 7                                                     ");
				sb.append("\n").append("     		   AND IDC.IC_CODE = "+ic_code+" #선택된 보도자료명 IC_CODE                                                    ");
				sb.append("\n").append("     	       )                                                    ");
				sb.append("\n").append("     	 WHERE ID.MD_DATE > '"+sDate+" 00:00:00'                                                   ");
				sb.append("\n").append("     	 GROUP BY DATE_FORMAT(ID.MD_DATE,'%Y-%m-%d')                                                   ");
				sb.append("\n").append("     	) BB ON (BB.MD_DATE = AA.MD_DATE)                                                  ");
				sb.append("\n").append(" ORDER BY AA.MD_DATE ASC                                                   ");
	
				rs = stmt.executeQuery(sb.toString());
				System.out.println(sb.toString());
				HashMap<String,String> item = new HashMap<String,String>();
				while(rs.next()) {
					item = new HashMap<String,String>();
					item.put("MD_DATE", rs.getString("MD_DATE"));
					item.put("POS", rs.getString("POS"));
					item.put("NEG", rs.getString("NEG"));
					item.put("NEU", rs.getString("NEU"));
					item.put("TOTAL_CNT", rs.getString("TOTAL_CNT"));
					result.add(item);
				}
			
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
	
	public ArrayList<Object[]> getPressReleaseAmount_excel(String sDate, String ic_code, String dayDiff, int size) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			if(!ic_code.equals("")) {
				sb = new StringBuffer();
				sb.append("\n").append("SELECT AA.MD_DATE AS `MD_DATE`                                                                                                               ");
				sb.append("\n").append("     , DATE_FORMAT(AA.MD_DATE, '%m-%d') AS `SHORT_DATE`                                                                                        ");
				sb.append("\n").append("     , IFNULL(BB.TOTAL_CNT,0) AS `TOTAL_CNT` #전체                                                                                        ");
				sb.append("\n").append("     , IFNULL(BB.CODE_1,0) AS `POS` #긍정                                                                                      ");
				sb.append("\n").append("     , IFNULL(BB.CODE_2,0) AS `NEG` #부정                                                                                    ");
				sb.append("\n").append("     , IFNULL(BB.CODE_3,0) AS `NEU` #중립                                                                                     ");
				sb.append("\n").append("  FROM (													  ");
				sb.append("\n").append("  		SELECT DATE_FORMAT(DATE_ADD('"+sDate+" 00:00:00', INTERVAL @ROW:=@ROW+1 DAY ),'%Y-%m-%d') AS MD_DATE , @ROW           ");
				sb.append("\n").append("      	  FROM KEYWORD A, (SELECT @ROW:=-1 )R                                                                                             ");
				sb.append("\n").append("  		 LIMIT "+dayDiff+" ) AA #검색시작일자 기준으로 현재까지                                                                                  ");
				sb.append("\n").append("    	LEFT OUTER JOIN (                                                                              ");
				sb.append("\n").append("    	SELECT DATE_FORMAT(ID.MD_DATE,'%Y-%m-%d') AS `MD_DATE`                                                                                                ");
				sb.append("\n").append("    	 	 , COUNT(*) AS `TOTAL_CNT`                                                                                                     ");
				sb.append("\n").append("    		 , SUM(IF(ID.ID_SENTI = 1,1,0)) AS `CODE_1`                                                                                      ");
				sb.append("\n").append("    		 , SUM(IF(ID.ID_SENTI = 2,1,0)) AS `CODE_2`                                                                                     ");
				sb.append("\n").append("    		 , SUM(IF(ID.ID_SENTI = 3,1,0)) AS `CODE_3`                                                                                     ");
				sb.append("\n").append("  		  FROM ISSUE_DATA ID                                                                                 ");
				sb.append("\n").append("     	 INNER JOIN ISSUE_DATA_CODE IDC ON (                                                    ");
				sb.append("\n").append("     		   ID.ID_SEQ = IDC.ID_SEQ AND IDC.IC_TYPE = 7                                                     ");
				sb.append("\n").append("     		   AND IDC.IC_CODE = "+ic_code+" #선택된 보도자료명 IC_CODE                                                    ");
				sb.append("\n").append("     	       )                                                    ");
				sb.append("\n").append("     	 WHERE ID.MD_DATE > '"+sDate+" 00:00:00'                                                   ");
				sb.append("\n").append("     	 GROUP BY DATE_FORMAT(ID.MD_DATE,'%Y-%m-%d')                                                   ");
				sb.append("\n").append("     	) BB ON (BB.MD_DATE = AA.MD_DATE)                                                  ");
				sb.append("\n").append(" ORDER BY AA.MD_DATE ASC                                                   ");
	
				rs = stmt.executeQuery(sb.toString());
				System.out.println(sb.toString());
				Object[] item = new Object[size];
				int idx = 0;
				while(rs.next()) {
					idx = 0;
					item = new Object[size];
					item[idx++] = rs.getString("MD_DATE");
					item[idx++] = rs.getInt("POS");
					item[idx++] = rs.getInt("NEG");
					item[idx++] = rs.getInt("NEU");
					item[idx++] = rs.getInt("TOTAL_CNT");
					result.add(item);
					
				}
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
	public ArrayList<HashMap<String,String>> getTopic_EachTypeCompClaimCount(String department, String sDate, String eDate, String company_code, String type, String code) {
		ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();
			sb.append("\n").append("SELECT A.IC_CODE                                                                                                        ");
			sb.append("\n").append("     , A.IC_NAME                                                                                                        ");
			sb.append("\n").append("     , IFNULL(B.TOTAL,0) AS TOTAL                                                                                       ");
			sb.append("\n").append("     , IFNULL(B.CJ,0) AS CJ                                                                                             ");
			sb.append("\n").append("     , IFNULL(B.DAESANG,0) AS DAESANG                                                                                   ");
			sb.append("\n").append("     , IFNULL(B.PULMUONE,0) AS PULMUONE                                                                                 ");
			sb.append("\n").append("     , IFNULL(B.OTTOGI,0) AS OTTOGI                                                                                     ");
			sb.append("\n").append("     , IFNULL(B.DONGWON,0) AS DONGWON                                                                                   ");
			sb.append("\n").append("  FROM (                                                                                                                ");
			sb.append("\n").append("        SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+type+" AND IC_CODE IN ("+code+")                        ");
			sb.append("\n").append("       )A LEFT OUTER JOIN (                                                                                             ");
			sb.append("\n").append("          SELECT A.AI_CODE2                                                                                             ");
			sb.append("\n").append("               , SUM(AI_TOTAL) AS TOTAL                                                                                 ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 1 , A.AI_TOTAL, 0 )) AS CJ                                                         ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 2 , A.AI_TOTAL, 0 )) AS DAESANG                                                    ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 3 , A.AI_TOTAL, 0 )) AS PULMUONE                                                   ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 4 , A.AI_TOTAL, 0 )) AS OTTOGI                                                     ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 5 , A.AI_TOTAL, 0 )) AS DONGWON                                                    ");
			sb.append("\n").append("            FROM ANA_MAIN_DEPT"+department+"_COMPANY_VOCD A                                                                          ");
			sb.append("\n").append("           WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' /*검색기간*/                                                     ");
			sb.append("\n").append("             AND A.AI_TYPE1 = "+topic_type1+"                                                                                          ");
			sb.append("\n").append("             AND A.AI_CODE1 IN ("+company_code+")                                                                              ");
			sb.append("\n").append("             AND A.AI_TYPE2 = "+type+"                                                                                         ");
			sb.append("\n").append("             AND A.AI_CODE2 IN ("+code+")                                                                          ");
			sb.append("\n").append("           GROUP BY A.AI_CODE2                                                                                          ");
			sb.append("\n").append("       )B ON A.IC_CODE = B.AI_CODE2                                                                                     ");
			sb.append("\n").append(" ORDER BY IC_CODE                                                                                                       ");
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			
			HashMap<String,String> item1 = new HashMap<String,String>();
			HashMap<String,String> item2 = new HashMap<String,String>();
			HashMap<String,String> item3 = new HashMap<String,String>();
			HashMap<String,String> item4 = new HashMap<String,String>();
			HashMap<String,String> item5 = new HashMap<String,String>();
			
			item1.put("IC_NAME", "CJ");
			item2.put("IC_NAME", "대상");
			item3.put("IC_NAME", "풀무원");
			item4.put("IC_NAME", "오뚜기");
			item5.put("IC_NAME", "동원");
			
			while(rs.next()) {
				
				
				item1.put("TYPE"+rs.getString("IC_CODE"), rs.getString("CJ"));
				item1.put("COMPANY", company_map.get("CJ"));
				
				item2.put("TYPE"+rs.getString("IC_CODE"), rs.getString("DAESANG"));
				item2.put("COMPANY", company_map.get("DAESANG"));
				
				item3.put("TYPE"+rs.getString("IC_CODE"), rs.getString("PULMUONE"));
				item3.put("COMPANY", company_map.get("PULMUONE"));
				
				item4.put("TYPE"+rs.getString("IC_CODE"), rs.getString("OTTOGI"));
				item4.put("COMPANY", company_map.get("OTTOGI"));
				
				item5.put("TYPE"+rs.getString("IC_CODE"), rs.getString("DONGWON"));
				item5.put("COMPANY", company_map.get("DONGWON"));
				
			}
					
			result.add(item1);
			result.add(item2);
			result.add(item3);
			result.add(item4);
			result.add(item5);
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
	public ArrayList<Object[]> getTopic_EachTypeCompClaimCount_excel(String department, String sDate, String eDate, String company_code, String type, String code, int size) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();
			sb.append("\n").append("SELECT A.IC_CODE                                                                                                        ");
			sb.append("\n").append("     , A.IC_NAME                                                                                                        ");
			sb.append("\n").append("     , IFNULL(B.TOTAL,0) AS TOTAL                                                                                       ");
			sb.append("\n").append("     , IFNULL(B.CJ,0) AS CJ                                                                                             ");
			sb.append("\n").append("     , IFNULL(B.DAESANG,0) AS DAESANG                                                                                   ");
			sb.append("\n").append("     , IFNULL(B.PULMUONE,0) AS PULMUONE                                                                                 ");
			sb.append("\n").append("     , IFNULL(B.OTTOGI,0) AS OTTOGI                                                                                     ");
			sb.append("\n").append("     , IFNULL(B.DONGWON,0) AS DONGWON                                                                                   ");			
			sb.append("\n").append("  FROM (                                                                                                                ");
			sb.append("\n").append("        SELECT IC_TYPE, IC_CODE, IC_NAME, IC_PCODE, IC_PTYPE FROM ISSUE_CODE WHERE IC_TYPE = "+type+" AND IC_CODE IN ("+code+")                        ");
			sb.append("\n").append("       )A LEFT OUTER JOIN (                                                                                             ");
			sb.append("\n").append("          SELECT A.AI_CODE1 AS AI_CODE1                                                                                             ");
			sb.append("\n").append("               , A.AI_TOTAL AS AI_TOTAL                                                                                            ");
			sb.append("\n").append("               , A.AI_CODE2                                                                                             ");
			sb.append("\n").append("               , SUM(AI_TOTAL) AS TOTAL                                                                                 ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 1 , A.AI_TOTAL, 0 )) AS CJ                                                         ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 2 , A.AI_TOTAL, 0 )) AS DAESANG                                                    ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 3 , A.AI_TOTAL, 0 )) AS PULMUONE                                                   ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 4 , A.AI_TOTAL, 0 )) AS OTTOGI                                                     ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 5 , A.AI_TOTAL, 0 )) AS DONGWON                                                    ");
			sb.append("\n").append("            FROM ANA_MAIN_DEPT"+department+"_COMPANY_VOCD A                                                                          ");
			sb.append("\n").append("           WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' /*검색기간*/                                                     ");
			sb.append("\n").append("             AND A.AI_TYPE1 = "+topic_type1+"                                                                                          ");
			sb.append("\n").append("             AND A.AI_CODE1 IN ("+company_code+")                                                                              ");
			sb.append("\n").append("             AND A.AI_TYPE2 = "+type+"                                                                                         ");
			sb.append("\n").append("             AND A.AI_CODE2 IN ("+code+")                                                                          ");
			sb.append("\n").append("           GROUP BY A.AI_CODE2                                                                                          ");
			sb.append("\n").append("       )B ON A.IC_CODE = B.AI_CODE2                                                                                     ");
			sb.append("\n").append(" ORDER BY IC_CODE                                                                                                       ");
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			Object[] item = new Object[size];
			int idx = 0;
			
			while(rs.next()) {
				idx = 0;
				item = new Object[size];
				item[idx++] = rs.getString("IC_NAME");
				item[idx++] = rs.getInt("CJ");
				item[idx++] = rs.getInt("DAESANG");
				item[idx++] = rs.getInt("PULMUONE");
				item[idx++] = rs.getInt("OTTOGI");
				item[idx++] = rs.getInt("DONGWON");
				result.add(item);
			}

			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
	public ArrayList<HashMap<String,String>> getTopic_EachTypeCompChannelCount(String department, String sDate, String eDate, String company_code, String DayDiff) {
		ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();
			sb.append("\n").append("SELECT A.IC_CODE                                                                                                             ");
			sb.append("\n").append("     , A.IC_NAME                                                                                                             ");
			sb.append("\n").append("     , IFNULL(B.AI_TOTAL,0) AS AI_TOTAL                                                                                      ");
			sb.append("\n").append("     , IFNULL(B.AI_MEDIA,0) AS AI_MEDIA                                                                                      ");
			sb.append("\n").append("     , IFNULL(B.AI_BLOG,0) AS AI_BLOG                                                                                        ");
			sb.append("\n").append("     , IFNULL(B.AI_CAFE,0) AS AI_CAFE                                                                                        ");
			sb.append("\n").append("     , IFNULL(B.AI_INTELLECT,0) AS AI_INTELLECT                                                                              ");
			sb.append("\n").append("     , IFNULL(B.AI_COMMUNITY,0) AS AI_COMMUNITY                                                                              ");
			sb.append("\n").append("     , IFNULL(B.AI_PUBLIC,0) AS AI_PUBLIC                                                                                    ");
			sb.append("\n").append("     , IFNULL(B.AI_TWITTER,0) AS AI_TWITTER                                                                                  ");
			sb.append("\n").append("     , IFNULL(B.AI_FACEBOOK,0) AS AI_FACEBOOK                                                                                ");
			sb.append("\n").append("     , IFNULL(B.AI_INSTAGRAM,0) AS AI_INSTAGRAM                                                                              ");
			sb.append("\n").append("     , IFNULL(B.AI_YOUTUBE,0) AS AI_YOUTUBE                                                                                  ");
			sb.append("\n").append("     , IFNULL(B.AI_OVERSEAS,0) AS AI_OVERSEAS                                                                                ");
			sb.append("\n").append("  FROM (                                                                                                                     ");
			sb.append("\n").append("        SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+topic_type1+" AND IC_CODE IN ("+company_code+")                                 ");
			sb.append("\n").append("       )A LEFT OUTER JOIN (                                                                                                  ");
			sb.append("\n").append("          SELECT A.AI_CODE1                                                                                                  ");
			sb.append("\n").append("               , SUM(AI_TOTAL) AS AI_TOTAL                                                                                   ");
			sb.append("\n").append("               , SUM(AI_MEDIA) AS AI_MEDIA                                                                                   ");
			sb.append("\n").append("               , SUM(AI_BLOG) AS AI_BLOG                                                                                     ");
			sb.append("\n").append("               , SUM(AI_CAFE) AS AI_CAFE                                                                                     ");
			sb.append("\n").append("               , SUM(AI_INTELLECT) AS AI_INTELLECT                                                                           ");
			sb.append("\n").append("               , SUM(AI_COMMUNITY) AS AI_COMMUNITY                                                                           ");
			sb.append("\n").append("               , SUM(AI_PUBLIC) AS AI_PUBLIC                                                                                 ");
			sb.append("\n").append("               , SUM(AI_TWITTER) AS AI_TWITTER                                                                               ");
			sb.append("\n").append("               , SUM(AI_FACEBOOK) AS AI_FACEBOOK                                                                             ");
			sb.append("\n").append("               , SUM(AI_INSTAGRAM) AS AI_INSTAGRAM                                                                           ");
			sb.append("\n").append("               , SUM(AI_YOUTUBE) AS AI_YOUTUBE                                                                               ");
			sb.append("\n").append("               , SUM(AI_OVERSEAS) AS AI_OVERSEAS                                                                             ");
			sb.append("\n").append("            FROM ANA_MAIN_DEPT"+department+"_COMPANY_VOCM A                                                                               ");
			sb.append("\n").append("           WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' /*검색기간*/                                                         ");
			sb.append("\n").append("             AND A.AI_TYPE1 = "+topic_type1+"                                                                                              ");
			sb.append("\n").append("             AND A.AI_CODE1 IN ("+company_code+")                                                                                   ");
			sb.append("\n").append("           GROUP BY A.AI_CODE1                                                                                               ");
			sb.append("\n").append("       )B ON A.IC_CODE = B.AI_CODE1                                                                                          ");
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			
			HashMap<String,String> item1 = new HashMap<String,String>();
			HashMap<String,String> item2 = new HashMap<String,String>();
			HashMap<String,String> item3 = new HashMap<String,String>();
			HashMap<String,String> item4 = new HashMap<String,String>();
			HashMap<String,String> item5 = new HashMap<String,String>();
			HashMap<String,String> item6 = new HashMap<String,String>();
			HashMap<String,String> item7 = new HashMap<String,String>();
			HashMap<String,String> item8 = new HashMap<String,String>();
			HashMap<String,String> item9 = new HashMap<String,String>();
			HashMap<String,String> item10 = new HashMap<String,String>();
			HashMap<String,String> item11 = new HashMap<String,String>();
			
			item1.put("IC_NAME", "언론");
			item2.put("IC_NAME", "블로그");
			item3.put("IC_NAME", "카페");
			item4.put("IC_NAME", "지식인");
			item5.put("IC_NAME", "커뮤니티");
			item6.put("IC_NAME", "기관");
			item7.put("IC_NAME", "트위터");
			item8.put("IC_NAME", "페이스북");
			item9.put("IC_NAME", "인스타");
			item10.put("IC_NAME", "유튜브");
			item11.put("IC_NAME", "해외");
			
			
			while(rs.next()) {
				
				
				item1.put("CHANNEL", siteGroup_map.get("AI_MEDIA"));
				item1.put("TYPE"+rs.getString("IC_CODE"), rs.getString("AI_MEDIA"));
				
				item2.put("CHANNEL", siteGroup_map.get("AI_BLOG"));
				item2.put("TYPE"+rs.getString("IC_CODE"), rs.getString("AI_BLOG"));
				
				item3.put("CHANNEL", siteGroup_map.get("AI_CAFE"));
				item3.put("TYPE"+rs.getString("IC_CODE"), rs.getString("AI_CAFE"));
				
				item4.put("CHANNEL", siteGroup_map.get("AI_INTELLECT"));
				item4.put("TYPE"+rs.getString("IC_CODE"), rs.getString("AI_INTELLECT"));
				
				item5.put("CHANNEL", siteGroup_map.get("AI_COMMUNITY"));
				item5.put("TYPE"+rs.getString("IC_CODE"), rs.getString("AI_COMMUNITY"));
				
				item6.put("CHANNEL", siteGroup_map.get("AI_PUBLIC"));
				item6.put("TYPE"+rs.getString("IC_CODE"), rs.getString("AI_PUBLIC"));
				
				item7.put("CHANNEL", siteGroup_map.get("AI_TWITTER"));
				item7.put("TYPE"+rs.getString("IC_CODE"), rs.getString("AI_TWITTER"));
				
				item8.put("CHANNEL", siteGroup_map.get("AI_FACEBOOK"));
				item8.put("TYPE"+rs.getString("IC_CODE"), rs.getString("AI_FACEBOOK"));
				
				item9.put("CHANNEL", siteGroup_map.get("AI_INSTAGRAM"));
				item9.put("TYPE"+rs.getString("IC_CODE"), rs.getString("AI_INSTAGRAM"));
				
				item10.put("CHANNEL", siteGroup_map.get("AI_YOUTUBE"));
				item10.put("TYPE"+rs.getString("IC_CODE"), rs.getString("AI_YOUTUBE"));
				
				item11.put("CHANNEL", siteGroup_map.get("AI_OVERSEAS"));
				item11.put("TYPE"+rs.getString("IC_CODE"), rs.getString("AI_OVERSEAS"));
				
			}
					
			result.add(item1);
			result.add(item2);
			result.add(item3);
			result.add(item4);
			result.add(item5);
			result.add(item6);
			result.add(item7);
			result.add(item8);
			result.add(item9);
			result.add(item10);
			result.add(item11);
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
	
	public ArrayList<Object[]> getTopic_EachTypeCompChannelCount_excel(String department, String sDate, String eDate, String company_code, int size) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();
			sb.append("\n").append("SELECT A.IC_CODE                                                                                                             ");
			sb.append("\n").append("     , A.IC_NAME                                                                                                             ");
			sb.append("\n").append("     , IFNULL(B.AI_TOTAL,0) AS AI_TOTAL                                                                                      ");
			sb.append("\n").append("     , IFNULL(B.AI_MEDIA,0) AS AI_MEDIA                                                                                      ");
			sb.append("\n").append("     , IFNULL(B.AI_BLOG,0) AS AI_BLOG                                                                                        ");
			sb.append("\n").append("     , IFNULL(B.AI_CAFE,0) AS AI_CAFE                                                                                        ");
			sb.append("\n").append("     , IFNULL(B.AI_INTELLECT,0) AS AI_INTELLECT                                                                              ");
			sb.append("\n").append("     , IFNULL(B.AI_COMMUNITY,0) AS AI_COMMUNITY                                                                              ");
			sb.append("\n").append("     , IFNULL(B.AI_PUBLIC,0) AS AI_PUBLIC                                                                                    ");
			sb.append("\n").append("     , IFNULL(B.AI_TWITTER,0) AS AI_TWITTER                                                                                  ");
			sb.append("\n").append("     , IFNULL(B.AI_FACEBOOK,0) AS AI_FACEBOOK                                                                                ");
			sb.append("\n").append("     , IFNULL(B.AI_INSTAGRAM,0) AS AI_INSTAGRAM                                                                              ");
			sb.append("\n").append("     , IFNULL(B.AI_YOUTUBE,0) AS AI_YOUTUBE                                                                                  ");
			sb.append("\n").append("     , IFNULL(B.AI_OVERSEAS,0) AS AI_OVERSEAS                                                                                ");
//			sb.append("\n").append("     , IFNULL(B.CJ_MEDIA,0) AS CJ_MEDIA                                                           "); 
//			sb.append("\n").append("     , IFNULL(B.CJ_BLOG,0) AS CJ_BLOG                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.CJ_CAFE,0) AS CJ_CAFE                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.CJ_INTELLECT,0) AS CJ_INTELLECT                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.CJ_COMMUNITY,0) AS CJ_COMMUNITY                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.CJ_PUBLIC,0) AS CJ_PUBLIC                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.CJ_TWITTER,0) AS CJ_TWITTER                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.CJ_FACEBOOK,0) AS CJ_FACEBOOK                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.CJ_INSTAGRAM,0) AS CJ_INSTAGRAM                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.CJ_YOUTUBE,0) AS CJ_YOUTUBE                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.CJ_OVERSEAS,0) AS CJ_OVERSEAS                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DAESANG_MEDIA,0) AS DAESANG_MEDIA                                                           "); 
//			sb.append("\n").append("     , IFNULL(B.DAESANG_BLOG,0) AS DAESANG_BLOG                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DAESANG_CAFE,0) AS DAESANG_CAFE                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DAESANG_INTELLECT,0) AS DAESANG_INTELLECT                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DAESANG_COMMUNITY,0) AS DAESANG_COMMUNITY                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DAESANG_PUBLIC,0) AS DAESANG_PUBLIC                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DAESANG_TWITTER,0) AS DAESANG_TWITTER                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DAESANG_FACEBOOK,0) AS DAESANG_FACEBOOK                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DAESANG_INSTAGRAM,0) AS DAESANG_INSTAGRAM                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DAESANG_YOUTUBE,0) AS DAESANG_YOUTUBE                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DAESANG_OVERSEAS,0) AS DAESANG_OVERSEAS                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.PULMUONE_MEDIA,0) AS PULMUONE_MEDIA                                                           "); 
//			sb.append("\n").append("     , IFNULL(B.PULMUONE_BLOG,0) AS PULMUONE_BLOG                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.PULMUONE_CAFE,0) AS PULMUONE_CAFE                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.PULMUONE_INTELLECT,0) AS PULMUONE_INTELLECT                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.PULMUONE_COMMUNITY,0) AS PULMUONE_COMMUNITY                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.PULMUONE_PUBLIC,0) AS PULMUONE_PUBLIC                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.PULMUONE_TWITTER,0) AS PULMUONE_TWITTER                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.PULMUONE_FACEBOOK,0) AS PULMUONE_FACEBOOK                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.PULMUONE_INSTAGRAM,0) AS PULMUONE_INSTAGRAM                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.PULMUONE_YOUTUBE,0) AS PULMUONE_YOUTUBE                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.PULMUONE_OVERSEAS,0) AS PULMUONE_OVERSEAS                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.OTTOGI_MEDIA,0) AS OTTOGI_MEDIA                                                           "); 
//			sb.append("\n").append("     , IFNULL(B.OTTOGI_BLOG,0) AS OTTOGI_BLOG                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.OTTOGI_CAFE,0) AS OTTOGI_CAFE                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.OTTOGI_INTELLECT,0) AS OTTOGI_INTELLECT                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.OTTOGI_COMMUNITY,0) AS OTTOGI_COMMUNITY                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.OTTOGI_PUBLIC,0) AS OTTOGI_PUBLIC                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.OTTOGI_TWITTER,0) AS OTTOGI_TWITTER                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.OTTOGI_FACEBOOK,0) AS OTTOGI_FACEBOOK                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.OTTOGI_INSTAGRAM,0) AS OTTOGI_INSTAGRAM                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.OTTOGI_YOUTUBE,0) AS OTTOGI_YOUTUBE                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.OTTOGI_OVERSEAS,0) AS OTTOGI_OVERSEAS                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DONGWON_MEDIA,0) AS DONGWON_MEDIA                                                           "); 
//			sb.append("\n").append("     , IFNULL(B.DONGWON_BLOG,0) AS DONGWON_BLOG                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DONGWON_CAFE,0) AS DONGWON_CAFE                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DONGWON_INTELLECT,0) AS DONGWON_INTELLECT                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DONGWON_COMMUNITY,0) AS DONGWON_COMMUNITY                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DONGWON_PUBLIC,0) AS DONGWON_PUBLIC                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DONGWON_TWITTER,0) AS DONGWON_TWITTER                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DONGWON_FACEBOOK,0) AS DONGWON_FACEBOOK                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DONGWON_INSTAGRAM,0) AS DONGWON_INSTAGRAM                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DONGWON_YOUTUBE,0) AS DONGWON_YOUTUBE                                                           ");    
//			sb.append("\n").append("     , IFNULL(B.DONGWON_OVERSEAS,0) AS DONGWON_OVERSEAS                                                           ");    
			sb.append("\n").append("  FROM (                                                                                                                     ");
			sb.append("\n").append("        SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+topic_type1+" AND IC_CODE IN ("+company_code+")                                 ");
			sb.append("\n").append("       )A LEFT OUTER JOIN (                                                                                                  ");
			sb.append("\n").append("          SELECT A.AI_CODE1                                                                                                  ");
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 1 , AI_MEDIA, 0 )) AS CJ_MEDIA                                                           "); 
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 1 , AI_BLOG, 0 )) AS CJ_BLOG                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 1 , AI_CAFE, 0 )) AS CJ_CAFE                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 1 , AI_INTELLECT, 0 )) AS CJ_INTELLECT                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 1 , AI_COMMUNITY, 0 )) AS CJ_COMMUNITY                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 1 , AI_PUBLIC, 0 )) AS CJ_PUBLIC                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 1 , AI_TWITTER, 0 )) AS CJ_TWITTER                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 1 , AI_FACEBOOK, 0 )) AS CJ_FACEBOOK                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 1 , AI_INSTAGRAM, 0 )) AS CJ_INSTAGRAM                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 1 , AI_YOUTUBE, 0 )) AS CJ_YOUTUBE                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 1 , AI_OVERSEAS, 0 )) AS CJ_OVERSEAS                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 2 , AI_MEDIA, 0 )) AS DAESANG_MEDIA                                                           "); 
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 2 , AI_BLOG, 0 )) AS DAESANG_BLOG                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 2 , AI_CAFE, 0 )) AS DAESANG_CAFE                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 2 , AI_INTELLECT, 0 )) AS DAESANG_INTELLECT                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 2 , AI_COMMUNITY, 0 )) AS DAESANG_COMMUNITY                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 2 , AI_PUBLIC, 0 )) AS DAESANG_PUBLIC                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 2 , AI_TWITTER, 0 )) AS DAESANG_TWITTER                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 2 , AI_FACEBOOK, 0 )) AS DAESANG_FACEBOOK                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 2 , AI_INSTAGRAM, 0 )) AS DAESANG_INSTAGRAM                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 2 , AI_YOUTUBE, 0 )) AS DAESANG_YOUTUBE                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 2 , AI_OVERSEAS, 0 )) AS DAESANG_OVERSEAS                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 3 , AI_MEDIA, 0 )) AS PULMUONE_MEDIA                                                           "); 
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 3 , AI_BLOG, 0 )) AS PULMUONE_BLOG                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 3 , AI_CAFE, 0 )) AS PULMUONE_CAFE                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 3 , AI_INTELLECT, 0 )) AS PULMUONE_INTELLECT                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 3 , AI_COMMUNITY, 0 )) AS PULMUONE_COMMUNITY                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 3 , AI_PUBLIC, 0 )) AS PULMUONE_PUBLIC                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 3 , AI_TWITTER, 0 )) AS PULMUONE_TWITTER                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 3 , AI_FACEBOOK, 0 )) AS PULMUONE_FACEBOOK                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 3 , AI_INSTAGRAM, 0 )) AS PULMUONE_INSTAGRAM                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 3 , AI_YOUTUBE, 0 )) AS PULMUONE_YOUTUBE                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 3 , AI_OVERSEAS, 0 )) AS PULMUONE_OVERSEAS                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 4 , AI_MEDIA, 0 )) AS OTTOGI_MEDIA                                                           "); 
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 4 , AI_BLOG, 0 )) AS OTTOGI_BLOG                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 4 , AI_CAFE, 0 )) AS OTTOGI_CAFE                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 4 , AI_INTELLECT, 0 )) AS OTTOGI_INTELLECT                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 4 , AI_COMMUNITY, 0 )) AS OTTOGI_COMMUNITY                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 4 , AI_PUBLIC, 0 )) AS OTTOGI_PUBLIC                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 4 , AI_TWITTER, 0 )) AS OTTOGI_TWITTER                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 4 , AI_FACEBOOK, 0 )) AS OTTOGI_FACEBOOK                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 4 , AI_INSTAGRAM, 0 )) AS OTTOGI_INSTAGRAM                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 4 , AI_YOUTUBE, 0 )) AS OTTOGI_YOUTUBE                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 4 , AI_OVERSEAS, 0 )) AS OTTOGI_OVERSEAS                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 5 , AI_MEDIA, 0 )) AS DONGWON_MEDIA                                                           "); 
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 5 , AI_BLOG, 0 )) AS DONGWON_BLOG                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 5 , AI_CAFE, 0 )) AS DONGWON_CAFE                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 5 , AI_INTELLECT, 0 )) AS DONGWON_INTELLECT                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 5 , AI_COMMUNITY, 0 )) AS DONGWON_COMMUNITY                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 5 , AI_PUBLIC, 0 )) AS DONGWON_PUBLIC                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 5 , AI_TWITTER, 0 )) AS DONGWON_TWITTER                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 5 , AI_FACEBOOK, 0 )) AS DONGWON_FACEBOOK                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 5 , AI_INSTAGRAM, 0 )) AS DONGWON_INSTAGRAM                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 5 , AI_YOUTUBE, 0 )) AS DONGWON_YOUTUBE                                                           ");    
//			sb.append("\n").append("     , SUM(IF(A.AI_CODE1 = 5 , AI_OVERSEAS, 0 )) AS DONGWON_OVERSEAS                                                           ");    
			sb.append("\n").append("               , SUM(AI_TOTAL) AS AI_TOTAL                                                                                   ");
			sb.append("\n").append("               , SUM(AI_MEDIA) AS AI_MEDIA                                                                                   ");
			sb.append("\n").append("               , SUM(AI_BLOG) AS AI_BLOG                                                                                     ");
			sb.append("\n").append("               , SUM(AI_CAFE) AS AI_CAFE                                                                                     ");
			sb.append("\n").append("               , SUM(AI_INTELLECT) AS AI_INTELLECT                                                                           ");
			sb.append("\n").append("               , SUM(AI_COMMUNITY) AS AI_COMMUNITY                                                                           ");
			sb.append("\n").append("               , SUM(AI_PUBLIC) AS AI_PUBLIC                                                                                 ");
			sb.append("\n").append("               , SUM(AI_TWITTER) AS AI_TWITTER                                                                               ");
			sb.append("\n").append("               , SUM(AI_FACEBOOK) AS AI_FACEBOOK                                                                             ");
			sb.append("\n").append("               , SUM(AI_INSTAGRAM) AS AI_INSTAGRAM                                                                           ");
			sb.append("\n").append("               , SUM(AI_YOUTUBE) AS AI_YOUTUBE                                                                               ");
			sb.append("\n").append("               , SUM(AI_OVERSEAS) AS AI_OVERSEAS                                                                             ");
			sb.append("\n").append("            FROM ANA_MAIN_DEPT"+department+"_COMPANY_VOCM A                                                                               ");
			sb.append("\n").append("           WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' /*검색기간*/                                                         ");
			sb.append("\n").append("             AND A.AI_TYPE1 = "+topic_type1+"                                                                                              ");
			sb.append("\n").append("             AND A.AI_CODE1 IN ("+company_code+")                                                                                   ");
			sb.append("\n").append("           GROUP BY A.AI_CODE1                                                                                               ");
			sb.append("\n").append("       )B ON A.IC_CODE = B.AI_CODE1                                                                                          ");
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			Object[] item = new Object[size];
			int idx = 0;
			while(rs.next()) {
				idx = 0;
				item = new Object[size];
				item[idx++] = rs.getString("IC_NAME");
				item[idx++] = rs.getInt("AI_MEDIA");
				item[idx++] = rs.getInt("AI_BLOG");
				item[idx++] = rs.getInt("AI_CAFE");
				item[idx++] = rs.getInt("AI_INTELLECT");
				item[idx++] = rs.getInt("AI_COMMUNITY");
				item[idx++] = rs.getInt("AI_PUBLIC");
				item[idx++] = rs.getInt("AI_TWITTER");
				item[idx++] = rs.getInt("AI_FACEBOOK");
				item[idx++] = rs.getInt("AI_INSTAGRAM");
				item[idx++] = rs.getInt("AI_YOUTUBE");
				item[idx++] = rs.getInt("AI_OVERSEAS");
				result.add(item);	
			}

			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	////////////////////////////수정끝!!!!!!!!!!!!!!!!!!!!!
	

	public ArrayList<HashMap<String,String>> getTopic_EachTypeCompCount(String department, String sDate, String eDate, String company_code, String DayDiff) {
		ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();
			sb.append("\n").append("SELECT DATE_FORMAT(A.DATE,'%Y-%m-%d')AS AI_DATE                                                                                                      ");
			sb.append("\n").append("     , IFNULL(B.TOTAL,0) AS TOTAL                                                                                             ");
			sb.append("\n").append("     , IFNULL(B.CJ,0) AS CJ                                                                                                   ");
			sb.append("\n").append("     , IFNULL(B.DAESANG,0) AS DAESANG                                                                                         ");
			sb.append("\n").append("     , IFNULL(B.PULMUONE,0) AS PULMUONE                                                                                       ");
			sb.append("\n").append("     , IFNULL(B.OTTOGI,0) AS OTTOGI                                                                                           ");
			sb.append("\n").append("     , IFNULL(B.DONGWON,0) AS DONGWON                                                                                         ");
			sb.append("\n").append("  FROM (                                                                                                                      ");
			sb.append("\n").append("      SELECT DATE_FORMAT(DATE_ADD('"+sDate+" 00:00:00', INTERVAL @ROW:=@ROW+1 DAY),'%Y%m%d')  AS DATE  /*시작일자*/              ");
			sb.append("\n").append("    		   FROM ISSUE_CODE A                                                                                              ");
			sb.append("\n").append("    			    , (SELECT @ROW:=-1)R                                                                                      ");
			sb.append("\n").append("    		  LIMIT "+DayDiff+" /*일자차이값 넣기*/                                                                                            ");
			sb.append("\n").append("        ) A LEFT OUTER JOIN (                                                                                                 ");
			sb.append("\n").append("          SELECT AI_DATE                                                                                                      ");
			sb.append("\n").append("               , SUM(AI_TOTAL) AS TOTAL                                                                                       ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 1 , A.AI_TOTAL, 0 )) AS CJ                                                               ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 2 , A.AI_TOTAL, 0 )) AS DAESANG                                                          ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 3 , A.AI_TOTAL, 0 )) AS PULMUONE                                                         ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 4 , A.AI_TOTAL, 0 )) AS OTTOGI                                                           ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 5 , A.AI_TOTAL, 0 )) AS DONGWON                                                          ");
			sb.append("\n").append("            FROM ANA_MAIN_DEPT"+department+"_COMPANY_VOCD A                                                                                ");
			sb.append("\n").append("           WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' /*검색기간*/                                                           ");
			sb.append("\n").append("             AND A.AI_TYPE1 = "+topic_type1+"                                                                                               ");
			sb.append("\n").append("             AND A.AI_CODE1 IN ("+company_code+")                                                                                    ");
			sb.append("\n").append("           GROUP BY AI_DATE                                                                                                   ");
			sb.append("\n").append("        ) B ON A.DATE = B.AI_DATE                                                                                             ");
			sb.append("\n").append("       ORDER BY A.DATE ASC                                                                                                    ");

			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			while(rs.next()) {
				HashMap<String,String> item = new HashMap<String,String>();
				item.put("DATE", rs.getString("AI_DATE"));
				item.put("CJ", rs.getString("CJ"));
				item.put("DAESANG", rs.getString("DAESANG"));
				item.put("PULMUONE", rs.getString("PULMUONE"));
				item.put("OTTOGI", rs.getString("OTTOGI"));
				item.put("DONGWON", rs.getString("DONGWON"));
				item.put("TOTAL_CNT", rs.getString("TOTAL"));
				result.add(item);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}

	
	
	  public ArrayList<HashMap<String, String>> getPopDataList(String target_date, String sDate , String eDate, int rowLimit, ArrayList<String[]> param, String sg_seq, String senti){
	    	ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
			try {
				conn  = new DBconn();
				conn.getDBCPConnection();
				stmt = conn.createStatement();
			                              
				sb = new StringBuffer();  
				sb.append("\n").append("  SELECT A.ID_SEQ                                                                                                                 ");
				sb.append("\n").append("             , DATE_FORMAT(A.MD_DATE,'%Y-%m-%d') AS DATE                                                                          ");
				sb.append("\n").append("             , A.MD_SITE_NAME                                                                                                     ");
				sb.append("\n").append("             , A.ID_TITLE                                                                                                         ");
				sb.append("\n").append("             , A.ID_URL                                                                                                           ");
				sb.append("\n").append("             , A.ID_SENTI  AS SENTI_CODE                                                                                                        ");
				sb.append("\n").append("             ,CASE                                                                                                                ");
				sb.append("\n").append("             WHEN A.ID_SENTI ='1' THEN '긍정'                                                                                       ");
				sb.append("\n").append("             WHEN A.ID_SENTI ='2' THEN '부정'                                                                                       ");
				sb.append("\n").append("             ELSE '중립'                                                                                                            ");
				sb.append("\n").append("             END AS `ID_SENTI`                                                                                                    ");
				sb.append("\n").append("             , A.S_SEQ                                                                                                            ");
				sb.append("\n").append("          FROM ISSUE_DATA A                                                                                                       ");
				for(int i=0; i<param.size(); i++){
					sb.append("\n").append("     INNER JOIN ISSUE_DATA_CODE S"+(i+1)+" ON A.ID_SEQ = S"+(i+1)+".ID_SEQ AND S"+(i+1)+".IC_TYPE = "+param.get(i)[0]+" AND S"+(i+1)+".IC_CODE = "+param.get(i)[1]+" /*부서*/                  ");
				}
				//날짜
				if("".equals(target_date)){
					sb.append("\n").append("         WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' 									                                        ");
					sb.append("\n").append("".equals(eDate)?" AND '"+sDate+" 23:59:59'" : "AND '"+eDate+" 23:59:59'						                                     ");
				}else{
					sb.append("\n").append("         WHERE A.MD_DATE BETWEEN '"+target_date+" 00:00:00' AND '"+target_date+" 23:59:59'								                                        ");
				}
				sb.append("\n").append("".equals(senti)?"":" AND A.ID_SENTI = "+senti+" /*성향*/                                                                                             ");
				sb.append("\n").append("".equals(sg_seq)?"":" AND A.SG_SEQ IN ("+sg_seq+") /*성향*/                                                                                                ");
				sb.append("\n").append("           LIMIT "+rowLimit+",10                                                                                                             ");
				
				rs = stmt.executeQuery(sb.toString());
				System.out.println(sb.toString());
				HashMap<String, String> item = new HashMap<String, String>();
				while(rs.next()) {
					item = new HashMap<String, String>();
					item.put("ID_SEQ", rs.getString("ID_SEQ"));
					item.put("DATE", rs.getString("DATE"));
					item.put("SITE", rs.getString("MD_SITE_NAME"));
					item.put("TITLE", rs.getString("ID_TITLE"));
					item.put("URL", rs.getString("ID_URL"));
					item.put("SENTI", rs.getString("ID_SENTI"));
					item.put("SENTI_CODE", rs.getString("SENTI_CODE"));
					item.put("S_SEQ", rs.getString("S_SEQ"));
					
					
					result.add(item);
				}
				
			} catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				close();
			}
			return result;
	    }
	  
	  

	  public int getPopDataLiCount(String target_date, String sDate , String eDate, ArrayList<String[]> param, String sg_seq, String senti){
		    int result = 0;
			try {
				conn  = new DBconn();
				conn.getDBCPConnection();
				stmt = conn.createStatement();
			                              
				sb = new StringBuffer();  
				sb.append("\n").append("  SELECT COUNT(*) AS CNT                                                                                                                 ");
				sb.append("\n").append("          FROM ISSUE_DATA A                                                                                                       ");
				for(int i=0; i<param.size(); i++){
					sb.append("\n").append("   INNER JOIN ISSUE_DATA_CODE S"+(i+1)+" ON A.ID_SEQ = S"+(i+1)+".ID_SEQ AND S"+(i+1)+".IC_TYPE = "+param.get(i)[0]+" AND S"+(i+1)+".IC_CODE = "+param.get(i)[1]+" /*부서*/                  ");
				}
				//날짜
				if("".equals(target_date)){
					sb.append("\n").append("         WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' 									                                        ");
					sb.append("\n").append("".equals(eDate)?" AND '"+sDate+" 23:59:59'" : "AND '"+eDate+" 23:59:59'						                                     ");
				}else{
					sb.append("\n").append("         WHERE A.MD_DATE BETWEEN '"+target_date+" 00:00:00' AND '"+target_date+" 23:59:59'								                                        ");
				}
				
				
				sb.append("\n").append("".equals(senti)?"":" AND A.ID_SENTI = "+senti+" /*성향*/                                                                                             ");
				sb.append("\n").append("".equals(sg_seq)?"":" AND A.SG_SEQ IN ("+sg_seq+") /*성향*/                                                                                                ");
				
				rs = stmt.executeQuery(sb.toString());
				System.out.println(sb.toString());
				if(rs.next()) {
					result  = rs.getInt("CNT");
				}
				
			} catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				close();
			}
			return result;
	    }
		

	public ArrayList<Object[]> getTopic_EachTypeCompCount_excel(String department, String sDate, String eDate, String company_code, String Daydiff, int size) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();
			sb.append("\n").append("SELECT DATE_FORMAT(A.DATE,'%Y-%m-%d')AS AI_DATE                                                                                                      ");
			sb.append("\n").append("     , IFNULL(B.TOTAL,0) AS TOTAL                                                                                             ");
			sb.append("\n").append("     , IFNULL(B.CJ,0) AS CJ                                                                                                   ");
			sb.append("\n").append("     , IFNULL(B.DAESANG,0) AS DAESANG                                                                                         ");
			sb.append("\n").append("     , IFNULL(B.PULMUONE,0) AS PULMUONE                                                                                       ");
			sb.append("\n").append("     , IFNULL(B.OTTOGI,0) AS OTTOGI                                                                                           ");
			sb.append("\n").append("     , IFNULL(B.DONGWON,0) AS DONGWON                                                                                         ");
			sb.append("\n").append("  FROM (                                                                                                                      ");
			sb.append("\n").append("      SELECT DATE_FORMAT(DATE_ADD('"+sDate+" 00:00:00', INTERVAL @ROW:=@ROW+1 DAY),'%Y%m%d')  AS DATE  /*시작일자*/              ");
			sb.append("\n").append("    		   FROM ISSUE_CODE A                                                                                              ");
			sb.append("\n").append("    			    , (SELECT @ROW:=-1)R                                                                                      ");
			sb.append("\n").append("    		  LIMIT "+Daydiff+" /*일자차이값 넣기*/                                                                                            ");
			sb.append("\n").append("        ) A LEFT OUTER JOIN (                                                                                                 ");
			sb.append("\n").append("          SELECT AI_DATE                                                                                                      ");
			sb.append("\n").append("               , SUM(AI_TOTAL) AS TOTAL                                                                                       ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 1 , A.AI_TOTAL, 0 )) AS CJ                                                               ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 2 , A.AI_TOTAL, 0 )) AS DAESANG                                                          ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 3 , A.AI_TOTAL, 0 )) AS PULMUONE                                                         ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 4 , A.AI_TOTAL, 0 )) AS OTTOGI                                                           ");
			sb.append("\n").append("               , SUM(IF(A.AI_CODE1 = 5 , A.AI_TOTAL, 0 )) AS DONGWON                                                          ");
			sb.append("\n").append("            FROM ANA_MAIN_DEPT"+department+"_COMPANY_VOCD A                                                                                ");
			sb.append("\n").append("           WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"' /*검색기간*/                                                           ");
			sb.append("\n").append("             AND A.AI_TYPE1 = "+topic_type1+"                                                                                               ");
			sb.append("\n").append("             AND A.AI_CODE1 IN ("+company_code+")                                                                                    ");
			sb.append("\n").append("           GROUP BY AI_DATE                                                                                                   ");
			sb.append("\n").append("        ) B ON A.DATE = B.AI_DATE                                                                                             ");
			sb.append("\n").append("       ORDER BY A.DATE ASC                                                                                                    ");

			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			Object[] item = new Object[size];
			int idx = 0;
			while(rs.next()) {
				idx = 0;
				item = new Object[size];
				item[idx++] = rs.getString("AI_DATE");
				item[idx++] = rs.getInt("CJ");
				item[idx++] = rs.getInt("DAESANG");
				item[idx++] = rs.getInt("PULMUONE");
				item[idx++] = rs.getInt("OTTOGI");
				item[idx++] = rs.getInt("DONGWON");
				item[idx++] = rs.getInt("TOTAL");
				result.add(item);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}

	
	public String getDayDiff(String sDate, String eDate){
		String result = "0";
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();
			sb.append("\n").append("SELECT TIMESTAMPDIFF( DAY,'"+sDate+"', '"+eDate+"')+1 AS DIFF ");

			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			if(rs.next()) {
				result =  rs.getString("DIFF");
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			close();
		}
		return result;
	}
	
	
	private void close() {
		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
		if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	}

}
