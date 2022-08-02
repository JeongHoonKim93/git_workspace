<%@page import="java.util.HashMap"%>
<%@page import="risk.admin.membergroup.membergroupBean"%>
<%@page import="risk.admin.membergroup.membergroupMng"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.util.DateUtil"%>
<%@ page import="risk.admin.keyword.KeywordMng"%>
<%@ page import="risk.admin.keyword.KeywordBean"%>
<%@ page import="risk.admin.member.MemberDao"%>
<%@include file="../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	KeywordMng km = new KeywordMng();
	membergroupMng mgm = new membergroupMng();
	membergroupBean mgb = new membergroupBean();
	MemberDao md = new MemberDao();

	String today = du.getCurrentDate("yyyy-MM-dd");
	String yesterday = du.addDay(today, -1);
	String current_hour = du.getCurrentTime().split(":")[0];
	
	
	String upKeywordType = pr.getString("upKeywordType");
	String xp = pr.getString("xp");
	String yp = pr.getString("yp");
	int row_limit  = 30;
	
	String basePage = "search_main_contents.jsp?searchmode=ALLKEY";
	
	//mgb = mgm.getMGBean(SS_MG_NO);
	xp = mgb.getMGxp();
	
	ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
	HashMap<String,String> item = new HashMap<String,String>(); 
	
	//수집량
	if("3".equals(upKeywordType)){
		result = km.getCntOfDecrease(today.replaceAll("-", ""), yesterday.replaceAll("-", ""), current_hour, row_limit);
		//데이터 없으면 한시간 전  데이터로 가져오기
		if(result.size()==0){
			result = km.getCntOfDecrease(today.replaceAll("-", ""), yesterday.replaceAll("-", ""), String.valueOf(Integer.parseInt(current_hour)-1) , row_limit);
		}
	//증가량
	}else if("2".equals(upKeywordType)){
		result = km.getCntOfIncrease(today.replaceAll("-", ""), yesterday.replaceAll("-", ""), current_hour, row_limit);
		//데이터 없으면 한시간 전  데이터로 가져오기
		if(result.size()==0){
			result = km.getCntOfIncrease(today.replaceAll("-", ""), yesterday.replaceAll("-", ""), String.valueOf(Integer.parseInt(current_hour)-1), row_limit);
		}
	//증가율
	}else{
		result = km.getRateOfDecrease(today.replaceAll("-", ""), yesterday.replaceAll("-", ""), current_hour, row_limit);
		//데이터 없으면 한시간 전  데이터로 가져오기
		if(result.size()==0){
			result = km.getRateOfDecrease(today.replaceAll("-", ""), yesterday.replaceAll("-", ""), String.valueOf(Integer.parseInt(current_hour)-1), row_limit);
		}
	}
%>
<table width="230" border="0" cellpadding="0" cellspacing="1" bgcolor="D5D5D5">
<%
if(result.size() > 0){
	for(int i = 0; i < result.size(); i++){
		item = result.get(i);
		String images = "";
		String jisu = "";
		if("1".equals(item.get("POINTER"))){
			images = SS_URL+"images/search/up_red.gif";
		}else if("-1".equals(item.get("POINTER"))){
			images = SS_URL+"images/search/down_blue.gif";
		}else{
			images = "";
		}
		
%>
	<tr>
		 <td width="102px;" height="26" bgcolor="#FFFFFF" style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
		<table border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed; text-overflow:ellipsis;overflow:hidden;white-space:nowrap;"> 
			<tr>
				<td width="97px;" style="text-overflow:ellipsis;overflow:hidden; display:inline-block; white-space:nowrap; padding-left: 5px;" title="<%=item.get("K_VALUE")%>"><%=item.get("K_VALUE")%></td>		
			</tr>
		</table>
		</td>
		
		<td width="36" align="center" bgcolor="#FFFFFF" style="cursor:pointer" onclick="javascript:selectTopKeyword('1','<%=item.get("K_XP")%>','<%=item.get("K_YP")%>','<%=item.get("K_ZP")%>')"><%=item.get("P_CNT")%></td>
		<td width="36" align="center" bgcolor="#FFFFFF" style="cursor:pointer" onclick="javascript:selectTopKeyword('0','<%=item.get("K_XP")%>','<%=item.get("K_YP")%>','<%=item.get("K_ZP")%>')"><%=item.get("C_CNT")%></td>		
		<td width="51" align="center" bgcolor="#FFFFFF"><%if(!images.equals("")){%><img src="<%=images%>"><%}%><%=item.get("GAP")%></td>
	</tr>
	
<%
	}
}
%>
</table>