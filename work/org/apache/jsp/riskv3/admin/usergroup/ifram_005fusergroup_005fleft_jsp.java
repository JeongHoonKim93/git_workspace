/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.0.M22
 * Generated at: 2022-07-27 07:39:40 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.riskv3.admin.usergroup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.*;
import java.util.List;
import risk.admin.membergroup.*;
import risk.util.ConfigUtil;

public final class ifram_005fusergroup_005fleft_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/riskv3/admin/usergroup/../../inc/sessioncheck.jsp", Long.valueOf(1658907412822L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("risk.util");
    _jspx_imports_packages.add("risk.admin.membergroup");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.List");
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
      out.write("\n");
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

	String fir_seq = null;
	List MGlist = null;
	membergroupMng MGmng = membergroupMng.getInstance();
	MGlist = MGmng.getMGList();

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>X-MAS Solution</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../../../css/base.css\" type=\"text/css\">\r\n");
      out.write("<SCRIPT LANGUAGE=\"JavaScript\">\r\n");
      out.write("<!--\r\n");
      out.write("var selobj;\r\n");
      out.write("function kg_over(obj) {\r\n");
      out.write("\tif( selobj != obj ) {\r\n");
      out.write("\t\ttmpspan.className=obj.className;\r\n");
      out.write("\t\tobj.className='kgmenu_over';\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function kg_out(obj){\r\n");
      out.write("\tif( selobj != obj ) {\r\n");
      out.write("\t\tobj.className=tmpspan.className;\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("/*\r\n");
      out.write("function sel(seq, index){\r\n");
      out.write("\t\r\n");
      out.write("\tvar t = document.getElementsByName('sel_bg');\r\n");
      out.write("\tfor(var i = 0; i < t.length; i++){\r\n");
      out.write("\t\tt[i].className = 'pop_mail_group_td';\r\n");
      out.write("\t}\r\n");
      out.write("\tt[index].className = 'pop_mail_group_td_on';\r\n");
      out.write("\tmembergroup.mgseq.value = seq;\r\n");
      out.write("\tparent.usergroup_right.location.href = 'ifram_usergroup_right.jsp?mgseq='+seq;\r\n");
      out.write("}*/\r\n");
      out.write("\r\n");
      out.write("function sel(obj, seq)\r\n");
      out.write("{\r\n");
      out.write("\tif( selobj ) selobj.className='pop_mail_group_td';\r\n");
      out.write("\ttmpspan.className=obj.className;\r\n");
      out.write("\tobj.className='pop_mail_group_td_on';\r\n");
      out.write("\tselobj = obj;\r\n");
      out.write("\tmembergroup.mgseq.value = seq;\r\n");
      out.write("\tparent.usergroup_right.location.href = 'ifram_usergroup_right.jsp?mgseq='+seq;\r\n");
      out.write("\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function del()\r\n");
      out.write("{\r\n");
      out.write("\tif( !membergroup.mgseq.value ) {\r\n");
      out.write("\t\talert('사용자그룹을 선택하십시요.');\r\n");
      out.write("\t} else {\r\n");
      out.write("\t\tmembergroup.submit();\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("//-->\r\n");
      out.write("</SCRIPT>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form name=\"membergroup\" action=\"ifram_usergroup_left_prc.jsp\" method=\"get\">\r\n");
      out.write("<input type=\"hidden\" name=\"mode\" value=\"del\">\r\n");
      out.write("<input type=\"hidden\" name=\"mgseq\">\r\n");
      out.write("</form>\r\n");
      out.write("<span id='tmpspan' style='display:none' class=''></span>\r\n");
      out.write("<table id=\"pop_mail_group\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td style=\"padding:10px;\" valign=\"top\">\r\n");
      out.write("\t\t<table style=\"width:100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");

	for(int i=0; i < MGlist.size();i++) {
		membergroupBean MGinfo = (membergroupBean)MGlist.get(i);
		if( fir_seq == null ) fir_seq = MGinfo.getMGseq();

      out.write("\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("<!--\t\t\t\t<td id=\"sel_bg\" class=\"");
if(i == 0){out.print("pop_mail_group_td_on");}else{out.print("pop_mail_group_td");}
      out.write("\" onclick=\"sel(");
      out.print(MGinfo.getMGseq());
      out.write(',');
      out.write(' ');
      out.print(i);
      out.write(");\" style=\"cursor:pointer;\">");
      out.print(MGinfo.getMGname());
      out.write("</td>-->\r\n");
      out.write("\t\t\t\t<td id=\"mg");
      out.print(MGinfo.getMGseq());
      out.write("\" class=\"");
if(i == 0){out.print("pop_mail_group_td_on");}else{out.print("pop_mail_group_td");}
      out.write("\" onclick=\"sel(this,");
      out.print(MGinfo.getMGseq());
      out.write(");\" style=\"cursor:pointer;\">");
      out.print(MGinfo.getMGname());
      out.write("</td>\r\n");
      out.write("\t\t\t</tr>\r\n");

	}

      out.write("\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("<SCRIPT LANGUAGE=\"JavaScript\">\r\n");
      out.write("<!--\r\n");

	/*
	if( fir_seq != null ) {
		out.println("sel("+fir_seq+", 0);");
	}
	*/
	
	if( fir_seq != null ) {
		out.println("sel(mg"+fir_seq+", "+fir_seq+");");
	}

      out.write("\r\n");
      out.write("//-->\r\n");
      out.write("</SCRIPT>");
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
