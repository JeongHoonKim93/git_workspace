<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="java.util.ArrayList
				,risk.util.*
				,risk.issue.*
				" %>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%
	response.setContentType("text/html;charset=UTF-8");
	response.setHeader("Cache-Control 	", "no-cache");
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	String mode = pr.getString("mode");
	String md_seq = pr.getString("md_seq");
	String checkdNo = "";
	String selected = "";
	IssueCodeMgr icm = new IssueCodeMgr();
	IssueDataBean idBean = new IssueDataBean();
	IssueMgr issueMgr = new IssueMgr();
	
	if(mode.equals("update")){
		idBean = issueMgr.getIssueDataBean(md_seq);
	}
	icm.init(0);
	ArrayList arrIcBean = icm.GetType(19);
	ArrayList arrIcBean2 = icm.getIssueCode(SS_MG_NO, 19);	
	IssueCodeBean icBean = new IssueCodeBean();
	
	out.print("<select name='typeCode19' style='width: 105px'>");
	out.print("<option value=''>선택하세요</option>");
	
	if(mode.equals("insert") || mode.equals("multi")){ //조합장 선거
		//selected = "1,1";
		selected = "";
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),19);
	}
	
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){ //이슈 수정
			
			checkdNo = icBean.getIc_type()+","+icBean.getIc_code();			
			for(int j=1;j<arrIcBean2.size();j++){
				IssueCodeBean icBean2 = (IssueCodeBean)arrIcBean2.get(j);	
				if(icBean.getIc_code() == icBean2.getIc_code()){
					out.print("<option value='"+icBean.getIc_type()+", "+ icBean2.getIc_code()+"' selected>"+icBean2.getIc_name()+"</option>");
				}
			}

		}else{ //신규등록
			for(int j=1;j<arrIcBean2.size();j++){
				IssueCodeBean icBean2 = (IssueCodeBean)arrIcBean2.get(j);	
				if(icBean.getIc_code() == icBean2.getIc_code()){
					out.print("<option value='"+icBean.getIc_type()+", "+ icBean2.getIc_code()+"'>"+icBean2.getIc_name()+"</option>");
				}
			}		
		}
		//if(i==6 && i!=0)out.print("<br>");
	}
	
	out.print("</select>");
	out.print("&nbsp;&nbsp;");
	out.print("<img src='../../images/issue/plus_btn.gif' onclick='add_IC();' style='vertical-align: bottom'>");
	out.print("<input type='hidden' name='focus_typeCode112' value='"+checkdNo+"'>");
%>