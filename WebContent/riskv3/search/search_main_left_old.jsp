<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="risk.util.Log,
                 risk.util.StringUtil,
                 risk.util.ParseRequest,
                 risk.search.userEnvMgr,
                 risk.search.userEnvInfo,
                 risk.search.GetKGMenu
                 "
%>
<%@include file="../inc/sessioncheck.jsp" %>
<%

    ParseRequest pr = new ParseRequest(request);
	GetKGMenu kg = new GetKGMenu();

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

%>

<html>
<head>
<title><%=SS_TITLE%></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/basic.css" type="text/css">
<style>
	<!--
	td { font-size:12px; color:#333333; font-family:"gulim"; ; line-height: 18px}
	input { font-size:11px; border:1x solid #CFCFCF; height:18px; color:#767676; }
	.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}
    .tCopy { font-family: "Tahoma"; font-size: 12px; color: #000000; font-weight: bold}
	a:link { color: #333333; text-decoration: none; }
	a:visited { text-decoration: none; color: #000000; }
	a:hover { text-decoration: none; color: #FF9900; }
	a:active { text-decoration: none; color: #ffffff; }
	.t_gray {font-family: Tahoma, "돋움", "돋움체"; font-size: 11px; color: #666666;}
	.inputnew {BORDER-RIGHT: #b4b4b4 1px solid; BORDER-TOP: #b4b4b4 1px solid; BORDER-LEFT: #b4b4b4 1px solid; BORDER-BOTTOM: #b4b4b4 1px solid; COLOR: #767676;  HEIGHT: 19px; FONT-SIZE: 11px; PADDING-TOP: 3px; BACKGROUND-COLOR: #ffffff}
	-->
	</style>
<script language="JavaScript" type="text/JavaScript">
<!--
function init() {
		document.all.allkey.style.backgroundColor = '#6AA8F8';
//		document.all.ifr_menu.style.height = document.body.scrollHeight-200;
	}

	var selectedObj = "";
	
    //검색 모드 변경
    function chageSearchMode( value, obj ) {
        
        	selectedObj = obj;
        	
        if ( value == "ALLKEY" ) {
        	add = '&xp=0&yp=0&zp=0';
        	
            top.bottomFrame.contentsFrame.document.location ='search_main_contents.jsp?searchmode=' + value + add ;
            document.all.allkey.background = '../../images/left/top_left_onbg.gif';
			document.all.solrkey.background = '../../images/left/top_left_offbg.gif';
			document.all.allkeytext.style.color='#ffffff';
			document.all.solrkeytext.style.color='#000000';
            

            document.all.portalkey.background = '../../images/left/top_left_offbg.gif';
			document.all.portalkeytext.style.color='#000000';

			document.all.ex_allkey.background = '../../images/left/top_left_offbg.gif';
			document.all.ex_allkeytext.style.color='#000000';

			ifr_menu.location = "inc_keyword_menu.jsp?menu=" + value + add;
			
			
        }else if ( value == "EX_ALLKEY" ) {
        	add = '&xp=0&yp=0&zp=0';
        	
            top.bottomFrame.contentsFrame.document.location ='search_main_exception.jsp?searchmode=' + value + add ;
            document.all.ex_allkey.background = '../../images/left/top_left_onbg.gif';
			document.all.solrkey.background = '../../images/left/top_left_offbg.gif';
			document.all.ex_allkeytext.style.color='#ffffff';
			document.all.solrkeytext.style.color='#000000';
            

            document.all.portalkey.background = '../../images/left/top_left_offbg.gif';
			document.all.portalkeytext.style.color='#000000';

			document.all.allkey.background = '../../images/left/top_left_offbg.gif';
			document.all.allkeytext.style.color='#000000';

			document.getElementById("ifr_menu").style.display = '';
			ifr_menu.location = "inc_keyword_menu.jsp?menu=" + value + add;
			
			
        }else if( value == "PORTAL" ) {
        	add = '&xp=0&yp=0&zp=0';
            
			top.bottomFrame.contentsFrame.document.location ='search_main_top.jsp?searchmode=' + value + add ;			
			document.all.allkey.background = '../../images/left/top_left_offbg.gif';
			document.all.solrkey.background = '../../images/left/top_left_offbg.gif';

			document.all.portalkey.background = '../../images/left/top_left_onbg.gif';
			document.all.portalkeytext.style.color='#ffffff';
			
			document.all.allkeytext.style.color='#000000';
			document.all.solrkeytext.style.color='#000000';
			//document.all.portalkeytext.style.color='#ffffff';
			
			document.all.ex_allkey.background = '../../images/left/top_left_offbg.gif';
			document.all.ex_allkeytext.style.color='#000000';
			
			//ifr_menu.location = "inc_keyword_menu.jsp?menu=PORTAL";
			document.getElementById("ifr_menu").style.display = 'none';
			
			
			
		}else if( value == "SOLR"){
	        top.bottomFrame.contentsFrame.document.location ='solr/search_main_contents_solr.jsp';
	        //obj.style.backgroundColor='398AAA';
	        //document.all.allkey.style.backgroundColor='6F7373';
			document.all.allkey.background = '../../images/left/top_left_offbg.gif';
			//document.all.alldb.background = '../../images/left/top_left_offbg.gif';
			document.all.solrkey.background = '../../images/left/top_left_onbg.gif';
//			document.all.portalkey.background = '../../images/left/top_left_offbg.gif';
			
			//document.all.alldbtext.style.color='#000000';
			document.all.allkeytext.style.color='#000000';
			document.all.solrkeytext.style.color='#ffffff';
			//document.all.portalkeytext.style.color='#000000';
			//ifr_menu.location = "inc_keyword_menu.jsp?xp=0&yp=0&zp=0";
			
			document.all.portalkey.background = '../../images/left/top_left_offbg.gif';
			document.all.portalkeytext.style.color='#000000';

			document.all.ex_allkey.background = '../../images/left/top_left_offbg.gif';
			document.all.ex_allkeytext.style.color='#000000';
			
			//ifr_menu.location = "inc_keyword_menu.jsp?menu=SOLR";
			document.getElementById("ifr_menu").style.display = 'none';

			
		} else{
			add = '&xp=0&yp=0&zp=0';
	        top.bottomFrame.contentsFrame.document.location ='search_main_contents.jsp?searchmode=' + value + add ;
		}
		

    }
    
    function saveEnv()
	{		
		if(parent.contentsFrame.env_save){
			parent.contentsFrame.env_save();
		}
	}

	function igList_resize(){
  		var ch = document.body.clientHeight;
  		var ifmObj = document.all.ifr_menu.style;
		var newH = ch - 160;
		
		//var newH = 400;
  		if (newH <100) newH = 100;
  		ifmObj.height = newH;
  	}

	function openLayer()
	{
		document.getElementById("whatkeyword").style.display='';
	}
	function closeLayer()
	{
		document.getElementById("whatkeyword").style.display='none';
	}
	function onMs(obj){
		obj.background='../../images/left/top_left_onbg.gif';
	}
	function outMs(obj){
		obj.background='../../images/left/top_left_offbg.gif';
		selectedObj.background='../../images/left/top_left_onbg.gif';
	}

	function twitterSearch(){
		TForm.query.value = document.all.twitterKeyword.value;
		TForm.submit();
	}

//-->
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="igList_resize();" onresize="igList_resize();">
<form name="TForm" action="../twitter/twitterRolling.jsp" method="post" target="ifr_twitter">
<input type="hidden" name="query">
<input type="hidden" name="menu">
</form>
<!-- 키워드 배너 -->
<div id="whatkeyword" style="width:328px;height:168px; top:45px; left=555px; cellspacing:0; cellpadding:0; position: absolute;border:black 0px solid; display:none;">  
<img src="../../images/left/layer_img.gif" style="cursor:hand" onclick="closeLayer();"></img>
</div>
<table width="300" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
<!--    <td align="right" valign="top" background="../../images/left/top_left_mbg.gif">-->
    <td align="right" valign="top">
    <table width="295" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="../../images/left/left_title01.gif" ><!--/// LEFT MENU TITLE
		//// 이슈관리 타이틀 <img src="../../images/left/left_title02.gif" width="205" height="52">
		//// 관리자 타이틀<img src="../../images/left/left_title03.gif" width="205" height="52">
		//// 알리미 타이틀<img src="../../images/left/left_title04.gif" width="205" height="52"> ////--></td>
      </tr>
      <tr>
        <td bgcolor="#FFFFFF"><img src="../../images/left/brank.gif" width="1" height="4"></td>
      </tr>
    </table>
		<!--
		  <table width="205" height="34" border="0" cellpadding="0" cellspacing="0">
        
         
        <tr>
          <td id="alldb" align="right" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif">
          <table width="190" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico01.gif" width="16" height="15"></td>
              <td class="leftM_off" style="padding: 2px 0px 0px 3px; font-weight: bold;"><a  id="alldbtext" style="color:000000;" href="javascript:chageSearchMode('ALLDB');">전체DB검색</a></td>
            </tr>
          </table></td>
        </tr>
        
      </table> -->
      
      
      
      
      <table width="295" height="34" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td id="solrkey" align="right" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);" style="padding: 0px 5px 0px 0px;"><table width="280" border="0" cellspacing="0" cellpadding="0">
              <tr >
                <td   width="20"><img src="../../images/left/left_mico02.gif" width="16" height="15"></td>
                <td class="leftM_on"  style="padding: 2px 0px 0px 3px; font-weight: bold;"><a id="solrkeytext" style="color:000000;" href="javascript:chageSearchMode('SOLR',document.all.solrkey);">전체검색</a></td>
              </tr>
          </table></td>
        </tr>
      </table>
      
      <table width="295" height="34" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td id="portalkey" align="right" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);" style="padding: 0px 5px 0px 0px;"><table width="280" border="0" cellspacing="0" cellpadding="0">
              <tr >
                <td   width="20"><img src="../../images/left/left_mico02.gif" width="16" height="15"></td>
                <td class="leftM_on"  style="padding: 2px 0px 0px 3px; font-weight: bold;"><a id="portalkeytext" style="color:000000;" href="javascript:chageSearchMode('PORTAL',document.all.portalkey);">포탈검색</a></td>
              </tr>
          </table></td>
        </tr>
      </table>
      
       
      <%--
      <table width="205" height="34" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td id="portalkey" align="right" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);" style="padding: 0px 5px 0px 0px;"><table width="190" border="0" cellspacing="0" cellpadding="0">
              <tr >
                <td   width="20"><img src="../../images/left/left_mico02.gif" width="16" height="15"></td>
                <td class="leftM_on"  style="padding: 2px 0px 0px 3px; font-weight: bold;"><a id="portalkeytext" style="color:000000;" href="javascript:chageSearchMode('PORTAL',document.all.portalkey);" onfocus="this.blur()">네이버-SAMSUNG</a></td>
              </tr>
          </table></td>
        </tr>
      </table>
      --%>
      
       <table width="295" height="34" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td id="allkey" align="right" background="../../images/left/top_left_onbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);" style="padding: 0px 5px 0px 0px;"><table width="280" border="0" cellspacing="0" cellpadding="0">
              <tr >
                <td   width="20"><img src="../../images/left/left_mico02.gif" width="16" height="15"></td>
                <td class="leftM_on"  style="padding: 2px 0px 0px 3px; font-weight: bold;"><a id="allkeytext" style="color:ffffff;" href="javascript:chageSearchMode('ALLKEY',document.all.allkey);">전체키워드검색</a></td>
              </tr>
          </table></td>
        </tr>
      </table> 
      
      
      <table width="295" height="34" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td id="ex_allkey" align="right" background="../../images/left/top_left_offbg.gif" onMouseover="onMs(this);" onmouseout="outMs(this);" style="padding: 0px 5px 0px 0px;"><table width="280" border="0" cellspacing="0" cellpadding="0">
              <tr >
                <td   width="20"><img src="../../images/left/left_mico02.gif" width="16" height="15"></td>
                <td class="leftM_on"  style="padding: 2px 0px 0px 3px; font-weight: bold;"><a id="ex_allkeytext" style="color:000000;" href="javascript:chageSearchMode('EX_ALLKEY',document.all.ex_allkey);">제외키워드검색</a></td>
              </tr>
          </table></td>
        </tr>
      </table>
      
   	
      
      
   	  <table id="keymeun" width="294" border="0" cellspacing="0" cellpadding="1">
        <tr>
          <td width="294"  align="right" valign="top">
          <iframe name="ifr_menu"  id="ifr_menu" src="inc_keyword_menu.jsp" frameborder="0" border="0" style="width:294px;"></iframe>
          </td>
        </tr>
      </table>
	

</body>
</html>
<script>

if (selectedObj==""){
	
	selectedObj=document.all.allkey;
	selectedObj.background='../../images/left/top_left_onbg.gif';
}

</script>
