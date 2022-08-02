<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import = " risk.voc.*,
					risk.util.*,
					risk.mail.*,
					risk.search.*
					"	%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%

	ConfigUtil cu = new ConfigUtil();
	StringUtil su = new StringUtil();
	MailTool mail = new MailTool();
	GoogleSmtp gMail = new GoogleSmtp();
	ParseRequest pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	pr.printParams();

	String v_seq    = pr.getString("v_seq");   
	
	String summary = pr.getString("summary");
	//String memo    = pr.getString("memo");
	
	String nowpage = pr.getString("nowpage");
	
	String mode =  pr.getString("mode");
	
	//String fname = pr.getString("fname");
	//String fOname = pr.getString("fOname");
	
	String send = pr.getString("send");
	String send_name = pr.getString("send_name");
	String resend  = pr.getString("resend");
	String resend_name  = pr.getString("resend_name");
	String sDateFrom   = pr.getString("sDateFrom","");  
	String sDateTo   = pr.getString("sDateTo","");   
	
	String md_title = pr.getString("md_title");
	
	
	VocDataMgr vMgr = new VocDataMgr();

	String ir_html = "";	
	String fromEmail = "";
	String toEmail = "";
	String title = "(VOC처리 재요청)" + su.cutString(md_title, 45, "...");
	String script_str = "";
	
	
	if(vMgr.getAppInsert(v_seq,summary.replaceAll("\n","<br>"),"",mode,send, sDateFrom.replaceAll("-",""), sDateTo.replaceAll("-",""))){
		
		if(mode.equals("reapp")){
			fromEmail = SS_M_MAIL;
			toEmail = vMgr.getmail(resend);
			
			v_seq = java.net.URLEncoder.encode(v_seq, "utf-8");
			sDateFrom = java.net.URLEncoder.encode(sDateFrom, "utf-8");
			sDateTo = java.net.URLEncoder.encode(sDateTo, "utf-8");
			send = java.net.URLEncoder.encode(send, "utf-8");
			send_name = java.net.URLEncoder.encode(send_name, "utf-8");
			resend = java.net.URLEncoder.encode(resend, "utf-8");
			resend_name = java.net.URLEncoder.encode(resend_name, "utf-8");
			summary = java.net.URLEncoder.encode(summary, "utf-8");
			
			String parameter = "v_seq=" + v_seq
							 + "&sDateFrom=" + sDateFrom
	        				 + "&sDateTo=" + sDateTo
			                 + "&send=" + send
			                 + "&send_name=" + send_name
			                 + "&resend=" + resend
			                 + "&resend_name=" + resend_name
			                 + "&summary=" + summary
			                 + "&mode=" + mode;
			
			String reportUrl = cu.getConfig("URL")+"riskv3/voc/mail_voc_pro_data_form.jsp?"+parameter;
			System.out.println(reportUrl);
			
			ir_html = su.encodingRequestPageByPost(reportUrl,"utf-8");
			
			boolean sendResult = false;
			sendResult = gMail.sendmessage(toEmail, fromEmail, title, ir_html, true);
			
			if(sendResult){
				script_str = "parent.document.getElementById('sending').style.display = 'none'; alert('메일이 발송되었습니다.'); parent.opener.document.location.href = 'voc_data_list.jsp?type=2&nowpage="+nowpage+"'; parent.window.close();\n";
			}else{
				script_str = "alert('error'); parent.document.getElementById('sending').style.display = 'none';\n";
			}
		}else{
			script_str = "parent.document.getElementById('sending').style.display = 'none'; alert('저장이 완료 되었습니다.'); parent.opener.document.location.href = 'voc_data_list.jsp?type=2&nowpage="+nowpage+"'; parent.window.close();\n";
		}
	
	}else{
		//script_str += "parent.document.getElementById('div_loding').innerHTML='';  \n";
		script_str += "alert('저장 실패 하였습니다.');  \n";
	}
	
%>

<%@page import="java.util.ArrayList"%><SCRIPT LANGUAGE="JavaScript">
<!--
	<%System.out.println(script_str);%>
	<%=script_str%>
//-->
</SCRIPT>