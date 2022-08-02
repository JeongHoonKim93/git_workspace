<%@page import="risk.dashboard.main.MainMgr"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.HashMap"%>
<%@page import="risk.util.Paging"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%	
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();	
	MainMgr mgr = new MainMgr();
 
	String sDate = pr.getString("sDate");
	String eDate = pr.getString("eDate");
	String sg_seqs = pr.getString("sg_seqs");
	String keyword_type = pr.getString("keyword_type");
	String search_keyword = pr.getString("search_keyword");
	String product_code = pr.getString("product_code", "");
	//String issue_name = pr.getString("issue_name", "");	
	String company_code = pr.getString("company_code");	
	String senti_type = pr.getString("senti_type");	
	String mode = pr.getString("mode");	
	
	ArrayList<HashMap<String, String>> info_list = mgr.get_VOCInformationSentiGraph(sDate, eDate, company_code, product_code, sg_seqs, keyword_type, search_keyword);	
	
	for(int i=0; i < info_list.size(); i++){    
		if(mode.equals("voc_information") && company_code.equals("1")){
  		%>  <tr style="height: 46px;"> 
	  	<%} else if(mode.equals("pr_information") && company_code.equals("1")) {%>
	  		<tr style="height: 74px;"> 
	  	<%} else if(mode.equals("plcc_information") && company_code.equals("1")) {%>
	  		<tr style="height: 30px;"> 
	  	<%} else if(company_code.equals("2")) {%>	
	  		<tr style="height: 93px;"> 
	  	<%} else if(company_code.equals("3")) {%>	
	  		<tr style="height: 75px;"> 
	  	<%} %>		  	
	            <td></td>
	            <td><a href="#" class="ui_lnk" onclick="showPopup('rowCnt=10&sg_seqs=<%=sg_seqs%>&sDate=<%=sDate%>&eDate=<%=eDate%>&TypeCode=<%=info_list.get(i).get("TYPE")%>:<%=info_list.get(i).get("CODE")%>@<%=senti_type%>:1&company_code=<%=company_code%>&keyword_type=<%=keyword_type%>&search_keyword=<%=search_keyword%>'); return false; "><span class="txt"><%=info_list.get(i).get("POS")%></span></a></td>
	            <td><a href="#" class="ui_lnk" onclick="showPopup('rowCnt=10&sg_seqs=<%=sg_seqs%>&sDate=<%=sDate%>&eDate=<%=eDate%>&TypeCode=<%=info_list.get(i).get("TYPE")%>:<%=info_list.get(i).get("CODE")%>@<%=senti_type%>:2&company_code=<%=company_code%>&keyword_type=<%=keyword_type%>&search_keyword=<%=search_keyword%>'); return false; "><span class="txt"><%=info_list.get(i).get("NEG")%></span></a></td>
	            <td><a href="#" class="ui_lnk" onclick="showPopup('rowCnt=10&sg_seqs=<%=sg_seqs%>&sDate=<%=sDate%>&eDate=<%=eDate%>&TypeCode=<%=info_list.get(i).get("TYPE")%>:<%=info_list.get(i).get("CODE")%>@<%=senti_type%>:3&company_code=<%=company_code%>&keyword_type=<%=keyword_type%>&search_keyword=<%=search_keyword%>'); return false; "><span class="txt"><%=info_list.get(i).get("NEU")%></span></a></td>
	            <td><a href="#" class="ui_lnk" onclick="showPopup('rowCnt=10&sg_seqs=<%=sg_seqs%>&sDate=<%=sDate%>&eDate=<%=eDate%>&TypeCode=<%=info_list.get(i).get("TYPE")%>:<%=info_list.get(i).get("CODE")%>&company_code=<%=company_code%>&keyword_type=<%=keyword_type%>&search_keyword=<%=search_keyword%>'); return false; "><span class="txt"><%=info_list.get(i).get("TOTAL_CNT")%></span></a></td>
	        </tr>
  <%}%>          