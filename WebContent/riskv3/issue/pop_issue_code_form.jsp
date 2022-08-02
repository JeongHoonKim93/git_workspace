<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import = "risk.util.ParseRequest" %>
<%@include file="../inc/sessioncheck.jsp"%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	String param_ic_type = pr.getString("param_ic_type");
	
	String popup_title = "";
	
	if("11".equals(param_ic_type)){
		popup_title = "온라인뉴스 추가";
		
	} else if("12".equals(param_ic_type)) {
		popup_title = "보도자료 추가";
		
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title></title>
<link rel="stylesheet" type="text/css" href="../../css/base.css" />
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/common.js"  type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script>
function add_issue_code(){
	
	var f = document.fSend;
	var value = f.issue_code.value;
	
	if(value==null){
		alert('값을 입력해 주십시오.');
		return false;
	}
	
	f.target='if_prc';
	f.action='issue_code_prc.jsp';
    f.submit();
}
</script>
</head>
<body>
<iframe id="if_prc" name="if_prc" width="100%" height="0" src="about:blank"></iframe>
<form name="fSend" id="fSend" action="#" method="post" onsubmit="return false;">
<input name="param_ic_type" id="param_ic_type" type="hidden" value="<%=param_ic_type%>">
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td id="pop_head">
			<p><%=popup_title %></p>
			<span><a href="javascript:close();"><img src="../../images/search/pop_tit_close.gif"></span>	
		</td>
		
	</tr>
	<tr>
		<td>
			<input type="text" class="textbox3" name="issue_code" size="27">
			<img src="../../images/search/btn_save_2.gif" align="absmiddle" onclick="add_issue_code();" style="cursor:pointer;align:left;valign:middle"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>