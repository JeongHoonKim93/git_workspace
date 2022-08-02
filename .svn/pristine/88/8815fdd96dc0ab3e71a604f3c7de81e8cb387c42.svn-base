package risk.admin.info;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class InfoGroupMgr {
	
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private StringBuffer sb = null;	
	private InfoGroupBean igBean = null;
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	
	
	/**
     * InfoGroup 등록
     * @param I_SEQ
     * @return
     */
	public boolean insertInfoGroup(InfoGroupBean igBean)
	{		
		boolean result = false;
		String insertNum = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" INSERT INTO INFO_GROUP(	                            		   \n");				
			sb.append("                   I_NM		                               \n");			
			sb.append("                   ,I_REGDATE                                 \n");
			sb.append("                   ,M_SEQ                                  \n");
			sb.append(" 				  )                                        \n");
			sb.append(" VALUES(                                                    \n");
			sb.append("                   '"+igBean.getI_nm()+"'                               \n");
			sb.append("                   ,'"+igBean.getI_regdate()+"'                               \n");
			sb.append("                   ,"+igBean.getM_seq()+"                               \n");
			sb.append(" )                                                    \n");
					
			System.out.println(sb.toString());
			if(stmt.executeUpdate(sb.toString())>0) result = true;																
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
		    sb = null;
		    if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}        
		return result;
	}
	
	/**
     * InfoGroup 등록
     * @param I_SEQ
     * @return
     */
	public boolean updateInfoGroup(InfoGroupBean igBean)
	{		
		boolean result = false;
		String insertNum = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" UPDATE INFO_GROUP	                            		   \n");				
			sb.append(" SET  I_NM = '"+igBean.getI_nm()+"'		                   \n");				
			sb.append("     ,M_SEQ = "+igBean.getM_seq()+"                         \n");		
			sb.append(" WHERE I_SEQ = "+igBean.getI_seq()+"                        \n");
					
			System.out.println(sb.toString());
			if(stmt.executeUpdate(sb.toString())>0) result = true;																
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
		    sb = null;
		    if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}        
		return result;
	}
	
	/**
     * InfoGroup 등록
     * @param I_SEQ
     * @return
     */
	public boolean DeleteInfoGroup(String i_seq)
	{		
		boolean result = false;
		String insertNum = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" DELETE FROM INFO_GROUP WHERE I_SEQ IN ("+i_seq+")                		   \n");				
			
			System.out.println(sb.toString());
			if(stmt.executeUpdate(sb.toString())>0) result = true;																
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
		    sb = null;
		    if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}        
		return result;
	}
	
	public InfoGroupBean getInfoGroupBean(String i_seq)
	{
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" SELECT I_SEQ, I_NM,  DATE_FORMAT(I_REGDATE,'%Y.%m.%d') AS I_REGDATE, M_SEQ  \n");	
			sb.append("        , (SELECT M_NAME FROM MEMBER WHERE M_SEQ = I.M_SEQ )M_NAME \n");	
			sb.append(" FROM INFO_GROUP I                            		   \n");
			sb.append(" WHERE I_SEQ = "+i_seq+"                           		   \n");	
			sb.append(" ORDER BY I_SEQ DESC                            		   \n");				
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{				
				igBean = new InfoGroupBean();
				igBean.setI_seq(rs.getString("I_SEQ"));
				igBean.setI_nm(rs.getString("I_NM"));
				igBean.setI_regdate(rs.getString("I_REGDATE"));
				igBean.setM_name(rs.getString("M_NAME"));
				igBean.setM_seq(rs.getString("M_SEQ"));				
			}
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
		    sb = null;
		    if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}        
		return igBean;
	}
	
	/**
     * InfoGroup 리스트
     * @param 
     * @return
     */
	public ArrayList getInfoGroup()
	{		
		ArrayList arr = new ArrayList();
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" SELECT I_SEQ, I_NM,  DATE_FORMAT(I_REGDATE,'%Y.%m.%d') AS I_REGDATE, M_SEQ  \n");	
			sb.append("        , (SELECT M_NAME FROM MEMBER WHERE M_SEQ = I.M_SEQ )M_NAME \n");	
			sb.append(" FROM INFO_GROUP I                            		   \n");
			sb.append(" ORDER BY I_SEQ DESC                            		   \n");				
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next())
			{				
				igBean = new InfoGroupBean();
				igBean.setI_seq(rs.getString("I_SEQ"));
				igBean.setI_nm(rs.getString("I_NM"));
				igBean.setI_regdate(rs.getString("I_REGDATE"));
				igBean.setM_name(rs.getString("M_NAME"));
				igBean.setM_seq(rs.getString("M_SEQ"));
				arr.add(igBean);
			}
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
		    sb = null;
		    if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}        
		return arr;
	}
}
