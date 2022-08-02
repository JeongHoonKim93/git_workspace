package risk.report;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

import risk.DBconn.*;
import risk.util.*;

public class ReportMgrNew2 {

	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private PreparedStatement pstmt = null;
	private int total_count = 0;
	StringUtil su = new StringUtil();
	
	public int getTotalCount() {
		return this.total_count;
	}
	
	
	public ArrayList getDailyIssueList(String ir_sdate, String ir_stime, String ir_edate, String ir_etime, String id_seqs) {
		
		ArrayList arrResult = new ArrayList();
		Map dataMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();			
			sb.append(" ## 일일보고서 이슈 리스트											\n");
			sb.append("SELECT A.ID_SEQ												\n");
			sb.append("     , A.ID_TITLE											\n");
			sb.append("     , A.ID_URL												\n");
			sb.append("     , A.ID_SENTI											\n");
			sb.append("     , A.MD_DATE												\n");
			sb.append("     , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 6) AS TYPE	\n");
			sb.append("     , A.MD_SITE_NAME										\n");
			sb.append("     , A.ID_CONTENT											\n");
			sb.append("     , FN_GET_KEYWORD(A.MD_SEQ) AS K_VALUE					\n");
			sb.append("     , IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = A.ID_SEQ AND IC_TYPE = 15),99) AS IMPORTANT	\n");
			sb.append("  FROM ISSUE_DATA A											\n");
			sb.append(" WHERE A.ID_SEQ IN ("+id_seqs+")								\n");							
			sb.append("  ORDER BY FIELD(A.ID_SEQ,"+id_seqs+")								\n");							
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
				dataMap.put("ID_SENTI", rs.getString("ID_SENTI"));
				dataMap.put("MD_DATE", rs.getString("MD_DATE"));
				dataMap.put("TYPE", rs.getString("TYPE"));
				dataMap.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
				dataMap.put("ID_CONTENT", cutKey(su.toHtmlString(rs.getString("ID_CONTENT")), rs.getString("K_VALUE")));
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
