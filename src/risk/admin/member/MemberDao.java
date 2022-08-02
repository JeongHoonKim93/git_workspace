
package risk.admin.member;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;

public class MemberDao {	
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private PreparedStatement psmt = null;
    private ResultSet rs    = null;
    private StringBuffer sb = null;
    List memList = null;
    private MemberBean mBean = new MemberBean();
    DateUtil du = new DateUtil();    
   
    public int totalct = 0;	
		
    public MemberBean schID(String memberId, String memberPass) throws Exception{
    	return getMember( "M_ID", memberId, memberPass );
    }
    
    public MemberBean schID(String memberId) throws Exception{
    	return getMember( "M_ID", memberId);
    }
    
    public MemberBean schName(String memberName) throws Exception{
    	return getMember( "M_NAME", memberName );
    }

	public MemberBean schSeq(String memberSeq) throws Exception{
    	return getMember( "M_SEQ", memberSeq );
    }
		
	public boolean checkId_old(String id){
		boolean result = false;
		int intCnt = 0;
		try{
			dbconn  = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("SELECT count(m_id) FROM MEMBER  \n");
			sb.append("WHERE M_ID = '"+id+"'   \n");       
			//System.out.println(sb.toString());
			stmt = dbconn.createStatement( );
	        rs = stmt.executeQuery( sb.toString());
	        
	        if(rs.next())
	        	intCnt = rs.getInt(1);
	        	if(intCnt > 0 )
	        		result = true;
		
			} catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
				if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			return result;
	}	
	
	
	public boolean checkId(String id){
		boolean result = false;
		int intCnt = 0;
		try{
			dbconn  = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("SELECT count(m_id) FROM MEMBER  \n");
			sb.append("WHERE M_ID = ?   \n");       
			//System.out.println(sb.toString());
	        
			psmt = dbconn.createPStatement(sb.toString());
       		psmt.setString(1, id);
            rs = psmt.executeQuery();
			
	        if(rs.next())
	        	intCnt = rs.getInt(1);
	        	if(intCnt > 0 )
	        		result = true;
		
			} catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (psmt != null) try { psmt.close(); } catch(SQLException ex) {}
				if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			return result;
	}
	
	public boolean chk_ip( String target_ip ){
		boolean result = false;
		ArrayList<String> permit_ipList = getPermitIp();
		if(permit_ipList.size() > 0){
			for(int i=0; i<permit_ipList.size(); i++){
				if(target_ip.equals(permit_ipList.get(i))){
					result = true;
					break;
				}
			}
		}
		
		return result;
	}
	
	public  ArrayList<String>  getPermitIp() {   
		ArrayList<String> result = new ArrayList<String>() ;
		
        try {
        	dbconn  = new DBconn();
        	dbconn.getDBCPConnection();
        	
        	
        	sb = new StringBuffer();        	
       	 	sb.append("SELECT  IP 	\n");
       	 	sb.append("FROM MEMBER_IP 	\n");
       	
       		//System.out.println( sb.toString() );
       	 	psmt = dbconn.createPStatement(sb.toString());
       	 	rs = psmt.executeQuery();
       	 	
            while (rs.next()) {
            	result.add(rs.getString("IP"));
            }
          
            
        } catch(SQLException ex) {
        	ex.printStackTrace();
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (psmt != null) try { psmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        
        System.out.println("-------5-------------");
        return result;
    }
	
	
	public MemberBean getMember_old(String SchField, String schval, String memberPass) throws  Exception {   
		MemberBean member = null ;
        try {
        	dbconn  = new DBconn();
        	dbconn.getDBCPConnection();
        	
        	
        	sb = new StringBuffer();        	
       	 	sb.append("SELECT M_SEQ, M_ID, M_PASS, M_NAME, M_DEPT, M_POSITION, 													\n");
       	 	sb.append("       M_MAIL, M_TEL, M_HP, MG_SEQ , M_REGDATE, M_ASS_GRADE, M_PRO_GRADE, M_APP_GRADE, M_ORGVIEW_USEYN 	\n");
       	 	sb.append("FROM MEMBER \n");
       	 	sb.append("WHERE 1=1 \n");
			if( SchField.equals("M_SEQ") )
       	 	sb.append("   AND M_SEQ = '"+schval+"' \n");
       	 	if( SchField.equals("M_ID") )
       	 	sb.append("   AND M_ID = '"+schval+"' \n");
       	 	if( SchField.equals("M_NAME") )
       	 	sb.append("   AND M_NAME = '"+schval+"' \n");
       	 	if( SchField.equals("M_MAIL") )
       	 	sb.append("   AND M_MAIL = '"+schval+"' \n");
       		if( SchField.equals("M_TEL") )
       	 	sb.append("   AND M_TEL = '"+schval+"' \n");
       	
       		System.out.println( sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            if (rs.next()) {
            	member = new MemberBean();
            	member.setM_seq(rs.getString("M_SEQ"));
            	member.setM_id(rs.getString("M_ID"));
            	member.setM_pass(rs.getString("M_PASS"));
            	member.setM_name(rs.getString("M_NAME"));
            	member.setM_dept(rs.getString("M_DEPT"));
            	member.setM_position(rs.getString("M_POSITION"));
            	member.setM_mail(rs.getString("M_MAIL"));
            	member.setM_tel(rs.getString("M_TEL"));
            	member.setM_hp(rs.getString("M_HP"));
            	member.setMg_seq(rs.getString("MG_SEQ"));				
            	member.setM_regdate(rs.getString("M_REGDATE"));		
            	mBean.setM_ass_grade(rs.getString("M_ASS_GRADE"));
        		mBean.setM_pro_grade(rs.getString("M_PRO_GRADE"));
        		mBean.setM_app_grade(rs.getString("M_APP_GRADE"));
        		member.setM_orgview_useyn(rs.getString("M_ORGVIEW_USEYN"));
            }
          
        } catch(SQLException ex) {
        	ex.printStackTrace();
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        
        System.out.println("-------5-------------");
        return member;
    }
	
	
	public MemberBean getMember(String SchField, String schval, String memberPass) throws  Exception {   
		MemberBean member = null ;
        try {
        	dbconn  = new DBconn();
        	dbconn.getDBCPConnection();
        	
        	
        	sb = new StringBuffer();        	
       	 	sb.append("SELECT M_SEQ, M_ID, M_PASS, M_NAME, M_DEPT, M_POSITION, 													\n");
       	 	sb.append("       M_MAIL, M_TEL, M_HP, MG_SEQ , M_REGDATE, M_ASS_GRADE, M_PRO_GRADE, M_APP_GRADE, M_ORGVIEW_USEYN 	\n");
       	 	sb.append("FROM MEMBER \n");
       	 	sb.append("WHERE 1=1 \n");
			if( SchField.equals("M_SEQ") )
       	 	sb.append("   AND M_SEQ = ? \n");
       	 	if( SchField.equals("M_ID") )
       	 	sb.append("   AND M_ID = ? \n");
       	 	if( SchField.equals("M_NAME") )
       	 	sb.append("   AND M_NAME = ?	\n");
       	 	if( SchField.equals("M_MAIL") )
       	 	sb.append("   AND M_MAIL = ?	\n");
       		if( SchField.equals("M_TEL") )
       	 	sb.append("   AND M_TEL = ? \n");
       	
       		//System.out.println( sb.toString() );
       		
       		psmt = dbconn.createPStatement(sb.toString());
       		psmt.clearParameters();
       		if(!"".equals(SchField)){
       			psmt.setString(1, schval);
       		}
            rs = psmt.executeQuery();
            if (rs.next()) {
            	member = new MemberBean();
            	member.setM_seq(rs.getString("M_SEQ"));
            	member.setM_id(rs.getString("M_ID"));
            	member.setM_pass(rs.getString("M_PASS"));
            	member.setM_name(rs.getString("M_NAME"));
            	member.setM_dept(rs.getString("M_DEPT"));
            	member.setM_position(rs.getString("M_POSITION"));
            	member.setM_mail(rs.getString("M_MAIL"));
            	member.setM_tel(rs.getString("M_TEL"));
            	member.setM_hp(rs.getString("M_HP"));
            	member.setMg_seq(rs.getString("MG_SEQ"));				
            	member.setM_regdate(rs.getString("M_REGDATE"));		
            	mBean.setM_ass_grade(rs.getString("M_ASS_GRADE"));
        		mBean.setM_pro_grade(rs.getString("M_PRO_GRADE"));
        		mBean.setM_app_grade(rs.getString("M_APP_GRADE"));
        		member.setM_orgview_useyn(rs.getString("M_ORGVIEW_USEYN"));
            }
          
        } catch(SQLException ex) {
        	ex.printStackTrace();
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (psmt != null) try { psmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        
        System.out.println("-------5-------------");
        return member;
    }
	
	public MemberBean getMember(String SchField, String schval)	throws  Exception {
    	
        try {
        	dbconn  = new DBconn();
       	 	dbconn.getDBCPConnection();
       	 	
       	    sb = new StringBuffer(); 
       	 	sb.append("SELECT M_SEQ, M_ID, M_PASS, M_NAME, M_DEPT, M_POSITION, \n");
       	 	sb.append("       M_MAIL, M_TEL, M_HP, MG_SEQ, M_ASS_GRADE, M_PRO_GRADE, M_APP_GRADE \n");
       	 	sb.append("FROM MEMBER \n");
       	 	sb.append("WHERE 1=1 \n");
			if( SchField.equals("M_SEQ") )
       	 	sb.append("   and M_SEQ = ? \n");
       	 	if( SchField.equals("M_ID") )
       	 	sb.append("   and M_ID = ? \n");
       	 	if( SchField.equals("M_NAME") )
       	 	sb.append("   and M_NAME = ? \n");
       	 	if( SchField.equals("M_MAIL") )
       	 	sb.append("   and M_MAIL = ? \n");
       		if( SchField.equals("M_TEL") )
       	 	sb.append("   and M_TEL = ? \n");
       	       	 
       		psmt = dbconn.createPStatement(sb.toString());
       		psmt.clearParameters();
       		if(!"".equals(SchField)){
       			psmt.setString(1, schval);
       		}
            rs = psmt.executeQuery();
            
            if (rs.next()) {
            	mBean = new MemberBean();
        		mBean.setM_seq(rs.getString("M_SEQ"));
        		mBean.setM_id(rs.getString("M_ID"));
        		mBean.setM_pass(rs.getString("M_PASS"));
        		mBean.setM_name(rs.getString("M_NAME"));
        		mBean.setM_dept(rs.getString("M_DEPT"));
        		mBean.setM_position(rs.getString("M_POSITION"));
        		mBean.setM_mail(rs.getString("M_MAIL"));
        		mBean.setM_tel(rs.getString("M_TEL"));
        		mBean.setM_hp(rs.getString("M_HP"));
        		mBean.setMg_seq(rs.getString("MG_SEQ"));
        		mBean.setM_ass_grade(rs.getString("M_ASS_GRADE"));
        		mBean.setM_pro_grade(rs.getString("M_PRO_GRADE"));
        		mBean.setM_app_grade(rs.getString("M_APP_GRADE"));
            }
            
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (psmt != null) try { psmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return mBean;
    }
	
public MemberBean getMember_old(String SchField, String schval)	throws  Exception {
    	
        try {
        	dbconn  = new DBconn();
       	 	dbconn.getDBCPConnection();
       	 	
       	    sb = new StringBuffer(); 
       	 	sb.append("SELECT M_SEQ, M_ID, M_PASS, M_NAME, M_DEPT, M_POSITION, \n");
       	 	sb.append("       M_MAIL, M_TEL, M_HP, MG_SEQ, M_ASS_GRADE, M_PRO_GRADE, M_APP_GRADE \n");
       	 	sb.append("FROM MEMBER \n");
       	 	sb.append("WHERE 1=1 \n");
			if( SchField.equals("M_SEQ") )
       	 	sb.append("   and M_SEQ = '"+schval+"' \n");
       	 	if( SchField.equals("M_ID") )
       	 	sb.append("   and M_ID = '"+schval+"' \n");
       	 	if( SchField.equals("M_NAME") )
       	 	sb.append("   and M_NAME = '"+schval+"' \n");
       	 	if( SchField.equals("M_MAIL") )
       	 	sb.append("   and M_MAIL = '"+schval+"' \n");
       		if( SchField.equals("M_TEL") )
       	 	sb.append("   and M_TEL = '"+schval+"' \n");
       	       	 
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            if (rs.next()) {
            	mBean = new MemberBean();
        		mBean.setM_seq(rs.getString("M_SEQ"));
        		mBean.setM_id(rs.getString("M_ID"));
        		mBean.setM_pass(rs.getString("M_PASS"));
        		mBean.setM_name(rs.getString("M_NAME"));
        		mBean.setM_dept(rs.getString("M_DEPT"));
        		mBean.setM_position(rs.getString("M_POSITION"));
        		mBean.setM_mail(rs.getString("M_MAIL"));
        		mBean.setM_tel(rs.getString("M_TEL"));
        		mBean.setM_hp(rs.getString("M_HP"));
        		mBean.setMg_seq(rs.getString("MG_SEQ"));
        		mBean.setM_ass_grade(rs.getString("M_ASS_GRADE"));
        		mBean.setM_pro_grade(rs.getString("M_PRO_GRADE"));
        		mBean.setM_app_grade(rs.getString("M_APP_GRADE"));
            }
            
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return mBean;
    }
	
    public List getList(String nowpage) throws ClassNotFoundException, Exception {
    	return getList( nowpage, "", "" );
    }

    public List getList_old(String nowpage, String field, String val) throws ClassNotFoundException, Exception {
    	
    	int Nowpage = Integer.parseInt(nowpage);
    	int StartNo=0;
    	int EndNo=0;
    	StartNo = (Nowpage-1) * 10;
    	EndNo = 10;
    	
    	try {
	        	dbconn  = new DBconn();
	       	 	dbconn.getDBCPConnection();
	       	 	sb = new StringBuffer();
	       	 	       	 	       	 
				sb.append("  SELECT M.M_SEQ, M.M_ID, M.M_NAME, M.M_DEPT, M.M_MAIL, M.M_HP, MG.MG_NAME,M.MG_SEQ \n");
				sb.append("  FROM MEMBER_GROUP MG, \n");
				sb.append("       (SELECT   M_SEQ, M_ID, M_NAME, M_DEPT, M_MAIL, M_HP, MG_SEQ \n");
				sb.append("        														             \n");
				sb.append("        FROM MEMBER \n");
				sb.append("        WHERE MG_SEQ != (SELECT MG_SEQ \n");
				sb.append("                         FROM MEMBER_GROUP \n");
				sb.append("                         WHERE MG_NAME = 'SYSTEM') \n");
				if( field.equals("m_id") )
				sb.append("                               AND M_ID LIKE '%"+val+"%' \n");
				if( field.equals("m_name") )
				sb.append("                               AND M_NAME LIKE '%"+val+"%' \n");
				if( field.equals("m_dept") )
				sb.append("                               AND M_DEPT LIKE '%"+val+"%' \n");
				if( field.equals("m_mail") )
				sb.append("                               AND M_MAIL LIKE '%"+val+"%' \n");
				if( field.equals("m_hp") )
				sb.append("                               AND M_HP LIKE '%"+val+"%' \n");				
				sb.append("              			) M \n");
				sb.append("  WHERE MG.MG_SEQ = M.MG_SEQ \n");				
				if( field.equals("mg_name") )
				sb.append("        AND MG_NAME LIKE '%"+val+"%' \n"); 
				sb.append("  LIMIT "+StartNo+","+EndNo+" \n"); 
				
	       	 	
				System.out.print( sb.toString() );
	            stmt = dbconn.createStatement();
	            rs = stmt.executeQuery(sb.toString());

	            memList = new ArrayList();
	            while (rs.next()) {
	                MemberBean member = new MemberBean();

					member.setM_seq(rs.getString(1));
					member.setM_id(rs.getString(2));
				    member.setM_name(rs.getString(3));
				    member.setM_dept(rs.getString(4));
					member.setM_mail(rs.getString(5));
				    member.setM_hp(rs.getString(6));				   
					member.setMg_name(rs.getString(7));
					member.setMg_seq(rs.getString(8));

					memList.add(member);
	            }
	            
	       	 	sb = new StringBuffer();
				sb.append("  SELECT COUNT(1) CT \n");
				sb.append("          FROM MEMBER_GROUP MG, \n");
				sb.append("               (SELECT M_SEQ, MG_SEQ \n");
				sb.append("                FROM MEMBER \n");
				sb.append("                WHERE MG_SEQ NOT IN (SELECT MG_SEQ \n");
				sb.append("                                      FROM MEMBER_GROUP \n");
				sb.append("                                      WHERE MG_NAME = 'SYSTEM') \n");
				if( field.equals("m_id") )
				sb.append("                                       AND M_ID LIKE '%"+val+"%' \n");
				if( field.equals("m_name") )
				sb.append("                                       AND M_NAME LIKE '%"+val+"%' \n");
				if( field.equals("m_dept") )
				sb.append("                                       AND M_DEPT LIKE '%"+val+"%' \n");
				if( field.equals("m_mail") )
				sb.append("                                       AND M_MAIL LIKE '%"+val+"%' \n");
				if( field.equals("m_hp") )
				sb.append("                                       AND M_HP LIKE '%"+val+"%' \n");				
				sb.append("                ) M \n");
				sb.append("         WHERE MG.MG_SEQ = M.MG_SEQ \n");
				if( field.equals("mg_name") )
				sb.append("           AND MG_NAME LIKE '%"+val+"%' \n");
	       	 	
				System.out.print( sb.toString() );
	            stmt = dbconn.createStatement();
	            rs = stmt.executeQuery( sb.toString() );
	            if( rs.next() ) {
	            	totalct = rs.getInt(1);
	            }
	            
	            
	        } catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	        }
		return memList;
    }
    
    
    public List getList(String nowpage, String field, String val) throws ClassNotFoundException, Exception {
    	
    	int Nowpage = Integer.parseInt(nowpage);
    	int StartNo=0;
    	int EndNo=0;
    	StartNo = (Nowpage-1) * 10;
    	EndNo = 10;
    	
    	try {
	        	dbconn  = new DBconn();
	       	 	dbconn.getDBCPConnection();
	       	 	sb = new StringBuffer();
	       	 	       	 	       	 
				sb.append("  SELECT M.M_SEQ, M.M_ID, M.M_NAME, M.M_DEPT, M.M_MAIL, M.M_HP, MG.MG_NAME,M.MG_SEQ \n");
				sb.append("  FROM MEMBER_GROUP MG, \n");
				sb.append("       (SELECT   M_SEQ, M_ID, M_NAME, M_DEPT, M_MAIL, M_HP, MG_SEQ \n");
				sb.append("        														             \n");
				sb.append("        FROM MEMBER \n");
				sb.append("        WHERE MG_SEQ != (SELECT MG_SEQ \n");
				sb.append("                         FROM MEMBER_GROUP \n");
				sb.append("                         WHERE MG_NAME = 'SYSTEM') \n");
				if( field.equals("m_id") )
				{sb.append("                               AND M_ID LIKE ? \n");}
				else if( field.equals("m_name") )
				{sb.append("                               AND M_NAME LIKE ? \n");}
				else if( field.equals("m_dept") )
				{sb.append("                               AND M_DEPT LIKE ? \n");}
				else if( field.equals("m_mail") )
				{sb.append("                               AND M_MAIL LIKE ? \n");}
				else if( field.equals("m_hp") )
				{sb.append("                               AND M_HP LIKE ? \n");}				
				sb.append("              			) M \n");
				sb.append("  WHERE MG.MG_SEQ = M.MG_SEQ \n");				
				if( field.equals("mg_name") )
				sb.append("        AND MG_NAME LIKE ? \n"); 
				sb.append("  LIMIT ?,? \n"); 
				
	       	 	
	            
	            psmt = dbconn.createPStatement(sb.toString());
	            psmt.clearParameters();
	            int idx = 1;
	       		if(!"".equals(field)) {
		            psmt.setString(idx++, "%"+val+"%");
		       		if( field.equals("mg_name") ){
		       			psmt.setString(idx++, "%"+val+"%");
		       		}
	       		}
	       		psmt.setInt(idx++, StartNo);
	       		psmt.setInt(idx++, EndNo);
	       		
    	
	            rs = psmt.executeQuery();
	            
	            
	            memList = new ArrayList();
	            while (rs.next()) {
	                MemberBean member = new MemberBean();

					member.setM_seq(rs.getString(1));
					member.setM_id(rs.getString(2));
				    member.setM_name(rs.getString(3));
				    member.setM_dept(rs.getString(4));
					member.setM_mail(rs.getString(5));
				    member.setM_hp(rs.getString(6));				   
					member.setMg_name(rs.getString(7));
					member.setMg_seq(rs.getString(8));

					memList.add(member);
	            }
	            
	       	 	sb = new StringBuffer();
				sb.append("  SELECT COUNT(1) CT \n");
				sb.append("          FROM MEMBER_GROUP MG, \n");
				sb.append("               (SELECT M_SEQ, MG_SEQ \n");
				sb.append("                FROM MEMBER \n");
				sb.append("                WHERE MG_SEQ NOT IN (SELECT MG_SEQ \n");
				sb.append("                                      FROM MEMBER_GROUP \n");
				sb.append("                                      WHERE MG_NAME = 'SYSTEM') \n");
				if( field.equals("m_id") )
				sb.append("                                       AND M_ID LIKE ? \n");
				if( field.equals("m_name") )
				sb.append("                                       AND M_NAME LIKE ? \n");
				if( field.equals("m_dept") )
				sb.append("                                       AND M_DEPT LIKE ? \n");
				if( field.equals("m_mail") )
				sb.append("                                       AND M_MAIL LIKE ? \n");
				if( field.equals("m_hp") )
				sb.append("                                       AND M_HP LIKE ? \n");				
				sb.append("                ) M \n");
				sb.append("         WHERE MG.MG_SEQ = M.MG_SEQ \n");
				if( field.equals("mg_name") )
				sb.append("           AND MG_NAME LIKE ?	\n");
	       	 	
				//System.out.print( sb.toString() );
	            psmt = dbconn.createPStatement(sb.toString());
	            psmt.clearParameters();
	            idx = 1;
	            if(!"".equals(field)) {
		       		psmt.setString(idx++, "%"+val+"%");
		       		if( field.equals("mg_name") ){
		       			psmt.setString(idx++, "%"+val+"%");
		       		}
	            }
	            rs = psmt.executeQuery();
	            
	            if( rs.next() ) {
	            	totalct = rs.getInt(1);
	            }
	            
	            
	        } catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (psmt != null) try { psmt.close(); } catch(SQLException ex) {}
	            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	        }
		return memList;
    }
    
    
    public void insertMember_old(MemberBean MBean) throws  Exception {
        
		String Mseq = null;
		
		String m_date = du.addDay(du.getCurrentDate("yyyy-MM-dd"),-1).replaceAll("-", "");
		String m_time = du.getCurrentDate("HHmmss"); 
		
	        try {
        	dbconn  = new DBconn();
        	dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			String	hp = MBean.getM_hp();

			sb = new StringBuffer();
			sb.append("INSERT INTO MEMBER(M_ID,M_PASS,M_NAME,M_DEPT,M_POSITION,M_MAIL,M_TEL,M_HP,MG_SEQ,M_REGDATE, M_ASS_GRADE, M_PRO_GRADE, M_APP_GRADE, AG_SEQ)  \n");
			sb.append("VALUES ( \n");
			sb.append("        '"+MBean.getM_id()+"' \n");
			sb.append("        ,SHA2('"+MBean.getM_pass()+"',256) \n");
			sb.append("        ,'"+MBean.getM_name()+"' \n");
			sb.append("        ,'"+MBean.getM_dept()+"' \n");
			sb.append("        ,'"+MBean.getM_position()+"' \n");
			sb.append("        ,'"+MBean.getM_mail()+"' \n");
			sb.append("        ,'"+MBean.getM_tel()+"' \n");
			sb.append("        ,'"+MBean.getM_hp()+"' \n");
			sb.append("        ,'"+MBean.getMg_seq()+"' \n");			
			sb.append("        ,'"+MBean.getM_regdate()+"' \n");
			sb.append("        ,"+MBean.getM_ass_grade()+" \n");
			sb.append("        ,"+MBean.getM_pro_grade()+" \n");
			sb.append("        ,"+MBean.getM_app_grade()+" \n");
			sb.append("        ,"+MBean.getAg_seq()+" \n");
			sb.append("        ) \n");
			
			System.out.print( sb.toString() );
			//stmt = dbconn.createStatement();
            stmt.executeUpdate(sb.toString());
            
            
            
            
            /*
            sb = new StringBuffer();
            sb.append("SELECT M_SEQ FROM MEMBER WHERE M_ID='"+MBean.getM_id()+"' \n");
            
			//System.out.print( sb.toString() );
			stmt = dbconn.createStatement();
			rs =  stmt.executeQuery(sb.toString());
			if( rs.next() ) {
				Mseq = rs.getString("M_SEQ");
				sb = new StringBuffer();
            
				sb.append("INSERT INTO MAP_MEMBER_AUTH \n");
				sb.append("            ( M_SEQ, IC_TYPE, IC_CODE) \n");
				sb.append("      VALUES \n");
				String[] ic_code_list = MBean.getIc_code().split(",");
				for (int i = 0; i < ic_code_list.length; i++) {		
						sb.append("			( \n");
						sb.append("        	'"+Mseq+"' \n");
						sb.append("        	,'"+MBean.getIc_type()+"' \n");
						sb.append("        	,'"+ic_code_list[i]+"' \n");
						if (i != (ic_code_list.length-1)) {
							sb.append("        	), \n");
						} else {
							sb.append("        	); \n");						
						}
					}
				
				stmt = dbconn.createStatement();
				stmt.executeUpdate(sb.toString());
			}
			*/
	        	
			
            sb = new StringBuffer();
            sb.append("SELECT M_SEQ FROM MEMBER WHERE M_ID='"+MBean.getM_id()+"' \n");
            
			//System.out.print( sb.toString() );
			//stmt = dbconn.createStatement();
            System.out.print( sb.toString() );
            rs =  stmt.executeQuery(sb.toString());
			
			
			
			
			if( rs.next() ) {
				Mseq = rs.getString("M_SEQ");					
				
	            sb = new StringBuffer();            
	            sb.append("INSERT INTO SETTING \n");
	            sb.append("            ( K_XP, ST_INTERVAL_DAY, MD_TYPE, SG_SEQ, ST_RELOAD_TIME, ST_LIST_CNT, ST_MENU, M_SEQ, ST_REGDATE, SG_SEQ_AL) \n");
	            sb.append("   (SELECT  S.K_XP, S.ST_INTERVAL_DAY, S.MD_TYPE, S.SG_SEQ, \n");
	            sb.append("           S.ST_RELOAD_TIME, S.ST_LIST_CNT, S.ST_MENU, "+Mseq+", '"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"', S.SG_SEQ_AL \n");
	            sb.append("    FROM SETTING S, (SELECT M_SEQ \n");
	            sb.append("                         FROM MEMBER \n");
	            sb.append("                         WHERE M_ID = '**NCS**') M \n");   
	            sb.append("    WHERE S.M_SEQ = M.M_SEQ) ");
	            
				//stmt = dbconn.createStatement();
	            System.out.print( sb.toString() );
	            stmt.executeUpdate(sb.toString());	            
	            sb = null;
	            
	            
	            if (MBean.getIc_code() !=null && !MBean.getIc_code().equals("")) {
					
				
	            	sb = new StringBuffer();
					sb.append("INSERT INTO MAP_MEMBER_AUTH \n");
					sb.append("            ( M_SEQ, IC_TYPE, IC_CODE) \n");
					sb.append("      VALUES \n");
					String[] ic_code_list = MBean.getIc_code().split(",");
					for (int i = 0; i < ic_code_list.length; i++) {		
							sb.append("			( \n");
							sb.append("        	'"+Mseq+"' \n");
							sb.append("        	,'"+MBean.getIc_type()+"' \n");
							sb.append("        	,'"+ic_code_list[i]+"' \n");
							if (i != (ic_code_list.length-1)) {
								sb.append("        	), \n");
							} else {
								sb.append("        	); \n");						
							}
						}
					
					System.out.print( sb.toString() );
					stmt.executeUpdate(sb.toString());
	            }
	            
	            
			}
            
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
    }

    
public void insertMember(MemberBean MBean) throws  Exception {
        
		String Mseq = null;
		
		String m_date = du.addDay(du.getCurrentDate("yyyy-MM-dd"),-1).replaceAll("-", "");
		String m_time = du.getCurrentDate("HHmmss"); 
		
	        try {
        	dbconn  = new DBconn();
        	dbconn.getDBCPConnection();
			
			String	hp = MBean.getM_hp();

			sb = new StringBuffer();
			sb.append("INSERT INTO MEMBER(M_ID,M_PASS,M_NAME,M_DEPT,M_POSITION,M_MAIL,M_TEL,M_HP,MG_SEQ,M_REGDATE, M_ASS_GRADE, M_PRO_GRADE, M_APP_GRADE, AG_SEQ)  \n");
			sb.append("VALUES ( \n");
			sb.append("        ? \n");
			sb.append("        ,? \n");
			sb.append("        ,? \n");
			sb.append("        ,? \n");
			sb.append("        ,? \n");
			sb.append("        ,? \n");
			sb.append("        ,? \n");
			sb.append("        ,? \n");
			sb.append("        ,? \n");			
			sb.append("        ,? \n");
			sb.append("        ,? \n");
			sb.append("        ,? \n");
			sb.append("        ,? \n");
			sb.append("        ,? \n");
			sb.append("        ) \n");
			
			//System.out.print( sb.toString() );
            
            psmt = dbconn.createPStatement(sb.toString());
            psmt.clearParameters();
            int idx = 1;
       		psmt.setString(idx++, MBean.getM_id());
       		psmt.setString(idx++, MBean.getM_pass());
       		psmt.setString(idx++, MBean.getM_name());
       		psmt.setString(idx++, MBean.getM_dept());
       		psmt.setString(idx++, MBean.getM_position());
       		psmt.setString(idx++, MBean.getM_mail());
       		psmt.setString(idx++, MBean.getM_tel());
       		psmt.setString(idx++, MBean.getM_hp());
       		psmt.setString(idx++, MBean.getMg_seq());
       		psmt.setString(idx++, MBean.getM_regdate());
       		psmt.setString(idx++, MBean.getM_ass_grade());
       		psmt.setString(idx++, MBean.getM_pro_grade());
       		psmt.setString(idx++, MBean.getM_app_grade());
       		psmt.setString(idx++, MBean.getAg_seq());

       		psmt.executeUpdate();
            
            
	        	
			
            sb = new StringBuffer();
            sb.append("SELECT M_SEQ FROM MEMBER WHERE M_ID = ? \n");
            
			
            psmt = dbconn.createPStatement(sb.toString());
            psmt.clearParameters();
       		psmt.setString(1, MBean.getM_id());
            rs = psmt.executeQuery();
			
			
			if( rs.next() ) {
				Mseq = rs.getString("M_SEQ");					
				
	            sb = new StringBuffer();            
	            sb.append("INSERT INTO SETTING \n");
	            sb.append("            ( K_XP, ST_INTERVAL_DAY, MD_TYPE, SG_SEQ, ST_RELOAD_TIME, ST_LIST_CNT, ST_MENU, M_SEQ, ST_REGDATE, SG_SEQ_AL) \n");
	            sb.append("   (SELECT  S.K_XP, S.ST_INTERVAL_DAY, S.MD_TYPE, S.SG_SEQ, \n");
	            sb.append("           S.ST_RELOAD_TIME, S.ST_LIST_CNT, S.ST_MENU, ? , '"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"', S.SG_SEQ_AL \n");
	            sb.append("    FROM SETTING S, (SELECT M_SEQ \n");
	            sb.append("                         FROM MEMBER \n");
	            sb.append("                         WHERE M_ID = '**NCS**') M \n");   
	            sb.append("    WHERE S.M_SEQ = M.M_SEQ) ");
	            
	            
	            
	            psmt = dbconn.createPStatement(sb.toString());
	            psmt.clearParameters();
	       		psmt.setString(1, Mseq);
	       		psmt.executeUpdate(); 
	            sb = null;
	            
	            
/*	            if (MBean.getIc_code() !=null && !MBean.getIc_code().equals("")) {
					
				
	            	sb = new StringBuffer();
					sb.append("INSERT INTO MAP_MEMBER_AUTH \n");
					sb.append("            ( M_SEQ, IC_TYPE, IC_CODE) \n");
					sb.append("      VALUES \n");
					String[] ic_code_list = MBean.getIc_code().split(",");
					for (int i = 0; i < ic_code_list.length; i++) {		
							sb.append("			( \n");
							sb.append("        	' ?  \n");
							sb.append("        	, ? \n");
							sb.append("        	, ? \n");
							if (i != (ic_code_list.length-1)) {
								sb.append("        	), \n");
							} else {
								sb.append("        	); \n");						
							}
					}
					
					
					psmt = dbconn.createPStatement(sb.toString());
					psmt.clearParameters();
					idx = 1;
					for (int i = 0; i < ic_code_list.length; i++) {		
						psmt.setString(idx++, Mseq);
						psmt.setString(idx++, MBean.getIc_type());
						psmt.setString(idx++, ic_code_list[i]);
					}
		       		
		       		psmt.executeUpdate(); 
	            } */
	            
	            
			} 
            
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
    }

public void updateMember(MemberBean MBean)
throws ClassNotFoundException, Exception {
	try {
    	dbconn  = new DBconn();
   	 	dbconn.getDBCPConnection();
   	 	
   	 	sb = new StringBuffer();
		sb.append("UPDATE MEMBER \n");			
		sb.append("    SET 				 \n");
		if( MBean.getM_name().length() > 0 ){
			sb.append("   M_NAME = ? \n");
			sb.append("   , \n");
		}
		if( MBean.getM_pass().length() > 0 ){
			sb.append("   M_PASS = ? \n");
			sb.append("   , \n");
		}
		if( MBean.getM_dept().length() > 0 ){
			sb.append("   M_DEPT = ? \n");
			sb.append("   , \n");
		}
		if( MBean.getM_position().length() > 0 ){
			sb.append("   M_POSITION = ? \n");
			sb.append("   , \n");
		}
		if( MBean.getM_mail().length() > 0 ){
			sb.append("   M_MAIL = ? \n");
			sb.append("   , \n");
		}			
		if( MBean.getM_tel().length() > 0 ){
			sb.append("   M_TEL = ? \n");
			sb.append("   , \n");
		}
		sb.append("   M_HP = ? \n");
		sb.append("   , \n");
		if( MBean.getMg_seq().length() > 0 ){
			sb.append("   MG_SEQ = ? \n");
			sb.append("   , \n");
		}			
		if(MBean.getM_regdate().length() > 0 ){
			sb.append("   M_REGDATE = ? \n");
			sb.append("   ,\n");
		}	
		
		if(MBean.getM_ass_grade().length() > 0 ){
			sb.append("   M_ASS_GRADE = ?  \n");
			sb.append("   ,\n");
		}
		if(MBean.getM_pro_grade().length() > 0 ){
			sb.append("   M_PRO_GRADE = ? \n");
			sb.append("   ,\n");
		}
		if(MBean.getM_app_grade().length() > 0 ){
			sb.append("   M_APP_GRADE = ? \n");
			sb.append("   \n");
		}
		sb.append(" WHERE M_SEQ = ? \n");

   	 	//System.out.print( sb.toString() );
        
		
		
		
        psmt = dbconn.createPStatement(sb.toString());
        psmt.clearParameters();
        int idx = 1;
        if( MBean.getM_name().length() > 0 ){
			psmt.setString(idx++, MBean.getM_name());
		}
		if( MBean.getM_pass().length() > 0 ){
			psmt.setString(idx++, MBean.getM_pass());
		}
		if( MBean.getM_dept().length() > 0 ){
			psmt.setString(idx++, MBean.getM_dept());
		}
		if( MBean.getM_position().length() > 0 ){
			psmt.setString(idx++, MBean.getM_position());
		}
		if( MBean.getM_mail().length() > 0 ){
			psmt.setString(idx++, MBean.getM_mail());
		}			
		if( MBean.getM_tel().length() > 0 ){
			psmt.setString(idx++, MBean.getM_tel());
		}
		
		psmt.setString(idx++, MBean.getM_hp());
		
		if( MBean.getMg_seq().length() > 0 ){
			psmt.setString(idx++, MBean.getMg_seq());
		}			
		if(MBean.getM_regdate().length() > 0 ){
			psmt.setString(idx++, MBean.getM_regdate());
		}	
		
		if(MBean.getM_ass_grade().length() > 0 ){
			psmt.setString(idx++, MBean.getM_ass_grade());
		}
		if(MBean.getM_pro_grade().length() > 0 ){
			psmt.setString(idx++, MBean.getM_pro_grade());
		}
		if(MBean.getM_app_grade().length() > 0 ){
			psmt.setString(idx++, MBean.getM_app_grade());
		}
		
		psmt.setString(idx++, MBean.getM_seq());
        
		
        psmt.executeUpdate();

        
        
        
        
        
        
        sb = new StringBuffer();
        sb.append("DELETE FROM MAP_MEMBER_AUTH  \n"); 
        sb.append("		  WHERE M_SEQ = ? \n"); 
	   
   	 	psmt = dbconn.createPStatement(sb.toString());
	   	psmt.clearParameters();
	   	psmt.setString(1, MBean.getM_seq());
	   	psmt.executeUpdate();
	   	 	
   	 	
        sb = new StringBuffer();
        
/*        if (MBean.getIc_code() !=null && !MBean.getIc_code().equals(""))  {
        	
			sb.append("INSERT INTO MAP_MEMBER_AUTH \n");
			sb.append("            ( M_SEQ, IC_TYPE, IC_CODE) \n");
			sb.append("      VALUES \n");
			String[] ic_code_list = MBean.getIc_code().split(",");
			for (int i = 0; i < ic_code_list.length; i++) {		
					sb.append("			( \n");
					sb.append("        	? \n");
					sb.append("        	,? \n");
					sb.append("        	,? \n");
					if (i != (ic_code_list.length-1)) {
						sb.append("        	), \n");
					} else {
						sb.append("        	); \n");						
					}
			}
			//Log.debug(sb.toString());

			psmt = dbconn.createPStatement(sb.toString());
			psmt.clearParameters();
			
			idx = 1;
			for (int i = 0; i < ic_code_list.length; i++) {	
				psmt.setString(idx++, MBean.getM_seq());
				psmt.setString(idx++, MBean.getIc_type());
				psmt.setString(idx++, ic_code_list[i]);
			}
			
			psmt.executeUpdate();
			
			
        }*/
        

    } catch(SQLException ex) {
		Log.writeExpt(ex, sb.toString() );
    } catch(Exception ex) {
		Log.writeExpt(ex);
    } finally {
        if (psmt != null) try { psmt.close(); } catch(SQLException ex) {}
        if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
    }
}  


    public void updateMember_old(MemberBean MBean)
    throws ClassNotFoundException, Exception {
    	try {
        	dbconn  = new DBconn();
       	 	dbconn.getDBCPConnection();
       	 	
       	 	sb = new StringBuffer();
			sb.append("UPDATE MEMBER \n");			
			sb.append("    SET 				 \n");
			if( MBean.getM_name().length() > 0 ){
				sb.append("   M_NAME = '"+MBean.getM_name()+"' \n");
				sb.append("   , \n");
			}
			if( MBean.getM_pass().length() > 0 ){
				sb.append("   M_PASS = SHA2('"+MBean.getM_pass()+"',256) \n");
				sb.append("   , \n");
			}
			if( MBean.getM_dept().length() > 0 ){
				sb.append("   M_DEPT = '"+MBean.getM_dept()+"' \n");
				sb.append("   , \n");
			}
			if( MBean.getM_position().length() > 0 ){
				sb.append("   M_POSITION = '"+MBean.getM_position()+"' \n");
				sb.append("   , \n");
			}
			if( MBean.getM_mail().length() > 0 ){
				sb.append("   M_MAIL = '"+MBean.getM_mail()+"' \n");
				sb.append("   , \n");
			}			
			if( MBean.getM_tel().length() > 0 ){
				sb.append("   M_TEL = '"+MBean.getM_tel()+"' \n");
				sb.append("   , \n");
			}
			sb.append("   M_HP = '"+MBean.getM_hp()+"' \n");
			sb.append("   , \n");
			if( MBean.getMg_seq().length() > 0 ){
				sb.append("   MG_SEQ = '"+MBean.getMg_seq()+"' \n");
				sb.append("   , \n");
			}			
			if(MBean.getM_regdate().length() > 0 ){
				sb.append("   M_REGDATE = '"+MBean.getM_regdate()+"' \n");
				sb.append("   ,\n");
			}	
			
			if(MBean.getM_ass_grade().length() > 0 ){
				sb.append("   M_ASS_GRADE = "+MBean.getM_ass_grade()+" \n");
				sb.append("   ,\n");
			}
			if(MBean.getM_pro_grade().length() > 0 ){
				sb.append("   M_PRO_GRADE = "+MBean.getM_pro_grade()+" \n");
				sb.append("   ,\n");
			}
			if(MBean.getM_app_grade().length() > 0 ){
				sb.append("   M_APP_GRADE = "+MBean.getM_app_grade()+" \n");
				sb.append("   \n");
			}
			
			
			sb.append(" WHERE M_SEQ = '"+MBean.getM_seq()+"' \n");

       	 	System.out.print( sb.toString() );
       	 	stmt = dbconn.createStatement( );
            stmt.executeUpdate( sb.toString());
            
            sb = new StringBuffer();
            sb.append("DELETE FROM MAP_MEMBER_AUTH  \n"); 
            sb.append("		  WHERE M_SEQ = '"+MBean.getM_seq()+"' \n"); 
       	 	stmt.executeUpdate(sb.toString());
       	 					
	        sb = new StringBuffer();
	        
	        if (MBean.getIc_code() !=null && !MBean.getIc_code().equals(""))  {
	        	
				sb.append("INSERT INTO MAP_MEMBER_AUTH \n");
				sb.append("            ( M_SEQ, IC_TYPE, IC_CODE) \n");
				sb.append("      VALUES \n");
				String[] ic_code_list = MBean.getIc_code().split(",");
				for (int i = 0; i < ic_code_list.length; i++) {		
						sb.append("			( \n");
						sb.append("        	'"+MBean.getM_seq()+"' \n");
						sb.append("        	,'"+MBean.getIc_type()+"' \n");
						sb.append("        	,'"+ic_code_list[i]+"' \n");
						if (i != (ic_code_list.length-1)) {
							sb.append("        	), \n");
						} else {
							sb.append("        	); \n");						
						}
				}
				Log.debug(sb.toString());
				stmt.executeUpdate(sb.toString());
	        }
	        

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
    }    

    public ArrayList<String[]> checkDataList_old(String m_id)
    throws ClassNotFoundException, Exception {
    	ArrayList<String[]> result = new ArrayList<String[]>();   
    	try {
        	dbconn  = new DBconn();
       	 	dbconn.getDBCPConnection();
       	 	
       	 	sb = new StringBuffer();
			sb.append("SELECT IFNULL(M.M_SEQ, '') AS M_SEQ, IFNULL(M.M_ID, '') AS M_ID,  IFNULL(GROUP_CONCAT(MAP.IC_CODE), '') AS IC_CODELIST  \n");			
			sb.append("FROM MEMBER M \n");			
			sb.append("		INNER JOIN MAP_MEMBER_AUTH MAP ON M.M_SEQ = MAP.M_SEQ \n");			
			sb.append("WHERE IC_TYPE = '16' \n");			
			sb.append("AND M_ID = '"+m_id+"' \n");			
			
			Log.debug(sb.toString());
			stmt = dbconn.createStatement( );
            rs = stmt.executeQuery( sb.toString());

			String[] check_data = new String[3];
			int idx = 0;
			if (rs.next()) {
				check_data = new String[3];
				check_data[idx++] =rs.getString("M_SEQ");
				check_data[idx++] =rs.getString("M_ID");
				check_data[idx++] =rs.getString("IC_CODELIST");
				
				result.add(check_data);
				
			}

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
		return result;
    }    
    
    

    public ArrayList<String[]> checkDataList(String m_id)
    throws ClassNotFoundException, Exception {
    	ArrayList<String[]> result = new ArrayList<String[]>();   
    	try {
        	dbconn  = new DBconn();
       	 	dbconn.getDBCPConnection();
       	 	
       	 	sb = new StringBuffer();
			sb.append("SELECT IFNULL(M.M_SEQ, '') AS M_SEQ, IFNULL(M.M_ID, '') AS M_ID,  IFNULL(GROUP_CONCAT(MAP.IC_CODE), '') AS IC_CODELIST  \n");			
			sb.append("FROM MEMBER M \n");			
			sb.append("		INNER JOIN MAP_MEMBER_AUTH MAP ON M.M_SEQ = MAP.M_SEQ \n");			
			sb.append("WHERE IC_TYPE = '16' \n");			
			sb.append("AND M_ID = ? \n");			
			
            psmt = dbconn.createPStatement(sb.toString());
            psmt.clearParameters();
            psmt.setString(1, m_id);
            rs = psmt.executeQuery();
            
            
			String[] check_data = new String[3];
			int idx = 0;
			if (rs.next()) {
				check_data = new String[3];
				check_data[idx++] =rs.getString("M_SEQ");
				check_data[idx++] =rs.getString("M_ID");
				check_data[idx++] =rs.getString("IC_CODELIST");
				
				result.add(check_data);
				
			}

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (psmt != null) try { psmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
		return result;
    } 
    
    public void deleteMember_old(String Mseq)
    throws ClassNotFoundException, Exception {
    	try {
        	dbconn  = new DBconn();
       	 	dbconn.getDBCPConnection();
       	 	
       	 	sb = new StringBuffer();
       	 	sb.append(" DELETE FROM MEMBER WHERE M_SEQ IN ("+Mseq+") ");
			Log.writeExpt( sb.toString() );
            stmt = dbconn.createStatement();
            stmt.executeUpdate( sb.toString());
            
            sb = new StringBuffer();
			sb.append(" DELETE FROM SETTING WHERE M_SEQ IN ("+Mseq+") ");
			Log.writeExpt( sb.toString() );
			stmt = dbconn.createStatement();
	        stmt.executeUpdate( sb.toString());
            
	        sb = new StringBuffer();
	        sb.append(" DELETE FROM MAP_MEMBER_AUTH WHERE M_SEQ IN ("+Mseq+") ");
	        Log.writeExpt( sb.toString() );
	        stmt = dbconn.createStatement();
	        stmt.executeUpdate( sb.toString());
	        
	        sb = new StringBuffer();
            sb.append(" DELETE FROM MEMBER_IP WHERE M_SEQ IN ("+Mseq+") ");
			Log.writeExpt( sb.toString() );
			stmt = dbconn.createStatement();
	        stmt.executeUpdate( sb.toString());
	        

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
    }
    
    public void deleteMember(String Mseq)
    	    throws ClassNotFoundException, Exception {
    	    	try {
    	        	dbconn  = new DBconn();
    	       	 	dbconn.getDBCPConnection();
    	       	 	
    	       	 	sb = new StringBuffer();
    	       	 	sb.append(" DELETE FROM MEMBER WHERE M_SEQ IN (?) ");
    	       	    //Log.writeExpt( sb.toString() );
    	            psmt = dbconn.createPStatement(sb.toString());
    	            psmt.clearParameters();
    	            psmt.setString(1, Mseq);
    	            psmt.executeUpdate();

    	            
    	            
    	            sb = new StringBuffer();
    				sb.append(" DELETE FROM SETTING WHERE M_SEQ IN (?) ");
    				//Log.writeExpt( sb.toString() );
    				psmt = dbconn.createPStatement(sb.toString());
      	            psmt.clearParameters();
      	            psmt.setString(1, Mseq);
      	            psmt.executeUpdate();
    	            
    		        sb = new StringBuffer();
    		        sb.append(" DELETE FROM MAP_MEMBER_AUTH WHERE M_SEQ IN (?) ");
    		        //Log.writeExpt( sb.toString() );
    		        psmt = dbconn.createPStatement(sb.toString());
      	            psmt.clearParameters();
      	            psmt.setString(1, Mseq);
      	            psmt.executeUpdate();
      	            
/*  		        sb = new StringBuffer();
    	            sb.append(" DELETE FROM MEMBER_IP WHERE M_SEQ IN (?) ");
    				//Log.writeExpt( sb.toString() );
    	            psmt = dbconn.createPStatement(sb.toString());
      	            psmt.clearParameters();
      	            psmt.setString(1, Mseq);
      	            psmt.executeUpdate(); 										*/
    		        

    	        } catch(SQLException ex) {
    				Log.writeExpt(ex, sb.toString() );
    	        } catch(Exception ex) {
    				Log.writeExpt(ex);
    	        } finally {
    	            if (psmt != null) try { psmt.close(); } catch(SQLException ex) {}
    	            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
    	        }
   }

    public List getMGList_old() throws ClassNotFoundException, Exception {
    	
	        try {
	        	dbconn  = new DBconn();
	       	 	dbconn.getDBCPConnection();
	       	 	sb = new StringBuffer();
				sb.append("SELECT MG_SEQ, MG_XP, MG_SITE, MG_MENU, MG_NAME FROM MEMBER_GROUP WHERE MG_NAME != 'SYSTEM' ");

				System.out.println( sb.toString() );
	            stmt = dbconn.createStatement();
	            rs = stmt.executeQuery(sb.toString());

	            memList = new ArrayList();
	            while (rs.next()) {
	                MemberGroupBean member = new MemberGroupBean();
					member.setMg_seq(rs.getString("MG_SEQ"));
					member.setMg_xp(rs.getString("MG_XP"));
				    member.setMg_site(rs.getString("MG_SITE"));
				    member.setMg_menu(rs.getString("MG_MENU"));
					member.setMg_name(rs.getString("MG_NAME"));

					memList.add(member);
	            }
	        } catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	        }
		return memList;
    }
    
    
    public List getMGList() throws ClassNotFoundException, Exception {
    	
        try {
        	dbconn  = new DBconn();
       	 	dbconn.getDBCPConnection();
       	 	sb = new StringBuffer();
			sb.append("SELECT MG_SEQ, MG_XP, MG_SITE, MG_MENU, MG_NAME FROM MEMBER_GROUP WHERE MG_NAME != 'SYSTEM' ");

			//System.out.println( sb.toString() );
			psmt = dbconn.createPStatement(sb.toString());
			psmt.clearParameters();
			rs = psmt.executeQuery();

            memList = new ArrayList();
            while (rs.next()) {
                MemberGroupBean member = new MemberGroupBean();
				member.setMg_seq(rs.getString("MG_SEQ"));
				member.setMg_xp(rs.getString("MG_XP"));
			    member.setMg_site(rs.getString("MG_SITE"));
			    member.setMg_menu(rs.getString("MG_MENU"));
				member.setMg_name(rs.getString("MG_NAME"));

				memList.add(member);
            }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (psmt != null) try { psmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
	return memList;
}
    
    //비밀번호 변경여부 확인
    public String checkPasswordUpdateDate(String m_seq) throws ClassNotFoundException, Exception {
    	String result = "";
    	
    	try {
	        	dbconn  = new DBconn();
	       	 	dbconn.getDBCPConnection();
	       	 	sb = new StringBuffer();
	       	 	       	 	       	 
				sb.append("  SELECT M_PASS, SUBSTRING(M_REGDATE,1,10) AS M_REGDATE, M_ID \n");
				sb.append("  FROM MEMBER \n");				
				sb.append("  WHERE M_SEQ = ? \n"); 
				
				psmt = dbconn.createPStatement(sb.toString());
				psmt.clearParameters();
	       		psmt.setString(1, m_seq);
	            rs = psmt.executeQuery();
	            
	            
	            if (rs.next()) {
	               result += rs.getString("M_PASS") + "/" + rs.getString("M_REGDATE") + "/" + rs.getString("M_ID");
	            }
	            
	        } catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (psmt != null) try { psmt.close(); } catch(SQLException ex) {}
	            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	        }
		return result;
    }
    
    
    //비밀번호 변경여부 확인
    public String checkPasswordUpdateDate_old(String m_seq) throws ClassNotFoundException, Exception {
    	String result = "";
    	
    	try {
	        	dbconn  = new DBconn();
	       	 	dbconn.getDBCPConnection();
	       	 	sb = new StringBuffer();
	       	 	       	 	       	 
				sb.append("  SELECT M_PASS, SUBSTRING(M_REGDATE,1,10) AS M_REGDATE, M_ID \n");
				sb.append("  FROM MEMBER \n");				
				sb.append("  WHERE M_SEQ = '"+m_seq+"' \n"); 
				
	            stmt = dbconn.createStatement();
	            rs = stmt.executeQuery(sb.toString());
	            System.out.print( sb.toString());

	            if (rs.next()) {
	               result += rs.getString("M_PASS") + "/" + rs.getString("M_REGDATE") + "/" + rs.getString("M_ID");
	            }
	            
	        } catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	        }
		return result;
    }
    
    //비밀번호 업데이트
    public boolean updatePassword_old(String m_seq, String update_pw_check) throws ClassNotFoundException, Exception {
    	boolean result = false;
    	
    	try {
	        	dbconn  = new DBconn();
	       	 	dbconn.getDBCPConnection();
	       	 	sb = new StringBuffer();
	       	 	       	 	       	 
				sb.append("  UPDATE MEMBER \n");
				sb.append("  SET M_PASS = '"+update_pw_check+"', M_REGDATE = '"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"'  \n");				
				sb.append("  WHERE M_SEQ = '"+m_seq+"' \n"); 
				
				System.out.print( sb.toString() );
	            stmt = dbconn.createStatement();
	            stmt.executeUpdate(sb.toString());
	            
	            result = true;
	        } catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	        }
		return result;
    }
    
    //비밀번호 업데이트
    public boolean updatePassword(String m_seq, String update_pw_check) throws ClassNotFoundException, Exception {
    	boolean result = false;
    	
    	try {
	        	dbconn  = new DBconn();
	       	 	dbconn.getDBCPConnection();
	       	 	sb = new StringBuffer();
	       	 	       	 	       	 
				sb.append("  UPDATE MEMBER \n");
				sb.append("  SET M_PASS =?, M_REGDATE = '"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"'  \n");				
				sb.append("  WHERE M_SEQ = ? \n"); 
				
				psmt = dbconn.createPStatement(sb.toString());
				psmt.clearParameters();
				psmt.setString(1, update_pw_check);
				psmt.setString(2, m_seq);
				psmt.executeUpdate();
	            
	            result = true;
	        } catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (psmt != null) try { psmt.close(); } catch(SQLException ex) {}
	            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	        }
		return result;
    }
    
	public String get_Mname(String mname){
		String result = "";
		try {
        	dbconn  = new DBconn();
       	 	dbconn.getDBCPConnection();
       	 	sb = new StringBuffer();
			String member_group = "1,2,70,9,77";
       	 	
			sb = new StringBuffer();
			sb.append("  SELECT A.M_NAME FROM MEMBER A, MEMBER_GROUP B \n");
			sb.append("  WHERE A.MG_SEQ = B.MG_SEQ \n");				
			sb.append("    AND B.MG_SEQ IN ("+member_group+") \n"); 
			sb.append("    AND A.M_NAME = '"+mname+"' \n"); 
			
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            System.out.print( sb.toString());

			if(rs.next()) {
				result =  rs.getString("A.M_NAME");
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
		return result;
	}
	
	public String getMgseq(String mname){
		String result = "";
		try {
        	dbconn  = new DBconn();
       	 	dbconn.getDBCPConnection();
       	 	sb = new StringBuffer();
			String member_group = "71,73,74"; // 현대카드 71, 현대캐피탈 73, 현대커머셜 74
       	 	
			sb = new StringBuffer();
			sb.append("  SELECT A.MG_SEQ FROM MEMBER A, MEMBER_GROUP B \n");
			sb.append("  WHERE A.MG_SEQ = B.MG_SEQ \n");				
			sb.append("    AND B.MG_SEQ IN ("+member_group+") \n"); 
			sb.append("    AND A.M_NAME = '"+mname+"' \n"); 
			
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            System.out.print( sb.toString());

			if(rs.next()) {
				result =  rs.getString("A.MG_SEQ");
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
		return result;
	} 

}