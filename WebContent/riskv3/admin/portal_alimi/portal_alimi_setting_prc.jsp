<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
                 	,risk.mobile.PortalAlimiSettingBean                
					,risk.mobile.PortalAlimiSettingDao
					,risk.util.ParseRequest
					,risk.admin.site.SitegroupBean
				 	,risk.admin.site.SiteMng
				 	,risk.search.userEnvMgr
				 	,risk.search.keywordInfo
				 	,risk.admin.classification.classificationMgr
				 	,risk.admin.classification.clfBean
				 	,java.util.List
					" 
%>
<%	

	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	PortalAlimiSettingBean pasBean = new PortalAlimiSettingBean();
	PortalAlimiSettingDao pasDao = new PortalAlimiSettingDao();	
	
	int result = 0;
	String mode = null;
	String nowpage = null;
	String pas_seq = null;
	String pas_seqs = null;
	String pas_title = null;
	String pas_chk = null;
	String pas_type = null;
	String pas_sms_time = null;
	String ab_seqs = null;	
	String pas_tran_num = "";

	mode = pr.getString("mode");
	nowpage = pr.getString("nowpage","1");
	pas_seq = pr.getString("pas_seq");
	pas_seqs = pr.getString("pas_seqs");
	pas_title = pr.getString("pas_title");
	pas_chk = pr.getString("pas_chk");
	pas_type = pr.getString("pas_type");
	pas_sms_time = pr.getString("pas_sms_time");
	ab_seqs = pr.getString("ab_seqs");
	pas_tran_num = pr.getString("pas_tran_num");
	
	pasBean = new PortalAlimiSettingBean();
	pasBean.setPas_seq(pas_seq);
	pasBean.setPas_title(pas_title);	
	pasBean.setPas_chk(pas_chk);
	pasBean.setPas_type(pas_type);
	pasBean.setPas_sms_time(pas_sms_time);
	pasBean.setPas_tran_num(pas_tran_num.replaceAll("-", "").replaceAll(" ", "").replaceAll("\\.", "").replaceAll("/", ""));
		
	if(mode.equals("INSERT"))
	{		
		result = pasDao.insertAlimisSet(pasBean , ab_seqs);
		
	}else if(mode.equals("UPDATE")){
		
		result = pasDao.updateReportSet(pasBean , ab_seqs);
	}else if(mode.equals("DELETE")){
		result = pasDao.deletePortalAlimiSet(pas_seqs);
	}
%>
<script language="javascript">
<!--
	parent.contentsFrame.location.href = 'portal_alimi_setting_list.jsp?nowpage=<%=nowpage%>';
//-->
</script>