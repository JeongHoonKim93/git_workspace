package risk.report;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.ConfigUtil;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;
import sun.misc.BASE64Decoder;


	public class ReportMgrMain {
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = null;	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	
	ReportDataSuperBean superBean = new ReportDataSuperBean();

	
	public ArrayList ReassembleTypeCode(String TypeCode){
		
		ArrayList master = new ArrayList();
		
		if(!TypeCode.equals("")){
		
			String[] tempRow = TypeCode.split("@");
			String[] tempCol = null;
			
			ReportDataSuperBean.typeCodeBean getTBean = null;
			ReportDataSuperBean.typeCodeBean setTBean = null;
			boolean exChk = false;
			
			for(int i =0; i < tempRow.length; i++){
				tempCol = tempRow[i].split(",");
				
				exChk = false;
				for(int j =0; j < master.size(); j++){
					
					getTBean = (ReportDataSuperBean.typeCodeBean)master.get(j);
					
					//마스터에 있으면 입장 
					if(tempCol[0].equals(getTBean.getIc_type())){
						
						//기존에 CODE를 쉼표단위로 추가하여 업데이트
						
						setTBean = superBean.new typeCodeBean();
						setTBean.setIc_type(getTBean.getIc_type());
						setTBean.setIc_code(getTBean.getIc_code() + "," + tempCol[1]);

						master.set(j, setTBean);
						
						exChk = true;
						break;
					}
				}
				
				
				//마스터어레이에 없으면 입장
				if(!exChk){
	
					//새로 추가
					setTBean = superBean.new typeCodeBean();
					setTBean.setIc_type(tempCol[0]);
					setTBean.setIc_code(tempCol[1]);
					master.add(setTBean);
				}
			}
		}
		
		return master;
	}
	
	public ArrayList getIssueList (String sDate, String eDate, String sTime, String eTime, String typeCode, String fNews, String report) {
		
		
		ArrayList arrResult = new ArrayList();
		try {
			
			ArrayList arTypeCode = ReassembleTypeCode(typeCode);
			
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			
			
			sb.append("SELECT DISTINCT A.ID_SEQ																					\n");
			sb.append("     , A.MD_SITE_NAME																			\n");
			sb.append("     , A.MD_TYPE																					\n");
			sb.append("     , A.ID_TITLE																				\n");
			sb.append("     , A.ID_URL																					\n");
			sb.append("     , A.MD_SAME_CT																				\n");
			sb.append("     , A.MD_DATE																					\n");
			/*sb.append("     , D1.IC_NAME AS TYPE9																		\n");*/
			sb.append("     , FN_GET_ISSUE_DETAIL_CODE(A.ID_SEQ, 9) AS TYPE9											\n");
			sb.append("     , D2.IC_NAME AS TYPE10																		\n");
			sb.append("     , A.SG_SEQ																		\n");
			sb.append("     , IF(A.SG_SEQ = 25, A.ID_CONTENT,'') AS	ID_CONTENT								\n");
			sb.append("  FROM ISSUE_DATA A																				\n");
			
			ReportDataSuperBean.typeCodeBean getTBean = null;
			for(int i = 0; i < arTypeCode.size(); i++){
				getTBean = (ReportDataSuperBean.typeCodeBean)arTypeCode.get(i);
				sb.append("       INNER JOIN ISSUE_DATA_CODE C"+(i+1)+" ON A.ID_SEQ = C"+(i+1)+".ID_SEQ 					\n");
				sb.append("                                        AND C"+(i+1)+".IC_TYPE = "+getTBean.getIc_type()+"		\n");
				sb.append("                                        AND C"+(i+1)+".IC_CODE IN ("+getTBean.getIc_code()+")	\n");
			}
			
			//sb.append("     , ISSUE_DATA_CODE B1																		\n");
			sb.append("     , ISSUE_DATA_CODE B2																		\n");
			//sb.append("     , ISSUE_CODE D1																				\n");
			sb.append("     , ISSUE_CODE D2																				\n");
			//sb.append(" WHERE A.ID_SEQ = B1.ID_SEQ																		\n");
			sb.append("   WHERE A.ID_SEQ = B2.ID_SEQ																		\n");
			//sb.append("   AND B1.IC_TYPE = D1.IC_TYPE																	\n");
			//sb.append("   AND B1.IC_CODE = D1.IC_CODE																	\n");
			sb.append("   AND B2.IC_TYPE = D2.IC_TYPE																	\n");
			sb.append("   AND B2.IC_CODE = D2.IC_CODE																	\n");
			sb.append("   AND A.MD_DATE BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"'							\n");
			sb.append("   AND A.ID_USEYN = 'Y'									      									\n");
			if(!fNews.equals("")){
				sb.append("   AND A.F_NEWS = '"+fNews+"'																\n");
			}
			if(!report.equals("")){
				sb.append("   AND A.ID_REPORTYN = '"+report+"'															\n");
			}
			//sb.append("   AND B1.IC_TYPE = 9																			\n");
			sb.append("   AND B2.IC_TYPE = 10																			\n");
			
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			ReportDataSuperBean.issueBean idBean = null;
			while( rs.next() ) {
				
				
				
				idBean = superBean.new issueBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setTemp1(rs.getString("TYPE9"));
				idBean.setTemp2(rs.getString("TYPE10"));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setId_content(rs.getString("ID_CONTENT"));
				arrResult.add(idBean);
			}
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
	
	public ArrayList getIssueDataList(String sDate, String eDate, String sTime, String eTime, String typeCode, String listNum){
		ArrayList arrResult = new ArrayList();
		try {
			
			ArrayList arTypeCode = ReassembleTypeCode(typeCode);
			
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("SELECT AA.*																					\n");
			sb.append("FROM (																					\n");
			sb.append("SELECT DISTINCT A.ID_SEQ																					\n");
			sb.append("     , A.MD_PSEQ																			\n");
			sb.append("     , A.MD_SITE_NAME																			\n");
			sb.append("     , A.MD_TYPE																					\n");
			sb.append("     , A.ID_TITLE																				\n");
			sb.append("     , A.ID_URL																					\n");
			sb.append("     , A.MD_SAME_CT																				\n");
			sb.append("     , A.MD_DATE																					\n");
			sb.append("     , FN_GET_ISSUE_DETAIL_CODE(A.ID_SEQ, 9) AS TYPE9											\n");
			if(listNum.equals("1")){
			sb.append("     , B2.IC_CODE 																		\n");
			//sb.append("     , (select ic_name from ISSUE_CODE where ic_type = 118 and ic_code = B2.IC_CODE ) AS TYPE118	\n");
			}
			sb.append("     , A.SG_SEQ																		\n");
			sb.append("     , IF(A.SG_SEQ = 25, A.ID_CONTENT,'') AS	ID_CONTENT								\n");
			sb.append("  FROM ISSUE_DATA A																				\n");
			
			ReportDataSuperBean.typeCodeBean getTBean = null;
			for(int i = 0; i < arTypeCode.size(); i++){
				getTBean = (ReportDataSuperBean.typeCodeBean)arTypeCode.get(i);
				sb.append("       INNER JOIN ISSUE_DATA_CODE C"+(i+1)+" ON A.ID_SEQ = C"+(i+1)+".ID_SEQ 					\n");
				sb.append("                                        AND C"+(i+1)+".IC_TYPE = "+getTBean.getIc_type()+"		\n");
				sb.append("                                        AND C"+(i+1)+".IC_CODE IN ("+getTBean.getIc_code()+")	\n");
			}
			if(listNum.equals("1")){
			sb.append("     , ISSUE_DATA_CODE A2																		\n");
			sb.append("     , ISSUE_DATA_CODE B2																		\n");
			}
			sb.append("   WHERE A.MD_DATE BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"'						\n");
			if(listNum.equals("1")){
				sb.append("   AND A.ID_SEQ = A2.ID_SEQ																	\n");
				sb.append("   AND A2.IC_TYPE = 18  AND A2.IC_CODE = 1												\n");
				sb.append("   AND A.ID_SEQ = B2.ID_SEQ																	\n");
				sb.append("   AND B2.IC_TYPE = 118																	\n");
			}
			sb.append("   AND A.ID_REPORTYN = 'Y'									      									\n");
			sb.append("   ) AA									      									\n");
			sb.append("   GROUP BY AA.MD_PSEQ									      									\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			ReportDataSuperBean.issueBean idBean = null;
			while( rs.next() ) {
				idBean = superBean.new issueBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setTemp1(rs.getString("TYPE9"));
				/*if(listNum.equals("1")){
					idBean.setTemp2(rs.getString("TYPE118"));
				}*/
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setId_content(rs.getString("ID_CONTENT"));
				arrResult.add(idBean);
			}
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
	
	
	public ArrayList getReportMaster (String type) {
		
		ArrayList arrResult = new ArrayList();
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			
			sb.append("SELECT IR_SEQ				\n");
			sb.append("     , IR_TITLE 				\n");
			sb.append("  FROM ISSUE_REPORT			\n");
			sb.append(" WHERE IR_TYPE = '"+type+"'	\n");
			sb.append(" ORDER BY IR_SEQ DESC		\n");
			sb.append(" LIMIT 10					\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			
			String[] bean = null;
			while( rs.next() ) {
				bean = new String[2];
				bean[0] = rs.getString("IR_SEQ");
				bean[1] = rs.getString("IR_TITLE");
				
				arrResult.add(bean);
			}
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
	
	public ArrayList getDailyReport(String company_code, String media_type, String sdate, String stime, String edate, String etime) {
		
		ArrayList arrResult = new ArrayList();
		ReportDataSuperBean.issueBean idBean = null;
		String site_name = "";
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			
			sb.append(" SELECT A.ID_SEQ																		\n");			
			sb.append("      , A.MD_SITE_NAME																\n");			
			sb.append("      , A.MD_TYPE																	\n");			
			sb.append("      , A.ID_TITLE																	\n");			
			sb.append("      , A.ID_URL																		\n");			
			sb.append("      , A.MD_SAME_CT																	\n");			
			sb.append("      , A.MD_DATE						                                            \n");
			sb.append("      , A.ID_SENTI    																\n");
			sb.append("   FROM ISSUE_DATA A	                                                                \n");
			sb.append("      , ISSUE_DATA_CODE B1															\n");			     		
			sb.append("  WHERE A.ID_SEQ = B1.ID_SEQ															\n");			   
			sb.append("    AND A.MD_DATE BETWEEN '"+ sdate +" "+ stime +"' AND '"+ edate +" "+ etime +"'	\n");					
			sb.append("    AND A.ID_USEYN = 'Y'									      						\n");			
			sb.append("    AND A.ID_REPORTYN = 'Y'				                                            \n");
			sb.append("    AND A.MD_TYPE = "+media_type+" #(1:언론, 2:개인미디어)                               \n");
			sb.append("    AND B1.IC_TYPE = 1                                                               \n");
			sb.append("    AND B1.IC_CODE = "+company_code+" #(1:자사, 2:경쟁사)                               \n");			

			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				idBean = superBean.new issueBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));			
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));		
				idBean.setId_title(rs.getString("ID_TITLE"));		
				idBean.setId_url(rs.getString("ID_URL"));		
				idBean.setMd_date(rs.getString("MD_DATE"));			
				idBean.setTemp1(rs.getString("ID_SENTI")); //성향		
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));		
				
				arrResult.add(idBean);
			}
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
	
	//by Woong 2015-11-25
	public ArrayList getIssueReport (String sdate, String stime, String edate, String etime) {
		
		ArrayList arrResult = new ArrayList();
		ReportDataSuperBean.issueBean idBean = null;
		String site_name = "";
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			/*
			sb.append("SELECT A.ID_SEQ																						\n");
			sb.append("		 , A.MD_SITE_NAME																				\n");
			sb.append("		 , A.S_SEQ																						\n");
			sb.append("		 , A.ID_TITLE																					\n");
			sb.append("		 , A.ID_URL																						\n");
			sb.append("		 , A.MD_DATE																					\n");
			sb.append("		 , IFNULL(IF(C.IC_CODE =1,'긍정',IF(C.IC_CODE=2,'부정',IF(C.IC_CODE=3,'중립',''))),'') AS TREND		\n");
			sb.append("		 , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 16) AS IMPACT												\n");
			sb.append("		 , A.MD_SAME_CT																					\n");
			sb.append("     FROM ISSUE_DATA A					 															\n");
			sb.append("  		,ISSUE_DATA_CODE_DETAIL C																	\n");
			sb.append("   WHERE A.MD_DATE BETWEEN '"+ sdate +" "+ stime +"' AND '"+ edate +" "+ etime +"'					\n");
			sb.append("   AND A.ID_SEQ = C.ID_SEQ																			\n");
			sb.append("   AND C.IC_TYPE = 9																					\n");
			sb.append("   AND C.IC_PTYPE IN ("+ dashType +")																\n");
			sb.append("   AND A.ID_REPORTYN ='Y'																			\n");
			sb.append("   GROUP BY A.ID_SEQ																					\n");
			sb.append("   ORDER BY A.MD_DATE																				\n");
			*/
			
			sb.append("SELECT A.ID_SEQ																						\n");
			sb.append(", A.MD_SITE_NAME																				\n");
			sb.append(", A.S_SEQ																					\n");
			sb.append(", A.ID_TITLE																					\n");
			sb.append(", A.ID_URL																					\n");
			sb.append(", A.MD_DATE																					\n");
			sb.append(", A.ID_SENTI																					\n");
			//sb.append(", FN_ISSUE_NAME(C.IC_CODE, C.IC_TYPE) AS TYPE												\n");
			sb.append(", FN_GET_ISSUE_CODE_NAME_CONCAT(A.ID_SEQ, 15) AS IMPORTTANT									\n");
			sb.append(", A.MD_SAME_CT																				\n");
			sb.append("FROM ISSUE_DATA A																			\n");
			sb.append(", ISSUE_DATA_CODE B																			\n");
			//sb.append(", ISSUE_DATA_CODE C																			\n");
			sb.append("WHERE A.MD_DATE BETWEEN '"+ sdate +" "+ stime +"' AND '"+ edate +" "+ etime +"'				\n");					
			sb.append("AND A.ID_SEQ = B.ID_SEQ																		\n");
			//sb.append("AND A.ID_SEQ = C.ID_SEQ																		\n");
			//sb.append("AND B.IC_TYPE = 5																			\n");
			//sb.append("AND B.IC_CODE = 2																			\n");
			//sb.append("AND C.IC_TYPE = 6																			\n");
			//sb.append("AND C.IC_CODE IN (7,8,9,10,11,12,13,14,15)													\n");
			sb.append("GROUP BY A.ID_SEQ																			\n");
			sb.append("ORDER BY A.MD_DATE DESC																		\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				idBean = superBean.new issueBean();
				
				/*
				site_name = "";
				if(rs.getString("MD_SITE_NAME") != null && rs.getString("MD_SITE_NAME").length() > 0){
					if(rs.getString("S_SEQ").equals("2196")){ //네이버
						site_name = "네이버_"+rs.getString("MD_SITE_NAME").substring(1);
					}else if(rs.getString("S_SEQ").equals("2199")){ //다음
						site_name = "다음_"+rs.getString("MD_SITE_NAME").substring(1);
					}else if(rs.getString("S_SEQ").equals("3883")){ //네이트
						site_name = "네이트_"+rs.getString("MD_SITE_NAME").substring(1);
					}else{
						site_name = rs.getString("MD_SITE_NAME");
					}
				}
				idBean.setMd_site_name(site_name);
				*/
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setTemp1(rs.getString("ID_SENTI")); //성향
				idBean.setTemp2(rs.getString("IMPORTTANT")); //영향력
				idBean.setTemp3("1");
				//idBean.setTemp3(rs.getString("TYPE"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				arrResult.add(idBean);
			}
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
	
	//by Woong 2015-11-25
		public ArrayList getMajorIssue (String sdate, String stime, String edate, String etime) {
			
			ArrayList arrResult = new ArrayList();
			ReportDataSuperBean.typeCodeBean icBean = null;
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				sb = new StringBuffer();
				
				sb.append("SELECT C.IC_CODE AS CODE																				\n");
				sb.append("		 , C.IC_NAME																					\n");
				sb.append("		 , C.IC_REGDATE																					\n");
				sb.append("		 , COUNT(A.ID_SEQ) AS CNT																		\n");
				sb.append("     FROM ISSUE_DATA A					 															\n");
				sb.append("  		,ISSUE_DATA_CODE B																			\n");
				sb.append("  		,ISSUE_CODE C																				\n");
				sb.append("   WHERE A.MD_DATE BETWEEN '"+ sdate +" "+ stime +"' AND '"+ edate +" "+ etime +"'					\n");
				sb.append("   AND A.ID_SEQ = B.ID_SEQ																			\n");
				sb.append("   AND B.IC_TYPE = 19																				\n");
				sb.append("   AND B.IC_TYPE = C.IC_TYPE																			\n");
				sb.append("   AND B.IC_CODE = C.IC_CODE 																		\n");
				sb.append("   AND A.ID_REPORTYN ='Y'																			\n");
				sb.append("   GROUP BY C.IC_CODE																				\n");
				sb.append("   ORDER BY CNT DESC																					\n");
				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				while( rs.next() ) {
					icBean = superBean.new typeCodeBean();
					icBean.setIc_code(rs.getString("CODE"));
					icBean.setIc_name(rs.getString("IC_NAME"));
					icBean.setIc_regdate(rs.getString("IC_REGDATE"));
					icBean.setCnt(rs.getString("CNT"));

					arrResult.add(icBean);
				}
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
		 * 고정 연관어
		 * */
		public ArrayList<String[]> getgetVocTrendReportRelationkeyTypeList(String sdate, String stime, String edate, String etime, String targetCode, String sortBy){
			//카테고리 
			String category_type = "17"; //사업운영본무
			String category_code = targetCode;
			String product_type = "3";
			String relation_type ="18";
			//String relation_code ="";
			int top_limit = 5;
			
			String[] top_code = null;
			String[] top_info = null;
			
			ArrayList<String[]> result = new ArrayList<String[]>();
			String[] sub_result = null;

			/*제품 정보*/
			ArrayList<String[]> procudtList = getProductListTop(sdate, stime, edate, etime, category_type, category_code, product_type, top_limit);
			/*전기 일자 구하기*/
			String[] prevDate = getPrevDate(sdate, stime, edate, etime);
					
			int tmp_cnt_p = 0;
			
			for(int i=0; i<procudtList.size(); i++){
				
				/*TOP연관어*/
				top_code = getTopCode3Depth2(sdate, stime, edate, etime, prevDate[0], prevDate[1], category_type, category_code, product_type, procudtList.get(i)[0], relation_type, sortBy);
				
				/*전기 수량*/
				tmp_cnt_p = getPcnt(sdate, stime, edate, etime, prevDate[0], prevDate[1], category_type, category_code, product_type, procudtList.get(i)[0], relation_type, top_code[0]);
				
				/*TOP 정보*/
				top_info = getTopKeyInfo(sdate, stime, edate, etime, category_type, category_code, product_type, procudtList.get(i)[0], relation_type, top_code[0], tmp_cnt_p);
				
				/*최종*/
				sub_result = new String[5];					
				sub_result[0] = (null==top_info[0])?"-":top_info[0];
				sub_result[1] = (null==top_info[1] || null==top_info[0])?"-":top_info[1];
				sub_result[2] = (null==top_info[2] || null==top_info[0])?"0":top_info[2];
				sub_result[3] = (null==top_info[3] || null==top_info[0])?"0":top_info[3];
				sub_result[4] = (null==top_info[4] || null==top_info[0])?"0":top_info[4];
				
				if( null!=top_code[0] && !"".equals(top_code[0])
					&& null!=top_info[0] && !"".equals(top_info[0])	
				){
					result.add(sub_result);
				}
			}
			
			/*정렬*/
			double[] chk_sort = new double[result.size()];
			String[] tmp_category_data = null;
			for(int i=0; i<result.size(); i++){
				tmp_category_data = result.get(i);
				
				//증감률
				if("2".equals(sortBy)){  
					chk_sort[i] = Double.parseDouble(tmp_category_data[4]);
					//수집건수
				}else if("1".equals(sortBy)){  
					chk_sort[i] = Integer.parseInt(tmp_category_data[2]);
				}
			}
				
			
			double temp = 0;
			String[] temp_arr = null;
			for( int i=0; i < chk_sort.length-1; i++){
	            for (int j=i+1; j<chk_sort.length; j++){               
	                if(chk_sort[i] < chk_sort[j]){ //오름차순 ; 큰수를 뒤로, data[i] > data[j]
	                    //데이터 값 체인지
	                    temp = chk_sort[i];
	                    temp_arr = result.get(i);
	                    
	                    chk_sort[i] = chk_sort[j];
	                    result.set(i, result.get(j));
	                    
	                    chk_sort[j] = temp;
	                    result.set(j, temp_arr);
	                }
	            }
	        }
			
			
			
			return result;
		}
		
		public ArrayList<String[]> getFoodTrendReportTopList(String sdate, String edate, String stime, String etime, String category_type, String  type_group, String targetType, String targetCode, int limit_length){
			ArrayList<String[]> result = new ArrayList<String[]>();
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();		
				
				sb = new StringBuffer();				                                                                                                                                                          
				sb.append("   				  SELECT C.IC_CODE                                                                                                                                                    \n");
				sb.append("                        , FN_ISSUE_NAME(C.IC_CODE, C.IC_TYPE) AS IC_NAME                                                                                                               \n");
				sb.append("                        , COUNT(*) AS CNT                                                                                                                                              \n");
				sb.append("                     FROM ISSUE_DATA A                                                                                                                                                 \n");
				sb.append("                        , ISSUE_DATA_CODE B                                                                                                                                            \n");
				sb.append("                        , ISSUE_DATA_CODE C                                                                                                                                            \n");
				sb.append("                    WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"' /*범위 설정*/                                                                                  \n");
				sb.append("                      AND A.ID_SEQ = B.ID_SEQ                                                                                                                                          \n");
				sb.append("                      AND A.ID_SEQ = C.ID_SEQ                                                                                                                                          \n");
				sb.append("                      AND B.IC_TYPE = "+targetType+"                                                                                                                                                \n");
				sb.append("                      AND B.IC_CODE = "+targetCode+" /*회사구분 1,2,3,4,5*/                                                                                                                             \n");
				sb.append("                      AND C.IC_TYPE = "+category_type+"                                                                                                                                                \n");
				sb.append("                      AND C.IC_CODE IN ("+type_group+") /*게시물 유형3*/           \n");
				sb.append("                    GROUP BY C.IC_CODE                                                                                                                                                 \n");
				sb.append("                    ORDER BY COUNT(*) DESC                                                                                                                                             \n");
				sb.append("                    LIMIT "+limit_length+"                                                                                                                                                            \n");
				sb.append("                                                                                                                                                                                       \n");
				sb.append("                                                                                                                                                                                       \n");
				                                                                                                                                   
				//System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				System.out.println(pstmt);
				String[] data = new String[3]; 
				while( rs.next() ) {
					data = new String[3];
					data[0] = rs.getString("IC_CODE");
					data[1] = rs.getString("IC_NAME");
					data[2] = su.digitFormat(rs.getString("CNT"));
					
					result.add(data);
				}				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			return result;
		}
			
		
		/**
		 * 연관어
		 * */
		public ArrayList<String[]> getVocTrendReportRelationkeyList(String sdate, String stime, String edate, String etime, String targetCode, String sortBy){
			//카테고리 
			String category_type = "17"; //사업운영본무
			String category_code = targetCode;
			String product_type = "3";
			String relation_type ="18";
			int top_limit = 5;
			
			String[] top_code = null;
			String[] top_info = null;
			
			ArrayList<String[]> result = new ArrayList<String[]>();
			String[] sub_result = null;

			/*제품 정보*/
			ArrayList<String[]> procudtList = getProductListTop(sdate, stime, edate, etime, category_type, category_code, product_type, top_limit);
			/*전기 일자 구하기*/
			String[] prevDate = getPrevDate(sdate, stime, edate, etime);
					
			int tmp_cnt_p = 0;
			
			for(int i=0; i<procudtList.size(); i++){
				
				/*TOP연관어*/
				top_code = getTopCodeRelation(sdate, stime, edate, etime, prevDate[0], prevDate[1], category_type, category_code, product_type, procudtList.get(i)[0], relation_type, sortBy);
				
				/*전기 수량*/
				tmp_cnt_p = getRelPcnt(sdate, stime, edate, etime, prevDate[0], prevDate[1], category_type, category_code, product_type, procudtList.get(i)[0], top_code[0]);
				
				/*TOP 정보*/
				top_info = getRelTopKeyInfo(sdate, stime, edate, etime, category_type, category_code, product_type, procudtList.get(i)[0], top_code[0], tmp_cnt_p);
				
				/*최종*/
				sub_result = new String[5];					
				sub_result[0] = (null==top_info[0])?"-":top_info[0];
				sub_result[1] = (null==top_info[1] || null==top_info[0])?"-":top_info[1];
				sub_result[2] = (null==top_info[2] || null==top_info[0])?"-":top_info[2];
				sub_result[3] = (null==top_info[3] || null==top_info[0])?"0":top_info[3];
				sub_result[4] = (null==top_info[4] || null==top_info[0])?"-":top_info[4];
				
				if( null != top_code[0] && !"".equals(top_code[0])
					&& null != top_info[0] && !"".equals(top_info[0])						
				){
					result.add(sub_result);
				}
			}
			
			/*정렬*/
			double[] chk_sort = new double[result.size()];
			String[] tmp_category_data = null;
			for(int i=0; i<result.size(); i++){
				tmp_category_data = result.get(i);
				
				//증감률
				if("2".equals(sortBy)){  
					chk_sort[i] = Double.parseDouble(tmp_category_data[4]);
					//수집건수
				}else if("1".equals(sortBy)){  
					chk_sort[i] = Integer.parseInt(tmp_category_data[2]);
				}
			}
				
			
			double temp = 0;
			String[] temp_arr = null;
			for( int i=0; i < chk_sort.length-1; i++){
	            for (int j=i+1; j<chk_sort.length; j++){               
	                if(chk_sort[i] < chk_sort[j]){ //오름차순 ; 큰수를 뒤로, data[i] > data[j]
	                    //데이터 값 체인지
	                    temp = chk_sort[i];
	                    temp_arr = result.get(i);
	                    
	                    chk_sort[i] = chk_sort[j];
	                    result.set(i, result.get(j));
	                    
	                    chk_sort[j] = temp;
	                    result.set(j, temp_arr);
	                }
	            }
	        }
			
			
			
			
			return result;
		}
																			
		public ArrayList<String[][]> getVocTrendReportProductTopList(String sdate, String stime, String edate, String etime, String targetCode, String sortBy){
			
			
			//카테고리 
			String category_type = "17"; //사업운영본무
			String category_code = targetCode;
			String group_types = "6"; 
			String group_codes = "3,4"; //3:클레임, 4:칭찬/제안/
			String product_type = "3";
			//String product_code = "";
			String mid_category_type ="7";
			String mid_category_codes="";
			int top_limit = 3;
			
			String[] top_code = null;
			String[] top_info = null;
			
			ArrayList<String[][]> result = new ArrayList<String[][]>();
			String[][] sub_result = null;

			/*제품 정보*/
			ArrayList<String[]> procudtList = getProductListTop(sdate, stime, edate, etime, category_type, category_code, product_type, top_limit);
			/*전기 일자 구하기*/
			String[] prevDate = getPrevDate(sdate, stime, edate, etime);
					
			String[] tmp_group = group_codes.split(",");
			int tmp_cnt_p = 0;
			
			for(int i=0; i<procudtList.size(); i++){
				sub_result = new String[2][6];				
				for(int t=0; t<tmp_group.length; t++){
					sub_result[t][0] = procudtList.get(i)[2];
					/*대분류, 중분류 CODE*/
					mid_category_codes = getMidCategory2Depth(group_types, tmp_group[t]);
					
					/*TOP연관어*/
					top_code = getTopCode3Depth(sdate, stime, edate, etime, prevDate[0], prevDate[1], category_type, category_code, product_type, procudtList.get(i)[0], mid_category_type, mid_category_codes, sortBy);
					
					/*전기 수량*/
					tmp_cnt_p = getPcnt(sdate, stime, edate, etime, prevDate[0], prevDate[1], category_type, category_code, product_type, procudtList.get(i)[0], mid_category_type, top_code[0]);
					
					/*TOP 정보*/
					top_info = getTopKeyInfo(sdate, stime, edate, etime, category_type, category_code, product_type, procudtList.get(i)[0], mid_category_type, top_code[0], tmp_cnt_p);
					
					/*최종*/
					//sub_result[t][0] = (null==top_info[0])?"-":top_info[0];
					sub_result[t][1] = (null==top_info[1] || null==top_info[0] || "".equals(top_info[1]))?"-":top_info[1];
					sub_result[t][2] = (null==top_info[2] || null==top_info[0])?"0":top_info[2];
					sub_result[t][3] = (null==top_info[3] || null==top_info[0])?"0":top_info[3];
					sub_result[t][4] = (null==top_info[4] || null==top_info[0])?"0":top_info[4];
				}
				
				result.add(sub_result);
			}
			
			/*정렬*/
			double[] chk_sort = new double[result.size()];
			String[][] tmp_category_data = null;
			for(int i=0; i<result.size(); i++){
				tmp_category_data = result.get(i);
				
				//증감률
				if("2".equals(sortBy)){  
					chk_sort[i] = Double.parseDouble(tmp_category_data[0][4])+Double.parseDouble(tmp_category_data[1][4]);
					//수집건수
				}else if("1".equals(sortBy)){  
					chk_sort[i] = Integer.parseInt(tmp_category_data[0][2])+Integer.parseInt(tmp_category_data[1][2]);
				}
			}
				
			
			double temp = 0;
			String[][] temp_arr = null;
			for( int i=0; i < chk_sort.length-1; i++){
	            for (int j=i+1; j<chk_sort.length; j++){               
	                if(chk_sort[i] < chk_sort[j]){ //오름차순 ; 큰수를 뒤로, data[i] > data[j]
	                    //데이터 값 체인지
	                    temp = chk_sort[i];
	                    temp_arr = result.get(i);
	                    
	                    chk_sort[i] = chk_sort[j];
	                    result.set(i, result.get(j));
	                    
	                    chk_sort[j] = temp;
	                    result.set(j, temp_arr);
	                }
	            }
	        }
			
			return result;
		}
		
		
		public int getRelPcnt(String sdate, String stime, String edate, String etime, String pSdate, String pEdate, String category_type, String category_code, String product_type, String product_code, String topCode){
			int result = 0;
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();				
				
				sb = new StringBuffer();				
				sb.append("SELECT COUNT(*) AS CNT_P                                                                                 \n");
				sb.append("   FROM ISSUE_DATA A                                                                                     \n");
				sb.append("      , ISSUE_DATA_CODE B                                                                                \n");
				sb.append("      , ISSUE_DATA_CODE C                                                                                \n");
				sb.append("      , RELATION_KEYWORD_MAP D                                                                                \n");
				sb.append("  WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'		            \n");
				sb.append("    AND A.ID_SEQ = B.ID_SEQ                                                                              \n");
				sb.append("    AND A.ID_SEQ = C.ID_SEQ                                                                              \n");
				sb.append("    AND A.ID_SEQ = D.ID_SEQ                                                                              \n");
				sb.append("    AND B.IC_TYPE = "+category_type+"                                                                     \n");
				sb.append("    AND B.IC_CODE = "+category_code+"                                                                     \n");
				sb.append("    AND C.IC_TYPE = "+product_type+"                                                                      \n");
				sb.append("    AND C.IC_CODE = "+product_code+"                                                                      \n");
				sb.append("    AND D.RK_SEQ = "+topCode+"                                                                            \n");
				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				if( rs.next() ) {
					result = rs.getInt("CNT_P");
				}				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		
		public int getPcnt(String sdate, String stime, String edate, String etime, String pSdate, String pEdate, String category_type, String category_code, String product_type, String product_code, String  mid_category_type, String topCode){
			int result = 0;
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();				
				
				sb = new StringBuffer();				
				sb.append("SELECT COUNT(*) AS CNT_P                                                                                 \n");
				sb.append("   FROM ISSUE_DATA A                                                                                     \n");
				sb.append("      , ISSUE_DATA_CODE B                                                                                \n");
				sb.append("      , ISSUE_DATA_CODE C                                                                                \n");
				sb.append("      , ISSUE_DATA_CODE D                                                                                \n");
				sb.append("  WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'		            \n");
				sb.append("    AND A.ID_SEQ = B.ID_SEQ                                                                              \n");
				sb.append("    AND A.ID_SEQ = C.ID_SEQ                                                                              \n");
				sb.append("    AND A.ID_SEQ = D.ID_SEQ                                                                              \n");
				sb.append("    AND B.IC_TYPE = "+category_type+"                                                                     \n");
				sb.append("    AND B.IC_CODE = "+category_code+"                                                                     \n");
				sb.append("    AND C.IC_TYPE = "+product_type+"                                                                      \n");
				sb.append("    AND C.IC_CODE = "+product_code+"                                                                      \n");
				sb.append("    AND D.IC_TYPE = "+mid_category_type+"                                                                 \n");
				sb.append("    AND D.IC_CODE = "+topCode+"                                                                            \n");
				
				//System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				if( rs.next() ) {
					result = rs.getInt("CNT_P");
				}				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		
	    public JSONObject getChannelCount(String ir_sdate, String ir_edate, String ir_stime, String ir_etime){	
	    	JSONObject result = new JSONObject();
	    	JSONArray arr = null; 
	    	int max_length = 0;
	    	int tmp_length = 0;
	   	 	try {
		   	 	dbconn = new DBconn();
		   	 	dbconn.getDBCPConnection();
		   	 	sb = new StringBuffer();
				sb.append("#유통 경로별 정보 현황 - 채널별                                                          										\n");	
				sb.append("SELECT  SG.SG_NAME AS CHANNEL                                                    			\n");	
				sb.append("      , SG.SG_SEQ                                                                			\n");	
				sb.append("      , COUNT(*) AS CNT                                                          			\n");	
				sb.append("   FROM ISSUE_DATA A	                                                            			\n");	
				sb.append("      , ISSUE_DATA_CODE B1	                                                    			\n");	
				sb.append("      , SITE_GROUP SG                                                            			\n");	
				sb.append("  WHERE A.ID_SEQ = B1.ID_SEQ		                                                			\n");	
				sb.append("    AND A.SG_SEQ = SG.SG_SEQ                                                     			\n");	
				sb.append("    AND SG.USEYN = 'Y'                                                           			\n");	
				sb.append("    AND A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'		\n");					
				sb.append("    AND A.ID_USEYN = 'Y'									      								\n");				
				sb.append("    AND B1.IC_TYPE = 1                                                           			\n");	
				sb.append("    AND B1.IC_CODE = 1 #(1:자사, 2:경쟁사)					                        			\n");	
				sb.append("  GROUP BY A.SG_SEQ                                                              			\n");	
				sb.append("  ORDER BY CNT DESC  ##정보량 높은순으로 요청				                            			\n");	
			
				
			
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("category", rs.getString("CHANNEL"));			
				obj.put("column-1", rs.getString("CNT"));
				//item.put("CHANNEL", rs.getString("CHANNEL"));
				//item.put("SG_SEQ", rs.getString("SG_SEQ"));
				//item.put("CNT", rs.getString("CNT"));
				arr.put(obj);
			}
			
			result.put("LIST", arr);
				
	   	 	}catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	        }
	   	 	return result;
	    }

	    
	    public JSONObject getChannelSentiCount(String ir_sdate, String ir_edate, String ir_stime, String ir_etime){	
	    	JSONObject result = new JSONObject();
	    	JSONArray arr = null; 
	    	int max_length = 0;
	    	int tmp_length = 0;
	   	 	try {
		   	 	dbconn = new DBconn();
		   	 	dbconn.getDBCPConnection();
		   	 	sb = new StringBuffer();
				sb.append("#유통 경로별 정보 현황 - 채널별(긍부정)                                                              	\n");	
                sb.append("                                                                                             \n");	
				sb.append("SELECT SG.SG_NAME AS CHANNEL                                                                 \n");	
				sb.append("     , SG.SG_SEQ                                                                             \n");	
				sb.append("     , COUNT(*) AS CNT #전체                                                                           						\n");	
				sb.append("     , SUM(A.ID_SENTI = 1) AS 'POS'#긍                                                                 					\n");	
				sb.append("     , SUM(A.ID_SENTI = 2) AS 'NEG'#부                                                                 					\n");	
				sb.append("     , SUM(A.ID_SENTI = 3) AS 'NEU'#중                                                                 					\n");	
				sb.append("  FROM ISSUE_DATA A	                                                                        \n");	
				sb.append("     , ISSUE_DATA_CODE B1	                                                                \n");	
				sb.append("     , SITE_GROUP SG                                                                         \n");	
				sb.append(" WHERE A.ID_SEQ = B1.ID_SEQ		                                                            \n");	
				sb.append("   AND A.SG_SEQ = SG.SG_SEQ                                                                  \n");	
				sb.append("   AND SG.USEYN = 'Y'                                                                        \n");	
				sb.append("   AND A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'			\n");					
				sb.append("   AND A.ID_USEYN = 'Y'									      								\n");	
				sb.append("   AND B1.IC_TYPE = 1                                                                        \n");	
				sb.append("   AND B1.IC_CODE = 1 #(1:자사, 2:경쟁사)				                                        \n");	
				sb.append(" GROUP BY A.SG_SEQ                                                                           \n");	
				sb.append(" ORDER BY SG.SG_ORDER                                                                        \n");	
			
				
			
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("category", rs.getString("CHANNEL"));	
				if(!"0".equals(rs.getString("POS"))) {
					obj.put("column-1", rs.getString("POS"));					
				}
				if(!"0".equals(rs.getString("NEG"))) {
					obj.put("column-2", rs.getString("NEG"));					
				}
				if(!"0".equals(rs.getString("NEU"))) {
					obj.put("column-3", rs.getString("NEU"));					
				}
				arr.put(obj);
				
			}
			
			result.put("LIST", arr);
				
	   	 	}catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	        }
	   	 	return result;
	    }

		public ArrayList<HashMap<String, String>> getDailyInformationList(String id_seqs, String company_code, String media_type){			
			ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
			String[] tmp = new String[3];
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				
				sb = new StringBuffer();				
				sb.append(" SELECT A.ID_SEQ																	\n");			
				sb.append("      , A.MD_SITE_NAME															\n");			
				sb.append("      , A.MD_TYPE																\n");			
				sb.append("      , A.ID_TITLE																\n");			
				sb.append("      , A.ID_URL																	\n");			
				sb.append("      , A.MD_SAME_CT																\n");			
				sb.append("      , DATE_FORMAT(A.MD_DATE, '%Y-%m-%d') AS MD_DATE					        \n");
				sb.append("      , A.ID_SENTI    															\n");
				sb.append("   FROM ISSUE_DATA A                                                             \n");
				sb.append("      , ISSUE_DATA_CODE B	                                                    \n");
				sb.append("  WHERE A.ID_SEQ IN ("+id_seqs+")												\n");			   
				sb.append("    AND A.ID_SEQ = B.ID_SEQ                           							\n");
				sb.append("    AND A.MD_TYPE = "+media_type+" #(1:언론, 2:개인미디어)                           \n");
				sb.append("    AND B.IC_TYPE = 1                                                            \n");
				sb.append("    AND B.IC_CODE = "+company_code+" #(1:자사, 2:경쟁사)                            \n");			
				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				HashMap<String, String> item = new HashMap<String, String>();
				while(rs.next()) {
					item = new HashMap<String, String>();
					item.put("ID_SEQ", rs.getString("ID_SEQ"));
					item.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
					item.put("ID_TITLE", rs.getString("ID_TITLE"));
					item.put("ID_URL", rs.getString("ID_URL"));
					item.put("MD_DATE", rs.getString("MD_DATE"));
					item.put("ID_SENTI", rs.getString("ID_SENTI"));
					item.put("MD_SAME_CT", rs.getString("MD_SAME_CT"));
					
					result.add(item);
				}
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		
		public ArrayList<String[][]> getVocTrendReportCategoryDataList(String sdate, String stime, String edate, String etime, String sortBy){
			//카테고리 
			String category_type = "17"; //사업운영본무
			String category_codes = "1,2,3,4,5";
			String group_types = "6"; 
			String group_codes = "3,4"; //3:클레임, 4:칭찬/제안/
			String mid_category_type = "8";
			String mid_category_codes = "";
			String[] top_code = new String[2];
			String[] top_info = null;
			//String realation_key = "";
			
			ArrayList<String[][]> result = new ArrayList<String[][]>();
			String[][] sub_result = null;

			/*카테고리 정보*/
			//ArrayList<String[]> categoryList = getCategory(sdate, stime, edate, etime, category_type, category_codes);
			//고객사 요청 출력순서 고정
			ArrayList<String[]> categoryList = getCategoryOrder(sdate, stime, edate, etime, category_type, category_codes);
			
			/*전기 일자 구하기*/
			String[] prevDate = getPrevDate(sdate, stime, edate, etime);
					
			String[] tmp_group = group_codes.split(",");
			String[] type_name_group = null;
			
			for(int i=0; i<categoryList.size(); i++){
				sub_result = new String[2][9];				
				mid_category_codes = "";
				for(int t=0; t<tmp_group.length; t++){
					sub_result[t][0] = categoryList.get(i)[1];
					/*대분류, 중분류 CODE*/
					mid_category_codes = getMidCategory3Depth(group_types, tmp_group[t]);
					
					/*TOP연관어*/
					top_code = getTopCode2Depth(sdate, stime, edate, etime, prevDate[0], prevDate[1], category_type, categoryList.get(i)[0], mid_category_type, mid_category_codes, sortBy);
					
					/*TOP 정보*/
					top_info = getCategoryTopInfo(sdate, stime, edate, etime, prevDate[0], prevDate[1], category_type, categoryList.get(i)[0], mid_category_type, top_code[0]);
					
					/*대분류, 중분류 명*/
					type_name_group = getParentTypeName(mid_category_type, top_code[0]);
					/*연관키워드*/
					//realation_key = getRelationkey(sdate, stime, edate, etime,mid_category_type, top_code[0]);	
					
					/*최종*/
					sub_result[t][1] = (null==type_name_group[0])?"-":type_name_group[0];
					sub_result[t][2] = (null==type_name_group[1])?"-":type_name_group[1];
					sub_result[t][3] = (null==type_name_group[2] || null==type_name_group[1])?"-":type_name_group[2];
					sub_result[t][4] = (null==top_info[0] || null==type_name_group[1])?"-":top_info[0];
					sub_result[t][5] = (null==top_info[1] || null==type_name_group[1])?"0":top_info[1];// 당기 수집건수
					sub_result[t][6] = (null==top_info[2] || null==type_name_group[1])?"0":top_info[2]; //증감건수
					sub_result[t][7] = (null==top_info[3] || null==type_name_group[1])?"0":top_info[3]; 
					sub_result[t][8] = (null==top_info[4] || null==type_name_group[1])?"0":top_info[4]; //증감률
					//sub_result[t][9] = realation_key;
				}
				
				result.add(sub_result);
			}
			
			
			/*정렬*/
			//고객사 요청 출력순서 고정 2020.11
		/*	double[] chk_sort = new double[result.size()];
			String[][] tmp_category_data = null;
			for(int i=0; i<result.size(); i++){
				tmp_category_data = result.get(i);
				
				//증감건수
				if("3".equals(sortBy)){
					chk_sort[i] = Integer.parseInt(tmp_category_data[0][6])+Integer.parseInt(tmp_category_data[1][6]);
				//증감률
				}else if("2".equals(sortBy)){  
					chk_sort[i] = Double.parseDouble(tmp_category_data[0][8])+Double.parseDouble(tmp_category_data[1][8]);
				//수집건수
				}else if("1".equals(sortBy)){  
					chk_sort[i] = Integer.parseInt(tmp_category_data[0][5])+Integer.parseInt(tmp_category_data[1][5]);
				}
			}
			
			
			double temp = 0;
			String[][] temp_arr = null;
			for( int i=0; i < chk_sort.length-1; i++){
	            for (int j=i+1; j<chk_sort.length; j++){               
	                if(chk_sort[i] < chk_sort[j]){ //오름차순 ; 큰수를 뒤로, data[i] > data[j]
	                    //데이터 값 체인지
	                    temp = chk_sort[i];
	                    temp_arr = result.get(i);
	                    
	                    chk_sort[i] = chk_sort[j];
	                    result.set(i, result.get(j));
	                    
	                    chk_sort[j] = temp;
	                    result.set(j, temp_arr);
	                }
	            }
	        }*/
			return result;
		}
		
		public String getRelationkey( String sdate,  String stime,  String edate,  String etime, String type, String code){			
			String result = "";
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();				
				
				sb = new StringBuffer();				
				sb.append("SELECT  RK_NAME																											\n"); 
				sb.append("  FROM RELATION_KEYWORD_R                                                                                                \n");
				sb.append(" WHERE RK_USE = 'Y'                                                                                                      \n");
				sb.append("  AND RK_SEQ = (                                                                                                         \n");
				sb.append("               SELECT A.RK_SEQ FROM (                                                                                    \n");
				sb.append("                      SELECT COUNT(*) AS CNT, RK_SEQ                                                                     \n");
				sb.append("                        FROM RELATION_KEYWORD_MAP                                                                        \n");
				sb.append("                       WHERE ID_SEQ IN (  SELECT GROUP_CONCAT(A.ID_SEQ) AS ID_SEQS                                       \n");
				sb.append("                                                FROM ISSUE_DATA A                                                        \n");
				sb.append("                                                    , ISSUE_DATA_CODE B                                                  \n");
				sb.append("                                          WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'        \n");
				sb.append("                                              AND A.ID_SEQ = B.ID_SEQ                                                    \n");
				sb.append("                                              AND B.IC_TYPE = "+type+"                                                          \n");
				sb.append("                                              AND B.IC_CODE = "+code+"                                                         \n");
				sb.append("                                            GROUP BY A.ID_SEQ                                                            \n");
				sb.append("                      )                                                                                                  \n");
				sb.append("                      GROUP BY RK_SEQ                                                                                    \n");
				sb.append("                  )A                                                                                                     \n");
				sb.append("                ORDER BY A.CNT DESC                                                                                      \n");
				sb.append("                    LIMIT 1                                                                                              \n");
				sb.append("  )                                                                                                                      \n");
				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				if( rs.next() ) {
					result = rs.getString("RK_NAME");
				}				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		
		
		public String[] getRelTopKeyInfo(String sdate, String stime, String edate, String etime, String category_type, String category_code, String product_type, String product_code, String top_code, int cnt_p){			
			String[] result = new String[5];
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();		
				
				sb = new StringBuffer();				
				sb.append("  SELECT FN_ISSUE_NAME(C.IC_CODE, C.IC_TYPE) AS PRODUCT                                                                  \n");
				sb.append("       , IFNULL((SELECT RK_NAME FROM RELATION_KEYWORD_R WHERE RK_SEQ = D.RK_SEQ LIMIT 1),'') AS RK_NAME                                                                   \n");
				sb.append("       , COUNT(*) AS CNT /*건수*/                                                                                          \n");
				sb.append("       , IF(("+cnt_p+" - COUNT(*)) = 0, 0, IF(("+cnt_p+" - COUNT(*)) > 0, -1, 1)) AS FACTOR_POINT /*증감 화살표*/           \n");
				sb.append("       , ROUND(IFNULL((ABS("+cnt_p+" - COUNT(*)) / "+cnt_p+" * 100),0),1) AS FACTOR_PER /*증감율*/                       \n");
				sb.append("    FROM ISSUE_DATA A                                                                                                    \n");
				sb.append("       , ISSUE_DATA_CODE B                                                                                               \n");
				sb.append("       , ISSUE_DATA_CODE C                                                                                               \n");
				sb.append("       , RELATION_KEYWORD_MAP D                                                                                               \n");
				sb.append("   WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'                                       \n");
				sb.append("     AND A.ID_SEQ = B.ID_SEQ                                                                                             \n");
				sb.append("     AND A.ID_SEQ = C.ID_SEQ                                                                                             \n");
				sb.append("     AND A.ID_SEQ = D.ID_SEQ                                                                                             \n");
				sb.append("     AND B.IC_TYPE = "+category_type+"                                                                                                  \n");
				sb.append("     AND B.IC_CODE = "+category_code+" /*사업운영본부*/                                                                                        \n");
				sb.append("     AND C.IC_TYPE = "+product_type+"                                                                                                   \n");
				sb.append("     AND C.IC_CODE = "+product_code+" /*회사3*/                                                                                          \n");
				sb.append("     AND D.RK_SEQ = "+top_code+" /*유형2*/                                                                                           \n");
				                                                                                                                                   
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				if( rs.next() ) {
					result[0] = rs.getString("PRODUCT");
					result[1] = rs.getString("RK_NAME");
					result[2] = rs.getString("CNT");
					result[3] = rs.getString("FACTOR_POINT");
					result[4] = rs.getString("FACTOR_PER");
				}				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		public String[] getTopKeyInfo(String sdate, String stime, String edate, String etime, String category_type, String category_code, String product_type, String product_code, String mid_category_type, String top_code, int cnt_p){			
			String[] result = new String[5];
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();		
				
				sb = new StringBuffer();				
				sb.append("  SELECT FN_ISSUE_NAME(C.IC_CODE, C.IC_TYPE) AS PRODUCT                                                                  \n");
				sb.append("       , FN_ISSUE_NAME(D.IC_CODE, D.IC_TYPE) AS TYPE                                                                     \n");
				sb.append("       , COUNT(*) AS CNT /*건수*/                                                                                          \n");
				sb.append("       , IF(("+cnt_p+" - COUNT(*)) = 0, 0, IF(("+cnt_p+" - COUNT(*)) > 0, -1, 1)) AS FACTOR_POINT /*증감 화살표*/           \n");
				sb.append("        , ROUND(IFNULL((ABS("+cnt_p+" - COUNT(*)) / "+cnt_p+" * 100),0),1) AS FACTOR_PER /*증감율*/                       \n");
				sb.append("    FROM ISSUE_DATA A                                                                                                    \n");
				sb.append("       , ISSUE_DATA_CODE B                                                                                               \n");
				sb.append("       , ISSUE_DATA_CODE C                                                                                               \n");
				sb.append("       , ISSUE_DATA_CODE D                                                                                               \n");
				sb.append("   WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'                                       \n");
				sb.append("     AND A.ID_SEQ = B.ID_SEQ                                                                                             \n");
				sb.append("     AND A.ID_SEQ = C.ID_SEQ                                                                                             \n");
				sb.append("     AND A.ID_SEQ = D.ID_SEQ                                                                                             \n");
				sb.append("     AND B.IC_TYPE = "+category_type+"                                                                                                  \n");
				sb.append("     AND B.IC_CODE = "+category_code+" /*사업운영본부*/                                                                                        \n");
				sb.append("     AND C.IC_TYPE = "+product_type+"                                                                                                   \n");
				sb.append("     AND C.IC_CODE = "+product_code+" /*회사3*/                                                                                          \n");
				sb.append("     AND D.IC_TYPE = "+mid_category_type+"                                                                                                   \n");
				sb.append("     AND D.IC_CODE = "+top_code+" /*유형2*/                                                                                           \n");
				                                                                                                                                   
				pstmt = dbconn.createPStatement(sb.toString());
				System.out.println(pstmt);
				rs = pstmt.executeQuery();
				if( rs.next() ) {
					result[0] = rs.getString("PRODUCT");
					result[1] = rs.getString("TYPE");
					result[2] = rs.getString("CNT");
					result[3] = rs.getString("FACTOR_POINT");
					result[4] = rs.getString("FACTOR_PER");
				}				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		public String[] getParentTypeName(String type, String code){			
			String[] result = new String[3];
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				
				
				sb = new StringBuffer();
				
				sb.append(" SELECT A.IC_NAME AS `TYPE`             \n");
				sb.append("      , B.IC_NAME AS `GROUP`         	\n");
				sb.append("      , C.IC_NAME AS `MID_GROUP`         \n");
				sb.append("   FROM ISSUE_CODE A                     \n");
				sb.append("      , ISSUE_CODE B                     \n");
				sb.append("      , ISSUE_CODE C                     \n");
				sb.append("  WHERE A.IC_TYPE = B.IC_PTYPE           \n");
				sb.append("    AND A.IC_CODE = B.IC_PCODE           \n");
				sb.append("    AND B.IC_TYPE = C.IC_PTYPE           \n");
				sb.append("    AND B.IC_CODE = C.IC_PCODE           \n");
				sb.append("    AND C.IC_TYPE = "+type+"             \n");
				sb.append("    AND C.IC_CODE = "+code+"             \n");
				sb.append("    AND A.IC_USEYN = 'Y'          		\n");
				sb.append("    AND B.IC_USEYN = 'Y'             	\n");
				sb.append("    AND C.IC_USEYN = 'Y'             	\n");
				
				//System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				if( rs.next() ) {
					result[0] = rs.getString("TYPE");
					result[1] = rs.getString("GROUP");
					result[2] = rs.getString("MID_GROUP");
				}				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		
		public String[] getTopCodeRelation(String sdate, String stime ,String  edate, String etime, String pSdate, String pEdate, String category_type, String category_code, String product_type, String product_code, String targetCode, String sortBy){
			String[] result = new String[2];
			
			try {				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				sb = new StringBuffer();	
				//정렬 기준 - 증감건수
				if("3".equals(sortBy)){
					
					
//					sb.append("  SELECT A.IC_CODE                                                                                                   \n");
//					sb.append("        , ABS(IFNULL(B.CNT,0) - IFNULL(C.CNT,0)) AS CNT                                                              \n");
//					sb.append("    FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+targetCode+") A                                              \n");
//					sb.append("         LEFT OUTER JOIN                                                                                             \n");
//					sb.append("        (                                                                                                            \n");
//					sb.append("          SELECT D.IC_CODE /*고정연관어*/                                                                                 \n");
//					sb.append("               , COUNT(*) AS CNT                                                                                     \n");
//					sb.append("            FROM ISSUE_DATA A                                                                                        \n");
//					sb.append("               , ISSUE_DATA_CODE B                                                                                   \n");
//					sb.append("               , ISSUE_DATA_CODE C                                                                                   \n");
//					sb.append("               , ISSUE_DATA_CODE D                                                                                   \n");
//					sb.append("           WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'/*당기*/                             \n");
//					sb.append("             AND A.ID_SEQ = B.ID_SEQ                                                                                 \n");
//					sb.append("             AND A.ID_SEQ = C.ID_SEQ                                                                                 \n");
//					sb.append("             AND A.ID_SEQ = D.ID_SEQ                                                                                 \n");
//					sb.append("             AND B.IC_TYPE = "+ category_type+"                                                                                      \n");
//					sb.append("             AND B.IC_CODE = "+category_code+" /*사업운영본부*/                                                                            \n");
//					sb.append("             AND C.IC_TYPE = "+product_type+"                                                                                       \n");
//					sb.append("             AND C.IC_CODE = "+product_code+" /*회사3*/                                                                              \n");
//					sb.append("             AND D.IC_TYPE = "+targetCode+"                                                                                      \n");
//					sb.append("           GROUP BY D.IC_CODE                                                                                        \n");
//					sb.append("        ) B ON A.IC_CODE = B.IC_CODE  LEFT OUTER JOIN (                                                              \n");
//					sb.append("        SELECT D.IC_CODE /*고정연관어*/                                                                                   \n");
//					sb.append("               , COUNT(*) AS CNT                                                                                     \n");
//					sb.append("            FROM ISSUE_DATA A                                                                                        \n");
//					sb.append("               , ISSUE_DATA_CODE B                                                                                   \n");
//					sb.append("               , ISSUE_DATA_CODE C                                                                                   \n");
//					sb.append("               , ISSUE_DATA_CODE D                                                                                   \n");
//					sb.append("           WHERE A.MD_DATE BETWEEN '"+pSdate+" "+stime+"' AND '"+pEdate+" "+etime+"'/*전기*/                             \n");
//					sb.append("             AND A.ID_SEQ = B.ID_SEQ                                                                                 \n");
//					sb.append("             AND A.ID_SEQ = C.ID_SEQ                                                                                 \n");
//					sb.append("             AND A.ID_SEQ = D.ID_SEQ                                                                                 \n");
//					sb.append("             AND B.IC_TYPE = "+ category_type+"                                                                                      \n");
//					sb.append("             AND B.IC_CODE = "+category_code+" /*사업운영본부*/                                                                            \n");
//					sb.append("             AND C.IC_TYPE = "+product_type+"                                                                                       \n");
//					sb.append("             AND C.IC_CODE = "+product_code+" /*회사3*/                                                                              \n");
//					sb.append("             AND D.IC_TYPE = "+targetCode+"                                                                                      \n");
//					sb.append("           GROUP BY D.IC_CODE                                                                                        \n");
//					sb.append("        ) C ON A.IC_CODE = C.IC_CODE                                                                                 \n");
//					sb.append("  ORDER BY CNT DESC                                                                                                  \n");
//					sb.append("     LIMIT 1                                                                                                         \n");
					
					sb.append("#기준이 증감건수 일때\n");
					sb.append("SELECT A.RK_SEQ\n");
					sb.append("     , ABS(A.CNT_D - IFNULL(B.CNT_P,0)) AS CNT													\n"); 
					sb.append("  FROM (SELECT D.RK_SEQ /*연관어*/																	\n");
					sb.append("             , COUNT(*) AS CNT_D																	\n");
					sb.append("          FROM ISSUE_DATA A																		\n");
					sb.append("             , ISSUE_DATA_CODE B																	\n");
					sb.append("             , ISSUE_DATA_CODE C																	\n");
					sb.append("             , RELATION_KEYWORD_MAP D															\n");
					sb.append("         WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'/*당기*/			\n");
					sb.append("           AND A.ID_SEQ = B.ID_SEQ																\n");
					sb.append("           AND A.ID_SEQ = C.ID_SEQ																\n");
					sb.append("           AND A.ID_SEQ = D.ID_SEQ																\n");
					sb.append("           AND B.IC_TYPE = "+category_type+"														\n");
					sb.append("           AND B.IC_CODE = "+category_code+" /*사업운영본부*/										\n");
					sb.append("           AND C.IC_TYPE = "+product_type+"														\n");
					sb.append("           AND C.IC_CODE = "+product_code+" /*회사3*/												\n");
					sb.append("      ) A LEFT OUTER JOIN (																		\n");
					sb.append("          SELECT D.RK_SEQ /*연관어*/																\n");
					sb.append("               , COUNT(*) AS CNT_P																\n");
					sb.append("	           FROM ISSUE_DATA A																	\n");
					sb.append("               , ISSUE_DATA_CODE B																\n");
					sb.append("               , ISSUE_DATA_CODE C																\n");
					sb.append("               , RELATION_KEYWORD_MAP D															\n");
					sb.append("           WHERE A.MD_DATE BETWEEN '"+pSdate+" "+stime+"' AND '"+pEdate+" "+etime+"'/*당기*/		\n");
					sb.append("             AND A.ID_SEQ = B.ID_SEQ																\n");
					sb.append("             AND A.ID_SEQ = C.ID_SEQ																\n");
					sb.append("             AND A.ID_SEQ = D.ID_SEQ																\n");
					sb.append("             AND B.IC_TYPE = "+category_type+"													\n");
					sb.append("             AND B.IC_CODE = "+category_code+" /*사업운영본부*/										\n");
					sb.append("             AND C.IC_TYPE = "+product_type+"													\n");
					sb.append("             AND C.IC_CODE = "+product_code+" /*회사3*/											\n"); 
					sb.append("      ) B ON A.RK_SEQ = B.RK_SEQ																	\n");
					sb.append("  ORDER BY CNT DESC                                                                              \n");
					sb.append("  LIMIT 1																						\n");
					
				//정렬 기준 - 증감률
				}else if("2".equals(sortBy)){
					sb.append(" # 기준이 증감율일때                                                                                                       														 \n");
//					sb.append("     SELECT A.IC_CODE                                                                                                \n");
//					sb.append("       , ROUND(IFNULL(((ABS(IFNULL(B.CNT,0) - IFNULL(C.CNT,0))  / IFNULL(C.CNT,0))  *  100),0),1) AS CNT             \n");
//					sb.append("    FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+targetCode+" AND IC_CODE > 0) A                              \n");
//					sb.append("         LEFT OUTER JOIN                                                                                             \n");
//					sb.append("        (                                                                                                            \n");
//					sb.append("         SELECT D.IC_CODE                                                                                            \n");
//					sb.append("               , COUNT(*) AS CNT                                                                                     \n");
//					sb.append("            FROM ISSUE_DATA A                                                                                        \n");
//					sb.append("               , ISSUE_DATA_CODE B                                                                                   \n");
//					sb.append("               , ISSUE_DATA_CODE C                                                                                   \n");
//					sb.append("               , ISSUE_DATA_CODE D                                                                                   \n");
//					sb.append("           WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'                                   \n");
//					sb.append("             AND A.ID_SEQ = B.ID_SEQ                                                                                 \n");
//					sb.append("             AND A.ID_SEQ = C.ID_SEQ                                                                                 \n");
//					sb.append("             AND A.ID_SEQ = D.ID_SEQ                                                                                 \n");
//					sb.append("             AND B.IC_TYPE = "+category_type+"                                                                                      \n");
//					sb.append("             AND B.IC_CODE = "+category_code+"                                                                                       \n");
//					sb.append("             AND C.IC_TYPE = "+product_type+"                                                                                       \n");
//					sb.append("             AND C.IC_CODE = "+product_code+"                                                                                      \n");
//					sb.append("             AND D.IC_TYPE = "+targetCode+"                                                                                      \n");
//					sb.append("           GROUP BY D.IC_CODE                                                                                        \n");
//					sb.append("        ) B ON A.IC_CODE = B.IC_CODE  LEFT OUTER JOIN (                                                              \n");
//					sb.append("         SELECT D.IC_CODE /*고정연관어*/                                                                                  \n");
//					sb.append("               , COUNT(*) AS CNT                                                                                     \n");
//					sb.append("            FROM ISSUE_DATA A                                                                                        \n");
//					sb.append("               , ISSUE_DATA_CODE B                                                                                   \n");
//					sb.append("               , ISSUE_DATA_CODE C                                                                                   \n");
//					sb.append("               , ISSUE_DATA_CODE D                                                                                   \n");
//					sb.append("           WHERE A.MD_DATE BETWEEN '"+pSdate+" "+stime+"' AND '"+pEdate+" "+etime+"'/*전기*/               \n");
//					sb.append("             AND A.ID_SEQ = B.ID_SEQ                                                                                 \n");
//					sb.append("             AND A.ID_SEQ = C.ID_SEQ                                                                                 \n");
//					sb.append("             AND A.ID_SEQ = D.ID_SEQ                                                                                 \n");
//					sb.append("             AND B.IC_TYPE = "+category_type+"                                                                     \n");
//					sb.append("             AND B.IC_CODE = "+category_code+" /*사업운영본부*/                                                        \n");
//					sb.append("             AND C.IC_TYPE = "+product_type+"                                                                       \n");
//					sb.append("             AND C.IC_CODE = "+product_code+" /*회사3*/                                                               \n");
//					sb.append("             AND D.IC_TYPE = "+targetCode+"                                                                          \n");
//					sb.append("           GROUP BY D.IC_CODE                                                                                        \n");
//					sb.append("        ) C ON A.IC_CODE = C.IC_CODE                                                                                 \n");
//					sb.append("  ORDER BY CNT DESC                                                                                                  \n");
//					sb.append("     LIMIT 1                                                                                                         \n");
					
					
					
					
					sb.append("SELECT A.RK_SEQ 																						\n");
					sb.append("     , ROUND(IFNULL(((ABS(A.CNT_D - IFNULL(B.CNT_P,0))  / IFNULL(B.CNT_P,0))  *  100),0),1) AS CNT	\n");
					sb.append("  FROM (SELECT D.RK_SEQ /*연관어*/																		\n");
					sb.append("             , COUNT(*) AS CNT_D																		\n");
					sb.append("          FROM ISSUE_DATA A																			\n");
					sb.append("             , ISSUE_DATA_CODE B																		\n");
					sb.append("             , ISSUE_DATA_CODE C																		\n");
					sb.append("             , RELATION_KEYWORD_MAP D																\n");
					sb.append("         WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'/*당기*/				\n");
					sb.append("           AND A.ID_SEQ = B.ID_SEQ																	\n");
					sb.append("           AND A.ID_SEQ = C.ID_SEQ																	\n");
					sb.append("           AND A.ID_SEQ = D.ID_SEQ																	\n");
					sb.append("           AND B.IC_TYPE = "+category_type+"															\n");
					sb.append("           AND B.IC_CODE = "+category_code+" /*사업운영본부*/											\n");
					sb.append("           AND C.IC_TYPE = "+product_type+"															\n");
					sb.append("           AND C.IC_CODE = "+product_code+" /*회사3*/													\n");
					sb.append("       ) A LEFT OUTER JOIN (																			\n");
					sb.append("           SELECT D.RK_SEQ /*연관어*/																	\n");
					sb.append("                , COUNT(*) AS CNT_P																	\n");
					sb.append("	            FROM ISSUE_DATA A																		\n");
					sb.append("                , ISSUE_DATA_CODE B																	\n");
					sb.append("                , ISSUE_DATA_CODE C																	\n");
					sb.append("                , RELATION_KEYWORD_MAP D																\n");
					sb.append("            WHERE A.MD_DATE BETWEEN '"+pSdate+" "+stime+"' AND '"+pEdate+" "+etime+"'/*당기*/			\n");
					sb.append("              AND A.ID_SEQ = B.ID_SEQ																\n");
					sb.append("              AND A.ID_SEQ = C.ID_SEQ																\n");
					sb.append("              AND A.ID_SEQ = D.ID_SEQ																\n");
					sb.append("              AND B.IC_TYPE = "+category_type+"														\n");
					sb.append("              AND B.IC_CODE = "+category_code+" /*사업운영본부*/											\n");
					sb.append("              AND C.IC_TYPE = "+product_type+"														\n");
					sb.append("              AND C.IC_CODE = "+product_code+" /*회사3*/												\n"); 
					sb.append("       ) B ON A.RKSEQ = B.RK_SEQ																		\n");
					sb.append("  ORDER BY CNT DESC                                                                                  \n");
					sb.append("  LIMIT 1																							\n");
					
				//정렬 기준 - 수집건수(1)	
				}else{
					
//					sb.append("  SELECT D.IC_CODE /*고정연관어*/																				   \n");
//					sb.append("        , COUNT(*) AS CNT                                                                                       \n");
//					sb.append("     FROM ISSUE_DATA A                                                                                          \n");
//					sb.append("        , ISSUE_DATA_CODE B                                                                                     \n");
//					sb.append("        , ISSUE_DATA_CODE C                                                                                     \n");
//					sb.append("        , ISSUE_DATA_CODE D                                                                                     \n");
//					sb.append("    WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'/*당기*/                               \n");
//					sb.append("      AND A.ID_SEQ = B.ID_SEQ                                                                                   \n");
//					sb.append("      AND A.ID_SEQ = C.ID_SEQ                                                                                   \n");
//					sb.append("      AND A.ID_SEQ = D.ID_SEQ                                                                                   \n");
//					sb.append("      AND B.IC_TYPE = "+category_type+"                                                                                        \n");
//					sb.append("      AND B.IC_CODE = "+category_code+" /*사업운영본부*/                                                                              \n");
//					sb.append("      AND C.IC_TYPE = "+product_type+"                                                                                         \n");
//					sb.append("      AND C.IC_CODE = "+product_code+" /*회사3*/                                                                                \n");
//					sb.append("      AND D.IC_TYPE = "+targetCode+"                                                                                        \n");
//					sb.append("    GROUP BY D.IC_CODE                                                                                          \n");
//					sb.append("    ORDER BY COUNT(*)                                                                                           \n");
//					sb.append("    LIMIT 1                                                                                                     \n");
					
					sb.append("#기준이 수집수량일때																	\n");
					sb.append("SELECT D.RK_SEQ /*연관어*/															\n");
					sb.append("     , COUNT(*) AS CNT															\n");
					sb.append("  FROM ISSUE_DATA A																\n");
					sb.append("     , ISSUE_DATA_CODE B															\n");
					sb.append("     , ISSUE_DATA_CODE C															\n");
					sb.append("     , RELATION_KEYWORD_MAP D													\n");
					sb.append(" WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'/*당기*/	\n");
					sb.append("   AND A.ID_SEQ = B.ID_SEQ														\n");
					sb.append("   AND A.ID_SEQ = C.ID_SEQ														\n");
					sb.append("   AND A.ID_SEQ = D.ID_SEQ														\n");
					sb.append("   AND B.IC_TYPE = "+category_type+"												\n");
					sb.append("   AND B.IC_CODE = "+category_code+" /*사업운영본부*/								\n");
					sb.append("   AND C.IC_TYPE = "+product_type+"												\n");
					sb.append("   AND C.IC_CODE = "+product_code+" /*회사3*/										\n");
					sb.append(" GROUP BY D.RK_SEQ																\n");
					sb.append(" ORDER BY COUNT(*)																\n");
					sb.append(" LIMIT 1																			\n");
				  
				  
				  
				}				                        
				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				if( rs.next() ) {
					result[0] = rs.getString("RK_SEQ");
					result[1] = (null==rs.getString("CNT"))? "" : rs.getString("CNT") ;
				}				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		
		public String[] getTopCode3Depth2(String sdate, String stime ,String  edate, String etime, String pSdate, String pEdate, String category_type, String category_code, String product_type, String product_code, String targetCode, String sortBy){
			String[] result = new String[2];
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				sb = new StringBuffer();	
				//정렬 기준 - 증감건수
				if("3".equals(sortBy)){
					sb.append("  SELECT A.IC_CODE                                                                                                   \n");
					sb.append("        , ABS(IFNULL(B.CNT,0) - IFNULL(C.CNT,0)) AS CNT                                                              \n");
					sb.append("    FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+targetCode+") A                                              \n");
					sb.append("         LEFT OUTER JOIN                                                                                             \n");
					sb.append("        (                                                                                                            \n");
					sb.append("          SELECT D.IC_CODE /*고정연관어*/                                                                                 \n");
					sb.append("               , COUNT(*) AS CNT                                                                                     \n");
					sb.append("            FROM ISSUE_DATA A                                                                                        \n");
					sb.append("               , ISSUE_DATA_CODE B                                                                                   \n");
					sb.append("               , ISSUE_DATA_CODE C                                                                                   \n");
					sb.append("               , ISSUE_DATA_CODE D                                                                                   \n");
					sb.append("           WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'/*당기*/                             \n");
					sb.append("             AND A.ID_SEQ = B.ID_SEQ                                                                                 \n");
					sb.append("             AND A.ID_SEQ = C.ID_SEQ                                                                                 \n");
					sb.append("             AND A.ID_SEQ = D.ID_SEQ                                                                                 \n");
					sb.append("             AND B.IC_TYPE = "+ category_type+"                                                                                      \n");
					sb.append("             AND B.IC_CODE = "+category_code+" /*사업운영본부*/                                                                            \n");
					sb.append("             AND C.IC_TYPE = "+product_type+"                                                                                       \n");
					sb.append("             AND C.IC_CODE = "+product_code+" /*회사3*/                                                                              \n");
					sb.append("             AND D.IC_TYPE = "+targetCode+"                                                                                      \n");
					sb.append("           GROUP BY D.IC_CODE                                                                                        \n");
					sb.append("        ) B ON A.IC_CODE = B.IC_CODE  LEFT OUTER JOIN (                                                              \n");
					sb.append("        SELECT D.IC_CODE /*고정연관어*/                                                                                   \n");
					sb.append("               , COUNT(*) AS CNT                                                                                     \n");
					sb.append("            FROM ISSUE_DATA A                                                                                        \n");
					sb.append("               , ISSUE_DATA_CODE B                                                                                   \n");
					sb.append("               , ISSUE_DATA_CODE C                                                                                   \n");
					sb.append("               , ISSUE_DATA_CODE D                                                                                   \n");
					sb.append("           WHERE A.MD_DATE BETWEEN '"+pSdate+" "+stime+"' AND '"+pEdate+" "+etime+"'/*전기*/                             \n");
					sb.append("             AND A.ID_SEQ = B.ID_SEQ                                                                                 \n");
					sb.append("             AND A.ID_SEQ = C.ID_SEQ                                                                                 \n");
					sb.append("             AND A.ID_SEQ = D.ID_SEQ                                                                                 \n");
					sb.append("             AND B.IC_TYPE = "+ category_type+"                                                                                      \n");
					sb.append("             AND B.IC_CODE = "+category_code+" /*사업운영본부*/                                                                            \n");
					sb.append("             AND C.IC_TYPE = "+product_type+"                                                                                       \n");
					sb.append("             AND C.IC_CODE = "+product_code+" /*회사3*/                                                                              \n");
					sb.append("             AND D.IC_TYPE = "+targetCode+"                                                                                      \n");
					sb.append("           GROUP BY D.IC_CODE                                                                                        \n");
					sb.append("        ) C ON A.IC_CODE = C.IC_CODE                                                                                 \n");
					sb.append("  ORDER BY CNT DESC                                                                                                  \n");
					sb.append("     LIMIT 1                                                                                                         \n");
				//정렬 기준 - 증감률
				}else if("2".equals(sortBy)){
					sb.append(" # 기준이 증감율일때                                                                                                       														 \n");
					sb.append("     SELECT A.IC_CODE                                                                                                \n");
					sb.append("       , ROUND(IFNULL(((ABS(IFNULL(B.CNT,0) - IFNULL(C.CNT,0))  / IFNULL(C.CNT,0))  *  100),0),1) AS CNT             \n");
					sb.append("    FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+targetCode+" AND IC_CODE > 0) A                              \n");
					sb.append("         LEFT OUTER JOIN                                                                                             \n");
					sb.append("        (                                                                                                            \n");
					sb.append("         SELECT D.IC_CODE                                                                                            \n");
					sb.append("               , COUNT(*) AS CNT                                                                                     \n");
					sb.append("            FROM ISSUE_DATA A                                                                                        \n");
					sb.append("               , ISSUE_DATA_CODE B                                                                                   \n");
					sb.append("               , ISSUE_DATA_CODE C                                                                                   \n");
					sb.append("               , ISSUE_DATA_CODE D                                                                                   \n");
					sb.append("           WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'                                   \n");
					sb.append("             AND A.ID_SEQ = B.ID_SEQ                                                                                 \n");
					sb.append("             AND A.ID_SEQ = C.ID_SEQ                                                                                 \n");
					sb.append("             AND A.ID_SEQ = D.ID_SEQ                                                                                 \n");
					sb.append("             AND B.IC_TYPE = "+category_type+"                                                                                      \n");
					sb.append("             AND B.IC_CODE = "+category_code+"                                                                                       \n");
					sb.append("             AND C.IC_TYPE = "+product_type+"                                                                                       \n");
					sb.append("             AND C.IC_CODE = "+product_code+"                                                                                      \n");
					sb.append("             AND D.IC_TYPE = "+targetCode+"                                                                                      \n");
					sb.append("           GROUP BY D.IC_CODE                                                                                        \n");
					sb.append("        ) B ON A.IC_CODE = B.IC_CODE  LEFT OUTER JOIN (                                                              \n");
					sb.append("         SELECT D.IC_CODE /*고정연관어*/                                                                                  \n");
					sb.append("               , COUNT(*) AS CNT                                                                                     \n");
					sb.append("            FROM ISSUE_DATA A                                                                                        \n");
					sb.append("               , ISSUE_DATA_CODE B                                                                                   \n");
					sb.append("               , ISSUE_DATA_CODE C                                                                                   \n");
					sb.append("               , ISSUE_DATA_CODE D                                                                                   \n");
					sb.append("           WHERE A.MD_DATE BETWEEN '"+pSdate+" "+stime+"' AND '"+pEdate+" "+etime+"'/*전기*/               \n");
					sb.append("             AND A.ID_SEQ = B.ID_SEQ                                                                                 \n");
					sb.append("             AND A.ID_SEQ = C.ID_SEQ                                                                                 \n");
					sb.append("             AND A.ID_SEQ = D.ID_SEQ                                                                                 \n");
					sb.append("             AND B.IC_TYPE = "+category_type+"                                                                     \n");
					sb.append("             AND B.IC_CODE = "+category_code+" /*사업운영본부*/                                                        \n");
					sb.append("             AND C.IC_TYPE = "+product_type+"                                                                       \n");
					sb.append("             AND C.IC_CODE = "+product_code+" /*회사3*/                                                               \n");
					sb.append("             AND D.IC_TYPE = "+targetCode+"                                                                          \n");
					sb.append("           GROUP BY D.IC_CODE                                                                                        \n");
					sb.append("        ) C ON A.IC_CODE = C.IC_CODE                                                                                 \n");
					sb.append("  ORDER BY CNT DESC                                                                                                  \n");
					sb.append("     LIMIT 1                                                                                                         \n");
				//정렬 기준 - 수집건수(1)	
				}else{
					sb.append("  SELECT D.IC_CODE /*고정연관어*/																				   \n");
					sb.append("        , COUNT(*) AS CNT                                                                                       \n");
					sb.append("     FROM ISSUE_DATA A                                                                                          \n");
					sb.append("        , ISSUE_DATA_CODE B                                                                                     \n");
					sb.append("        , ISSUE_DATA_CODE C                                                                                     \n");
					sb.append("        , ISSUE_DATA_CODE D                                                                                     \n");
					sb.append("    WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'/*당기*/                               \n");
					sb.append("      AND A.ID_SEQ = B.ID_SEQ                                                                                   \n");
					sb.append("      AND A.ID_SEQ = C.ID_SEQ                                                                                   \n");
					sb.append("      AND A.ID_SEQ = D.ID_SEQ                                                                                   \n");
					sb.append("      AND B.IC_TYPE = "+category_type+"                                                                                        \n");
					sb.append("      AND B.IC_CODE = "+category_code+" /*사업운영본부*/                                                                              \n");
					sb.append("      AND C.IC_TYPE = "+product_type+"                                                                                         \n");
					sb.append("      AND C.IC_CODE = "+product_code+" /*회사3*/                                                                                \n");
					sb.append("      AND D.IC_TYPE = "+targetCode+"                                                                                        \n");
					sb.append("    GROUP BY D.IC_CODE                                                                                          \n");
					sb.append("    ORDER BY COUNT(*)                                                                                           \n");
					sb.append("    LIMIT 1                                                                                                     \n");
				}				                        
				
				//System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				if( rs.next() ) {
					result[0] = rs.getString("IC_CODE");
					result[1] = (null==rs.getString("CNT"))? "" : rs.getString("CNT") ;
				}				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		
		
		public String[] getTopCode3Depth(String sdate, String stime ,String  edate, String etime, String pSdate, String pEdate, String category_type, String category_code, String product_type, String product_code, String mid_category_type, String mid_category_codes, String sortBy){
			String[] result = new String[2];
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				sb = new StringBuffer();	
				//정렬 기준 - 증감건수
				if("3".equals(sortBy)){
					sb.append(" SELECT A.IC_CODE                                                                                                        \n");
					sb.append("       , ABS(IFNULL(B.CNT,0) - IFNULL(C.CNT,0)) AS CNT                                                                   \n");
					sb.append("   FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+mid_category_type +" AND IC_CODE IN ("+mid_category_codes+")) A                     \n");
					sb.append("        LEFT OUTER JOIN                                                                                                  \n");
					sb.append("       (                                                                                                                 \n");
					sb.append("       SELECT D.IC_CODE /*유형2*/                                                                                          \n");
					sb.append("            , COUNT(*) AS CNT                                                                                            \n");
					sb.append("         FROM ISSUE_DATA A                                                                                               \n");
					sb.append("            , ISSUE_DATA_CODE B                                                                                          \n");
					sb.append("            , ISSUE_DATA_CODE C                                                                                          \n");
					sb.append("            , ISSUE_DATA_CODE D                                                                                          \n");
					sb.append("        WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'                                   \n");
					sb.append("          AND A.ID_SEQ = B.ID_SEQ                                                                                        \n");
					sb.append("          AND A.ID_SEQ = C.ID_SEQ                                                                                        \n");
					sb.append("          AND A.ID_SEQ = D.ID_SEQ                                                                                        \n");
					sb.append("          AND B.IC_TYPE = "+category_type+"                                                                                             \n");
					sb.append("          AND B.IC_CODE = "+category_code+"                                                                                  \n");
					sb.append("          AND C.IC_TYPE = "+product_type+"                                                                                              \n");
					sb.append("          AND C.IC_CODE = "+product_code+"                                                                               \n");
					sb.append("          AND D.IC_TYPE = "+mid_category_type+"                                                                                              \n");
					sb.append("          AND D.IC_CODE IN ("+mid_category_codes+")                                                                         \n");
					sb.append("        GROUP BY D.IC_CODE                                                                                               \n");
					sb.append("       ) B ON A.IC_CODE = B.IC_CODE  LEFT OUTER JOIN (                                                                   \n");
					sb.append("       SELECT D.IC_CODE /*유형2*/                                                                                          \n");
					sb.append("            , COUNT(*) AS CNT                                                                                            \n");
					sb.append("         FROM ISSUE_DATA A                                                                                               \n");
					sb.append("            , ISSUE_DATA_CODE B                                                                                          \n");
					sb.append("            , ISSUE_DATA_CODE C                                                                                          \n");
					sb.append("            , ISSUE_DATA_CODE D                                                                                          \n");
					sb.append("        WHERE A.MD_DATE BETWEEN '"+pSdate+" "+stime+"' AND '"+pEdate+" "+etime+"'                                    \n");
					sb.append("          AND A.ID_SEQ = B.ID_SEQ                                                                                        \n");
					sb.append("          AND A.ID_SEQ = C.ID_SEQ                                                                                        \n");
					sb.append("          AND A.ID_SEQ = D.ID_SEQ                                                                                        \n");
					sb.append("          AND B.IC_TYPE = "+category_type+"                                                                                             \n");
					sb.append("          AND B.IC_CODE = "+category_code+"                                                                                  \n");
					sb.append("          AND C.IC_TYPE = "+product_type+"                                                                                              \n");
					sb.append("          AND C.IC_CODE = "+product_code+"                                                                               \n");
					sb.append("          AND D.IC_TYPE = "+mid_category_type+"                                                                                              \n");
					sb.append("          AND D.IC_CODE IN ("+mid_category_codes+")                                                                        \n");
					sb.append("        GROUP BY D.IC_CODE                                                                                               \n");
					sb.append("       ) C ON A.IC_CODE = C.IC_CODE                                                                                      \n");
					sb.append(" ORDER BY CNT DESC                                                                                                       \n");
					sb.append("    LIMIT 1                                                                                                              \n");
				//정렬 기준 - 증감률
				}else if("2".equals(sortBy)){
					sb.append("  SELECT A.IC_CODE                                                                                                     \n");
					sb.append("        , ROUND(IFNULL(((ABS(IFNULL(B.CNT,0) - IFNULL(C.CNT,0))  / IFNULL(C.CNT,0))  *  100),0),1) AS CNT              \n");
					sb.append("     FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+mid_category_type+" AND IC_CODE IN ("+mid_category_codes+")) A                 \n");
					sb.append("          LEFT OUTER JOIN                                                                                              \n");
					sb.append("         (                                                                                                             \n");
					sb.append("         SELECT D.IC_CODE /*유형2*/                                                                                      \n");
					sb.append("              , COUNT(*) AS CNT                                                                                        \n");
					sb.append("           FROM ISSUE_DATA A                                                                                           \n");
					sb.append("              , ISSUE_DATA_CODE B                                                                                      \n");
					sb.append("              , ISSUE_DATA_CODE C                                                                                      \n");
					sb.append("              , ISSUE_DATA_CODE D                                                                                      \n");
					sb.append("          WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'                               \n");
					sb.append("            AND A.ID_SEQ = B.ID_SEQ                                                                                    \n");
					sb.append("            AND A.ID_SEQ = C.ID_SEQ                                                                                    \n");
					sb.append("            AND A.ID_SEQ = D.ID_SEQ                                                                                    \n");
					sb.append("            AND B.IC_TYPE = "+category_type+"                                                                                         \n");
					sb.append("            AND B.IC_CODE = "+category_code+"                                                                               \n");
					sb.append("            AND C.IC_TYPE = "+product_type+"                                                                                          \n");
					sb.append("            AND C.IC_CODE = "+product_code+"                                                                            \n");
					sb.append("            AND D.IC_TYPE = "+mid_category_type+"                                                                                          \n");
					sb.append("            AND D.IC_CODE IN ("+mid_category_codes+")                                                                   \n");
					sb.append("          GROUP BY D.IC_CODE                                                                                           \n");
					sb.append("         ) B ON A.IC_CODE = B.IC_CODE  LEFT OUTER JOIN (                                                               \n");
					sb.append("         SELECT D.IC_CODE                                                                                     \n");
					sb.append("              , COUNT(*) AS CNT                                                                                        \n");
					sb.append("           FROM ISSUE_DATA A                                                                                           \n");
					sb.append("              , ISSUE_DATA_CODE B                                                                                      \n");
					sb.append("              , ISSUE_DATA_CODE C                                                                                      \n");
					sb.append("              , ISSUE_DATA_CODE D                                                                                      \n");
					sb.append("          WHERE A.MD_DATE BETWEEN '"+pSdate+" "+stime+"' AND '"+pEdate+" "+etime+"'                                \n");
					sb.append("            AND A.ID_SEQ = B.ID_SEQ                                                                                    \n");
					sb.append("            AND A.ID_SEQ = C.ID_SEQ                                                                                    \n");
					sb.append("            AND A.ID_SEQ = D.ID_SEQ                                                                                    \n");
					sb.append("            AND B.IC_TYPE = "+category_type+"                                                                                         \n");
					sb.append("            AND B.IC_CODE = "+category_code+"                                                                               \n");
					sb.append("            AND C.IC_TYPE = "+product_type+"                                                                                          \n");
					sb.append("            AND C.IC_CODE = "+product_code+"                                                                           \n");
					sb.append("            AND D.IC_TYPE = "+mid_category_type+"                                                                                          \n");
					sb.append("            AND D.IC_CODE IN ("+mid_category_codes+") 	                                                                   \n");
					sb.append("          GROUP BY D.IC_CODE                                                                                           \n");
					sb.append("         ) C ON A.IC_CODE = C.IC_CODE                                                                                  \n");
					sb.append("   ORDER BY CNT DESC                                                                                                   \n");
					sb.append("      LIMIT 1                                                                                                          \n");
				//정렬 기준 - 수집건수(1)	
				}else{
					sb.append(" SELECT D.IC_CODE /*유형2*/                                                                                \n");
					sb.append("      , COUNT(*) AS CNT                                                                                  \n");
					sb.append("   FROM ISSUE_DATA A                                                                                     \n");
					sb.append("      , ISSUE_DATA_CODE B                                                                                \n");
					sb.append("      , ISSUE_DATA_CODE C                                                                                \n");
					sb.append("      , ISSUE_DATA_CODE D                                                                                \n");
					sb.append("  WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'                    \n");
					sb.append("    AND A.ID_SEQ = B.ID_SEQ                                                                              \n");
					sb.append("    AND A.ID_SEQ = C.ID_SEQ                                                                              \n");
					sb.append("    AND A.ID_SEQ = D.ID_SEQ                                                                              \n");
					sb.append("    AND B.IC_TYPE = "+category_type+"                                                                     \n");
					sb.append("    AND B.IC_CODE = "+category_code+"                                                                     \n");
					sb.append("    AND C.IC_TYPE = "+product_type+"                                                                     \n");
					sb.append("    AND C.IC_CODE = "+product_code+"                                                                      \n");
					sb.append("    AND D.IC_TYPE = "+mid_category_type+"                                                                 \n");
					sb.append("    AND D.IC_CODE IN ("+mid_category_codes+")                                                               \n");
					sb.append("  GROUP BY D.IC_CODE                                                                                     \n");
					sb.append("  ORDER BY COUNT(*) DESC                                                                                 \n");
					sb.append("  LIMIT 1                                                                                                \n");
				}				                        
				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				if( rs.next() ) {
					result[0] = rs.getString("IC_CODE");
					result[1] = (null==rs.getString("CNT"))? "" : rs.getString("CNT") ;
				}				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			return result;
		}
		
		public String[] getTopCode2Depth(String sdate, String stime ,String  edate, String etime, String pSdate, String pEdate, String category_type, String category_code, String mid_category_type, String mid_category_codes, String sortBy){			
			String[] result = new String[2];
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				sb = new StringBuffer();	
				//정렬 기준 - 증감건수
				if("3".equals(sortBy)){
					sb.append(" SELECT A.IC_CODE                                                                                                                \n");
					sb.append("       , ABS(IFNULL(B.CNT,0) - IFNULL(C.CNT,0)) AS CNT                                                                           \n");
					sb.append("    FROM (                                                                                                                       \n");
					sb.append("          SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = "+mid_category_type+" AND IC_CODE IN ("+mid_category_codes+")           \n");
					sb.append("         ) A LEFT OUTER JOIN(                                                                                                    \n");
					sb.append("          SELECT C.IC_CODE                                                                                                       \n");
					sb.append("               , COUNT(*) AS CNT                                                                                                 \n");
					sb.append("            FROM ISSUE_DATA A                                                                                                    \n");
					sb.append("               , ISSUE_DATA_CODE B                                                                                               \n");
					sb.append("               , ISSUE_DATA_CODE C                                                                                               \n");
					sb.append("           WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'                                   \n");
					sb.append("             AND A.ID_SEQ = B.ID_SEQ                                                                                             \n");
					sb.append("             AND A.ID_SEQ = C.ID_SEQ                                                                                             \n");
					sb.append("             AND B.IC_TYPE = "+category_type+"                                                                                   \n");
					sb.append("             AND B.IC_CODE = "+category_code+"                                                                                   \n");
					sb.append("             AND C.IC_TYPE = "+mid_category_type+"                                                                               \n");
					sb.append("             AND C.IC_CODE IN ("+mid_category_codes+")                                                                           \n");
					sb.append("           GROUP BY C.IC_CODE                                                                                                    \n");
					sb.append("         ) B ON A.IC_CODE = B.IC_CODE LEFT OUTER JOIN(                                                                           \n");
					sb.append("          SELECT C.IC_CODE                                                                                                       \n");
					sb.append("               , COUNT(*) AS CNT                                                                                                 \n");
					sb.append("            FROM ISSUE_DATA A                                                                                                    \n");
					sb.append("               , ISSUE_DATA_CODE B                                                                                               \n");
					sb.append("               , ISSUE_DATA_CODE C                                                                                               \n");
					sb.append("           WHERE A.MD_DATE BETWEEN '"+pSdate+" "+stime+"' AND '"+pEdate+" "+etime+"'/*전기*/                           \n");
					sb.append("             AND A.ID_SEQ = B.ID_SEQ                                                                                             \n");
					sb.append("             AND A.ID_SEQ = C.ID_SEQ                                                                                             \n");
					sb.append("             AND B.IC_TYPE = "+category_type+"                                                                                   \n");
					sb.append("             AND B.IC_CODE = "+category_code+"                                                                                   \n");
					sb.append("             AND C.IC_TYPE = "+mid_category_type+"                                                                               \n");
					sb.append("             AND C.IC_CODE IN ("+mid_category_codes+")                                                                           \n");
					sb.append("           GROUP BY C.IC_CODE                                                                                                    \n");
					sb.append("         ) C ON A.IC_CODE = C.IC_CODE                                                                                            \n");
					sb.append("    ORDER BY CNT DESC                                                                                                            \n");
					sb.append("    LIMIT 1                                                                                                                      \n");
				//정렬 기준 - 증감률
				}else if("2".equals(sortBy)){
					sb.append("  SELECT A.IC_CODE																										\n");			
					sb.append("       , ROUND(IFNULL(((ABS(IFNULL(B.CNT,0) - IFNULL(C.CNT,0))  / IFNULL(C.CNT,0))  *  100),0),1) AS CNT                 \n");
					sb.append("    FROM (                                                                                                               \n");
					sb.append("          SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = "+mid_category_type+" AND IC_CODE IN ("+mid_category_codes+")   \n");
					sb.append("         ) A LEFT OUTER JOIN(                                                                                            \n");
					sb.append("          SELECT C.IC_CODE                                                                                               \n");
					sb.append("               , COUNT(*) AS CNT                                                                                         \n");
					sb.append("            FROM ISSUE_DATA A                                                                                            \n");
					sb.append("               , ISSUE_DATA_CODE B                                                                                       \n");
					sb.append("               , ISSUE_DATA_CODE C                                                                                       \n");
					sb.append("           WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"' /*당기*/                    \n");
					sb.append("             AND A.ID_SEQ = B.ID_SEQ                                                                                     \n");
					sb.append("             AND A.ID_SEQ = C.ID_SEQ                                                                                     \n");
					sb.append("             AND B.IC_TYPE = "+category_type+"                                                                           \n");
					sb.append("             AND B.IC_CODE = "+category_code+"                                                                           \n");
					sb.append("             AND C.IC_TYPE = "+mid_category_type+"                                                                       \n");
					sb.append("             AND C.IC_CODE IN ("+mid_category_codes+")                                                                   \n");
					sb.append("           GROUP BY C.IC_CODE                                                                                            \n");
					sb.append("         ) B ON A.IC_CODE = B.IC_CODE LEFT OUTER JOIN(                                                                   \n");
					sb.append("          SELECT C.IC_CODE                                                                                               \n");
					sb.append("               , COUNT(*) AS CNT                                                                                         \n");
					sb.append("            FROM ISSUE_DATA A                                                                                            \n");
					sb.append("               , ISSUE_DATA_CODE B                                                                                       \n");
					sb.append("               , ISSUE_DATA_CODE C                                                                                       \n");
					sb.append("           WHERE A.MD_DATE BETWEEN '"+pSdate+" "+stime+"' AND '"+pEdate+" "+etime+"'/*전기*/                  \n");
					sb.append("             AND A.ID_SEQ = B.ID_SEQ                                                                                     \n");
					sb.append("             AND A.ID_SEQ = C.ID_SEQ                                                                                     \n");
					sb.append("             AND B.IC_TYPE = "+category_type+"                                                                           \n");
					sb.append("             AND B.IC_CODE = "+category_code+"                                                                           \n");
					sb.append("             AND C.IC_TYPE = "+mid_category_type+"                                                                       \n");
					sb.append("             AND C.IC_CODE IN ("+mid_category_codes+")                                                                   \n");
					sb.append("           GROUP BY C.IC_CODE                                                                                            \n");
					sb.append("         ) C ON A.IC_CODE = C.IC_CODE                                                                                    \n");
					sb.append("    ORDER BY CNT DESC                                                                                                    \n");
					sb.append("    LIMIT 1                                                                                                              \n");
				//정렬 기준 - 수집건수(1)	
				}else{
					sb.append("SELECT C.IC_CODE																					\n");  
					sb.append("   FROM ISSUE_DATA A																				\n");  
					sb.append("      , ISSUE_DATA_CODE B																		\n");  
					sb.append("      , ISSUE_DATA_CODE C																		\n");  
					sb.append("  WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"' 			\n");  
					sb.append("    AND A.ID_SEQ = B.ID_SEQ 																		\n");  
					sb.append("    AND A.ID_SEQ = C.ID_SEQ 																		\n");  
					sb.append("    AND B.IC_TYPE = "+category_type+"															\n");  
					sb.append("    AND B.IC_CODE = "+category_code+" 															\n");   
					sb.append("    AND C.IC_TYPE = "+mid_category_type+" 														\n");  
					sb.append("    AND C.IC_CODE IN ("+mid_category_codes+") 													\n"); 
					sb.append("  GROUP BY C.IC_CODE 																			\n"); 
					sb.append("  ORDER BY COUNT(*) DESC  																		\n"); 
					sb.append("  LIMIT 1 																						\n"); 
				}				                        
				
				//System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				if( rs.next() ) {
					result[0] = rs.getString("IC_CODE");
					result[1] = "";//(null==rs.getString("CNT"))? "" : rs.getString("CNT") ;
				}				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		
		public String[] getCategoryTopInfo(String sdate, String stime ,String  edate, String etime, String pSdate, String pEdate, String category_type, String category_code, String mid_category_type, String top_code){			
			String[] result = new String[6];
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();				
				
				sb = new StringBuffer();				
				sb.append("  SELECT A.CNT_P /*전기 건수*/                                                                                       \n");
				sb.append("       , A.CNT_D /*당기 건수*/                                                                                       \n");
				sb.append("       , ABS(A.CNT_P - A.CNT_D) AS FACTOR_CNT /*증감 건수*/                                                          \n");
				sb.append("       , IF((A.CNT_P - A.CNT_D) = 0, 0, IF((A.CNT_P - A.CNT_D) > 0, -1, 1)) AS FACTOR_POINT /*증감 화살표*/           \n");
				sb.append("       , ROUND(IFNULL((ABS(A.CNT_P - A.CNT_D) / A.CNT_P * 100),0),1) AS FACTOR_PER /*증감율*/                       \n");
				sb.append("       , FN_ISSUE_NAME("+top_code+","+mid_category_type+") AS TOP_KEY                                                                          \n");
				sb.append("    FROM (                                                                                                       \n");
				sb.append("            SELECT (                                                                                             \n");
				sb.append("                    SELECT COUNT(*) AS CNT_P                                                                     \n");
				sb.append("                     FROM ISSUE_DATA A                                                                           \n");
				sb.append("                        , ISSUE_DATA_CODE B                                                                      \n");
				sb.append("                        , ISSUE_DATA_CODE C                                                                      \n");
				sb.append("                    WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'          \n");
				sb.append("                      AND A.ID_SEQ = B.ID_SEQ                                                                    \n");
				sb.append("                      AND A.ID_SEQ = C.ID_SEQ                                                                    \n");
				sb.append("                      AND B.IC_TYPE = "+category_type+"                                                          \n");
				sb.append("                      AND B.IC_CODE = "+category_code+"                                                          \n");
				sb.append("                      AND C.IC_TYPE = "+mid_category_type+"                                                      \n");
				sb.append("                      AND C.IC_CODE = "+top_code+"                                                               \n");
				sb.append("            ) AS CNT_D,(                                                                                         \n");
				sb.append("                    SELECT COUNT(*) AS CNT_P                                                                     \n");
				sb.append("                     FROM ISSUE_DATA A                                                                           \n");
				sb.append("                        , ISSUE_DATA_CODE B                                                                      \n");
				sb.append("                        , ISSUE_DATA_CODE C                                                                      \n");
				sb.append("                    WHERE A.MD_DATE BETWEEN '"+pSdate+" "+stime+"' AND '"+pEdate+" "+etime+"'        \n");
				sb.append("                      AND A.ID_SEQ = B.ID_SEQ                                                                    \n");
				sb.append("                      AND A.ID_SEQ = C.ID_SEQ                                                                    \n");
				sb.append("                      AND B.IC_TYPE = "+category_type+"                                                          \n");
				sb.append("                      AND B.IC_CODE = "+category_code+"                                                          \n");
				sb.append("                      AND C.IC_TYPE = "+mid_category_type+"                                                      \n");
				sb.append("                      AND C.IC_CODE = "+top_code+" 	                                                            \n");
				sb.append("          ) AS CNT_P                                                                                             \n");
				sb.append("         )A                                                                                                      \n");
				
				//System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				int idx = 0;
				if( rs.next() ) {
					result[idx++] = rs.getString("CNT_P");
					result[idx++] = rs.getString("CNT_D");
					result[idx++] = rs.getString("FACTOR_CNT");
					result[idx++] = rs.getString("FACTOR_POINT");
					result[idx++] = rs.getString("FACTOR_PER");
					result[idx++] = rs.getString("TOP_KEY");
				}				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		
		public String getMidCategory2Depth(String group_types, String ic_code){			
			String result = "";
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				
				
				sb = new StringBuffer();
				
				sb.append("SELECT GROUP_CONCAT(B.IC_CODE)  AS `IC_CODES`	        \n");
				sb.append("   FROM ISSUE_CODE A                                     \n");
				sb.append("      , ISSUE_CODE B                                     \n");
				sb.append("  WHERE A.IC_TYPE = "+group_types+"                      \n");
				sb.append("    AND A.IC_CODE = "+ic_code+" 					        \n");
				sb.append("    AND A.IC_TYPE = B.IC_PTYPE                           \n");
				sb.append("    AND A.IC_CODE = B.IC_PCODE                           \n");
				sb.append("    AND A.IC_USEYN = 'Y' 	                            \n");
				sb.append("    AND B.IC_USEYN = 'Y'                                 \n");
				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				//System.out.println(pstmt);
				rs = pstmt.executeQuery();
				if( rs.next() ) {
					result = rs.getString("IC_CODES");
				}				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		
		public String getMidCategory3Depth(String group_types, String ic_code){			
			String result = "";
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				
				
				sb = new StringBuffer();
				
				sb.append("SELECT GROUP_CONCAT(C.IC_CODE) AS `IC_CODES`		\n");   
				sb.append("  FROM ISSUE_CODE A   				\n");   
				sb.append("    , ISSUE_CODE B    				\n");    
				sb.append("     , ISSUE_CODE C    				\n");   
				sb.append(" WHERE A.IC_TYPE = "+group_types+"   \n");   
				sb.append("   AND A.IC_CODE = "+ic_code+" 		\n");    /* 3:클레임/불만, 4:문의/제안/칭찬 */
				sb.append("   AND A.IC_TYPE = B.IC_PTYPE 		\n");   
				sb.append("   AND A.IC_CODE = B.IC_PCODE 		\n");   
				sb.append("   AND B.IC_TYPE = C.IC_PTYPE 		\n");   
				sb.append("   AND B.IC_CODE = C.IC_PCODE 		\n");    
				sb.append("   AND A.IC_USEYN = 'Y' 				\n");
				sb.append("   AND B.IC_USEYN = 'Y' 				\n");
				sb.append("   AND C.IC_USEYN = 'Y' 				\n");
				
				//System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				if( rs.next() ) {
					result = rs.getString("IC_CODES");
				}				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		public ArrayList<String[]> getProductListTop(String sdate, String stime, String edate, String etime, String ic_type, String ic_code, String targetType, int limit_length){			
			ArrayList<String[]> result = new ArrayList<String[]>();
			String[] tmp = new String[3];
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				
				
				sb = new StringBuffer();
				
				sb.append("SELECT C.IC_CODE /*회사3*/                                                                \n");
				sb.append("     , COUNT(*) AS CNT                                                                  \n");
				sb.append("     , FN_ISSUE_NAME(C.IC_CODE,"+targetType+") AS IC_NAME                                                                \n");
				sb.append("  FROM ISSUE_DATA A                                                                     \n");
				sb.append("     , ISSUE_DATA_CODE B                                                                \n");
				sb.append("     , ISSUE_DATA_CODE C                                                                \n");
				sb.append(" WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'    \n");                           
				sb.append("   AND A.ID_SEQ = B.ID_SEQ                                                              \n");
				sb.append("   AND A.ID_SEQ = C.ID_SEQ                                                              \n");
				sb.append("   AND B.IC_TYPE = "+ic_type+"                                                          \n");
				sb.append("   AND B.IC_CODE = "+ic_code+"	                                                       \n");
				sb.append("   AND C.IC_TYPE = "+targetType+"                                                       \n");
				sb.append(" GROUP BY C.IC_CODE                                                                     \n");
				sb.append(" 	ORDER BY COUNT(*) DESC                                                                 \n");
				sb.append(" LIMIT "+limit_length+"                                          		               \n");
					
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
			
				while( rs.next() ) {
					tmp = new String[3];					
					tmp[0] = rs.getString("IC_CODE");
					tmp[1] = rs.getString("CNT");
					tmp[2] = rs.getString("IC_NAME");

					result.add(tmp);
				}
				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		
		
		public ArrayList<String[]> getCategory(String sdate, String stime, String edate, String etime, String ic_type, String ic_codes){			
			ArrayList<String[]> result = new ArrayList<String[]>();
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				
				
				sb = new StringBuffer();
				
				sb.append("#1.Voc Trend Report                                                                                                                                              	 \n");
				sb.append("SELECT B.IC_CODE                                                                      \n");                         
				sb.append("     , FN_ISSUE_NAME(B.IC_CODE,B.IC_TYPE)AS IS_NAME                                    \n");                                                                
				sb.append("     , COUNT(*) AS CNT                                                                \n");                                                              
				sb.append("  FROM ISSUE_DATA A                                                                   \n");                                                            
				sb.append("     , ISSUE_DATA_CODE B                                                              \n");                                                       
				sb.append(" WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'  \n");                                                              
				sb.append("   AND A.ID_SEQ = B.ID_SEQ                                                              \n");                                                          
				sb.append("   AND B.IC_TYPE = "+ic_type+"                                                              \n");                                                     
				sb.append("   AND B.IC_CODE IN ("+ic_codes+")                                                               \n");                                                
				sb.append(" GROUP BY B.IC_CODE                                                                           \n");                                           
				sb.append(" ORDER BY COUNT(*) DESC                                                                        \n");
				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				String[] tmp = new String[3];
				int idx = 0 ;
				while( rs.next() ) {
					idx = 0;
					tmp = new String[3];					
					tmp[idx++] = rs.getString("IC_CODE");
					tmp[idx++] = rs.getString("IS_NAME");
					tmp[idx++] = rs.getString("CNT");

					result.add(tmp);
				}
				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		
		public ArrayList<String[]> getCategoryOrder(String sdate, String stime, String edate, String etime, String ic_type, String ic_codes){			
			ArrayList<String[]> result = new ArrayList<String[]>();
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				
				
				sb = new StringBuffer();
				
				sb.append("#1.Voc Trend Report                                                                                                                                              	 \n");
				sb.append("SELECT B.IC_CODE                                                                      \n");                         
				sb.append("     , FN_ISSUE_NAME(B.IC_CODE,B.IC_TYPE)AS IS_NAME                                    \n");                                                                
				sb.append("     , COUNT(*) AS CNT                                                                \n");                                                              
				sb.append("  FROM ISSUE_DATA A                                                                   \n");                                                            
				sb.append("     , ISSUE_DATA_CODE B                                                              \n");                                                       
				sb.append(" WHERE A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'  \n");                                                              
				sb.append("   AND A.ID_SEQ = B.ID_SEQ                                                              \n");                                                          
				sb.append("   AND B.IC_TYPE = "+ic_type+"                                                              \n");                                                     
				sb.append("   AND B.IC_CODE IN ("+ic_codes+")                                                               \n");                                                
				sb.append(" GROUP BY B.IC_CODE                                                                           \n");                                           
				sb.append(" ORDER BY IC_CODE                                                                       \n");
				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				String[] tmp = new String[3];
				int idx = 0 ;
				while( rs.next() ) {
					idx = 0;
					tmp = new String[3];					
					tmp[idx++] = rs.getString("IC_CODE");
					tmp[idx++] = rs.getString("IS_NAME");
					tmp[idx++] = rs.getString("CNT");

					result.add(tmp);
				}
				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		
		 public String saveChartImage(String name, String binary, String SS_M_NO){
		    	
		    	String result = "";
		    	
		    	DateUtil du = new DateUtil();
		    	ConfigUtil cu = new ConfigUtil();
		    	String savePath	= cu.getConfig("CHARTPATH");
		    	String urlPath	= cu.getConfig("CHARTURL");
		    	
		    	String fileName = name+"_"+SS_M_NO+"_"+du.getCurrentDate("yyyyMMddHHmmss")+".png";
		    	
				try {
					BASE64Decoder decoder = new BASE64Decoder();
					byte[] imgBytes = decoder.decodeBuffer(binary.split(",")[1]);
					
					BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imgBytes));
					File imgOutFile = new File(savePath+fileName);
					ImageIO.write(bufImg, "png", imgOutFile);
					
					result = urlPath+fileName;

					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result = "";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result = "";
				}
			
		    	
		    	return result;
		    }
		 

		public String[] getPrevDate(String sdate, String stime, String edate, String etime){			
			String[] result = new String[2];
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				sb = new StringBuffer();				
				sb.append("SELECT DATE_ADD(DATE_ADD('"+sdate+" "+stime+"', INTERVAL -1 DAY), INTERVAL -(TIMESTAMPDIFF( DAY, '"+sdate+" "+stime+"', '"+edate+" "+etime+"') + 1) DAY) AS PEV_SDATE    \n");
				sb.append("       , DATE_ADD('"+sdate+" "+stime+"', INTERVAL -1 DAY) AS PEV_EDATE                                                                                                   \n");
				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				if( rs.next() ) {
					result[0] = rs.getString("PEV_SDATE");
					result[1] = rs.getString("PEV_EDATE");
				}
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		
		public String[] getPrevMonth(String edate, String etime, String term) {			
			String[] result = new String[2];
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				sb = new StringBuffer();				
				sb.append("SELECT DATE_ADD(DATE_ADD('"+edate+" "+etime+"', INTERVAL -1 DAY), INTERVAL -"+term+" MONTH) AS S_DATE    \n");
				sb.append("     , DATE_ADD('"+edate+" "+etime+"', INTERVAL -1 DAY) AS E_DATE                                  \n");
				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				if( rs.next() ) {
					result[0] = rs.getString("S_DATE");
					result[1] = rs.getString("E_DATE");
				}
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		
		public String[] getPrevMonthExSTime(String edate, String eTime, String term) {			
			String[] result = new String[2];
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				sb = new StringBuffer();				
				sb.append("SELECT DATE_ADD(DATE_ADD('"+edate+" 00:00:00', INTERVAL -1 DAY), INTERVAL -"+term+" MONTH) AS S_DATE    \n");
				sb.append("     , DATE_ADD('"+edate+" "+eTime+"', INTERVAL -1 DAY) AS E_DATE                                  \n");
				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				if( rs.next() ) {
					result[0] = rs.getString("S_DATE");
					result[1] = rs.getString("E_DATE");
				}
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		
		public String[] getPrevMonthExTime(String edate,  String term) {			
			String[] result = new String[2];
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				sb = new StringBuffer();				
				sb.append("SELECT DATE_ADD(DATE_ADD('"+edate+" 00:00:00', INTERVAL -1 DAY), INTERVAL -"+term+" MONTH) AS S_DATE    \n");
				sb.append("     , DATE_ADD('"+edate+" 00:00:00', INTERVAL -1 DAY) AS E_DATE                                  \n");
				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				if( rs.next() ) {
					result[0] = rs.getString("S_DATE");
					result[1] = rs.getString("E_DATE");
				}
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		public String getDiffDateFromFirstDay(String edate, String etime) {			
			String result = "";
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				sb = new StringBuffer();				
				sb.append("SELECT TIMESTAMPDIFF( DAY, DATE_FORMAT('"+edate+" 00:00:00', '%Y-%m-01 00:00:00'), '"+edate+" 00:00:00') AS DIFF   \n");
				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				if( rs.next() ) {
					result = rs.getString("DIFF");
				}
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
		
		public String[] getIssueNameGroup(String ic_codes, String type, int retrun_size){
			String[] result = new String[retrun_size];
	   	 	try {
		   	 	dbconn = new DBconn();
		   	 	dbconn.getDBCPConnection();
		   	 	sb = new StringBuffer();
				sb.append("SELECT IC_NAME			          \n");
				sb.append("  FROM ISSUE_CODE                  \n");
				sb.append("WHERE IC_TYPE = "+type+"           \n");
				sb.append("  AND IC_CODE IN ("+ic_codes+")    \n");
			
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
			int idx = 0;
			while(rs.next()){
				result[idx++] = rs.getString("IC_NAME");
			}
			if(result.length < retrun_size ){
				
				int tmp_size = retrun_size - result.length;
				for(int i=0; i< tmp_size; i++){
					result[idx++] = "";
				}
			}
			
				
	   	 	}catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	        }
			return  result;
		}
		
		public JSONObject getIssueTrandReportChart2TypeCategory(String sDate, String eDate, String category_type,  String category_code, String limit_length){	
			JSONObject obj = new JSONObject();
			String code_group = "";
			String name_group = "";
	   	 	try {
		   	 	dbconn = new DBconn();
		   	 	dbconn.getDBCPConnection();
		   	 	sb = new StringBuffer();
				sb.append("  SELECT 	B.IC_CODE                                                                                         \n");
				sb.append("              , FN_ISSUE_NAME(B.IC_CODE, B.IC_TYPE) AS IC_NAME                                                                          \n");
				sb.append("              , COUNT(*) AS CNT                                                                           \n");
				sb.append("            FROM ISSUE_DATA A                                                                              \n");
				sb.append("               , ISSUE_DATA_CODE B                                                                         \n");
				sb.append("           WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' /*범위 설정*/               \n");
				sb.append("             AND A.ID_SEQ = B.ID_SEQ                                                                       \n");
				sb.append("             AND B.IC_TYPE = "+category_type+"                                                                             \n");
				sb.append("             AND B.IC_CODE IN ("+category_code+")                                                              \n");
				sb.append("           GROUP BY B.IC_CODE                                                                              \n");
				sb.append("           ORDER BY COUNT(*) DESC                                                                          \n");
				sb.append("           LIMIT "+limit_length+"                                                                                         \n");
			
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
			while(rs.next()){
				if("".equals(code_group)){
					code_group = rs.getString("IC_CODE");
				}else{
					code_group += ","+rs.getString("IC_CODE");
				}
								
				if("".equals(name_group)){
					name_group = rs.getString("IC_NAME");
				}else{
					name_group += ","+rs.getString("IC_NAME");
				}
			}
			
			obj.put("IC_CODE_GROUP", code_group);
			obj.put("IC_NAME_GROUP", name_group);			
				
	   	 	}catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	        }
	   	 	return obj;
	    }
		
		public JSONArray getIssueTrandReportChartType4(String sDate, String eDate, String category_type,  String category_code, String sg_code){	
	    	JSONArray arr = null; 
	    	String comp_group = "";
	    	String[] comp_group_arr = null;
	    	String[] category_code_arr = category_code.split(",");
	    	
	   	 	try {
		   	 	dbconn = new DBconn();
		   	 	dbconn.getDBCPConnection();
		   	 	
		   	 	sb = new StringBuffer();
		   	 	sb.append("            SELECT GROUP_CONCAT(IC_NAME)AS IC_NAME_GP			\n");
		   	 	sb.append("            		FROM ISSUE_CODE	 								\n");
				sb.append("            			WHERE IC_TYPE = "+category_type+"	 		\n");
				sb.append("            		 AND IC_CODE IN ("+category_code+")	 			\n");
				
			
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					comp_group = rs.getString("IC_NAME_GP");
				}
				comp_group_arr = comp_group.split(",");
		   	 	
		   	 	sb = new StringBuffer();
		   	 	sb.append("          SELECT A.SG_SEQ																											\n");
				sb.append("                      , A.SG_NAME                                                                                                   \n");
				//sb.append("                      , IFNULL(B.TOTAL,0) AS TOTAL                                                                                  \n");
				//sb.append("                      , IFNULL(B.CJ,0) AS CJ                                                                                        \n");
				//sb.append("                      , IFNULL(B.DAESANG,0) AS DAESANG                                                                              \n");
				//sb.append("                      , IFNULL(B.PULMUONE,0) AS PULMUONE                                                                            \n");
				//sb.append("                      , IFNULL(B.OTTOGI,0) AS OTTOGI                                                                                \n");
				//sb.append("                      , IFNULL(B.DONGWON,0) AS DONGWON                                                                              \n");
				for(int i=0; i<category_code_arr.length; i++){
					sb.append("             	, IFNULL(B.TYPE"+i+",0) AS '"+comp_group_arr[i]+"'                      \n");
				}
				
				sb.append("                   FROM (SELECT SG_SEQ, SG_NAME, SG_ORDER  FROM SITE_GROUP WHERE SG_SEQ IN ("+sg_code+")) A           \n");
				sb.append("                        LEFT OUTER JOIN                                                                                             \n");
				sb.append("                        (                                                                                                           \n");
				sb.append("                        SELECT A.SG_SEQ                                                                                             \n");
				//sb.append("                             , COUNT(*) AS TOTAL                                                                                    \n");
				//sb.append("                             , IFNULL(SUM(IF(B.IC_CODE = 1,1,0)),0) AS CJ                                                           \n");
				//sb.append("                             , IFNULL(SUM(IF(B.IC_CODE = 2,1,0)),0) AS DAESANG                                                      \n");
				//sb.append("                             , IFNULL(SUM(IF(B.IC_CODE = 3,1,0)),0) AS PULMUONE                                                     \n");
				//sb.append("                             , IFNULL(SUM(IF(B.IC_CODE = 4,1,0)),0) AS OTTOGI                                                       \n");
				//sb.append("                             , IFNULL(SUM(IF(B.IC_CODE = 5,1,0)),0) AS DONGWON                                                      \n");
				for(int i=0; i<category_code_arr.length; i++){
					sb.append("             			 , IFNULL(SUM(IF(B.IC_CODE = "+category_code_arr[i]+",1,0)),0) AS 'TYPE"+i+"'                \n");
				}
				sb.append("                          FROM ISSUE_DATA A                                                                                         \n");
				sb.append("                             , ISSUE_DATA_CODE B                                                                                    \n");
				sb.append("                         WHERE A.MD_DATE BETWEEN '"+sDate+"' AND '"+eDate+"' /*범위 설정*/                          \n");
				sb.append("                           AND A.ID_SEQ = B.ID_SEQ                                                                                  \n");
				sb.append("                           AND B.IC_TYPE = "+category_type+"                                                                                        \n");
				sb.append("                           AND B.IC_CODE IN ("+category_code+")                                                                             \n");
				sb.append("                           AND A.SG_SEQ IN ("+sg_code+")                                                   \n");
				sb.append("                         GROUP BY A.SG_SEQ                                                                                          \n");
				sb.append("                        ) B ON A.SG_SEQ = B.SG_SEQ                                                                                  \n");
				sb.append("                        ORDER BY SG_ORDER                                                                                             \n");

				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				
				ArrayList<String[]> data_list = new ArrayList<String[]>();
				String[] data  = new String[comp_group_arr.length+2];
				
				int idx = 0;
				while(rs.next()){
					idx = 0;
					data  = new String[comp_group_arr.length+2];
					data[idx++] = rs.getString("SG_SEQ");
					data[idx++] = rs.getString("SG_NAME");
					for(int i=0; i<comp_group_arr.length; i++){
						data[idx++] =  rs.getString(comp_group_arr[i]);
					}					
					data_list.add(data);
				}
				
				
				arr = new JSONArray();			
				JSONObject obj = new JSONObject();				
				//가로축/세로축 변환
				for(int i=0; i<comp_group_arr.length; i++){
					obj = new JSONObject();
					obj.put("category", (comp_group_arr[i].equals("CJ 제일제당")?"CJ":comp_group_arr[i]));
					for(int j=0; j<data_list.size(); j++){
						obj.put("column-"+(j+1), data_list.get(j)[i+2]);
					}
					arr.put(obj);
				}	
				
				
	   	 	}catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	        }
	   	 	return arr;
	    }
		public JSONObject getIssueTrandReportChartType3(String sDate, String eDate, String category_type,  String category_code){
			JSONObject result = new JSONObject(); 
	    	JSONArray arr = null; 
	    	String comp_group = "";
	    	String[] comp_group_arr = null;
	    	String[] category_code_arr = category_code.split(",");
	    	ArrayList<String[]> category_list = new ArrayList<String[]>();
	    	category_list.add(new String[]{"언론","93"});
	    	category_list.add(new String[]{"커뮤니티","97"});
	    	category_list.add(new String[]{"SNS","99,100,101,102"});
	    	category_list.add(new String[]{"기타","94, 95, 96, 98"});
	    	
	    	int max_length = 0;
	    	int tmp_length = 0;
	    	
	   	 	try {
		   	 	dbconn = new DBconn();
		   	 	dbconn.getDBCPConnection();
		   	 	
		   	 	sb = new StringBuffer();
		   	 	sb.append("            SELECT GROUP_CONCAT(IC_NAME)AS IC_NAME_GP			\n");
		   	 	sb.append("            		FROM ISSUE_CODE	 								\n");
				sb.append("            			WHERE IC_TYPE = "+category_type+"	 		\n");
				sb.append("            		 AND IC_CODE IN ("+category_code+")	 			\n");
				
			
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					comp_group = rs.getString("IC_NAME_GP");
				}
				comp_group_arr = comp_group.split(",");
		   	 	
		   	 	//월별 값 출력
		   	 	sb = new StringBuffer();
				sb.append("         (SELECT '"+category_list.get(0)[0]+"' AS SG_NAME                                                                                   \n");
				//sb.append("              , COUNT(*) AS TOTAL                                                                                  \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 1,1,0)),0) AS CJ                                                         \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 2,1,0)),0) AS DAESANG                                                    \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 3,1,0)),0) AS PULMUONE                                                   \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 4,1,0)),0) AS OTTOGI                                                     \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 5,1,0)),0) AS DONGWON                                                    \n");
				for(int i=0; i<category_code_arr.length; i++){
					sb.append("              , IFNULL(SUM(IF(B.IC_CODE = "+category_code_arr[i]+",1,0)),0) AS '"+comp_group_arr[i]+"'                      \n");
				}
				sb.append("           FROM ISSUE_DATA A                                                                                       \n");
				sb.append("              , ISSUE_DATA_CODE B                                                                                  \n");
				sb.append("          WHERE A.MD_DATE BETWEEN '"+sDate+"' AND '"+eDate+"' /*범위 설정*/                        \n");
				sb.append("            AND A.ID_SEQ = B.ID_SEQ                                                                                \n");
				sb.append("            AND B.IC_TYPE =  "+category_type+"                                                                                     \n");
				sb.append("            AND B.IC_CODE IN ("+category_code+")                                                                           \n");
				sb.append("            AND A.SG_SEQ IN ("+category_list.get(0)[1]+"))                                                                                  \n");
				sb.append("         UNION ALL                                                                                                 \n");
				sb.append("         (SELECT '"+category_list.get(1)[0]+"' AS SG_NAME                                                                                 \n");
				//sb.append("              , COUNT(*) AS TOTAL                                                                                  \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 1,1,0)),0) AS CJ                                                         \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 2,1,0)),0) AS DAESANG                                                    \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 3,1,0)),0) AS PULMUONE                                                   \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 4,1,0)),0) AS OTTOGI                                                     \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 5,1,0)),0) AS DONGWON                                                    \n");
				for(int i=0; i<category_code_arr.length; i++){
					sb.append("              , IFNULL(SUM(IF(B.IC_CODE = "+category_code_arr[i]+",1,0)),0) AS '"+comp_group_arr[i]+"'                      \n");
				}
				sb.append("           FROM ISSUE_DATA A                                                                                       \n");
				sb.append("              , ISSUE_DATA_CODE B                                                                                  \n");
				sb.append("          WHERE A.MD_DATE BETWEEN '"+sDate+"' AND '"+eDate+"' /*범위 설정*/                        \n");
				sb.append("            AND A.ID_SEQ = B.ID_SEQ                                                                                \n");
				sb.append("            AND B.IC_TYPE = "+category_type+"                                                                                      \n");
				sb.append("            AND B.IC_CODE IN ("+category_code+")                                                                           \n");
				sb.append("            AND A.SG_SEQ IN ("+category_list.get(1)[1]+"))                                                                                  \n");
				sb.append("         UNION ALL                                                                                                 \n");
				sb.append("         (SELECT '"+category_list.get(2)[0]+"' AS SG_NAME                                                                                  \n");
				//sb.append("              , COUNT(*) AS TOTAL                                                                                  \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 1,1,0)),0) AS CJ                                                         \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 2,1,0)),0) AS DAESANG                                                    \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 3,1,0)),0) AS PULMUONE                                                   \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 4,1,0)),0) AS OTTOGI                                                     \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 5,1,0)),0) AS DONGWON                                                    \n");
				for(int i=0; i<category_code_arr.length; i++){
					sb.append("              , IFNULL(SUM(IF(B.IC_CODE = "+category_code_arr[i]+",1,0)),0) AS '"+comp_group_arr[i]+"'                      \n");
				}
				sb.append("           FROM ISSUE_DATA A                                                                                       \n");
				sb.append("              , ISSUE_DATA_CODE B                                                                                  \n");
				sb.append("          WHERE A.MD_DATE BETWEEN '"+sDate+"' AND '"+eDate+"' /*범위 설정*/                        \n");
				sb.append("            AND A.ID_SEQ = B.ID_SEQ                                                                                \n");
				sb.append("            AND B.IC_TYPE = "+category_type+"                                                                                      \n");
				sb.append("            AND B.IC_CODE IN ("+category_code+")                                                                           \n");
				sb.append("            AND A.SG_SEQ IN ("+category_list.get(2)[1]+"))                                                                      \n");
				sb.append("         UNION ALL                                                                                                 \n");
				sb.append("         (SELECT '"+category_list.get(3)[0]+"' AS SG_NAME                                                                                   \n");
				//sb.append("              , COUNT(*) AS TOTAL                                                                                  \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 1,1,0)),0) AS CJ                                                         \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 2,1,0)),0) AS DAESANG                                                    \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 3,1,0)),0) AS PULMUONE                                                   \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 4,1,0)),0) AS OTTOGI                                                     \n");
				//sb.append("              , IFNULL(SUM(IF(B.IC_CODE = 5,1,0)),0) AS DONGWON                                                    \n");
				for(int i=0; i<category_code_arr.length; i++){
					sb.append("              , IFNULL(SUM(IF(B.IC_CODE = "+category_code_arr[i]+",1,0)),0) AS '"+comp_group_arr[i]+"'                      \n");
				}
				sb.append("           FROM ISSUE_DATA A                                                                                       \n");
				sb.append("              , ISSUE_DATA_CODE B                                                                                  \n");
				sb.append("          WHERE A.MD_DATE BETWEEN '"+sDate+"' AND '"+eDate+"' /*범위 설정*/                        \n");
				sb.append("            AND A.ID_SEQ = B.ID_SEQ                                                                                \n");
				sb.append("            AND B.IC_TYPE = "+category_type+"                                                                                      \n");
				sb.append("            AND B.IC_CODE IN ("+category_code+")                                                                           \n");
				sb.append("            AND A.SG_SEQ IN ("+category_list.get(3)[1]+"))                                                                      \n");

				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				
				ArrayList<String[]> data_list = new ArrayList<String[]>();
				String[] data  = new String[comp_group_arr.length+1];
				
				int idx = 0;
				while(rs.next()){
					idx = 0;
					data  = new String[comp_group_arr.length+1];
					data[idx++] = rs.getString("SG_NAME");
					for(int i=0; i<comp_group_arr.length; i++){
						data[idx++] =  rs.getString(comp_group_arr[i]);
					}					
					data_list.add(data);
				}
				
				
				arr = new JSONArray();			
				JSONObject obj = new JSONObject();				
				//가로축/세로축 변환
				for(int i=0; i<comp_group_arr.length; i++){
					obj = new JSONObject();
					obj.put("category", (comp_group_arr[i].equals("CJ 제일제당")?"CJ":comp_group_arr[i]));
					tmp_length = 0;
					for(int j=0; j<data_list.size(); j++){
						obj.put("column-"+(j+1), data_list.get(j)[i+1]);
						tmp_length += Integer.parseInt(data_list.get(j)[i+1]);
					}
					if(tmp_length>max_length){
						max_length = tmp_length;
					}
					arr.put(obj);
				}	
				
				result.put("MAX_LENGTH", max_length);
				result.put("LIST", arr);
				
				
	   	 	}catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	        }
	   	 	return result;
	    }
		
		public JSONArray getIssueTrandReportChartType2(String sDate, String eDate, String category_type,  String category_code, String ic_type,  String ic_code, String Diff, int month_term){	
	    	JSONArray arr = null; 
	   	 	try {
		   	 	dbconn = new DBconn();
		   	 	dbconn.getDBCPConnection();
		   	 	
		   	 	String[] month_title = new String[month_term+1];
		   	 	String[] month_set = new String[month_term+1];
		   	 	int month_idx = 0;
		   	 	//월 단위 출력
		   	 	for(int i=0; i< month_term ;i++){
			   	 	sb = new StringBuffer();
			   	 	sb.append("            SELECT DATE_FORMAT(DATE_ADD('"+eDate+"', INTERVAL (-"+month_term+"+"+i+") MONTH),'%Y-%m') AS `MONTH`        \n");
					sb.append("            			,DATE_FORMAT(DATE_ADD('"+eDate+"', INTERVAL (-"+month_term+"+"+i+") MONTH),'%m')AS `MONTH_TIT` 	 \n");
				
					pstmt = dbconn.createPStatement(sb.toString());
					rs = pstmt.executeQuery();
					
					if(rs.next()){
						month_set[month_idx] = rs.getString("MONTH");
						month_title[month_idx] = rs.getString("MONTH_TIT")+"월";
					}
					month_idx++;
		   	 	}
		   	 	
		   	 	month_set[month_idx] = eDate.split("-")[0]+"-"+eDate.split("-")[1];
				month_title[month_idx] = eDate.split("-")[1]+"월";
		   	 	
		   	 	
		   	 	//월별 값 출력
		   	 	sb = new StringBuffer();
		   		sb.append("  SELECT A.IC_CODE                                                                                                                      												\n");
				sb.append("       , A.IC_NAME                                                                                                                     												 \n");
				//sb.append("       , IFNULL(B.MONTH1, 0) AS MONTH1                                                                                                  												\n");
				for(int i=0; i<month_title.length; i++){
					sb.append("       , IFNULL(B."+month_title[i]+", 0) AS '"+month_title[i]+"'                                                                                                												 \n");
				}
				
				sb.append("    FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+category_type+" AND IC_CODE IN ("+category_code+") ) A             												 \n");
				sb.append("         LEFT OUTER JOIN                                                                                                                												\n");
				sb.append("         (                                                                                                                              												\n");
				sb.append("         SELECT B.IC_CODE                                                                                                               													\n");
				//sb.append("              , SUM(IF(DATE_FORMAT(DATE_ADD(A.MD_DATE, INTERVAL -("+Diff+"+1) DAY), '%Y-%m') = DATE_FORMAT(DATE_ADD('"+eDate+"', INTERVAL -6 MONTH),'%Y-%m'),1,0)) AS MONTH1           \n");
				for(int i=0; i<month_set.length; i++){
					sb.append("              , SUM(IF(DATE_FORMAT(A.MD_DATE,'%Y-%m') = '"+month_set[i]+"',1,0)) AS '"+month_title[i]+"'           \n");
				}
				
				sb.append("           FROM ISSUE_DATA A                                                                                                            												\n");
				sb.append("              , ISSUE_DATA_CODE B                                                                                                       												\n");
				sb.append("              , ISSUE_DATA_CODE C                                                                                                       												\n");
				sb.append("          WHERE A.MD_DATE BETWEEN '"+sDate+"' AND '"+eDate+"' /*범위 설정*/                                             												\n");
				sb.append("            AND A.ID_SEQ = B.ID_SEQ                                                                                                     												\n");
				sb.append("            AND A.ID_SEQ = C.ID_SEQ                                                                                                     												\n");
				sb.append("            AND B.IC_TYPE = "+category_type+"                                                                                                           												\n");
				sb.append("            AND B.IC_CODE IN ("+category_code+")                                                                                               												 \n");
				sb.append("            AND C.IC_TYPE = "+ic_type+"                                                                                                          												 \n");
				sb.append("            AND C.IC_CODE = "+ic_code+" /*게시물유형2*/                                                                                               												 \n");
				sb.append("          GROUP BY B.IC_CODE                                                                                                           												 \n");
				sb.append("         )B ON A.IC_CODE = B.IC_CODE                                                                                                    												\n");
			
				System.out.println("-----------------------");
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				
				ArrayList<String[]> data_list = new ArrayList<String[]>();
				String[] data  = new String[month_title.length];
				
				int idx = 0;
				while(rs.next()){
					idx = 0;
					data  = new String[month_title.length];
					for(int i=0; i<month_title.length; i++){
						data[idx++] =  rs.getString(month_title[i]);
					}					
					data_list.add(data);
				}
				
				
				
				arr = new JSONArray();			
				JSONObject obj = new JSONObject();				
				//가로축/세로축 변환
				for(int i=0; i<month_title.length; i++){
					obj = new JSONObject();
					obj.put("category", month_title[i]);
					for(int j=0; j<data_list.size(); j++){
						obj.put("column-"+(j+1), data_list.get(j)[i]);
					}
					arr.put(obj);
				}	
				
				
	   	 	}catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	        }
	   	 	return arr;
	    }
		
		public JSONObject getIssueTrandReportTermType(String sDate, String eDate, String category_type,  String category_code, String ic_type,  String ic_codes, String Month){	
			JSONObject result = new JSONObject();
			JSONArray arr = null; 
	    	int max_length = 0;
	    	int tmp_length = 0;
	   	 	try {
		   	 	dbconn = new DBconn();
		   	 	dbconn.getDBCPConnection();
		   	 	sb = new StringBuffer();
		   	 	sb.append("  # 최근 유포현황                                                                                          \n");
		   		sb.append("  SELECT A.IC_NAME                                                                                             \n");
				sb.append("         , B.IC_CODE                                                                                           \n");
				sb.append("         , IFNULL(B.TYPE1,0) AS TYPE1                                                                          \n");
				sb.append("         , IFNULL(B.TYPE2,0) AS TYPE2                                                                          \n");
				sb.append("         , IFNULL(B.TYPE3,0) AS TYPE3                                                                          \n");
				sb.append("         , IFNULL(B.TYPE4,0) AS TYPE4                                                                          \n");
				sb.append("         , IFNULL(B.TYPE5,0) AS TYPE5                                                                          \n");
				sb.append("         , IFNULL(B.TYPE7,0) AS TYPE7                                                                          \n");
				sb.append("         , IFNULL(B.TYPE8,0) AS TYPE8                                                                          \n");
				sb.append("     FROM (                                                                                                    \n");
				sb.append("     	SELECT I.IC_CODE, FN_ISSUE_NAME(I.IC_CODE, I.IC_TYPE) AS IC_NAME                                          \n");
				sb.append("     		FROM  ISSUE_CODE I                                                                                        \n");
				sb.append("     			WHERE I.IC_TYPE = "+category_type+"                                                                                       \n");
				sb.append("    					 AND I.IC_CODE IN ("+category_code+")                                                                              \n");
				sb.append("      				 GROUP BY I.IC_CODE                                                                                      \n");
				sb.append("    	 )A LEFT OUTER JOIN (                                                                                      \n");
				sb.append(" 		SELECT B.IC_CODE                                                                                              \n");
				sb.append("     	   	, FN_ISSUE_NAME(B.IC_CODE, B.IC_TYPE) AS IC_NAME                                                         \n");
				sb.append("      	   	, ROUND((SUM(IF(C.IC_CODE = 1, 1,0))),0) AS TYPE1                                          \n");
				sb.append("      	   	, ROUND((SUM(IF(C.IC_CODE = 2, 1,0))),0) AS TYPE2                                          \n");
				sb.append("      	   	, ROUND((SUM(IF(C.IC_CODE = 3, 1,0))),0) AS TYPE3                                          \n");
				sb.append("     	   	, ROUND((SUM(IF(C.IC_CODE = 4, 1,0))),0) AS TYPE4                                          \n");
				sb.append("      	   	, ROUND((SUM(IF(C.IC_CODE = 5, 1,0))),0) AS TYPE5                                          \n");
				sb.append("     	   	, ROUND((SUM(IF(C.IC_CODE = 7, 1,0))),0) AS TYPE7                                          \n");
				sb.append("      	   	, ROUND((SUM(IF(C.IC_CODE = 8, 1,0))),0) AS TYPE8                                          \n");
				sb.append("   		FROM ISSUE_DATA A                                                                                           \n");
				sb.append("     		, ISSUE_DATA_CODE B                                                                                      \n");
				sb.append("     	 	, ISSUE_DATA_CODE C                                                                                      \n");
				sb.append("  		WHERE A.MD_DATE BETWEEN '"+sDate+"' AND '"+eDate+"' /*범위 설정*/                            \n");
				sb.append("    			AND A.ID_SEQ = B.ID_SEQ                                                                                    \n");
				sb.append("    			AND A.ID_SEQ = C.ID_SEQ                                                                                    \n");
				sb.append("    			AND B.IC_TYPE = "+category_type+"                                                                                          \n");
				sb.append("    			AND B.IC_CODE IN ("+category_code+")                                                                               \n");
				sb.append("   			AND C.IC_TYPE = "+ic_type+"                                                                                          \n");
				sb.append("    			AND C.IC_CODE IN ("+ic_codes+")                                                                           \n");
				sb.append("  	GROUP BY B.IC_CODE                                                                                           \n");
				sb.append("  )B ON A.IC_CODE = B.IC_CODE                                                                                  \n");
			
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("category", (rs.getString("IC_NAME").equals("CJ 제일제당")?"CJ":rs.getString("IC_NAME")));
				obj.put("column-1", rs.getString("TYPE1"));
				obj.put("column-2", rs.getString("TYPE2"));
				obj.put("column-3", rs.getString("TYPE3"));
				obj.put("column-4", rs.getString("TYPE4"));
				obj.put("column-5", rs.getString("TYPE5"));
				obj.put("column-6", rs.getString("TYPE7"));
				obj.put("column-7", rs.getString("TYPE8"));
				arr.put(obj);
				tmp_length = rs.getInt("TYPE1")+rs.getInt("TYPE2")+rs.getInt("TYPE3")+rs.getInt("TYPE4")+rs.getInt("TYPE5")+rs.getInt("TYPE7")+rs.getInt("TYPE8");
				if(tmp_length>max_length){
					max_length = tmp_length;
				}
			}
				
			result.put("MAX_LENGTH", max_length );
			result.put("LIST", arr );
			
			
	   	 	}catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	        }
	   	 	return result;
	    }
		
	    public JSONObject getVocTrendData(String sDate, String eDate, String sTime, String eTime, String category_type,  String category_code, String ic_type,  String ic_codes){	
	    	JSONObject result = new JSONObject();
	    	JSONArray arr = null; 
	    	int max_length = 0;
	    	int tmp_length = 0;
	   	 	try {
		   	 	dbconn = new DBconn();
		   	 	dbconn.getDBCPConnection();
		   	 	sb = new StringBuffer();
		   		sb.append("    SELECT A.IC_CODE																										\n");
				sb.append("         , A.IC_NAME                                                                                                     \n");
				sb.append("         , IFNULL(B.CNT,0) AS CNT                                                                                        \n");
				sb.append("      FROM (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+ic_type+" AND IC_CODE IN ("+ic_codes+")) A         \n");  
				sb.append("           LEFT OUTER JOIN                                                                                               \n");
				sb.append("           (                                                                                                             \n");
				sb.append("              SELECT C.IC_CODE                                                                                           \n");
				sb.append("                   , COUNT(*) AS CNT                                                                                     \n");
				sb.append("                FROM ISSUE_DATA A                                                                                        \n");
				sb.append("                   , ISSUE_DATA_CODE B                                                                                   \n");
				sb.append("                   , ISSUE_DATA_CODE C                                                                                   \n");
				sb.append("               WHERE A.MD_DATE BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"'/*당기*/                             \n");
				sb.append("                 AND A.ID_SEQ = B.ID_SEQ                                                                                 \n");
				sb.append("                 AND A.ID_SEQ = C.ID_SEQ                                                                                 \n");
				sb.append("                 AND B.IC_TYPE = "+category_type+"                                                                       \n");
				sb.append("                 AND B.IC_CODE = "+category_code+" /*사업운영본부*/                                                          \n");
				sb.append("                 AND C.IC_TYPE = "+ic_type+"                                                                             \n");
				sb.append("                 AND C.IC_CODE IN ("+ic_codes+") /*1번에서 나온값*/                                                           \n");
				sb.append("               GROUP BY C.IC_CODE                                                                                        \n");
				sb.append("           )B ON A.IC_CODE = B.IC_CODE                                                                                   \n");
				sb.append("    ORDER BY A.IC_CODE                                                                                                   \n");
			
				
			
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				arr = new JSONArray();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				obj.put("category", rs.getString("IC_NAME").replace("/", "\n").replace(" ", "\n")  );			
				obj.put("column-1", rs.getString("CNT"));
				tmp_length = rs.getInt("CNT");
				arr.put(obj);
				
				if(tmp_length>max_length){
					max_length = tmp_length;
				}
			}
			
			result.put("LIST", arr);
			result.put("MAX_LENGTH", max_length);
				
	   	 	}catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	        }
	   	 	return result;
	    }
	    
		public String getFoodTrendReportType(String type, String codes){			
			String result = "";
			
			try {
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				
				
				sb = new StringBuffer();
				
				sb.append("SELECT GROUP_CONCAT(IC_CODE) AS IC_CODES      \n");
				sb.append("	FROM ISSUE_CODE                        		 \n");
				sb.append(" 	WHERE IC_TYPE = "+type+"               		\n");
				sb.append("		AND IC_PCODE IN ("+codes+")		       		\n");
				
				//System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				if( rs.next() ) {
					result = rs.getString("IC_CODES");
				}				
				
			} catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
				if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
			}
			
			
			return result;
		}
	
}
    
