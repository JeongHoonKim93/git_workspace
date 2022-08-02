package risk.sms;

//기본 참조
import risk.util.*;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.lang.String;


//사용 참조
import risk.DBconn.DBconn;

public class TelegramDao {
	private	DBconn  dbconn  = null;
	private	Statement stmt = null;
	private	ResultSet rs   = null;
	private StringBuffer sb = null;

	public TelegramDao(){
		 dbconn  = new DBconn();        
	     sb = new StringBuffer();   
	}

	
	public ArrayList<String[]> getTelegramGroupList(){
			
		ArrayList<String[]> result = new ArrayList<String[]>();
		String[] tmp_info = new String[3];
		
		try {
			dbconn = new DBconn();
			Statement stmt = null;
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			rs = null;
			
			sb.append(" SELECT TG_SEQ, TG_NAME, TG_ID					\n");
			sb.append("		FROM TELEGRAM_GROUP						 	\n");
			sb.append("	 WHERE TG_USEYN = 'Y'     						\n");
			sb.append("		 ORDER BY TG_SEQ 							\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
					
			while(rs.next()){
				tmp_info = new String[3];
				tmp_info[0] = rs.getString("TG_SEQ");
				tmp_info[1] = rs.getString("TG_NAME");
				tmp_info[2] = rs.getString("TG_ID");
				
	        	result.add(tmp_info);
			}
			
		

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
        	System.out.println(sb);
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	
	  
	  
	  
}


