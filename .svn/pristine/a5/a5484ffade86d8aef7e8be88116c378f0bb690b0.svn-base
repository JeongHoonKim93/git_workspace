package risk.admin.portal_alimi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import risk.DBconn.DBconn;
import risk.util.Log;

public class PortalAlimiLogMgr {
	
	DBconn dbconn   = null;
    Statement stmt  = null;
    ResultSet rs    = null;
    StringBuffer sb = null;
    
    int FullCnt = 0;
    public int getFullCnt() {
		return FullCnt;
	}


	public ArrayList getAlimiLogList(int nowpage, int rowCnt, String sDate, String eDate, String pal_type, String searchKey, String pas_type) {
    	ArrayList result = new ArrayList();
    	PortalAlimiLogSuperBean bean = new PortalAlimiLogSuperBean();
    	PortalAlimiLogSuperBean.AlimiLogList childBean = null;
    	
        try {

        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            
        	sb = new StringBuffer();
        	sb.append("SELECT COUNT(*) AS CNT FROM (SELECT PM_NO FROM PALIMI_LOG WHERE PM_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'			\n");
        	if(!pal_type.equals("")){
        		sb.append(" AND PAL_TYPE = "+pal_type+"																											\n");
        	}
        	if(!searchKey.equals("")){
        		sb.append(" AND SEND_MESSAGE LIKE '%"+searchKey+"%'																								\n");
        	}
        	if(!pas_type.equals("")){
        		sb.append(" AND PAS_TYPE = "+pas_type+"																												\n");
        	}
        	sb.append(" GROUP BY PM_NO  )A 																																	\n");
        	
        	
        	System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());        
        	
            if(rs.next()){
            	FullCnt = rs.getInt("CNT");
            }
            
            int index = (nowpage-1) * rowCnt;
            
            sb = new StringBuffer();
            
            sb.append("SELECT A.*																		  						\n");
            sb.append("     , (SELECT COUNT(*) FROM PORTAL_ALIMI_RECEIVER WHERE PAS_SEQ = A.PAS_SEQ) AS PAS_CNT	  				\n");
            sb.append("  FROM (																			  						\n");
            sb.append("         SELECT PM_NO, SEND_MESSAGE, PM_URL, PM_DATE, PAS_SEQ, PAS_TITLE, PAL_TYPE, PAS_TYPE  	\n");  
            sb.append("           FROM PALIMI_LOG 														  						\n");
            sb.append("          WHERE PM_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	  						\n");
            if(!pal_type.equals("")){
        		sb.append("        AND PAL_TYPE = "+pal_type+"														\n");
        	}
            if(!searchKey.equals("")){
        		sb.append(" AND SEND_MESSAGE LIKE '%"+searchKey+"%'													\n");
        	}
            if(!pas_type.equals("")){
        		sb.append(" AND PAS_TYPE = "+pas_type+"																												\n");
        	}
            sb.append("          GROUP BY PM_NO		 													  			\n");
            sb.append("          ORDER BY PAL_SEQ DESC 													  			\n");
            sb.append("          LIMIT  "+index+" , "+rowCnt+"											  			\n");
            sb.append("       )A 																		  			\n"); 
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());            

            while ( rs.next() ) {
            	childBean = bean.new AlimiLogList();
            	childBean.setPm_no(rs.getString("PM_NO"));
            	childBean.setSend_message(rs.getString("SEND_MESSAGE"));
            	childBean.setPm_date(rs.getString("PM_DATE"));
            	childBean.setPas_seq(rs.getString("PAS_SEQ"));
            	childBean.setPas_title(rs.getString("PAS_TITLE"));
            	childBean.setPas_cnt(rs.getString("PAS_CNT"));
            	childBean.setPas_type(rs.getString("PAS_TYPE"));
            	childBean.setPal_type(rs.getString("PAL_TYPE"));
            	childBean.setPm_url(rs.getString("PM_URL"));
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
    
}
