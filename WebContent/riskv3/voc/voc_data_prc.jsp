<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import = " risk.issue.IssueDataBean,
					risk.voc.*,
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
	
	
	VocDataMgr vMgr = new VocDataMgr();
	
	String v_seq    = pr.getString("v_seq");   
	String sDateFrom   = pr.getString("sDateFrom");  
	String sDateTo   = pr.getString("sDateTo");
	
	
	String send    = pr.getString("send");   
	String send_name    = pr.getString("send_name");   
	//String resend  = pr.getString("resend");
	//String resend_name  = pr.getString("resend_name");
	
	String[] user_val = pr.getStringArr("user_val");
	String[] user_name = pr.getStringArr("user_name");
	
	String summary = pr.getString("summary").replaceAll("\n","<br>");
	
	
	String nowpage = pr.getString("nowpage");
	String mode = pr.getString("mode");
	String md_title = pr.getString("md_title");
	
	//String mt_no = pr.getString("mt_no");
	
	
	String ir_html = "";	
	String fromEmail = "";
	String toEmail = "";
	String title = "(VOC처리요청)" + su.cutString(md_title, 45, "...");
	String script_str = "";
	
	
	if(mode.equals("write")){
		
		
		
		ArrayList copyVoc = new ArrayList(); 
		copyVoc.add(v_seq);
			
		if(user_val != null && user_val.length > 0){
			for(int i =0; i < user_val.length - 1 ; i++){
				copyVoc.add(vMgr.CopyVoc(v_seq));
			}
			
			if(copyVoc.size() == user_val.length){
			
				for(int i =0; i < copyVoc.size(); i++){
					
					if(vMgr.getVocDetailInsert((String)copyVoc.get(i),sDateFrom.replaceAll("-",""),sDateTo.replaceAll("-",""),"1",send,user_val[i],summary,"")){
						
						fromEmail = SS_M_MAIL;
						toEmail = vMgr.getmail(user_val[i]);
						
						/*
						copyVoc.set(i, java.net.URLEncoder.encode((String)copyVoc.get(i), "utf-8"));
						sDateFrom = java.net.URLEncoder.encode(sDateFrom, "utf-8");
						sDateTo = java.net.URLEncoder.encode(sDateTo, "utf-8");
						send = java.net.URLEncoder.encode(send, "utf-8");
						send_name = java.net.URLEncoder.encode(send_name, "utf-8");
						user_val[i] = java.net.URLEncoder.encode(user_val[i], "utf-8");
						user_name[i] = java.net.URLEncoder.encode(user_name[i], "utf-8");
						summary = java.net.URLEncoder.encode(summary, "utf-8");
						*/
						
						
						String parameter = "v_seq=" + java.net.URLEncoder.encode((String)copyVoc.get(i), "utf-8")
										 + "&sDateFrom=" + java.net.URLEncoder.encode(sDateFrom, "utf-8")
			            				 + "&sDateTo=" + java.net.URLEncoder.encode(sDateTo, "utf-8")
						                 + "&send=" + java.net.URLEncoder.encode(send, "utf-8")
						                 + "&send_name=" + java.net.URLEncoder.encode(send_name, "utf-8")
						                 + "&resend=" + java.net.URLEncoder.encode(user_val[i], "utf-8")
						                 + "&resend_name=" + java.net.URLEncoder.encode(user_name[i], "utf-8")
						                 + "&summary=" + java.net.URLEncoder.encode(summary, "utf-8");
						
						String reportUrl = cu.getConfig("URL")+"riskv3/voc/mail_voc_data_form.jsp?"+parameter;
						System.out.println(reportUrl);
						
						ir_html = su.encodingRequestPageByPost(reportUrl,"utf-8");
												
						boolean sendResult = false;
						//sendResult = mail.sendMail(toEmail, title, ir_html, fromEmail);
						sendResult = gMail.sendmessage(toEmail, fromEmail, title, ir_html, true);
						
						if(sendResult){
							script_str = "parent.document.getElementById('sending').style.display = 'none'; alert('메일이 발송되었습니다.'); parent.opener.document.location.href = 'voc_data_list.jsp?type=1&nowpage="+nowpage+"'; parent.window.close();\n";
						}else{
							script_str = "alert('error'); parent.document.getElementById('sending').style.display = 'none';\n";
						}
						
					}	
				}
				
			}else{
				script_str = "alert('insert error'); parent.document.getElementById('sending').style.display = 'none';\n";
			}
			
		}
		
	
		
		
		
	}else if(mode.equals("delete")){
		vMgr.getVocDelete(v_seq);
		script_str = "parent.document.location.href = 'voc_data_list.jsp?nowpage="+nowpage+"';\n";
	}
	
%>

<%@page import="java.util.ArrayList"%><SCRIPT LANGUAGE="JavaScript">
<!--
	<%System.out.println(script_str);%>
	<%=script_str%>
//-->
</SCRIPT>