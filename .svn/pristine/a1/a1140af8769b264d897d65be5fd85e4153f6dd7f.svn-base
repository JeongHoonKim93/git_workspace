<%@ page contentType = "text/html; charset=utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="risk.mobile.AlimiSettingDao
				,risk.mobile.AlimiSettingBean
				,risk.admin.alimi.AlimiLogMgr
				,risk.admin.alimi.AlimiLogSuperBean			
				,risk.util.ParseRequest
				,java.util.ArrayList
				,java.util.*				
				" %>
<%@page import="risk.util.PageIndex"%>
<%
	
	ParseRequest pr = new ParseRequest(request);
	AlimiSettingDao asDao = new AlimiSettingDao();
	AlimiSettingBean asBean = new AlimiSettingBean();
	ArrayList arrAlimiSetList = new ArrayList();
	
	pr.printParams();
	
	int rowCnt = 10;
	int iNowPage  = pr.getInt("nowpage",1);	
	
	//arrAlimiSetList = asDao.getAlimiSetList(iNowPage , rowCnt ,"","Y");
	arrAlimiSetList = asDao.getTelegramSetList(iNowPage , rowCnt ,"","");
	
	int iTotalCnt= asDao.getListCnt();	
	int iTotalPage      = iTotalCnt / rowCnt;
    if ( ( iTotalCnt % rowCnt ) > 0 ) iTotalPage++;
    
	AlimiLogMgr lMgr = new AlimiLogMgr();
	
	//ArrayList reData = lMgr.getAlimiLogList(iNowPage , rowCnt, sDateFrom, sDateTo, mal_type, searchKey, as_type);
	//ArrayList reData = lMgr.getTelegramLogList(iNowPage , rowCnt, sDateFrom, sDateTo, mal_type, searchKey, tl_code);


	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RISK</title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<script language="javascript">
<!--
	var allcheck = 0;

	function asSetInsert()
	{
		var f = document.alimi_setting_list;
		f.mode.value='INSERT';
		f.target='';
		f.action = 'alimi_setting_detail.jsp';
		f.submit();
	}
		
	function asSetUpdate( ts_seq )
	{
		var f = document.alimi_setting_list;
		f.ts_seq.value = ts_seq;
		f.mode.value ='UPDATE';
		f.target='';
		f.action = 'alimi_setting_detail.jsp';
		f.submit();		
	}
		
	function allselect()
	{
		var frm = document.all;
		if( frm.chkNum ) {
			if( allcheck == 0 ) {
				if( frm.chkNum.length > 1 ) {
					for( i=0; i< frm.chkNum.length; i++ )
			   		{
			   			 frm.chkNum[i].checked = true;
			   		}
			   	}else {
			   		frm.chkNum.checked = true;
			   	}
		   		allcheck = 1;
		   	}else {
		   		if( frm.chkNum.length > 1 ) {
			   		for( i=0; i< frm.chkNum.length; i++ )
			   		{
			   			 frm.chkNum[i].checked = false;
			   		}
			   	}else {
			   		frm.chkNum.checked = false;
			   	}
		   		allcheck = 0;
		   	}
		}
	}
	
	function delList()
	{
		var selectNum = '';
    	var i = 0;
    	
    	var frm = document.alimi_setting_list;
    	
    	if ( confirm("삭제 하시겠습니까?" ) ) {
    		if( frm.chkNum ) {
	    		if( frm.chkNum.length > 1 ) {
		    		for( i=0; i< frm.chkNum.length; i++ )
		    		{
		    			if( frm.chkNum[i].checked == true ) {
		    				if( selectNum.length > 0 ) {
		    					selectNum = selectNum+','+frm.chkNum[i].value;
		    				}else {
		    					selectNum = frm.chkNum[i].value;
		    				}
		    			}
		    		}
		    	}else {
		    		if( frm.chkNum.checked == true ) {
		    			selectNum = frm.chkNum.value;
		    		}
		    	}
			}
			//alert(selectNum);
		    if( selectNum.length > 0 ) {
			    frm.ts_seqs.value = selectNum;
			    frm.mode.value = 'DELETE';
			    frm.target = '';
			    frm.action = 'alimi_setting_prc.jsp';
			    frm.submit();
			}else {
	    		alert('삭제할 수신자를 선택하세요');
	    	}
		}
	}
	
	function pageClick( paramUrl ) {
		var f = document.alimi_setting_list;
		f.nowpage.value = paramUrl.substr(paramUrl.indexOf("=")+1, paramUrl.length);
		f.action='alimi_setting_list.jsp';
		f.submit();
    }
	
//-->
</script>
</head>
<body style="margin-left: 15px">
<form name="alimi_setting_list" method="post">
<input type="hidden" name="mode">
<input type="hidden" name="nowpage" value="<%=iNowPage%>">
<input type="hidden" name="ts_seq">
<input type="hidden" name="ts_seqs">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/alimi/tit_icon.gif" /><img src="../../../images/admin/alimi/tit_0510.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">텔레그램 알리미 설정관리</td>
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
				<td style="height:40px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
				  	<tr>
						<td style="width:80px;"><img src="../../../images/admin/alimi/btn_allselect.gif" onclick="allselect();" style="cursor:pointer;"/></td>					
					<!-- <td><img src="../../../images/admin/alimi/btn_del.gif" onclick="delList();" style="cursor:pointer;"/></td>
						<td style="width:52px;"><img src="../../../images/admin/alimi/btn_alrimiadd.gif" onclick="asSetInsert();" style="cursor:pointer;"/></td> -->
					</tr> 
				</table>
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td>       
				<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
				<col width="5%"><col width="25%"><col width="20%"><col width="25%"><col width="25%">
					<tr>           
						<th><input type="checkbox" name="chkNum" value="" onclick="allselect();"></th>  
						<th>그룹방 명</th>
						<th>작동 여부</th>
						<th>ID 명</th>
						<th>마지막 발송 일자</th>
					</tr>
<%
	
	if(arrAlimiSetList.size()>0){
	  	for(int i=0; i < arrAlimiSetList.size(); i++)
		{
	  		asBean = new AlimiSettingBean();  	
	  		asBean = 	(AlimiSettingBean)arrAlimiSetList.get(i);
%>
					<tr>
						<td><input type="checkbox" name="chkNum" value="<%=asBean.getTs_seq()%>"></td>  
						<td onclick="asSetUpdate( '<%=asBean.getTs_seq()%>' );" style="cursor:pointer" ><p class="board_01_tit"><%=asBean.getTs_name()%></p></td>
						<%if(asBean.getOperate_yn().equals("0")){%><td> N </td><%}else if(!asBean.getOperate_yn().equals("0")){%><td> Y </td><%}%>						
						<td><%=asBean.getTs_id()%></td>
						<td><%=asBean.getLast_send_date()%></td>
<%-- 						<td><%=asBean.getLastSendDate()%></td>
 --%>					</tr>
<%
		}
	}else{
%>
					<tr>
						<td colspan="6" style="font-weight:bold;height:40px" align="center">조건에 맞는 데이터가 없습니다.</td>
					</tr>
<%		
	}
%>
				</table>
				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;" align="center">
				<table style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1">
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