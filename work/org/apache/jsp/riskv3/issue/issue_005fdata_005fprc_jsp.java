/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.0.M22
 * Generated at: 2022-07-28 01:46:31 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.riskv3.issue;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.json.JSONObject;
import java.net.URLEncoder;
import risk.util.TelegramUtil;
import risk.util.ParseRequest;
import risk.util.StringUtil;
import risk.util.DateUtil;
import risk.search.MetaMgr;
import risk.search.DomainKeywordMgr;
import risk.util.RsnAnalysis;
import risk.search.MetaBean;
import risk.issue.IssueMgr;
import risk.issue.IssueDataBean;
import risk.issue.IssueCommentBean;
import java.util.ArrayList;
import risk.util.ConfigUtil;

public final class issue_005fdata_005fprc_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/riskv3/issue/../inc/sessioncheck.jsp", Long.valueOf(1658907412822L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("risk.search.DomainKeywordMgr");
    _jspx_imports_classes.add("risk.util.RsnAnalysis");
    _jspx_imports_classes.add("risk.issue.IssueDataBean");
    _jspx_imports_classes.add("risk.issue.IssueMgr");
    _jspx_imports_classes.add("risk.util.StringUtil");
    _jspx_imports_classes.add("java.util.ArrayList");
    _jspx_imports_classes.add("risk.issue.IssueCommentBean");
    _jspx_imports_classes.add("java.net.URLEncoder");
    _jspx_imports_classes.add("risk.json.JSONObject");
    _jspx_imports_classes.add("risk.search.MetaBean");
    _jspx_imports_classes.add("risk.util.ConfigUtil");
    _jspx_imports_classes.add("risk.util.ParseRequest");
    _jspx_imports_classes.add("risk.util.DateUtil");
    _jspx_imports_classes.add("risk.search.MetaMgr");
    _jspx_imports_classes.add("risk.util.TelegramUtil");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
//@ page contentType="text/html; charset=euc-kr"
      out.write("\r\n");
      out.write("\r\n");

    
	String SS_M_NO = (String)session.getAttribute("SS_M_NO")   == null ? "": (String)session.getAttribute("SS_M_NO")  ;
    String SS_M_ID = (String)session.getAttribute("SS_M_ID")   == null ? "": (String)session.getAttribute("SS_M_ID")  ;
    String SS_M_NAME = (String)session.getAttribute("SS_M_NAME") == null ? "": (String)session.getAttribute("SS_M_NAME");
    String SS_MG_NO = (String)session.getAttribute("SS_MG_NO")  == null ? "": (String)session.getAttribute("SS_MG_NO") ;
	String SS_TITLE = (String)session.getAttribute("SS_TITLE")  == null ? "": (String)session.getAttribute("SS_TITLE");
    String SS_URL =   (String)session.getAttribute("SS_URL")    == null ? "": (String)session.getAttribute("SS_URL") ;
    String SS_M_DEPT =   (String)session.getAttribute("SS_M_DEPT")    == null ? "": (String)session.getAttribute("SS_M_DEPT") ;    
    String SS_M_IP =   (String)session.getAttribute("SS_M_IP")    == null ? "": (String)session.getAttribute("SS_M_IP") ;    
    String SS_M_MAIL =   (String)session.getAttribute("SS_M_MAIL")    == null ? "": (String)session.getAttribute("SS_M_MAIL") ;    
	String SS_M_ORGVIEW_USEYN = (String)session.getAttribute("SS_M_ORGVIEW_USEYN")    == null ? "": (String)session.getAttribute("SS_M_ORGVIEW_USEYN") ;    
	String SS_ML_URL = javax.servlet.http.HttpUtils.getRequestURL(request).toString();
	
	String SS_SEARCHDATE = (String)session.getAttribute("SS_SEARCHDATE")    == null ? "": (String)session.getAttribute("SS_SEARCHDATE") ;
	
	
    

	if ((SS_M_ID.equals("")) || !SS_M_IP.equals(request.getRemoteAddr()) ) {
		ConfigUtil cu = new ConfigUtil();
		out.print("<SCRIPT Language=JavaScript>");
		//out.print("window.setTimeout( \" top.document.location = "+cu.getConfig("URL")+"'riskv3/error/sessionerror.jsp' \") ");
		out.print("top.document.location = '"+cu.getConfig("URL")+"risk/sessionerror.jsp'");
        out.print("</SCRIPT>");
    }
	

      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');

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

      out.write("\r\n");
      out.write("\r\n");
      out.write("<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n");
      out.print(script_str);
      out.write("\r\n");
      out.write("</script>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
