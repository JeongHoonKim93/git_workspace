<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="risk.util.*,
				 risk.admin.site_type.SiteTypeMng
                 "
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
    ParseRequest    pr = new ParseRequest(request);

	String mode	= pr.getString("mode");
	String sgseq	= pr.getString("sgseq");

	String[] gsn;
	int i;
	StringBuffer sb = new StringBuffer();

	gsn = request.getParameterValues("gsn");

	for(i=0; i< gsn.length;i++)
	{
		if( i != 0) sb.append(",");
		sb.append(gsn[i]);
	}

	SiteTypeMng sitemng = new SiteTypeMng();

	if( mode.equals("ins") ) {
		sitemng.insertSgRelationType(sgseq, sb.toString() );
	} else if( mode.equals("del") ) {
		sitemng.deleteSgRelationType(sgseq, sb.toString() );
	}

%>
<SCRIPT LANGUAGE="JavaScript">
<!--
	//parent.sc_sitemng.location.href = 'ifram_source.jsp';
	//parent.tg_sitemng.location.href = 'ifram_target.jsp';
	parent.source_form.submit();
	parent.target_form.submit();
//-->
</SCRIPT>