<%@page import="org.apache.poi.util.SystemOutLogger"%>
<%@ page contentType = "text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
                 	,risk.sms.AddressBookDao
					,risk.sms.AddressBookBean
					,risk.sms.AddressBookGroupBean
					,risk.util.ParseRequest
					,java.util.*
					" %>
<%@page import="risk.util.PageIndex"%>

<%
	String seqList = "";
	ParseRequest pr = new ParseRequest(request);
	AddressBookDao AddDao = new AddressBookDao();
	AddressBookBean AddBean;
	pr.printParams();
	
	
	int rowCnt = 10;
	int iNowPage        =  Integer.parseInt(pr.getString("nowpage","1").split("&")[0]);
	String searchWord = pr.getString("searchWord","");
	String searchGroup = pr.getString("searchGroup","");
	
	if( !pr.getString("seqList").equals("") ){
		seqList = pr.getString("seqList");
	}
	
	int iTotalCnt= AddDao.addressCount( searchWord, searchGroup );
	System.out.println(iTotalCnt + "<<iTotalCnt");
	int iTotalPage      = iTotalCnt / rowCnt;
    if ( ( iTotalCnt % rowCnt ) > 0 ) iTotalPage++;
		
	ArrayList ArrMailUserBean;
	ArrMailUserBean = AddDao.addressList( iNowPage, rowCnt, searchWord, searchGroup);
	
	String strMsg = "";
	strMsg = " <b>"+iTotalCnt+"건</b>, "+iNowPage+"/"+iTotalPage+" pages";
	
	List AbGroupList = new ArrayList();
	
	AbGroupList = AddDao.getAdressBookGroup();
	AddressBookGroupBean abgBean = null;
	Iterator it = null;
	if(AbGroupList!=null)
	{
		it = AbGroupList.iterator();
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>삼성SDI</title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<script language="javascript">

	var allcheck = 0;
	function receverAdd()
	{
		location.href = 'receiver_detail.jsp?mode=add';
	}
	
	function receverDetail( seq )
	{
		location.href = 'receiver_detail.jsp?abSeq='+seq+'&mode=edit';
	}
	function allselect()
	{
		var frm = document.all;
		if( frm.tt ) {
			if( allcheck == 0 ) {
				if( frm.tt.length > 1 ) {
					for( i=0; i< frm.tt.length; i++ )
			   		{
			   			 frm.tt[i].checked = true;
			   		}
			   	}else {
			   		frm.tt.checked = true;
			   	}
		   		allcheck = 1;
		   	}else {
		   		if( frm.tt.length > 1 ) {
			   		for( i=0; i< frm.tt.length; i++ )
			   		{
			   			 frm.tt[i].checked = false;
			   		}
			   	}else {
			   		frm.tt.checked = false;
			   	}
		   		allcheck = 0;
		   	}
		}
	}
	
	function delList()
	{
		var kg_list = '';
    	var i = 0;
    	
    	var frm = document.fSend;
    	
    	if ( confirm("삭제 하시겠습니까?" ) ) {
    		if( frm.ab_seq ) {
	    		if( frm.ab_seq.length > 1 ) {
		    		for( i=0; i< frm.ab_seq.length; i++ )
		    		{
		    			if( frm.ab_seq[i].checked == true ) {
		    				if( kg_list.length > 0 ) {
		    					kg_list = kg_list+','+frm.ab_seq[i].value;
		    				}else {
		    					kg_list = frm.ab_seq[i].value;
		    				}
		    			}
		    		}
		    	}else {
		    		if( frm.ab_seq.checked == true ) {
		    			kg_list = frm.ab_seq.value;
		    		}
		    	}
			}
			    
		    if( kg_list.length > 0 ) {
			    frm.seqList.value = kg_list;
			    frm.mode.value = 'del';
			    frm.target = '';
			    frm.action = 'receiver_prc.jsp';
			    frm.submit();
			}else {
	    		alert('삭제할 수신자를 선택하세요');
	    	}
		}
	}
	
	function pageClick( paramUrl ) {
		document.fSend.nowpage.value = paramUrl.substr(paramUrl.indexOf("=")+1, paramUrl.length);
		document.fSend.submit();
    }
	
	//단어 검색시
	function Search()
	{
		var f = document.fSend;
		//AJAX 사용시 한글처리
		//f.searchKey.value = encodeURI(f.searchWord.value);
		//앞뒤 공백 제거
		f.searchWord.value = f.searchWord.value.replace(/^\s*/,'');
		f.searchWord.value = f.searchWord.value.replace(/\s*$/,'');	
		f.nowpage.value = '1';
		var value_searchGroup = document.getElementsByName("searchGroup");
		for (var i = 0; i < value_searchGroup.length; i++) {
			if(value_searchGroup[i].checked == true) {
				f.searchGroup.value = value_searchGroup[i].value;
			console.log(value_searchGroup[i].value);
			}
		}
		if(f.searchGroup.value == '') {	
			console.log(f.searchGroup.value + '<<안나와야 정상!');
		} else {
			console.log(f.searchGroup.value + '<<나와야 정상!');
		}
		f.submit();
	}

</script>
</head>
<body style="margin-left: 15px">
<form name="fSend" action="receiver_list.jsp" method="post">
<input type="hidden" name="mode" value="">
<input type="hidden" name="nowpage" value="<%=iNowPage%>">
<input type="hidden" name="seqList" value="">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/receiver/tit_icon.gif" /><img src="../../../images/admin/receiver/tit_0510.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">보고서수신자관리</td>
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
						<th style="height:30px;">이름검색</th>
						<td style="vertical-align:middle"><input style="width:460px;vertical-align:middle" class="textbox" type="text" name="searchWord" value="<%=searchWord%>" onkeydown="javascript:if (event.keycode == 13) { Search(); }"><img src="../../../images/admin/receiver/btn_search.gif" onclick="Search();" style="cursor:pointer;vertical-align:middle"/></td>
					</tr>
					<tr>
						<th style="height:30px;">그룹명검색</th>
						<%-- <td style="vertical-align:middle"><input style="width:460px;vertical-align:middle" class="textbox" type="text" name="searchWord" value="<%=searchWord%>" onkeydown="javascript:if (event.keycode == 13) { fsend.submit(); }"><img src="../../../images/admin/receiver/btn_search.gif" onclick="fSend.submit();" style="cursor:pointer;vertical-align:middle"/></td> --%>
						<td style="vertical-align:middle">
							 <select name="searchGroup" id="searchGroup" title="그룹명 선택" style="width: 90;">
							 <option value="">전체</option> 
<%
	if(it!=null){
		String selected = "";
		while(it.hasNext()){
			selected = "";
			abgBean = new AddressBookGroupBean();
			abgBean = (AddressBookGroupBean)it.next();
			if(searchGroup.equals(abgBean.getAg_seq())){
				selected="selected";
			}
%>        	
								<option value="<%=abgBean.getAg_seq()%>" <%=selected%>><%=abgBean.getAg_name()%></option>
<%
		}
	}
%>
							</select>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 검색 끝 -->
			<tr>
				<td style="height:40px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:80px;"><img src="../../../images/admin/receiver/btn_allselect.gif" onclick="allselect();" style="cursor:pointer;"/></td>
						<td><img src="../../../images/admin/receiver/btn_del.gif" onclick="delList();" style="cursor:pointer;"/></td>
						<td style="width:88px;"><img src="../../../images/admin/receiver/btn_useradd2.gif" onclick="receverAdd();" style="cursor:pointer;"/></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td>       
				<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
				<col width="5%"><col width="15%"><col width="30%"><col width="15%"><col width="20%"><col width="15%">
					<tr>      
						<th><input type="checkbox" id="tt" value="checkbox" onclick="allselect();"></th>       
						<th>성명</th>
						<th>부서</th>
						<th>수신자그룹</th>
						<th>보고서 수신</th>
						<th>연락처</th>
					</tr>
<%
  	for(int i=0; i < ArrMailUserBean.size(); i++){
		AddBean = new AddressBookBean();
		AddBean = 	(AddressBookBean)ArrMailUserBean.get(i);
		
		String smsReceive = "";
		String reportReceive = "";
		String[] sms = AddBean.getMab_sms_receivechk().split(",");
		for(int j=0; j < sms.length; j++){
			if(sms[j].equals("1") |	sms[j].equals("2") | sms[j].equals("3") | sms[j].equals("4")){
				smsReceive = "Y";
			}else {
			smsReceive = "N";
			}
		}
		if(AddBean.getMab_issue_receivechk().equals("1"))reportReceive = "I";
		if(AddBean.getMab_report_day_chk().equals("1")) reportReceive =  reportReceive.equals("") ?   "D" : reportReceive + "/D" ; 
		if(AddBean.getMab_report_week_chk().equals("1")) reportReceive =  reportReceive.equals("") ?   "D" : reportReceive + "/W" ;
%>
					<tr>
						<td><input type="checkbox" name="ab_seq" id="tt" value=<%=AddBean.getMab_seq()%>></td>
						<td onclick="receverDetail( <%=AddBean.getMab_seq()%> );" style="cursor:pointer;"><%=AddBean.getMab_name()%></td>
						<td><%=AddBean.getMab_dept()%></td>
						<td><%=AddBean.getAg_name()%></td>
						<td><%=reportReceive%></td>
						<td><%=AddBean.getMab_mobile()%> </td>
					</tr>
<%
	}
%>
				</table>
				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:128px;">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="80px"><img src="../../../images/admin/receiver/btn_allselect.gif" onclick="allselect();" style="cursor:pointer;"/></td>
								<td><img src="../../../images/admin/receiver/btn_del.gif" onclick="delList();" style="cursor:pointer;"/></td>
							</tr>
						</table>
						</td>
						<td align="right"><img src="../../../images/admin/receiver/btn_useradd2.gif" onclick="receverAdd();" style="cursor:pointer;"/></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td>
				<table align="center" style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td>
							<table id="paging" border="0" cellpadding="0" cellspacing="2">
								<tr>
									<%=PageIndex.getPageIndex(iNowPage, iTotalPage,"","" )%>
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
</body>
</html>