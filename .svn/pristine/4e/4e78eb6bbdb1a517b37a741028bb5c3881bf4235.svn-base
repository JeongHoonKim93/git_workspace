package risk.demon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.ConfigUtil;
import risk.util.DateUtil;
import risk.util.Log;

public class HOTELSHILLA_02_DailyProcess{
	
	/**
     *  로컬 DB 컨넥션
     * @return
     */
    static DBconn dbconn1 = null;
    static DBconn dbconn2 = null;
    static String name = "HOTELSHILLA_02_DailyProcess";
    static String addName = "";
    static String telegram_api_url = "http://telegramapi.realsn.com/rsnTelegramAPI.php";
   
    public static void main( String[] args ){
    	
    	Log.crond(name, name + " START ...");
   
    	boolean success = false; 
    	ConfigUtil cu = new ConfigUtil();
    	 
    	try{
    		
    		dbconn1 = new DBconn();
    		dbconn1.getSubDirectConnection();
    		
    		dbconn2 = new DBconn();
    		dbconn2.getLucyDirectConnection();

    		        	
           	Log.crond(name, "##META정보 삭제 Start.##");
        	delMeta(30);
        	Log.crond(name, "##META정보 삭제 End.##");
        	
        	Log.crond(name, "##TOP정보 삭제 Start.##");
        	delTop(365);
        	Log.crond(name, "##TOP정보 삭제 End.##");
        	
        	Log.crond(name, "##REPLY정보 삭제 Start.##");
        	delReply(30);
        	Log.crond(name, "##REPLY정보 삭제 End.##");
        	
        	Log.crond(name, "##EX META정보 삭제 Start.##");
        	delExMeta(6);
        	Log.crond(name, "##EX META정보 삭제 End.##");
        	
        	Log.crond(name, "##일반 키워드 통계 갱신 Start.##");
        	//AddStatisticsKeyword();
        	Log.crond(name, "##일반 키워드 통계 갱신 End.##");
        	
        	
        	Log.crond(name, "##사이트 갱신 Start.##");
        	String siteKey = getApiKey("SITE");
        	String apiUrl = getApiUrl();
        	ArrayList<String[]> siteData = getSiteMaster(siteKey, apiUrl);
        	Log.crond(name, "사이트 카운트 : " + siteData.size());
        	if(siteData.size()>0) { //에러로그 없이, 데이터 반환이 없는 경우
        		insertSite(siteData);
        	}
        	Log.crond(name, "##사이트 갱신 End.##");
        	
        	Log.crond(name, "##관계 키워드 데이터 갱신 Start.##");
        	ArrayList<String[]> relKeyword = SelectRelationKeyword();
        	//InsertRelationKeyword(relKeyword);
        	Log.crond(name, "##관계 키워드 데이터 갱신 End.##");
        	
        	Log.crond(name, "##긍정 키워드 데이터 갱신 Start.##");
        	ArrayList<String[]> posKeyword = SelectSentiKeyword("POSITIVE");
        	//InsertSentiKeyword("POSITIVE",posKeyword);
        	Log.crond(name, "##긍정 키워드 데이터 갱신 End.##");

        	Log.crond(name, "##부정 키워드 데이터 갱신 Start.##");
        	ArrayList<String[]> negKeyword = SelectSentiKeyword("NEGATIVE");
        	//InsertSentiKeyword("NEGATIVE", negKeyword);
        	Log.crond(name, "##부정 키워드 데이터 갱신 End.##");
			
        	
        	/*
        	Log.crond(name, "##일반 사이트 통계 갱신 Start.##");
        	StatisticsSite("");
        	Log.crond(name, "##일반 사이트 통계 갱신 End.##");
        	*/
        	
        	/*
        	Log.crond(name, "##전체제외 키워드 통계 갱신 Start.##");
        	StatisticsKeyword("EXCEPTION_");
        	Log.crond(name, "##전체제외 키워드 통계 갱신 End.##");
        	
        	Log.crond(name, "##전체제외키워드 사이트 통계 갱신 Start.##");
        	StatisticsSite("EXCEPTION_");
        	Log.crond(name, "##전체제외키워드 사이트 통계 갱신 End.##");
        	*/
        	
        	success = true;
        	
    	}catch(Exception ex) {
    		Log.writeExpt(name,ex.getMessage());
        	ex.printStackTrace();
        }finally {
        	
			try {
				if(!success){
					
					//텔레그램 발송
					callTelegramAPI("ID", "-287324110", URLEncoder.encode("[호텔신라] "+name+" 프로세스오류 ","UTF-8"));
				}
			} catch (UnsupportedEncodingException e) {
				Log.writeExpt(name,e.getMessage());
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
        	
            if (dbconn1 != null) try { dbconn1.close(); } catch(SQLException ex) {}
            if (dbconn2 != null) try { dbconn2.close(); } catch(SQLException ex) {}
        }
    	
    	Log.crond(name, name + " END ...");
    }
    
    public static String callTelegramAPI(String option, String senderKey, String message){
    	String returnData = "";
        StringBuffer param = new StringBuffer();
        param.append("type=sendRequest");
        if(option.equals("ID")){
        	param.append("&m_ch_id=" + senderKey);
        }else if(option.equals("ADDRESS")){
        	param.append("&m_phon=" + senderKey);
        }else if(option.equals("GROUP")){
        	param.append("&chat_group=" + senderKey);
        }
        param.append("&message=" + message);
        param.append("&key=08bf020fc865c5e1");
        //param.append("&preview=1");

        Log.crond(name,"[SEND]" + telegram_api_url+ "?" +  param);
    	returnData = GetHtmlPost(telegram_api_url, param.toString());
    	Log.crond(name,"[RECEIVE]" + returnData);
        return returnData;
    }
    
    
    static String getApiKey(String apiName){
    	
    	String result = "";
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
        	sb = new StringBuffer();
        	sb.append("#체널조회\n");
    		sb.append("SELECT CH_SEQ, CH_KEY, CH_API_KEY, CH_LAST_DATE, CH_LAST_DOCID, CH_LIST_CNT, 0 AS IDX, 0 AS MAX_IDX FROM CHANNEL WHERE CH_USEYN = 'Y' AND CH_TYPE = 2 AND CH_KEY LIKE '%"+apiName+"%' ORDER BY CH_SEQ \n");
        	Log.crond(name, sb.toString());
        	rs = stmt.executeQuery( sb.toString());
        	
        	if(rs.next()) {
        		result = rs.getString("CH_API_KEY");
        	}
        	
        	
						
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static String getApiUrl(){
    	
    	String result = "";
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
        	sb = new StringBuffer();
        	sb.append("#체널조회\n");
        	String pKey = "";
        	if(dbconn1.getCONST_MYSQL_SUBDB_URL().equals("localhost")){
        		pKey = "apiUrl";
        	}else {
        		pKey = "testApiUrl";	
        	}        	
        	sb.append("SELECT p_value FROM PROPERTY WHERE P_KEY = '"+pKey+"' \n");
        	
    		
    		Log.crond(name, sb.toString());
        	rs = stmt.executeQuery( sb.toString());
        	
        	if(rs.next()) {
        		result = rs.getString("p_value");
        	}
        	
        	
						
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
   
    //보관함 오래된 META삭제
   static String delStoMeta(int minusDate){
    	
    	String result = "";
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	
        	stmt = dbconn1.createStatement();
        	
        	
        	sb = new StringBuffer();
     
        	sb.append("SELECT MD_SEQ FROM META WHERE MD_DATE < TIMESTAMPADD(DAY, -"+minusDate+", '"+getCurrDate()+" 00:00:00') ORDER BY MD_SEQ DESC LIMIT 1\n");
        	Log.crond(name, sb.toString());
        	rs = stmt.executeQuery( sb.toString());
        	String md_seq = "";
        	if(rs.next()) {
        		md_seq = rs.getString("MD_SEQ");
        	}
        	
        	if(!md_seq.equals("")) {
        		sb = new StringBuffer();
            	sb.append("DELETE  FROM MAP_META_STORAGE  	\n");
            	sb.append("WHERE   MD_SEQ <=  "+md_seq+"	\n");

            	Log.crond(name, sb.toString());
            	stmt.executeUpdate( sb.toString());
        	}       	
   	      				
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    
    
    static String delMeta(int minusDate){
    	
    	String result = "";
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	
        	stmt = dbconn1.createStatement();
        	
        	
        	sb = new StringBuffer();
        	sb.append("SELECT MD_SEQ FROM META WHERE MD_DATE < TIMESTAMPADD(DAY, -"+minusDate+", '"+getCurrDate()+" 00:00:00') ORDER BY MD_SEQ DESC LIMIT 1\n");
        	Log.crond(name, sb.toString());
        	rs = stmt.executeQuery( sb.toString());
        	String md_seq = "";
        	if(rs.next()) {
        		md_seq = rs.getString("MD_SEQ");
        	}
        	
        	if(!md_seq.equals("")) {
        		sb = new StringBuffer();
            	sb.append("DELETE FROM META_SEQ WHERE MD_SEQ <=  "+md_seq+"\n");
            	Log.crond(name, sb.toString());
            	stmt.executeUpdate( sb.toString());
            	
            	
            	sb = new StringBuffer();
            	sb.append("DELETE FROM IDX WHERE MD_SEQ <=  "+md_seq+"\n");
            	Log.crond(name, sb.toString());
            	stmt.executeUpdate( sb.toString());
            	
            	sb = new StringBuffer();
            	sb.append("DELETE FROM IDX_CATEGORY WHERE MD_SEQ <=  "+md_seq+"\n");
            	Log.crond(name, sb.toString());
            	stmt.executeUpdate( sb.toString());
            	
            	sb = new StringBuffer();
            	sb.append("DELETE FROM TRANS_DATA WHERE MD_SEQ <=  "+md_seq+"\n");
            	Log.crond(name, sb.toString());
            	stmt.executeUpdate( sb.toString());
            	
            	sb = new StringBuffer();
            	sb.append("DELETE FROM META WHERE MD_SEQ <=  "+md_seq+"\n");
            	Log.crond(name, sb.toString());
            	stmt.executeUpdate( sb.toString());	
        	}
        	
        	
        				
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    
    
    
 static String delWarning(int minusDate){
    	
    	String result = "";
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	
        	stmt = dbconn1.createStatement();
        	
        	
        	sb = new StringBuffer();
        	sb.append("SELECT MD_SEQ FROM WARNING WHERE MD_DATE < TIMESTAMPADD(DAY, -"+minusDate+", '"+getCurrDate()+" 00:00:00') ORDER BY MD_SEQ DESC LIMIT 1\n");
        	Log.crond(name, sb.toString());
        	rs = stmt.executeQuery( sb.toString());
        	String md_seq = "";
        	if(rs.next()) {
        		md_seq = rs.getString("MD_SEQ");
        	}
        	
        	if(!md_seq.equals("")) {
        		sb = new StringBuffer();
            	sb.append("DELETE FROM META_SEQ WHERE MD_SEQ <=  "+md_seq+"\n");
            	Log.crond(name, sb.toString());
            	stmt.executeUpdate( sb.toString());
            	
            	
            	sb = new StringBuffer();
            	sb.append("DELETE FROM IDX_WARNING WHERE MD_SEQ <=  "+md_seq+"\n");
            	Log.crond(name, sb.toString());
            	stmt.executeUpdate( sb.toString());
            	
            	/*
            	sb = new StringBuffer();
            	sb.append("DELETE FROM IDX_CATEGORY WHERE MD_SEQ <=  "+md_seq+"\n");
            	Log.crond(name, sb.toString());
            	stmt.executeUpdate( sb.toString());
            	
            	sb = new StringBuffer();
            	sb.append("DELETE FROM TRANS_DATA WHERE MD_SEQ <=  "+md_seq+"\n");
            	Log.crond(name, sb.toString());
            	stmt.executeUpdate( sb.toString());
            	*/
            	
            	sb = new StringBuffer();
            	sb.append("DELETE FROM WARNING WHERE MD_SEQ <=  "+md_seq+"\n");
            	Log.crond(name, sb.toString());
            	stmt.executeUpdate( sb.toString());	
        	}
        	
        	
        				
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static String delExMeta(int minusDate){
    	
    	String result = "";
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	
        	stmt = dbconn1.createStatement();
        	sb = new StringBuffer();
        	sb.append("SELECT MD_SEQ FROM EXCEPTION_META WHERE MD_DATE < TIMESTAMPADD(DAY, -"+minusDate+", '"+getCurrDate()+" 00:00:00') ORDER BY MD_SEQ DESC LIMIT 1\n");
        	Log.crond(name, sb.toString());
        	rs = stmt.executeQuery( sb.toString());
        	String md_seq = "";
        	if(rs.next()) {
        		md_seq = rs.getString("MD_SEQ");
        	}
        	
        	if(!md_seq.equals("")) {
        		sb = new StringBuffer();
            	sb.append("DELETE FROM EXCEPTION_IDX WHERE MD_SEQ <=  "+md_seq+"\n");
            	Log.crond(name, sb.toString());
            	stmt.executeUpdate( sb.toString());
            	
            	
            	sb = new StringBuffer();
            	sb.append("DELETE FROM EXCEPTION_META WHERE MD_SEQ <=  "+md_seq+"\n");
            	Log.crond(name, sb.toString());
            	stmt.executeUpdate( sb.toString());	
        	}
        	
        	
        	
        				
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static String delReply(int minusDate){
    	
    	String result = "";
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	
        	stmt = dbconn1.createStatement();
        	sb = new StringBuffer();
        	sb.append("SELECT MD_SEQ FROM REPLY WHERE MD_DATE < TIMESTAMPADD(DAY, -"+minusDate+", '"+getCurrDate()+" 00:00:00') ORDER BY MD_SEQ DESC LIMIT 1\n");
        	Log.crond(name, sb.toString());
        	rs = stmt.executeQuery( sb.toString());
        	String md_seq = "";
        	if(rs.next()) {
        		md_seq = rs.getString("MD_SEQ");
        	}
        	
        	if(!md_seq.equals("")) {
        		sb = new StringBuffer();
            	sb.append("DELETE FROM REPLY WHERE MD_SEQ <=  "+md_seq+"\n");
            	Log.crond(name, sb.toString());
            	stmt.executeUpdate( sb.toString());	
        	}
        	
        	
        	
        	
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    
    /**
     * 현재 날짜 리턴 (년,월,일)
     * @param minusDate
     * @return
     */
	static String getCurrDate()
	{
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
		
		return sdf.format(new java.util.Date()); 
	}
    

	
	
	static ArrayList<String[]> getSiteMaster(String acessToken, String apiUrl){
    	
    	JSONObject json = null;
    	String[][] result = null;
    	String returnData = ""; 
    	
    	ArrayList<String[]> resultArr = new ArrayList<String[]>();
    	
    	DateUtil du = new DateUtil();
    	String today = du.getCurrentDate("yyyyMMdd");
    	
    	long startNum = 0;
    	
    	
    	//String apiUrl = "http://api.realsn.com:2025/json.php";
    	
        String param = "";
    	
    	String[] parsingKey = {
    			"COMMON.SITE.Seq",
    			"COMMON.SITE.Name",
    			"COMMON.SITE.Link",
    			"COMMON.SITE.Active",
    			"COMMON.SITE.Channel",
    			"COMMON.SITE.Lang3",
    			"COMMON.SITE.Country3",
    			"COMMON.SITE.Category",
    			"COMMON.SITE.Tag",
    			"COMMON.SITE.CreateStamp" 			
    			
    	};
        
        try{
        	while(true){
        		param = "";
            	param += "api_key=" + acessToken;
            	param += "&max_seq=" + startNum;
            	param += "&search_day=" + today;
            	param += "&list_count=500";
            	
            	Log.crond(name, "[SEND]" + apiUrl+ "?" +  param);
            	returnData = GetHtmlPost(apiUrl, param);
            	Log.crond(name, "[RECEIVE]");
            	
            	json = new JSONObject(returnData);
            	JSONArray jAr = new JSONArray();
            	JSONObject row_json = null;
            	if(!json.isNull("data")){
            		jAr = json.getJSONArray("data");
            		
            		if(jAr == null || jAr.length() <= 0){
            			break;
            		}
            		
            		result = new String[jAr.length()][parsingKey.length];
            		
            		for(int i=0; i< jAr.length(); i++){
            			row_json = jAr.getJSONObject(i);
            			
            			for (int j=0 ; j<parsingKey.length ; j++) {
    						if(!row_json.isNull(parsingKey[j])){
    							result[i][j] = row_json.getString(parsingKey[j]);
    						}else{ 
    							result[i][j] = "";	
    						}
    					}
            		}
            	}else if(!json.isNull("error")){
            		System.out.println(json.getJSONObject("error").getString("message"));
            		System.exit(1);
            	}else{
            		result = new String[0][16];
            	}
            	
            	for (int i=0 ; i<result.length; i++) {
            		resultArr.add(result[i]);
            		
            		startNum = Long.parseLong(result[i][0]);
				}
        	}
        	
        } catch(Exception ex) {
        	ex.printStackTrace();
			System.exit(1);
        } 
        
        return resultArr;
    }
	
	static void insertSite(ArrayList<String[]> site)
    {       
    	PreparedStatement pstmt = null;
        Statement stmt = null;
        StringBuffer sb = null;
        
        try{
			// SITE비운다.
			sb = new StringBuffer();
			sb.append("TRUNCATE TABLE SITE \n");
			stmt = dbconn1.createStatement();
			stmt.executeUpdate(sb.toString());

			// 전부입력.
			sb = new StringBuffer();
			
			
			
			sb.append("\n INSERT IGNORE INTO SITE (S_SEQ, S_NAME, S_URL, S_ACTIVE, S_CHANNEL, S_LANG, S_COUNTY, S_CODE1, S_TAG, S_REG_TIMESTAMP) 	\n");
			sb.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) 																\n");
			
			System.out.println( sb.toString() );
			pstmt = dbconn1.createPStatement(sb.toString());
			
			
			
			String[] data = null;
			for (int i = 0; i < site.size(); i++) {
				data = site.get(i);

				pstmt.clearParameters();
				for (int j=0 ; j<data.length ; j++) {
					pstmt.setString(j+1, data[j]);	
				}
				
				pstmt.execute();
			}                
                    
        } catch(SQLException ex) {
        	Log.crond(name, ex.toString());               
        } catch(Exception ex) {
        	Log.crond(name, ex.toString());
        } finally {
        	sb = null;
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }     
     }
	
	
	static String AddStatisticsKeyword(){
		
    	String result = "";
    	Statement stmt = null;
    	PreparedStatement pstmt = null;
    	
        ResultSet rs = null;
        StringBuffer sb = null;
        
        DateUtil du = new DateUtil();
        
        try{
        	stmt = dbconn1.createStatement();
        	String date = "";
        	
        	date = du.getDate("yyyyMMdd");
        	
        	if(!date.equals("")){
        		
        		date = du.addDay_v2(date, 1);

        		sb = new StringBuffer();
        		sb.append(" SELECT COUNT(*) AS CNT FROM META_ANA_KEYWORD WHERE MAK_DATE = '"+date.replaceAll("-", "")+"'\n");
        		Log.crond(name, sb.toString());
            	rs = stmt.executeQuery(sb.toString());
            	
            	int cnt = 0;
            	while(rs.next()){
            		cnt = rs.getInt("CNT");
            	}
            	
            	if(cnt == 0) {
            		sb = new StringBuffer();
            		//sb.append("INSERT INTO META_ANA_KEYWORD (MAK_DATE, K_XP, K_YP, K_ZP, K_VALUE, MAK_CNT)\n");
            		//sb.append("   (SELECT '"+date.replaceAll("-", "")+"' AS MAK_DATE, K_XP, K_YP, K_ZP, K_VALUE, 0 AS MAK_CNT FROM KEYWORD WHERE K_TYPE <= 10 ORDER BY K_XP, K_YP, K_ZP)\n");
            		sb.append("INSERT INTO META_ANA_KEYWORD (MAK_DATE, K_XP, K_YP, K_ZP, K_VALUE, MAK_CNT)(\n");
            		sb.append("    SELECT * \n");
            		sb.append("      FROM (\n");
            		sb.append("            (SELECT '"+date.replaceAll("-", "")+"' AS MAK_DATE, 0 AS K_XP, 0 AS K_YP, 0 AS K_ZP, '전체' AS K_VALUE, 0 AS MAK_CNT)\n");
            		sb.append("                    UNION ALL\n");
            		sb.append("            (SELECT '"+date.replaceAll("-", "")+"' AS MAK_DATE, K_XP, K_YP, K_ZP, K_VALUE, 0 AS MAK_CNT FROM KEYWORD WHERE K_TYPE <= 10 ORDER BY K_XP, K_YP, K_ZP)\n");  
            		sb.append("           )A\n");
            		sb.append(" )\n");
            		
            		
            		Log.crond(name, sb.toString());
            		stmt.executeUpdate(sb.toString());	
            	}
            	        		
        	}
        
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); pstmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
	



static String getCapitalK_XP(){
	
	String result = "";
	Statement stmt = null;
	PreparedStatement pstmt = null;
	
    ResultSet rs = null;
    StringBuffer sb = null;
    
    
    try{
    	stmt = dbconn1.createStatement();

		sb = new StringBuffer();                               
		sb.append(" SELECT GROUP_CONCAT(K_XP) AS K_XPS         \n");
		sb.append("   FROM KEYWORD_WARNING_CAPITAL                     \n");
		sb.append(" WHERE K_YP = 0                             \n");
		sb.append("   AND K_ZP = 0                             \n");
		sb.append("   AND K_USEYN = 'Y'                        \n");
		
    	rs = stmt.executeQuery(sb.toString());
    	
    	if(rs.next()){
    		result = rs.getString("K_XPS");
    	}
        	        		
    
    } catch(Exception ex) {
    	Log.writeExpt(name,ex.toString());			
    	ex.printStackTrace();
		System.exit(1);
		
    } finally {
    	sb = null;
        if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
        if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        if (pstmt != null) try { pstmt.close(); pstmt = null;} catch(SQLException ex) {}
    }
    
    return result;
}


static String getWaringK_XP(){
	
	String result = "";
	Statement stmt = null;
	PreparedStatement pstmt = null;
	
    ResultSet rs = null;
    StringBuffer sb = null;
    
    
    try{
    	stmt = dbconn1.createStatement();

		sb = new StringBuffer();                               
		sb.append(" SELECT GROUP_CONCAT(K_XP) AS K_XPS         \n");
		sb.append("   FROM KEYWORD_WARNING                     \n");
		sb.append(" WHERE K_YP = 0                             \n");
		sb.append("   AND K_ZP = 0                             \n");
		sb.append("   AND K_USEYN = 'Y'                        \n");
		
    	rs = stmt.executeQuery(sb.toString());
    	
    	if(rs.next()){
    		result = rs.getString("K_XPS");
    	}
        	        		
    
    } catch(Exception ex) {
    	Log.writeExpt(name,ex.toString());			
    	ex.printStackTrace();
		System.exit(1);
		
    } finally {
    	sb = null;
        if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
        if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        if (pstmt != null) try { pstmt.close(); pstmt = null;} catch(SQLException ex) {}
    }
    
    return result;
}
	

static String getKeywordK_XP(String tbName){
	
	String result = "";
	Statement stmt = null;
	PreparedStatement pstmt = null;
	
    ResultSet rs = null;
    StringBuffer sb = null;
    
    
    try{
    	stmt = dbconn1.createStatement();

		sb = new StringBuffer();                               
		sb.append(" SELECT GROUP_CONCAT(K_XP) AS K_XPS         \n");
		sb.append("   FROM "+tbName+" 			               \n");
		sb.append(" WHERE K_YP = 0                             \n");
		sb.append("   AND K_ZP = 0                             \n");
		sb.append("   AND K_USEYN = 'Y'                        \n");
		
    	rs = stmt.executeQuery(sb.toString());
    	
    	if(rs.next()){
    		result = rs.getString("K_XPS");
    	}
        	        		
    
    } catch(Exception ex) {
    	Log.writeExpt(name,ex.toString());			
    	ex.printStackTrace();
		System.exit(1);
		
    } finally {
    	sb = null;
        if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
        if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        if (pstmt != null) try { pstmt.close(); pstmt = null;} catch(SQLException ex) {}
    }
    
    return result;
}
	
	
	
	static String StatisticsSite(String exTable){
		
    	String result = "";
    	Statement stmt = null;
    	PreparedStatement pstmt = null;
    	
        ResultSet rs = null;
        StringBuffer sb = null;
        
        DateUtil du = new DateUtil();
        
        try{
        	stmt = dbconn1.createStatement();
        	String date = "";
        	
        	date = du.getDate("yyyyMMdd");
        	
        	if(!date.equals("")){
        		
        		date = du.addDay_v2(date, -1);
        		
        		sb = new StringBuffer();
        		sb.append("DELETE FROM "+exTable+"META_ANA_SITE WHERE MAS_DATE = '"+date.replaceAll("-", "")+"'\n");
        		Log.crond(name, sb.toString());
        		stmt.executeUpdate(sb.toString());
        		
        		sb = new StringBuffer();        		
        		sb.append("INSERT INTO 										\n");
        		sb.append(""+exTable+"META_ANA_SITE (MAS_DATE, S_SEQ, S_NAME, SG_SEQ, SG_NAME, MAS_CNT)\n");
        		sb.append("              (SELECT '"+date.replaceAll("-", "")+"'\n");
        		sb.append("                    , A.S_SEQ					\n");
        		sb.append("                    , C.S_NAME					\n");
        		sb.append("                    , D.SG_SEQ					\n");
        		sb.append("                    , D.SG_NAME					\n");
        		sb.append("                    , COUNT(*) AS CNT			\n");
        		sb.append("                 FROM "+exTable+"META A		\n");
        		sb.append("                    , SG_S_RELATION B				\n");
        		sb.append("                    , SITE C						\n");
        		sb.append("                    , SITE_GROUP D				\n");
        		sb.append("                WHERE A.MD_DATE BETWEEN '"+date+" 00:00:00' AND  '"+date+" 23:59:59'\n");
        		sb.append("                  AND A.S_SEQ = B.S_SEQ 			\n");
        		sb.append("                  AND B.S_SEQ = C.S_SEQ			\n");
        		sb.append("                  AND B.SG_SEQ = D.SG_SEQ		\n");
        		sb.append("                GROUP BY A.S_SEQ)				\n");
        				
        		
        		Log.crond(name, sb.toString());
            	stmt.executeUpdate(sb.toString());        		
        	}
        
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); pstmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
	
	static String GetHtmlPost(String sUrl, String param) {
		StringBuffer html = new StringBuffer();
		try {
			String line = null;
			URL aURL = new URL(sUrl);
			HttpURLConnection urlCon = (HttpURLConnection) aURL.openConnection();
			urlCon.setRequestMethod("POST");

			urlCon.setDoInput(true);
			urlCon.setDoOutput(true);
			OutputStream out = urlCon.getOutputStream();
			out.write(param.getBytes());
			out.flush();

			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(urlCon.getInputStream(),"utf-8"));
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(aURL.openStream()));
			while ((line = br.readLine()) != null) {
				html.append(line);
			}

			aURL = null;
			urlCon = null;
			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.writeExpt(name, "MalformedURLException : " + e);
			// 프로세스 종료
			// System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			Log.writeExpt(name, "IOException :" + e);
			// 프로세스 종료
			// System.exit(1);
		}
		return html.toString();
	}
	
	//테이블 데이터 삭제
	static String TruncateTable(String tbName) {
		String result = "";
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try {
			stmt = dbconn1.createStatement();
			
			sb = new StringBuffer();
			
			sb.append("TRUNCATE TABLE AUTO_"+tbName+"_KEYWORD	\n");
			
			Log.crond(name, sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
		} catch(Exception e) {
        	Log.writeExpt(name,e.toString());			
        	e.printStackTrace();
			System.exit(1);
		} finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); pstmt = null;} catch(SQLException ex) {}
		}
		
		return result;
	}
	
	//RelationKeyword select
	static ArrayList<String[]> SelectRelationKeyword() {
		ArrayList<String[]> relKeyword = new ArrayList<String[]>();
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try {
			
			stmt = dbconn2.createStatement();
			sb = new StringBuffer();
			
			sb.append("SELECT DISTINCT B.PAT_SEQ 											\n");
			sb.append("				  , B.WORD_NM 													\n");
			sb.append("		 FROM ANA_PATTERN_INFO_V3_SYSTEM_IDX_2 A 							\n");
			sb.append(" 		  , ANA_PATTERN_INFO_V3_2 B										\n");
			sb.append("		 WHERE A.PAT_SEQ = B.PAT_SEQ 										\n");
			sb.append("		 AND A.SYSTEMKEY = 1  												\n");
			sb.append("		 AND A.IC_TYPE = 3 												\n");
			
			Log.crond(name, sb.toString());
			rs = stmt.executeQuery(sb.toString());
			String[] bean = new String[2];
			while(rs.next()) {
				bean = new String[2];
				bean[0] = rs.getString("PAT_SEQ");
				bean[1] = rs.getString("WORD_NM");
				relKeyword.add(bean);
			}
		} catch(Exception e) {
			Log.writeExpt(name,e.toString());
			e.printStackTrace();
			System.exit(1);
		} finally {
			sb = null;
			if(rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
			if(stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
			if(pstmt != null) try { pstmt.close(); pstmt = null;} catch(SQLException ex) {}
		}
		
		return relKeyword;
	}
	
	//RelationKeyword insert
	static void InsertRelationKeyword(ArrayList<String[]> relKeyword) {
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try {
			if(relKeyword.size() > 0 ) {
				TruncateTable("RELATION");
			}
			
			stmt = dbconn1.createStatement();
			sb = new StringBuffer();
			
			sb.append("INSERT INTO AUTO_RELATION_KEYWORD (PAT_SEQ, PAT_WORD) 				\n");
			sb.append(" 	VALUES(?, ?) 				\n");
			
			Log.crond(name, sb.toString());
			pstmt = dbconn1.createPStatement(sb.toString());
			
			String[] data = null;
			for (int i = 0; i < relKeyword.size(); i++) {
				data = relKeyword.get(i);

				pstmt.clearParameters();
				for (int j=0 ; j<data.length ; j++) {
					pstmt.setString(j+1, data[j]);	
				}
				
				pstmt.execute();
			}                
			
			
		} catch(Exception e) {
			Log.writeExpt(name,e.toString());
			e.printStackTrace();
			System.exit(1);
		} finally {
			sb = null;
			if(rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
			if(stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
			if(pstmt != null) try { pstmt.close(); pstmt = null;} catch(SQLException ex) {}
		}
		
	}
	
	//SentiKeyword select
	static ArrayList<String[]> SelectSentiKeyword(String tbName) {
		ArrayList<String[]>sentiKeyword = new ArrayList<String[]>();
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try {
			
			stmt = dbconn2.createStatement();
			sb = new StringBuffer();
			
			sb.append(" SELECT DISTINCT B.PAT_SEQ AS PAT_SEQ 											\n");
			sb.append("				  , B.WORD_NM AS PAT_WORD										\n");
			if(tbName.equals("POSITIVE")) {
			sb.append("			  	  , 1 AS PAT_SENTI												\n");
			} else {
			sb.append("			 	  , 2 AS PAT_SENTI												\n");
			}	
			sb.append("			 FROM ANA_PATTERN_INFO_V3_SYSTEM_IDX_2 A 							\n");
			sb.append(" 			  , ANA_PATTERN_INFO_V3_2 B										\n");
			sb.append("			 WHERE A.PAT_SEQ = B.PAT_SEQ 										\n");
			sb.append("			 AND A.SYSTEMKEY = 1  												\n");
			sb.append("			 AND A.IC_TYPE = 1 													\n");
			if(tbName.equals("POSITIVE")) {
			sb.append("			 AND A.IC_CODE = 1 													\n");
			} else {
			sb.append("			 AND A.IC_CODE = 2 													\n");
			}
			
			Log.crond(name, sb.toString());
			rs = stmt.executeQuery(sb.toString());
			String[] bean = new String[3];
			while(rs.next()) {
				bean = new String[3];
				bean[0] =  rs.getString("PAT_SEQ");
				bean[1] = rs.getString("PAT_WORD");
				bean[2] = rs.getString("PAT_SENTI");
				sentiKeyword.add(bean);
			}
		} catch(Exception e) {
			Log.writeExpt(name,e.toString());
			e.printStackTrace();
			System.exit(1);
		} finally {
			sb = null;
			if(rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
			if(stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
			if(pstmt != null) try { pstmt.close(); pstmt = null;} catch(SQLException ex) {}
		}
		
		return sentiKeyword;
	}
	

    static String delTop(int minusDate){
    	
    	String result = "";
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	
        	stmt = dbconn1.createStatement();
        	sb = new StringBuffer();
        	sb.append("SELECT MD_SEQ FROM TOP WHERE MD_DATE < TIMESTAMPADD(DAY, -"+minusDate+", '"+getCurrDate()+" 00:00:00') ORDER BY MD_SEQ DESC LIMIT 1\n");
        	Log.crond(name, sb.toString());
        	rs = stmt.executeQuery( sb.toString());
        	String md_seq = "";
        	if(rs.next()) {
        		md_seq = rs.getString("MD_SEQ");
        	}
        	
        	if(!md_seq.equals("")) {
        		sb = new StringBuffer();
            	sb.append("DELETE FROM IDX_TOP WHERE MD_SEQ <=  "+md_seq+"\n");
            	Log.crond(name, sb.toString());
            	stmt.executeUpdate( sb.toString());
            	
            	
            	sb = new StringBuffer();
            	sb.append("DELETE FROM TOP WHERE MD_SEQ <=  "+md_seq+"\n");
            	Log.crond(name, sb.toString());
            	stmt.executeUpdate( sb.toString());	
        	}
        	
        	
        	
        				
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
	//Positive Keyword, Negative Keyword insert
	static String InsertSentiKeyword(String tbName,ArrayList<String[]> sentiKeyword) {
		String result = "";
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try {
			
			if(sentiKeyword.size() > 0 ) {
				TruncateTable(tbName);
			}
			
			stmt = dbconn2.createStatement();
			sb = new StringBuffer();
			
			sb.append("INSERT INTO AUTO_"+tbName+"_KEYWORD (PAT_SEQ, PAT_WORD, PAT_SENTI) 			\n");
			sb.append("		  VALUES(?, ?, ?)														\n");
			
			Log.crond(name, sb.toString());
			pstmt = dbconn1.createPStatement(sb.toString());
			
			String[] data = null;
			for (int i = 0; i < sentiKeyword.size(); i++) {
				data = sentiKeyword.get(i);

				pstmt.clearParameters();
				for (int j=0 ; j<data.length ; j++) {
					pstmt.setString(j+1, data[j]);	
				}
				
				pstmt.execute();
			}                
			
			
		} catch(Exception e) {
			Log.writeExpt(name,e.toString());
			e.printStackTrace();
			System.exit(1);
		} finally {
			sb = null;
			if(rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
			if(stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
			if(pstmt != null) try { pstmt.close(); pstmt = null;} catch(SQLException ex) {}
		}
		
		return result;
	}
	
}
