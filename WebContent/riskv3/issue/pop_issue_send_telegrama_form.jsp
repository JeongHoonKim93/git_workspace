<%@page import="risk.sms.TelegramDao"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.util.DateUtil"%>
<%@ page import="risk.util.StringUtil"%>
<%@ page import="risk.search.MetaBean"%>
<%@ page import="risk.search.MetaMgr"%>
<%@ page import="risk.issue.IssueCodeMgr"%>
<%@ page import="risk.issue.IssueDataBean"%>
<%@ page import="risk.issue.IssueMgr"%>
<%@ page import="risk.issue.IssueCodeBean"%>
<%@include file="../inc/sessioncheck.jsp"%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	DateUtil	 du = new DateUtil();
	StringUtil	 su = new StringUtil();
	MetaMgr  metaMgr = new MetaMgr();

	
	// Parameter
	String md_seq = pr.getString("md_seq");
	
	// Bean
	IssueDataBean idBean = new IssueDataBean();
	MetaBean metaBean = new MetaBean();
	TelegramDao dao = new TelegramDao();
	int ic_seq = 0;
	String relationkeywordR_str = "";
	
	ArrayList<String[]> receiver_list = dao.getTelegramGroupList();

	metaBean = metaMgr.getMetaData(md_seq, "");
	
%>

<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="../../css/base.css" />
</head>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/common.js"  type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script>
	
	function send_telegram(mode){
		//수신자
		var chk_receivers=''; 
		var chk_obj = document.getElementsByName("chk_receiver");
		
		for(var i=0;i<chk_obj.length;i++){
			if(chk_obj[i].checked==true){
				if('' == chk_receivers){
					chk_receivers = chk_obj[i].value;
				}else{
					chk_receivers = chk_receivers+','+chk_obj[i].value;
				}
				
			}
		}
		
		var f = document.fSend;		
		f.mode.value = mode;	
		f.receivers.value = chk_receivers;
		f.msg.value = document.getElementById("send_msg").value;
		f.target='st_prc';
		f.action='issue_data_prc.jsp';
  	    f.submit();
	}
	



</script>
<body style="overflow: hidden;">
	<iframe id="st_prc" name="st_prc" width="100%" height="0" src="about:blank"></iframe>
	<form name="fSend" id="fSend" action="#" method="post" onsubmit="return false;">
	<input name="md_seq" id="md_seq" type="hidden" value="<%=md_seq%>">
	<input name="mode" id="mode" type="hidden" value="">
	<input name="receivers" id="receivers" type="hidden" value="">
	<input name="msg" id="msg" type="hidden" value="">
	

	<div id="pop_head">
	<p>텔레그램 발송</p>
	<span><a href="javascript:close();"><img src="../../images/search/pop_tit_close.gif"></a></span>
	</div>
	<div style="width:100%;">
			<br>
			
			<div>
				<div>
					<table style="width:90%; margin-left: 20px;" border="0" cellpadding="0" cellspacing="0">
				
					<tr>
						<td style="height:30px;"><span class="sub_tit">해당 이슈 기본 정보</span></td>
					</tr>
					<tr>
						<td>
						<table id="board_write" border="0" cellspacing="0">
							<tr style="height: 30px;">
								<th><span class="board_write_tit">제목</span></th>
								<td colspan="3" ><span style="width:100%; font-size:15px;" id="id_title" name="id_title"><%=su.ChangeString(metaBean.getMd_title())%></span></td>
							</tr>
							
							<tr style="height: 30px;">
								<th><span class="board_write_tit">URL</span></th>
								<td colspan="3" ><span style="width:100%;  font-size:15px;" name="id_url"><%=metaBean.getMd_url()%></span></td>
							</tr>
							
						
						</table>
						</td>
					</tr>
					</table>
				
				</div>
			</div>
			<br>
			<div style="width:80%;" >
					<span class="sub_tit" style="margin-left: 20px; font-size: 12px;">발송 내용</span> 
			</div>
			<div style="padding: 10px 20px 30px 20px;">
				<textarea rows="10" cols="90" id="send_msg"><%=su.ChangeString(metaBean.getMd_title())%><%=metaBean.getMd_url()%></textarea>
			</div>
			
			<div style="width:100%;margin-bottom: 10px;" >
					<span class="sub_tit" style="margin-left: 20px; font-size: 12px;">텔레그램 수신 그룹 설정</span> 
			</div>
			<div style="padding-left:20px;">
				<table style="width:95%;" id="board_write" border="0" cellspacing="0">
					<tr style="height: 30px;">
						<th><span class="board_write_tit">그룹명</span></th>
						<td>
							<div>
						<%if(receiver_list.size() > 0){
							for(int i=0; i<receiver_list.size(); i++){
							%>
								<div class="dcp ui_vt" style="width:300px;"><input type="checkbox" checked="" class="board_write_tit" id="search_site_group_<%=i%>" name="chk_receiver"  value="<%=receiver_list.get(i)[0]%>@<%=receiver_list.get(i)[2]%>"><label for="search_site_group_<%=i%>"><%=receiver_list.get(i)[1]%></label></div>
								<br/>
						<%
							}
						} %>
								
							</div>
						</td>
					</tr>
				</table>
			</div>
			</form>
			<div style="width: 100%; margin-top: 30px; margin-left: 300px;">
				<img src="../../images/search/btn_send.gif" onclick="send_telegram('send_telegram');"  style="cursor:pointer;">&nbsp;<img src="../../images/search/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"/>
			</div>

</body>
</html>