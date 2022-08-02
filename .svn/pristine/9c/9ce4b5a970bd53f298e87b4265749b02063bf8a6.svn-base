package risk.issue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import risk.DBconn.DBconn;
import risk.statistics.StatisticsSuperBean;
import risk.util.ConfigUtil;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class IssueMgr_t {
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = null;	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	private IssueBean isBean = null;
	private IssueTitleBean itBean = null;
	private IssueDataBean idBean = null;
	private IssueCodeBean icBean = null;
	private IssueCommentBean icmBean = null;
	private int startNum = 0;
    private int endNum = 0;
	private int totalIssueCnt = 0;
	private int totalIssueTitleCnt = 0;
	private int totalIssueDataCnt = 0;
	private int totalSameDataCnt = 0;
	
	/**
     * 어레이 호출 함수의 정보 카운트
     * @return
     */
	public int getTotalIssueCnt() {
		return totalIssueCnt;
	}

	public int getTotalIssueTitleCnt() {
		return totalIssueTitleCnt;
	}

	public int getTotalIssueDataCnt() {
		return totalIssueDataCnt;
	}
	
	public int getTotalSameDataCnt() {
		return totalSameDataCnt;
	}
	

	/**
     * ISSUE 등록하고 해당번호를 반환
     * @param i_seq : ISSUE.I_SEQ
     * @return
     */
	public boolean insertIssue(IssueBean isBean)
	{		
		boolean result = false;
		String insertNum = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" INSERT INTO ISSUE(	                            		   \n");				
			sb.append("                   I_TITLE                                  \n");
			sb.append("                   ,I_REGDATE                               \n");			
			sb.append("                   ,I_USEYN                                 \n");
			sb.append("                   ,M_SEQ)                                  \n");
			sb.append(" VALUES(                                                    \n");
			sb.append("        '"+isBean.getI_title()+"'                           \n");			
			sb.append("        ,'"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"'     \n");
			sb.append("        ,'"+isBean.getI_useyn()+"'                          \n");				
			sb.append("        ,"+isBean.getM_seq()+"                              \n");
			sb.append(" )                                                          \n");
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
     * ISSUE 등록하고 해당번호를 반환
     * @param IssueDataBean
     * @return
     */
	public boolean updateIssue(IssueBean isBean)
	{	
		boolean result = false;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append("UPDATE ISSUE SET I_TITLE = '"+isBean.getI_title()+"' WHERE I_SEQ IN ("+isBean.getI_seq()+")  \n");			
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
     * ISSUE 의 사용 상태를 변경한다. 
     * @param IssueDataBean
     * @return
     */
	public boolean updateIssueFlag(IssueBean isBean)
	{	
		boolean result = false;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			String nextFalg = "Y";
			String str = "SELECT I_USEYN FROM ISSUE WHERE I_SEQ IN ("+isBean.getI_seq()+")";
			rs = stmt.executeQuery(str);			
			if(rs.next()){
				if(rs.getString("I_USEYN").equals("Y"))
					nextFalg = "N";
				else
					nextFalg = "Y";
			}
			
			sb = new StringBuffer();   
			sb.append("UPDATE ISSUE SET I_USEYN = '"+nextFalg+"' WHERE I_SEQ IN ("+isBean.getI_seq()+")  \n");			
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
     * ISSUE 등록하고 해당번호를 반환
     * @param IssueDataBean
     * @return
     */
	public boolean deleteIssue(IssueBean isBean)
	{	
		boolean result = false;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append("SELECT COUNT(*) AS CNT FROM ISSUE_TITLE WHERE I_SEQ IN ("+isBean.getI_seq()+")");
			rs = stmt.executeQuery(sb.toString());			
			if(rs.next()){
				if(rs.getInt("CNT") == 0){
					sb = new StringBuffer(); 
					sb.append(" DELETE FROM ISSUE WHERE I_SEQ IN ("+isBean.getI_seq()+")               \n");					
					if(stmt.executeUpdate(sb.toString())>0) {
						result = true;						
					}
				}else{
					result = false;
				}
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
		return result;
	}
	 
    /**
     * ISSUE 테이블을 조회하여 해당정보를 어레이로 반환.
     * @param pageNum : 페이징번호 0일시 페이징처리 안함
     * @param i_seq : ISSUE.I_SEQ
     * @param schKey : ISSUE.I_TITLE
     * @param schSdate : ISSUE.I_REGDATE
     * @param schEdate : ISSUE.I_REGDATE
     * @param useYn : ISSUE.I_USEYN
     * @return : 이슈검색리스트
     */
    public ArrayList getIssueList( 
    		int pageNum, 
    		int rowCnt, 
    		String i_seq, 
    		String schKey,	    	
    		String schSdate, 
    		String schEdate, 	    	
    		String useYN
    )    
    {    	
    	ArrayList issueList = new ArrayList();    	    	
    	String tmpI_seq = null;
    	
    	//리스트 시작, 끝번호 
		if(rowCnt>0) this.startNum = (pageNum-1)*rowCnt;
		this.endNum = rowCnt;
    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("		SELECT COUNT(I_SEQ) AS CNT 								\n");
			sb.append("		FROM ISSUE                                                      \n");
			sb.append("		WHERE 1=1														\n");
			if( !i_seq.equals("") ) {
				sb.append("		AND	I_SEQ IN ("+i_seq+")  									\n");
			}				
			if( !schKey.equals("") ) {
				sb.append("		AND	I_TITLE LIKE '%"+schKey+"%'  							\n");
			}				
			if( !schSdate.equals("") && !schEdate.equals("")) {					
				sb.append("		AND	I_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");				
			}				
			if (!useYN.equals("")) {
				sb.append(" 	AND I_USEYN = '"+useYN+"'									    \n");
			}
						
			rs = stmt.executeQuery(sb.toString());
            if ( rs.next() ) {
            	totalIssueCnt  = rs.getInt("CNT");            	
            }
            
            rs.close();                     			
			sb = new StringBuffer();				
			sb.append(" SELECT I_SEQ                                                         \n");				
			sb.append("        ,I_TITLE                                                      \n");						
			sb.append("        ,DATE_FORMAT(I_REGDATE,'%Y-%m-%d %H:%i:%s') AS I_REGDATE     \n");
			sb.append("        ,M_SEQ , I_USEYN                                                       \n");
			sb.append("        ,(SELECT M_NAME FROM MEMBER WHERE M_SEQ = I.M_SEQ) AS M_NAME  \n");
			sb.append("        ,(SELECT COUNT(1) FROM ISSUE_TITLE WHERE I_SEQ = I.I_SEQ) AS I_COUNT  \n");
			sb.append(" FROM ISSUE I                                                         \n");
			sb.append(" WHERE 1=1														     \n");
			if( !i_seq.equals("") ) {
				sb.append("		AND	I_SEQ IN ("+i_seq+")  									 \n");
			}				
			if( !schKey.equals("") ) {
				sb.append("		AND	I_TITLE LIKE '%"+schKey+"%'  							 \n");
			}
			if( !schSdate.equals("") && !schEdate.equals("")) {
				sb.append("		AND	I_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	 \n");
			}				
			if (!useYN.equals("")) {
				sb.append(" 	AND I_USEYN = '"+useYN+"'									     \n");
			}
			sb.append(" ORDER BY I_REGDATE DESC 		                                     \n");
			if(pageNum>0){
			sb.append(" LIMIT   "+startNum+","+endNum+"                                      \n");
			}			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				isBean = new IssueBean();
				isBean.setI_seq(rs.getString("I_SEQ"));
				isBean.setI_title(rs.getString("I_TITLE"));
				isBean.setI_regdate(rs.getString("I_REGDATE"));
				isBean.setM_seq(rs.getString("M_SEQ"));
				isBean.setI_useyn(rs.getString("I_USEYN"));
				isBean.setM_name(rs.getString("M_NAME"));
				isBean.setI_count(rs.getString("I_COUNT"));
				issueList.add(isBean);
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
	    	
	    return issueList;
	 }
	
	/**
     * ISSUE_TITLE 등록하고 해당번호를 반환
     * @param IssueTitleBean
     * @return
     */
	 public boolean insertIssueTitle(IssueTitleBean itBean)
	 {
    	String[] arrTemp;
    	String[] arrTypeCode ;
        boolean result = false;
        String insertNum = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" INSERT INTO ISSUE_TITLE(	                      		   \n");				
			sb.append("                         IT_TITLE                           \n");
			sb.append("                         ,I_SEQ                             \n");
			sb.append("                         ,IT_REGDATE                        \n");				
			sb.append("                         ,IT_USEYN                          \n");
			sb.append("                         ,M_SEQ)                            \n");
			sb.append(" VALUES(                                                    \n");
			sb.append("        '"+itBean.getIt_title()+"'                          \n");
			sb.append("        ,"+itBean.getI_seq()+"                              \n");				
			sb.append("        ,'"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"'     \n");
			sb.append("        ,'"+itBean.getIt_useyn()+"'                         \n");				
			sb.append("        ,"+itBean.getM_seq()+"                              \n");
			sb.append(" )                                                          \n");
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
     * ISSUE_TITLE 수정
     * @param it_seq : ISSUE_TITLE.IT_SEQ
     * @return
     */
	public boolean updateIssueTitle(IssueTitleBean itBean)
	{	
		boolean result = false;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb = new StringBuffer();
			sb.append("UPDATE ISSUE_TITLE SET IT_TITLE = '"+itBean.getIt_title()+"'   WHERE IT_SEQ IN ("+itBean.getIt_seq()+")     \n");			
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
     * ISSUE_TITLE의 useyn 수정
     * @param it_seq : ISSUE_TITLE.IT_SEQ
     * @return
     */
	public boolean updateIssueTitleFlag(IssueTitleBean itBean)
	{	
		boolean result = false;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			String nextFalg = "Y";
			String str = "SELECT IT_USEYN FROM ISSUE_TITLE WHERE IT_SEQ IN ("+itBean.getIt_seq()+")";
			rs = stmt.executeQuery(str);			
			if(rs.next()){
				if(rs.getString("IT_USEYN").equals("Y"))
					nextFalg = "N";
				else
					nextFalg = "Y";
			}
			
			
			sb = new StringBuffer();   
			sb = new StringBuffer();
			sb.append("UPDATE ISSUE_TITLE SET IT_USEYN = '"+nextFalg+"'   WHERE IT_SEQ IN ("+itBean.getIt_seq()+")     \n");			
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
     * ISSUE_TITLE 삭제
     * @param it_seq : ISSUE_TITLE.IT_SEQ
     * @return
     */
	public boolean deleteIssueTitle(IssueTitleBean itBean)
	{	
		boolean result = false;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb = new StringBuffer();
			sb.append(" DELETE FROM ISSUE_TITLE WHERE IT_SEQ IN ("+itBean.getIt_seq()+")     \n");			
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
     * ISSUE_TITLE 테이블을 조회하여 해당정보를 어레이로 반환.
     * @param pageNum : 페이징번호 0일시 페이징처리 안함
     * @param it_seq : ISSUE_TITLE.IT_SEQ
     * @param schKey : ISSUE_TITLE.IT_TITLE
     * @param schSdate : ISSUE_TITLE.IT_REGDATE
     * @param schEdate : ISSUE_TITLE.IT_REGDATE
     * @param useYn : ISSUE.IT_USEYN
     * @return : 이슈타이틀검색리스트
     */
    public ArrayList getIssueTitleList( 
    		int pageNum, 
    		int rowCnt,
    		String i_seq,
    		String it_seq, 
    		String schKey,	    	
    		String schSdate, 
    		String schEdate, 	    	
    		String useYN
    )    
    {    	
    	ArrayList issueTitleList = new ArrayList();    	    	
    	String tmpI_seq = null;
    	
    	//리스트 시작, 끝번호 
		if(rowCnt>0) this.startNum = (pageNum-1)*rowCnt;
		this.endNum = rowCnt;
    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("		SELECT COUNT(IT_SEQ) AS CNT 								\n");
			sb.append("		FROM ISSUE_TITLE                                                      \n");
			sb.append("		WHERE 1=1														\n");
			if( !i_seq.equals("") ) {
				sb.append("		AND	I_SEQ IN ("+i_seq+")  									\n");
			}
			if( !it_seq.equals("") ) {
				sb.append("		AND	IT_SEQ IN ("+it_seq+")  									\n");
			}	
			if( !schKey.equals("") ) {
				sb.append("		AND	IT_TITLE LIKE '%"+schKey+"%'  							\n");
			}				
			if( !schSdate.equals("") && !schEdate.equals("")) {					
				sb.append("		AND	IT_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");				
			}				
			if (!useYN.equals("")) {
				sb.append(" 	AND IT_USEYN = '"+useYN+"'									    \n");
			}
						
			rs = stmt.executeQuery(sb.toString());
            if ( rs.next() ) {
            	totalIssueTitleCnt  = rs.getInt("CNT");            	
            }
            
            rs.close();                     			
			sb = new StringBuffer();				
			sb.append(" SELECT IT_SEQ                                                         \n");	
			sb.append("        ,I_SEQ                                                      \n");
			sb.append("        ,IT_TITLE , IT_USEYN                                                     \n");						
			sb.append("        ,DATE_FORMAT(IT_REGDATE,'%Y-%m-%d %H:%i:%s') AS IT_REGDATE     \n");
			sb.append("        ,M_SEQ                                                         \n");
			sb.append("        ,(SELECT M_NAME FROM MEMBER WHERE M_SEQ = IT.M_SEQ) AS M_NAME  \n");
			sb.append(" FROM ISSUE_TITLE IT                                                   \n");
			sb.append(" WHERE 1=1														      \n");
			if( !i_seq.equals("") ) {
				sb.append("		AND	I_SEQ IN ("+i_seq+")  									\n");
			}
			if( !it_seq.equals("") ) {
				sb.append("		AND	IT_SEQ IN ("+it_seq+")  									\n");
			}				
			if( !schKey.equals("") ) {
				sb.append("		AND	IT_TITLE LIKE '%"+schKey+"%'  							 \n");
			}
			if( !schSdate.equals("") && !schEdate.equals("")) {
				sb.append("		AND	IT_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	 \n");
			}				
			if (!useYN.equals("")) {
				sb.append(" 	AND IT_USEYN = '"+useYN+"'									     \n");
			}
			sb.append(" ORDER BY IT_REGDATE DESC 		                                     \n");
			if(pageNum>0){
			sb.append(" LIMIT   "+startNum+","+endNum+"                                      \n");
			}			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				itBean = new IssueTitleBean();
				itBean.setIt_seq(rs.getString("IT_SEQ"));
				itBean.setI_seq(rs.getString("I_SEQ"));
				itBean.setIt_title(rs.getString("IT_TITLE"));
				itBean.setIt_useyn(rs.getString("IT_USEYN"));
				itBean.setIt_regdate(rs.getString("IT_REGDATE"));
				itBean.setM_seq(rs.getString("M_SEQ"));
				itBean.setM_name(rs.getString("M_NAME"));	
				issueTitleList.add(itBean);
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
		
	    return issueTitleList;
	 }	 
    
    
    
    
    public int IssueRegistCheck(String md_pseqs)    
    {    	
    	int result = 0;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();				
			sb.append(" SELECT COUNT(*) AS CNT FROM ISSUE_DATA WHERE MD_PSEQ IN ("+md_pseqs+")	\n");
			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				result = rs.getInt("CNT");
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
    
    
    
	
	/**
     * ISSUE_DATA, ISSUE_CODE, ISSUE_COMMENT 등록하고 이슈번호 리턴
     * @param IssueDataBean :이슈데이터빈
     * @param IssueCommentBean :이슈코멘트빈
     * @param typeCode :분류체계
     * @return
     */
    public String insertIssueData(IssueDataBean idBean, IssueCommentBean icBean, String typeCode)
    {
    	String[] arrTemp;
    	String[] arrTypeCode ;
        boolean result = false;
        String insertNum = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" INSERT INTO ISSUE_DATA(MD_SEQ                             	\n");
			sb.append("                   ,I_SEQ                                  	\n");
			sb.append("                   ,IT_SEQ                                 	\n");
			sb.append("                   ,S_SEQ                                  	\n");
			sb.append("                   ,SG_SEQ                                 	\n");
			sb.append("                   ,MD_SITE_NAME                           	\n");
			sb.append("                   ,MD_SITE_MENU                           	\n");
			sb.append("                   ,MD_DATE                                	\n");
			sb.append("                   ,ID_TITLE                               	\n");
			sb.append("                   ,ID_URL                                 	\n");
			sb.append("                   ,ID_WRITTER                             	\n");
			sb.append("                   ,ID_CONTENT                             	\n");
			sb.append("                   ,ID_REGDATE                             	\n");
			sb.append("                   ,ID_MAILYN                              	\n");
			sb.append("                   ,ID_USEYN                               	\n");
			sb.append("                   ,MD_SAME_CT                            	\n");
			sb.append("                   ,M_SEQ  	                               	\n");
			sb.append("                   ,MD_TYPE                                 	\n");
			sb.append("                   ,MD_PSEQ                                 	\n");
			sb.append("                   ,ID_REPORTYN                             	\n");
			//sb.append("                   ,K_XP                             		\n");
			//sb.append("                   ,K_YP                             		\n");
			sb.append("                   ,MEDIA_INFO                             	\n");
			sb.append("                   ,F_NEWS	                             	\n");
			sb.append("                   ,L_ALPHA) 								\n");
			sb.append(" VALUES(                                                   	\n");
			sb.append("        "+idBean.getMd_seq()+"                             	\n");
			sb.append("        ,"+idBean.getI_seq()+"                             	\n");
			sb.append("        ,"+idBean.getIt_seq()+"                            	\n");
			sb.append("        ,"+idBean.getS_seq()+"                             	\n");
			sb.append("        ,"+idBean.getSg_seq()+"                            	\n");
			sb.append("        ,'"+idBean.getMd_site_name()+"'                    	\n");
			sb.append("        ,'"+idBean.getMd_site_menu()+"'                    	\n");
			sb.append("        ,'"+idBean.getMd_date()+"'                         	\n");
			sb.append("        ,'"+idBean.getId_title()+"'                        	\n");
			sb.append("        ,'"+idBean.getId_url()+"'                          	\n");
			sb.append("        ,'"+idBean.getId_writter()+"'                      	\n");
			sb.append("        ,'"+idBean.getId_content()+"'                      	\n");
			sb.append("        ,'"+idBean.getId_regdate()+"'                      	\n");
			sb.append("        ,'"+idBean.getId_mailyn()+"'                       	\n");
			sb.append("        ,'"+idBean.getId_useyn()+"'                        	\n");
			sb.append("        ,'"+idBean.getMd_same_ct()+"'                        \n");
			sb.append("        ,"+idBean.getM_seq()+"                             	\n");
			sb.append("        ,"+idBean.getMd_type()+"                             \n");
			sb.append("        ,'"+idBean.getMd_pseq()+"'                           \n");
			sb.append("        ,'"+idBean.getId_reportyn()+"'                       \n");
			//sb.append("        ,'"+idBean.getK_xp()+"'     			                \n");
			//sb.append("        ,'"+idBean.getK_yp()+"'                       		\n");
			sb.append("        ,'"+idBean.getMedia_info()+"'                        \n");
			sb.append("        ,'"+idBean.getF_news()+"'                       	 	\n");
			sb.append("        ,'"+idBean.getL_alpha()+"'                           \n");
			sb.append(" )                                                         	\n");
			
			System.out.println(sb.toString());
			
			String sg_seq ="";
			
			if(stmt.executeUpdate(sb.toString())>0) {
				sb = new StringBuffer();
				//sb.append(" SELECT MAX(ID_SEQ) ID_SEQ FROM ISSUE_DATA                  \n");
				sb.append(" SELECT ID_SEQ, (SELECT B.IC_CODE																				  \n"); 
				sb.append("                   FROM IC_S_RELATION A																			  \n");
				sb.append("                      , ISSUE_CODE B 																			  \n");
				sb.append("                  WHERE A.IC_SEQ = B.IC_SEQ																		  \n"); 
				sb.append("                    AND A.S_SEQ = SG_SEQ) AS SG_SEQ FROM ISSUE_DATA WHERE MD_SEQ = "+idBean.getMd_seq()+"   		  \n");
				
				rs = stmt.executeQuery(sb.toString());
				if(rs.next()){
					insertNum = rs.getString("ID_SEQ");
					sg_seq = rs.getString("SG_SEQ");
				}
				
				if(!idBean.getMd_site_menu().equals("SOLR") && !idBean.getSg_seq().equals("30")){
					sb = new StringBuffer();
					sb.append(" UPDATE IDX_TMP SET ISSUE_CHECK ='Y'                        \n");
					sb.append(" WHERE MD_SEQ = "+idBean.getMd_seq()+"                  \n");
					stmt.executeUpdate(sb.toString());	
				}
				
				if(idBean.getSg_seq().equals("30")){
					sb = new StringBuffer();
					sb.append(" UPDATE TOP_IDX SET ISSUE_CHECK ='Y'                        \n");
					sb.append(" WHERE T_SEQ = "+idBean.getMd_seq()+"                  \n");
					stmt.executeUpdate(sb.toString());
					
				}
				
				
				
			}							
			
			if(icBean!=null){			
				sb = new StringBuffer();   
				sb.append(" INSERT INTO ISSUE_COMMENT(ID_SEQ                        \n");							
				sb.append("                           ,IM_DATE)                     \n");
				sb.append("                           ,IM_COMMENT                   \n");
				sb.append("                           )                             \n");
				sb.append(" VALUES(                                                 \n");
				sb.append("        "+insertNum+"                                    \n");
				sb.append("        ,'"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"'  \n");
				sb.append("        ,"+icBean.getIm_comment()+"                      \n");		
				sb.append(" )                                                       \n");			
				if(stmt.executeUpdate(sb.toString())>0) result = true;
			}			
			
			Boolean check = false;
			
			if(typeCode!=null && !typeCode.equals("")){
				arrTemp = typeCode.split("@");
				for (int i = 0; i < arrTemp.length; i++) {		        		
					arrTypeCode = arrTemp[i].split(",");
					
					if(arrTypeCode[0].equals("6")){
						check = true;
					}
					
					sb = new StringBuffer();
	        		sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
	        		sb.append("VALUES ( 						\n");
	        		sb.append("        " + insertNum + " 		\n");
	        		sb.append("        ," + arrTypeCode[0] + "	\n");
	        		sb.append("        ," + arrTypeCode[1] + "  \n");
	        		sb.append("        ) 						\n");		        		
	        		if(stmt.executeUpdate(sb.toString())>0) result = true;        		
	        	}
				
				if(!check){
					
					sb = new StringBuffer();
					
					sb = new StringBuffer();
	        		sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
	        		sb.append("VALUES ( 						\n");
	        		sb.append("        " + insertNum + " 		\n");
	        		sb.append("        ,6						\n");
	        		sb.append("        ,"+sg_seq+"  			\n");
	        		sb.append("        ) 						\n");		        		
	        		if(stmt.executeUpdate(sb.toString())>0) result = true;
				}
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
        return insertNum;
    }
    
    
    /*
     * 자기사 이슈 저장
     */
    public String insertIssueData_sub(String md_seq, IssueCommentBean icBean, String typeCode, IssueDataBean idBean)
    {
    
        String insertNum = "";
        String sg_seq = "";
        String[] arrTemp = null; 
        String[] arrTypeCode = null;
        
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			//자기사 가져오기~
			sb = new StringBuffer();
			sb.append("SELECT C.MD_SEQ 																				\n");
			sb.append("  FROM META_TMP C																				\n");
			sb.append(" WHERE C.MD_PSEQ = (SELECT MD_PSEQ FROM META_TMP WHERE MD_SEQ = "+md_seq+")						\n");
			sb.append("   AND C.MD_SEQ <> "+md_seq+"																\n");
			sb.append("   AND NOT EXISTS (																			\n");
			sb.append("                   SELECT 1																	\n"); 
			sb.append("                     FROM META_TMP A, ISSUE_DATA B												\n"); 
			sb.append("                    WHERE A.MD_PSEQ = (SELECT MD_PSEQ FROM META_TMP WHERE MD_SEQ = "+md_seq+")	\n");  
			sb.append("                      AND A.MD_SEQ <> "+md_seq+"												\n");
			sb.append("                      AND A.MD_SEQ = B.MD_SEQ												\n");
			sb.append("                      AND A.MD_SEQ = C.MD_PSEQ												\n");
			sb.append("                   )																			\n");
			
			rs = stmt.executeQuery(sb.toString());
			
			ArrayList arr_mdSeq = new ArrayList();
			while(rs.next()){
				arr_mdSeq.add(rs.getString("MD_SEQ"));
			}
			
			System.out.println("유사 : " + arr_mdSeq.size() + "건");
			
			for(int i = 0; i < arr_mdSeq.size(); i++){				
				sb = new StringBuffer();
				sb.append("INSERT INTO ISSUE_DATA      ( I_SEQ					\n");
				sb.append("                            , IT_SEQ					\n");
				sb.append("                            , MD_SEQ					\n");
				sb.append("                            , ID_TITLE				\n");
				sb.append("                            , ID_URL					\n");
				sb.append("                            , SG_SEQ					\n");
				sb.append("                            , S_SEQ					\n");
				sb.append("                            , MD_SITE_NAME			\n");
				sb.append("                            , MD_SITE_MENU			\n");
				sb.append("                            , MD_DATE				\n");
				sb.append("                            , ID_WRITTER				\n");
				sb.append("                            , ID_CONTENT				\n");
				sb.append("                            , ID_REGDATE				\n");
				sb.append("                            , ID_MAILYN				\n");
				sb.append("                            , ID_USEYN				\n");
				sb.append("                            , M_SEQ					\n");
				sb.append("                            , MD_SAME_CT				\n");
				sb.append("                            , MD_TYPE				\n");
				sb.append("                            , L_ALPHA				\n");
				sb.append("                            , ID_REPORTYN			\n");
				//sb.append("                            , K_XP					\n");
				//sb.append("                            , K_YP					\n");
				sb.append("                            , MEDIA_INFO				\n");
				sb.append("                            , F_NEWS					\n");
				sb.append("                            , MD_PSEQ) 				\n");
				sb.append(" (													\n");
				sb.append("   SELECT 0 AS I_SEQ									\n"); 
				sb.append("        , 0 AS IT_SEQ								\n");
				sb.append("        , A.MD_SEQ 									\n");
				sb.append("        , A.MD_TITLE									\n");
				sb.append("        , A.MD_URL									\n");
				sb.append("        , B.SG_SEQ									\n");
				sb.append("        , A.S_SEQ									\n");
				sb.append("        , A.MD_SITE_NAME								\n");
				sb.append("        , A.MD_MENU_NAME								\n");
				sb.append("        , A.MD_DATE									\n");
				sb.append("        , ''											\n");
				sb.append("        , C.MD_CONTENT								\n");
				sb.append("        , NOW()										\n");
				sb.append("        , 'N'										\n");
				sb.append("        , 'Y'										\n");
				sb.append("        , 1											\n");
				sb.append("        , A.MD_SAME_COUNT							\n");
				sb.append("        , A.MD_TYPE									\n");
				sb.append("        , A.L_ALPHA									\n");
				sb.append("        , 'N'										\n");
				//sb.append("        , "+idBean.getK_xp()+"						\n");
				//sb.append("        , "+idBean.getK_yp()+"						\n");
				sb.append("        , '"+ idBean.getMedia_info() +"'				\n");
				sb.append("        , ''											\n");
				sb.append("        , A.MD_PSEQ 									\n");
				sb.append("     FROM META_TMP A, SG_S_RELATION B, DATA_TMP C			\n");
				sb.append("    WHERE A.S_SEQ = B.S_SEQ							\n");
				sb.append("      AND A.MD_SEQ = C.MD_SEQ						\n");
				sb.append("      AND A.MD_SEQ = "+(String)arr_mdSeq.get(i)+"	\n");
				sb.append(" )													\n");
				
				
				insertNum = "";
				sg_seq = "";
				
				if(stmt.executeUpdate(sb.toString()) > 0){
					sb = new StringBuffer();
					sb.append(" SELECT ID_SEQ, (SELECT B.IC_CODE																				  \n"); 
					sb.append("                   FROM IC_S_RELATION A																			  \n");
					sb.append("                      , ISSUE_CODE B 																			  \n");
					sb.append("                  WHERE A.IC_SEQ = B.IC_SEQ																		  \n"); 
					sb.append("                    AND A.S_SEQ = SG_SEQ) AS SG_SEQ FROM ISSUE_DATA WHERE MD_SEQ = "+(String)arr_mdSeq.get(i)+"    \n");			
					rs = stmt.executeQuery(sb.toString());
					if(rs.next()){
						insertNum = rs.getString("ID_SEQ");
						sg_seq = rs.getString("SG_SEQ");
					}
					
					
					sb = new StringBuffer();
					sb.append(" UPDATE IDX_TMP SET ISSUE_CHECK ='Y'                        \n");
					sb.append(" WHERE MD_SEQ = "+(String)arr_mdSeq.get(i)+"            \n");
					stmt.executeUpdate(sb.toString());
					
				}
				
				
				
				

				if(icBean!=null && !insertNum.equals("")){			
					sb = new StringBuffer();   
					sb.append(" INSERT INTO ISSUE_COMMENT(ID_SEQ                        \n");							
					sb.append("                           ,IM_DATE)                     \n");
					sb.append("                           ,IM_COMMENT                   \n");
					sb.append("                           )                             \n");
					sb.append(" VALUES(                                                 \n");
					sb.append("        "+insertNum+"                                    \n");
					sb.append("        ,'"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"'  \n");
					sb.append("        ,"+icBean.getIm_comment()+"                      \n");		
					sb.append(" )                                                       \n");
					
					stmt.executeUpdate(sb.toString());
				}	
				
				if(typeCode!=null && !typeCode.equals("") && !insertNum.equals("")){
					
					sb = new StringBuffer();   
					sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
	        		sb.append("VALUES ( 						\n");
	        		sb.append("        " + insertNum + " 		\n");
	        		sb.append("        ,6						\n");
	        		sb.append("        ," + sg_seq + " 			\n");
	        		sb.append("        ) 						\n");		        		
	        		stmt.executeUpdate(sb.toString());
					
					arrTemp = typeCode.split("@");
					
					for (int j = 0; j < arrTemp.length; j++) {		        		
						arrTypeCode = arrTemp[j].split(",");
						if(!arrTypeCode[0].equals("6")){
							sb = new StringBuffer();
			        		sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
			        		sb.append("VALUES ( 						\n");
			        		sb.append("        " + insertNum + " 		\n");
			        		sb.append("        ," + arrTypeCode[0] + "	\n");
			        		sb.append("        ," + arrTypeCode[1] + "  \n");
			        		sb.append("        ) 						\n");		        		
			        		stmt.executeUpdate(sb.toString());
						}
		        	}
					
					
					
					
				}
				
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
        return insertNum;
    }
    
    /**
     * ISSUE_DATA, ISSUE_CODE, ISSUE_COMMENT 수정
     * @param IssueDataBean :이슈데이터빈
     * @param IssueCommentBean :이슈코멘트빈
     * @param typeCode :분류체계
     * @return
     */
    public boolean updateIssueData(IssueDataBean idBean,  IssueCommentBean icBean, String typeCode)
    {
    	String[] arrTemp;
    	String[] arrTypeCode ;
        boolean result = false;
       
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" UPDATE ISSUE_DATA SET                                     \n");
			sb.append("                 I_SEQ ="+idBean.getI_seq()+"              \n");
			sb.append("                 ,IT_SEQ ="+idBean.getIt_seq()+"           \n");				
			sb.append("                 ,ID_TITLE ='"+idBean.getId_title()+"'     \n");
			sb.append("                 ,ID_URL ='"+idBean.getId_url()+"'         \n");
			sb.append("                 ,MD_SITE_NAME ='"+idBean.getMd_site_name()+"' \n");
			sb.append("                 ,ID_WRITTER ='"+idBean.getId_writter()+"' \n");		
			sb.append("                 ,ID_CONTENT ='"+idBean.getId_content()+"' \n");						
			sb.append("                 ,M_SEQ ="+idBean.getM_seq()+"             \n");
			sb.append("                 ,MD_TYPE ="+idBean.getMd_type()+"             \n");
			sb.append("                 ,ID_REPORTYN = '"+idBean.getId_reportyn()+"'             \n");
			sb.append("                 ,K_XP = "+idBean.getK_xp()+"             \n");
			sb.append("                 ,K_YP = "+idBean.getK_yp()+"             \n");
			sb.append("                 ,MEDIA_INFO = '"+idBean.getMedia_info()+"'       \n");
			sb.append("                 ,F_NEWS = '"+idBean.getF_news()+"'       \n");
			sb.append(" WHERE                                                     \n");
			sb.append("       ID_SEQ = "+idBean.getId_seq()+"                     \n");
			
			if(stmt.executeUpdate(sb.toString())>0) {
				sb = new StringBuffer();
				sb.append("DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ ="+idBean.getId_seq()+"   \n");			
				stmt.executeUpdate(sb.toString());
				
				sb = new StringBuffer();
				sb.append("DELETE FROM ISSUE_COMMENT WHERE ID_SEQ ="+idBean.getId_seq()+"   \n");			
				stmt.executeUpdate(sb.toString());
			}							
			
			if(icBean!=null){			
				sb = new StringBuffer();   
				sb.append(" INSERT INTO ISSUE_COMMENT(ID_SEQ                        \n");							
				sb.append("                           ,IM_DATE)                     \n");
				sb.append("                           ,IM_COMMENT                   \n");
				sb.append("                           )                             \n");
				sb.append(" VALUES(                                                 \n");
				sb.append("        "+idBean.getId_seq()+"                           \n");
				sb.append("        ,'"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"'  \n");
				sb.append("        ,"+icBean.getIm_comment()+"                      \n");		
				sb.append(" )                                                       \n");			
				if(stmt.executeUpdate(sb.toString())>0) result = true;
			}		
			
			if(typeCode!=null && !typeCode.equals("")){
				arrTemp = typeCode.split("@");
				for (int i = 0; i < arrTemp.length; i++) {		        		
					arrTypeCode = arrTemp[i].split(",");   		        	        		        		
					sb = new StringBuffer();
	        		sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
	        		sb.append("VALUES ( 						\n");
	        		sb.append("        " + idBean.getId_seq() +"\n");
	        		sb.append("        ," + arrTypeCode[0] + "	\n");
	        		sb.append("        ," + arrTypeCode[1] + "  \n");
	        		sb.append("        ) 						\n");		        		
	        		if(stmt.executeUpdate(sb.toString())>0) result = true;        		
	        	}
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
        return result;
    }
    
    
    
    public boolean updateChildIssueData(IssueDataBean idBean,  IssueCommentBean icBean, String typeCode)
    {
    	String[] arrTemp;
    	String[] arrTypeCode ;
        boolean result = false;
       
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" SELECT ID_SEQ FROM ISSUE_DATA WHERE MD_PSEQ = "+idBean.getMd_pseq()+"  \n");
			rs =  stmt.executeQuery(sb.toString());
			String idseqs = "";
			ArrayList<String> arrIdSeq = new ArrayList<String>();
			
			
			while(rs.next()){
				if(idseqs.equals("")){
					idseqs = rs.getString("ID_SEQ");
				}else{
					idseqs += "," + rs.getString("ID_SEQ");
				}	
				
				arrIdSeq.add(rs.getString("ID_SEQ"));
			}
			
			//먼저 삭제후 인서트
			
			sb = new StringBuffer();   
			sb.append(" DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ IN ("+idseqs+")  \n");
			stmt.executeUpdate(sb.toString());
			
			
			for(int i=0; i < arrIdSeq.size(); i++){
				if(typeCode!=null && !typeCode.equals("")){
					arrTemp = typeCode.split("@");
					for (int j = 0; j < arrTemp.length; j++) {		        		
						arrTypeCode = arrTemp[j].split(",");   		        	        		        		
						sb = new StringBuffer();
		        		sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
		        		sb.append("VALUES ( 						\n");
		        		sb.append("        " + arrIdSeq.get(i) +"\n");
		        		sb.append("        ," + arrTypeCode[0] + "	\n");
		        		sb.append("        ," + arrTypeCode[1] + "  \n");
		        		sb.append("        ) 						\n");		        		
		        		if(stmt.executeUpdate(sb.toString())>0) result = true;        		
		        	}
					if(icBean!=null){			
						sb = new StringBuffer();   
						sb.append(" INSERT INTO ISSUE_COMMENT(ID_SEQ                        \n");							
						sb.append("                           ,IM_DATE)                     \n");
						sb.append("                           ,IM_COMMENT                   \n");
						sb.append("                           )                             \n");
						sb.append(" VALUES(                                                 \n");
						sb.append("        "+arrIdSeq.get(i)+"                           	\n");
						sb.append("        ,'"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"'  \n");
						sb.append("        ,"+icBean.getIm_comment()+"                      \n");		
						sb.append(" )                                                       \n");			
						if(stmt.executeUpdate(sb.toString())>0) result = true;
					}	
				}
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
        return result;
    }
    
    
    /**
     * ISSUE_DATA, ISSUE_CODE, ISSUE_COMMENT 삭제
     * @param id_seq : ISSUE_DATA.ID_SEQ  
     * @return
     */
    public boolean deleteIssueData(String id_seq)
    {    	
        boolean result = false;
        String tempNo = "";
        String tempPNo = "";
        String tempIseqNo = "";
        
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT MD_PSEQ FROM ISSUE_DATA WHERE ID_SEQ IN ("+id_seq+") \n");		
			rs = stmt.executeQuery(sb.toString());

			while(rs.next()){
				tempPNo += tempPNo.equals("")? rs.getString("MD_PSEQ") : ","+rs.getString("MD_PSEQ");				
			}

			sb = new StringBuffer();
			sb.append("SELECT ID_SEQ FROM ISSUE_DATA WHERE MD_PSEQ IN ("+tempPNo+")                      \n");
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				tempIseqNo += tempIseqNo.equals("")? rs.getString("ID_SEQ") : ","+rs.getString("ID_SEQ");				
			}
			

			
			
			
			//일반 IDX 처리
			tempNo = "";
			sb = new StringBuffer();
			sb.append("SELECT MD_SEQ FROM ISSUE_DATA WHERE MD_PSEQ IN ("+tempPNo+") AND SG_SEQ <> 30           \n");
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				tempNo += tempNo.equals("")? rs.getString("MD_SEQ") : ","+rs.getString("MD_SEQ");				
			}
			if(!tempNo.equals("")){
				sb = new StringBuffer();   
				sb.append("UPDATE IDX_TMP SET ISSUE_CHECK='N' WHERE MD_SEQ IN ("+tempNo+")   \n");
				System.out.println(sb.toString());			
				stmt.executeUpdate(sb.toString());	
			}
			
			//TOP IDX 처리
			tempNo = "";
			sb = new StringBuffer();
			sb.append("SELECT MD_SEQ FROM ISSUE_DATA WHERE MD_PSEQ IN ("+tempPNo+") AND SG_SEQ = 30           \n");
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				tempNo += tempNo.equals("")? rs.getString("MD_SEQ") : ","+rs.getString("MD_SEQ");				
			}
				if(!tempNo.equals("")){
				sb = new StringBuffer();   
				sb.append("UPDATE TOP_IDX SET ISSUE_CHECK='N' WHERE T_SEQ IN ("+tempNo+")   \n");
				System.out.println(sb.toString());			
				stmt.executeUpdate(sb.toString());		
			}
			
			

			
		
			sb = new StringBuffer();
			sb.append(" DELETE FROM ISSUE_DATA                                      \n");		
			sb.append(" WHERE                                                       \n");
			sb.append("       ID_SEQ IN ("+tempIseqNo+")                                \n");						
			System.out.println(sb.toString());
			if(stmt.executeUpdate(sb.toString())>0) result = true;
			
			sb = new StringBuffer();
			sb.append("DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ IN ("+tempIseqNo+") \n");			
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());
			
			sb = new StringBuffer();
			sb.append("DELETE FROM ISSUE_COMMENT WHERE ID_SEQ IN ("+tempIseqNo+")   \n");						
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());		

		
	
	
			
				
				
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
     * ISSUE_DATA.MD_SEQ 로 ISSUE_DATA ,ISSUE_DATA_CODE ,ISSUE_COMMENT 테이블을 조회하여 해당정보를 빈으로 반환.
     * @param md_seq : ISSUE_DATA.MD_SEQ
     * @return
     */
    public IssueDataBean getIssueDataBean(String md_seq)
    {
    	IssueDataBean idBean = new IssueDataBean();
    	ArrayList arrIdBean = new ArrayList();
    	arrIdBean = getIssueDataList(0,0,"","","",md_seq,"","1","","","","","","","Y","","","N");
    	if(arrIdBean.size()>0)
    	{
    		idBean = (IssueDataBean)arrIdBean.get(0);
    	}
    	return idBean;
    }
    
    
    /**
     * ISSUE_DATA ,ISSUE_DATA_CODE ,ISSUE_COMMENT 테이블을 조회하여 해당정보를 어레이로 반환.
     * @param pageNum : 페이징번호 0일시 페이징처리 안함
     * @param id_seq : ISSUE_DATA.ID_SEQ
     * @param i_seq : ISSUE_DATA.I_SEQ
     * @param it_seq : ISSUE_DATA.IT_SEQ 
     * @param md_seq : ISSUE_DATA.MD_SEQ
     * @param schKey : ISSUE_DATA.ID_TITLE
     * @param DateType : 1:ISSUE_DATA.ID_REGDATE 2:ISSUE_DATA.MD_DATE
     * @param schSdate : ISSUE_DATA.ID_REGDATE , ISSUE_DATA.MD_DATE
     * @param schEdate : ISSUE_DATA.ID_REGDATE , ISSUE_DATA.MD_DATE
     * @param typeCode : ISSUE_DATA_CODE.*
     * @param Order : 정렬하고픈 code type
     * @param useYN : 보고서 사용여부
     * @return : 이슈데이터 검색리스트
     */
    /*
    public ArrayList getIssueDataList( 
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,    		
    		String useYN,
    		String language
    )    
    {
    	return  getIssueDataList(pageNum,rowCnt,id_seq,i_seq,it_seq,md_seq,schKey,DateType,schSdate,schStime,schEdate,schEtime,typeCode,"","Y",language,"");
    }
    
    public ArrayList getIssueDataList2( 
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,    		
    		String useYN,
    		String language,
    		String reportYn
    )    
    {
    	return  getIssueDataList(pageNum,rowCnt,id_seq,i_seq,it_seq,md_seq,schKey,DateType,schSdate,schStime,schEdate,schEtime,typeCode,"","Y",language,reportYn);
    }
    */
    public ArrayList getIssueDataList( 
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,
    		String typeCodeOrder,
    		String useYN,
    		String language,
    		String reportyn,
    		String parents
    ){
    	return  getIssueDataList(pageNum, rowCnt, id_seq,i_seq, it_seq, md_seq, schKey, DateType, schSdate, schStime, schEdate, schEtime, typeCode, typeCodeOrder, useYN, language, reportyn, parents, "");
    }
    
    public ArrayList getIssueDataList( 
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,
    		String typeCodeOrder,
    		String useYN,
    		String language,
    		String reportyn,
    		String parents,
    		String f_news
    ){
    	return  getIssueDataList(pageNum, rowCnt, id_seq,i_seq, it_seq, md_seq, schKey, DateType, schSdate, schStime, schEdate, schEtime, typeCode, typeCodeOrder, useYN, language, reportyn, parents, f_news,"");
    }
    
    /**
     * ISSUE_DATA ,ISSUE_DATA_CODE ,ISSUE_COMMENT 테이블을 조회하여 해당정보를 어레이로 반환.
     * @param pageNum : 페이징번호 0일시 페이징처리 안함
     * @param id_seq : ISSUE_DATA.ID_SEQ
     * @param i_seq : ISSUE_DATA.I_SEQ
     * @param it_seq : ISSUE_DATA.IT_SEQ 
     * @param md_seq : ISSUE_DATA.MD_SEQ
     * @param schKey : ISSUE_DATA.ID_TITLE
     * @param DateType : 1:ISSUE_DATA.ID_REGDATE 2:ISSUE_DATA.MD_DATE
     * @param schSdate : ISSUE_DATA.ID_REGDATE , ISSUE_DATA.MD_DATE
     * @param schEdate : ISSUE_DATA.ID_REGDATE , ISSUE_DATA.MD_DATE
     * @param typeCode : ISSUE_DATA_CODE.*
     * @param typeCodeOrder : ISSUE_DATA_CODE.*
     * @param Order : 정렬하고픈 code type
     * @param useYN : 보고서 사용여부
     * @return : 이슈데이터 검색리스트
     */
    public ArrayList getIssueDataList( 
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,
    		String typeCodeOrder,
    		String useYN,
    		String language,
    		String reportyn,
    		String parents,
    		String f_news,
    		String s_seq
    )    
    {    	
    	ArrayList issueDataList = new ArrayList();    	    	
    	String[] tmpArr = typeCode.split("@");
    	String[] arrTypeCode = null;
    	String tmpId_seq = null;
    	
    	
    	//code에 대한 쿼리문을 생성한다.
    	String tmpSameCodeYn = "";
    	int sameCodeCount = 0;
    	String codeQuery = "SELECT DISTINCT(ID_SEQ) \n";
    	codeQuery +=       "FROM ISSUE_DATA_CODE   \n";
    	codeQuery +=       "WHERE                  \n";
    	codeQuery +=       "	 1=1               \n";
    	for( int i=0 ; i<tmpArr.length; i++ )
		{
    		
			if (!tmpArr[i].equals("")) {
				arrTypeCode = tmpArr[i].split(",");		
					if (arrTypeCode.length==2) {
					if(tmpSameCodeYn.equals(arrTypeCode[0])) sameCodeCount++;
					codeQuery = i == 0 ?	
					codeQuery+"AND (IC_TYPE="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") \n" : codeQuery+" OR (IC_TYPE="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") \n";				
					tmpSameCodeYn = arrTypeCode[0];
				}
			}
		}
    	System.out.println("tmpArr.length:"+tmpArr.length);
    	System.out.println("sameCodeCount:"+sameCodeCount);
    	codeQuery += "GROUP BY ID_SEQ HAVING COUNT(0)="+(tmpArr.length-sameCodeCount);
    	
    	//리스트 시작, 끝번호 
		if(rowCnt>0) this.startNum = (pageNum-1)*rowCnt;
		this.endNum = rowCnt;
    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("		SELECT COUNT(ID.ID_SEQ) AS CNT 								\n");
			sb.append("		     , SUM(ID.MD_SAME_CT + 1) AS SAME_CNT 					\n");
			sb.append("		FROM ISSUE_DATA ID                                          \n");
			if(!typeCode.equals("")){
				sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			sb.append("		WHERE 1=1													\n");
			if( !id_seq.equals("") ) {
				sb.append("		AND	ID.ID_SEQ IN ("+id_seq+")  							\n");
			}
			if( !i_seq.equals("") ) {
				sb.append("		AND	ID.I_SEQ IN ("+i_seq+")  									\n");
			}
			if( !it_seq.equals("") ) {
				sb.append("		AND	ID.IT_SEQ IN ("+it_seq+")  									\n");
			}
			if( !md_seq.equals("") ) {
				sb.append("		AND	ID.MD_SEQ IN ("+md_seq+") 			                        \n");
			}
			if( !schKey.equals("") ) {
				sb.append("		AND	ID.ID_TITLE LIKE '%"+schKey+"%'  							\n");
			}
			if( !schSdate.equals("") && !schEdate.equals("")) {
				if(!schStime.equals("") && !schEtime.equals("")){
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            		\n");	
					}
				}else{
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            		\n");	
					}
				}
			}				
			if (!useYN.equals("")) {
				sb.append(" 	AND ID.ID_USEYN = '"+useYN+"'									    \n");
			}
			if (!language.equals("")) {
				sb.append(" 	AND ID.L_ALPHA = '"+language+"'									    \n");
			}
			
			//최초기사일 경우
			if(!f_news.equals("")){
				sb.append(" AND ID.F_NEWS = '"+f_news+"'		\n");
			}
			
			//모기사일경우
			if(parents.equals("Y")){
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}
			
			if(!s_seq.equals("")){
				sb.append(" AND ID.S_SEQ = "+s_seq+"\n");
			}
			
			
			/*
			// 기사 유형~~
			if(parents.equals("super")){
				//모기사일경우
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}else if(parents.equals("child")){
				//자기사일경우
				sb.append(" AND ID.MD_SEQ <> ID.MD_PSEQ		\n");
			}else if(parents.equals("self")){
				//수동등록
				sb.append(" AND M_SEQ NOT IN (1,3)		\n");
			}else if(parents.equals("childSelf")){
				//모기사 + 수동등록
				sb.append(" AND (ID.MD_SEQ = ID.MD_PSEQ OR M_SEQ NOT IN (1,3))		\n");
			}
			*/
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
            if ( rs.next() ) {
            	totalIssueDataCnt  = rs.getInt("CNT");
            	totalSameDataCnt = rs.getInt("SAME_CNT");
            }
            
            rs.close();                     			
			sb = new StringBuffer();
			
			sb.append("SELECT A.*													\n");
			sb.append("     , FN_GET_SITE_SAME(A.MD_PSEQ,'"+schSdate+" "+schStime+"','"+schEdate+" "+schEtime+"') AS MD_SAME_CT 			\n");
			sb.append("     , FN_GET_SITE_SAME_CHECK(A.MD_PSEQ,'"+schSdate+" "+schStime+"','"+schEdate+" "+schEtime+"') AS MD_SAME_CT_CHECK \n");
			sb.append("     , (SELECT M_NAME FROM MEMBER WHERE M_SEQ = 3) AS M_NAME	\n");
			sb.append("  FROM(														\n");
			
			sb.append(" SELECT ID.ID_SEQ                                                         \n");
			sb.append("        ,ID.MD_SEQ                                                        \n");
			sb.append("        ,ID.I_SEQ                                                     	  \n");
			sb.append("        ,ID.IT_SEQ                                                     	  \n");
			sb.append("        ,ID.ID_URL                                                        \n");
			sb.append("        ,ID.ID_TITLE                                                      \n");
			//sb.append("        ,ID.ID_URL                                                        \n");
			sb.append("        ,ID.SG_SEQ                                                        \n");
			sb.append("        ,ID.S_SEQ                                                         \n");
			sb.append("        ,ID.MD_SITE_NAME                                                  \n");
			sb.append("        ,ID.MD_SITE_MENU                                                  \n");
			sb.append("        ,DATE_FORMAT(ID.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE           \n");
			sb.append("        ,DATE_FORMAT(ID.ID_REGDATE,'%Y-%m-%d %H:%i:%s') AS ID_REGDATE     \n");
			sb.append("        ,ID.ID_WRITTER                                                    \n");
			sb.append("        ,ID.ID_CONTENT                                                    \n");
			sb.append("        ,ID.ID_MAILYN                                                     \n");
			sb.append("        ,ID.ID_USEYN                                                      \n");
			sb.append("        ,ID.M_SEQ                                                         \n");
			//sb.append("        ,ID.MD_SAME_CT                                                    \n");
			sb.append("        ,ID.MD_TYPE                                                    	 \n");
			//sb.append("        ,(SELECT M_NAME FROM MEMBER WHERE M_SEQ = ID.M_SEQ) AS M_NAME  	 \n");
			sb.append("        , ID.L_ALPHA 													 \n");
			sb.append("        , ID.ID_REPORTYN 													 \n");
			sb.append("        , ID.MD_PSEQ 													 \n");
			
			sb.append("        , ID.K_XP 														 \n");
			sb.append("        , ID.K_YP 														 \n");
			sb.append("        , ID.MEDIA_INFO 													 \n");
			sb.append("        , ID.F_NEWS 													 \n");
			
			if(!typeCodeOrder.equals("")){
				sb.append("    ,IDC.IC_CODE      \n");
			}
			sb.append(" FROM ISSUE_DATA ID                                                    \n");
			if(!typeCodeOrder.equals("")&&typeCode.equals("")){
				sb.append("  INNER JOIN (SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE  WHERE  IC_TYPE = "+typeCodeOrder+") IDC  ON (ID.ID_SEQ = IDC.ID_SEQ)    \n");
			}			
			if(!typeCode.equals("")&&typeCodeOrder.equals("")){
				sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			if(!typeCodeOrder.equals("")&&!typeCode.equals("")){
				sb.append("  INNER JOIN (SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE  WHERE  IC_TYPE = "+typeCodeOrder+") IDC    \n");
				sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IDC.ID_SEQ AND ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			
			sb.append(" WHERE 1=1										      \n");
			if(!reportyn.equals("")){
				sb.append("   AND ID.ID_REPORTYN = '"+reportyn+"' 						      \n");
			}
			
			if( !id_seq.equals("") ) {
				sb.append("		AND	ID.ID_SEQ IN ("+id_seq+")  									  \n");
			}
			if( !i_seq.equals("") ) {
				sb.append("		AND	ID.I_SEQ IN ("+i_seq+")  									\n");
			}
			if( !it_seq.equals("") ) {
				sb.append("		AND	ID.IT_SEQ IN ("+it_seq+")  									\n");
			}
			if( !md_seq.equals("") ) {
				sb.append("		AND	ID.MD_SEQ in ("+md_seq+") 			                          \n");
			}
			if( !schKey.equals("") ) {
				sb.append("		AND	ID.ID_TITLE LIKE '%"+schKey+"%'  							  \n");
			}
			if( !schSdate.equals("") && !schEdate.equals("")) {
				if(!schStime.equals("") && !schEtime.equals("")){
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            		\n");	
					}
				}else{
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            		\n");	
					}
				}
			}			
			if (!useYN.equals("")) {
				sb.append(" 	AND ID.ID_USEYN = '"+useYN+"'									      \n");
			}
			if (!language.equals("")) {
				sb.append(" 	AND ID.L_ALPHA = '"+language+"'									    \n");
			}
			
			//최초기사일 경우
			if(!f_news.equals("")){
				sb.append(" AND ID.F_NEWS = '"+f_news+"'		\n");
			}
			if(parents.equals("Y")){
				//모기사일경우
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}
			
			if(!s_seq.equals("")){
				sb.append(" AND ID.S_SEQ = "+s_seq+"\n");
			}
			
			/*
			// 기사 유형~~
			if(parents.equals("super")){
				//모기사일경우
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}else if(parents.equals("child")){
				//자기사일경우
				sb.append(" AND ID.MD_SEQ <> ID.MD_PSEQ		\n");
			}else if(parents.equals("self")){
				//수동등록
				sb.append(" AND M_SEQ NOT IN (1,3)		\n");
			}else if(parents.equals("childSelf")){
				//모기사 + 수동등록
				sb.append(" AND (ID.MD_SEQ = ID.MD_PSEQ OR M_SEQ NOT IN (1,3))		\n");
			}
			*/
			
			
			if(DateType.equals("1")){
				if(!typeCodeOrder.equals("")){
					sb.append(" ORDER BY IDC.IC_CODE, ID_REGDATE DESC 		                                      \n");
				}else{
					sb.append(" ORDER BY ID_REGDATE DESC 		                                      \n");
				}
			
			}else{
				if(!typeCodeOrder.equals("")){
					sb.append(" ORDER BY IDC.IC_CODE, MD_DATE DESC 		                                      \n");
				}else{
					sb.append("	ORDER BY MD_DATE DESC 		                                          \n");	
				}			
			}
			if(pageNum>0){
			sb.append(" LIMIT   "+startNum+","+endNum+"                                       \n");
			}			
			
			
			sb.append("  )A                                                                    \n");
			
			
			
			
			
			
			
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());
			
			tmpId_seq ="";
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setId_writter(rs.getString("ID_WRITTER"));
				idBean.setId_content(rs.getString("ID_CONTENT"));
				idBean.setId_regdate(rs.getString("ID_REGDATE"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setI_seq(rs.getString("I_SEQ"));
				idBean.setIt_seq(rs.getString("IT_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setMd_site_menu(rs.getString("MD_SITE_MENU"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setId_mailyn(rs.getString("ID_MAILYN"));
				idBean.setId_useyn(rs.getString("ID_USEYN"));
				idBean.setM_seq(rs.getString("M_SEQ"));
				idBean.setM_name(rs.getString("M_NAME"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setL_alpha(rs.getString("L_ALPHA"));
				idBean.setId_reportyn(rs.getString("ID_REPORTYN"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setK_xp(rs.getString("K_XP"));
				idBean.setK_yp(rs.getString("K_YP"));
				idBean.setMedia_info(rs.getString("MEDIA_INFO"));
				idBean.setF_news(rs.getString("F_NEWS"));
				idBean.setMd_same_ct_check(rs.getString("MD_SAME_CT_CHECK"));
				
				issueDataList.add(idBean);	
				if(tmpId_seq.equals("")) tmpId_seq += rs.getString("ID_SEQ");
				else tmpId_seq += "," + rs.getString("ID_SEQ");
			}
			
			
			
			String prevI_seq = null;			
			if( tmpId_seq.length() > 0 ) 
			{
				//이슈에 이슈코드어레이 추가
				ArrayList ArrIcList = null;
				ArrIcList = new ArrayList();	    			    	
		    	
				rs.close();
				sb = new StringBuffer();				
				sb.append("SELECT IDC.ID_SEQ 								        	\n");
				sb.append("		  ,IC.IC_SEQ							                \n");
				sb.append("		  ,IDC.IC_TYPE							                \n");
				sb.append(" 	  ,IDC.IC_CODE 								            \n");
				sb.append(" 	  ,IC.IC_PTYPE							                \n");
				sb.append(" 	  ,IC.IC_PCODE   						                \n");
				sb.append(" 	  ,DATE_FORMAT(IC.IC_REGDATE,'%Y-%m-%d %H:%i:%s') IC_REGDATE		\n");				
				sb.append(" 	  ,IC_DESCRIPTION								        \n");				
				sb.append(" 	  ,IC.M_SEQ								                \n");
				sb.append("       ,IC.IC_NAME 									        \n");
				sb.append("FROM ISSUE_CODE IC, ISSUE_DATA_CODE IDC	 					\n");
				sb.append("WHERE	IDC.ID_SEQ IN ("+tmpId_seq+")  						\n");
				sb.append("         AND IC.IC_CODE = IDC.IC_CODE   						\n");
				sb.append("         AND IC.IC_TYPE = IDC.IC_TYPE	 					\n");
				sb.append("ORDER BY IDC.ID_SEQ DESC					 					\n");				
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				
				while(rs.next()){
	        		
	        		if(prevI_seq!=null && !prevI_seq.equals(rs.getString("ID_SEQ")) )
	        		{
	        			addIssueCode(issueDataList,prevI_seq,ArrIcList);        			
	        			ArrIcList = new ArrayList();
	        		}        		
	        		
	        		prevI_seq = rs.getString("ID_SEQ");
	        		
	        		icBean = new IssueCodeBean();
	        		icBean.setIc_seq(rs.getInt("IC_SEQ"));
	        		icBean.setIc_type(rs.getInt("IC_TYPE"));
	        		icBean.setIc_code(rs.getInt("IC_CODE"));
	        		icBean.setIc_ptype(rs.getInt("IC_PTYPE"));
	        		icBean.setIc_pcode(rs.getInt("IC_PCODE"));
	        		icBean.setIc_regdate(rs.getString("IC_REGDATE"));
	        		icBean.setIc_description(rs.getString("IC_DESCRIPTION"));
	        		icBean.setM_seq(rs.getString("M_SEQ"));
	        		icBean.setIc_name(rs.getString("IC_NAME"));
	        		
	        		ArrIcList.add(icBean);
	        	}
				addIssueCode(issueDataList,prevI_seq,ArrIcList);
				
				
				//이슈에 이슈코멘트어레이 추가					    	
				rs = null;
				prevI_seq = null;	
				sb = new StringBuffer();
				ArrayList arrIcmList = new ArrayList();
				
				sb.append("SELECT IM_SEQ, IM_COMMENT, DATE_FORMAT(IM_DATE,'%Y-%m-%d %H:%i:%s') IM_DATE, ID_SEQ \n");
				sb.append("FROM ISSUE_COMMENT	 						                           \n");
				sb.append("WHERE ID_SEQ IN ("+tmpId_seq+")  					                       \n");
				sb.append("ORDER BY IM_SEQ DESC                                 				   \n");				
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				
				while(rs.next()){
	        		
	        		if(prevI_seq!=null && !prevI_seq.equals(rs.getString("ID_SEQ")) )
	        		{
	        			addIssueCode(issueDataList,prevI_seq,arrIcmList);        			
	        			arrIcmList = new ArrayList();
	        		}        		
	        		
	        		prevI_seq = rs.getString("ID_SEQ");
	        		
	        		icmBean = new IssueCommentBean();
	        		icmBean.setIm_seq(rs.getString("IM_SEQ"));
	        		icmBean.setIm_comment(rs.getString("IM_COMMENT"));
	        		icmBean.setIm_date(rs.getString("IM_DATE"));       			        		
	        		
	        		arrIcmList.add(icmBean);
	        	}
				addIssueComment(issueDataList,prevI_seq,arrIcmList);
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
    	
    	return issueDataList;
    }
    
    
    
    
    
    
    public ArrayList getIssueDataList_groupBy( 
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,
    		String typeCodeOrder,
    		String useYN,
    		String language,
    		String reportyn,
    		String parents,
    		String f_news
    )    
    {    	
    	ArrayList issueDataList = new ArrayList();    	    	
    	String[] tmpArr = typeCode.split("@");
    	String[] arrTypeCode = null;
    	String tmpId_seq = null;
    	
    	
    	//code에 대한 쿼리문을 생성한다.
    	String tmpSameCodeYn = "";
    	int sameCodeCount = 0;
    	String codeQuery = "SELECT DISTINCT(ID_SEQ) \n";
    	codeQuery +=       "FROM ISSUE_DATA_CODE   \n";
    	codeQuery +=       "WHERE                  \n";
    	codeQuery +=       "	 1=1               \n";
    	for( int i=0 ; i<tmpArr.length; i++ )
		{
    		
			if (!tmpArr[i].equals("")) {
				arrTypeCode = tmpArr[i].split(",");		
					if (arrTypeCode.length==2) {
					if(tmpSameCodeYn.equals(arrTypeCode[0])) sameCodeCount++;
					codeQuery = i == 0 ?	
					codeQuery+"AND (IC_TYPE="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") \n" : codeQuery+" OR (IC_TYPE="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") \n";				
					tmpSameCodeYn = arrTypeCode[0];
				}
			}
		}
    	System.out.println("tmpArr.length:"+tmpArr.length);
    	System.out.println("sameCodeCount:"+sameCodeCount);
    	codeQuery += "GROUP BY ID_SEQ HAVING COUNT(0)="+(tmpArr.length-sameCodeCount);
    	
    	//리스트 시작, 끝번호 
		if(rowCnt>0) this.startNum = (pageNum-1)*rowCnt;
		this.endNum = rowCnt;
    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();		
			
			
			sb.append("SELECT COUNT(*) AS CNT FROM (\n");
			sb.append("		SELECT MD_PSEQ AS CNT 								\n");
			//sb.append("		SELECT COUNT(ID.ID_SEQ) AS CNT 								\n");
			sb.append("		     , SUM(ID.MD_SAME_CT + 1) AS SAME_CNT 					\n");
			sb.append("		FROM ISSUE_DATA ID                                          \n");
			if(!typeCode.equals("")){
				sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			sb.append("		WHERE 1=1													\n");
			if( !id_seq.equals("") ) {
				sb.append("		AND	ID.ID_SEQ IN ("+id_seq+")  							\n");
			}
			if( !i_seq.equals("") ) {
				sb.append("		AND	ID.I_SEQ IN ("+i_seq+")  									\n");
			}
			if( !it_seq.equals("") ) {
				sb.append("		AND	ID.IT_SEQ IN ("+it_seq+")  									\n");
			}
			if( !md_seq.equals("") ) {
				sb.append("		AND	ID.MD_SEQ IN ("+md_seq+") 			                        \n");
			}
			if( !schKey.equals("") ) {
				String[] arrSchKey = schKey.split(" ");
				
				for(int i =0; i < arrSchKey.length; i++){
					sb.append("		AND	ID.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  							\n");
				}
				
			}
			if( !schSdate.equals("") && !schEdate.equals("")) {
				if(!schStime.equals("") && !schEtime.equals("")){
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            		\n");	
					}
				}else{
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            		\n");	
					}
				}
			}				
			if (!useYN.equals("")) {
				sb.append(" 	AND ID.ID_USEYN = '"+useYN+"'									    \n");
			}
			if (!language.equals("")) {
				sb.append(" 	AND ID.L_ALPHA = '"+language+"'									    \n");
			}
			
			//최초기사일 경우
			if(!f_news.equals("")){
				sb.append(" AND ID.F_NEWS = '"+f_news+"'		\n");
			}
			
			//모기사일경우
			if(parents.equals("Y")){
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}
			
			sb.append(" GROUP BY ID.MD_PSEQ)A		\n");
			
			
			/*
			// 기사 유형~~
			if(parents.equals("super")){
				//모기사일경우
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}else if(parents.equals("child")){
				//자기사일경우
				sb.append(" AND ID.MD_SEQ <> ID.MD_PSEQ		\n");
			}else if(parents.equals("self")){
				//수동등록
				sb.append(" AND M_SEQ NOT IN (1,3)		\n");
			}else if(parents.equals("childSelf")){
				//모기사 + 수동등록
				sb.append(" AND (ID.MD_SEQ = ID.MD_PSEQ OR M_SEQ NOT IN (1,3))		\n");
			}
			*/
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
            if ( rs.next() ) {
            	totalIssueDataCnt  = rs.getInt("CNT");
            	//totalSameDataCnt = rs.getInt("SAME_CNT");
            }
            
            rs.close();                     			
			sb = new StringBuffer();
			
			sb.append("SELECT A.*													\n");
			sb.append("     , FN_GET_SITE_SAME(A.MD_PSEQ,'"+schSdate+" "+schStime+"','"+schEdate+" "+schEtime+"') AS MD_SAME_CT 			\n");
			sb.append("     , FN_GET_SITE_SAME_CHECK(A.MD_PSEQ,'"+schSdate+" "+schStime+"','"+schEdate+" "+schEtime+"') AS MD_SAME_CT_CHECK \n");
			sb.append("     , (SELECT M_NAME FROM MEMBER WHERE M_SEQ = 3) AS M_NAME	\n");
			sb.append("  FROM(														\n");
			
			sb.append(" SELECT ID.ID_SEQ                                                         \n");
			sb.append("        ,ID.MD_SEQ                                                        \n");
			sb.append("        ,ID.I_SEQ                                                     	  \n");
			sb.append("        ,ID.IT_SEQ                                                     	  \n");
			sb.append("        ,ID.ID_URL                                                        \n");
			sb.append("        ,ID.ID_TITLE                                                      \n");
			//sb.append("        ,ID.ID_URL                                                        \n");
			sb.append("        ,ID.SG_SEQ                                                        \n");
			sb.append("        ,ID.S_SEQ                                                         \n");
			sb.append("        ,ID.MD_SITE_NAME                                                  \n");
			sb.append("        ,ID.MD_SITE_MENU                                                  \n");
			sb.append("        ,DATE_FORMAT(ID.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE           \n");
			sb.append("        ,DATE_FORMAT(ID.ID_REGDATE,'%Y-%m-%d %H:%i:%s') AS ID_REGDATE     \n");
			sb.append("        ,ID.ID_WRITTER                                                    \n");
			sb.append("        ,ID.ID_CONTENT                                                    \n");
			sb.append("        ,ID.ID_MAILYN                                                     \n");
			sb.append("        ,ID.ID_USEYN                                                      \n");
			sb.append("        ,ID.M_SEQ                                                         \n");
			//sb.append("        ,ID.MD_SAME_CT                                                    \n");
			sb.append("        ,ID.MD_TYPE                                                    	 \n");
			//sb.append("        ,(SELECT M_NAME FROM MEMBER WHERE M_SEQ = ID.M_SEQ) AS M_NAME  	 \n");
			sb.append("        , ID.L_ALPHA 													 \n");
			sb.append("        , ID.ID_REPORTYN 													 \n");
			sb.append("        , ID.MD_PSEQ 													 \n");
			
			sb.append("        , ID.K_XP 														 \n");
			sb.append("        , ID.K_YP 														 \n");
			sb.append("        , ID.MEDIA_INFO 													 \n");
			sb.append("        , ID.F_NEWS 													 \n");

			
			sb.append("   FROM ( 													 \n");
			
			
			sb.append("SELECT ID.* FROM ISSUE_DATA ID 													 \n");
			
			
			if(!typeCode.equals("")&&typeCodeOrder.equals("")){
				sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			
			sb.append(" WHERE 1=1										      \n");
			if(!reportyn.equals("")){
				sb.append("   AND ID.ID_REPORTYN = '"+reportyn+"' 						      \n");
			}
			
			if( !id_seq.equals("") ) {
				sb.append("		AND	ID.ID_SEQ IN ("+id_seq+")  									  \n");
			}
			if( !i_seq.equals("") ) {
				sb.append("		AND	ID.I_SEQ IN ("+i_seq+")  									\n");
			}
			if( !it_seq.equals("") ) {
				sb.append("		AND	ID.IT_SEQ IN ("+it_seq+")  									\n");
			}
			if( !md_seq.equals("") ) {
				sb.append("		AND	ID.MD_SEQ in ("+md_seq+") 			                          \n");
			}
			if( !schKey.equals("") ) {
				
				String[] arrSchKey = schKey.split(" ");
				
				for(int i =0; i < arrSchKey.length; i++){
					sb.append("		AND	ID.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  							\n");
				}
				
			}
			if( !schSdate.equals("") && !schEdate.equals("")) {
				if(!schStime.equals("") && !schEtime.equals("")){
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            		\n");	
					}
				}else{
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            		\n");	
					}
				}
			}			
			if (!useYN.equals("")) {
				sb.append(" 	AND ID.ID_USEYN = '"+useYN+"'									      \n");
			}
			if (!language.equals("")) {
				sb.append(" 	AND ID.L_ALPHA = '"+language+"'									    \n");
			}
			
			//최초기사일 경우
			if(!f_news.equals("")){
				sb.append(" AND ID.F_NEWS = '"+f_news+"'		\n");
			}
			if(parents.equals("Y")){
				//모기사일경우
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}
			sb.append(" ORDER BY MD_SEQ ASC		\n");
			
			sb.append("   )ID 																 \n");
			sb.append("   GROUP BY ID.MD_PSEQ 												 \n");
			sb.append("   ORDER BY ID.ID_SEQ DESC 											 \n");
			if(pageNum>0){
			sb.append(" LIMIT   "+startNum+","+endNum+"                                       \n");
			}			
			
			sb.append("  )A                                                                    \n");
			
			
			
			
			
			
			
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());
			
			tmpId_seq ="";
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setId_writter(rs.getString("ID_WRITTER"));
				idBean.setId_content(rs.getString("ID_CONTENT"));
				idBean.setId_regdate(rs.getString("ID_REGDATE"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setI_seq(rs.getString("I_SEQ"));
				idBean.setIt_seq(rs.getString("IT_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setMd_site_menu(rs.getString("MD_SITE_MENU"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setId_mailyn(rs.getString("ID_MAILYN"));
				idBean.setId_useyn(rs.getString("ID_USEYN"));
				idBean.setM_seq(rs.getString("M_SEQ"));
				idBean.setM_name(rs.getString("M_NAME"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setL_alpha(rs.getString("L_ALPHA"));
				idBean.setId_reportyn(rs.getString("ID_REPORTYN"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setK_xp(rs.getString("K_XP"));
				idBean.setK_yp(rs.getString("K_YP"));
				idBean.setMedia_info(rs.getString("MEDIA_INFO"));
				idBean.setF_news(rs.getString("F_NEWS"));
				idBean.setMd_same_ct_check(rs.getString("MD_SAME_CT_CHECK"));
				
				issueDataList.add(idBean);	
				if(tmpId_seq.equals("")) tmpId_seq += rs.getString("ID_SEQ");
				else tmpId_seq += "," + rs.getString("ID_SEQ");
			}
			
			
			
			String prevI_seq = null;			
			if( tmpId_seq.length() > 0 ) 
			{
				//이슈에 이슈코드어레이 추가
				ArrayList ArrIcList = null;
				ArrIcList = new ArrayList();	    			    	
		    	
				rs.close();
				sb = new StringBuffer();				
				sb.append("SELECT IDC.ID_SEQ 								        	\n");
				sb.append("		  ,IC.IC_SEQ							                \n");
				sb.append("		  ,IDC.IC_TYPE							                \n");
				sb.append(" 	  ,IDC.IC_CODE 								            \n");
				sb.append(" 	  ,IC.IC_PTYPE							                \n");
				sb.append(" 	  ,IC.IC_PCODE   						                \n");
				sb.append(" 	  ,DATE_FORMAT(IC.IC_REGDATE,'%Y-%m-%d %H:%i:%s') IC_REGDATE		\n");				
				sb.append(" 	  ,IC_DESCRIPTION								        \n");				
				sb.append(" 	  ,IC.M_SEQ								                \n");
				sb.append("       ,IC.IC_NAME 									        \n");
				sb.append("FROM ISSUE_CODE IC, ISSUE_DATA_CODE IDC	 					\n");
				sb.append("WHERE	IDC.ID_SEQ IN ("+tmpId_seq+")  						\n");
				sb.append("         AND IC.IC_CODE = IDC.IC_CODE   						\n");
				sb.append("         AND IC.IC_TYPE = IDC.IC_TYPE	 					\n");
				sb.append("ORDER BY IDC.ID_SEQ DESC					 					\n");				
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				
				while(rs.next()){
	        		
	        		if(prevI_seq!=null && !prevI_seq.equals(rs.getString("ID_SEQ")) )
	        		{
	        			addIssueCode(issueDataList,prevI_seq,ArrIcList);        			
	        			ArrIcList = new ArrayList();
	        		}        		
	        		
	        		prevI_seq = rs.getString("ID_SEQ");
	        		
	        		icBean = new IssueCodeBean();
	        		icBean.setIc_seq(rs.getInt("IC_SEQ"));
	        		icBean.setIc_type(rs.getInt("IC_TYPE"));
	        		icBean.setIc_code(rs.getInt("IC_CODE"));
	        		icBean.setIc_ptype(rs.getInt("IC_PTYPE"));
	        		icBean.setIc_pcode(rs.getInt("IC_PCODE"));
	        		icBean.setIc_regdate(rs.getString("IC_REGDATE"));
	        		icBean.setIc_description(rs.getString("IC_DESCRIPTION"));
	        		icBean.setM_seq(rs.getString("M_SEQ"));
	        		icBean.setIc_name(rs.getString("IC_NAME"));
	        		
	        		ArrIcList.add(icBean);
	        	}
				addIssueCode(issueDataList,prevI_seq,ArrIcList);
				
				
				//이슈에 이슈코멘트어레이 추가					    	
				rs = null;
				prevI_seq = null;	
				sb = new StringBuffer();
				ArrayList arrIcmList = new ArrayList();
				
				sb.append("SELECT IM_SEQ, IM_COMMENT, DATE_FORMAT(IM_DATE,'%Y-%m-%d %H:%i:%s') IM_DATE, ID_SEQ \n");
				sb.append("FROM ISSUE_COMMENT	 						                           \n");
				sb.append("WHERE ID_SEQ IN ("+tmpId_seq+")  					                       \n");
				sb.append("ORDER BY IM_SEQ DESC                                 				   \n");				
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				
				while(rs.next()){
	        		
	        		if(prevI_seq!=null && !prevI_seq.equals(rs.getString("ID_SEQ")) )
	        		{
	        			addIssueCode(issueDataList,prevI_seq,arrIcmList);        			
	        			arrIcmList = new ArrayList();
	        		}        		
	        		
	        		prevI_seq = rs.getString("ID_SEQ");
	        		
	        		icmBean = new IssueCommentBean();
	        		icmBean.setIm_seq(rs.getString("IM_SEQ"));
	        		icmBean.setIm_comment(rs.getString("IM_COMMENT"));
	        		icmBean.setIm_date(rs.getString("IM_DATE"));       			        		
	        		
	        		arrIcmList.add(icmBean);
	        	}
				addIssueComment(issueDataList,prevI_seq,arrIcmList);
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
    	
    	return issueDataList;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public ArrayList getSourceData(String md_pseq, String ic_seq, String sdate, String stime, String edate, String etime) {
		
		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			
			sb.append("SELECT A.ID_SEQ														\n");
			sb.append("     , A.MD_SEQ														\n");
			sb.append("     , A.I_SEQ														\n");
			sb.append("     , A.IT_SEQ														\n");
			sb.append("     , A.ID_URL														\n");
			sb.append("     , A.ID_TITLE													\n");
			sb.append("     , A.SG_SEQ														\n");
			sb.append("     , A.S_SEQ														\n");
			sb.append("     , A.MD_SITE_NAME												\n");
			sb.append("     , A.MD_SITE_MENU												\n");
			sb.append("     , DATE_FORMAT(A.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE			\n");
			sb.append("     , DATE_FORMAT(A.ID_REGDATE,'%Y-%m-%d %H:%i:%s') AS ID_REGDATE	\n");
			sb.append("     , A.ID_WRITTER													\n");
			sb.append("     , A.ID_CONTENT													\n");
			sb.append("     , A.ID_MAILYN													\n");
			sb.append("     , A.ID_USEYN													\n");
			sb.append("     , A.M_SEQ														\n");
			sb.append("     , A.MD_TYPE														\n");
			sb.append("     , A.L_ALPHA														\n");
			sb.append("     , A.ID_REPORTYN													\n");
			sb.append("     , A.MD_PSEQ														\n");
			sb.append("     , A.K_XP														\n");
			sb.append("     , A.K_YP														\n");
			sb.append("     , A.MEDIA_INFO													\n");
			sb.append("     , A.F_NEWS														\n");
			sb.append("  FROM ISSUE_DATA A													\n");
			sb.append("     , IC_S_RELATION B												\n");
			sb.append("     , ISSUE_CODE C													\n");
			sb.append(" WHERE A.MD_PSEQ = "+md_pseq+"										\n");
			sb.append("   AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'		\n");
			sb.append("   AND A.SG_SEQ = B.S_SEQ											\n");
			sb.append("   AND B.IC_SEQ = C.IC_SEQ											\n");
			sb.append("   AND C.IC_CODE > 0													\n");
			sb.append("   AND C.IC_USEYN = 'Y' 												\n");
			sb.append("   AND C.IC_CODE = "+ic_seq+"										\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				idBean = new IssueDataBean();

				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setI_seq(rs.getString("I_SEQ"));
				idBean.setIt_seq(rs.getString("IT_SEQ"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setMd_site_menu(rs.getString("MD_SITE_MENU"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_regdate(rs.getString("ID_REGDATE"));
				idBean.setId_writter(rs.getString("ID_WRITTER"));
				idBean.setId_content(rs.getString("ID_CONTENT"));
				idBean.setId_mailyn(rs.getString("ID_MAILYN"));
				idBean.setId_useyn(rs.getString("ID_USEYN"));
				idBean.setM_seq(rs.getString("M_SEQ"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setL_alpha(rs.getString("L_ALPHA"));
				idBean.setId_reportyn(rs.getString("ID_REPORTYN"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setK_xp(rs.getString("K_XP"));
				idBean.setK_yp(rs.getString("K_YP"));
				idBean.setMedia_info(rs.getString("MEDIA_INFO"));
				idBean.setF_news(rs.getString("F_NEWS"));

				arrResult.add(idBean);
			}
			sb = null;
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
    
    
    
    public ArrayList getDailyIssueWeather(String sDate,String sTime, String eDate,String eTime, String siteGroup, String type1) {
		
		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("SELECT A1.IC_CODE, A1.IC_NAME, IFNULL(A2.CT1,0) AS CT1, IFNULL(A2.CT2,0) AS CT2, IFNULL(A2.CT3,0) AS CT3, IFNULL(CT1+CT2,0) AS TOTAL, IFNULL(CASE CT1 WHEN 0 THEN '0.00' ELSE FORMAT(CT1/(CT1+CT2)*100,'####.##') END,0) AS CT1_PER	\n");
			sb.append("FROM (	                                                                                                                                                                                                                                                                               	\n");
			sb.append("		SELECT *                                                                                                                                                                                                                                                                  	\n");
			sb.append("		FROM ISSUE_CODE IC3                                                                                                                                                                                                                                        	\n");
			sb.append("		WHERE IC3.IC_TYPE = 4 AND IC3.IC_CODE != 0                                                                                                                                                                                                               	\n");
			sb.append("	 )A1 LEFT OUTER JOIN                                                                                                                                                                                                                                                        	\n");
			sb.append("	 (                                                                                                                                                                                                                                                                                     	\n");
			sb.append("	  SELECT IFNULL(SUM(CASE IC.IC_CODE WHEN 1 THEN 1 END), 0) AS CT1, IFNULL(SUM(CASE IC.IC_CODE WHEN 2 THEN 1 END), 0) AS CT2,                        		       	\n");
			sb.append("		    IFNULL(SUM(CASE IC.IC_CODE WHEN 3 THEN 1 END), 0) AS CT3, IDC2.IC_CODE                                                                                                                  		       	\n");
			sb.append("			FROM ISSUE_DATA ID, SG_S_RELATION SD, ISSUE_DATA_CODE IDC1, ISSUE_DATA_CODE IDC2, ISSUE_CODE IC 	                               	\n");
			sb.append("			WHERE ID_REGDATE BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"'  \n");
			sb.append("			AND ID.S_SEQ = SD.S_SEQ                                                                                                                                                                        		                        		\n");
			sb.append("			AND ID.ID_SEQ = IDC1.ID_SEQ                                                                                                                                                                               		                                		\n");
			sb.append("			AND ID.ID_SEQ = IDC2.ID_SEQ                                                                                                                                                                                                                       	       	\n");
			sb.append("			AND IDC1.IC_TYPE = IC.IC_TYPE AND IDC1.IC_CODE = IC.IC_CODE                                                                                                                                                                      	       	\n");
			sb.append("			AND IDC1.IC_TYPE = 1 AND IDC1.IC_CODE IN ("+type1+")                                                                                                                                                                                      	       	\n");
			sb.append("			AND IDC2.IC_TYPE = 4                                                                                                                                                                                                                                 	       	\n");
			//sb.append("			AND IDC3.IC_TYPE = 5                                                                                                                          		                                                                                        		\n");
			sb.append("			AND SD.SG_SEQ IN ("+siteGroup+")                                                                                                                                                                                                                   	       	\n");
			sb.append("			GROUP BY IDC2.IC_CODE                                                                                                                                                                                                                                   	\n");
			sb.append("	 )A2 ON (A1.IC_CODE = A2.IC_CODE)                                                                                                                                                                                                                                     	\n");
			sb.append("ORDER BY A1.IC_CODE	                                                                                                                                                                                                                                                       	\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
					String[] result = new String[5];
					result[0] = rs.getString("CT1");
					result[1] = rs.getString("CT2");
					result[2] = rs.getString("IC_NAME");
					result[3] = rs.getString("CT1_PER");
					result[4] = rs.getString("TOTAL");
					arrResult.add(result);
			}
			sb = null;
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
    
    
	/**
     * 해당이슈데이터의  코드 추가
     * @param IssueDataBean
     * @return
     */   
    private ArrayList addIssueCode(ArrayList IssueList, String id_seq, ArrayList issueCodeList) {
    	int i=0;
    	for(i=0; i<IssueList.size(); i++) {
    		IssueDataBean idBean = (IssueDataBean) IssueList.get(i);
    		if (idBean.getId_seq().equals(id_seq)) {
    			idBean.setArrCodeList(issueCodeList);
    			IssueList.set(i, idBean);
    		}
    	}
    	return IssueList;
    }
    
	/**
     * 해당이슈데이터의  Comment 추가
     * @param IssueDataBean
     * @return
     */
    private ArrayList addIssueComment(ArrayList IssueList, String id_seq, ArrayList issueCommentList) {
    	int i=0;
    	for(i=0; i<IssueList.size(); i++) {
    		IssueDataBean idBean = (IssueDataBean) IssueList.get(i);
    		if (idBean.getId_seq().equals(id_seq)) {
    			idBean.setArrCommentList(issueCommentList);
    			IssueList.set(i, idBean);
    		}
    	}
    	return IssueList;
    }
    
    //이슈 불러오기
    public ArrayList getActiveIssueTw(String sDate, String eDate, String gSn){
		ArrayList issueList = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			DateUtil du = new DateUtil();
			
			sb = new StringBuffer();
			
			sb.append("SELECT I.I_SEQ                                                           \n");
			sb.append("     , I.I_TITLE	                                                        \n");			                          
			sb.append("  FROM ISSUE I                                                           \n");
			sb.append("     , ISSUE_DATA ID                                                     \n");
			sb.append("	    , ISSUE_TITLE IT	                                                \n");	
			sb.append(" WHERE I.I_USEYN = 'Y'		                                            \n");								                  
			sb.append("   AND IT.IT_USEYN = 'Y'			                                        \n");							                  
			sb.append("   AND ID.S_SEQ = '"+gSn+"'                                              \n");					                  
			sb.append("   AND ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'  \n");						
			sb.append("   AND ID.IT_SEQ = IT.IT_SEQ								                \n");
			sb.append("   AND IT.I_SEQ = I.I_SEQ									            \n");
			sb.append(" GROUP BY I.I_SEQ, I.I_TITLE												\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			issueList = new ArrayList();
			
			while(rs.next()){
				
				isBean =  new IssueBean();
				isBean.setI_seq(rs.getString("I_SEQ"));
				isBean.setI_title(rs.getString("I_TITLE"));
				isBean.setI_regdate("");
				isBean.setM_seq("");
				isBean.setI_count("");
	        	
	        	issueList.add(isBean);
			}

			if(issueList==null || issueList.size()==0){
				sb = new StringBuffer();
				
				sb.append("SELECT I.I_SEQ               \n");
				sb.append("     , I.I_TITLE             \n");
				sb.append("		, I.I_REGDATE           \n");
				sb.append("		, I.M_SEQ               \n");
				sb.append("		, IT.IT_SEQ             \n");
				sb.append("		, IT.IT_TITLE	        \n");	
				sb.append("  FROM ISSUE       I         \n");
				sb.append("	    , ISSUE_TITLE IT	    \n");                                  
				sb.append("	WHERE I.I_SEQ = IT.I_SEQ	\n");						                                  												                                  
				sb.append("	ORDER BY IT.IT_SEQ DESC     \n");
				sb.append("	LIMIT 0,1                   \n");
				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				issueList = new ArrayList();
				while(rs.next()){
					
					isBean =  new IssueBean();
					isBean.setI_seq(rs.getString("I_SEQ"));
					isBean.setI_title(rs.getString("I_TITLE"));
					isBean.setI_regdate(rs.getString("I_REGDATE"));
					isBean.setM_seq(rs.getString("M_SEQ"));
					isBean.setI_count("");
		        	
		        	issueList.add(isBean);
				}
				
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return issueList;
	}
    
    //주제 불러오기
	public ArrayList getActiveTitleTw(String sDate, String eDate, String gSn){
		ArrayList issueList = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			DateUtil du = new DateUtil();
			
			sb = new StringBuffer();

			sb.append("SELECT IT.I_SEQ                                                          \n");
			sb.append("	    , IT.IT_TITLE                                                       \n");
			sb.append("		, IT.IT_SEQ	                                                        \n");				         
			sb.append("  FROM ISSUE       I                                                     \n");
			sb.append("	    , ISSUE_DATA  ID                                                    \n");
			sb.append("	    , ISSUE_TITLE IT	                                                \n");	
			sb.append(" WHERE I.I_USEYN = 'Y'			                                        \n");					                   		
			sb.append("	  AND IT.IT_USEYN = 'Y'			                                        \n");					                  		
			sb.append("   AND ID.S_SEQ = '"+gSn+"'	                                    	    \n");			                  		
			sb.append("   AND ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'  \n");							
			sb.append("   AND ID.IT_SEQ = IT.IT_SEQ							                    \n");
			sb.append("   AND IT.I_SEQ = I.I_SEQ								                \n");
			sb.append(" GROUP BY IT.I_SEQ, IT.IT_TITLE, IT.IT_SEQ                               \n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			issueList = new ArrayList();
			IssueTitleBean itBean = null;
			while(rs.next()){
				itBean =  new IssueTitleBean();
				itBean.setIt_seq(rs.getString("IT_SEQ"));
	        	itBean.setIt_title(rs.getString("IT_TITLE"));
	        	itBean.setI_seq(rs.getString("I_SEQ"));
	        	
	        	issueList.add(itBean);
			}
			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return issueList;
	}
	
	public void reportNameUpdate(String ir_seq, String ir_title){
    	try{
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		sb = new StringBuffer();
    		
    		sb.append("UPDATE ISSUE_REPORT SET IR_TITLE = '"+ir_title+"' WHERE IR_SEQ = "+ir_seq+"\n");
    		
    		System.out.println(sb.toString());
    		pstmt = dbconn.createPStatement(sb.toString());
    		pstmt.executeUpdate();
    	}catch(SQLException e){
    		Log.writeExpt(e, sb.toString());
    	}catch(Exception e){
    		Log.writeExpt(e);
    	}finally{
    		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
    	}
    }
	
	public ArrayList getIssueTitle(ArrayList pArray, String iSeq){
		ArrayList result = new ArrayList();
		IssueTitleBean itb = null;
		for (int i = 0; i < pArray.size(); i++) {
			itb = (IssueTitleBean)pArray.get(i);
			if(itb.getI_seq().equals(iSeq)){
				result.add(itb);
			}
			
		}
		return result;
	}
	
	//사이트 그룹 정렬해서 가져오기
	public ArrayList getSiteGroup_order(){
		ArrayList issueList = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT A.IC_CODE				\n");
			sb.append("     , A.IC_NAME				\n");
			sb.append("  FROM ISSUE_CODE A 			\n");
			sb.append("     , IC_S_RELATION B		\n");
			sb.append(" WHERE A.IC_SEQ = B.IC_SEQ	\n");
			sb.append("   AND A.IC_TYPE = 6 		\n");
			sb.append("   AND A.IC_CODE > 0 		\n");
			sb.append("   AND A.IC_USEYN = 'Y'		\n");
			//sb.append("   AND IC_CODE <> 8			\n");
			sb.append(" ORDER BY B.IC_ORDER			\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			issueList = new ArrayList();
			String[] siteGroup = null;
			while(rs.next()){
				
				siteGroup = new String[2];
				
				siteGroup[0] = rs.getString("IC_CODE");
				siteGroup[1] = rs.getString("IC_NAME");
	        	
	        	issueList.add(siteGroup);
			}
			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return issueList;
	}
	
	//사이트 그룹 정렬해서 가져오기
	public ArrayList getSiteGroup_order2(){
		ArrayList issueList = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT A.IC_CODE				\n");
			sb.append("     , A.IC_NAME				\n");
			sb.append("     , B.IC_ORDER			\n");
			sb.append("  FROM ISSUE_CODE A 			\n");
			sb.append("     , IC_S_RELATION B		\n");
			sb.append(" WHERE A.IC_SEQ = B.IC_SEQ	\n");
			sb.append("   AND A.IC_TYPE = 6 		\n");
			sb.append("   AND A.IC_CODE > 0 		\n");
			sb.append("   AND A.IC_USEYN = 'Y'		\n");
			//sb.append("   AND IC_CODE <> 8			\n");
			sb.append(" ORDER BY B.IC_ORDER			\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			issueList = new ArrayList();
			String[] siteGroup = null;
			while(rs.next()){
				
				siteGroup = new String[3];
				
				siteGroup[0] = rs.getString("IC_CODE");
				siteGroup[1] = rs.getString("IC_NAME");
				siteGroup[2] = rs.getString("IC_ORDER");
	        	
	        	issueList.add(siteGroup);
			}
			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return issueList;
	}
	
	public int GetSearchSourceOrder(ArrayList data, String icCode ){
		int result = 0;
		String[] row_data = null;
		for(int i = 0; i < data.size(); i++){
			row_data = (String[])data.get(i);
			if(row_data[0].equals(icCode)){
				result = Integer.parseInt(row_data[2]);
				break;
			}
		}
		return result;
	}
    
	
	public ArrayList TrendPieChartData(String ic_type, String ir_sdate, String ir_stime, String ir_edate, String ir_etime){
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
    	try{
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		sb = new StringBuffer();
    		
    		
    		sb.append("SELECT IC.IC_TYPE, IC.IC_CODE, IC.IC_NAME AS CATEGORY, IFNULL(T.CNT, 0) AS CNT\n");
    		sb.append("FROM\n");
    		sb.append("(SELECT * FROM ISSUE_CODE WHERE IC_TYPE = "+ic_type+" AND IC_USEYN = 'Y' AND IC_CODE != 0) IC\n");
    		//sb.append("LEFT OUTER JOIN\n");
    		sb.append("INNER JOIN\n");
    		sb.append("(\n");
    		sb.append("		SELECT IDC.IC_CODE, COUNT(*) AS CNT\n");
    		sb.append("		FROM ISSUE_DATA ID\n");
    		sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
    		sb.append("		ON IDC.IC_TYPE = "+ic_type+"\n");
    		sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
    		sb.append("		INNER JOIN ISSUE_DATA_CODE IDC2\n");
    		sb.append("		ON IDC2.IC_TYPE = 4 AND IDC2.IC_CODE IN (1,2)\n");
    		sb.append("		AND ID.ID_SEQ = IDC2.ID_SEQ\n");
    		sb.append("		WHERE ID.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'\n");
    		sb.append("		GROUP BY IDC.IC_CODE\n");
    		sb.append(") T\n");
    		sb.append("ON IC.IC_CODE = T.IC_CODE\n");
    		sb.append("ORDER BY IC.IC_TYPE, IC.IC_CODE\n");
    		
    		System.out.println(sb.toString());
    		pstmt = dbconn.createPStatement(sb.toString());
    		rs = pstmt.executeQuery();
    		while(rs.next()){
    			dataMap = new HashMap();
    			dataMap.put("CATEGORY", rs.getString("CATEGORY"));
    			dataMap.put("CNT", rs.getString("CNT"));
    			result.add(dataMap);
    		}
    	}catch(SQLException e){
    		Log.writeExpt(e, sb.toString());
    	}catch(Exception e){
    		Log.writeExpt(e);
    	}finally{
    		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
    	}
    	return result;
    }
	
	public ArrayList BarChartData(String ic_type, String ir_sdate, String ir_stime, String ir_edate, String ir_etime){
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			
			sb.append("SELECT B.IC_TYPE, B.IC_CODE, B.IC_NAME AS CATEGORY, IFNULL(A.CNT,0) AS CNT  FROM (\n");
			
			
			sb.append("SELECT IDC.IC_TYPE, IDC.IC_CODE, IC.IC_NAME AS CATEGORY, COUNT(*) AS CNT\n");
			sb.append("FROM ISSUE_DATA ID\n");
			sb.append("INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("ON IDC.IC_TYPE = "+ic_type+"\n");
			sb.append("AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("INNER JOIN ISSUE_CODE IC\n");
			sb.append("ON IDC.IC_TYPE = IC.IC_TYPE AND IDC.IC_CODE = IC.IC_CODE\n");
			sb.append("INNER JOIN ISSUE_DATA_CODE IDC2\n");
			sb.append("ON IDC2.IC_TYPE = 4 AND IDC2.IC_CODE IN (1,2)\n");
			sb.append("AND ID.ID_SEQ = IDC2.ID_SEQ\n");
			sb.append("WHERE ID.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'\n");
			sb.append("GROUP BY IDC.IC_TYPE, IDC.IC_CODE, IC.IC_NAME\n");
			sb.append("ORDER BY IDC.IC_TYPE, IDC.IC_CODE\n");
			
			
			sb.append(")A RIGHT OUTER JOIN (SELECT A.IC_TYPE, A.IC_CODE\n");
			sb.append(", A.IC_NAME\n");
			sb.append(", B.IC_ORDER\n");
			sb.append("FROM ISSUE_CODE A \n");
			sb.append(", IC_S_RELATION B\n");
			sb.append("WHERE A.IC_SEQ = B.IC_SEQ\n");
			sb.append("AND A.IC_TYPE = 6 \n");
			sb.append("AND A.IC_CODE > 0 \n");
			sb.append("AND A.IC_USEYN = 'Y'\n");
			sb.append(") B ON A.IC_CODE = B.IC_CODE ORDER BY B.IC_ORDER ASC\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			int sumPotal = 0;
			
			while(rs.next()){
				
				if(rs.getString("IC_CODE").equals("6") || rs.getString("IC_CODE").equals("8")){
					sumPotal += rs.getInt("CNT"); 
				}
			}
			
			rs.beforeFirst();
			
			while(rs.next()){
				
				dataMap = new HashMap();
				
				
				if(!rs.getString("IC_CODE").equals("8")){
					dataMap.put("CATEGORY", rs.getString("CATEGORY"));
					if(rs.getString("IC_CODE").equals("6")){
				
						dataMap.put("CNT", Integer.toString(sumPotal));
					}else{
						dataMap.put("CNT", rs.getString("CNT"));
					}
					result.add(dataMap);
				}
				
				
			}
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	public ArrayList channelTrendChartData(String ic_type, String ir_sdate, String ir_stime, String ir_edate, String ir_etime){
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			
			sb.append("SELECT IC.IC_CODE, IC.IC_NAME AS CATEGORY\n");
			sb.append(", IFNULL(SUM(T.P_CNT), 0) AS PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) AS NAME1\n");
			sb.append(", IFNULL(SUM(T.N_CNT), 0) AS NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) AS NAME2\n");
			sb.append(", IFNULL(SUM(T.E_CNT), 0) AS ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) AS NAME3\n");
			sb.append("FROM\n");
			sb.append("(SELECT * FROM ISSUE_CODE WHERE IC_TYPE = "+ic_type+" AND IC_USEYN = 'Y' AND IC_CODE != 0) IC\n");
			//sb.append("LEFT OUTER JOIN\n");
			sb.append("INNER JOIN\n");
			sb.append("(\n");
			sb.append("		SELECT IDC1.IC_CODE\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 1 THEN SUM(1) END, 0) AS P_CNT\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 2 THEN SUM(1) END, 0) AS N_CNT\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 3 THEN SUM(1) END, 0) AS E_CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON IDC.IC_TYPE = 9	\n");
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC1\n");
			sb.append("		ON IDC1.IC_TYPE = "+ic_type+"\n");
			sb.append("		AND IDC1.ID_SEQ = ID.ID_SEQ\n");
			sb.append("INNER JOIN ISSUE_DATA_CODE IDC2\n");
			sb.append("ON IDC2.IC_TYPE = 4 AND IDC2.IC_CODE IN (1,2)\n");
			sb.append("AND IDC2.ID_SEQ = ID.ID_SEQ\n");
			//sb.append("		WHERE DATE_FORMAT(ID.MD_DATE, '%Y%m%d') = '"+date+"'\n");
			sb.append("		WHERE ID.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'	\n");
			sb.append("		GROUP BY IDC1.IC_CODE, IDC.IC_CODE\n");
			sb.append(") T\n");
			sb.append("ON IC.IC_CODE = T.IC_CODE\n");
			sb.append("GROUP BY IC.IC_CODE, IC.IC_NAME\n");
			sb.append("ORDER BY IC.IC_CODE\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				
					dataMap = new HashMap();
					dataMap.put("CATEGORY", rs.getString("CATEGORY"));
					dataMap.put("PCNT", rs.getString("PCNT"));
					dataMap.put("NAME1", rs.getString("NAME1"));
					dataMap.put("NCNT", rs.getString("NCNT"));
					dataMap.put("NAME2", rs.getString("NAME2"));
					dataMap.put("ECNT", rs.getString("ECNT"));
					dataMap.put("NAME3", rs.getString("NAME3"));
					result.add(dataMap);
				
			}
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	public ArrayList channelTrendChartData_TYPE15(String ic_type, String ir_sdate, String ir_stime, String ir_edate, String ir_etime){
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			
			sb.append("SELECT IC.IC_CODE, IC.IC_NAME AS CATEGORY\n");
			sb.append(", IFNULL(SUM(T.P_CNT), 0) AS PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) AS NAME1\n");
			sb.append(", IFNULL(SUM(T.N_CNT), 0) AS NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) AS NAME2\n");
			sb.append(", IFNULL(SUM(T.E_CNT), 0) AS ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) AS NAME3\n");
			sb.append("FROM\n");
			sb.append("(SELECT * FROM ISSUE_CODE WHERE IC_TYPE = "+ic_type+" AND IC_USEYN = 'Y' AND IC_CODE != 0) IC\n");
			//sb.append("LEFT OUTER JOIN\n");
			sb.append("INNER JOIN\n");
			sb.append("(\n");
			sb.append("		SELECT IDC1.IC_CODE\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 1 THEN SUM(1) END, 0) AS P_CNT\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 2 THEN SUM(1) END, 0) AS N_CNT\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 3 THEN SUM(1) END, 0) AS E_CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON IDC.IC_TYPE = 9	\n");
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC1\n");
			sb.append("		ON IDC1.IC_TYPE = "+ic_type+"\n");
			sb.append("		AND IDC1.ID_SEQ = ID.ID_SEQ\n");
			sb.append("INNER JOIN ISSUE_DATA_CODE IDC2\n");
			sb.append("ON IDC2.IC_TYPE = 4 AND IDC2.IC_CODE IN (1,2)\n");
			sb.append("AND IDC2.ID_SEQ = ID.ID_SEQ\n");
			//sb.append("		WHERE DATE_FORMAT(ID.MD_DATE, '%Y%m%d') = '"+date+"'\n");
			sb.append("		WHERE ID.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'	\n");
			sb.append("		GROUP BY IDC1.IC_CODE, IDC.IC_CODE\n");
			sb.append(") T\n");
			sb.append("ON IC.IC_CODE = T.IC_CODE\n");
			sb.append("GROUP BY IC.IC_CODE, IC.IC_NAME\n");
			sb.append("ORDER BY IC.IC_CODE\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			StatisticsSuperBean superBean = new StatisticsSuperBean();
			StatisticsSuperBean.dailyChart[] childBean = new StatisticsSuperBean.dailyChart[9]; 
			
				childBean[0] = superBean.new dailyChart(); 
				childBean[0].setCategory("경영진");
				childBean[1] = superBean.new dailyChart(); 
				childBean[1].setCategory("광고/홍보");
				childBean[2] = superBean.new dailyChart(); 
				childBean[2].setCategory("인사/노무");
				childBean[3] = superBean.new dailyChart(); 
				childBean[3].setCategory("경제/일반");
				childBean[4] = superBean.new dailyChart(); 
				childBean[4].setCategory("해외/투자");
				childBean[5] = superBean.new dailyChart(); 
				childBean[5].setCategory("사회공헌");
				childBean[6] = superBean.new dailyChart(); 
				childBean[6].setCategory("제품/기술");
				childBean[7] = superBean.new dailyChart(); 
				childBean[7].setCategory("환경/재해");
				childBean[8] = superBean.new dailyChart(); 
				childBean[8].setCategory("행사/문화");
			
			while(rs.next()){
				if(rs.getString("IC_CODE").equals("2") || rs.getString("IC_CODE").equals("3")){
					childBean[0].setAddPcnt(rs.getInt("PCNT"));
					childBean[0].setAddNcnt(rs.getInt("NCNT"));
					childBean[0].setAddEcnt(rs.getInt("ECNT"));
				}
				
				if(rs.getString("IC_CODE").equals("10")){
					childBean[1].setAddPcnt(rs.getInt("PCNT"));
					childBean[1].setAddNcnt(rs.getInt("NCNT"));
					childBean[1].setAddEcnt(rs.getInt("ECNT"));
				}
				
				if(rs.getString("IC_CODE").equals("6") || rs.getString("IC_CODE").equals("7")){
					childBean[2].setAddPcnt(rs.getInt("PCNT"));
					childBean[2].setAddNcnt(rs.getInt("NCNT"));
					childBean[2].setAddEcnt(rs.getInt("ECNT"));
				}
				
				if(rs.getString("IC_CODE").equals("1")){
					childBean[3].setAddPcnt(rs.getInt("PCNT"));
					childBean[3].setAddNcnt(rs.getInt("NCNT"));
					childBean[3].setAddEcnt(rs.getInt("ECNT"));
				}
				
				if(rs.getString("IC_CODE").equals("4")){
					childBean[4].setAddPcnt(rs.getInt("PCNT"));
					childBean[4].setAddNcnt(rs.getInt("NCNT"));
					childBean[4].setAddEcnt(rs.getInt("ECNT"));
				}
				
				if(rs.getString("IC_CODE").equals("11")){
					childBean[5].setAddPcnt(rs.getInt("PCNT"));
					childBean[5].setAddNcnt(rs.getInt("NCNT"));
					childBean[5].setAddEcnt(rs.getInt("ECNT"));
				}
				
				if(rs.getString("IC_CODE").equals("5")){
					childBean[6].setAddPcnt(rs.getInt("PCNT"));
					childBean[6].setAddNcnt(rs.getInt("NCNT"));
					childBean[6].setAddEcnt(rs.getInt("ECNT"));
				}
				
				if(rs.getString("IC_CODE").equals("8") || rs.getString("IC_CODE").equals("9")){
					childBean[7].setAddPcnt(rs.getInt("PCNT"));
					childBean[7].setAddNcnt(rs.getInt("NCNT"));
					childBean[7].setAddEcnt(rs.getInt("ECNT"));
				}
				
				if(rs.getString("IC_CODE").equals("12")){
					childBean[8].setAddPcnt(rs.getInt("PCNT"));
					childBean[8].setAddNcnt(rs.getInt("NCNT"));
					childBean[8].setAddEcnt(rs.getInt("ECNT"));
				}
			
			}
			
			
			for(int i =0; i < childBean.length; i++){
				if(childBean[i].getPcnt() > 0 || childBean[i].getNcnt() > 0 || childBean[i].getEcnt() > 0){
					dataMap = new HashMap();
					dataMap.put("CATEGORY", childBean[i].getCategory());
					dataMap.put("PCNT", Integer.toString(childBean[i].getPcnt()));
					dataMap.put("NAME1", childBean[i].getName1());
					dataMap.put("NCNT", Integer.toString(childBean[i].getNcnt()));
					dataMap.put("NAME2", childBean[i].getName2());
					dataMap.put("ECNT", Integer.toString(childBean[i].getEcnt()));
					dataMap.put("NAME3", childBean[i].getName3());
					result.add(dataMap);
					
				}
			}
			
			/*
			while(rs.next()){
				
					dataMap = new HashMap();
					dataMap.put("CATEGORY", rs.getString("CATEGORY"));
					dataMap.put("PCNT", rs.getString("PCNT"));
					dataMap.put("NAME1", rs.getString("NAME1"));
					dataMap.put("NCNT", rs.getString("NCNT"));
					dataMap.put("NAME2", rs.getString("NAME2"));
					dataMap.put("ECNT", rs.getString("ECNT"));
					dataMap.put("NAME3", rs.getString("NAME3"));
					result.add(dataMap);
				
			}
			*/
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	
	public ArrayList channelTrendChartData_TEMP(String ic_type, String ir_sdate, String ir_stime, String ir_edate, String ir_etime){
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			
			System.out.println("메롱메롱메롱");
			
			sb.append("SELECT A.* FROM (\n");
			
			
			
			sb.append("SELECT IC.IC_CODE, IC.IC_NAME AS CATEGORY\n");
			sb.append(", IFNULL(SUM(T.P_CNT), 0) AS PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) AS NAME1\n");
			sb.append(", IFNULL(SUM(T.N_CNT), 0) AS NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) AS NAME2\n");
			sb.append(", IFNULL(SUM(T.E_CNT), 0) AS ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) AS NAME3\n");
			sb.append("FROM\n");
			sb.append("(SELECT * FROM ISSUE_CODE WHERE IC_TYPE = "+ic_type+" AND IC_USEYN = 'Y' AND IC_CODE != 0) IC\n");
			sb.append("LEFT OUTER JOIN\n");
			//sb.append("INNER JOIN\n");
			sb.append("(\n");
			sb.append("		SELECT IDC1.IC_CODE\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 1 THEN SUM(1) END, 0) AS P_CNT\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 2 THEN SUM(1) END, 0) AS N_CNT\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 3 THEN SUM(1) END, 0) AS E_CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON IDC.IC_TYPE = 9\n");
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC1\n");
			sb.append("		ON IDC1.IC_TYPE = "+ic_type+"\n");
			sb.append("		AND IDC1.ID_SEQ = ID.ID_SEQ\n");
			
			sb.append("INNER JOIN ISSUE_DATA_CODE IDC2\n");
			sb.append("ON IDC2.IC_TYPE = 4 AND IDC2.IC_CODE IN (1,2)\n");
			sb.append("AND IDC2.ID_SEQ = ID.ID_SEQ\n");
			
			//sb.append("		WHERE DATE_FORMAT(ID.MD_DATE, '%Y%m%d') = '"+date+"'\n");
			sb.append("		WHERE ID.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'	\n");
			sb.append("		GROUP BY IDC1.IC_CODE, IDC.IC_CODE\n");
			sb.append(") T\n");
			sb.append("ON IC.IC_CODE = T.IC_CODE\n");
			sb.append("GROUP BY IC.IC_CODE, IC.IC_NAME\n");
			sb.append("ORDER BY IC.IC_CODE\n");
			
			
			sb.append(")A,(SELECT A.IC_CODE\n");
			sb.append(", A.IC_NAME\n");
			sb.append(", B.IC_ORDER\n");
			sb.append("FROM ISSUE_CODE A \n");
			sb.append(", IC_S_RELATION B\n");
			sb.append("WHERE A.IC_SEQ = B.IC_SEQ\n");
			sb.append("AND A.IC_TYPE = 6 \n");
			sb.append("AND A.IC_CODE > 0 \n");
			sb.append("AND A.IC_USEYN = 'Y'\n");
			sb.append(") B WHERE A.IC_CODE = B.IC_CODE ORDER BY B.IC_ORDER ASC\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			int sumPotal_pcnt = 0;
			int sumPotal_ncnt = 0;
			int sumPotal_ecnt = 0;
			
			while(rs.next()){
				if(rs.getString("IC_CODE").equals("6") || rs.getString("IC_CODE").equals("8")){
					sumPotal_pcnt += rs.getInt("PCNT"); 
					sumPotal_ncnt += rs.getInt("NCNT"); 
					sumPotal_ecnt += rs.getInt("ECNT"); 
				}
			}
			
			rs.beforeFirst();
			
			while(rs.next()){
				
				if(!rs.getString("IC_CODE").equals("8")){
					
					if(rs.getString("IC_CODE").equals("6")){
						dataMap = new HashMap();
						dataMap.put("CATEGORY", rs.getString("CATEGORY"));
						dataMap.put("PCNT", Integer.toString(sumPotal_pcnt));
						dataMap.put("NAME1", rs.getString("NAME1"));
						dataMap.put("NCNT", Integer.toString(sumPotal_ncnt));
						dataMap.put("NAME2", rs.getString("NAME2"));
						dataMap.put("ECNT", Integer.toString(sumPotal_ecnt));
						dataMap.put("NAME3", rs.getString("NAME3"));
						result.add(dataMap);
					}else{
						dataMap = new HashMap();
						dataMap.put("CATEGORY", rs.getString("CATEGORY"));
						dataMap.put("PCNT", rs.getString("PCNT"));
						dataMap.put("NAME1", rs.getString("NAME1"));
						dataMap.put("NCNT", rs.getString("NCNT"));
						dataMap.put("NAME2", rs.getString("NAME2"));
						dataMap.put("ECNT", rs.getString("ECNT"));
						dataMap.put("NAME3", rs.getString("NAME3"));
						result.add(dataMap);
					}
					
					
				}
			}
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	public ArrayList channelTrendChartData_Keyword(String k_xp, String ir_sdate, String ir_stime, String ir_edate, String ir_etime){
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			
			
			sb.append("SELECT A.K_YP, A.K_VALUE AS CATEGORY																				\n");
			sb.append("     , IFNULL(SUM(B.P_CNT), 0) AS PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) AS NAME1	\n");
			sb.append("     , IFNULL(SUM(B.N_CNT), 0) AS NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) AS NAME2	\n");
			sb.append("     , IFNULL(SUM(B.E_CNT), 0) AS ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) AS NAME3	\n");
			sb.append("  FROM 																											\n");
			sb.append("       (SELECT K_YP, K_VALUE FROM KEYWORD_TMP WHERE K_XP = "+k_xp+" AND K_YP > 0 AND K_ZP = 0)A						\n");
			sb.append("        LEFT OUTER JOIN																							\n");
			sb.append("       (SELECT A.K_YP																							\n");
			sb.append("             , IFNULL(CASE B.IC_CODE WHEN 1 THEN SUM(1) END, 0) AS P_CNT											\n");
			sb.append("             , IFNULL(CASE B.IC_CODE WHEN 2 THEN SUM(1) END, 0) AS N_CNT											\n");
			sb.append("             , IFNULL(CASE B.IC_CODE WHEN 3 THEN SUM(1) END, 0) AS E_CNT											\n");
			sb.append("          FROM ISSUE_DATA_TEMP A																					\n");
			sb.append("             , ISSUE_DATA_CODE B																					\n");
			sb.append("         WHERE A.ID_SEQ = B.ID_SEQ																				\n");
			sb.append("           AND A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'						\n");
			sb.append("           AND B.IC_TYPE = 9																						\n");
			sb.append("           AND A.K_XP = "+k_xp+"																					\n");
			sb.append("         GROUP BY A.K_YP, B.IC_CODE)B ON A.K_YP = B.K_YP															\n");
			sb.append("         GROUP BY A.K_YP, A.K_VALUE  																			\n");
			sb.append("         ORDER BY A.K_YP																							\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				dataMap = new HashMap();
				dataMap.put("CATEGORY", rs.getString("CATEGORY"));
				dataMap.put("PCNT", rs.getString("PCNT"));
				dataMap.put("NAME1", rs.getString("NAME1"));
				dataMap.put("NCNT", rs.getString("NCNT"));
				dataMap.put("NAME2", rs.getString("NAME2"));
				dataMap.put("ECNT", rs.getString("ECNT"));
				dataMap.put("NAME3", rs.getString("NAME3"));
				result.add(dataMap);
			}
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	public ArrayList DailyReport1 (String id_seqs) {
		
		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();			
			
			sb.append("SELECT A.SG_SEQ																								\n");
			sb.append("     , A.MD_SITE_NAME																						\n");
			sb.append("     , A.ID_TITLE																							\n");
			sb.append("     , A.MEDIA_INFO																							\n");
			sb.append("     , A.IC_NAME																								\n");
			sb.append("     , A.MD_PSEQ																								\n");
			sb.append("     , A.ID_URL																								\n");
			sb.append("     , A.MD_DATE																								\n");
			sb.append("     , (SELECT COUNT(*)-1 FROM ISSUE_DATA WHERE MD_PSEQ = A.MD_PSEQ AND SG_SEQ = A.SG_SEQ)  AS SAME_CT		\n");
			sb.append("  FROM ( 																									\n");
			sb.append("        SELECT A.*																							\n");
			sb.append("             , IF(A.SG_SEQ = @SG_SEQ, @ROW:=@ROW+1, @ROW:=1) AS RANK											\n");
			sb.append("             , @SG_SEQ := A.SG_SEQ																			\n");
			sb.append("          FROM (   																							\n");
			sb.append("                SELECT A.SG_SEQ																				\n");
			sb.append("                     , A.MD_SITE_NAME																		\n");
			sb.append("                     , A.ID_TITLE																			\n");
			sb.append("                     , A.MEDIA_INFO																			\n");
			sb.append("                     , C.IC_NAME																				\n");
			sb.append("                     , A.MD_PSEQ																				\n");
			sb.append("                     , A.ID_URL																				\n");
			sb.append("                     , A.MD_DATE																				\n");
			sb.append("                  FROM ISSUE_DATA A																			\n");
			sb.append("                     , ISSUE_DATA_CODE B																		\n");
			sb.append("                     , ISSUE_CODE C																			\n");
			sb.append("                 WHERE A.MD_PSEQ IN (SELECT MD_PSEQ FROM ISSUE_DATA WHERE ID_SEQ IN ("+id_seqs+"))			\n");  
			sb.append("                   AND A.SG_SEQ IN (17,18,29)																\n");
			sb.append("                   AND A.ID_SEQ = B.ID_SEQ																	\n");
			sb.append("                   AND B.IC_TYPE = 12																		\n");
			sb.append("                   AND B.IC_TYPE = C.IC_TYPE																	\n");
			sb.append("                   AND B.IC_CODE = C.IC_CODE																	\n");
			sb.append("                   AND C.IC_USEYN = 'Y'																		\n");
			sb.append("              GROUP BY A.MD_PSEQ, A.SG_SEQ																	\n");
			sb.append("              ORDER BY SG_SEQ, MD_PSEQ																		\n");
			sb.append("             )A																								\n");
			sb.append("           , (SELECT @ROW:=0, @SG_SEQ:='') R																	\n");  
			sb.append("       )A																									\n");
			sb.append(" WHERE RANK <= 3																								\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				idBean = new IssueDataBean();

				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setMedia_info(rs.getString("MEDIA_INFO"));
				idBean.setTemp1(rs.getString("IC_NAME"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setMd_same_ct(rs.getString("SAME_CT"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				
				arrResult.add(idBean);
			}
			sb = null;
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	public ArrayList DailyReport2 (String ir_sdate, String ir_stime, String ir_edate, String ir_etime) {
		
		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();			
			 
			sb.append("SELECT A.*																										\n");
			sb.append("     , B.SG_NAME																									\n");
			sb.append("     , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 12) AS TYPE12															\n");
			sb.append("     , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 13) AS TYPE13															\n");
			sb.append("     , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 9) AS TYPE9																\n");
			sb.append("  FROM (																											\n");
			sb.append("        (SELECT SG_SEQ, A.ID_SEQ, A.MD_SITE_NAME, A.ID_TITLE	,A.ID_URL, MEDIA_INFO, A.MD_DATE					\n");  
			sb.append("              , (SELECT COUNT(*)-1 FROM ISSUE_DATA WHERE MD_PSEQ = A.MD_PSEQ AND SG_SEQ = A.SG_SEQ) AS SAME_CT	\n");
			sb.append("           FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C												\n");
			sb.append("          WHERE A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'					\n"); 
			sb.append("            AND A.SG_SEQ = 17 AND A.ID_SEQ = B.ID_SEQ AND B.IC_TYPE = 12 AND A.ID_SEQ = C.ID_SEQ AND C.IC_TYPE = 13 AND C.IC_CODE <> 1 AND A.MD_SEQ = A.MD_PSEQ										\n");
			sb.append("          ORDER BY B.IC_CODE ASC, SAME_CT DESC LIMIT 1)															\n");
			sb.append("        UNION 																									\n");
			sb.append("        (SELECT SG_SEQ, A.ID_SEQ, A.MD_SITE_NAME, A.ID_TITLE, A.ID_URL, MEDIA_INFO, A.MD_DATE					\n");  
			sb.append("              , (SELECT COUNT(*)-1 FROM ISSUE_DATA WHERE MD_PSEQ = A.MD_PSEQ AND SG_SEQ = A.SG_SEQ) AS SAME_CT	\n");
			sb.append("           FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C 												\n");
			sb.append("          WHERE A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'					\n"); 
			sb.append("            AND A.SG_SEQ = 18 AND A.ID_SEQ = B.ID_SEQ AND B.IC_TYPE = 12 AND A.ID_SEQ = C.ID_SEQ AND C.IC_TYPE = 13 AND C.IC_CODE <> 1 AND A.MD_SEQ = A.MD_PSEQ									\n");
			sb.append("          ORDER BY B.IC_CODE ASC, SAME_CT DESC LIMIT 1)															\n");
			sb.append("        UNION 																									\n");
			sb.append("        (SELECT SG_SEQ, A.ID_SEQ, A.MD_SITE_NAME, A.ID_TITLE, A.ID_URL, MEDIA_INFO, A.MD_DATE					\n");  
			sb.append("              , (SELECT COUNT(*)-1 FROM ISSUE_DATA WHERE MD_PSEQ = A.MD_PSEQ AND SG_SEQ = A.SG_SEQ) AS SAME_CT	\n");
			sb.append("           FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C 												\n");
			sb.append("          WHERE A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'					\n"); 
			sb.append("            AND A.SG_SEQ = 29 AND A.ID_SEQ = B.ID_SEQ AND B.IC_TYPE = 12 AND A.ID_SEQ = C.ID_SEQ AND C.IC_TYPE = 13 AND C.IC_CODE <> 1 AND A.MD_SEQ = A.MD_PSEQ										\n");
			sb.append("          ORDER BY B.IC_CODE ASC, SAME_CT DESC LIMIT 1)															\n");
			sb.append("        UNION 																									\n");
			sb.append("        (SELECT SG_SEQ, A.ID_SEQ, A.MD_SITE_NAME, A.ID_TITLE, A.ID_URL, MEDIA_INFO, A.MD_DATE					\n");  
			sb.append("              , (SELECT COUNT(*)-1 FROM ISSUE_DATA WHERE MD_PSEQ = A.MD_PSEQ AND SG_SEQ = A.SG_SEQ) AS SAME_CT	\n");
			sb.append("           FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C 												\n");
			sb.append("          WHERE A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'					\n"); 
			sb.append("            AND A.SG_SEQ = 19 AND A.ID_SEQ = B.ID_SEQ AND B.IC_TYPE = 12 AND A.ID_SEQ = C.ID_SEQ AND C.IC_TYPE = 13 AND C.IC_CODE <> 1 AND A.MD_SEQ = A.MD_PSEQ										\n");
			sb.append("          ORDER BY B.IC_CODE ASC, SAME_CT DESC LIMIT 1)															\n");
			sb.append("       )A																										\n");
			sb.append("     , SITE_GROUP B																								\n");
			sb.append(" WHERE A.SG_SEQ = B.SG_SEQ																						\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				idBean = new IssueDataBean();

				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setMd_same_ct(rs.getString("SAME_CT"));
				idBean.setTemp1(rs.getString("SG_NAME"));
				idBean.setTemp2(rs.getString("TYPE12"));
				idBean.setTemp3(rs.getString("TYPE13"));
				idBean.setTemp4(rs.getString("TYPE9"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMedia_info(rs.getString("MEDIA_INFO"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				
				arrResult.add(idBean);
			}
			sb = null;
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
    
public ArrayList DailyReport3 (String id_seq, String ir_sdate, String ir_stime, String ir_edate, String ir_etime) {
		
		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();		
			
			sb.append("SELECT A.T_SEQ          AS MD_SEQ													\n");
			sb.append("     , A.T_SITE         AS MD_SITE_NAME	 											\n");
			sb.append("     , A.T_TITLE        AS ID_TITLE 													\n");
			sb.append("     , A.T_BOARD        AS MD_SITE_MENU						 						\n");
			sb.append("     , A.T_DATETIME     AS MD_DATE													\n");
			sb.append("     , A.T_PRESENTTIME  AS TEMP1														\n");
			sb.append("     , A.T_URL          AS ID_URL													\n");
			sb.append("  FROM TOP A																			\n");
			sb.append("     , ISSUE_DATA B																	\n");
			sb.append(" WHERE A.T_SEQ = B.MD_SEQ															\n");
			sb.append("   AND B.SG_SEQ = 30																	\n");
			sb.append("   AND B.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'	\n"); 
			sb.append("   AND B.F_NEWS = 'Y'																\n");
			if(!id_seq.equals("")){
			sb.append("   AND ID_SEQ IN ("+id_seq+")															\n");
			}
			
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				idBean = new IssueDataBean();

				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setMd_site_menu(rs.getString("MD_SITE_MENU"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setTemp1(rs.getString("TEMP1"));
				idBean.setId_url(rs.getString("ID_URL"));
				arrResult.add(idBean);
			}
			sb = null;
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}

	public ArrayList DailyReport4 (String id_seqs , String ir_sdate, String ir_stime, String ir_edate, String ir_etime) {
		
		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();			
			
			sb.append("SELECT A.ID_SEQ 																							\n");
			sb.append("     , A.ID_TITLE																						\n");
			sb.append("     , A.ID_URL																							\n");
			sb.append("     , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 14) AS TYPE14													\n");
			sb.append("     , A.MD_SITE_NAME																					\n");
			sb.append("     , A.MD_DATE																							\n");
			sb.append("     , (SELECT COUNT(*)-1 FROM ISSUE_DATA WHERE MD_PSEQ = A.MD_PSEQ AND SG_SEQ = A.SG_SEQ)  AS SAME_CT	\n");
			sb.append("  FROM ISSUE_DATA A																						\n");
			sb.append("     , ISSUE_DATA_CODE B																					\n");
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ																				\n");
			sb.append("   AND A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'						\n");
			sb.append("   AND B.IC_TYPE = 6																						\n");
			sb.append("   AND B.IC_CODE IN (1,6)																				\n");
			sb.append("   AND A.F_NEWS = 'Y'																					\n");
			if(!id_seqs.equals("")){
				sb.append("   AND A.ID_SEQ IN ("+id_seqs+") 																	\n");
			}
			sb.append("  ORDER BY SAME_CT DESC																					\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setTemp1(rs.getString("TYPE14"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setMd_same_ct(rs.getString("SAME_CT"));
				arrResult.add(idBean);
			}
			sb = null;
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}

	public ArrayList getChartData(String sdate, String stime, String edate, String etime, ArrayList codeList, boolean addType){
		ArrayList result = new ArrayList();
		ArrayList TempResult = new ArrayList();
		StringBuffer sb = null;
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		
		
		try{
			
			long diff = du.DateDiff(sdate.replaceAll("-", ""), edate.replaceAll("-", ""));
			
			
			// 차이값구할때 첫날은  빼버림 (사유 : 데이터 안나와서.SOL)
			String tempDate = du.addDay(edate.replaceAll("-", ""), (int)diff+1, "yyyyMMdd");
			
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			
			sb.append("SELECT MD_DATE\n");
			for(int i = 1; i < codeList.size(); i++){
				IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
				
				sb.append(", SUM(T.CNT"+i+") AS CNT"+i+"\n");
				
				
			}
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d') AS MD_DATE\n");
			for(int i = 1; i < codeList.size(); i++){
				
				IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
				
				if(icb.getIc_code() == 6){
					sb.append("		, IFNULL(CASE IDC1.IC_CODE WHEN "+icb.getIc_code()+" THEN SUM(1) END, 0) + IFNULL(CASE IDC1.IC_CODE WHEN 8 THEN SUM(1) END, 0) AS CNT"+i+"\n");
				}else{
					sb.append("		, IFNULL(CASE IDC1.IC_CODE WHEN "+icb.getIc_code()+" THEN SUM(1) END, 0) AS CNT"+i+"\n");
				}
				
			
			}
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC1\n");
			sb.append("		ON ID.ID_SEQ = IDC1.ID_SEQ\n");
			sb.append("		AND IDC1.IC_TYPE = 6\n");
			
			
			if(addType){
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC2\n");
			sb.append("		ON ID.ID_SEQ = IDC2.ID_SEQ\n");
			sb.append("		AND IDC2.IC_TYPE = 9 AND IDC2.IC_CODE = 1\n");
			}
			
			sb.append("INNER JOIN ISSUE_DATA_CODE IDC3					\n");
			sb.append("   ON ID.ID_SEQ = IDC3.ID_SEQ					\n");
			sb.append("  AND IDC3.IC_TYPE = 4 AND IDC3.IC_CODE IN (1,2)	\n"); 
			
			sb.append("		WHERE DATE_FORMAT(MD_DATE, '%Y%m%d%H') BETWEEN '"+sdate.replaceAll("-", "")+stime.substring(0,2)+"' AND '"+edate.replaceAll("-", "")+etime.substring(0,2)+"'\n");
			sb.append("		GROUP BY DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d'), IDC1.IC_CODE\n");
			sb.append(") T\n");
			sb.append("GROUP BY T.MD_DATE\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				IssueDataBean idb = new IssueDataBean();
				idb.setMd_date(rs.getString("MD_DATE"));
				int[] cnt = new int[codeList.size()-1];
				if(codeList.size() > 0){
					for(int i = 1; i < codeList.size(); i++){
						IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
						cnt[i-1] = rs.getInt("CNT"+i);
					}
				}
				idb.setCnt(cnt);
				TempResult.add(idb);
			}
			
			boolean chk = false;
			int chkCnt = 0;
			while(true){
				if(chkCnt != 0){
					tempDate = du.addDay(tempDate, 1, "yyyyMMdd");
				}
				for(int i = 0; i < TempResult.size(); i++){
					IssueDataBean idb = (IssueDataBean)TempResult.get(i);
					if(idb.getMd_date().equals(tempDate)){
						chk = true;
						result.add(idb);
					}
				}
				if(!chk){
					IssueDataBean idb = new IssueDataBean();
					idb.setMd_date(tempDate);
					int[] cnt = new int[codeList.size()-1];
	    			if(codeList.size() > 0){
	    				for(int i = 1; i < codeList.size(); i++){
	    					IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
	    					cnt[i-1] = 0;
	    				}
	    			}
	    			idb.setCnt(cnt);
					result.add(idb);
				}else{
					chk = false;
				}
				chkCnt++;
				if(tempDate.equals(edate.replaceAll("-", ""))){
					break;
				}
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	
	public ArrayList getChartData_type9(String sdate, String stime, String edate, String etime, ArrayList codeList, boolean addType){
		ArrayList result = new ArrayList();
		ArrayList TempResult = new ArrayList();
		StringBuffer sb = null;
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		
		
		try{
			
			long diff = du.DateDiff(sdate.replaceAll("-", ""), edate.replaceAll("-", ""));
			
			
			// 차이값구할때 첫날은  빼버림 (사유 : 데이터 안나와서.SOL)
			String tempDate = du.addDay(edate.replaceAll("-", ""), (int)diff+1, "yyyyMMdd");
			
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			
			sb.append("SELECT MD_DATE\n");
			for(int i = 1; i < codeList.size(); i++){
				IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
				
				sb.append(", SUM(T.CNT"+i+") AS CNT"+i+"\n");
				
				
			}
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d') AS MD_DATE\n");
			for(int i = 1; i < codeList.size(); i++){
				
				IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
				
				if(icb.getIc_code() == 6){
					sb.append("		, IFNULL(CASE IDC1.IC_CODE WHEN "+icb.getIc_code()+" THEN SUM(1) END, 0) + IFNULL(CASE IDC1.IC_CODE WHEN 8 THEN SUM(1) END, 0) AS CNT"+i+"\n");
				}else{
					sb.append("		, IFNULL(CASE IDC1.IC_CODE WHEN "+icb.getIc_code()+" THEN SUM(1) END, 0) AS CNT"+i+"\n");
				}
				
			
			}
			sb.append("		FROM ISSUE_DATA ID\n");
			
			
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC1\n");
			sb.append("		ON ID.ID_SEQ = IDC1.ID_SEQ\n");
			sb.append("		AND IDC1.IC_TYPE = 9\n");
			
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC3\n");
			sb.append("		ON ID.ID_SEQ = IDC3.ID_SEQ\n");
			sb.append("		AND IDC3.IC_TYPE = 4 AND IDC3.IC_CODE IN (1,2)\n");
			
			
			sb.append("		WHERE DATE_FORMAT(MD_DATE, '%Y%m%d%H') BETWEEN '"+sdate.replaceAll("-", "")+stime.substring(0,2)+"' AND '"+edate.replaceAll("-", "")+etime.substring(0,2)+"'\n");
			sb.append("		GROUP BY DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d'), IDC1.IC_CODE\n");
			sb.append(") T\n");
			sb.append("GROUP BY T.MD_DATE\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				IssueDataBean idb = new IssueDataBean();
				idb.setMd_date(rs.getString("MD_DATE"));
				int[] cnt = new int[codeList.size()-1];
				if(codeList.size() > 0){
					for(int i = 1; i < codeList.size(); i++){
						IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
						cnt[i-1] = rs.getInt("CNT"+i);
					}
				}
				idb.setCnt(cnt);
				TempResult.add(idb);
			}
			
			boolean chk = false;
			int chkCnt = 0;
			while(true){
				if(chkCnt != 0){
					tempDate = du.addDay(tempDate, 1, "yyyyMMdd");
				}
				for(int i = 0; i < TempResult.size(); i++){
					IssueDataBean idb = (IssueDataBean)TempResult.get(i);
					if(idb.getMd_date().equals(tempDate)){
						chk = true;
						result.add(idb);
					}
				}
				if(!chk){
					IssueDataBean idb = new IssueDataBean();
					idb.setMd_date(tempDate);
					int[] cnt = new int[codeList.size()-1];
	    			if(codeList.size() > 0){
	    				for(int i = 1; i < codeList.size(); i++){
	    					IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
	    					cnt[i-1] = 0;
	    				}
	    			}
	    			idb.setCnt(cnt);
					result.add(idb);
				}else{
					chk = false;
				}
				chkCnt++;
				if(tempDate.equals(edate.replaceAll("-", ""))){
					break;
				}
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	public ArrayList getChartData_type(String sdate, String stime, String edate, String etime, ArrayList codeList, boolean addType){
		ArrayList result = new ArrayList();
		ArrayList TempResult = new ArrayList();
		StringBuffer sb = null;
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String tempDate = du.addDay(edate.replaceAll("-", ""), -6, "yyyyMMdd");
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			
			sb.append("SELECT MD_DATE\n");
			for(int i = 1; i < codeList.size(); i++){
				IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
				sb.append(", SUM(T.CNT"+i+") AS CNT"+i+"\n");
			}
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d') AS MD_DATE\n");
			for(int i = 1; i < codeList.size(); i++){
				IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
			sb.append("		, IFNULL(CASE IDC1.IC_CODE WHEN "+icb.getIc_code()+" THEN SUM(1) END, 0) AS CNT"+i+"\n");
			}
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC1\n");
			sb.append("		ON ID.ID_SEQ = IDC1.ID_SEQ\n");
			sb.append("		AND IDC1.IC_TYPE = 6\n");
			
			if(addType){
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC2\n");
			sb.append("		ON ID.ID_SEQ = IDC2.ID_SEQ\n");
			sb.append("		AND IDC2.IC_TYPE = 9 AND IDC2.IC_CODE = 1\n");
			}
			
			sb.append("		WHERE DATE_FORMAT(MD_DATE, '%Y%m%d%H') BETWEEN '"+tempDate.replaceAll("-", "")+stime.substring(0,2)+"' AND '"+edate.replaceAll("-", "")+etime.substring(0,2)+"'\n");
			sb.append("		GROUP BY DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d'), IDC1.IC_CODE\n");
			sb.append(") T\n");
			sb.append("GROUP BY T.MD_DATE\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				IssueDataBean idb = new IssueDataBean();
				idb.setMd_date(rs.getString("MD_DATE"));
				int[] cnt = new int[codeList.size()-1];
				if(codeList.size() > 0){
					for(int i = 1; i < codeList.size(); i++){
						IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
						cnt[i-1] = rs.getInt("CNT"+i);
					}
				}
				idb.setCnt(cnt);
				TempResult.add(idb);
			}
			
			boolean chk = false;
			int chkCnt = 0;
			while(true){
				if(chkCnt != 0){
					tempDate = du.addDay(tempDate, 1, "yyyyMMdd");
				}
				for(int i = 0; i < TempResult.size(); i++){
					IssueDataBean idb = (IssueDataBean)TempResult.get(i);
					if(idb.getMd_date().equals(tempDate)){
						chk = true;
						result.add(idb);
					}
				}
				if(!chk){
					IssueDataBean idb = new IssueDataBean();
					idb.setMd_date(tempDate);
					int[] cnt = new int[codeList.size()-1];
	    			if(codeList.size() > 0){
	    				for(int i = 1; i < codeList.size(); i++){
	    					IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
	    					cnt[i-1] = 0;
	    				}
	    			}
	    			idb.setCnt(cnt);
					result.add(idb);
				}else{
					chk = false;
				}
				chkCnt++;
				if(tempDate.equals(edate.replaceAll("-", ""))){
					break;
				}
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
public ArrayList getLastDataCode(String m_seq) {
		
		ArrayList arrResult = new ArrayList();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();			
			
			sb.append("SELECT IC_TYPE, IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = (SELECT ID_SEQ FROM ISSUE_DATA WHERE M_SEQ = "+m_seq+" ORDER BY ID_SEQ DESC LIMIT 1)\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				icBean = new IssueCodeBean();
				
				icBean.setIc_type(rs.getInt("IC_TYPE"));
				icBean.setIc_code(rs.getInt("IC_CODE"));

				arrResult.add(icBean);
			}
			sb = null;
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}

}//IssueMgr End.
    
