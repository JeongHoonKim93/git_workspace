<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="risk.util.*,
				 risk.admin.site.SiteMng
                 "
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
String html = "";
try{
    ParseRequest    pr = new ParseRequest(request);

	String mode	= pr.getString("mode");
	String sgseq = pr.getString("sgseq");
	String sgval = pr.getString("sgval");

	

	SiteTypeMng sitemng = new SiteTypeMng();

	if( mode.equals("ins") ) {
		
		sitemng.insertSiteGroupType(sgval);
		html = "parent.opener.source_form.submit();parent.opener.target_form.submit();parent.close();";
	} else if( mode.equals("edit") ) {
		sitemng.updateSiteGroupType(Integer.parseInt(sgseq), sgval);
		html = "parent.opener.parent.source_form.submit();parent.opener.parent.target_form.submit();parent.close();";
	} else if( mode.equals("del") ) {
		sitemng.deleteSiteGroupType(Integer.parseInt(sgseq));
		html = "parent.source_form.submit();parent.target_form.submit();";
	}
}catch(Exception e){System.out.println("error ifram_target_prc line 26 : "+e);}
%>

<%@page import="risk.admin.site_type.SiteTypeMng"%><SCRIPT LANGUAGE="JavaScript">
<!--
<%=html%>
//-->
</SCRIPT>