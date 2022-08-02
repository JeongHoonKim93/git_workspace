package risk.admin.usergroup;

import java.io.*;
import java.sql.*;
import java.util.*;

import risk.DBconn.DBconn;
import risk.util.Log;
import risk.util.DateUtil;
import risk.util.StringUtil;
import risk.search.MetaMgr;
import risk.statistics.StatisticsBean;
import risk.admin.site.SiteBean;
import risk.issue.IssueDataBean;

public class UserGroupMgr {
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private ResultSet rs2    = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = null;	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
    
    private StatisticsBean statisticsBean = null;
    UserGroupSuperBean superBean = new UserGroupSuperBean();

    String menuUrls = "";    
	public String getMenuUrls() {
		return menuUrls;
	}


	public ArrayList getMenu() {
		
		ArrayList arrResult = new ArrayList();
		
		UserGroupSuperBean.MenuBean childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();						
			sb.append("SELECT ME_SEQ			\n");
			sb.append("     , ME_XP				\n");
			sb.append("     , ME_YP				\n");
			sb.append("     , ME_ZP				\n");
			sb.append("     , ME_NAME			\n");
			sb.append("     , ME_USEYN			\n");
			sb.append("  FROM MENU				\n");
			sb.append("  WHERE ME_USEYN = 'Y'	\n");
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
				
				childBean = superBean.new MenuBean();
				
				childBean.setMe_seq(rs.getString("ME_SEQ"));
				childBean.setMe_xp(rs.getString("ME_XP"));
				childBean.setMe_yp(rs.getString("ME_YP"));
				childBean.setMe_zp(rs.getString("ME_ZP"));
				childBean.setMe_name(rs.getString("ME_NAME"));
				childBean.setMe_useyn(rs.getString("ME_USEYN"));
				
				arrResult.add(childBean);
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	public ArrayList getSearchYp(String xp, String meSeq) {
		return getSearchYp(xp, meSeq, "");
	}
	public ArrayList getSearchYp(String xp, String meSeq, String UseYn) {
		
		ArrayList arrResult = new ArrayList();
		
		
		UserGroupSuperBean.MenuBean childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();						
			sb.append("SELECT A.ME_SEQ					\n");
			sb.append("     , A.ME_XP						\n");
			sb.append("     , A.ME_YP						\n");
			sb.append("     , A.ME_ZP						\n");
			sb.append("     , A.ME_NAME					\n");
			sb.append("     , A.ME_URL					\n");
			sb.append("     , A.ME_IMG_ON					\n");
			sb.append("     , A.ME_IMG_OFF				\n");
			sb.append("     , (SELECT COUNT(*) FROM MENU WHERE ME_XP = A.ME_XP AND ME_YP = A.ME_YP AND ME_ZP > 0) AS ZP_CNT \n");
			sb.append("  FROM MENU A 						\n");
			sb.append(" WHERE A.ME_XP = "+xp+"			\n");
			sb.append("   AND A.ME_YP > 0 				\n");
			sb.append("   AND A.ME_ZP = 0 				\n");
			sb.append("   AND A.ME_SEQ IN ("+meSeq+")		\n");
			
			if(UseYn.equals("")){
				sb.append("   AND ME_USEYN = 'Y'	\n");
			}
			
			sb.append(" ORDER BY 2,3,4					\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
				
				
				if(menuUrls.equals("")){
					menuUrls = rs.getString("ME_URL");
				}else{
					menuUrls += ',' + rs.getString("ME_URL");
				}
				
			
				childBean = superBean.new MenuBean();
				
				childBean.setMe_seq(rs.getString("ME_SEQ"));
				childBean.setMe_xp(rs.getString("ME_XP"));
				childBean.setMe_yp(rs.getString("ME_YP"));
				childBean.setMe_zp(rs.getString("ME_ZP"));
				childBean.setMe_name(rs.getString("ME_NAME"));
				childBean.setMe_url(rs.getString("ME_URL"));
				childBean.setMe_img_on(rs.getString("ME_IMG_ON"));
				childBean.setMe_img_off(rs.getString("ME_IMG_OFF"));
				childBean.setMe_zp_cnt(rs.getInt("ZP_CNT"));
			
				arrResult.add(childBean);
			
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	
	
	
	public ArrayList getSearchZp(String xp, String yp) {
		return getSearchZp(xp, yp, "", "");
	}
	
	public ArrayList getSearchZp(String xp, String yp, String meSeq) {
		return getSearchZp(xp, yp, "", meSeq);
	}
	
	public ArrayList getSearchZp(String xp, String yp, String UseYn, String meSeq) {
		
		ArrayList arrResult = new ArrayList();
		
		
		UserGroupSuperBean.MenuBean childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();						
			sb.append("SELECT ME_SEQ					\n");
			sb.append("     , ME_XP						\n");
			sb.append("     , ME_YP						\n");
			sb.append("     , ME_ZP						\n");
			sb.append("     , ME_NAME					\n");
			sb.append("     , ME_URL					\n");
			sb.append("     , ME_IMG_ON					\n");
			sb.append("     , ME_IMG_OFF				\n");
			sb.append("  FROM MENU 						\n");
			sb.append(" WHERE ME_XP = "+xp+"			\n");
			sb.append("   AND ME_YP = "+yp+"			\n");
			sb.append("   AND ME_ZP > 0 				\n");
			if(!meSeq.equals("")){
				sb.append("   AND ME_SEQ IN ("+meSeq+")		\n");
			}
			
			
			if(UseYn.equals("")){
				sb.append("   AND ME_USEYN = 'Y'	\n");
			}
			
			sb.append(" ORDER BY 2,3,4					\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
			
				childBean = superBean.new MenuBean();
				
				childBean.setMe_seq(rs.getString("ME_SEQ"));
				childBean.setMe_xp(rs.getString("ME_XP"));
				childBean.setMe_yp(rs.getString("ME_YP"));
				childBean.setMe_zp(rs.getString("ME_ZP"));
				childBean.setMe_name(rs.getString("ME_NAME"));
				childBean.setMe_url(rs.getString("ME_URL"));
				childBean.setMe_img_on(rs.getString("ME_IMG_ON"));
				childBean.setMe_img_off(rs.getString("ME_IMG_OFF"));
			
				arrResult.add(childBean);
			
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	
	public String getSearchTop(String xp, String meSeq) {
		return getSearchTop(xp, meSeq, "");
	}
	public String getSearchTop(String xp, String meSeq, String search_seq) {
		
		String result = "";
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();						
			sb.append("SELECT ME_URL    				\n");
			sb.append("  FROM MENU 						\n");
			sb.append(" WHERE ME_XP = "+xp+" 			\n");
			sb.append("   AND ME_YP > 0 				\n");
			sb.append("   AND ME_ZP = 0 				\n");
			sb.append("   AND ME_SEQ IN ("+meSeq+")		\n");
			if(!search_seq.equals("")){
				sb.append("   AND ME_YP = "+search_seq+"	\n");
			}
			sb.append("   AND ME_USEYN = 'Y'  			\n");
			sb.append(" ORDER BY ME_YP ASC				\n");
			sb.append(" LIMIT 1							\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if( rs.next() ) {
				result = rs.getString("ME_URL");
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	public ArrayList getDetalMenu(ArrayList data, String type){
		ArrayList result = new ArrayList();
		UserGroupSuperBean.MenuBean childBean = null;
		
		for(int i =0; i < data.size(); i++){
			childBean = (UserGroupSuperBean.MenuBean)data.get(i);
			if(type.equals("xp")){
				if(childBean.getMe_yp().equals("0") && childBean.getMe_zp().equals("0") ){
					result.add(childBean);
				}
			}else if(type.equals("yp")){
				if(!childBean.getMe_yp().equals("0") && childBean.getMe_zp().equals("0") ){
					result.add(childBean);
				}
			}else if(type.equals("zp")){
				if(!childBean.getMe_zp().equals("0") ){
					result.add(childBean);
				}
			}
		}
		return result;
	}
	
	public ArrayList getMenuYp(ArrayList data, String xp){
		ArrayList result = new ArrayList();
		UserGroupSuperBean.MenuBean childBean = null;
		for(int i =0; i < data.size(); i++){
			childBean = (UserGroupSuperBean.MenuBean)data.get(i);
			
			if(childBean.getMe_xp().equals(xp) && !childBean.getMe_yp().equals("0")){
				result.add(childBean);
			}
		}
		return result;
	}
	
}
