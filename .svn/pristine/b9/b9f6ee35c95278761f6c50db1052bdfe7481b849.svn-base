<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import = "risk.util.*
                   ,java.util.ArrayList
                   , risk.statistics.StatisticsTwitterMgr
				   , risk.statistics.StatisticsTwitterSuperBean
                    "
%>                   
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	DateUtil   du = new DateUtil();
	ParseRequest  pr = new ParseRequest(request);	
	StringUtil su = new StringUtil();
	ConfigUtil cu = new ConfigUtil();
	
	String mode = pr.getString("mode","insert");
	
	StatisticsTwitterMgr tMgr = new StatisticsTwitterMgr();
	StatisticsTwitterSuperBean superBean = new StatisticsTwitterSuperBean();   
	StatisticsTwitterSuperBean.ImportTwitterBean childBean = superBean.new ImportTwitterBean(); 
	
	childBean.setT_user_id(pr.getString("user_id",""));
	childBean.setT_name(pr.getString("user_name",""));
	childBean.setT_job(pr.getString("input_job",""));
	childBean.setT_mail(pr.getString("input_mail",""));
	childBean.setT_memo(pr.getString("memo",""));
	childBean.setT_import(pr.getString("input_import",""));
	childBean.setT_profile_image(pr.getString("profile_url",""));
	
	
	String idx = pr.getString("idx","");
	
	String script_str = "";
	int result = 0;
	if(mode.equals("insert")){
		result = tMgr.insertImportTwitter(childBean);
		
		if(result > 0 ){
			script_str = "var obj = parent.opener.document.getElementById('inter_"+idx+"'); \n obj.src='../../../images/statistics/icon_001.gif';\n   parent.close(); ";
		}else{
			script_str = "alert(저장 실패 하였습니다.);";
		}
	}else if(mode.equals("update")){
		result = tMgr.UpdateImportTwitter(childBean);
		if(result > 0 ){
			script_str = "parent.close(); ";
		}else{
			script_str = "alert(저장 실패 하였습니다.);";
		}
	}else if(mode.equals("delete")){
		tMgr.DeleteInterest(pr.getString("check_no"));
			script_str = "parent.document.location.reload();";
	}
	
	
	
	
	
%>
<script language="JavaScript" type="text/JavaScript">
<%=script_str%>
</script>
