<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="java.util.ArrayList
				,risk.util.ParseRequest
				,risk.util.StringUtil
				,risk.issue.IssueReportMgr
				,risk.issue.IssueReportBean							
				,risk.util.PageIndex
				,risk.admin.log.LogBean
				,risk.sms.*
				,java.util.List
				,risk.sms.AddressBookDao
               	,risk.sms.AddressBookGroupBean
               	,risk.admin.membergroup.membergroupBean              
               	,java.util.Iterator
				"%>  				 
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	ArrayList arrIrBean = new ArrayList();
	IssueReportMgr irMgr = new IssueReportMgr();
	IssueReportBean irBean = new IssueReportBean();
	LogBean logBean = new LogBean();
	
	String ir_type = pr.getString("ir_type");
	irBean = irMgr.getReportBean(pr.getString("ir_seq"));// 리포트 정보
	
	
	//시스템 멤버 그룹 
	AddressBookDao abDao = new AddressBookDao();
	AddressBookGroupBean abgBean = new AddressBookGroupBean();
	List abgGroupList = new ArrayList();
	abgGroupList = abDao.getAdressBookGroup();
	Iterator it = abgGroupList.iterator();	
		
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>RIS-K</title>
<link rel="stylesheet" type="text/css" href="../../css/base.css" />
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script language="javascript">
<!--
	////////////////////////////////////////AJAX 수신자 설정///////////////////////////////////////

	////////////////////수신자 리스트 //////////////////////
	$(document).ready(pageInit);
	
	function pageInit()
	{
		loadList1();
		loadList2();
	}
	
	function loadList1()
	{	
		var f = document.report_receiver;
			
		if("1" == (f.receiver_mode.value)){
			ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_receiver.jsp','report_receiver','receiverList');
		}else{
			ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_receiver_group.jsp','report_receiver','receiverList');
		}
		
	}
	
	function getReceiverList(){
		var f = document.report_receiver;
		var selectOp = document.getElementById("receiver_type");
		selectOp = selectOp.options[selectOp.selectedIndex].value;
		f.receiver_mode.value = selectOp;
		
		loadList1();
		loadList2();
	}

	function findList1()
	{
		var f = document.report_receiver;
		var f2 = document.report_selectedReceiver;
		
		f.selectedAbSeq.value = f2.selectedAbSeq.value;	
		f.selectedAgSeq.value = f2.selectedAgSeq.value;	
		if("1" == (f.receiver_mode.value)){
			ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_receiver.jsp','report_receiver','receiverList'); 
		}else{
			ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_receiver_group.jsp','report_receiver','receiverList');
		}
		
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
	
	
	function selectedListCheckGroup(ag_seq)
	{
		var f = document.report_selectedReceiver;
		var check = true;
		var list = new Array();
		list = f.selectedAgSeq.value.split(',');
	
		for(var i =0; i<list.length; i++)
		{
			if(list[i]==ag_seq)
			{
				check = false;
				break;
			}
		}
		return check;
	}
	
	function selectRightGroupMove(ag_seq)
	{
		var f = document.report_selectedReceiver;
	
		if(!selectedListCheckGroup(ag_seq)){alert('이미 선택된 수신자 그룹 입니다.');	return;}	
		
		if(f.selectedAgSeq.value!='')
		{
			f.selectedAgSeq.value += ","+ ag_seq;
		}else{
			f.selectedAgSeq.value = ag_seq;
		}
		findList1(); 		
		findList2();
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
	
	function delAgSeq(ag_seq)
	{
		var f = document.report_selectedReceiver;
		var list = new Array();
		list = f.selectedAgSeq.value.split(',');
	
		f.selectedAgSeq.value = '';
		for(var i =0; i<list.length; i++)
		{
			if(list[i]!=ag_seq)
			{				
				if(f.selectedAgSeq.value!='')
				{
					f.selectedAgSeq.value += ","+ list[i];
				}else{
					f.selectedAgSeq.value = list[i];
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
	
	function selectLeftMoveGroup(ag_seq)
	{
		var f = document.report_selectedReceiver;
		delAgSeq(ag_seq);
		findList1();		
		findList2();
	}	

	
	function loadList2()
	{	
		ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_selected_receiver_multi.jsp','report_selectedReceiver','selectedList');
		
		<%--
		var f = document.report_receiver;
		 if("1" == (f.receiver_mode.value)){
			ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_selected_receiver.jsp','report_selectedReceiver','selectedList');
		}else{
			ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_selected_receiver_group.jsp','report_selectedReceiver','selectedList');
		} --%>
		
	}
	
	function findList2()
	{	
		ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_selected_receiver_multi.jsp','report_selectedReceiver','selectedList');
		<%-- var f = document.report_receiver;
		if("1" == (f.receiver_mode.value)){
			ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_selected_receiver.jsp','report_selectedReceiver','selectedList');
		}else{
			ajax.post('<%=SS_URL%>riskv3/admin/receiver/behind_selected_receiver_group.jsp','report_selectedReceiver','selectedList');
		} --%>
	}				
	//////////////////////////////////////////////////////

	//리스트 목록
	function goList(ir_type){
 		var f = document.fSend;
 		f.ir_type.value = ir_type;
 		f.action="issue_report_list.jsp";
		f.target='';
		f.submit(); 	
	}
	
	//저장된 보고서 보기 페이지를 띄운다
 	function showReport(ir_seq){
	 	var f = document.fSend;		
		f.ir_seq.value = ir_seq;
		popup.openByPost('fSend','pop_report.jsp',800,945,false,true,false,'goReportView');		
 	}

 	//보고서 수정
 	function showReportEditor(ir_seq, reportType){
 	 	
	 	var f = document.fSend;
	 	f.mode.value = 'update';		
		f.ir_seq.value = ir_seq;	

		window.open('about:blank', 'PopUp', 'width=820,height=1000,scrollbars=yes,status=yes');		
		//f.mode.value = 'view';
		f.action = 'pop_report_editform.jsp?reportType=' + reportType;
		f.target = 'PopUp';
		f.submit();	
 	}

 
 	
 	//보고서 재발송
	function sendReport(mode){
 		var f = document.fSend;
		if(document.getElementById('receiverTool').style.display=='none'){
			document.getElementById('receiverTool').style.display ='';
		}else{
			f.mailreceiver.value = document.report_selectedReceiver.selectedAbSeq.value;
			f.mailreceiverGroup.value = document.report_selectedReceiver.selectedAgSeq.value;
			
			/* if(f.mailreceiver.value.length==0){alert('수신자를 선택해주세요'); return;} */
			if(f.mailreceiver.value.length==0 && f.mailreceiverGroup.value.length==0){alert('수신자를 선택해주세요'); return;}
			
			var smsChk = document.getElementsByName("smsChk");

			var streamSms ="";
			if(smsChk){
				if(smsChk.length){
					for(var i = 0; i < smsChk.length; i++){
						if(smsChk[i].checked){
							if(streamSms == ""){
								streamSms = smsChk[i].value; 
							}else{
								streamSms += "," + smsChk[i].value;
							}
						}
					}  
				}else{
					if(smsChk.checked){
						streamSms = smsChk.value; 
					}
				}
			}
			
			f.smsreceiver.value = streamSms;

			//센딩이미지 처리
			
			var imgObj = document.getElementById("sending");
			var sendBtn = document.getElementById("sendBtn");
			imgObj.style.left = (document.body.clientWidth / 2) - (imgObj.width / 2);			
			rect = sendBtn.getBoundingClientRect();
			imgObj.style.top = rect.top + document.body.scrollTop - 50;			  
			//imgObj.style.left = rect.left + 190;
			imgObj.style.left = rect.left - 80;
			imgObj.style.display = '';

			
			/* var reportForm = document.report_receiver;
			if("1" == reportForm.receiver_mode.value){				
				f.mode.value = 'mail';
			}else{
				f.mode.value = 'mail_group';	
			}			
			 */
			f.mode.value = mode;
			f.target = 'processFrm';
			f.action = 'issue_report_prc.jsp';
			f.submit();		
			
		}			
	}
 	
 	function all_Select(){

 		var f = document.report_selectedReceiver;
		var ab_seqs = document.getElementById('receiverSeq').value;
		if(ab_seqs!=''){
		alert(document.getElementById('receiverSeq').value);
			
			if(f.selectedAbSeq.value!='')
			{
				f.selectedAbSeq.value += ","+ ab_seqs;
			}else{
				f.selectedAbSeq.value = ab_seqs;
			}
	
			findList1(); 		
			findList2();		
		}
	
 	}
 	
 	function all_Select_group(){

 		var f = document.report_selectedReceiver;
		var ag_seqs = document.getElementById('receiverSeqGroup').value;
		
		if(ag_seqs!=''){
			
			if(f.selectedAgSeq.value!='')
			{
				f.selectedAgSeq.value += ","+ ag_seqs;
			}else{
				f.selectedAgSeq.value = ag_seqs;
			}
	
			findList1(); 		
			findList2();		
		}
 	}
 	
 	
 	function all_Select_multi(){
 		var f = document.report_selectedReceiver;
		
		
		if(null != document.getElementById('receiverSeqGroup')){
			var ag_seqs = document.getElementById('receiverSeqGroup').value;
			if(ag_seqs!=''){
				if(f.selectedAgSeq.value!='')
				{
					f.selectedAgSeq.value += ","+ ag_seqs;
				}else{
					f.selectedAgSeq.value = ag_seqs;
				}
		
				findList1(); 		
				findList2();	
			}
		}
	
		
		
		if(null != document.getElementById('receiverSeq')){
			var ab_seqs = document.getElementById('receiverSeq').value;
			if(ab_seqs!=''){
				if(f.selectedAbSeq.value!='')
				{
					f.selectedAbSeq.value += ","+ ab_seqs;
				}else{
					f.selectedAbSeq.value = ab_seqs;
				}
		
				findList1(); 		
				findList2();		
			}
		}
 	}
 	
 	function all_DeSelect(){
 		
 		var f = document.report_selectedReceiver;
 		f.selectedAbSeq.value='';
 		f.selectedAgSeq.value='';
 		
		findList1(); 		
		findList2();	
 	}

//-->
</script>
</head>
<body style="margin-left: 15px">
<img  id="sending" src="../../images/report/sending.gif" style="position: absolute; display: none;" >
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="body_bg_top" valign="top">
					<form name="fSend" id="fSend" action="" method="post">
					<iframe id="processFrm" name ="processFrm" width="0" height="0" ></iframe>
					<input type="hidden" name="mode">
					<input type="hidden" name="delseq">
					<input type="hidden" name="ir_seq" value="<%=pr.getString("ir_seq")%>">
					<input type="hidden" name="ir_type" value="<%=ir_type %>">
					<input type="hidden" name="mailreceiver" value="">
					<input type="hidden" name="mailreceiverGroup" value="">   
					<input type="hidden" name="smsreceiver" value="">   
					<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
						<!-- 타이틀 시작 -->
						<tr>
							<td class="tit_bg" style="height:67px;padding-top:20px;">
							<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td><img src="../../images/report/tit_icon.gif" />
			<%
				if(ir_type.equals("U")){out.print("<img src=\"../../images/report/tit_0302.gif\" />");}
				else if(ir_type.equals("D1")){out.print("<img src=\"../../images/report/tit_0303.gif\" />");}
				else if(ir_type.equals("D2")){out.print("<img src=\"../../images/report/tit_0303.gif\" />");}
				else if(ir_type.equals("D3")){out.print("<img src=\"../../images/report/tit_0306.gif\" />");}
				else if(ir_type.equals("D4")){out.print("<img src=\"../../images/report/tit_0307.gif\" />");}
				else if(ir_type.equals("W")){out.print("<img src=\"../../images/report/tit_0304.gif\" />");}
				else if(ir_type.equals("IM")){out.print("<img src=\"../../images/report/tit_0812_01.gif\" />");}
				else if(ir_type.equals("IS")){out.print("<img src=\"../../images/report/tit_report_issue_t.gif\" />");}
				else if(ir_type.equals("OIS")){out.print("<img src=\"../../images/report/tit_report_online_issue_t.gif\" />");}
				else if(ir_type.equals("VT")){out.print("<img src=\"../../images/report/tit_report_voc_trend.gif\" />");}
				else if(ir_type.equals("FVT")){out.print("<img src=\"../../images/report/tit_report_food_trend.gif\" />");}
			%>
									</td>
									<td align="right">
									<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td class="navi_home">HOME</td>
											<td class="navi_arrow">보고서관리</td>
											<td class="navi_arrow2">
			<%
				if(ir_type.equals("U")){out.print("긴급보고서");}
				else if(ir_type.equals("D1")){out.print("일일보고서");}
				else if(ir_type.equals("D2")){out.print("일일보고서");}
				else if(ir_type.equals("D3")){out.print("현대캐피탈 일일보고서");}
				else if(ir_type.equals("D4")){out.print("현대커머셜 일일보고서");}
				else if(ir_type.equals("W")){out.print("주간보고서");}
				else if(ir_type.equals("IM")){out.print("일일보고서(언론)");}
				else if(ir_type.equals("IS")){out.print("Issue Report");}
				else if(ir_type.equals("OIS")){out.print("Online Issue Report");}
				else if(ir_type.equals("VT")){out.print("Voc Trend Report");}
				else if(ir_type.equals("FVT")){out.print("식품업계 Issue Trend Report");}
			%>
											</td>
										</tr>
									</table>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="15"></td>
						</tr>
						<!-- 타이틀 끝 -->
						<tr>
							<td style="height:30px;"><span class="sub_tit">보고서 상세정보</span></td>
						</tr>
						<tr>
							<td>
							<table id="board_write" border="0" cellspacing="0" style="table-layout:fixed;">
							<col width="20%"><col width="80%">
								<tr>
									<th><span class="board_write_tit">제     목</span></th>
									<td><span style="width:500px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" title="<%=irBean.getIr_title()%>"><%=irBean.getIr_title()%></span> 
									<img src="../../images/report/btn_report.gif" style="cursor:pointer" onclick="showReport(<%=irBean.getIr_seq()%>);" hspace="2" />
									<%-- <img src="../../images/report/btn_report_edit.gif" style="cursor:pointer" onclick="showReportEditor(<%=irBean.getIr_seq()%>,1);" /> --%>
									<img src="../../images/report/btn_report_edit.gif" style="cursor:pointer" onclick="showReportEditor(<%=irBean.getIr_seq()%>,2);" />
									</td>
								</tr>
								<tr>
									<th><span class="board_write_tit">작성일</span></th>
									<td><%=irBean.getFormatIr_regdate("MM/dd HH:mm")%></td>
								</tr>
							</table>
							</td>
						</tr>
						<!-- 게시판 시작 -->
						<tr>
							<td style="padding-top:20px;height:50px;"><span class="sub_tit">메일수신자</span></td>
						</tr>
						<tr>
							<td>       
							<table id="board_01" border="0" cellpadding="0" cellspacing="0">
							<col width=*><!-- <col width="17%"><col width="40%"> --><col width="20%"><col width="20%">
								<tr>
									<th>메일 수신자</th>
									<th>부서</th>
									<th>메일주소</th>
									<th>발송시간</th>
									<th>수신확인여부</th>
								</tr>
			<% 
				logBean = irMgr.getIssueReportLogBeanGroup(pr.getString("ir_seq")); // 리포트 로그 정보
				ArrayList arrABBean;
				arrABBean =	logBean.getArrArrReceiver();
				//AddressBookBean ABBean = null;
				AddressBookGroupBean ABBean = null;
				String mailReceiver = "";
				String receipt = "";
				if (arrABBean!=null) {
					for(int i=0; i < arrABBean.size(); i++){
						/* ABBean  = new AddressBookBean();
						ABBean = (AddressBookBean)arrABBean.get(i);	
						mailReceiver += mailReceiver.equals("") ? ABBean.getMab_seq() : ","+ABBean.getMab_seq(); */
						
						ABBean  = new AddressBookGroupBean();
						ABBean = (AddressBookGroupBean)arrABBean.get(i);	
						mailReceiver += mailReceiver.equals("") ? ABBean.getAg_seq() : ","+ABBean.getAg_seq();
						
						receipt = "미확인";
						/* if(ABBean.getL_count() != null){
							if(Integer.parseInt(ABBean.getL_count()) > 0){
								receipt = "확인";
							}
						} */

						if(ABBean.getAg_L_count() != null){
							if(Integer.parseInt(ABBean.getAg_L_count()) > 0){
								receipt = "확인";
							}
						}
						
			%>
								<tr>
									<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;cursor:pointer;"><%=ABBean.getAg_name()%>(그룹)</td>
									<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;cursor:pointer">-</td>
									<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;cursor:pointer"><span class="mail">-</span></td>
									<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;cursor:pointer"><%=ABBean.getFormatAg_send_date("yy/MM/dd HH:mm:ss")%></td>
									<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;cursor:pointer"><%=receipt%></td>
								</tr>
			<%
					}
				}
			%>
			
			
			<% 
				logBean = irMgr.getIssueReportLogBean(pr.getString("ir_seq")); // 리포트 로그 정보
				arrABBean =	logBean.getArrArrReceiver();
				AddressBookBean ABBean2 = null;
				
				mailReceiver = "";
				receipt = "";
				if (arrABBean!=null) {
					for(int i=0; i < arrABBean.size(); i++){
						ABBean2  = new AddressBookBean();
						ABBean2 = (AddressBookBean)arrABBean.get(i);	
						mailReceiver += mailReceiver.equals("") ? ABBean2.getMab_seq() : ","+ABBean2.getMab_seq(); 
						
						
						receipt = "미확인";
						if(ABBean2.getL_count() != null){
							if(Integer.parseInt(ABBean2.getL_count()) > 0){
								receipt = "확인";
							}
						} 

					
						
			%>
								<tr>
									<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;cursor:pointer;"><%=ABBean2.getMab_name()%></td>
									<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;cursor:pointer"><%=ABBean2.getMab_dept()%></td>
									<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;cursor:pointer"><%=ABBean2.getMab_mail()%></td> 
									<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;cursor:pointer"><%=ABBean2.getFormatMab_send_date("yy/MM/dd HH:mm:ss")%></td>
									<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;cursor:pointer"><%=receipt%></td>
								</tr>
			<%
					}
				}
			%>
				
							</table>
							</td>
						</tr>
						<!-- 게시판 끝 -->
						<tr>
							<%-- <td style="text-align:center;height:40px;"><img src="../../images/report/btn_list.gif" onclick="goList('<%=ir_type%>');" style="cursor:pointer"/><img id="sendBtn" src="../../images/report/btn_reportsend.gif" style="cursor:pointer;" onclick="sendReport('mail');"/></td> --%>
							<td style="text-align:center;height:40px;">
							<img src="../../images/report/btn_list.gif" onclick="goList('<%=ir_type%>');" style="cursor:pointer"/>
							<img id="sendBtn" src="../../images/report/btn_reportsend.gif" style="cursor:pointer;" onclick="sendReport('mail_multi');"/>
							</td>
						</tr>
					</table>
					</form> 
					</td>
				</tr>
				<tr>
					<td style="padding-left:20px">
					<!-- 2020.11. 이전 개인별 -->
					<%-- <table style="width:820px;height:350px;display:none;" border="0" cellpadding="0" cellspacing="0" id="receiverTool">
						<tr>
							<td colspan="3" style="padding:3px 0px 3px 0px"><span class="sub_tit">메일 발송 설정</span></td>
						</tr>
						<tr>
							<td style="width:340px;" valign="top">
							<form name="report_receiver" id="report_receiver">
							<input name="selectedAbSeq" id="selectedAbSeq" type="hidden" value="">
							<table id="pop_mail_group" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<th>메일 수신자 그룹</th>
								</tr>
								<tr>
									<td style="padding:5px;" valign="top">
									<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="20%" class="pop_mail_group_top">그룹선택 </td>
											<td width="30%" align="right" class="pop_mail_group_top2">
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
											<td width="50%" align="right"  class="pop_mail_group_top2" >
												<input id="selectAll" type="button" style="cursor:pointer;" onclick="all_Select()" value="전체 선택">
											</td>
										</tr>
										<tr>
											<td colspan="3"><div id="receiverList"></div></td>
										</tr>
									</table>
									</td>
								</tr>
							</table>
							</form>
							</td>
							<td style="text-align:center; width: 50px">&nbsp;</td>
							<td style="width:340px; vertical-align: top;">
							<form name="report_selectedReceiver" id="report_selectedReceiver">
							<input name="selectedAbSeq" id="selectedAbSeq" type="hidden" value="">
							<table id="pop_mail_group" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<th>메일 대상자 그룹</th>
								</tr>
								<tr>
									<td style="padding:4px;" valign="top">
									<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="100%" align="right"  class="pop_mail_group_top2" >
												<input id="deSelectAll" type="button" style="cursor:pointer;" onclick="all_DeSelect();" value="전체 선택 해제">
											</td>
										</tr>									
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
					</table> --%>
					<!-- 2020.11. 이후 그룹별 --><!-- 2020.11.23이후  그룹별/개인별 선택  - 고객사요청 -->
					<table style="width:820px;height:350px;display:none;" border="0" cellpadding="0" cellspacing="0" id="receiverTool">
						<tr>
							<td colspan="3" style="padding:3px 0px 3px 0px"><span class="sub_tit">메일 발송 설정</span>
								<select id="receiver_type" style="min-width:150px;" class="ui_wid_100p" onchange="getReceiverList();">
			 						<option value="0">그룹</option>
             						<option value="1">개인</option>
								</select><label for="receiver_type"></label>
							</td>
						</tr>
						<tr id="receiver_list">
							<td style="width:340px;" valign="top">
							<form name="report_receiver" id="report_receiver">
							<input type="hidden" name="receiver_mode" value="0">
							<input name="selectedAbSeq" id="selectedAbSeq" type="hidden" value="">
							<input name="selectedAgSeq" id="selectedAgSeq" type="hidden" value="">
							<table id="pop_mail_group" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<th>메일 수신자 (개인/그룹)</th>
								</tr>
								<tr>
									<td style="padding:5px;" valign="top">
									<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="25%" class="pop_mail_group_top">그룹선택 </td>
											<td width="30%" align="right" class="pop_mail_group_top2">
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
											<td width="50%" align="right"  class="pop_mail_group_top2" >
												<input id="selectAll" type="button" style="cursor:pointer;" onclick="all_Select_multi()" value="전체 선택">
											</td>
										</tr>
										<tr>
											<td colspan="3"><div id="receiverList" style="width:100%; height:300px; overflow:auto"></div></td>
										</tr>
									</table>
									</td>
								</tr>
							</table>
							</form>
							</td>
							<td style="text-align:center; width: 50px">&nbsp;</td>
							<td style="width:340px; vertical-align: top;">
							<form name="report_selectedReceiver" id="report_selectedReceiver">
							<input name="selectedAbSeq" id="selectedAbSeq" type="hidden" value="">
							<input name="selectedAgSeq" id="selectedAgSeq" type="hidden" value="">
							<table id="pop_mail_group" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<th>메일 대상자 (개인/그룹)</th>
								</tr>
								<tr>
									<td style="padding:4px;" valign="top">
									<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="100%" align="right"  class="pop_mail_group_top2" >
												<input id="deSelectAll" type="button" style="cursor:pointer;" onclick="all_DeSelect();" value="전체 선택 해제">
											</td>
										</tr>									
										<tr>
											<td><div id="selectedList" style="width:100%; height:300px; overflow:auto"></div></td>
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
			</table>
			</td>
			<!-- 퀵 -->
			<td style="background:#eaeaea;" >
			<table class="quick_bg" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="quick_bg2" valign="top"><img src="../../images/common/quick_bg_top.gif" /></td>
				</tr>
			</table>
		</td>
		<!-- 퀵 -->
	</tr>
</table>
</body>
</html>