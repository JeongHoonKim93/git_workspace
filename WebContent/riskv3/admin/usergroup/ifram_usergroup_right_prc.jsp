<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>

<%@ page import="risk.util.*,
				 java.util.*,
				 risk.admin.membergroup.membergroupMng
				 ,risk.search.userEnvInfo
                 ,risk.search.userEnvMgr
				 "%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	String mgseq = null;
	String[] mg_menu = null;
	String[] mg_kg = null;
	String[] mg_site = null;
	String[] mg_company = null;
	String menuset = "";
	String xpset = "";
	String siteset = "";
	String companyset = "";
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");

	StringUtil su = new StringUtil();
	membergroupMng mgmng = membergroupMng.getInstance();

	ArrayList subMenu = new ArrayList();
	if( request.getParameter("mg_menu") != null ) {
		mg_menu = request.getParameterValues("mg_menu");
		
		
		
		
		String menuIdx = pr.getString("menuIdx");
		String[] ar_menu = menuIdx.split(",");
		
		for(int i =0; i < ar_menu.length; i++){
			subMenu.add(request.getParameterValues("mg_menu2_" + ar_menu[i]));
		}
		
		String SubMenus = "";
		for(int i =0; i < subMenu.size(); i++){
			
			if(SubMenus.equals("")){
				
				if(subMenu.get(i) != null){
					SubMenus = su.dissplit((String[])subMenu.get(i), ",");	
				}
				
			}else{
				if(subMenu.get(i) != null){
					SubMenus += "," + su.dissplit((String[])subMenu.get(i), ",");	
				}
			}
		}
		
		menuset = su.dissplit(mg_menu, ",");
		
		if(menuset.equals("")){
			menuset = SubMenus;
		}else{
			menuset += "," + SubMenus;
		}
		
		
		
		
	}
	if( request.getParameter("mg_kg") != null ) {
		mg_kg = request.getParameterValues("mg_kg");
		xpset = su.dissplit(mg_kg, ",");
	}
	if( request.getParameter("mg_site") != null ) {
		mg_site = request.getParameterValues("mg_site");
		siteset = su.dissplit(mg_site, ",");
	}
	if( request.getParameter("mg_company") != null ) {
		mg_company = request.getParameterValues("mg_company");
		companyset = su.dissplit(mg_company, ",");
	}
	if( request.getParameter("mgseq") != null ) {
		mgseq = request.getParameter("mgseq");
		mgmng.updateMGset(mgseq, menuset, xpset, siteset, companyset); 
	}
	//유저 환경정보 다시 세션에 세팅
	if(uei!=null && uei.getMg_seq().equals(mgseq)){ // 내가 속한 그룹이면...
		uei.setMg_menu(menuset);
		uei.setMg_company(companyset);
		session.setAttribute("ENV", uei);
	}

%>
<SCRIPT LANGUAGE="JavaScript">
<!--
	location.href = 'ifram_usergroup_right.jsp?mgseq='+<%=mgseq%>;

	
	<%	if(uei!=null && uei.getMg_seq().equals(mgseq)){%> // 내가 속한 그룹이면...
	<%		if(su.inarray(mg_menu,"5")){ %>
				top.topFrame.location = '../../inc/topmenu/inc_topmenu.jsp?selectedMenu=5';  
	<%		} else {%>
				top.location = '../../main.jsp';
	<%		}%>
	<%	}%>
//-->
</SCRIPT>