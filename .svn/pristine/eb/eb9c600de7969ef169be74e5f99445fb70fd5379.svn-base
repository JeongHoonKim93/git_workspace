<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="UTF-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.admin.keyword.KeywordMng"%>
<%@ page import="risk.admin.keyword.KeywordBean"%>
<%@ page import="risk.sms.AddressBookDao"%>
<%@ page import="risk.sms.AddressBookGroupBean"%>
<%@ page import="risk.admin.alarm.alarmMgr"%>
<%@ page import="risk.admin.alarm.alarmBean"%>
<%@ page import="java.util.Iterator"%>
<%
	ParseRequest pr = new ParseRequest(request);
	KeywordMng km = new KeywordMng();
	KeywordBean kb = new KeywordBean();
	alarmMgr am = new alarmMgr();
	alarmBean ab = new alarmBean();
	
	String ar_seqs = pr.getString("ar_seqs");
	ab = am.getAlarmInfo(ar_seqs);
	
	kb = km.getKeywordInfo(ab.getK_seq());
	String xp = kb.getK_xp();
	String yp = kb.getK_yp();
	String k_seq = ab.getK_seq();
	
	ArrayList ypList = new ArrayList();
	ypList = km.getYpList(xp);

	ArrayList zpList = new ArrayList();
	zpList = km.getZpList(xp, yp);
	
	ArrayList xpList = new ArrayList();
	xpList = km.getXpList();
	
	//시스템 멤버 그룹 
	AddressBookDao abDao = new AddressBookDao();
	AddressBookGroupBean abgBean = new AddressBookGroupBean();
	List abgGroupList = new ArrayList();
	abgGroupList = abDao.getAdressBookGroup();
	Iterator it = abgGroupList.iterator();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=SS_URL%>css/base.css" type="text/css">
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script>
	function getYpList(){
		ajax.post("alarmYPList.jsp", "fSend", "yp");
	}
	function getZpList(){
		ajax.post("alarmZPList.jsp", "fSend", "k_seq");
	}

	function alarmSave(){
		
	}

	function alarmUpdate(){
		var f = document.getElementById('fSend');

		//분석방법
		var ar_type = $('input[name=t_ar_type]:checked').val();
		f.ar_type.value = ar_type;
		
		if(ar_type == '1'){
			f.ar_value.value = f.ar_value1.value;
			f.ar_time.value = '';	
		}else if(ar_type == '2'){
			f.ar_value.value = f.ar_value2.value;
			f.ar_time.value = f.t_ar_time.value;
		}else if(ar_type == '3'){
			f.ar_value.value = '';
			f.ar_time.value = '';
		}

		//수신방법
		var ar_sendtype = $('input[name=t_ar_sendtype]:checked').val();
		f.ar_sendtype.value = ar_sendtype;

		f.ar_receiver.value = document.report_selectedReceiver.selectedAbSeq.value;
		if(f.ar_receiver.value.length == 0){alert('수신자를 선택해주세요'); return;}

		f.mode.value = 'update';
		f.action = 'alarmPrc.jsp';
		f.target = 'prc';
		f.submit();
	}


	////////////////////수신자 리스트 //////////////////////
	$(document).ready(pageInit);
	
	function pageInit()
	{
		loadList1();
		loadList2();
	}
	
	function loadList1()
	{	
		ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_receiver.jsp','report_receiver','receiverList');		
	}
	
	function findList1()
	{
		var f = document.report_receiver;
		var f2 = document.report_selectedReceiver;
	
		f.selectedAbSeq.value = f2.selectedAbSeq.value;		
		ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_receiver.jsp','report_receiver','receiverList');			
	}	
	/////////////////////////////////////////////////////	
	///////////////////선택된 수신자 리스트/////////////////						
	function selectedListCheck(ab_seq)
	{
		var f = document.report_selectedReceiver;
		var check = true;
		var list = new Array();
		list = f.selectedAbSeq.value.split(',');
	
		for(var i =0; i<list.length; i++)
		{
			if(list[i]==ab_seq)
			{
				check = false;
				break;
			}
		}
		return check;
	}	
	
	function selectRightMove(ab_seq)
	{
		var f = document.report_selectedReceiver;
	
		if(!selectedListCheck(ab_seq)){alert('이미 선택된 수신자 입니다.');	return;}	
		
		if(f.selectedAbSeq.value!='')
		{
			f.selectedAbSeq.value += ","+ ab_seq;
		}else{
			f.selectedAbSeq.value = ab_seq;
		}
		findList1(); 		
		findList2();
	}
	
	function addReceiver() 
	{
	 	window.open('<%=SS_URL%>riskv3/admin/receiver/receiver_detail.jsp?mode=add','adduser', 'width=800,height=500,scrollbars=no');
	}
	
	
	function editReceiver(ab_seq) 
	{
	 	window.open('<%=SS_URL%>riskv3/admin/receiver/receiver_detail.jsp?mode=edit&abSeq='+ab_seq,'adduser', 'width=800,height=500,scrollbars=no');
	}
	
	function delReceiver(ab_seq) 
	{
		if(window.confirm("삭제하시겠습니까?"))
		{
	 		window.open('<%=SS_URL%>riskv3/admin/receiver/receiver_prc.jsp?mode=del&seqList='+ab_seq,'adduser', 'width=800,height=500,scrollbars=no');
		}else{
			return;
		}
	}
	
	function delAbSeq(ab_seq)
	{
		var f = document.report_selectedReceiver;
		var list = new Array();
		list = f.selectedAbSeq.value.split(',');
	
		f.selectedAbSeq.value = '';
		for(var i =0; i<list.length; i++)
		{
			if(list[i]!=ab_seq)
			{				
				if(f.selectedAbSeq.value!='')
				{
					f.selectedAbSeq.value += ","+ list[i];
				}else{
					f.selectedAbSeq.value = list[i];
				} 
			}
		}
	}
	
	function selectLeftMove(ab_seq)
	{
		var f = document.report_selectedReceiver;
		delAbSeq(ab_seq);
		findList1();		
		findList2();
	}	
	
	function loadList2()
	{	
		ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_selected_receiver.jsp','report_selectedReceiver','selectedList');		
	}
	
	function findList2()
	{		
		ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_selected_receiver.jsp','report_selectedReceiver','selectedList');					
	}				
	//////////////////////////////////////////////////////
</script>
</head>
<body style="margin-left: 15px">
<form name="fSend" id="fSend" method="post">
<input type="hidden" name="mode" id="mode">
<input type="hidden" name="ar_receiver" id="ar_receiver">
<input type="hidden" name="ar_seqs" id="ar_seqs" value="<%=ar_seqs%>">
<input type="hidden" name="ar_type" id="ar_type">
<input type="hidden" name="ar_value" id="ar_value">
<input type="hidden" name="ar_time" id="ar_time">
<input type="hidden" name="ar_sendtype" id="ar_sendtype">
<table style="width:100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
			<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td class="tit_bg" style="height:67px;">
					<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
				<td><img src="../../../images/admin/alarm/tit_icon.gif" /><img src="../../../images/admin/alarm/tit_01.gif" /></td>
				<td align="right">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="navi_home">HOME</td>
						<td class="navi_arrow">관리자</td>
						<td class="navi_arrow2">키워드 알람</td>
					</tr>
				</table>
				</td>
					</tr>
				</table>
				</td>
			</tr>
			</table>
		</td>
	</tr>	
	<tr>
		<td height="10"></td>
	</tr>
	<tr>
		<td class="body_bg_top"><table style="width:820px;" border="0" cellpadding="0" cellspacing="1" bgcolor="CCCCCC">
			<tr bgcolor="FFFFFF">
				<td bgcolor="F0F5F9" width="147" style="font-weight:bold;" align="center">키워드</td>
				<td style="padding : 3px 0px 3px 5px">
					<select name="xp" id="xp" style="width:100px" onchange="getYpList()">
						<option value="">대그룹</option>
<%
if(xpList.size() > 0){
	for(int i = 0; i < xpList.size(); i++){
		kb = (KeywordBean)xpList.get(i);
%>
						<option value="<%=kb.getK_xp()%>" <%if(kb.getK_xp().equals(xp)){out.print("selected");}%>><%=kb.getK_value()%></option>
<%
	}
}
%>
					</select>
					<select name="yp" id="yp" style="width:100px" onchange="getZpList()">
						<option value="">중그룹</option>
<%
if(ypList.size() > 0){
	for(int i = 0; i < ypList.size(); i++){
		kb = (KeywordBean)ypList.get(i);
%>
						<option value="<%=kb.getK_yp()%>" <%if(kb.getK_yp().equals(yp)){out.print("selected");}%>><%=kb.getK_value()%></option>
<%
	}
}
%>
					</select>
					<select name="k_seq" id="k_seq" style="width:100px">
						<option value="">키워드</option>
<%
if(zpList.size() > 0){
	for(int i = 0; i < zpList.size(); i++){
		kb = (KeywordBean)zpList.get(i);
%>
						<option value="<%=kb.getK_seq()%>" <%if(kb.getK_seq().equals(k_seq)){out.print("selected");}%>><%=kb.getK_value()%></option>
<%
	}
}
%>
					</select>
				</td>
			</tr>
			<tr bgcolor="FFFFFF">
				<td bgcolor="F0F5F9" width="147" style="font-weight:bold" align="center">분석방법</td>
				<td style="padding : 3px 0px 3px 5px">
					<span style="width:200px;height:25px;">
						<input type="radio" name="t_ar_type" id="t_ar_type" value="1" <%if(ab.getAr_type().equals("1")){out.print("checked");}%>>전체 평균 대비
						<select name="ar_value1" id="ar_value1">
							<option value="200" <%if(ab.getAr_type().equals("1")){if(ab.getAr_value().equals("200")){out.print("selected");}}%>>200%</option>
							<option value="300" <%if(ab.getAr_type().equals("1")){if(ab.getAr_value().equals("300")){out.print("selected");}}%>>300%</option>
							<option value="400" <%if(ab.getAr_type().equals("1")){if(ab.getAr_value().equals("400")){out.print("selected");}}%>>400%</option>
							<option value="500" <%if(ab.getAr_type().equals("1")){if(ab.getAr_value().equals("500")){out.print("selected");}}%>>500%</option>
						</select>
					</span><span style="height:25px">* 현재의 수량을 하루의 평균과 비교 합니다.</span>
					<br>
					<span style="width:200px;height:25px;">
						<input type="radio" name="t_ar_type" id="t_ar_type" value="2" <%if(ab.getAr_type().equals("2")){out.print("checked");}%>>최근
						<select name="t_ar_time" id="t_ar_time">
							<option value="30" <%if(ab.getAr_type().equals("2")){if(ab.getAr_time().equals("30")){out.print("selected");}}%>>30</option>
							<option value="40" <%if(ab.getAr_type().equals("2")){if(ab.getAr_time().equals("40")){out.print("selected");}}%>>40</option>
							<option value="50" <%if(ab.getAr_type().equals("2")){if(ab.getAr_time().equals("50")){out.print("selected");}}%>>50</option>
							<option value="60" <%if(ab.getAr_type().equals("2")){if(ab.getAr_time().equals("60")){out.print("selected");}}%>>60</option>
							<option value="70" <%if(ab.getAr_type().equals("2")){if(ab.getAr_time().equals("70")){out.print("selected");}}%>>70</option>
							<option value="80" <%if(ab.getAr_type().equals("2")){if(ab.getAr_time().equals("80")){out.print("selected");}}%>>80</option>
							<option value="90" <%if(ab.getAr_type().equals("2")){if(ab.getAr_time().equals("90")){out.print("selected");}}%>>90</option>
							<option value="100" <%if(ab.getAr_type().equals("2")){if(ab.getAr_time().equals("100")){out.print("selected");}}%>>100</option>
							<option value="110" <%if(ab.getAr_type().equals("2")){if(ab.getAr_time().equals("110")){out.print("selected");}}%>>110</option>
							<option value="120" <%if(ab.getAr_type().equals("2")){if(ab.getAr_time().equals("120")){out.print("selected");}}%>>120</option>
						</select>
						분간
						<select name="ar_value2" id="ar_value2">
							<option value="200" <%if(ab.getAr_type().equals("2")){if(ab.getAr_value().equals("200")){out.print("selected");}}%>>200%</option>
							<option value="300" <%if(ab.getAr_type().equals("2")){if(ab.getAr_value().equals("300")){out.print("selected");}}%>>300%</option>
							<option value="400" <%if(ab.getAr_type().equals("2")){if(ab.getAr_value().equals("400")){out.print("selected");}}%>>400%</option>
							<option value="500" <%if(ab.getAr_type().equals("2")){if(ab.getAr_value().equals("500")){out.print("selected");}}%>>500%</option>
						</select>
					</span><span style="height:25px">* 현재의 수량을 최근 시간의 평균과 비교 합니다.</span>
					<br>
					<span style="width:200px;height:25px;">
						<input type="radio" name="t_ar_type" id="t_ar_type" value="3" <%if(ab.getAr_type().equals("3")){out.print("checked");}%>>앞 수량 구간 가중치
					</span><span style="height:25px">* 바로 앞 시간대의 수량을 비교합니다.</span>
				</td>
			</tr>
			<tr bgcolor="FFFFFF">
				<td bgcolor="F0F5F9" width="147" style="font-weight:bold" align="center">기준수량</td>
				<td style="padding : 3px 0px 3px 5px">
					<div><input type="text" class="txtbox" style="width:40px" name="ar_avg_cnt" value="<%=ab.getAr_avg_cnt()%>"> <span></span></div>
				</td>
			</tr>
			<tr bgcolor="FFFFFF">
				<td bgcolor="F0F5F9" width="147" style="font-weight:bold" align="center">수신방법</td>
				<td style="padding : 3px 0px 3px 5px">
					<div><label><input type="radio" name="t_ar_sendtype" id="t_ar_sendtype" value="1" onfocus="this.blur()" <%if(ab.getAr_sendtype().equals("1")){out.print("checked");}%>> mail</label></div>
					<div><label><input type="radio" name="t_ar_sendtype" id="t_ar_sendtype" value="2" onfocus="this.blur()" <%if(ab.getAr_sendtype().equals("2")){out.print("checked");}%>> sms</label></div>
				</td>
			</tr>
		</table></td>
	</tr>
</table>
<iframe name="prc" id="prc" style="display:none"></iframe>
</form>
<table>
	<tr>
		<td style="padding: 5px 0px 5px 20px">
		<table style="width:100%;height:350px;" border="0" cellpadding="0" cellspacing="0" id="receiverTool">
			<tr>
				<td colspan="3" style="padding:3px 0px 3px 0px"><span class="sub_tit">메일 발송 설정</span></td>
			</tr>
			<tr>
				<td style="width:340px;">
				<form name="report_receiver" id="report_receiver">
				<input name="selectedAbSeq" id="selectedAbSeq" type="hidden" value="<%=ab.getAr_receiver()%>">
				<table id="pop_mail_group" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th>메일 수신자 그룹</th>
					</tr>
					<tr>
						<td style="padding:10px;" valign="top">
						<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="pop_mail_group_top">그룹선택</td>
								<td style="text-align:right;" class="pop_mail_group_top2">
									<select name="ag_seq" onchange="findList1();">
										<option value="" selected>전체 수신자 그룹</option>
<%
	while(it.hasNext()){
		abgBean = new AddressBookGroupBean();
		abgBean = (AddressBookGroupBean)it.next();
%>
										<option value="<%=abgBean.getAg_seq()%>"><%=abgBean.getAg_name()%></option>
<%
	}
%>
									</select>
								</td>
							</tr>
							<tr>
								<td colspan="2"><div id="receiverList"></div></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</form>
				</td>
				<td style="text-align:center;width:90px">&nbsp;</td>
				<td style="width:340px; vertical-align: top;">
				<form name="report_selectedReceiver" id="report_selectedReceiver">
				<input name="selectedAbSeq" id="selectedAbSeq" type="hidden" value="<%=ab.getAr_receiver()%>">
				<table id="pop_mail_group" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th>메일 대상자 그룹</th>
					</tr>
					<tr>
						<td style="padding:10px;" valign="top">
						<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td><div id="selectedList"></div></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</form>
				</td>
			</tr>
		</table>

		</td>
	</tr>
	<tr align="center">
		<td style="padding: 5px 0px 5px 20px"><img src="../../../images/admin/alarm/save_btn.gif" onclick="alarmUpdate()" style="cursor:pointer;"></td>
	</tr>
</table>
</body>
</html>