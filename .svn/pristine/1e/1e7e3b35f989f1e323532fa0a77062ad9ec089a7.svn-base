<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
                 	,risk.issue.IssueMgr
                 	,risk.issue.IssueCodeMgr
					,risk.util.ParseRequest
					,risk.issue.IssueCodeBean
					" %>
<%@page import="risk.util.PageIndex"%>

<%
	String seqList = "";
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	int rowCnt = 10;
	int iNowPage        = pr.getInt("nowpage",1);
	String searchWord = pr.getString("searchWord","");
	
	IssueCodeBean icBean;
	
	ArrayList arrICB = new ArrayList();
    IssueCodeMgr icm;
    
    icm = IssueCodeMgr.getInstance();	
	int iTotalCnt= icm.mainIssueCount( searchWord );
	arrICB = icm.getMainIssue(iNowPage, rowCnt, searchWord);
	
	int iTotalPage      = iTotalCnt / rowCnt;
    if ( ( iTotalCnt % rowCnt ) > 0 ) iTotalPage++;
	
	String strMsg = "";
	strMsg = " <b>"+iTotalCnt+"건</b>, "+iNowPage+"/"+iTotalPage+" pages";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>삼성SDI</title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<script language="javascript">
<!--
	var kg_list = "";
	function updateDisp()
	{
    	var frm = document.fSend;
    	
    	if ( confirm("저장 하시겠습니까?" ) ) {
			    frm.target = '';
			    frm.seqList.value=kg_list;
			    frm.action = 'mainissue_prc.jsp';
			    frm.submit();
		}
	}
	function pageClick( paramUrl ) {
		document.fSend.nowpage.value = paramUrl.substr(paramUrl.indexOf("=")+1, paramUrl.length);
		document.fSend.submit();
    }
//-->
</script>
</head>
<body style="margin-left: 15px">
<form name="fSend" action="mainissue_list.jsp" method="post">
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
						<td><img src="../../../images/admin/mainissue/tit_icon.gif" /><img src="../../../images/admin/mainissue/title.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">주요이슈관리</td>
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
						<th style="height:30px;">주요이슈검색</th>
						<td style="vertical-align:middle"><input style="width:460px;vertical-align:middle" class="textbox" type="text" name="searchWord" value="<%=searchWord%>" onkeydown="javascript:if (event.keycode == 13) { fsend.submit(); }"><img src="../../../images/admin/receiver/btn_search.gif" onclick="fSend.submit();" style="cursor:pointer;vertical-align:middle"/></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 검색 끝 -->
			<tr>
				<td style="height:40px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:95%;">&nbsp;</td>
						<td style="width:5%;"><img src="../../../images/admin/member_group/btn_save.gif" onclick="updateDisp();" style="cursor:pointer;"/></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td>       
				<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
				<col width="70%"><col width="30%">
					<tr>      
						<th>주요이슈 명</th>
						<th>출력 여부</th>
					</tr>
<%
  	for(int i=0; i < arrICB.size(); i++){
		icBean = new IssueCodeBean();
		icBean = 	(IssueCodeBean)arrICB.get(i);
		
%>
					<tr>
						<td><%=icBean.getIc_name()%></td>
						<td>
							<%
								seqList=seqList+","+icBean.getIc_seq();
							%>
							<input type="radio" name="dispYN<%=icBean.getIc_seq()%>" value="Y" <%if(icBean.getIc_dispyn() != null && icBean.getIc_dispyn().equals("Y")){ %>checked<%} %>/>예&nbsp;
							<input type="radio" name="dispYN<%=icBean.getIc_seq()%>" value="N" <%if(icBean.getIc_dispyn() != null && icBean.getIc_dispyn().equals("N")){ %>checked<%} %>/>아니오
						</td>
					</tr>
<%
	}
%>
				</table>
				</td>
			</tr>
			<!-- 게시판 끝 -->
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
<script language="javascript">
kg_list = '<%=seqList%>';
</script>
</form>
</body>
</html>