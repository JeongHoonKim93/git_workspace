<%@page import="risk.search.MetaMgr_warning"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="risk.search.GetKGMenu
                 ,risk.util.DateUtil
                 ,risk.util.ParseRequest
                 ,risk.util.StringUtil
                 ,risk.search.MetaMgr
                 ,risk.search.MetaMgr_warning
                 ,risk.search.WarningMgr
                 ,risk.search.DomainKeywordMgr
                 ,risk.search.userEnvInfo
                 ,risk.search.userEnvMgr
                 ,risk.util.ConfigUtil"
				
%>
<%@include file="../inc/sessioncheck.jsp" %>
<%
	//사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.
    userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");
    ConfigUtil cu = new ConfigUtil(); 
    String showCount=cu.getConfig("ShowKeywrodCount");
    
    if ( uei.getK_xp().equals("0")) uei.setK_xp("");   	        
    if ( uei.getK_yp().equals("0")) uei.setK_yp("");
    if ( uei.getK_yp().equals("0")) uei.setK_zp("");

    DateUtil        du = new DateUtil();
    StringUtil      su = new StringUtil();
    ParseRequest    pr = new ParseRequest(request);
   // pr.printParams();
    MetaMgr       sMgr = new MetaMgr();
    WarningMgr       WMgr2= new WarningMgr();
    DomainKeywordMgr       dkMgr= new DomainKeywordMgr();
    userEnvInfo env = (userEnvInfo)session.getAttribute("ENV");
    String sideMenu = pr.getString("menu", "ALLKEY");
    String stName = "";
    
    int xp = Integer.parseInt(su.nvl(uei.getK_xp(),"0"));  
	int yp = Integer.parseInt(su.nvl(uei.getK_yp(),"0"));  
	int zp = Integer.parseInt(su.nvl(uei.getK_zp(),"0")); 
    
    GetKGMenu kg = new GetKGMenu();
    kg.setSelected(xp,yp,zp);
    kg.setBaseTarget("top.bottomFrame.contentsFrame.document");
    
    
    if(sideMenu.equals("ALLKEY")){
    	stName = "";
    	kg.setBaseURL("search_main_contents.jsp?searchmode=ALLKEY");
    }else if(sideMenu.equals("EX_ALLKEY")){
    	stName = "EXCEPTION_";
    	kg.setBaseURL("search_main_exception.jsp?searchmode=EX_ALLKEY");
    }else if(sideMenu.equals("PORTALKEY")){
    	stName = "";
    	kg.setBaseURL("search_main_portalKeyword.jsp");
    }else if(sideMenu.equals("NEWS")){
    	stName = "PORTAL_SEARCH_";
    	kg.setBaseURL("search_news_contents.jsp?searchmode=ALLKEY");
    }else if(sideMenu.equals("WARNING")){
    	stName = "WARNING";
    	kg.setBaseURL("warning_main_contents.jsp?searchmode=ALLKEY");
    }else if(sideMenu.equals("DOMAIN")){
    	stName = "DOMAIN";
    	kg.setBaseURL("domain_main_contents.jsp?searchmode=ALLKEY");
    }
    //String IDXcnt = sMgr.getIdxDelCNT(SS_M_NO,stName);
    
	String sCurrentDate = du.getCurrentDate("yyyy-MM-dd");
	
    //if ( uei.getDateFrom()==null) uei.setDateFrom(sCurrentDate);
    //if ( uei.getDateTo()==null) uei.setDateTo(sCurrentDate);
    
		
	
	String kgHtml="";
	System.out.println(">>>>>>>"+stName);
	
	if(stName.equals("PORTAL_SEARCH_")){
		kgHtml   = kg.GetHtml2( sCurrentDate, sCurrentDate, sMgr.getPortalSearchKXP(), showCount, stName);
	}else if(stName.equals("WARNING")){
		kgHtml   = kg.GetHtml4( sCurrentDate, sCurrentDate, WMgr2.getWarningKXP(), showCount, stName);
	}else if(stName.equals("DOMAIN")){
		kgHtml   = kg.GetHtml3( sCurrentDate, sCurrentDate, uei.getMg_xp(), showCount, stName);
	}else {
		kgHtml   = kg.GetHtml( sCurrentDate, sCurrentDate, uei.getMg_xp(), showCount, stName);
	}
    
	String kgScript = kg.GetScript();
	String kgStyle  = kg.GetStyle();
%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
<!--
	function delete_all(){
		
		if(confirm('휴지통의 정보를 모두 삭제하시겠습니까? \n 삭제된 정보는 복구할 수 없습니다.')){
			
			top.bottomFrame.contentsFrame.idxProcess('delAll');
			
		}else{
			return;
		}
	}

-->
</script>
<link rel="stylesheet" href="../css/basic.css" type="text/css">
<style>
<!--
-->
</style>
<%
	out.println(kgStyle);
	out.println(kgScript);
%>
<body bgcolor="#F1F1F1" leftmargin="5" topmargin="4" >
<form name="leftmenu">
<table style="width:100%;table-layout: fixed;">

<%
    out.println(kgHtml);
%>
</table>
<%
	boolean menuCheck = false;
	String  infoMsg = "";	
	String[] mg_menu = env.getMg_menu().split(",");
	
	for(int i=0; i<mg_menu.length; i++){
		if(mg_menu[i].equals("2") || mg_menu[i].equals("3") || mg_menu[i].equals("10")){
			menuCheck = true;
			//워닝키워드일 경우는 안내문 안보이게
		/* 	if(mg_menu[i].equals("10")){
				infoMsg =  "none";
				
			} */
			
		}
	}
%>


<%-- 
<%
	if(menuCheck){
%>
<div style="width:100%;">
<span style="width:10%;">
<% if(stName.equals("PORTAL_SEARCH_")){ %>
<img class="kgmenu_img" src="../../images/search/del_ico.gif" onClick="parent.top.bottomFrame.contentsFrame.document.location ='search_news_contents.jsp?searchmode=DELIDX&xp=0&yp=0&zp=0';" border="0" align=absmiddle>
</span>
<span style="width:60%;" class="kgmenu" id="imgID_0_0_0" onclick="parent.top.bottomFrame.contentsFrame.document.location ='search_news_contents.jsp?searchmode=DELIDX&xp=0&yp=0&zp=0';kg_click(this);" onmouseover="kg_over(this);"onmouseout="kg_out(this);"><b>휴지통</b> 
<%}else{ %>
<img class="kgmenu_img" src="../../images/search/del_ico.gif" onClick="parent.top.bottomFrame.contentsFrame.document.location ='search_main_contents.jsp?searchmode=DELIDX&xp=0&yp=0&zp=0';" border="0" align=absmiddle>
</span>
<span style="width:60%;" class="kgmenu" id="imgID_0_0_0" onclick="parent.top.bottomFrame.contentsFrame.document.location ='search_main_contents.jsp?searchmode=DELIDX&xp=0&yp=0&zp=0';kg_click(this);" onmouseover="kg_over(this);"onmouseout="kg_out(this);"><b>휴지통</b> 
<%} %>
<span class="kgmenu_cnt">[<%=IDXcnt%>]</span></span>
<span style="width:20%;valign:bottom;padding: 0 0 0 9;" class="kgmenu_cnt">
	<a style="cursor:hand;" onclick="delete_all();"><img src="../../images/search/row_btn.gif" ></a>
</span>
</div>

<%} %>
--%>

 <br>
<table style="display: <%=infoMsg%>">
   <tr>
     <td style="font-size:11px; color:#666666; padding-left:0px; padding-bottom:10px;">* 키워드별 카운트는 오늘 날짜의 유사기사를 포함한 수치입니다.</td>
   </tr>      
</table>   
</form>
</body>
</html>