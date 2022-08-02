package risk.admin.ex_site;

import java.sql.*;
import java.util.*;
import java.lang.String;

import risk.admin.member.MemberBean;
import risk.admin.membergroup.membergroupBean;
import risk.util.DateUtil;
import risk.util.Log;
import risk.DBconn.DBconn;


public class ExSiteMng {
	
	StringBuffer sb = null;
	DateUtil du = new DateUtil();
	
	public ArrayList getExSitetList(String s_seq, String searchWords){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    ResultSet rs = null;	
	    ArrayList result = null;
	    
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb = new StringBuffer();
		    
		    sb.append("   #ExSiteMng  getExSitetList()               \n");   
		    sb.append("   SELECT  A.EX_SEQ                            \n");    
		    sb.append("           ,A.S_SEQ                    \n");       
		    sb.append("           ,A.EX_URL                   \n");     
		    sb.append("           ,B.S_NAME                   \n");     
		    sb.append("           ,C.SG_NAME                  \n");  	
		    sb.append("      FROM EX_SITE_URL A, SG_S_RELATION B, SITE_GROUP C  	\n");
		    sb.append("      WHERE A.S_SEQ = B.S_SEQ                        	\n");
		    sb.append("         AND B.SG_SEQ = C.SG_SEQ                     	\n");
		    sb.append("         AND A.USEYN = 'Y'                		    	\n");
		    if(!s_seq.equals("")){
		    	sb.append("    		AND S_SEQ IN ("+s_seq+")                           \n");
		    }
		    if(!searchWords.equals("")){
//		    		sb.append("    		AND EX_URL LIKE '%"+searchWords+"%'                           \n");
		    	sb.append("    		AND  (S_NAME LIKE '%"+searchWords+"%'                     \n");
		    	sb.append("    		OR  EX_URL LIKE '%"+searchWords+"%'   )                  \n");
		    }
		    
		    //sb.append("    LIMIT 0, 10                      \n");
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    result = new ArrayList();
		    
		    String tmp[] = null;
		    
		    while( rs.next() ) {
		    	tmp = new String [5];
		    	tmp[0] = rs.getString("EX_SEQ");          
		    	tmp[1] = rs.getString("S_SEQ");             
		    	tmp[2] = rs.getString("EX_URL");             
		    	tmp[3] = rs.getString("S_NAME");            
		    	tmp[4] = rs.getString("SG_NAME");            
		    	
		    	result.add(tmp);
		    	
		    	tmp = null;
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
	public ArrayList updatePopUp_getExList(String ex_seq, String searchWords){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    ResultSet rs = null;	
	    ArrayList result = null;
	    
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb = new StringBuffer();
		    	
		    sb.append("   #EX_SITE_URL updatePopUp_getExList()               		\n");
		    sb.append("       SELECT A.S_SEQ                            \n");
		    sb.append("             ,B.EX_SEQ                           \n");
		    sb.append("             ,A.S_NAME                           \n");
		    sb.append("             ,B.EX_URL                           \n");
		  // sb.append("             ,B.EX_KEYWORD                       \n");
		    sb.append("             ,C.SG_NAME                          \n");
		    sb.append("       FROM SG_S_RELATION A, EX_SITE_URL B           \n");
		    sb.append("             ,SITE_GROUP C                       \n");
		    sb.append("       WHERE A.S_SEQ = B.S_SEQ                   \n");
		    sb.append("         AND A.SG_SEQ = C.SG_SEQ   				\n");	
		    sb.append("         AND B.USEYN = 'Y'		   				\n");	
		    
		    if(!ex_seq.equals("")){
		    	sb.append("    		AND B.EX_SEQ IN ("+ex_seq+")                           \n");
		    }
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    result = new ArrayList();
		    
		    String tmp[];
		    
		    while( rs.next() ) {
		    	tmp = new String [5];
		    	tmp[0] = rs.getString("S_SEQ");
		    	tmp[1] = rs.getString("EX_SEQ");
		    	tmp[2] = rs.getString("EX_URL");
		    	tmp[3] = rs.getString("S_NAME");
		    	tmp[4] = rs.getString("SG_NAME");
		    	
		    	result.add(tmp);
		    	
		    	tmp = null;
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
	
	public ArrayList getSitetList(String sg_seq, String s_seq, String mode){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    ResultSet rs = null;	
	    ArrayList result = null;
	    
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb = new StringBuffer();
		    
		    sb.append("   #SITE getSitetList()             \n");
		    sb.append("   #POPUP에 사용됨.                 \n"); 
		    sb.append("   SELECT  B.SG_SEQ                 \n"); 
		    sb.append("         , A.S_SEQ                  \n"); 
		    sb.append("         , A.S_NAME                 \n"); 
		    sb.append("         , A.S_URL                  \n"); 
		    sb.append("         , A.S_ACTIVE               \n"); 
		    sb.append("   FROM SITE A, SG_S_RELATION B     \n"); 
		    sb.append("   WHERE A.S_SEQ = B.S_SEQ          \n");
		    if(mode.equals("update")){
		    	
		    }
		    if(!sg_seq.equals("")){
		    	sb.append("     AND B.SG_SEQ  in ("+sg_seq+")		       \n"); 
		    }
		    if(!s_seq.equals("")){
		    	sb.append("     AND A.S_SEQ  in ("+s_seq+")		       \n"); 
		    }
		    sb.append("     AND A.S_ACTIVE = 1		       \n"); 
		    sb.append("   GROUP BY A.S_URL, A.S_NAME		   \n"); 
		    sb.append("   ORDER BY A.S_NAME      			   \n");
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    result = new ArrayList();
		    
		    String tmp[] = new String [5];
		    
		    while( rs.next() ) {
		    	tmp[0] = rs.getString("SG_SEQ");
		    	tmp[1] = rs.getString("S_SEQ");
		    	tmp[2] = rs.getString("S_NAME");
		    	tmp[3] = rs.getString("S_URL");
		    	tmp[4] = rs.getString("S_ACTIVE");
		    	
		    	result.add(tmp);
		    	tmp = null;
		    	tmp = new String[5];
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
	public ArrayList getSgSitetList(){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null; 
	    ResultSet rs = null;	
	    ArrayList result = null;
	    
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb = new StringBuffer();
		    
		    sb.append("   #SITE getSgSitetList()             \n");
		    sb.append("   SELECT SG_SEQ                    \n"); 
		    sb.append("          ,SG_NAME                  \n"); 
		    sb.append("          ,SG_REGDATE               \n"); 
		    sb.append("       FROM SITE_GROUP              \n"); 
		    
		    System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
		    
		    result = new ArrayList();
		    
		    String tmp[] = new String [3];
		    
		    while( rs.next() ) {
		    	tmp[0] = rs.getString("SG_SEQ");
		    	tmp[1] = rs.getString("SG_NAME");
		    	tmp[2] = rs.getString("SG_REGDATE");
		    	
		    	result.add(tmp);
		    	tmp = null;
		    	tmp = new String[3];
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
	public int insertExSite(String s_seq, String ex_url){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null;
	    int result = 0;
	    
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb = new StringBuffer();
		    sb.append("   #SITE insertExSite()               				 \n");                                                 
		    sb.append("       INSERT INTO EX_SITE_URL            			 \n");
		    sb.append("                   ( S_SEQ           				 \n");
		    sb.append("                     ,EX_URL           				 \n");
		    sb.append("                     ,USEYN            				 \n");
		    sb.append("                     ,REG_DATE                        \n");
		    sb.append("                    )                                 \n");
		    sb.append("            VALUES ( "+Integer.parseInt(s_seq)+"      \n");
		    sb.append("                     ,'"+ex_url.trim()+"'    		\n");
		    sb.append("                     ,'Y'            	 			\n");
		    sb.append("                     ,NOW()	            	 		\n");
		    sb.append("                    )                 	 			\n");
		    
		    System.out.println(sb.toString());
		    stmt.executeUpdate(sb.toString());
		    
	    } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
	    
		return result;
	}
	public int updateExSite(String ex_seq, String ex_url){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null;
	    int result = 0;
	    
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb = new StringBuffer();
		    sb.append("   #SITE updateExSite()                  \n");                                                 
		    sb.append("      UPDATE EX_SITE_URL                     \n");
		    sb.append("        SET EX_URL = '"+ex_url+"'           \n");
		    sb.append("        WHERE EX_SEQ = "+ex_seq+"          \n");
		    
		    System.out.println(sb.toString());
		    result = stmt.executeUpdate(sb.toString());
		    
	    } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
	    
		return result;
	}
	public int deleteExSite(String ex_seqs){
	    DBconn  dbconn  = null;   
	    Statement stmt  = null;
	    int result = 0;
	    
	    try{
	    	dbconn = new DBconn();
		    dbconn.getDBCPConnection();
		    stmt = dbconn.createStatement();
		    
		    sb = new StringBuffer();
		    sb.append("   #SITE deleteExSite()                  \n");                                                 
		    //sb.append("      DELETE FROM EX_SITE_URL                \n");
		    //sb.append("         WHERE EX_SEQ IN ("+ex_seqs+")         		\n");
		   
		    sb.append("      UPDATE EX_SITE_URL                     \n");
		    sb.append("        SET USEYN = 'N'           \n");
		    sb.append("         WHERE EX_SEQ IN ("+ex_seqs+")         		\n");
		    
		    System.out.println(sb.toString());
		    stmt.executeUpdate(sb.toString());
		    
	    } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
	    
		return result;
	}
}
