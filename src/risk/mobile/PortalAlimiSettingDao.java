package risk.mobile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import risk.DBconn.DBconn;
import risk.util.ConfigUtil;
import risk.util.DateUtil;
import risk.util.Log;

//알리미 셋팅 DAO
public class PortalAlimiSettingDao {
	
	DBconn dbconn   = null;
    Statement stmt  = null;
    ResultSet rs    = null;
    StringBuffer sb = null;
    String sQuery   = "";
    
    private int listCnt = 0;
    
    public int getListCnt()
    {
    	return this.listCnt;
    }
    
    public ArrayList getPortalAlimiSetList(int nowPage, int rowCnt, String Pas_seq, String subInfoIn)
    {
    	ArrayList arrPortalAlimiSetList = new ArrayList();
    	ArrayList arrReceiverList = new ArrayList();
    	PortalAlimiSettingBean pasBean = new PortalAlimiSettingBean();
    	PortalAlimiReceiverBean arBean = new PortalAlimiReceiverBean();
    	String pas_seq = null;
    	
    	int liststart = 0;
    	int listend = 0;
    	int whereCnt = 0;   	
    	
    	try
    	{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();   
			sb.append(" SELECT COUNT(*) AS TOTAL_CNT FROM PORTAL_ALIMI_SETTING \n");							
			if(!Pas_seq.equals(""))
			{
				if(whereCnt==0)
				{
					sb.append("WHERE ");
				}else{
					sb.append("AND ");
				}
				sb.append("PAS_SEQ = "+Pas_seq+" \n");
				whereCnt++;
			}

			//Log.debug(sb.toString());  
			System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());            
            if ( rs.next() ) {
            	listCnt  = rs.getInt("TOTAL_CNT");                     
            }
            
          
 		    liststart = (nowPage-1) * rowCnt;
 		    listend = rowCnt;
               
            rs = null;           
            sb = new StringBuffer();
            whereCnt =0;
            
            sb.append("SELECT PAS_SEQ                                                    \n");                 
            sb.append("     , PAS_TITLE                                                  \n");            
            sb.append("     , PAS_CHK                                                    \n");       
            sb.append("     , PAS_TYPE                                                   \n");  
            sb.append("     , PAS_SMS_TIME                                               \n");
            sb.append("     , PAS_LAST_SENDTIME                                          \n");
            sb.append("     , PAS_TRAN_NUM                                          	 \n");
            sb.append("  FROM PORTAL_ALIMI_SETTING                                       \n");
           
            if(!Pas_seq.equals(""))
            sb.append(" WHERE PAS_SEQ = "+Pas_seq+"                                       \n");
            
            if(rowCnt>0){
            sb.append(" ORDER BY PAS_SEQ DESC                                             \n");
            sb.append(" LIMIT "+liststart+","+listend+"                                   \n");
            }
            
            //System.out.println(sb.toString());
			Log.debug(sb.toString() );			
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next())
			{
				pasBean = new PortalAlimiSettingBean();
				pasBean.setPas_seq(rs.getString("PAS_SEQ"));
				pasBean.setPas_title(rs.getString("PAS_TITLE"));
				pasBean.setPas_chk(rs.getString("PAS_CHK"));
				pasBean.setPas_type(rs.getString("PAS_TYPE"));
				pasBean.setPas_sms_time(rs.getString("PAS_SMS_TIME"));
				pasBean.setPas_last_sendtime(rs.getString("PAS_LAST_SENDTIME"));
				pasBean.setPas_tran_num(rs.getString("PAS_TRAN_NUM"));
				arrPortalAlimiSetList.add(pasBean);
			}
			
			if(subInfoIn.equals("Y"))
			{
				rs = null;           
	            sb = new StringBuffer();
	            whereCnt =1;
	            pas_seq ="";
	            
	            sb.append("SELECT A.PAS_SEQ	                    \n");                
    			sb.append("     , C.AB_SEQ                      \n");                         
    			sb.append("     , C.AB_NAME                     \n");                         
    			sb.append("     , C.AB_DEPT                     \n");                         
    			sb.append("     , C.AB_POSITION                 \n");                              
    			sb.append("     , C.AB_MOBILE                   \n");                         
    			sb.append("     , C.AB_MAIL                     \n");                                                
    			sb.append("  FROM PORTAL_ALIMI_SETTING  A       \n");
    			sb.append("     , PORTAL_ALIMI_RECEIVER B       \n");
    			sb.append("     , ADDRESS_BOOK   C              \n");
    			sb.append(" WHERE A.PAS_SEQ = B.PAS_SEQ	        \n");                       
    			sb.append("   AND B.AB_SEQ = C.AB_SEQ           \n");
    			
	            if(!Pas_seq.equals(""))
				{
					if(whereCnt==0)
					{
						sb.append("WHERE ");
					}else{
						sb.append("AND ");
					}
					sb.append("A.PAS_SEQ = "+Pas_seq+" \n");
					whereCnt++;
				}
	            sb.append("ORDER BY A.PAS_SEQ ASC	                          \n");

	            //System.out.println(sb.toString());
				Log.debug(sb.toString() );				
				rs = stmt.executeQuery(sb.toString());				
	            
	            while(rs.next())
				{
	            	if (!pas_seq.equals(rs.getString("PAS_SEQ"))) {
	            		//System.out.println("arrReceiverList:"+arrReceiverList.size());
	            		arrPortalAlimiSetList = addArrReceiber(arrPortalAlimiSetList, pas_seq, arrReceiverList);
	            		arrReceiverList = new ArrayList();
					}
	            	pas_seq = rs.getString("PAS_SEQ");
	            	arBean = new PortalAlimiReceiverBean();
	            	arBean.setAb_name( rs.getString("AB_NAME"));
	            	arBean.setAb_dept( rs.getString("AB_DEPT"));
	            	arBean.setAb_pos( rs.getString("AB_POSITION"));
	            	arBean.setAb_mobile( rs.getString("AB_MOBILE"));
	            	arrReceiverList.add(arBean);
				}
	            //System.out.println("arrReceiverList:"+arrReceiverList.size());
	            arrPortalAlimiSetList = addArrReceiber(arrPortalAlimiSetList, pas_seq, arrReceiverList);
			}
			
			//System.out.println("arrAlimiSetList:"+arrAlimiSetList.size());
			
			
    	 } catch (SQLException ex ) {
             Log.writeExpt(ex, ex.getMessage() );

         } catch (Exception ex ) {
             Log.writeExpt(ex.getMessage());

         } finally {
             try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
             try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
             try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
         }   
         
         return arrPortalAlimiSetList;
    }
	
    //리포트 셋 빈의 리시버 어레이 추가
	public ArrayList addArrReceiber(ArrayList paArrInsert, String pas_seq, ArrayList paArrData){
		int i=0;
		PortalAlimiSettingBean pasBean = new PortalAlimiSettingBean();
    	for(i=0; i<paArrInsert.size(); i++) {
    		pasBean = new PortalAlimiSettingBean();
    		pasBean = (PortalAlimiSettingBean) paArrInsert.get(i);
    		if (pasBean.getPas_seq().equals(pas_seq)) {
    			pasBean.setArrReceiver(paArrData);
    			paArrInsert.set(i, pasBean);
    		}
    	}
    	return paArrInsert;
	}
	
    //설정된 수신자 번호 가져오기.
    public String getReceiverSeq(String pas_seq)
    {
    	String result = "";
    	PortalAlimiReceiverBean arBean = new PortalAlimiReceiverBean();
    	
		try {			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();
			sb.append("SELECT A.AB_SEQ \n");		
			sb.append("FROM PORTAL_ALIMI_RECEIVER A , ADDRESS_BOOK B \n");
			sb.append("WHERE A.AB_SEQ = B.AB_SEQ\n");
			sb.append("      AND A.PAS_SEQ = "+pas_seq+"\n");
			
			Log.debug(sb.toString() );
			rs = stmt.executeQuery(sb.toString());			
						
	        while(rs.next())
	        {
	        	
	        	result += result.equals("") ?  rs.getString("AB_SEQ") : ","+ rs.getString("AB_SEQ");
	        }
		
		} catch (SQLException ex ) {
            Log.writeExpt(ex, ex.getMessage() );

        } catch (Exception ex ) {
            Log.writeExpt(ex.getMessage());

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
    	return result;
    }
    
    //주소록 리스트 가져오기
    public ArrayList getAddressList( String ab_seq,
    								 String ab_name,
    								 String ab_dept    								 
    								)
    {
    	ArrayList result = new ArrayList();
    	PortalAlimiReceiverBean arBean = new PortalAlimiReceiverBean();
    	
		try {
			String[] issue_Dept=null;
			
			String check = ab_seq+ab_name+ab_dept;
			String whereQuery = "";
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();
			
			sb.append("SELECT AB_SEQ          \n");
			sb.append("     , AB_NAME         \n");
			sb.append("	    , AB_DEPT         \n");
			sb.append("	    , AB_POSITION     \n");
			sb.append("	    , AB_MOBILE       \n");
			sb.append("	    , AB_MAIL         \n");
			sb.append("  FROM ADDRESS_BOOK    \n");
			sb.append(" WHERE 1=1             \n");

			if( ab_seq.length() > 0 )
			whereQuery += " AND AB_SEQ IN ("+ab_seq+") ";
			
			if( ab_name.length() > 0 ) {
				if( whereQuery.length() > 0 ) whereQuery += " AND ";
				whereQuery += " AB_NAME LIKE '%"+ab_name+"%' ";
			}
			
			if( ab_dept.length() > 0 ) {
				if( whereQuery.length() > 0 ) whereQuery += " AND ";
				whereQuery += " AB_DEPT LIKE '%"+ab_dept+"%' ";
			}
	
			sb.append(whereQuery);			
			
			rs = stmt.executeQuery(sb.toString());
			
			result = new ArrayList();
	        while(rs.next())
	        {
	        	//핸드폰번호 특수문자 제거
	        	String replaceString =  rs.getString(5);
	        	replaceString = replaceString.replaceAll("-", "");
	        	replaceString = replaceString.replaceAll("\\.", "");
	        	replaceString = replaceString.replaceAll(" ", "");
	        	replaceString = replaceString.replaceAll("_", "");
	        	replaceString = replaceString.replaceAll("\'", "");
	        	replaceString = replaceString.replaceAll("\"", "");
	        	
	        	arBean = new PortalAlimiReceiverBean();
	        	arBean.setAb_seq(rs.getString(1));
	        	arBean.setAb_name(rs.getString(2));
	        	arBean.setAb_dept(rs.getString(3));
	        	arBean.setAb_pos(rs.getString(4));
	        	arBean.setAb_mobile(replaceString);
	        	result.add(arBean);					
	        	
	        }
		
		} catch (SQLException ex ) {
            Log.writeExpt(ex, ex.getMessage() );

        } catch (Exception ex ) {
            Log.writeExpt(ex.getMessage());

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
    	return result;
    }
    
    public int insertAlimisSet(PortalAlimiSettingBean pasBean, String ab_seqs)
    {
    	int result = 0;
    		
    	String startMtNo = "";
    	String pas_seq = null;    	
    	String[] arrAbSeq = null;
    	
    	if(ab_seqs!=null && !ab_seqs.equals(""))
    	{
    		arrAbSeq = ab_seqs.trim().split(",");
    	}        
    
    	try
    	{
    		
    		startMtNo = getMaxMtNo();
    		
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append(" INSERT INTO PORTAL_ALIMI_SETTING      	\n");
			sb.append(" 	(                          			\n");
			sb.append(" 	PAS_TITLE,                  		\n");
			sb.append(" 	PAS_CHK,                    		\n");
			sb.append(" 	PAS_TYPE,                   		\n");
			sb.append(" 	PAS_SMS_TIME,              			\n");
			sb.append(" 	PAS_LAST_SENDTIME,           		\n");
			sb.append(" 	PAS_LAST_NUM,		           		\n");
			sb.append(" 	PAS_TRAN_NUM		           		\n");
			sb.append(" 	)                          			\n");
			sb.append(" VALUES                        		 	\n");
			sb.append(" 	(                          			\n");
			sb.append(" 	'"+pasBean.getPas_title()+"',       \n");
			sb.append(" 	"+pasBean.getPas_chk()+",           \n");
			sb.append(" 	"+pasBean.getPas_type()+",          \n");		
			sb.append(" 	'"+pasBean.getPas_sms_time()+"',    \n");
			sb.append(" 	NOW(),              				\n");
			sb.append(" 	"+startMtNo+",              		\n");
			sb.append(" 	'"+pasBean.getPas_tran_num()+"'    \n");
			sb.append(" 	)                          			\n");			
			Log.debug(sb.toString() );
			
			result =  stmt.executeUpdate(sb.toString());			
			
			if(result==1)
			{
				
				sb = new StringBuffer();
				sb.append("SELECT MAX(PAS_SEQ) AS PAS_SEQ\n");
				sb.append("FROM PORTAL_ALIMI_SETTING \n");
				
				Log.debug(sb.toString() );
						
				rs = stmt.executeQuery(sb.toString());
				while(rs.next())
				{
					pas_seq = rs.getString("PAS_SEQ");
				}
				
				if(arrAbSeq!=null)
		    	{
					for(int i=0;i<arrAbSeq.length;i++)
					{
						sb = new StringBuffer();
						sb.append("INSERT INTO PORTAL_ALIMI_RECEIVER(PAS_SEQ,AB_SEQ) \n");
						sb.append("		  VALUES("+pas_seq+","+arrAbSeq[i]+") \n");
						
						Log.debug(sb.toString() );
						result =  stmt.executeUpdate(sb.toString());
					}
				}
				
			}
			
			
    	 } catch (SQLException ex ) {
             Log.writeExpt(ex, ex.getMessage() );

         } catch (Exception ex ) {
             Log.writeExpt(ex.getMessage());

         } finally {
        	 try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
     		try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
     		try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }            
         }
    	 
         return result;    
    }
    
    public String getMaxMtNo()
	{	
	 
		String lastNo = "";	  
		try 
		{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			rs = null;
			sb = new StringBuffer();
			sb.append("SELECT PM_SEQ FROM PORTAL_META ORDER BY PM_SEQ DESC LIMIT 1 \n");
			
			Log.debug(sb.toString() );
			rs = stmt.executeQuery(sb.toString());
			if (rs.next()) {
				lastNo = rs.getString("PM_SEQ");
			}	
			   
		}catch (SQLException ex ) {
            Log.writeExpt(ex, ex.getMessage() );

        } catch (Exception ex ) {
            Log.writeExpt(ex.getMessage());

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
     		try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
     		try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }  
        } 
		return lastNo;
	}
    
    public int deletePortalAlimiSet(String pas_seqs)
    {
    	int result = 0;
    	
    	try
    	{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			
			sb.append("DELETE FROM PORTAL_ALIMI_SETTING                                   \n");			
			sb.append("WHERE PAS_SEQ IN ( "+pas_seqs+" )                                  \n");	
			
			Log.debug(sb.toString() );			
			result =  stmt.executeUpdate(sb.toString());			
			
			if(result==1)
			{
				sb = new StringBuffer();
				sb.append("DELETE FROM PORTAL_ALIMI_RECEIVER \n");
				sb.append("WHERE PAS_SEQ IN  ("+pas_seqs+") \n");
				
				result =  stmt.executeUpdate(sb.toString());

			}
			
    	 } catch (SQLException ex ) {
             Log.writeExpt(ex, ex.getMessage() );

         } catch (Exception ex ) {
             Log.writeExpt(ex.getMessage());

         } finally {
             try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
             try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
             try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
         }
    	 
         return result;    
    }
    
    public int updateReportSet(PortalAlimiSettingBean pasBean, String ab_seqs)
    {
    	int result = 0;
    	String as_seq = null;
    	String[] arrAbSeq = null;
    	String startMtNo = "";
    	
    	if(ab_seqs!=null && !ab_seqs.equals(""))
    	{    		
    		arrAbSeq = ab_seqs.trim().split(",");
    	}        
    
    	try
    	{
    		startMtNo = getMaxMtNo();
    		
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("UPDATE PORTAL_ALIMI_SETTING                                     			\n");
			sb.append("SET                                                  					\n");
			sb.append("	PAS_TITLE = '"+pasBean.getPas_title()+"',                               \n");
			sb.append("	PAS_CHK = "+pasBean.getPas_chk()+",                                    	\n");
			sb.append("	PAS_TYPE = "+pasBean.getPas_type()+",                                 	\n");
			sb.append("	PAS_SMS_TIME = '"+pasBean.getPas_sms_time()+"',                          \n");
			sb.append("	PAS_LAST_SENDTIME = NOW(),                          						\n");
			sb.append("	PAS_LAST_NUM = "+startMtNo+"                          					\n");
			sb.append("	,PAS_TRAN_NUM = '"+pasBean.getPas_tran_num()+"'                          \n");
			sb.append("WHERE PAS_SEQ = "+pasBean.getPas_seq()+"                               	\n");	
			
			Log.debug(sb.toString() );
			
			result =  stmt.executeUpdate(sb.toString());
			
			if(result==1)
			{
				sb = new StringBuffer();
				sb.append("DELETE FROM PORTAL_ALIMI_RECEIVER 			\n");
				sb.append("WHERE PAS_SEQ = "+pasBean.getPas_seq()+" 	\n");
				
				Log.debug(sb.toString() );
				result =  stmt.executeUpdate(sb.toString());
				
				if(arrAbSeq!=null)
		    	{
					for(int i=0;i<arrAbSeq.length;i++)
					{
						sb = new StringBuffer();
						sb.append("INSERT INTO PORTAL_ALIMI_RECEIVER(PAS_SEQ, AB_SEQ) 			\n");
						sb.append("		  VALUES("+pasBean.getPas_seq()+","+arrAbSeq[i]+" ) 	\n");
						Log.debug(sb.toString() );
						
						result =  stmt.executeUpdate(sb.toString());
					}
		    	}
				
			}
			
    	 } catch (SQLException ex ) {
             Log.writeExpt(ex, ex.getMessage() );

         } catch (Exception ex ) {
             Log.writeExpt(ex.getMessage());

         } finally {
             try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
             try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
             try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
         }
    	 
         return result;    
    }
}
