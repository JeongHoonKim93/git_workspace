<%@page import="risk.json.JSONObject"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="risk.util.TelegramUtil"%>
<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import = "risk.util.ParseRequest" %>
<%@ page import = "risk.util.StringUtil" %>
<%@ page import = "risk.util.DateUtil" %>
<%@ page import = "risk.search.MetaMgr" %>
<%@ page import = "risk.search.DomainKeywordMgr" %>
<%@ page import="risk.util.RsnAnalysis"%>
<%@ page import = "risk.search.MetaBean" %>
<%@ page import = "risk.issue.IssueMgr" %>
<%@ page import = "risk.issue.IssueDataBean" %>
<%@ page import = "risk.issue.IssueCommentBean" %>
<%@ page import = "java.util.ArrayList" %>
<%@ include file="../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	StringUtil su = new StringUtil();
	DateUtil du = new DateUtil();
	IssueMgr iMgr = new IssueMgr();
	IssueDataBean idBean = null;
	RsnAnalysis ana = new RsnAnalysis();

	
	// Parameter
	String txtcmt = pr.getString("txtcmt","");
	String txtcmt2 = pr.getString("txtcmt2");
	String mode = pr.getString("mode");
	String mode2 = pr.getString("mode2");
	String typeCodes = pr.getString("typeCodes");
	String typeCodesTrend = pr.getString("typeCodesDetail", "");
	String typeCodesInfo = pr.getString("typeCodesInfo", "");
	String relationkeyCode = pr.getString("relationkeyCode","");
	String relationkeyNames = pr.getString("relationkeyNames","");
	String md_seq = pr.getString("md_seq");
	String md_seqs = pr.getString("md_seqs", "");
	String md_date = pr.getString("md_date","");
	String id_seqs = pr.getString("id_seqs", "");
	String child = pr.getString("child", "N");
	String subMode = pr.getString("subMode", "");
	String param_writername = pr.getString("param_writername", "");
	
	//이슈정보 검색 조건 유지	
	String nowpage = pr.getString("nowpage");
	String sentiType = pr.getString("sentiType","");
	String cloutType = pr.getString("cloutType","");
	String transType = pr.getString("transType","");
	String siteType = pr.getString("siteType","");
	
	String typeCode14 = pr.getString("typeCode14", "");
	String typeCode7 = pr.getString("typeCode7", "");
	
	if(!typeCode14.equals("")) {
		typeCodes += "@" + typeCode14; 	 	
	}
	if(!typeCode7.equals("")) {
		typeCodes += "@" + typeCode7; 	 	
	}
	
	String script_str = "";

	if( mode.equals("insert")) {
		
		//if(iMgr.IssueRegistCheck(pr.getString("md_pseq","0")) == 0){
			idBean = new IssueDataBean();
			idBean.setI_seq(pr.getString("i_seq","0"));
			idBean.setIt_seq(pr.getString("it_seq","0"));
			idBean.setMd_seq(pr.getString("md_seq",""));		
			idBean.setId_regdate(pr.getString("param_id_regdate",""));	
			idBean.setId_title(su.dbString(pr.getString("param_id_title","")));
			idBean.setId_url(su.dbString(pr.getString("param_id_url","")));
			idBean.setId_writter(pr.getString("m_name",SS_M_NAME));
			idBean.setId_content(su.dbString(pr.getString("param_id_content","")));
			idBean.setMd_site_name(pr.getString("param_md_site_name",""));
			idBean.setMd_site_menu(pr.getString("md_site_menu","").replaceAll("'", ""));
			idBean.setMd_same_ct(pr.getString("md_same_ct",""));
			idBean.setS_seq(pr.getString("s_seq",""));
			idBean.setSg_seq(pr.getString("sg_seq",""));
			idBean.setMd_date(pr.getString("md_date",""));
			idBean.setId_mailyn("N");
			idBean.setId_useyn("Y");
			idBean.setM_seq(SS_M_NO);
			idBean.setMd_type(pr.getString("param_md_type"));
			idBean.setL_alpha(pr.getString("l_alpha"));
			idBean.setMd_pseq(pr.getString("md_pseq","0"));
			idBean.setId_reportyn(transType);
			idBean.setRelationkeys(relationkeyNames);	
			idBean.setRelationkeysCode(relationkeyCode);	
			idBean.setTemp1("SIM");
			idBean.setId_clout(cloutType);
			idBean.setId_senti(sentiType);
			//idBean.setId_trans_useyn(transType);
			idBean.setId_comment(txtcmt);
			idBean.setMd_writer(param_writername);	//기자명추가
 
			String last = iMgr.insertIssueData_V2(mode, idBean, typeCodes, typeCodesTrend, typeCodesInfo, mode2);
			//String last = iMgr.insertIssueData_V2(mode, idBean, typeCodes, "", "", mode2);
			
/* 			if(!txtcmt.equals("")){					
			iMgr.InsertIssueComment(id_seqs, txtcmt);				
			}	
 */			
			
			// 이슈정보 등록		
			idBean.setId_seq(last);  //이슈등록후 등록 번호 셋팅
			if(!last.equals("") &&  last != null){
				
				System.out.println("유사그룹 등록 테스트");
				System.out.println("유사그룹 등록 테스트");
				System.out.println("유사그룹 등록 테스트");
				System.out.println("유사그룹 등록 테스트");
				System.out.println("유사그룹 등록 테스트");
				// 자기사 이슈정보 등록	
				idBean.setTemp1("SIC");
				iMgr.insertIssueData_V2_sub(mode, idBean.getMd_seq(), idBean, typeCodes, typeCodesTrend, typeCodesInfo, subMode);
			}
			
			//script_str = "parent.document.getElementById('sending').style.display = 'none'; var obj = parent.opener.document.getElementById('issue_menu_icon"+idBean.getMd_seq()+"'); \n obj.src='../../images/search/btn_manage_on.gif';\n obj.onclick='';\n obj.title='이슈로 등록된 정보 입니다.';\n parent.close();\n";
			//고객사요청- 등록후 재수정 가능
			script_str = "parent.document.getElementById('sending').style.display = 'none'; var obj = parent.opener.document.getElementById('issue_menu_icon"+idBean.getMd_seq()+"'); \n obj.src='../../images/search/btn_manage_on.gif';\n obj.title='이슈로 등록된 정보 입니다.';\n obj.setAttribute(\"onClick\",\"send_issue('update','"+pr.getString("md_seq")+"')\");\n parent.close();\n";
			/*
		} else {
			script_str = "parent.document.getElementById('sending').style.display = 'none'; alert('이슈로 등록되었거나 등록 예정인 정보 입니다.');\n";
		}
		*/
	} else if("multi".equals(mode)){
		MetaMgr metaMgr = new MetaMgr();
		DomainKeywordMgr dkMgr = new DomainKeywordMgr();
		du = new DateUtil();
		JSONObject jObject = new JSONObject();
		String writername = "-";
		//String md_pseqs = metaMgr.Alter_mdSeq_mdPseq(md_seqs, mode2);
		
		//if(iMgr.IssueRegistCheck(md_pseqs) == 0){
			if(!md_seqs.equals("")){
				ArrayList metaList = new ArrayList();
				
				if(subMode.equals("domain")) {
					metaList = dkMgr.getMetaDataList(md_seqs,mode2);
				} else {
					metaList = metaMgr.getMetaDataList(md_seqs,mode2);
				}
				
				
				for(int i=0; i < metaList.size(); i++){
					MetaBean mBean = (MetaBean)metaList.get(i);
					if(iMgr.IssueRegistCheck_v2(mBean.getMd_seq()) > 0) {
						continue;
					}
					//이슈  데이터 등록 관련
					idBean = new IssueDataBean();
					idBean.setI_seq(pr.getString("i_seq","0"));
					idBean.setIt_seq(pr.getString("it_seq","0"));
					idBean.setMd_seq(mBean.getMd_seq());
					idBean.setId_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));	
					idBean.setId_title(su.dbString(mBean.getMd_title()));
					idBean.setId_url(su.dbString(mBean.getMd_url()));
					idBean.setId_content(su.dbString(su.ChangeString(mBean.getMd_content())));
					idBean.setMd_site_name(mBean.getMd_site_name());
					idBean.setMd_site_menu(mBean.getMd_site_menu());
					idBean.setS_seq(mBean.getS_seq());
					idBean.setSg_seq(mBean.getSg_seq());
					//idBean.setSg_seq(siteType);
					idBean.setMd_date(mBean.getFormatMd_date("yyyy-MM-dd HH:mm:ss"));	
					idBean.setId_writter(pr.getString("m_name",SS_M_NAME));
					idBean.setId_mailyn("N");
					idBean.setId_useyn("Y");
					idBean.setM_seq(SS_M_NO);
					idBean.setMd_type(mBean.getMd_type());
					idBean.setMd_same_ct(mBean.getMd_same_count());
					idBean.setL_alpha(mBean.getL_alpha());
					idBean.setMd_pseq(mBean.getMd_pseq());
					//idBean.setId_reportyn(pr.getString("ra_report","N"));
					idBean.setRelationkeysCode(relationkeyCode);	
					idBean.setRelationkeys(relationkeyNames);
					idBean.setId_clout(cloutType);
					idBean.setId_senti(sentiType);
					idBean.setId_reportyn(transType);
					idBean.setId_comment(txtcmt);
					
				    //기자명 파싱
			    	jObject = new JSONObject(mBean.getJson_data());
			    	if(jObject.has("MAIN.DATA.WriterName")) {
			    		writername = jObject.getString("MAIN.DATA.WriterName");
			    	} else if(jObject.has("TWITTER.DATA.WriterID")) {
			    		writername = jObject.getString("TWITTER.DATA.WriterID");
			    	} else {
			    		writername = "";
			    	}
			    	idBean.setMd_writer(writername);
					
					idBean.setTemp1("MIM");
					
		 			//String[] arRelation = mBean.getMd_relation().split(",");

		 			//ArrayList arRelation = ana.makeAutoReplyRelation(su.dbString(su.ChangeString(mBean.getMd_content())));				

					//String last = iMgr.insertIssueData_V2(mode, idBean, typeCodes, typeCodesTrend, typeCodesInfo, mode2, arRelation, subMode);
					String last = iMgr.insertIssueData_V2(mode, idBean, typeCodes, typeCodesTrend, typeCodesInfo, mode2, subMode);
					
					
					idBean.setId_seq(last);  //이슈등록후 등록 번호 셋팅	
					if(!last.equals("") &&  last != null){
						// 자기사 이슈정보 등록	
						idBean.setTemp1("MIC");
						//iMgr.insertIssueData_V2_sub(mode, idBean.getMd_seq(), idBean, typeCodes, typeCodesTrend, typeCodesInfo, arRelation, subMode);
						iMgr.insertIssueData_V2_sub(mode, idBean.getMd_seq(), idBean, typeCodes, typeCodesTrend, typeCodesInfo, subMode);
					}
					script_str += "\n var obj"+i+" = parent.opener.document.getElementById('issue_menu_icon"+mBean.getMd_seq()+"'); \n obj"+i+".src='../../images/search/btn_manage_on.gif';\n obj"+i+".title='이슈로 등록된 정보 입니다.';\n obj"+i+".onclick='';\n";
					
					
					script_str += "parent.document.getElementById('sending').style.display = 'none'; var obj = parent.opener.document.getElementById('issue_menu_icon"+idBean.getMd_seq()+"'); \n obj.src='../../images/search/btn_manage_on.gif';\n obj.title='이슈로 등록된 정보 입니다.';\n obj.setAttribute(\"onClick\",\"send_issue('update','"+idBean.getMd_seq()+"')\");\n parent.close();\n";
				}
				/* script_str += "alert('이슈정보가 등록되었습니다.'); parent.close();"; */
			}
			/*
		} else{
			script_str = "alert('이슈로 등록되었거나 등록 예정인 정보가 있습니다.');\n";
		}*/
		
		
	} else if("delete".equals(mode)){
		
		iMgr.deleteIssueData(pr.getString("id_seqs"));
		
/* 		if(!txtcmt.equals("")){				
		iMgr.DeleteIssueComment(id_seqs);			
		}			
 */		
		script_str = "";
	} else if("trans".equals(mode)){
		iMgr.transIssueData(pr.getString("id_seqs"));
		script_str = "";
	} else if("update".equals(mode)){		
		//이슈  데이터 등록 관련
		idBean = new IssueDataBean();
		idBean.setId_seq(pr.getString("id_seq"));
		idBean.setI_seq(pr.getString("i_seq","0"));
		idBean.setIt_seq(pr.getString("it_seq","0"));
		idBean.setMd_seq(pr.getString("md_seq"));
		idBean.setId_regdate(pr.getString("param_id_regdate",""));	
		idBean.setId_title(su.dbString(pr.getString("param_id_title","")));
		idBean.setId_url(su.dbString(pr.getString("param_id_url","")));
		idBean.setId_writter(pr.getString("m_name", SS_M_NAME));
		idBean.setId_content(su.dbString(pr.getString("param_id_content","")));
		idBean.setMd_site_name(pr.getString("param_md_site_name",""));
		idBean.setMd_site_menu(pr.getString("md_site_menu","").replaceAll("'", ""));
		idBean.setS_seq(pr.getString("s_seq"));
		idBean.setSg_seq(pr.getString("sg_seq"));
		//idBean.setSg_seq(siteType);
		idBean.setMd_date(pr.getString("md_date"));	
		idBean.setId_useyn("Y");
		idBean.setId_mailyn(pr.getString("id_mailyn", "N"));
		idBean.setMd_type(pr.getString("param_md_type"));
		idBean.setM_seq(SS_M_NO);
		idBean.setId_reportyn(transType);
		idBean.setRelationkeysCode(relationkeyCode);	
		idBean.setRelationkeys(relationkeyNames);
		idBean.setId_clout(cloutType);
		idBean.setId_senti(sentiType);
		//idBean.setId_trans_useyn(transType);
		idBean.setTemp1("SUM");
		idBean.setId_comment(txtcmt);

		iMgr.updateIssueData_V2(mode, idBean, typeCodes, typeCodesTrend, typeCodesInfo, mode2);	

		if(child.equals("Y")){
			idBean.setTemp1("SUC");
			System.out.println("유사데이터 수정");
			iMgr.updateChildIssueData(mode, idBean, typeCodes, typeCodesTrend, typeCodesInfo, mode2);
		}
		
		
		if(null != nowpage && !"".equals(nowpage) ){
			script_str = "alert('이슈정보가 수정되었습니다.'); \n  parent.opener.search('"+nowpage+"'); \n parent.close();";
		}else{
			script_str = "alert('이슈정보가 수정되었습니다.'); \n  parent.close();";
		}
	 } else if( mode.equals("update_multi") ) {
		if(!id_seqs.equals("")){
			String[] id_seq_list = id_seqs.split(","); 
			
			for(int i=0; i<id_seq_list.length; i++){
				idBean = new IssueDataBean();				
				idBean.setId_seq(id_seq_list[i]);
				idBean.setRelationkeysCode(relationkeyCode);	
				idBean.setRelationkeys(relationkeyNames);
				idBean.setId_clout(cloutType);
				idBean.setId_senti(sentiType);
				idBean.setId_reportyn(transType);
				iMgr.multi_updateIssueData(idBean, typeCodes);
				
				System.out.println();
				System.out.println("코드 명 : " + typeCodes);
				System.out.println("연관키워드 코드 : " + idBean.getRelationkeysCode());
				System.out.println("연관키워드 명 : " + idBean.getRelationkeys());
				System.out.println("보고서 여부 : " + idBean.getId_reportyn());
				System.out.println("감성 : " + idBean.getId_senti());
				System.out.println("영향력 : " + idBean.getId_clout());
				System.out.println();
				
				if(child.equals("Y")){
					idBean.setTemp1("SUC");
					System.out.println("유사데이터 수정");
					iMgr.updateChildIssueData(mode, idBean, typeCodes, typeCodesTrend, typeCodesInfo, mode2);
				}
				
				//iMgr.multi_updateIssueData(idBean, typeCodes);
			//	String last = iMgr.insertIssueData_V2(mode, idBean, typeCodes, typeCodesTrend, typeCodesInfo, mode2);
			//	idBean.setId_seq(last);  //이슈등록후 등록 번호 셋팅	
			//	script_str += "\n var obj"+i+" = parent.opener.document.getElementById('issue_menu_icon"+mBean.getMd_seq()+"'); \n obj"+i+".src='../../images/search/btn_manage_on.gif';\n obj"+i+".title='이슈로 등록된 정보 입니다.';\n obj"+i+".onclick='';\n";
			}
			script_str = "alert('이슈정보가 수정되었습니다.'); \n  parent.opener.search('"+nowpage+"'); \n parent.close();";
		}
		 
	}
	/*}else if( mode.equals("update_multi") ) {
		 System.out.print(id_seqs);
		if(!"".equals(id_seqs)){
			String[] id_seq_list = id_seqs.split(","); 
			for(int i=0; i<id_seq_list.length; i++){
				idBean = new IssueDataBean();				
				idBean.setId_seq(id_seq_list[i]);
				idBean.setRelationkeys(relationkeyNames);
				idBean.setCaprelationkeys(relationkeyNames1);	
				idBean.setComrelationkeys(relationkeyNames2);	
				idBean.setId_senti(sentiType);
				idBean.setId_trans_useyn(transType);
				iMgr.multi_updateIssueData(idBean, typeCodes);
			}
		}	
		script_str = "alert('이슈정보가 수정되었습니다.'); \n  parent.opener.search('"+nowpage+"'); \n parent.close();"; 
		
	}*/else if(mode.equals("send_telegram")){
		TelegramUtil tUtil = new TelegramUtil();
		String msg = pr.getString("msg");
		String m_ch_id_group = pr.getString("receivers");		 
			
			/* if(tUtil.SendTG_Group2(m_ch_id_group, msg)){
				script_str = "alert('텔레그램을 발송하였습니다.'); \n  parent.close();";
			}else {
				script_str = "alert('텔레그램을 발송에 실패하였습니다.\n 재시도 부탁드립니다.'); \n  parent.close();";
			} */
			
			String[] m_ch_id_liset = m_ch_id_group.split(",");
			int success_chk = 0;
			String[] telegram_groupCode_groupId = new String[2]; //tg_seq@tg_id
			
			
			for(int i=0; i<m_ch_id_liset.length; i++){
				
				telegram_groupCode_groupId = m_ch_id_liset[i].split("@");
				
				
				System.out.println(telegram_groupCode_groupId[0]);
				
				if(tUtil.SendTG_Group(telegram_groupCode_groupId[1] , msg)){
					success_chk++;				
					//텔레그램 발송 수동로그 남기기 // 성공로그만 남김
					tUtil.insertTelegramLog(telegram_groupCode_groupId[0], msg, SS_M_NO);
					
				}		
			}
			
			//텔레그램 발송 결과
			if(success_chk == m_ch_id_liset.length){
				script_str = "alert('텔레그램을 발송하였습니다.'); \n  parent.close();";
			}else{
				script_str = "alert('텔레그램을 발송에 실패하였습니다.\n 재시도 부탁드립니다.'); \n  parent.close();";
			}
		
			
	}else if(mode.equals("new")){
		
		String[] arSite = iMgr.getConfirmSite(pr.getString("param_md_site_name",""));
		
		idBean = new IssueDataBean();
		idBean.setI_seq("0");
		idBean.setIt_seq("0");
		idBean.setMd_seq("0");		
		idBean.setId_regdate(pr.getString("param_id_regdate",""));	
		idBean.setId_title(su.dbString(pr.getString("param_id_title","")));
		idBean.setId_url(su.dbString(pr.getString("param_id_url","")));
		idBean.setId_writter(pr.getString("m_name",SS_M_NAME));
		idBean.setId_content(su.dbString(pr.getString("param_id_content","")));
		idBean.setMd_site_name(pr.getString("param_md_site_name",""));
		idBean.setMd_site_menu("수동등록");
		idBean.setMd_same_ct("0");
		idBean.setS_seq(arSite[1]);
		idBean.setSg_seq(arSite[0]);
		//idBean.setSg_seq(siteType);
		idBean.setMd_date(pr.getString("param_id_regdate",""));
		idBean.setId_mailyn("N");
		idBean.setId_useyn("Y");
		idBean.setM_seq(SS_M_NO);
		idBean.setMd_type(pr.getString("param_md_type"));
		idBean.setL_alpha("KOR");
		idBean.setMd_pseq("0");
		idBean.setId_reportyn(transType);
		idBean.setRelationkeysCode(relationkeyCode);	
		idBean.setRelationkeys(relationkeyNames);	
		idBean.setTemp1("SIM");
		idBean.setId_clout(cloutType);
		idBean.setId_senti(sentiType);
		
		String last = iMgr.insertIssueData_V2(mode, idBean, typeCodes, typeCodesTrend, typeCodesInfo, mode2);
		
		if(pr.getString("menu","").equals("issue")){
			script_str = " parent.opener.search('"+nowpage+"'); \n parent.close();";
		}else{
			script_str = "parent.document.getElementById('sending').style.display = 'none';  parent.close();\n";	
		}
		
	}
%>

<script language="JavaScript" type="text/JavaScript">
<%=script_str%>
</script>