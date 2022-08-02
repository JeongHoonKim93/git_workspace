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

	userEnvInfo uei = null;
	uei     = (userEnvInfo) session.getAttribute("ENV");
	StringUtil su = new StringUtil();
	String id = (String)session.getAttribute("SS_M_ID");
	
	//메뉴코드 스트림
    String menus =  uei.getMg_menu();
    String[] arMenu = menus.split(",");
    UserGroupMgr uMgr = new UserGroupMgr();
    System.out.println(menus);
    ArrayList menuList = uMgr.getSearchYp("5", menus);
    String meunUrl = uMgr.getMenuUrls();
    
    System.out.println(meunUrl);
    
%>
<html>
<head>
<title><%=SS_TITLE%></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
			"user/admin_user_list.jsp"
		,	"usergroup/admin_usergroup.jsp"
		,	"logs/user_log_list.jsp?sch_svc=1"
		,	"aekeyword/aekeyword_manager.jsp"
		,	"keyword/admin_keyword.jsp"
		,	"site/admin_site.jsp"
		,	"receiver/receiver_list.jsp"
		,	"classification/classification_mgr.jsp"
		,	"alimi/alimi_setting_list.jsp"
		,	"alimi/alimi_log_list.jsp"
		,	"tier/tier_main.jsp"
		,	"relationKeyword/relation_keyword.jsp"
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
	
	//BackupList
	function menuClick2(obj){
		selectedObj.src = selectedObj.src.replace("_on.","_off.");
		obj.src = obj.src.replace("_off.","_on.");
		selectedObj = obj; 			 

		var no = obj.name.replace("img","");

		top.bottomFrame.contentsFrame.document.location = "backup/backup_manager.jsp"
  	}
	
	//SlowQuery
	function menuClick3(obj){
		selectedObj.src = selectedObj.src.replace("_on.","_off.");
		obj.src = obj.src.replace("_off.","_on.");
		selectedObj = obj; 			 

		var no = obj.name.replace("img","");

		top.bottomFrame.contentsFrame.document.location = "slowquery/slowquery_manager.jsp"
  	}
	
	// test
	/* function test(obj){
		selectedObj.src = selectedObj.src.replace("_on.","_off.");
		obj.src = obj.src.replace("_off.","_on.");
		selectedObj = obj; 			 

		top.bottomFrame.contentsFrame.document.location = "portal_search_keyword/portal_search_keyword.jsp";
  	} */
	
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
            <td><img src="../../images/left/left_05_top.gif" width="191" height="41" /></td>
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
