package risk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import risk.DBconn.DBconn;

public class TelegramUtil {
	private String className = this.getClass().getSimpleName();
	private String TG_API_URL    = "http://telegramapi.realsn.com/rsnTelegramAPI.php";
	private String TG_KEY  		= "3773087449cea65b";
	
	// 텔레그램 수진자 정보
	private String[][] TG_RECIVER = {
											 	{	"010-3740-2319"	,"양진솔"	}											  
											   ,{	"010-4920-8838"	,"황정민"	}											  
											   ,{	"010-3530-8715"	,"김정훈"	}											  
												
									};
	
	public void SendTG_Per(String msg, String demon_name){			
		String param = ""; 
		
		try {				
			for(int i=0; i<TG_RECIVER.length; i++){
				param = "type=sendRequest&key="+TG_KEY+"&m_phon="+TG_RECIVER[i][0]+"&message="+URLEncoder.encode(msg, "UTF-8");
				// 전송
				GetHtmlPost(TG_API_URL, param , demon_name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean SendTG_Group(String m_ch_id, String msg){			
		String param = "";
		boolean result = false;
		
		try {				
			param = "type=sendRequest&key="+TG_KEY+"&m_ch_id="+m_ch_id+"&message="+URLEncoder.encode(msg, "UTF-8");
			// 전송
			GetHtmlPost(TG_API_URL, param , className);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	
	public boolean SendTG_Group2(String m_ch_id_group, String msg){			
		String param = ""; 
		boolean result = false;
		String[] m_ch_id_liset = m_ch_id_group.split(",");
		
		try {		
			for(int i=0; i<m_ch_id_liset.length; i++){
				param = "type=sendRequest&key="+TG_KEY+"&m_ch_id="+m_ch_id_liset[i]+"&message="+URLEncoder.encode(msg, "UTF-8");
				// 전송
				GetHtmlPost(TG_API_URL, param , className);
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	/**
	 * 수동 텔레그램 로그 저장
	 * @param telegram_group_code 텔레그램 그룹방 코드 
	 * @param msg 발송 메세지 
	 * **/
	  public void insertTelegramLog(String telegram_group_code, String msg, String m_seq)
	    {

		  DBconn dbconn   = null;
		  ResultSet rs    = null;
		  PreparedStatement pstmt = null;
		  StringBuffer sb = null;	
		  
		  try{
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				
				sb = new StringBuffer();   
				sb.append("#텔레그램 로그 저장\n");
		  		sb.append("INSERT INTO TELEGRAM_MANUAL_LOG (TML_MESSAGE, TML_SEND_DATE, TS_SEQ, M_SEQ)			\n");
		  		sb.append("   	VALUES (?, NOW(), ?, ?)																					\n");
				//System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				
				int idx = 1;				
				pstmt.setString(idx++, msg);
				pstmt.setString(idx++, telegram_group_code);
				pstmt.setString(idx++, m_seq);
				
				System.out.println(pstmt);
				System.out.println(sb.toString());
				pstmt.executeUpdate();
									
				
			} catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            sb = null;
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	            if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	        }        
	    }

	public String GetHtmlPost(String sUrl, String param, String demon_name){
	
	Log.writeExpt(demon_name, "GetHtmlPost\n" + sUrl+"?"+param);
	
	StringBuffer html = new StringBuffer();
	try{
		
		String line = null;
		URL aURL = new URL(sUrl);
		HttpURLConnection urlCon = (HttpURLConnection)aURL.openConnection();
		urlCon.setRequestMethod("POST");
		
		urlCon.setDoInput(true);
		urlCon.setDoOutput(true);
		OutputStream out = urlCon.getOutputStream();
		out.write(param.getBytes());
		out.flush();
		
		out.close();
		BufferedReader br  = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
		while((line = br.readLine()) != null){
			html.append(line);
		}
		
		aURL = null;
		urlCon = null;
		br.close();
	}catch(MalformedURLException e){
		Log.writeExpt(demon_name, "GetHtmlPost \n MalformedURLException : "+Log.stackTraceToString(e) );
	}catch(IOException e) {
		Log.writeExpt(demon_name, "GetHtmlPost \n IOException : "+ Log.stackTraceToString(e) );
	}
	return html.toString();
}
	
	
	
	
}
