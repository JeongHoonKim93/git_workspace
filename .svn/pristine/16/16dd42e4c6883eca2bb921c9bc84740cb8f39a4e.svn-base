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
    ArrayList menuList = uMgr.getSearchYp("2", menus);
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

.style2 {FONT-WEIGHT: bold; FONT-SIZE: 12px; COLOR: #0167B2; LINE-HEIGHT: 16px; FONT-FAMILY: "돋움"; }
-->
</style>
<script language="JavaScript" type="text/JavaScript">
<!--

	var selectedObj = "";


	var links = '<%=meunUrl%>';
	var link = links.split(',');

	/*
	var link = new Array(
			"issue_data_list.jsp"
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

	function menuClick(obj, sub_obj, img){
		selectedObj.src = selectedObj.src.replace("_on.","_off.");
		obj.src = obj.src.replace("_off.","_on.");
		selectedObj = obj; 			 

		var no = obj.name.replace("img","");


		var sub_cnt = Number(document.getElementById("sub_cnt").value);
		for(var i = 1; i <= sub_cnt; i++){
			document.getElementById("td_writer_" + i).bgColor='#F8F7F7';	
			document.getElementById("img_writer_" + i).src = '../../images/left/icon_01.gif';
			document.getElementById("span_writer_" + i).className = '';  
		}

		
		
		if(sub_obj != '' && sub_obj >= 0){

			document.getElementById("td_writer_" + sub_obj).bgColor='#E3EEFD';	
			document.getElementById("img_writer_" + sub_obj).src = '../../images/left/icon_01_on.gif';
			document.getElementById("span_writer_" + sub_obj).className = 'style2';
			
			top.bottomFrame.contentsFrame.document.location = img;	
		}else{
			top.bottomFrame.contentsFrame.document.location = link[Number(no) - 1];
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

//-->
</script>
</head>
<body>
<table width="192" height="100%" border="0" cellspacing="0" cellpadding="0" align="left" >
    <tr>
    
      <td width="192" height="100%" valign="top"><table width="191" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="../../images/left/left_02_top.gif" width="191" height="41" /></td>
          </tr>
          <tr>
          
            <td width="192" height="100%" valign="top" background="../../images/left/left_05_bg.gif"><table width="191" border="0" cellspacing="0" cellpadding="0">
            <%
            	UserGroupSuperBean.MenuBean mbean = null;
            	UserGroupSuperBean.MenuBean mbean2 = null;
            	ArrayList menuList2 = null;
            	int zpCnt = 0;
            	
            	
            	String keyIdx = "";
            	String  type = "";
            	//for(int i = 0; i < menuList.size(); i++){
            		
            		mbean = (UserGroupSuperBean.MenuBean) menuList.get(0);
            		
            %>
                <tr>
                  <td><img style="cursor: pointer; display: <%=su.inarray(arMenu, mbean.getMe_seq()) ? "" : "none"%>;" src="../../images/left/<%=mbean.getMe_img_off()%>" onMouseover="onMs(this);" onmouseout="outMs(this);" onclick="menuClick(this,'<%=type%>');" name="img<%=0+1%>"></td>
                </tr>
                
                <%
                	if(mbean.getMe_zp_cnt() > 0){
                %>
                
               		<tr>
	                  <td><table width="206" border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                      <td><table width="206" border="0" cellspacing="0" cellpadding="0">
	                          <tr>
	                            <td width="1" bgcolor="DCDCDC"></td>
	                            <td bgcolor="#F8F7F7"><table width="204" border="0" cellspacing="0" cellpadding="0">
	                            <%
	                            	menuList2 = uMgr.getSearchZp(mbean.getMe_xp(), mbean.getMe_yp(),"1",menus);
	                            
		                            for(int j = 0; j < menuList2.size(); j++){
		                    			mbean2 = (UserGroupSuperBean.MenuBean) menuList2.get(j);
		                    			zpCnt++;
		                    	%>
		                    		<tr>
	                                	<td onclick="menuClick(document.img<%=(0+1)%>,<%=zpCnt%>,'<%=mbean2.getMe_url()%>');"  id="td_writer_<%=zpCnt%>" height="26"  style="padding:0px 0px 0px 10px; cursor: pointer;"><img id="img_writer_<%=zpCnt%>" src="../../images/left/icon_01.gif" width="5" height="6" /> <span id="span_writer_<%=zpCnt%>" class=""><%=mbean2.getMe_name()%></span></td>
	                              	</tr>		                    	
		                    	<%			
		                            }
	                            %>
	                           
	                            </table></td>
	                            <td width="1" bgcolor="DCDCDC"></td>
	                          </tr>
	                      </table></td>
	                    </tr>
	
	                  </table></td>
	                </tr>
                
                <%	
                	//}
                %>
                
                
            <%		
            	}
            %>    
                  
            </table></td>
          </tr>
      </table></td>
    </tr>
</table>
<input type="hidden" id="sub_cnt" name="sub_cnt" value="<%=zpCnt%>">
</body>
</html>
<script>

if (selectedObj==""){
	selectedObj=document.all.img1;
	selectedObj.src= selectedObj.src.replace("_off.","_on.");
}

</script>
