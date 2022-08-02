<%@ page language="java" contentType="text/json; charset=UTF-8"pageEncoding="UTF-8"%>
<%@ page import="risk.util.ParseRequest
				,risk.util.StringUtil
				,java.util.ArrayList
				,risk.voc.VocDataMgr
				,risk.voc.VocBean
				"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%

	//이슈데이터 등록 관련
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	String resend = pr.getString("resend");
	
	VocDataMgr vMgr = new VocDataMgr();
	
	ArrayList arData = vMgr.getCategoryUser("PRO", resend);
	
	String result = "";
	
	//json 데이터 만들기~
	if(arData != null){
		result += "[";
		VocBean.CategoryUserBean childBean = null;
		for(int i = 0; i < arData.size(); i++){
			childBean = (VocBean.CategoryUserBean) arData.get(i);
			
			result += "{"
				    + " \"m_seq\":\""+ childBean.getM_seq() +"\""
			        + ",\"m_name\":\""+ childBean.getM_name() +"\""
			        + "}";
			        
			if(i != arData.size() -1){
				result += ",";	
			}
		}
		
		result += "]";
	}	
	out.print(result);
%>




