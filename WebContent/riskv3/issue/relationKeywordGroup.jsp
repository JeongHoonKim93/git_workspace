<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.issue.IssueMgr"%>
<%@ page import="risk.issue.IssueDataBean"%>
<%@include file="../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);

	IssueMgr im = new IssueMgr();
	IssueDataBean idb = new IssueDataBean();

	String typeCode10 = pr.getString("typeCode10", "");
	String ic_type = "";
	String ic_code = "";
	
	if(!typeCode10.equals("")) {
		ic_type = typeCode10.split(",")[0];
		ic_code = typeCode10.split(",")[1];	//이거에 따라서 저장된 연관어 바뀌게 하기
	}
	//String type = pr.getString("type");
	
	ArrayList relationKeyword = new ArrayList();
	if(!ic_code.equals("")){
		relationKeyword = im.getRelationKeyword2(ic_type,ic_code);
	}
	
	if(relationKeyword.size() > 0){
		for(int i = 0; i < relationKeyword.size(); i++){
			idb = (IssueDataBean)relationKeyword.get(i);
			if(i != 0){
				out.print("&nbsp;");
			}
///			out.print("<span style=\"cursor:pointer;font-weight:bold\" onclick=\"addTagKeySel('"+idb.getItc_name()+"', '0');\">["+idb.getItc_name()+"]</span>");
			out.print("<span style=\"cursor:pointer;font-weight:bold\" onclick=\"addKeyword2('"+i+"','"+ic_code+"', '"+idb.getRk_name()+"');\">["+idb.getRk_name()+"]</span>");
		}
	}
%>