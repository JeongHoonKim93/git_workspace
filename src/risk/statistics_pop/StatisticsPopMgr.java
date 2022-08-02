package risk.statistics_pop;

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


public class StatisticsPopMgr {
	private DateUtil du = new DateUtil();
	private StringUtil su = new StringUtil();
	

	DBconn conn = null;
	Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    StringBuffer sb = null;


    public ArrayList<String[]> getSiteGroupList(String ex_seq){
    	conn = new DBconn();
    	
    	ArrayList<String[]> result = new ArrayList<String[]>();    	
    	try {

			conn.getDBCPConnection();
			sb = new StringBuffer();
		    sb.append("SELECT SG_SEQ, SG_NAME                        \n");
		    sb.append("FROM SITE_GROUP                               \n");
		    sb.append("  WHERE SG_SEQ NOT IN ("+ex_seq+")            \n");
		    sb.append("ORDER BY SG_ORDER                             \n");
		    
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
    
    public ArrayList<HashMap<String, String>> getDataList(String type, String sDate , String eDate, String psDate, String peDate, String settingCode, String sg_code, String orderBy){
    	ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    	String ORDER = "CNT_D";
    	//정렬기준 (0건수, 1:증가율)
    	if("1".equals(orderBy)){
    		ORDER = "FACTOR_PER";
    	}
    	
    	String[] type_code = settingCode.split("@");
    	String[] tmp_type_code = new String[2];
    	

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
		
			sb = new StringBuffer();                                                                                   
			sb.append("\n").append("   SELECT A.IC_CODE                                                                                                      ");
			sb.append("\n").append("   		, A.IC_NAME                                                                                                           ");
			sb.append("\n").append("  		, A.CNT_D                                                                                                             ");
			sb.append("\n").append("   		, B.CNT_P                                                                                                             ");
			sb.append("\n").append("   		, IFNULL(ABS(B.CNT_P - A.CNT_D),0) AS FACTOR_CNT /*증감 건수*/                                                            ");
			sb.append("\n").append("   		, IF((B.CNT_P - A.CNT_D) = 0, 0, IF((B.CNT_P - A.CNT_D) > 0, -1, 1)) AS FACTOR_POINT /*증감 화살표*/                       ");
			sb.append("\n").append("   		, ROUND(IFNULL((ABS(B.CNT_P - A.CNT_D) / B.CNT_P * 100),0),1) AS FACTOR_PER /*증감율*/                                   ");
			sb.append("\n").append("   FROM (                                                                                                                ");
			sb.append("\n").append("  	 SELECT A.IC_CODE                                                                                                      ");
			sb.append("\n").append("         , A.IC_NAME                                                                                                     ");
			sb.append("\n").append("         , IFNULL(B.CNT,0) AS CNT_D                                                                                      ");
			sb.append("\n").append("      FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+type+" AND IC_CODE > 0) A                                ");
			sb.append("\n").append("           LEFT OUTER JOIN                                                                                               ");
			sb.append("\n").append("           (                                                                                                             ");
			sb.append("\n").append("              SELECT B.IC_CODE                                                                                           ");
			sb.append("\n").append("                   , COUNT(*) AS CNT                                                                                     ");
			sb.append("\n").append("                FROM ISSUE_DATA A                                                                                        ");
			sb.append("\n").append("                   INNER JOIN ISSUE_DATA_CODE B  ON  A.ID_SEQ = B.ID_SEQ                                                                              ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					sb.append("\n").append("           INNER JOIN ISSUE_DATA_CODE C"+i+"  ON A.ID_SEQ = C"+i+".ID_SEQ                                                                       ");
				}
			}
			sb.append("\n").append("               WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'/*당기*/                             ");
			sb.append("\n").append("                 AND B.IC_TYPE = "+type+"                                                                                       ");

			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					tmp_type_code = type_code[i].split(",");
					System.out.println(tmp_type_code[0]);
					sb.append("\n").append("             AND C"+i+".IC_CODE = "+tmp_type_code[1]+"                                                                                       ");
					sb.append("\n").append("             AND C"+i+".IC_TYPE = "+tmp_type_code[0]+"                                                                                      ");
				}
			}
			sb.append("\n").append("".equals(sg_code)?"":" AND A.SG_SEQ = "+sg_code+"                                                                     ");
			sb.append("\n").append("               GROUP BY B.IC_CODE                                 			                                                ");
			sb.append("\n").append("           )B ON A.IC_CODE = B.IC_CODE                                                                                   ");
			sb.append("\n").append("    ORDER BY A.IC_CODE                                                                                                   ");
			sb.append("\n").append("   )A LEFT OUTER JOIN (                                                                                                  ");
			sb.append("\n").append("    	SELECT A.IC_CODE                                                                                                     ");
			sb.append("\n").append("         , A.IC_NAME                                                                                                     ");
			sb.append("\n").append("         , IFNULL(B.CNT,0) AS CNT_P                                                                                      ");
			sb.append("\n").append("      	FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+type+" AND IC_CODE > 0) A                                ");
			sb.append("\n").append("           LEFT OUTER JOIN                                                                                               ");
			sb.append("\n").append("           (                                                                                                             ");
			sb.append("\n").append("              SELECT B.IC_CODE                                                                                           ");
			sb.append("\n").append("                   , COUNT(*) AS CNT                                                                                     ");
			sb.append("\n").append("                FROM ISSUE_DATA A                                                                                        ");
			sb.append("\n").append("                   INNER JOIN ISSUE_DATA_CODE B ON   A.ID_SEQ = B.ID_SEQ                                                                                 ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					sb.append("\n").append("              INNER JOIN ISSUE_DATA_CODE C"+i+"     ON A.ID_SEQ = C"+i+".ID_SEQ                                                                     ");
				}
			}
			sb.append("\n").append("               WHERE A.MD_DATE BETWEEN '"+psDate+" 00:00:00' AND '"+peDate+" 23:59:59'/*당기*/                             ");
			sb.append("\n").append("                 AND B.IC_TYPE = "+type+"                                                                                       ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					tmp_type_code = type_code[i].split(",");
					sb.append("\n").append("             AND C"+i+".IC_CODE = "+tmp_type_code[1]+"                                                                                       ");
					sb.append("\n").append("             AND C"+i+".IC_TYPE = "+tmp_type_code[0]+"                                                                                      ");
				}
			}
			sb.append("\n").append("".equals(sg_code)?"":" AND A.SG_SEQ = "+sg_code+"                                                                     ");
			sb.append("\n").append("               GROUP BY B.IC_CODE                                                                                        ");
			sb.append("\n").append("           )B ON A.IC_CODE = B.IC_CODE                                                                                   ");
			sb.append("\n").append("   )B ON A.IC_CODE = B.IC_CODE                                                                                           ");
			sb.append("\n").append("   ORDER  BY "+ORDER+" DESC                                                                                                  ");
			
			
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
				item.put("CNT_D", rs.getString("CNT_D"));
				item.put("CNT_P", rs.getString("CNT_P"));
				
				item.put("IC_NAME", rs.getString("IC_NAME"));
				item.put("FACTOR_PER", rs.getString("FACTOR_PER"));
				
				
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
    
    public ArrayList<Object[]> getDataList_excel(String type, String sDate , String eDate, String psDate, String peDate, String settingCode, String sg_code, String orderBy, int size){
    	ArrayList<Object[]> result = new ArrayList<Object[]>();
    	String ORDER = "CNT_D";
    	//정렬기준 (0건수, 1:증가율)
    	if("1".equals(orderBy)){
    		ORDER = "FACTOR_PER";
    	}
    	
    	String[] type_code = settingCode.split("@");
    	String[] tmp_type_code = new String[2];
    	

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
		
			sb = new StringBuffer();                                                                                   
			sb.append("\n").append("   SELECT A.IC_CODE                                                                                                      ");
			sb.append("\n").append("   		, A.IC_NAME                                                                                                           ");
			sb.append("\n").append("  		, A.CNT_D                                                                                                             ");
			sb.append("\n").append("   		, B.CNT_P                                                                                                             ");
			sb.append("\n").append("   		, IFNULL(ABS(B.CNT_P - A.CNT_D),0) AS FACTOR_CNT /*증감 건수*/                                                            ");
			sb.append("\n").append("   		, IF((B.CNT_P - A.CNT_D) = 0, 0, IF((B.CNT_P - A.CNT_D) > 0, -1, 1)) AS FACTOR_POINT /*증감 화살표*/                       ");
			sb.append("\n").append("   		, ROUND(IFNULL((ABS(B.CNT_P - A.CNT_D) / B.CNT_P * 100),0),1) AS FACTOR_PER /*증감율*/                                   ");
			sb.append("\n").append("   FROM (                                                                                                                ");
			sb.append("\n").append("  	 SELECT A.IC_CODE                                                                                                      ");
			sb.append("\n").append("         , A.IC_NAME                                                                                                     ");
			sb.append("\n").append("         , IFNULL(B.CNT,0) AS CNT_D                                                                                      ");
			sb.append("\n").append("      FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+type+" AND IC_CODE > 0) A                                ");
			sb.append("\n").append("           LEFT OUTER JOIN                                                                                               ");
			sb.append("\n").append("           (                                                                                                             ");
			sb.append("\n").append("              SELECT B.IC_CODE                                                                                           ");
			sb.append("\n").append("                   , COUNT(*) AS CNT                                                                                     ");
			sb.append("\n").append("                FROM ISSUE_DATA A                                                                                        ");
			sb.append("\n").append("                   INNER JOIN ISSUE_DATA_CODE B  ON  A.ID_SEQ = B.ID_SEQ                                                                              ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					sb.append("\n").append("           INNER JOIN ISSUE_DATA_CODE C"+i+"  ON A.ID_SEQ = C"+i+".ID_SEQ                                                                       ");
				}
			}
			sb.append("\n").append("               WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'/*당기*/                             ");
			sb.append("\n").append("                 AND B.IC_TYPE = "+type+"                                                                                       ");

			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					tmp_type_code = type_code[i].split(",");
					System.out.println(tmp_type_code[0]);
					sb.append("\n").append("             AND C"+i+".IC_CODE = "+tmp_type_code[1]+"                                                                                       ");
					sb.append("\n").append("             AND C"+i+".IC_TYPE = "+tmp_type_code[0]+"                                                                                      ");
				}
			}
			sb.append("\n").append("".equals(sg_code)?"":" AND A.SG_SEQ = "+sg_code+"                                                                     ");
			sb.append("\n").append("               GROUP BY B.IC_CODE                                 			                                                ");
			sb.append("\n").append("           )B ON A.IC_CODE = B.IC_CODE                                                                                   ");
			sb.append("\n").append("    ORDER BY A.IC_CODE                                                                                                   ");
			sb.append("\n").append("   )A LEFT OUTER JOIN (                                                                                                  ");
			sb.append("\n").append("    	SELECT A.IC_CODE                                                                                                     ");
			sb.append("\n").append("         , A.IC_NAME                                                                                                     ");
			sb.append("\n").append("         , IFNULL(B.CNT,0) AS CNT_P                                                                                      ");
			sb.append("\n").append("      	FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+type+" AND IC_CODE > 0) A                                ");
			sb.append("\n").append("           LEFT OUTER JOIN                                                                                               ");
			sb.append("\n").append("           (                                                                                                             ");
			sb.append("\n").append("              SELECT B.IC_CODE                                                                                           ");
			sb.append("\n").append("                   , COUNT(*) AS CNT                                                                                     ");
			sb.append("\n").append("                FROM ISSUE_DATA A                                                                                        ");
			sb.append("\n").append("                   INNER JOIN ISSUE_DATA_CODE B ON   A.ID_SEQ = B.ID_SEQ                                                                                 ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					sb.append("\n").append("              INNER JOIN ISSUE_DATA_CODE C"+i+"     ON A.ID_SEQ = C"+i+".ID_SEQ                                                                     ");
				}
			}
			sb.append("\n").append("               WHERE A.MD_DATE BETWEEN '"+psDate+" 00:00:00' AND '"+peDate+" 23:59:59'/*당기*/                             ");
			sb.append("\n").append("                 AND B.IC_TYPE = "+type+"                                                                                       ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					tmp_type_code = type_code[i].split(",");
					sb.append("\n").append("             AND C"+i+".IC_CODE = "+tmp_type_code[1]+"                                                                                       ");
					sb.append("\n").append("             AND C"+i+".IC_TYPE = "+tmp_type_code[0]+"                                                                                      ");
				}
			}
			sb.append("\n").append("".equals(sg_code)?"":" AND A.SG_SEQ = "+sg_code+"                                                                     ");
			sb.append("\n").append("               GROUP BY B.IC_CODE                                                                                        ");
			sb.append("\n").append("           )B ON A.IC_CODE = B.IC_CODE                                                                                   ");
			sb.append("\n").append("   )B ON A.IC_CODE = B.IC_CODE                                                                                           ");
			sb.append("\n").append("   ORDER  BY "+ORDER+" DESC                                                                                                  ");
			
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			Object[] item = new Object[size];
			int idx = 0;
			String point = "";
			while(rs.next()) {
				point = "";
				idx = 0;
				item = new Object[size];
				item[idx++] = rs.getString("IC_NAME");
				item[idx++] = rs.getString("CNT_P");
				item[idx++] = rs.getString("CNT_D");
				
				
				
				//증감표시
				point = rs.getString("FACTOR_POINT");
				if("0".equals(point)){
					point = "";
				}else if("1".equals(point)){
					point = "▲";
				}else if ("-1".equals(point)){
					point = "▼";
				}
				item[idx++] = point + rs.getString("FACTOR_CNT");
				item[idx++] = point + rs.getString("FACTOR_PER");
				
				System.out.println(rs.getString("IC_NAME"));
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
    
    
    public ArrayList<HashMap<String, String>> getDataList2(String type, String type2, String code, String sDate , String eDate, String psDate, String peDate, String settingCode, String sg_code, String orderBy){
    	ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    	String ORDER = "CNT_D";
    	//정렬기준 (0건수, 1:증가율)
    	if("1".equals(orderBy)){
    		ORDER = "FACTOR_PER";
    	}
    	
    	String[] type_code = settingCode.split("@");
    	String[] tmp_type_code = new String[2];
    	

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
		
			sb = new StringBuffer();                                                                                   
			sb.append("\n").append("   SELECT A.IC_CODE                                                                                                      ");
			sb.append("\n").append("   		, A.IC_NAME                                                                                                           ");
			sb.append("\n").append("  		, IFNULL(A.CNT_D,0) AS CNT_D                                                                                                             ");
			sb.append("\n").append("   		, IFNULL(B.CNT_P,0) AS CNT_P                                                                                                             ");
			sb.append("\n").append("   		, IFNULL(ABS(B.CNT_P - A.CNT_D),0) AS FACTOR_CNT /*증감 건수*/                                                            ");
			sb.append("\n").append("   		, IF((B.CNT_P - A.CNT_D) = 0, 0, IF((B.CNT_P - A.CNT_D) > 0, -1, 1)) AS FACTOR_POINT /*증감 화살표*/                       ");
			sb.append("\n").append("   		, ROUND(IFNULL((ABS(B.CNT_P - A.CNT_D) / B.CNT_P * 100),0),1) AS FACTOR_PER /*증감율*/                                   ");
			sb.append("\n").append("   FROM (                                                                                                                ");
			sb.append("\n").append("  	 SELECT A.IC_CODE                                                                                                      ");
			sb.append("\n").append("         , A.IC_NAME                                                                                                     ");
			sb.append("\n").append("         , IFNULL(B.CNT,0) AS CNT_D                                                                                      ");
			sb.append("\n").append("      FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+type+" AND IC_CODE > 0) A                                ");
			sb.append("\n").append("           LEFT OUTER JOIN                                                                                               ");
			sb.append("\n").append("           (                                                                                                             ");
			sb.append("\n").append("              SELECT B.IC_CODE                                                                                           ");
			sb.append("\n").append("                   , COUNT(*) AS CNT                                                                                     ");
			sb.append("\n").append("                FROM ISSUE_DATA A                                                                                        ");
			sb.append("\n").append("                   INNER JOIN ISSUE_DATA_CODE B  ON A.ID_SEQ = B.ID_SEQ  AND B.IC_TYPE = "+type+"                                                                                 ");
			sb.append("\n").append("                   INNER JOIN ISSUE_DATA_CODE C  ON A.ID_SEQ = C.ID_SEQ                                                                                   ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					sb.append("\n").append("           INNER JOIN ISSUE_DATA_CODE C"+i+"  ON A.ID_SEQ = C"+i+".ID_SEQ                                                                     ");
				}
			}
			sb.append("\n").append("               WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'/*당기*/                             ");
			sb.append("\n").append("             	 AND C.IC_CODE = "+code+"                                                                                       ");
			sb.append("\n").append("             	 AND C.IC_TYPE = "+type2+"                                                                                      ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					tmp_type_code = type_code[i].split(",");
					System.out.println(tmp_type_code[0]);
					sb.append("\n").append("             AND C"+i+".IC_CODE = "+tmp_type_code[1]+"                                                                                       ");
					sb.append("\n").append("             AND C"+i+".IC_TYPE = "+tmp_type_code[0]+"                                                                                      ");
				}
			}
			sb.append("\n").append("".equals(sg_code)?"":" AND A.SG_SEQ = "+sg_code+"                                                                     ");
			sb.append("\n").append("               GROUP BY B.IC_CODE                                 			                                                ");
			sb.append("\n").append("           )B ON A.IC_CODE = B.IC_CODE                                                                                   ");
			sb.append("\n").append("    ORDER BY A.IC_CODE                                                                                                   ");
			sb.append("\n").append("   )A LEFT OUTER JOIN (                                                                                                  ");
			sb.append("\n").append("    	SELECT A.IC_CODE                                                                                                     ");
			sb.append("\n").append("         , A.IC_NAME                                                                                                     ");
			sb.append("\n").append("         , IFNULL(B.CNT,0) AS CNT_P                                                                                      ");
			sb.append("\n").append("      	FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+type+" AND IC_CODE > 0) A                                ");
			sb.append("\n").append("           LEFT OUTER JOIN                                                                                               ");
			sb.append("\n").append("           (                                                                                                             ");
			sb.append("\n").append("              SELECT B.IC_CODE                                                                                           ");
			sb.append("\n").append("                   , COUNT(*) AS CNT                                                                                     ");
			sb.append("\n").append("                FROM ISSUE_DATA A                                                                                        ");
			sb.append("\n").append("                   INNER JOIN  ISSUE_DATA_CODE B  ON A.ID_SEQ = B.ID_SEQ  AND B.IC_TYPE = "+type+"                                                                                 ");
			sb.append("\n").append("                   INNER JOIN  ISSUE_DATA_CODE C  ON A.ID_SEQ = C.ID_SEQ                                                                                 ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					sb.append("\n").append("           INNER JOIN  ISSUE_DATA_CODE C"+i+"    ON A.ID_SEQ = C"+i+".ID_SEQ                                                                      ");
				}
			}
			sb.append("\n").append("               WHERE A.MD_DATE BETWEEN '"+psDate+" 00:00:00' AND '"+peDate+" 23:59:59'/*당기*/                             ");
			sb.append("\n").append("             	 AND C.IC_CODE = "+code+"                                                                                       ");
			sb.append("\n").append("             	 AND C.IC_TYPE = "+type2+"                                                                                      ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					tmp_type_code = type_code[i].split(",");
					sb.append("\n").append("             AND C"+i+".IC_CODE = "+tmp_type_code[1]+"                                                                                       ");
					sb.append("\n").append("             AND C"+i+".IC_TYPE = "+tmp_type_code[0]+"                                                                                      ");
				}
			}
			sb.append("\n").append("".equals(sg_code)?"":" AND A.SG_SEQ = "+sg_code+"                                                                     ");
			sb.append("\n").append("               GROUP BY B.IC_CODE                                                                                        ");
			sb.append("\n").append("           )B ON A.IC_CODE = B.IC_CODE                                                                                   ");
			sb.append("\n").append("   )B ON A.IC_CODE = B.IC_CODE                                                                                           ");
			sb.append("\n").append("   ORDER  BY "+ORDER+" DESC                                                                                                  ");
			
			
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
				item.put("CNT_D", rs.getString("CNT_D"));
				item.put("CNT_P", rs.getString("CNT_P"));
				
				item.put("IC_NAME", rs.getString("IC_NAME"));
				item.put("FACTOR_PER", rs.getString("FACTOR_PER"));
				
				
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
    
    
    
    public ArrayList<Object[]> getDataList2_excel(String type, String type2, String code, String sDate , String eDate, String psDate, String peDate, String settingCode, String sg_code, String orderBy, int size){
    	ArrayList<Object[]> result = new ArrayList<Object[]>();
    	String ORDER = "CNT_D";
    	//정렬기준 (0건수, 1:증가율)
    	if("1".equals(orderBy)){
    		ORDER = "FACTOR_PER";
    	}
    	
    	String[] type_code = settingCode.split("@");
    	String[] tmp_type_code = new String[2];
    	

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
		
			sb = new StringBuffer();                                                                                   
			sb.append("\n").append("   SELECT A.IC_CODE                                                                                                      ");
			sb.append("\n").append("   		, A.IC_NAME                                                                                                           ");
			sb.append("\n").append("  		, IFNULL(A.CNT_D,0) AS CNT_D                                                                                                             ");
			sb.append("\n").append("   		, IFNULL(B.CNT_P,0) AS CNT_P                                                                                                             ");
			sb.append("\n").append("   		, IFNULL(ABS(B.CNT_P - A.CNT_D),0) AS FACTOR_CNT /*증감 건수*/                                                            ");
			sb.append("\n").append("   		, IF((B.CNT_P - A.CNT_D) = 0, 0, IF((B.CNT_P - A.CNT_D) > 0, -1, 1)) AS FACTOR_POINT /*증감 화살표*/                       ");
			sb.append("\n").append("   		, ROUND(IFNULL((ABS(B.CNT_P - A.CNT_D) / B.CNT_P * 100),0),1) AS FACTOR_PER /*증감율*/                                   ");
			sb.append("\n").append("   FROM (                                                                                                                ");
			sb.append("\n").append("  	 SELECT A.IC_CODE                                                                                                      ");
			sb.append("\n").append("         , A.IC_NAME                                                                                                     ");
			sb.append("\n").append("         , IFNULL(B.CNT,0) AS CNT_D                                                                                      ");
			sb.append("\n").append("      FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+type+" AND IC_CODE > 0) A                                ");
			sb.append("\n").append("           LEFT OUTER JOIN                                                                                               ");
			sb.append("\n").append("           (                                                                                                             ");
			sb.append("\n").append("              SELECT B.IC_CODE                                                                                           ");
			sb.append("\n").append("                   , COUNT(*) AS CNT                                                                                     ");
			sb.append("\n").append("                FROM ISSUE_DATA A                                                                                        ");
			sb.append("\n").append("                   INNER JOIN ISSUE_DATA_CODE B  ON A.ID_SEQ = B.ID_SEQ  AND B.IC_TYPE = "+type+"                                                                                 ");
			sb.append("\n").append("                   INNER JOIN ISSUE_DATA_CODE C  ON A.ID_SEQ = C.ID_SEQ                                                                                   ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					sb.append("\n").append("           INNER JOIN ISSUE_DATA_CODE C"+i+"  ON A.ID_SEQ = C"+i+".ID_SEQ                                                                     ");
				}
			}
			sb.append("\n").append("               WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'/*당기*/                             ");
			sb.append("\n").append("             	 AND C.IC_CODE = "+code+"                                                                                       ");
			sb.append("\n").append("             	 AND C.IC_TYPE = "+type2+"                                                                                      ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					tmp_type_code = type_code[i].split(",");
					System.out.println(tmp_type_code[0]);
					sb.append("\n").append("             AND C"+i+".IC_CODE = "+tmp_type_code[1]+"                                                                                       ");
					sb.append("\n").append("             AND C"+i+".IC_TYPE = "+tmp_type_code[0]+"                                                                                      ");
				}
			}
			sb.append("\n").append("".equals(sg_code)?"":" AND A.SG_SEQ = "+sg_code+"                                                                     ");
			sb.append("\n").append("               GROUP BY B.IC_CODE                                 			                                                ");
			sb.append("\n").append("           )B ON A.IC_CODE = B.IC_CODE                                                                                   ");
			sb.append("\n").append("    ORDER BY A.IC_CODE                                                                                                   ");
			sb.append("\n").append("   )A LEFT OUTER JOIN (                                                                                                  ");
			sb.append("\n").append("    	SELECT A.IC_CODE                                                                                                     ");
			sb.append("\n").append("         , A.IC_NAME                                                                                                     ");
			sb.append("\n").append("         , IFNULL(B.CNT,0) AS CNT_P                                                                                      ");
			sb.append("\n").append("      	FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+type+" AND IC_CODE > 0) A                                ");
			sb.append("\n").append("           LEFT OUTER JOIN                                                                                               ");
			sb.append("\n").append("           (                                                                                                             ");
			sb.append("\n").append("              SELECT B.IC_CODE                                                                                           ");
			sb.append("\n").append("                   , COUNT(*) AS CNT                                                                                     ");
			sb.append("\n").append("                FROM ISSUE_DATA A                                                                                        ");
			sb.append("\n").append("                   INNER JOIN  ISSUE_DATA_CODE B  ON A.ID_SEQ = B.ID_SEQ  AND B.IC_TYPE = "+type+"                                                                                 ");
			sb.append("\n").append("                   INNER JOIN  ISSUE_DATA_CODE C  ON A.ID_SEQ = C.ID_SEQ                                                                                 ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					sb.append("\n").append("           INNER JOIN  ISSUE_DATA_CODE C"+i+"    ON A.ID_SEQ = C"+i+".ID_SEQ                                                                      ");
				}
			}
			sb.append("\n").append("               WHERE A.MD_DATE BETWEEN '"+psDate+" 00:00:00' AND '"+peDate+" 23:59:59'/*당기*/                             ");
			sb.append("\n").append("             	 AND C.IC_CODE = "+code+"                                                                                       ");
			sb.append("\n").append("             	 AND C.IC_TYPE = "+type2+"                                                                                      ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					tmp_type_code = type_code[i].split(",");
					sb.append("\n").append("             AND C"+i+".IC_CODE = "+tmp_type_code[1]+"                                                                                       ");
					sb.append("\n").append("             AND C"+i+".IC_TYPE = "+tmp_type_code[0]+"                                                                                      ");
				}
			}
			sb.append("\n").append("".equals(sg_code)?"":" AND A.SG_SEQ = "+sg_code+"                                                                     ");
			sb.append("\n").append("               GROUP BY B.IC_CODE                                                                                        ");
			sb.append("\n").append("           )B ON A.IC_CODE = B.IC_CODE                                                                                   ");
			sb.append("\n").append("   )B ON A.IC_CODE = B.IC_CODE                                                                                           ");
			sb.append("\n").append("   ORDER  BY "+ORDER+" DESC                                                                                                  ");
			
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			Object[] item = new Object[size];
			int idx = 0;
			String point = "";
			while(rs.next()) {
				point = "";
				idx = 0;
				item = new Object[size];
				
				item[idx++] = rs.getString("IC_NAME");
				item[idx++] = rs.getString("CNT_P");
				item[idx++] = rs.getString("CNT_D");
				
				//증감표시
				point = rs.getString("FACTOR_POINT");
				if("0".equals(point)){
					point = "";
				}else if("1".equals(point)){
					point = "▲";
				}else if ("-1".equals(point)){
					point = "▼";
				}
				item[idx++] = point + rs.getString("FACTOR_CNT");
				item[idx++] = point + rs.getString("FACTOR_PER");
				
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
    
    public ArrayList<HashMap<String, String>> getRelationKeywordList(String type, String code, String sDate , String eDate, String psDate, String peDate, String settingCode, String sg_code, String orderBy){
    	ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    	String ORDER = "CNT_D";
    	//정렬기준 (0건수, 1:증가율)
    	if("1".equals(orderBy)){
    		ORDER = "FACTOR_PER";
    	}
    	
    	String[] type_code = settingCode.split("@");
    	String[] tmp_type_code = new String[2];
    	

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();    
		                                                                                                                                                
			sb = new StringBuffer();                                                                                                                    
			sb.append("\n").append("  SELECT A.RK_NAME                                                                                                  ");
			sb.append("\n").append("    , IFNULL(A.CNT_D,0)AS CNT_D                                                                                                       ");
			sb.append("\n").append("    , IFNULL(B.CNT_P,0)AS CNT_P                                                                                                     ");
			sb.append("\n").append("    , IFNULL(ABS(B.CNT_P - A.CNT_D),0) AS FACTOR_CNT /*증감 건수*/                                                      ");
			sb.append("\n").append("    , IF((B.CNT_P - A.CNT_D) = 0, 0, IF((B.CNT_P - A.CNT_D) > 0, -1, 1)) AS FACTOR_POINT /*증감 화살표*/                 ");
			sb.append("\n").append("    , ROUND(IFNULL((ABS(B.CNT_P - A.CNT_D) / B.CNT_P * 100),0),1) AS FACTOR_PER /*증감율*/                             ");
			sb.append("\n").append("   FROM(SELECT RK.RK_NAME, IFNULL(COUNT(*),0) AS CNT_D                                                                         ");
			sb.append("\n").append("        ,RK.RK_SEQ                                                                                                  ");
			sb.append("\n").append("        FROM RELATION_KEYWORD_R RK                                                                                  ");
			sb.append("\n").append("        INNER JOIN RELATION_KEYWORD_MAP MAP ON RK.RK_SEQ = MAP.RK_SEQ                                               ");
			sb.append("\n").append("        INNER JOIN ISSUE_DATA A ON A.ID_SEQ = MAP.ID_SEQ                                                            ");
			sb.append("\n").append("        INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ = C.ID_SEQ                                                         ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					sb.append("\n").append("           INNER JOIN ISSUE_DATA_CODE C"+i+"  ON A.ID_SEQ = C"+i+".ID_SEQ                                                                     ");
				}
			}
			sb.append("\n").append("        WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'/*당기*/                               ");
			sb.append("\n").append("             	 AND C.IC_CODE = "+code+"                                                                                       ");
			sb.append("\n").append("             	 AND C.IC_TYPE = "+type+"                                                                                      ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					tmp_type_code = type_code[i].split(",");
					System.out.println(tmp_type_code[0]);
					sb.append("\n").append("             AND C"+i+".IC_CODE = "+tmp_type_code[1]+"                                                                                       ");
					sb.append("\n").append("             AND C"+i+".IC_TYPE = "+tmp_type_code[0]+"                                                                                      ");
				}
			}
			sb.append("\n").append("".equals(sg_code)?"":" AND A.SG_SEQ = "+sg_code+"                                                                     ");
			sb.append("\n").append("   GROUP BY MAP.RK_SEQ                                                                                              ");
			sb.append("\n").append("   )A LEFT OUTER JOIN (                                                                                             ");
			sb.append("\n").append("     SELECT RK.RK_NAME, IFNULL(COUNT(*),0) AS CNT_P                                                                            ");
			sb.append("\n").append("          ,RK.RK_SEQ                                                                                            	");
			sb.append("\n").append("          FROM RELATION_KEYWORD_R RK                                                                                ");
			sb.append("\n").append("         INNER JOIN RELATION_KEYWORD_MAP MAP ON RK.RK_SEQ = MAP.RK_SEQ                                             ");
			sb.append("\n").append("          INNER JOIN ISSUE_DATA A ON A.ID_SEQ = MAP.ID_SEQ                                                          ");
			sb.append("\n").append("          INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ = C.ID_SEQ                                                       ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					sb.append("\n").append("           INNER JOIN ISSUE_DATA_CODE C"+i+"  ON A.ID_SEQ = C"+i+".ID_SEQ                                                                     ");
				}
			}
			sb.append("\n").append("          WHERE A.MD_DATE BETWEEN '"+psDate+" 00:00:00' AND '"+peDate+" 23:59:59'/*당기*/                             ");
			sb.append("\n").append("             	 AND C.IC_CODE = "+code+"                                                                                       ");
			sb.append("\n").append("             	 AND C.IC_TYPE = "+type+"                                                                                      ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					tmp_type_code = type_code[i].split(",");
					System.out.println(tmp_type_code[0]);
					sb.append("\n").append("             AND C"+i+".IC_CODE = "+tmp_type_code[1]+"                                                                                       ");
					sb.append("\n").append("             AND C"+i+".IC_TYPE = "+tmp_type_code[0]+"                                                                                      ");
				}
			}
			sb.append("\n").append("".equals(sg_code)?"":" AND A.SG_SEQ = "+sg_code+"                                                                     ");
			sb.append("\n").append("     GROUP BY MAP.RK_SEQ                                                                                            ");
			sb.append("\n").append("   )B ON A.RK_SEQ = B.RK_SEQ                                                                                        ");
			sb.append("\n").append("     ORDER BY "+ORDER+" DESC                                                                                          ");
			
			
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
				
				item.put("CNT_D", rs.getString("CNT_D"));
				item.put("CNT_P", rs.getString("CNT_P"));
				
				item.put("IC_NAME", rs.getString("RK_NAME"));
				item.put("FACTOR_PER", rs.getString("FACTOR_PER"));
				
				
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
    
    public ArrayList<Object[]> getRelationKeywordList_excel(String type, String code, String sDate , String eDate, String psDate, String peDate, String settingCode, String sg_code, String orderBy, int size){
    	ArrayList<Object[]> result = new ArrayList<Object[]>();
    	String ORDER = "CNT_D";
    	//정렬기준 (0건수, 1:증가율)
    	if("1".equals(orderBy)){
    		ORDER = "FACTOR_PER";
    	}
    	
    	String[] type_code = settingCode.split("@");
    	String[] tmp_type_code = new String[2];
    	

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();    
		                                                                                                                                                
			sb = new StringBuffer();                                                                                                                    
			sb.append("\n").append("  SELECT A.RK_NAME                                                                                                  ");
			sb.append("\n").append("    , IFNULL(A.CNT_D,0)AS CNT_D                                                                                                       ");
			sb.append("\n").append("    , IFNULL(B.CNT_P,0)AS CNT_P                                                                                                     ");
			sb.append("\n").append("    , IFNULL(ABS(B.CNT_P - A.CNT_D),0) AS FACTOR_CNT /*증감 건수*/                                                      ");
			sb.append("\n").append("    , IF((B.CNT_P - A.CNT_D) = 0, 0, IF((B.CNT_P - A.CNT_D) > 0, -1, 1)) AS FACTOR_POINT /*증감 화살표*/                 ");
			sb.append("\n").append("    , ROUND(IFNULL((ABS(B.CNT_P - A.CNT_D) / B.CNT_P * 100),0),1) AS FACTOR_PER /*증감율*/                             ");
			sb.append("\n").append("   FROM(SELECT RK.RK_NAME, IFNULL(COUNT(*),0) AS CNT_D                                                                         ");
			sb.append("\n").append("        ,RK.RK_SEQ                                                                                                  ");
			sb.append("\n").append("        FROM RELATION_KEYWORD_R RK                                                                                  ");
			sb.append("\n").append("        INNER JOIN RELATION_KEYWORD_MAP MAP ON RK.RK_SEQ = MAP.RK_SEQ                                               ");
			sb.append("\n").append("        INNER JOIN ISSUE_DATA A ON A.ID_SEQ = MAP.ID_SEQ                                                            ");
			sb.append("\n").append("        INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ = C.ID_SEQ                                                         ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					sb.append("\n").append("           INNER JOIN ISSUE_DATA_CODE C"+i+"  ON A.ID_SEQ = C"+i+".ID_SEQ                                                                     ");
				}
			}
			sb.append("\n").append("        WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'/*당기*/                               ");
			sb.append("\n").append("             	 AND C.IC_CODE = "+code+"                                                                                       ");
			sb.append("\n").append("             	 AND C.IC_TYPE = "+type+"                                                                                      ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					tmp_type_code = type_code[i].split(",");
					System.out.println(tmp_type_code[0]);
					sb.append("\n").append("             AND C"+i+".IC_CODE = "+tmp_type_code[1]+"                                                                                       ");
					sb.append("\n").append("             AND C"+i+".IC_TYPE = "+tmp_type_code[0]+"                                                                                      ");
				}
			}
			sb.append("\n").append("".equals(sg_code)?"":" AND A.SG_SEQ = "+sg_code+"                                                                     ");
			sb.append("\n").append("   GROUP BY MAP.RK_SEQ                                                                                              ");
			sb.append("\n").append("   )A LEFT OUTER JOIN (                                                                                             ");
			sb.append("\n").append("     SELECT RK.RK_NAME, IFNULL(COUNT(*),0) AS CNT_P                                                                            ");
			sb.append("\n").append("          ,RK.RK_SEQ                                                                                            	");
			sb.append("\n").append("          FROM RELATION_KEYWORD_R RK                                                                                ");
			sb.append("\n").append("         INNER JOIN RELATION_KEYWORD_MAP MAP ON RK.RK_SEQ = MAP.RK_SEQ                                             ");
			sb.append("\n").append("          INNER JOIN ISSUE_DATA A ON A.ID_SEQ = MAP.ID_SEQ                                                          ");
			sb.append("\n").append("          INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ = C.ID_SEQ                                                       ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					sb.append("\n").append("           INNER JOIN ISSUE_DATA_CODE C"+i+"  ON A.ID_SEQ = C"+i+".ID_SEQ                                                                     ");
				}
			}
			sb.append("\n").append("          WHERE A.MD_DATE BETWEEN '"+psDate+" 00:00:00' AND '"+peDate+" 23:59:59'/*당기*/                             ");
			sb.append("\n").append("             	 AND C.IC_CODE = "+code+"                                                                                       ");
			sb.append("\n").append("             	 AND C.IC_TYPE = "+type+"                                                                                      ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					tmp_type_code = type_code[i].split(",");
					System.out.println(tmp_type_code[0]);
					sb.append("\n").append("             AND C"+i+".IC_CODE = "+tmp_type_code[1]+"                                                                                       ");
					sb.append("\n").append("             AND C"+i+".IC_TYPE = "+tmp_type_code[0]+"                                                                                      ");
				}
			}
			sb.append("\n").append("".equals(sg_code)?"":" AND A.SG_SEQ = "+sg_code+"                                                                     ");
			sb.append("\n").append("     GROUP BY MAP.RK_SEQ                                                                                            ");
			sb.append("\n").append("   )B ON A.RK_SEQ = B.RK_SEQ                                                                                        ");
			sb.append("\n").append("     ORDER BY "+ORDER+" DESC                                                                                          ");
			
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			Object[] item = new Object[size];
			int idx = 0;
			String point = "";
			while(rs.next()) {
				point = "";
				item = new Object[size];
				idx = 0;
				
				item[idx++] = rs.getString("RK_NAME");
				item[idx++] = rs.getString("CNT_P");
				item[idx++] = rs.getString("CNT_D");
				
				//증감표시
				point = rs.getString("FACTOR_POINT");
				if("0".equals(point)){
					point = "";
				}else if("1".equals(point)){
					point = "▲";
				}else if ("-1".equals(point)){
					point = "▼";
				}
				item[idx++] = point + rs.getString("FACTOR_CNT");
				item[idx++] = point + rs.getString("FACTOR_PER");
				
				
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
    
    public ArrayList<HashMap<String, String>> getEachTypeDataList(String type, String code,String  type2, String code2, String sDate, String  eDate, String psDate, String peDate, String settingCode, String sg_code){
    	ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

    	String[] type_code = settingCode.split("@");
    	String[] tmp_type_code = new String[2];
    	String diff = "7";

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();   
			
			sb = new StringBuffer();   
			sb.append("SELECT TIMESTAMPDIFF( DAY, '"+sDate+" 00:00:00', '"+eDate+" 00:00:00')+1 AS DIFF   \n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if( rs.next() ) {
				diff= rs.getString("DIFF");
			}      
			
			sb = new StringBuffer();
			sb.append("\n").append("SELECT A.MD_DATE                                                                                          ");
			sb.append("\n").append(", IFNULL(B.CNT,0) AS CNT                                                                                  ");
			sb.append("\n").append("FROM (                                                                                                    ");
			sb.append("\n").append("    SELECT  DATE_FORMAT(DATE_ADD('"+sDate+" 00:00:00', interval @ROW:=@ROW+1 DAY),'%Y-%m-%d') AS MD_DATE ");
			sb.append("\n").append("      FROM ISSUE_DATA A, (SELECT @ROW:= -1)R ORDER BY MD_DATE 			                                  ");
			sb.append("\n").append("      LIMIT "+diff+" 	                                  ");
			sb.append("\n").append("      )A LEFT OUTER JOIN (                                                                                ");
			sb.append("\n").append(" SELECT  DATE_FORMAT(A.MD_DATE,'%Y-%m-%d') AS 'MD_DATE'                                                     ");
			sb.append("\n").append("              ,IFNULL(COUNT(*),0) AS CNT                                                                 ");
			sb.append("\n").append("                FROM ISSUE_DATA A                                                                        ");
			sb.append("\n").append("                    INNER JOIN ISSUE_DATA_CODE B ON A.ID_SEQ = B.ID_SEQ                                  ");
			sb.append("\n").append("                    INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ = C.ID_SEQ                                  ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					sb.append("\n").append("           INNER JOIN ISSUE_DATA_CODE C"+i+"  ON A.ID_SEQ = C"+i+".ID_SEQ                                                                     ");
				}
			}
			sb.append("\n").append("               WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'/*당기*/             ");
			sb.append("\n").append("                 AND A.ID_SEQ = B.ID_SEQ                                                                 ");
			sb.append("\n").append("                 AND B.IC_TYPE = "+type+"                                                                      ");
			sb.append("\n").append("                 AND B.IC_CODE = "+code+"                                                                     ");
			sb.append("\n").append("                 AND C.IC_TYPE = "+type2+"                                                                       ");
			sb.append("\n").append("                 AND C.IC_CODE = "+code2+"                                                                      ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					tmp_type_code = type_code[i].split(",");
					System.out.println(tmp_type_code[0]);
					sb.append("\n").append("             AND C"+i+".IC_CODE = "+tmp_type_code[1]+"                                                                                       ");
					sb.append("\n").append("             AND C"+i+".IC_TYPE = "+tmp_type_code[0]+"                                                                                      ");
				}
			}
			sb.append("\n").append("".equals(sg_code)?"":" AND A.SG_SEQ = "+sg_code+"                                                                     ");
			sb.append("\n").append("  GROUP BY DATE_FORMAT(A.MD_DATE,'%Y-%m-%d')                                                             ");
			sb.append("\n").append(") B ON A.MD_DATE = B.MD_DATE                                                             ");
				
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			HashMap<String, String> item = new HashMap<String, String>();
			
			while(rs.next()) {
				item = new HashMap<String, String>();
				item.put("category", rs.getString("MD_DATE"));
				item.put("CNT", rs.getString("CNT"));
				System.out.println();
				
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
    
    
    public ArrayList<Object[]> getEachTypeDataList_excel(String type, String code,String  type2, String code2, String sDate, String  eDate, String psDate, String peDate, String settingCode, String sg_code, int size){
    	ArrayList<Object[]> result = new ArrayList<Object[]>();

    	String[] type_code = settingCode.split("@");
    	String[] tmp_type_code = new String[2];
    	String diff = "7";

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();   
			
			sb = new StringBuffer();   
			sb.append("SELECT TIMESTAMPDIFF( DAY, '"+sDate+" 00:00:00', '"+eDate+" 00:00:00')+1 AS DIFF   \n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if( rs.next() ) {
				diff= rs.getString("DIFF");
			}      
			
			sb = new StringBuffer();
			sb.append("\n").append("SELECT A.MD_DATE                                                                                          ");
			sb.append("\n").append(", IFNULL(B.CNT,0) AS CNT                                                                                  ");
			sb.append("\n").append("FROM (                                                                                                    ");
			sb.append("\n").append("    SELECT  DATE_FORMAT(DATE_ADD('"+sDate+" 00:00:00', interval @ROW:=@ROW+1 DAY),'%Y-%m-%d') AS MD_DATE ");
			sb.append("\n").append("      FROM ISSUE_DATA A, (SELECT @ROW:= -1)R ORDER BY MD_DATE 			                                  ");
			sb.append("\n").append("      LIMIT "+diff+" 	                                  ");
			sb.append("\n").append("      )A LEFT OUTER JOIN (                                                                                ");
			sb.append("\n").append(" SELECT  DATE_FORMAT(A.MD_DATE,'%Y-%m-%d') AS 'MD_DATE'                                                     ");
			sb.append("\n").append("              ,IFNULL(COUNT(*),0) AS CNT                                                                 ");
			sb.append("\n").append("                FROM ISSUE_DATA A                                                                        ");
			sb.append("\n").append("                    INNER JOIN ISSUE_DATA_CODE B ON A.ID_SEQ = B.ID_SEQ                                  ");
			sb.append("\n").append("                    INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ = C.ID_SEQ                                  ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					sb.append("\n").append("           INNER JOIN ISSUE_DATA_CODE C"+i+"  ON A.ID_SEQ = C"+i+".ID_SEQ                                                                     ");
				}
			}
			sb.append("\n").append("               WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'/*당기*/             ");
			sb.append("\n").append("                 AND A.ID_SEQ = B.ID_SEQ                                                                 ");
			sb.append("\n").append("                 AND B.IC_TYPE = "+type+"                                                                      ");
			sb.append("\n").append("                 AND B.IC_CODE = "+code+"                                                                     ");
			sb.append("\n").append("                 AND C.IC_TYPE = "+type2+"                                                                       ");
			sb.append("\n").append("                 AND C.IC_CODE = "+code2+"                                                                      ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					tmp_type_code = type_code[i].split(",");
					System.out.println(tmp_type_code[0]);
					sb.append("\n").append("             AND C"+i+".IC_CODE = "+tmp_type_code[1]+"                                                                                       ");
					sb.append("\n").append("             AND C"+i+".IC_TYPE = "+tmp_type_code[0]+"                                                                                      ");
				}
			}
			sb.append("\n").append("".equals(sg_code)?"":" AND A.SG_SEQ = "+sg_code+"                                                                     ");
			sb.append("\n").append("  GROUP BY DATE_FORMAT(A.MD_DATE,'%Y-%m-%d')                                                             ");
			sb.append("\n").append(") B ON A.MD_DATE = B.MD_DATE                                                             ");
				
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			Object[] item = new Object[size];
			int idx = 0;
			
			while(rs.next()) {
				idx = 0;
				item = new Object[size];
				item[idx++] = rs.getString("MD_DATE");
				item[idx++] = rs.getString("CNT");
				System.out.println();
				
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
    
    public ArrayList<HashMap<String, String>> getRowDataList(String type, String code,String  type2, String code2, String sDate, String  eDate, String psDate, String peDate, String settingCode, String sg_code, int SLimit, String[] printType){
    	ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

    	String[] type_code = settingCode.split("@");
    	String[] tmp_type_code = new String[2];
    	

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();     
			 
		                                                                                                                                                
			sb = new StringBuffer();                 
			sb.append("\n").append("SELECT A.ID_SEQ                                                     ");
			sb.append("\n").append("      , A.MD_SEQ                                                    ");
			sb.append("\n").append("      , A.ID_TITLE                                                  ");
			sb.append("\n").append("      , A.ID_URL                                                    ");
			sb.append("\n").append("      , A.MD_DATE                                                   ");
			sb.append("\n").append("      , DATE_FORMAT(A.MD_DATE,'%Y-%m-%d %H:%i') AS SIMPLE_DATE                                                  ");
			sb.append("\n").append("      , A.ID_WRITTER                                                ");
			sb.append("\n").append("      , A.ID_CONTENT                                                ");
			sb.append("\n").append("      , A.S_SEQ	                                                ");
			sb.append("\n").append("      , A.MD_SITE_NAME	                                                ");
			for(int i=0; i<printType.length; i++){
				sb.append("\n").append("      , IFNULL(B"+printType[i]+".TYPE"+printType[i]+",'') AS TYPE"+printType[i]+"       ");
			}
			sb.append("\n").append("   FROM(                             ");
			sb.append("\n").append(" 			SELECT A.ID_SEQ, A.MD_SEQ, A.ID_TITLE, A.ID_URL, A.MD_DATE, A.ID_WRITTER, A.ID_CONTENT, A.S_SEQ, A.MD_SITE_NAME                                                                                         ");
			sb.append("\n").append("                 FROM ISSUE_DATA A                                                                  ");
			sb.append("\n").append("                    INNER JOIN ISSUE_DATA_CODE B ON A.ID_SEQ = B.ID_SEQ                             ");
			sb.append("\n").append("                    INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ = C.ID_SEQ                             ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					sb.append("\n").append("           INNER JOIN ISSUE_DATA_CODE C"+i+"  ON A.ID_SEQ = C"+i+".ID_SEQ                                                                     ");
				}
			}
			sb.append("\n").append("                WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'/*당기*/       ");          
			sb.append("\n").append("                  AND A.ID_USEYN = 'Y'                                                              ");
			sb.append("\n").append("".equals(sg_code)?"":" AND A.SG_SEQ = "+sg_code+"                                                                     ");
			sb.append("\n").append("                  AND B.IC_TYPE = "+type+"                                                                ");
			sb.append("\n").append("                  AND B.IC_CODE = "+code+"                                                                 ");
			sb.append("\n").append("                  AND C.IC_TYPE = "+type2+"                                                               ");
			sb.append("\n").append("                  AND C.IC_CODE = "+code2+"                                                                ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					tmp_type_code = type_code[i].split(",");
					System.out.println(tmp_type_code[0]);
					sb.append("\n").append("             AND C"+i+".IC_CODE = "+tmp_type_code[1]+"                                                                                       ");
					sb.append("\n").append("             AND C"+i+".IC_TYPE = "+tmp_type_code[0]+"                                                                                      ");
				}
			}			
			sb.append("\n").append("       LIMIT "+SLimit+",10                                                                ");
			sb.append("\n").append(")A  LEFT OUTER JOIN (                                                              ");
			
			for(int i=0; i<printType.length; i++){
			  if(i!=0){
				sb.append("\n").append(" LEFT OUTER JOIN (                                                              ");
			  }
				sb.append("\n").append("      SELECT  FN_ISSUE_NAME(IC_CODE, IC_TYPE)AS 'TYPE"+printType[i]+"', ID_SEQ       ");                      
				sb.append("\n").append("            FROM  ISSUE_DATA_CODE                                        ");  
				sb.append("\n").append("              WHERE IC_TYPE = "+printType[i]+"                                             ");                                                                                                                                            
				sb.append("\n").append(" )B"+printType[i]+" ON A.ID_SEQ = B"+printType[i]+".ID_SEQ                                ");
			}			
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			HashMap<String, String> item = new HashMap<String, String>();
			
			while(rs.next()) {
				item = new HashMap<String, String>();
				item.put("ID_SEQ", rs.getString("ID_SEQ"));
				item.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
				item.put("MD_SEQ", rs.getString("MD_SEQ"));
				item.put("TITLE", rs.getString("ID_TITLE"));
				item.put("URL", rs.getString("ID_URL"));
				item.put("MD_DATE", rs.getString("MD_DATE"));
				item.put("SIMPLE_DATE", rs.getString("SIMPLE_DATE"));
				item.put("WRITTER", rs.getString("ID_WRITTER"));
				item.put("S_SEQ", rs.getString("S_SEQ"));				
				for(int i=0; i<printType.length; i++){
					item.put("TYPE"+printType[i], rs.getString("TYPE"+printType[i]));
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
    
    
    public ArrayList<Object[]> getRowDataList_excel(String type, String code,String  type2, String code2, String sDate, String  eDate, String psDate, String peDate, String settingCode, String sg_code, String[] printType, int size){
    	ArrayList<Object[]> result = new ArrayList<Object[]>();

    	String[] type_code = settingCode.split("@");
    	String[] tmp_type_code = new String[2];
    	

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();     
			 
		                                                                                                                                                
			sb = new StringBuffer();                 
			sb.append("\n").append("SELECT A.ID_SEQ                                                     ");
			sb.append("\n").append("      , A.MD_SEQ                                                    ");
			sb.append("\n").append("      , A.ID_TITLE                                                  ");
			sb.append("\n").append("      , A.ID_URL                                                    ");
			sb.append("\n").append("      , A.MD_DATE                                                   ");
			sb.append("\n").append("      , DATE_FORMAT(A.MD_DATE,'%Y-%m-%d %H:%i') AS SIMPLE_DATE                                                  ");
			sb.append("\n").append("      , A.ID_WRITTER                                                ");
			sb.append("\n").append("      , A.ID_CONTENT                                                ");
			sb.append("\n").append("      , A.S_SEQ	                                                ");
			sb.append("\n").append("      , A.MD_SITE_NAME	                                                ");
			for(int i=0; i<printType.length; i++){
				sb.append("\n").append("      , IFNULL(B"+printType[i]+".TYPE"+printType[i]+",'') AS TYPE"+printType[i]+"       ");
			}
			sb.append("\n").append("   FROM(                             ");
			sb.append("\n").append(" 			SELECT A.ID_SEQ, A.MD_SEQ, A.ID_TITLE, A.ID_URL, A.MD_DATE, A.ID_WRITTER, A.ID_CONTENT, A.S_SEQ, A.MD_SITE_NAME                                                                                         ");
			sb.append("\n").append("                 FROM ISSUE_DATA A                                                                  ");
			sb.append("\n").append("                    INNER JOIN ISSUE_DATA_CODE B ON A.ID_SEQ = B.ID_SEQ                             ");
			sb.append("\n").append("                    INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ = C.ID_SEQ                             ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					sb.append("\n").append("           INNER JOIN ISSUE_DATA_CODE C"+i+"  ON A.ID_SEQ = C"+i+".ID_SEQ                                                                     ");
				}
			}
			sb.append("\n").append("                WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'/*당기*/       ");          
			sb.append("\n").append("                  AND A.ID_USEYN = 'Y'                                                              ");
			sb.append("\n").append("".equals(sg_code)?"":" AND A.SG_SEQ = "+sg_code+"                                                                     ");
			sb.append("\n").append("                  AND B.IC_TYPE = "+type+"                                                                ");
			sb.append("\n").append("                  AND B.IC_CODE = "+code+"                                                                 ");
			sb.append("\n").append("                  AND C.IC_TYPE = "+type2+"                                                               ");
			sb.append("\n").append("                  AND C.IC_CODE = "+code2+"                                                                ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					tmp_type_code = type_code[i].split(",");
					System.out.println(tmp_type_code[0]);
					sb.append("\n").append("             AND C"+i+".IC_CODE = "+tmp_type_code[1]+"                                                                                       ");
					sb.append("\n").append("             AND C"+i+".IC_TYPE = "+tmp_type_code[0]+"                                                                                      ");
				}
			}			
			sb.append("\n").append(")A  LEFT OUTER JOIN (                                                              ");
			
			for(int i=0; i<printType.length; i++){
			  if(i!=0){
				sb.append("\n").append(" LEFT OUTER JOIN (                                                              ");
			  }
				sb.append("\n").append("      SELECT  FN_ISSUE_NAME(IC_CODE, IC_TYPE)AS 'TYPE"+printType[i]+"', ID_SEQ       ");                      
				sb.append("\n").append("            FROM  ISSUE_DATA_CODE                                        ");  
				sb.append("\n").append("              WHERE IC_TYPE = "+printType[i]+"                                             ");                                                                                                                                            
				sb.append("\n").append(" )B"+printType[i]+" ON A.ID_SEQ = B"+printType[i]+".ID_SEQ                                ");
			}			
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			Object[] item = new Object[size];
			int idx = 0;
			
			while(rs.next()) {
				idx = 0;
				item = new Object[size];				
				item[idx++] = rs.getString("MD_SITE_NAME");
				item[idx++] = rs.getString("ID_TITLE");
				item[idx++] = rs.getString("ID_URL");
				item[idx++] = rs.getString("MD_DATE");
				for(int i=0; i<printType.length; i++){
					item[idx++] =  rs.getString("TYPE"+printType[i]);
				}
				item[idx++] = rs.getString("ID_WRITTER");
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
    
    public int  getRowDataCount(String type, String code,String  type2, String code2, String sDate, String  eDate, String psDate, String peDate, String settingCode, String sg_code){
    	int result = 0;

    	String[] type_code = settingCode.split("@");
    	String[] tmp_type_code = new String[2];
    	

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();                                                                                                              
		                                                                                                                                                
			sb = new StringBuffer();                 
			sb.append("\n").append(" SELECT COUNT(*) AS CNT                                                                                         ");
			sb.append("\n").append("                 FROM ISSUE_DATA A                                                                  ");
			sb.append("\n").append("                    INNER JOIN ISSUE_DATA_CODE B ON A.ID_SEQ = B.ID_SEQ                             ");
			sb.append("\n").append("                    INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ = C.ID_SEQ                             ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					sb.append("\n").append("           INNER JOIN ISSUE_DATA_CODE C"+i+"  ON A.ID_SEQ = C"+i+".ID_SEQ                                                                     ");
				}
			}
			sb.append("\n").append("                WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'/*당기*/       ");          
			sb.append("\n").append("                  AND A.ID_USEYN = 'Y'                                                              ");
			sb.append("\n").append("".equals(sg_code)?"":" AND A.SG_SEQ = "+sg_code+"                                                                     ");
			sb.append("\n").append("                  AND B.IC_TYPE = "+type+"                                                                ");
			sb.append("\n").append("                  AND B.IC_CODE = "+code+"                                                                 ");
			sb.append("\n").append("                  AND C.IC_TYPE = "+type2+"                                                               ");
			sb.append("\n").append("                  AND C.IC_CODE = "+code2+"                                                                ");
			if(null != settingCode && !"".equals(settingCode)){
				for(int i =0; i<type_code.length; i++){
					tmp_type_code = type_code[i].split(",");
					System.out.println(tmp_type_code[0]);
					sb.append("\n").append("             AND C"+i+".IC_CODE = "+tmp_type_code[1]+"                                                                                       ");
					sb.append("\n").append("             AND C"+i+".IC_TYPE = "+tmp_type_code[0]+"                                                                                      ");
				}
			}
			
			
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
    
	private void close() {
		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
		if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	}
    
	
	
}
