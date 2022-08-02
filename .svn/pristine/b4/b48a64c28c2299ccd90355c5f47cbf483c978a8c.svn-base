<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="risk.util.Log,
                 risk.util.StringUtil,
                 risk.util.ParseRequest,
                 risk.search.userEnvMgr,
                 risk.search.userEnvInfo,
                 risk.search.GetKGMenu,
                 risk.admin.usergroup.UserGroupMgr,
                 risk.admin.usergroup.UserGroupSuperBean,
                 java.util.ArrayList
                 "
%>
<%@include file="../inc/sessioncheck.jsp" %>
<%

	ParseRequest pr = new ParseRequest(request);
	GetKGMenu kg = new GetKGMenu();
	StringUtil su = new StringUtil();
	
	//사용자 기본 환경을 조회한다.
	//없으면  NCS기본 유저의 환경을 조회하여 가져온다.
	userEnvInfo uei = null;
	uei     = (userEnvInfo) session.getAttribute("ENV");
	
	String sInit = (String)session.getAttribute("INIT");
	
	//Top메뉴에서 로고나 정보검색 메뉴 클릭시에는 검색조건을 초기화 시킨다.
	if ( sInit != null  && sInit.equals("INIT") ) {
	    userEnvMgr uem = new userEnvMgr();
	    uei = uem.getUserEnv( SS_M_ID );
	    session.removeAttribute("INIT");
	}
	
	if ( uei.getSearchMode() == null || uei.getSearchMode().equals("") ) {
	    uei.setSearchMode("ALLKEY");
	}
	
	//지금까지 설정 내역을 세션에 저장
	session.removeAttribute("ENV");
	session.setAttribute("ENV",uei);
	
	//메뉴코드 스트림
    String menus =  uei.getMg_menu();
    String[] arMenu = menus.split(",");
    UserGroupMgr uMgr = new UserGroupMgr();
    ArrayList menuList = uMgr.getSearchYp("3", menus);
    String meunUrl = uMgr.getMenuUrls();

%>

<html>
<head>
<title><%=SS_TITLE%></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="../../css/left/base.css" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script language="JavaScript" type="text/JavaScript">
<!--

	var selectedObj = "";

	var links = '<%=meunUrl%>';
	var link = links.split(',');
	
	/*
	var link = new Array(
			"issue_report_list.jsp?ir_type=E"
		,	"issue_report_list.jsp?ir_type=D"
		,	"issue_report_list.jsp?ir_type=W"
		,	"issue_report_creater.jsp"
		     );
	*/	     
	
	function igList_resize(){
  		var ch = document.body.clientHeight;
  		var ifmObj = document.all.ifr_menu.style;
		var newH = ch - 160;
		
		//var newH = 400;
  		if (newH <100) newH = 100;
  		ifmObj.height = newH;
  	}

	function menuClick(obj){
		selectedObj.src = selectedObj.src.replace("_on.","_off.");
		obj.src = obj.src.replace("_off.","_on.");
		selectedObj = obj; 			 

		var no = obj.name.replace("img","");
		top.bottomFrame.contentsFrame.document.location = link[Number(no) - 1];
  	}
	
	function onMs(obj){
		obj.src = obj.src.replace("_off.","_on.");
	}
	function outMs(obj){
		if(selectedObj != obj){
			obj.src = obj.src.replace("_on.","_off.");
		}
	}

//-->
</script>
</head>
<body>
<table width="192" height="100%" border="0" cellspacing="0" cellpadding="0" align="left" >
    <tr>
      <td width="192" height="100%" valign="top"><table width="191" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="../../images/left/left_03_top.gif" width="191" height="41" /></td>
          </tr>
          <tr>
            <td width="192" height="100%" valign="top" background="../../images/left/left_05_bg.gif"><table width="191" border="0" cellspacing="0" cellpadding="0">
			<%
	           	UserGroupSuperBean.MenuBean mbean = null;
	           	String keyIdx = "";
	           	String  type = "";
	           	for(int i = 0; i < menuList.size(); i++){
	           		mbean = (UserGroupSuperBean.MenuBean) menuList.get(i);
           		
            %>
                <tr>
                  <td><img style="cursor: pointer; display: <%=su.inarray(arMenu, mbean.getMe_seq()) ? "" : "none"%>;" src="../../images/left/<%=mbean.getMe_img_off()%>" onMouseover="onMs(this);" onmouseout="outMs(this);" onclick="menuClick(this,'<%=type%>');" name="img<%=i+1%>"></td>
                </tr>
            <%		
            	}
            %>   
            	<!-- 운영에 올릴때 하드코딩 삭제 후 MENU테이블에 Y로 바꾸기 -->
            	<%-- <tr>
                  <td><img style="cursor: pointer; display: <%=su.inarray(arMenu, mbean.getMe_seq()) ? "" : "none"%>;" src="../../images/left/left_report_hycard_off.gif" onMouseover="onMs(this);" onmouseout="outMs(this);" onclick="menuClick(this,'<%=type%>');" name="img<%=4%>"></td>
                </tr>
                <tr>
                  <td><img style="cursor: pointer; display: <%=su.inarray(arMenu, mbean.getMe_seq()) ? "" : "none"%>;" src="../../images/left/left_report_hycap_off.gif" onMouseover="onMs(this);" onmouseout="outMs(this);" onclick="menuClick(this,'<%=type%>');" name="img<%=5%>"></td>
                </tr>
                <tr>
                  <td><img style="cursor: pointer; display: <%=su.inarray(arMenu, mbean.getMe_seq()) ? "" : "none"%>;" src="../../images/left/left_report_hycom_off.gif" onMouseover="onMs(this);" onmouseout="outMs(this);" onclick="menuClick(this,'<%=type%>');" name="img<%=6%>"></td>
                </tr>  --%>
                <!-- 운영에 올릴때 하드코딩 삭제 후 MENU테이블에 Y로 바꾸기 -->       
            </table></td>
          </tr>
      </table></td>
    </tr>
</table>
	

</body>
</html>
<script>

if (selectedObj==""){
	selectedObj=document.all.img1;
	selectedObj.src= selectedObj.src.replace("_off.","_on.");
}

</script>
