<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
                 risk.util.ParseRequest,
                 risk.report.ReportMgr_hycard" 
%>
<%
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	
	String SS_M_ID = (String)session.getAttribute("SS_M_ID")   == null ? "": (String)session.getAttribute("SS_M_ID");
	
	ReportMgr_hycard hyMgr = new ReportMgr_hycard();
	
	String mode = pr.getString("mode");
	String md_seqs = pr.getString("md_seqs", "");
	String rank_list = pr.getString("rank_list", "");
	boolean result = false;
 	if(mode.equals("del")){		
 		if(hyMgr.DeleteConsumerNews(md_seqs)) {
 			result = true;
 		}

 	} else if(mode.equals("up")) {
 		result = true;
 	} else if(mode.equals("update")) {
 		if(hyMgr.UpdateConsumerNews(md_seqs, rank_list)) {
			result = true;
 		}
 	} else {
 		if(hyMgr.RestoreConsumerNews(md_seqs)) {
			result = true;
 		}
 	} 

 	out.println(result);
%>
