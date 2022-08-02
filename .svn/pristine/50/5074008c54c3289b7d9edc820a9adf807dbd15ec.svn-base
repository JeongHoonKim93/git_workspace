<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page import="risk.issue.IssueDataBean
                ,risk.issue.IssueMgr
                ,java.util.ArrayList
                ,risk.util.ParseRequest
                ,risk.issue.IssueCodeMgr
                ,risk.issue.IssueCodeBean
                ,risk.issue.IssueBean                
                ,risk.search.MetaMgr
                ,risk.search.userEnvMgr
                ,risk.search.userEnvInfo
                ,risk.util.StringUtil
                ,risk.util.DateUtil
                ,risk.util.PageIndex
                ,java.net.URLDecoder" %>

<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	IssueMgr issueMgr = new IssueMgr();
	MetaMgr smgr = new MetaMgr();
	ArrayList arrIcBean = null;
	ArrayList arrIdBean = null;
	ArrayList arrIdcBean =  null;
	
	ArrayList arrSiteGroup = null;
	
	
	int nowpage = pr.getInt("nowpage",1);	
	int pageCnt = 10;
	int totalCnt = 0;
	int totalPage = 0;	
	
	
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	
	String ir_stime = pr.getString("ir_stime","16");
	String ir_etime = pr.getString("ir_etime","16");
	
	//세션정보 가져오기~~
	userEnvInfo uei = null;
    uei = (userEnvInfo) session.getAttribute("ENV");
	
	
	
	
	//타입코드 조건주기~~~
	String typeCode = "";
	String typeCode6 = pr.getString("typeCode6","");
	String typeCodePack = pr.getString("typeCodePack","");
	if(!typeCode6.equals("")){
		
		if(typeCode6.equals("8002")){
			
			if(typeCode.equals("")){
				typeCode = "8," + "2";
			}else{
				typeCode += "@8," + "2";
			}
		}else{
			if(typeCode.equals("")){
				typeCode = "6," + typeCode6;
			}else{
				typeCode += "@6," + typeCode6;
			}
		}
	}else{
		typeCode = "6," + "1";
	}

	
	if(!typeCodePack.equals("")){
		if(typeCode.equals("")){
			typeCode = typeCodePack;
		}else{
			typeCode += "@" + typeCodePack;
		}
	}
	
	
	String srtMsg = null;
	String language = pr.getString("language");
	String parents = pr.getString("parents");
	
	//IssueCodeMgr 인스턴스 생성
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	IssueCodeBean icBean = null;	
	icMgr.init(0);		
	
	//검색날짜 설정 : 기본 7일간 검색한다.
	String sCurrDate    = du.getCurrentDate();
	if (sDateTo.equals("")) sDateTo = du.getCurrentDate();
	if (sDateFrom.equals("")) {
		du.addDay(-1);
		sDateFrom = du.getDate();
	}
	
	String beforDateFrom = du.addDay_v2(sDateFrom, -1);
	String beforDateTo = du.addDay_v2(sDateTo, -1);
	String nextDateFrom = du.addDay_v2(sDateFrom, 1);
	String nextDateTo = du.addDay_v2(sDateTo, 1);
	
	//관련정보 리스트
	IssueDataBean idBean = null;	
	arrIdBean = issueMgr.getIssueDataList_groupBy(nowpage,pageCnt,"","","","","","2",sDateFrom,ir_stime+":00:00",sDateTo,ir_etime+":00:00",typeCode,"","Y", language,"","","",uei.getMg_company());	
	totalCnt =  issueMgr.getTotalIssueDataCnt();
	totalPage = totalCnt / pageCnt;
	if(totalCnt % pageCnt > 0){
		totalPage += 1;
	}

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<title>RIS-K</title>
<link rel="stylesheet" type="text/css" href="../../css/mobile/basic.css">
<style type="text/css">
<!--
.t_gray {
	font-family: Tahoma, "돋움", "돋움체";
	font-size: 11px;
	color: #9F9F9F;
}
.input {
	BORDER-RIGHT: #E1E2E1 1px solid; BORDER-TOP: #E1E2E1 1px solid; BORDER-LEFT: #E1E2E1 1px solid; BORDER-BOTTOM: #E1E2E1 1px solid; COLOR: #767676;  HEIGHT: 20px; FONT-SIZE: 12px; PADDING-TOP: 3px; BACKGROUND-COLOR: #ffffff
}
body {
	background-image: url(../../images/mobile/login_bg01.gif);
	background-color: #384b5a;
}

.select_he{height: 27px; width: 130px}

-->
</style>
<script language="JavaScript" type="text/JavaScript">
<!--

	


	
 	function getSearch(type4){
	 	 		
 		var f = document.fSend;
		if(type4 != null && type4 !=''){
			f.typeCode6.value = type4;
		}
		f.nowpage.value = 1;
 		f.target = '';
 		f.action = 'mobile_issue_list.jsp';
 		f.submit(); 	
 	}

	var chkPop = 1;
	function hrefPop(url){
		//window.open(url,'hrefPop'+chkPop,'');
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkPop,'');
		chkPop++;
	}

	function fn_before()
	{
		var f = document.fSend;
		
		var nowpage = Number(f.nowpage.value);
		
		if( nowpage > 1)
		{
			f.nowpage.value = nowpage - 1;
			f.target = '';
			f.action = 'mobile_issue_list.jsp';
			f.submit();
		}
	}
	function fn_next()
	{
		var f = document.fSend;
		
		var nowpage = Number(f.nowpage.value);
		var totalPage = Number(f.totalPage.value);
		
		if( nowpage < totalPage)
		{
			f.nowpage.value = nowpage + 1;
			f.target = '';
			f.action = 'mobile_issue_list.jsp';
			f.submit();
		}
	}

	function setDate(sDate, eDate){
		var f = document.fSend;
		
		f.nowpage.value = '1';
		f.sDateFrom.value = sDate;
		f.sDateTo.value = eDate;
		f.target = '';
		f.action = 'mobile_issue_list.jsp';
		f.submit();
	}

	function goPcVersion(){
		document.location = '../main.jsp';		
	}
	function logOut(){
		document.location = '../logout.jsp?mType=m';
	}

	function getMenu(menu){

		if(menu == 'search'){

			document.search.action = 'mobile_content_list.jsp';
			document.search.target = '';
			document.search.submit();
		}else if(menu == 'issue'){

			document.fSend.nowpage.value = '1';
			document.fSend.sDateFrom.value = '';
			document.fSend.sDateTo.value = '';
			document.fSend.typeCode6.value = '';
			document.fSend.typeCodePack.value = '';


			document.fSend.action = 'mobile_issue_list.jsp';
			document.fSend.target = ''; 
			document.fSend.submit();
			
		}else if(menu == 'report'){
			document.report.action = 'mobile_report_list.jsp';
			document.report.target = '';
			document.report.submit();
		}else if(menu == 'dashboard'){
			document.location.href = 'mobile_dashboard.jsp';
		}
		
	}
	
//-->	
</script>
</head>

<body onload="window.scrollTo(0,1);" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<form name="search" id="search" action="mobile_content_list.jsp" method="post">
	<input type="hidden" name="nowpage" value="1">
	<input type="hidden" name="xp" value="">
	<input type="hidden" name="yp" value="">
	<input type="hidden" name="zp" value="">
	<input type="hidden" name="sg_seq" value="">
	<input type="hidden" name="kXp" value="">
</form>

<form name="report" id="report" action="mobile_report_list.jsp" method="post">
<input type="hidden" name="nowpage" value="1">
<input type="hidden" name="totalPage" value="">
<input type="hidden" name="ir_type" value="">
</form>


<form name="fSend" id="fSend" action="mobile_issue_list.jsp" method="post">
<input type="hidden" name="nowpage" value="<%=nowpage %>">
<input type="hidden" name="typeCode6" value="<%=typeCode6%>">
<input type="hidden" name="totalPage" value="<%=totalPage%>">
<input type="hidden" name="sDateFrom" value="<%=sDateFrom%>">
<input type="hidden" name="sDateTo" value="<%=sDateTo%>">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td background="../../images/mobile/bg.gif"><img src="../../images/mobile/list_top.gif"></td>
  </tr>
    
  <tr>
    <td height="35" background="../../images/mobile/menu_bg_03.gif"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
      	<td width="25%" align="center" background="../../images/mobile/menu_bg_02.gif" class="menu_blueTEXTover" style="background-repeat:no-repeat; cursor: pointer;" onclick="getMenu('dashboard')"><strong><img src="../../images/mobile/menu_04_off.gif" width="19" height="18" align="absmiddle"> 대시보드</strong></td>
        <td width="25%" align="center" background="../../images/mobile/menu_bg_02.gif" class="menu_blueTEXTover" style="background-repeat:no-repeat; cursor: pointer;" onclick="getMenu('search')"><img src="../../images/mobile/menu_01_off.gif" width="22" height="22" align="absmiddle" ><strong> 정보검색</strong></td>
        <td width="25%" align="center" background="../../images/mobile/menu_bg_01.gif" class="menu_blueOver" style="cursor: pointer;" onclick="getMenu('issue')"><img src="../../images/mobile/menu_02_on.gif" width="19" height="18" align="absmiddle" ><strong> 이슈관리</strong></td>
        <td width="25%" align="center" background="../../images/mobile/menu_bg_02.gif" class="menu_blueTEXTover" style="background-repeat:no-repeat; cursor: pointer;" onclick="getMenu('report')"><img src="../../images/mobile/menu_03_off.gif" width="18" height="20" align="absmiddle" > <strong>보고서</strong></td>
      </tr>
    </table></td>
  </tr>
    <tr>
    <td height="35" bgcolor="#e4e4e4" style="padding:3px 13px 0px 13px"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><strong class="menu_gray">이슈관리</strong> <img src="../../images/mobile/txt_line.gif" width="9" height="13" align="absmiddle"><strong class="menu_black"> <%=totalCnt%>건</strong></td>
        <td align="right">
<%
	String selected = "";
	String codeTypeName = "";
	arrIcBean = icMgr.GetType(4);
	codeTypeName = icMgr.GetCodeName(arrIcBean,0);
%>        
        	<select name="typeCodePack" onChange="getSearch();" class="select_he">
				<option value="">== <%=codeTypeName%> ==</option>
<%
	for(int i = 1; i < arrIcBean.size(); i++){
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(typeCodePack.equals(String.valueOf(icBean.getIc_type()+ ","+ icBean.getIc_code()))) selected = "selected";
		else selected = "";
		
		if(!(icBean.getIc_type()+ ","+ icBean.getIc_code()).equals("4,5")){
%>				
				<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
<%
		}
	}

	selected = "";
	codeTypeName = "";
	arrIcBean = icMgr.GetType(13);
	codeTypeName = icMgr.GetCodeName(arrIcBean,0);

%>
			<option value="">== <%=codeTypeName%> ==</option>
<%
	for(int i = 1; i < arrIcBean.size(); i++){
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(typeCodePack.equals(String.valueOf(icBean.getIc_type()+ ","+ icBean.getIc_code()))) selected = "selected";
		else selected = "";
		
%>	
				<option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>" <%=selected%>><%=icBean.getIc_name()%></option>
<%
		
	}
%>
				
			</select> 

		</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="" bgcolor="#B9B9B9"></td>
  </tr>
  <tr>
    <td height="30" background="../../images/mobile/menu_bg_04.gif"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
<%
	selected = "";
	codeTypeName = "";
	arrIcBean = icMgr.GetType_mobile();
	codeTypeName = icMgr.GetCodeName(arrIcBean,0);
	
	
	
	/*
	String check = "";
	for(int i = 1; i < arrIcBean.size(); i++){
		icBean = (IssueCodeBean) arrIcBean.get(i);
		if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())){
			check = "check";
			break;
		}
	}
	*/
	
	
	for(int i = 1; i < arrIcBean.size(); i++){
		icBean = (IssueCodeBean) arrIcBean.get(i);
		//if(icMgr.isTypeCode(typeCode,icBean.getIc_type()+","+icBean.getIc_code())) selected = "selected";
		//else selected = "";
		
		String font = "";
		if(icBean.getIc_code() == 8002){
			font = "<font color='F23446'/>";
		}else{
			font = "";
		}
		
		
		if(typeCode6.equals("")){
			if (i==1){
				out.print("<td onclick=\"getSearch('"+icBean.getIc_code()+"');\" align=\"center\" background=\"../../images/mobile/menu_bg_05.gif\" class=\"menu_blueOver\" style=\"cursor:pointer; \">" + font + icBean.getIc_name()+"</td>");
				
			}else{
				out.print("<td onclick=\"getSearch('"+icBean.getIc_code()+"');\" align=\"center\" background=\"../../images/mobile/menu_bg_06.gif\" class=\"menu_blueTEXTover\" style=\"background-repeat:no-repeat; cursor:pointer; \">" + font + icBean.getIc_name()+"</td>");	
			}	
		}else{
			if ( typeCode6.equals(String.valueOf(icBean.getIc_code()))){
				out.print("<td onclick=\"getSearch('"+icBean.getIc_code()+"');\" align=\"center\" background=\"../../images/mobile/menu_bg_05.gif\" class=\"menu_blueOver\" style=\"cursor:pointer; \">" + font + icBean.getIc_name()+"</td>");
			}else{
				out.print("<td onclick=\"getSearch('"+icBean.getIc_code()+"');\" align=\"center\" background=\"../../images/mobile/menu_bg_06.gif\" class=\"menu_blueTEXTover\" style=\"background-repeat:no-repeat; cursor:pointer; \">" + font + icBean.getIc_name()+"</td>");	
			}
		}
		
	}
%>      
      
      </tr>
    </table></td>
  </tr>

<%
	String sunghyang = "";
	for(int i =0; i < arrIdBean.size(); i++){
		idBean = (IssueDataBean)arrIdBean.get(i);
		arrIdcBean = (ArrayList) idBean.getArrCodeList();
		sunghyang = icMgr.GetCodeNameType(arrIdcBean,9);
%>
  
  <tr>
    <td bgcolor="#FFFFFF" style="padding:8px 13px 8px 13px"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="23" class="menu_blue"><a onClick="hrefPop('<%=idBean.getId_url()%>');" href="javascript:void(0);"><%=su.nvl(su.cutString(idBean.getId_title(),30,"..." ),"제목없음")%></a></td>
      </tr>
      <tr>
        <td class="menu_gray"><%=su.cutKey(su.toHtmlString(idBean.getId_content().replaceAll("<font color='black'>","").replaceAll("<b>","").replaceAll("</font>","").replaceAll("</b>","")), "포스코 POSCO", 50, "0000CC")%></td>
      </tr>
      <tr>
<%
	
	String type9Img = ""; 
	
	if(sunghyang.equals("긍정")){
		type9Img = "<img src='../../images/mobile/smile_icon_01.gif' width='16' height='16' align='absmiddle'></span><span class='smile'><strong>긍정</strong></span>";
	}else if(sunghyang.equals("부정")){
		type9Img = "<img src='../../images/mobile/bad_icon_01.gif' width='16' height='16' align='absmiddle'></span><span class='bad'><strong>부정</strong></span>";
	}else if(sunghyang.equals("중립")){
		type9Img = "<img src='../../images/mobile/s_icon_01.gif' width='16' height='16' align='absmiddle'></span><span class='s'><strong>중립</strong></span>";
	}
%>
      
        <td height="20"><span class="menu_red"><%=idBean.getMd_site_name()%></span> <span class="menu_gray02"><%=du.getDate(idBean.getMd_date(), "yy/MM/dd HH:mm")%> <%=type9Img%></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="1" bgcolor="#e1e1e1" ></td>
  </tr>
  
<%
	}
%> 
  
  <tr>
    <td height="2" background="../../images/mobile/list_line01.gif" ></td>
  </tr>
  <tr>
    <td height="28" background="../../images/mobile/list_bg01.gif" bgcolor="#FFFFFF" ><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="50%" align="center" class="textwhite" onclick="fn_before();" style="cursor: pointer;"><strong class="textwhite02">&lt; 이전</strong></td>
        <td width="50%" align="center" onclick="fn_next();" background="../../images/mobile/list_bar01.gif" class="textwhite02" style="background-repeat:no-repeat; font-weight: bold; cursor: pointer;">다음 &gt;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="2" background="../../images/mobile/list_line02.gif"></td>
  </tr>
  <tr>
    <td height="33" background="../../images/mobile/list_bg02.gif"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="33%" align="center" class="menu_blueOver" onclick="setDate('<%=beforDateFrom %>','<%=beforDateTo  %>')" style="cursor: pointer;"><%=du.getDate(beforDateTo,"M월 dd일(E)")%></td>
        <td width="33%" align="center" background="../../images/mobile/list_bar02.gif" class="menu_blueTEXTover" style="background-repeat:no-repeat; cursor: pointer;" onclick="setDate('<%=sDateFrom %>','<%=sDateTo  %>')"><%=du.getDate(sDateTo,"M월 dd일(E)")%></td>
        <td width="33%" align="center" background="../../images/mobile/list_bar02.gif" class="menu_blueTEXTover" style="background-repeat:no-repeat; cursor: pointer;" onclick="setDate('<%=nextDateFrom  %>','<%=nextDateTo   %>')"><%=du.getDate(nextDateTo,"M월 dd일(E)")%></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="2" background="../../images/mobile/list_line03.gif"></td>
  </tr>
  <tr>
    <td height="38" background="../../images/mobile/list_bg03.gif"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="50%" align="center" class="textwhite" onclick="goPcVersion();" style="cursor: pointer;"><strong>PC버전</strong></td>
        <td width="50%" align="center" onclick="logOut();" background="../../images/mobile/list_bar03.gif" class="textwhite" style="background-repeat:no-repeat; font-weight: bold; cursor: pointer;">로그아웃</td>
      </tr>
    </table></td>
  </tr>
</table>






</form>
</body>
</html>