package risk.issue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import risk.DBconn.DBconn;
import risk.admin.log.LogBean;
import risk.admin.log.LogConstants;
import risk.sms.AddressBookBean;
import risk.sms.AddressBookGroupBean;
import risk.util.ConfigUtil;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class IssueReportMgr {
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private ResultSet rs2    = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = null;	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	private IssueReportBean irBean = new IssueReportBean();
	private LogBean lBean = new LogBean();
	private int startNum = 0;
    private int endNum = 0;
	private int totalCnt = 0;
	
	
	public String msMinNo   = "";   //검색 시작 MD_SEQ
    public String msMaxNo   = "";   //검색 마지막 MD_SEQ
	
	public int getTotalCnt() {
		return totalCnt;
	}

	/**
     * ISSUE_REPORT를 등록하고 이슈번호 리턴
     * @param IssueBean
     * @return
     */
    public String insertReport(IssueReportBean irBean)
    {
    	String[] arrTemp;
    	String[] arrTypeCode ;
        boolean result = false;
        String insertNum = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			//stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" INSERT INTO ISSUE_REPORT(                                  \n");
			sb.append("                    IR_TYPE                                 \n");
			sb.append("                   ,IR_TITLE                                \n");
			sb.append("                   ,IR_MEMO                                 \n");
			sb.append("                   ,IR_HTML                                 \n");
			sb.append("                   ,IR_REGDATE                              \n");
			sb.append("                   ,IR_MAILYN                               \n");
			sb.append("                   ,ID_SEQ                               \n");
			sb.append("        			  ,IR_IMG_NAME            							\n");
			sb.append("        			  ,IR_REPORT_SDATE            							\n");
			sb.append("        			  ,IR_REPORT_EDATE            							\n");
			sb.append("                   )                               		   \n");
			sb.append(" VALUES(                                                    \n");			
			sb.append("        ?						                          	\n");
			sb.append("        , ?                        						 	\n");
			sb.append("        , ?                          						\n");
			sb.append("        , ?                         							\n");
			sb.append("        , ?     												\n");
			sb.append("        , ?                      							\n");
			sb.append("        , ?                      							\n");
			sb.append("        , ?                      							\n");
			sb.append("        , ?                      							\n");
			sb.append("        , ?                      							\n");
			
			sb.append(" 	  )                                                    	\n");
			//System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			
			int idx = 1;
			
			pstmt.setString(idx++, irBean.getIr_type());
			pstmt.setString(idx++, irBean.getIr_title());
			pstmt.setString(idx++, irBean.getIr_memo());
			pstmt.setString(idx++, irBean.getIr_html());
			pstmt.setString(idx++, du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
			pstmt.setString(idx++, irBean.getIr_mailyn());
			pstmt.setString(idx++, irBean.getId_seq());
			pstmt.setString(idx++, irBean.getIr_capture_img_name());
			pstmt.setString(idx++, irBean.getIr_regdate());
			pstmt.setString(idx++, irBean.getIr_regdate());
			
			System.out.println(pstmt);
			if(pstmt.executeUpdate()>0) {
				sb = new StringBuffer();
				sb.append(" SELECT MAX(IR_SEQ)AS IR_SEQ FROM ISSUE_REPORT                              \n");			
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				if(rs.next()){
					insertNum = rs.getString("IR_SEQ");
				}								
			}							
								
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }        
        return insertNum;
    }
    
    /**
     * ISSUE_REPORT를 등록하고 이슈번호 리턴
     * @param IssueBean
     * @return
     */
    public boolean UpdateReport(String irSeq, String irHtml)
    {
        boolean result = false;
        
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			//stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			
			sb.append("UPDATE ISSUE_REPORT           \n");
			sb.append("   SET IR_HTML = ? \n");
			sb.append(" WHERE IR_SEQ = ?     \n");
			
			pstmt = dbconn.createPStatement(sb.toString());
			
			pstmt.setString(1, irHtml);
			pstmt.setString(2, irSeq);
			if(pstmt.executeUpdate()>0) {
				result = true;								
			}											
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }        
        return result;
    }
    
    /**
     * ISSUE_REPORT 메일 성공 여부
     * @param IssueBean
     * @return
     */
    public boolean updateMailYN(IssueReportBean irBean)
    {    	
        boolean result = false;
        String insertNum = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" UPDATE ISSUE_REPORT SET IR_MAILYN = '"+irBean.getIr_mailyn()+"'       \n");			
			sb.append(" WHERE                                                    \n");			
			sb.append("       IR_SEQ = "+irBean.getIr_seq()+"                    \n");
			
			if(stmt.executeUpdate(sb.toString())>0) {
				result = true;		
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
     * ISSUE_REPORT를 삭제한다.
     * @param IssueBean
     * @return
     */
    public String deleteReport(String ir_seq)
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
			sb.append(" DELETE FROM ISSUE_REPORT                              	   \n");
			sb.append(" WHERE IR_SEQ IN ("+ir_seq+")                               \n");
		
			if(stmt.executeUpdate(sb.toString())>0) {
										
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
    
    public IssueReportBean getReportBean(String ir_seq)
    {
    	return (IssueReportBean)getIssueReportList(0,0,ir_seq,"","","","").get(0);	
    }
    
    /**
     * ISSUE_REPORT 테이블을 조회하여 해당정보를 어레이로 반환.
     * @param pageNum : 페이징번호 0일시 페이징처리 안함
     * @param ir_seq : ISSUE_REPORT.IR_SEQ
     * @param schKey : ISSUE_REPORT.IR_TITLE
     * @param schSdate : ISSUE_REPORT.IR_REGDATE
     * @param schEdate : ISSUE_REPORT.IR_REGDATE
     * @param useYn : ISSUE_REPORT.IR_USEYN
     * @return : 이슈 리포트 검색리스트
     */
    public ArrayList getIssueReportList( 
    		int pageNum, 
    		int rowCnt,
    		String ir_seq,
    		String ir_type, 
    		String schKey,	    	
    		String schSdate, 
    		String schEdate
    )    
    {    	
    	ArrayList issueReportList = new ArrayList();    	    	
    	String tmpI_seq = null;
    	
    	//리스트 시작, 끝번호 
		if(rowCnt>0) this.startNum = (pageNum-1)*rowCnt;
		this.endNum = rowCnt;
		
	/*	if(ir_type.equals("D")){
			ir_type = "D','MI','ID','D2";
		} */
		
		if(ir_type.equals("W")){
			ir_type = "W','W2";
		}
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("		SELECT COUNT(IR_SEQ) AS CNT 								\n");
			sb.append("		FROM ISSUE_REPORT                                                      \n");
			sb.append("		WHERE 1=1														\n");
			if( !ir_seq.equals("") ) {
				sb.append("		AND	IR_SEQ IN ("+ir_seq+")  									\n");
			}
			if( !ir_type.equals("") ) {
				sb.append("		AND	IR_TYPE IN ( '"+ir_type+"')  									\n");
			}	
			if( !schKey.equals("") ) {
				sb.append("		AND	IR_TITLE LIKE '%"+schKey+"%'  							\n");
			}				
			if( !schSdate.equals("") && !schEdate.equals("")) {					
				sb.append("		AND	IR_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");				
			}					
						
			rs = stmt.executeQuery(sb.toString());
            if ( rs.next() ) {
            	totalCnt  = rs.getInt("CNT");            	
            }
            
            rs.close();                     			
			sb = new StringBuffer();				
			sb.append(" SELECT IR_SEQ                                                         \n");	
			sb.append("        ,IR_TYPE                                                      \n");
			sb.append("        ,IR_TITLE                                                      \n");
			sb.append("        ,IR_HTML                                                      \n");
			sb.append("        ,IR_MEMO                                                      \n");
			sb.append("        ,IR_MAILYN                                                     \n");
			sb.append("        ,IR_IMG_NAME                                                     \n");			
			sb.append("        ,IFNULL(IR_REPORT_SDATE,\"\") AS  IR_REPORT_SDATE     \n");
			sb.append("        ,IFNULL(IR_REPORT_EDATE,\"\") AS IR_REPORT_EDATE     \n");
			sb.append("        ,(SELECT L_REGDATE FROM LOG L WHERE L.L_KEY = IR.IR_SEQ AND L.L_KINDS ="+LogConstants.getReportKindsVal()+" AND L.L_TYPE="+LogConstants.getMailTypeVal()+" ORDER BY L_REGDATE DESC LIMIT 1) AS IR_MAILDATE     \n");
			sb.append("        ,DATE_FORMAT(IR_REGDATE,'%Y-%m-%d %H:%i:%s') AS IR_REGDATE     \n");			
			sb.append("        ,(SELECT COUNT(*) FROM LOG L , LOG_RECEIVER_GROUP LR WHERE L.L_SEQ=LR.L_SEQ AND L.L_KEY = IR.IR_SEQ AND L.L_KINDS ="+LogConstants.getReportKindsVal()+" AND L.L_TYPE="+LogConstants.getMailTypeVal()+") AS MAIL_CNT     \n");
			sb.append(" FROM ISSUE_REPORT IR                                                   \n");
			sb.append(" WHERE 1=1														      \n");
			if( !ir_seq.equals("") ) {
				sb.append("		AND	IR_SEQ IN ("+ir_seq+")  									\n");
			}
			if( !ir_type.equals("") ) {
				sb.append("		AND	IR_TYPE IN ( '"+ir_type+"')  									\n");
			}	
			if( !schKey.equals("") ) {
				sb.append("		AND	IR_TITLE LIKE '%"+schKey+"%'  							\n");
			}				
			if( !schSdate.equals("") && !schEdate.equals("")) {					
				sb.append("		AND	IR_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");				
			}	
			
			sb.append(" ORDER BY IR_REGDATE DESC 		                                     \n");
			if(pageNum>0){
			sb.append(" LIMIT   "+startNum+","+endNum+"                                      \n");
			}			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				irBean = new IssueReportBean();
				irBean.setIr_seq(rs.getString("IR_SEQ"));
				irBean.setIr_title(rs.getString("IR_TITLE"));
				irBean.setIr_html(rs.getString("IR_HTML"));
				irBean.setIr_type(rs.getString("IR_TYPE"));
				irBean.setIr_memo(rs.getString("IR_MEMO"));
				irBean.setIr_regdate(rs.getString("IR_REGDATE"));
				irBean.setIr_maildate(rs.getString("IR_MAILDATE"));
				irBean.setIr_mailyn(rs.getString("IR_MAILYN"));
				irBean.setIr_mailcnt(rs.getString("MAIL_CNT"));
				irBean.setIr_capture_img_name(rs.getString("IR_IMG_NAME"));				
				irBean.setIr_sdate(rs.getString("IR_REPORT_SDATE"));				
				irBean.setIr_edate(rs.getString("IR_REPORT_EDATE"));				
				issueReportList.add(irBean);
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
		
	    return issueReportList;
	}
    
    /**
     * ISSUE_REPORT 테이블을 조회하여 해당정보를 어레이로 반환. (Excel)
     * @param pageNum : 페이징번호 0일시 페이징처리 안함
     * @param ir_seq : ISSUE_REPORT.IR_SEQ
     * @param schKey : ISSUE_REPORT.IR_TITLE
     * @param schSdate : ISSUE_REPORT.IR_REGDATE
     * @param schEdate : ISSUE_REPORT.IR_REGDATE
     * @param useYn : ISSUE_REPORT.IR_USEYN
     * @return : 이슈 리포트 검색리스트
     */
    public ArrayList getIssueReportList_excel( 
    		String ir_seq,
    		String ir_type, 
    		String schKey,	    	
    		String schSdate, 
    		String schEdate
    )    
    {    	
    	ArrayList issueReportList = new ArrayList();    	    	
    	String tmpI_seq = null;
    			
	/*	if(ir_type.equals("D")){
			ir_type = "D','MI','ID','D2";
		} */
		
		if(ir_type.equals("W")){
			ir_type = "W','W2";
		}
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("		SELECT COUNT(IR_SEQ) AS CNT 								\n");
			sb.append("		FROM ISSUE_REPORT                                                      \n");
			sb.append("		WHERE 1=1														\n");
			if( !ir_seq.equals("") ) {
				sb.append("		AND	IR_SEQ IN ("+ir_seq+")  									\n");
			}
			if( !ir_type.equals("") ) {
				sb.append("		AND	IR_TYPE IN ( '"+ir_type+"')  									\n");
			}	
			if( !schKey.equals("") ) {
				sb.append("		AND	IR_TITLE LIKE '%"+schKey+"%'  							\n");
			}				
			if( !schSdate.equals("") && !schEdate.equals("")) {					
				sb.append("		AND	IR_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");				
			}					
						
			rs = stmt.executeQuery(sb.toString());
            if ( rs.next() ) {
            	totalCnt  = rs.getInt("CNT");            	
            }
            
            rs.close();                     			
			sb = new StringBuffer();				
			sb.append(" SELECT IR_SEQ                                                         \n");	
			sb.append("        ,IR_TYPE                                                      \n");
			sb.append("        ,IR_TITLE                                                      \n");
			sb.append("        ,IR_HTML                                                      \n");
			sb.append("        ,IR_MEMO                                                      \n");
			sb.append("        ,IR_MAILYN                                                     \n");
			sb.append("        ,IFNULL((SELECT L_REGDATE FROM LOG L WHERE L.L_KEY = IR.IR_SEQ AND L.L_KINDS ="+LogConstants.getReportKindsVal()+" AND L.L_TYPE="+LogConstants.getMailTypeVal()+" ORDER BY L_REGDATE DESC LIMIT 1),'-') AS IR_MAILDATE     \n");
			sb.append("        ,DATE_FORMAT(IR_REGDATE,'%Y-%m-%d %H:%i:%s') AS IR_REGDATE     \n");			
			sb.append("        ,(SELECT COUNT(*) FROM LOG L , LOG_RECEIVER_GROUP LR WHERE L.L_SEQ=LR.L_SEQ AND L.L_KEY = IR.IR_SEQ AND L.L_KINDS ="+LogConstants.getReportKindsVal()+" AND L.L_TYPE="+LogConstants.getMailTypeVal()+") AS MAIL_CNT     \n");
			sb.append(" FROM ISSUE_REPORT IR                                                   \n");
			sb.append(" WHERE 1=1														      \n");
			if( !ir_seq.equals("") ) {
				sb.append("		AND	IR_SEQ IN ("+ir_seq+")  									\n");
			}
			if( !ir_type.equals("") ) {
				sb.append("		AND	IR_TYPE IN ( '"+ir_type+"')  									\n");
			}	
			if( !schKey.equals("") ) {
				sb.append("		AND	IR_TITLE LIKE '%"+schKey+"%'  							\n");
			}				
			if( !schSdate.equals("") && !schEdate.equals("")) {					
				sb.append("		AND	IR_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");				
			}	
			
			sb.append(" ORDER BY IR_REGDATE DESC 		                                     \n");
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				irBean = new IssueReportBean();
				irBean.setIr_seq(rs.getString("IR_SEQ"));
				irBean.setIr_title(rs.getString("IR_TITLE"));
				irBean.setIr_html(rs.getString("IR_HTML"));
				irBean.setIr_type(rs.getString("IR_TYPE"));
				irBean.setIr_memo(rs.getString("IR_MEMO"));
				irBean.setIr_regdate(rs.getString("IR_REGDATE"));
				irBean.setIr_maildate(rs.getString("IR_MAILDATE"));
				irBean.setIr_mailyn(rs.getString("IR_MAILYN"));
				irBean.setIr_mailcnt(rs.getString("MAIL_CNT"));
				issueReportList.add(irBean);
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
		
	    return issueReportList;
	}
    
    /**
     * LOG 테이블을 조회하여 리포트 로그를 어레이로 반환.   
     * @param ir_seq : LOG.L_KEY     
     * @return : 이슈 리포트 검색리스트
     */
    public LogBean getIssueReportLogBean(String ir_seq)    
    {
    	ArrayList receiverList = new ArrayList();
    	AddressBookBean abBean = new AddressBookBean();
    	String l_seq = "";
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		    			
			sb = new StringBuffer();				
			sb.append(" SELECT L.L_SEQ                         \n");
    		sb.append("       ,L.L_KINDS                       \n");
    		sb.append("       ,(SELECT LK_NAME FROM LOG_KINDS  WHERE LK_SEQ = L.L_KINDS) AS KINDSNAME  \n");
    		sb.append("       ,L.L_TYPE                        \n");
    		sb.append("       ,(SELECT LT_NAME FROM LOG_TYPE  WHERE LT_SEQ = L.L_TYPE) AS TYPENAME  \n");
    		sb.append("       ,IFNULL(L.L_IP,'') AS L_IP         \n");
    		sb.append("       ,L.L_KEY                           \n");
    		sb.append("       ,L.L_REGDATE                        \n");
    		sb.append("       ,L.M_SEQ                         \n");
    		sb.append("       ,M.M_ID                         \n");
    		sb.append("       ,M.M_NAME                         \n");
    		sb.append(" FROM LOG L INNER JOIN MEMBER M ON L.M_SEQ = M.M_SEQ \n");
    		sb.append(" WHERE                                               \n");
    		sb.append(" L.L_KINDS ="+LogConstants.getReportKindsVal()+" AND L.L_TYPE="+LogConstants.getMailTypeVal()+"  \n");
    		
    		if(ir_seq.length()>0)
    		{
    			sb.append(" AND L_KEY = "+ir_seq+" \n");
    		}
			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				l_seq += l_seq.equals("") ?  rs.getString("L_SEQ") : ","+ rs.getString("L_SEQ");    	
			}
			
			rs.close();
			
			if(l_seq.length()>0){
				sb = new StringBuffer();				
				sb.append(" SELECT AB.*	,DATE_FORMAT(LR.LR_REGDATE,'%Y-%m-%d %H:%i:%s')LR_REGDATE, LR.L_COUNT	 			\n");
	    		sb.append(" FROM LOG_RECEIVER LR,ADDRESS_BOOK AB WHERE LR.AB_SEQ=AB.AB_SEQ AND L_SEQ IN ("+l_seq+") \n");
	    		sb.append(" ORDER BY LR_REGDATE DESC \n");
	    		System.out.println(sb.toString());
	    		rs = stmt.executeQuery(sb.toString());
	    		while(rs.next()){
	    			
	    			abBean = new AddressBookBean();
		        	abBean.setMab_seq(rs.getInt("AB_SEQ"));
		        	abBean.setMab_name(rs.getString("AB_NAME"));
		        	abBean.setMab_dept(rs.getString("AB_DEPT"));
		        	abBean.setMab_pos(rs.getString("AB_POSITION"));
		        	abBean.setMab_mobile(rs.getString("AB_MOBILE"));
		        	abBean.setMab_mail(rs.getString("AB_MAIL"));
		        	abBean.setMab_issue_receivechk(rs.getString("AB_ISSUE_CHK"));
		        	abBean.setMab_report_day_chk(rs.getString("AB_REPORT_DAY_CHK"));
		        	abBean.setMab_report_week_chk(rs.getString("AB_REPORT_WEEK_CHK"));
		        	abBean.setMab_send_date(rs.getString("LR_REGDATE"));
		        	abBean.setL_count(rs.getString("L_COUNT"));
	    			receiverList.add(abBean);
	    		}	    		
	    		lBean.setArrReceiver(receiverList);
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
		
	    return lBean;
	}
 
    
    public LogBean getIssueReportLogBeanGroup(String ir_seq)    
    {
    	ArrayList receiverList = new ArrayList();
    	AddressBookGroupBean abBean = new AddressBookGroupBean();
    	String l_seq = "";
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		    			
			sb = new StringBuffer();				
			sb.append(" SELECT L.L_SEQ                         \n");
    		sb.append("       ,L.L_KINDS                       \n");
    		sb.append("       ,(SELECT LK_NAME FROM LOG_KINDS  WHERE LK_SEQ = L.L_KINDS) AS KINDSNAME  \n");
    		sb.append("       ,L.L_TYPE                        \n");
    		sb.append("       ,(SELECT LT_NAME FROM LOG_TYPE  WHERE LT_SEQ = L.L_TYPE) AS TYPENAME  \n");
    		sb.append("       ,IFNULL(L.L_IP,'') AS L_IP         \n");
    		sb.append("       ,L.L_KEY                           \n");
    		sb.append("       ,L.L_REGDATE                        \n");
    		sb.append("       ,L.M_SEQ                         \n");
    		sb.append("       ,M.M_ID                         \n");
    		sb.append("       ,M.M_NAME                         \n");
    		sb.append(" FROM LOG L INNER JOIN MEMBER M ON L.M_SEQ = M.M_SEQ \n");
    		sb.append(" WHERE                                               \n");
    		sb.append(" L.L_KINDS ="+LogConstants.getReportKindsVal()+" AND L.L_TYPE="+LogConstants.getMailTypeVal()+"  \n");
    		
    		if(ir_seq.length()>0)
    		{
    			sb.append(" AND L_KEY = "+ir_seq+" \n");
    		}
			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				l_seq += l_seq.equals("") ?  rs.getString("L_SEQ") : ","+ rs.getString("L_SEQ");    	
			}
			
			rs.close();
			
			if(l_seq.length()>0){
				sb = new StringBuffer();				
				sb.append(" SELECT AB.*	,DATE_FORMAT(LR.LR_REGDATE,'%Y-%m-%d %H:%i:%s')LR_REGDATE, LR.L_COUNT	 			\n");
				sb.append(" ,AG.*											\n");
	    		sb.append(" FROM LOG_RECEIVER_GROUP LR,ADDRESS_BOOK AB, ADDRESS_BOOK_GROUP AG  \n");
	    		sb.append(" WHERE LR.AG_SEQ=AB.AG_SEQ AND AB.AG_SEQ = AG.AG_SEQ \n");
	    		sb.append("  AND L_SEQ IN ("+l_seq+") \n");
	    		sb.append(" GROUP BY  AG.AG_SEQ \n");
	    		sb.append(" ORDER BY LR_REGDATE DESC \n");
	    		System.out.println(sb.toString());
	    		rs = stmt.executeQuery(sb.toString());
	    		while(rs.next()){
	    			
	    			abBean = new AddressBookGroupBean();
		        	abBean.setAg_seq(rs.getString("AG_SEQ"));
		        	abBean.setAg_name(rs.getString("AG_NAME"));
		        	abBean.setAg_send_datae(rs.getString("LR_REGDATE"));
		        	abBean.setAg_L_count(rs.getString("L_COUNT"));
	    			receiverList.add(abBean);
	    		}	    		
	    		lBean.setArrReceiver(receiverList);
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
		
	    return lBean;
	}
	
	/**
     * 이슈 보고서 차트  데이터 
     * A타입:일일 여론변화
     * B타입:출처별 관심도
     * C타입:업무구분 주요정보
     */
	public HashMap getIssueChartData(String ir_sdate,String ir_stime, String ir_edate,String ir_etime,String i_seq)
	{
		HashMap ChartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		
		long diffDay = 0;
		int intDiffDay = 0;
		
		String[] date  = null;
		
		ArrayList typeAChart = new ArrayList();
		HashMap dataA = new HashMap();
		HashMap tempHash = new HashMap();		
		ArrayList typeBChart = new ArrayList();
		HashMap dataB = new HashMap();	
		ArrayList typeCChart = new ArrayList();
		HashMap dataC = new HashMap();
		ArrayList typeDChart = new ArrayList();
		HashMap dataD = new HashMap();
		ArrayList typeEChart = new ArrayList();
		HashMap dataE = new HashMap();
		
		String first = "";
		String last = "";
		
		
		try{
			dbconn = new DBconn();			
			dbconn.getDBCPConnection();
			
			first = du.getDate(ir_sdate,"yyyyMMdd");
			last = du.getDate(ir_edate,"yyyyMMdd");
			
			diffDay = -du.DateDiff("yyyyMMdd",first,last);
			intDiffDay = Integer.parseInt(String.valueOf(diffDay));
			
			if(intDiffDay>0)
			{
				date  = new String[intDiffDay+1];
				for(int i = 0; i<intDiffDay+1; i++)
				{
					date[i] = du.addDay(first, i , "yyyyMMdd");
					tempHash = new HashMap();
					tempHash.put("CATEGORY", date[i]);
					tempHash.put("PCNT","0");
					tempHash.put("NCNT","0");
					tempHash.put("TCNT","0");							
					typeAChart.add(tempHash);
				}
			}									
	    	
	    	sb = new StringBuffer();
	    	sb.append(" SELECT  A1.MD_DATE AS CATEGORY, COUNT(ID_SEQ) TCNT , '계' NAME1                       \n");
	    	sb.append(" 		, SUM(PCNT) PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) NAME2     \n");
	    	sb.append(" 		, SUM(NCNT) NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) NAME3                       \n");
	    	sb.append(" 		, SUM(ECNT) ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) NAME4                      \n");
	    	sb.append(" FROM                                                                                                                   \n");
	    	sb.append(" (                                                                                                                      \n");
	    	sb.append("     SELECT  A2.ID_SEQ, A2.MD_DATE, IF(B2.IC_CODE=1,1,0) PCNT, IF(B2.IC_CODE=2,1,0) NCNT, IF(B2.IC_CODE=3,1,0) ECNT 	\n");
	    	sb.append("     FROM                                                                                                               \n");
	    	sb.append("     ( SELECT ID_SEQ,DATE_FORMAT(MD_DATE,'%Y%m%d')AS MD_DATE FROM ISSUE_DATA                                      \n");
	    	sb.append("       WHERE MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'  AND I_SEQ ="+i_seq+"                                       \n");
	    	sb.append("      )A2                                                            	                                               \n");
	    	sb.append("     ,(SELECT ID_SEQ,IC_CODE,IC_TYPE FROM ISSUE_DATA_CODE WHERE IC_TYPE=9)B2                                            \n");
	    	sb.append("     WHERE                                                                                                              \n");
	    	sb.append("         A2.ID_SEQ = B2.ID_SEQ                                                                                          \n");
	    	sb.append(" ) A1                                                                                                                   \n");
	    	sb.append(" GROUP BY MD_DATE                                                                                                    \n");
	    	sb.append(" ORDER BY MD_DATE                                                                                                    \n");
																		
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
	    	while(rs.next()){
	    		
	    		for(int i =0;i<typeAChart.size();i++)
	    		{
	    			
	    			dataA = new HashMap();
	    			dataA =(HashMap)typeAChart.get(i);
	    			//System.out.println("date : "+(String)dataA.get("1"));
	    			if((rs.getString("CATEGORY")).equals((String)dataA.get("CATEGORY")))
	    			{
	    				dataA.put("TCNT",rs.getString("TCNT"));
	    				dataA.put("NAME1",rs.getString("NAME1"));
	    				dataA.put("PCNT",rs.getString("PCNT"));
	    				dataA.put("NAME2",rs.getString("NAME2"));
	    				dataA.put("NCNT",rs.getString("NCNT"));
	    				dataA.put("NAME3",rs.getString("NAME3"));
	    				dataA.put("ECNT",rs.getString("ECNT"));
	    				dataA.put("NAME4",rs.getString("NAME4"));
	    			}else{
	    				dataA.put("NAME1",rs.getString("NAME1"));
	    				dataA.put("NAME2",rs.getString("NAME2"));
	    				dataA.put("NAME3",rs.getString("NAME3"));
	    				dataA.put("NAME4",rs.getString("NAME4"));	    				
	    			}
	    			typeAChart.set(i, dataA);	    			
	    		}	    		
	    	}    		    	
	    	ChartDataHm.put("A",typeAChart);			
		    

	    	rs = null;
			pstmt = null;
	    	sb = new StringBuffer();	    
    		sb.append(" SELECT A2.IC_NAME AS CATEGORY, IFNULL(B2.CNT,0) AS CNT                                                                                \n");
	    	sb.append(" FROM                                                                                                                   \n");
	    	sb.append(" (SELECT IC_NAME,IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 9 AND IC_CODE<>0) A2                                           \n");
	    	sb.append(" LEFT OUTER JOIN                                                                                                        \n");
	    	sb.append(" (                                                                                                                      \n");
	    	sb.append("  SELECT IC_CODE, COUNT(*) CNT                                                                                          \n");
	    	sb.append("  FROM ISSUE_DATA A INNER JOIN  ISSUE_DATA_CODE B ON (A.ID_SEQ = B.ID_SEQ )										    	\n");
	    	sb.append("  WHERE                                                                                                                 \n");
	    	sb.append("      A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"' AND A.I_SEQ ="+i_seq+"                                     \n");
	    	sb.append("      AND B.IC_TYPE =9                                                                                                  \n");
	    	sb.append("  GROUP BY B.IC_CODE                                                                                                    \n");
	    	sb.append("  )B2 ON (A2.IC_CODE =B2.IC_CODE)                                                                                       \n");	
	    	
	    	System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			
	    	while(rs.next()){
	    		dataB = new HashMap();
	    		dataB.put("CATEGORY", rs.getString("CATEGORY"));
	    		dataB.put("CNT", rs.getString("CNT"));
	    		typeBChart.add(dataB);
	    	}
	    	ChartDataHm.put("B",typeBChart);
	    	
	    	rs = null;
			pstmt = null;
	    	sb = new StringBuffer();	    
	    	sb.append(" SELECT A1.IC_NAME AS CATEGORY                                                                                   \n");
	    	sb.append("        ,IFNULL(A2.PCNT,0) PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) AS NAME1                                                                 \n");
	    	sb.append("        ,IFNULL(A2.NCNT,0) NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) AS NAME2                                                                 \n");
	    	sb.append("        ,IFNULL(A2.ECNT,0) ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) AS NAME3                                                                 \n");
	    	sb.append(" FROM                                                                                                            \n");
	    	sb.append("     (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_CODE<>0) A1 LEFT OUTER JOIN               \n");
	    	sb.append("     (                                                                                                           \n");
	    	sb.append("     SELECT C.IC_CODE , SUM(IF(B.IC_CODE=1,1,0))PCNT, SUM(IF(B.IC_CODE=2,1,0))NCNT, SUM(IF(B.IC_CODE=3,1,0))ECNT \n");
	    	sb.append("     FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C                                                     \n");
	    	sb.append("     WHERE                                                                                                       \n");
	    	sb.append("          A.ID_SEQ = B.ID_SEQ                                                                                    \n");
	    	sb.append("          AND B.ID_SEQ = C.ID_SEQ                                                                                \n");
	    	sb.append("          AND B.IC_TYPE = 9                                                                                      \n");
	    	sb.append("          AND C.IC_TYPE = 6                                                                                      \n");
	    	sb.append("          AND A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"' AND A.I_SEQ ="+i_seq+"                               \n");
	    	sb.append("     GROUP BY C.IC_CODE                                                                                          \n");
	    	sb.append("     )A2 ON(A1.IC_CODE=A2.IC_CODE)                                                                               \n");
	    	sb.append(" ORDER BY A1.IC_CODE ASC                                                                                         \n");
	    	
	    	System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			
	    	while(rs.next()){
	    		dataC = new HashMap();
	    		dataC.put("CATEGORY", rs.getString("CATEGORY"));
	    		dataC.put("PCNT", rs.getString("PCNT"));
	    		dataC.put("NAME1", rs.getString("NAME1"));
	    		dataC.put("NCNT", rs.getString("NCNT"));
	    		dataC.put("NAME2", rs.getString("NAME2"));
	    		dataC.put("ECNT", rs.getString("ECNT"));
	    		dataC.put("NAME3", rs.getString("NAME3"));
	    		typeCChart.add(dataC);
	    	}
	    	ChartDataHm.put("C",typeCChart);
	    	   	
	    	
	    } catch(SQLException ex) {
	    	Log.writeExpt(ex,ex.getMessage());
	    } catch(Exception ex) {
			Log.writeExpt(ex);
	    } finally {
	    	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	        if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	    }   	
		return ChartDataHm;
	}
    
    /**
     * 기간 보고서 차트  데이터 
     * A타입:일일 여론변화
     * B타입:출처별 관심도
     * C타입:업무구분 주요정보
     */
	public HashMap getPeoridChartData(String ir_sdate,String ir_stime, String ir_edate,String ir_etime,String k_xp,String k_yp,String k_zp,String sg_seq)
	{
		HashMap ChartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		
		long diffDay = 0;
		int intDiffDay = 0;
		
		String[] date  = null;
		
		ArrayList typeAChart = new ArrayList();
		HashMap dataA = new HashMap();
		HashMap tempHash = new HashMap();		
		ArrayList typeBChart = new ArrayList();
		HashMap dataB = new HashMap();	
		ArrayList typeCChart = new ArrayList();
		HashMap dataC = new HashMap();
		ArrayList typeDChart = new ArrayList();
		HashMap dataD = new HashMap();
		ArrayList typeEChart = new ArrayList();
		HashMap dataE = new HashMap();
		
		String tmpTime = "";
		String first = "";
		String last = "";
		getMaxMinNo( ir_sdate, ir_edate );
		
		try{
			dbconn = new DBconn();			
			dbconn.getDBCPConnection();
				
			
			for(int i = 0; i<23; i++)
			{			
				tempHash = new HashMap();
				
				if(i+1>=10){tmpTime =String.valueOf(i+1);}
				else{tmpTime ="0"+(i+1);}
				
				System.out.println("tmpTime:"+tmpTime);
				
				tempHash.put("CATEGORY", tmpTime);
				tempHash.put("CNT","0");
										
				typeAChart.add(tempHash);
			}					
	    	
	    	sb = new StringBuffer();
	    	sb.append("SELECT MD_DATE CATEGORY, COUNT(*) AS CNT, (SELECT SG_NAME FROM SITE_GROUP WHERE SG_SEQ =RESULT.SG_SEQ) NAME\n");
	    	sb.append("FROM                                                                                        \n");
	    	sb.append("(                                                                                           \n");
	    	sb.append("  SELECT				                                                    \n");
	    	sb.append("      MIN(DISTINCT A.MD_SEQ) AS MD_SEQ ,B.S_SEQ ,B.MD_SITE_NAME ,B.MD_MENU_NAME ,B.MD_TYPE  \n");
	    	sb.append("      ,DATE_FORMAT(B.MD_DATE,'%H') AS MD_DATE ,B.MD_IMG ,B.MD_SAME_COUNT              \n");
	    	sb.append("      ,B.MD_PSEQ ,B.MD_TITLE ,B.MD_URL , SG_SEQ, ISSUE_CHECK, I_DELDATE, M_SEQ              \n");
	    	sb.append("  FROM IDX A, META B		                                                            \n");
	    	sb.append("  WHERE A.MD_SEQ BETWEEN "+msMinNo+" AND "+msMaxNo+"                                            \n");
	    	sb.append("       AND A.MD_SEQ = B.MD_SEQ                                                              \n");
	    	if(k_xp.length()>0)
	    	sb.append("       AND A.K_XP   IN ("+k_xp+")                                           \n");
	    	if(k_yp.length()>0)
	    	sb.append("       AND A.K_YP   IN ("+k_yp+")                                           \n");
	    	if(k_zp.length()>0)
	    	sb.append("       AND A.K_ZP   IN ("+k_zp+")                                           \n");
	    	if(sg_seq.length()>0)
	    	sb.append("       AND A.SG_SEQ IN ("+sg_seq+")                                                            \n");
	    	sb.append("       AND B.MD_SEQ = B.MD_SEQ                                                              \n");
	    	sb.append("       AND (A.I_STATUS = 'N')                                             \n");
	    	sb.append("  GROUP BY B.MD_PSEQ                                                                        \n");
	    	sb.append(") RESULT                                                                                    \n");
	    	sb.append("GROUP BY MD_DATE                                                                            \n");
																		
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
	    	while(rs.next()){
	    		
	    		for(int i =0;i<typeAChart.size();i++)
	    		{
	    			
	    			dataA = new HashMap();
	    			dataA =(HashMap)typeAChart.get(i);
	    			//System.out.println("date : "+(String)dataA.get("1"));
	    			if((rs.getString("CATEGORY")).equals((String)dataA.get("CATEGORY")))
	    			{
	    				dataA.put("NAME",rs.getString("NAME"));	
	    				dataA.put("CNT",rs.getString("CNT"));	    				
	    			}else{
	    				dataA.put("NAME",rs.getString("NAME"));	
	    			}
	    			typeAChart.set(i, dataA);	    			
	    		}	    		
	    	}    		    	
	    	ChartDataHm.put("A",typeAChart);
	    	
	    	
	    	   	
	    	
	    } catch(SQLException ex) {
	    	Log.writeExpt(ex,ex.getMessage());
	    } catch(Exception ex) {
			Log.writeExpt(ex);
	    } finally {
	    	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	        if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	    }   	
		return ChartDataHm;
	}
	
	public void getMaxMinNo( String psSDate, String psEdate ) {

        try {
        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
             
            sb = new StringBuffer();                    
            sb.append(" SELECT (SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '"+psSDate+" 00:00:00' AND '"+psSDate+" 23:59:59' ORDER BY MD_DATE ASC LIMIT 1) MIN_NO \n");
            sb.append("        ,(SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '"+psEdate+" 00:00:00' AND '"+psEdate+" 23:59:59' ORDER BY MD_DATE DESC LIMIT 1) MAX_NO \n");
                       

            Log.debug(sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());

            if ( rs.next() ) {
                msMinNo = rs.getString("MIN_NO");
                msMaxNo = rs.getString("MAX_NO");
                
                if(msMinNo==null || msMinNo.equals("null")){
                	msMinNo = "0";
                }
                if(msMaxNo==null || msMaxNo.equals("null")){
                	msMaxNo = "999999999";
                }
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
    }
	
	public void AddCnt( String l_seq, String ab_seq ) {

        try {
        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            
            sb = new StringBuffer();                    
            sb.append(" UPDATE LOG_RECEIVER SET L_COUNT = L_COUNT+1 WHERE L_SEQ = "+l_seq+" AND AB_SEQ = "+ab_seq+" \n");
            
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
    }
	
	public void AddCnt_GROUP( String l_seq, String ag_seq ) {

        try {
        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            
            sb = new StringBuffer();                    
            sb.append(" UPDATE LOG_RECEIVER_GROUP SET L_COUNT = L_COUNT+1 WHERE L_SEQ = "+l_seq+" AND AG_SEQ = "+ag_seq+" \n");
            
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
    }
	
	public boolean setIdContent(String id_seq, String d_content ) {

		boolean result = false; 
        try {
        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
            
            sb = new StringBuffer();                    
            sb.append(" UPDATE ISSUE_DATA SET ID_CONTENT = ? WHERE ID_SEQ = "+id_seq+" \n");
            
            System.out.println(sb.toString());
            pstmt = dbconn.createPStatement(sb.toString());
            
            pstmt.clearParameters();
            pstmt.setString(1, d_content);
            
            if(pstmt.executeUpdate() > 0){
            	result = true;
            }

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
	
	/**
     * ISSUE_REPORT 테이블을 조회하여 해당정보를 어레이로 반환.
     * SK에서 작성할 종합 보고서에서 RSN이 작성한 보고서 리스트를 불러 올 때 씀
     * @param ir_type : 검색할 보고서 유형
     * @param ir_sdate : 시작날짜
     * @param ir_edate : 마지막날짜
     * @return : 보고서 리스트
     * Used SK Group
     */
    public ArrayList getIntegratedReportList( 
    		
    		String ir_type, 
    		String ir_sdate, 
    		String ir_edate
    )    
    {    	
    	ArrayList issueReportList = new ArrayList();    	    	
    
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("		SELECT IR_SEQ, IR_TITLE, DATE_FORMAT(IR_REGDATE,'%m/%d %H:%i') AS IR_REGDATE						\n");
			sb.append("			FROM ISSUE_REPORT 																	\n");
			sb.append("			WHERE IR_REGDATE BETWEEN '"+ ir_sdate +" 00:00:00' AND '"+ir_edate+" 23:59:59' 		\n");
			sb.append("			AND IR_TYPE = '"+ ir_type +"'		 												\n");

			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				irBean = new IssueReportBean();
				irBean.setIr_seq(rs.getString("IR_SEQ"));
				irBean.setIr_title(rs.getString("IR_TITLE"));
				irBean.setIr_regdate(rs.getString("IR_REGDATE"));
				issueReportList.add(irBean);
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
		
	    return issueReportList;
	}	
    
	/**
     * ISSUE_REPORT 테이블을 조회하여 해당정보를 어레이로 반환.
     * SK에서 작성할 종합 보고서에서 RSN이 작성한 보고서 HTML코드를 불러 올 때 씀
     * @param ir_type : 선택된 보고서 IR_SEQ
     * @return : 보고서 HTML
     * Used SK Group
     */
    public String getReportHtml( 
    		
    		String ir_seq
    )    
    {    	
    	String report_Html = "";  	    	
    
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("		SELECT IR_HTML						\n");
			sb.append("			FROM ISSUE_REPORT 				\n");
			sb.append("			WHERE IR_SEQ ="+ ir_seq +"		\n");

			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			if(rs.next()){
				report_Html = rs.getString("IR_HTML").trim();
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
		
	    return report_Html;
	}	
    
    //정보수집 기사공유 보고서 그리기 2018.02.20 고기범
	public String newsReportHtml(String md_seq, String md_title, String md_site, String md_date, String md_url, String contentArea, String urlArea){
		ConfigUtil cu = new ConfigUtil();
		
		try{
			String siteUrl = cu.getConfig("URL");
			String baseUrl = siteUrl+"riskv3/report/";
			
			String[] md_seqs = md_seq.split("@[$][$]@");
			String[] md_titles = md_title.split("@[$][$]@");
			String[] md_sites = md_site.split("@[$][$]@");
			String[] md_dates = md_date.split("@[$][$]@");
			String[] md_urls = md_url.split("@[$][$]@");
			String[] contentAreas = contentArea.split("@[$][$]@");
			String[] urlAreas = urlArea.split("@[$][$]@");
			
			
			sb = new StringBuffer();
			
			sb.append(" <table border='0' cellSpacing='0' cellPadding='0' width='750' align='center' style='font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:12px;empty-cells:show'> \n");
			sb.append(" 	<tbody> \n");
			sb.append(" 	<tr> \n");
			sb.append(" 	<td style='border:1px solid #d0d0d0'> \n");
			sb.append(" 		<table border='0' cellSpacing='0' cellPadding='0' width='100%' align='center' style='font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:12px;empty-cells:show'> \n");
			sb.append(" 			<tbody> \n");
			sb.append(" 			<tr> \n");
			sb.append(" 			<td> \n");
			sb.append(" 				<table border='0' cellSpacing='0' cellPadding='0' width='100%' align='left' style='font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;empty-cells:show;table-layout:fixed'> \n");
			sb.append(" 				<tbody> \n");
			sb.append(" 				<tr><td style='font-size:1px;line-height:1px;'><img src='"+baseUrl+"img/h1_logo_news.gif' alt='Online Monitoring Report'></td></tr> \n");
			sb.append(" 				</tbody> \n");
			sb.append(" 				</table> \n");
			sb.append(" 			</td> \n");
			sb.append(" 			</tr> \n");
			sb.append(" 			<tr> \n");
			sb.append(" 			<td style='padding:20px 0 30px'> \n");
			sb.append(" 				<table border='0' cellSpacing='0' cellPadding='0' width='708' align='center' style='font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;empty-cells:show;table-layout:fixed'> \n");
			sb.append(" 				<tbody> \n");
			for(int i=0; i<md_seqs.length; i++){
				sb.append(" <tr> \n");
				sb.append(" 				<td style='padding:20px 0 0 0'> \n");
				sb.append(" 					<table border='0' cellSpacing='0' cellPadding='0' width='100%' align='left' style='font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;empty-cells:show;table-layout:fixed'> \n");
				sb.append(" 					<tbody> \n");
				sb.append(" 					<tr> \n");
				sb.append(" 					<td style='padding:0 0 14px 0'> \n");
				sb.append(" 						<table border='0' cellSpacing='0' cellPadding='0' align='left' style='font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;empty-cells:show;table-layout:fixed'> \n");
				sb.append(" 						<tbody> \n");
				sb.append(" 						<tr> \n");
				sb.append(" 						<td style='color:#000000;font-size:17px;font-weight:bold'>"+(i+1)+". "+md_titles[i]+"</td> \n");
				sb.append(" 						</tr> \n");
				sb.append(" 						</tbody> \n");
				sb.append(" 						</table> \n");
				sb.append(" 					</td> \n");
				sb.append(" 					</tr> \n");
				sb.append(" 					<tr> \n");
				sb.append(" 					<td> \n");
				sb.append(" 						<table border='0' cellSpacing='0' cellPadding='0' width='100%' align='left' style='border-bottom:1px solid #d7d7d7;font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;empty-cells:show;table-layout:fixed'> \n");
				sb.append(" 						<thead> \n");
				sb.append(" 						<tr> \n");
				sb.append(" 						<th style='width:110px;font-size:1px;line-height:1px;'><img src='"+baseUrl+"img/bullet_01_news.gif' alt='-'></th> \n");
				sb.append(" 						<th style='width:290px;font-size:1px;line-height:1px;'><img src='"+baseUrl+"img/bullet_02_news.gif' alt='-'></th> \n");
				sb.append(" 						<th style='width:110px;font-size:1px;line-height:1px;'><img src='"+baseUrl+"img/bullet_01_news.gif' alt='-'></th> \n");
				sb.append(" 						<th style='font-size:1px;line-height:1px;'><img src='"+baseUrl+"img/bullet_03_news.gif' alt='-'></th> \n");
				sb.append(" 						</tr> \n");
				sb.append(" 						</thead> \n");
				sb.append(" 						<tbody> \n");
				sb.append(" 						<tr> \n");
				sb.append(" 						<td style='padding:10px 5px;border-bottom:1px solid #d7d7d7;background:#f6f6f6;color:#000000;font-size:12px;font-weight:bold;text-align:center;'>정보 출처</td> \n");
				sb.append(" 						<td style='padding:10px 20px;border-bottom:1px solid #d7d7d7;color:#000000;font-size:12px;text-align:left;'>"+md_sites[i]+"</td> \n");
				sb.append(" 						<td style='padding:10px 5px;border-bottom:1px solid #d7d7d7;background:#f6f6f6;color:#000000;font-size:12px;font-weight:bold;text-align:center;'>수집시간</td> \n");
				sb.append(" 						<td style='padding:10px 20px;border-bottom:1px solid #d7d7d7;color:#000000;font-size:12px;text-align:left;'>"+md_dates[i].substring(0, 19)+"</td> \n");
				sb.append(" 						</tr> \n");
				sb.append(" 						<tr> \n");
				sb.append(" 						<td style='padding:10px 5px;border-bottom:1px solid #d7d7d7;background:#f6f6f6;color:#000000;font-size:12px;font-weight:bold;text-align:center;'>URL</td> \n");
				sb.append(" 						<td colspan='3' style='padding:10px 20px;border-bottom:1px solid #d7d7d7;color:#000000;font-size:12px;text-align:left;word-break:break-all'><a href='#' target='_blank' style='color:#000000 !important'>"+md_urls[i]+"</a></td> \n");
				sb.append(" 						</tr> \n");
				sb.append(" 						<tr> \n");
				sb.append(" 						<td style='padding:10px 5px;border-bottom:1px solid #d7d7d7;background:#f6f6f6;color:#000000;font-size:12px;font-weight:bold;text-align:center;'>단축 URL</td> \n");
				sb.append(" 						<td colspan='3' style='padding:10px 20px;border-bottom:1px solid #d7d7d7;color:#000000;font-size:12px;text-align:left;word-break:break-all'>"+urlAreas[i]+"</td> \n");
				sb.append(" 						</tr> \n");
				sb.append(" 						<tr> \n");
				sb.append(" 						<td style='padding:10px 5px;background:#f6f6f6;color:#000000;font-size:12px;font-weight:bold;text-align:center;'>내용요약</td> \n");
				sb.append(" 						<td colspan='3' style='padding:10px 20px;color:#000000;font-size:12px;text-align:justify;mso-line-height-rule:exactly;line-height:18px;word-break:break-all'>"+contentAreas[i]+"</td> \n");
				sb.append(" 						</tr> \n");
				sb.append(" 						</tbody> \n");
				sb.append(" 						</table> \n");
				sb.append(" 					</td> \n");
				sb.append(" 					</tr> \n");
				sb.append(" 					</tbody> \n");
				sb.append(" 					</table> \n");
				sb.append(" 				</td> \n");
				sb.append(" 				</tr> \n");
			}
			sb.append(" 				</tbody> \n");
			sb.append(" 				</table> \n");
			sb.append(" 			</td> \n");
			sb.append(" 			</tr> \n");
			sb.append(" 			<tr> \n");
			sb.append(" 			<td> \n");
			sb.append(" 				<table border='0' cellSpacing='0' cellPadding='0' width='100%' align='left' style='font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;empty-cells:show;table-layout:fixed'> \n");
			sb.append(" 				<tbody> \n");
			sb.append(" 				<tr> \n");
			sb.append(" 				<td style='border-top:1px solid #d0d0d0'> \n");
			sb.append(" 					<img src='"+baseUrl+"img/fot_news.gif' alt='SK'> \n");
			sb.append(" 				</td> \n");
			sb.append(" 				</tr> \n");
			sb.append(" 				</tbody> \n");
			sb.append(" 				</table> \n");
			sb.append(" 			</td> \n");
			sb.append(" 			</tr> \n");
			sb.append(" 			</tbody> \n");
			sb.append(" 		</table> \n");
			sb.append(" 	</td> \n");
			sb.append(" 	</tr> \n");
			sb.append(" 	</tbody> \n");
			sb.append(" </table> \n");
			

		} catch(Exception ex) {
			ex.printStackTrace();
			Log.writeExpt(ex);
		}
		
		return sb.toString();
	}
    
    
    
}//IssueReportMgr End.
