<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.admin.hashtag.HashtagBean,
                 risk.admin.hashtag.HashtagMgr,
                 risk.util.PageIndex,
                 risk.util.ParseRequest,
                 java.net.*"
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	String searchKey = pr.getString("searchKey");			//검색어
	String searchWord = pr.getString("searchWord");			//검색어
	String order = pr.getString("order", "HC_NAME ASC");	//정렬 순서
	String checkIds = pr.getString("checkIds");
	String activeVal = pr.getString("activeVal");
	String mode = pr.getString("mode", "");
	
	HashtagMgr hMgr = new HashtagMgr();
	String str_word = "";
	int PAGE_SIZE = 10;		//페이지당 리스트 수
	int totpage = 0;		//총 페이지수
	int nowpage = 1;		//현재 페이지번호
	boolean updateChk = false;
	
	if( order.equals("HC_NAME ASC") ) {
		str_word = "HC_NAME DESC";		
	}else if( order.equals("HC_NAME DESC") ) {
		str_word = "HC_NAME ASC";		
	}
	
	if(mode.equals("update")) {
		updateChk = hMgr.updateActive(checkIds,activeVal);
		updateChk = true;
	} else {
		updateChk = false;
	}
	
	nowpage = pr.getInt("nowpage",1);
	HashtagBean[] arrHbean = null;
	arrHbean = hMgr.getKeywordList(searchWord, order, nowpage);
	
	totpage = hMgr.getFullCnt(searchWord, nowpage)/10;
	if(hMgr.getFullCnt(searchKey, nowpage)%10!=0) totpage++;
	
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
<script language="javascript">


	$(document).ready(loadList);
		
	//첨 로드시~
	function loadList()
	{	
		var f = document.managerForm;
		f.action = "hashtag_manager.jsp";
		f.target = "";
<%-- 		if('<%=mode%>' == "update") { --%>
<%-- 			if(<%=updateChk%>) { --%>
// 				alert('저장하였습니다.');
// 			} else {
// 				alert('다시 시도해 주세요.');
// 			}
// 		}
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
		
		f.submit();
// 		loadList();
	}
	
	//출력 여부
	function printYN(pVal) {
		var f = document.managerForm;
		checkId();
		f.activeVal.value = pVal;
		f.mode.value = "update";
		
		if(f.checkIds.value != "") {
			f.submit();
		} else {
			alert("선택된 정보가 없습니다.");
		}
	}
	
	function pageClick(paramUrl) {
		var f = document.managerForm; 
        f.action = 'hashtag_manager.jsp' + paramUrl;
        f.target='';
        f.submit();
	}

	//정렬시 - 사용 안함
	function excuteOrder(order)
	{
		var f = document.managerForm;		
		f.order.value = order;		
		Search(); 
	}

	//성향단어 에디터팝
	function popupEdit(id,mode)
	{				
		var f = document.managerForm;		
		f.hcCode.value = id;
		f.mode.value = mode;
		if(mode=='update')
		{
			popup.openByPost('managerForm','hashtag_keyword.jsp',500,200,false,false,false,'trendPop');
		}else{
			popup.openByPost('managerForm','hashtag_keyword.jsp',500,200,false,false,false,'trendPop');
		}
// 		loadList();
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

	//체크된  ID
	function checkId()
	{
		var f = document.managerForm;
		f.checkIds.value = '';
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
	}
	
	function submitF() {
		var f = document.managerForm;
		f.target='';
		f.action='hashtag_manager.jsp';
		f.submit();
	}

	//단어 삭제 - 사용 안함
	function deleteWord()
	{
		var f = document.managerForm;

		checkId();		
		if(f.checkIds.value==''){alert('선택된 리스트가 없습니다.'); return;}		
		f.mode.value = 'delete';
		f.hcCode.value = f.checkIds.value;
		f.target='processFrm';
		f.action='hashtag_prc.jsp';
		f.submit();		
	}
		
</script>

</head>
<body style="margin-left: 15px">
<form name="managerForm" id="managerForm" method="post" onsubmit="return false;">
<input type="hidden" name="mode" id="mode">
<input type="hidden" name="hcCode" id="hcCode">
<input type="hidden" name="checkIds" id ="checkIds">
<input type="hidden" name="order" id="order">
<input type="hidden" name="searchKey" id="searchKey">
<!-- <input type="hidden" name="searchWord" id="searchWord"> -->
<input type="hidden" name="activeVal" id="activeVal">
<input name="nowpage" type="hidden" value="<%=nowpage%>">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/hashtag/tit_icon.gif" /><img src="../../../images/admin/hashtag/tit_0501.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">해시태그관리</td>
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
						<td style="vertical-align:middle"><input style="width:500px;vertical-align:middle" class="textbox" type="text" name="searchWord" value="<%=searchWord%>" onkeypress="Javascript:if (event.keyCode == 13) { Search(); }" style="vertical-align:middle"><img src="../../../images/admin/hashtag/btn_search.gif" style="vertical-align:middle;cursor:pointer" onclick="Search();"/></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 검색 끝 -->
			<tr>
				<td style="height:40px;">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/hashtag/btn_allselect.gif" style="cursor:pointer" onclick="selectAll(!document.managerForm.checkAll.checked);"/>&nbsp;</td>
<!-- 						<td><img src="../../../images/admin/hashtag/btn_add2.gif" style="cursor:pointer" onclick="popupEdit('','insert');"/>&nbsp;</td> -->
<!-- 						<td><img src="../../../images/admin/hashtag/btn_del.gif" style="cursor:pointer" onclick="deleteWord();"/>&nbsp;</td> -->
						<td><img src="../../../images/admin/hashtag/btn_1.gif" style="cursor:pointer" onclick="printYN('Y')"/>&nbsp;</td>
						<td><img src="../../../images/admin/hashtag/btn_2.gif" style="cursor:pointer" onclick="printYN('N')"/>&nbsp;</td>
					</tr>
				</table>				
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td id="hashtagList">
					<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
					<col width="5%"><col width="80%"><col width="15%">
						<tr>               
							<th><input type="checkbox" name="checkAll" onclick="selectAll(this.checked);"></th>
					<%-- 		<th onclick="excuteOrder('<%=str_word%>');" style="cursor:pointer">키워드</th> --%>
							<th>해시태그</th>
							<th>출력여부</th>
						</tr>
					<%
						if(arrHbean != null && arrHbean.length > 0){
							for(int i = 0; i < arrHbean.length; i++){
					%> 
						<tr>         
							<td><input type="checkbox" name="checkId" value="<%=arrHbean[i].getHcCode()%>"></td>
							<td><p class="board_01_tit" onclick="popupEdit('<%=arrHbean[i].getHcCode()%>','update');" style="cursor:pointer"> <%=arrHbean[i].getHcName()%></p></td>
							<td><%=arrHbean[i].getHcActive()%></td>
						</tr>
					<%
							}
						}else{
					%>
						<tr>
							<td colspan="3" style="height:40px;font-weight:bold" align="center">조건에 맞는 데이터가 없습니다.</td>
						</tr>
					<%
						}
					%>
					</table>
				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr id="pageIndex">
				<td style="height:40px;" align="center">
						<table style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1">
							<tr>
								<td>
									<table id="paging" border="0" cellpadding="0" cellspacing="2">
										<tr>
											<%=PageIndex.getPageIndex(nowpage, totpage,"","" )%>
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
</table>
</form>
<iframe name="processFrm" height="0" border="0" style="display: none;"></iframe>
</body>
</html>