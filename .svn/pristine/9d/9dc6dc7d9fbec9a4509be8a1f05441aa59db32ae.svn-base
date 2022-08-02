package risk.admin.alimi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import risk.DBconn.DBconn;
import risk.util.Log;

public class AlimiLogMgr {
	
	DBconn dbconn   = null;
    Statement stmt  = null;
    ResultSet rs    = null;
    StringBuffer sb = null;
    
    int FullCnt = 0;
    public int getFullCnt() {
		return FullCnt;
	}
    
	public ArrayList getTelegramLogList(int nowpage, int rowCnt, String sDate, String eDate, String mal_type, String searchKey, String tl_code) {
    	ArrayList result = new ArrayList();
    	AlimiLogSuperBean bean = new AlimiLogSuperBean();
    	AlimiLogSuperBean.AlimiLogList childBean = null;
    	
        try {

        	
        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            
        	sb = new StringBuffer();
        	sb.append("SELECT COUNT(*) AS CNT FROM (SELECT MD_SEQ FROM TELEGRAM_LOG WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	\n");
        	if(!mal_type.equals("")){
        		sb.append(" AND TL_SEND_STATUS = "+mal_type+"																											\n");
        	}
        	if(!searchKey.equals("")){
        		sb.append(" AND TL_MESSAGE LIKE '%"+searchKey+"%'																								\n");
        	}
        	if(!tl_code.equals("")){
        		sb.append(" AND TS_SEQ = "+tl_code+"																												\n");
        	}
        	sb.append("   )A 																																	\n");
        	
        	
        	System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());        
        	
            if(rs.next()){
            	FullCnt = rs.getInt("CNT");
            }
            
            int index = (nowpage-1) * rowCnt;
            
            sb = new StringBuffer();
            
            sb.append("SELECT A.*																		  			\n");
            sb.append("     , (SELECT COUNT(*) FROM TELEGRAM_LOG WHERE MD_SEQ = A.MD_SEQ) AS AS_CNT	  			    \n");
            sb.append("  FROM (																			  			\n");
            sb.append("         SELECT DISTINCT TS_SEQ, MD_SEQ, TL_MESSAGE, MD_DATE, TL_SEND_DATE, TL_SEND_STATUS 	\n");  
            sb.append("           FROM TELEGRAM_LOG 														  		\n");
            sb.append("          WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	  			\n");
            if(!mal_type.equals("")){
        		sb.append("        AND TL_SEND_STATUS = "+mal_type+"												\n");
        	}
            if(!searchKey.equals("")){
        		sb.append(" AND TL_MESSAGE LIKE '%"+searchKey+"%'													\n");
        	}
            if(!tl_code.equals("")){
        		sb.append(" AND TS_TSQ = "+tl_code+"																\n");
        	}
            //sb.append("          ORDER BY MD_SEQ ASC 													  			\n");
            sb.append("          ORDER BY TL_SEND_DATE DESC 													  			\n");
            sb.append("          LIMIT  "+index+" , "+rowCnt+"											  			\n");
            sb.append("       )A 																		  			\n"); 
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());            

            while ( rs.next() ) {
            	childBean = bean.new AlimiLogList();
            	childBean.setTl_code(rs.getString("TS_SEQ"));
            	childBean.setMd_seq(rs.getString("MD_SEQ"));
            	childBean.setTl_message(rs.getString("TL_MESSAGE"));
            	childBean.setMd_date(rs.getString("MD_DATE"));
            	childBean.setTl_send_date(rs.getString("TL_SEND_DATE"));
            	childBean.setTl_send_status(rs.getString("TL_SEND_STATUS"));
            	childBean.setAs_cnt(rs.getString("AS_CNT"));
            	result.add(childBean);
            }

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex ) {
            Log.writeExpt(ex);
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return result;
    }
	
	public ArrayList getTelegramManualLogList(int nowpage, int rowCnt, String sDate, String eDate, String searchKey, String tl_code) {
    	ArrayList result = new ArrayList();
    	AlimiLogSuperBean bean = new AlimiLogSuperBean();
    	AlimiLogSuperBean.AlimiLogList childBean = null;
    	
        try {

        	
        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            
        	sb = new StringBuffer();
        	sb.append("SELECT COUNT(*) AS CNT FROM (SELECT TML_SEQ FROM TELEGRAM_MANUAL_LOG WHERE TML_SEND_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	\n");
        	if(!searchKey.equals("")){
        		sb.append(" AND TML_MESSAGE LIKE '%"+searchKey+"%'																								\n");
        	}
        	if(!tl_code.equals("")){
        		sb.append(" AND TS_SEQ = "+tl_code+"																												\n");
        	}
        	sb.append("   )A 																																	\n");
        	
        	
        	System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());        
        	
            if(rs.next()){
            	FullCnt = rs.getInt("CNT");
            }
            
            int index = (nowpage-1) * rowCnt;
            
            sb = new StringBuffer();
            
            sb.append("SELECT A.*																		  			\n");
            sb.append("     , (SELECT COUNT(*) FROM TELEGRAM_MANUAL_LOG WHERE TML_SEQ = A.TML_SEQ) AS AS_CNT	  			    \n");
            sb.append("  FROM (																			  			\n");
            sb.append("         SELECT DISTINCT A.TML_SEQ, A.TML_MESSAGE, A.TML_SEND_DATE, A.TS_SEQ, M.M_NAME 	\n");  
            sb.append("           FROM TELEGRAM_MANUAL_LOG A														  		\n");
            sb.append("            INNER JOIN MEMBER M ON A.M_SEQ = M.M_SEQ														  		\n");
            sb.append("          WHERE TML_SEND_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	  			\n");
            if(!searchKey.equals("")){
        		sb.append(" AND TML_MESSAGE LIKE '%"+searchKey+"%'													\n");
        	}
            if(!tl_code.equals("")){
        		sb.append(" AND TS_SEQ = "+tl_code+"																\n");
        	}
            //sb.append("          ORDER BY MD_SEQ ASC 													  			\n");
            sb.append("          ORDER BY TML_SEND_DATE DESC 													  			\n");
            sb.append("          LIMIT  "+index+" , "+rowCnt+"											  			\n");
            sb.append("       )A 																		  			\n"); 
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());            

            while ( rs.next() ) {
            	childBean = bean.new AlimiLogList();
            	childBean.setTl_code(rs.getString("TS_SEQ"));
            	childBean.setTl_message(rs.getString("TML_MESSAGE"));
            	childBean.setTl_send_date(rs.getString("TML_SEND_DATE"));
            	childBean.setAs_cnt(rs.getString("AS_CNT"));
            	childBean.setTl_send_m_name(rs.getString("M_NAME"));
            	result.add(childBean);
            }

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex ) {
            Log.writeExpt(ex);
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return result;
    }
	
	
	
	/*public ArrayList getAlimiLogList(int nowpage, int rowCnt, String sDate, String eDate, String mal_type, String searchKey, String as_type) {
    	ArrayList result = new ArrayList();
    	AlimiLogSuperBean bean = new AlimiLogSuperBean();
    	AlimiLogSuperBean.AlimiLogList childBean = null;
    	
        try {

        	
        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            
        	sb = new StringBuffer();
        	sb.append("SELECT COUNT(*) AS CNT FROM (SELECT MT_NO FROM MALIMI_LOG WHERE MT_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	\n");
        	if(!mal_type.equals("")){
        		sb.append(" AND MAL_TYPE = "+mal_type+"																											\n");
        	}
        	if(!searchKey.equals("")){
        		sb.append(" AND SEND_MESSAGE LIKE '%"+searchKey+"%'																								\n");
        	}
        	if(!as_type.equals("")){
        		sb.append(" AND AS_TYPE = "+as_type+"																												\n");
        	}
        	sb.append("   )A 																																	\n");
        	
        	
        	System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());        
        	
            if(rs.next()){
            	FullCnt = rs.getInt("CNT");
            }
            
            int index = (nowpage-1) * rowCnt;
            
            sb = new StringBuffer();
            
            sb.append("SELECT A.*																		  			\n");
            sb.append("     , (SELECT COUNT(*) FROM ALIMI_RECEIVER WHERE AS_SEQ = A.AS_SEQ) AS AS_CNT	  			\n");
            sb.append("  FROM (																			  			\n");
            sb.append("         SELECT DISTINCT MT_NO, SEND_MESSAGE, MT_URL, MT_DATE, AS_SEQ, AS_TITLE, MAL_TYPE, AS_TYPE  	\n");  
            sb.append("           FROM MALIMI_LOG 														  			\n");
            sb.append("          WHERE MT_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	  			\n");
            if(!mal_type.equals("")){
        		sb.append("        AND MAL_TYPE = "+mal_type+"														\n");
        	}
            if(!searchKey.equals("")){
        		sb.append(" AND SEND_MESSAGE LIKE '%"+searchKey+"%'													\n");
        	}
            if(!as_type.equals("")){
        		sb.append(" AND AS_TYPE = "+as_type+"																												\n");
        	}
            sb.append("          ORDER BY MAL_SEQ DESC 													  			\n");
            sb.append("          LIMIT  "+index+" , "+rowCnt+"											  			\n");
            sb.append("       )A 																		  			\n"); 
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());            

            while ( rs.next() ) {
            	childBean = bean.new AlimiLogList();
            	childBean.setMt_no(rs.getString("MT_NO"));
            	childBean.setSend_message(rs.getString("SEND_MESSAGE"));
            	childBean.setMt_date(rs.getString("MT_DATE"));
            	childBean.setAs_seq(rs.getString("AS_SEQ"));
            	childBean.setAs_title(rs.getString("AS_TITLE"));
            	childBean.setAs_cnt(rs.getString("AS_CNT"));
            	childBean.setMal_type(rs.getString("MAL_TYPE"));
            	childBean.setMt_url(rs.getString("MT_URL"));
            	childBean.setAs_type(rs.getString("AS_TYPE"));
            	result.add(childBean);
            }

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex ) {
            Log.writeExpt(ex);
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return result;
    }*/
	
}
