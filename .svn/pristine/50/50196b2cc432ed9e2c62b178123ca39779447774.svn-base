<%@page import="com.sun.org.apache.xerces.internal.util.URI"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="risk.util.*
				,risk.mail.*
				,risk.issue.*
				,risk.search.*
				,risk.admin.log.*
				,java.util.ArrayList
				,java.net.*
				,risk.sms.AddressBookDao
                ,risk.sms.AddressBookBean"           
%>
<%@ include file="../inc/sessioncheck.jsp" %>    
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	DateUtil du = new DateUtil();
	GoogleSmtp gMail = new GoogleSmtp();
	ConfigUtil cu = new ConfigUtil();
	StringUtil su = new StringUtil();
	MetaMgr mtmgr = new MetaMgr();
	LogMgr logMgr = new LogMgr();
	IssueReportMgr irMgr = new IssueReportMgr();
	LogBean logBean  = new LogBean();
	AddressBookDao abDao = new AddressBookDao();
	
	ArrayList arrABBean = null;
	IssueReportBean irBean = null;
	AddressBookBean abBean = null;
	String script_str = null;
	boolean sendResult = false;
	
	String mode = pr.getString("mode");	
	String md_seq = pr.getString("md_seq");
	String md_seq2 = pr.getString("md_seq2","");
	
	//메일 발송 관련
	String m_seq = SS_M_ID; 				              //등록자
	String sendMailUser = SS_M_MAIL;			          //보낸이
	String mailreceiver = pr.getString("mailreceiver");	  //보낼 수신자 번호
	
	//보고서 관련
	String ir_html = "";
	
	if(mode.equals("important_check") || mode.equals("important_check2")){
		
		String important_val = pr.getString("important_val");
		String mode2 = pr.getString("mode2","");
		mtmgr.updateImportant(md_seq,important_val,mode2);
		if(md_seq2 != ""){
			mtmgr.updateImportant(md_seq2,important_val,"PORTAL_SEARCH_");
		}
		
	}else if(mode.equals("news_mail")){
		
		String[] md_title_temp = pr.getString("md_title").split("@[$][$]@");
		
		String md_title = pr.getString("md_title");
		String md_site = pr.getString("md_site");
		String md_date = pr.getString("md_date");
		String md_url = pr.getString("md_url");
		//String md_important = pr.getString("md_important");
		String contentArea = pr.getString("contentArea");
		String urlArea = pr.getString("urlArea");
		
		//리포트 저장후 로그 저장
		irBean = new IssueReportBean();
		logBean = new LogBean();
		
		//리포트 html통신으로 그리기
		//ir_html = su.encodingRequestPageByGet(SS_URL+"riskv3/report/meta_report_form.jsp?md_seq="+md_seq+"&mr_type="+ReportTypeConstants.getShareVal()+"&md_title="+md_title+"&md_site="+md_site+"&md_date="+md_date+"&md_url="+md_url+"&md_important="+md_important+"&contentArea="+contentArea+"&urlArea="+urlArea,"UTF-8");

		
 		irBean.setIr_title("기사공유("+du.getCurrentDate("yy/MM/dd")+") - "+md_title_temp[0].replaceAll("'",""));
		//irMgr에서 리포트 그리기
		ir_html = irMgr.newsReportHtml(md_seq, md_title, md_site, md_date, md_url, contentArea, urlArea);
		irBean.setIr_html(su.dbString(ir_html));
		irBean.setIr_memo("");
		irBean.setIr_type(ReportTypeConstants.getShareVal());
		irBean.setIr_mailyn("Y");
		irBean.setIr_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));		
				
		logBean.setKey(irMgr.insertReport(irBean)); //리포트 저장후 리포트 번호 셋팅
		logBean.setL_kinds(LogConstants.getReportKindsVal());
		logBean.setL_type(LogConstants.getMailTypeVal());
		logBean.setL_ip(request.getRemoteAddr());
		logBean.setM_seq(SS_M_NO);
		logBean.setL_seq(logMgr.insertLog(logBean)); //로그 저장후 로그 번호 셋팅
		
	 	//수신자 정보 
		arrABBean = new ArrayList();
		arrABBean = abDao.getAddressBook(mailreceiver);		
			
		//String receipt = cu.getConfig("URL") + "riskv3/report/report_receipt.jsp";
		String receipt = cu.getConfig("URL") + "riskv3/report/report_receipt_group.jsp";
		
		  
		//메일발송
		if(arrABBean.size()>0){			
			
			for( int i=0 ; i <arrABBean.size() ; i++ )
			{			
				abBean = (AddressBookBean)arrABBean.get(i);							
				if(sendMailUser.length()>0){
					/* sendResult = gMail.sendmessage(abBean.getMab_mail(), sendMailUser, su.cutString(irBean.getIr_title(), 45, "..."), ir_html.replaceAll("report_receipt", receipt + "?info=" + logBean.getL_seq() + "," + abBean.getMab_seq()), true); */	
					sendResult = gMail.sendmessage(abBean.getMab_mail(), sendMailUser, su.cutString(irBean.getIr_title(), 45, "..."), ir_html.replaceAll("report_receipt", receipt + "?info=" + logBean.getL_seq() + "," + abBean.getMag_seq()), true);
				}else{
					/* sendResult = gMail.sendmessage(abBean.getMab_mail(), su.cutString(irBean.getIr_title(), 45, "..."), ir_html.replaceAll("report_receipt", receipt + "?info=" + logBean.getL_seq() + "," + abBean.getMab_seq()), true); */
					sendResult = gMail.sendmessage(abBean.getMab_mail(), su.cutString(irBean.getIr_title(), 45, "..."), ir_html.replaceAll("report_receipt", receipt + "?info=" + logBean.getL_seq() + "," + abBean.getMag_seq()), true);
				}					
			}
			//메일 수신자 로그
			//logMgr.insertLogReceiver(logBean.getL_seq(),mailreceiver.split(","));
			logMgr.insertLogReceiverGroup(logBean.getL_seq(),mailreceiver.split(","));
			
		}
		if(sendResult){
			script_str = "parent.document.getElementById('sending').style.display = 'none'; alert('메일수신자에게 메일을 발송하였습니다.'); parent.window.close(); \n";
		}else{
			irBean.setIr_mailyn("F");
			irMgr.updateMailYN(irBean);
			script_str = "parent.document.getElementById('sending').style.display = 'none'; alert('메일발송에 실패하였습니다.');\n";
		}
		
	}
	

%>
<script language="JavaScript" type="text/JavaScript">

<%-- parent.openSameList('<%=issue_Check%>','<%=sMtPno%>','<%=sMtno%>','<%=searchMode%>'); --%>
</script>