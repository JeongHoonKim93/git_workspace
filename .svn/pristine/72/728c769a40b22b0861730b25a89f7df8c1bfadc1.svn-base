package risk.admin.relation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.NamingException;

import risk.DBconn.DBconn;
import risk.admin.relation.RelationBean;
import risk.util.Log;

public class RelationMgr {

	private DBconn dbconn;
	private StringBuffer sb;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Statement stmt;
	
	public ArrayList getRelationKeywordList(String searchKeyword, String kor_val, String eng_val, String num_val){
		ArrayList result = new ArrayList();
		HashMap map = new HashMap();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT RK_SEQ , RK_NAME					\n");
			sb.append("FROM RELATION_KEYWORD_R                  \n");
			sb.append("WHERE 1=1                  				\n");
			sb.append("AND RK_USE = 'Y'                  				\n");
			if(!kor_val.equals(""))
			{
				if(kor_val.equals("ㄱ")){
					sb.append("AND RK_NAME >= '가' AND RK_NAME < '나' 	\n");
				}else if(kor_val.equals("ㄴ")){	
					sb.append("AND RK_NAME >= '나' AND RK_NAME < '다' 	\n");
				}else if(kor_val.equals("ㄷ")){
					sb.append("AND RK_NAME >= '다' AND RK_NAME < '라' 	\n");
				}else if(kor_val.equals("ㄹ")){
					sb.append("AND RK_NAME >= '라' AND RK_NAME < '마' 	\n");
				}else if(kor_val.equals("ㅁ")){
					sb.append("AND RK_NAME >= '마' AND RK_NAME < '바' 	\n");
				}else if(kor_val.equals("ㅂ")){
					sb.append("AND RK_NAME >= '바' AND RK_NAME < '사' 	\n");
				}else if(kor_val.equals("ㅅ")){
					sb.append("AND RK_NAME >= '사' AND RK_NAME < '아' 	\n");
				}else if(kor_val.equals("ㅇ")){
					sb.append("AND RK_NAME >= '아' AND RK_NAME < '자' 	\n");
				}else if(kor_val.equals("ㅈ")){
					sb.append("AND RK_NAME >= '자' AND RK_NAME < '차' 	\n");
				}else if(kor_val.equals("ㅊ")){
					sb.append("AND RK_NAME >= '차' AND RK_NAME < '카' 	\n");
				}else if(kor_val.equals("ㅋ")){
					sb.append("AND RK_NAME >= '카' AND RK_NAME < '타' 	\n");
				}else if(kor_val.equals("ㅌ")){
					sb.append("AND RK_NAME >= '타' AND RK_NAME < '파' 	\n");
				}else if(kor_val.equals("ㅍ")){
					sb.append("AND RK_NAME >= '파' AND RK_NAME < '하' 	\n");
				}else if(kor_val.equals("ㅎ")){
					sb.append("AND RK_NAME >= '하' AND RK_NAME < '힣' 	\n");
				}
				sb.append("ORDER BY RK_NAME ASC 	\n");
			}
			if(!eng_val.equals("")){
				sb.append("AND substring(RK_NAME, 1, 1) = '"+eng_val+"' \n");
				sb.append("ORDER BY RK_NAME ASC 	\n");
			}
			if(!num_val.equals("")){
				sb.append("AND substring(RK_NAME, 1, 1) = '"+num_val+"' \n");
				sb.append("ORDER BY RK_NAME ASC 	\n");
			}
			if(!searchKeyword.equals("")){
				sb.append("AND RK_NAME like '%"+searchKeyword+"%' 		\n");
				sb.append("ORDER BY RK_NAME ASC 	\n");
			}
			if(searchKeyword.equals("") && kor_val.equals("") && eng_val.equals("") && num_val.equals("")){
				sb.append("ORDER BY RK_NAME ASC 	\n");
			}
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				map = new HashMap();
				map.put("rk_seq", rs.getString("RK_SEQ"));
				map.put("rk_name", rs.getString("RK_NAME"));
				result.add(map);
			}
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		return result;
	}
	/*
	public boolean InsertKeyword(RelationBean bean)
    {
		boolean result = false;
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("INSERT INTO RELATION_KEYWORD_R (RK_SEQ, RK_NAME, RK_USE)\n"); 
			sb.append("VALUES (?, ?, ?)							 \n");
	
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			pstmt.clearParameters();
			int idx = 0;
			pstmt.setString(++idx, bean.getRk_seq());
			pstmt.setString(++idx, bean.getRk_name());
			pstmt.setString(++idx, bean.getRk_use()); 
			pstmt.executeUpdate();
			
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }*/
	public RelationBean getKeyword(String rkSeq)
	{
		dbconn = null;
		stmt = null;
		pstmt = null;
		rs = null;
		sb = null;
		ArrayList arrList = new ArrayList();
		
		RelationBean RKeyword = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT RK_SEQ, RK_NAME, RK_USE		\n");
			sb.append("FROM RELATION_KEYWORD_R			\n");
			sb.append("WHERE RK_SEQ = ('"+rkSeq+"')				\n");
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				RKeyword = new RelationBean();
				RKeyword.setRk_seq(rs.getString("RK_SEQ"));
				RKeyword.setRk_name(rs.getString("RK_NAME"));
				RKeyword.setRk_use(rs.getString("RK_USE"));
			}
			
		} catch (SQLException	ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally{
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}		
		return RKeyword;				
	}
	
	public boolean InsertKeyword(String rkName)
    {
		boolean result = false;
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
						
			sb = new StringBuffer();
			sb.append(" INSERT INTO RELATION_KEYWORD_R (RK_NAME)		\n");
			sb.append(" VALUES ('"+rkName+"')									\n");
			
			stmt.executeUpdate(sb.toString());
			result = true;
			
		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	public boolean UpdateKeyword(String rkName, String rkSeq)
    {
		boolean result = false;
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append(" UPDATE RELATION_KEYWORD_R		 		\n");
			sb.append(" SET RK_NAME = '"+rkName+"'				\n");
			sb.append(" WHERE RK_SEQ = "+rkSeq+"				\n");
			
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());
			result = true;
			
		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	public boolean DeleteKeyword(String rkSeq)
    {
		boolean result = false;
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append(" UPDATE RELATION_KEYWORD_R		 		\n");
			sb.append(" SET RK_USE = 'N'				\n");
			sb.append(" WHERE RK_SEQ = "+rkSeq+"				\n");
			
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());
			result = true;
			
		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	public String relationkeywordMerge(String keyword, String rkSeqs) throws SQLException, NamingException{
		String result = "";
		String insertRkSeq = "";
		//String idSeqs = "";
		ArrayList relArr = null;
		String[] data = null; 
		boolean chk = false;
		boolean delChk1 = false;
		boolean delChk2 = false;
		dbconn = new DBconn();
		dbconn.getDBCPConnection();	
		stmt = dbconn.createStatement();
		
		try {
			//트랜잭션 시작
			dbconn.TransactionStart();
			sb = new StringBuffer();
			sb.append(" INSERT INTO RELATION_KEYWORD_R (RK_NAME) \n");
			sb.append(" VALUES ('"+keyword+"');                                         \n");
			System.out.println(sb.toString());
			if(stmt.executeUpdate(sb.toString()) > 0  ){
				sb = new StringBuffer();
				sb.append(" SELECT LAST_INSERT_ID() AS last_rkseq \n");
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				if(rs.next()){
					insertRkSeq = rs.getString("last_rkseq");
					System.out.println("insertRkSeq  : "+insertRkSeq);
				}
			}else{
				chk = false;
			}

			if(!insertRkSeq.equals("")){
				
				sb = new StringBuffer();
			    sb.append("   SELECT ID_SEQ 						\n");
			    sb.append("        , IC_TYPE		 				\n");
			    sb.append("        , IC_CODE		 				\n");
			    sb.append("        , RK_SEQ		 					\n");
			    sb.append("   FROM RELATION_KEYWORD_MAP MAP      	\n");
			    sb.append("  WHERE RK_SEQ IN ("+rkSeqs+")         	\n");
			    sb.append("  GROUP BY ID_SEQ, IC_TYPE, IC_CODE		\n");
			    sb.append("  ORDER BY RK_SEQ, ID_SEQ				\n");
			    System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				relArr =  new ArrayList();
				while(rs.next()){
					data = new String[4];
					data[0] = rs.getString("ID_SEQ");
					data[1] = rs.getString("IC_TYPE");
					data[2] = rs.getString("IC_CODE");
					data[3] = rs.getString("RK_SEQ");
					relArr.add(data);
					
				}
			}
			int cnt = 0;
			if(relArr.size() > 0 ){
				String[] temp = null;

				for(int i=0; i < relArr.size(); i++){
					temp = (String[])relArr.get(i);
					sb = new StringBuffer();
					if(temp!=null){
						sb.append("	INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE, PRE_RK_SEQ)	\n"); 
						sb.append("	VALUES ("+temp[0]+", "+insertRkSeq+", "+temp[1]+", "+temp[2]+", "+temp[3]+");				\n");
						System.out.println(sb.toString());
						if(stmt.executeUpdate(sb.toString()) > 0  ){
							cnt++;
						}					
					}

				}
			}
			chk = false;
			
			if(cnt > 0){
				chk = true;
			}
			
/*			sb = new StringBuffer();
			sb.append("	DELETE FROM RELATION_KEYWORD_MAP WHERE RK_SEQ IN ("+rkSeqs+") \n");
			if(stmt.executeUpdate(sb.toString()) > 0  ){ delChk1 = true; }
			
		    sb = new StringBuffer();
		    sb.append("	DELETE FROM RELATION_KEYWORD_R WHERE RK_SEQ IN ("+rkSeqs+")		\n");
		    if(stmt.executeUpdate(sb.toString()) > 0  ){delChk2 = true;}*/
			
		    // RK_USE 사용여부 y, n
			if(chk){
				chk = false;
				sb = new StringBuffer();
				sb.append("	UPDATE RELATION_KEYWORD_R SET RK_USE='N' WHERE RK_SEQ IN ("+rkSeqs+")	\n");
				if(stmt.executeUpdate(sb.toString()) > 0  ){ chk = true; }				
			}
			
			if(chk){
				dbconn.Commit();
				result = "success";
			}else{
				result = "fail";
				dbconn.Rollback();
			}
			
		} catch (SQLException ex) {
			if(dbconn!=null) try{dbconn.Rollback();}catch(SQLException sqle){}
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			if(dbconn!=null) try{dbconn.Rollback();}catch(SQLException sqle){}
			Log.writeExpt(ex);
		} finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
		
        return result;
		
	}
	
}
