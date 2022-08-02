<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="risk.util.ParseRequest
				,risk.util.StringUtil
				,java.util.ArrayList
				,risk.issue.IssueCodeMgr
				,risk.issue.IssueCodeBean
				"%>
<%

	//이슈데이터 등록 관련
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	StringUtil su = new StringUtil();
	IssueCodeMgr icm = new IssueCodeMgr();
	IssueCodeBean icb = new IssueCodeBean();
	
	//String sel_issue = pr.getString("sel_issue","");
	
	String targetValue = pr.getString("targetValue","");
	
	String selectChk = "";
	
	String ic_pType = "";
	String ic_pCode = "";
	if(!targetValue.equals("")){
		ic_pType = targetValue.split(",")[0];
		ic_pCode = targetValue.split(",")[1];
	}
	
	ArrayList ar_data = icm.getSearchIssueCode("7",ic_pType, ic_pCode);
	
	String result = "";
	
	//json 데이터 만들기~
	if(ar_data != null){
		result += "[";
		IssueCodeBean bean = null;
		for(int i = 0; i < ar_data.size(); i++){
			bean = (IssueCodeBean) ar_data.get(i);
			
			result += "{ \"type\":\""+ bean.getIc_type() +"\""
			        + ", \"code\":\""+ bean.getIc_code() +"\""
			        + ", \"name\":\""+ bean.getIc_name() +"\""
			        + "}";
			        
			if(i != ar_data.size() -1){
				result += ",";	
			}
		}
		
		result += "]";
	}
	System.out.print(result);
	out.print(result);
	
%>           			

<%-- 
<table>
<%					
				for (int i = 0; i < ar_data.size(); i++) {
					icb = (IssueCodeBean)ar_data.get(i);
					out.print("<div><input type='checkbox' name='typeCode7' id='typeCode7_"+i+"' onclick='getTypeCode9("+i+")' value='"+icb.getIc_type()+","+icb.getIc_code()+"'>" + icb.getIc_name()+"</div>"			
							+"<div id='typeCode9_"+i+"' style='margin-left:5px;display:none;'><span style='background:#f7f7f7'>성향*</span><input type='radio' name='typeCode9' value='9,1' >긍정<input type='radio' name='typeCode9' value='9,2' >부정<input type='radio' name='typeCode9' value='9,3' >중립</div>"
							+"<br/>");
%>
	<tr>
		<td>
			<input type='checkbox' value="<%=icb.getIc_type()%>,<%=icb.getIc_code()%>" <%=selectChk%>><%=icb.getIc_name()%>
		</td>
		<th style="background-image: url('../../images/common/icon_dot.gif')10px 50% no-repeat;padding-left:15px; color:#555555;">성향*</th>
		<td>
			<input type='radio' name='typeCode9' value='9,1' >긍정
			<input type='radio' name='typeCode9' value='9,2' >부정
			<input type='radio' name='typeCode9' value='9,3' >중립
		</td>
	</tr>
<%
				}
%>
</table> --%>
