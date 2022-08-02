<%@page import="risk.search.userEnvInfo"%>
<%@page import="risk.search.userEnvMgr"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	String refresh_time = pr.getString("refresh_time","0");
	String m_no = pr.getString("m_no");
	String mode = pr.getString("mode");
	
	userEnvMgr uEmgr = new userEnvMgr();
	boolean result = false;
	
	userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");
    String sInit = (String)session.getAttribute("INIT");
    
    if ( sInit != null  && sInit.equals("INIT") ) {
        userEnvMgr uem = new userEnvMgr();
        uei = uem.getUserEnv((String)session.getAttribute("SS_M_ID"));
    }
	//설정시간 저장하기
	if("update".equals(mode)){
		uei.setSt_reload_time(refresh_time);
		if(null!=m_no && !"".equals(m_no)){
			result = uEmgr.saveUserReloadTimeEnv(refresh_time, m_no);	
		}
	//설정 시간 가져오기
	}else if("select".equals(mode)){
		refresh_time =  uei.getSt_reload_time();
	}
%>


<%if("update".equals(mode)){%>
	<%=result%>
<%}else if("select".equals(mode)){%>
	<%=refresh_time%>
<%}%>