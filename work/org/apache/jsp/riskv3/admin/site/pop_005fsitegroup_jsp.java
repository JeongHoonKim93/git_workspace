/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.0.M22
 * Generated at: 2022-05-06 01:24:39 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.riskv3.admin.site;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.*;
import risk.admin.site.*;
import risk.util.ConfigUtil;

public final class pop_005fsitegroup_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/riskv3/admin/site/../../inc/sessioncheck.jsp", Long.valueOf(1651044823347L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("risk.admin.site");
    _jspx_imports_packages.add("risk.util");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("risk.util.ConfigUtil");
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
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

//@ page contentType="text/html; charset=euc-kr"

    
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
	


    ParseRequest    pr = new ParseRequest(request);

    String mode          = pr.getString("mode");
    String sgseq          = pr.getString("sgseq");
    String language      = pr.getString("language");
	String title = "";
	String sgval = "";

	SiteMng SGmng = new SiteMng();

	if( mode.equals("ins") ) {
		title = "사이트그룹 추가";
	} else if( mode.equals("edit") ) {
		title = "사이트그룹 수정";
		sgval = SGmng.getSG(sgseq,"sg");
	}

      out.write(_jspx_char_array_0);
      out.print(sgseq);
      out.write(_jspx_char_array_1);
      out.print(language);
      out.write(_jspx_char_array_2);
      out.print(sgval);
      out.write(_jspx_char_array_3);

	if( mode.equals("ins") ) {

      out.write(_jspx_char_array_4);

	} else if( mode.equals("edit") ) {

      out.write(_jspx_char_array_5);

	}

      out.write(_jspx_char_array_6);
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
  static char[] _jspx_char_array_0 = "<html>\r\n<head>\r\n<title>RISK V3 - RSN</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n<link rel=\"stylesheet\" href=\"../../../css/base.css\" type=\"text/css\">\r\n<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n<!--\r\n	function init()\r\n	{\r\n		membergroup.sgval.focus();	\r\n	}\r\n	\r\n	\r\n	function add_kg()\r\n	{\r\n		if( !membergroup.sgval.value )\r\n		{\r\n			alert('사이트그룹명을 입력하십시요.');\r\n		} else {\r\n			membergroup.mode.value = 'ins';\r\n			membergroup.submit();\r\n			window.close();\r\n		}\r\n	}\r\n\r\n	function edit_kg()\r\n	{\r\n		if( !membergroup.sgval.value )\r\n		{\r\n			alert('사이트그룹명을 입력하십시요.');\r\n		} else {\r\n			membergroup.mode.value = 'edit';\r\n			membergroup.submit();\r\n			window.close();\r\n		}\r\n	}\r\n//-->\r\n</script>\r\n</head>\r\n<body bgcolor=\"#FFFFFF\" text=\"#000000\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" onload=\"init();\">\r\n<form name=\"membergroup\" action=\"ifram_target_prc.jsp\" method=\"post\" target=\"tg_sitemng\">\r\n<input type=\"hidden\" name=\"mode\">\r\n<input type=\"hidden\" name=\"sgseq\" value=\"".toCharArray();
  static char[] _jspx_char_array_1 = "\">\r\n<input type=\"hidden\" name=\"language\" value=\"".toCharArray();
  static char[] _jspx_char_array_2 = "\">\r\n\r\n<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"10\">\r\n	<tr>\r\n		<td colspan=\"3\" id=\"pop_head\">\r\n			<p>사이트그룹추가</p>\r\n			<span><a href=\"javascript:close();\"><img src=\"../../../images/search/pop_tit_close.gif\"></a></span>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<table style=\"width:100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n  <tr>\r\n    <td style=\"padding-left:10px\"><table width=\"370\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n		<tr>\r\n			<td width=\"120\" align=\"left\" style=\"padding: 3px 0px 0px 0px;\" class=\"menu_gray\"><strong>사이트그룹명 :</strong></td>\r\n			<td width=\"311\" align=\"left\" style=\"padding: 3px 0px 0px 0px;\"><input type=\"text\" name=\"sgval\" value=\"".toCharArray();
  static char[] _jspx_char_array_3 = "\" OnKeyDown=\"Javascript:if (event.keyCode == 13) { add_kg();}\"></td>\r\n		</tr>\r\n	</table></td>\r\n	</tr>\r\n</table>\r\n<table width=\"378\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n	<tr>\r\n		<td height=\"40\" align=\"right\">\r\n".toCharArray();
  static char[] _jspx_char_array_4 = "<img src=\"../../../images/admin/member/btn_save2.gif\"  hspace=\"5\" onclick=\"add_kg();\" style=\"cursor:pointer;\">\r\n".toCharArray();
  static char[] _jspx_char_array_5 = "<img src=\"../../../images/admin/member_group/btn_modify.gif\" hspace=\"5\" onclick=\"edit_kg();\" style=\"cursor:pointer;\">\r\n".toCharArray();
  static char[] _jspx_char_array_6 = "<img src=\"../../../images/admin/member/btn_cancel.gif\" onclick=\"window.close();\" style=\"cursor:pointer;\"></td>\r\n	</tr>\r\n</table>\r\n</form>\r\n</body>\r\n</html>\r\n".toCharArray();
}
