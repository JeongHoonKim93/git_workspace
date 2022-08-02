<%@ page contentType="text/html; charset=EUC_KR" %>
<%@ include file="../inc/sessioncheck.jsp" %>  
<%@ page import="risk.util.ParseRequest
				, risk.search.userEnvMgr
                , risk.search.userEnvInfo
                , risk.admin.usergroup.UserGroupMgr
                , risk.admin.usergroup.UserGroupSuperBean
                "
%>
<%
    ParseRequest    pr = new ParseRequest(request);
    if ( pr.getString("INIT").equals("INIT") ) {
        session.setAttribute("INIT","INIT");
    }
    
    //1순위 중메뉴 가져오기~
    userEnvInfo uei = null;
    uei = (userEnvInfo) session.getAttribute("ENV");
    String getUrl = null;
    if(uei != null) {
	    String menus =  uei.getMg_menu();    
	    UserGroupMgr uMgr = new UserGroupMgr();
	    getUrl = uMgr.getSearchTop("1", menus, "3");
%>
	<FRAMESET COLS="200,*" frameborder="0" border="0">
		<FRAME SRC="search_main_left.jsp" NAME="leftFrame" scrolling="no" noresize>
		<FRAME SRC="<%=getUrl%>" NAME="contentsFrame" noresize>
	</FRAMESET>
<%
    }
    
%>

