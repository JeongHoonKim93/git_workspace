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
    String init =  pr.getString("INIT");
    String rtnpage =  pr.getString("rtnpage");
    
    if (init.equals("INIT") ) {
        session.setAttribute("INIT","INIT");
    }
    
    /*
    if(rtnpage.equals("")) {
    	rtnpage = "statistics_analysis.jsp";
    }
    */
    
  	//1순위 중메뉴 가져오기~
    userEnvInfo uei = null;
    uei = (userEnvInfo) session.getAttribute("ENV");
    String menus =  uei.getMg_menu();    
    UserGroupMgr uMgr = new UserGroupMgr();
    String getUrl = uMgr.getSearchTop("4", menus);
%>
<FRAMESET COLS="192,*"  frameborder="0" border="0">
	<FRAME SRC="statistics_left_menu.jsp" NAME="leftFrame" scrolling="no" noresize>
	<FRAME SRC="<%=getUrl%>" NAME="contentsFrame" noresize>
</FRAMESET>