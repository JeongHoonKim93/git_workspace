package risk.issue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import risk.DBconn.DBconn;
import risk.admin.member.MemberBean;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.statistics.StatisticsSuperBean;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IssueMgr {

	private DBconn dbconn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = null;
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	private IssueDataBean idBean = null;
	private IssueCodeBean icBean = null;
	private IssueCommentBean icmBean = null;
	private MemberBean mBean = null;
	private int startNum = 0;
	private int endNum = 0;
	private int totalIssueCnt = 0;
	private int totalIssueTitleCnt = 0;
	private int totalIssueDataCnt = 0;
	private int totalSameDataCnt = 0;

	/**
	 * 어레이 호출 함수의 정보 카운트
	 * 
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

	/*
		*//**
			 * ISSUE 등록하고 해당번호를 반환
			 * 
			 * @param i_seq : ISSUE.I_SEQ
			 * @return
			 */
	/*
	 * public boolean insertIssue(IssueBean isBean) { boolean result = false; String
	 * insertNum = null; try{ dbconn = new DBconn(); dbconn.getDBCPConnection();
	 * stmt = dbconn.createStatement();
	 * 
	 * sb = new StringBuffer();
	 * sb.append(" INSERT INTO ISSUE(	                            		   \n");
	 * sb.append("                   I_TITLE                                  \n");
	 * sb.append("                   ,I_REGDATE                               \n");
	 * sb.append("                   ,I_USEYN                                 \n");
	 * sb.append("                   ,M_SEQ)                                  \n");
	 * sb.append(" VALUES(                                                    \n");
	 * sb.append("        '"+isBean.getI_title()+"'                           \n");
	 * sb.append("        ,'"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"'     \n");
	 * sb.append("        ,'"+isBean.getI_useyn()+"'                          \n");
	 * sb.append("        ,"+isBean.getM_seq()+"                              \n");
	 * sb.append(" )                                                          \n");
	 * System.out.println(sb.toString()); if(stmt.executeUpdate(sb.toString())>0)
	 * result = true;
	 * 
	 * } catch(SQLException ex) { Log.writeExpt(ex, sb.toString() ); }
	 * catch(Exception ex) { Log.writeExpt(ex); } finally { sb = null; if (rs !=
	 * null) try { rs.close(); } catch(SQLException ex) {} if (stmt != null) try {
	 * stmt.close(); } catch(SQLException ex) {} if( dbconn != null) try {
	 * dbconn.close(); } catch(SQLException ex) {} } return result; }
	 */
	/*	
		*//**
			 * ISSUE 등록하고 해당번호를 반환
			 * 
			 * @param IssueDataBean
			 * @return
			 */
	/*
	 * public boolean updateIssue(IssueBean isBean) { boolean result = false;
	 * 
	 * try{ dbconn = new DBconn(); dbconn.getDBCPConnection(); stmt =
	 * dbconn.createStatement();
	 * 
	 * sb = new StringBuffer();
	 * sb.append("UPDATE ISSUE SET I_TITLE = '"+isBean.getI_title()
	 * +"' WHERE I_SEQ IN ("+isBean.getI_seq()+")  \n");
	 * System.out.println(sb.toString()); if(stmt.executeUpdate(sb.toString())>0)
	 * result = true;
	 * 
	 * } catch(SQLException ex) { Log.writeExpt(ex, sb.toString() ); }
	 * catch(Exception ex) { Log.writeExpt(ex); } finally { sb = null; if (rs !=
	 * null) try { rs.close(); } catch(SQLException ex) {} if (stmt != null) try {
	 * stmt.close(); } catch(SQLException ex) {} if( dbconn != null) try {
	 * dbconn.close(); } catch(SQLException ex) {} } return result; }
	 * 
	 *//**
		 * ISSUE 의 사용 상태를 변경한다.
		 * 
		 * @param IssueDataBean
		 * @return
		 */
	/*
	 * public boolean updateIssueFlag(IssueBean isBean) { boolean result = false;
	 * 
	 * try{ dbconn = new DBconn(); dbconn.getDBCPConnection(); stmt =
	 * dbconn.createStatement(); String nextFalg = "Y"; String str =
	 * "SELECT I_USEYN FROM ISSUE WHERE I_SEQ IN ("+isBean.getI_seq()+")"; rs =
	 * stmt.executeQuery(str); if(rs.next()){
	 * if(rs.getString("I_USEYN").equals("Y")) nextFalg = "N"; else nextFalg = "Y";
	 * }
	 * 
	 * sb = new StringBuffer();
	 * sb.append("UPDATE ISSUE SET I_USEYN = '"+nextFalg+"' WHERE I_SEQ IN ("+isBean
	 * .getI_seq()+")  \n"); if(stmt.executeUpdate(sb.toString())>0) result = true;
	 * 
	 * } catch(SQLException ex) { Log.writeExpt(ex, sb.toString() ); }
	 * catch(Exception ex) { Log.writeExpt(ex); } finally { sb = null; if (rs !=
	 * null) try { rs.close(); } catch(SQLException ex) {} if (stmt != null) try {
	 * stmt.close(); } catch(SQLException ex) {} if( dbconn != null) try {
	 * dbconn.close(); } catch(SQLException ex) {} } return result; }
	 * 
	 * 
	 *//**
		 * ISSUE 등록하고 해당번호를 반환
		 * 
		 * @param IssueDataBean
		 * @return
		 *//*
			 * public boolean deleteIssue(IssueBean isBean) { boolean result = false;
			 * 
			 * try{ dbconn = new DBconn(); dbconn.getDBCPConnection(); stmt =
			 * dbconn.createStatement();
			 * 
			 * sb = new StringBuffer();
			 * sb.append("SELECT COUNT(*) AS CNT FROM ISSUE_TITLE WHERE I_SEQ IN ("+isBean.
			 * getI_seq()+")"); rs = stmt.executeQuery(sb.toString()); if(rs.next()){
			 * if(rs.getInt("CNT") == 0){ sb = new StringBuffer();
			 * sb.append(" DELETE FROM ISSUE WHERE I_SEQ IN ("+isBean.getI_seq()
			 * +")               \n"); if(stmt.executeUpdate(sb.toString())>0) { result =
			 * true; } }else{ result = false; } }
			 * 
			 * 
			 * } catch(SQLException ex) { Log.writeExpt(ex, sb.toString() ); }
			 * catch(Exception ex) { Log.writeExpt(ex); } finally { sb = null; if (rs !=
			 * null) try { rs.close(); } catch(SQLException ex) {} if (stmt != null) try {
			 * stmt.close(); } catch(SQLException ex) {} if( dbconn != null) try {
			 * dbconn.close(); } catch(SQLException ex) {} } return result; }
			 */

	/* *//**
			 * ISSUE 테이블을 조회하여 해당정보를 어레이로 반환.
			 * 
			 * @param pageNum  : 페이징번호 0일시 페이징처리 안함
			 * @param i_seq    : ISSUE.I_SEQ
			 * @param schKey   : ISSUE.I_TITLE
			 * @param schSdate : ISSUE.I_REGDATE
			 * @param schEdate : ISSUE.I_REGDATE
			 * @param useYn    : ISSUE.I_USEYN
			 * @return : 이슈검색리스트
			 */
	/*
	 * public ArrayList getIssueList( int pageNum, int rowCnt, String i_seq, String
	 * schKey, String schSdate, String schEdate, String useYN ) { ArrayList
	 * issueList = new ArrayList(); String tmpI_seq = null;
	 * 
	 * //리스트 시작, 끝번호 if(rowCnt>0) this.startNum = (pageNum-1)*rowCnt; this.endNum =
	 * rowCnt;
	 * 
	 * 
	 * try { dbconn = new DBconn(); dbconn.getDBCPConnection(); stmt =
	 * dbconn.createStatement();
	 * 
	 * sb = new StringBuffer();
	 * sb.append("		SELECT COUNT(I_SEQ) AS CNT 								\n"
	 * ); sb.
	 * append("		FROM ISSUE                                                      \n"
	 * ); sb.
	 * append("		WHERE 1=1														\n"
	 * ); if( !i_seq.equals("") ) { sb.append("		AND	I_SEQ IN ("
	 * +i_seq+")  									\n"); } if( !schKey.equals("") )
	 * { sb.append("		AND	I_TITLE LIKE '%"
	 * +schKey+"%'  							\n"); } if( !schSdate.equals("") &&
	 * !schEdate.equals("")) {
	 * sb.append("		AND	I_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"
	 * +schEdate+" 23:59:59'            	\n"); } if (!useYN.equals("")) {
	 * sb.append(" 	AND I_USEYN = '"
	 * +useYN+"'									    \n"); }
	 * 
	 * rs = stmt.executeQuery(sb.toString()); if ( rs.next() ) { totalIssueCnt =
	 * rs.getInt("CNT"); }
	 * 
	 * rs.close(); sb = new StringBuffer(); sb.
	 * append(" SELECT I_SEQ                                                         \n"
	 * ); sb.
	 * append("        ,I_TITLE                                                      \n"
	 * ); sb.
	 * append("        ,DATE_FORMAT(I_REGDATE,'%Y-%m-%d %H:%i:%s') AS I_REGDATE     \n"
	 * ); sb.
	 * append("        ,M_SEQ , I_USEYN                                                       \n"
	 * ); sb.
	 * append("        ,(SELECT M_NAME FROM MEMBER WHERE M_SEQ = I.M_SEQ) AS M_NAME  \n"
	 * ); sb.
	 * append("        ,(SELECT COUNT(1) FROM ISSUE_TITLE WHERE I_SEQ = I.I_SEQ) AS I_COUNT  \n"
	 * ); sb.
	 * append(" FROM ISSUE I                                                         \n"
	 * ); sb.
	 * append(" WHERE 1=1														     \n"
	 * ); if( !i_seq.equals("") ) { sb.append("		AND	I_SEQ IN ("
	 * +i_seq+")  									 \n"); } if( !schKey.equals("")
	 * ) { sb.append("		AND	I_TITLE LIKE '%"
	 * +schKey+"%'  							 \n"); } if( !schSdate.equals("") &&
	 * !schEdate.equals("")) {
	 * sb.append("		AND	I_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"
	 * +schEdate+" 23:59:59'            	 \n"); } if (!useYN.equals("")) {
	 * sb.append(" 	AND I_USEYN = '"
	 * +useYN+"'									     \n"); }
	 * sb.append(" ORDER BY I_REGDATE DESC 		                                     \n"
	 * ); if(pageNum>0){ sb.append(" LIMIT   "+startNum+","+
	 * endNum+"                                      \n"); }
	 * System.out.println(sb.toString()); rs = stmt.executeQuery(sb.toString());
	 * 
	 * while(rs.next()){ isBean = new IssueBean();
	 * isBean.setI_seq(rs.getString("I_SEQ"));
	 * isBean.setI_title(rs.getString("I_TITLE"));
	 * isBean.setI_regdate(rs.getString("I_REGDATE"));
	 * isBean.setM_seq(rs.getString("M_SEQ"));
	 * isBean.setI_useyn(rs.getString("I_USEYN"));
	 * isBean.setM_name(rs.getString("M_NAME"));
	 * isBean.setI_count(rs.getString("I_COUNT")); issueList.add(isBean); }
	 * 
	 * } catch(SQLException ex) { Log.writeExpt(ex, sb.toString() ); }
	 * catch(Exception ex) { Log.writeExpt(ex); } finally { if (rs != null) try {
	 * rs.close(); } catch(SQLException ex) {} if (stmt != null) try { stmt.close();
	 * } catch(SQLException ex) {} if (dbconn != null) try { dbconn.close(); }
	 * catch(SQLException ex) {} }
	 * 
	 * return issueList; }
	 * 
	 *//**
		 * ISSUE_TITLE 등록하고 해당번호를 반환
		 * 
		 * @param IssueTitleBean
		 * @return
		 */
	/*
	 * public boolean insertIssueTitle(IssueTitleBean itBean) { String[] arrTemp;
	 * String[] arrTypeCode ; boolean result = false; String insertNum = null; try{
	 * dbconn = new DBconn(); dbconn.getDBCPConnection(); stmt =
	 * dbconn.createStatement();
	 * 
	 * sb = new StringBuffer();
	 * sb.append(" INSERT INTO ISSUE_TITLE(	                      		   \n");
	 * sb.append("                         IT_TITLE                           \n");
	 * sb.append("                         ,I_SEQ                             \n");
	 * sb.append("                         ,IT_REGDATE                        \n");
	 * sb.append("                         ,IT_USEYN                          \n");
	 * sb.append("                         ,M_SEQ)                            \n");
	 * sb.append(" VALUES(                                                    \n");
	 * sb.append("        '"+itBean.getIt_title()+"'                          \n");
	 * sb.append("        ,"+itBean.getI_seq()+"                              \n");
	 * sb.append("        ,'"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"'     \n");
	 * sb.append("        ,'"+itBean.getIt_useyn()+"'                         \n");
	 * sb.append("        ,"+itBean.getM_seq()+"                              \n");
	 * sb.append(" )                                                          \n");
	 * System.out.println(sb.toString()); if(stmt.executeUpdate(sb.toString())>0)
	 * result = true;
	 * 
	 * } catch(SQLException ex) { Log.writeExpt(ex, sb.toString() ); }
	 * catch(Exception ex) { Log.writeExpt(ex); } finally { sb = null; if (rs !=
	 * null) try { rs.close(); } catch(SQLException ex) {} if (stmt != null) try {
	 * stmt.close(); } catch(SQLException ex) {} if( dbconn != null) try {
	 * dbconn.close(); } catch(SQLException ex) {} } return result; }
	 * 
	 *//**
		 * ISSUE_TITLE 수정
		 * 
		 * @param it_seq : ISSUE_TITLE.IT_SEQ
		 * @return
		 */
	/*
	 * public boolean updateIssueTitle(IssueTitleBean itBean) { boolean result =
	 * false;
	 * 
	 * try{ dbconn = new DBconn(); dbconn.getDBCPConnection(); stmt =
	 * dbconn.createStatement();
	 * 
	 * sb = new StringBuffer(); sb = new StringBuffer();
	 * sb.append("UPDATE ISSUE_TITLE SET IT_TITLE = '"+itBean.getIt_title()
	 * +"'   WHERE IT_SEQ IN ("+itBean.getIt_seq()+")     \n");
	 * System.out.println(sb.toString()); if(stmt.executeUpdate(sb.toString())>0)
	 * result = true;
	 * 
	 * } catch(SQLException ex) { Log.writeExpt(ex, sb.toString() ); }
	 * catch(Exception ex) { Log.writeExpt(ex); } finally { sb = null; if (rs !=
	 * null) try { rs.close(); } catch(SQLException ex) {} if (stmt != null) try {
	 * stmt.close(); } catch(SQLException ex) {} if( dbconn != null) try {
	 * dbconn.close(); } catch(SQLException ex) {} } return result; }
	 * 
	 *//**
		 * ISSUE_TITLE의 useyn 수정
		 * 
		 * @param it_seq : ISSUE_TITLE.IT_SEQ
		 * @return
		 */
	/*
	 * public boolean updateIssueTitleFlag(IssueTitleBean itBean) { boolean result =
	 * false;
	 * 
	 * try{ dbconn = new DBconn(); dbconn.getDBCPConnection(); stmt =
	 * dbconn.createStatement();
	 * 
	 * String nextFalg = "Y"; String str =
	 * "SELECT IT_USEYN FROM ISSUE_TITLE WHERE IT_SEQ IN ("+itBean.getIt_seq()+")";
	 * rs = stmt.executeQuery(str); if(rs.next()){
	 * if(rs.getString("IT_USEYN").equals("Y")) nextFalg = "N"; else nextFalg = "Y";
	 * }
	 * 
	 * 
	 * sb = new StringBuffer(); sb = new StringBuffer();
	 * sb.append("UPDATE ISSUE_TITLE SET IT_USEYN = '"
	 * +nextFalg+"'   WHERE IT_SEQ IN ("+itBean.getIt_seq()+")     \n");
	 * if(stmt.executeUpdate(sb.toString())>0) result = true;
	 * 
	 * } catch(SQLException ex) { Log.writeExpt(ex, sb.toString() ); }
	 * catch(Exception ex) { Log.writeExpt(ex); } finally { sb = null; if (rs !=
	 * null) try { rs.close(); } catch(SQLException ex) {} if (stmt != null) try {
	 * stmt.close(); } catch(SQLException ex) {} if( dbconn != null) try {
	 * dbconn.close(); } catch(SQLException ex) {} } return result; }
	 * 
	 * 
	 *//**
		 * ISSUE_TITLE 삭제
		 * 
		 * @param it_seq : ISSUE_TITLE.IT_SEQ
		 * @return
		 */

	/*
	 * public boolean deleteIssueTitle(IssueTitleBean itBean) { boolean result =
	 * false;
	 * 
	 * try{ dbconn = new DBconn(); dbconn.getDBCPConnection(); stmt =
	 * dbconn.createStatement();
	 * 
	 * sb = new StringBuffer(); sb = new StringBuffer();
	 * sb.append(" DELETE FROM ISSUE_TITLE WHERE IT_SEQ IN ("+itBean.getIt_seq()
	 * +")     \n"); System.out.println(sb.toString());
	 * if(stmt.executeUpdate(sb.toString())>0) result = true;
	 * 
	 * } catch(SQLException ex) { Log.writeExpt(ex, sb.toString() ); }
	 * catch(Exception ex) { Log.writeExpt(ex); } finally { sb = null; if (rs !=
	 * null) try { rs.close(); } catch(SQLException ex) {} if (stmt != null) try {
	 * stmt.close(); } catch(SQLException ex) {} if( dbconn != null) try {
	 * dbconn.close(); } catch(SQLException ex) {} } return result; }
	 * 
	 *//**
		 * ISSUE_TITLE 테이블을 조회하여 해당정보를 어레이로 반환.
		 * 
		 * @param pageNum  : 페이징번호 0일시 페이징처리 안함
		 * @param it_seq   : ISSUE_TITLE.IT_SEQ
		 * @param schKey   : ISSUE_TITLE.IT_TITLE
		 * @param schSdate : ISSUE_TITLE.IT_REGDATE
		 * @param schEdate : ISSUE_TITLE.IT_REGDATE
		 * @param useYn    : ISSUE.IT_USEYN
		 * @return : 이슈타이틀검색리스트
		 *//*
			 * public ArrayList getIssueTitleList( int pageNum, int rowCnt, String i_seq,
			 * String it_seq, String schKey, String schSdate, String schEdate, String useYN
			 * ) { ArrayList issueTitleList = new ArrayList(); String tmpI_seq = null;
			 * 
			 * //리스트 시작, 끝번호 if(rowCnt>0) this.startNum = (pageNum-1)*rowCnt; this.endNum =
			 * rowCnt;
			 * 
			 * 
			 * try { dbconn = new DBconn(); dbconn.getDBCPConnection(); stmt =
			 * dbconn.createStatement();
			 * 
			 * sb = new StringBuffer(); sb.
			 * append("		SELECT COUNT(IT_SEQ) AS CNT 								\n"
			 * ); sb.
			 * append("		FROM ISSUE_TITLE                                                      \n"
			 * ); sb.
			 * append("		WHERE 1=1														\n"
			 * ); if( !i_seq.equals("") ) { sb.append("		AND	I_SEQ IN ("
			 * +i_seq+")  									\n"); } if( !it_seq.equals("") )
			 * { sb.append("		AND	IT_SEQ IN ("
			 * +it_seq+")  									\n"); } if( !schKey.equals("") )
			 * { sb.append("		AND	IT_TITLE LIKE '%"
			 * +schKey+"%'  							\n"); } if( !schSdate.equals("") &&
			 * !schEdate.equals("")) {
			 * sb.append("		AND	IT_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"
			 * +schEdate+" 23:59:59'            	\n"); } if (!useYN.equals("")) {
			 * sb.append(" 	AND IT_USEYN = '"
			 * +useYN+"'									    \n"); }
			 * 
			 * rs = stmt.executeQuery(sb.toString()); if ( rs.next() ) { totalIssueTitleCnt
			 * = rs.getInt("CNT"); }
			 * 
			 * rs.close(); sb = new StringBuffer(); sb.
			 * append(" SELECT IT_SEQ                                                         \n"
			 * ); sb.
			 * append("        ,I_SEQ                                                      \n"
			 * ); sb.
			 * append("        ,IT_TITLE , IT_USEYN                                                     \n"
			 * ); sb.
			 * append("        ,DATE_FORMAT(IT_REGDATE,'%Y-%m-%d %H:%i:%s') AS IT_REGDATE     \n"
			 * ); sb.
			 * append("        ,M_SEQ                                                         \n"
			 * ); sb.
			 * append("        ,(SELECT M_NAME FROM MEMBER WHERE M_SEQ = IT.M_SEQ) AS M_NAME  \n"
			 * ); sb.
			 * append(" FROM ISSUE_TITLE IT                                                   \n"
			 * ); sb.
			 * append(" WHERE 1=1														      \n"
			 * ); if( !i_seq.equals("") ) { sb.append("		AND	I_SEQ IN ("
			 * +i_seq+")  									\n"); } if( !it_seq.equals("") )
			 * { sb.append("		AND	IT_SEQ IN ("
			 * +it_seq+")  									\n"); } if( !schKey.equals("") )
			 * { sb.append("		AND	IT_TITLE LIKE '%"
			 * +schKey+"%'  							 \n"); } if( !schSdate.equals("") &&
			 * !schEdate.equals("")) {
			 * sb.append("		AND	IT_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"
			 * +schEdate+" 23:59:59'            	 \n"); } if (!useYN.equals("")) {
			 * sb.append(" 	AND IT_USEYN = '"
			 * +useYN+"'									     \n"); } sb.
			 * append(" ORDER BY IT_REGDATE DESC 		                                     \n"
			 * ); if(pageNum>0){ sb.append(" LIMIT   "+startNum+","+
			 * endNum+"                                      \n"); }
			 * System.out.println(sb.toString()); rs = stmt.executeQuery(sb.toString());
			 * 
			 * while(rs.next()){ itBean = new IssueTitleBean();
			 * itBean.setIt_seq(rs.getString("IT_SEQ"));
			 * itBean.setI_seq(rs.getString("I_SEQ"));
			 * itBean.setIt_title(rs.getString("IT_TITLE"));
			 * itBean.setIt_useyn(rs.getString("IT_USEYN"));
			 * itBean.setIt_regdate(rs.getString("IT_REGDATE"));
			 * itBean.setM_seq(rs.getString("M_SEQ"));
			 * itBean.setM_name(rs.getString("M_NAME")); issueTitleList.add(itBean); }
			 * 
			 * } catch(SQLException ex) { Log.writeExpt(ex, sb.toString() ); }
			 * catch(Exception ex) { Log.writeExpt(ex); } finally { if (rs != null) try {
			 * rs.close(); } catch(SQLException ex) {} if (stmt != null) try { stmt.close();
			 * } catch(SQLException ex) {} if (dbconn != null) try { dbconn.close(); }
			 * catch(SQLException ex) {} }
			 * 
			 * return issueTitleList; }
			 */

	public int IssueRegistCheck(String md_pseqs) {
		int result = 0;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();
			sb.append(" SELECT COUNT(*) AS CNT FROM ISSUE_DATA WHERE MD_PSEQ IN (" + md_pseqs + ")	\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				result = rs.getInt("CNT");
			}
		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
			if (sb != null)
				sb = null;
		}
		return result;
	}

	/**
	 * md_seq에 대한 이슈등록 여부
	 * 
	 * @param md_seq
	 * @return
	 */
	public int IssueRegistCheck_v2(String md_seq) {
		int result = 0;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();
			sb.append(" SELECT COUNT(*) AS CNT FROM ISSUE_DATA WHERE MD_SEQ = " + md_seq + "	\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				result = rs.getInt("CNT");
			}
			if (result > 0) {
				Log.writeExpt("IssueCheck", "중복카운트_md_seq:[" + md_seq + "] count:[" + result + "]");
			}
		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
			if (sb != null)
				sb = null;
		}
		return result;
	}

	/**
	 * 
	 * @param typeCode : 타입,코드
	 * @param type     : 리턴받을 코드의 타입 값
	 * @return
	 */
	public String getTypeCode(String typeCode, String type) {
		String ret = "";
		String[] arrTemp;
		String[] arrTypeCode;

		if (typeCode != null && !typeCode.equals("")) {
			arrTemp = typeCode.split("@");
			for (int i = 0; i < arrTemp.length; i++) {
				if (!arrTemp[i].equals("")) {
					arrTypeCode = arrTemp[i].split(",");
					if (type.equals(arrTypeCode[0])) {
						ret = arrTypeCode[1];
					}
				}
			}
		}
		return ret;
	}

	public String GetRegType(String md_seq) {
		String result = "";
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();
			sb.append(" SELECT REG_TYPE FROM ISSUE_DATA WHERE MD_SEQ = "+md_seq+"	\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				result = rs.getString("REG_TYPE");
			}
		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
			if (sb != null)
				sb = null;
		}
		return result;
	}

	public String insertIssueData(String mode, IssueDataBean idBean, IssueCommentBean icBean, String typeCodes,
			String typeCodesTrend, String typeCodesInfo) {
		return insertIssueData(mode, idBean, icBean, typeCodes, typeCodesTrend, typeCodesInfo, "");
	}

	/**
	 * ISSUE_DATA, ISSUE_CODE, ISSUE_COMMENT 등록하고 이슈번호 리턴
	 * 
	 * @param IssueDataBean    :이슈데이터빈
	 * @param IssueCommentBean :이슈코멘트빈
	 * @param typeCode         :분류체계
	 * @return
	 */
	public String insertIssueData(String mode, IssueDataBean idBean, IssueCommentBean icBean, String typeCodes,
			String typeCodesTrend, String typeCodesInfo, String mode2) {

		String[] arrTypeCode = null;
		String[] arrTemp = null;
		String[] arrTemp2 = null;
		String[] arrTemp3 = null;

		String[] arrRelKeyType = null;
		boolean result = false;
		String insertNum = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			dbconn.TransactionStart();

			sb = new StringBuffer();
			sb.append(" INSERT INTO ISSUE_DATA(MD_SEQ                          		\n");
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
			sb.append("                   ,REG_TYPE                             	\n");
			sb.append("                   ,L_ALPHA) 								\n");
			sb.append(" VALUES(                                                   	\n");
			sb.append("        ?					                            	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append(" )                                                         	\n");

			pstmt = dbconn.createPStatement(sb.toString());
			int idx = 1;
			pstmt.setString(idx++, idBean.getMd_seq());
			pstmt.setString(idx++, idBean.getI_seq());
			pstmt.setString(idx++, idBean.getIt_seq());
			pstmt.setString(idx++, idBean.getS_seq());
			pstmt.setString(idx++, idBean.getSg_seq());
			pstmt.setString(idx++, idBean.getMd_site_name());
			pstmt.setString(idx++, idBean.getMd_site_menu());
			pstmt.setString(idx++, idBean.getMd_date());
			pstmt.setString(idx++, idBean.getId_title());
			pstmt.setString(idx++, idBean.getId_url());
			pstmt.setString(idx++, idBean.getId_writter());
			pstmt.setString(idx++, idBean.getId_content());
			pstmt.setString(idx++, idBean.getId_regdate());
			pstmt.setString(idx++, idBean.getId_mailyn());
			pstmt.setString(idx++, idBean.getId_useyn());
			pstmt.setString(idx++, idBean.getMd_same_ct());
			pstmt.setString(idx++, idBean.getM_seq());
			pstmt.setString(idx++, idBean.getMd_type());
			pstmt.setString(idx++, idBean.getMd_pseq());
			pstmt.setString(idx++, idBean.getId_reportyn());
			pstmt.setString(idx++, idBean.getTemp1());
			pstmt.setString(idx++, idBean.getL_alpha());

			System.out.println(sb.toString());

			String sg_seq = "";

			if (pstmt.executeUpdate() > 0) {
				sb = new StringBuffer();
				sb.append(
						" SELECT ID_SEQ, (SELECT B.IC_CODE																				  \n");
				sb.append(
						"                   FROM IC_S_RELATION A																			  \n");
				sb.append(
						"                      , ISSUE_CODE B 																			  \n");
				sb.append(
						"                  WHERE A.IC_SEQ = B.IC_SEQ																		  \n");
				sb.append("                    AND A.S_SEQ = SG_SEQ) AS SG_SEQ FROM ISSUE_DATA WHERE MD_SEQ = "
						+ idBean.getMd_seq() + "   		  \n");
				System.out.println(sb.toString());
				stmt = dbconn.createStatement();
				rs = stmt.executeQuery(sb.toString());
				if (rs.next()) {
					insertNum = rs.getString("ID_SEQ");
					sg_seq = rs.getString("SG_SEQ");
				}

				sb = new StringBuffer();
				sb.append(" UPDATE " + mode2 + "IDX SET ISSUE_CHECK ='Y'                        \n");
				sb.append(" WHERE MD_SEQ = " + idBean.getMd_seq() + "                  \n");
				stmt.executeUpdate(sb.toString());
			}

			Boolean check = false;

			if (typeCodes != null && !typeCodes.equals("")) {
				arrTypeCode = typeCodes.split("@");
				for (int i = 0; i < arrTypeCode.length; i++) {
					if (!arrTypeCode[i].equals("")) {
						arrTemp = arrTypeCode[i].split(",");

						if (arrTemp[0].equals("6")) {
							check = true;
						}

						sb = new StringBuffer();
						sb.append("INSERT INTO ISSUE_DATA_CODE		\n");
						sb.append("VALUES ( 						\n");
						sb.append("        " + insertNum + " 		\n");
						sb.append("        ," + arrTemp[0] + "		\n");
						sb.append("        ," + arrTemp[1] + "  	\n");
						sb.append("        ) 						\n");
						System.out.println(sb.toString());
						if (stmt.executeUpdate(sb.toString()) > 0)
							result = true;

					}
				}

				if (!check) {

					sb = new StringBuffer();

					sb = new StringBuffer();
					sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
					sb.append("VALUES ( 						\n");
					sb.append("        " + insertNum + " 		\n");
					sb.append("        ,6						\n");
					sb.append("        ," + sg_seq + "  			\n");
					sb.append("        ) 						\n");
					if (stmt.executeUpdate(sb.toString()) > 0)
						result = true;
				}

			}

			// 각 대단위 분류체계 하위 속성 저장

			// 세부 성향 저장
			if (typeCodesTrend != null && !typeCodesTrend.equals("")) {

				arrTypeCode = typeCodesTrend.split("@");
				for (int i = 0; i < arrTypeCode.length; i++) {
					if (!arrTypeCode[i].equals("")) {
						arrTemp = arrTypeCode[i].split(",");

						sb = new StringBuffer();
						sb.append("INSERT INTO ISSUE_DATA_CODE_DETAIL	 	\n");
						sb.append("VALUES ( 								\n");
						sb.append("        " + insertNum + " 				\n");
						sb.append("        ," + arrTemp[1] + "				\n");
						sb.append("        ," + arrTemp[2] + "  			\n");
						sb.append("        ," + arrTemp[0] + "  			\n");
						sb.append("        ,0					  			\n");
						sb.append("        ) 								\n");
						System.out.println(sb.toString());
						if (stmt.executeUpdate(sb.toString()) > 0)
							result = true;

						if (mode.equals("insert") || mode.equals("multi")) {
							sb = new StringBuffer();
							sb.append("INSERT INTO ISSUE_DATA_CODE_DETAIL	 	\n");
							sb.append("VALUES ( 								\n");
							sb.append("        " + insertNum + " 				\n");
							sb.append("        ,20								\n");
							sb.append("        ," + arrTemp[2] + "  			\n");
							sb.append("        ," + arrTemp[0] + "  			\n");
							sb.append("        ,0					  			\n");
							sb.append("        ) 								\n");
							System.out.println(sb.toString());
							if (stmt.executeUpdate(sb.toString()) > 0)
								result = true;
						}
					}
				}

			}

			System.out.println("정보 속성 저장");
			// 정보 속성 저장
			if (typeCodesInfo != null && !typeCodesInfo.equals("")) {
				arrTypeCode = typeCodesInfo.split("@");
				for (int i = 0; i < arrTypeCode.length; i++) {
					if (!arrTypeCode[i].equals("")) {
						arrTemp = arrTypeCode[i].split(",");

						sb = new StringBuffer();
						sb.append("INSERT INTO ISSUE_DATA_CODE_DETAIL	 	\n");
						sb.append("VALUES ( 								\n");
						sb.append("        " + insertNum + " 				\n");
						sb.append("        ," + arrTemp[1] + "				\n");
						sb.append("        ," + arrTemp[2] + "  			\n");
						sb.append("        ," + arrTemp[0] + "  			\n");
						sb.append("        ,0					  			\n");
						sb.append("        ) 								\n");
						System.out.println(sb.toString());
						if (stmt.executeUpdate(sb.toString()) > 0)
							result = true;

					}
				}

			}

			// 지면 여부 저장 2018.02.06 고기범
			/*
			 * System.out.println("지면 여부 저장"); String newsVal = idBean.getId_news();
			 * 
			 * if(newsVal!=null && !newsVal.equals("")){ arrTypeCode = newsVal.split(",");
			 * 
			 * sb = new StringBuffer();
			 * sb.append("INSERT INTO ISSUE_DATA_NEWS	 			\n");
			 * sb.append("VALUES ( 								\n"); sb.append("        " +
			 * insertNum + " 				\n"); sb.append("        ," + arrTypeCode[0] +
			 * "			\n"); sb.append("        ," + arrTypeCode[1] + "  		\n");
			 * sb.append("        ) 								\n");
			 * System.out.println(sb.toString()); if(stmt.executeUpdate(sb.toString())>0)
			 * result = true;
			 * 
			 * }
			 */

			// 연관키워드 저장
			if (!idBean.getRelationkeys().equals("")) {
				arrTemp = idBean.getRelationkeys().split("@");

				for (int i = 0; i < arrTemp.length; i++) {
					arrRelKeyType = new String[arrTemp.length];

					if (!arrTemp[i].equals("") || arrTemp[i] != null) {
						arrTemp2 = arrTemp[i].split("/");
						arrRelKeyType[i] = arrTemp2[0];
						if (arrTemp2.length > 1) {
							arrTemp3 = arrTemp2[1].split(",");
						} else {
							arrTemp3 = new String[0];
						}
						for (int j = 0; j < arrTemp3.length; j++) {
							sb = new StringBuffer();
							StringBuffer sb_insertMap = new StringBuffer();
							StringBuffer sb_insertKey = new StringBuffer();

							sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='"
									+ su.dbString(arrTemp3[j]) + "' AND RK_USE = 'Y' \n");
							rs = stmt.executeQuery(sb.toString());
							if (rs.next()) {
								sb_insertMap.append(
										" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
												+ insertNum + "," + rs.getInt("RK_SEQ") + "," + arrRelKeyType[i]
												+ ", 0)  \n");
								System.out.println(sb_insertMap.toString());
								stmt.executeUpdate(sb_insertMap.toString());
							} else {
								sb_insertKey.append(" INSERT INTO RELATION_KEYWORD_R (RK_NAME) VALUES ('"
										+ su.dbString(arrTemp3[j]) + "')  \n");
								System.out.println(sb_insertKey.toString());
								stmt.executeUpdate(sb_insertKey.toString());
								sb = new StringBuffer();
								rs = null;
								sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='"
										+ su.dbString(arrTemp3[j]) + "' AND RK_USE = 'Y'  \n");
								rs = stmt.executeQuery(sb.toString());
								if (rs.next()) {

									sb_insertMap.append(
											" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
													+ insertNum + "," + rs.getInt("RK_SEQ") + "," + arrRelKeyType[i]
													+ ", 0)   \n");
									System.out.println(sb_insertMap.toString());
									stmt.executeUpdate(sb_insertMap.toString());
									result = true;
								}
							}
						}
					}
				}
			}

			/*
			 * //통계용 데이터 저장 sb = new StringBuffer();
			 * sb.append("INSERT INTO ANA_ISSUE_DATA (ID_SEQ, MD_DATE) VALUES ("+insertNum+
			 * ",'"+idBean.getMd_date()+"')	\n"); if(stmt.executeUpdate(sb.toString())>0)
			 * result = true;
			 */
			dbconn.Commit();

		} catch (SQLException ex) {
			// deleteErrorIssueData(insertNum, idBean.getMd_seq());
			Log.writeExpt(ex, sb.toString());
			Log.writeExpt("IssueCheck", ex, sb.toString());
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} catch (Exception ex) {
			// deleteErrorIssueData(insertNum, idBean.getMd_seq());
			Log.writeExpt(ex);
			Log.writeExpt("IssueCheck", ex);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return insertNum;
	}

	/**
	 * SKT 이슈등록 NEW
	 * 
	 * @param mode
	 * @param idBean
	 * @param typeCodes
	 * @param typeCodesTrend
	 * @param typeCodesInfo
	 * @param mode2
	 * @return
	 */
	public String insertIssueData_V2(String mode, IssueDataBean idBean, String typeCodes, String typeCodesTrend,
			String typeCodesInfo, String mode2) {

		ArrayList<String> HashTagList = new ArrayList<String>();
		String insertNum = null;
		boolean result = false;

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			dbconn.TransactionStart();

			sb = new StringBuffer();
			sb.append(" INSERT INTO ISSUE_DATA(MD_SEQ                          		\n");
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
			sb.append("                   ,REG_TYPE                             	\n");
			sb.append("                   ,L_ALPHA 									\n");
			sb.append("                   ,ID_CLOUT	 								\n");
			sb.append("                   ,ID_SENTI	 								\n");
			//sb.append("                   ,MD_WRITER	 							\n");
			//sb.append("                   ,ID_TRANS_USEYN	 						\n");
			//sb.append("                   ,ID_REPLYCOUNT	 						\n");
			sb.append("                   ) 										\n");
			sb.append(" VALUES(                                                   	\n");
			sb.append("        ?					                            	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			//sb.append("        ,?					                             	\n");
			//sb.append("        ,?					                             	\n");
			//sb.append("        ,?					                             	\n");
			sb.append(" )                                                         	\n");

			pstmt = dbconn.createPStatement(sb.toString());
			int idx = 1;
			pstmt.setString(idx++, idBean.getMd_seq());
			pstmt.setString(idx++, idBean.getI_seq());
			pstmt.setString(idx++, idBean.getIt_seq());
			pstmt.setString(idx++, idBean.getS_seq());
			pstmt.setString(idx++, idBean.getSg_seq());
			pstmt.setString(idx++, idBean.getMd_site_name());
			pstmt.setString(idx++, idBean.getMd_site_menu());
			pstmt.setString(idx++, idBean.getMd_date());
			pstmt.setString(idx++, idBean.getId_title());
			pstmt.setString(idx++, idBean.getId_url());
			pstmt.setString(idx++, idBean.getId_writter());
			pstmt.setString(idx++, idBean.getId_content());
			pstmt.setString(idx++, idBean.getId_regdate());
			pstmt.setString(idx++, idBean.getId_mailyn());
			pstmt.setString(idx++, idBean.getId_useyn());
			pstmt.setString(idx++, idBean.getMd_same_ct());
			pstmt.setString(idx++, idBean.getM_seq());
			pstmt.setString(idx++, idBean.getMd_type());
			pstmt.setString(idx++, idBean.getMd_pseq());
			pstmt.setString(idx++, idBean.getId_reportyn());
			pstmt.setString(idx++, idBean.getTemp1());
			pstmt.setString(idx++, idBean.getL_alpha());
			pstmt.setString(idx++, idBean.getId_clout());
			pstmt.setString(idx++, idBean.getId_senti());
			//pstmt.setString(idx++, idBean.getMd_writer());
			//pstmt.setString(idx++, idBean.getId_trans_useyn());
			//pstmt.setString(idx++, idBean.getReply_count());

			String sg_seq = "";

			if (pstmt.executeUpdate() > 0) {

				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					insertNum = rs.getString(1);
				}				
				
				sb = new StringBuffer();
				sb.append(" UPDATE " + mode2 + " META SET ISSUE_CHECK ='Y'                        \n");
				sb.append(" WHERE MD_SEQ = " + idBean.getMd_seq() + "                  \n");
				System.out.println(sb.toString());
				pstmt.executeUpdate(sb.toString());

			}
			
			//인스타그램일 때, 해시태그 저장
			if(mode.equals("insert") || mode.equals("multi") || mode.equals("new")) {
				if("109".equals(idBean.getSg_seq())) {
					HashTagList = getHashTags(idBean.getId_content());
					insertIssueHashTag(Integer.parseInt(insertNum),HashTagList);
				}
			}
			
			String[] arrTypeCode = null;
			String[] arrTemp = null;
			// 각 항목들 저장
			if (typeCodes != null && !typeCodes.equals("")) {
				arrTypeCode = typeCodes.split("@");
				for (String typeCode : arrTypeCode) {
					if (!"".equals(typeCode)) {
						arrTemp = typeCode.split(",");
						sb = new StringBuffer();
						sb.append("INSERT INTO ISSUE_DATA_CODE		\n");
						sb.append("VALUES ( 						\n");
						sb.append("        " + insertNum + " 		\n");
						sb.append("        ," + arrTemp[0] + "		\n");
						sb.append("        ," + arrTemp[1] + "  	\n");
						sb.append("        ) 						\n");
						System.out.println(sb.toString());
						if (pstmt.executeUpdate(sb.toString()) > 0)
							result = true;
					}
				}
			}

			String[] arrRelName = null;
			String rkSeq = "0";
			String RelCode = "";			
			// 연관키워드 저장
			if (null != (idBean.getRelationkeys()) && !"".equals(idBean.getRelationkeys())) {
				
				RelCode = idBean.getRelationkeysCode();
				arrRelName = idBean.getRelationkeys().split("@");
				for (String relKey : arrRelName) {					
					sb = new StringBuffer();
					sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + relKey + "' \n");
					System.out.println(sb.toString());
					rs = pstmt.executeQuery(sb.toString());

					if (rs.next()) {
						rkSeq = rs.getString("RK_SEQ");

						sb = null;
						sb = new StringBuffer();
						if(!"".equals(RelCode)) {
							sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
									+ insertNum + "," + rs.getInt("RK_SEQ") + ","+RelCode.split(",")[0]+","+RelCode.split(",")[1]+")  \n");
						}else {
							sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("
									+ insertNum + "," + rs.getInt("RK_SEQ") + ")  \n");
							
						}
						System.out.println(sb.toString());
						pstmt.executeUpdate(sb.toString());
						
					} else {
						sb = null;
						sb = new StringBuffer();
						sb.append(" INSERT INTO RELATION_KEYWORD_R (RK_NAME) VALUES ('" + relKey + "')  \n");
						System.out.println(sb.toString());
						pstmt.executeUpdate(sb.toString());
						
						sb = null;
						sb = new StringBuffer();
						rs = null;
						sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + relKey + "' \n");
						rs = pstmt.executeQuery(sb.toString());

						if (rs.next()) {
							rkSeq = rs.getString("RK_SEQ");

							sb = null;
							sb = new StringBuffer();
							if(!"".equals(RelCode)) {
								sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
										+ insertNum + "," + rs.getInt("RK_SEQ") + ","+RelCode.split(",")[0]+","+RelCode.split(",")[1]+")  \n");
							}else {
								sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("
										+ insertNum + "," + rs.getInt("RK_SEQ") + ")  \n");								
							}
							System.out.println(sb.toString());
							pstmt.executeUpdate(sb.toString());
							
							result = true;
						}
					}
				}
			}
	
			// 통계용 데이터 저장
			sb = new StringBuffer();
			sb.append("INSERT INTO ANA_ISSUE_DATA (ID_SEQ, MD_DATE) VALUES (" + insertNum + ",'"
					+ du.getDate(idBean.getMd_date(), "yyyyMMdd") + "')	\n");
			if (pstmt.executeUpdate(sb.toString()) > 0)
				result = true;

			dbconn.Commit();
		} catch (SQLException ex) {
			Log.writeExpt(ex);
			Log.writeExpt("IssueCheck", ex);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} catch (Exception ex) {
			Log.writeExpt(ex);
			Log.writeExpt("IssueCheck", ex);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return insertNum;
	}

	public String insertIssueData_V2(String mode, IssueDataBean idBean, String typeCodes, String typeCodesTrend,
			String typeCodesInfo, String mode2, String submode) {

		ArrayList<String> HashTagList = new ArrayList<String>();
		String insertNum = null;
		boolean result = false;

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			dbconn.TransactionStart();

			sb = new StringBuffer();
			sb.append(" INSERT INTO ISSUE_DATA(MD_SEQ                          		\n");
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
			sb.append("                   ,REG_TYPE                             	\n");
			sb.append("                   ,L_ALPHA 									\n");
			sb.append("                   ,ID_CLOUT	 								\n");
			sb.append("                   ,ID_SENTI	 								\n");
			sb.append("                   ,MD_WRITER	 							\n");
			//sb.append("                   ,ID_TRANS_USEYN	 						\n");
			//sb.append("                   ,ID_REPLYCOUNT	 						\n");
			sb.append("                   ) 										\n");
			sb.append(" VALUES(                                                   	\n");
			sb.append("        ?					                            	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			sb.append("        ,?					                             	\n");
			//sb.append("        ,?					                             	\n");
			//sb.append("        ,?					                             	\n");
			sb.append(" )                                                         	\n");

			pstmt = dbconn.createPStatement(sb.toString());
			int idx = 1;
			pstmt.setString(idx++, idBean.getMd_seq());
			pstmt.setString(idx++, idBean.getI_seq());
			pstmt.setString(idx++, idBean.getIt_seq());
			pstmt.setString(idx++, idBean.getS_seq());
			pstmt.setString(idx++, idBean.getSg_seq());
			pstmt.setString(idx++, idBean.getMd_site_name());
			pstmt.setString(idx++, idBean.getMd_site_menu());
			pstmt.setString(idx++, idBean.getMd_date());
			pstmt.setString(idx++, idBean.getId_title());
			pstmt.setString(idx++, idBean.getId_url());
			pstmt.setString(idx++, idBean.getId_writter());
			pstmt.setString(idx++, idBean.getId_content());
			pstmt.setString(idx++, idBean.getId_regdate());
			pstmt.setString(idx++, idBean.getId_mailyn());
			pstmt.setString(idx++, idBean.getId_useyn());
			pstmt.setString(idx++, idBean.getMd_same_ct());
			pstmt.setString(idx++, idBean.getM_seq());
			pstmt.setString(idx++, idBean.getMd_type());
			pstmt.setString(idx++, idBean.getMd_pseq());
			pstmt.setString(idx++, idBean.getId_reportyn());
			pstmt.setString(idx++, idBean.getTemp1());
			pstmt.setString(idx++, idBean.getL_alpha());
			pstmt.setString(idx++, idBean.getId_clout());
			pstmt.setString(idx++, idBean.getId_senti());
			pstmt.setString(idx++, idBean.getMd_writer());
			//pstmt.setString(idx++, idBean.getId_trans_useyn());
			//pstmt.setString(idx++, idBean.getReply_count());

			String sg_seq = "";

			if (pstmt.executeUpdate() > 0) {

				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					insertNum = rs.getString(1);
				}

				/*
				 * sb = new StringBuffer();
				 * sb.append(" SELECT ID_SEQ, SG_SEQ  FROM ISSUE_DATA WHERE MD_SEQ = "+idBean.
				 * getMd_seq()+"   		  \n");
				 * 
				 * System.out.println(sb.toString()); stmt = dbconn.createStatement(); rs =
				 * stmt.executeQuery(sb.toString()); if(rs.next()){ insertNum =
				 * rs.getString("ID_SEQ"); sg_seq = rs.getString("SG_SEQ"); }
				 */

				String main_tb = "";
				String sub_tb = "";
				
				if(submode.equals("domain")) {
					main_tb = "SECTION";
					sub_tb = "META";
				} else {
					main_tb = "META";
					sub_tb = "SECTION";
				}	
				
				sb = new StringBuffer();
				sb.append(" UPDATE " + mode2 + " "+main_tb+"  SET ISSUE_CHECK ='Y'                        \n");
				sb.append(" WHERE MD_SEQ = " + idBean.getMd_seq() + "                  \n");
				System.out.println(sb.toString());
				pstmt.executeUpdate(sb.toString());
				
				//인스타그램일 때, 해시태그 저장
				if(mode.equals("insert") || mode.equals("multi") || mode.equals("new")) {
					if("109".equals(idBean.getSg_seq())) {
						HashTagList = getHashTags(idBean.getId_content());
						insertIssueHashTag(Integer.parseInt(insertNum),HashTagList);
					}
				}
				
				int cnt = 0;
				sb = new StringBuffer();
				sb.append(" SELECT COUNT(*) AS CNT FROM "+sub_tb+" WHERE MD_SEQ ='"+idBean.getMd_seq()+"' \n");
				rs = pstmt.executeQuery(sb.toString());
				if(rs.next()) {
					cnt = rs.getInt("CNT");
				}
				
				if(cnt > 0) {
					sb = new StringBuffer();
					sb.append(" UPDATE "+sub_tb+" SET ISSUE_CHECK ='Y'                        \n");
					sb.append(" WHERE MD_SEQ = "+idBean.getMd_seq()+"                  \n");
					System.out.println(sb.toString());
					pstmt.executeUpdate(sb.toString());	
				}

			}

			String[] arrTypeCode = null;
			String[] arrTemp = null;

			// 각 항목들 저장
			if (typeCodes != null && !typeCodes.equals("")) {
				arrTypeCode = typeCodes.split("@");
				for (String typeCode : arrTypeCode) {
					if (!"".equals(typeCode)) {
						arrTemp = typeCode.split(",");
						sb = new StringBuffer();
						sb.append("INSERT INTO ISSUE_DATA_CODE		\n");
						sb.append("VALUES ( 						\n");
						sb.append("        " + insertNum + " 		\n");
						sb.append("        ," + arrTemp[0] + "		\n");
						sb.append("        ," + arrTemp[1] + "  	\n");
						sb.append("        ) 						\n");
						System.out.println(sb.toString());
						if (pstmt.executeUpdate(sb.toString()) > 0)
							result = true;
					}
				}
			}

			String[] arrRelName = null;
			String rkSeq = "0";
			String RelCode = "";			

			// 연관키워드 저장
			if (null != (idBean.getRelationkeys()) && !"".equals(idBean.getRelationkeys())) {
				RelCode = idBean.getRelationkeysCode();
				arrRelName = idBean.getRelationkeys().split("@");
				for (String relKey : arrRelName) {

					sb = new StringBuffer();
					sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + relKey + "' \n");
					System.out.println(sb.toString());
					rs = pstmt.executeQuery(sb.toString());

					if (rs.next()) {
						rkSeq = rs.getString("RK_SEQ");

						sb = null;
						sb = new StringBuffer();
						sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
								+ insertNum + "," + rs.getInt("RK_SEQ") + ","+RelCode.split(",")[0]+","+RelCode.split(",")[1]+")  \n");
						System.out.println(sb.toString());
						pstmt.executeUpdate(sb.toString());
						
					} else {
						sb = null;
						sb = new StringBuffer();
						sb.append(
								" INSERT INTO RELATION_KEYWORD_R (RK_NAME) VALUES ('" + relKey + "')  \n");
						System.out.println(sb.toString());
						pstmt.executeUpdate(sb.toString());
						sb = null;
						sb = new StringBuffer();
						rs = null;
						sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + relKey + "' \n");
						rs = pstmt.executeQuery(sb.toString());
						if (rs.next()) {
							rkSeq = rs.getString("RK_SEQ");
							
							sb = null;
							sb = new StringBuffer();
							sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
									+ insertNum + "," + rs.getInt("RK_SEQ") + ","+RelCode.split(",")[0]+","+RelCode.split(",")[1]+")  \n");
							System.out.println(sb.toString());
							pstmt.executeUpdate(sb.toString());
							result = true;
						}
					}
				}
			}

			// 통계용 데이터 저장
			sb = new StringBuffer(); 
			sb.append("INSERT INTO ANA_ISSUE_DATA (ID_SEQ, MD_DATE) VALUES (" + insertNum + ",'"
					+ du.getDate(idBean.getMd_date(), "yyyyMMdd") + "')	\n");
			if (pstmt.executeUpdate(sb.toString()) > 0)
				result = true;

			dbconn.Commit();
		} catch (SQLException ex) {
			Log.writeExpt(ex);
			Log.writeExpt("IssueCheck", ex);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} catch (Exception ex) {
			Log.writeExpt(ex);
			Log.writeExpt("IssueCheck", ex);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return insertNum;
	}

	public String insertIssueData_V2_sub(String mode, String md_seq, IssueDataBean idBean, String typeCodes,
		String typeCodesTrend, String typeCodesInfo, String submode) {
		String insertNum = "";
		String sg_seq = "";
		
		String main_tb = "";
		String sub_tb = "";
		if(submode.equals("domain")) {
			main_tb = "SECTION";
			sub_tb = "META";
		} else {
			main_tb = "META";
			sub_tb = "SECTION";
		}
		
		ArrayList<String> HashTagList = new ArrayList<String>();
		boolean result = false;

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			dbconn.TransactionStart();
			stmt = dbconn.createStatement();
			// 자기사 가져오기~
			sb = new StringBuffer();
			sb.append(
					"SELECT C.MD_SEQ 																				\n");
			sb.append("	  , C.JSON_DATA																				\n");
			sb.append("	  , C.MD_CONTENT																				\n");
			sb.append("	  , C.SG_SEQ																				\n");
			sb.append(
					"  FROM "+main_tb+" C																				\n");
			sb.append(" WHERE C.MD_PSEQ = (SELECT MD_PSEQ FROM "+main_tb+" WHERE MD_SEQ = " + md_seq
					+ ")						\n");
			sb.append("   AND C.MD_SEQ <> " + md_seq
					+ "																\n");
			sb.append(
					"   AND NOT EXISTS (																			\n");
			sb.append(
					"                   SELECT 1																	\n");
			sb.append(
					"                     FROM "+main_tb+" A, ISSUE_DATA B												\n");
			sb.append("                    WHERE A.MD_PSEQ = (SELECT MD_PSEQ FROM "+main_tb+" WHERE MD_SEQ = " + md_seq
					+ ")	\n");
			sb.append("                      AND A.MD_SEQ <> " + md_seq
					+ "												\n");
			sb.append(
					"                      AND A.MD_SEQ = B.MD_SEQ												\n");
			sb.append(
					"                      AND A.MD_SEQ = C.MD_PSEQ												\n");
			sb.append(
					"                   )																			\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			ArrayList arr_mdSeq = new ArrayList();
			ArrayList arr_mdContent = new ArrayList();
			ArrayList arr_sgSeq = new ArrayList();
			ArrayList<String> arr_writername = new ArrayList<String>();
			while (rs.next()) {
				arr_mdSeq.add(rs.getString("MD_SEQ"));
				arr_mdContent.add(rs.getString("MD_CONTENT"));
				arr_sgSeq.add(rs.getString("SG_SEQ"));				
			}

			System.out.println("유사 : " + arr_mdSeq.size() + "건");

			for (int i = 0; i < arr_mdSeq.size(); i++) {
				sb = null;
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
				sb.append("                            , REG_TYPE				\n");
				sb.append("                            , MD_PSEQ 				\n");
				sb.append("                            , ID_CLOUT 				\n");
				sb.append("                            , ID_SENTI 				\n");
				//sb.append("                            , MD_WRITER 				\n");
				//sb.append("                            , ID_REPLYCOUNT			\n");
				sb.append("                            )		 				\n");
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
				sb.append("        , '" + idBean.getId_writter() + "'			\n");
				sb.append("        , A.MD_CONTENT								\n");
				sb.append("        , NOW()										\n");
				sb.append("        , 'N'										\n");
				sb.append("        , 'Y'										\n");
				sb.append("        , '" + idBean.getM_seq() + "'				\n");
				sb.append("        , A.MD_SAME_COUNT							\n");
				sb.append("        , A.MD_TYPE									\n");
				sb.append("        , A.L_ALPHA									\n");
				sb.append("        , 'N'				\n");
				sb.append("        , '" + idBean.getTemp1() + "'				\n");
				sb.append("        , A.MD_PSEQ 									\n");
				sb.append("        , '" + idBean.getId_clout() + "'				\n");
				sb.append("        , '" + idBean.getId_senti() + "'				\n");
				//sb.append("        , '" + arr_writername.get(i) + "'				\n");
				//sb.append("        , '" + idBean.getReply_count() + "'			\n");
				sb.append("     FROM "+main_tb+" A, SG_S_RELATION B					\n");
				sb.append("    WHERE A.S_SEQ = B.S_SEQ							\n");
				sb.append("      AND A.MD_SEQ = " + (String) arr_mdSeq.get(i) + "\n");
				sb.append(" )													\n");

				insertNum = "";
				sg_seq = "";
				// info_type = "";
				if (stmt.executeUpdate(sb.toString()) > 0) {
					
					sb = new StringBuffer();
					sb.append(" SELECT ID_SEQ FROM ISSUE_DATA WHERE MD_SEQ = " + (String) arr_mdSeq.get(i) + "    \n");
					rs = stmt.executeQuery(sb.toString());
					
					if (rs.next()) {
						insertNum = rs.getString("ID_SEQ");
					}
					
					sb = new StringBuffer();
					sb.append(" UPDATE "+main_tb+" SET ISSUE_CHECK ='Y'                        \n");
					sb.append(" WHERE MD_SEQ = " + (String) arr_mdSeq.get(i) + "            \n");
					stmt.executeUpdate(sb.toString());
					
					int cnt = 0;
					
					sb = new StringBuffer();
					sb.append(" SELECT COUNT(*) AS CNT FROM "+sub_tb+" WHERE MD_SEQ ='"+idBean.getMd_seq()+"' \n");
					rs = stmt.executeQuery(sb.toString());
					
					if(rs.next()) {
						cnt = rs.getInt("CNT");
					}
					
					if(cnt > 0) {
						sb = new StringBuffer();
						sb.append(" UPDATE "+sub_tb+" SET ISSUE_CHECK ='Y'                        \n");
						sb.append(" WHERE MD_SEQ = "+idBean.getMd_seq()+"                  \n");
						System.out.println(sb.toString());
						stmt.executeUpdate(sb.toString());	
					}
				}
				
				//인스타그램일 때, 해시태그 저장
				if(mode.equals("insert") || mode.equals("multi") || mode.equals("new")) {
					if("109".equals((String) arr_sgSeq.get(i))) {
						HashTagList = getHashTags((String) arr_mdContent.get(i));
						insertIssueHashTag(Integer.parseInt(insertNum),HashTagList);
					}
				}

				String[] arrTypeCode = null;
				String[] arrTemp = null;

				// 각 항목들 저장
				if (typeCodes != null && !typeCodes.equals("")) {
					arrTypeCode = typeCodes.split("@");
					for (String typeCode : arrTypeCode) {
						if (!"".equals(typeCode)) {
							arrTemp = typeCode.split(",");
							sb = new StringBuffer();
							sb.append("INSERT INTO ISSUE_DATA_CODE		\n");
							sb.append("VALUES ( 						\n");
							sb.append("        " + insertNum + " 		\n");
							sb.append("        ," + arrTemp[0] + "		\n");
							sb.append("        ," + arrTemp[1] + "  	\n");
							sb.append("        ) 						\n");
							System.out.println(sb.toString());
							if (stmt.executeUpdate(sb.toString()) > 0)
								result = true;
						}
					}
				}

				String[] arrRelName = null;
				
				String RelCode = "";
				String rkSeq = "0";
				// 연관키워드 저장
				if (null != (idBean.getRelationkeys()) && !"".equals(idBean.getRelationkeys())) {
					RelCode = idBean.getRelationkeysCode();
					arrRelName = idBean.getRelationkeys().split("@");
					
					for (String relKey : arrRelName) {

						sb = new StringBuffer();
						sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + relKey + "' \n");
						System.out.println(sb.toString());
						rs = stmt.executeQuery(sb.toString());

						if (rs.next()) {
							rkSeq = rs.getString("RK_SEQ");
							
							sb = null;
							sb = new StringBuffer();
							if(!"".equals(RelCode)) {
								sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
										+ insertNum + "," + rs.getInt("RK_SEQ") + ","+RelCode.split(",")[0]+","+RelCode.split(",")[1]+")  \n");
							}else {
								sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("
										+ insertNum + "," + rs.getInt("RK_SEQ") + ")  \n");
							}
							
							System.out.println(sb.toString());
							stmt.executeUpdate(sb.toString());
						} else {
							sb = null;
							sb = new StringBuffer();
							sb.append(" INSERT INTO RELATION_KEYWORD_R (RK_NAME) VALUES ('" + relKey + "')  \n");
							System.out.println(sb.toString());
							stmt.executeUpdate(sb.toString());
							sb = null;
							sb = new StringBuffer();
							rs = null;
							sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + relKey + "' \n");
							rs = stmt.executeQuery(sb.toString());
							if (rs.next()) {
								rkSeq = rs.getString("RK_SEQ");
								
								sb = null;
								sb = new StringBuffer();
								if(!"".equals(RelCode)) {
									sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
											+ insertNum + "," + rs.getInt("RK_SEQ") + ","+RelCode.split(",")[0]+","+RelCode.split(",")[1]+")  \n");
								}else {
									sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("
											+ insertNum + "," + rs.getInt("RK_SEQ") + ")  \n");
								}
								
								System.out.println(sb.toString());
								stmt.executeUpdate(sb.toString());
								result = true;
							}
						}
					}
				}
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
			Log.writeExpt("IssueCheck", ex, sb.toString() + "\nID_SEQ: " + insertNum);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} catch (Exception ex) {
			Log.writeExpt("IssueCheck", ex);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
		/*	if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}*/
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return insertNum;
	}

	public String insertIssueData_V2_sub(String mode, String md_seq, IssueDataBean idBean, String typeCodes,
		String typeCodesTrend, String typeCodesInfo, String[] AutoList, String submode) {
		String insertNum = "";
		String sg_seq = "";
		
		String main_tb = "";
		String sub_tb = "";
		
		if(submode.equals("domain")) {
			main_tb = "SECTION";
			sub_tb = "META";
		} else {
			main_tb = "META";
			sub_tb = "SECTION";
		}
		
		boolean result = false;

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			dbconn.TransactionStart();
			stmt = dbconn.createStatement();
			
			// 자기사 가져오기~
			sb = new StringBuffer();
			sb.append(
					"SELECT C.MD_SEQ 																				\n");
			sb.append(
					"  FROM "+main_tb+" C																				\n");
			sb.append(" WHERE C.MD_PSEQ = (SELECT MD_PSEQ FROM "+main_tb+" WHERE MD_SEQ = " + md_seq
					+ ")						\n");
			sb.append("   AND C.MD_SEQ <> " + md_seq
					+ "																\n");
			sb.append(
					"   AND NOT EXISTS (																			\n");
			sb.append(
					"                   SELECT 1																	\n");
			sb.append(
					"                     FROM "+main_tb+" A, ISSUE_DATA B												\n");
			sb.append("                    WHERE A.MD_PSEQ = (SELECT MD_PSEQ FROM "+main_tb+" WHERE MD_SEQ = " + md_seq
					+ ")	\n");
			sb.append("                      AND A.MD_SEQ <> " + md_seq
					+ "												\n");
			sb.append(
					"                      AND A.MD_SEQ = B.MD_SEQ												\n");
			sb.append(
					"                      AND A.MD_SEQ = C.MD_PSEQ												\n");
			sb.append(
					"                   )																			\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			ArrayList arr_mdSeq = new ArrayList();
			while (rs.next()) {
				arr_mdSeq.add(rs.getString("MD_SEQ"));
			}

			System.out.println("유사 : " + arr_mdSeq.size() + "건");

			for (int i = 0; i < arr_mdSeq.size(); i++) {
				sb = null;
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
				sb.append("                            , REG_TYPE				\n");
				sb.append("                            , MD_PSEQ 				\n");
				sb.append("                            , ID_SENTI 				\n");
				sb.append("                            , ID_REPLYCOUNT			\n");
				sb.append("                            )		 				\n");
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
				sb.append("        , '" + idBean.getId_writter() + "'				\n");
				sb.append("        , A.MD_CONTENT								\n");
				sb.append("        , NOW()										\n");
				sb.append("        , 'N'										\n");
				sb.append("        , 'Y'										\n");
				sb.append("        , '" + idBean.getM_seq() + "'					\n");
				sb.append("        , A.MD_SAME_COUNT							\n");
				sb.append("        , A.MD_TYPE									\n");
				sb.append("        , A.L_ALPHA									\n");
				sb.append("        , 'N'										\n");
				sb.append("        , '" + idBean.getTemp1() + "'					\n");
				sb.append("        , A.MD_PSEQ 									\n");
				sb.append("        , '" + idBean.getId_senti() + "'					\n");
				sb.append("        , " + idBean.getReply_count() + "					\n");
				sb.append("     FROM "+main_tb+" A, SG_S_RELATION B					\n");
				sb.append("    WHERE A.S_SEQ = B.S_SEQ							\n");
				sb.append("      AND A.MD_SEQ = " + (String) arr_mdSeq.get(i) + "	\n");
				sb.append(" )													\n");

				insertNum = "";
				sg_seq = "";
				// info_type = "";
				if (stmt.executeUpdate(sb.toString()) > 0) {
					sb = new StringBuffer();
					sb.append(" SELECT ID_SEQ FROM ISSUE_DATA WHERE MD_SEQ = " + (String) arr_mdSeq.get(i) + "    \n");
					rs = stmt.executeQuery(sb.toString());
					if (rs.next()) {
						insertNum = rs.getString("ID_SEQ");
						// sg_seq = rs.getString("IC_CODE");
					}
					sb = new StringBuffer();
					sb.append(" UPDATE "+main_tb+" SET ISSUE_CHECK ='Y'                        \n");
					sb.append(" WHERE MD_SEQ = " + (String) arr_mdSeq.get(i) + "            \n");
					stmt.executeUpdate(sb.toString());
					
					int cnt = 0;
					sb = new StringBuffer();
					sb.append(" SELECT COUNT(*) AS CNT FROM "+sub_tb+" WHERE MD_SEQ ='"+idBean.getMd_seq()+"' \n");
					rs = pstmt.executeQuery(sb.toString());
					if(rs.next()) {
						cnt = rs.getInt("CNT");
					}
					
					if(cnt > 0) {
						sb = new StringBuffer();
						sb.append(" UPDATE "+sub_tb+" SET ISSUE_CHECK ='Y'                        \n");
						sb.append(" WHERE MD_SEQ = "+idBean.getMd_seq()+"                  \n");
						System.out.println(sb.toString());
						pstmt.executeUpdate(sb.toString());	
					}

				}

				String[] arrTypeCode = null;
				String[] arrTemp = null;

				// 각 항목들 저장
				if (typeCodes != null && !typeCodes.equals("")) {
					arrTypeCode = typeCodes.split("@");
					for (String typeCode : arrTypeCode) {
						if (!"".equals(typeCode)) {
							arrTemp = typeCode.split(",");
							sb = new StringBuffer();
							sb.append("INSERT INTO ISSUE_DATA_CODE		\n");
							sb.append("VALUES ( 						\n");
							sb.append("        " + insertNum + " 		\n");
							sb.append("        ," + arrTemp[0] + "		\n");
							sb.append("        ," + arrTemp[1] + "  	\n");
							sb.append("        ) 						\n");
							System.out.println(sb.toString());
							if (stmt.executeUpdate(sb.toString()) > 0)
								result = true;
						}
					}
				}

				String[] arrRelName = null;
				String[] arrRelName1 = null;
				String[] arrRelName2 = null;
				String[] arrRelName3 = null;
				// 연관키워드 저장
				if (null != (idBean.getRelationkeys()) && !"".equals(idBean.getRelationkeys())) {
					arrRelName = idBean.getRelationkeys().split("@");
					for (String relKey : arrRelName) {
						sb = new StringBuffer();
						sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + su.dbString(relKey) + "' \n");
						System.out.println(sb.toString());
						rs = stmt.executeQuery(sb.toString());

						if (rs.next()) {
							sb = null;
							sb = new StringBuffer();
							sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
									+ insertNum + "," + rs.getInt("RK_SEQ") + ",1,1)  \n");
							System.out.println(sb.toString());
							stmt.executeUpdate(sb.toString());
						} else {
							sb = null;
							sb = new StringBuffer();
							sb.append(
									" INSERT INTO RELATION_KEYWORD_R (RK_NAME) VALUES ('" + su.dbString(relKey) + "')  \n");
							System.out.println(sb.toString());
							stmt.executeUpdate(sb.toString());
							sb = null;
							sb = new StringBuffer();
							rs = null;
							sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + su.dbString(relKey) + "' \n");
							rs = stmt.executeQuery(sb.toString());
							if (rs.next()) {
								sb = null;
								sb = new StringBuffer();
								sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
										+ insertNum + "," + rs.getInt("RK_SEQ") + ",1,1)  \n");
								System.out.println(sb.toString());
								stmt.executeUpdate(sb.toString());
								result = true;
							}
						}
					}
				}

				if (null != (idBean.getCaprelationkeys()) && !"".equals(idBean.getCaprelationkeys())) {
					arrRelName1 = idBean.getCaprelationkeys().split("@");
					for (String relKey : arrRelName1) {
						sb = new StringBuffer();
						sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + su.dbString(relKey) + "' \n");
						System.out.println(sb.toString());
						rs = stmt.executeQuery(sb.toString());

						if (rs.next()) {
							sb = null;
							sb = new StringBuffer();
							sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
									+ insertNum + "," + rs.getInt("RK_SEQ") + ",1,2)  \n");
							System.out.println(sb.toString());
							stmt.executeUpdate(sb.toString());
						} else {
							sb = null;
							sb = new StringBuffer();
							sb.append(
									" INSERT INTO RELATION_KEYWORD_R (RK_NAME) VALUES ('" + su.dbString(relKey) + "')  \n");
							System.out.println(sb.toString());
							stmt.executeUpdate(sb.toString());
							sb = null;
							sb = new StringBuffer();
							rs = null;
							sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + su.dbString(relKey) + "' \n");
							rs = stmt.executeQuery(sb.toString());
							if (rs.next()) {
								sb = null;
								sb = new StringBuffer();
								sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
										+ insertNum + "," + rs.getInt("RK_SEQ") + ",1,2)  \n");
								System.out.println(sb.toString());
								stmt.executeUpdate(sb.toString());
								result = true;
							}
						}
					}
				}

				if (null != (idBean.getComrelationkeys()) && !"".equals(idBean.getComrelationkeys())) {
					arrRelName2 = idBean.getComrelationkeys().split("@");
					for (String relKey : arrRelName2) {
						sb = new StringBuffer();
						sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + su.dbString(relKey) + "' \n");
						System.out.println(sb.toString());
						rs = stmt.executeQuery(sb.toString());

						if (rs.next()) {
							sb = null;
							sb = new StringBuffer();
							sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
									+ insertNum + "," + rs.getInt("RK_SEQ") + ",1,3)  \n");
							System.out.println(sb.toString());
							stmt.executeUpdate(sb.toString());
						} else {
							sb = null;
							sb = new StringBuffer();
							sb.append(
									" INSERT INTO RELATION_KEYWORD_R (RK_NAME) VALUES ('" + su.dbString(relKey) + "')  \n");
							System.out.println(sb.toString());
							stmt.executeUpdate(sb.toString());
							sb = null;
							sb = new StringBuffer();
							rs = null;
							sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + su.dbString(relKey) + "' \n");
							rs = stmt.executeQuery(sb.toString());
							if (rs.next()) {
								sb = null;
								sb = new StringBuffer();
								sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
										+ insertNum + "," + rs.getInt("RK_SEQ") + ",1,3)  \n");
								System.out.println(sb.toString());
								stmt.executeUpdate(sb.toString());
								result = true;
							}
						}
					}
				}

				// 자동연관어 저장

				if (AutoList != null) {
					for (int k = 0; k < AutoList.length; k++) {
						sb = new StringBuffer();
						sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + AutoList[k] + "' \n");
						System.out.println(sb.toString());
						rs = stmt.executeQuery(sb.toString());

						if (rs.next()) {
							sb = null;
							sb = new StringBuffer();
							sb.append(" INSERT INTO AUTO_RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("
									+ insertNum + "," + rs.getInt("RK_SEQ") + ")  \n");
							System.out.println(sb.toString());
							stmt.executeUpdate(sb.toString());
						} else {
							sb = null;
							sb = new StringBuffer();
							sb.append(" INSERT INTO RELATION_KEYWORD_R (RK_NAME) VALUES ('" + AutoList[k] + "')  \n");
							System.out.println(sb.toString());
							stmt.executeUpdate(sb.toString());
							sb = null;
							sb = new StringBuffer();
							rs = null;
							sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + AutoList[k] + "' \n");
							rs = stmt.executeQuery(sb.toString());
							if (rs.next()) {
								sb = null;
								sb = new StringBuffer();
								sb.append(" INSERT INTO AUTO_RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("
										+ insertNum + "," + rs.getInt("RK_SEQ") + ")  \n");
								System.out.println(sb.toString());
								stmt.executeUpdate(sb.toString());
								result = true;
							}
						}
					}
				}
								
				//현대카드,현대캐피탈,현대커머셜 주요이슈 커멘트 추가
				if (idBean.getHycard_comment() != "") {
					sb = null;
					sb = new StringBuffer();
					sb.append("	INSERT INTO ISSUE_COMMENT 	     	\n");
					sb.append("  (ID_SEQ, IC_TYPE, IC_CODE, ID_COMMENT)		        \n");
					sb.append("  VALUES (" + insertNum + ", 1, 1, '" + idBean.getHycard_comment() + "')	\n");

					System.out.println(sb.toString());
					stmt.executeUpdate(sb.toString());
				}
				if (idBean.getHycap_comment() != "") {
					sb = null;					
					sb = new StringBuffer();
					sb.append("	INSERT INTO ISSUE_COMMENT 	     	\n");
					sb.append("  (ID_SEQ, IC_TYPE, IC_CODE, ID_COMMENT)		        \n");
					sb.append("  VALUES (" + insertNum + ", 1, 2, '" + idBean.getHycap_comment() + "')	\n");

					System.out.println(sb.toString());
					stmt.executeUpdate(sb.toString());
				}
				if (idBean.getHycom_comment() != "") {
					sb = null;					
					sb = new StringBuffer();
					sb.append("	INSERT INTO ISSUE_COMMENT 	     	\n");
					sb.append("  (ID_SEQ, IC_TYPE, IC_CODE, ID_COMMENT)		        \n");
					sb.append("  VALUES (" + insertNum + ", 1, 3, '" + idBean.getHycom_comment() + "')	\n");

					System.out.println(sb.toString());
					stmt.executeUpdate(sb.toString());
				} 
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
			Log.writeExpt("IssueCheck", ex, sb.toString() + "\nID_SEQ: " + insertNum);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} catch (Exception ex) {
			Log.writeExpt("IssueCheck", ex);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return insertNum;
	}

	/*
	 * 자기사 이슈 저장
	 */
	public String insertIssueData_sub(String mode, String md_seq, IssueCommentBean icBean, IssueDataBean idBean,
			String typeCodes, String typeCodesTrend, String typeCodesInfo, String submode) {

		String insertNum = "";
		String ic_code = "";
		String info_type = "";
		String[] arrTypeCode = null;
		String[] arrTemp = null;
		String[] arrTemp2 = null;
		String[] arrTemp3 = null;

		String[] arrRelKeyType = null;
		boolean result = false;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			dbconn.TransactionStart();
			stmt = dbconn.createStatement();
			String md_seq_temp = "0";
			// 자기사 가져오기~
			sb = new StringBuffer();
			sb.append(
					"SELECT C.MD_SEQ 																				\n");
			sb.append(
					"  FROM META C																				\n");
			sb.append(" WHERE C.MD_PSEQ = (SELECT MD_PSEQ FROM META WHERE MD_SEQ = " + md_seq
					+ ")						\n");
			sb.append("   AND C.MD_SEQ <> " + md_seq
					+ "																\n");
			sb.append(
					"   AND NOT EXISTS (																			\n");
			sb.append(
					"                   SELECT 1																	\n");
			sb.append(
					"                     FROM META A, ISSUE_DATA B												\n");
			sb.append("                    WHERE A.MD_PSEQ = (SELECT MD_PSEQ FROM META WHERE MD_SEQ = " + md_seq
					+ ")	\n");
			sb.append("                      AND A.MD_SEQ <> " + md_seq
					+ "												\n");
			sb.append(
					"                      AND A.MD_SEQ = B.MD_SEQ												\n");
			sb.append(
					"                      AND A.MD_SEQ = C.MD_PSEQ												\n");
			sb.append(
					"                   )																			\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			ArrayList arr_mdSeq = new ArrayList();
			while (rs.next()) {
				arr_mdSeq.add(rs.getString("MD_SEQ"));
			}

			System.out.println("유사 : " + arr_mdSeq.size() + "건");

			for (int i = 0; i < arr_mdSeq.size(); i++) {
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
				sb.append("                            , REG_TYPE				\n");
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
				sb.append("        , '" + idBean.getId_writter() + "'				\n");
				sb.append("        , C.MD_CONTENT								\n");
				sb.append("        , NOW()										\n");
				sb.append("        , 'N'										\n");
				sb.append("        , 'Y'										\n");
				sb.append("        , '" + idBean.getM_seq() + "'					\n");
				sb.append("        , A.MD_SAME_COUNT							\n");
				sb.append("        , A.MD_TYPE									\n");
				sb.append("        , A.L_ALPHA									\n");
				sb.append("        , 'N'										\n");
				sb.append("        , '" + idBean.getTemp1() + "'					\n");
				sb.append("        , A.MD_PSEQ 									\n");
				sb.append("     FROM META A, SG_S_RELATION B, DATA C			\n");
				sb.append("    WHERE A.S_SEQ = B.S_SEQ							\n");
				sb.append("      AND A.MD_SEQ = C.MD_SEQ						\n");
				sb.append("      AND A.MD_SEQ = " + (String) arr_mdSeq.get(i) + "	\n");
				sb.append(" )													\n");

				insertNum = "";
				ic_code = "";
				info_type = "";

				if (stmt.executeUpdate(sb.toString()) > 0) {

					sb = new StringBuffer();
					sb.append(
							" SELECT ID_SEQ, (SELECT B.IC_CODE																				  \n");
					sb.append(
							"                   FROM IC_S_RELATION A																			  \n");
					sb.append(
							"                      , ISSUE_CODE B 																			  \n");
					sb.append(
							"                  WHERE A.IC_SEQ = B.IC_SEQ																		  \n");
					sb.append("                    AND A.S_SEQ = SG_SEQ) AS IC_CODE FROM ISSUE_DATA WHERE MD_SEQ = "
							+ (String) arr_mdSeq.get(i) + "    \n");
					rs = stmt.executeQuery(sb.toString());
					if (rs.next()) {
						insertNum = rs.getString("ID_SEQ");
						ic_code = rs.getString("IC_CODE");
					}
					md_seq_temp = (String) arr_mdSeq.get(i);
					sb = new StringBuffer();
					sb.append(" UPDATE META SET ISSUE_CHECK ='Y'                        \n");
					sb.append(" WHERE MD_SEQ = " + (String) arr_mdSeq.get(i) + "            \n");
					stmt.executeUpdate(sb.toString());

				}

				/*
				 * if(idBean.getTemp1().equals("MUC")){ sb = new StringBuffer(); sb.
				 * append(" INSERT INTO ISSUE_LOG_ERROR		        	               					\n"
				 * ); sb.append(" VALUES("+insertNum+", "+(String)arr_mdSeq.get(i)+", '"+
				 * typeCodes +"', 2)		\n"); stmt.executeUpdate(sb.toString()); }
				 */

				if (typeCodes != null && !typeCodes.equals("") && !insertNum.equals("")) {

					sb = new StringBuffer();
					sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
					sb.append("VALUES ( 						\n");
					sb.append("        " + insertNum + " 		\n");
					sb.append("        ,6						\n");
					sb.append("        ," + ic_code + " 		\n");
					sb.append("        ) 						\n");
					stmt.executeUpdate(sb.toString());

					if (ic_code.equals("1")) {
						info_type = "1";
					} else {
						info_type = "2";
					}

					sb = new StringBuffer();
					sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
					sb.append("VALUES ( 						\n");
					sb.append("        " + insertNum + " 		\n");
					sb.append("        ,17						\n");
					sb.append("        ," + info_type + " 		\n");
					sb.append("        ) 						\n");
					stmt.executeUpdate(sb.toString());

					arrTypeCode = typeCodes.split("@");
					for (int j = 0; j < arrTypeCode.length; j++) {
						if (!arrTypeCode[j].equals("")) {
							arrTemp = arrTypeCode[j].split(",");
							if (!arrTemp[0].equals("6") && !arrTemp[0].equals("17")) {
								sb = new StringBuffer();
								sb.append("INSERT INTO ISSUE_DATA_CODE		\n");
								sb.append("VALUES ( 						\n");
								sb.append("        " + insertNum + " 		\n");
								sb.append("        ," + arrTemp[0] + "		\n");
								sb.append("        ," + arrTemp[1] + "  	\n");
								sb.append("        ) 						\n");
								if (stmt.executeUpdate(sb.toString()) > 0)
									result = true;
							}

						}
					}
				} else {
					sb = new StringBuffer();
					sb.append(
							" INSERT INTO ISSUE_LOG_ERROR		        	               								\n");

					sb.append(" VALUES(" + insertNum + ", " + (String) arr_mdSeq.get(i) + ", '" + typeCodes
							+ "', 1)		\n");
					stmt.executeUpdate(sb.toString());
				}

				// 각 대단위 분류체계 하위 속성 저장

				// 세부 성향 저장
				if (typeCodesTrend != null && !typeCodesTrend.equals("") && !insertNum.equals("")) {

					arrTypeCode = typeCodesTrend.split("@");
					for (int j = 0; j < arrTypeCode.length; j++) {
						if (!arrTypeCode[j].equals("")) {
							arrTemp = arrTypeCode[j].split(",");

							sb = new StringBuffer();
							sb.append("INSERT INTO ISSUE_DATA_CODE_DETAIL	 	\n");
							sb.append("VALUES ( 								\n");
							sb.append("        " + insertNum + " 				\n");
							sb.append("        ," + arrTemp[1] + "				\n");
							sb.append("        ," + arrTemp[2] + "  			\n");
							sb.append("        ," + arrTemp[0] + "  			\n");
							sb.append("        ,0					  			\n");
							sb.append("        ) 								\n");
							if (stmt.executeUpdate(sb.toString()) > 0)
								result = true;

							if (mode.equals("insert") || mode.equals("multi")) {
								sb = new StringBuffer();
								sb.append("INSERT INTO ISSUE_DATA_CODE_DETAIL	 	\n");
								sb.append("VALUES ( 								\n");
								sb.append("        " + insertNum + " 				\n");
								sb.append("        ,20								\n");
								sb.append("        ," + arrTemp[2] + "  			\n");
								sb.append("        ," + arrTemp[0] + "  			\n");
								sb.append("        ,0					  			\n");
								sb.append("        ) 								\n");
								if (stmt.executeUpdate(sb.toString()) > 0)
									result = true;
							}
						}
					}
				}

				// 정보 속성 저장
				if (typeCodesInfo != null && !typeCodesInfo.equals("") && !insertNum.equals("")) {
					arrTypeCode = typeCodesInfo.split("@");
					for (int j = 0; j < arrTypeCode.length; j++) {
						if (!arrTypeCode[j].equals("")) {
							arrTemp = arrTypeCode[j].split(",");

							sb = new StringBuffer();
							sb.append("INSERT INTO ISSUE_DATA_CODE_DETAIL	 	\n");
							sb.append("VALUES ( 								\n");
							sb.append("        " + insertNum + " 				\n");
							sb.append("        ," + arrTemp[1] + "				\n");
							sb.append("        ," + arrTemp[2] + "  			\n");
							sb.append("        ," + arrTemp[0] + "  			\n");
							sb.append("        ,0					  			\n");
							sb.append("        ) 								\n");
							if (stmt.executeUpdate(sb.toString()) > 0)
								result = true;

						}
					}

				}

				// 지면 여부 저장 2018.02.06 고기범
				/*
				 * System.out.println("지면 여부 저장"); String newsVal = idBean.getId_news();
				 * 
				 * if(newsVal!=null && !newsVal.equals("")){ arrTypeCode = newsVal.split(",");
				 * 
				 * sb = new StringBuffer();
				 * sb.append("INSERT INTO ISSUE_DATA_NEWS	 			\n");
				 * sb.append("VALUES ( 								\n"); sb.append("        " +
				 * insertNum + " 				\n"); sb.append("        ," + arrTypeCode[0] +
				 * "			\n"); sb.append("        ," + arrTypeCode[1] + "  		\n");
				 * sb.append("        ) 								\n");
				 * System.out.println(sb.toString()); if(stmt.executeUpdate(sb.toString())>0)
				 * result = true;
				 * 
				 * }
				 */

				// 연관키워드 저장
				if (!idBean.getRelationkeys().equals("")) {
					arrTemp = idBean.getRelationkeys().split("@");

					for (int j = 0; j < arrTemp.length; j++) {
						arrRelKeyType = new String[arrTemp.length];

						if (!arrTemp[j].equals("") || arrTemp[j] != null) {
							arrTemp2 = arrTemp[j].split("/");
							arrRelKeyType[j] = arrTemp2[0];
							if (arrTemp2.length > 1) {
								arrTemp3 = arrTemp2[1].split(",");
							} else {
								arrTemp3 = new String[0];
							}

							for (int k = 0; k < arrTemp3.length; k++) {
								sb = new StringBuffer();
								StringBuffer sb_insertMap = new StringBuffer();
								StringBuffer sb_insertKey = new StringBuffer();

								sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='"
										+ su.dbString(arrTemp3[k]) + "'  AND RK_USE = 'Y' \n");
								rs = stmt.executeQuery(sb.toString());
								if (rs.next()) {
									sb_insertMap.append(
											" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
													+ insertNum + "," + rs.getInt("RK_SEQ") + "," + arrRelKeyType[j]
													+ ", 0)  \n");
									System.out.println(sb_insertMap.toString());
									stmt.executeUpdate(sb_insertMap.toString());
								} else {
									sb_insertKey.append(" INSERT INTO RELATION_KEYWORD_R (RK_NAME) VALUES ('"
											+ su.dbString(arrTemp3[k]) + "')  \n");
									System.out.println(sb_insertKey.toString());
									stmt.executeUpdate(sb_insertKey.toString());
									sb = new StringBuffer();
									rs = null;
									sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='"
											+ su.dbString(arrTemp3[k]) + "' AND RK_USE = 'Y'  \n");
									rs = stmt.executeQuery(sb.toString());
									if (rs.next()) {

										sb_insertMap.append(
												" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
														+ insertNum + "," + rs.getInt("RK_SEQ") + "," + arrRelKeyType[j]
														+ ", 0)   \n");
										System.out.println(sb_insertMap.toString());
										stmt.executeUpdate(sb_insertMap.toString());
										result = true;
									}
								}
							}
						}
					}
				}

				/*
				 * //통계용 데이터 저장 sb = new StringBuffer();
				 * sb.append("INSERT INTO ANA_ISSUE_DATA (ID_SEQ, MD_DATE) VALUES ("+insertNum+
				 * ",'"+idBean.getMd_date()+"')	\n"); stmt.executeUpdate(sb.toString());
				 */

			}
			dbconn.Commit();

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
			Log.writeExpt("IssueCheck", ex, sb.toString() + "\nID_SEQ: " + insertNum);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} catch (Exception ex) {
			Log.writeExpt(ex);
			Log.writeExpt("IssueCheck", ex);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return insertNum;
	}

	/**
	 * ISSUE_DATA, ISSUE_CODE, ISSUE_COMMENT 수정
	 * 
	 * @param IssueDataBean    :이슈데이터빈
	 * @param IssueCommentBean :이슈코멘트빈
	 * @param typeCode         :분류체계
	 * @return
	 */
	public boolean updateIssueData(IssueDataBean idBean, IssueCommentBean icBean, String typeCodes,
			String typeCodesTrend, String typeCodesInfo) {
		String[] arrTypeCode = null;
		String[] arrTemp = null;
		String[] arrTemp2 = null;
		String[] arrTemp3 = null;
		ArrayList arrPastTrend = new ArrayList();
		String[] arrRelKeyType = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			dbconn.TransactionStart();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append("SELECT IC_CODE, IC_PTYPE, IC_PCODE   				\n");
			sb.append("		FROM ISSUE_DATA_CODE_DETAIL   							\n");
			sb.append("		WHERE ID_SEQ =" + idBean.getId_seq() + " AND IC_TYPE = 9  	\n");
			sb.append("	GROUP BY IC_CODE, IC_PTYPE, IC_PCODE							  	\n");
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {

				IssueCodeBean icb = new IssueCodeBean();
				icb.setIc_code(rs.getInt("IC_CODE"));
				icb.setIc_ptype(rs.getInt("IC_PTYPE"));
				icb.setIc_pcode(rs.getInt("IC_PCODE"));

				arrPastTrend.add(icb);

			}
			rs = null;

			sb = new StringBuffer();
			sb.append(" UPDATE ISSUE_DATA SET                                     		\n");
			sb.append("                 I_SEQ = ? 					            		\n");
			sb.append("                 ,IT_SEQ = ?          							\n");
			sb.append("                 ,ID_TITLE = ?     								\n");
			sb.append("                 ,ID_URL = ?         							\n");
			sb.append("                 ,MD_SITE_NAME = ? 								\n");
			sb.append("                 ,ID_WRITTER = ? 								\n");
			sb.append("                 ,ID_CONTENT = ? 								\n");
			sb.append("                 ,M_SEQ = ?             							\n");
			sb.append("                 ,MD_TYPE = ?             						\n");
			sb.append("                 ,ID_REPORTYN = ?							    \n");
			sb.append("                 ,REG_TYPE = ?								    \n");
			sb.append(" WHERE                                                     		\n");
			sb.append("       ID_SEQ = ?                     							\n");
			System.out.println(sb.toString());

			pstmt = dbconn.createPStatement(sb.toString());
			int idx = 1;

			pstmt.setString(idx++, idBean.getI_seq());
			pstmt.setString(idx++, idBean.getIt_seq());
			pstmt.setString(idx++, idBean.getId_title());
			pstmt.setString(idx++, idBean.getId_url());
			pstmt.setString(idx++, idBean.getMd_site_name());
			pstmt.setString(idx++, idBean.getId_writter());
			pstmt.setString(idx++, idBean.getId_content());
			pstmt.setString(idx++, idBean.getM_seq());
			pstmt.setString(idx++, idBean.getMd_type());
			pstmt.setString(idx++, idBean.getId_reportyn());
			pstmt.setString(idx++, idBean.getTemp1());
			pstmt.setString(idx++, idBean.getId_seq());

			if (pstmt.executeUpdate() > 0) {

				sb = new StringBuffer();
				sb.append("DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ =" + idBean.getId_seq() + "   \n");
				stmt.executeUpdate(sb.toString());

				sb = new StringBuffer();
				sb.append("DELETE FROM ISSUE_DATA_CODE_DETAIL WHERE ID_SEQ =" + idBean.getId_seq() + "   \n");
				System.out.println(sb.toString());
				stmt.executeUpdate(sb.toString());

				// 지면 여부 업데이트 전 삭제 2018.02.07 고기범
				/*
				 * sb = new StringBuffer();
				 * sb.append("DELETE FROM ISSUE_DATA_NEWS WHERE ID_SEQ ="+idBean.getId_seq()
				 * +"   \n"); System.out.println(sb.toString());
				 * stmt.executeUpdate(sb.toString());
				 */

				sb = new StringBuffer();
				sb.append("DELETE FROM RELATION_KEYWORD_MAP WHERE ID_SEQ = " + idBean.getId_seq() + "   \n");
				System.out.println(sb.toString());
				stmt.executeUpdate(sb.toString());

				/*
				 * sb = new StringBuffer();
				 * sb.append("DELETE FROM ANA_ISSUE_DATA WHERE ID_SEQ = "+idBean.getId_seq()
				 * +"   \n"); System.out.println(sb.toString());
				 * stmt.executeUpdate(sb.toString());
				 */

			}

			if (typeCodes != null && !typeCodes.equals("")) {
				arrTypeCode = typeCodes.split("@");
				for (int i = 0; i < arrTypeCode.length; i++) {
					if (!arrTypeCode[i].equals("")) {
						arrTemp = arrTypeCode[i].split(",");

						sb = new StringBuffer();
						sb.append("INSERT INTO ISSUE_DATA_CODE			\n");
						sb.append("VALUES ( 							\n");
						sb.append("        " + idBean.getId_seq() + " 	\n");
						sb.append("        ," + arrTemp[0] + "			\n");
						sb.append("        ," + arrTemp[1] + "  		\n");
						sb.append("        ) 							\n");
						System.out.println(sb.toString());
						if (stmt.executeUpdate(sb.toString()) > 0)
							result = true;

					}
				}

			}

			// 각 대단위 분류체계 하위 속성 저장

			// 세부 성향 저장
			if (typeCodesTrend != null && !typeCodesTrend.equals("")) {
				arrTypeCode = typeCodesTrend.split("@");
				for (int i = 0; i < arrTypeCode.length; i++) {
					if (!arrTypeCode[i].equals("")) {
						arrTemp = arrTypeCode[i].split(",");

						sb = new StringBuffer();
						sb.append("INSERT INTO ISSUE_DATA_CODE_DETAIL	 	\n");
						sb.append("VALUES ( 								\n");
						sb.append("        " + idBean.getId_seq() + " 		\n");
						sb.append("        ," + arrTemp[1] + "				\n");
						sb.append("        ," + arrTemp[2] + "  			\n");
						sb.append("        ," + arrTemp[0] + "  			\n");
						sb.append("        ,0					  			\n");
						sb.append("        ) 								\n");
						System.out.println(sb.toString());
						if (stmt.executeUpdate(sb.toString()) > 0)
							result = true;

					}
				}
			}

			for (int i = 0; i < arrPastTrend.size(); i++) {

				IssueCodeBean icb = (IssueCodeBean) arrPastTrend.get(i);

				sb = new StringBuffer();
				sb.append("INSERT INTO ISSUE_DATA_CODE_DETAIL	 	\n");
				sb.append("VALUES ( 								\n");
				sb.append("        " + idBean.getId_seq() + " 		\n");
				sb.append("        ,20								\n");
				sb.append("        ," + icb.getIc_code() + "  		\n");
				sb.append("        ," + icb.getIc_ptype() + "  		\n");
				sb.append("        ," + icb.getIc_pcode() + "		\n");
				sb.append("        ) 								\n");
				System.out.println(sb.toString());
				if (stmt.executeUpdate(sb.toString()) > 0)
					result = true;

			}

			System.out.println("정보 속성 저장");
			// 정보 속성 저장
			if (typeCodesInfo != null && !typeCodesInfo.equals("")) {
				arrTypeCode = typeCodesInfo.split("@");
				for (int i = 0; i < arrTypeCode.length; i++) {
					if (!arrTypeCode[i].equals("")) {
						arrTemp = arrTypeCode[i].split(",");

						sb = new StringBuffer();
						sb.append("INSERT INTO ISSUE_DATA_CODE_DETAIL	 	\n");
						sb.append("VALUES ( 								\n");
						sb.append("        " + idBean.getId_seq() + " 		\n");
						sb.append("        ," + arrTemp[1] + "				\n");
						sb.append("        ," + arrTemp[2] + "  			\n");
						sb.append("        ," + arrTemp[0] + "  			\n");
						sb.append("        ,0					  			\n");
						sb.append("        ) 								\n");
						System.out.println(sb.toString());
						if (stmt.executeUpdate(sb.toString()) > 0)
							result = true;

					}
				}

			}

			/*
			 * // 지면 여부 업데이트 2018.02.07 고기범 System.out.println("지면 여부 저장"); String newsVal =
			 * idBean.getId_news();
			 * 
			 * if(newsVal!=null && !newsVal.equals("")){ arrTypeCode = newsVal.split(",");
			 * 
			 * sb = new StringBuffer();
			 * sb.append("INSERT INTO ISSUE_DATA_NEWS	 			\n");
			 * sb.append("VALUES ( 								\n"); sb.append("        " +
			 * idBean.getId_seq() + " 		\n"); sb.append("        ," + arrTypeCode[0] +
			 * "			\n"); sb.append("        ," + arrTypeCode[1] + "  		\n");
			 * sb.append("        ) 								\n");
			 * System.out.println(sb.toString()); if(stmt.executeUpdate(sb.toString())>0)
			 * result = true;
			 * 
			 * }
			 */
			System.out.println("idBean.getRelationkeys: " + idBean.getRelationkeys());
			// 연관키워드 저장
			if (!idBean.getRelationkeys().equals("")) {
				arrTemp = idBean.getRelationkeys().split("@");

				for (int i = 0; i < arrTemp.length; i++) {
					arrRelKeyType = new String[arrTemp.length];

					if (!arrTemp[i].equals("") || arrTemp[i] != null) {
						arrTemp2 = arrTemp[i].split("/");
						arrRelKeyType[i] = arrTemp2[0];
						if (arrTemp2.length > 1) {
							arrTemp3 = arrTemp2[1].split(",");
						} else {
							arrTemp3 = new String[0];
						}
						for (int j = 0; j < arrTemp3.length; j++) {
							sb = new StringBuffer();
							StringBuffer sb_insertMap = new StringBuffer();
							StringBuffer sb_insertKey = new StringBuffer();

							sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='"
									+ su.dbString(arrTemp3[j]) + "' AND RK_USE = 'Y' \n");
							rs = stmt.executeQuery(sb.toString());
							if (rs.next()) {
								sb_insertMap.append(
										" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
												+ idBean.getId_seq() + "," + rs.getInt("RK_SEQ") + ","
												+ arrRelKeyType[i] + ", 0)  \n");
								System.out.println(sb_insertMap.toString());
								stmt.executeUpdate(sb_insertMap.toString());
							} else {
								sb_insertKey.append(" INSERT INTO RELATION_KEYWORD_R (RK_NAME) VALUES ('"
										+ su.dbString(arrTemp3[j]) + "')  \n");
								System.out.println(sb_insertKey.toString());
								stmt.executeUpdate(sb_insertKey.toString());
								sb = new StringBuffer();
								rs = null;
								sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='"
										+ su.dbString(arrTemp3[j]) + "' AND RK_USE = 'Y' \n");
								rs = stmt.executeQuery(sb.toString());
								if (rs.next()) {

									sb_insertMap.append(
											" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
													+ idBean.getId_seq() + "," + rs.getInt("RK_SEQ") + ","
													+ arrRelKeyType[i] + ", 0)   \n");
									System.out.println(sb_insertMap.toString());
									stmt.executeUpdate(sb_insertMap.toString());
									result = true;
								}
							}
						}
					}
				}
			}

			/*
			 * //통계용 데이터 저장 sb = new StringBuffer();
			 * sb.append("INSERT INTO ANA_ISSUE_DATA (ID_SEQ, MD_DATE) VALUES ("+idBean.
			 * getId_seq()+",'"+idBean.getMd_date()+"')	\n");
			 * if(stmt.executeUpdate(sb.toString())>0) result = true;
			 */
			dbconn.Commit();

		} catch (SQLException ex) {
			// Log.writeExpt(ex, sb.toString() );
			Log.writeExpt(ex, sb.toString());
			Log.writeExpt("IssueCheck", ex, sb.toString());
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} catch (Exception ex) {
			Log.writeExpt(ex);
			Log.writeExpt("IssueCheck", ex);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

	/**
	 * SKT 이슈수정 NEW
	 * 
	 * @param mode
	 * @param idBean
	 * @param typeCodes
	 * @param typeCodesTrend
	 * @param typeCodesInfo
	 * @param mode2
	 * @return
	 */
	public boolean multi_updateIssueData(IssueDataBean idBean, String typeCodes) {

		boolean result = false;

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			dbconn.TransactionStart();

			rs = null;
			sb = new StringBuffer();
			stmt = dbconn.createStatement();

			String[] arrTypeCode = null;
			String[] arrTemp = null;
			
			String hotel_type = "3,4,5,6";
			String tr_type = "7,8";
			String shp_type = "9,10";
			String sbtm_type = "11";
			String ceo_type = "12";
			String ir_type = "13";
			String esg_type = "14";
			String compare_code = "0";
			
			/*
			sb = new StringBuffer();
			sb.append("DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ =" + idBean.getId_seq() + "   \n");
			stmt.executeUpdate(sb.toString());
			System.out.println(sb.toString());
			*/
			
			// 각 항목들 저장
			if (typeCodes != null && !typeCodes.equals("")) {
				arrTypeCode = typeCodes.split("@");
				for (String typeCode : arrTypeCode) {
					if (!"".equals(typeCode)) {
						arrTemp = typeCode.split(",");
						
						//구분 항목이 기존이랑 다르다면 하위 타입들 전부 다 삭제
						if("1".equals(arrTemp[0])) {
							
							sb = new StringBuffer();
							sb.append(" SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = " + idBean.getId_seq() + " AND IC_TYPE = 1 \n");
							System.out.println(sb.toString());
							rs = stmt.executeQuery(sb.toString());

							if (rs.next()) {
								compare_code = rs.getString("IC_CODE");
							}
							
							if(!"0".equals(compare_code) && !compare_code.equals(arrTemp[1])) {
								sb = new StringBuffer();
								sb.append("DELETE FROM ISSUE_DATA_CODE					    																	  \n");
								sb.append("      WHERE ID_SEQ =" + idBean.getId_seq() + "   																	  \n");
								sb.append("        AND IC_TYPE IN ("+hotel_type+","+tr_type+","+shp_type+","+sbtm_type+","+ceo_type+","+ir_type+","+esg_type+")   \n");
								stmt.executeUpdate(sb.toString());
								System.out.println(sb.toString());
							}
							compare_code = "0";
						}
						
						sb = new StringBuffer();
						sb.append("DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ =" + idBean.getId_seq() + "   \n");
						sb.append("                              AND IC_TYPE =" + arrTemp[0] + "   		  \n");
						stmt.executeUpdate(sb.toString());
						System.out.println(sb.toString());

						sb = new StringBuffer();
						sb.append("INSERT INTO ISSUE_DATA_CODE		\n");
						sb.append("VALUES ( 						\n");
						sb.append("        " + idBean.getId_seq() + " 		\n");
						sb.append("        ," + arrTemp[0] + "		\n");
						sb.append("        ," + arrTemp[1] + "  	\n");
						sb.append("        ) 						\n");
						System.out.println(sb.toString());
						if (stmt.executeUpdate(sb.toString()) > 0)
							result = true;
					}
				}
			}
			
			// REG_TYPE
			sb = new StringBuffer();
			sb.append(" UPDATE ISSUE_DATA SET REG_TYPE = '"+idBean.getTemp1()+"' WHERE ID_SEQ = "+idBean.getId_seq()+"		\n");
			stmt.executeUpdate(sb.toString());

			// 영향력
			if (!"".equals(idBean.getId_clout())) {
				sb = new StringBuffer();
				sb.append(" UPDATE ISSUE_DATA SET ID_CLOUT = " + idBean.getId_clout() + " WHERE ID_SEQ = "
						+ idBean.getId_seq() + "		\n");
				stmt.executeUpdate(sb.toString());
			}

			// 성향
			if (!"".equals(idBean.getId_senti())) {
				sb = new StringBuffer();
				sb.append(" UPDATE ISSUE_DATA SET ID_SENTI = " + idBean.getId_senti() + " WHERE ID_SEQ = "
						+ idBean.getId_seq() + "		\n");
				stmt.executeUpdate(sb.toString());
			}

			// 데이터이관
			if (!"".equals(idBean.getId_reportyn())) {
				sb = new StringBuffer();
				sb.append(" UPDATE ISSUE_DATA SET ID_REPORTYN = '" + idBean.getId_reportyn() + "' WHERE ID_SEQ = "
						+ idBean.getId_seq() + "		\n");
				stmt.executeUpdate(sb.toString());
			}
			
			// 출처
			/*
			if (!"".equals(idBean.getSg_seq())) {
				sb = new StringBuffer();
				sb.append(" UPDATE ISSUE_DATA SET SG_SEQ = '" + idBean.getSg_seq() + "' WHERE ID_SEQ = "
						+ idBean.getId_seq() + "		\n");
				stmt.executeUpdate(sb.toString());
			}
			*/ 
			
			//연관어 삭제
			sb = new StringBuffer();
			sb.append("DELETE FROM RELATION_KEYWORD_MAP WHERE ID_SEQ = " + idBean.getId_seq() + "   \n");
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());
			
			String[] arrRelName = null;
			String rkSeq = "0";
			String RelCode = "";			
			// 연관키워드 저장
			if (null != (idBean.getRelationkeys()) && !"".equals(idBean.getRelationkeys())) {
								
				RelCode = idBean.getRelationkeysCode();
				arrRelName = idBean.getRelationkeys().split("@");
				for (String relKey : arrRelName) {					
					sb = new StringBuffer();
					sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + relKey + "' \n");
					System.out.println(sb.toString());
					rs = stmt.executeQuery(sb.toString());

					if (rs.next()) {
						rkSeq = rs.getString("RK_SEQ");

						sb = null;
						sb = new StringBuffer();
						if(!"".equals(RelCode)) {
							sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
									+ idBean.getId_seq() + "," + rs.getInt("RK_SEQ") + ","+RelCode.split(",")[0]+","+RelCode.split(",")[1]+")  \n");
						}else {
							sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("
									+ idBean.getId_seq() + "," + rs.getInt("RK_SEQ") + ")  \n");
							
						}
						System.out.println(sb.toString());
						stmt.executeUpdate(sb.toString());
						
					} else {
						sb = null;
						sb = new StringBuffer();
						sb.append(" INSERT INTO RELATION_KEYWORD_R (RK_NAME) VALUES ('" + relKey + "')  \n");
						System.out.println(sb.toString());
						stmt.executeUpdate(sb.toString());
						
						sb = null;
						sb = new StringBuffer();
						rs = null;
						sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + relKey + "' \n");
						rs = stmt.executeQuery(sb.toString());

						if (rs.next()) {
							rkSeq = rs.getString("RK_SEQ");

							sb = null;
							sb = new StringBuffer();
							if(!"".equals(RelCode)) {
								sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
										+ idBean.getId_seq() + "," + rs.getInt("RK_SEQ") + ","+RelCode.split(",")[0]+","+RelCode.split(",")[1]+")  \n");
							}else {
								sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("
										+ idBean.getId_seq() + "," + rs.getInt("RK_SEQ") + ")  \n");								
							}
							System.out.println(sb.toString());
							stmt.executeUpdate(sb.toString());
							
							result = true;
						}
					}
				}
			}
			
			dbconn.Commit();
		} catch (SQLException ex) {
			Log.writeExpt(ex);
			Log.writeExpt("IssueCheck", ex);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} catch (Exception ex) {
			Log.writeExpt(ex);
			Log.writeExpt("IssueCheck", ex);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

	/**
	 * SKT 이슈수정 NEW
	 * 
	 * @param mode
	 * @param idBean
	 * @param typeCodes
	 * @param typeCodesTrend
	 * @param typeCodesInfo
	 * @param mode2
	 * @return
	 */
	public boolean updateIssueData_V2(String mode, IssueDataBean idBean, String typeCodes, String typeCodesTrend,
			String typeCodesInfo, String mode2) {

		boolean result = false;

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			dbconn.TransactionStart();

			/*
			 * ArrayList arrPastTrend = new ArrayList(); sb = new StringBuffer();
			 * sb.append("SELECT IC_CODE, IC_PTYPE, IC_PCODE   				\n");
			 * sb.append("		FROM ISSUE_DATA_CODE_DETAIL   							\n"
			 * );
			 * sb.append("		WHERE ID_SEQ ="+idBean.getId_seq()+" AND IC_TYPE = 9  	\n"
			 * ); sb.
			 * append("	GROUP BY IC_CODE, IC_PTYPE, IC_PCODE							  	\n"
			 * ); stmt = dbconn.createStatement(); rs = stmt.executeQuery(sb.toString());
			 * 
			 * while(rs.next()){
			 * 
			 * IssueCodeBean icb = new IssueCodeBean();
			 * icb.setIc_code(rs.getInt("IC_CODE")); icb.setIc_ptype(rs.getInt("IC_PTYPE"));
			 * icb.setIc_pcode(rs.getInt("IC_PCODE")); arrPastTrend.add(icb);
			 * 
			 * }
			 */
			rs = null;

			sb = new StringBuffer();
			sb.append(" UPDATE ISSUE_DATA SET                                     		\n");
			sb.append("                  I_SEQ = ? 					            		\n");
			sb.append("                 ,IT_SEQ = ?          							\n");
			sb.append("                 ,ID_TITLE = ?     								\n");
			sb.append("                 ,ID_URL = ?         							\n");
			sb.append("                 ,MD_SITE_NAME = ? 								\n");
			sb.append("                 ,ID_WRITTER = ? 								\n");
			sb.append("                 ,ID_CONTENT = ? 								\n");
			sb.append("                 ,M_SEQ = ?             							\n");
			sb.append("                 ,MD_TYPE = ?             						\n");
			sb.append("                 ,ID_REPORTYN = ?							    \n");
			sb.append("                 ,REG_TYPE = ?								    \n");
			sb.append("                 ,ID_CLOUT = ?								    \n");
			sb.append("                 ,ID_SENTI = ?								    \n");
			sb.append("                 ,SG_SEQ = ?								    	\n");
			//sb.append("                 ,ID_TRANS_USEYN = ?							    \n");
			//sb.append("                 ,ID_REPLYCOUNT = ?							    \n");
			sb.append(" WHERE                                                     		\n");
			sb.append("       ID_SEQ = ?                     							\n");
			System.out.println(sb.toString());

			pstmt = dbconn.createPStatement(sb.toString());
			System.out.println(idBean.getId_seq());
			int idx = 1;
			pstmt.setString(idx++, idBean.getI_seq());
			pstmt.setString(idx++, idBean.getIt_seq());
			pstmt.setString(idx++, idBean.getId_title());
			pstmt.setString(idx++, idBean.getId_url());
			pstmt.setString(idx++, idBean.getMd_site_name());
			pstmt.setString(idx++, idBean.getId_writter());
			pstmt.setString(idx++, idBean.getId_content());
			pstmt.setString(idx++, idBean.getM_seq());
			pstmt.setString(idx++, idBean.getMd_type());
			pstmt.setString(idx++, idBean.getId_reportyn());
			pstmt.setString(idx++, idBean.getTemp1());
			pstmt.setString(idx++, idBean.getId_clout());
			pstmt.setString(idx++, idBean.getId_senti());
			pstmt.setString(idx++, idBean.getSg_seq());
			//pstmt.setString(idx++, idBean.getId_trans_useyn());
			//pstmt.setString(idx++, idBean.getReply_count()());
			pstmt.setString(idx++, idBean.getId_seq());

			if (pstmt.executeUpdate() > 0) {

				/*
				 * sb = new StringBuffer();
				 * sb.append("DELETE FROM ISSUE_DATA_CODE_DETAIL WHERE ID_SEQ ="+idBean.
				 * getId_seq()+"   \n"); System.out.println(sb.toString());
				 * stmt.executeUpdate(sb.toString());
				 */

				/*
				 * sb = new StringBuffer();
				 * sb.append("DELETE FROM ANA_ISSUE_DATA WHERE ID_SEQ = "+idBean.getId_seq()
				 * +"   \n"); System.out.println(sb.toString());
				 * stmt.executeUpdate(sb.toString());
				 */
				sb = new StringBuffer();
				/*
				 * sb.
				 * append(" SELECT ID_SEQ, (SELECT B.IC_CODE																				  \n"
				 * ); sb.
				 * append("                   FROM IC_S_RELATION A																			  \n"
				 * );
				 * sb.append("                      , ISSUE_CODE B 																			  \n"
				 * ); sb.
				 * append("                  WHERE A.IC_SEQ = B.IC_SEQ																		  \n"
				 * ); sb.
				 * append("                    AND A.S_SEQ = SG_SEQ) AS SG_SEQ FROM ISSUE_DATA WHERE ID_SEQ = "
				 * +idBean.getId_seq()+"   		  \n");
				 */

				sb.append(" SELECT ID_SEQ, SG_SEQ FROM ISSUE_DATA WHERE ID_SEQ = " + idBean.getId_seq()
						+ "   		  \n");
				System.out.println(sb.toString());
				stmt = dbconn.createStatement();
				rs = stmt.executeQuery(sb.toString());
				String sg_seq = "";
				if (rs.next()) {
					sg_seq = rs.getString("SG_SEQ");
				}

				sb = new StringBuffer();
				sb.append("DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ =" + idBean.getId_seq() + "   \n");
				stmt.executeUpdate(sb.toString());
				System.out.println(sb.toString());

				sb = new StringBuffer();
				sb.append("DELETE FROM RELATION_KEYWORD_MAP WHERE ID_SEQ = " + idBean.getId_seq() + "   \n");
				System.out.println(sb.toString());
				stmt.executeUpdate(sb.toString());

				sb = new StringBuffer();
				sb.append("DELETE FROM AUTO_RELATION_KEYWORD_MAP WHERE ID_SEQ = " + idBean.getId_seq() + "   \n");
				System.out.println(sb.toString());
				stmt.executeUpdate(sb.toString());

				/*
				 * sb = new StringBuffer();
				 * sb.append("INSERT INTO ISSUE_DATA_CODE			\n");
				 * sb.append("VALUES ( 							\n"); sb.append("        " +
				 * idBean.getId_seq() + " 	\n");
				 * sb.append("        , 6							\n"); sb.append("        ,"
				 * + sg_seq + "  			\n");
				 * sb.append("        ) 							\n");
				 * stmt.executeUpdate(sb.toString());
				 */
			}

			String[] arrTypeCode = null;
			String[] arrTemp = null;

			// 각 항목들 저장
			if (typeCodes != null && !typeCodes.equals("")) {
				arrTypeCode = typeCodes.split("@");
				for (String typeCode : arrTypeCode) {
					if (!"".equals(typeCode)) {
						arrTemp = typeCode.split(",");
						sb = new StringBuffer();
						sb.append("INSERT INTO ISSUE_DATA_CODE		\n");
						sb.append("VALUES ( 						\n");
						sb.append("        " + idBean.getId_seq() + " 		\n");
						sb.append("        ," + arrTemp[0] + "		\n");
						sb.append("        ," + arrTemp[1] + "  	\n");
						sb.append("        ) 						\n");
						System.out.println(sb.toString());
						if (stmt.executeUpdate(sb.toString()) > 0)
							result = true;
					}
				}
			}

			String[] arrRelName = null;
			String rkSeq = "0";
			String RelCode = "";			
			// 연관키워드 저장
			if (null != (idBean.getRelationkeys()) && !"".equals(idBean.getRelationkeys())) {
				
				RelCode = idBean.getRelationkeysCode();
				arrRelName = idBean.getRelationkeys().split("@");
				for (String relKey : arrRelName) {					
					sb = new StringBuffer();
					sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + relKey + "' \n");
					System.out.println(sb.toString());
					rs = stmt.executeQuery(sb.toString());

					if (rs.next()) {
						rkSeq = rs.getString("RK_SEQ");

						sb = null;
						sb = new StringBuffer();
						if(!"".equals(RelCode)) {
							sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
									+ idBean.getId_seq() + "," + rs.getInt("RK_SEQ") + ","+RelCode.split(",")[0]+","+RelCode.split(",")[1]+")  \n");
						}else {
							sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("
									+ idBean.getId_seq() + "," + rs.getInt("RK_SEQ") + ")  \n");
							
						}
						System.out.println(sb.toString());
						stmt.executeUpdate(sb.toString());
						
					} else {
						sb = null;
						sb = new StringBuffer();
						sb.append(" INSERT INTO RELATION_KEYWORD_R (RK_NAME) VALUES ('" + relKey + "')  \n");
						System.out.println(sb.toString());
						stmt.executeUpdate(sb.toString());
						
						sb = null;
						sb = new StringBuffer();
						rs = null;
						sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + relKey + "' \n");
						rs = stmt.executeQuery(sb.toString());

						if (rs.next()) {
							rkSeq = rs.getString("RK_SEQ");

							sb = null;
							sb = new StringBuffer();
							if(!"".equals(RelCode)) {
								sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
										+ idBean.getId_seq() + "," + rs.getInt("RK_SEQ") + ","+RelCode.split(",")[0]+","+RelCode.split(",")[1]+")  \n");
							}else {
								sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("
										+ idBean.getId_seq() + "," + rs.getInt("RK_SEQ") + ")  \n");								
							}
							System.out.println(sb.toString());
							stmt.executeUpdate(sb.toString());
							
							result = true;
						}
					}
				}
			}
			
			dbconn.Commit();
		} catch (SQLException ex) {
			Log.writeExpt(ex);
			Log.writeExpt("IssueCheck", ex);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} catch (Exception ex) {
			Log.writeExpt(ex);
			Log.writeExpt("IssueCheck", ex);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

	/**
	 * SKT 이슈수정 NEW
	 * 
	 * @param mode
	 * @param idBean
	 * @param typeCodes
	 * @param typeCodesTrend
	 * @param typeCodesInfo
	 * @param mode2
	 * @return
	 */
	public boolean updateChildIssueData(String mode, IssueDataBean idBean, String typeCodes, String typeCodesTrend,
			String typeCodesInfo, String mode2) {

		boolean result = false;

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			dbconn.TransactionStart();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();
			sb.append(" SELECT MD_PSEQ, MD_SEQ FROM ISSUE_DATA WHERE ID_SEQ = " + idBean.getId_seq()
					+ " 				\n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			if (rs.next()) {
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));
			}

			sb = new StringBuffer();
			sb.append(" SELECT ID_SEQ FROM ISSUE_DATA WHERE MD_PSEQ = " + idBean.getMd_pseq() + " AND MD_SEQ <> "
					+ idBean.getMd_seq() + " \n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			ArrayList<String> arrIdSeq = new ArrayList<String>();
			StringBuffer id_seqs_buff = null;
			while (rs.next()) {
				if (id_seqs_buff == null) {
					id_seqs_buff = new StringBuffer(rs.getString("ID_SEQ"));
				} else {
					id_seqs_buff.append(",").append(rs.getString("ID_SEQ"));
				}
				arrIdSeq.add(rs.getString("ID_SEQ"));
			}

			if (id_seqs_buff != null) {
				System.out.println("idseqs : " + id_seqs_buff.toString());

				rs = null;
				for (int i = 0; i < arrIdSeq.size(); i++) {
										
					if("update".equals(mode)) {
						
						sb = new StringBuffer();
						sb.append(" UPDATE ISSUE_DATA SET                                     		\n");
						sb.append("                  REG_TYPE = ? 					            	\n");
						sb.append("                 ,ID_CLOUT = ?          							\n");
						sb.append("                 ,ID_SENTI = ?     								\n");
						sb.append("                 ,ID_REPORTYN = ?         						\n");
						sb.append(" WHERE                                                     		\n");
						sb.append("       ID_SEQ = ?                     							\n");
						System.out.println(sb.toString());
	
						pstmt = dbconn.createPStatement(sb.toString());
						System.out.println(idBean.getId_seq());
						int idx = 1;
						pstmt.setString(idx++, idBean.getTemp1());
						pstmt.setString(idx++, idBean.getId_clout());
						pstmt.setString(idx++, idBean.getId_senti());
						pstmt.setString(idx++, idBean.getId_reportyn());
						pstmt.setString(idx++, arrIdSeq.get(i));
						pstmt.executeUpdate();
						
						sb = new StringBuffer();
						sb.append("DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ =" + arrIdSeq.get(i) + "   \n");
						stmt.executeUpdate(sb.toString());
						System.out.println(sb.toString());
	
						sb = new StringBuffer();
						sb.append("DELETE FROM RELATION_KEYWORD_MAP WHERE ID_SEQ = " + arrIdSeq.get(i) + "   \n");
						System.out.println(sb.toString());
						stmt.executeUpdate(sb.toString());
	
					}else {
						
						// REG_TYPE
						sb = new StringBuffer();
						sb.append(" UPDATE ISSUE_DATA SET REG_TYPE = '"+idBean.getTemp1()+"' WHERE ID_SEQ = "+arrIdSeq.get(i)+"		\n");
						stmt.executeUpdate(sb.toString());

						// 영향력
						if (!"".equals(idBean.getId_clout())) {
							sb = new StringBuffer();
							sb.append(" UPDATE ISSUE_DATA SET ID_CLOUT = " + idBean.getId_clout() + " WHERE ID_SEQ = "
									+ arrIdSeq.get(i) + "		\n");
							stmt.executeUpdate(sb.toString());
						}
						
						// 성향
						if (!"".equals(idBean.getId_senti())) {
							sb = new StringBuffer();
							sb.append(" UPDATE ISSUE_DATA SET ID_SENTI = " + idBean.getId_senti() + " WHERE ID_SEQ = "
									+ arrIdSeq.get(i) + "		\n");
							stmt.executeUpdate(sb.toString());
						}

						// 보고서
						if (!"".equals(idBean.getId_reportyn())) {
							sb = new StringBuffer();
							sb.append(" UPDATE ISSUE_DATA SET ID_REPORTYN = '" + idBean.getId_reportyn() + "' WHERE ID_SEQ = "
									+ arrIdSeq.get(i) + "		\n");
							stmt.executeUpdate(sb.toString());
						}
					}
					
					String[] arrTypeCode = null;
					String[] arrTemp = null;

					String hotel_type = "3,4,5,6";
					String tr_type = "7,8";
					String shp_type = "9,10";
					String sbtm_type = "11";
					String ceo_type = "12";
					String ir_type = "13";
					String esg_type = "14";
					String compare_code = "0";
					
					// 각 항목들 저장
					if (typeCodes != null && !typeCodes.equals("")) {
						arrTypeCode = typeCodes.split("@");
						for (String typeCode : arrTypeCode) {
							if (!"".equals(typeCode)) {
								arrTemp = typeCode.split(",");

								if (("update_multi".equals(mode))) {
									
									//구분 항목이 기존이랑 다르다면 하위 타입들 전부 다 삭제
									if("1".equals(arrTemp[0])) {
										
										sb = new StringBuffer();
										sb.append(" SELECT IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = " + arrIdSeq.get(i) + " AND IC_TYPE = 1 \n");
										System.out.println(sb.toString());
										rs = stmt.executeQuery(sb.toString());

										if (rs.next()) {
											compare_code = rs.getString("IC_CODE");
										}
										
										if(!"0".equals(compare_code) && !compare_code.equals(arrTemp[1])) {
											sb = new StringBuffer();
											sb.append("DELETE FROM ISSUE_DATA_CODE					    																	  \n");
											sb.append("      WHERE ID_SEQ =" + arrIdSeq.get(i) + "   																	  \n");
											sb.append("        AND IC_TYPE IN ("+hotel_type+","+tr_type+","+shp_type+","+sbtm_type+","+ceo_type+","+ir_type+","+esg_type+")   \n");
											stmt.executeUpdate(sb.toString());
											System.out.println(sb.toString());
										}
										compare_code = "0";
									}
									
									sb = new StringBuffer();
									sb.append("DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ = " + arrIdSeq.get(i) + "   \n");
									sb.append("								 AND IC_TYPE = " + arrTemp[0] + "   \n");
									System.out.println(sb.toString());
									stmt.executeUpdate(sb.toString());
								}
								
								sb = new StringBuffer();
								sb.append("INSERT INTO ISSUE_DATA_CODE		\n");
								sb.append("VALUES ( 						\n");
								sb.append("        " + arrIdSeq.get(i) + " 		\n");
								sb.append("        ," + arrTemp[0] + "		\n");
								sb.append("        ," + arrTemp[1] + "  	\n");
								sb.append("        ) 						\n");
								System.out.println(sb.toString());
								if (stmt.executeUpdate(sb.toString()) > 0)
									result = true;
							}
						}
					}
					
					if ("update_multi".equals(mode)) {
						//연관어 삭제
						sb = new StringBuffer();
						sb.append("DELETE FROM RELATION_KEYWORD_MAP WHERE ID_SEQ IN (" + arrIdSeq.get(i) + ")   \n");
						System.out.println(sb.toString());
						stmt.executeUpdate(sb.toString());
					}
					
					String[] arrRelName = null;
					String rkSeq = "0";
					String RelCode = "";			
									
					// 연관키워드 저장
					if (null != (idBean.getRelationkeys()) && !"".equals(idBean.getRelationkeys())) {
						
						RelCode = idBean.getRelationkeysCode();
						arrRelName = idBean.getRelationkeys().split("@");
						for (String relKey : arrRelName) {					
							sb = new StringBuffer();
							sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + relKey + "' \n");
							System.out.println(sb.toString());
							rs = stmt.executeQuery(sb.toString());

							if (rs.next()) {
								rkSeq = rs.getString("RK_SEQ");

								sb = null;
								sb = new StringBuffer();
								if(!"".equals(RelCode)) {
									sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
											+ arrIdSeq.get(i) + "," + rs.getInt("RK_SEQ") + ","+RelCode.split(",")[0]+","+RelCode.split(",")[1]+")  \n");
								}else {
									sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("
											+ arrIdSeq.get(i) + "," + rs.getInt("RK_SEQ") + ")  \n");
									
								}
								System.out.println(sb.toString());
								stmt.executeUpdate(sb.toString());
								
							} else {
								sb = null;
								sb = new StringBuffer();
								sb.append(" INSERT INTO RELATION_KEYWORD_R (RK_NAME) VALUES ('" + relKey + "')  \n");
								System.out.println(sb.toString());
								stmt.executeUpdate(sb.toString());
								
								sb = null;
								sb = new StringBuffer();
								rs = null;
								sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='" + relKey + "' \n");
								rs = stmt.executeQuery(sb.toString());

								if (rs.next()) {
									rkSeq = rs.getString("RK_SEQ");

									sb = null;
									sb = new StringBuffer();
									if(!"".equals(RelCode)) {
										sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, IC_TYPE, IC_CODE) VALUES ("
												+ arrIdSeq.get(i) + "," + rs.getInt("RK_SEQ") + ","+RelCode.split(",")[0]+","+RelCode.split(",")[1]+")  \n");
									}else {
										sb.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("
												+ arrIdSeq.get(i) + "," + rs.getInt("RK_SEQ") + ")  \n");								
									}
									System.out.println(sb.toString());
									stmt.executeUpdate(sb.toString());
									
									result = true;
								}
							}
						}
					}
				}
			}
			dbconn.Commit();
		} catch (SQLException ex) {
			Log.writeExpt(ex);
			Log.writeExpt("IssueCheck", ex);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} catch (Exception ex) {
			Log.writeExpt(ex);
			Log.writeExpt("IssueCheck", ex);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				e.printStackTrace();
				Log.writeExpt(e);
			}
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

	public boolean updateChildIssueData(IssueDataBean idBean, IssueCommentBean icBean, String typeCodes,
			String typeCodesTrend, String typeCodesInfo) {
		String[] arrTypeCode = null;
		String[] arrTemp = null;
		String[] arrTemp2 = null;
		String[] arrTemp3 = null;
		ArrayList arrPastTrend = new ArrayList();
		String[] arrRelKeyType = null;
		boolean result = false;

		try {

			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			dbconn.TransactionStart();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();
			sb.append(" SELECT ID_SEQ FROM ISSUE_DATA WHERE MD_PSEQ = " + idBean.getMd_pseq() + " AND MD_SEQ <> "
					+ idBean.getMd_seq() + " \n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			String idseqs = "";
			ArrayList<String> arrIdSeq = new ArrayList<String>();

			while (rs.next()) {
				if (idseqs.equals("")) {
					idseqs = rs.getString("ID_SEQ");
				} else {
					idseqs += "," + rs.getString("ID_SEQ");
				}

				arrIdSeq.add(rs.getString("ID_SEQ"));
			}

			System.out.println("idseqs : " + idseqs);
			/*
			 * if(!idseqs.equals("")){ sb = new StringBuffer();
			 * sb.append("SELECT ID_SEQ, IC_CODE, IC_PTYPE, IC_PCODE   			\n");
			 * sb.append("		FROM ISSUE_DATA_CODE_DETAIL   						\n");
			 * sb.append("		WHERE ID_SEQ IN ("+idseqs+") AND IC_TYPE = 9  		\n");
			 * System.out.println(sb.toString()); rs = stmt.executeQuery(sb.toString());
			 * while(rs.next()){
			 * 
			 * IssueCodeBean icb = new IssueCodeBean(); icb.setId_seq(rs.getInt("ID_SEQ"));
			 * icb.setIc_code(rs.getInt("IC_CODE")); icb.setIc_ptype(rs.getInt("IC_PTYPE"));
			 * icb.setIc_pcode(rs.getInt("IC_PCODE"));
			 * 
			 * arrPastTrend.add(icb);
			 * 
			 * } rs = null; }
			 */
			if (!idseqs.equals("")) {

				// 먼저 삭제후 인서트
				sb = new StringBuffer();
				sb.append(" DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ IN (" + idseqs
						+ ") AND IC_TYPE NOT IN ( 6, 17 )  \n");
				stmt.executeUpdate(sb.toString());

				/*
				 * sb = new StringBuffer();
				 * sb.append(" DELETE FROM ISSUE_DATA_CODE_DETAIL WHERE ID_SEQ IN ("
				 * +idseqs+") \n"); stmt.executeUpdate(sb.toString());
				 */

				sb = new StringBuffer();
				sb.append(" DELETE FROM RELATION_KEYWORD_MAP WHERE ID_SEQ IN (" + idseqs + ")  \n");
				stmt.executeUpdate(sb.toString());

				/*
				 * sb = new StringBuffer();
				 * sb.append("DELETE FROM ANA_ISSUE_DATA WHERE ID_SEQ IN ("+idseqs+")    \n");
				 * System.out.println(sb.toString()); stmt.executeUpdate(sb.toString());
				 */

				for (int i = 0; i < arrIdSeq.size(); i++) {

					if (typeCodes != null && !typeCodes.equals("")) {
						arrTypeCode = typeCodes.split("@");
						for (int j = 0; j < arrTypeCode.length; j++) {
							if (!arrTypeCode[j].equals("")) {
								arrTemp = arrTypeCode[j].split(",");

								if (!arrTemp[0].equals("6") && !arrTemp[0].equals("17")) {
									sb = new StringBuffer();
									sb.append("INSERT INTO ISSUE_DATA_CODE			\n");
									sb.append("VALUES ( 							\n");
									sb.append("        " + arrIdSeq.get(i) + " 		\n");
									sb.append("        ," + arrTemp[0] + "			\n");
									sb.append("        ," + arrTemp[1] + "  		\n");
									sb.append("        ) 							\n");
									System.out.println(sb.toString());
									if (stmt.executeUpdate(sb.toString()) > 0)
										result = true;
								}

							}
						}

					}

					// 각 대단위 분류체계 하위 속성 저장
					if (typeCodesTrend != null && !typeCodesTrend.equals("")) {
						arrTypeCode = typeCodesTrend.split("@");
						for (int j = 0; j < arrTypeCode.length; j++) {
							if (!arrTypeCode[j].equals("")) {
								arrTemp = arrTypeCode[j].split(",");

								sb = new StringBuffer();
								sb.append("INSERT INTO ISSUE_DATA_CODE_DETAIL	 	\n");
								sb.append("VALUES ( 								\n");
								sb.append("        " + arrIdSeq.get(i) + " 			\n");
								sb.append("        ," + arrTemp[1] + "				\n");
								sb.append("        ," + arrTemp[2] + "  			\n");
								sb.append("        ," + arrTemp[0] + "  			\n");
								sb.append("        ,0					  			\n");
								sb.append("        ) 								\n");
								System.out.println(sb.toString());
								if (stmt.executeUpdate(sb.toString()) > 0)
									result = true;

							}
						}
					}

					System.out.println("정보 속성 저장");
					// 정보 속성 저장
					if (typeCodesInfo != null && !typeCodesInfo.equals("")) {
						arrTypeCode = typeCodesInfo.split("@");
						for (int j = 0; j < arrTypeCode.length; j++) {
							if (!arrTypeCode[j].equals("")) {
								arrTemp = arrTypeCode[j].split(",");

								sb = new StringBuffer();
								sb.append("INSERT INTO ISSUE_DATA_CODE_DETAIL	 	\n");
								sb.append("VALUES ( 								\n");
								sb.append("        " + arrIdSeq.get(i) + " 			\n");
								sb.append("        ," + arrTemp[1] + "				\n");
								sb.append("        ," + arrTemp[2] + "  			\n");
								sb.append("        ," + arrTemp[0] + "  			\n");
								sb.append("        ,0					  			\n");
								sb.append("        ) 								\n");
								System.out.println(sb.toString());
								if (stmt.executeUpdate(sb.toString()) > 0)
									result = true;

							}
						}

					}

					/*
					 * System.out.println("지면 여부 저장"); String newsVal = idBean.getId_news();
					 * 
					 * if(newsVal!=null && !newsVal.equals("")){ arrTypeCode = newsVal.split(",");
					 * 
					 * sb = new StringBuffer();
					 * sb.append("INSERT INTO ISSUE_DATA_NEWS	 			\n");
					 * sb.append("VALUES ( 								\n"); sb.append("        " +
					 * arrIdSeq.get(i) + " 			\n"); sb.append("        ," + arrTypeCode[0] +
					 * "			\n"); sb.append("        ," + arrTypeCode[1] + "  		\n");
					 * sb.append("        ) 								\n");
					 * System.out.println(sb.toString()); if(stmt.executeUpdate(sb.toString())>0)
					 * result = true;
					 * 
					 * }
					 */
				}

				for (int i = 0; i < arrPastTrend.size(); i++) {

					IssueCodeBean icb = (IssueCodeBean) arrPastTrend.get(i);
					sb = new StringBuffer();
					sb.append("INSERT INTO ISSUE_DATA_CODE_DETAIL	 	\n");
					sb.append("VALUES ( 								\n");
					sb.append("        " + icb.getId_seq() + " 			\n");
					sb.append("        ,20								\n");
					sb.append("        ," + icb.getIc_code() + "  		\n");
					sb.append("        ," + icb.getIc_ptype() + "  		\n");
					sb.append("        ," + icb.getIc_pcode() + "		\n");
					sb.append("        ) 								\n");
					System.out.println(sb.toString());
					if (stmt.executeUpdate(sb.toString()) > 0)
						result = true;

				}
			}
			dbconn.Commit();
		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
			Log.writeExpt("IssueCheck", ex, sb.toString());
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Log.writeExpt(e);
				e.printStackTrace();
			}
		} catch (Exception ex) {
			Log.writeExpt(ex);
			Log.writeExpt("IssueCheck", ex);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				Log.writeExpt(e);
				e.printStackTrace();
			}
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
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
			dbconn.TransactionStart();
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
			sb.append("SELECT MD_SEQ FROM ISSUE_DATA WHERE MD_PSEQ IN ("+tempPNo+")            \n");
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				tempNo += tempNo.equals("")? rs.getString("MD_SEQ") : ","+rs.getString("MD_SEQ");				
			}	
			if(!tempNo.equals("")){
				String[] tempNo_list = tempNo.split(","); 
				int cnt = 0;
				/*
				sb = new StringBuffer();
				sb.append("UPDATE META SET ISSUE_CHECK='N' WHERE MD_SEQ IN ("+tempNo+")   \n");
				System.out.println(sb.toString());			
				stmt.executeUpdate(sb.toString());	
				 */
				for (int i = 0; i < tempNo_list.length; i++) {
					sb = new StringBuffer();
					sb.append("SELECT COUNT(*) AS CNT FROM SECTION WHERE MD_SEQ = "+tempNo_list[i]+"      \n");
					rs = stmt.executeQuery(sb.toString());
					
					if(rs.next()) {
						cnt = rs.getInt("CNT");
					}
					
					if(cnt == 1) {
						sb = new StringBuffer();
						sb.append("UPDATE SECTION SET ISSUE_CHECK='N' WHERE MD_SEQ = "+tempNo_list[i]+"   \n");
						System.out.println(sb.toString());			
						stmt.executeUpdate(sb.toString());	
					}
					
					
					sb = new StringBuffer();
					sb.append("SELECT COUNT(*) AS CNT FROM META WHERE MD_SEQ = "+tempNo_list[i]+"      \n");
					rs = stmt.executeQuery(sb.toString());
					
					if(rs.next()) {
						cnt = rs.getInt("CNT");
					}
					
					if(cnt == 1) {
						sb = new StringBuffer();
						sb.append("UPDATE META SET ISSUE_CHECK='N' WHERE MD_SEQ = "+tempNo_list[i]+"   \n");
						System.out.println(sb.toString());			
						stmt.executeUpdate(sb.toString());	
					}
					
					
				}
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
			sb.append("DELETE FROM RELATION_KEYWORD_MAP WHERE ID_SEQ IN ("+tempIseqNo+")   \n");						
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());		

			sb = new StringBuffer();
			sb.append("DELETE FROM AUTO_RELATION_KEYWORD_MAP WHERE ID_SEQ IN ("+tempIseqNo+")   \n");						
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());		

			sb = new StringBuffer();
			sb.append("DELETE FROM ISSUE_COMMENT WHERE ID_SEQ IN ("+tempIseqNo+")   \n");						
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());		

			sb = new StringBuffer();
			sb.append("DELETE FROM ISSUE_HASHTAG WHERE ID_SEQ IN ("+tempIseqNo+")   \n");						
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());		
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
			Log.writeExpt("IssueCheck", ex, sb.toString());
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.writeExpt(e );
			}
        } catch(Exception ex) {
        	Log.writeExpt(ex);
			Log.writeExpt("IssueCheck", ex);
			try {
				dbconn.Rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.writeExpt(e );
			}
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}            
        }        
        return result;
    }

	/**
	 * ISSUE_DATA - ID_TRANS_USEYN 값을 Y로 변경
	 * 
	 * @param id_seq : ISSUE_DATA.ID_SEQ
	 * @return
	 */
	public boolean transIssueData(String id_seq) {
		boolean result = false;
		String tempIseqNo = "";

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			dbconn.TransactionStart();
			stmt = dbconn.createStatement();

			tempIseqNo = id_seq;

			sb = new StringBuffer();
			sb.append("UPDATE ISSUE_DATA SET ID_TRANS_USEYN = 'Y' WHERE ID_SEQ IN (" + tempIseqNo + ")          \n");

			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());

			dbconn.Commit();

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

	/**
	 * ISSUE_DATA, ISSUE_CODE, ISSUE_COMMENT 삭제
	 * 
	 * @param id_seq : ISSUE_DATA.ID_SEQ
	 * @return 과거 성향
	 */
	// public void deleteMultiIssueData(String id_seqs)
	public void deleteMultiIssueData(String md_seqs) {
		boolean result = false;
		String tempNo = "";
		String tempIseqNo = "";
		// ArrayList arrPastTrend = new ArrayList();
		// String md_seqs = "";

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			/*
			 * sb = new StringBuffer();
			 * sb.append("SELECT MD_SEQ FROM ISSUE_DATA WHERE ID_SEQ IN ("
			 * +id_seqs+")                      \n"); System.out.println(sb.toString()); rs
			 * = stmt.executeQuery(sb.toString()); while(rs.next()){ md_seqs +=
			 * md_seqs.equals("")? rs.getString("MD_SEQ") : ","+rs.getString("MD_SEQ"); }
			 */

			// 일반 IDX 처리
			tempNo = "";
			tempIseqNo = "";
			sb = new StringBuffer();
			sb.append("SELECT MD_SEQ, ID_SEQ FROM ISSUE_DATA WHERE MD_PSEQ IN (" + md_seqs + ")           \n");
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			while (rs.next()) {
				tempNo += tempNo.equals("") ? rs.getString("MD_SEQ") : "," + rs.getString("MD_SEQ");
				tempIseqNo += tempIseqNo.equals("") ? rs.getString("ID_SEQ") : "," + rs.getString("ID_SEQ");

			}

			if (!tempNo.equals("")) {
				sb = new StringBuffer();
				sb.append("UPDATE META SET ISSUE_CHECK='N' WHERE MD_SEQ IN (" + tempNo + ")   \n");
				System.out.println(sb.toString());
				stmt.executeUpdate(sb.toString());

			}

			// 과거 성향 따로 저장
			/*
			 * sb = new StringBuffer(); sb.
			 * append(" SELECT A.MD_SEQ, B.IC_CODE, B.IC_PTYPE, B.IC_PCODE           				\n"
			 * );
			 * sb.append(" 	FROM  ISSUE_DATA A								           				\n"
			 * );
			 * sb.append(" 		 ,ISSUE_DATA_CODE_DETAIL B         									\n"
			 * ); sb.
			 * append("		WHERE A.ID_SEQ = B.ID_SEQ          										\n"
			 * );
			 * sb.append(" 	AND B.IC_TYPE = 9								           				\n"
			 * ); sb.append(" 	AND A.MD_PSEQ IN ("
			 * +md_seqs+")           								\n");
			 * 
			 * rs = stmt.executeQuery(sb.toString()); while(rs.next()){ IssueCodeBean icb =
			 * new IssueCodeBean(); icb.setMd_seq(rs.getInt("MD_SEQ"));
			 * icb.setIc_code(rs.getInt("IC_CODE")); icb.setIc_ptype(rs.getInt("IC_PTYPE"));
			 * icb.setIc_pcode(rs.getInt("IC_PCODE"));
			 * 
			 * arrPastTrend.add(icb); }
			 */
			sb = new StringBuffer();
			sb.append(" DELETE FROM ISSUE_DATA                                      \n");
			sb.append(" WHERE                                                       \n");
			sb.append("       MD_SEQ IN (" + tempNo + ")                                \n");
			System.out.println(sb.toString());
			if (stmt.executeUpdate(sb.toString()) > 0)
				result = true;

			sb = new StringBuffer();
			sb.append("DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ IN (" + tempIseqNo + ") \n");
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());

			/*
			 * sb = new StringBuffer();
			 * sb.append("DELETE FROM ISSUE_DATA_CODE_DETAIL WHERE ID_SEQ IN ("
			 * +id_seqs+") \n"); System.out.println(sb.toString());
			 * stmt.executeUpdate(sb.toString());
			 */

			/*
			 * sb = new StringBuffer();
			 * sb.append("DELETE FROM ISSUE_DATA_NEWS WHERE ID_SEQ IN ("+tempIseqNo+")   \n"
			 * ); System.out.println(sb.toString()); stmt.executeUpdate(sb.toString());
			 */

			/*
			 * sb = new StringBuffer();
			 * sb.append("DELETE FROM ISSUE_COMMENT WHERE ID_SEQ IN ("+tempIseqNo+")   \n");
			 * System.out.println(sb.toString()); stmt.executeUpdate(sb.toString());
			 */

			sb = new StringBuffer();
			sb.append("DELETE FROM RELATION_KEYWORD_MAP WHERE ID_SEQ IN (" + tempIseqNo + ") \n");
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
			Log.writeExpt("IssueCheck", ex, sb.toString());
			ex.printStackTrace();
		} catch (Exception ex) {
			Log.writeExpt(ex);
			Log.writeExpt("IssueCheck", ex);
			ex.printStackTrace();
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		// return arrPastTrend;
	}

	public ArrayList getMultiTrend(String md_seqs, int ic_type) {

		ArrayList multi_Trend = new ArrayList();
		IssueCodeBean icb = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();
			sb.append("  SELECT                             			\n");
			sb.append("         A.MD_SEQ                    			\n");
			sb.append("        ,B.IC_TYPE                   			\n");
			sb.append("        ,B.IC_CODE                   			\n");
			sb.append("        ,B.IC_PTYPE                  			\n");
			sb.append("        ,B.IC_PCODE                  			\n");
			sb.append("  FROM ISSUE_DATA A			        			\n");
			sb.append("  	  ,ISSUE_DATA_CODE_DETAIL B					\n");
			sb.append("  WHERE A.ID_SEQ = B.ID_SEQ          			\n");
			sb.append("  AND A.MD_SEQ IN (" + md_seqs + ")    			\n");
			sb.append("  AND B.IC_TYPE = 9		            			\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				icb = new IssueCodeBean();
				icb.setIc_seq(rs.getInt("MD_SEQ")); // MD_SEQ 임
				icb.setIc_type(rs.getInt("IC_TYPE"));
				icb.setIc_code(rs.getInt("IC_CODE"));
				icb.setIc_ptype(rs.getInt("IC_PTYPE"));
				icb.setIc_pcode(rs.getInt("IC_PCODE"));

				multi_Trend.add(icb);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return multi_Trend;
	}

	/**
	 * ISSUE_CODE 테이블을 조회하여 해당정보를 어레이로 반환.
	 * 
	 * @param id_seq : 페이징번호 0일시 페이징처리 안함
	 * @param type   : ISSUE_CODE.IC_PTYPE
	 * @param code   : ISSUE_CODE.IC_PCODE
	 * @return : 이슈코드 자식코드 리스트
	 */
	public ArrayList getIssueCode(String id_seq, String type, String code) {
		ArrayList arryList = new ArrayList();

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();

			sb.append("	SELECT   	 	    	                                                        \n");
			sb.append("	       A.ID_SEQ 	                                                            \n");
			sb.append("	       , B.IC_NAME 	                                                            \n");
			sb.append("	       , A.IC_TYPE                                                              \n");
			sb.append("	       , A.IC_CODE                                                              \n");
			sb.append("	       , B.IC_PTYPE                                                             \n");
			sb.append("	       , B.IC_PCODE                                                             \n");
			sb.append("	FROM ISSUE_DATA_CODE A                                                         	\n");
			sb.append("		 ,ISSUE_CODE B                                                            	\n");
			sb.append("	     WHERE A.IC_TYPE = B.IC_TYPE                                                \n");
			sb.append("	     AND A.IC_CODE = B.IC_CODE                                                  \n");
			sb.append("	     AND B.IC_PTYPE = " + type + "                                                  \n");
			sb.append("	     AND B.IC_PCODE = " + code + "                                                  \n");
			sb.append("		 AND A.ID_SEQ IN (" + id_seq + ")  						   				     	\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {

				icBean = new IssueCodeBean();
				icBean.setIc_name(rs.getString("IC_NAME"));
				icBean.setIc_type(rs.getInt("IC_TYPE"));
				icBean.setIc_code(rs.getInt("IC_CODE"));
				icBean.setIc_ptype(rs.getInt("IC_PTYPE"));
				icBean.setIc_pcode(rs.getInt("IC_PCODE"));
				arryList.add(icBean);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return arryList;

	}

	public ArrayList getCodeDeatil(String id_seq) {
		ArrayList arryList = new ArrayList();

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();
			sb.append("SELECT IC_TYPE	 								        	\n");
			sb.append("		  ,IC_CODE								                \n");
			sb.append("		  ,IC_PTYPE								                \n");
			sb.append(" 	  ,IC_PCODE 								            \n");
			sb.append("	FROM ISSUE_DATA_CODE_DETAIL			 						\n");
			sb.append("		WHERE ID_SEQ IN (" + id_seq + ")  							\n");
			// System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {

				icBean = new IssueCodeBean();
				icBean.setIc_type(rs.getInt("IC_TYPE"));
				icBean.setIc_code(rs.getInt("IC_CODE"));
				icBean.setIc_ptype(rs.getInt("IC_PTYPE"));
				icBean.setIc_pcode(rs.getInt("IC_PCODE"));

				arryList.add(icBean);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return arryList;

	}

	/**
	 * ISSUE_DATA.MD_SEQ 로 ISSUE_DATA ,ISSUE_DATA_CODE ,ISSUE_COMMENT 테이블을 조회하여
	 * 해당정보를 빈으로 반환.
	 * 
	 * @param md_seq : ISSUE_DATA.MD_SEQ
	 * @return
	 */
	public IssueDataBean getIssueDataBean(String md_seq) {
		IssueDataBean idBean = new IssueDataBean();
		ArrayList arrIdBean = new ArrayList();
		arrIdBean = getIssueDataList(0, 0, "", "", "", md_seq, "", "1", "", "", "", "", "", "", "Y", "", "", "", "", 0);
		if (arrIdBean.size() > 0) {
			idBean = (IssueDataBean) arrIdBean.get(0);
		}
		return idBean;
	}

	public IssueDataBean getIssueDataBean_V2(String md_seq) {

		IssueDataBean idBean = new IssueDataBean();

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append(" SELECT A.* 																	\n");
			sb.append(" 	 , FN_GET_SITE_SAME(A.MD_PSEQ,' ',' ') AS MD_SAME_CT					\n");
			sb.append("   	 , FN_GET_SITE_SAME_CHECK(A.MD_PSEQ,' ',' ') AS MD_SAME_CT_CHECK		\n");
			sb.append("   	 , (SELECT M_NAME FROM MEMBER WHERE M_SEQ = 3) AS M_NAME				\n");
			sb.append(" FROM(																		\n");
			sb.append("   SELECT ID.ID_SEQ															\n");
			sb.append("    		,ID.MD_SEQ															\n");
			sb.append(" 		,ID.I_SEQ 															\n");
			sb.append("  		,ID.IT_SEQ  														\n");
			sb.append("  		,ID.ID_URL															\n");
			sb.append("   		,ID.ID_TITLE														\n");
			sb.append("   		,ID.SG_SEQ															\n");
			sb.append("   		,(SELECT SG_NAME FROM SITE_GROUP WHERE SG_SEQ = ID.SG_SEQ) AS SG_NAME	\n");
			sb.append("  		,ID.S_SEQ 															\n");
			sb.append("   		,ID.MD_SITE_NAME													\n");
			sb.append(" 		,ID.MD_SITE_MENU 													\n");
			sb.append(" 		,DATE_FORMAT(ID.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE 			\n");
			sb.append(" 		,DATE_FORMAT(ID.ID_REGDATE,'%Y-%m-%d %H:%i:%s') AS ID_REGDATE 		\n");
			sb.append(" 		,ID.ID_WRITTER 														\n");
			sb.append(" 		,ID.ID_CONTENT	 													\n");
			sb.append(" 		,ID.ID_MAILYN 														\n");
			sb.append(" 		,ID.ID_USEYN 														\n");
			sb.append(" 		,ID.M_SEQ		 													\n");
			sb.append(" 		,ID.MD_TYPE		 													\n");
			sb.append(" 		,ID.L_ALPHA		 													\n");
			sb.append(" 		,ID.ID_REPORTYN 													\n");
			sb.append(" 		,ID.MD_PSEQ 														\n");
			sb.append(" 		,ID.REG_TYPE 														\n");
			sb.append(" 		,ID.ID_CLOUT 														\n");
			sb.append(" 		,ID.ID_SENTI 														\n");
			sb.append(" 		,ID.ID_TRANS_USEYN 													\n");
			sb.append(" 		,ID.ID_REPLYCOUNT 													\n");
			sb.append(" 	FROM ISSUE_DATA ID 														\n");
			sb.append(" 	WHERE ID.MD_SEQ = " + md_seq + "	 									\n");
			sb.append(" 	AND ID.ID_USEYN = 'Y'													\n");
			sb.append(" ) A							 												\n");
			sb.append(" ORDER BY ID_REGDATE DESC 													\n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			if (rs.next()) {
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
				idBean.setSg_name(rs.getString("SG_NAME"));
				idBean.setId_mailyn(rs.getString("ID_MAILYN"));
				idBean.setId_useyn(rs.getString("ID_USEYN"));
				idBean.setM_seq(rs.getString("M_SEQ"));
				idBean.setM_name(rs.getString("M_NAME"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setL_alpha(rs.getString("L_ALPHA"));
				idBean.setId_reportyn(rs.getString("ID_REPORTYN"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setTemp1(rs.getString("REG_TYPE"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				idBean.setMd_same_ct_check(rs.getString("MD_SAME_CT_CHECK"));
				idBean.setId_clout(rs.getString("ID_CLOUT"));
				idBean.setId_senti(rs.getString("ID_SENTI"));
				idBean.setId_trans_useyn(rs.getString("ID_TRANS_USEYN"));
				idBean.setReply_count(rs.getString("ID_REPLYCOUNT"));
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
			if (sb != null)
				sb = null;
		}

		return idBean;
	}

	public IssueDataBean getOneIssueDataBeanCode(String id_seq, String ic_type) {

		IssueDataBean bean = new IssueDataBean();

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append(
					"SELECT A.*           																														\n");
			sb.append("		, IF(''=A.IC_CODE ,'',FN_ISSUE_NAME(A.IC_CODE," + ic_type
					+ ")) AS IC_NAME          														\n");
			sb.append("		, IF(''=A.IC_CODE ,'',(SELECT IC_PCODE FROM ISSUE_CODE WHERE IC_TYPE = " + ic_type
					+ " AND IC_CODE = A.IC_CODE)) AS IC_PCODE       			\n");
			sb.append(
					" FROM(         																																\n");
			sb.append(
					"			SELECT I.ID_SEQ, I.ID_TITLE,  I.ID_CONTENT, I.ID_URL, I.MD_SITE_NAME           														\n");
			sb.append(
					"				, I.ID_REGDATE						       						 																\n");
			sb.append("				, IFNULL((SELECT IC_CODE FROM ISSUE_DATA_CODE IDC WHERE ID_SEQ=" + id_seq
					+ " AND IDC.IC_TYPE = " + ic_type + " ),'') AS IC_CODE        \n");
			sb.append(
					"    		FROM ISSUE_DATA I                            																						\n");
			sb.append("  	  WHERE I.ID_SEQ = " + id_seq
					+ "                    																						\n");
			sb.append(
					" )A                    																														\n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			if (rs.next()) {
				bean.setId_seq(rs.getString("ID_SEQ"));
				bean.setId_title(rs.getString("ID_TITLE"));
				bean.setId_content(rs.getString("ID_CONTENT"));
				bean.setId_url(rs.getString("ID_URL"));
				bean.setSg_name(rs.getString("MD_SITE_NAME"));
				bean.setId_regdate(rs.getString("ID_REGDATE"));
				bean.setTemp1(rs.getString("IC_CODE"));
				bean.setTemp2(rs.getString("IC_NAME"));
				bean.setTemp3(rs.getString("IC_PCODE"));
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
			if (sb != null)
				sb = null;
		}

		return bean;
	}

	public IssueDataBean getOneIssueDataBean(String id_seq) {

		IssueDataBean bean = new IssueDataBean();

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append("SELECT ID_SEQ, ID_TITLE,  ID_CONTENT, ID_URL, MD_SITE_NAME           			\n");
			sb.append(
					"		, ID_REGDATE, S_SEQ , MD_SITE_MENU        						 								\n");
			sb.append("    FROM ISSUE_DATA                             									\n");
			sb.append("    WHERE ID_SEQ = " + id_seq + "                    								\n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			if (rs.next()) {
				bean.setId_seq(rs.getString("ID_SEQ"));
				bean.setId_title(rs.getString("ID_TITLE"));
				bean.setId_content(rs.getString("ID_CONTENT"));
				bean.setId_url(rs.getString("ID_URL"));
				bean.setSg_name(rs.getString("MD_SITE_NAME"));
				bean.setId_regdate(rs.getString("ID_REGDATE"));
				bean.setS_seq(rs.getString("S_SEQ"));
				bean.setMd_site_menu(rs.getString("MD_SITE_MENU"));
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
			if (sb != null)
				sb = null;
		}

		return bean;
	}

	public Map getIssueDataCodeMap(String id_seq) {

		Map map = new HashMap();

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append(" SELECT	 																	\n");
			sb.append(" 	IC_TYPE																	\n");
			sb.append("   	, GROUP_CONCAT(IC_CODE) AS IC_CODES										\n");
			sb.append(" FROM																		\n");
			sb.append(" 	ISSUE_DATA_CODE															\n");
			sb.append(" WHERE ID_SEQ = " + id_seq + "	 											\n");
			sb.append(" GROUP BY IC_TYPE															\n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				map.put(rs.getString("IC_TYPE"), rs.getString("IC_CODES"));
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
			if (sb != null)
				sb = null;
		}

		return map;
	}
	
	public String getIssueDataCodeString(String md_seq, String ic_type) {

		String result = "";

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			
			
			sb = new StringBuffer();
			sb.append(" SELECT	 																	\n");
			sb.append(" 	IC_CODE																	\n");
			sb.append(" FROM																		\n");
			sb.append(" 	  ISSUE_DATA_CODE A														\n");
			sb.append(" 	, ISSUE_DATA B														\n");
			sb.append(" WHERE B.MD_SEQ = " + md_seq + "	 											\n");
			sb.append("   AND A.ID_SEQ = B.ID_SEQ	 											\n");
			sb.append("   AND IC_TYPE = " + ic_type + "	 											\n");
			sb.append(" GROUP BY IC_TYPE															\n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				result = rs.getString("IC_CODE");
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
			if (sb != null)
				sb = null;
		}

		return result;
	}

	public HashMap<String, ArrayList<String[]>> getSubdepthList(String str_m_ic_types) {
		String[] m_ic_types = str_m_ic_types.split(",");
		HashMap<String, ArrayList<String[]>> result = new HashMap<String, ArrayList<String[]>>();

		for (int i = 0; i < m_ic_types.length; i++) {
			result.put(m_ic_types[i], getSubdepth(m_ic_types[i]));
		}

		return result;
	}

	public ArrayList<String[]> getSubdepth(String m_type) {
		ArrayList<String[]> result = new ArrayList<String[]>();
		String[] tmp_type = null;

		while (true) {
			tmp_type = null;

			tmp_type = getSubSeq(m_type);
			if (null == tmp_type[0] || "".equals(tmp_type[0])) {
				break;
			} else {
				result.add(tmp_type);
				m_type = tmp_type[2];
			}
		}

		return result;
	}

	public String[] getSubSeq(String m_ic_type) {
		String result[] = new String[4];// 하위 ic_type
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append(" SELECT IC_SEQ, IC_NAME, IC_TYPE, IC_CODE			                    \n");
			sb.append("   FROM ISSUE_CODE                                                  \n");
			sb.append(" WHERE IC_USEYN = 'Y'                                               \n");
			sb.append("  AND IC_PCODE = 0 AND IC_PTYPE = " + m_ic_type + "	                   \n");
			sb.append(" ORDER BY IC_ORDER                                               \n");

			//System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			int idx = 0;
			if (rs.next()) {
				result[idx++] = rs.getString("IC_SEQ");
				result[idx++] = rs.getString("IC_NAME");
				result[idx++] = rs.getString("IC_TYPE");
				result[idx++] = rs.getString("IC_CODE");
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
			if (sb != null)
				sb = null;
		}

		return result;
	}

	public Map getIssueDataCodeDetailMap(String id_seq) {

		Map map = new HashMap();

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append(" SELECT	 																	\n");
			// sb.append(" CONCAT(IC_PTYPE, ',', IC_PCODE) AS PCODE_STR \n");
			sb.append(" 	IC_PTYPE AS PCODE_STR													\n");
			sb.append("   	, GROUP_CONCAT(CONCAT(IC_TYPE, ',', IC_CODE) SEPARATOR '@') AS CODE_STR	\n");
			sb.append(" FROM																		\n");
			sb.append(" 	ISSUE_DATA_CODE_DETAIL													\n");
			sb.append(" WHERE ID_SEQ = " + id_seq + "	 											\n");
			sb.append(" GROUP BY IC_PTYPE, IC_PCODE													\n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				map.put(rs.getString("PCODE_STR"), rs.getString("CODE_STR"));
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
			if (sb != null)
				sb = null;
		}

		return map;
	}

	public Map getRelationKeywordR_Map(String id_seq) {

		Map map = new HashMap();

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append(" SELECT	 																			\n");
			// sb.append(" CONCAT(RKM.IC_TYPE, ',', RKM.IC_CODE) AS CODE_STR \n");
			sb.append(" 	RKM.IC_TYPE AS CODE_STR															\n");
			sb.append(
					"   	, GROUP_CONCAT(CONCAT(CAST(RK.RK_SEQ AS CHAR), ',', RK.RK_NAME) SEPARATOR '@') AS RK_STR		\n");
			// sb.append(" , GROUP_CONCAT(RK.RK_NAME) AS RK_STR \n");
			sb.append(" FROM																				\n");
			sb.append(" 	RELATION_KEYWORD_MAP RKM														\n");
			sb.append(" INNER JOIN RELATION_KEYWORD_R RK ON RKM.RK_SEQ = RK.RK_SEQ							\n");
			sb.append(" WHERE RKM.ID_SEQ = " + id_seq + "		 											\n");
			sb.append(" AND RK.RK_USE = 'Y'																	\n");
			sb.append(" GROUP BY RKM.IC_TYPE, RKM.IC_CODE													\n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				map.put(rs.getString("CODE_STR"), rs.getString("RK_STR"));
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
			if (sb != null)
				sb = null;
		}

		return map;
	}

	public String getRelationKeyword_str(String id_seq) {

		String result = "";

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append("  SELECT RK.RK_NAME AS `RK_NAME`             \n");
			sb.append("   	  , MAP.IC_CODE	 AS 'IC_CODE'                                       \n");
			sb.append("   FROM RELATION_KEYWORD_MAP MAP                                        \n");
			sb.append("    INNER JOIN RELATION_KEYWORD_R RK ON RK.RK_SEQ = MAP.RK_SEQ          \n");
			sb.append("   WHERE RK.RK_USE = 'Y'                                                \n");
			sb.append("   AND MAP.ID_SEQ = " + id_seq + "                                          \n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				if(result.equals("")) {
					result = rs.getString("RK_NAME") + ":" + rs.getString("IC_CODE");
				} else {
					result += "@" + rs.getString("RK_NAME") + ":" + rs.getString("IC_CODE");
				}
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
			if (sb != null)
				sb = null;
		}

		return result;
	}

	public String getRelationKeyword_str(String id_seq, String hytype, String hycode) {

		String result = "";

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append("  SELECT GROUP_CONCAT(RK.RK_NAME SEPARATOR '@')AS `RK_NAME`             \n");
			sb.append("   FROM RELATION_KEYWORD_MAP MAP                                        \n");
			sb.append("    INNER JOIN RELATION_KEYWORD_R RK ON RK.RK_SEQ = MAP.RK_SEQ          \n");
		//	sb.append("   WHERE RK.RK_USE = 'Y'                                                \n");
			sb.append("   WHERE MAP.ID_SEQ = " + id_seq + "                                          \n");
			sb.append("   AND MAP.IC_TYPE = " + hytype + "                                    	   \n");
			sb.append("   AND MAP.IC_CODE = " + hycode + "                                         \n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			if (rs.next()) {
				result = rs.getString("RK_NAME");
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
			if (sb != null)
				sb = null;
		}

		return result;
	}

	public String getAutoRelationKeyword_str(String id_seq) {

		String result = "";

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append("  SELECT GROUP_CONCAT(RK.RK_NAME SEPARATOR '@')AS `RK_NAME`             \n");
			sb.append("   FROM AUTO_RELATION_KEYWORD_MAP MAP                                        \n");
			sb.append("    INNER JOIN RELATION_KEYWORD_R RK ON RK.RK_SEQ = MAP.RK_SEQ          \n");
			sb.append("   WHERE RK.RK_USE = 'Y'                                                \n");
			sb.append("   AND MAP.ID_SEQ = " + id_seq + "                                          \n");
			sb.append("   ORDER BY RK_SEQ DESC		                                          \n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			if (rs.next()) {
				result = rs.getString("RK_NAME");
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
			if (sb != null)
				sb = null;
		}

		return result;
	}

	/**
	 * ISSUE_DATA ,ISSUE_DATA_CODE ,ISSUE_COMMENT 테이블을 조회하여 해당정보를 어레이로 반환.
	 * 
	 * @param pageNum       : 페이징번호 0일시 페이징처리 안함
	 * @param id_seq        : ISSUE_DATA.ID_SEQ
	 * @param i_seq         : ISSUE_DATA.I_SEQ
	 * @param it_seq        : ISSUE_DATA.IT_SEQ
	 * @param md_seq        : ISSUE_DATA.MD_SEQ
	 * @param schKey        : ISSUE_DATA.ID_TITLE
	 * @param DateType      : 1:ISSUE_DATA.ID_REGDATE 2:ISSUE_DATA.MD_DATE
	 * @param schSdate      : ISSUE_DATA.ID_REGDATE , ISSUE_DATA.MD_DATE
	 * @param schEdate      : ISSUE_DATA.ID_REGDATE , ISSUE_DATA.MD_DATE
	 * @param typeCode      : ISSUE_DATA_CODE.*
	 * @param typeCodeOrder : ISSUE_DATA_CODE.*
	 * @param Order         : 정렬하고픈 code type
	 * @param useYN         : 보고서 사용여부
	 * @return : 이슈데이터 검색리스트
	 */
	public ArrayList getIssueDataList(int pageNum, int rowCnt, String id_seq, String i_seq, String it_seq,
			String md_seq, String schKey, String DateType, String schSdate, String schStime, String schEdate,
			String schEtime, String typeCode, String typeCodeOrder, String useYN, String language, String reportyn,
			String parents, String s_seq, int tier) {
		ArrayList issueDataList = new ArrayList();
		String[] tmpArr = typeCode.split("@");
		String[] arrTypeCode = null;
		String tmpId_seq = null;

		// code에 대한 쿼리문을 생성한다.
		String tmpSameCodeYn = "";
		int sameCodeCount = 0;
		String codeQuery = "SELECT DISTINCT(ID_SEQ) \n";
		codeQuery += "FROM ISSUE_DATA_CODE   \n";
		codeQuery += "WHERE                  \n";
		codeQuery += "	 1=1               \n";
		for (int i = 0; i < tmpArr.length; i++) {

			if (!tmpArr[i].equals("")) {
				arrTypeCode = tmpArr[i].split(",");
				if (arrTypeCode.length == 2) {
					if (tmpSameCodeYn.equals(arrTypeCode[0]))
						sameCodeCount++;
					codeQuery = i == 0
							? codeQuery + "AND (IC_TYPE=" + arrTypeCode[0] + " AND IC_CODE=" + arrTypeCode[1] + ") \n"
							: codeQuery + " OR (IC_TYPE=" + arrTypeCode[0] + " AND IC_CODE=" + arrTypeCode[1] + ") \n";
					tmpSameCodeYn = arrTypeCode[0];
				}
			}
		}
		codeQuery += "GROUP BY ID_SEQ HAVING COUNT(0)=" + (tmpArr.length - sameCodeCount);

		// 리스트 시작, 끝번호
		if (rowCnt > 0)
			this.startNum = (pageNum - 1) * rowCnt;
		this.endNum = rowCnt;

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();
			sb.append("		SELECT COUNT(ID.ID_SEQ) AS CNT 								\n");
			sb.append("		     , SUM(ID.MD_SAME_CT + 1) AS SAME_CNT 					\n");
			sb.append("		FROM ISSUE_DATA ID                                          \n");
			if (!typeCode.equals("")) {
				sb.append("  INNER JOIN (" + codeQuery + ")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
			}

			/*
			 * if(tier == 1){ sb.
			 * append("         INNER JOIN TIER_SITE E ON E.TS_SEQ = ID.S_SEQ									\n"
			 * ); }
			 */

			sb.append("		WHERE 1=1													\n");
			if (!id_seq.equals("")) {
				sb.append("		AND	ID.ID_SEQ IN (" + id_seq + ")  							\n");
			}
			if (!i_seq.equals("")) {
				sb.append("		AND	ID.I_SEQ IN (" + i_seq + ")  									\n");
			}
			if (!it_seq.equals("")) {
				sb.append("		AND	ID.IT_SEQ IN (" + it_seq + ")  									\n");
			}
			if (!md_seq.equals("")) {
				sb.append("		AND	ID.MD_SEQ IN (" + md_seq + ") 			                        \n");
			}
			if (!schKey.equals("")) {
				sb.append("		AND	ID.ID_TITLE LIKE '%" + schKey + "%'  							\n");
			}
			if (!schSdate.equals("") && !schEdate.equals("")) {
				if (!schStime.equals("") && !schEtime.equals("")) {
					if (DateType.equals("1")) {
						sb.append("		AND	ID.ID_REGDATE BETWEEN '" + schSdate + " " + schStime + "' AND '" + schEdate
								+ " " + schEtime + "'            	\n");
					} else {
						sb.append("		AND	ID.MD_DATE BETWEEN '" + schSdate + " " + schStime + "' AND '" + schEdate
								+ " " + schEtime + "'            		\n");
					}
				} else {
					if (DateType.equals("1")) {
						sb.append("		AND	ID.ID_REGDATE BETWEEN '" + schSdate + " 00:00:00' AND '" + schEdate
								+ " 23:59:59'            	\n");
					} else {
						sb.append("		AND	ID.MD_DATE BETWEEN '" + schSdate + " 00:00:00' AND '" + schEdate
								+ " 23:59:59'            		\n");
					}
				}
			}
			if (!useYN.equals("")) {
				sb.append(" 	AND ID.ID_USEYN = '" + useYN + "'									    \n");
			}
			if (!language.equals("")) {
				sb.append(" 	AND ID.L_ALPHA = '" + language + "'									    \n");
			}

			// 모기사일경우
			if (parents.equals("Y")) {
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}

			if (!s_seq.equals("")) {
				sb.append(" AND ID.S_SEQ = " + s_seq + "\n");
			}

			/*
			 * // 기사 유형~~ if(parents.equals("super")){ //모기사일경우
			 * sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n"); }else
			 * if(parents.equals("child")){ //자기사일경우
			 * sb.append(" AND ID.MD_SEQ <> ID.MD_PSEQ		\n"); }else
			 * if(parents.equals("self")){ //수동등록
			 * sb.append(" AND M_SEQ NOT IN (1,3)		\n"); }else
			 * if(parents.equals("childSelf")){ //모기사 + 수동등록
			 * sb.append(" AND (ID.MD_SEQ = ID.MD_PSEQ OR M_SEQ NOT IN (1,3))		\n"); }
			 */

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			if (rs.next()) {
				totalIssueDataCnt = rs.getInt("CNT");
				totalSameDataCnt = rs.getInt("SAME_CNT");
			}

			rs.close();
			sb = new StringBuffer();

			sb.append("SELECT A.*													\n");
			sb.append("     , FN_GET_SITE_SAME(A.MD_PSEQ,'" + schSdate + " " + schStime + "','" + schEdate + " "
					+ schEtime + "') AS MD_SAME_CT 			\n");
			sb.append("     , FN_GET_SITE_SAME_CHECK(A.MD_PSEQ,'" + schSdate + " " + schStime + "','" + schEdate + " "
					+ schEtime + "') AS MD_SAME_CT_CHECK \n");
			sb.append("     , (SELECT M_NAME FROM MEMBER WHERE M_SEQ = 3) AS M_NAME	\n");
			sb.append("  FROM(														\n");

			sb.append(" SELECT ID.ID_SEQ                                                         \n");
			sb.append("        ,ID.MD_SEQ                                                        \n");
			sb.append("        ,ID.I_SEQ                                                     	  \n");
			sb.append("        ,ID.IT_SEQ                                                     	  \n");
			sb.append("        ,ID.ID_URL                                                        \n");
			sb.append("        ,ID.ID_TITLE                                                      \n");
			// sb.append(" ,ID.ID_URL \n");
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
			// sb.append(" ,ID.MD_SAME_CT \n");
			sb.append("        ,ID.MD_TYPE                                                    	 \n");
			// sb.append(" ,(SELECT M_NAME FROM MEMBER WHERE M_SEQ = ID.M_SEQ) AS M_NAME
			// \n");
			sb.append("        , ID.L_ALPHA 													 \n");
			sb.append("        , ID.ID_REPORTYN 													 \n");
			sb.append("        , ID.MD_PSEQ 													 \n");

			if (!typeCodeOrder.equals("")) {
				sb.append("    ,IDC.IC_CODE      \n");
			}
			sb.append(" FROM ISSUE_DATA ID                                                    \n");
			if (!typeCodeOrder.equals("") && typeCode.equals("")) {
				sb.append("  INNER JOIN (SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE  WHERE  IC_TYPE = " + typeCodeOrder
						+ ") IDC  ON (ID.ID_SEQ = IDC.ID_SEQ)    \n");
			}
			if (!typeCode.equals("") && typeCodeOrder.equals("")) {
				sb.append("  INNER JOIN (" + codeQuery + ")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			if (!typeCodeOrder.equals("") && !typeCode.equals("")) {
				sb.append("  INNER JOIN (SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE  WHERE  IC_TYPE = " + typeCodeOrder
						+ ") IDC    \n");
				sb.append("  INNER JOIN (" + codeQuery
						+ ")IC ON (ID.ID_SEQ = IDC.ID_SEQ AND ID.ID_SEQ = IC.ID_SEQ)    \n");
			}

			/*
			 * if(tier == 1){ sb.
			 * append("         INNER JOIN TIER_SITE E ON E.TS_SEQ = ID.S_SEQ									\n"
			 * ); }
			 */

			sb.append(" WHERE 1=1										      \n");
			if (!reportyn.equals("")) {
				sb.append("   AND ID.ID_REPORTYN = '" + reportyn + "' 						      \n");
			}

			if (!id_seq.equals("")) {
				sb.append("		AND	ID.ID_SEQ IN (" + id_seq + ")  									  \n");
			}
			if (!i_seq.equals("")) {
				sb.append("		AND	ID.I_SEQ IN (" + i_seq + ")  									\n");
			}
			if (!it_seq.equals("")) {
				sb.append("		AND	ID.IT_SEQ IN (" + it_seq + ")  									\n");
			}
			if (!md_seq.equals("")) {
				sb.append("		AND	ID.MD_SEQ in (" + md_seq + ") 			                          \n");
			}
			if (!schKey.equals("")) {
				sb.append("		AND	ID.ID_TITLE LIKE '%" + schKey + "%'  							  \n");
			}
			if (!schSdate.equals("") && !schEdate.equals("")) {
				if (!schStime.equals("") && !schEtime.equals("")) {
					if (DateType.equals("1")) {
						sb.append("		AND	ID.ID_REGDATE BETWEEN '" + schSdate + " " + schStime + "' AND '" + schEdate
								+ " " + schEtime + "'            	\n");
					} else {
						sb.append("		AND	ID.MD_DATE BETWEEN '" + schSdate + " " + schStime + "' AND '" + schEdate
								+ " " + schEtime + "'            		\n");
					}
				} else {
					if (DateType.equals("1")) {
						sb.append("		AND	ID.ID_REGDATE BETWEEN '" + schSdate + " 00:00:00' AND '" + schEdate
								+ " 23:59:59'            	\n");
					} else {
						sb.append("		AND	ID.MD_DATE BETWEEN '" + schSdate + " 00:00:00' AND '" + schEdate
								+ " 23:59:59'            		\n");
					}
				}
			}
			if (!useYN.equals("")) {
				sb.append(" 	AND ID.ID_USEYN = '" + useYN + "'									      \n");
			}
			if (!language.equals("")) {
				sb.append(" 	AND ID.L_ALPHA = '" + language + "'									    \n");
			}

			if (parents.equals("Y")) {
				// 모기사일경우
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}

			if (!s_seq.equals("")) {
				sb.append(" AND ID.S_SEQ = " + s_seq + "\n");
			}

			/*
			 * // 기사 유형~~ if(parents.equals("super")){ //모기사일경우
			 * sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n"); }else
			 * if(parents.equals("child")){ //자기사일경우
			 * sb.append(" AND ID.MD_SEQ <> ID.MD_PSEQ		\n"); }else
			 * if(parents.equals("self")){ //수동등록
			 * sb.append(" AND M_SEQ NOT IN (1,3)		\n"); }else
			 * if(parents.equals("childSelf")){ //모기사 + 수동등록
			 * sb.append(" AND (ID.MD_SEQ = ID.MD_PSEQ OR M_SEQ NOT IN (1,3))		\n"); }
			 */
			// sb.append(" AND ID.ID_SEQ <> 176441 \n");
			// sb.append(" AND ID.ID_SEQ <> 176440 \n");

			if (DateType.equals("1")) {
				if (!typeCodeOrder.equals("")) {
					sb.append(" ORDER BY IDC.IC_CODE, ID_REGDATE DESC 		                                      \n");
				} else {
					sb.append(" ORDER BY ID_REGDATE DESC 		                                      \n");
				}

			} else {
				if (!typeCodeOrder.equals("")) {
					sb.append(" ORDER BY IDC.IC_CODE, MD_DATE DESC 		                                      \n");
				} else {
					sb.append("	ORDER BY MD_DATE DESC 		                                          \n");
				}
			}
			if (pageNum > 0) {
				sb.append(" LIMIT   " + startNum + "," + endNum + "                                       \n");
			}

			sb.append("  )A                                                                    \n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			tmpId_seq = "";
			while (rs.next()) {
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
				idBean.setMd_same_ct_check(rs.getString("MD_SAME_CT_CHECK"));

				issueDataList.add(idBean);
				if (tmpId_seq.equals(""))
					tmpId_seq += rs.getString("ID_SEQ");
				else
					tmpId_seq += "," + rs.getString("ID_SEQ");
			}

			String prevI_seq = null;
			if (tmpId_seq.length() > 0) {

				// 지면여부 정보 가져오기 2018.02.07 고기범
				/*
				 * rs.close(); sb = new StringBuffer();
				 * sb.append("SELECT * FROM ISSUE_DATA_NEWS		\n");
				 * sb.append("	WHERE ID_SEQ IN ("+ tmpId_seq +")	\n");
				 * 
				 * System.out.println(sb.toString()); rs = stmt.executeQuery(sb.toString()); if
				 * ( rs.next() ) { idBean.setId_news(rs.getInt("IC_ASPECT")+ ","
				 * +rs.getInt("IC_COLONNES")); }
				 */

				// 이슈에 이슈코드어레이 추가
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
				sb.append("WHERE	IDC.ID_SEQ IN (" + tmpId_seq + ")  						\n");
				sb.append("         AND IC.IC_CODE = IDC.IC_CODE   						\n");
				sb.append("         AND IC.IC_TYPE = IDC.IC_TYPE	 					\n");
				sb.append("ORDER BY IDC.ID_SEQ DESC					 					\n");
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());

				while (rs.next()) {

					if (prevI_seq != null && !prevI_seq.equals(rs.getString("ID_SEQ"))) {
						addIssueCode(issueDataList, prevI_seq, ArrIcList);
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
				addIssueCode(issueDataList, prevI_seq, ArrIcList);

				/*
				 * //이슈에 이슈코멘트어레이 추가 rs = null; prevI_seq = null; sb = new StringBuffer();
				 * ArrayList arrIcmList = new ArrayList();
				 * 
				 * sb.
				 * append("SELECT IM_SEQ, IM_COMMENT, DATE_FORMAT(IM_DATE,'%Y-%m-%d %H:%i:%s') IM_DATE, ID_SEQ \n"
				 * ); sb.
				 * append("FROM ISSUE_COMMENT	 						                           \n"
				 * ); sb.append("WHERE ID_SEQ IN ("
				 * +tmpId_seq+")  					                       \n");
				 * sb.append("ORDER BY IM_SEQ DESC                                 				   \n"
				 * ); System.out.println(sb.toString()); rs = stmt.executeQuery(sb.toString());
				 * 
				 * while(rs.next()){
				 * 
				 * if(prevI_seq!=null && !prevI_seq.equals(rs.getString("ID_SEQ")) ) {
				 * addIssueCode(issueDataList,prevI_seq,arrIcmList); arrIcmList = new
				 * ArrayList(); }
				 * 
				 * prevI_seq = rs.getString("ID_SEQ");
				 * 
				 * icmBean = new IssueCommentBean(); icmBean.setIm_seq(rs.getString("IM_SEQ"));
				 * icmBean.setIm_comment(rs.getString("IM_COMMENT"));
				 * icmBean.setIm_date(rs.getString("IM_DATE"));
				 * 
				 * arrIcmList.add(icmBean); }
				 * addIssueComment(issueDataList,prevI_seq,arrIcmList);
				 */
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return issueDataList;
	}

	public String getSunghyang(String id_seq, int type) {
		String str = "";

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();

			sb.append("SELECT IC.IC_NAME \n");
			sb.append("FROM ISSUE_CODE IC, ISSUE_DATA_CODE IDCD \n");
			sb.append("WHERE IC.IC_TYPE = IDCD.IC_TYPE \n");
			sb.append("AND IC.IC_CODE = IDCD.IC_CODE \n");
			sb.append("AND IDCD.ID_SEQ = " + id_seq + " AND IDCD.IC_TYPE=" + type + " LIMIT 1 \n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				str = rs.getString("IC_NAME");
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return str;
	}

	public ArrayList getIssueDataList_groupBy(int pageNum, int rowCnt, String id_seq, String i_seq, String it_seq,
			String md_seq, String schKey, String DateType, String schSdate, String schStime, String schEdate,
			String schEtime, String typeCode, String typeCodeOrder, String useYN, String language, String reportyn,
			String parents, String mg_company) {
		ArrayList issueDataList = new ArrayList();
		String[] tmpArr = typeCode.split("@");
		String[] arrTypeCode = null;
		String tmpId_seq = null;

		// code에 대한 쿼리문을 생성한다.
		String tmpSameCodeYn = "";
		int sameCodeCount = 0;
		String codeQuery = "SELECT DISTINCT(ID_SEQ) \n";
		codeQuery += "FROM ISSUE_DATA_CODE   \n";
		codeQuery += "WHERE                  \n";
		codeQuery += "	 1=1               \n";
		for (int i = 0; i < tmpArr.length; i++) {

			if (!tmpArr[i].equals("")) {
				arrTypeCode = tmpArr[i].split(",");
				if (arrTypeCode.length == 2) {
					if (tmpSameCodeYn.equals(arrTypeCode[0]))
						sameCodeCount++;
					codeQuery = i == 0
							? codeQuery + "AND (IC_TYPE=" + arrTypeCode[0] + " AND IC_CODE=" + arrTypeCode[1] + ") \n"
							: codeQuery + " OR (IC_TYPE=" + arrTypeCode[0] + " AND IC_CODE=" + arrTypeCode[1] + ") \n";
					tmpSameCodeYn = arrTypeCode[0];
				}
			}
		}

		codeQuery += "GROUP BY ID_SEQ HAVING COUNT(0)=" + (tmpArr.length - sameCodeCount);

		// 리스트 시작, 끝번호
		if (rowCnt > 0)
			this.startNum = (pageNum - 1) * rowCnt;
		this.endNum = rowCnt;

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();

			sb.append("SELECT COUNT(*) AS CNT, SUM(SAME_CNT) AS SAME_CNT FROM (\n");
			sb.append("		SELECT MD_PSEQ AS CNT 								\n");
			// sb.append(" SELECT COUNT(ID.ID_SEQ) AS CNT \n");
			sb.append("		     , COUNT(ID.MD_PSEQ) AS SAME_CNT 					\n");
			sb.append("		FROM ISSUE_DATA ID                                          \n");
			if (!typeCode.equals("")) {
				sb.append("  INNER JOIN (" + codeQuery + ")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			if (!mg_company.equals("")) {
				sb.append("  INNER JOIN ISSUE_DATA_CODE T ON ID.ID_SEQ = T.ID_SEQ AND T.IC_TYPE = 7 AND T.IC_CODE IN ("
						+ mg_company + ")  \n");
			}

			sb.append("		WHERE 1=1													\n");
			if (!id_seq.equals("")) {
				sb.append("		AND	ID.ID_SEQ IN (" + id_seq + ")  							\n");
			}
			if (!i_seq.equals("")) {
				sb.append("		AND	ID.I_SEQ IN (" + i_seq + ")  									\n");
			}
			if (!it_seq.equals("")) {
				sb.append("		AND	ID.IT_SEQ IN (" + it_seq + ")  									\n");
			}
			if (!md_seq.equals("")) {
				sb.append("		AND	ID.MD_SEQ IN (" + md_seq + ") 			                        \n");
			}
			if (!schKey.equals("")) {
				String[] arrSchKey = schKey.split(" ");

				for (int i = 0; i < arrSchKey.length; i++) {
					sb.append("		AND	ID.ID_TITLE LIKE '%" + arrSchKey[i] + "%'  							\n");
				}

			}
			if (!schSdate.equals("") && !schEdate.equals("")) {
				if (!schStime.equals("") && !schEtime.equals("")) {
					if (DateType.equals("1")) {
						sb.append("		AND	ID.ID_REGDATE BETWEEN '" + schSdate + " " + schStime + "' AND '" + schEdate
								+ " " + schEtime + "'            	\n");
					} else {
						sb.append("		AND	ID.MD_DATE BETWEEN '" + schSdate + " " + schStime + "' AND '" + schEdate
								+ " " + schEtime + "'            		\n");
					}
				} else {
					if (DateType.equals("1")) {
						sb.append("		AND	ID.ID_REGDATE BETWEEN '" + schSdate + " 00:00:00' AND '" + schEdate
								+ " 23:59:59'            	\n");
					} else {
						sb.append("		AND	ID.MD_DATE BETWEEN '" + schSdate + " 00:00:00' AND '" + schEdate
								+ " 23:59:59'            		\n");
					}
				}
			}
			if (!useYN.equals("")) {
				sb.append(" 	AND ID.ID_USEYN = '" + useYN + "'									    \n");
			}
			if (!language.equals("")) {
				sb.append(" 	AND ID.L_ALPHA = '" + language + "'									    \n");
			}
			// 모기사일경우
			if (parents.equals("Y")) {
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}

			sb.append(" GROUP BY ID.MD_PSEQ)A		\n");

			/*
			 * // 기사 유형~~ if(parents.equals("super")){ //모기사일경우
			 * sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n"); }else
			 * if(parents.equals("child")){ //자기사일경우
			 * sb.append(" AND ID.MD_SEQ <> ID.MD_PSEQ		\n"); }else
			 * if(parents.equals("self")){ //수동등록
			 * sb.append(" AND M_SEQ NOT IN (1,3)		\n"); }else
			 * if(parents.equals("childSelf")){ //모기사 + 수동등록
			 * sb.append(" AND (ID.MD_SEQ = ID.MD_PSEQ OR M_SEQ NOT IN (1,3))		\n"); }
			 */

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			if (rs.next()) {
				totalIssueDataCnt = rs.getInt("CNT");
				totalSameDataCnt = rs.getInt("SAME_CNT");
			}

			rs.close();
			sb = new StringBuffer();

			sb.append("SELECT A.* FROM (	\n");

			sb.append("SELECT A.*													\n");
			sb.append("     , FN_GET_SITE_SAME(A.MD_PSEQ,'" + schSdate + " " + schStime + "','" + schEdate + " "
					+ schEtime + "') AS MD_SAME_CT 			\n");
			sb.append("     , FN_GET_SITE_SAME_CHECK(A.MD_PSEQ,'" + schSdate + " " + schStime + "','" + schEdate + " "
					+ schEtime + "') AS MD_SAME_CT_CHECK \n");
			sb.append("     , (SELECT M_NAME FROM MEMBER WHERE M_SEQ = 3) AS M_NAME	\n");
			sb.append("  FROM(														\n");

			sb.append(" SELECT ID.ID_SEQ                                                         \n");
			sb.append("        ,ID.MD_SEQ                                                        \n");
			sb.append("        ,ID.I_SEQ                                                     	  \n");
			sb.append("        ,ID.IT_SEQ                                                     	  \n");
			sb.append("        ,ID.ID_URL                                                        \n");
			sb.append("        ,ID.ID_TITLE                                                      \n");
			// sb.append(" ,ID.ID_URL \n");
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
			// sb.append(" ,ID.MD_SAME_CT \n");
			sb.append("        ,ID.MD_TYPE                                                    	 \n");
			// sb.append(" ,(SELECT M_NAME FROM MEMBER WHERE M_SEQ = ID.M_SEQ) AS M_NAME
			// \n");
			sb.append("        , ID.L_ALPHA 													 \n");
			sb.append("        , ID.ID_REPORTYN 													 \n");
			sb.append("        , ID.MD_PSEQ 													 \n");

			sb.append("        , ID.K_XP 														 \n");
			sb.append("        , ID.K_YP 														 \n");
			sb.append("        , ID.MEDIA_INFO 													 \n");
			sb.append("        , FN_GET_ISSUE_CODE(ID.ID_SEQ,9) AS TYPE9					 \n");

			sb.append("   FROM ( 													 \n");

			sb.append("SELECT ID.* FROM ISSUE_DATA ID 													 \n");

			if (!typeCode.equals("") && typeCodeOrder.equals("")) {
				sb.append("  INNER JOIN (" + codeQuery + ")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			if (!mg_company.equals("")) {
				sb.append("  INNER JOIN ISSUE_DATA_CODE T ON ID.ID_SEQ = T.ID_SEQ AND T.IC_CODE IN (" + mg_company
						+ ")  \n");
			}

			sb.append(" WHERE 1=1										      \n");
			if (!reportyn.equals("")) {
				sb.append("   AND ID.ID_REPORTYN = '" + reportyn + "' 						      \n");
			}

			if (!id_seq.equals("")) {
				sb.append("		AND	ID.ID_SEQ IN (" + id_seq + ")  									  \n");
			}
			if (!i_seq.equals("")) {
				sb.append("		AND	ID.I_SEQ IN (" + i_seq + ")  									\n");
			}
			if (!it_seq.equals("")) {
				sb.append("		AND	ID.IT_SEQ IN (" + it_seq + ")  									\n");
			}
			if (!md_seq.equals("")) {
				sb.append("		AND	ID.MD_SEQ in (" + md_seq + ") 			                          \n");
			}
			if (!schKey.equals("")) {

				String[] arrSchKey = schKey.split(" ");

				for (int i = 0; i < arrSchKey.length; i++) {
					sb.append("		AND	ID.ID_TITLE LIKE '%" + arrSchKey[i] + "%'  							\n");
				}

			}
			if (!schSdate.equals("") && !schEdate.equals("")) {
				if (!schStime.equals("") && !schEtime.equals("")) {
					if (DateType.equals("1")) {
						sb.append("		AND	ID.ID_REGDATE BETWEEN '" + schSdate + " " + schStime + "' AND '" + schEdate
								+ " " + schEtime + "'            	\n");
					} else {
						sb.append("		AND	ID.MD_DATE BETWEEN '" + schSdate + " " + schStime + "' AND '" + schEdate
								+ " " + schEtime + "'            		\n");
					}
				} else {
					if (DateType.equals("1")) {
						sb.append("		AND	ID.ID_REGDATE BETWEEN '" + schSdate + " 00:00:00' AND '" + schEdate
								+ " 23:59:59'            	\n");
					} else {
						sb.append("		AND	ID.MD_DATE BETWEEN '" + schSdate + " 00:00:00' AND '" + schEdate
								+ " 23:59:59'            		\n");
					}
				}
			}
			if (!useYN.equals("")) {
				sb.append(" 	AND ID.ID_USEYN = '" + useYN + "'									      \n");
			}
			if (!language.equals("")) {
				sb.append(" 	AND ID.L_ALPHA = '" + language + "'									    \n");
			}

			if (parents.equals("Y")) {
				// 모기사일경우
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}
			sb.append(" ORDER BY MD_SEQ ASC		\n");

			sb.append("   )ID 																 \n");
			sb.append("   GROUP BY ID.MD_PSEQ 												 \n");
			sb.append("   ORDER BY ID.ID_SEQ DESC 											 \n");
			if (pageNum > 0) {
				sb.append(" LIMIT   " + startNum + "," + endNum + "                                       \n");
			}

			sb.append("  )A                                                                    \n");

			// sb.append(") A LEFT OUTER JOIN CONNECT_VOC B ON A.ID_SEQ = B.ID_SEQ \n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			tmpId_seq = "";
			while (rs.next()) {
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
				idBean.setMd_same_ct_check(rs.getString("MD_SAME_CT_CHECK"));
				// idBean.setV_seq(rs.getString("V_SEQ"));
				idBean.setTemp1(rs.getString("TYPE9"));

				issueDataList.add(idBean);
				if (tmpId_seq.equals(""))
					tmpId_seq += rs.getString("ID_SEQ");
				else
					tmpId_seq += "," + rs.getString("ID_SEQ");
			}

			String prevI_seq = null;
			if (tmpId_seq.length() > 0) {
				// 이슈에 이슈코드어레이 추가
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
				sb.append("WHERE	IDC.ID_SEQ IN (" + tmpId_seq + ")  						\n");
				sb.append("         AND IC.IC_CODE = IDC.IC_CODE   						\n");
				sb.append("         AND IC.IC_TYPE = IDC.IC_TYPE	 					\n");
				sb.append("ORDER BY IDC.ID_SEQ DESC					 					\n");
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());

				while (rs.next()) {

					if (prevI_seq != null && !prevI_seq.equals(rs.getString("ID_SEQ"))) {
						addIssueCode(issueDataList, prevI_seq, ArrIcList);
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
				addIssueCode(issueDataList, prevI_seq, ArrIcList);

				// 이슈에 이슈코멘트어레이 추가
				rs = null;
				prevI_seq = null;
				sb = new StringBuffer();
				ArrayList arrIcmList = new ArrayList();

				sb.append("SELECT IM_SEQ, IM_COMMENT, DATE_FORMAT(IM_DATE,'%Y-%m-%d %H:%i:%s') IM_DATE, ID_SEQ \n");
				sb.append("FROM ISSUE_COMMENT	 						                           \n");
				sb.append("WHERE ID_SEQ IN (" + tmpId_seq + ")  					                       \n");
				sb.append("ORDER BY IM_SEQ DESC                                 				   \n");
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());

				while (rs.next()) {

					if (prevI_seq != null && !prevI_seq.equals(rs.getString("ID_SEQ"))) {
						addIssueCode(issueDataList, prevI_seq, arrIcmList);
						arrIcmList = new ArrayList();
					}

					prevI_seq = rs.getString("ID_SEQ");

					icmBean = new IssueCommentBean();
					icmBean.setIm_seq(rs.getString("IM_SEQ"));
					icmBean.setIm_comment(rs.getString("IM_COMMENT"));
					icmBean.setIm_date(rs.getString("IM_DATE"));

					arrIcmList.add(icmBean);
				}
				addIssueComment(issueDataList, prevI_seq, arrIcmList);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return issueDataList;
	}

	public ArrayList ReassembleTypeCode(String TypeCode) {
		ArrayList master = new ArrayList();

		if (!TypeCode.equals("")) {
			String[] tempRow = TypeCode.split("@");
			String[] tempCol = null;

			String[] getTBean = null;
			String[] setTBean = null;

			boolean exChk = false;

			for (int i = 0; i < tempRow.length; i++) {
				tempCol = tempRow[i].split(",");

				exChk = false;
				for (int j = 0; j < master.size(); j++) {
					getTBean = (String[]) master.get(j);

					// 마스터에 있으면 입장
					if (tempCol[0].equals(getTBean[0])) {
						// 기존에 CODE를 쉼표단위로 추가하여 업데이트
						setTBean = new String[2];
						setTBean[0] = getTBean[0];
						setTBean[1] = (getTBean[1] + "," + tempCol[1]);

						master.set(j, setTBean);

						exChk = true;
						break;
					}
				}

				// 마스터어레이에 없으면 입장
				if (!exChk) {
					// 새로 추가
					setTBean = new String[2];
					setTBean[0] = tempCol[0];
					if (tempCol.length > 0) {
						setTBean[1] = tempCol[1];
					}
					master.add(setTBean);
				}
			}
		}

		return master;
	}

	public ArrayList getIssueDataList_groupBy_v2(int pageNum, int rowCnt, String sDate, String eDate, String sTime,
			String eTime, String keyType /* 1:제목, 2:제목+내용 */
			, String keyword /* 키워드 */
			, String typeCode /* 분류체계 */
	) {

		ArrayList result = new ArrayList();

		ArrayList arTypeCode = ReassembleTypeCode(typeCode);
		String[] getTBean = null;

		// 리스트 시작, 끝번호
		if (rowCnt > 0)
			this.startNum = (pageNum - 1) * rowCnt;
		this.endNum = rowCnt;

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();

			sb.append("SELECT COUNT(DISTINCT A.ID_SEQ) AS SAME_CNT										\n");
			sb.append("     , COUNT(DISTINCT A.MD_PSEQ) AS CNT									\n");
			sb.append("  FROM ISSUE_DATA A														\n");

			// 세부조건검색
			for (int i = 0; i < arTypeCode.size(); i++) {
				getTBean = (String[]) arTypeCode.get(i);

				sb.append("			INNER JOIN ISSUE_DATA_CODE C" + (i + 1) + " ON A.ID_SEQ = C" + (i + 1)
						+ ".ID_SEQ 										\n");
				sb.append("                                        AND C" + (i + 1) + ".IC_TYPE = " + getTBean[0]
						+ "										\n");
				sb.append("                                        AND C" + (i + 1) + ".IC_CODE IN (" + getTBean[1]
						+ ")									\n");

			}

			/*
			 * //회사코드 조건 주기 if(!mg_company.equals("")){ sb.
			 * append("  INNER JOIN ISSUE_DATA_CODE T ON A.ID_SEQ = T.ID_SEQ AND T.IC_TYPE = 7 AND T.IC_CODE IN ("
			 * +mg_company+")  \n"); }
			 */

			sb.append(" WHERE A.MD_DATE BETWEEN '" + sDate + " " + sTime + "' AND '" + eDate + " " + eTime + "'	\n");

			// 키워드 조건 주기(AND검색)
			if (!keyword.equals("")) {
				String[] arKey = keyword.split(" ");

				for (int i = 0; i < arKey.length; i++) {
					if (!arKey[i].trim().equals("")) {
						if (keyType.equals("1")) {
							// 제목검색
							sb.append("AND ID_TITLE LIKE '%" + arKey[i] + "%' \n");

						} else if (keyType.equals("2")) {
							// 제목+내용검색
							sb.append("AND CONCAT(ID_TITLE,ID_CONTENT) LIKE '%" + arKey[i] + "%'\n");
						}
					}
				}
			}

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			if (rs.next()) {
				totalSameDataCnt = rs.getInt("SAME_CNT");
				totalIssueDataCnt = rs.getInt("CNT");
			}

			rs.close();
			sb = new StringBuffer();

			// sb.append("SELECT A.*, IFNULL(B.V_SEQ,'') AS V_SEQ FROM ( \n");

			sb.append("SELECT A.* 												\n");
			sb.append("     , FN_GET_ISSUE_CODE(A.ID_SEQ,9) AS TYPE9			\n");
			sb.append("     , FN_ISSUE_NAME_ID_SEQ(A.ID_SEQ,10) AS TYPE10		\n");
			sb.append("     , FN_GET_SITE_SAME(A.MD_PSEQ,'" + sDate + " " + sTime + "','" + eDate + " " + eTime
					+ "') AS MD_SAME_CT 			\n");
			sb.append("     , FN_GET_SITE_SAME_CHECK(A.MD_PSEQ,'" + sDate + " " + sTime + "','" + eDate + " " + eTime
					+ "') AS MD_SAME_CT_CHECK	\n");
			// sb.append(" , IFNULL((SELECT V_SEQ FROM CONNECT_VOC WHERE ID_SEQ = A.ID_SEQ
			// LIMIT 1),'') AS V_SEQ \n");
			sb.append("  FROM (													\n");
			sb.append("        SELECT A.*										\n");
			sb.append("          FROM ( 										\n");
			sb.append("                SELECT 									\n");
			sb.append("                       distinct(A.ID_SEQ) AS ID_SEQ		\n");
			sb.append("                     , A.MD_SEQ							\n");
			sb.append("                     , A.MD_PSEQ							\n");
			sb.append("                     , A.MD_TYPE							\n");
			sb.append("                     , A.S_SEQ							\n");
			sb.append("                     , A.SG_SEQ							\n");
			sb.append("                     , A.MD_DATE							\n");
			sb.append("                     , A.MD_SITE_NAME					\n");
			sb.append("                     , A.ID_TITLE						\n");
			sb.append("                     , A.ID_URL							\n");
			sb.append("                     , A.ID_CONTENT						\n");
			sb.append("                  FROM ISSUE_DATA A						\n");

			// 세부조건검색
			for (int i = 0; i < arTypeCode.size(); i++) {
				getTBean = (String[]) arTypeCode.get(i);
				sb.append("			INNER JOIN ISSUE_DATA_CODE C" + (i + 1) + " ON A.ID_SEQ = C" + (i + 1)
						+ ".ID_SEQ 										\n");
				sb.append("                                        AND C" + (i + 1) + ".IC_TYPE = " + getTBean[0]
						+ "										\n");
				sb.append("                                        AND C" + (i + 1) + ".IC_CODE IN (" + getTBean[1]
						+ ")									\n");
			}

			/*
			 * //회사코드 조건 주기 if(!mg_company.equals("")){ sb.
			 * append("  INNER JOIN ISSUE_DATA_CODE T ON A.ID_SEQ = T.ID_SEQ AND T.IC_TYPE = 7 AND T.IC_CODE IN ("
			 * +mg_company+")  \n"); }
			 */
			sb.append("                 WHERE A.MD_DATE BETWEEN '" + sDate + " " + sTime + "' AND '" + eDate + " "
					+ eTime + "'	\n");

			// 키워드 조건 주기(AND검색)
			if (!keyword.equals("")) {
				String[] arKey = keyword.split(" ");

				for (int i = 0; i < arKey.length; i++) {
					if (!arKey[i].trim().equals("")) {
						if (keyType.equals("1")) {
							// 제목검색
							sb.append("AND ID_TITLE LIKE '%" + arKey[i] + "%' \n");

						} else if (keyType.equals("2")) {
							// 제목+내용검색
							sb.append("AND CONCAT(ID_TITLE,ID_CONTENT) LIKE '%" + arKey[i] + "%'\n");
						}
					}
				}
			}

			sb.append("                 ORDER BY MD_SEQ ASC						\n");
			sb.append("               ) A										\n");
			sb.append("         GROUP BY A.MD_PSEQ								\n");
			sb.append("         ORDER BY A.ID_SEQ DESC							\n");
			sb.append("         LIMIT " + startNum + "," + endNum + "					\n");
			sb.append("       )A												\n");

			// sb.append(") A LEFT OUTER JOIN CONNECT_VOC B ON A.ID_SEQ = B.ID_SEQ \n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setTemp1(rs.getString("TYPE9"));
				idBean.setTemp2(rs.getString("TYPE10"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				idBean.setMd_same_ct_check(rs.getString("MD_SAME_CT_CHECK"));
				// idBean.setV_seq(rs.getString("V_SEQ"));
				idBean.setId_content(rs.getString("ID_CONTENT"));
				result.add(idBean);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;

	}

	public ArrayList getIssueDataList_groupBy_v2(int pageNum, int rowCnt, String sDate, String eDate, String sTime,
			String eTime, String keyType /* 1:제목, 2:제목+내용 */
			, String keyword /* 키워드 */
			, String typeCode /* 분류체계 */
			, String reportYN /* 보고서포함유무 */
	) {

		ArrayList result = new ArrayList();

		ArrayList arTypeCode = ReassembleTypeCode(typeCode);
		String[] getTBean = null;

		// 리스트 시작, 끝번호
		if (rowCnt > 0)
			this.startNum = (pageNum - 1) * rowCnt;
		this.endNum = rowCnt;

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();

			sb.append(
					"SELECT COUNT(DISTINCT A.ID_SEQ) AS SAME_CNT													\n");
			sb.append(
					"     , COUNT(DISTINCT A.MD_PSEQ) AS CNT														\n");
			sb.append(
					"  FROM ISSUE_DATA A																			\n");

			// 세부조건검색
			for (int i = 0; i < arTypeCode.size(); i++) {
				getTBean = (String[]) arTypeCode.get(i);

				sb.append("			INNER JOIN ISSUE_DATA_CODE C" + (i + 1) + " ON A.ID_SEQ = C" + (i + 1)
						+ ".ID_SEQ 			\n");
				sb.append("                                        AND C" + (i + 1) + ".IC_TYPE = " + getTBean[0]
						+ "			\n");
				sb.append("                                        AND C" + (i + 1) + ".IC_CODE IN (" + getTBean[1]
						+ ")		\n");
			}
			sb.append(" WHERE A.MD_DATE BETWEEN '" + sDate + " " + sTime + "' AND '" + eDate + " " + eTime
					+ "'						\n");
			if (!reportYN.equals("")) {
				sb.append(" AND A.ID_REPORTYN = '" + reportYN
						+ "'																\n");
			}
			// 키워드 조건 주기(AND검색)
			if (!keyword.equals("")) {
				String[] arKey = keyword.split(" ");

				for (int i = 0; i < arKey.length; i++) {
					if (!arKey[i].trim().equals("")) {
						if (keyType.equals("1")) {
							// 제목검색
							sb.append("AND ID_TITLE LIKE '%" + arKey[i] + "%' \n");

						} else if (keyType.equals("2")) {
							// 제목+내용검색
							sb.append("AND CONCAT(ID_TITLE,ID_CONTENT) LIKE '%" + arKey[i] + "%'\n");
						}
					}
				}
			}

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			if (rs.next()) {
				totalSameDataCnt = rs.getInt("SAME_CNT");
				totalIssueDataCnt = rs.getInt("CNT");
			}

			rs.close();
			sb = new StringBuffer();

			// sb.append("SELECT A.*, IFNULL(B.V_SEQ,'') AS V_SEQ FROM ( \n");

			sb.append(
					"SELECT A.* 																								\n");
			sb.append(
					"     , FN_GET_ISSUE_CODE(A.ID_SEQ,9) AS TYPE9															\n");
			sb.append(
					"     , FN_ISSUE_NAME_ID_SEQ(A.ID_SEQ,10) AS TYPE10														\n");
			sb.append("     , FN_GET_SITE_SAME(A.MD_PSEQ,'" + sDate + " " + sTime + "','" + eDate + " " + eTime
					+ "') AS MD_SAME_CT 			\n");
			sb.append("     , FN_GET_SITE_SAME_CHECK(A.MD_PSEQ,'" + sDate + " " + sTime + "','" + eDate + " " + eTime
					+ "') AS MD_SAME_CT_CHECK	\n");
			// sb.append(" , IFNULL((SELECT V_SEQ FROM CONNECT_VOC WHERE ID_SEQ = A.ID_SEQ
			// LIMIT 1),'') AS V_SEQ \n");
			sb.append(
					"  FROM (																									\n");
			sb.append(
					"        SELECT A.*																						\n");
			sb.append("          FROM ( 										\n");
			sb.append("                SELECT 									\n");
			sb.append("                       distinct(A.ID_SEQ) AS ID_SEQ		\n");
			sb.append("                     , A.MD_SEQ							\n");
			sb.append("                     , A.MD_PSEQ							\n");
			sb.append("                     , A.MD_TYPE							\n");
			sb.append("                     , A.S_SEQ							\n");
			sb.append("                     , A.SG_SEQ							\n");
			sb.append("                     , A.MD_DATE							\n");
			sb.append("                     , A.MD_SITE_NAME					\n");
			sb.append("                     , A.ID_TITLE						\n");
			sb.append("                     , A.ID_URL							\n");
			sb.append("                     , A.ID_CONTENT						\n");
			sb.append("                  FROM ISSUE_DATA A						\n");

			// 세부조건검색
			for (int i = 0; i < arTypeCode.size(); i++) {
				getTBean = (String[]) arTypeCode.get(i);
				sb.append("			INNER JOIN ISSUE_DATA_CODE C" + (i + 1) + " ON A.ID_SEQ = C" + (i + 1)
						+ ".ID_SEQ 										\n");
				sb.append("                                        AND C" + (i + 1) + ".IC_TYPE = " + getTBean[0]
						+ "										\n");
				sb.append("                                        AND C" + (i + 1) + ".IC_CODE IN (" + getTBean[1]
						+ ")									\n");
			}

			sb.append("                 WHERE A.MD_DATE BETWEEN '" + sDate + " " + sTime + "' AND '" + eDate + " "
					+ eTime + "'											\n");
			if (!reportYN.equals("")) {
				sb.append(" AND A.ID_REPORTYN = '" + reportYN
						+ "'																								\n");
			}
			// 키워드 조건 주기(AND검색)
			if (!keyword.equals("")) {
				String[] arKey = keyword.split(" ");

				for (int i = 0; i < arKey.length; i++) {
					if (!arKey[i].trim().equals("")) {
						if (keyType.equals("1")) {
							// 제목검색
							sb.append("AND ID_TITLE LIKE '%" + arKey[i] + "%' \n");

						} else if (keyType.equals("2")) {
							// 제목+내용검색
							sb.append("AND CONCAT(ID_TITLE,ID_CONTENT) LIKE '%" + arKey[i] + "%'	\n");
						}
					}
				}
			}

			sb.append("                 ORDER BY MD_SEQ ASC						\n");
			sb.append("               ) A										\n");
			sb.append("         GROUP BY A.MD_PSEQ								\n");
			sb.append("         ORDER BY A.ID_SEQ DESC							\n");
			sb.append("         LIMIT " + startNum + "," + endNum + "					\n");
			sb.append("       )A												\n");

			// sb.append(") A LEFT OUTER JOIN CONNECT_VOC B ON A.ID_SEQ = B.ID_SEQ \n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setTemp1(rs.getString("TYPE9"));
				idBean.setTemp2(rs.getString("TYPE10"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				idBean.setMd_same_ct_check(rs.getString("MD_SAME_CT_CHECK"));
				// idBean.setV_seq(rs.getString("V_SEQ"));
				idBean.setId_content(rs.getString("ID_CONTENT"));
				result.add(idBean);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;

	}

	public ArrayList getSourceData(String md_pseq, String ic_seq, String sdate, String stime, String edate,
			String etime) {

		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();

			sb.append("SELECT A.* FROM (						\n");
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
			sb.append("  FROM ISSUE_DATA A													\n");
			sb.append("     , ISSUE_DATA_CODE B												\n");
			sb.append(" WHERE A.MD_PSEQ = " + md_pseq + "										\n");
			sb.append("   AND A.MD_DATE BETWEEN '" + sdate + " " + stime + "' AND '" + edate + " " + etime
					+ "'		\n");
			sb.append("   AND A.ID_SEQ = B.ID_SEQ											\n");
			// sb.append(" AND B.IC_TYPE = 6 \n");
			// sb.append(" AND B.IC_CODE = "+ic_seq+" \n");
			sb.append(")A																	\n");
			// sb.append(") A LEFT OUTER JOIN CONNECT_VOC B ON A.ID_SEQ = B.ID_SEQ \n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
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
				// idBean.setV_seq(rs.getString("V_SEQ"));

				arrResult.add(idBean);
			}
			sb = null;
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	public ArrayList getSourceData(String md_pseq, String sdate, String stime, String edate, String etime) {

		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			stmt = dbconn.createStatement();
			
			sb.append("SELECT A.*																				\n");
			sb.append("		, IF(''=A.M_SEQ ,'',(SELECT M_NAME FROM MEMBER WHERE M_SEQ = A.M_SEQ)) AS M_NAME  	\n");
			sb.append("	 FROM (																					\n");
			sb.append("				SELECT A.ID_SEQ																\n");
			sb.append("     				, A.MD_SEQ														\n");
			sb.append("     				, A.I_SEQ														\n");
			sb.append("     				, A.IT_SEQ														\n");
			sb.append("     				, A.ID_URL														\n");
			sb.append("     				, A.ID_TITLE													\n");
			sb.append("     				, A.SG_SEQ														\n");
			sb.append("     				, A.S_SEQ														\n");
			sb.append("     				, A.MD_SITE_NAME												\n");
			sb.append("     				, A.MD_SITE_MENU												\n");
			sb.append("     				, DATE_FORMAT(A.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE			\n");
			sb.append("     				, DATE_FORMAT(A.ID_REGDATE,'%Y-%m-%d %H:%i:%s') AS ID_REGDATE	\n");
			sb.append("     				, A.ID_WRITTER													\n");
			sb.append("     				, A.ID_CONTENT													\n");
			sb.append("     				, A.ID_MAILYN													\n");
			sb.append("     				, A.ID_USEYN													\n");
			sb.append("     				, A.M_SEQ														\n");
			sb.append("     				, A.MD_TYPE														\n");
			sb.append("     				, A.L_ALPHA														\n");
			sb.append("     				, A.ID_REPORTYN													\n");
			sb.append("     				, A.MD_PSEQ														\n");
			sb.append("     				, B.SG_NAME														\n");
			sb.append("     				, A.ID_SENTI												\n");
			sb.append("  				FROM ISSUE_DATA A													\n");
			sb.append("     				, SITE_GROUP B														\n");
			sb.append(
					" 					WHERE A.MD_PSEQ = " + md_pseq + "										\n");
			sb.append("   				AND A.MD_DATE BETWEEN '" + sdate + " " + stime + "' AND '" + edate + " " + etime
					+ "'	\n");
			sb.append("   				AND A.SG_SEQ = B.SG_SEQ													\n");
			sb.append("		)A																					\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
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
				idBean.setSg_name(rs.getString("SG_NAME"));
				idBean.setL_alpha(rs.getString("L_ALPHA"));
				idBean.setId_reportyn(rs.getString("ID_REPORTYN"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setM_name(rs.getString("M_NAME"));
				idBean.setId_senti(rs.getString("ID_SENTI"));
				
				// idBean.setV_seq(rs.getString("V_SEQ"));

				arrResult.add(idBean);
			}
			sb = null;
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	public ArrayList getDailyIssueWeather(String sDate, String sTime, String eDate, String eTime, String siteGroup,
			String type1) {

		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append(
					"SELECT A1.IC_CODE, A1.IC_NAME, IFNULL(A2.CT1,0) AS CT1, IFNULL(A2.CT2,0) AS CT2, IFNULL(A2.CT3,0) AS CT3, IFNULL(CT1+CT2,0) AS TOTAL, IFNULL(CASE CT1 WHEN 0 THEN '0.00' ELSE FORMAT(CT1/(CT1+CT2)*100,'####.##') END,0) AS CT1_PER	\n");
			sb.append(
					"FROM (	                                                                                                                                                                                                                                                                               	\n");
			sb.append(
					"		SELECT *                                                                                                                                                                                                                                                                  	\n");
			sb.append(
					"		FROM ISSUE_CODE IC3                                                                                                                                                                                                                                        	\n");
			sb.append(
					"		WHERE IC3.IC_TYPE = 4 AND IC3.IC_CODE != 0                                                                                                                                                                                                               	\n");
			sb.append(
					"	 )A1 LEFT OUTER JOIN                                                                                                                                                                                                                                                        	\n");
			sb.append(
					"	 (                                                                                                                                                                                                                                                                                     	\n");
			sb.append(
					"	  SELECT IFNULL(SUM(CASE IC.IC_CODE WHEN 1 THEN 1 END), 0) AS CT1, IFNULL(SUM(CASE IC.IC_CODE WHEN 2 THEN 1 END), 0) AS CT2,                        		       	\n");
			sb.append(
					"		    IFNULL(SUM(CASE IC.IC_CODE WHEN 3 THEN 1 END), 0) AS CT3, IDC2.IC_CODE                                                                                                                  		       	\n");
			sb.append(
					"			FROM ISSUE_DATA ID, SG_S_RELATION SD, ISSUE_DATA_CODE IDC1, ISSUE_DATA_CODE IDC2, ISSUE_CODE IC 	                               	\n");
			sb.append("			WHERE ID_REGDATE BETWEEN '" + sDate + " " + sTime + "' AND '" + eDate + " " + eTime
					+ "'  \n");
			sb.append(
					"			AND ID.S_SEQ = SD.S_SEQ                                                                                                                                                                        		                        		\n");
			sb.append(
					"			AND ID.ID_SEQ = IDC1.ID_SEQ                                                                                                                                                                               		                                		\n");
			sb.append(
					"			AND ID.ID_SEQ = IDC2.ID_SEQ                                                                                                                                                                                                                       	       	\n");
			sb.append(
					"			AND IDC1.IC_TYPE = IC.IC_TYPE AND IDC1.IC_CODE = IC.IC_CODE                                                                                                                                                                      	       	\n");
			sb.append("			AND IDC1.IC_TYPE = 1 AND IDC1.IC_CODE IN (" + type1
					+ ")                                                                                                                                                                                      	       	\n");
			sb.append(
					"			AND IDC2.IC_TYPE = 4                                                                                                                                                                                                                                 	       	\n");
			// sb.append(" AND IDC3.IC_TYPE = 5 \n");
			sb.append("			AND SD.SG_SEQ IN (" + siteGroup
					+ ")                                                                                                                                                                                                                   	       	\n");
			sb.append(
					"			GROUP BY IDC2.IC_CODE                                                                                                                                                                                                                                   	\n");
			sb.append(
					"	 )A2 ON (A1.IC_CODE = A2.IC_CODE)                                                                                                                                                                                                                                     	\n");
			sb.append(
					"ORDER BY A1.IC_CODE	                                                                                                                                                                                                                                                       	\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String[] result = new String[5];
				result[0] = rs.getString("CT1");
				result[1] = rs.getString("CT2");
				result[2] = rs.getString("IC_NAME");
				result[3] = rs.getString("CT1_PER");
				result[4] = rs.getString("TOTAL");
				arrResult.add(result);
			}
			sb = null;
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	/**
	 * 해당이슈데이터의 코드 추가
	 * 
	 * @param IssueDataBean
	 * @return
	 */
	private ArrayList addIssueCode(ArrayList IssueList, String id_seq, ArrayList issueCodeList) {
		for (int i = 0; i < IssueList.size(); i++) {
			IssueDataBean idBean = (IssueDataBean) IssueList.get(i);
			if (idBean.getId_seq().equals(id_seq)) {
				idBean.setArrCodeList(issueCodeList);
				IssueList.set(i, idBean);
			}
		}
		return IssueList;
	}

	/**
	 * 해당이슈데이터의 Comment 추가
	 * 
	 * @param IssueDataBean
	 * @return
	 */
	private ArrayList addIssueComment(ArrayList IssueList, String id_seq, ArrayList issueCommentList) {
		int i = 0;
		for (i = 0; i < IssueList.size(); i++) {
			IssueDataBean idBean = (IssueDataBean) IssueList.get(i);
			if (idBean.getId_seq().equals(id_seq)) {
				idBean.setArrCommentList(issueCommentList);
				IssueList.set(i, idBean);
			}
		}
		return IssueList;
	}

	/*
	 * //이슈 불러오기 public ArrayList getActiveIssueTw(String sDate, String eDate,
	 * String gSn){ ArrayList issueList = null; try { dbconn = new DBconn();
	 * dbconn.getDBCPConnection();
	 * 
	 * DateUtil du = new DateUtil();
	 * 
	 * sb = new StringBuffer();
	 * 
	 * sb.
	 * append("SELECT I.I_SEQ                                                           \n"
	 * ); sb.
	 * append("     , I.I_TITLE	                                                        \n"
	 * ); sb.
	 * append("  FROM ISSUE I                                                           \n"
	 * ); sb.
	 * append("     , ISSUE_DATA ID                                                     \n"
	 * ); sb.
	 * append("	    , ISSUE_TITLE IT	                                                \n"
	 * ); sb.
	 * append(" WHERE I.I_USEYN = 'Y'		                                            \n"
	 * ); sb.
	 * append("   AND IT.IT_USEYN = 'Y'			                                        \n"
	 * ); sb.append("   AND ID.S_SEQ = '"
	 * +gSn+"'                                              \n");
	 * sb.append("   AND ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"
	 * +eDate+" 23:59:59'  \n"); sb.
	 * append("   AND ID.IT_SEQ = IT.IT_SEQ								                \n"
	 * );
	 * sb.append("   AND IT.I_SEQ = I.I_SEQ									            \n"
	 * ); sb.
	 * append(" GROUP BY I.I_SEQ, I.I_TITLE												\n"
	 * );
	 * 
	 * System.out.println(sb.toString()); pstmt =
	 * dbconn.createPStatement(sb.toString()); rs = pstmt.executeQuery(); issueList
	 * = new ArrayList();
	 * 
	 * while(rs.next()){
	 * 
	 * isBean = new IssueBean(); isBean.setI_seq(rs.getString("I_SEQ"));
	 * isBean.setI_title(rs.getString("I_TITLE")); isBean.setI_regdate("");
	 * isBean.setM_seq(""); isBean.setI_count("");
	 * 
	 * issueList.add(isBean); }
	 * 
	 * if(issueList==null || issueList.size()==0){ sb = new StringBuffer();
	 * 
	 * sb.append("SELECT I.I_SEQ               \n");
	 * sb.append("     , I.I_TITLE             \n");
	 * sb.append("		, I.I_REGDATE           \n");
	 * sb.append("		, I.M_SEQ               \n");
	 * sb.append("		, IT.IT_SEQ             \n");
	 * sb.append("		, IT.IT_TITLE	        \n");
	 * sb.append("  FROM ISSUE       I         \n");
	 * sb.append("	    , ISSUE_TITLE IT	    \n");
	 * sb.append("	WHERE I.I_SEQ = IT.I_SEQ	\n");
	 * sb.append("	ORDER BY IT.IT_SEQ DESC     \n");
	 * sb.append("	LIMIT 0,1                   \n");
	 * 
	 * System.out.println(sb.toString()); pstmt =
	 * dbconn.createPStatement(sb.toString()); rs = pstmt.executeQuery(); issueList
	 * = new ArrayList(); while(rs.next()){
	 * 
	 * isBean = new IssueBean(); isBean.setI_seq(rs.getString("I_SEQ"));
	 * isBean.setI_title(rs.getString("I_TITLE"));
	 * isBean.setI_regdate(rs.getString("I_REGDATE"));
	 * isBean.setM_seq(rs.getString("M_SEQ")); isBean.setI_count("");
	 * 
	 * issueList.add(isBean); }
	 * 
	 * }
	 * 
	 * } catch(SQLException ex) { Log.writeExpt(ex, sb.toString() ); }
	 * catch(Exception ex) { Log.writeExpt(ex); } finally { if (rs != null) try {
	 * rs.close(); } catch(SQLException ex) {} if (pstmt != null) try {
	 * pstmt.close(); } catch(SQLException ex) {} if (stmt != null) try {
	 * stmt.close(); } catch(SQLException ex) {} if (dbconn != null) try {
	 * dbconn.close(); } catch(SQLException ex) {} } return issueList; }
	 * 
	 * //주제 불러오기 public ArrayList getActiveTitleTw(String sDate, String eDate,
	 * String gSn){ ArrayList issueList = null; try { dbconn = new DBconn();
	 * dbconn.getDBCPConnection();
	 * 
	 * DateUtil du = new DateUtil();
	 * 
	 * sb = new StringBuffer();
	 * 
	 * sb.
	 * append("SELECT IT.I_SEQ                                                          \n"
	 * ); sb.
	 * append("	    , IT.IT_TITLE                                                       \n"
	 * ); sb.
	 * append("		, IT.IT_SEQ	                                                        \n"
	 * ); sb.
	 * append("  FROM ISSUE       I                                                     \n"
	 * ); sb.
	 * append("	    , ISSUE_DATA  ID                                                    \n"
	 * ); sb.
	 * append("	    , ISSUE_TITLE IT	                                                \n"
	 * ); sb.
	 * append(" WHERE I.I_USEYN = 'Y'			                                        \n"
	 * ); sb.
	 * append("	  AND IT.IT_USEYN = 'Y'			                                        \n"
	 * ); sb.append("   AND ID.S_SEQ = '"
	 * +gSn+"'	                                    	    \n");
	 * sb.append("   AND ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"
	 * +eDate+" 23:59:59'  \n"); sb.
	 * append("   AND ID.IT_SEQ = IT.IT_SEQ							                    \n"
	 * );
	 * sb.append("   AND IT.I_SEQ = I.I_SEQ								                \n"
	 * ); sb.
	 * append(" GROUP BY IT.I_SEQ, IT.IT_TITLE, IT.IT_SEQ                               \n"
	 * );
	 * 
	 * System.out.println(sb.toString()); pstmt =
	 * dbconn.createPStatement(sb.toString()); rs = pstmt.executeQuery();
	 * 
	 * issueList = new ArrayList(); IssueTitleBean itBean = null; while(rs.next()){
	 * itBean = new IssueTitleBean(); itBean.setIt_seq(rs.getString("IT_SEQ"));
	 * itBean.setIt_title(rs.getString("IT_TITLE"));
	 * itBean.setI_seq(rs.getString("I_SEQ"));
	 * 
	 * issueList.add(itBean); }
	 * 
	 * 
	 * } catch(SQLException ex) { Log.writeExpt(ex, sb.toString() ); }
	 * catch(Exception ex) { Log.writeExpt(ex); } finally { if (rs != null) try {
	 * rs.close(); } catch(SQLException ex) {} if (pstmt != null) try {
	 * pstmt.close(); } catch(SQLException ex) {} if (stmt != null) try {
	 * stmt.close(); } catch(SQLException ex) {} if (dbconn != null) try {
	 * dbconn.close(); } catch(SQLException ex) {} } return issueList; }
	 */

	public void reportNameUpdate(String ir_seq, String ir_title) {
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();

			sb.append("UPDATE ISSUE_REPORT SET IR_TITLE = '" + ir_title + "' WHERE IR_SEQ = " + ir_seq + "\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
	}

	public ArrayList getIssueTitle(ArrayList pArray, String iSeq) {
		ArrayList result = new ArrayList();
		IssueTitleBean itb = null;
		for (int i = 0; i < pArray.size(); i++) {
			itb = (IssueTitleBean) pArray.get(i);
			if (itb.getI_seq().equals(iSeq)) {
				result.add(itb);
			}

		}
		return result;
	}

	// 사이트 그룹 정렬해서 가져오기
	public ArrayList getSiteGroup_order() {
		ArrayList issueList = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();

			sb.append("SELECT A.IC_CODE				\n");
			sb.append("     , A.IC_NAME				\n");
			sb.append("  FROM ISSUE_CODE A 			\n");
			sb.append(" WHERE A.IC_TYPE = 6 		\n");
			sb.append("   AND A.IC_CODE > 0 		\n");
			sb.append("   AND A.IC_USEYN = 'Y'		\n");
			sb.append(" ORDER BY A.IC_ORDER			\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			issueList = new ArrayList();
			String[] siteGroup = null;
			while (rs.next()) {

				siteGroup = new String[2];

				siteGroup[0] = rs.getString("IC_CODE");
				siteGroup[1] = rs.getString("IC_NAME");

				issueList.add(siteGroup);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return issueList;
	}

	// 사이트 그룹 정렬해서 가져오기
	public ArrayList getSiteGroup_order2() {
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
			// sb.append(" AND IC_CODE <> 8 \n");
			sb.append(" ORDER BY B.IC_ORDER			\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			issueList = new ArrayList();
			String[] siteGroup = null;
			while (rs.next()) {

				siteGroup = new String[3];

				siteGroup[0] = rs.getString("IC_CODE");
				siteGroup[1] = rs.getString("IC_NAME");
				siteGroup[2] = rs.getString("IC_ORDER");

				issueList.add(siteGroup);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return issueList;
	}

	// 사이트 그룹 정렬해서 가져오기
	public ArrayList getSiteGroup_order3() {
		ArrayList issueList = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();

			sb.append("SELECT SG_SEQ 			\n");
			sb.append("     , SG_NAME				\n");
			sb.append("     , SG_ORDER				\n");
			sb.append("  FROM SITE_GROUP  			\n");
			sb.append(" ORDER BY SG_ORDER		\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			issueList = new ArrayList();
			String[] siteGroup = null;
			while (rs.next()) {

				siteGroup = new String[3];

				siteGroup[0] = rs.getString("SG_SEQ");
				siteGroup[1] = rs.getString("SG_NAME");
				siteGroup[2] = rs.getString("SG_ORDER");

				issueList.add(siteGroup);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return issueList;
	}
	
	// 사이트 그룹 정렬해서 가져오기
	public ArrayList getSiteGroup_order4() {
		ArrayList issueList = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();

			sb.append("SELECT SG_SEQ 			\n");
			sb.append("     , SG_NAME				\n");
			sb.append("     , SG_ORDER				\n");
			sb.append("  FROM SITE_GROUP  			\n");
			sb.append(" WHERE USEYN = 'Y'		\n");
			sb.append(" ORDER BY SG_ORDER		\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			issueList = new ArrayList();
			String[] siteGroup = null;
			while (rs.next()) {

				siteGroup = new String[3];

				siteGroup[0] = rs.getString("SG_SEQ");
				siteGroup[1] = rs.getString("SG_NAME");
				siteGroup[2] = rs.getString("SG_ORDER");

				issueList.add(siteGroup);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return issueList;
	}
	

	public int GetSearchSourceOrder(ArrayList data, String icCode) {
		int result = 0;
		String[] row_data = null;
		for (int i = 0; i < data.size(); i++) {
			row_data = (String[]) data.get(i);
			if (row_data[0].equals(icCode)) {
				result = Integer.parseInt(row_data[2]);
				break;
			}
		}
		return result;
	}

	public ArrayList TrendPieChartData(String ic_type, String ir_sdate, String ir_stime, String ir_edate,
			String ir_etime) {
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();

			sb.append("SELECT IC.IC_TYPE, IC.IC_CODE, IC.IC_NAME AS CATEGORY, IFNULL(T.CNT, 0) AS CNT\n");
			sb.append("FROM\n");
			sb.append("(SELECT * FROM ISSUE_CODE WHERE IC_TYPE = " + ic_type
					+ " AND IC_USEYN = 'Y' AND IC_CODE != 0) IC\n");
			// sb.append("LEFT OUTER JOIN\n");
			sb.append("LEFT OUTER JOIN\n");
			sb.append("(\n");
			sb.append("		SELECT IDC.IC_CODE, COUNT(*) AS CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON IDC.IC_TYPE = " + ic_type + "\n");
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC2\n");
			sb.append("		ON IDC2.IC_TYPE = 4 AND IDC2.IC_CODE IN (1,2)\n");
			sb.append("		AND ID.ID_SEQ = IDC2.ID_SEQ\n");
			sb.append("		WHERE ID.MD_DATE BETWEEN '" + ir_sdate + " " + ir_stime + "' AND '" + ir_edate + " "
					+ ir_etime + "'\n");
			sb.append("		GROUP BY IDC.IC_CODE\n");
			sb.append(") T\n");
			sb.append("ON IC.IC_CODE = T.IC_CODE\n");
			sb.append("ORDER BY IC.IC_TYPE, IC.IC_CODE\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dataMap = new HashMap();
				dataMap.put("CATEGORY", rs.getString("CATEGORY"));
				dataMap.put("CNT", rs.getString("CNT"));
				result.add(dataMap);
			}
		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

	public ArrayList TrendLineBarChartData(String ic_code, String ir_edate) {

		ArrayList result = new ArrayList();
		boolean bDataCheck = false;

		DateUtil du = new DateUtil();
		du.setDate(ir_edate.replaceAll("-", ""));

		String eDate = du.getDate("yyyy-MM-dd");
		du.addDay(-13);
		String sDate = du.getDate("yyyy-MM-dd");

		String[][] dataSet = new String[14][3];
		String[] tempSet = new String[3];

		du.setDate(sDate.replaceAll("-", ""));
		for (int i = 0; i < dataSet.length; i++) {
			dataSet[i][0] = du.getDate("yyyy-MM-dd");
			dataSet[i][1] = "0";
			dataSet[i][2] = "0";

			du.addDay(1);
		}

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();

			sb.append(
					"	SELECT A.MD_DATE, IFNULL(A.CNT_ALL, 0) AS CNT_ALL, IFNULL(B.CNT_NAG,0) AS CNT_NAG			\n");
			sb.append(
					"	FROM (																						\n");
			sb.append(
					"	    SELECT DATE_FORMAT(ID.MD_DATE, '%Y-%m-%d') AS MD_DATE, COUNT(*) AS CNT_ALL				\n");
			sb.append(
					"	    FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC													\n");
			sb.append(
					"	    WHERE ID.ID_SEQ = IDC.ID_SEQ															\n");
			sb.append("	      AND ID.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate
					+ " 23:59:59'					\n");
			sb.append(
					"		  AND IDC.IC_TYPE = 16																	\n");
			sb.append("	      AND IDC.IC_CODE = " + ic_code
					+ "															\n");
			sb.append(
					"	    GROUP BY DATE_FORMAT(ID.MD_DATE, '%Y-%m-%d')											\n");
			sb.append(
					"	)A LEFT OUTER JOIN (																		\n");
			sb.append(
					"	    SELECT DATE_FORMAT(ID.MD_DATE, '%Y-%m-%d') AS MD_DATE, COUNT(*) AS CNT_NAG				\n");
			sb.append(
					"	    FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
			sb.append(
					"	    WHERE ID.ID_SEQ = IDC.ID_SEQ															\n");
			sb.append(
					"	      AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
			sb.append("	      AND ID.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate
					+ " 23:59:59'					\n");
			sb.append(
					"	     AND IDC.IC_TYPE = 16																	\n");
			sb.append("	      AND IDC.IC_CODE = " + ic_code
					+ "															\n");
			sb.append(
					"	      AND IDC2.IC_TYPE = 9																	\n");
			sb.append(
					"	      AND IDC2.IC_CODE = 2																	\n");
			sb.append(
					"	    GROUP BY DATE_FORMAT(ID.MD_DATE, '%Y-%m-%d')											\n");
			sb.append(
					"	)B ON A.MD_DATE = B.MD_DATE																	\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			int i = 0;
			while (rs.next()) {
				while (!dataSet[i][0].equals(rs.getString("MD_DATE"))) {
					tempSet = dataSet[i];
					result.add(tempSet);
					i++;
				}

				if (dataSet[i][0].equals(rs.getString("MD_DATE"))) {
					dataSet[i][1] = rs.getString("CNT_ALL");
					dataSet[i][2] = rs.getString("CNT_NAG");
					tempSet = dataSet[i];
					result.add(tempSet);
					i++;
				}

				if (rs.getInt("CNT_ALL") != 0 || rs.getInt("CNT_NAG") != 0) {
					bDataCheck = true;
				}
			}

			while (i < dataSet.length) {
				tempSet = dataSet[i];
				result.add(tempSet);
				i++;
			}
		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		if (!bDataCheck) {
			result.clear();
		}

		return result;
	}

	public ArrayList TrendPieChartData(String ic_code, String ir_edate) {

		ArrayList result = new ArrayList();

		DateUtil du = new DateUtil();
		du.setDate(ir_edate.replaceAll("-", ""));

		String eDate = du.getDate("yyyy-MM-dd");
		du.addDay(-13);
		String sDate = du.getDate("yyyy-MM-dd");

		String tmp[] = null;

		boolean bDataCheck = false;

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();

			sb.append(
					"	SELECT AB.IC_NAME, AB.CNT AS CNT_ALL, IFNULL(C.CNT, 0) AS CNT_NAG FROM (					\n");
			sb.append("	  SELECT A.IC_CODE, A.IC_NAME, IFNULL(B.CNT, 0) AS CNT  FROM (							\n");
			sb.append("	    SELECT IC.IC_CODE, IC.IC_NAME														\n");
			sb.append("	    FROM IC_S_RELATION ICSR, SITE_GROUP SG, ISSUE_CODE IC								\n");
			sb.append("	    WHERE ICSR.S_SEQ = SG.SG_SEQ														\n");
			sb.append("	      AND ICSR.IC_SEQ = IC.IC_SEQ														\n");
			sb.append("	  ) A LEFT OUTER JOIN (																	\n");
			sb.append("	    SELECT IDC2.IC_CODE, COUNT(IDC2.IC_CODE) AS CNT										\n");
			sb.append("	    FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2						\n");
			sb.append("	    WHERE ID.ID_SEQ = IDC.ID_SEQ														\n");
			sb.append("	      AND ID.ID_SEQ = IDC2.ID_SEQ														\n");
			sb.append("	      AND ID.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate
					+ " 23:59:59'				\n");
			sb.append("	      AND IDC.IC_TYPE = 16																\n");
			sb.append("	      AND IDC.IC_CODE = " + ic_code
					+ "														\n");
			sb.append("	      AND IDC2.IC_TYPE = 6																\n");
			sb.append("	    GROUP BY IDC2.IC_CODE  																\n");
			sb.append("	  ) B ON A.IC_CODE = B.IC_CODE															\n");
			sb.append("	) AB LEFT OUTER JOIN (  																\n");
			sb.append("	    SELECT IDC2.IC_CODE, COUNT(IDC2.IC_CODE) AS CNT										\n");
			sb.append("	    FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3	\n");
			sb.append("	    WHERE ID.ID_SEQ = IDC.ID_SEQ														\n");
			sb.append("	      AND ID.ID_SEQ = IDC2.ID_SEQ														\n");
			sb.append("	      AND ID.ID_SEQ = IDC3.ID_SEQ														\n");
			sb.append("	      AND ID.MD_DATE BETWEEN '" + sDate + " 00:00:00' AND '" + eDate
					+ " 23:59:59'				\n");
			sb.append("	      AND IDC.IC_TYPE = 16																\n");
			sb.append("	      AND IDC.IC_CODE = " + ic_code
					+ "														\n");
			sb.append("	      AND IDC2.IC_TYPE = 6																\n");
			sb.append("	      AND IDC3.IC_TYPE = 9																\n");
			sb.append("	      AND IDC3.IC_CODE = 2																\n");
			sb.append("	    GROUP BY IDC2.IC_CODE  																\n");
			sb.append("	) C ON AB.IC_CODE = C.IC_CODE 															\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tmp = new String[3];
				tmp[0] = rs.getString("IC_NAME");
				tmp[1] = rs.getString("CNT_ALL");
				tmp[2] = rs.getString("CNT_NAG");
				result.add(tmp);

				if (rs.getInt("CNT_ALL") != 0 || rs.getInt("CNT_NAG") != 0) {
					bDataCheck = true;
				}
			}
		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		if (!bDataCheck) {
			result.clear();
		}

		return result;
	}

	public ArrayList getIssueInfoList(String issueSeq) {

		ArrayList result = new ArrayList();
		IssueDataBean idb = null;

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();

			sb.append(
					"	SELECT ID.ID_SEQ, SG.SG_NAME, ID.ID_TITLE, ID.ID_URL, M.MD_SITE_NAME, ID.MD_DATE, ID.MD_SAME_CT	\n");
			sb.append(
					"	FROM ISSUE_DATA ID, SITE_GROUP SG, META M 														\n");
			sb.append("	WHERE ID.ID_SEQ IN (" + issueSeq
					+ ")																\n");
			sb.append(
					"	  AND ID.SG_SEQ = SG.SG_SEQ																		\n");
			sb.append(
					"	  AND ID.MD_PSEQ = M.MD_SEQ																		\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				idb = new IssueDataBean();
				idb.setId_seq(rs.getString("ID_SEQ"));
				idb.setSg_name(rs.getString("SG_NAME"));
				idb.setId_title(rs.getString("ID_TITLE"));
				idb.setId_url(rs.getString("ID_URL"));
				idb.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idb.setMd_date(rs.getString("MD_DATE"));
				idb.setMd_same_ct(rs.getString("MD_SAME_CT"));

				result.add(idb);
			}
		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return result;
	}

	public ArrayList majorIssueDate(String sDate_pre, String eDate_pre, String ir_sdate, String ir_stime,
			String ir_edate, String ir_etime) {
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("	## 주요이슈	\n");
			/*
			 * sb.
			 * append("	SELECT																			\n"
			 * ); sb.
			 * append("	    (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 118 AND IC_CODE = AAA.IC_CODE LIMIT 1) AS IC_NAME   \n"
			 * ); sb.
			 * append("	    ,ifnull(AAA.CNT1 ,0) AS CNT1                                                \n"
			 * ); sb.
			 * append("	    ,ifnull(BBB.CNT2 ,0) AS CNT2                                                \n"
			 * ); sb.
			 * append("	FROM                                                                            \n"
			 * ); sb.
			 * append("	 (                                                                              \n"
			 * ); sb.
			 * append("	 SELECT                                                                         \n"
			 * ); sb.
			 * append("	        B.IC_CODE                                                               \n"
			 * ); sb.
			 * append("	        ,COUNT(*) AS CNT1                                                       \n"
			 * ); sb.
			 * append("	   FROM ISSUE_DATA A                                                            \n"
			 * ); sb.
			 * append("	      , ISSUE_DATA_CODE B                                                       \n"
			 * ); sb.
			 * append("	      , ISSUE_DATA_CODE C                                                       \n"
			 * ); sb.append("	  WHERE A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"
			 * +ir_edate+" "+ir_etime+"' \n"); sb.
			 * append("	    AND A.ID_SEQ = B.ID_SEQ                                                     \n"
			 * ); sb.
			 * append("	    AND A.ID_SEQ = C.ID_SEQ                                                     \n"
			 * ); sb.
			 * append("	    AND B.IC_TYPE = 118                                                         \n"
			 * ); sb.
			 * append("	    AND C.IC_TYPE = 18 AND C.IC_CODE = 1                                        \n"
			 * ); sb.
			 * append("	    GROUP BY B.IC_CODE                                                          \n"
			 * ); sb.
			 * append("	    ) AAA,                                                                      \n"
			 * ); sb.
			 * append("	     (                                                                          \n"
			 * ); sb.
			 * append("	 SELECT                                                                         \n"
			 * ); sb.
			 * append("	        B.IC_CODE                                                               \n"
			 * ); sb.
			 * append("	        ,COUNT(*) AS CNT2                                                       \n"
			 * ); sb.
			 * append("	   FROM ISSUE_DATA A                                                            \n"
			 * ); sb.
			 * append("	      , ISSUE_DATA_CODE B                                                       \n"
			 * ); sb.
			 * append("	      , ISSUE_DATA_CODE C                                                       \n"
			 * );
			 * sb.append("	  WHERE A.MD_DATE BETWEEN '"+sDate_pre+" "+ir_stime+"' AND '"
			 * +eDate_pre+" "+ir_etime+"' \n"); sb.
			 * append("	    AND A.ID_SEQ = B.ID_SEQ                                                     \n"
			 * ); sb.
			 * append("	    AND A.ID_SEQ = C.ID_SEQ                                                     \n"
			 * ); sb.
			 * append("	    AND B.IC_TYPE = 118                                                         \n"
			 * ); sb.
			 * append("	    AND C.IC_TYPE = 18 AND C.IC_CODE = 1                                        \n"
			 * ); sb.
			 * append("	    GROUP BY B.IC_CODE                                                          \n"
			 * ); sb.
			 * append("	    ) BBB                                                                       \n"
			 * ); sb.
			 * append("	    WHERE AAA.IC_CODE = BBB.IC_CODE                                             \n"
			 * );
			 */
			sb.append(
					"SELECT (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 118 AND IC_CODE = AAA.IC_CODE LIMIT 1) AS IC_NAME \n");
			sb.append(", SUM(CNT1) CNT1, SUM(CNT2) CNT2								\n");
			sb.append("FROM(                                                                        \n");
			sb.append(" SELECT                                                                      \n");
			sb.append("	        B.IC_CODE                                                           \n");
			sb.append("	        ,COUNT(*) AS CNT1                                                   \n");
			sb.append("          ,0 AS CNT2                                                         \n");
			sb.append("	   FROM ISSUE_DATA A                                                        \n");
			sb.append("	      , ISSUE_DATA_CODE B                                                   \n");
			sb.append("	      , ISSUE_DATA_CODE C                                                   \n");
			sb.append("	  WHERE A.MD_DATE BETWEEN '" + ir_sdate + " " + ir_stime + "' AND '" + ir_edate + " " + ir_etime
					+ "' \n");
			sb.append("	    AND A.ID_SEQ = B.ID_SEQ                                                 \n");
			sb.append("	    AND A.ID_SEQ = C.ID_SEQ                                                 \n");
			sb.append("	    AND B.IC_TYPE = 118                                                     \n");
			sb.append("	    AND C.IC_TYPE = 18 AND C.IC_CODE = 1                                    \n");
			sb.append("	    GROUP BY B.IC_CODE                                                      \n");
			sb.append("      union all                                                              \n");
			sb.append("	 SELECT                                                                     \n");
			sb.append("	        B.IC_CODE                                                           \n");
			sb.append("          ,0 AS CNT1                                                         \n");
			sb.append("	        ,COUNT(*) AS CNT2                                                   \n");
			sb.append("	   FROM ISSUE_DATA A                                                        \n");
			sb.append("	      , ISSUE_DATA_CODE B                                                   \n");
			sb.append("	      , ISSUE_DATA_CODE C                                                   \n");
			sb.append("	  WHERE A.MD_DATE BETWEEN '" + sDate_pre + " " + ir_stime + "' AND '" + eDate_pre + " "
					+ ir_etime + "' \n");
			sb.append("	    AND A.ID_SEQ = B.ID_SEQ                                                 \n");
			sb.append("	    AND A.ID_SEQ = C.ID_SEQ                                                 \n");
			sb.append("	    AND B.IC_TYPE = 118                                                     \n");
			sb.append("	    AND C.IC_TYPE = 18 AND C.IC_CODE = 1                                    \n");
			sb.append("	    GROUP BY B.IC_CODE                                                      \n");
			sb.append(") AAA                                                                        \n");
			sb.append("GROUP BY AAA.IC_CODE                                                         \n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dataMap = new HashMap();
				dataMap.put("CATEGORY", rs.getString("IC_NAME"));
				dataMap.put("CNT1", rs.getString("CNT1"));
				dataMap.put("CNT2", rs.getString("CNT2"));
				result.add(dataMap);
			}

		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return result;
	}

	public ArrayList getManagementDate(String sDate_pre, String eDate_pre, String ir_sdate, String ir_stime,
			String ir_edate, String ir_etime) {
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("	## 경영진 \n");
			/*
			 * sb.
			 * append("SELECT																				\n"
			 * ); sb.
			 * append("  AAA.IC_NAME                                                                        \n"
			 * ); sb.
			 * append("  ,ifnull(AAA.CNT1, 0) AS CNT1                                                       \n"
			 * ); sb.
			 * append("  ,ifnull(BBB.CNT2, 0) AS CNT2                                                       \n"
			 * ); sb.
			 * append("FROM                                                                                 \n"
			 * ); sb.
			 * append("(                                                                                    \n"
			 * ); sb.
			 * append("SELECT                                                                               \n"
			 * ); sb.
			 * append("      AA.IC_NAME                                                                     \n"
			 * ); sb.
			 * append("      ,BB.CNT1                                                                       \n"
			 * ); sb.
			 * append("FROM                                                                                 \n"
			 * ); sb.
			 * append("(SELECT IC_NAME, IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 7 AND IC_CODE IN (1,2))AA   \n"
			 * ); sb.
			 * append("LEFT OUTER JOIN                                                                      \n"
			 * ); sb.
			 * append("(SELECT                                                                              \n"
			 * ); sb.
			 * append("      B.IC_CODE                                                                      \n"
			 * ); sb.
			 * append("      ,COUNT(1) AS CNT1                                                              \n"
			 * ); sb.
			 * append("  FROM ISSUE_DATA A                                                                  \n"
			 * ); sb.
			 * append("      ,ISSUE_DATA_CODE B                                                             \n"
			 * ); sb.append("	  WHERE A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"
			 * +ir_edate+" "+ir_etime+"' \n"); sb.
			 * append("   AND A.ID_SEQ = B.ID_SEQ                                                           \n"
			 * ); sb.
			 * append("   AND B.IC_TYPE = 7 AND B.IC_CODE IN (1,2)                                          \n"
			 * ); sb.
			 * append(" GROUP BY B.IC_CODE) BB ON AA.IC_CODE = BB.IC_CODE                                   \n"
			 * ); sb.
			 * append(" UNION ALL                                                                           \n"
			 * ); sb.
			 * append(" SELECT                                                                              \n"
			 * ); sb.
			 * append("      '기타' AS IC_NAME                                                              \n"
			 * ); sb.
			 * append("      ,COUNT(1) AS CNT1                                                              \n"
			 * ); sb.
			 * append("  FROM ISSUE_DATA A                                                                  \n"
			 * ); sb.
			 * append("      ,ISSUE_DATA_CODE B                                                             \n"
			 * ); sb.append("	  WHERE A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"
			 * +ir_edate+" "+ir_etime+"' \n"); sb.
			 * append("   AND A.ID_SEQ = B.ID_SEQ                                                           \n"
			 * ); sb.
			 * append("   AND B.IC_TYPE = 7 AND B.IC_CODE IN (3,4,5)                                        \n"
			 * ); sb.
			 * append(" ) AAA ,                                                                             \n"
			 * ); sb.
			 * append(" (                                                                                   \n"
			 * ); sb.
			 * append("SELECT                                                                               \n"
			 * ); sb.
			 * append("      AA.IC_NAME                                                                     \n"
			 * ); sb.
			 * append("      ,BB.CNT2                                                                       \n"
			 * ); sb.
			 * append("FROM                                                                                 \n"
			 * ); sb.
			 * append("(SELECT IC_NAME, IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 7 AND IC_CODE IN (1,2))AA   \n"
			 * ); sb.
			 * append("LEFT OUTER JOIN                                                                      \n"
			 * ); sb.
			 * append("(SELECT                                                                              \n"
			 * ); sb.
			 * append("      B.IC_CODE                                                                      \n"
			 * ); sb.
			 * append("      ,COUNT(1) AS CNT2                                                              \n"
			 * ); sb.
			 * append("  FROM ISSUE_DATA A                                                                  \n"
			 * ); sb.
			 * append("      ,ISSUE_DATA_CODE B                                                             \n"
			 * );
			 * sb.append("	  WHERE A.MD_DATE BETWEEN '"+sDate_pre+" "+ir_stime+"' AND '"
			 * +eDate_pre+" "+ir_etime+"' \n"); sb.
			 * append("   AND A.ID_SEQ = B.ID_SEQ                                                           \n"
			 * ); sb.
			 * append("   AND B.IC_TYPE = 7 AND B.IC_CODE IN (1,2)                                          \n"
			 * ); sb.
			 * append(" GROUP BY B.IC_CODE) BB ON AA.IC_CODE = BB.IC_CODE                                   \n"
			 * ); sb.
			 * append(" UNION ALL                                                                           \n"
			 * ); sb.
			 * append(" SELECT                                                                              \n"
			 * ); sb.
			 * append("      '기타' AS IC_NAME                                                              \n"
			 * ); sb.
			 * append("      ,COUNT(1) AS CNT2                                                              \n"
			 * ); sb.
			 * append("  FROM ISSUE_DATA A                                                                  \n"
			 * ); sb.
			 * append("      ,ISSUE_DATA_CODE B                                                             \n"
			 * );
			 * sb.append("	  WHERE A.MD_DATE BETWEEN '"+sDate_pre+" "+ir_stime+"' AND '"
			 * +eDate_pre+" "+ir_etime+"' \n"); sb.
			 * append("   AND A.ID_SEQ = B.ID_SEQ                                                           \n"
			 * ); sb.
			 * append("   AND B.IC_TYPE = 7 AND B.IC_CODE IN (3,4,5)                                        \n"
			 * ); sb.
			 * append(" ) BBB                                                                               \n"
			 * ); sb.
			 * append(" WHERE AAA.IC_NAME = BBB.IC_NAME                                                     \n"
			 * );
			 */
			sb.append("	  SELECT 																			\n");
			sb.append("      AAA.IC_NAME                                                                    \n");
			sb.append("      ,SUM(AAA.CNT1) AS CNT1                                                         \n");
			sb.append("      ,SUM(AAA.CNT2) AS CNT2                                                         \n");
			sb.append("  FROM                                                                               \n");
			sb.append("      (                                                                              \n");
			sb.append("   SELECT                                                                            \n");
			sb.append("      AA.IC_NAME                                                                     \n");
			sb.append("      ,IFNULL(BB.CNT1, 0) AS CNT1                                                    \n");
			sb.append("      ,IFNULL(BB.CNT2, 0) AS CNT2                                                    \n");
			sb.append("FROM                                                                                 \n");
			sb.append("(SELECT IC_NAME, IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 7 AND IC_CODE IN (1,2))AA   \n");
			sb.append("LEFT OUTER JOIN                                                                      \n");
			sb.append("(SELECT                                                                              \n");
			sb.append("      B.IC_CODE                                                                      \n");
			sb.append("      ,COUNT(B.IC_CODE) AS CNT1                                                      \n");
			sb.append("      ,0 AS CNT2                                                              \n");
			sb.append("  FROM ISSUE_DATA A                                                                  \n");
			sb.append("      ,ISSUE_DATA_CODE B                                                             \n");
			sb.append("	  WHERE A.MD_DATE BETWEEN '" + ir_sdate + " " + ir_stime + "' AND '" + ir_edate + " " + ir_etime
					+ "' \n");
			sb.append("   AND A.ID_SEQ = B.ID_SEQ                                                           \n");
			sb.append("   AND B.IC_TYPE = 7 AND B.IC_CODE IN (1,2)                                          \n");
			sb.append(" GROUP BY B.IC_CODE) BB ON AA.IC_CODE = BB.IC_CODE                                   \n");
			sb.append(" UNION ALL                                                                           \n");
			sb.append(" SELECT                                                                              \n");
			sb.append("      '기타' AS IC_NAME                                                              \n");
			sb.append("      ,0 AS CNT1                                                                     \n");
			sb.append("      ,COUNT(1) AS CNT2                                                              \n");
			sb.append("  FROM ISSUE_DATA A                                                                  \n");
			sb.append("      ,ISSUE_DATA_CODE B                                                             \n");
			sb.append("	  WHERE A.MD_DATE BETWEEN '" + ir_sdate + " " + ir_stime + "' AND '" + ir_edate + " " + ir_etime
					+ "' \n");
			sb.append("   AND A.ID_SEQ = B.ID_SEQ                                                           \n");
			sb.append("   AND B.IC_TYPE = 7 AND B.IC_CODE IN (3,4,5)                                        \n");
			sb.append(" UNION ALL                                                                           \n");
			sb.append("SELECT                                                                               \n");
			sb.append("      AA.IC_NAME                                                                     \n");
			sb.append("      ,IFNULL(BB.CNT1, 0) AS CNT1                                                    \n");
			sb.append("      ,IFNULL(BB.CNT2, 0) AS CNT2                                                    \n");
			sb.append("FROM                                                                                 \n");
			sb.append("(SELECT IC_NAME, IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 7 AND IC_CODE IN (1,2))AA   \n");
			sb.append("LEFT OUTER JOIN                                                                      \n");
			sb.append("(SELECT                                                                              \n");
			sb.append("      B.IC_CODE                                                                      \n");
			sb.append("      ,0 AS CNT1                                                                     \n");
			sb.append("      ,COUNT(1) AS CNT2                                                              \n");
			sb.append("  FROM ISSUE_DATA A                                                                  \n");
			sb.append("      ,ISSUE_DATA_CODE B                                                             \n");
			sb.append("	  WHERE A.MD_DATE BETWEEN '" + sDate_pre + " " + ir_stime + "' AND '" + eDate_pre + " "
					+ ir_etime + "' \n");
			sb.append("   AND A.ID_SEQ = B.ID_SEQ                                                           \n");
			sb.append("   AND B.IC_TYPE = 7 AND B.IC_CODE IN (1,2)                                          \n");
			sb.append(" GROUP BY B.IC_CODE) BB ON AA.IC_CODE = BB.IC_CODE                                   \n");
			sb.append(" UNION ALL                                                                           \n");
			sb.append(" SELECT                                                                              \n");
			sb.append("      '기타' AS IC_NAME                                                              \n");
			sb.append("      ,0 AS CNT1                                                                     \n");
			sb.append("      ,COUNT(1) AS CNT2                                                              \n");
			sb.append("  FROM ISSUE_DATA A                                                                  \n");
			sb.append("      ,ISSUE_DATA_CODE B                                                             \n");
			sb.append("	  WHERE A.MD_DATE BETWEEN '" + sDate_pre + " " + ir_stime + "' AND '" + eDate_pre + " "
					+ ir_etime + "' \n");
			sb.append("   AND A.ID_SEQ = B.ID_SEQ                                                           \n");
			sb.append("   AND B.IC_TYPE = 7 AND B.IC_CODE IN (3,4,5)                                        \n");
			sb.append("   ) AAA                                                                             \n");
			sb.append("   GROUP BY AAA.IC_NAME                                                              \n");
			sb.append("   ORDER BY 1 DESC                                                              \n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dataMap = new HashMap();
				dataMap.put("CATEGORY", rs.getString("IC_NAME"));
				dataMap.put("CNT1", rs.getString("CNT1"));
				dataMap.put("CNT2", rs.getString("CNT2"));
				result.add(dataMap);
			}

		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return result;
	}

	public ArrayList getSourceData(String ir_sdate, String ir_stime, String ir_edate, String ir_etime) {
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("	## 출처별 \n");
			sb.append("SELECT			\n");
			sb.append("  AA.IC_NAME     \n");
			sb.append("  ,ifnull(BB.POS,0) AS POS        \n");
			sb.append("  ,ifnull(BB.NEG,0) AS NEG        \n");
			sb.append("  ,ifnull(BB.NEU,0) AS NEU         \n");
			sb.append("FROM             \n");
			sb.append("(SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=6 AND IC_CODE IN (2,3,4,6,8))AA	\n");
			sb.append("LEFT OUTER JOIN                                                                          \n");
			sb.append("(SELECT                                                                                  \n");
			sb.append("      B.IC_CODE                                                                          \n");
			sb.append("      ,SUM(IF(C.IC_CODE=1 , 1, 0)) AS POS                                                \n");
			sb.append("      ,SUM(IF(C.IC_CODE=2 , 1, 0)) AS NEG                                                \n");
			sb.append("      ,SUM(IF(C.IC_CODE=3 , 1, 0)) AS NEU                                                \n");
			sb.append("FROM ISSUE_DATA A                                                                        \n");
			sb.append("    ,ISSUE_DATA_CODE B                                                                   \n");
			sb.append("    ,ISSUE_DATA_CODE_DETAIL C                                                            \n");
			sb.append("WHERE A.MD_DATE BETWEEN '" + ir_sdate + " " + ir_stime + "' AND '" + ir_edate + " " + ir_etime
					+ "'      \n");
			sb.append("AND A.ID_SEQ = B.ID_SEQ                                                                  \n");
			sb.append("AND B.ID_SEQ = C.ID_SEQ                                                                  \n");
			sb.append("AND B.IC_TYPE = 6 AND B.IC_CODE IN (2,3,4,6,8)                                           \n");
			sb.append("GROUP BY B.IC_CODE) BB ON AA.IC_CODE = BB.IC_CODE                                        \n");
			sb.append("UNION ALL                                                                                \n");
			sb.append("SELECT                                                                                   \n");
			sb.append("      '기타' AS IC_NAME                                                                  \n");
			sb.append(
					"      ,ifnull(SUM(IF(C.IC_CODE=1 , 1, 0)),0) AS POS                                                \n");
			sb.append(
					"      ,ifnull(SUM(IF(C.IC_CODE=2 , 1, 0)),0) AS NEG                                                \n");
			sb.append(
					"      ,ifnull(SUM(IF(C.IC_CODE=3 , 1, 0)),0) AS NEU                                                \n");
			sb.append("FROM ISSUE_DATA A                                                                        \n");
			sb.append("    ,ISSUE_DATA_CODE B                                                                   \n");
			sb.append("    ,ISSUE_DATA_CODE_DETAIL C                                                            \n");
			sb.append("WHERE A.MD_DATE BETWEEN '" + ir_sdate + " " + ir_stime + "' AND '" + ir_edate + " " + ir_etime
					+ "'      \n");
			sb.append("AND A.ID_SEQ = B.ID_SEQ                                                                  \n");
			sb.append("AND B.ID_SEQ = C.ID_SEQ                                                                  \n");
			sb.append("AND B.IC_TYPE = 6 AND B.IC_CODE IN (1,5,7)                                               \n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dataMap = new HashMap();
				dataMap.put("CATEGORY", rs.getString("IC_NAME"));
				dataMap.put("POS", rs.getString("POS"));
				dataMap.put("NEG", rs.getString("NEG"));
				dataMap.put("NEU", rs.getString("NEU"));
				result.add(dataMap);
			}

		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return result;
	}

	public ArrayList BarChartData(String ic_type, String ir_sdate, String ir_stime, String ir_edate, String ir_etime) {
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();

			sb.append("SELECT B.IC_TYPE, B.IC_CODE, B.IC_NAME AS CATEGORY, IFNULL(A.CNT,0) AS CNT  FROM (\n");

			sb.append("SELECT IDC.IC_TYPE, IDC.IC_CODE, IC.IC_NAME AS CATEGORY, COUNT(*) AS CNT\n");
			sb.append("FROM ISSUE_DATA ID\n");
			sb.append("INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("ON IDC.IC_TYPE = " + ic_type + "\n");
			sb.append("AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("INNER JOIN ISSUE_CODE IC\n");
			sb.append("ON IDC.IC_TYPE = IC.IC_TYPE AND IDC.IC_CODE = IC.IC_CODE\n");
			sb.append("INNER JOIN ISSUE_DATA_CODE IDC2\n");
			sb.append("ON IDC2.IC_TYPE = 4 AND IDC2.IC_CODE IN (1,2)\n");
			sb.append("AND ID.ID_SEQ = IDC2.ID_SEQ\n");
			sb.append("WHERE ID.MD_DATE BETWEEN '" + ir_sdate + " " + ir_stime + "' AND '" + ir_edate + " " + ir_etime
					+ "'\n");
			sb.append("GROUP BY IDC.IC_TYPE, IDC.IC_CODE, IC.IC_NAME\n");
			sb.append("ORDER BY IDC.IC_TYPE, IDC.IC_CODE\n");

			sb.append(")A RIGHT OUTER JOIN (SELECT A.IC_TYPE, A.IC_CODE\n");
			sb.append(", A.IC_NAME\n");
			sb.append(", A.IC_ORDER\n");
			sb.append("FROM ISSUE_CODE A \n");
			// sb.append(", IC_S_RELATION B\n");
			// sb.append("WHERE A.IC_SEQ = B.IC_SEQ\n");
			sb.append("WHERE A.IC_TYPE = 6 \n");
			sb.append("AND A.IC_CODE > 0 \n");
			sb.append("AND A.IC_USEYN = 'Y'\n");
			sb.append(") B ON A.IC_CODE = B.IC_CODE ORDER BY B.IC_ORDER ASC\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			int sumPotal = 0;

			while (rs.next()) {

				if (rs.getString("IC_CODE").equals("6") || rs.getString("IC_CODE").equals("8")) {
					sumPotal += rs.getInt("CNT");
				}
			}

			rs.beforeFirst();

			while (rs.next()) {

				dataMap = new HashMap();

				if (!rs.getString("IC_CODE").equals("8")) {
					dataMap.put("CATEGORY", rs.getString("CATEGORY"));
					if (rs.getString("IC_CODE").equals("6")) {

						dataMap.put("CNT", Integer.toString(sumPotal));
					} else {
						dataMap.put("CNT", rs.getString("CNT"));
					}
					result.add(dataMap);
				}

			}
		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

	public ArrayList channelTrendChartData(String ic_type, String ir_sdate, String ir_stime, String ir_edate,
			String ir_etime) {
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();

			sb.append("SELECT IC.IC_CODE, IC.IC_NAME AS CATEGORY\n");
			sb.append(
					", IFNULL(SUM(T.P_CNT), 0) AS PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) AS NAME1\n");
			sb.append(
					", IFNULL(SUM(T.N_CNT), 0) AS NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) AS NAME2\n");
			sb.append(
					", IFNULL(SUM(T.E_CNT), 0) AS ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) AS NAME3\n");
			sb.append("FROM\n");
			sb.append("(SELECT * FROM ISSUE_CODE WHERE IC_TYPE = " + ic_type
					+ " AND IC_USEYN = 'Y' AND IC_CODE != 0) IC\n");
			// sb.append("LEFT OUTER JOIN\n");
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
			sb.append("		ON IDC1.IC_TYPE = " + ic_type + "\n");
			sb.append("		AND IDC1.ID_SEQ = ID.ID_SEQ\n");
			sb.append("INNER JOIN ISSUE_DATA_CODE IDC2\n");
			sb.append("ON IDC2.IC_TYPE = 4 AND IDC2.IC_CODE IN (1,2)\n");
			sb.append("AND IDC2.ID_SEQ = ID.ID_SEQ\n");
			// sb.append(" WHERE DATE_FORMAT(ID.MD_DATE, '%Y%m%d') = '"+date+"'\n");
			sb.append("		WHERE ID.MD_DATE BETWEEN '" + ir_sdate + " " + ir_stime + "' AND '" + ir_edate + " "
					+ ir_etime + "'	\n");
			sb.append("		GROUP BY IDC1.IC_CODE, IDC.IC_CODE\n");
			sb.append(") T\n");
			sb.append("ON IC.IC_CODE = T.IC_CODE\n");
			sb.append("GROUP BY IC.IC_CODE, IC.IC_NAME\n");
			sb.append("ORDER BY IC.IC_CODE\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {

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
		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

	public ArrayList channelTrendChartData_TYPE15(String ic_type, String ir_sdate, String ir_stime, String ir_edate,
			String ir_etime) {
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();

			sb.append("SELECT IC.IC_CODE, IC.IC_NAME AS CATEGORY\n");
			sb.append(
					", IFNULL(SUM(T.P_CNT), 0) AS PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) AS NAME1\n");
			sb.append(
					", IFNULL(SUM(T.N_CNT), 0) AS NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) AS NAME2\n");
			sb.append(
					", IFNULL(SUM(T.E_CNT), 0) AS ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) AS NAME3\n");
			sb.append("FROM\n");
			sb.append("(SELECT * FROM ISSUE_CODE WHERE IC_TYPE = " + ic_type
					+ " AND IC_USEYN = 'Y' AND IC_CODE != 0) IC\n");
			// sb.append("LEFT OUTER JOIN\n");
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
			sb.append("		ON IDC1.IC_TYPE = " + ic_type + "\n");
			sb.append("		AND IDC1.ID_SEQ = ID.ID_SEQ\n");
			sb.append("INNER JOIN ISSUE_DATA_CODE IDC2\n");
			sb.append("ON IDC2.IC_TYPE = 4 AND IDC2.IC_CODE IN (1,2)\n");
			sb.append("AND IDC2.ID_SEQ = ID.ID_SEQ\n");
			// sb.append(" WHERE DATE_FORMAT(ID.MD_DATE, '%Y%m%d') = '"+date+"'\n");
			sb.append("		WHERE ID.MD_DATE BETWEEN '" + ir_sdate + " " + ir_stime + "' AND '" + ir_edate + " "
					+ ir_etime + "'	\n");
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
			childBean[0].setCategory("CEO/경영진");
			childBean[1] = superBean.new dailyChart();
			childBean[1].setCategory("경영/사업");
			childBean[2] = superBean.new dailyChart();
			childBean[2].setCategory("상품/기술");
			childBean[3] = superBean.new dailyChart();
			childBean[3].setCategory("서비스");
			childBean[4] = superBean.new dailyChart();
			childBean[4].setCategory("광고/홍보");
			childBean[5] = superBean.new dailyChart();
			childBean[5].setCategory("행사/이벤트");
			childBean[6] = superBean.new dailyChart();
			childBean[6].setCategory("사회공헌");
			childBean[7] = superBean.new dailyChart();
			childBean[7].setCategory("사건사고");
			childBean[8] = superBean.new dailyChart();
			childBean[8].setCategory("기타");

			while (rs.next()) {
				if (rs.getString("IC_CODE").equals("1") || rs.getString("IC_CODE").equals("2")) {
					childBean[0].setAddPcnt(rs.getInt("PCNT"));
					childBean[0].setAddNcnt(rs.getInt("NCNT"));
					childBean[0].setAddEcnt(rs.getInt("ECNT"));
					// childBean[0].setCategory(rs.getString("CATEGORY"));
				}

				if (rs.getString("IC_CODE").equals("3") || rs.getString("IC_CODE").equals("5")
						|| rs.getString("IC_CODE").equals("7") || rs.getString("IC_CODE").equals("8")
						|| rs.getString("IC_CODE").equals("9") || rs.getString("IC_CODE").equals("10")
						|| rs.getString("IC_CODE").equals("11")) {

					childBean[1].setAddPcnt(rs.getInt("PCNT"));
					childBean[1].setAddNcnt(rs.getInt("NCNT"));
					childBean[1].setAddEcnt(rs.getInt("ECNT"));
					// childBean[1].setCategory(rs.getString("CATEGORY"));
				}

				if (rs.getString("IC_CODE").equals("4")) {
					childBean[2].setAddPcnt(rs.getInt("PCNT"));
					childBean[2].setAddNcnt(rs.getInt("NCNT"));
					childBean[2].setAddEcnt(rs.getInt("ECNT"));
					// childBean[2].setCategory(rs.getString("CATEGORY"));
				}

				if (rs.getString("IC_CODE").equals("6")) {
					childBean[3].setAddPcnt(rs.getInt("PCNT"));
					childBean[3].setAddNcnt(rs.getInt("NCNT"));
					childBean[3].setAddEcnt(rs.getInt("ECNT"));
					// childBean[3].setCategory(rs.getString("CATEGORY"));
				}

				if (rs.getString("IC_CODE").equals("16")) {
					childBean[4].setAddPcnt(rs.getInt("PCNT"));
					childBean[4].setAddNcnt(rs.getInt("NCNT"));
					childBean[4].setAddEcnt(rs.getInt("ECNT"));
					// childBean[4].setCategory(rs.getString("CATEGORY"));
				}

				if (rs.getString("IC_CODE").equals("17") || rs.getString("IC_CODE").equals("18")) {
					childBean[5].setAddPcnt(rs.getInt("PCNT"));
					childBean[5].setAddNcnt(rs.getInt("NCNT"));
					childBean[5].setAddEcnt(rs.getInt("ECNT"));
					// childBean[5].setCategory(rs.getString("CATEGORY"));
				}

				if (rs.getString("IC_CODE").equals("19")) {
					childBean[6].setAddPcnt(rs.getInt("PCNT"));
					childBean[6].setAddNcnt(rs.getInt("NCNT"));
					childBean[6].setAddEcnt(rs.getInt("ECNT"));
					// childBean[6].setCategory(rs.getString("CATEGORY"));
				}

				if (rs.getString("IC_CODE").equals("14") || rs.getString("IC_CODE").equals("15")) {
					childBean[7].setAddPcnt(rs.getInt("PCNT"));
					childBean[7].setAddNcnt(rs.getInt("NCNT"));
					childBean[7].setAddEcnt(rs.getInt("ECNT"));
					// childBean[7].setCategory(rs.getString("CATEGORY"));
				}

				if (rs.getString("IC_CODE").equals("20")) {
					childBean[8].setAddPcnt(rs.getInt("PCNT"));
					childBean[8].setAddNcnt(rs.getInt("NCNT"));
					childBean[8].setAddEcnt(rs.getInt("ECNT"));
					// childBean[8].setCategory(rs.getString("CATEGORY"));
				}

			}

			for (int i = 0; i < childBean.length; i++) {
				if (childBean[i].getPcnt() > 0 || childBean[i].getNcnt() > 0 || childBean[i].getEcnt() > 0) {
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
			 * while(rs.next()){
			 * 
			 * dataMap = new HashMap(); dataMap.put("CATEGORY", rs.getString("CATEGORY"));
			 * dataMap.put("PCNT", rs.getString("PCNT")); dataMap.put("NAME1",
			 * rs.getString("NAME1")); dataMap.put("NCNT", rs.getString("NCNT"));
			 * dataMap.put("NAME2", rs.getString("NAME2")); dataMap.put("ECNT",
			 * rs.getString("ECNT")); dataMap.put("NAME3", rs.getString("NAME3"));
			 * result.add(dataMap);
			 * 
			 * }
			 */
		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

	public ArrayList channelTrendChartData_TEMP(String ic_type, String ir_sdate, String ir_stime, String ir_edate,
			String ir_etime) {
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();

			sb.append("SELECT A.* FROM (\n");

			sb.append("SELECT IC.IC_CODE, IC.IC_NAME AS CATEGORY\n");
			sb.append(
					", IFNULL(SUM(T.P_CNT), 0) AS PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) AS NAME1\n");
			sb.append(
					", IFNULL(SUM(T.N_CNT), 0) AS NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) AS NAME2\n");
			sb.append(
					", IFNULL(SUM(T.E_CNT), 0) AS ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) AS NAME3\n");
			sb.append("FROM\n");
			sb.append("(SELECT * FROM ISSUE_CODE WHERE IC_TYPE = " + ic_type
					+ " AND IC_USEYN = 'Y' AND IC_CODE != 0) IC\n");
			sb.append("LEFT OUTER JOIN\n");
			// sb.append("INNER JOIN\n");
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
			sb.append("		ON IDC1.IC_TYPE = " + ic_type + "\n");
			sb.append("		AND IDC1.ID_SEQ = ID.ID_SEQ\n");

			sb.append("INNER JOIN ISSUE_DATA_CODE IDC2\n");
			sb.append("ON IDC2.IC_TYPE = 4 AND IDC2.IC_CODE IN (1,2)\n");
			sb.append("AND IDC2.ID_SEQ = ID.ID_SEQ\n");

			// sb.append(" WHERE DATE_FORMAT(ID.MD_DATE, '%Y%m%d') = '"+date+"'\n");
			sb.append("		WHERE ID.MD_DATE BETWEEN '" + ir_sdate + " " + ir_stime + "' AND '" + ir_edate + " "
					+ ir_etime + "'	\n");
			sb.append("		GROUP BY IDC1.IC_CODE, IDC.IC_CODE\n");
			sb.append(") T\n");
			sb.append("ON IC.IC_CODE = T.IC_CODE\n");
			sb.append("GROUP BY IC.IC_CODE, IC.IC_NAME\n");
			sb.append("ORDER BY IC.IC_CODE\n");

			sb.append(")A,(SELECT A.IC_CODE\n");
			sb.append(", A.IC_NAME\n");
			sb.append(", A.IC_ORDER\n");
			sb.append("FROM ISSUE_CODE A \n");
			// sb.append(", IC_S_RELATION B\n");
			// sb.append("WHERE A.IC_SEQ = B.IC_SEQ\n");
			sb.append("WHERE A.IC_TYPE = 6 \n");
			sb.append("AND A.IC_CODE > 0 \n");
			sb.append("AND A.IC_USEYN = 'Y'\n");
			sb.append(") B WHERE A.IC_CODE = B.IC_CODE ORDER BY B.IC_ORDER ASC\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			int sumPotal_pcnt = 0;
			int sumPotal_ncnt = 0;
			int sumPotal_ecnt = 0;

			while (rs.next()) {
				if (rs.getString("IC_CODE").equals("6") || rs.getString("IC_CODE").equals("8")) {
					sumPotal_pcnt += rs.getInt("PCNT");
					sumPotal_ncnt += rs.getInt("NCNT");
					sumPotal_ecnt += rs.getInt("ECNT");
				}
			}

			rs.beforeFirst();

			while (rs.next()) {

				if (!rs.getString("IC_CODE").equals("8")) {

					if (rs.getString("IC_CODE").equals("6")) {
						dataMap = new HashMap();
						dataMap.put("CATEGORY", rs.getString("CATEGORY"));
						dataMap.put("PCNT", Integer.toString(sumPotal_pcnt));
						dataMap.put("NAME1", rs.getString("NAME1"));
						dataMap.put("NCNT", Integer.toString(sumPotal_ncnt));
						dataMap.put("NAME2", rs.getString("NAME2"));
						dataMap.put("ECNT", Integer.toString(sumPotal_ecnt));
						dataMap.put("NAME3", rs.getString("NAME3"));
						result.add(dataMap);
					} else {
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
		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

	public ArrayList channelTrendChartData_Keyword(String k_xp, String ir_sdate, String ir_stime, String ir_edate,
			String ir_etime) {
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();

			sb.append(
					"SELECT A.K_YP, A.K_VALUE AS CATEGORY																				\n");
			sb.append(
					"     , IFNULL(SUM(B.P_CNT), 0) AS PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) AS NAME1	\n");
			sb.append(
					"     , IFNULL(SUM(B.N_CNT), 0) AS NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) AS NAME2	\n");
			sb.append(
					"     , IFNULL(SUM(B.E_CNT), 0) AS ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) AS NAME3	\n");
			sb.append(
					"  FROM 																											\n");
			sb.append("       (SELECT K_YP, K_VALUE FROM KEYWORD WHERE K_XP = " + k_xp
					+ " AND K_YP > 0 AND K_ZP = 0)A						\n");
			sb.append(
					"        LEFT OUTER JOIN																							\n");
			sb.append(
					"       (SELECT A.K_YP																							\n");
			sb.append(
					"             , IFNULL(CASE B.IC_CODE WHEN 1 THEN SUM(1) END, 0) AS P_CNT											\n");
			sb.append(
					"             , IFNULL(CASE B.IC_CODE WHEN 2 THEN SUM(1) END, 0) AS N_CNT											\n");
			sb.append(
					"             , IFNULL(CASE B.IC_CODE WHEN 3 THEN SUM(1) END, 0) AS E_CNT											\n");
			sb.append(
					"          FROM ISSUE_DATA	  A																					\n");
			sb.append(
					"             , ISSUE_DATA_CODE B																					\n");
			sb.append(
					"         WHERE A.ID_SEQ = B.ID_SEQ																				\n");
			sb.append("           AND A.MD_DATE BETWEEN '" + ir_sdate + " " + ir_stime + "' AND '" + ir_edate + " "
					+ ir_etime + "'						\n");
			sb.append(
					"           AND B.IC_TYPE = 9																						\n");
			sb.append("           AND A.K_XP = " + k_xp
					+ "																					\n");
			sb.append(
					"         GROUP BY A.K_YP, B.IC_CODE)B ON A.K_YP = B.K_YP															\n");
			sb.append(
					"         GROUP BY A.K_YP, A.K_VALUE  																			\n");
			sb.append(
					"         ORDER BY A.K_YP																							\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
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
		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

	public ArrayList DailyReport1(String id_seqs) {

		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();
			sb.append("SELECT MD_PSEQ FROM ISSUE_DATA WHERE ID_SEQ IN (" + id_seqs + ")\n");
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			String md_pseq = "";
			if (rs.next()) {

				if (md_pseq.equals("")) {
					md_pseq = rs.getString("MD_PSEQ");
				} else {
					md_pseq += "," + rs.getString("MD_PSEQ");
				}

			}

			sb = new StringBuffer();
			sb.append(
					"SELECT A.SG_SEQ																								\n");
			sb.append(
					"     , A.MD_SITE_NAME																						\n");
			sb.append(
					"     , A.ID_TITLE																							\n");
			sb.append(
					"     , A.MEDIA_INFO																							\n");
			sb.append(
					"     , A.IC_NAME																								\n");
			sb.append(
					"     , A.MD_PSEQ																								\n");
			sb.append(
					"     , A.ID_URL																								\n");
			sb.append(
					"     , A.MD_DATE																								\n");
			sb.append(
					"     , (SELECT COUNT(*)-1 FROM ISSUE_DATA WHERE MD_PSEQ = A.MD_PSEQ AND SG_SEQ = A.SG_SEQ)  AS SAME_CT		\n");
			sb.append(
					"  FROM ( 																									\n");
			sb.append(
					"        SELECT A.*																							\n");
			sb.append(
					"             , IF(A.SG_SEQ = @SG_SEQ, @ROW:=@ROW+1, @ROW:=1) AS RANK											\n");
			sb.append(
					"             , @SG_SEQ := A.SG_SEQ																			\n");
			sb.append(
					"          FROM (   																							\n");
			sb.append(
					"                SELECT A.SG_SEQ																				\n");
			sb.append(
					"                     , A.MD_SITE_NAME																		\n");
			sb.append(
					"                     , A.ID_TITLE																			\n");
			sb.append(
					"                     , A.MEDIA_INFO																			\n");
			sb.append(
					"                     , C.IC_NAME																				\n");
			sb.append(
					"                     , A.MD_PSEQ																				\n");
			sb.append(
					"                     , A.ID_URL																				\n");
			sb.append(
					"                     , A.MD_DATE																				\n");
			sb.append(
					"                  FROM ISSUE_DATA A																			\n");
			sb.append(
					"                     , ISSUE_DATA_CODE B																		\n");
			sb.append(
					"                     , ISSUE_CODE C																			\n");
			sb.append("                 WHERE A.MD_PSEQ IN (" + md_pseq
					+ ")															\n");
			sb.append(
					"                   AND A.SG_SEQ IN (17,18,29)																\n");
			sb.append(
					"                   AND A.ID_SEQ = B.ID_SEQ																	\n");
			sb.append(
					"                   AND B.IC_TYPE = 12																		\n");
			sb.append(
					"                   AND B.IC_TYPE = C.IC_TYPE																	\n");
			sb.append(
					"                   AND B.IC_CODE = C.IC_CODE																	\n");
			sb.append(
					"                   AND C.IC_USEYN = 'Y'																		\n");
			sb.append(
					"              GROUP BY A.MD_PSEQ, A.SG_SEQ																	\n");
			sb.append(
					"              ORDER BY SG_SEQ, MD_PSEQ																		\n");
			sb.append(
					"             )A																								\n");
			sb.append(
					"           , (SELECT @ROW:=0, @SG_SEQ:='') R																	\n");
			sb.append(
					"       )A																									\n");
			sb.append(
					" WHERE RANK <= 3																								\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
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

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	public ArrayList IssueTitle(String id_seqs, String ir_sdate, String ir_stime, String ir_edate, String ir_etime) {

		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append(
					"SELECT                                                                                                  \n");
			sb.append(
					"     (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 118 AND IC_CODE = AA.IC_CODE AND IC_USEYN='Y' LIMIT 1) AS IC_NAME    \n");
			sb.append(
					"     ,COUNT(AA.IC_CODE) AS CNT                                                                           \n");
			sb.append(
					"FROM(                                                                                                    \n");
			sb.append(
					"SELECT                                                                                                   \n");
			sb.append(
					"      DISTINCT(A.ID_SEQ) AS ID_SEQ                                                                       \n");
			sb.append(
					"      , C.IC_CODE                                                                                        \n");
			sb.append(
					"  FROM ISSUE_DATA A																					    \n");
			sb.append(
					"    , ISSUE_DATA_CODE B                                                                                  \n");
			sb.append(
					"    , ISSUE_DATA_CODE C                                                                                  \n");
			sb.append(
					"WHERE A.ID_SEQ = B.ID_SEQ																				\n");
			sb.append(
					"AND A.ID_SEQ = C.ID_SEQ																					\n");
			sb.append("AND A.MD_DATE BETWEEN '" + ir_sdate + " " + ir_stime + "' AND '" + ir_edate + " " + ir_etime
					+ "'						\n");
			sb.append(
					"AND B.IC_TYPE = 7                                                                                        \n");
			sb.append(
					"AND C.IC_TYPE = 118                                                                                      \n");
			if (!id_seqs.equals("")) {
				sb.append("   AND A.ID_SEQ IN (" + id_seqs
						+ ") 																	\n");
			}
			sb.append(
					") AA                                                                                                     \n");
			sb.append(
					"GROUP BY AA.IC_CODE                                                                                      \n");
			sb.append(
					"ORDER BY AA.IC_CODE DESC																					\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			String tmp[] = null;
			while (rs.next()) {
				tmp = new String[2];
				tmp[0] = rs.getString("IC_NAME");
				tmp[1] = rs.getString("CNT");
				arrResult.add(tmp);
			}
			sb = null;
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	public ArrayList DailyIssueReport1(String id_seqs, String ir_sdate, String ir_stime, String ir_edate,
			String ir_etime) {

		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append(
					"SELECT DISTINCT(A.ID_SEQ) AS ID_SEQ 																							\n");
			sb.append(
					"     , A.ID_TITLE																						\n");
			sb.append(
					"     , A.ID_URL																							\n");
			sb.append(
					"     , IFNULL( (SELECT T_USER_ID FROM ISSUE_TWITTER WHERE ID_SEQ = A.ID_SEQ), '') AS T_ID				\n");
			sb.append(
					"     , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 12) AS TYPE12												    \n");
			sb.append(
					"     , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 118) AS TYPE118												    \n");
			sb.append(
					"     , FN_ISSUE_NAME_ID_SEQ_DETAIL(A.ID_SEQ, 9, B.IC_TYPE, B.IC_CODE ) AS TYPE9							\n");
			sb.append(
					"     , A.MD_SITE_NAME																					\n");
			sb.append(
					"     , DATE_FORMAT(A.MD_DATE, '%m.%d') AS MD_DATE														\n");
			sb.append(
					"     , (SELECT COUNT(*)-1 FROM ISSUE_DATA WHERE MD_PSEQ = A.MD_PSEQ AND SG_SEQ = A.SG_SEQ)  AS SAME_CT	\n");
			sb.append(
					"  FROM ISSUE_DATA A																						\n");
			sb.append(
					"     , ISSUE_DATA_CODE B																					\n");
			sb.append(
					"     , ISSUE_DATA_CODE C																					\n");
			sb.append(
					" WHERE A.ID_SEQ = B.ID_SEQ																				\n");
			sb.append(
					"   AND A.ID_SEQ = C.ID_SEQ																				\n");
			sb.append("   AND A.MD_DATE BETWEEN '" + ir_sdate + " " + ir_stime + "' AND '" + ir_edate + " " + ir_etime
					+ "'						\n");
			if (!id_seqs.equals("")) {
				sb.append("   AND A.ID_SEQ IN (" + id_seqs
						+ ") 																	\n");
			}
			sb.append(
					"  AND B.IC_TYPE = 7																						\n");
			sb.append(
					"  AND C.IC_TYPE = 118																					\n");
			sb.append(
					"  ORDER BY C.IC_CODE DESC, A.MD_DATE DESC																\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setTemp1(rs.getString("TYPE9")); // 성향
				idBean.setTemp2(rs.getString("TYPE118")); // 이슈
				idBean.setTemp3(rs.getString("TYPE12")); // 영향력(파워, 일반)
				idBean.setId_writter(rs.getString("T_ID")); // 트위터 아이디
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setMd_same_ct(rs.getString("SAME_CT"));
				arrResult.add(idBean);
			}
			sb = null;
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	public ArrayList getChartData(String sdate, String stime, String edate, String etime, ArrayList codeList,
			boolean addType) {
		ArrayList result = new ArrayList();
		ArrayList TempResult = new ArrayList();
		StringBuffer sb = null;
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			long diff = du.DateDiff(sdate.replaceAll("-", ""), edate.replaceAll("-", ""));

			// 차이값구할때 첫날은 빼버림 (사유 : 데이터 안나와서.SOL)
			String tempDate = du.addDay(edate.replaceAll("-", ""), (int) diff + 1, "yyyyMMdd");

			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();

			sb.append("SELECT MD_DATE\n");
			for (int i = 1; i < codeList.size(); i++) {
				IssueCodeBean icb = (IssueCodeBean) codeList.get(i);

				sb.append(", SUM(T.CNT" + i + ") AS CNT" + i + "\n");

			}
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d') AS MD_DATE\n");
			for (int i = 1; i < codeList.size(); i++) {

				IssueCodeBean icb = (IssueCodeBean) codeList.get(i);

				if (icb.getIc_code() == 6) {
					sb.append("		, IFNULL(CASE IDC1.IC_CODE WHEN " + icb.getIc_code()
							+ " THEN SUM(1) END, 0) + IFNULL(CASE IDC1.IC_CODE WHEN 8 THEN SUM(1) END, 0) AS CNT" + i
							+ "\n");
				} else {
					sb.append("		, IFNULL(CASE IDC1.IC_CODE WHEN " + icb.getIc_code() + " THEN SUM(1) END, 0) AS CNT"
							+ i + "\n");
				}

			}
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC1\n");
			sb.append("		ON ID.ID_SEQ = IDC1.ID_SEQ\n");
			sb.append("		AND IDC1.IC_TYPE = 6\n");

			if (addType) {
				sb.append("		INNER JOIN ISSUE_DATA_CODE IDC2\n");
				sb.append("		ON ID.ID_SEQ = IDC2.ID_SEQ\n");
				sb.append("		AND IDC2.IC_TYPE = 9 AND IDC2.IC_CODE = 1\n");
			}

			sb.append("INNER JOIN ISSUE_DATA_CODE IDC3					\n");
			sb.append("   ON ID.ID_SEQ = IDC3.ID_SEQ					\n");
			sb.append("  AND IDC3.IC_TYPE = 4 AND IDC3.IC_CODE IN (1,2)	\n");

			sb.append("		WHERE MD_DATE BETWEEN '" + sdate + " " + stime + "' AND '" + edate + " " + etime + "'\n");
			sb.append("		GROUP BY DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d'), IDC1.IC_CODE\n");
			sb.append(") T\n");
			sb.append("GROUP BY T.MD_DATE\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				IssueDataBean idb = new IssueDataBean();
				idb.setMd_date(rs.getString("MD_DATE"));
				int[] cnt = new int[codeList.size() - 1];
				if (codeList.size() > 0) {
					for (int i = 1; i < codeList.size(); i++) {
						IssueCodeBean icb = (IssueCodeBean) codeList.get(i);
						cnt[i - 1] = rs.getInt("CNT" + i);
					}
				}
				idb.setCnt(cnt);
				TempResult.add(idb);
			}

			boolean chk = false;
			int chkCnt = 0;
			while (true) {
				if (chkCnt != 0) {
					tempDate = du.addDay(tempDate, 1, "yyyyMMdd");
				}
				for (int i = 0; i < TempResult.size(); i++) {
					IssueDataBean idb = (IssueDataBean) TempResult.get(i);
					if (idb.getMd_date().equals(tempDate)) {
						chk = true;
						result.add(idb);
					}
				}
				if (!chk) {
					IssueDataBean idb = new IssueDataBean();
					idb.setMd_date(tempDate);
					int[] cnt = new int[codeList.size() - 1];
					if (codeList.size() > 0) {
						for (int i = 1; i < codeList.size(); i++) {
							IssueCodeBean icb = (IssueCodeBean) codeList.get(i);
							cnt[i - 1] = 0;
						}
					}
					idb.setCnt(cnt);
					result.add(idb);
				} else {
					chk = false;
				}
				chkCnt++;
				if (tempDate.equals(edate.replaceAll("-", ""))) {
					break;
				}
			}

		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			sb = null;
			try {
				if (dbconn != null) {
					dbconn.close();
				}
			} catch (Exception e) {
			}
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception e) {
			}
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
			}
		}
		return result;
	}

	public ArrayList getChartData_type9(String sdate, String stime, String edate, String etime, ArrayList codeList,
			boolean addType) {
		ArrayList result = new ArrayList();
		ArrayList TempResult = new ArrayList();
		StringBuffer sb = null;
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			long diff = du.DateDiff(sdate.replaceAll("-", ""), edate.replaceAll("-", ""));

			// 차이값구할때 첫날은 빼버림 (사유 : 데이터 안나와서.SOL)
			String tempDate = du.addDay(edate.replaceAll("-", ""), (int) diff + 1, "yyyyMMdd");

			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();

			sb.append("SELECT MD_DATE\n");
			for (int i = 1; i < codeList.size(); i++) {
				IssueCodeBean icb = (IssueCodeBean) codeList.get(i);

				sb.append(", SUM(T.CNT" + i + ") AS CNT" + i + "\n");

			}
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d') AS MD_DATE\n");
			for (int i = 1; i < codeList.size(); i++) {

				IssueCodeBean icb = (IssueCodeBean) codeList.get(i);

				if (icb.getIc_code() == 6) {
					sb.append("		, IFNULL(CASE IDC1.IC_CODE WHEN " + icb.getIc_code()
							+ " THEN SUM(1) END, 0) + IFNULL(CASE IDC1.IC_CODE WHEN 8 THEN SUM(1) END, 0) AS CNT" + i
							+ "\n");
				} else {
					sb.append("		, IFNULL(CASE IDC1.IC_CODE WHEN " + icb.getIc_code() + " THEN SUM(1) END, 0) AS CNT"
							+ i + "\n");
				}

			}
			sb.append("		FROM ISSUE_DATA ID\n");

			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC1\n");
			sb.append("		ON ID.ID_SEQ = IDC1.ID_SEQ\n");
			sb.append("		AND IDC1.IC_TYPE = 9\n");

			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC3\n");
			sb.append("		ON ID.ID_SEQ = IDC3.ID_SEQ\n");
			sb.append("		AND IDC3.IC_TYPE = 4 AND IDC3.IC_CODE IN (1,2)\n");

			sb.append("		WHERE MD_DATE BETWEEN '" + sdate + " " + stime + "' AND '" + edate + " " + etime + "'\n");
			sb.append("		GROUP BY DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d'), IDC1.IC_CODE\n");
			sb.append(") T\n");
			sb.append("GROUP BY T.MD_DATE\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				IssueDataBean idb = new IssueDataBean();
				idb.setMd_date(rs.getString("MD_DATE"));
				int[] cnt = new int[codeList.size() - 1];
				if (codeList.size() > 0) {
					for (int i = 1; i < codeList.size(); i++) {
						IssueCodeBean icb = (IssueCodeBean) codeList.get(i);
						cnt[i - 1] = rs.getInt("CNT" + i);
					}
				}
				idb.setCnt(cnt);
				TempResult.add(idb);
			}

			boolean chk = false;
			int chkCnt = 0;
			while (true) {
				if (chkCnt != 0) {
					tempDate = du.addDay(tempDate, 1, "yyyyMMdd");
				}
				for (int i = 0; i < TempResult.size(); i++) {
					IssueDataBean idb = (IssueDataBean) TempResult.get(i);
					if (idb.getMd_date().equals(tempDate)) {
						chk = true;
						result.add(idb);
					}
				}
				if (!chk) {
					IssueDataBean idb = new IssueDataBean();
					idb.setMd_date(tempDate);
					int[] cnt = new int[codeList.size() - 1];
					if (codeList.size() > 0) {
						for (int i = 1; i < codeList.size(); i++) {
							IssueCodeBean icb = (IssueCodeBean) codeList.get(i);
							cnt[i - 1] = 0;
						}
					}
					idb.setCnt(cnt);
					result.add(idb);
				} else {
					chk = false;
				}
				chkCnt++;
				if (tempDate.equals(edate.replaceAll("-", ""))) {
					break;
				}
			}

		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			sb = null;
			try {
				if (dbconn != null) {
					dbconn.close();
				}
			} catch (Exception e) {
			}
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception e) {
			}
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
			}
		}
		return result;
	}

	public ArrayList getChartData_type(String sdate, String stime, String edate, String etime, ArrayList codeList,
			boolean addType) {
		ArrayList result = new ArrayList();
		ArrayList TempResult = new ArrayList();
		StringBuffer sb = null;
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String tempDate = du.addDay(edate.replaceAll("-", ""), -6, "yyyyMMdd");
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();

			sb.append("SELECT MD_DATE\n");
			for (int i = 1; i < codeList.size(); i++) {
				IssueCodeBean icb = (IssueCodeBean) codeList.get(i);
				sb.append(", SUM(T.CNT" + i + ") AS CNT" + i + "\n");
			}
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d') AS MD_DATE\n");
			for (int i = 1; i < codeList.size(); i++) {
				IssueCodeBean icb = (IssueCodeBean) codeList.get(i);
				sb.append("		, IFNULL(CASE IDC1.IC_CODE WHEN " + icb.getIc_code() + " THEN SUM(1) END, 0) AS CNT" + i
						+ "\n");
			}
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC1\n");
			sb.append("		ON ID.ID_SEQ = IDC1.ID_SEQ\n");
			sb.append("		AND IDC1.IC_TYPE = 6\n");

			if (addType) {
				sb.append("		INNER JOIN ISSUE_DATA_CODE IDC2\n");
				sb.append("		ON ID.ID_SEQ = IDC2.ID_SEQ\n");
				sb.append("		AND IDC2.IC_TYPE = 9 AND IDC2.IC_CODE = 1\n");
			}

			sb.append("		WHERE DATE_FORMAT(MD_DATE, '%Y%m%d%H') BETWEEN '" + tempDate.replaceAll("-", "")
					+ stime.substring(0, 2) + "' AND '" + edate.replaceAll("-", "") + etime.substring(0, 2) + "'\n");
			sb.append("		GROUP BY DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d'), IDC1.IC_CODE\n");
			sb.append(") T\n");
			sb.append("GROUP BY T.MD_DATE\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				IssueDataBean idb = new IssueDataBean();
				idb.setMd_date(rs.getString("MD_DATE"));
				int[] cnt = new int[codeList.size() - 1];
				if (codeList.size() > 0) {
					for (int i = 1; i < codeList.size(); i++) {
						IssueCodeBean icb = (IssueCodeBean) codeList.get(i);
						cnt[i - 1] = rs.getInt("CNT" + i);
					}
				}
				idb.setCnt(cnt);
				TempResult.add(idb);
			}

			boolean chk = false;
			int chkCnt = 0;
			while (true) {
				if (chkCnt != 0) {
					tempDate = du.addDay(tempDate, 1, "yyyyMMdd");
				}
				for (int i = 0; i < TempResult.size(); i++) {
					IssueDataBean idb = (IssueDataBean) TempResult.get(i);
					if (idb.getMd_date().equals(tempDate)) {
						chk = true;
						result.add(idb);
					}
				}
				if (!chk) {
					IssueDataBean idb = new IssueDataBean();
					idb.setMd_date(tempDate);
					int[] cnt = new int[codeList.size() - 1];
					if (codeList.size() > 0) {
						for (int i = 1; i < codeList.size(); i++) {
							IssueCodeBean icb = (IssueCodeBean) codeList.get(i);
							cnt[i - 1] = 0;
						}
					}
					idb.setCnt(cnt);
					result.add(idb);
				} else {
					chk = false;
				}
				chkCnt++;
				if (tempDate.equals(edate.replaceAll("-", ""))) {
					break;
				}
			}

		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			sb = null;
			try {
				if (dbconn != null) {
					dbconn.close();
				}
			} catch (Exception e) {
			}
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception e) {
			}
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
			}
		}
		return result;
	}

	public ArrayList getLastDataCode() {

		ArrayList arrResult = new ArrayList();

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();

			sb.append(
					"SELECT IC_TYPE, IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = (SELECT ID_SEQ FROM ISSUE_DATA_CODE ORDER BY 1 DESC LIMIT 1)\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				icBean = new IssueCodeBean();

				icBean.setIc_type(rs.getInt("IC_TYPE"));
				icBean.setIc_code(rs.getInt("IC_CODE"));

				arrResult.add(icBean);
			}
			sb = null;
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	public ArrayList getIssueChart1(String sDate, String sTime, String eDate, String eTime, String typeCode,
			String gubun, String schKey, String mg_company, String keyType) {

		ArrayList arrResult = new ArrayList();
		IssueSuperBean superBean = new IssueSuperBean();

		ArrayList arTypeCode = ReassembleTypeCode(typeCode);
		String[] getTBean = null;

		// String result = "";
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();

			String dayType = "";
			if (gubun.equals("day")) {
				dayType = "%Y%m%d";
			} else if (gubun.equals("time")) {
				dayType = "%Y%m%d %H";
			}

			sb.append("SELECT A.MD_DATE																\n");
			sb.append("     , SUM(IF(A.IC_CODE = 1, A.CNT ,0)) AS POS								\n");
			sb.append("     , SUM(IF(A.IC_CODE = 2, A.CNT ,0)) AS NEG								\n");
			sb.append("     , SUM(IF(A.IC_CODE = 3, A.CNT ,0)) AS NEU								\n");
			sb.append("  FROM (																		\n");

			sb.append("SELECT DATE_FORMAT(MD_DATE, '" + dayType + "') AS MD_DATE					\n");
			sb.append("     , B.IC_CODE															\n");
			sb.append("     , COUNT(*) AS CNT													\n");
			sb.append("  FROM ISSUE_DATA A														\n");

			// 분류체계검색
			// sb.append(getTypeCodeSql(sDate, eDate, sTime, eTime, typeCode) + "\n");

			// 세부조건검색
			for (int i = 0; i < arTypeCode.size(); i++) {
				getTBean = (String[]) arTypeCode.get(i);
				sb.append("			INNER JOIN ISSUE_DATA_CODE C" + (i + 1) + " ON A.ID_SEQ = C" + (i + 1)
						+ ".ID_SEQ 										\n");
				sb.append("                                        AND C" + (i + 1) + ".IC_TYPE = " + getTBean[0]
						+ "										\n");
				sb.append("                                        AND C" + (i + 1) + ".IC_CODE IN (" + getTBean[1]
						+ ")									\n");
			}

			if (!mg_company.equals("")) {
				sb.append(
						"INNER JOIN ISSUE_DATA_CODE T ON A.ID_SEQ = T.ID_SEQ AND T.IC_CODE IN (" + mg_company + ")\n");
			}

			sb.append("     , ISSUE_DATA_CODE B 												\n");
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ												\n");
			sb.append("   AND A.MD_DATE BETWEEN '" + sDate + " " + sTime + "' AND '" + eDate + " " + eTime + "'	\n");
			sb.append("   AND B.IC_TYPE = 9 													\n");

			/*
			 * if( !schKey.equals("") ) { String[] arrSchKey = schKey.split(" ");
			 * 
			 * for(int i =0; i < arrSchKey.length; i++){
			 * sb.append("		AND	A.ID_TITLE LIKE '%"+arrSchKey[i]
			 * +"%'  							\n"); } }
			 */

			// 키워드 조건 주기(AND검색)
			if (!schKey.equals("")) {
				String[] arKey = schKey.split(" ");

				for (int i = 0; i < arKey.length; i++) {
					if (!arKey[i].trim().equals("")) {
						if (keyType.equals("1")) {
							// 제목검색
							sb.append("AND ID_TITLE LIKE '%" + arKey[i] + "%' \n");

						} else if (keyType.equals("2")) {
							// 제목+내용검색
							sb.append("AND CONCAT(ID_TITLE,ID_CONTENT) LIKE '%" + arKey[i] + "%'\n");
						}
					}
				}
			}

			sb.append(" GROUP BY DATE_FORMAT(MD_DATE, '" + dayType + "'), IC_CODE					\n");

			sb.append("   )A GROUP BY MD_DATE ORDER BY MD_DATE 										\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			IssueSuperBean.Issue_chartBean cbean = null;

			while (rs.next()) {

				/*
				 * if(result.equals("")){ result =
				 * "{date: '"+rs.getString("MD_DATE")+"',긍정: "+rs.getString("POS")+",부정: "+rs.
				 * getString("NEG")+",중립: "+rs.getString("NEU")+"}"; }else{ result += "," +
				 * "{date: '"+rs.getString("MD_DATE")+"',긍정: "+rs.getString("POS")+",부정: "+rs.
				 * getString("NEG")+",중립: "+rs.getString("NEU")+"}"; }
				 */

				cbean = superBean.new Issue_chartBean();
				cbean.setMd_date(rs.getString("MD_DATE"));
				cbean.setPositive(rs.getString("POS"));
				cbean.setNegative(rs.getString("NEG"));
				cbean.setNeutral(rs.getString("NEU"));
				arrResult.add(cbean);

			}
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	public String getTypeCodeSql(String sdate, String edate, String stime, String etime, String typeCode) {

		String result = "";

		if (!typeCode.equals("")) {
			String[] type = typeCode.split("@");
			String[] code = null;

			StringBuffer sb = new StringBuffer();
			sb.append("INNER JOIN (SELECT DISTINCT A.ID_SEQ\n");
			sb.append("              FROM ISSUE_DATA A   \n");
			sb.append("                 , ISSUE_DATA_CODE B   \n");
			sb.append("             WHERE A.ID_SEQ = B.ID_SEQ        \n");
			sb.append("               AND A.MD_DATE BETWEEN '" + sdate + " " + stime + "' AND '" + edate + " " + etime
					+ "'        \n");
			sb.append("               AND (               \n");

			for (int i = 0; i < type.length; i++) {
				code = type[i].split(",");
				if (i != 0) {
					sb.append(" OR ");
				}
				sb.append(" (IC_TYPE=" + code[0] + " AND IC_CODE=" + code[1] + ")\n");
			}
			sb.append("                    )\n");
			sb.append("             GROUP BY ID_SEQ \n");
			sb.append("            HAVING COUNT(0)=" + type.length + ")Z ON A.ID_SEQ = Z.ID_SEQ\n");

			result = sb.toString();
		}

		return result;
	}

	public ArrayList getIssueDataTrendList(String ic_code, String sDate, String eDate, String sTime, String eTime) {

		ArrayList arrResult = new ArrayList();

		String tmpId_seq = "";

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();

			sb.append("	SELECT ID.* 									\n");
			sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC		\n");
			sb.append("	WHERE ID.ID_SEQ = IDC.ID_SEQ				\n");
			sb.append("	AND ID.MD_DATE BETWEEN '" + sDate + " " + sTime + ":00:00' AND '" + eDate + " " + eTime
					+ ":59:59' 	\n");
			sb.append("	AND IDC.IC_TYPE = 16			\n");
			sb.append("	AND IDC.IC_CODE = " + ic_code + " 	\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setId_regdate(rs.getString("ID_REGDATE"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setMd_site_menu(rs.getString("MD_SITE_MENU"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setId_mailyn(rs.getString("ID_MAILYN"));
				idBean.setId_useyn(rs.getString("ID_USEYN"));
				idBean.setM_seq(rs.getString("M_SEQ"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setL_alpha(rs.getString("L_ALPHA"));
				idBean.setId_reportyn(rs.getString("ID_REPORTYN"));

				arrResult.add(idBean);
				if (tmpId_seq.equals(""))
					tmpId_seq += rs.getString("ID_SEQ");
				else
					tmpId_seq += "," + rs.getString("ID_SEQ");
			}

			String prevI_seq = null;
			if (tmpId_seq.length() > 0) {
				// 이슈에 이슈코드어레이 추가
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
				sb.append("WHERE	IDC.ID_SEQ IN (" + tmpId_seq + ")  						\n");
				sb.append("         AND IC.IC_CODE = IDC.IC_CODE   						\n");
				sb.append("         AND IC.IC_TYPE = IDC.IC_TYPE	 					\n");
				sb.append("ORDER BY IDC.ID_SEQ DESC					 					\n");
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();

				while (rs.next()) {

					if (prevI_seq != null && !prevI_seq.equals(rs.getString("ID_SEQ"))) {
						addIssueCode(arrResult, prevI_seq, ArrIcList);
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
				addIssueCode(arrResult, prevI_seq, ArrIcList);
			}

			sb = null;
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	public ArrayList getIssueAllCode(String id_seq, int type) {
		ArrayList arrRet = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();
			sb.append(" SELECT                    \n");
			sb.append(" 		ID_SEQ            \n");
			sb.append(" 	  , IC_TYPE         \n");
			sb.append(" 	  , IC_CODE         \n");
			sb.append(" FROM ISSUE_DATA_CODE      \n");
			sb.append(" WHERE ID_SEQ = " + id_seq + "     \n");
			sb.append(" AND IC_TYPE= " + type + "             \n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				icBean = new IssueCodeBean();
				icBean.setIc_type(rs.getInt("IC_TYPE"));
				icBean.setIc_code(rs.getInt("IC_CODE"));
				arrRet.add(icBean);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return arrRet;
	}

	public ArrayList getIssueGubunList(String id_seqs) {

		ArrayList result = new ArrayList();
		ArrayList child_result = new ArrayList();
		try {
			if (!id_seqs.equals("")) {

				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				sb = new StringBuffer();
				sb.append("	SELECT AA.*									                       \n");
				sb.append("	FROM (									                   		   \n");
				sb.append("SELECT DISTINCT A.ID_SEQ									\n");
				sb.append("     , A.ID_TITLE								\n");
				sb.append("     , A.ID_URL									\n");
				sb.append("     , A.MD_SITE_NAME							\n");
				sb.append("     , A.MD_DATE									\n");
				sb.append("     , D.IC_NAME AS TYPE4						\n");
				sb.append("     , C.IC_CODE AS TYPE9						\n");
				sb.append("     , A.MD_TYPE									\n");
				sb.append("     , A.SG_SEQ									\n");
				// 카페만 적용
				sb.append("     , IF(A.SG_SEQ = 25, A.ID_CONTENT, '') AS ID_CONTENT			   \n");
				sb.append("	     , @ROW :=(SELECT COUNT(*)-1                                   \n");
				sb.append("                 FROM ISSUE_DATA A2, ISSUE_DATA_CODE B2             \n");
				sb.append("                 WHERE A2.ID_SEQ = B2.ID_SEQ                        \n");
				sb.append("                 AND A2.MD_PSEQ = A.MD_PSEQ                         \n");
				sb.append("                 AND B2.IC_TYPE = 19                                \n");
				sb.append("                 AND B2.IC_CODE = 1) AS MD_SAME_CTF                 \n");
				sb.append("	     , IF(@ROW < 0, 0, @ROW) AS MD_SAME_CT                         \n");
				sb.append("  FROM ISSUE_DATA A								\n");
				sb.append("     , ISSUE_DATA_CODE B							\n");
				sb.append("     , ISSUE_DATA_CODE_DETAIL C					\n");
				sb.append("     , ISSUE_CODE D								\n");
				sb.append(" WHERE A.ID_SEQ = B.ID_SEQ						\n");
				sb.append("   AND A.ID_SEQ = C.ID_SEQ						\n");
				sb.append("   AND A.ID_SEQ IN (" + id_seqs + ")					\n");
				sb.append("   AND B.IC_TYPE = 4								\n");
				sb.append("   AND C.IC_TYPE = 9								\n");
				sb.append("   AND B.IC_TYPE = D.IC_TYPE						\n");
				sb.append("   AND B.IC_CODE = D.IC_CODE						\n");
				sb.append("	 ORDER BY MD_SAME_CT DESC ) AA                             		   \n");
				sb.append("	 ORDER BY TYPE4 ASC, MD_TYPE, TYPE9  	               \n");
				// sb.append(" ORDER BY MD_TYPE, MD_SAME_CT DESC \n");
				// sb.append(" ORDER BY MD_TYPE, C.IC_CODE, A.MD_SAME_CT DESC \n");

				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();

				String pre_type4 = "";

				// ReportDataSuperBean.issueBean iBean = null;
				boolean check = false;
				while (rs.next()) {

					check = true;

					// type4가 변경될때만 입장
					if (pre_type4 != "" && !pre_type4.equals(rs.getString("TYPE4"))) {
						result.add(child_result);
						child_result = new ArrayList();
					}
					idBean = new IssueDataBean();
					// idBean = superBean.new issueBean();
					idBean.setId_seq(rs.getString("ID_SEQ"));
					idBean.setId_title(rs.getString("ID_TITLE"));
					idBean.setId_url(rs.getString("ID_URL"));
					idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
					idBean.setMd_date(rs.getString("MD_DATE"));
					idBean.setTemp1(rs.getString("TYPE4"));
					idBean.setTemp2(rs.getString("TYPE9"));
					idBean.setMd_type(rs.getString("MD_TYPE"));
					idBean.setSg_seq(rs.getString("SG_SEQ"));
					idBean.setId_content(rs.getString("ID_CONTENT"));
					idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
					child_result.add(idBean);

					pre_type4 = rs.getString("TYPE4");
				}

				if (check) {
					// 마지막 어레이까지 넣기~
					result.add(child_result);
				}
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

	public ArrayList getIssueGubunList_Infor(String id_seqs) {

		ArrayList result = new ArrayList();
		ArrayList child_result = new ArrayList();
		try {
			if (!id_seqs.equals("")) {

				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				sb = new StringBuffer();

				sb.append("	SELECT AA.*									                       \n");
				sb.append("	FROM (									                   		   \n");
				sb.append("	SELECT A.ID_SEQ									                   \n");
				sb.append("	     , A.ID_TITLE								                   \n");
				sb.append("	     , A.ID_URL									                   \n");
				sb.append("	     , A.MD_SITE_NAME							                   \n");
				sb.append("	     , A.MD_DATE									               \n");
				sb.append("	     , D.IC_NAME AS TYPE4						                   \n");
				sb.append("	     , C.IC_CODE AS TYPE9						                   \n");
				sb.append("	     , A.MD_TYPE									               \n");
				sb.append("	     , A.SG_SEQ									                   \n");
				sb.append("	     , IF(A.SG_SEQ = 25, A.ID_CONTENT, '') AS ID_CONTENT	       \n");
				sb.append("	     , @ROW :=(SELECT COUNT(*)-1                                   \n");
				sb.append("                 FROM ISSUE_DATA A2, ISSUE_DATA_CODE B2             \n");
				sb.append("                 WHERE A2.ID_SEQ = B2.ID_SEQ                        \n");
				sb.append("                 AND A2.MD_PSEQ = A.MD_PSEQ                         \n");
				sb.append("                 AND B2.IC_TYPE = 19                                \n");
				sb.append("                 AND B2.IC_CODE = 1) AS MD_SAME_CTF                 \n");
				sb.append("	     , IF(@ROW < 0, 0, @ROW) AS MD_SAME_CT                         \n");
				sb.append("	  FROM ISSUE_DATA A								                   \n");
				sb.append("	     , ISSUE_DATA_CODE B							               \n");
				sb.append("	     , ISSUE_DATA_CODE_DETAIL C					                   \n");
				sb.append("	     , ISSUE_CODE D                                                \n");
				sb.append("	     , (SELECT @ROW:= 0)R                                          \n");
				sb.append("	 WHERE A.ID_SEQ = B.ID_SEQ						                   \n");
				sb.append("	   AND A.ID_SEQ = C.ID_SEQ						                   \n");
				sb.append("	   AND A.ID_SEQ IN (" + id_seqs + ")	   							   \n");
				sb.append("	   AND A.MD_TYPE = 1								               \n");
				sb.append("	   AND B.IC_TYPE = 4								               \n");
				sb.append("	   AND C.IC_TYPE = 9								               \n");
				sb.append("	   AND B.IC_TYPE = D.IC_TYPE						               \n");
				sb.append("	   AND B.IC_CODE = D.IC_CODE						               \n");
				sb.append("	 ORDER BY MD_SAME_CT DESC ) AA                             		   \n");
				sb.append("	 ORDER BY TYPE4 DESC, MD_TYPE, TYPE9  	               \n");
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();

				String pre_type4 = "";

				// ReportDataSuperBean.issueBean iBean = null;
				boolean check = false;
				while (rs.next()) {

					check = true;

					// type4가 변경될때만 입장
					if (pre_type4 != "" && !pre_type4.equals(rs.getString("TYPE4"))) {
						result.add(child_result);
						child_result = new ArrayList();
					}
					idBean = new IssueDataBean();
					// idBean = superBean.new issueBean();
					idBean.setId_seq(rs.getString("ID_SEQ"));
					idBean.setId_title(rs.getString("ID_TITLE"));
					idBean.setId_url(rs.getString("ID_URL"));
					idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
					idBean.setMd_date(rs.getString("MD_DATE"));
					idBean.setTemp1(rs.getString("TYPE4"));
					idBean.setTemp2(rs.getString("TYPE9"));
					idBean.setMd_type(rs.getString("MD_TYPE"));
					idBean.setSg_seq(rs.getString("SG_SEQ"));
					idBean.setId_content(rs.getString("ID_CONTENT"));
					idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));

					child_result.add(idBean);

					pre_type4 = rs.getString("TYPE4");
				}

				if (check) {
					// 마지막 어레이까지 넣기~
					result.add(child_result);
				}
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;

	}

	public ArrayList getRelationKey() {
		return getRelationKey("");
	}

	public ArrayList getRelationKey(String id_seq) {

		ArrayList arrResult = new ArrayList();
		IssueCodeBean icb;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();
			if (!id_seq.equals("")) {
				sb.append(" SELECT B.RK_NAME 									\n");
				sb.append(" FROM RELATION_KEYWORD_MAP A, RELATION_KEYWORD_R B 	\n");
				sb.append(" WHERE A.RK_SEQ = B.RK_SEQ 							\n");
				sb.append(" AND A.ID_SEQ = " + id_seq + "  							\n");
				sb.append(" AND B.RK_USE = 'Y'		  							\n");
				sb.append(" GROUP BY B.RK_NAME									\n");

			} else {
				sb.append(" SELECT B.RK_NAME 									\n");
				sb.append(" FROM RELATION_KEYWORD_R B 							\n");
				sb.append(" WHERE B.RK_USE = 'Y'	 							\n");
			}

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {

				arrResult.add(rs.getString("RK_NAME"));
			}
			sb = null;
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	public ArrayList getRelationKey2() {

		ArrayList arrResult = new ArrayList();
		String[] arrRelkeyword = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();

			sb.append(" SELECT B.RK_SEQ, B.RK_NAME 							\n");
			sb.append(" FROM RELATION_KEYWORD_R B 							\n");
			sb.append(" WHERE B.RK_USE = 'Y'	 							\n");
			sb.append(" ORDER BY RK_NAME		 							\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {

				arrRelkeyword = new String[2];
				arrRelkeyword[0] = rs.getString("RK_SEQ");
				arrRelkeyword[1] = rs.getString("RK_NAME");
				arrResult.add(arrRelkeyword);
			}
			sb = null;
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	public ArrayList getRelationKeyword(String ic_code) {

		ArrayList arrResult = new ArrayList();
		IssueCodeBean icb;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();
			if (!ic_code.equals("")) {
				// sb.append(" SELECT B.RK_NAME \n");
				// sb.append(" FROM RELATION_KEYWORD_MAP A, RELATION_KEYWORD_R B \n");
				// sb.append(" WHERE A.RK_SEQ = B.RK_SEQ \n");
				// sb.append(" AND A.ID_SEQ = "+id_seq+" \n");
				// sb.append(" GROUP BY B.RK_NAME \n");

				sb.append("	SELECT A.RK_NAME from RELATION_KEYWORD_R A, RELATION_KEYWORD_R_ISSUE B 	\n");
				sb.append("	WHERE A.RK_SEQ = B.RK_SEQ AND B.IC_CODE = " + ic_code + " 					\n");
				sb.append("	AND B.RK_USE = 'Y'		 												\n");
				sb.append(" GROUP BY A.RK_NAME	ORDER BY 1 ASC										\n");

			} else {
				sb.append(" SELECT B.RK_NAME 														\n");
				sb.append(" FROM RELATION_KEYWORD_R B 												\n");
				sb.append(" WHERE B.RK_USE = 'Y'	 												\n");
			}

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				icb = new IssueCodeBean();
				icb.setIc_name(rs.getString("RK_NAME"));
				arrResult.add(icb);

			}
			sb = null;
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	public ArrayList getRelationKey2(String id_seq, String ic_type) {

		ArrayList arrResult = new ArrayList();
		String[] relKey = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();
			sb.append(" SELECT R.RK_SEQ 										\n");
			sb.append(" 	 , R.RK_NAME 										\n");
			sb.append(" 	FROM RELATION_KEYWORD_MAP MAP 						\n");
			sb.append(" 		, RELATION_KEYWORD_R R							\n");
			sb.append(" 	WHERE MAP.RK_SEQ = R.RK_SEQ						 	\n");
			sb.append(" 	AND MAP.ID_SEQ = " + id_seq + "						\n");
			sb.append(" 	AND MAP.IC_TYPE IN ( " + ic_type + " )				\n");
			sb.append(" 	AND R.RK_USE = 'Y'									\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				relKey = new String[2];
				relKey[0] = rs.getString("RK_SEQ");
				relKey[1] = rs.getString("RK_NAME");

				arrResult.add(relKey);
			}
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	public ArrayList getTypeCodeRelationKey(int ic_type, int ic_code) {

		ArrayList arrResult = new ArrayList();
		String[] relKey = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();
			sb.append(" SELECT R.RK_SEQ 										\n");
			sb.append(" 	 , R.RK_NAME 										\n");
			sb.append(" 	FROM RELATION_KEYWORD_MAP MAP 						\n");
			sb.append(" 		, RELATION_KEYWORD_R R							\n");
			sb.append(" 	WHERE MAP.RK_SEQ = R.RK_SEQ						 	\n");
			sb.append(" 	AND MAP.IC_TYPE = " + ic_type + "						\n");
			sb.append(" 	AND MAP.IC_CODE = " + ic_code + "						\n");
			sb.append(" 	AND R.RK_USE = 'Y'									\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				relKey = new String[2];
				relKey[0] = rs.getString("RK_SEQ");
				relKey[1] = rs.getString("RK_NAME");

				arrResult.add(relKey);
			}
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	public void saveOriginalorNot(String yData, String nData, String mode) {
		ArrayList arrResult = new ArrayList();
		String id_seq = "";
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			if (!"".equals(yData)) {
				// 원문저장
				sb = new StringBuffer();
				if ("SEARCH".equals(mode)) {
					sb.append("	SELECT ID_SEQ FROM ISSUE_DATA WHERE MD_SEQ IN (" + yData + ") \n");
				} else {
					sb.append("	SELECT ID_SEQ FROM ISSUE_DATA WHERE ID_SEQ IN (" + yData + ") \n");
				}
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				while (rs.next()) {
					arrResult.add(rs.getString("ID_SEQ"));
				}

				if (arrResult.size() > 0) {
					for (int i = 0; i < arrResult.size(); i++) {
						id_seq = (String) arrResult.get(i);
						System.out.println(id_seq);
						sb = new StringBuffer();
						sb.append("	DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ IN(" + id_seq + ")  AND IC_TYPE = 19 \n");
						pstmt = dbconn.createPStatement(sb.toString());
						pstmt.executeUpdate(sb.toString());

						sb = new StringBuffer();
						sb.append("	INSERT INTO ISSUE_DATA_CODE \n");
						sb.append("  (ID_SEQ, IC_TYPE, IC_CODE) \n");
						sb.append("  VALUES (" + id_seq + ", 19, 1);	\n");
						pstmt = dbconn.createPStatement(sb.toString());
						pstmt.executeUpdate(sb.toString());
					}
				}
			}

			if (!"".equals(nData)) {
				// 원문해제
				arrResult = new ArrayList();
				id_seq = "";
				sb = new StringBuffer();
				if ("SEARCH".equals(mode)) {
					sb.append("	SELECT ID_SEQ FROM ISSUE_DATA WHERE MD_SEQ IN (" + nData + ") \n");
				} else {
					sb.append("	SELECT ID_SEQ FROM ISSUE_DATA WHERE ID_SEQ IN (" + nData + ") \n");
				}
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				while (rs.next()) {
					arrResult.add(rs.getString("ID_SEQ"));
				}

				if (arrResult.size() > 0) {
					for (int i = 0; i < arrResult.size(); i++) {
						id_seq = (String) arrResult.get(i);
						System.out.println(id_seq);
						sb = new StringBuffer();
						sb.append("	DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ IN(" + id_seq + ")  AND IC_TYPE = 19 \n");
						pstmt = dbconn.createPStatement(sb.toString());
						pstmt.executeUpdate(sb.toString());

						sb = new StringBuffer();
						sb.append("	INSERT INTO ISSUE_DATA_CODE \n");
						sb.append("  (ID_SEQ, IC_TYPE, IC_CODE) \n");
						sb.append("  VALUES (" + id_seq + ", 19, 2);	\n");
						pstmt = dbconn.createPStatement(sb.toString());
						pstmt.executeUpdate(sb.toString());
					}
				}
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
	}

	// 품질안전센터_비고 데이터 선택
	public IssueCommentBean SelectIssueComment(String id_seq) {

		ArrayList arrResult = new ArrayList();
		String[] relKey = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			// 데이터 시퀀스 및 현대카드_주요이슈 커멘트 추가
			sb = new StringBuffer();
			sb.append("	SELECT ID_COMMENT FROM ISSUE_COMMENT 			     	\n");
			sb.append("  WHERE ID_SEQ = " + id_seq + "	  				        \n");
			sb.append("    AND IC_TYPE = 1 AND IC_CODE = 1	  			        \n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			icmBean = new IssueCommentBean();

			if (rs.next()) {
				//현대카드 주요이슈 코멘트
				icmBean.setHycard_comment(rs.getString("ID_COMMENT"));
			}
			// 데이터 시퀀스 및 현대캐피탈_주요이슈 커멘트 추가
			sb = new StringBuffer();
			sb.append("	SELECT ID_COMMENT FROM ISSUE_COMMENT 			     	\n");
			sb.append("  WHERE ID_SEQ = " + id_seq + "	  				        \n");
			sb.append("    AND IC_TYPE = 1 AND IC_CODE = 2	  			        \n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			if (rs.next()) {
				//현대캐피탈 주요이슈 코멘트
				icmBean.setHycap_comment(rs.getString("ID_COMMENT"));
			}

			// 데이터 시퀀스 및 현대커머셜_주요이슈 커멘트 추가
			sb = new StringBuffer();
			sb.append("	SELECT ID_COMMENT FROM ISSUE_COMMENT 			     	\n");
			sb.append("  WHERE ID_SEQ = " + id_seq + "	  				        \n");
			sb.append("    AND IC_TYPE = 1 AND IC_CODE = 3	  			        \n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			if (rs.next()) {
				//현대커머셜 주요이슈 코멘트
				icmBean.setHycom_comment(rs.getString("ID_COMMENT"));
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return icmBean;
	}

	// 품질안전센터_비고 데이터 삽입
	public void InsertIssueComment(String id_seq, String text) {
		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			// 데이터 시퀀스 및 부서-품질안전센터 커멘트 추가
			sb = new StringBuffer();
			sb.append("	INSERT INTO ISSUE_COMMENT 	     	\n");
			sb.append("  (ID_SEQ, ID_COMMENT)		        \n");
			sb.append("  VALUES (" + id_seq + ", '" + text + "')	\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			pstmt.executeUpdate(sb.toString());

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
	}

	// 품질안전센터_비고 데이터 삭제
	public String DeleteIssueComment(String id_seqs) {
		String text = "";
		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			// 데이터 시퀀스 및 부서-품질안전센터 커멘트 삭제
			sb = new StringBuffer();
			sb.append("	DELETE FROM ISSUE_COMMENT 	     		\n");
			sb.append("  WHERE ID_SEQ IN (" + id_seqs + ")		        \n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			pstmt.executeUpdate(sb.toString());

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return text;
	}

	public int getOriginalType(String md_seq) {
		int flag = 0;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("	SELECT   COUNT(*) AS ORIGINAL  \n");
			sb.append("   FROM   ISSUE_DATA_CODE  \n");
			sb.append("  WHERE   ID_SEQ IN (SELECT ID_SEQ FROM ISSUE_DATA WHERE MD_SEQ = " + md_seq + ")  \n");
			sb.append("    AND   (IC_TYPE = 19 AND IC_CODE=1) \n");
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery(sb.toString());

			if (rs.next()) {
				flag = rs.getInt("ORIGINAL");
			}
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return flag;
	}

	public String getIdContent(String id_seq) {
		String contents = "";

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("	SELECT ID_CONTENT FROM ISSUE_DATA WHERE ID_SEQ = " + id_seq + "  \n");
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery(sb.toString());

			if (rs.next()) {
				contents = rs.getString("ID_CONTENT");
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return contents;
	}

	/*
	 * 유사 묶기
	 */
	public IssueDataBean getIssueDataInfo(String md_seq, String baseSeq) {

		idBean = new IssueDataBean();

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();

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
			sb.append("        , ''	AS ID_WRITTER							\n");
			sb.append("        , C.MD_CONTENT								\n");
			sb.append("        , NOW() AS ID_REGDATE						\n");
			sb.append("        , 'N' AS ID_MAILYN							\n");
			sb.append("        , 'Y' AS	ID_USEYN							\n");
			sb.append("        , A.MD_SAME_COUNT							\n");
			sb.append("        , A.MD_TYPE									\n");
			sb.append("        , A.L_ALPHA									\n");
			sb.append("        , 'Y' AS ID_REPORTYN							\n");
			sb.append("        , A.MD_PSEQ 									\n");
			sb.append("     FROM META A, SG_S_RELATION B, DATA C			\n");
			sb.append("    WHERE A.S_SEQ = B.S_SEQ							\n");
			sb.append("      AND A.MD_SEQ = C.MD_SEQ						\n");
			sb.append("      AND A.MD_SEQ = " + md_seq + "						\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {

				idBean.setI_seq(rs.getString("I_SEQ"));
				idBean.setIt_seq(rs.getString("IT_SEQ"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setId_title(su.dbString(rs.getString("MD_TITLE")));
				idBean.setId_url(su.dbString(rs.getString("MD_URL")));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_writter(rs.getString("ID_WRITTER"));
				idBean.setId_content(su.dbString(rs.getString("MD_CONTENT")));
				idBean.setId_regdate(rs.getString("ID_REGDATE"));
				idBean.setId_mailyn(rs.getString("ID_MAILYN"));
				idBean.setId_useyn(rs.getString("ID_USEYN"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_COUNT"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setL_alpha(rs.getString("L_ALPHA"));
				idBean.setId_reportyn(rs.getString("ID_REPORTYN"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setM_seq("99");
				idBean.setTemp1("SBM");

			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return idBean;
	}

	public String getTypeCode(String baseSeq) {

		String result = "";

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();

			sb.append("SELECT B.IC_TYPE					\n");
			sb.append("     , B.IC_CODE					\n");
			sb.append("  FROM ISSUE_DATA A				\n");
			sb.append("     , ISSUE_DATA_CODE B			\n");
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ		\n");
			sb.append("   AND A.MD_SEQ = " + baseSeq + "	\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				if (result.equals("")) {
					result = rs.getString("IC_TYPE") + "," + rs.getString("IC_CODE");
				} else {
					result += "@" + rs.getString("IC_TYPE") + "," + rs.getString("IC_CODE");
				}
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

	/**
	 * ISSUE_DATA, ISSUE_CODE, ISSUE_COMMENT 등록하고 이슈번호 리턴
	 * 
	 * @param IssueDataBean    :이슈데이터빈
	 * @param IssueCommentBean :이슈코멘트빈
	 * @param typeCode         :분류체계
	 * @return
	 */
	public String insertIssueData(IssueDataBean idBean, IssueCommentBean icBean, String typeCode) {
		String[] arrTemp;
		String[] arrTypeCode;
		boolean result = false;
		String insertNum = null;
		try {
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
			sb.append("                   ,MD_PSEQ 									\n");
			sb.append("                   ,REG_TYPE 								\n");
			sb.append("                   ) 										\n");
			sb.append(" VALUES(                                                   	\n");
			sb.append("        " + idBean.getMd_seq() + "                             	\n");
			sb.append("        ," + idBean.getI_seq() + "                             	\n");
			sb.append("        ," + idBean.getIt_seq() + "                            	\n");
			sb.append("        ," + idBean.getS_seq() + "                             	\n");
			sb.append("        ," + idBean.getSg_seq() + "                            	\n");
			sb.append("        ,'" + idBean.getMd_site_name() + "'                    	\n");
			sb.append("        ,'" + idBean.getMd_site_menu() + "'                    	\n");
			sb.append("        ,'" + idBean.getMd_date() + "'                         	\n");
			sb.append("        ,'" + idBean.getId_title() + "'                        	\n");
			sb.append("        ,'" + idBean.getId_url() + "'                          	\n");
			sb.append("        ,'" + idBean.getId_writter() + "'                      	\n");
			sb.append("        ,'" + idBean.getId_content() + "'                      	\n");
			sb.append("        ,'" + idBean.getId_regdate() + "'                      	\n");
			sb.append("        ,'" + idBean.getId_mailyn() + "'                       	\n");
			sb.append("        ,'" + idBean.getId_useyn() + "'                        	\n");
			sb.append("        ,'" + idBean.getMd_same_ct() + "'                        \n");
			sb.append("        ," + idBean.getM_seq() + "                             	\n");
			sb.append("        ," + idBean.getMd_type() + "                             \n");
			sb.append("        ,'" + idBean.getMd_pseq() + "'                           \n");
			sb.append("        ,'" + idBean.getTemp1() + "'   	                        \n");
			sb.append(" )                                                         	\n");

			System.out.println(sb.toString());

			String sg_seq = "";

			if (stmt.executeUpdate(sb.toString()) > 0) {
				sb = new StringBuffer();
				// sb.append(" SELECT MAX(ID_SEQ) ID_SEQ FROM ISSUE_DATA \n");
				sb.append(
						" SELECT ID_SEQ, (SELECT B.IC_CODE																				  \n");
				sb.append(
						"                   FROM IC_S_RELATION A																			  \n");
				sb.append(
						"                      , ISSUE_CODE B 																			  \n");
				sb.append(
						"                  WHERE A.IC_SEQ = B.IC_SEQ																		  \n");
				sb.append("                    AND A.S_SEQ = SG_SEQ) AS SG_SEQ FROM ISSUE_DATA WHERE MD_SEQ = "
						+ idBean.getMd_seq() + "   		  \n");

				rs = stmt.executeQuery(sb.toString());
				if (rs.next()) {
					insertNum = rs.getString("ID_SEQ");
					sg_seq = rs.getString("SG_SEQ");
				}

				if (!idBean.getMd_site_menu().equals("SOLR") && !idBean.getSg_seq().equals("26")) {
					sb = new StringBuffer();
					sb.append(" UPDATE IDX SET ISSUE_CHECK ='Y'                        \n");
					sb.append(" WHERE MD_SEQ = " + idBean.getMd_seq() + "                  \n");
					stmt.executeUpdate(sb.toString());
				}

				if (idBean.getSg_seq().equals("26")) {
					sb = new StringBuffer();
					sb.append(" UPDATE TOP_IDX SET ISSUE_CHECK ='Y'                        \n");
					sb.append(" WHERE T_SEQ = " + idBean.getMd_seq() + "                  \n");
					stmt.executeUpdate(sb.toString());

				}
			}

			if (icBean != null) {
				sb = new StringBuffer();
				sb.append(" INSERT INTO ISSUE_COMMENT(ID_SEQ                        \n");
				sb.append("                           ,IM_DATE)                     \n");
				sb.append("                           ,IM_COMMENT                   \n");
				sb.append("                           )                             \n");
				sb.append(" VALUES(                                                 \n");
				sb.append("        " + insertNum + "                                    \n");
				sb.append("        ,'" + du.getCurrentDate("yyyy-MM-dd HH:mm:ss") + "'  \n");
				sb.append("        ," + icBean.getIm_comment() + "                      \n");
				sb.append(" )                                                       \n");
				if (stmt.executeUpdate(sb.toString()) > 0)
					result = true;
			}

			Boolean check = false;

			if (typeCode != null && !typeCode.equals("")) {
				arrTemp = typeCode.split("@");
				for (int i = 0; i < arrTemp.length; i++) {
					arrTypeCode = arrTemp[i].split(",");

					if (arrTypeCode[0].equals("6")) {
						check = true;
					}

					sb = new StringBuffer();
					sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
					sb.append("VALUES ( 						\n");
					sb.append("        " + insertNum + " 		\n");
					sb.append("        ," + arrTypeCode[0] + "	\n");
					sb.append("        ," + arrTypeCode[1] + "  \n");
					sb.append("        ) 						\n");
					if (stmt.executeUpdate(sb.toString()) > 0)
						result = true;
				}

				if (!check) {

					sb = new StringBuffer();

					sb = new StringBuffer();
					sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
					sb.append("VALUES ( 						\n");
					sb.append("        " + insertNum + " 		\n");
					sb.append("        ,6						\n");
					sb.append("        ," + sg_seq + "  			\n");
					sb.append("        ) 						\n");
					if (stmt.executeUpdate(sb.toString()) > 0)
						result = true;
				}
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return insertNum;
	}

	/*
	 * 자기사 이슈 저장
	 */
	public String insertIssueData_sub(String md_seq, IssueCommentBean icBean, String typeCode, IssueDataBean idBean) {

		String insertNum = "";
		String sg_seq = "";
		String[] arrTemp = null;
		String[] arrTypeCode = null;

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			// 자기사 가져오기~
			sb = new StringBuffer();
			sb.append(
					"SELECT C.MD_SEQ 																				\n");
			sb.append(
					"  FROM META C																				\n");
			sb.append(" WHERE C.MD_PSEQ = (SELECT MD_PSEQ FROM META WHERE MD_SEQ = " + md_seq
					+ ")						\n");
			sb.append("   AND C.MD_SEQ <> " + md_seq
					+ "																\n");
			sb.append(
					"   AND NOT EXISTS (																			\n");
			sb.append(
					"                   SELECT 1																	\n");
			sb.append(
					"                     FROM META A, ISSUE_DATA B												\n");
			sb.append("                    WHERE A.MD_PSEQ = (SELECT MD_PSEQ FROM META WHERE MD_SEQ = " + md_seq
					+ ")	\n");
			sb.append("                      AND A.MD_SEQ <> " + md_seq
					+ "												\n");
			sb.append(
					"                      AND A.MD_SEQ = B.MD_SEQ												\n");
			sb.append(
					"                      AND A.MD_SEQ = C.MD_PSEQ												\n");
			sb.append(
					"                   )																			\n");

			rs = stmt.executeQuery(sb.toString());

			ArrayList arr_mdSeq = new ArrayList();
			while (rs.next()) {
				arr_mdSeq.add(rs.getString("MD_SEQ"));
			}

			System.out.println("유사 : " + arr_mdSeq.size() + "건");

			for (int i = 0; i < arr_mdSeq.size(); i++) {
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
				if (idBean.getKeywordInfo() != null && !idBean.getKeywordInfo().equals("")) {
					sb.append("                            , K_XP					\n");
					sb.append("                            , K_YP					\n");
					sb.append("                            , K_ZP					\n");
				}
				sb.append("                            , MD_PSEQ 				\n");
				if (idBean.getH_seq() != null && !idBean.getH_seq().equals("")) {
					sb.append("                            , H_SEQ 				\n");
				}
				if (idBean.getId_mobile() != null && !idBean.getId_mobile().equals("")) {
					sb.append("                            , ID_MOBILE 				\n");
				}
				sb.append("                            ) 				\n");
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
				if (idBean.getKeywordInfo() != null && !idBean.getKeywordInfo().equals("")) {
					sb.append("        , " + idBean.getKeywordInfo().split(",")[0] + " 	\n");
					sb.append("        , " + idBean.getKeywordInfo().split(",")[1] + " 	\n");
					sb.append("        , " + idBean.getKeywordInfo().split(",")[2] + " 	\n");
				}
				sb.append("        , A.MD_PSEQ 									\n");
				if (idBean.getH_seq() != null && !idBean.getH_seq().equals("")) {
					sb.append("        , " + idBean.getH_seq() + " 									\n");
				}
				if (idBean.getId_mobile() != null && !idBean.getId_mobile().equals("")) {
					sb.append("        , '" + idBean.getId_mobile() + "' 									\n");
				}
				sb.append("     FROM META A, SG_S_RELATION B, DATA C			\n");
				sb.append("    WHERE A.S_SEQ = B.S_SEQ							\n");
				sb.append("      AND A.MD_SEQ = C.MD_SEQ						\n");
				sb.append("      AND A.MD_SEQ = " + (String) arr_mdSeq.get(i) + "	\n");
				sb.append(" )													\n");

				insertNum = "";
				sg_seq = "";

				if (stmt.executeUpdate(sb.toString()) > 0) {
					sb = new StringBuffer();
					sb.append(
							" SELECT ID_SEQ, (SELECT B.IC_CODE																				  \n");
					sb.append(
							"                   FROM IC_S_RELATION A																			  \n");
					sb.append(
							"                      , ISSUE_CODE B 																			  \n");
					sb.append(
							"                  WHERE A.IC_SEQ = B.IC_SEQ																		  \n");
					sb.append("                    AND A.S_SEQ = SG_SEQ) AS SG_SEQ FROM ISSUE_DATA WHERE MD_SEQ = "
							+ (String) arr_mdSeq.get(i) + "    \n");
					rs = stmt.executeQuery(sb.toString());
					if (rs.next()) {
						insertNum = rs.getString("ID_SEQ");
						sg_seq = rs.getString("SG_SEQ");
					}

					sb = new StringBuffer();
					sb.append(" UPDATE IDX SET ISSUE_CHECK ='Y'                        \n");
					sb.append(" WHERE MD_SEQ = " + (String) arr_mdSeq.get(i) + "            \n");
					stmt.executeUpdate(sb.toString());

				}

				if (icBean != null && !insertNum.equals("")) {
					sb = new StringBuffer();
					sb.append(" INSERT INTO ISSUE_COMMENT(ID_SEQ                        \n");
					sb.append("                           ,IM_DATE)                     \n");
					sb.append("                           ,IM_COMMENT                   \n");
					sb.append("                           )                             \n");
					sb.append(" VALUES(                                                 \n");
					sb.append("        " + insertNum + "                                    \n");
					sb.append("        ,'" + du.getCurrentDate("yyyy-MM-dd HH:mm:ss") + "'  \n");
					sb.append("        ," + icBean.getIm_comment() + "                      \n");
					sb.append(" )                                                       \n");

					stmt.executeUpdate(sb.toString());
				}

				if (typeCode != null && !typeCode.equals("") && !insertNum.equals("")) {

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
						if (!arrTypeCode[0].equals("6")) {
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

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return insertNum;
	}

	public String updateMdPseqs(String idSeqs) {

		String mdPseqs = "";
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();
			sb.append("SELECT MD_PSEQ 			\n");
			sb.append("  FROM ISSUE_DATA 			\n");
			sb.append(" WHERE ID_SEQ IN (" + idSeqs + ")		\n");
			sb.append("	ORDER BY MD_PSEQ ASC		\n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				mdPseqs += "," + rs.getString("MD_PSEQ");
			}
			mdPseqs = mdPseqs.substring(1);
			System.out.println("mdPseqs : " + mdPseqs);

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return mdPseqs;
	}

	// 주요 이슈 데이터
	public ArrayList MajorIssueReport(String ir_sdate, String ir_stime, String ir_edate, String ir_etime,
			Long diff_date, String ic_codes) {

		ArrayList arrResult = new ArrayList();
		ArrayList arrResult2 = new ArrayList();
		String[] result = null;
		String[] result2 = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append(
					" SELECT date_format(AA.MD_DATE, '%Y-%m-%d') AS MD_DATE																		\n");
			sb.append(
					"     , IFNULL(BB.CNT1, 0) AS MEDIA																							\n");
			sb.append(
					"     , IFNULL(BB.CNT2, 0) AS TWEET																							\n");
			sb.append(
					"     , IFNULL(BB.CNT3, 0) AS FACEBOOK																						\n");
			sb.append(
					"     , IFNULL(BB.CNT4, 0) AS BLOG																   						\n");
			sb.append(
					"     , IFNULL(BB.CNT5, 0) AS CAFE_COMMUNITY															   		 				\n");
			sb.append(
					"     , IFNULL(BB.CNT6, 0) AS ETC																								\n");
			sb.append(
					"     , IFNULL(CNT, 0) AS TOTAL																								\n");
			sb.append("     FROM ( SELECT date_format(date_add('" + ir_edate
					+ "', interval @ROW:=@ROW-1 DAY),'%Y-%m-%d') AS MD_DATE 					\n");
			sb.append(
					" 		FROM KEYWORD A, (SELECT @ROW:= 1)R																					\n");
			sb.append("     	ORDER BY MD_DATE DESC LIMIT " + diff_date
					+ " ) AA 																		\n");
			sb.append(
					" 	LEFT OUTER JOIN																											\n");
			sb.append(
					"  	(SELECT																													\n");
			sb.append(
					"   			DATE_FORMAT(A.MD_DATE, '%Y-%m-%d') AS MD_DATE 																	\n");
			sb.append(
					"  			,SUM(IF(B.IC_CODE = 1 ,1 ,0) ) AS CNT1																			\n");
			sb.append(
					"  			,SUM(IF(B.IC_CODE = 2 ,1 ,0) ) AS CNT2																			\n");
			sb.append(
					"  			,SUM(IF(B.IC_CODE = 3 ,1 ,0) ) AS CNT3																			\n");
			sb.append(
					"  			,SUM(IF(B.IC_CODE = 4 ,1 ,0) ) AS CNT4																			\n");
			sb.append(
					"  			,SUM(IF(B.IC_CODE = 5 ,1 ,0) ) AS CNT5																			\n");
			sb.append(
					"  			,SUM(IF(B.IC_CODE = 6 ,1 ,0) ) AS CNT6																			\n");
			sb.append(
					"  			,COUNT(B.IC_CODE) AS CNT																						\n");
			sb.append(
					"  		FROM ISSUE_DATA A																									\n");
			sb.append(
					"  			,ISSUE_DATA_CODE B  																							\n");
			sb.append(
					"  			,ISSUE_DATA_CODE C  																							\n");
			sb.append("  			WHERE A.MD_DATE BETWEEN '" + ir_sdate + " " + ir_stime + "' AND '" + ir_edate + " "
					+ ir_etime + "'						\n");
			sb.append(
					"  			AND A.ID_SEQ = B.ID_SEQ  																						\n");
			sb.append(
					"  			AND A.ID_SEQ = C.ID_SEQ  																						\n");
			sb.append(
					"  			AND B.IC_TYPE = 6  																								\n");
			sb.append(
					"  			AND C.IC_TYPE = 19 																								\n");
			if (!ic_codes.equals(""))
				sb.append("  			AND C.IC_CODE IN (" + ic_codes
						+ ") 																				\n");
			sb.append(
					"  			GROUP BY DATE_FORMAT(A.MD_DATE, '%Y-%m-%d')  																	\n");
			sb.append(
					"  			) BB 				  																							\n");
			sb.append(
					"  			ON AA.MD_DATE = BB.MD_DATE   																					\n");
			sb.append(
					"  			ORDER BY MD_DATE ASC  																							\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {

				result = new String[8];

				result[0] = rs.getString("MD_DATE");
				result[1] = rs.getString("MEDIA");
				result[2] = rs.getString("BLOG");
				result[3] = rs.getString("TWEET");
				result[4] = rs.getString("FACEBOOK");
				result[5] = rs.getString("CAFE_COMMUNITY");
				result[6] = rs.getString("ETC");
				result[7] = rs.getString("TOTAL");
				arrResult.add(result);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	// SK 일일보고서 데이터
	public ArrayList DailyIssueReport(String id_seqs, String types) {

		ArrayList arrResult = new ArrayList();
		String site_name = "";
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append(
					" SELECT																								\n");
			sb.append(
					"       A.MD_SITE_NAME																				\n");
			sb.append(
					"     , A.S_SEQ																						\n");
			sb.append(
					"     , A.ID_TITLE																					\n");
			sb.append(
					"     , A.ID_URL																						\n");
			sb.append(
					"     , date_format(A.MD_DATE, '%y/%m/%d') AS MD_DATE													\n");
			sb.append(
					"     , IFNULL(IF(C.IC_CODE =1,'긍정',IF(C.IC_CODE=2,'부정',IF(C.IC_CODE=3,'중립',''))),'') AS TREND 		\n");
			sb.append(
					"     , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 16) AS IMPACT												\n");
			sb.append(
					"     , A.MD_SAME_CT																					\n");
			sb.append(
					"     FROM  ISSUE_DATA	A																			\n");
			sb.append(
					"     	 ,ISSUE_DATA_CODE_DETAIL C																	\n");
			sb.append(
					"     WHERE 1=1																						\n");
			sb.append(
					"     AND A.ID_SEQ = C.ID_SEQ																			\n");
			sb.append(
					"     AND A.ID_REPORTYN ='Y'																			\n");
			sb.append(
					"     AND C.IC_TYPE = 9																				\n");
			sb.append("     AND C.IC_PTYPE IN (" + types
					+ ")																	\n");
			if (!id_seqs.equals("")) {
				sb.append(" 	AND A.ID_SEQ IN (" + id_seqs
						+ ")																\n");
			}
			sb.append(
					" 	GROUP BY C.ID_SEQ																				\n");
			sb.append(
					" 	ORDER BY A.MD_DATE 																				\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {

				idBean = new IssueDataBean();
				// idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				site_name = "";
				if (rs.getString("MD_SITE_NAME") != null && rs.getString("MD_SITE_NAME").length() > 0) {
					if (rs.getString("S_SEQ").equals("2196")) { // 네이버
						site_name = "네이버_" + rs.getString("MD_SITE_NAME").substring(1);
					} else if (rs.getString("S_SEQ").equals("2199")) { // 다음
						site_name = "다음_" + rs.getString("MD_SITE_NAME").substring(1);
					} else if (rs.getString("S_SEQ").equals("3883")) { // 네이트
						site_name = "네이트_" + rs.getString("MD_SITE_NAME").substring(1);
					} else {
						site_name = rs.getString("MD_SITE_NAME");
					}
				}
				idBean.setMd_site_name(site_name);
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setTemp1(rs.getString("TREND"));
				idBean.setTemp2(rs.getString("IMPACT"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				arrResult.add(idBean);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	// SK 종합 일일보고서 데이터
	public ArrayList getIntegratedDailyIssueReport(String id_seqs, String types, String section) {

		ArrayList arrResult = new ArrayList();
		String site_name = "";
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append(
					" SELECT 																								\n");
			sb.append("  		'" + section
					+ "' AS SECTION																	\n");
			sb.append(
					" 		, MD_SITE_NAME																				\n");
			sb.append(
					" 		, S_SEQ																						\n");
			sb.append(
					" 		, ID_TITLE																					\n");
			sb.append(
					" 		, ID_URL																					\n");
			sb.append(
					" 		, date_format(MD_DATE, '%y/%m/%d') AS MD_DATE												\n");
			sb.append(
					" 		, IF(B.IC_CODE = 1,'긍정',IF(B.IC_CODE = 2, '부정', IF(B.IC_CODE = 3, '중립', ''))) AS TREND	\n");
			sb.append(
					" 		, FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 16) AS IMPACT											\n");
			sb.append(
					" 		, A.MD_SAME_CT																				\n");
			sb.append(
					" 		FROM ISSUE_DATA A																			\n");
			sb.append(
					" 			,ISSUE_DATA_CODE_DETAIL B																\n");
			sb.append(
					" 		WHERE A.ID_SEQ = B.ID_SEQ																	\n");
			sb.append(
					" 		AND A.ID_REPORTYN ='Y'																		\n");
			if (!id_seqs.equals(""))
				sb.append(" 		AND A.ID_SEQ IN (" + id_seqs
						+ ")																\n");
			sb.append("     	AND B.IC_PTYPE IN (" + types
					+ ")																\n");
			sb.append(
					"     	AND B.IC_TYPE = 9																			\n");
			sb.append(
					"     	GROUP BY B.ID_SEQ																			\n");
			sb.append(
					"     	ORDER BY MD_DATE	 																		\n");

			System.out.println(sb.toString());
			stmt = dbconn.createStatement();
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {

				idBean = new IssueDataBean();
				idBean.setTemp3(rs.getString("SECTION")); // 구분
				site_name = "";
				if (rs.getString("MD_SITE_NAME") != null && rs.getString("MD_SITE_NAME").length() > 0) {
					if (rs.getString("S_SEQ").equals("2196")) { // 네이버
						site_name = "네이버_" + rs.getString("MD_SITE_NAME").substring(1);
					} else if (rs.getString("S_SEQ").equals("2199")) { // 다음
						site_name = "다음_" + rs.getString("MD_SITE_NAME").substring(1);
					} else if (rs.getString("S_SEQ").equals("3883")) { // 네이트
						site_name = "네이트_" + rs.getString("MD_SITE_NAME").substring(1);
					} else {
						site_name = rs.getString("MD_SITE_NAME");
					}
				}
				idBean.setMd_site_name(site_name);
				// idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setTemp1(rs.getString("TREND"));
				idBean.setTemp2(rs.getString("IMPACT"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				arrResult.add(idBean);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	/*
	 * SK 정보량 현황 구하는 메소드
	 */
	public ArrayList getIRDataChart_company(String sDate, String sTime, String eDate, String eTime) {

		ArrayList arrResult = new ArrayList();
		HashMap dataMap = new HashMap();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
//			sb.append(" SELECT IFNULL(SUM(A.CNT),0) AS CNT												\n");
//			sb.append(" 		,IFNULL(SUM(A.POS),0) AS POS											\n");
//			sb.append(" 		,IFNULL(SUM(A.NEG),0) AS NEG											\n");
//			sb.append(" 		,IFNULL(SUM(A.NEU),0) AS NEU											\n");
//			sb.append(" 	FROM 																		\n");
//			sb.append(" 	(																			\n");
//			sb.append(" 		SELECT 1 AS CNT															\n");
//			sb.append(" 			,IF(IDCD.IC_CODE = 1,1,0) AS POS									\n");
//			sb.append(" 			,IF(IDCD.IC_CODE = 2,1,0) AS NEG									\n");
//			sb.append(" 			,IF(IDCD.IC_CODE = 3,1,0) AS NEU									\n");
//			sb.append(" 		FROM ISSUE_DATA ID														\n");
//			sb.append(" 			,ISSUE_DATA_CODE_DETAIL IDCD										\n");
//			sb.append(" 			,ISSUE_CODE IC														\n");
//			sb.append(" 	WHERE MD_DATE BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"'		\n");
//			sb.append(" 	AND ID.ID_SEQ = IDCD.ID_SEQ													\n");
//			sb.append(" 	AND IDCD.IC_TYPE = 9														\n");
//			sb.append(" 	AND IDCD.IC_PTYPE = IC.IC_TYPE												\n");
//			sb.append(" 	AND IC.IC_PTYPE = "+ic_type+"												\n");
//			sb.append(" 	GROUP BY IDCD.ID_SEQ, IDCD.IC_TYPE, IDCD.IC_PTYPE							\n");
//			sb.append(" 	 ) A 																		\n");

			sb.append(" # 자사 정보량									    									\n");
			sb.append(" SELECT SUM(A.POS)+SUM(A.NEG)+SUM(A.NEU) AS CNT    									\n");
			sb.append("       ,SUM(A.POS) AS POS                                                            \n");
			sb.append("       ,SUM(A.NEG) AS NEG                                                            \n");
			sb.append("       ,SUM(A.NEU) AS NEU                                                            \n");
			sb.append("   FROM (                                                                            \n");
			sb.append("     SELECT IF(IDCD.IC_CODE = 1, COUNT(0), 0) AS POS                                 \n");
			sb.append("           ,IF(IDCD.IC_CODE = 2, COUNT(0), 0) AS NEG                                 \n");
			sb.append("           ,IF(IDCD.IC_CODE = 3, COUNT(0), 0) AS NEU                                 \n");
			sb.append("     FROM ISSUE_DATA ID                                                              \n");
			sb.append("         ,ISSUE_DATA_CODE_DETAIL IDCD                                                \n");
			sb.append("     WHERE ID.ID_SEQ = IDCD.ID_SEQ                                                   \n");
			sb.append("       AND ID.MD_DATE BETWEEN '" + sDate + " " + sTime + "' AND '" + eDate + " " + eTime
					+ "'		\n");
			sb.append("       AND IDCD.IC_TYPE = 9                                                          \n");
			sb.append("     GROUP BY IDCD.IC_CODE                                                           \n");
			sb.append("   ) A                                                                               \n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			if (rs.next()) {

				dataMap = new HashMap();
				dataMap.put("CATEGORY", "총합");
				dataMap.put("CNT", rs.getInt("CNT"));
				arrResult.add(dataMap);

				dataMap = new HashMap();
				dataMap.put("CATEGORY", "긍정");
				dataMap.put("CNT", rs.getInt("POS"));
				arrResult.add(dataMap);

				dataMap = new HashMap();
				dataMap.put("CATEGORY", "부정");
				dataMap.put("CNT", rs.getInt("NEG"));
				arrResult.add(dataMap);

				dataMap = new HashMap();
				dataMap.put("CATEGORY", "중립");
				dataMap.put("CNT", rs.getInt("NEU"));
				arrResult.add(dataMap);

			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	/*
	 * SK 정보량 현황 구하는 메소드
	 */
	public ArrayList getIRDataChart1(ArrayList arrData) {

		ArrayList arrResult = new ArrayList();
		HashMap dataMap = new HashMap();
		Iterator it = null;
		boolean dataChk = false;
		try {

			int total = 0;
			int pos = 0;
			int neg = 0;
			int neu = 0;

			if (arrData != null) {
				dataChk = true;
				it = arrData.iterator();
				while (it.hasNext()) {
					dataMap = new HashMap();
					dataMap = (HashMap) it.next();

					if (((String) dataMap.get("CATEGORY")).equals("총합") && (Integer) dataMap.get("CNT") != null) {
						total += (Integer) dataMap.get("CNT");
					}
					if (((String) dataMap.get("CATEGORY")).equals("긍정") && (Integer) dataMap.get("CNT") != null) {
						pos += (Integer) dataMap.get("CNT");
					}
					if (((String) dataMap.get("CATEGORY")).equals("부정") && (Integer) dataMap.get("CNT") != null) {
						neg += (Integer) dataMap.get("CNT");
					}
					if (((String) dataMap.get("CATEGORY")).equals("중립") && (Integer) dataMap.get("CNT") != null) {
						neu += (Integer) dataMap.get("CNT");
					}
				}
			}

			if (dataChk) {

				dataMap = new HashMap();
				dataMap.put("CATEGORY", "총합");
				dataMap.put("CNT", total);
				arrResult.add(dataMap);

				dataMap = new HashMap();
				dataMap.put("CATEGORY", "긍정");
				dataMap.put("CNT", pos);
				arrResult.add(dataMap);

				dataMap = new HashMap();
				dataMap.put("CATEGORY", "부정");
				dataMap.put("CNT", neg);
				arrResult.add(dataMap);

				dataMap = new HashMap();
				dataMap.put("CATEGORY", "중립");
				dataMap.put("CNT", neu);
				arrResult.add(dataMap);

			}
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {

		}
		return arrResult;
	}

	/*
	 * SK그룹 차트2 날짜별 정보량 TM, SK그룹, 관계사 들만
	 * 
	 */
	public ArrayList getIRDataChart_Date_cnt(String ir_sdate, String ir_stime, String ir_edate, String ir_etime,
			int diff_sch, String a_month_ago, int diff_avg) {

		ArrayList arrResult = new ArrayList();
		HashMap dataMap = new HashMap();
		int avg = 0;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();
			sb.append(
					" ##날짜별 정보량 구하기 																												\n");
			sb.append(
					"  SELECT A.MD_DATE, IFNULL(B.CNT, 0) AS CNT, C.AVG																			\n");
			sb.append(
					"    FROM (                                                                                                                   \n");
			sb.append("         SELECT 1 AS PK, DATE_FORMAT(DATE_ADD('" + ir_edate
					+ "', interval @ROW:=@ROW-1 DAY),'%Y-%m-%d') AS MD_DATE 				\n");
			sb.append("  		      FROM KEYWORD A, (SELECT @ROW:= 1)R ORDER BY MD_DATE DESC LIMIT " + diff_sch
					+ " 		                            \n");
			sb.append(
					"       ) A LEFT OUTER JOIN (                                                                                                 \n");
			sb.append(
					"         SELECT DATE_FORMAT(ID.MD_DATE, '%Y-%m-%d') AS MD_DATE, COUNT(0) AS CNT                                              \n");
			sb.append(
					"         FROM ISSUE_DATA ID                                                                                                  \n");
			sb.append(
					"             ,ISSUE_DATA_CODE_DETAIL IDCD                                                                                    \n");
			sb.append(
					"         WHERE ID.ID_SEQ = IDCD.ID_SEQ                                                                                       \n");
			sb.append("           AND ID.MD_DATE BETWEEN '" + ir_sdate + " " + ir_stime + "' AND '" + ir_edate + " "
					+ ir_etime + "'					            \n");
			sb.append(
					"           AND IDCD.IC_TYPE = 9                                                                                              \n");
			sb.append(
					"         GROUP BY DATE_FORMAT(ID.MD_DATE, '%Y-%m-%d')                                                                        \n");
			sb.append(
					"       ) B ON A.MD_DATE = B.MD_DATE INNER JOIN (                                                                             \n");
			sb.append("         SELECT 1 AS PK, ROUND(COUNT(0) / " + diff_avg
					+ ") AS AVG                                                               \n");
			sb.append(
					"           FROM ISSUE_DATA ID                                                                                                \n");
			sb.append(
					"               ,ISSUE_DATA_CODE_DETAIL IDCD                                                                                  \n");
			sb.append(
					"          WHERE ID.ID_SEQ = IDCD.ID_SEQ                                                                                      \n");
			sb.append("            AND ID.MD_DATE BETWEEN '" + a_month_ago + " " + ir_stime + "' AND '" + ir_edate + " "
					+ ir_etime + "'					        \n");
			sb.append(
					"            AND IDCD.IC_TYPE = 9                                                                                             \n");
			sb.append(
					"       ) C ON A.PK = C.PK                                                                                                    \n");
			sb.append(
					"    ORDER BY MD_DATE                                                                                                         \n");
			System.out.println(sb.toString());
			pstmt = null;
			rs = null;
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dataMap = new HashMap();
				dataMap.put("CATEGORY", rs.getString("MD_DATE"));
				dataMap.put("CNT", rs.getString("CNT"));
				dataMap.put("AVG", rs.getString("AVG"));
				arrResult.add(dataMap);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	/*
	 * SK 구분별 성향별 현황 구하는 메소드
	 */
	public HashMap getIRDataChart_part(String type, String ir_sdate, String ir_stime, String ir_edate, String ir_etime,
			String category) {

		// ArrayList arrResult = new ArrayList();
		HashMap dataMap = new HashMap();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("    SELECT SUM(A.POS) AS POS														\n");
			sb.append("          ,SUM(A.NEG) AS NEG                                                     \n");
			sb.append("          ,SUM(A.NEU) AS NEU                                                     \n");
			sb.append("    FROM (                                                                       \n");
			sb.append("      SELECT IF(IDCD.IC_CODE = 1, COUNT(0), 0) AS POS                            \n");
			sb.append("            ,IF(IDCD.IC_CODE = 2, COUNT(0), 0) AS NEG                            \n");
			sb.append("            ,IF(IDCD.IC_CODE = 3, COUNT(0), 0) AS NEU                            \n");
			sb.append("      FROM ISSUE_DATA ID                                                         \n");
			sb.append("          ,ISSUE_DATA_CODE_DETAIL IDCD                                           \n");
			sb.append("      WHERE ID.ID_SEQ = IDCD.ID_SEQ                                              \n");
			sb.append("        AND ID.MD_DATE BETWEEN '" + ir_sdate + " " + ir_stime + "' AND '" + ir_edate + " "
					+ ir_etime + "'	\n");
			sb.append("        AND IDCD.IC_TYPE = 9                                                     \n");
			sb.append("        AND IDCD.IC_PTYPE IN (" + type + ")                                          \n");
			sb.append("      GROUP BY IDCD.IC_CODE                                                      \n");
			sb.append("    ) A                                                                          \n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			if (rs.next()) {

				dataMap = new HashMap();
				dataMap.put("CATEGORY", category);
				dataMap.put("POS", rs.getString("POS"));
				dataMap.put("NEG", rs.getString("NEG"));
				dataMap.put("NEU", rs.getString("NEU"));

			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return dataMap;
	}

	/*
	 * 출처별 성향별 정보량
	 */
	public ArrayList getIRDataChart4(String sDate, String sTime, String eDate, String eTime) {

		ArrayList arrResult = new ArrayList();
		HashMap dataMap = new HashMap();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append(
					"     SELECT A.IC_NAME																						\n");
			sb.append(
					"           ,IFNULL(SUM(B.POS), 0) AS POS                                                                  \n");
			sb.append(
					"           ,IFNULL(SUM(B.NEG), 0) AS NEG                                                                  \n");
			sb.append(
					"           ,IFNULL(SUM(B.NEU), 0) AS NEU                                                                  \n");
			sb.append(
					"      FROM (                                                                                              \n");
			sb.append(
					"       SELECT IC_NAME, IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_CODE > 0 AND IC_USEYN = 'Y'       \n");
			sb.append(
					"       ) A LEFT OUTER JOIN (                                                                              \n");
			sb.append(
					"       SELECT IDC.IC_CODE                                                                                 \n");
			sb.append(
					"             ,IF(IDCD.IC_CODE = 1, COUNT(0), 0) AS POS                                                    \n");
			sb.append(
					"             ,IF(IDCD.IC_CODE = 2, COUNT(0), 0) AS NEG                                                    \n");
			sb.append(
					"             ,IF(IDCD.IC_CODE = 3, COUNT(0), 0) AS NEU                                                    \n");
			sb.append(
					"         FROM ISSUE_DATA ID                                                                               \n");
			sb.append(
					"            , ISSUE_DATA_CODE IDC                                                                         \n");
			sb.append(
					"            , ISSUE_DATA_CODE_DETAIL IDCD                                                                 \n");
			sb.append("        WHERE ID.MD_DATE BETWEEN '" + sDate + " " + sTime + "' AND '" + eDate + " " + eTime
					+ "'	                    \n");
			sb.append(
					"          AND ID.ID_SEQ = IDC.ID_SEQ                                                                      \n");
			sb.append(
					"          AND ID.ID_SEQ = IDCD.ID_SEQ                                                                     \n");
			sb.append(
					"          AND IDC.IC_TYPE = 6                                                                             \n");
			sb.append(
					"          AND IDCD.IC_TYPE = 9                                                                            \n");
			sb.append(
					"        GROUP BY IDC.IC_CODE, IDCD.IC_CODE                                                                \n");
			sb.append(
					"      ) B ON A.IC_CODE = B.IC_CODE                                                                        \n");
			sb.append(
					"      GROUP BY A.IC_CODE                                                                                  \n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {

				dataMap = new HashMap();
				if (rs.getString("IC_NAME").indexOf("/") > -1) {
					dataMap.put("CATEGORY", rs.getString("IC_NAME").split("/")[1]);
				} else {
					dataMap.put("CATEGORY", rs.getString("IC_NAME"));
				}
				dataMap.put("POS", rs.getString("POS"));
				dataMap.put("NEG", rs.getString("NEG"));
				dataMap.put("NEU", rs.getString("NEU"));

				arrResult.add(dataMap);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	/*
	 * param: id_seq ISSUE_DATA_CODE 값 전체 ArrayList
	 * 
	 */
	public ArrayList getTypeCodes(String id_seq) {

		ArrayList arrResult = new ArrayList();
		IssueCodeBean icb;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();

			sb.append(" SELECT IC_TYPE, IC_CODE 	\n");
			sb.append(" FROM ISSUE_DATA_CODE	 	\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {

				icb = new IssueCodeBean();
				icb.setIc_type(rs.getInt("IC_TYPE"));
				icb.setIc_code(rs.getInt("IC_CODE"));

				arrResult.add(icb);

			}
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	/**
	 * 과거 성향 등록
	 * 
	 * @param ArrayList     과거성향
	 * @param IssueDataBean 이슈데이터빈
	 * @return true or false
	 */
	public boolean insertPastTrendData(IssueDataBean idBean, ArrayList arrPastTrend) {

		IssueCodeBean icb = null;
		String[] arrTypeCode = null;
		String[] arrTemp = null;
		boolean result = false;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			for (int i = 0; i < arrPastTrend.size(); i++) {

				icb = (IssueCodeBean) arrPastTrend.get(i);
				String md_seq = String.valueOf(icb.getMd_seq());
				if (md_seq.equals(idBean.getMd_seq())) {
					sb = new StringBuffer();
					sb.append("INSERT INTO ISSUE_DATA_CODE_DETAIL	 	\n");
					sb.append("VALUES ( 								\n");
					sb.append("        " + idBean.getId_seq() + "		\n");
					sb.append("        ,20								\n");
					sb.append("        ," + icb.getIc_code() + "  		\n");
					sb.append("        ," + icb.getIc_ptype() + "  		\n");
					sb.append("        ," + icb.getIc_pcode() + "		\n");
					sb.append("        ) 								\n");
					System.out.println(sb.toString());
					if (stmt.executeUpdate(sb.toString()) > 0)
						result = true;
				}
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

	/**
	 * 과거 성향 등록
	 * 
	 * @param String           모드
	 * @param String           이슈번호
	 * @param IssueCommentBean 이슈코드빈
	 * @param 각                구분 별 성향
	 * @return true or false
	 */
	public boolean insert_Multi_PastTrendData(String md_seq, ArrayList arrPastTrend) {

		String id_seq = "";
		IssueDataBean idBean = null;
		ArrayList arrTemp = new ArrayList();
		IssueCodeBean icb = null;
		String[] arrTypeCode = null;
		boolean result = false;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			rs = null;

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			sb = null;
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

	// written by 고범준
	// modified by 정천웅
	public ArrayList search(int pageNum, int rowCnt, String sDate, String eDate, String sTime, String eTime,
			String keyType /* 1:제목, 2:제목+내용 */
			, String keyword /* 키워드 */
			, String typeCode /* 분류체계 */
			, String typeCode_Etc /* 분류체계 */
			, String reportYN /* 보고서포함유무 */
			, String relationKeySeq /* 연관키워드 */
			, String register /* 이슈등록자 */
			, String order /* 정렬항목 */
			, String orderAlign /* 정렬순서 */
	) {

		ArrayList result = new ArrayList();

		ArrayList arTypeCode = ReassembleTypeCode(typeCode);
		ArrayList arTypeCode_Etc = ReassembleTypeCode_Etc(typeCode_Etc);
		String[] getTBean = null;

		// 리스트 시작, 끝번호
		if (rowCnt > 0)
			this.startNum = (pageNum - 1) * rowCnt;
		this.endNum = rowCnt;

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();

			sb.append(
					"SELECT COUNT(DISTINCT A.ID_SEQ) AS SAME_CNT													\n");
			sb.append(
					"     , COUNT(DISTINCT A.MD_PSEQ) AS CNT														\n");
			sb.append(
					"  FROM ISSUE_DATA A																			\n");

			// 세부조건검색
			for (int i = 0; i < arTypeCode.size(); i++) {
				getTBean = (String[]) arTypeCode.get(i);

				sb.append("			INNER JOIN ISSUE_DATA_CODE C" + (i + 1) + " ON A.ID_SEQ = C" + (i + 1)
						+ ".ID_SEQ 			\n");
				sb.append("                                        AND C" + (i + 1) + ".IC_TYPE = " + getTBean[0]
						+ "			\n");
				sb.append("                                        AND C" + (i + 1) + ".IC_CODE IN (" + getTBean[1]
						+ ")		\n");
			}

			// 성향, 정보속성 검색
			for (int i = 0; i < arTypeCode_Etc.size(); i++) {
				getTBean = (String[]) arTypeCode_Etc.get(i);
				sb.append("			INNER JOIN ISSUE_DATA_CODE_DETAIL D" + (i + 1) + " ON A.ID_SEQ = D" + (i + 1)
						+ ".ID_SEQ 								\n");
				sb.append("                                        AND D" + (i + 1) + ".IC_TYPE = " + getTBean[0]
						+ "										\n");
				sb.append("                                        AND D" + (i + 1) + ".IC_CODE IN (" + getTBean[1]
						+ ")									\n");
				sb.append("                                        AND D" + (i + 1) + ".IC_PTYPE IN (" + getTBean[2]
						+ ")									\n");
			}
			if (!relationKeySeq.equals("")) {
				sb.append(
						"			INNER JOIN RELATION_KEYWORD_MAP RKM ON A.ID_SEQ = RKM.ID_SEQ 														\n");
				sb.append("										AND RKM.RK_SEQ = " + relationKeySeq
						+ "												\n");
			}
			sb.append(
					"		INNER JOIN MEMBER M ON A.M_SEQ = M.M_SEQ																					\n");
			if (!register.equals("")) {
				sb.append("										AND M.M_SEQ = " + register
						+ "															\n");
			}
			sb.append(" WHERE A.MD_DATE BETWEEN '" + sDate + " " + sTime + "' AND '" + eDate + " " + eTime
					+ "'											\n");
			if (!reportYN.equals("")) {
				sb.append(" AND A.ID_REPORTYN = '" + reportYN
						+ "'																\n");
			}
			// 키워드 조건 주기(AND검색)
			if (!keyword.equals("")) {
				String[] arKey = keyword.split(" ");

				for (int i = 0; i < arKey.length; i++) {
					if (!arKey[i].trim().equals("")) {
						if (keyType.equals("1")) {
							// 제목검색
							sb.append("AND ID_TITLE LIKE '%" + arKey[i] + "%' \n");

						} else if (keyType.equals("2")) {
							// 제목+내용검색
							sb.append("AND CONCAT(ID_TITLE,ID_CONTENT) LIKE '%" + arKey[i] + "%'\n");
						}
					}
				}
			}

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			if (rs.next()) {
				totalSameDataCnt = rs.getInt("SAME_CNT");
				totalIssueDataCnt = rs.getInt("CNT");
			}

			rs.close();
			sb = new StringBuffer();

			// sb.append("SELECT A.*, IFNULL(B.V_SEQ,'') AS V_SEQ FROM ( \n");

			sb.append(
					"SELECT A.* 																								\n");
			sb.append(
					"     , FN_GET_ISSUE_CODE(A.ID_SEQ,9) AS TYPE9															\n");
			sb.append(
					"     , FN_ISSUE_NAME_ID_SEQ(A.ID_SEQ,10) AS TYPE10														\n");
			sb.append("     , FN_GET_SITE_SAME(A.MD_PSEQ,'" + sDate + " " + sTime + "','" + eDate + " " + eTime
					+ "') AS MD_SAME_CT 			\n");
			sb.append("     , FN_GET_SITE_SAME_CHECK(A.MD_PSEQ,'" + sDate + " " + sTime + "','" + eDate + " " + eTime
					+ "') AS MD_SAME_CT_CHECK	\n");
			// sb.append(" , IFNULL((SELECT V_SEQ FROM CONNECT_VOC WHERE ID_SEQ = A.ID_SEQ
			// LIMIT 1),'') AS V_SEQ \n");
			sb.append(
					"  FROM (																									\n");
			sb.append(
					"        SELECT A.*																						\n");
			sb.append("          FROM ( 										\n");
			sb.append("                SELECT 									\n");
			sb.append("                       distinct(A.ID_SEQ) AS ID_SEQ		\n");
			sb.append("                     , A.MD_SEQ							\n");
			sb.append("                     , A.MD_PSEQ							\n");
			sb.append("                     , A.MD_TYPE							\n");
			sb.append("                     , A.S_SEQ							\n");
			sb.append("                     , A.SG_SEQ							\n");
			sb.append("                     , A.MD_DATE							\n");
			sb.append("                     , A.MD_SITE_NAME					\n");
			sb.append("                     , A.ID_TITLE						\n");
			sb.append("                     , A.ID_URL							\n");
			sb.append("                     , A.ID_CONTENT						\n");
			sb.append("                     , M.M_NAME							\n");
			sb.append("                  FROM ISSUE_DATA A						\n");

			// 세부조건검색
			for (int i = 0; i < arTypeCode.size(); i++) {
				getTBean = (String[]) arTypeCode.get(i);
				sb.append("			INNER JOIN ISSUE_DATA_CODE C" + (i + 1) + " ON A.ID_SEQ = C" + (i + 1)
						+ ".ID_SEQ 										\n");
				sb.append("                                        AND C" + (i + 1) + ".IC_TYPE = " + getTBean[0]
						+ "										\n");
				sb.append("                                        AND C" + (i + 1) + ".IC_CODE IN (" + getTBean[1]
						+ ")									\n");
			}

			// 성향, 정보속성 검색
			for (int i = 0; i < arTypeCode_Etc.size(); i++) {
				getTBean = (String[]) arTypeCode_Etc.get(i);
				sb.append("			INNER JOIN ISSUE_DATA_CODE_DETAIL D" + (i + 1) + " ON A.ID_SEQ = D" + (i + 1)
						+ ".ID_SEQ 								\n");
				sb.append("                                        AND D" + (i + 1) + ".IC_TYPE = " + getTBean[0]
						+ "										\n");
				sb.append("                                        AND D" + (i + 1) + ".IC_CODE IN (" + getTBean[1]
						+ ")									\n");
				sb.append("                                        AND D" + (i + 1) + ".IC_PTYPE IN (" + getTBean[2]
						+ ")									\n");
			}
			if (!relationKeySeq.equals("")) {
				sb.append(
						"			INNER JOIN RELATION_KEYWORD_MAP RKM ON A.ID_SEQ = RKM.ID_SEQ 														\n");
				sb.append("										AND RKM.RK_SEQ = " + relationKeySeq
						+ "													\n");
			}
			sb.append(
					"		INNER JOIN MEMBER M ON A.M_SEQ = M.M_SEQ																					\n");
			if (!register.equals("")) {
				sb.append("		AND M.M_SEQ = " + register
						+ "															\n");
			}
			sb.append("                 WHERE A.MD_DATE BETWEEN '" + sDate + " " + sTime + "' AND '" + eDate + " "
					+ eTime + "'											\n");
			if (!reportYN.equals("")) {
				sb.append(" AND A.ID_REPORTYN = '" + reportYN
						+ "'																								\n");
			}
			// 키워드 조건 주기(AND검색)
			if (!keyword.equals("")) {
				String[] arKey = keyword.split(" ");

				for (int i = 0; i < arKey.length; i++) {
					if (!arKey[i].trim().equals("")) {
						if (keyType.equals("1")) {
							// 제목검색
							sb.append("AND ID_TITLE LIKE '%" + arKey[i] + "%' \n");

						} else if (keyType.equals("2")) {
							// 제목+내용검색
							sb.append("AND CONCAT(ID_TITLE,ID_CONTENT) LIKE '%" + arKey[i] + "%'	\n");
						}
					}
				}
			}
			sb.append("         	GROUP BY MD_PSEQ									\n");
			sb.append("       		ORDER BY " + order + " " + orderAlign + "				\n");
			sb.append("               ) A										\n");
			sb.append("         LIMIT " + startNum + "," + endNum + "					\n");
			sb.append("       )A												\n");

			// sb.append(") A LEFT OUTER JOIN CONNECT_VOC B ON A.ID_SEQ = B.ID_SEQ \n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setTemp1(rs.getString("TYPE9"));
				idBean.setTemp2(rs.getString("TYPE10"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				idBean.setMd_same_ct_check(rs.getString("MD_SAME_CT_CHECK"));
				// idBean.setV_seq(rs.getString("V_SEQ"));
				idBean.setM_name(rs.getString("M_NAME"));
				idBean.setId_content(rs.getString("ID_CONTENT"));
				result.add(idBean);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;

	}
	///////// 테스트 끝

	public ArrayList searchIssueData(int pageNum, int rowCnt, String sDate, String sTime, String eDate, String eTime,
			String typeCode, String cloutCode, String sentiCode, String infoCode, String relKeywordCode, String searchKeyType,
			String searchKeyword, String register, String order, String orderAlign, String sg_seqs, String transSeries) {
		ArrayList result = new ArrayList();

		// 리스트 시작, 끝번호
		if (rowCnt > 0)
			this.startNum = (pageNum - 1) * rowCnt;
		this.endNum = rowCnt;

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();

			sb.append(
					"SELECT COUNT(DISTINCT A.ID_SEQ) AS SAME_CNT													\n");
			sb.append(
					"     , COUNT(DISTINCT A.MD_PSEQ) AS CNT														\n");
			sb.append(
					"  FROM ISSUE_DATA A																			\n");

			String[] arrTypeCode = null;
			String[] arrTypeCode2 = null;
			String str_typecode = "";

			// 각 분류체계
			if (!"".equals(typeCode)) {
				arrTypeCode = typeCode.split("@");
				for (int i = 0; i < arrTypeCode.length; i++) {
					str_typecode = arrTypeCode[i];
					if (!"".equals(str_typecode)) {
						arrTypeCode2 = str_typecode.split(",");
						sb.append("	INNER JOIN ISSUE_DATA_CODE IDC" + (i + 1) + " ON A.ID_SEQ = IDC" + (i + 1)
								+ ".ID_SEQ 			\n");
						sb.append("		AND  IDC" + (i + 1) + ".IC_TYPE = " + arrTypeCode2[0]
								+ "									\n");
						sb.append("		AND  IDC" + (i + 1) + ".IC_CODE IN ( " + arrTypeCode2[1]
								+ " )							\n");
					}
				}
			}

			// 각 분류체계 별 성향
			/*
			 * if(!"".equals(sentiCode)){ arrTypeCode = null; str_typecode = ""; arrTypeCode
			 * = sentiCode.split("@"); for(int i=0; i < arrTypeCode.length; i++){
			 * str_typecode = arrTypeCode[i]; if(!"".equals(str_typecode)){ arrTypeCode2 =
			 * null; arrTypeCode2 = str_typecode.split(",");
			 * sb.append("	INNER JOIN ISSUE_DATA_CODE_DETAIL SENTI"+(i+1)
			 * +" ON A.ID_SEQ = SENTI"+(i+1)+".ID_SEQ 	\n");
			 * sb.append("      AND SENTI"+(i+1)+".IC_TYPE = "+arrTypeCode2[2]
			 * +"									\n");
			 * sb.append("      AND SENTI"+(i+1)+".IC_CODE IN ("+arrTypeCode2[3]
			 * +")								\n");
			 * sb.append("      AND SENTI"+(i+1)+".IC_PTYPE = "+arrTypeCode2[0]
			 * +"									\n");
			 * sb.append("      AND SENTI"+(i+1)+".IC_PCODE IN ("+arrTypeCode2[1]
			 * +")								\n"); } } }
			 */

			// 각 분류체계 별 정보유형
			/*
			 * if(!"".equals(infoCode)){ arrTypeCode = null; str_typecode = ""; arrTypeCode
			 * = infoCode.split("@"); for(int i=0; i < arrTypeCode.length; i++){
			 * str_typecode = arrTypeCode[i]; if(!"".equals(str_typecode)){ arrTypeCode2 =
			 * null; arrTypeCode2 = str_typecode.split(",");
			 * sb.append("	INNER JOIN ISSUE_DATA_CODE_DETAIL INFO"+(i+1)
			 * +" ON A.ID_SEQ = INFO"+(i+1)+".ID_SEQ 		\n");
			 * sb.append("      AND INFO"+(i+1)+".IC_TYPE = "+arrTypeCode2[2]
			 * +"									\n");
			 * sb.append("      AND INFO"+(i+1)+".IC_CODE IN ("+arrTypeCode2[3]
			 * +")									\n");
			 * sb.append("      AND INFO"+(i+1)+".IC_PTYPE = "+arrTypeCode2[0]
			 * +"									\n");
			 * sb.append("      AND INFO"+(i+1)+".IC_PCODE IN ("+arrTypeCode2[1]
			 * +")								\n"); } } }
			 */

			// 각 분류체계 별 연관키워드
			/*
			 * if(!"".equals(relKeywordCode)){ arrTypeCode = null; str_typecode = "";
			 * arrTypeCode = relKeywordCode.split("@"); for(int i=0; i < arrTypeCode.length;
			 * i++){ str_typecode = arrTypeCode[i]; if(!"".equals(str_typecode)){
			 * arrTypeCode2 = null; arrTypeCode2 = str_typecode.split(",");
			 * sb.append("	INNER JOIN RELATION_KEYWORD_MAP RKM"+(i+1)+" ON A.ID_SEQ = RKM"+
			 * (i+1)+".ID_SEQ 		\n");
			 * sb.append("      AND RKM"+(i+1)+".IC_TYPE = "+arrTypeCode2[0]
			 * +"									\n");
			 * sb.append("      AND RKM"+(i+1)+".IC_CODE IN ("+arrTypeCode2[1]
			 * +")								\n");
			 * sb.append("      AND RKM"+(i+1)+".RK_SEQ = "+arrTypeCode2[2]
			 * +"									\n"); } } }
			 */

			sb.append("	INNER JOIN MEMBER M ON A.M_SEQ = M.M_SEQ 			\n");
			if (!"".equals(register)) {
				sb.append("	AND M.M_SEQ = " + register + "						\n");
			}
			sb.append(" WHERE A.MD_DATE BETWEEN '" + sDate + " " + sTime + "' AND '" + eDate + " " + eTime
					+ "'							\n");
			if(!"".equals(sg_seqs)) {
			sb.append(" 	 AND SG_SEQ IN (" + sg_seqs
					+ ")																	\n");
			}
			if(!"".equals(transSeries)) {
			sb.append(" 	 AND A.ID_REPORTYN = '" + transSeries + "'						\n");
			}
			if (!searchKeyword.equals("")) {
				String[] arKey = searchKeyword.split(" ");
				for (int i = 0; i < arKey.length; i++) {
					if (!arKey[i].trim().equals("")) {
						if (searchKeyType.equals("1")) {
							// 제목검색
							sb.append("AND ID_TITLE LIKE '%" + arKey[i] + "%' \n");

						} else if (searchKeyType.equals("2")) {
							// 제목+내용검색
							sb.append("AND CONCAT(ID_TITLE,ID_CONTENT) LIKE '%" + arKey[i] + "%'\n");
						}
					}
				}
			}
			
			// 영향력 선택
			if (!"".equals(cloutCode)) {
				sb.append("AND ID_CLOUT = '" + cloutCode + "' \n");
			}
			// 성향 선택
			if (!"".equals(sentiCode)) {
				sb.append("AND ID_SENTI = '" + sentiCode + "' \n");
			}

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			if (rs.next()) {
				totalSameDataCnt = rs.getInt("SAME_CNT");
				totalIssueDataCnt = rs.getInt("CNT");
			}

			rs.close();

			sb = new StringBuffer();
			sb.append("	SELECT A.* 																								\n");
			sb.append("  , FN_GET_SITE_SAME2(A.MD_PSEQ,'"+sDate+" "+sTime+"','"+eDate+" "+eTime+"') AS MD_SAME_CT 				\n");
			sb.append("  , FN_GET_SITE_SAME_CHECK2(A.MD_PSEQ,'"+sDate+" "+sTime+"','"+eDate+" "+eTime+"') AS MD_SAME_CT_CHECK	\n");
			sb.append("  , IFNULL((SELECT 1 FROM ISSUE_REPORT WHERE IR_TYPE = 'U' AND ID_SEQ = A.ID_SEQ LIMIT 1),0) AS ISSUE_REPORT	\n");
			sb.append("  , IFNULL((SELECT 1 FROM ISSUE_REPORT WHERE IR_TYPE = 'OIS' AND ID_SEQ = A.ID_SEQ LIMIT 1),0) AS ONLINE_ISSUE_REPORT\n");
			sb.append(" FROM (										\n");
			sb.append(" 	SELECT A.*								\n");
			sb.append(" 	FROM									\n");
			sb.append(" 	(										\n");
			sb.append("  	SELECT 									\n");
			sb.append("    	   	DISTINCT(A.ID_SEQ) AS ID_SEQ		\n");
			sb.append("   	  	, A.MD_SEQ							\n");
			sb.append("   	  	, A.MD_PSEQ							\n");
			sb.append("  	   	, A.MD_TYPE							\n");
			sb.append("  	   	, A.S_SEQ							\n");
			sb.append(" 	   	, A.SG_SEQ							\n");	
			sb.append("  	   	, A.MD_DATE							\n");
			sb.append("  	   	, A.MD_SITE_NAME					\n");
			sb.append("  	   	, A.ID_SENTI						\n");
			sb.append("   	  	, A.ID_TITLE						\n");
			sb.append("   	  	, A.ID_URL							\n");
			sb.append("  	   	, A.ID_CONTENT						\n");
			sb.append("  	   	, A.ID_TRANS_USEYN						\n");
			sb.append("   	  	, M.M_NAME							\n");
			sb.append(" 	 FROM ISSUE_DATA A						\n");
			
			arrTypeCode = null;
			arrTypeCode2 = null;
			str_typecode = "";
			
			// 각 분류체계
			if(!"".equals(typeCode)){
				arrTypeCode = typeCode.split("@");
				for(int i=0; i < arrTypeCode.length; i++){
					str_typecode = arrTypeCode[i];
					if(!"".equals(str_typecode)){
						arrTypeCode2 = str_typecode.split(","); 
						sb.append("		INNER JOIN ISSUE_DATA_CODE IDC"+(i+1)+" ON A.ID_SEQ = IDC"+(i+1)+".ID_SEQ 			\n");
						sb.append("			AND  IDC"+(i+1)+".IC_TYPE = "+arrTypeCode2[0]+"									\n");
						sb.append("			AND  IDC"+(i+1)+".IC_CODE IN ( "+arrTypeCode2[1]+" )							\n");
					}
				}
			}
			
			// 각 분류체계 별 성향
		/*	if(!"".equals(sentiCode)){
				arrTypeCode = null;
				str_typecode = "";
				arrTypeCode = sentiCode.split("@");
				for(int i=0; i < arrTypeCode.length; i++){
					str_typecode = arrTypeCode[i];
					if(!"".equals(str_typecode)){
						arrTypeCode2 = null;
						arrTypeCode2 = str_typecode.split(","); 
						sb.append("		INNER JOIN ISSUE_DATA_CODE_DETAIL SENTI"+(i+1)+" ON A.ID_SEQ = SENTI"+(i+1)+".ID_SEQ 	\n");
						sb.append("    	  AND SENTI"+(i+1)+".IC_TYPE = "+arrTypeCode2[2]+"									\n");
						sb.append("   	  AND SENTI"+(i+1)+".IC_CODE IN ("+arrTypeCode2[3]+")								\n");
						sb.append("    	  AND SENTI"+(i+1)+".IC_PTYPE = "+arrTypeCode2[0]+"									\n");
						sb.append("    	  AND SENTI"+(i+1)+".IC_PCODE IN ("+arrTypeCode2[1]+")								\n");
					}
				}
			}*/
			
			// 각 분류체계 별 정보유형
			/*if(!"".equals(infoCode)){
				arrTypeCode = null;
				str_typecode = "";
				arrTypeCode = infoCode.split("@");
				for(int i=0; i < arrTypeCode.length; i++){
					str_typecode = arrTypeCode[i];
					if(!"".equals(str_typecode)){
						arrTypeCode2 = null;
						arrTypeCode2 = str_typecode.split(","); 
						sb.append("		INNER JOIN ISSUE_DATA_CODE_DETAIL INFO"+(i+1)+" ON A.ID_SEQ = INFO"+(i+1)+".ID_SEQ 		\n");
						sb.append("     	 AND INFO"+(i+1)+".IC_TYPE = "+arrTypeCode2[2]+"									\n");
						sb.append("     	 AND INFO"+(i+1)+".IC_CODE IN ("+arrTypeCode2[3]+")									\n");
						sb.append("     	 AND INFO"+(i+1)+".IC_PTYPE = "+arrTypeCode2[0]+"									\n");
						sb.append("     	 AND INFO"+(i+1)+".IC_PCODE IN ("+arrTypeCode2[1]+")								\n");
					}
				}
			}*/
			
			// 각 분류체계 별 연관키워드
			/*if(!"".equals(relKeywordCode)){
				arrTypeCode = null;
				str_typecode = "";
				arrTypeCode = relKeywordCode.split("@");
				for(int i=0; i < arrTypeCode.length; i++){
					str_typecode = arrTypeCode[i];
					if(!"".equals(str_typecode)){
						arrTypeCode2 = null;
						arrTypeCode2 = str_typecode.split(","); 
						sb.append("		INNER JOIN RELATION_KEYWORD_MAP RKM"+(i+1)+" ON A.ID_SEQ = RKM"+(i+1)+".ID_SEQ 		\n");
						sb.append("     	AND RKM"+(i+1)+".IC_TYPE = "+arrTypeCode2[0]+"									\n");
						sb.append("     	AND RKM"+(i+1)+".IC_CODE IN ("+arrTypeCode2[1]+")								\n");
						sb.append("    	  	AND RKM"+(i+1)+".RK_SEQ = "+arrTypeCode2[2]+"									\n");
					}
				}
			}*/
			
			sb.append("		INNER JOIN MEMBER M ON A.M_SEQ = M.M_SEQ 			\n");
			if(!"".equals(register)){
				sb.append("		AND M.M_SEQ = "+register+"						\n");				
			}
			sb.append(" 	WHERE A.MD_DATE BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"'							\n");
			if(!searchKeyword.equals("")){
				String[] arKey = searchKeyword.split(" ");
				for(int i =0; i < arKey.length; i++){
					if(!arKey[i].trim().equals("")){
						if(searchKeyType.equals("1")){
							//제목검색
							sb.append("AND ID_TITLE LIKE '%"+arKey[i]+"%' \n");
			
						}else if(searchKeyType.equals("2")){
							//제목+내용검색
							sb.append("AND CONCAT(ID_TITLE,ID_CONTENT) LIKE '%"+arKey[i]+"%'\n");
						}
					}
				}
			}
			
			// 영향력 선택
			if (!"".equals(cloutCode)) {
				sb.append("AND ID_CLOUT = '" + cloutCode + "' \n");
			}			
			//성향 선택
			if(!"".equals(sentiCode)){
				sb.append("AND ID_SENTI = '"+sentiCode+"' \n");
			}
			
			//출처
			if(!"".equals(sg_seqs)){
				sb.append("AND SG_SEQ IN ("+sg_seqs+") \n");
			}
			
			//보고서
			if(!"".equals(transSeries)) {
				sb.append(" 	 AND A.ID_REPORTYN = '" + transSeries + "'						\n");
			}
			sb.append(" 	GROUP BY MD_PSEQ																				\n");
			//sb.append(" 	ORDER BY "+ order +" "+ orderAlign +"															\n");
			sb.append(" 	ORDER BY A.ID_SEQ DESC																			\n");
			
			
			sb.append(" 	) A																								\n");
			sb.append(" 	LIMIT "+startNum+", "+endNum+"																	\n");
			sb.append(" ) A																									\n");
				
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				idBean = null;
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setId_senti(rs.getString("ID_SENTI"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				idBean.setMd_same_ct_check(rs.getString("MD_SAME_CT_CHECK"));
				idBean.setM_name(rs.getString("M_NAME"));
				idBean.setId_content(rs.getString("ID_CONTENT"));
				idBean.setId_trans_useyn(rs.getString("ID_TRANS_USEYN"));
				idBean.setTemp1(rs.getString("ISSUE_REPORT"));
				idBean.setTemp2(rs.getString("ONLINE_ISSUE_REPORT"));
				result.add(idBean);
			}
			
		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return result;
	}

	public ArrayList getIssueDataExcel(String id_seqs, String sDate, String sTime, String eDate, String eTime,
			String typeCode, String sentiCode, String infoCode, String relKeywordCode, String searchKeyType,
			String searchKeyword, String register, String order, String orderAlign, String sg_seq, String transSeries) {

		ArrayList result = new ArrayList();

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();
			sb.append(
					"	SELECT A.* 																								\n");
			sb.append("  , FN_GET_SITE_SAME(A.MD_PSEQ,'" + sDate + " " + sTime + "','" + eDate + " " + eTime
					+ "') AS MD_SAME_CT 				\n");
			sb.append("  , FN_GET_SITE_SAME_CHECK(A.MD_PSEQ,'" + sDate + " " + sTime + "','" + eDate + " " + eTime
					+ "') AS MD_SAME_CT_CHECK	\n");

			sb.append("  , FN_GET_SITE_SAME(A.MD_PSEQ,'"+sDate+" "+sTime+"','"+eDate+" "+eTime+"') AS MD_SAME_CT 				\n");
			sb.append("  , FN_GET_SITE_SAME_CHECK(A.MD_PSEQ,'"+sDate+" "+sTime+"','"+eDate+" "+eTime+"') AS MD_SAME_CT_CHECK	\n");
			sb.append("  , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 1) AS 'DIVISION'														\n");	//구분
			sb.append("  , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 2) AS 'RISK_GRADE'													\n");	//리스크·VOC 등급
			sb.append("  , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 3) AS 'SHILLA_HOTEL'													\n");	//신라호텔
			sb.append("  , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 4) AS 'SHILLA_MONOGRAM'												\n");	//신라모노그램
			sb.append("  , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 5) AS 'SHILLA_STAY'													\n");	//신라스테이
			sb.append("  , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 6) AS 'HOTEL_VOC_TYPE'												\n");	//호텔_리스크·VOC 유형
			sb.append("  , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 7) AS 'BUSINESS'														\n");	//영업점
			sb.append("  , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 8) AS 'TR_VOC_TYPE'													\n");	//TR_리스크·VOC 유형
			sb.append("  , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 9) AS 'SUBSIDIARY'													\n");	//자회사·계열사
			sb.append("  , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 10) AS 'SHP_VOC_TYPE'												\n");	//SHP_리스크·VOC 유형
			sb.append("  , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 11) AS 'SBTM_VOC_TYPE'												\n");	//SBTM_리스크·VOC 유형
			sb.append("  , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 12) AS 'CEO_VOC_TYPE'												\n");	//CEO/경영진_리스크·VOC 유형
			sb.append("  , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 13) AS 'IR_VOC_TYPE'													\n");	//사업/IR/준법/임직원 등_리스크·VOC 유형
			sb.append("  , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 14) AS 'ESG_VOC_TYPE'												\n");	//정부/ESG 등_리스크·VOC 유형
			sb.append("  , FN_GET_RELATION_KEYWORD_NAME_V2(A.ID_SEQ) AS `RELATION_KEYWORD`												\n");	//연관어
			//sb.append("  , FN_GET_RELATION_KEYWORD_NAME(A.ID_SEQ, 10) AS `RELATION_KEYWORD`												\n");	//연관어
			//고정연관어&연관키워드 - 긍정 / 부정 성향 나눔 (2021.07 고객사 요청사항)
			/*
			sb.append("  , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 26) AS 'FIXRELWORD_26'													\n");
			sb.append("  , FN_GET_RELATION_KEYWORD_NAME_V3(A.ID_SEQ, 1, 26) AS `POS_RELATION_26`												\n");	
			sb.append("  , FN_GET_RELATION_KEYWORD_NAME_V3(A.ID_SEQ, 2, 26) AS `NEG_RELATION_26`												\n");
			sb.append("  , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 27) AS 'FIXRELWORD_27'													\n");
			sb.append("  , FN_GET_RELATION_KEYWORD_NAME_V3(A.ID_SEQ, 1, 27) AS `POS_RELATION_27`												\n");	
			sb.append("  , FN_GET_RELATION_KEYWORD_NAME_V3(A.ID_SEQ, 2, 27) AS `NEG_RELATION_27`												\n");
			sb.append("  , FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 28) AS 'FIXRELWORD_28'													\n");
			sb.append("  , FN_GET_RELATION_KEYWORD_NAME_V3(A.ID_SEQ, 1, 28) AS `POS_RELATION_28`												\n");	
			sb.append("  , FN_GET_RELATION_KEYWORD_NAME_V3(A.ID_SEQ, 2, 28) AS `NEG_RELATION_28`												\n");
			*/
			
			sb.append(" FROM (										\n");
			sb.append(" 	SELECT A.*								\n");
			sb.append(" 	FROM									\n");
			sb.append(" 	(										\n");
			sb.append("  	SELECT 									\n");
			sb.append("    	   	DISTINCT(A.ID_SEQ) AS ID_SEQ		\n");
			sb.append("   	  	, A.MD_SEQ							\n");
			sb.append("   	  	, A.MD_PSEQ							\n");
			sb.append("  	   	, A.MD_TYPE							\n");
			sb.append("  	   	, A.S_SEQ							\n");
			sb.append(" 	   	, A.SG_SEQ							\n");	//출처
			sb.append(
					"  	   	, DATE_FORMAT(A.MD_DATE, '%Y-%m-%d %H:%i:%s') AS MD_DATE									\n");
			sb.append("  	   	, A.MD_SITE_NAME					\n");
			sb.append("   	  	, A.ID_TITLE						\n");
			sb.append("   	  	, A.ID_URL							\n");
			sb.append("  	   	, A.ID_CONTENT						\n");
			sb.append("  	   	, A.ID_CLOUT 						\n");	//영향력
			sb.append("  	   	, A.ID_SENTI 						\n");	//감성
			sb.append("  	   	, A.ID_REPORTYN 					\n");	//보고서
			sb.append("   	  	, M.M_NAME							\n");
			sb.append(" 	 FROM ISSUE_DATA A						\n");

			String[] arrTypeCode = null;
			String[] arrTypeCode2 = null;
			String str_typecode = "";

			// 각 분류체계
			if (!"".equals(typeCode)) {
				arrTypeCode = typeCode.split("@");
				for (int i = 0; i < arrTypeCode.length; i++) {
					str_typecode = arrTypeCode[i];
					if (!"".equals(str_typecode)) {
						arrTypeCode2 = str_typecode.split(",");
						sb.append("		INNER JOIN ISSUE_DATA_CODE IDC" + (i + 1) + " ON A.ID_SEQ = IDC" + (i + 1)
								+ ".ID_SEQ 			\n");
						sb.append("			AND  IDC" + (i + 1) + ".IC_TYPE = " + arrTypeCode2[0]
								+ "									\n");
						sb.append("			AND  IDC" + (i + 1) + ".IC_CODE IN ( " + arrTypeCode2[1]
								+ " )							\n");
					}
				}
			}

			// 각 분류체계 별 성향
			if (!"".equals(sentiCode)) {
				arrTypeCode = null;
				str_typecode = "";
				arrTypeCode = sentiCode.split("@");
				for (int i = 0; i < arrTypeCode.length; i++) {
					str_typecode = arrTypeCode[i];
					if (!"".equals(str_typecode)) {
						arrTypeCode2 = null;
						arrTypeCode2 = str_typecode.split(",");
						sb.append("		INNER JOIN ISSUE_DATA_CODE_DETAIL SENTI" + (i + 1) + " ON A.ID_SEQ = SENTI"
								+ (i + 1) + ".ID_SEQ 	\n");
						sb.append("    	  AND SENTI" + (i + 1) + ".IC_TYPE = " + arrTypeCode2[2]
								+ "									\n");
						sb.append("   	  AND SENTI" + (i + 1) + ".IC_CODE IN (" + arrTypeCode2[3]
								+ ")								\n");
						sb.append("    	  AND SENTI" + (i + 1) + ".IC_PTYPE = " + arrTypeCode2[0]
								+ "									\n");
						sb.append("    	  AND SENTI" + (i + 1) + ".IC_PCODE IN (" + arrTypeCode2[1]
								+ ")								\n");
					}
				}
			}

			// 각 분류체계 별 정보유형
			if (!"".equals(infoCode)) {
				arrTypeCode = null;
				str_typecode = "";
				arrTypeCode = infoCode.split("@");
				for (int i = 0; i < arrTypeCode.length; i++) {
					str_typecode = arrTypeCode[i];
					if (!"".equals(str_typecode)) {
						arrTypeCode2 = null;
						arrTypeCode2 = str_typecode.split(",");
						sb.append("		INNER JOIN ISSUE_DATA_CODE_DETAIL INFO" + (i + 1) + " ON A.ID_SEQ = INFO"
								+ (i + 1) + ".ID_SEQ 		\n");
						sb.append("     	 AND INFO" + (i + 1) + ".IC_TYPE = " + arrTypeCode2[2]
								+ "									\n");
						sb.append("     	 AND INFO" + (i + 1) + ".IC_CODE IN (" + arrTypeCode2[3]
								+ ")									\n");
						sb.append("     	 AND INFO" + (i + 1) + ".IC_PTYPE = " + arrTypeCode2[0]
								+ "									\n");
						sb.append("     	 AND INFO" + (i + 1) + ".IC_PCODE IN (" + arrTypeCode2[1]
								+ ")								\n");
					}
				}
			}

			// 각 분류체계 별 연관키워드
			if (!"".equals(relKeywordCode)) {
				arrTypeCode = null;
				str_typecode = "";
				arrTypeCode = relKeywordCode.split("@");
				for (int i = 0; i < arrTypeCode.length; i++) {
					str_typecode = arrTypeCode[i];
					if (!"".equals(str_typecode)) {
						arrTypeCode2 = null;
						arrTypeCode2 = str_typecode.split(",");
						sb.append("		INNER JOIN RELATION_KEYWORD_MAP RKM" + (i + 1) + " ON A.ID_SEQ = RKM" + (i + 1)
								+ ".ID_SEQ 		\n");
						sb.append("     	AND RKM" + (i + 1) + ".IC_TYPE = " + arrTypeCode2[0]
								+ "									\n");
						sb.append("     	AND RKM" + (i + 1) + ".IC_CODE IN (" + arrTypeCode2[1]
								+ ")								\n");
						sb.append("    	  	AND RKM" + (i + 1) + ".RK_SEQ = " + arrTypeCode2[2]
								+ "									\n");
					}
				}
			}

			sb.append("		INNER JOIN MEMBER M ON A.M_SEQ = M.M_SEQ 			\n");
			if (!"".equals(register)) {
				sb.append("		AND M.M_SEQ = " + register + "						\n");
			}
			
			sb.append(" 	AND A.MD_DATE BETWEEN '" + sDate + " " + sTime + "' AND '" + eDate + " " + eTime
					+ "'							\n");
			if(!"".equals(sg_seq)) {
				sb.append(" 	AND A.SG_SEQ IN (" + sg_seq + ")					\n");		//츌처
			}
			if(!"".equals(transSeries)) {
				sb.append(" 	AND A.ID_REPORTYN = '" + transSeries + "'					\n");	//보고서
			}
			
			if (!searchKeyword.equals("")) {
				String[] arKey = searchKeyword.split(" ");
				for (int i = 0; i < arKey.length; i++) {
					if (!arKey[i].trim().equals("")) {
						if (searchKeyType.equals("1")) {
							// 제목검색
							sb.append("AND ID_TITLE LIKE '%" + arKey[i] + "%' \n");

						} else if (searchKeyType.equals("2")) {
							// 제목+내용검색
							sb.append("AND CONCAT(ID_TITLE,ID_CONTENT) LIKE '%" + arKey[i] + "%'\n");
						}
					}
				}
			}

			// 이슈코드 있을 경우
			if (!"".equals(id_seqs)) {
				sb.append(" 	 WHERE ID_SEQ IN (" + id_seqs + ")			\n");
			}
			sb.append(" 	ORDER BY " + order + " " + orderAlign
					+ "															\n");
			sb.append(
					" 	) A																								\n");
			sb.append(
					" ) A																									\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				idBean = null;
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setId_reportyn(rs.getString("ID_REPORTYN"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				idBean.setMd_same_ct_check(rs.getString("MD_SAME_CT_CHECK"));
				idBean.setM_name(rs.getString("M_NAME"));
				idBean.setId_content(rs.getString("ID_CONTENT"));
				idBean.setId_clout(rs.getString("ID_CLOUT")); 
				idBean.setSenti(rs.getString("ID_SENTI"));
				idBean.setCode1(rs.getString("DIVISION"));
				idBean.setCode2(rs.getString("RISK_GRADE"));
				idBean.setCode3(rs.getString("SHILLA_HOTEL"));
				idBean.setCode4(rs.getString("SHILLA_MONOGRAM"));
				idBean.setCode5(rs.getString("SHILLA_STAY"));
				idBean.setCode6(rs.getString("HOTEL_VOC_TYPE"));
				idBean.setCode7(rs.getString("BUSINESS"));
				idBean.setCode8(rs.getString("TR_VOC_TYPE"));
				idBean.setCode9(rs.getString("SUBSIDIARY"));
				idBean.setCode10(rs.getString("SHP_VOC_TYPE"));
				idBean.setCode11(rs.getString("SBTM_VOC_TYPE"));
				idBean.setCode12(rs.getString("CEO_VOC_TYPE"));
				idBean.setCode13(rs.getString("IR_VOC_TYPE"));
				idBean.setCode14(rs.getString("ESG_VOC_TYPE"));
				idBean.setRelationkeys(rs.getString("RELATION_KEYWORD"));

				result.add(idBean);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return result;
	}

	
    public HashMap<String, String> getCodeSiteGroupMap(){
    	HashMap<String, String> codeMap = new HashMap<String, String>();
    	
    	try {

    		dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();                                       
			sb.append("SELECT SG_SEQ            \n");
			sb.append("	 	, SG_NAME            \n");
		    sb.append("  FROM SITE_GROUP                                    \n");
		    sb.append(" WHERE USEYN = 'Y'                     \n");
		    
		    System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

	    	while(rs.next()){
	    		codeMap.put(rs.getString("SG_SEQ"), rs.getString("SG_NAME"));
	    	}

		}catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
   	 	return codeMap;
    }	
	
	// written by 고범준
	public ArrayList ReassembleTypeCode_Etc(String TypeCode_Etc) {
		ArrayList master = new ArrayList();

		if (!TypeCode_Etc.equals("")) {
			String[] tempRow = TypeCode_Etc.split("@");
			String[] tempCol = null;

			String[] getTBean = null;
			String[] setTBean = null;

			boolean exChk = false;

			for (int i = 0; i < tempRow.length; i++) {
				tempCol = tempRow[i].split(",");

				exChk = false;
				for (int j = 0; j < master.size(); j++) {
					getTBean = (String[]) master.get(j);

					// 마스터에 있으면 입장
					if (tempCol[0].equals(getTBean[0])) {
						// 기존에 CODE를 쉼표단위로 추가하여 업데이트
						setTBean = new String[3];
						setTBean[0] = getTBean[0];
						setTBean[1] = (getTBean[1] + "," + tempCol[1]);
						setTBean[2] = (getTBean[2] + "," + tempCol[2]);

						master.set(j, setTBean);

						exChk = true;
						break;
					}
				}

				// 마스터어레이에 없으면 입장
				if (!exChk) {
					// 새로 추가
					setTBean = new String[3];
					setTBean[0] = tempCol[0];
					if (tempCol.length > 0) {
						setTBean[1] = tempCol[1];
						setTBean[2] = tempCol[2];
					}
					master.add(setTBean);
				}
			}
		}

		return master;
	}

	// written by 고범준
	// modified by 정천웅 2015-12-24 타입코드 조건 추가
	// modefied in 2016-01-08 연관키워드 및 등록자 조건 추가
	public ArrayList getExcel(String schSdate, String schStime, String schEdate, String schEtime, String typeCode,
			String typeCode_Etc, String relationKeySeq, String searchKey, String keyType, String register) {
		ArrayList issueDataList = new ArrayList();
		String arrTypeCode[] = null;
		String arrTypeCode_Etc[] = null;
		ArrayList types = new ArrayList();
		ArrayList codes = new ArrayList();

		ArrayList ptypes = new ArrayList();
		ArrayList etc_Types = new ArrayList();
		ArrayList etc_Codes = new ArrayList();
		// code에 대한 쿼리문을 생성한다.

		try {

			// 각 typeCode 분류하기
			if (!typeCode.equals("")) {
				arrTypeCode = typeCode.split("@");
			}

			if (!typeCode_Etc.equals("")) {
				arrTypeCode_Etc = typeCode_Etc.split("@");
			}

			if (arrTypeCode != null) {
				String temp[] = null;

				for (int i = 0; i < arrTypeCode.length; i++) {

					if (!arrTypeCode[i].equals(""))
						temp = arrTypeCode[i].split(",");

					if (temp != null) {
						types.add(temp[0]);
						codes.add(temp[1]);
					}
				}

				// 하위 분류체계가 있으면 상위 분류체계를 Query에 넣을 필요가 없음
				if (types.contains("8")) { // TM
					codes.remove(types.indexOf("2"));
					types.remove("2");
				}
				if (types.contains("10")) { // Family
					codes.remove(types.indexOf("3"));
					types.remove("3");
				}
				if (types.contains("11")) { // 인물
					codes.remove(types.indexOf("4"));
					types.remove("4");
				}
				if (types.contains("12")) { // 그룹
					codes.remove(types.indexOf("5"));
					types.remove("5");
				}
				if (types.contains("13")) { // 관계사
					codes.remove(types.indexOf("7"));
					types.remove("7");
				}
			}

			if (arrTypeCode_Etc != null) {

				String temp[] = null;

				for (int i = 0; i < arrTypeCode_Etc.length; i++) {

					if (!arrTypeCode_Etc[i].equals(""))
						temp = arrTypeCode_Etc[i].split(",");

					if (temp != null) {
						etc_Types.add(temp[0]);
						etc_Codes.add(temp[1]);
						ptypes.add(temp[2]);
					}
				}
			}

			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();

			sb.append("SELECT *																\n");
			// TM
			sb.append("		, FN_GET_ISSUE_CODE_NAME_CONCAT(ID_SEQ, 8) AS A8				\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 9,  2) AS A9_2			\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 14,  2) AS A14_2		\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 20,  2) AS A20_2		\n");
			sb.append("		, FN_GET_RELATION_KEYWORD_NAME(ID_SEQ, 2) AS RK2				\n");
			// Family
			sb.append("		, FN_GET_ISSUE_CODE_NAME_CONCAT(ID_SEQ, 10) AS A10				\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 9,  3) AS A9_3			\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 14,  3) AS A14_3		\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 20,  3) AS A20_3		\n");
			sb.append("		, FN_GET_RELATION_KEYWORD_NAME(ID_SEQ, 3) AS RK3				\n");
			// 인물
			sb.append("		, FN_GET_ISSUE_CODE_NAME_CONCAT(ID_SEQ, 11) AS A11				\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 9,  4) AS A9_4			\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 14,  4) AS A14_4		\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 20,  4) AS A20_4		\n");
			sb.append("		, FN_GET_RELATION_KEYWORD_NAME(ID_SEQ, 4) AS RK4				\n");
			// 그룹
			sb.append("		, FN_GET_ISSUE_CODE_NAME_CONCAT(ID_SEQ, 12) AS A12				\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 9,  5) AS A9_5			\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 15,  5) AS A15_5		\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 20,  5) AS A20_5		\n");
			sb.append("		, FN_GET_RELATION_KEYWORD_NAME(ID_SEQ, 5) AS RK5				\n");
			// 관계사
			sb.append("		, FN_GET_ISSUE_CODE_NAME_CONCAT(ID_SEQ, 13) AS A13				\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 9,  7) AS A9_7			\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 15,  7) AS A15_7		\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 20,  7) AS A20_7		\n");
			sb.append("		, FN_GET_RELATION_KEYWORD_NAME(ID_SEQ, 7) AS RK7				\n");
			// 나머지부분
			sb.append("		, FN_GET_ISSUE_CODE_NAME(ID_SEQ, 16) AS A16						\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME(ID_SEQ, 6) AS A6						\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME(ID_SEQ, 17) AS A17						\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME(ID_SEQ, 18) AS A18						\n");
			sb.append("		, FN_GET_ISSUE_CODE_NAME(ID_SEQ, 19) AS A19						\n");
			sb.append("		, FN_GET_SITE_SAME(MD_PSEQ, '" + schSdate + " " + schStime + "', '" + schEdate + " "
					+ schEtime + "') AS MD_SAME_CT		\n");
			sb.append("	FROM (																\n");
			sb.append("		SELECT															\n");
			sb.append("			  ID.ID_SEQ													\n");
			sb.append("			 ,MD_SITE_NAME												\n");
			sb.append("			 ,ID_TITLE													\n");
			sb.append("			 ,ID_URL													\n");
			sb.append("			 ,DATE_FORMAT(MD_DATE, '%Y-%m-%d %H:%i:%s') AS DATE			\n");
			sb.append("			 ,MD_PSEQ													\n");
			sb.append("			 ,ID.ID_REPORTYN											\n");
			sb.append("			 ,M.M_NAME													\n");
			sb.append("		FROM ISSUE_DATA ID												\n");
			sb.append(
					"		INNER JOIN MEMBER M ON ID.M_SEQ = M.M_SEQ																					\n");
			if (!relationKeySeq.equals("")) {
				sb.append(
						"			INNER JOIN RELATION_KEYWORD_MAP RKM ON ID.ID_SEQ = RKM.ID_SEQ 														\n");
				sb.append("										AND RKM.RK_SEQ = " + relationKeySeq
						+ "												\n");
			}
			for (int i = 0; i < types.size(); i++) {
				sb.append("		, ISSUE_DATA_CODE IDC" + (i + 1) + "								\n");
			}
			for (int i = 0; i < etc_Types.size(); i++) {
				sb.append("		, ISSUE_DATA_CODE_DETAIL IDCD" + (i + 1) + "						\n");
			}
			if (!register.equals("")) {
				sb.append("										AND M.M_SEQ = " + register
						+ "															\n");
			}
			sb.append("		WHERE MD_DATE BETWEEN '" + schSdate + " " + schStime + "' AND '" + schEdate + " " + schEtime
					+ "'						\n");
			for (int i = 0; i < types.size(); i++) {
				sb.append("		AND ID.ID_SEQ = IDC" + (i + 1) + ".ID_SEQ							\n");
				sb.append("		AND IDC" + (i + 1) + ".IC_TYPE = " + types.get(i).toString() + "	\n");
				sb.append("		AND IDC" + (i + 1) + ".IC_CODE = " + codes.get(i).toString() + "	\n");
			}
			for (int i = 0; i < etc_Types.size(); i++) {
				sb.append("		AND ID.ID_SEQ = IDCD" + (i + 1) + ".ID_SEQ							\n");
				sb.append("		AND IDCD" + (i + 1) + ".IC_TYPE = " + etc_Types.get(i).toString() + "	\n");
				sb.append("		AND IDCD" + (i + 1) + ".IC_CODE = " + etc_Codes.get(i).toString() + "	\n");
				sb.append("		AND IDCD" + (i + 1) + ".IC_PTYPE = " + ptypes.get(i).toString() + "	\n");
			}
			// 키워드 조건 주기(AND검색)
			if (!"".equals(searchKey)) {
				String[] arKey = searchKey.split(" ");

				for (int i = 0; i < arKey.length; i++) {
					if (!arKey[i].trim().equals("")) {
						if (keyType.equals("1")) {
							// 제목검색
							sb.append("   AND ID.ID_TITLE LIKE '%" + arKey[i] + "%' \n");

						} else if (keyType.equals("2")) {
							// 제목+내용검색
							sb.append("   AND CONCAT(ID.ID_TITLE,ID.ID_CONTENT) LIKE '%" + arKey[i] + "%'		\n");
						}
					}
				}
			}
			sb.append("	  GROUP BY ID.MD_PSEQ												\n");
			sb.append("	  ORDER BY DATE														\n");
			sb.append("	) A																	\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				idBean = new IssueDataBean();
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setDate(rs.getString("DATE"));
				idBean.setM_name(rs.getString("M_NAME"));
				idBean.setA8(rs.getString("A8"));
				idBean.setA9_2(rs.getString("A9_2"));
				idBean.setA14_2(rs.getString("A14_2"));
				idBean.setA20_2(rs.getString("A20_2"));
				idBean.setRk2(rs.getString("RK2"));
				idBean.setA10(rs.getString("A10"));
				idBean.setA9_3(rs.getString("A9_3"));
				idBean.setA14_3(rs.getString("A14_3"));
				idBean.setA20_3(rs.getString("A20_3"));
				idBean.setRk3(rs.getString("RK3"));
				idBean.setA11(rs.getString("A11"));
				idBean.setA9_4(rs.getString("A9_4"));
				idBean.setA14_4(rs.getString("A14_4"));
				idBean.setA20_4(rs.getString("A20_4"));
				idBean.setRk4(rs.getString("RK4"));
				idBean.setA12(rs.getString("A12"));
				idBean.setA9_5(rs.getString("A9_5"));
				idBean.setA15_5(rs.getString("A15_5"));
				idBean.setA20_5(rs.getString("A20_5"));
				idBean.setRk5(rs.getString("RK5"));
				idBean.setA13(rs.getString("A13"));
				idBean.setA9_7(rs.getString("A9_7"));
				idBean.setA15_7(rs.getString("A15_7"));
				idBean.setA20_7(rs.getString("A20_7"));
				idBean.setRk7(rs.getString("RK7"));
				idBean.setA16(rs.getString("A16"));
				idBean.setA6(rs.getString("A6"));
				idBean.setA17(rs.getString("A17"));
				idBean.setA18(rs.getString("A18"));
				idBean.setA19(rs.getString("A19"));
				idBean.setId_reportyn(rs.getString("ID_REPORTYN"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));

				issueDataList.add(idBean);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return issueDataList;
	}

	public JSONArray getChartJSON(String dateFormat, String sDate, String sTime, String eDate, String eTime,
			String typeCode, String sentiCode, String infoCode, String relKeywordCode, String searchKeyType,
			String searchKeyword, String register) {
		JSONArray jsonArray = new JSONArray();
		DateUtil du = new DateUtil();
		String[] arrTypeCode = null;
		String[] arrTypeCode2 = null;
		String str_typecode = "";

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			String sDateFormat = sDate + " " + sTime;
			String eDateFormat = eDate + " " + eTime;
			String displayDateFormat = "";

			sb = new StringBuffer();
			sb.append(
					" SELECT A.DATE AS MD_DATE																		\n");
			sb.append(
					" 		, IFNULL(B.POS, 0) AS POS																\n");
			sb.append(
					" 		, IFNULL(B.NEG, 0) AS NEG																\n");
			sb.append(
					" 		, IFNULL(B.NEU, 0) AS NEU																\n");
			sb.append(
					" FROM																							\n");
			sb.append(
					" (																								\n");
			sb.append(" 	SELECT DATE_FORMAT(A.DATE, '" + dateFormat
					+ "') AS DATE												\n");
			sb.append(
					"	FROM																						\n");
			sb.append(
					"	(																							\n");
			if ("%Y%m%d %H".equals(dateFormat)) {
				sb.append(
						"	 SELECT ADDDATE(CURDATE(), 1) - INTERVAL (A.A + (10 * B.A) + (100 * C.A)) HOUR AS DATE	\n");
			} else if ("%Y%m%d".equals(dateFormat)) {
				sb.append(
						"	 SELECT CURDATE() - INTERVAL (A.A + (10 * B.A) + (100 * C.A)) DAY AS DATE				\n");
			}
			sb.append(
					"	 FROM (SELECT 0 AS A UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS A			\n");
			sb.append(
					"	 CROSS JOIN (SELECT 0 AS A UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS B   	\n");
			sb.append(
					"	 CROSS JOIN (SELECT 0 AS A UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS C		\n");
			sb.append(
					"	) A																							\n");
			sb.append("	WHERE A.DATE BETWEEN STR_TO_DATE('"
					+ sDateFormat.replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "")
					+ "', '%Y%m%d%H%i%s') AND STR_TO_DATE('"
					+ eDateFormat.replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "")
					+ "', '%Y%m%d%H%i%s')	\n");
			sb.append(
					"	ORDER BY DATE																				\n");
			sb.append(
					" ) A LEFT OUTER JOIN																			\n");
			sb.append(
					" (																								\n");
			sb.append(" 	SELECT 																				\n");
			sb.append(" 		A.MD_DATE																		\n");
			sb.append(" 		, SUM(IF(A.IC_CODE = 1, A.CNT ,0)) AS POS										\n");
			sb.append(" 		, SUM(IF(A.IC_CODE = 2, A.CNT ,0)) AS NEG										\n");
			sb.append(" 		, SUM(IF(A.IC_CODE = 3, A.CNT ,0)) AS NEU										\n");
			sb.append(" 	FROM (																				\n");
			sb.append(
					" 		SELECT DATE_FORMAT(MD_DATE, '" + dateFormat + "') AS MD_DATE						\n");
			sb.append(" 			, B.IC_CODE																	\n");
			sb.append(" 			, COUNT(*) AS CNT															\n");
			sb.append(" 		FROM ISSUE_DATA A																\n");
			sb.append(" 		INNER JOIN ISSUE_DATA_CODE_DETAIL B ON A.ID_SEQ = B.ID_SEQ AND B.IC_TYPE = 9	\n");

			if (!"".equals(typeCode)) {
				arrTypeCode = typeCode.split("@");
				for (int i = 0; i < arrTypeCode.length; i++) {
					str_typecode = arrTypeCode[i];
					if (!"".equals(str_typecode)) {
						arrTypeCode2 = str_typecode.split(",");
						sb.append("		INNER JOIN ISSUE_DATA_CODE IDC" + (i + 1) + " ON A.ID_SEQ = IDC" + (i + 1)
								+ ".ID_SEQ 			\n");
						sb.append("			AND  IDC" + (i + 1) + ".IC_TYPE = " + arrTypeCode2[0]
								+ "									\n");
						sb.append("			AND  IDC" + (i + 1) + ".IC_CODE IN ( " + arrTypeCode2[1]
								+ " )							\n");
					}
				}
			}

			// 각 분류체계 별 정보유형
			if (!"".equals(infoCode)) {
				arrTypeCode = null;
				str_typecode = "";
				arrTypeCode = infoCode.split("@");
				for (int i = 0; i < arrTypeCode.length; i++) {
					str_typecode = arrTypeCode[i];
					if (!"".equals(str_typecode)) {
						arrTypeCode2 = null;
						arrTypeCode2 = str_typecode.split(",");
						sb.append("		INNER JOIN ISSUE_DATA_CODE_DETAIL INFO" + (i + 1) + " ON A.ID_SEQ = INFO"
								+ (i + 1) + ".ID_SEQ 		\n");
						sb.append("     	 AND INFO" + (i + 1) + ".IC_TYPE = " + arrTypeCode2[2]
								+ "									\n");
						sb.append("     	 AND INFO" + (i + 1) + ".IC_CODE IN (" + arrTypeCode2[3]
								+ ")									\n");
						sb.append("     	 AND INFO" + (i + 1) + ".IC_PTYPE = " + arrTypeCode2[0]
								+ "									\n");
						sb.append("     	 AND INFO" + (i + 1) + ".IC_PCODE IN (" + arrTypeCode2[1]
								+ ")								\n");
					}
				}
			}
			// 각 분류체계 별 연관키워드
			if (!"".equals(relKeywordCode)) {
				arrTypeCode = null;
				str_typecode = "";
				arrTypeCode = relKeywordCode.split("@");
				for (int i = 0; i < arrTypeCode.length; i++) {
					str_typecode = arrTypeCode[i];
					if (!"".equals(str_typecode)) {
						arrTypeCode2 = null;
						arrTypeCode2 = str_typecode.split(",");
						sb.append("		INNER JOIN RELATION_KEYWORD_MAP RKM" + (i + 1) + " ON A.ID_SEQ = RKM" + (i + 1)
								+ ".ID_SEQ 		\n");
						sb.append("     	AND RKM" + (i + 1) + ".IC_TYPE = " + arrTypeCode2[0]
								+ "									\n");
						sb.append("     	AND RKM" + (i + 1) + ".IC_CODE IN (" + arrTypeCode2[1]
								+ ")								\n");
						sb.append("    	  	AND RKM" + (i + 1) + ".RK_SEQ = " + arrTypeCode2[2]
								+ "									\n");
					}
				}
			}
			sb.append(" 		INNER JOIN MEMBER M ON A.M_SEQ = M.M_SEQ 										\n");
			sb.append(
					" 		WHERE MD_DATE BETWEEN '" + sDateFormat + "' AND '" + eDateFormat + "' 				\n");
			sb.append(
					" 		GROUP BY DATE_FORMAT(MD_DATE, '" + dateFormat + "'), IC_CODE						\n");
			sb.append(" 	) A																					\n");
			sb.append(" 	GROUP BY MD_DATE																	\n");
			sb.append(" ) B ON A.DATE = B.MD_DATE																\n");
			sb.append(" ORDER BY MD_DATE																		\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			JSONObject jsonObj = null;
			String date_temp = "";
			while (rs.next()) {
				jsonObj = new JSONObject();
				date_temp = rs.getString("MD_DATE");
				if ("%Y%m%d %H".equals(dateFormat)) {
					jsonObj.put("date", date_temp.substring(6, 8) + "일 " + date_temp.substring(9, 11) + "시");
				} else if ("%Y%m%d".equals(dateFormat)) {
					jsonObj.put("date", du.getEnglishDate(date_temp, "MM dd"));
				}
				jsonObj.put("POS", rs.getInt("POS"));
				jsonObj.put("NEG", rs.getInt("NEG"));
				jsonObj.put("NEU", rs.getInt("NEU"));
				jsonArray.put(jsonObj);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return jsonArray;
	}

	public ArrayList getChart(String sDate, String sTime, String eDate, String eTime, String typeCode,
			String typeCode_Etc, String gubun, String schKey, String keyType, String relationKeySeq, String register) {

		ArrayList arrResult = new ArrayList();
		IssueSuperBean superBean = new IssueSuperBean();

		ArrayList arTypeCode = ReassembleTypeCode(typeCode);
		ArrayList arTypeCode_Etc = ReassembleTypeCode_Etc(typeCode_Etc);
		String[] getTBean = null;

		// String result = "";
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();

			String dayType = "";
			if (gubun.equals("day")) {
				dayType = "%Y%m%d";
			} else if (gubun.equals("time")) {
				dayType = "%Y%m%d %H";
			}

			sb.append("SELECT A.MD_DATE																\n");
			sb.append("     , SUM(IF(A.IC_CODE = 1, A.CNT ,0)) AS POS								\n");
			sb.append("     , SUM(IF(A.IC_CODE = 2, A.CNT ,0)) AS NEG								\n");
			sb.append("     , SUM(IF(A.IC_CODE = 3, A.CNT ,0)) AS NEU								\n");
			sb.append("  FROM (																		\n");

			sb.append("SELECT DATE_FORMAT(MD_DATE, '" + dayType + "') AS MD_DATE					\n");
			sb.append("     , B.IC_CODE															\n");
			sb.append("     , COUNT(*) AS CNT													\n");
			sb.append("  FROM ISSUE_DATA A														\n");

			// 분류체계검색
			// sb.append(getTypeCodeSql(sDate, eDate, sTime, eTime, typeCode) + "\n");

			// 세부조건검색
			for (int i = 0; i < arTypeCode.size(); i++) {
				getTBean = (String[]) arTypeCode.get(i);
				sb.append("			INNER JOIN ISSUE_DATA_CODE C" + (i + 1) + " ON A.ID_SEQ = C" + (i + 1)
						+ ".ID_SEQ 										\n");
				sb.append("                                        AND C" + (i + 1) + ".IC_TYPE = " + getTBean[0]
						+ "										\n");
				sb.append("                                        AND C" + (i + 1) + ".IC_CODE IN (" + getTBean[1]
						+ ")									\n");
			}

			// 성향, 정보속성 검색
			for (int i = 0; i < arTypeCode_Etc.size(); i++) {
				getTBean = (String[]) arTypeCode_Etc.get(i);
				sb.append("			INNER JOIN ISSUE_DATA_CODE_DETAIL D" + (i + 1) + " ON A.ID_SEQ = D" + (i + 1)
						+ ".ID_SEQ 								\n");
				sb.append("                                        AND D" + (i + 1) + ".IC_TYPE = " + getTBean[0]
						+ "										\n");
				sb.append("                                        AND D" + (i + 1) + ".IC_CODE IN (" + getTBean[1]
						+ ")									\n");
				sb.append("                                        AND D" + (i + 1) + ".IC_PTYPE IN (" + getTBean[2]
						+ ")									\n");
			}

			if (!relationKeySeq.equals("")) {
				sb.append(
						"			INNER JOIN RELATION_KEYWORD_MAP RKM ON A.ID_SEQ = RKM.ID_SEQ 														\n");
				sb.append("										AND RKM.RK_SEQ = " + relationKeySeq
						+ "													\n");
			}
			sb.append(
					"		INNER JOIN MEMBER M ON A.M_SEQ = M.M_SEQ																					\n");
			if (!register.equals("")) {
				sb.append("		AND M.M_SEQ = " + register
						+ "															\n");
			}
			sb.append("     , ISSUE_DATA_CODE_DETAIL B 											\n");
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ												\n");
			sb.append("   AND A.MD_DATE BETWEEN '" + sDate + " " + sTime + "' AND '" + eDate + " " + eTime + "'	\n");
			sb.append("   AND B.IC_TYPE = 9 													\n");

			/*
			 * if( !schKey.equals("") ) { String[] arrSchKey = schKey.split(" ");
			 * 
			 * for(int i =0; i < arrSchKey.length; i++){
			 * sb.append("		AND	A.ID_TITLE LIKE '%"+arrSchKey[i]
			 * +"%'  							\n"); } }
			 */

			// 키워드 조건 주기(AND검색)
			if (!schKey.equals("")) {
				String[] arKey = schKey.split(" ");

				for (int i = 0; i < arKey.length; i++) {
					if (!arKey[i].trim().equals("")) {
						if (keyType.equals("1")) {
							// 제목검색
							sb.append("AND ID_TITLE LIKE '%" + arKey[i] + "%' \n");

						} else if (keyType.equals("2")) {
							// 제목+내용검색
							sb.append("AND CONCAT(ID_TITLE,ID_CONTENT) LIKE '%" + arKey[i] + "%'\n");
						}
					}
				}
			}

			sb.append(" GROUP BY DATE_FORMAT(MD_DATE, '" + dayType + "'), IC_CODE					\n");

			sb.append("   )A GROUP BY MD_DATE ORDER BY MD_DATE 										\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			IssueSuperBean.Issue_chartBean cbean = null;

			while (rs.next()) {

				/*
				 * if(result.equals("")){ result =
				 * "{date: '"+rs.getString("MD_DATE")+"',긍정: "+rs.getString("POS")+",부정: "+rs.
				 * getString("NEG")+",중립: "+rs.getString("NEU")+"}"; }else{ result += "," +
				 * "{date: '"+rs.getString("MD_DATE")+"',긍정: "+rs.getString("POS")+",부정: "+rs.
				 * getString("NEG")+",중립: "+rs.getString("NEU")+"}"; }
				 */

				cbean = superBean.new Issue_chartBean();
				cbean.setMd_date(rs.getString("MD_DATE"));
				cbean.setPositive(rs.getString("POS"));
				cbean.setNegative(rs.getString("NEG"));
				cbean.setNeutral(rs.getString("NEU"));
				arrResult.add(cbean);

			}
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	public ArrayList getIssueRegMember() {
		return getIssueRegMember("");
	}

	/**
	 * @param : MG_SEQ : 2->RSN관리자 , 3->재택근무자
	 * @return : 센싱유저리스트 이슈 센싱 유저 목록 가져오기
	 * 
	 **/
	public ArrayList getIssueRegMember(String mg_seq) {

		ArrayList arrResult = new ArrayList();

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();

			sb.append(" SELECT M_SEQ, M_NAME FROM MEMBER		\n");
			sb.append(" WHERE 1=1	 		\n");
			if (!"".equals(mg_seq)) {
				sb.append(" AND MG_SEQ IN (" + mg_seq + ")	 		\n");
			}
			sb.append(" ORDER BY M_NAME							\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {

				mBean = new MemberBean();
				mBean.setM_seq(rs.getString("M_SEQ"));
				mBean.setM_name(rs.getString("M_NAME"));

				arrResult.add(mBean);

			}
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	public ArrayList getExcel(String fromDate) throws SQLException {
		ArrayList result = new ArrayList();
		dbconn = new DBconn(); // 로컬디비
		Statement stmt = null;
		StringBuffer sb = null;
		ResultSet rs = null;
		try {
			dbconn.getSubDirectConnection();
			sb = new StringBuffer();
			sb.append(" SELECT * FROM NAVER_REPLY  \n");
			if (!"".equals(fromDate)) {
				sb.append(" WHERE  regTime >= '" + fromDate + "'	\n");
			}
			sb.append(" GROUP BY commentNo ORDER BY 1 DESC   \n");
			System.out.println(sb.toString());
			stmt = dbconn.createStatement();
			rs = stmt.executeQuery(sb.toString());
			Map map = null;
			while (rs.next()) {
				map = new HashMap();
				map.put("commentNo", rs.getString("commentNo"));
				map.put("userName", rs.getString("userName"));
				map.put("regTime", rs.getString("regTime"));
				map.put("modTime", rs.getString("modTime"));
				map.put("contents", rs.getString("contents"));
				result.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			sb = null;
			rs.close();
			stmt.close();
			dbconn.close();
		}

		return result;
	}

	/**
	 * @param : MG_SEQ : 2->RSN관리자 , 3->재택근무자 이슈 센싱 유저 목록 가져오기
	 * 
	 **/
	public void deleteErrorIssueData(String id_seqs, String md_seqs) {

		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();

			sb.append(" DELETE FROM ISSUE_DATA					\n");
			sb.append(" WHERE ID_SEQ IN (" + id_seqs + ")	 		\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			if (pstmt.executeUpdate() > 0) {

				pstmt.close();
				sb = new StringBuffer();
				sb.append(" UPDATE IDX SET ISSUE_CHECK ='N'             \n");
				sb.append(" WHERE MD_SEQ = " + md_seqs + "                  \n");
				pstmt = dbconn.createPStatement(sb.toString());

				if (pstmt.executeUpdate() > 0) {
					pstmt.close();
					sb = new StringBuffer();
					sb.append(" DELETE FROM ISSUE_DATA_CODE				\n");
					sb.append(" WHERE ID_SEQ IN (" + id_seqs + ")	 		\n");
					pstmt = dbconn.createPStatement(sb.toString());
					pstmt.executeUpdate();

					pstmt.close();
					sb = new StringBuffer();
					sb.append(" DELETE FROM ISSUE_DATA_CODE_DETAIL		\n");
					sb.append(" WHERE ID_SEQ IN (" + id_seqs + ")	 		\n");
					pstmt = dbconn.createPStatement(sb.toString());
					pstmt.executeUpdate();

					pstmt.close();
					sb = new StringBuffer();
					sb.append(" DELETE FROM RELATION_KEYWORD_MAP		\n");
					sb.append(" WHERE ID_SEQ IN (" + id_seqs + ")	 		\n");
					pstmt = dbconn.createPStatement(sb.toString());
					pstmt.executeUpdate();
				}
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
	}

	public ArrayList getExcel_SK(String schSdate, String schStime, String schEdate, String schEtime, String typeCode,
			String typeCode_Etc, String relationKeySeq, String searchKey, String keyType, String register) {
		ArrayList issueDataList = new ArrayList();
		String arrTypeCode[] = null;
		String arrTypeCode_Etc[] = null;
		ArrayList types = new ArrayList();
		ArrayList codes = new ArrayList();

		ArrayList ptypes = new ArrayList();
		ArrayList etc_Types = new ArrayList();
		ArrayList etc_Codes = new ArrayList();
		// code에 대한 쿼리문을 생성한다.

		try {

			// 각 typeCode 분류하기
			if (!typeCode.equals("")) {
				arrTypeCode = typeCode.split("@");
			}

			if (!typeCode_Etc.equals("")) {
				arrTypeCode_Etc = typeCode_Etc.split("@");
			}

			if (arrTypeCode != null) {
				String temp[] = null;

				for (int i = 0; i < arrTypeCode.length; i++) {

					if (!arrTypeCode[i].equals(""))
						temp = arrTypeCode[i].split(",");

					if (temp != null) {
						types.add(temp[0]);
						codes.add(temp[1]);
					}
				}

				// 하위 분류체계가 있으면 상위 분류체계를 Query에 넣을 필요가 없음
				if (types.contains("8")) { // TM
					codes.remove(types.indexOf("2"));
					types.remove("2");
				}
				if (types.contains("10")) { // Family
					codes.remove(types.indexOf("3"));
					types.remove("3");
				}
				if (types.contains("11")) { // 인물
					codes.remove(types.indexOf("4"));
					types.remove("4");
				}
				if (types.contains("12")) { // 그룹
					codes.remove(types.indexOf("5"));
					types.remove("5");
				}
				if (types.contains("13")) { // 관계사
					codes.remove(types.indexOf("7"));
					types.remove("7");
				}
			}

			if (arrTypeCode_Etc != null) {

				String temp[] = null;

				for (int i = 0; i < arrTypeCode_Etc.length; i++) {

					if (!arrTypeCode_Etc[i].equals(""))
						temp = arrTypeCode_Etc[i].split(",");

					if (temp != null) {
						etc_Types.add(temp[0]);
						etc_Codes.add(temp[1]);
						ptypes.add(temp[2]);
					}
				}
			}

			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();

			sb = new StringBuffer();

			sb.append("SELECT *																\n");
			/*
			 * // TM sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_CONCAT(ID_SEQ, 8) AS A8				\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 9,  2) AS A9_2			\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 14,  2) AS A14_2		\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 20,  2) AS A20_2		\n"
			 * ); sb.
			 * append("		, FN_GET_RELATION_KEYWORD_NAME(ID_SEQ, 2) AS RK2				\n"
			 * ); // Family sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_CONCAT(ID_SEQ, 10) AS A10				\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 9,  3) AS A9_3			\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 14,  3) AS A14_3		\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 20,  3) AS A20_3		\n"
			 * ); sb.
			 * append("		, FN_GET_RELATION_KEYWORD_NAME(ID_SEQ, 3) AS RK3				\n"
			 * ); // 인물 sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_CONCAT(ID_SEQ, 11) AS A11				\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 9,  4) AS A9_4			\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 14,  4) AS A14_4		\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 20,  4) AS A20_4		\n"
			 * ); sb.
			 * append("		, FN_GET_RELATION_KEYWORD_NAME(ID_SEQ, 4) AS RK4				\n"
			 * ); //그룹 sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_CONCAT(ID_SEQ, 12) AS A12				\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 9,  5) AS A9_5			\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 15,  5) AS A15_5		\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 20,  5) AS A20_5		\n"
			 * ); sb.
			 * append("		, FN_GET_RELATION_KEYWORD_NAME(ID_SEQ, 5) AS RK5				\n"
			 * ); //관계사 sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_CONCAT(ID_SEQ, 13) AS A13				\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 9,  7) AS A9_7			\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 15,  7) AS A15_7		\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, 20,  7) AS A20_7		\n"
			 * ); sb.
			 * append("		, FN_GET_RELATION_KEYWORD_NAME(ID_SEQ, 7) AS RK7				\n"
			 * ); //나머지부분 sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME(ID_SEQ, 16) AS A16						\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME(ID_SEQ, 6) AS A6						\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME(ID_SEQ, 17) AS A17						\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME(ID_SEQ, 18) AS A18						\n"
			 * ); sb.
			 * append("		, FN_GET_ISSUE_CODE_NAME(ID_SEQ, 19) AS A19						\n"
			 * );
			 */
			sb.append("	FROM (																\n");
			sb.append("		SELECT															\n");
			sb.append("			  ID.ID_SEQ													\n");
			sb.append("			 ,MD_SITE_NAME												\n");
			sb.append("			 ,ID_TITLE													\n");
			sb.append("			 ,ID_URL													\n");
			sb.append("			 ,DATE_FORMAT(MD_DATE, '%Y-%m-%d') AS DATE					\n");
			sb.append("		FROM ISSUE_DATA ID												\n");
			sb.append(
					"		INNER JOIN MEMBER M ON ID.M_SEQ = M.M_SEQ																					\n");
			if (!relationKeySeq.equals("")) {
				sb.append(
						"			INNER JOIN RELATION_KEYWORD_MAP RKM ON ID.ID_SEQ = RKM.ID_SEQ 														\n");
				sb.append("										AND RKM.RK_SEQ = " + relationKeySeq
						+ "												\n");
			}
			for (int i = 0; i < types.size(); i++) {
				sb.append("		, ISSUE_DATA_CODE IDC" + (i + 1) + "								\n");
			}
			for (int i = 0; i < etc_Types.size(); i++) {
				sb.append("		, ISSUE_DATA_CODE_DETAIL IDCD" + (i + 1) + "						\n");
			}
			if (!register.equals("")) {
				sb.append("										AND M.M_SEQ = " + register
						+ "															\n");
			}
			sb.append("		WHERE MD_DATE BETWEEN '" + schSdate + " " + schStime + "' AND '" + schEdate + " " + schEtime
					+ "'						\n");
			for (int i = 0; i < types.size(); i++) {
				sb.append("		AND ID.ID_SEQ = IDC" + (i + 1) + ".ID_SEQ							\n");
				sb.append("		AND IDC" + (i + 1) + ".IC_TYPE = " + types.get(i).toString() + "	\n");
				sb.append("		AND IDC" + (i + 1) + ".IC_CODE = " + codes.get(i).toString() + "	\n");
			}
			for (int i = 0; i < etc_Types.size(); i++) {
				sb.append("		AND ID.ID_SEQ = IDCD" + (i + 1) + ".ID_SEQ							\n");
				sb.append("		AND IDCD" + (i + 1) + ".IC_TYPE = " + etc_Types.get(i).toString() + "	\n");
				sb.append("		AND IDCD" + (i + 1) + ".IC_CODE = " + etc_Codes.get(i).toString() + "	\n");
				sb.append("		AND IDCD" + (i + 1) + ".IC_PTYPE = " + ptypes.get(i).toString() + "	\n");
			}
			// 키워드 조건 주기(AND검색)
			if (!"".equals(searchKey)) {
				String[] arKey = searchKey.split(" ");

				for (int i = 0; i < arKey.length; i++) {
					if (!arKey[i].trim().equals("")) {
						if (keyType.equals("1")) {
							// 제목검색
							sb.append("   AND ID.ID_TITLE LIKE '%" + arKey[i] + "%' \n");

						} else if (keyType.equals("2")) {
							// 제목+내용검색
							sb.append("   AND CONCAT(ID.ID_TITLE,ID.ID_CONTENT) LIKE '%" + arKey[i] + "%'		\n");
						}
					}
				}
			}
			sb.append("	  ORDER BY DATE														\n");
			sb.append("	) A																	\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				idBean = new IssueDataBean();
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setDate(rs.getString("DATE"));
				/*
				 * idBean.setA8(rs.getString("A8")); idBean.setA9_2(rs.getString("A9_2"));
				 * idBean.setA14_2(rs.getString("A14_2"));
				 * idBean.setA20_2(rs.getString("A20_2")); idBean.setRk2(rs.getString("RK2"));
				 * idBean.setA10(rs.getString("A10")); idBean.setA9_3(rs.getString("A9_3"));
				 * idBean.setA14_3(rs.getString("A14_3"));
				 * idBean.setA20_3(rs.getString("A20_3")); idBean.setRk3(rs.getString("RK3"));
				 * idBean.setA11(rs.getString("A11")); idBean.setA9_4(rs.getString("A9_4"));
				 * idBean.setA14_4(rs.getString("A14_4"));
				 * idBean.setA20_4(rs.getString("A20_4")); idBean.setRk4(rs.getString("RK4"));
				 * idBean.setA12(rs.getString("A12")); idBean.setA9_5(rs.getString("A9_5"));
				 * idBean.setA15_5(rs.getString("A15_5"));
				 * idBean.setA20_5(rs.getString("A20_5")); idBean.setRk5(rs.getString("RK5"));
				 * idBean.setA13(rs.getString("A13")); idBean.setA9_7(rs.getString("A9_7"));
				 * idBean.setA15_7(rs.getString("A15_7"));
				 * idBean.setA20_7(rs.getString("A20_7")); idBean.setRk7(rs.getString("RK7"));
				 * idBean.setA16(rs.getString("A16")); idBean.setA6(rs.getString("A6"));
				 * idBean.setA17(rs.getString("A17")); idBean.setA18(rs.getString("A18"));
				 * idBean.setA19(rs.getString("A19"));
				 */

				issueDataList.add(idBean);
			}

		} catch (SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}

		return issueDataList;
	}

	public ArrayList getAutoCompleteSite() {

		ArrayList arrResult = new ArrayList();
		IssueCodeBean icb;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();
			sb.append("SELECT S_NAME FROM SG_S_RELATION						\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				arrResult.add(rs.getString("S_NAME"));
			}
			sb = null;
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return arrResult;
	}

	public String[] getConfirmSite(String site_name) {

		String[] result = new String[2];
		result[0] = "";
		result[1] = "";

		IssueCodeBean icb;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();

			sb = new StringBuffer();
			sb.append("SELECT SG_SEQ, S_SEQ FROM SG_S_RELATION WHERE S_NAME = '" + site_name + "'	\n");

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				result[0] = rs.getString("SG_SEQ");
				result[1] = rs.getString("S_SEQ");
			}
			sb = null;
		} catch (SQLException ex) {
			Log.writeExpt(ex, ex.getMessage());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (dbconn != null)
				try {
					dbconn.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}
	
	
	public ArrayList getRelationKeyword2(String ic_type, String ic_code){
		ArrayList result = new ArrayList();
		
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("SELECT R.RK_SEQ 								\n");
			sb.append("  	, R.RK_NAME 							\n");
			sb.append("  	, H.IC_CODE AS RK_CODE					\n");
			sb.append("  FROM RELATION_KEYWORD_R R  				\n");
			sb.append("		, RELATION_KEYWORD_HISTORY H			\n");
			sb.append(" WHERE R.RK_SEQ = H.RK_SEQ					\n");
			sb.append("   AND H.IC_CODE = "+ic_code+"				\n");
			sb.append("   AND H.IC_CODE != 0						\n");
			sb.append("   AND H.IC_USEYN = 'Y'						\n");
			sb.append(" ORDER BY RK_SEQ DESC						\n");
			sb.append(" LIMIT 10									\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				IssueDataBean idb = new IssueDataBean();
				idb.setRk_seq(rs.getString("RK_SEQ"));
				idb.setRk_name(rs.getString("RK_NAME"));
				idb.setRk_type(rs.getString("RK_CODE"));
	///			idb.setItc_cnt(rs.getInt("CNT"));
				result.add(idb);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	//해시태그 추출
	public ArrayList<String> getHashTags(String str) {
		ArrayList<String> result = new ArrayList<String>();
		String regexpStr = "(#[^ #\"]+)";
		Pattern p = Pattern.compile(regexpStr); 
		Matcher m = p.matcher(str);
		while (m.find()) {
			result.add(m.group().trim());
		}
		return result;
	}
	
	//해시태그 저장
	public void insertIssueHashTag(int id_seq, ArrayList<String> arHashTag){
    	
    	PreparedStatement pstmt1 = null;
    	PreparedStatement pstmt2 = null;
    	PreparedStatement pstmt3 = null;
    	
        StringBuffer sb = null;
    	ResultSet rs = null;
    	
    	try{
    		
			//dbconn = new DBconn();
			//dbconn.getDBCPConnection();
    		
    		sb = new StringBuffer();
			sb.append("SELECT HC_CODE FROM HASHTAG_CODE WHERE BINARY HC_NAME = ? \n");
    		pstmt1 = dbconn.createPStatement(sb.toString());
    		
    		sb = new StringBuffer();
    		sb.append("INSERT INTO HASHTAG_CODE (HC_NAME) VALUES (?);\n");
    		pstmt2 = dbconn.createPStatement(sb.toString());
    		
    		sb = new StringBuffer();
    		sb.append("INSERT INTO ISSUE_HASHTAG (ID_SEQ, HC_CODE) VALUES (?, ?);\n");
    		pstmt3 = dbconn.createPStatement(sb.toString());
    
    		int hcCode = 0;
    		for(int i =0; i<arHashTag.size(); i++){
    			hcCode = 0;
    			
    			if(arHashTag.get(i).length() > 120){
    				arHashTag.set(i,arHashTag.get(i).substring(0, 120));
    			}
    			
    			pstmt1.clearParameters();
    			pstmt1.setString(1, arHashTag.get(i));
    			rs = pstmt1.executeQuery();
    			hcCode = 0;
    			while(rs.next()){
    				hcCode = rs.getInt("HC_CODE");
    			}
    			
    			if(hcCode > 0){
    				pstmt3.clearParameters();
        			pstmt3.setInt(1, id_seq);
        			pstmt3.setInt(2, hcCode);
        			pstmt3.executeUpdate();
    			
    			}else {
    				pstmt2.clearParameters();
        			pstmt2.setString(1, arHashTag.get(i));
        			pstmt2.executeUpdate();
        			
        			rs = pstmt2.getGeneratedKeys(); 
        			if(rs.next()){
        				hcCode = rs.getInt(1);
        			}
        			pstmt3.clearParameters();
        			pstmt3.setInt(1, id_seq);
        			pstmt3.setInt(2, hcCode);
        			pstmt3.executeUpdate();
    			}
    		}
    		
    	}catch(Exception ex) {
    		//Log.writeExpt(name,ex.toString());
    		Log.writeExpt(ex);
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt1 != null) try { pstmt1.close(); } catch(SQLException ex) {}
            if (pstmt2 != null) try { pstmt2.close(); } catch(SQLException ex) {}
            if (pstmt3 != null) try { pstmt3.close(); } catch(SQLException ex) {}
        }
    
    }	
	
}// IssueMgr End.
