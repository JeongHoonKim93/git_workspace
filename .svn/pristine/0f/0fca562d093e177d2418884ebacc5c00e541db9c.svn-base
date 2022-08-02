<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="inc/sessioncheck.jsp" %>
<title><%=SS_TITLE%></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
<%@ page import="risk.util.StringUtil,
				 risk.search.userEnvInfo
                 "
%>

<%
	userEnvInfo env = (userEnvInfo)session.getAttribute("ENV");
	String MGmenu = env.getMg_menu();
	String[] arrMGmenu = null;
	
	String menuPath = "";
	if( MGmenu != null ){
		arrMGmenu = MGmenu.split(",");
	}
		
	if(arrMGmenu != null && arrMGmenu.length > 0){
		if(arrMGmenu[0].equals("1")){
			menuPath = "search/search.jsp?INIT=INIT";
		}else if(arrMGmenu[0].equals("2")){
			menuPath = "issue/issue.jsp";
		}else if(arrMGmenu[0].equals("3")){
			menuPath = "report/report.jsp";
		}else if(arrMGmenu[0].equals("4")){
			menuPath = "statistics/statistics.jsp";
		}else if(arrMGmenu[0].equals("5")){
			menuPath = "admin/admin_main.jsp";
		}else{
			menuPath = "search/search.jsp?INIT=INIT";
		}
	}
%>

<link rel="shortcut icon" href="../images/SearchPlus.ico" />
<FRAMESET COLS="0,*" frameborder="0" border="0">
	<FRAME SRC="inc/left/inc_left_line.jsp" NAME="leftFrame" scrolling="no" noresize>
	<FRAMESET ROWS="62,*" id="mainset" frameborder="0" border="0">
		<FRAME SRC="inc/topmenu/inc_topmenu.jsp" NAME="topFrame" scrolling="no" noresize>
		<FRAME SRC="<%=menuPath %>" NAME="bottomFrame" noresize>
	</FRAMESET>
</FRAMESET>