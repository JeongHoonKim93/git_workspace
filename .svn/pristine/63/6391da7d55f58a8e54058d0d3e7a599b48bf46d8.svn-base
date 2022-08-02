<%@page import="org.apache.poi.util.SystemOutLogger"%>
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
	pr.printParams();
    String sInit = (String)session.getAttribute("INIT");
    
   

    //Top메뉴에서 로고나 정보검색 메뉴 클릭시에는 검색조건을 초기화 시킨다.
    if ( sInit != null  && sInit.equals("INIT") ) {
        userEnvMgr uem = new userEnvMgr();
        uei = uem.getUserEnv( SS_M_ID );
        session.removeAttribute("INIT");
    }

    if ( uei.getSearchMode() == null ) {
        uei.setSearchMode("ALLKEY");
    }

    //지금까지 설정 내역을 세션에 저장
    session.removeAttribute("ENV");
    session.setAttribute("ENV",uei);

    //메뉴코드 스트림
    String menus =  uei.getMg_menu();
    String[] arMenu = menus.split(","); 
    UserGroupMgr uMgr = new UserGroupMgr();
    ArrayList menuList = uMgr.getSearchYp("1", menus);
    String meunUrl = uMgr.getMenuUrls();
   
%>



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
<script type="text/JavaScript">

	var selectedObj = "";

	var links = '<%=meunUrl%>';
	var link = links.split(',');
	
	/*
	var link = new Array(
			'solr/search_main_contents_solr.jsp'	
		,	'search_main_contents.jsp?searchmode=ALLKEY&xp=0&yp=0&zp=0'
		,	'search_main_exception.jsp?searchmode=EX_ALLKEY&xp=0&yp=0&zp=0'	
		,	'search_main_portalKeyword.jsp?searchmode=EX_ALLKEY'
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

	function menuClick(obj, type){
		selectedObj.src = selectedObj.src.replace("_on.","_off.");
		obj.src = obj.src.replace("_off.","_on.");
		selectedObj = obj; 			 
		var no = obj.name.replace("img","");
		top.bottomFrame.contentsFrame.document.location = link[Number(no)-1];
		
		if(type == 'key' ){
			document.getElementById("tr_menu").style.display = '';
			ifr_menu.location = "inc_keyword_menu.jsp?menu=ALLKEY&xp=0&yp=0&zp=0";
		}else if (type == 'exkey'){
			document.getElementById("tr_menu").style.display = 'none';
			ifr_menu.location = "inc_keyword_menu.jsp?menu=EX_ALLKEY&xp=0&yp=0&zp=0";
		}else if (type == 'portalkey'){
			document.getElementById("tr_menu").style.display = 'none';
		}else if (type == 'news'){
			//top.bottomFrame.contentsFrame.document.location = "search_news_contents.jsp?searchmode=ALLKEY&xp=0&yp=0&zp=0";
			document.getElementById("tr_menu").style.display = '';
			ifr_menu.location = "inc_keyword_menu.jsp?menu=NEWS&xp=0&yp=0&zp=0";
		}else if (type == 'warning'){
			document.getElementById("tr_menu").style.display = '';
			ifr_menu.location = "inc_keyword_menu.jsp?menu=WARNING&xp=0&yp=0&zp=0";
		}else if (type == 'domain'){
			document.getElementById("tr_menu").style.display = '';
			ifr_menu.location = "inc_keyword_menu.jsp?menu=DOMAIN&xp=0&yp=0&zp=0";
		}else if (type == 'warning'){
			document.getElementById("tr_menu").style.display = '';
			ifr_menu.location = "inc_keyword_menu.jsp?menu=WARNING&xp=0&yp=0&zp=0";
		}else{
			document.getElementById("tr_menu").style.display = 'none';
		}
					
  	}


	
	function onMs(obj){
		obj.src = obj.src.replace("_off.","_on.");
	}
	function outMs(obj){
		if(selectedObj != obj){
			obj.src = obj.src.replace("_on.","_off.");
		}
	}

</script>
</head>
<body onLoad="igList_resize();">
<table width="192" height="100%" border="0" cellspacing="0" cellpadding="0" align="left" >
    <tr>
	<td valign="top">
    	<img src="../../images/left/left_bg_01.gif">
    </td>
      <td width="192" height="100%" valign="top"><table width="191" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="../../images/left/left_01_top.gif" width="191" height="41" /></td>
          </tr>
          <tr>
            <td width="192" height="100%" valign="top"><table width="191" border="0" cellspacing="0" cellpadding="0">
            
            <%
            	UserGroupSuperBean.MenuBean mbean = null;
            	String keyIdx = "";
            	String  type = "";
            	for(int i = 0; i < menuList.size(); i++){
            		mbean = (UserGroupSuperBean.MenuBean) menuList.get(i);
            		
            		if(mbean.getMe_yp().equals("2")){
            			keyIdx = String.valueOf(i);
            			type = "";
            		}
            		
            		if(mbean.getMe_yp().equals("3")){
            			keyIdx = String.valueOf(i);
            			type = "key";

            		}
            		
            		if(mbean.getMe_yp().equals("12")){
            			keyIdx = String.valueOf(i);
            			type = "exkey";

            		}
            		
            		if(mbean.getMe_yp().equals("5")){
            			keyIdx = String.valueOf(i);
            			type = "portalkey";

            		}
            		
            		if(mbean.getMe_yp().equals("8")){
            			keyIdx = String.valueOf(i);
            			type = "news";

            		}
            		if(mbean.getMe_yp().equals("13")){
            			keyIdx = String.valueOf(i);
            			type = "warning";
            		}
            		/* if(mbean.getMe_yp().equals("13")){
            			keyIdx = String.valueOf(i);
            			type = "domain";
            		} */
            		
            		
            %>
            	<tr>
                  <td><img style="cursor: pointer; display: <%=su.inarray(arMenu, mbean.getMe_seq()) ? "" : "none"%>;" src="../../images/left/<%=mbean.getMe_img_off()%>" onMouseover="onMs(this);" onmouseout="outMs(this);" onclick="menuClick(this,'<%=type%>');" name="img<%=i+1%>"></td>
                </tr>
            
            <%		
            	}            
	
            	if(keyIdx.equals("")){
            		keyIdx = "1";
            	}
            
            %>
            	<!-- <tr>
                  <td><img style="cursor: pointer; display:""; src="../../images/left/left_search_11_off.gif" onMouseover="onMs(this);" onmouseout="outMs(this);" onclick="menuClick(this,'news');" name="img5"></td>
                </tr> -->
            
	          <tr>
	            <td><table width="192" border="0" cellspacing="0" cellpadding="0">
	              <tr>
	                <td bgcolor="F1F1F1"><table width="192" border="0" cellpadding="0" cellspacing="1">
	                    <tr>
	                      <td id="tr_menu" bgcolor="#F2EFE9">
	          				<iframe name="ifr_menu"  id="ifr_menu" src="inc_keyword_menu.jsp" frameborder="0" style="border:1px solid #000;width:188px;"></iframe>            	
	                      </td>
	                    </tr>
	                </table></td>
	              </tr>
	            </table></td>
	          </tr>
	          
	          
                
            </table></td>
          </tr>
      </table></td>
    </tr>
</table>
	
<script>

if (selectedObj==""){
	selectedObj=document.all.img1;
	selectedObj.src= selectedObj.src.replace("_off.","_on.");
}
</script>

</body>
</html>
