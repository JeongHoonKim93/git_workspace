package risk.admin.aekeyword;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class StorageMgr {
	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	
	public StorageBean[] getStorageList(String SearchKey, String WriterName, String Ordered, String m_seq)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        StorageBean[] arrAStorage= null;
        StorageBean AStorage = null;
        int rowCnt = 0;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	
       	 	sb = new StringBuffer();
       		sb.append("	SELECT A.STO_SEQ, A.STO_NAME, DATE_FORMAT(STO_DATE,'%Y.%m.%d') STO_DATE, B.M_NAME  \n");
			sb.append(" FROM STORAGE A		\n");
			sb.append(" INNER JOIN MEMBER B  ON A.M_SEQ = B.M_SEQ  \n");
//			sb.append(" WHERE A.M_SEQ = "+m_seq+" \n");
			
			  if( SearchKey.length() > 0 )
			  {sb.append(" AND STO_NAME LIKE '%"+SearchKey+"%' \n");}
			  if(WriterName.length() > 0 )
			  {sb.append(" AND A.M_SEQ = "+WriterName+" \n");}
			  
			  
			  
       	 	if( !Ordered.equals("") )
			sb.append(" ORDER BY "+Ordered+" 			  \n");
       	 	
       	 	Log.debug(sb.toString());
       	 	
       	 	pstmt = conn.createPStatement( sb.toString() );
       	 	rs = pstmt.executeQuery();
       	 	
       	 	rs.last();
       	 	if(rs.getRow()>0) arrAStorage = new StorageBean[rs.getRow()];
       	 	rs.beforeFirst();
            
            while (rs.next()) {
            	AStorage = new StorageBean();
            	AStorage.setStoSeq(rs.getInt("STO_SEQ"));
            	AStorage.setStoName(rs.getString("STO_NAME"));
            	AStorage.setStoDate(rs.getString("STO_DATE"));
            	AStorage.setmName(rs.getString("M_NAME"));
      
            	arrAStorage[rowCnt] =	AStorage;    		
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
        return arrAStorage;
    }
	
	public StorageBean getStorage( int stoSeq )
	{
		DBconn  conn  = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        ArrayList arrList = new ArrayList();
        
        StorageBean AEKeyword = null;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	
       	 	sb = new StringBuffer();
       	 	
       	 	sb.append(" SELECT STO_SEQ, STO_NAME, DATE_FORMAT(STO_DATE,'%Y.%m.%d') STO_DATE  \n");
			sb.append(" FROM STORAGE \n");
			sb.append(" WHERE STO_SEQ="+stoSeq+" \n");       	 	
       	 	
       	 	//Log.debug(sb.toString());
       	 	
       	 	pstmt = conn.createPStatement( sb.toString() );
       	 	rs = pstmt.executeQuery();
            
            if (rs.next()) {
            	AEKeyword = new StorageBean();
            	AEKeyword = new StorageBean();
            	AEKeyword.setStoSeq(rs.getInt("STO_SEQ"));
            	AEKeyword.setStoName(rs.getString("STO_NAME"));
            	AEKeyword.setStoDate(rs.getString("STO_DATE"));
            	AEKeyword.setmName(rs.getString("M_NAME"));
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
        return AEKeyword;
    }
	
	public ArrayList getStorageWriterList()
	{
		DBconn  conn  = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        ArrayList arrList = new ArrayList();
        
        StorageBean stoBean = null;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	
       	 	sb = new StringBuffer();
       	 	
       	 	sb.append(" SELECT DISTINCT B.M_NAME, A.M_SEQ \n");
			sb.append(" FROM STORAGE A		\n");
			sb.append(" INNER JOIN MEMBER B ON A.M_SEQ = B.M_SEQ  \n"); 	 	
       	 	
       	 	//Log.debug(sb.toString());
       	 	
       	 	pstmt = conn.createPStatement( sb.toString() );
       	 	rs = pstmt.executeQuery();
            
            while (rs.next()) {
            	 stoBean = new StorageBean();
            	 stoBean.setmSeq(rs.getInt("M_SEQ"));
            	 stoBean.setmName(rs.getString("M_NAME"));
            	 arrList.add(stoBean);
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
        return arrList;
    }
	
	
	public boolean insertStorage( String m_seq, String storageName )
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        boolean rebln = false;
        
        int chkcount = 0;
        int chkPosition = 0;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();
       	
    	 	sb = new StringBuffer();	        
			sb.append(" INSERT INTO STORAGE (STO_NAME, STO_DATE, M_SEQ) \n");
		 	sb.append(" VALUES ('"+storageName+"', '"+du.getCurrentDate("yyyy-MM-dd")+"',"+m_seq+") \n");
	 	
		 	System.out.println(sb.toString());
		 	stmt.executeUpdate(sb.toString());
		 	rebln = true;
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return rebln;
	
	}
	
	public boolean updateStorage( String m_seq, String storageName,String sto_seq)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        boolean rebln = false;
        
        int chkcount = 0;
        String chkWord = null;
        String chkWeight = null;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();       	 	
       	 	
       	 	sb = new StringBuffer();
			 sb.append("SELECT COUNT(1) FROM STORAGE WHERE STO_NAME='"+storageName+"' \n"); 
	         
	        rs = stmt.executeQuery(sb.toString());
	
	        if (rs.next()) {
            	chkcount = rs.getInt(1);
            }       
	         
	        if( chkcount > 0 ) {
            	rebln = false;
            }else {
            	
	        	sb = new StringBuffer();
	            
				sb.append(" UPDATE STORAGE	\n");
				sb.append(" SET STO_NAME='"+storageName+"', 		\n");
				sb.append(" 	STO_DATE= '"+du.getCurrentDate("yyyy-MM-dd")+"', 	\n");		
				sb.append(" 	M_SEQ= '"+m_seq+"' 	\n");		
			 	sb.append(" WHERE STO_SEQ="+sto_seq+" 			\n");
	   	 	
			 	Log.debug(sb.toString());
			 	stmt.executeUpdate(sb.toString());
			 	rebln = true;
	        }
	        System.out.println("rebln:"+rebln);
       	 	
            
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
        return rebln;
	
	}
	
	public boolean delStorage(String delList )
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        String[] arrList = null;
        boolean rebln = false;
        
        try {       	      	
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();	      
        	
        	arrList = delList.split(",");
        	
        	if(delList.length()>1000)
        	{
        		rebln = false;
        	}else{
	        	sb = new StringBuffer();
	            
	        	sb.append("DELETE FROM STORAGE		\n");
			 	sb.append("WHERE STO_SEQ IN ("+delList+")    		\n");
			 	
			 	Log.debug(sb.toString());
			 	stmt.executeUpdate(sb.toString());
			 	
			 	sb = new StringBuffer();
			 	
			 	sb.append("DELETE FROM MAP_META_STORAGE		\n");
			 	sb.append("WHERE STO_SEQ IN ("+delList+")    		\n");
			 	
			 	Log.debug(sb.toString());
			 	stmt.executeUpdate(sb.toString());
			 	rebln = true;
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
        return rebln;
	
	}
	
	public ArrayList<StorageBean> getStorageNameList(String m_seq) {
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        ArrayList<StorageBean> list = new ArrayList<StorageBean>();
        sb = new StringBuffer();        
        sb.append("	SELECT A.STO_NAME, A.STO_SEQ  \n");
        sb.append(" 	FROM STORAGE A   \n");
        sb.append(" 	INNER JOIN MEMBER B ON A.M_SEQ = B.M_SEQ   \n");
        sb.append(" WHERE  A.M_SEQ = "+m_seq+" \n");
        sb.append(" 	ORDER BY  STO_SEQ ASC   \n");
        boolean rebln = false;
        
        try {       	      	
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();	      
        	rs = stmt.executeQuery(sb.toString());
        	
        	while(rs.next()) {
        		 StorageBean storageBean = new StorageBean();
        		 
        		 storageBean.setStoName(rs.getString("STO_NAME"));
        		 storageBean.setStoSeq(rs.getInt("STO_SEQ"));
        		 
        		 list.add(storageBean);
        	}
        	rs.close();
        	
	        
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return list;
	}
	
	
	public boolean insertStorageList3( String md_seqs, String sto_seq , String submode)
	{
		boolean result = false;
		String[] tmp_md_seq_list = md_seqs.split(",");
		for(int i=0; i< tmp_md_seq_list.length; i++) {
			result = insertStorageList2( tmp_md_seq_list[i], sto_seq);
       	}
		return result;
	}
	
	//선택한 게시글이 다른 보관함에 이미 담겨있는 게시글이면 보과함명 update
	//보관함에 담겨있지 않은 게시글이면 보관함에 insert
	public boolean insertStorageList2( String md_seq, String sto_seq )
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        boolean rebln = false;
        int chkcount = 0;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();
       	 	
       	 	sb = new StringBuffer();
       	 	
       	 	sb.append("SELECT COUNT(*) FROM MAP_META_STORAGE WHERE MD_SEQ = '"+md_seq+"' \n"); 
       	 	rs = stmt.executeQuery(sb.toString());
     	
	        if (rs.next()) {
	        	chkcount = rs.getInt(1);
	        }       
	        
	        if( chkcount == 0 ) {
	        	sb = new StringBuffer();
	       	 	sb.append(" INSERT INTO MAP_META_STORAGE (MD_SEQ, STO_SEQ)  \n");
		        sb.append(" VALUES ("+md_seq+","+sto_seq+") \n");	

				Log.debug(sb.toString());
			 	stmt.executeUpdate(sb.toString());
			 	rebln = true;
	        }else {
         	
	        	sb = new StringBuffer();
	            
				sb.append(" UPDATE MAP_META_STORAGE	\n");
				sb.append(" SET STO_SEQ='"+sto_seq+"' 		\n");
				sb.append(" WHERE MD_SEQ IN("+md_seq+" ");
				sb.append(") \n");
				
				Log.debug(sb.toString());
			 	stmt.executeUpdate(sb.toString());
			 	rebln = true;
	        }
	        System.out.println("rebln:"+rebln);
       	 	
			
			 System.out.println(sb.toString()); 
			 Log.debug(sb.toString());
			 stmt.executeUpdate(sb.toString()); 
			 rebln = true;
			 
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return rebln;
	
	}
	
	//선택한 게시글이 다른 보관함에 이미 담겨있는 게시글이면 보과함명 update
	//보관함에 담겨있지 않은 게시글이면 보관함에 insert
	public boolean insertStorageList( String md_seqs, String sto_seq, String submode )
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        boolean rebln = false;
        int chkcount = 0;
        int cnt = 0;
        String[] md_seq_list = md_seqs.split(",");

        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();
       	 	
       	 	
       	 	//보관함에 넣을 데이터가 META데이터 인지 조회
       	 	for (int i = 0; i < md_seq_list.length; i++) {
       	 		sb = new StringBuffer();
       	 		
       	 		sb.append("SELECT COUNT(*) AS CNT FROM META WHERE MD_SEQ = '"+md_seq_list[i]+"' \n");
       	 		rs = stmt.executeQuery(sb.toString());
       	 		if(rs.next()) {
       	 			cnt = rs.getInt("CNT");
       	 		}
       	 		
       	 		rs.close();
       	 		
       	 		if(cnt > 0) {
	       	 		sb = new StringBuffer();
	           	 	
	           	 	sb.append("SELECT COUNT(*) FROM MAP_META_STORAGE WHERE MD_SEQ = "+md_seq_list[i]+" \n"); 
	           	 	rs = stmt.executeQuery(sb.toString());
	           	 	
		           	if (rs.next()) {
		 	        	chkcount = rs.getInt(1);
		 	        }  
		           	
			        if( chkcount == 0 ) {
			        	
			        	sb = new StringBuffer();
			       	 	sb.append(" INSERT INTO MAP_META_STORAGE (MD_SEQ, STO_SEQ)  \n");
				        sb.append(" VALUES ("+md_seq_list[i]+","+sto_seq+") \n");	

						Log.debug(sb.toString());
					 	stmt.executeUpdate(sb.toString());
					 	
					 	rebln = true;
			        }else {
		         	
			        	sb = new StringBuffer();
			            
						sb.append(" UPDATE MAP_META_STORAGE	\n");
						sb.append(" SET STO_SEQ='"+sto_seq+"' 		\n");
						sb.append(" WHERE MD_SEQ = "+md_seq_list[i]+" \n"); 
						
						Log.debug(sb.toString());
					 	stmt.executeUpdate(sb.toString());
					 	rebln = true;
			        }
       	 		}
       	 		
			}
       	 	
       	 	//보관함에 넣을 데이터가 WARNING데이터인지 조회
       	 	for (int i = 0; i < md_seq_list.length; i++) {
       	 		sb = new StringBuffer();
       	 		
       	 		sb.append("SELECT COUNT(*) AS CNT FROM SECTION WHERE MD_SEQ = '"+md_seq_list[i]+"' \n");
       	 		rs = stmt.executeQuery(sb.toString());
       	 		if(rs.next()) {
       	 			cnt = rs.getInt("CNT");
       	 		}
       	 		
       	 		rs.close();
       	 		
       	 		if(cnt > 0) {
	       	 		sb = new StringBuffer();
	           	 	
	           	 	sb.append("SELECT COUNT(*) FROM MAP_SECTION_STORAGE WHERE MD_SEQ = "+md_seq_list[i]+" \n"); 
	           	 	rs = stmt.executeQuery(sb.toString());
	           	 	
		           	if (rs.next()) {
		 	        	chkcount = rs.getInt(1);
		 	        }  
		           	
			        if( chkcount == 0 ) {
			        	
			        	//WARNING. META 중 보관함 넣기 누른 페이지에 맞게 MAP_STORAGE테이블에 넣기
			        	sb = new StringBuffer();
			       	 	sb.append(" INSERT INTO MAP_SECTION_STORAGE (MD_SEQ, STO_SEQ)  \n");
				        sb.append(" VALUES ("+md_seq_list[i]+","+sto_seq+") \n");	

						Log.debug(sb.toString());
					 	stmt.executeUpdate(sb.toString());
					 	
					 	rebln = true;
			        }else {
		         	
			        	sb = new StringBuffer();
			            
						sb.append(" UPDATE MAP_SECTION_STORAGE	\n");
						sb.append(" SET STO_SEQ='"+sto_seq+"' 		\n");
						sb.append(" WHERE MD_SEQ = "+md_seq_list[i]+" \n"); 
						
						Log.debug(sb.toString());
					 	stmt.executeUpdate(sb.toString());
					 	rebln = true;
			        }
       	 		}
       	 		
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
        return rebln;
	
	}
	
}
	

	