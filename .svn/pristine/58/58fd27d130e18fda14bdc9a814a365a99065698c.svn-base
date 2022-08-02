<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import = "risk.issue.*
                   ,risk.mail.*
                   ,risk.sms.AddressBookDao
                   ,risk.sms.AddressBookBean
                   ,risk.util.*
                   ,risk.search.*
                   ,risk.admin.log.*
                   ,java.util.ArrayList
                    ,risk.admin.classification.classificationMgr"
%>                   
<%@ include file="../inc/sessioncheck.jsp" %>
<%	

	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	DateUtil du = new DateUtil();
	ConfigUtil cu = new ConfigUtil();
	//MailTool mail = new MailTool();
	GoogleSmtp gMail = new GoogleSmtp();
	StringUtil su = new StringUtil();
	IssueMgr iMgr = new IssueMgr();
	MetaMgr metaMgr = new MetaMgr();
	IssueReportMgr irMgr = new IssueReportMgr();
	LogMgr logMgr = new LogMgr();
	LogBean logBean = new LogBean();
	
	MetaBean mBean = null;
	IssueDataBean idBean = null;
	IssueCommentBean icBean = null;
	IssueReportBean irBean = null;	
	AddressBookDao abDao = new AddressBookDao();
	ArrayList arrABBean = null;
	AddressBookBean abBean = null;
	String typeCode= null;
	String reportHtml= null;
	String script_str = null;
	boolean sendResult = false;
	
	classificationMgr cMgr = new classificationMgr();
	
	String child = pr.getString("child");
	
	//프로세스모드
	String mode = pr.getString("mode");
	
	//이슈정보 검색 조건 유지	
	String nowPage = pr.getString("nowPage");	
	
	
	//코드 정보
	typeCode = pr.getString("typeCode","");

	//메일 발송 관련
	String m_seq = SS_M_ID; 				              //등록자
	String sendMailUser = SS_M_MAIL;			          //보낸이
	String mailreceiver = pr.getString("mailreceiver");	  //보낼 수신자 번호
	
	//보고서 관련
	String ir_html = "";
	
	
	//이슈리스트에서 체크된 관련정보를 긴급보고서로 메일 발송
	if(mode.equals("mail")){
		
		//리포트 저장후 로그 저장
		irBean = new IssueReportBean();
		logBean = new LogBean();
		
		irBean.setIr_title(pr.getString("ir_title").replaceAll("'",""));
		ir_html = su.encodingRequestPageByGet(SS_URL+"riskv3/report/issue_report_form.jsp?id_seq="+pr.getString("id_seq")+"&ir_type="+ReportTypeConstants.getEmergencyVal(),"UTF-8");
		irBean.setIr_html(su.dbString(ir_html));
		irBean.setIr_memo("");
		irBean.setIr_type(ReportTypeConstants.getEmergencyVal());
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
		
		String receipt = cu.getConfig("URL") + "riskv3/report/report_receipt.jsp";
		
		//메일발송
		if(arrABBean.size()>0){			
			
			for( int i=0 ; i <arrABBean.size() ; i++ )
			{			
				abBean = (AddressBookBean)arrABBean.get(i);							
				if(sendMailUser.length()>0){
					sendResult = gMail.sendmessage(abBean.getMab_mail(), sendMailUser, su.cutString(irBean.getIr_title(), 45, "..."), ir_html.replaceAll("report_receipt", receipt + "?info=" + logBean.getL_seq() + "," + abBean.getMab_seq()), true);	
				}else{
					sendResult = gMail.sendmessage(abBean.getMab_mail(), su.cutString(irBean.getIr_title(), 45, "..."), ir_html.replaceAll("report_receipt", receipt + "?info=" + logBean.getL_seq() + "," + abBean.getMab_seq()), true);
				}					
			}
			//메일 수신자 로그
			logMgr.insertLogReceiver(logBean.getL_seq(),mailreceiver.split(","));
		}
		if(sendResult){
			script_str = "parent.document.getElementById('sending').style.display = 'none'; alert('메일수신자에게 메일을 발송하였습니다.'); parent.window.close(); \n";
		}else{
			irBean.setIr_mailyn("F");
			irMgr.updateMailYN(irBean);
			script_str = "parent.document.getElementById('sending').style.display = 'none'; alert('메일발송에 실패하였습니다.');\n";
		}
		
	
	//관련 정보 수정폼에서 관련정보를 업데이트 후 메일 발송
	}else if( mode.equals("update&mail") ) {	
		
		//이슈  데이터 등록 관련
		idBean = new IssueDataBean();
		idBean.setId_seq(pr.getString("id_seq"));
		idBean.setI_seq(pr.getString("i_seq","0"));
		idBean.setIt_seq(pr.getString("it_seq","0"));
		idBean.setMd_seq(pr.getString("md_seq"));
		idBean.setId_regdate(pr.getString("id_regdate"));	
		idBean.setId_title(su.dbString(pr.getString("id_title")));
		idBean.setId_url(su.dbString(pr.getString("id_url")));
		idBean.setId_writter(pr.getString("name"));
		idBean.setId_content(su.dbString(pr.getString("id_content")));
		idBean.setMd_site_name(pr.getString("md_site_name"));
		idBean.setMd_site_menu(pr.getString("md_site_menu"));
		idBean.setS_seq(pr.getString("s_seq"));
		idBean.setSg_seq(pr.getString("sg_seq"));
		idBean.setMd_date(pr.getString("md_date"));	
		idBean.setId_useyn("Y");
		idBean.setMd_type(pr.getString("md_type"));
		idBean.setId_mailyn(pr.getString("id_mailyn"));
		idBean.setM_seq(SS_M_NO);
		
		// 기자 등록 프로세스
		if(!pr.getString("name","").equals("") && pr.getString("typeCode7","").equals(""))
		{
			cMgr.InsertClf(7,0,pr.getString("name",""),SS_M_NO);
			typeCode += "@"+cMgr.getInsertTypeCode(7);
		}
		
		// 이슈정보 수정	
		iMgr.updateIssueData(idBean,icBean,typeCode);  //이슈등록후 등록 번호 셋팅		
		
		//리포트 저장후 로그 저장
		irBean = new IssueReportBean();
		logBean = new LogBean();
		
		irBean.setIr_title(idBean.getId_title());
		ir_html = su.encodingRequestPageByGet(SS_URL+"riskv3/issue/issue_report_form.jsp?id_seq="+idBean.getId_seq()+"&ir_type="+ReportTypeConstants.getEmergencyVal(),"UTF-8");
		irBean.setIr_html(su.dbString(ir_html));
		irBean.setIr_memo("");
		irBean.setIr_type(ReportTypeConstants.getEmergencyVal());
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
		
		//메일발송
		if(arrABBean.size()>0){			
			
			for( int i=0 ; i <arrABBean.size() ; i++ )
			{			
				abBean = (AddressBookBean)arrABBean.get(i);							
				if(sendMailUser.length()>0){
					sendResult = gMail.sendmessage(abBean.getMab_mail(), sendMailUser, su.cutString(irBean.getIr_title(), 45, "..."), ir_html, true);	
				}else{
					sendResult = gMail.sendmessage(abBean.getMab_mail(), su.cutString(irBean.getIr_title(), 45, "..."), ir_html, true);
				}						
			}
			//메일 수신자 로그
			logMgr.insertLogReceiver(logBean.getL_seq(),mailreceiver.split(","));
		}
				
		if(sendResult){
			script_str = "alert('이슈정보가 수정되었습니다.\\n메일수신자에게 메일을 발송하였습니다.');  parent.document.location.reload();\n";
		}else{
			irBean.setIr_mailyn("F");
			irMgr.updateMailYN(irBean);
			script_str = "alert('이슈정보가 수정되었습니다.\\n메일발송에 실패하였습니다.');  parent.document.location.reload();\n";
		}
	
	//관련 정보 수정폼에서 관련정보를 업데이트
	}else if( mode.equals("update") ) {
		
		//이슈  데이터 등록 관련
		idBean = new IssueDataBean();
		idBean.setId_seq(pr.getString("id_seq"));
		idBean.setI_seq(pr.getString("i_seq","0"));
		idBean.setIt_seq(pr.getString("it_seq","0"));
		idBean.setMd_seq(pr.getString("md_seq"));
		idBean.setMd_pseq(pr.getString("md_pseq"));
		idBean.setId_regdate(pr.getString("id_regdate"));	
		idBean.setId_title(su.dbString(pr.getString("id_title")));
		idBean.setId_url(su.dbString(pr.getString("id_url")));
		idBean.setId_writter(pr.getString("name"));
		idBean.setId_content(su.dbString(pr.getString("id_content")));
		idBean.setMd_site_name(pr.getString("md_site_name"));
		idBean.setMd_site_menu(pr.getString("md_site_menu"));
		idBean.setS_seq(pr.getString("s_seq"));
		idBean.setSg_seq(pr.getString("sg_seq"));
		idBean.setMd_date(pr.getString("md_date"));	
		idBean.setId_useyn("Y");
		idBean.setId_mailyn(pr.getString("id_mailyn"));
		idBean.setMd_type(pr.getString("md_type"));
		idBean.setM_seq(SS_M_NO);
		idBean.setId_reportyn(pr.getString("ra_report"));
		//idBean.setK_xp(pr.getString("keyTypeXp"));
		//idBean.setK_yp(pr.getString("keyTypeYp"));
		idBean.setMedia_info(pr.getString("media_info"));
		idBean.setF_news(pr.getString("f_news",""));
		
		
		// 기자 등록 프로세스
		if(!pr.getString("name","").equals("") && pr.getString("typeCode7","").equals(""))
		{
			cMgr.InsertClf(7,0,pr.getString("name",""),SS_M_NO);
			typeCode += "@"+cMgr.getInsertTypeCode(7);
		}
		
		// 이슈정보 수정	
		iMgr.updateIssueData(idBean,icBean,typeCode);  //이슈등록후 등록 번호 셋팅		
		
		if(child.equals("Y")){
			iMgr.updateChildIssueData(idBean,icBean,typeCode);
		}
		
//		script_str = "alert('이슈정보가 수정되었습니다.'); \n  parent.opener.document.location='issue_data_list.jsp?nowPage="+nowPage+"' \n parent.close();";	
		script_str = "alert('이슈정보가 수정되었습니다.'); \n  parent.opener.search('"+nowPage+"'); \n parent.close();";	
	
	//이슈리스트에서 체크된 관련정보를 삭제
	}if( mode.equals("delete") ) {		
		iMgr.deleteIssueData(pr.getString("check_no"));		
		script_str = "alert('이슈정보가 삭제되었습니다.'); \n  parent.document.location='issue_data_list.jsp?nowPage="+nowPage+"'";	
	}
	
%>
<script language="JavaScript" type="text/JavaScript">
<%=script_str%>
</script>
