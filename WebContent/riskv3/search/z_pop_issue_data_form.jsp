<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList
				,risk.util.ParseRequest
				,risk.util.DateUtil
				,risk.util.StringUtil
				,risk.search.MetaBean
				,risk.search.MetaMgr
				,risk.issue.IssueCodeMgr
				,risk.issue.IssueCodeBean
				,risk.issue.IssueBean	
				,risk.issue.IssueMgr
				,java.util.List
				,java.util.Iterator
				,risk.search.solr.*
				,risk.sms.AddressBookDao
				,risk.sms.AddressBookBean
				,risk.sms.AddressBookGroupBean
				,risk.issue.IssueDataBean	
				,risk.admin.info.*
				"%>
<%@include file="../inc/sessioncheck.jsp"%>
<%
		ParseRequest pr = new ParseRequest(request);		
		DateUtil	 du = new DateUtil();
		StringUtil		su = new StringUtil();
		pr.printParams();
		
		MetaMgr  metaMgr = new MetaMgr();
		IssueCodeMgr 	icm = new IssueCodeMgr();		
		IssueMgr issueMgr = new IssueMgr();
		AddressBookDao abDao = new AddressBookDao();
		
		ArrayList arrIcBean = new ArrayList();	 //분류코드 어레이
		List arrAddGroupBean = new ArrayList();  //수신자그룹 어레이
				
		AddressBookGroupBean addGroupBean = new AddressBookGroupBean();	
		IssueDataBean idBean = new IssueDataBean();
		IssueCodeBean icBean = new IssueCodeBean();
		MetaBean metaBean = new MetaBean();			
				
		String selected = null;
		String mode = pr.getString("mode");
		String nowPage = pr.getString("nowPage");
		String subMode = pr.getString("subMode","");
		String md_seq = pr.getString("md_seq");
		
		String child = pr.getString("child","");
		String md_seqs = pr.getString("md_seqs");
		
		int ic_seq = 0;
		
		String checkdNo = "";
		
		//분류체계코드 (0:분류항목 포함, 1:분류항목 제외)
		icm.init(0);				
		
		//모드에 따른 분기
		if(mode.equals("insert")){
			//메타 정보
			if(subMode.equals("solr")){
				metaBean.setMd_seq(pr.getString("md_seq"));
				metaBean.setS_seq(pr.getString("s_seq"));
				metaBean.setSg_seq("");
				metaBean.setMd_site_name(pr.getString("md_site_name"));
				metaBean.setMd_site_menu("SOLR");
				metaBean.setMd_date(du.getDate(pr.getString("md_date"), "yyyy-MM-dd HH:mm:ss"));
				metaBean.setMd_title(su.dbString(pr.getString("md_title")));
				metaBean.setMd_url(su.dbString(pr.getString("md_url")));
				metaBean.setL_alpha("KOR");
			}else if(subMode.equals("TOP")){
				metaBean = metaMgr.getTopData(md_seq);	
				ic_seq = icm.getSiteMapCode(metaBean.getSg_seq());  //사이트 이슈 코드 맵핑 정보 코드 시퀀스로 가져옴
			 }else{
				metaBean = metaMgr.getMetaData(md_seq);	
				ic_seq = icm.getSiteMapCode(metaBean.getSg_seq());  //사이트 이슈 코드 맵핑 정보 코드 시퀀스로 가져옴
			 }
			
		}else if(mode.equals("update")){
			//이슈 정보
			idBean = issueMgr.getIssueDataBean(md_seq);
			
		}
		
		//이슈데이터 등록 관련
	   	IssueMgr isMgr = new IssueMgr();
	   	IssueBean isBean = new IssueBean();	   	   	
	   	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
		
%>

<%@page import="risk.admin.keyword.KeywordBean"%>
<%@page import="risk.search.keywordInfo"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>POSCO</title>
<link rel="stylesheet" type="text/css" href="../../css/base.css" />
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/common.js"  type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script language="javascript">
<!--

	//관심정보 등록폼으로 이동
	function goIssueDataForm()
	{
		var f = document.fSend;
		f.target='';
		f.action='pop_issue_data_form.jsp';
	    f.submit();
	}
	
	//이슈관리폼으로 이동		
	function goIssueManager()
	{
		var f = document.fSend;
		f.target='';
		f.action='../issue/issue_manager.jsp';
	    f.submit();
	}
	
	//MULTICHECK 전체 체크 기능
	function chkAll(obj, chk){
		if(obj){
			if(obj.length){
				for(var i=0; i<obj.length; i++){
					obj[i].checked = chk;
				}
			}
		}
	} 

	//MULTICHECK 체크 기능
	function chkBox(obj, obj2, chk){
		if(chk == false){
			obj2.checked = false;
		}else{
			var chkCnt = 0;
			if(obj){
				if(obj.length){
					for(var i=0; i<obj.length; i++){
						if(obj[i].checked==true){
							chkCnt ++;
						}
					}
					if(chkCnt==obj.length){
						obj2.checked = true;
					}
				}
			}
		}
		
	} 

	//타입코드 셋팅
	function settingTypeCode()
	{
		var form = document.fSend;

		form.typeCode.value = '';

		
		for(var i=0;i<form.typeCode4.length;i++){
			if(form.typeCode4[i].checked)
			{
				form.typeCode.value += form.typeCode.value=='' ? form.typeCode4[i].value : '@'+form.typeCode4[i].value ;
			}	
		}
		for(var i=0;i<form.typeCode5.length;i++){
			if(form.typeCode5[i].checked)
			{
				form.typeCode.value += form.typeCode.value=='' ? form.typeCode5[i].value : '@'+form.typeCode5[i].value ;
			}	
		}
		
		form.typeCode.value += form.typeCode.value=='' ? form.typeCode7.value : '@'+form.typeCode7.value ;
				
		for(var i=0;i<form.typeCode8.length;i++){
			if(form.typeCode8[i].checked)
			{
				form.typeCode.value += form.typeCode.value=='' ? form.typeCode8[i].value : '@'+form.typeCode8[i].value ;
			}	
		}
		for(var i=0;i<form.typeCode6.length;i++){
			if(form.typeCode6[i].checked)
			{
				form.typeCode.value += form.typeCode.value=='' ? form.typeCode6[i].value : '@'+form.typeCode6[i].value ;
			}	
		}
		for(var i=0;i<form.typeCode9.length;i++){
			if(form.typeCode9[i].checked)
			{
				form.typeCode.value += form.typeCode.value=='' ? form.typeCode9[i].value : '@'+form.typeCode9[i].value ;				
			}			
		}
		for(var i=0;i<form.typeCode10.length;i++){
			if(form.typeCode10[i].checked)
			{
				form.typeCode.value += form.typeCode.value=='' ? form.typeCode10[i].value : '@'+form.typeCode10[i].value ;
			}	
		}
		for(var i=0;i<form.typeCode11.length;i++){
			if(form.typeCode11[i].checked)
			{
				form.typeCode.value += form.typeCode.value=='' ? form.typeCode11[i].value : '@'+form.typeCode11[i].value ;
			}	
		}


		//추가분
		for(var i=0;i<form.typeCode12.length;i++){
			if(form.typeCode12[i].checked)
			{
				form.typeCode.value += form.typeCode.value=='' ? form.typeCode12[i].value : '@'+form.typeCode12[i].value ;
			}	
		}
		for(var i=0;i<form.typeCode13.length;i++){
			if(form.typeCode13[i].checked)
			{
				form.typeCode.value += form.typeCode.value=='' ? form.typeCode13[i].value : '@'+form.typeCode13[i].value ;
			}	
		}
		for(var i=0;i<form.typeCode14.length;i++){
			if(form.typeCode14[i].checked)
			{
				form.typeCode.value += form.typeCode.value=='' ? form.typeCode14[i].value : '@'+form.typeCode14[i].value ;
			}	
		}
		for(var i=0;i<form.typeCode15.length;i++){
			if(form.typeCode15[i].checked)
			{
				form.typeCode.value += form.typeCode.value=='' ? form.typeCode15[i].value : '@'+form.typeCode15[i].value ;
			}	
		}
		
		for(var i=0;i<form.typeCode16.length;i++){
			if(form.typeCode16[i].checked)
			{
				form.typeCode.value += form.typeCode.value=='' ? form.typeCode16[i].value : '@'+form.typeCode16[i].value ;
			}	
		}

		for(var i=0;i<form.typeCode17.length;i++){
			if(form.typeCode17[i].checked)
			{
				form.typeCode.value += form.typeCode.value=='' ? form.typeCode17[i].value : '@'+form.typeCode17[i].value ;
			}	
		}

		
				
		return 	form.typeCode.value;	
	}

	//코드 필수 선택 체크
	function chkData(str1, str2){
		result = 0;
		var tmp = str1.split('@');
		for(var i=0; i<tmp.length; i++){
			var tmp2 = tmp[i].split(',');
			if(tmp2[0]==str2){
				result = 1;
				break;
			}
		}
		if(result==1){
			return true;
		}else{
			 return false;
		}
	}

	//이슈 타이틀 변경
    function changeIssueTitle()
	{    	
		ajax.post('selectbox_issue_title.jsp','fSend','td_it_title');
	}

	var regChk = false;


	function save_issue(mode)
	{

		var f = document.fSend;
		
		var typeCode = settingTypeCode();
		
		var chk = 0;
		var chk1 = 0;


		var obj = document.getElementById('fnMsg');
		obj.innerHTML = '';				
		

		//분류체계 메시지 주기~
		var type = ['4','7','8','6','9','10','13','14','15'];
		for(var i =0; i <type.length; i++){

			//멀티일때 출처는 제외
			if(type[i] != '6' || mode != 'multi'){
				if(!chkData(typeCode, type[i])){
					//는 빼버리기~
					obj.innerHTML = document.getElementById('typeTitle' + type[i]).innerHTML.replace(/\*/gi,"") + '를(을) 선택해 주세요.';
					return;
				}		
			}
		}

		if(f.ra_report[0].checked == false && f.ra_report[1].checked  == false){
			obj.innerHTML = '보고서 포함여부를 선택해 주세요.';
			return;
		}

		if(f.f_news.checked){
			f.f_news.value = "Y";
		}else{
			f.f_news.value = "N";
		} 
		

		obj.innerHTML = '';

						

		//alert(typeCode); return;
		
		//if(f.i_seq[f.i_seq.selectedIndex].value=='0') {alert('정보그룹을 선택해주세요.'); return;}
			
			
			
		if(!regChk){

			document.getElementById("sending").style.display = '';
			
			//regChk = true;
			f.typeCode.value = typeCode;	
			f.mode.value = mode;
			f.target='if_samelist';
			f.action='issue_data_prc.jsp';
	        f.submit();
		}else{
			alert('등록중입니다.. 잠시만 기다려주세요.');
		}
 	}
 	
	////////////////////////////////////////AJAX 수신자 설정///////////////////////////////////////
	
	////////////////////수신자 리스트 //////////////////////
	//$(document).ready(pageInit);

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
	
	function changeIssueIcon(docid){
		var obj1 = opener.document.getElementById("issueChkImg"+docid);
		obj1.src='images/search/yellow_star.gif';
		obj1.title='이슈로 등록된 정보 입니다.';
	    obj1.onclick='';
		var obj2 = opener.document.getElementById("issueChkText"+docid);
		obj2.href = "javascript:";
		close();	
	}

	function loding(){
		 var imgObj = document.getElementById("sending");
		 imgObj.style.left = (document.body.clientWidth / 2) - (imgObj.width / 2);
		 imgObj.style.top = (document.body.clientHeight / 2) - (imgObj.height / 2);
		 imgObj.style.display = 'none'; 
	}
	
	function comboCheck(obj){

		var f = document.fSend;


		focusObj = eval('f.focus_'+ obj.name);

		if(focusObj.value == obj.value){
			obj.checked = false;
			focusObj.value = '';
		}else{
			focusObj.value = obj.value;
		}
	}

	//이슈 타이틀 변경
    function getYp()
	{    	    	
		ajax.post('selectbox_kyp.jsp','fSend','getSelect');
	}



  	

	/*2013-1-7 ADD*/
	
		
    function getTypeCode7(target)
	{    	    		
		ajax.post('selectbox_type7.jsp?targetValue=' + target,'fSend','span_typeCode7');
	}
	
    function getType4ClickEvn(){
		var f = document.fSend;
		var chkNum = "0";
		
		if(f.typeCode4.length){
			for(var i =0; i < f.typeCode4.length; i++){
				if(f.typeCode4[i].checked){
					
					chkNum = f.typeCode4[i].value.split(",")[1];
					break;				
				}	
			} 
		}else{
			if(f.typeCode4.checked){
				chkNum = f.typeCode4.value;
			}
		}

		//초기화
		document.getElementById("tr_typeCode5").style.display = 'none';
		TypeClear('typeCode5');

		//var typeCode7 = document.getElementById("typeCode7");

		getTypeCode7("4," + chkNum);
		
		if(chkNum == '2'){
			document.getElementById("tr_typeCode5").style.display = '';		
		}
				
	}


    /*
    function getType5ClickEvn(){
		var f = document.fSend;
		var chkNum = "0";
		
		if(f.typeCode5.length){
			for(var i =0; i < f.typeCode5.length; i++){
				if(f.typeCode5[i].checked){
					chkNum = f.typeCode5[i].value.split(",")[1];
					break;				
				}	
			} 
		}else{
			if(f.typeCode5.checked){
				chkNum = f.typeCode5.value;
			}
		}

		getTypeCode7("5," + chkNum);
				
	}
*/
	

	function TypeClear(name){
		var f = document.fSend;
		
		var focusObj = eval('f.'+ name);

		if(focusObj){
			if(focusObj.length){
				for(var i = 0; i < focusObj.length; i++){
					focusObj[i].checked = false;
				} 
			}else{
				focusObj.checked = false;
			}
		}
	}


	String.prototype.trim = function(){
		return this.replace(/^\s\s*/,'').replace(/\s\s*$/,'');
    }

	
	
//-->
</script>
</head>
<body onload="loding();">
<iframe id="if_samelist" name="if_samelist" width="100%" height="0" src="about:blank"></iframe>
<form name="fSend" id="fSend" action="issue_data_prc.jsp" method="post" onsubmit="return false;">
<input name="mode" id="mode" type="hidden" value=""><!-- 모드 -->
<input name="subMode" id="subMode" type="hidden" value=<%=subMode%>>
<input type="hidden" name="nowPage" value="<%=nowPage %>">


<%if(mode.equals("insert")){ %>
<input name="md_seq" id="md_seq" type="hidden" value="<%=metaBean.getMd_seq()%>"><!-- 기사번호 -->
<input name="md_pseq" id="md_pseq" type="hidden" value="<%=metaBean.getMd_pseq()%>"><!-- 모 기사번호 -->
<input name="s_seq" id="s_seq" type="hidden" value="<%=metaBean.getS_seq()%>"><!-- 사이트번호 -->
<input name="sg_seq" id="sg_seq" type="hidden" value="<%=metaBean.getSg_seq()%>"><!-- 사이트 그룹 -->
<input name="md_date" id="md_date" type="hidden" value="<%=metaBean.getMd_date()%>"><!-- 수십 시간-->
<input name="md_site_menu" id="md_site_menu" type="hidden" value="<%=metaBean.getMd_site_menu()%>"><!-- 사이트 메뉴 -->
<input name="md_same_ct" id="md_same_ct" type="hidden" value="<%=metaBean.getMd_same_count()%>"><!-- 유사개수 -->
<input name="typeCode" id="typeCode" type="hidden" value=""><!-- 타입코드 -->
<input name="mailreceiver" id="mailreceiver" type="hidden" value=""><!--선택된 메일 수신자 -->
<input name="selItseq" id="selItseq" type="hidden" value="">
<input name="l_alpha" id="selItseq" type="hidden" value=<%=metaBean.getL_alpha()%>><!--언어코드 -->

<input name="kyp"; type="hidden">

<%}else if(mode.equals("update")){%>
<input name="id_seq" id="id_seq" type="hidden" value="<%=idBean.getId_seq()%>"><!-- 기사번호 -->
<input name="md_seq" id="md_seq" type="hidden" value="<%=idBean.getMd_seq()%>"><!-- 기사번호 -->
<input name="s_seq" id="s_seq" type="hidden" value="<%=idBean.getS_seq()%>"><!-- 사이트번호 -->
<input name="sg_seq" id="sg_seq" type="hidden" value="<%=idBean.getSg_seq()%>"><!-- 사이트 그룹 -->
<input name="md_date" id="md_date" type="hidden" value="<%=idBean.getMd_date()%>"><!-- 수십 시간-->
<input name="md_site_menu" id="md_site_menu" type="hidden" value="<%=idBean.getMd_site_menu()%>"><!-- 사이트 메뉴 -->
<input name="id_mailyn" id="id_mailyn" type="hidden" value="<%=idBean.getId_mailyn()%>"><!-- 사이트 메뉴 -->
<input name="typeCode" id="typeCode" type="hidden" value=""><!-- 타입코드 -->
<input name="mailreceiver" id="mailreceiver" type="hidden" value=""><!--선택된 메일 수신자 -->
<input name="selItseq" id="selItseq" type="hidden" value="<%=idBean.getIt_seq()%>"><!--선택된 it_seq -->
<%} %>
<img  id="sending" src="../../images/search/saving.gif" style="position: absolute;  " >
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td id="pop_head">
			<p>이슈등록</p>
			<span><a href="javascript:close();"><img src="../../images/search/pop_tit_close.gif"></span>
		</div>
		</td>
	</tr>
	<tr>
		<td>
		<!-- 게시판 시작 -->
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
			<tr style="display: none;">
				<td style="height:30px;"><span class="sub_tit">기본정보</span></td>
			</tr>
			<tr>
				<td>
				<table id="board_write" border="0" cellspacing="0" style="display: none;">
					<tr>
						<th><span class="board_write_tit">제목</span></th>
						<td colspan="3"><input style="width:94%;" type="text" class="textbox2" name="id_title" value="<%if(mode.equals("insert")){out.print(su.ChangeString(metaBean.getMd_title()));}else{out.print(su.ChangeString(idBean.getId_title()));}%>"></td>
					</tr>
					<tr>
						<th><span class="board_write_tit">URL</span></th>
						<td colspan="3"><input style="width:94%;" type="text" class="textbox2" name="id_url" value="<%if(mode.equals("insert")){out.print(metaBean.getMd_url());}else{out.print(idBean.getId_url());}%>"></td>
					</tr>
					<tr>
						<th><span class="board_write_tit">사이트이름</span></th>
						<td><input style="width:150px;" type="text" class="textbox2" name="md_site_name" value="<%if(mode.equals("insert")){out.print(metaBean.getMd_site_name());}else{out.print(idBean.getMd_site_name());}%>"></td>
						<th><span class="board_write_tit">정보분류시간</span></th>
						<td><input style="width:140px;" type="text" class="textbox2" name="id_regdate" value="<%if(mode.equals("insert")){out.print(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));}else{out.print(idBean.getFormatId_regdate("yyyy-MM-dd HH:mm:ss"));}%>" readonly="readonly"></td>
					</tr>
					<tr>
						<th><span class="board_write_tit">정보종류</span></th>
						<td colspan="3">
							<select name="md_type" id="md_type">
								<option value="1" <%if(mode.equals("insert")){if(metaBean.getMd_type().equals("1")){out.print("selected");}}else{if(idBean.getMd_type().equals("1")){out.print("selected");}}%>>언론</option>
								<option value="2" <%if(mode.equals("insert")){if(Integer.parseInt(metaBean.getMd_type()) > 1){out.print("selected");}}else{if(idBean.getMd_type().equals("2")){out.print("selected");}}%>>개인</option>
							</select>
						</td>
					</tr>
					<tr>
						<th><span class="board_write_tit">주요내용</span></th>
						<td colspan="3"><textarea style="width:94%;height:60px;" name="id_content"><%if(mode.equals("insert")){out.print(su.cutKey(su.ChangeString(metaBean.getMd_content()),"",300,""));}else{out.print(su.ChangeString(idBean.getId_content()));}%></textarea></td>
					</tr>
					<!-- 키워드 안씀  -->
					<!--
					<tr>
						<th><span class="board_write_tit">키워드 분류</span></th>
						<td colspan="3" align="left" >
						<%
						/*
							KeywordBean kbean = null;
							ArrayList arrXp = metaMgr.getK_XPData(md_seq, subMode);
							*/
						%>
							<select name="keyTypeXp" onchange="getYp();" style="width:120px;">
							<option value="0">선택하세요</option>
						<%	
						/*
							String kxp = "";  
							String selectChk = "";
							if(arrXp.size() == 1){
								selectChk = "selected";
							}
						
							for(int i =0; i < arrXp.size(); i++){
								kbean = (KeywordBean)arrXp.get(i);
								out.print("<option value='"+kbean.getKGxp() + "' "+selectChk+" >" + kbean.getKGvalue() + "</option> \n");
								if(i==0){
									kxp = kbean.getKGxp();								
								}
							}
							*/
						%>
							</select>&nbsp;&nbsp;->&nbsp; 
							<span id="getSelect" style="width:150px;"> 
							<select name="keyTypeYp" style="width:120px;">
							<option value="0">선택하세요</option>
						<% 
						/*
							if(arrXp.size() == 1){
								ArrayList arrYp = metaMgr.getK_YPData(md_seq, kxp, subMode);
								
								selectChk = "";
								if(arrYp.size() == 1){
									selectChk = "selected";
								}
								
								for(int i =0; i < arrYp.size(); i++){
									kbean = (KeywordBean)arrYp.get(i);
									out.print("<option value='"+kbean.getKGyp() + "' "+selectChk+">" + kbean.getKGvalue() + "</option> \n");
								}
							}
						*/
						%>	
							</select>
							</span>
						</td>
					</tr>
					-->
					
					
				</table>
				</td>
			</tr>
			<tr>
				<td style="padding-top:15px;height:45px;"><span class="sub_tit">정보분류 항목</span></td>
			</tr>
			<tr>
				<td>
				<table id="board_write" border="0" cellspacing="0">
<!-- 회사 구분  -->				
					<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(4);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 			
						<th><span class="board_write_tit"><%=icBean.getIc_name()%>*</span></th>
						<td colspan="3">
<%
    checkdNo = ""; 
	if(mode.equals("insert")){
		selected = "4,1";
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),4);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			
			checkdNo = icBean.getIc_type()+","+icBean.getIc_code();
			
			out.print("<input type='radio' name='typeCode4' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this);getType4ClickEvn();' checked >" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode4' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this);getType4ClickEvn();' >" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
	out.print("<input type='hidden' name='focus_typeCode4' value='"+checkdNo+"'>");
%>	
						</td>
					</tr>


<!-- 사업 구분  -->				
					<tr id="tr_typeCode5" style="display: none;">
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(5);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 			
						<th><span class="board_write_tit"><%=icBean.getIc_name()%></span></th>
						<td colspan="3">
<%
    checkdNo = ""; 
	if(mode.equals("insert")){
		selected = "";
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),5);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			
			checkdNo = icBean.getIc_type()+","+icBean.getIc_code();
			
			out.print("<input type='radio' name='typeCode5' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this); getTypeCode7(\""+icBean.getIc_type()+","+icBean.getIc_code()+"\");' checked >" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode5' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this); getTypeCode7(\""+icBean.getIc_type()+","+icBean.getIc_code()+"\");' >" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
	out.print("<input type='hidden' name='focus_typeCode5' value='"+checkdNo+"'>");
%>	
						</td>
					</tr>					
<!-- 회사 -->					
					<tr>
<%
arrIcBean = new ArrayList();
arrIcBean = icm.GetType(7);
icBean = new IssueCodeBean();
icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 		
						<th><span class="board_write_tit"><%=icBean.getIc_name()%>*</span></th>
						<td colspan="3">
						  <span id="span_typeCode7">
							<select name="typeCode7">
<%	
	if(mode.equals("insert")){
		selected = "";
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),7);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			out.print("<option value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' selected>" + icBean.getIc_name() + "</option>");
		}else{
			out.print("<option value='"+icBean.getIc_type()+","+icBean.getIc_code()+"'>" + icBean.getIc_name() + "</option>");
		}
	}
%>
							</select>
							</span>
						</td>
					</tr>
<!-- 이슈구분 -->					
					<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(8);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 		
						<th><span class="board_write_tit"><%=icBean.getIc_name()%>*</span></th>
						<td colspan="3">
<%
	checkdNo = ""; 
	if(mode.equals("insert")){
		selected = "8,3";
		/*
		if(ic_seq > 0){
			selected = icm.getTypeCodeValByKey(arrIcBean , ic_seq);	    
		}
		*/
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),8);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			checkdNo = icBean.getIc_type()+","+icBean.getIc_code();
			out.print("<input type='radio' name='typeCode8' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' checked>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode8' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)'>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
	out.print("<input type='hidden' name='focus_typeCode8' value='"+checkdNo+"'>");
%>	
						</td>
					</tr>
<!-- 출처 -->					
					<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(6);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%> 
						<th><span class="board_write_tit"><%=icBean.getIc_name()%>*</span></th>
						<td colspan="3">
<%	
	checkdNo = ""; 
	if(mode.equals("insert")){
		selected = "";
		if(ic_seq > 0){
			selected = icm.getTypeCodeValByKey(arrIcBean , ic_seq);	    
		}
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),6);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			checkdNo = icBean.getIc_type()+","+icBean.getIc_code();
			out.print("<input type='radio' name='typeCode6' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' checked>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode6' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' >" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
	out.print("<input type='hidden' name='focus_typeCode6' value='"+checkdNo+"'>");
%>
						</td>
					</tr>
<!-- 성향 -->					
					<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(9);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 			
						<th><span class="board_write_tit"><%=icBean.getIc_name()%>*</span></th>
						<td colspan="3">
<%	
	checkdNo = ""; 
	if(mode.equals("insert")){
		selected = "9,3";
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),9);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			checkdNo = icBean.getIc_type()+","+icBean.getIc_code();
			out.print("<input type='radio' name='typeCode9' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' checked>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode9' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' >" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
	out.print("<input type='hidden' name='focus_typeCode9' value='"+checkdNo+"'>");
%>
						</td>
					</tr>
<!-- 중요도 -->					
					<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(10);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 			
						<th><span class="board_write_tit"><%=icBean.getIc_name()%>*</span></th>
						<td colspan="3">
<%	
	checkdNo = ""; 
	if(mode.equals("insert")){
		selected = "";
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),10);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			checkdNo = icBean.getIc_type()+","+icBean.getIc_code();
			out.print("<input type='radio' name='typeCode10' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' checked>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode10' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' >" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
	out.print("<input type='hidden' name='focus_typeCode10' value='"+checkdNo+"'>");
%>
						</td>
					</tr>
					
					
					
<!-- 영향도 -->					
					<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(12);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 			
						<th><span class="board_write_tit"><%=icBean.getIc_name()%></span></th>
						<td colspan="3">
<%	
	checkdNo = ""; 
	if(mode.equals("insert")){
		selected = "";
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),12);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			checkdNo = icBean.getIc_type()+","+icBean.getIc_code();
			out.print("<input type='radio' name='typeCode12' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' checked>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode12' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' >" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
	out.print("<input type='hidden' name='focus_typeCode12' value='"+checkdNo+"'>");
%>
						</td>						
					</tr>
<!-- 정보유형 -->					
					<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(13);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 			
						<th><span class="board_write_tit"><%=icBean.getIc_name()%>*</span></th>
						<td colspan="3">
<%	
	checkdNo = ""; 
	if(mode.equals("insert")){
		selected = "";
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),10);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			checkdNo = icBean.getIc_type()+","+icBean.getIc_code();
			out.print("<input type='radio' name='typeCode13' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' checked>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode13' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' >" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
	out.print("<input type='hidden' name='focus_typeCode13' value='"+checkdNo+"'>");
%>
						</td>
					</tr>
<!-- 정보순서-->
					<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(11);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 			
						<th><span class="board_write_tit"><%=icBean.getIc_name()%></span></th>
						<td colspan="3">
<%	
	checkdNo = ""; 
	if(mode.equals("insert")){
		selected = "";
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),11);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			checkdNo = icBean.getIc_type()+","+icBean.getIc_code();
			out.print("<input type='radio' name='typeCode11' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' checked>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode11' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' >" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
	out.print("<input type='hidden' name='focus_typeCode11' value='"+checkdNo+"'>");
%>
						</td>
					</tr>					
<!-- 내용구분-->					
					<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(14);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 			
						<th><span class="board_write_tit"><%=icBean.getIc_name()%>*</span></th>
						<td colspan="3">
<%	
	checkdNo = ""; 
	if(mode.equals("insert")){
		selected = "";
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),10);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			checkdNo = icBean.getIc_type()+","+icBean.getIc_code();
			out.print("<input type='radio' name='typeCode14' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' checked>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode14' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' >" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
	out.print("<input type='hidden' name='focus_typeCode14' value='"+checkdNo+"'>");
%>
						</td>
					</tr>		
<!-- 정보 속성-->					
					<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(15);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 			
						<th><span class="board_write_tit"><%=icBean.getIc_name()%>*</span></th>
						<td colspan="3">
<%	
	checkdNo = ""; 
	if(mode.equals("insert")){
		selected = "";
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),10);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			checkdNo = icBean.getIc_type()+","+icBean.getIc_code();
			out.print("<input type='radio' name='typeCode15' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' checked>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode15' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' >" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
	out.print("<input type='hidden' name='focus_typeCode15' value='"+checkdNo+"'>");
%>
						</td>
					</tr>								

<!-- 이슈관리-->					
					<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(16);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 			
						<th><span class="board_write_tit"><%=icBean.getIc_name()%>*</span></th>
						<td colspan="3">
<%	
	checkdNo = ""; 
	if(mode.equals("insert")){
		selected = "";
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),10);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			checkdNo = icBean.getIc_type()+","+icBean.getIc_code();
			out.print("<input type='radio' name='typeCode16' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' checked>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode16' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' >" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
	out.print("<input type='hidden' name='focus_typeCode16' value='"+checkdNo+"'>");
%>
						</td>
					</tr>
					
<!-- VOC관리-->					
					<tr>
<%
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(17);
	icBean = new IssueCodeBean();
	icBean = (IssueCodeBean) arrIcBean.get(0);
%>                                 			
						<th><span class="board_write_tit"><%=icBean.getIc_name()%>*</span></th>
						<td colspan="3">
<%	
	checkdNo = ""; 
	if(mode.equals("insert")){
		selected = "";
	}else{
		selected = icm.getTypeCodeVal(idBean.getArrCodeList(),10);
	}
	for (int i = 1; i < arrIcBean.size(); i++) {
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals(icBean.getIc_type()+","+icBean.getIc_code())){
			checkdNo = icBean.getIc_type()+","+icBean.getIc_code();
			out.print("<input type='radio' name='typeCode17' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' checked>" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}else{
			out.print("<input type='radio' name='typeCode17' value='"+icBean.getIc_type()+","+icBean.getIc_code()+"' onclick='comboCheck(this)' >" + icBean.getIc_name() + "&nbsp;&nbsp;&nbsp;");
		}
		if(i==6 && i!=0)out.print("<br>");
	}
	out.print("<input type='hidden' name='focus_typeCode17' value='"+checkdNo+"'>");
%>
						</td>
					</tr>							

					<tr>
                               			
						<th><span class="board_write_tit">보고서*</span></th>
						<td colspan="3">
						<input type="radio" name="ra_report" value="Y" onclick="comboCheck(this)" checked="checked">포함 &nbsp;&nbsp;&nbsp;
						<input type="radio" name="ra_report" value="N" onclick="comboCheck(this)" >미포함 &nbsp;&nbsp;&nbsp;
						<input type='hidden' name='focus_ra_report' value='Y'>
						</td>
					</tr>
					<tr>
						<th><span class="board_write_tit">기사 정보</span></th>
						<td colspan="3">
						<input type="checkbox" name="f_news">최초기사 
						</td>
					</tr>
					<tr>
						<th><span class="board_write_tit">개인미디어 정보</span></th>
						<td colspan="3">
						<input type="text" name="media_info" style="width: 400px" value=""> 
						</td>
					</tr>
					<%--
					<tr>
						<th><span class="board_write_tit">정보그룹</span></th>
						<td colspan="3">
							<select name="i_seq" style="width:400px">
								<option value="0">전체</option>
<%
	for(int i=0; i<igArr.size();i++){
		igBean = new InfoGroupBean();
        igBean = (InfoGroupBean)igArr.get(i);
		selected = "";         												
		if(mode.equals("insert")){
		}else{
			if(idBean.getI_seq().equals(igBean.getI_seq())){selected="selected";}
		}
%>
								<option value="<%=igBean.getI_seq()%>" <%=selected%>><%=igBean.getI_nm()%></option>
<%
	}
%>
							</select>
						</td>
					</tr>
					--%>
				</table>
				</td>
			</tr>
		</table>
		<!-- 게시판 끝 -->
		</td>
	</tr>
	<tr>
		<td style="text-align:center;"><font id="fnMsg" style="color: #5364D2; font-weight: bold; font-size: 13px"; ></font></td>
	</tr>
	<tr>
		<td style="text-align:center;"><img src="../../images/search/btn_save_2.gif" onclick="save_issue('<%=mode%>');" style="cursor:pointer;"/>&nbsp;<img src="../../images/search/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"/></td>
	</tr>
	<!---------------->
</table>
</form>
</body>
</html>