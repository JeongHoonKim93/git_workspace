/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.0.M22
 * Generated at: 2022-05-24 07:50:45 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.riskv3.admin.classification2;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import risk.util.ParseRequest;
import risk.util.ConfigUtil;

public final class pop_005fclassification_005ftest_005fmodify_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/riskv3/admin/classification2/../../inc/sessioncheck.jsp", Long.valueOf(1651044823347L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("risk.util.ConfigUtil");
    _jspx_imports_classes.add("risk.util.ParseRequest");
    _jspx_imports_classes.add("java.util.ArrayList");
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
	


	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	String itype = pr.getString("itype");
	String icode = pr.getString("icode");
	
	String ic_seq = pr.getString("ic_seq");	
	String ic_name = pr.getString("ic_name");

      out.write(_jspx_char_array_0);
      out.print(itype);
      out.write(_jspx_char_array_1);
      out.print(icode);
      out.write(_jspx_char_array_2);
      out.print(ic_seq);
      out.write(_jspx_char_array_3);
      out.print(ic_name);
      out.write(_jspx_char_array_4);
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
  static char[] _jspx_char_array_0 = "<html>\r\n<head>\r\n<title>Insert title here</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n<link rel=\"stylesheet\" href=\"../../../css/base.css\" type=\"text/css\">\r\n<style>\r\n<!--	\r\n	.t {  font-family: \"Tahoma\"; font-size: 11px; color: #666666}\r\n\r\n-->\r\n	</style>\r\n<script language=\"javascript\">\r\n\r\n	function update()\r\n	{\r\n		var f = document.editForm;\r\n		\r\n		if(f.ic_name.value=='')\r\n		{	\r\n			alert('명칭 를 입력해주세요.'); return;\r\n		}else{\r\n			//앞뒤 공백 제거\r\n			f.ic_name.value = f.ic_name.value.replace(/^\\s*/,'');\r\n			f.ic_name.value = f.ic_name.value.replace(/\\s*$/,'');	\r\n		}\r\n			\r\n		f.target='';\r\n		f.action='classification_test_prc.jsp';\r\n		f.submit();\r\n	}\r\n\r\n\r\n</script>\r\n</head>\r\n<body>\r\n<form name=\"editForm\" method=\"post\" onsubmit=\"return false;\">\r\n<input type=\"hidden\" name=\"itype\" value=\"".toCharArray();
  static char[] _jspx_char_array_1 = "\">\r\n<input type=\"hidden\" name=\"icode\" value=\"".toCharArray();
  static char[] _jspx_char_array_2 = "\">\r\n<input type=\"hidden\" name=\"ic_seq\" value=\"".toCharArray();
  static char[] _jspx_char_array_3 = "\">\r\n<input type=\"hidden\" name=\"mode\" value=\"update\">\r\n\r\n<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"10\">\r\n	<tr>\r\n		<td colspan=\"3\" id=\"pop_head\">\r\n			<p>분류항목 수정</p>\r\n			<span><a href=\"javascript:close();\"><img src=\"../../../images/search/pop_tit_close.gif\"></a></span>\r\n		</td>\r\n	</tr>\r\n</table>\r\n<table style=\"width:100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n  <tr>\r\n    <td style=\"padding-left:10px\"><table width=\"370\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n		<tr>\r\n			<td width=\"120\" align=\"left\" style=\"padding: 3px 0px 0px 0px;\" class=\"menu_gray\"><strong>분류명 :</strong></td>\r\n			<td width=\"311\" align=\"left\" style=\"padding: 3px 0px 0px 0px;\"><input type=\"text\" name=\"ic_name\" value=\"".toCharArray();
  static char[] _jspx_char_array_4 = "\"></td>\r\n		</tr>\r\n	</table></td>\r\n	</tr>\r\n</table>\r\n<table width=\"378\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n	<tr>\r\n		<td height=\"40\" align=\"right\">\r\n		<img src=\"../../../images/admin/member/btn_save2.gif\"  hspace=\"5\" onclick=\"update();\" style=\"cursor:pointer;\">\r\n		<img src=\"../../../images/admin/member/btn_cancel.gif\" onclick=\"window.close();\" style=\"cursor:pointer;\"></td>\r\n	</tr>\r\n</table>\r\n</form>\r\n</body>\r\n</html>".toCharArray();
}
