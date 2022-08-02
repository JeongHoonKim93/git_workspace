<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
                 risk.util.ParseRequest,
                 java.net.*,
                 risk.admin.backup.BackupListBean,
                 risk.admin.backup.BackupListMgr" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	String searchKey = pr.getString("searchKey");			//검색어
	String order = pr.getString("order");	//정렬 순서
	
	String bl_seq = pr.getString("bl_seq");
	String bl_tbName = pr.getString("bl_tbName");
	String bl_op = pr.getString("bl_op");
	String bl_useYn = pr.getString("bl_useYn");
	String bl_del_useYn = pr.getString("bl_del_useYn");
	String bl_day_term = pr.getString("bl_day_term");
	String ins_field_name = pr.getString("ins_field_name");
	String del_date_field_name = pr.getString("del_date_field_name");
	String del_date_field_type = pr.getString("del_date_field_type");
	String mode = pr.getString("mode");
	//String checkIds = pr.getString("checkIds");

	
	BackupListMgr bm = new BackupListMgr();
	BackupListBean backupBean = new BackupListBean();

	BackupListBean[] blBean = null;
	blBean = bm.getAllTableList(order);	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RIS-K</title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<link rel="stylesheet" href="<%=SS_URL%>/css/common.css" type="text/css">
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script type="text/javascript" src="../../../axisj/jquery/jquery.min.js"></script>
<script language="javascript">


	$(document).ready(loadList);
	
	//첨 로드시~
	function loadList()
	{		
		ajax.post('backup_behind_list.jsp?subCodeName=issueCode','managerForm','backup_behindList');
		clearSearch();
	}
	
	//검색시 보여지는 리스트
	function loadList_search() {
		ajax.post('backup_search_list.jsp?subCodeName=issueCode','managerForm','backup_behindList');
	}
	
	//단어 검색시
	function Search()
	{
		var f = document.managerForm;
		//AJAX 사용시 한글처리
		//f.searchKey.value = encodeURI(f.searchWord.value);
		//앞뒤 공백 제거
		f.searchWord.value = f.searchWord.value.replace(/^\s*/,'');
		f.searchWord.value = f.searchWord.value.replace(/\s*$/,'');	
		
		var value_searchType = document.getElementsByName("searchType");
		for (var i = 0; i < value_searchType.length; i++) {
			if(value_searchType[i].checked == true) {
				f.searchType.value = value_searchType[i].value;
			}
		}
		if(f.searchWord.value == '') {	
			loadList();
		} else {
			loadList_search();
		}
	}
	
	//페이지 다시 로드할때 검색창 비우기
	function clearSearch() {
		var clearData = document.getElementsByClassName('textbox');
		for(var i = 0; i < clearData.length; i++) {
			clearData[i].value = '';
		}
		$("#type option:eq(0)").prop("selected",true);
		var f = document.managerForm;		
		f.order.value = '';		
	}
	
	//관리팝업창
	function popupEdit(bl_seq,mode, bl_tbName, bl_op, bl_useYn, bl_del_useYn, bl_day_term, ins_field_name, del_date_field_name, del_date_field_type)
	{				
		var f = document.managerForm;		
		f.bl_seq.value = bl_seq;
		f.mode.value = mode;
		f.bl_tbName.value = bl_tbName;
		f.bl_op.value = bl_op;
		f.bl_useYn.value = bl_useYn;
		f.bl_del_useYn.value = bl_del_useYn;
		f.bl_day_term.value = bl_day_term;
		f.ins_field_name.value = ins_field_name;
		f.del_date_field_name.value = del_date_field_name;
		f.del_date_field_type.value = del_date_field_type;
		
		if(mode=='update')
		{
			popup.openByPost('managerForm','backup_add.jsp',400,303,false,false,false,'trendPop');
		} 
		
	}
	
	function show_me(i) {
		//console.log('show me');
		div_show.innerHTML = eval('div'+i).innerHTML;
    	div_show.style.top = event.y + document.body.scrollTop + 15;
    	div_show.style.display='';
	}
	
	function close_me(i) {
		div_show.style.display='none';
	}
	/*
	//백업리스트에서 삭제
	function deleteBackupList(bl_seq,bl_tbName)
	{
		var f = document.managerForm;

		f.bl_seq.value = bl_seq;	
		f.bl_tbName.value = bl_tbName;	
		f.mode.value = 'deleteList';
		f.target='processFrm';
		f.action='backup_prc.jsp';
		f.submit();		
	}
	*/
	
 	//정렬시
	function excuteOrder(order)
	{
		var f = document.managerForm;		
		f.order.value = order;		
		Search(); 
	} 
	
 	//체크 박스 전체 선택시
	function selectAll(result)
	{			
		var f = document.managerForm;
		if( f.checkId ) {
			if(f.checkId.length){
				for( i=0; i< f.checkId.length; i++ )
		   		{
		   			 f.checkId[i].checked = result;
		   		}
			}else{
				f.checkId.checked = result;
			}	 
		}
		f.checkAll.checked = result;
	}

/* 	//체크된  ID
	function checkId()
	{
		var f = document.managerForm;
		var checkList = "";
		if( f.checkId ) {
			if(f.checkId.length){				
				for( i=0; i< f.checkId.length; i++ )
		   		{
			   		if(f.checkId[i].checked==true)
			   		{				   		
				   		f.checkIds.value == '' ? f.checkIds.value = f.checkId[i].value : f.checkIds.value += ','+ f.checkId[i].value;
			   		}
		   		}
			}else{
				if(f.checkId.checked==true)
		   		{	
					f.checkIds.value = f.checkId.value; 
		   		}
			}	 
		}
		//alert(f.checkIds.value);
		console.log(f.checkIds.value + "<<value");
	} */
	
	function checkList() {
		var test = '';
		$('input:checkbox[name=checkId]:checked').each(function() {
			if(test == '') {
				test = $(this).val();
			} else {
				test += "," + $(this).val();
			}
		});
		return test;
	}
	
	function multiEdit() {
		var f = document.managerForm;
		var chkList = checkList().split(",");

		if(chkList == '' ) {
			alert('일괄수정할 테이블을 선택해주세요.');	
		} else {
			var tmpseq_list = '';
			var tbName_list = '';
			for (var i = 0; i < chkList.length; i++) {
				if(tmpseq_list == '') {
					tmpseq_list = chkList[i].split("/")[0];
				} else {
					tmpseq_list += "," + chkList[i].split("/")[0];
				}
				
				if(tbName_list == '') {
					tbName_list = chkList[i].split("/")[1];
				} else {
					tbName_list += "," + chkList[i].split("/")[1];
				}
			}
			f.bl_seq.value = tmpseq_list;
			f.bl_tbName.value = tbName_list; 
			
			//window.open("backup_add_multi.jsp","multiEdit","width=400, height=303,  left=600, top=200");
			popup.openByPost('managerForm','backup_add_multi.jsp',400,303,false,false,false,'trendPop');
		}
		
	}

/* 	//단어 삭제
	function deleteLists()
	{
		var f = document.managerForm;

		checkId();		
		if(f.checkIds.value==''){alert('선택된 리스트가 없습니다.'); return;}		
		f.mode.value = 'delete';
		f.target='processFrm';
		f.action='backup_prc.jsp';
		f.submit();		
	} 
	 */
</script>
</head>
<body style="margin-left: 15px">
<form name="managerForm" id="managerForm" method="post" onsubmit="return false;">
<input type="hidden" name="mode" id="mode">
<input type="hidden" name="bl_seq" id="bl_seq">
<input type="hidden" name="searchKey" id="searchKey">
<input type="hidden" name="bl_tbName" id="bl_tbName">
<input type="hidden" name="bl_op" id="bl_op">
<input type="hidden" name="bl_useYn" id="bl_useYn">
<input type="hidden" name="bl_del_useYn" id="bl_del_useYn">
<input type="hidden" name="bl_day_term" id="bl_day_term">
<input type="hidden" name="ins_field_name" id="ins_field_name">
<input type="hidden" name="del_date_field_name" id="del_date_field_name">
<input type="hidden" name="del_date_field_type" id="del_date_field_type">
<input type="hidden" name="order" id="order">

<div id=div_show style="font-size: 12px; padding-right: 5px; display: none; padding-left: 5px; left: 100px; width:500px; height:auto; padding-bottom: 5px; padding-top: 5px; position: absolute; background-color: #ffffcc; border: #ff6600 2px solid;"></div>

<!-- <input type="hidden" name="checkIds" id ="checkIds"> -->
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/aekeyword/tit_icon.gif" /><img src="../../../images/admin/aekeyword/tit_1215.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">백업데이터관리</td>
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

			<!-- 검색 시작 -->
			<tr>
				<td class="search_box">
				<table id="search_box" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th>검색 단어</th>
						<td style="vertical-align:middle">
							<input style="width:415px;vertical-align:middle" class="textbox" type="text" name="searchWord" 
								   onkeypress="Javascript:if (event.keyCode == 13) { Search(); }" style="vertical-align:middle;" 
								   placeholder="검색할 테이블명을 입력하세요.">
							
							<select name="searchType" id="type">
								<option value="total" >전체</option>
								<option value="update" >백업 사용</option>
								<option value="insert" >백업 미사용</option>
							</select>	
							&nbsp;&nbsp;
							<!-- <input type="radio" name="searchType" value="total" style="vertical-align:-3px; text-align:center;">전체 -->
							<!-- <input type="radio" name="searchType" value="insert" style="vertical-align:-3px; text-align:center;">추가 -->
							<!-- <input type="radio" name="searchType" value="update" style="vertical-align:-3px; text-align:center;">수정 &nbsp;&nbsp; -->
							<img src="../../../images/admin/aekeyword/btn_search.gif" style="vertical-align:-7px;cursor:pointer" onclick="Search();"/>
							<img src="../../../images/admin/aekeyword/btn_reset.gif" style="vertical-align:-7px;cursor:pointer" onclick="loadList();"/>
							&nbsp;&nbsp;
							<!-- 일괄수정 -->
							<img src="../../../images/admin/aekeyword/btn_multiUpdate.gif" style="vertical-align:-7px;cursor:pointer" onclick="multiEdit();"/> 
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 검색 끝 -->
			<tr>
				<td style="height:10px;">
				<!-- <table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/aekeyword/btn_allselect.gif" style="cursor:pointer" onclick="selectAll(!document.managerForm.checkAll.checked);"/>&nbsp;</td>
						<td><img src="../../../images/admin/aekeyword/btn_add2.gif" style="cursor:pointer" onclick="popupEdit('','insert','','','','','','','');"/>&nbsp;</td>
						<td><img src="../../../images/admin/aekeyword/btn_del.gif" style="cursor:pointer" onclick="deleteLists();"/>&nbsp;</td>
					</tr>
				</table>		 -->		
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td id="backup_behindList"></td>
			</tr>
			<!-- 게시판 끝 -->
			<!-- 하단 버튼 시작 -->
			<tr>
				<td style="height:50px;">
				<%-- <table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/aekeyword/btn_allselect.gif" style="cursor:pointer" onclick="selectAll(!document.managerForm.checkAll.checked);"/>&nbsp;</td>
						<td><img src="../../../images/admin/aekeyword/btn_add2.gif" style="cursor:pointer" onclick="popupEdit('','insert','','','','','','','');"/>&nbsp;</td>
						<td><img src="../../../images/admin/aekeyword/btn_del.gif" style="cursor:pointer" onclick="deleteLists();"/>&nbsp;</td>
					</tr>
				</table>	 --%>			
				</td>
			</tr>
			<!-- 하단 버튼 끝 -->
		</table>
		</td>
	</tr>
</table>
</form>
<iframe name="processFrm" height="0" border="0" style="display: none;"></iframe>
</body>
</html>