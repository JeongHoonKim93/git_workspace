package risk.admin.backup;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class BackupListMgr {
	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	
    //백업테이블 조회 시 필요한 데이터베이스명 조회
	public String getDatabaseName()
	{
		DBconn  conn  = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        String name = null;
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();

       	 	sb = new StringBuffer();
				
       	 	sb.append(" SELECT  DISTINCT TABLE_SCHEMA 		\n");
       	 	sb.append(" FROM 	information_schema.TABLES 		\n");
       	 	sb.append(" WHERE 	TABLE_SCHEMA LIKE '%_BACKUP%'		\n");
       	 		
       	 	Log.debug(sb.toString());
       	 	pstmt = conn.createPStatement( sb.toString() );
       	 	rs = pstmt.executeQuery();
       	 	
	        if(rs.next()) {
	        	 name = rs.getString("TABLE_SCHEMA");
	        }

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return name;
    }
	
	
	public BackupListBean[] getAllTableList(String order)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        BackupListBean[] list = null;
        BackupListBean BackupListBean = null;
        String db_name = getDatabaseName();
        int rowCnt = 0;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();

       	 	sb = new StringBuffer();
       	 	
       		sb.append(" SELECT DISTINCT IFNULL(B.BL_SEQ,'') AS BL_SEQ		\n");
       		sb.append("			, A.TABLE_NAME AS BL_TBNAME  		\n");
       		sb.append("			, IFNULL(A.TABLE_COMMENT,'') AS BL_COMMENT  		\n");
       		sb.append("			, IFNULL(A.ENGINE,'') AS ENGINE  		\n");
       		sb.append("			, IFNULL(A.TABLE_ROWS,'') AS TABLE_ROWS  		\n");
       		sb.append("			, IFNULL(A.CREATE_TIME,'') AS CREATE_TIME  		\n");
       		sb.append("			, IFNULL(A.UPDATE_TIME,'') AS UPDATE_TIME  		\n");
			sb.append("			, IFNULL(B.BL_OP,'') AS BL_OP  		\n");
			sb.append("			, IFNULL(B.BL_USEYN,'') AS BL_USEYN  		\n");
			sb.append("			, IFNULL(B.BL_DEL_USEYN,'') AS BL_DEL_USEYN  		\n");
			sb.append("			, IFNULL(B.BL_DAY_TERM,'') AS BL_DAY_TERM  		\n");
			sb.append("			, IFNULL(B.INS_FIELD_NAME,'') AS INS_FIELD_NAME  		\n");
			sb.append("			, IFNULL(B.DEL_DATE_FIELD_NAME,'') AS DEL_DATE_FIELD_NAME 		\n");
			sb.append("			, IFNULL(B.DEL_DATE_FIELD_TYPE,'') AS DEL_DATE_FIELD_TYPE 		\n");
			sb.append("		FROM ( SELECT TABLE_NAME, TABLE_COMMENT, ENGINE, TABLE_ROWS, CREATE_TIME, UPDATE_TIME 		\n");
			sb.append("				  FROM information_schema.TABLES WHERE TABLE_SCHEMA LIKE '%_ASP%')A  		\n");
			sb.append("			 LEFT OUTER JOIN `"+db_name+"`.BACKUP_LIST B ON A.TABLE_NAME = B.BL_TBNAME   		\n");
       	 	
       	 	if( !order.equals("") )
			sb.append(" ORDER BY "+order+" 			  \n");
       	 	
       	 	Log.debug(sb.toString());
       	 	pstmt = conn.createPStatement( sb.toString() );
    	 	rs = pstmt.executeQuery();
    	 	
       	 	rs.last();
       	 	if(rs.getRow()>0) list = new BackupListBean[rs.getRow()];
       	 	rs.beforeFirst();
       	 	
	        while(rs.next()) {
	        	 BackupListBean = new BackupListBean();
	        	 
	        	 BackupListBean.setBl_seq(rs.getString("BL_SEQ"));
	        	 BackupListBean.setBl_tbName(rs.getString("BL_TBNAME"));
	        	 BackupListBean.setBl_comment(rs.getString("BL_COMMENT"));
	        	 BackupListBean.setEngine(rs.getString("ENGINE"));
	        	 BackupListBean.setTable_rows(rs.getString("TABLE_ROWS"));
	        	 BackupListBean.setCreate_time(rs.getString("CREATE_TIME"));
	        	 BackupListBean.setUpdate_time(rs.getString("UPDATE_TIME"));
	        	 BackupListBean.setBl_op(rs.getString("BL_OP"));
	        	 BackupListBean.setBl_useYn(rs.getString("BL_USEYN"));
	        	 BackupListBean.setBl_del_useYn(rs.getString("BL_DEL_USEYN"));
	        	 BackupListBean.setBl_day_term(rs.getInt("BL_DAY_TERM"));
	        	 BackupListBean.setIns_field_name(rs.getString("INS_FIELD_NAME"));
	        	 BackupListBean.setDel_date_field_name(rs.getString("DEL_DATE_FIELD_NAME"));
	        	 BackupListBean.setDel_date_field_type(rs.getString("DEL_DATE_FIELD_TYPE"));
	        		 
	        	 list[rowCnt] =	BackupListBean;    		
	             rowCnt++;
	        }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return list;
    }
    
	/*
	//모든 테이블 일괄 업데이트
	public BackupListBean[] getAllTablename_multiUpdate()
	{
		DBconn  conn  = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        BackupListBean[] list = null;
        BackupListBean BackupListBean = null;
        int rowCnt = 0;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();

       	 	sb = new StringBuffer();
       	 	
       		sb.append(" SELECT DISTINCT A.TABLE_NAME AS BL_TBNAME  		\n");
			sb.append("		FROM ( SELECT TABLE_NAME 		\n");
			sb.append("				  FROM information_schema.TABLES WHERE TABLE_SCHEMA LIKE '%_ASP%')A  		\n");
			sb.append("			 LEFT OUTER JOIN `HYCARD_BACKUP`.BACKUP_LIST B ON A.TABLE_NAME = B.BL_TBNAME   		\n");
       	 	
       	 	Log.debug(sb.toString());
       	 	pstmt = conn.createPStatement( sb.toString() );
    	 	rs = pstmt.executeQuery();
    	 	
       	 	rs.last();
       	 	if(rs.getRow()>0) list = new BackupListBean[rs.getRow()];
       	 	rs.beforeFirst();
       	 	
	        while(rs.next()) {
	        	 BackupListBean = new BackupListBean();

	        	 BackupListBean.setBl_tbName(rs.getString("BL_TBNAME"));
	        		 
	        	 list[rowCnt] =	BackupListBean;    		
	             rowCnt++;
	        }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return list;
    }
	*/
	
	//BACKUP_LIST 테이블에 insert할 테이블명 조회
	public BackupListBean[] getSearchTableList_insert(String searchKey,String searchType)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        BackupListBean[] list = null;
        BackupListBean BackupListBean = null;
        String db_name = getDatabaseName();
        int rowCnt = 0;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();

       	 	sb = new StringBuffer();
				
       	 	sb.append(" SELECT  DISTINCT A.TABLE_NAME AS BL_TBNAME 		\n");
//       	 	sb.append(" FROM 	information_schema.TABLES A		\n");
       	 	sb.append(" FROM 	(SELECT TABLE_NAME	\n");
       	 	sb.append("				  FROM information_schema.TABLES WHERE TABLE_SCHEMA LIKE '%_ASP%')A  		\n");
       	 	sb.append(" WHERE 	A.TABLE_NAME NOT IN (SELECT BL_TBNAME FROM "+db_name+".BACKUP_LIST B WHERE A.TABLE_NAME = B.BL_TBNAME)		\n");
       	 	if( searchKey.length() > 0 ) {
       	 		sb.append(" AND A.TABLE_NAME LIKE '%"+searchKey+"%' \n");
       	 	}
       	 		
       	 	Log.debug(sb.toString());
       	 	pstmt = conn.createPStatement( sb.toString() );
       	 	rs = pstmt.executeQuery();
    	 	
       	 	rs.last();
       	 	if(rs.getRow()>0) list = new BackupListBean[rs.getRow()];
       	 	rs.beforeFirst();
       	 	
	        while(rs.next()) {
	        	 BackupListBean = new BackupListBean();	 
	        	 BackupListBean.setBl_tbName(rs.getString("BL_TBNAME"));
	        		 
	        	 list[rowCnt] =	BackupListBean;    		
	             rowCnt++;
	        }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return list;
    }
	
	//BACKUP_LIST 테이블에 들어있는 테이블들 중에 update할 테이블명 조회
	public BackupListBean[] getSearchTableList_update(String searchKey,String searchType)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        BackupListBean[] list = null;
        BackupListBean BackupListBean = null;
        String db_name = getDatabaseName();
        int rowCnt = 0;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();

       	 	sb = new StringBuffer();
				
	        sb.append(" SELECT BL_SEQ, BL_TBNAME, BL_OP, BL_USEYN, BL_DEL_USEYN, BL_DAY_TERM, INS_FIELD_NAME, DEL_DATE_FIELD_NAME, DEL_DATE_FIELD_TYPE 		\n");
	       	sb.append(" FROM "+db_name+".BACKUP_LIST		\n");
	        if( searchKey.length() > 0 ) {
	       	    sb.append(" WHERE BL_TBNAME LIKE '%"+searchKey+"%' \n");
	       	}
	       	 	
	       	Log.debug(sb.toString());
    	 	pstmt = conn.createPStatement( sb.toString() );
    	 	rs = pstmt.executeQuery();

       	 	rs.last();
       	 	if(rs.getRow()>0) list = new BackupListBean[rs.getRow()];
       	 	rs.beforeFirst();
       	 	
	        while(rs.next()) {
	        	 BackupListBean = new BackupListBean();	 
	        	 
	        	 BackupListBean.setBl_seq(rs.getString("BL_SEQ"));
	        	 BackupListBean.setBl_tbName(rs.getString("BL_TBNAME"));
	        	 BackupListBean.setBl_op(rs.getString("BL_OP"));
	        	 BackupListBean.setBl_useYn(rs.getString("BL_USEYN"));
	        	 BackupListBean.setBl_del_useYn(rs.getString("BL_DEL_USEYN"));
	        	 BackupListBean.setBl_day_term(rs.getInt("BL_DAY_TERM"));
	        	 BackupListBean.setIns_field_name(rs.getString("INS_FIELD_NAME"));
	        	 BackupListBean.setDel_date_field_name(rs.getString("DEL_DATE_FIELD_NAME"));
	        	 BackupListBean.setDel_date_field_type(rs.getString("DEL_DATE_FIELD_TYPE"));
	        		 
	        	 list[rowCnt] =	BackupListBean;    		
	             rowCnt++;
	        }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return list;
    }

	//BACKUP_LIST에 없는 테이블명이면 insert, BACKUP_LIST에 있는 테이블명이면 update
	public boolean UpdateBackupList(String bl_seq, String bl_tbName, String bl_op, String bl_useYn, String bl_del_useYn, String bl_day_term, String ins_field_name, String del_date_field_name, String del_date_field_type)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        boolean result = false;
        String db_name = getDatabaseName();
        String[] tb_name = bl_tbName.split(",");
        if(bl_day_term.equals("")) {
        	bl_day_term = "0";
        }
        int chkcount = 0;
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();       	 	
       	 	
       	 	for(int i = 0; i < tb_name.length; i++) {
       	 		
	       	 	sb = new StringBuffer();
	       	 	sb.append("SELECT COUNT(1) FROM "+db_name+".BACKUP_LIST WHERE BL_TBNAME='"+tb_name[i]+"' \n");
	       	 	
	       	 	Log.debug(sb.toString()); 
		        rs = stmt.executeQuery(sb.toString());
		
		        if (rs.next()) {
	            	chkcount = rs.getInt(1);
	            }        
		         
		        if( chkcount == 0 ) {
		        	
		       	 	sb = new StringBuffer();	
		       	 	
		       	 	sb.append(" INSERT INTO "+db_name+".BACKUP_LIST																														\n");
					sb.append("		   (BL_TBNAME, BL_OP, BL_USEYN, BL_DEL_USEYN, BL_DAY_TERM, INS_FIELD_NAME, DEL_DATE_FIELD_NAME,DEL_DATE_FIELD_TYPE)  										\n");
					sb.append("	       VALUE																																\n");
					sb.append("		  	('"+tb_name[i]+"', '"+bl_op+"', '"+bl_useYn+"', '"+bl_del_useYn+"', '"+bl_day_term+"', '"+ins_field_name+"', '"+del_date_field_name+"', '"+del_date_field_type+"')	\n");
	            
					Log.debug(sb.toString());
				 	stmt.executeUpdate(sb.toString());
				 	result = true;
				 	
		        }else {
	            	
		        	sb = new StringBuffer();
		            
					sb.append(" UPDATE "+db_name+".BACKUP_LIST	\n");
					sb.append(" SET  		\n");
					sb.append(" 	  BL_OP= '"+bl_op+"' 	\n");		
					sb.append(" 	, BL_USEYN= '"+bl_useYn+"' 	\n");		
					sb.append(" 	, BL_DEL_USEYN= '"+bl_del_useYn+"' 	\n");
					sb.append(" 	, BL_DAY_TERM= "+bl_day_term+" 	\n");
					if(bl_op.equals(Integer.toString(2))) {
						sb.append(" 	, INS_FIELD_NAME= '"+ins_field_name+"' 	\n");
						if(bl_del_useYn.equals("Y")) {
							sb.append(" 	, DEL_DATE_FIELD_NAME= '"+del_date_field_name+"' 	\n");						
							sb.append(" 	, DEL_DATE_FIELD_TYPE= '"+del_date_field_type+"' 	\n");
						} else {
							sb.append(" 	, DEL_DATE_FIELD_NAME= '' 	\n");						
							sb.append(" 	, DEL_DATE_FIELD_TYPE= '' 	\n");
						}
					} else {					
						sb.append(" 	, INS_FIELD_NAME= '' 	\n");
						sb.append(" 	, DEL_DATE_FIELD_NAME= '' 	\n");
						sb.append(" 	, DEL_DATE_FIELD_TYPE= '' 	\n");
					}
				 	sb.append(" WHERE BL_TBNAME='"+tb_name[i]+"' 			\n");
		   	 	
				 	Log.debug(sb.toString());
				 	stmt.executeUpdate(sb.toString());
				 	result = true;
		        }
       	 	} 
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
			return false;
        } catch(Exception ex) {
			Log.writeExpt(ex);
			return false;
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return result;
	
	}	
	
	/*
	//BACKUP_LIST 테이블에 일괄업데이트 할 데이터베이스명 조회
	public String getDatabaseName()
	{
		DBconn  conn  = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        String name = null;
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();

       	 	sb = new StringBuffer();
				
       	 	sb.append(" SELECT  DISTINCT TABLE_SCHEMA 		\n");
       	 	sb.append(" FROM 	information_schema.TABLES 		\n");
       	 	sb.append(" WHERE 	TABLE_SCHEMA LIKE '%_ASP%'		\n");
       	 		
       	 	Log.debug(sb.toString());
       	 	pstmt = conn.createPStatement( sb.toString() );
       	 	rs = pstmt.executeQuery();
       	 	
	        while(rs.next()) {
	        	 name = rs.getString("TABLE_SCHEMA");
	        }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        System.out.println(name + "<<name");
        return name;
    }
	*/
	
	
	//하나씩 삭제
	public boolean DeleteBackupList(String bl_seq, String bl_tbName) 
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        String[] tb_name = bl_tbName.split(",");
        String db_name = getDatabaseName();
        boolean result = false;
        
        try {       	      	
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();	      
       	 	
       	 	for(int i = 0; i < tb_name.length; i++) {
	        sb = new StringBuffer();
	            
	        sb.append(" DELETE FROM "+db_name+".BACKUP_LIST						\n");
			sb.append("		   WHERE BL_TBNAME = '"+tb_name[i]+"' 		\n");
	   	 	
			Log.debug(sb.toString());
			stmt.executeUpdate(sb.toString());
			result = true;
			
			
	        //sb = new StringBuffer();
            
	        //sb.append(" DROP TABLE `CJ_ORM2_BACKUP`.'"+bl_tbName+"'						\n");
	   	 		
			//System.out.println("here4");
			//Log.debug(sb.toString());
			//stmt.executeUpdate(sb.toString());
			//result = true;
       	 	}
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return result;
	
	}
	
	
	/*
	//여러개 삭제
	public boolean DeleteBackupLists(String delList) 
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        boolean result = false;
        
        try {       	      	
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();	      
        	
        	if(delList.length()>1000)
        	{
        		result = false;
        	}else{
	        	sb = new StringBuffer();
	            
	        	sb.append(" DELETE FROM CJ_ORM2_BACKUP.BACKUP_LIST						\n");
				sb.append("		   WHERE BL_SEQ IN ("+delList+") 		\n");
	   	 		
				System.out.println("here4");
			 	Log.debug(sb.toString());
			 	stmt.executeUpdate(sb.toString());
			 	result = true;
        	}
	        
       	 	
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return result;
	
	}
	*/
}