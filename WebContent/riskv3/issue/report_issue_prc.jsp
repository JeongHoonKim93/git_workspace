<%@page import="risk.issue.IssueDataBean"%>
<%@page import="risk.issue.IssueMgr"%>
<%@page import="risk.admin.log.LogMgr"%>
<%@page import="risk.admin.log.LogConstants"%>
<%@page import="risk.admin.log.LogBean"%>
<%@page import="risk.issue.IssueReportBean"%>
<%@page import="risk.issue.IssueReportMgr"%>
<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import = "risk.util.ParseRequest" %>
<%@ page import = "risk.util.StringUtil" %>
<%@ page import = "risk.util.DateUtil" %>
<%@ page import = "java.util.ArrayList" %>
<%@ include file="../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	StringUtil su = new StringUtil();
	DateUtil du = new DateUtil();
	IssueReportMgr irMgr = new IssueReportMgr();
	IssueReportBean irBean = new IssueReportBean();
	
	LogMgr logMgr = new LogMgr();
	LogBean logBean = new LogBean();
	IssueMgr issueMgr = new IssueMgr();
	
	// Parameter
	String mode = pr.getString("mode");
	String id_seq = pr.getString("id_seq");
	String id_title = pr.getString("id_title");
	
	pr.printParams();
	
	String result="fail";
	String execute = "";
	String ir_html = "";
	
	
	if( mode.equals("IS")) {
		
		irBean.setIr_title(id_title.replaceAll("'",""));
		ir_html = su.encodingRequestPageByGet(SS_URL+"riskv3/report/issue_report_form_issue.jsp?id_seq="+id_seq,"UTF-8");
		System.out.println("ir_html"+ir_html);
		irBean.setIr_html(su.dbString(ir_html));
		irBean.setIr_memo("");
		irBean.setIr_type(mode);
		System.out.println("mode"+mode);
		irBean.setIr_mailyn("N");
		irBean.setIr_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));	
		execute = irMgr.insertReport(irBean);
		
		logBean.setKey(execute); //리포트 저장후 리포트 번호 셋팅
		logBean.setL_kinds(LogConstants.getReportKindsVal());
		logBean.setL_type(LogConstants.getMailTypeVal());
		logBean.setL_ip(request.getRemoteAddr());
		logBean.setM_seq(SS_M_NO);
		logBean.setL_seq(logMgr.insertLog(logBean)); //로그 저장후 로그 번호 셋팅
		
		
		if(null!= execute
			 && !"".equals(execute) 
		 ){
			result = "success";
		}
		
	
	}else if(mode.equals("OIS")){
		irBean.setIr_title(id_title.replaceAll("'",""));
		ir_html = su.encodingRequestPageByGet(SS_URL+"riskv3/report/issue_report_form_onlne_issue.jsp?id_seq="+id_seq,"UTF-8");
		System.out.println("ir_html"+ir_html);
		irBean.setIr_html(su.dbString(ir_html));
		irBean.setIr_memo("");
		irBean.setIr_type(mode);
		System.out.println("mode"+mode);
		irBean.setIr_mailyn("N");
		irBean.setIr_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));	
		execute = irMgr.insertReport(irBean);
		
		logBean.setKey(execute); //리포트 저장후 리포트 번호 셋팅
		logBean.setL_kinds(LogConstants.getReportKindsVal());
		logBean.setL_type(LogConstants.getMailTypeVal());
		logBean.setL_ip(request.getRemoteAddr());
		logBean.setM_seq(SS_M_NO);
		logBean.setL_seq(logMgr.insertLog(logBean)); //로그 저장후 로그 번호 셋팅
		
		
		if(null!= execute
			 && !"".equals(execute) 
		 ){
			result = "success";
		}
	}
	out.print(result);
%>

