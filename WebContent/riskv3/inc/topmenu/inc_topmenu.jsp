<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="risk.util.StringUtil,
				 risk.search.userEnvInfo
				 ,risk.util.ParseRequest
                 "
%>
<%@ include file="../sessioncheck.jsp" %>
<%

	ParseRequest    pr = new ParseRequest(request);
	
	String sms = "";
	if( request.getParameter("sms") != null ) sms = request.getParameter("sms");
	
	pr.printParams();
	String selectedMenu =  pr.getString("selectedMenu","");
	
	

	String MGmenu = "";
	String[] arrMGmenu = null;

	StringUtil su = new StringUtil();

	userEnvInfo env = (userEnvInfo)session.getAttribute("ENV");
	MGmenu = env.getMg_menu();
	if( MGmenu != null )
	arrMGmenu = MGmenu.split(",");
	
	//대쉬보드 시간 초기화
	session.setAttribute("SS_SEARCHDATE", "");
	
	String firstMenu = arrMGmenu[0];
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../../../css/top/base.css" type="text/css">
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

// 현재 선택된 메뉴를 저장한다.
// 초기에 지정해 주어야 한다. 지정하지 않으면 검색메뉴를 지정한다.
var selectedObj = "";

// 각 메뉴 클릭시 이동할 페이지 정보를 지정한다.
var link = new Array(
// +Dashboard
"../../search/search.jsp?INIT=INIT",
"../../issue/issue.jsp",
"../../report/report.jsp",
"../../statistics/statistics.jsp",
"../../admin/admin_main.jsp"
);
// 메뉴 클릭시 이벤트
// 클릭한 메뉴를 선택상태로 만들고, 하단 프레임을 변경한다.
function mnu_chick(obj,seq) {
	var tmpObj;
	for (var i=1;i<7 ;i++ )
	{
		tmpObj = document.getElementById('img'+i);
		if( tmpObj ){
			setOff(tmpObj);
		}
	}
	setOn(obj);
	selectedObj = obj;
	parent.bottomFrame.location.href=link[seq];
	
}


function dashboard_chick() {
	//parent.location.href = '../../../dashboard/index.jsp';
	parent.location.href = '../../../dashboard/view/summary/index.jsp';
}

// 각 메뉴의 마우스 오버시 이벤트
function mnu_over(obj) {
	setOn(obj);
}

// 각 메뉴의 마우스 아웃시 이벤트
// 현재 선택된 메뉴면 
function mnu_out(obj) {
	if (selectedObj != obj)	{ setOff(obj);	}
}

function setOn(obj)
{
	var oSrc = obj.src;
	re = /_off./i;
	var nSrc = obj.src.replace(re, "_on.");
	obj.src=nSrc;
}
function setOff(obj)
{
	var oSrc = obj.src;
	re = /_on./i;
	var nSrc = obj.src.replace(re, "_off.");
	obj.src=nSrc;
}
function system_wait() {
	alert('준비중입니다.');
}

function sms_check() {
	setOn(document.all.img3);
}

function pop_up(){
	var url ="../../statistics_pop/index.jsp"; 
	window.open(url,"search","width=1200,height=1000,toolbar=no,status=no,location=no,scrollbars=yes,menubar=no,resizable=yes,left=50,right=50");
	
		
}


</script>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="62" valign="top" background="../../../images/top/top_bg.gif"><table width="1000"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="200"><img src="../../../images/top/system_logo.gif" onclick="mnu_chick(img1, 0);" width="200" style="cursor: pointer;"></td>
        <td width="808" rowspan="2" valign="top" background="../../../images/top/top_bg_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="10"></td>
          </tr>
          <tr>
            <td height="52" background="../../../images/top/top_bg_02.gif"><table width="809" border="0" cellspacing="0" cellpadding="0">
              <tr>
              <%
              	if( arrMGmenu != null ) {
              		if( su.inarray(arrMGmenu, "6") ) {
              %>
	                <td width="94"><img src="../../../images/top/menu_09_off.gif" onclick="dashboard_chick();" onMouseOut="mnu_out(this);" onMouseOver="mnu_over(this);" style="cursor: pointer;" name="img6" id="img6"></td>
	                <td width="1"><img src="../../../images/top/menu_line.gif" width="1" height="52" /></td>
				<%	} 
              		if( su.inarray(arrMGmenu, "1") ) {
              	%>
	                <td width="104"><img src="../../../images/top/menu_01_off.gif" onclick="mnu_chick(this, 0);" onMouseOut="mnu_out(this);" onMouseOver="mnu_over(this);" style="cursor: pointer;" name="img1" id="img1"></td>
	                <td width="1"><img src="../../../images/top/menu_line.gif" width="1" height="52" /></td>
	          	<%	} 

              		if( su.inarray(arrMGmenu, "2") ) {
              %>      
	                <td width="95"><img src="../../../images/top/menu_02_off.gif" onclick="mnu_chick(this, 1);" onMouseOut="mnu_out(this);" onMouseOver="mnu_over(this);" style="cursor: pointer;" name="img2" id="img2"></td>
	                <td width="1"><img src="../../../images/top/menu_line.gif" width="1" height="52" /></td>
				<%	} 

              		if( su.inarray(arrMGmenu, "3") ) {
              %>
	                <td width="104"><img src="../../../images/top/menu_03_off.gif" onclick="mnu_chick(this, 2);" onMouseOut="mnu_out(this);" onMouseOver="mnu_over(this);" style="cursor: pointer;" name="img3" id="img3"></td>
	                <td width="1"><img src="../../../images/top/menu_line.gif" width="1" height="52" /></td>
				<%	} 

              		if( su.inarray(arrMGmenu, "4") ) {
              %>
	                <!-- <td width="96"><img src="../../../images/top/menu_04_off.gif" onclick="mnu_chick(this, 3);" onMouseOut="mnu_out(this);" onMouseOver="mnu_over(this);" style="cursor: pointer;" name="img4" id="img4"></td> -->
	                <td width="96"><img src="../../../images/top/menu_04_off.gif" onclick="pop_up();" onMouseOut="mnu_out(this);" onMouseOver="mnu_over(this);" style="cursor: pointer;" name="img4" id="img4"></td>
	                <td width="1"><img src="../../../images/top/menu_line.gif" width="1" height="52" /></td>
				<%	} 

              		if( su.inarray(arrMGmenu, "5") ) {
              			
              %>
	                <td width="93"><img src="../../../images/top/menu_06_off.gif" onclick="mnu_chick(this, 4);" onMouseOut="mnu_out(this);" onMouseOver="mnu_over(this);" style="cursor: pointer;" name="img5" id="img5"></td>
	                <td width="1"><img src="../../../images/top/menu_line.gif" width="1" height="52" /></td>
				<%	}
              		
				}%> 
                
                
                <td width="95%" valign="top" background="../../../images/top/top_bg_03.gif" style="padding:12px 0px 0px 0px"><table width="230" border="0" align="right" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="140"><img src="../../../images/top/login_icon.gif" width="21" height="13" /><span class="gray_s_01"><%=SS_M_NAME%>님 반갑습니다.</span></td>
                      <td width="70"><table width="68" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="34"><a href="../../logout.jsp" target="_top"><img src="../../../images/top/login_icon_01.gif" width="34" height="24"></a></td>
                            <td width="34"><a href="../../search/search_env_setting.jsp" target="contentsFrame"><img src="../../../images/top/login_icon_02.gif" width="34" height="24"></a></td>
                          </tr>
                      </table></td>
                    </tr>
                </table></td>
                
              </tr>
            </table></td>
          </tr>
          
        </table></td>
      </tr>
      <tr>
        <td height="11" valign="top"><img src="../../../images/top/left_menu_bg.gif" width="200" height="11" /></td>
        </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
                

<SCRIPT LANGUAGE="JavaScript">
<!--
// 최초 선택된 메뉴를 선택상태로 만든다.
// 선택된 메뉴가 없으면 검색메뉴를 선택상태로 만든다.
//if (selectedObj==""){selectedObj=document.getElementById('img'+<%//selectMenu%>//);  setOn(selectedObj);}
//else{setOn(selectedObj);}

<%
	if(selectedMenu.equals("")){
%>
		if(eval(<%=firstMenu%>+" == '1' || "+<%=firstMenu%>+" == '6'")){			
			selectedObj=document.getElementById('img1');
		} else {
			
			selectedObj=document.getElementById('img'+<%=firstMenu%>);
		}

		setOn(selectedObj);

<%		
	} else {
%>		
	selectedObj=document.getElementById('img'+<%=selectedMenu%>);
	setOn(selectedObj);
<%		
	}
%>

function logOut(){
	
}


//-->
</SCRIPT>
