/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.0.M22
 * Generated at: 2022-07-27 07:39:42 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.riskv3.admin.warning_005fkeyword;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import risk.util.*;
import java.util.List;
import risk.admin.warning_keyword.KeywordMng;
import risk.admin.warning_keyword.KeywordBean;
import risk.util.ConfigUtil;
import java.util.ArrayList;

public final class admin_005fkeyword_005fright_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/riskv3/admin/warning_keyword/../../inc/sessioncheck.jsp", Long.valueOf(1658907412822L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("risk.util");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("risk.admin.warning_keyword.KeywordBean");
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("risk.util.ConfigUtil");
    _jspx_imports_classes.add("risk.admin.warning_keyword.KeywordMng");
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
      out.write("\t\t\t\t \r\n");
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

    ParseRequest    pr = new ParseRequest(request);

    String xp          = pr.getString("xp");
    String yp          = pr.getString("yp");
    String zp          = pr.getString("zp");

	String value = "";
	String part = "total";
	String k_op = "";
	String near_len = "";
	String partname = "테스트";
	List exkeylist = null;
	
	ArrayList arSgSeq = null;

	if( xp.equals("") ) xp = "0";
	if( yp.equals("") ) yp = "0";
	if( zp.equals("") ) zp = "0";

	KeywordMng keymng = KeywordMng.getInstance();
	

	if( xp.equals("0") || xp.equals("") ) {
	} else if( yp.equals("0") || yp.equals("") ) {
		part = "upkg";
		partname = "대분류";
		
		String[] arValue =  keymng.getKG(xp); 
		
		value = arValue[0];
		arSgSeq = keymng.getSgSeq(arValue[1]);
		
	} else if( zp.equals("0") || zp.equals("") ) {
		part = "downkg";
		partname = "소분류";
		value = keymng.getKG(xp,yp);
	} else {
		part = "kg";
		partname = "키워드";
		String[] arValue  = keymng.getKG(xp,yp,zp);
		value = arValue[0];
		k_op = arValue[1];
		near_len = arValue[2];
		exkeylist = keymng.getEXKG(xp,yp,zp);
		
	}

      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>RISK V3 - RSN</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"css/basic.css\" type=\"text/css\">\r\n");
      out.write("<style>\r\n");
      out.write("\t<!--\r\n");
      out.write("\ttd { font-size:12px; color:#333333; font-family:\"dotum\"; ; line-height: 18px}\r\n");
      out.write("    input { font-size:12px; border:1px solid #CFCFCF; height:16px; color:#767676; }\r\n");
      out.write("\t.t {  font-family: \"Tahoma\"; font-size: 11px; color: #666666}\r\n");
      out.write("    .tCopy { font-family: \"Tahoma\"; font-size: 12px; color: #000000; font-weight: bold}\r\n");
      out.write("body {\r\n");
      out.write("\tmargin-left: 0px;\r\n");
      out.write("\tmargin-top: 5px;\r\n");
      out.write("\tmargin-right: 0px;\r\n");
      out.write("\tmargin-bottom: 0px;\r\n");
      out.write("\tbackground-color: #F3F3F3;\r\n");
      out.write("}\r\n");
      out.write("\t-->\r\n");
      out.write("\t</style>\r\n");
      out.write("<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n");
      out.write("<!--\r\n");
      out.write("\tfunction edit_kg()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif( !keygroup.val.value )\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\talert('");
      out.print(partname);
      out.write("명을 입력하십시요. ');\r\n");
      out.write("\t\t} else {\r\n");
      out.write("\t\t\tkeygroup.mode.value = 'edit';\r\n");
      out.write("\t\t\tkeygroup.submit();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction del_kg()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif( !keygroup.xp.value )\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\talert('대상을 선택하십시요.');\r\n");
      out.write("\t\t} else {\r\n");
      out.write("\t\t\tkeygroup.mode.value = 'del';\r\n");
      out.write("\t\t\tkeygroup.submit();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction up_kg()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif( keygroup.yp.value > 0 ) {\r\n");
      out.write("\t\t\talert('위치변경은 대분류만 가능합니다.');\r\n");
      out.write("\t\t} else if( !keygroup.xp.value ){\r\n");
      out.write("\t\t\talert('대상을 선택하십시요.');\r\n");
      out.write("\t\t} else {\r\n");
      out.write("\t\t\tkeygroup.mode.value = 'up';\r\n");
      out.write("\t\t\tkeygroup.submit();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction down_kg()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif( keygroup.yp.value > 0 ) {\r\n");
      out.write("\t\t\talert('위치변경은 대분류만 가능합니다.');\r\n");
      out.write("\t\t} else if( !keygroup.xp.value ){\r\n");
      out.write("\t\t\talert('대상을 선택하십시요.');\r\n");
      out.write("\t\t} else {\r\n");
      out.write("\t\t\tkeygroup.mode.value = 'down';\r\n");
      out.write("\t\t\tkeygroup.submit();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction del_exkg()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif( !keygroup.type.value )\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\talert('대상을 선택하십시요.');\r\n");
      out.write("\t\t} else {\r\n");
      out.write("\t\t\tkeygroup.tg.value = 'exkey';\r\n");
      out.write("\t\t\tkeygroup.mode.value = 'del';\r\n");
      out.write("\t\t\tkeygroup.submit();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction del_exkg()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif( !keygroup.type.value )\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\talert('대상을 선택하십시요.');\r\n");
      out.write("\t\t} else {\r\n");
      out.write("\t\t\tkeygroup.tg.value = 'exkey';\r\n");
      out.write("\t\t\tkeygroup.mode.value = 'del';\r\n");
      out.write("\t\t\tkeygroup.submit();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\tfunction saveSiteGroup()\r\n");
      out.write("\t{\r\n");
      out.write("\r\n");
      out.write("\t\tvar f = document.keygroup;\r\n");
      out.write("\r\n");
      out.write("\t\tvar strTmp = '';\r\n");
      out.write("\t\tif(f.sgSeq){\r\n");
      out.write("\t\t\tif(f.sgSeq.length){\r\n");
      out.write("\t\t\t\tfor(var i = 0; i < f.sgSeq.length; i++){\r\n");
      out.write("\t\t\t\t\tif(f.sgSeq[i].checked == true){\r\n");
      out.write("\t\t\t\t\t\tif(strTmp == ''){\r\n");
      out.write("\t\t\t\t\t\t\tstrTmp = f.sgSeq[i].value;\r\n");
      out.write("\t\t\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\t\t\tstrTmp += ',' + f.sgSeq[i].value;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t} \r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\tif(f.sgSeq.checked == true){\r\n");
      out.write("\t\t\t\t\tstrTmp = f.sgSeq.value;\t\t\r\n");
      out.write("\t\t\t\t}\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\t\t\r\n");
      out.write("\t\tf.sg_seqs.value = strTmp;\t\t\r\n");
      out.write("\r\n");
      out.write("\t\tkeygroup.mode.value = 'sitegroup';\r\n");
      out.write("\t\tkeygroup.submit();\r\n");
      out.write("\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction nearLenSave()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tkeygroup.mode.value = 'near';\r\n");
      out.write("\t\tkeygroup.submit();\r\n");
      out.write("\t}\r\n");
      out.write("\tfunction nearLenSaveAll()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tkeygroup.mode.value = 'near_all';\r\n");
      out.write("\t\tkeygroup.submit();\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction onlyNumber(event) {\r\n");
      out.write("\t    var key = window.event ? event.keyCode : event.which;    \r\n");
      out.write("\r\n");
      out.write("\t    if ((event.shiftKey == false) && ((key  > 47 && key  < 58) || (key  > 95 && key  < 106)\r\n");
      out.write("\t    || key  == 35 || key  == 36 || key  == 37 || key  == 39  // 방향키 좌우,home,end  \r\n");
      out.write("\t    || key  == 8  || key  == 46 ) // del, back space\r\n");
      out.write("\t    ) {\r\n");
      out.write("\t        return true;\r\n");
      out.write("\t    }else {\r\n");
      out.write("\t        return false;\r\n");
      out.write("\t    }    \r\n");
      out.write("\t};\r\n");
      out.write("//-->\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("<table width=\"315\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("<form name=\"keygroup\" action=\"admin_keyword_prc.jsp\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"tg\" value=\"");
      out.print(part);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"mode\">\r\n");
      out.write("<input type=\"hidden\" name=\"xp\" value=\"");
      out.print(xp);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"yp\" value=\"");
      out.print(yp);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"zp\" value=\"");
      out.print(zp);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"sg_seqs\">\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td height=\"20\" style=\"padding-left:7px\"><img src=\"../../../images/admin/warning/admin_ico02.gif\" width=\"8\" height=\"5\" hspace=\"3\" align=\"absmiddle\"><span class=\"menu_black\"><strong>");
      out.print(value);
if(!part.equals("total")) out.print(" - ");
      out.print(partname);
      out.write("</strong></span></td>\r\n");
      out.write("  </tr>\r\n");
      out.write("</table>\r\n");
      out.write("<table width=\"315\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td align=\"right\" style=\"padding-left:10px\">\r\n");
      out.write("\t<table width=\"300\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("      <tr height=\"10\">\r\n");
      out.write("        <td><img src=\"../../../images/admin/warning/brank.gif\" width=\"1\" height=\"5\"></td>\r\n");
      out.write("      </tr>\r\n");

	if( part.equals("upkg") || part.equals("downkg") )
	{

      out.write("\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td><img src=\"../../../images/admin/warning/admin_ico03.gif\" width=\"10\" height=\"11\" hspace=\"3\">");
      out.print(partname);
      out.write("명</td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td height=\"25\"><input name=\"val\" type=\"text\" size=\"36\" value=\"");
      out.print(value);
      out.write("\" OnKeyDown=\"Javascript:if (event.keyCode == 13) { edit_kg();}\">\r\n");
      out.write("          <img src=\"../../../images/admin/warning/admin_modify.gif\" width=\"45\" height=\"18\" align=\"absmiddle\" onclick=\"edit_kg();\" style=\"cursor:hand;\"></td>\r\n");
      out.write("      </tr>\r\n");

	}

      out.write('\r');
      out.write('\n');

	if( part.equals("kg") )
	{

      out.write("\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td><img src=\"../../../images/admin/warning/admin_ico03.gif\" width=\"10\" height=\"11\" hspace=\"3\">제외단어 리스트</td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr height=\"3\">\r\n");
      out.write("        <td></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td valign=\"top\">\r\n");
      out.write("\t\t<select name=\"type\" multiple style=\"width:225px;height:180px;\" class=\"t\">\r\n");

		for(int i=0; i < exkeylist.size();i++) {
			KeywordBean exkeyinfo = (KeywordBean)exkeylist.get(i);

      out.write("\r\n");
      out.write("\t\t\t<option value=\"");
      out.print(exkeyinfo.getKGtype());
      out.write('"');
      out.write('>');
      out.print(exkeyinfo.getKGvalue());
      out.write("</option>\r\n");

		}

      out.write("\r\n");
      out.write("\t\t</select>&nbsp;<img src=\"../../../images/admin/warning/admin_del.gif\" width=\"45\" height=\"18\" border=\"0\" onclick=\"del_exkg();\" style=\"cursor:pointer; vertical-align: bottom\">\r\n");
      out.write("\t\t</td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      \r\n");
      out.write("      ");

      	if(k_op.equals("2")){
      
      out.write("\r\n");
      out.write("      \r\n");
      out.write("      <tr>\r\n");
      out.write("      \t<td style=\"padding-top: 5px\"><img src=\"../../../images/admin/warning/admin_ico03.gif\" width=\"10\" height=\"11\" hspace=\"3\">최대 인접 자릿수 </td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr height=\"5\">\r\n");
      out.write("        <td></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td valign=\"top\" style=\"padding-left: 20px\">최대 <input type=\"text\" name=\"nearLen\" style=\"width: 35px;text-align: right;\" maxlength=\"4\"  onkeydown=\"return onlyNumber(event)\" value=\"");
      out.print(near_len);
      out.write("\"> 자 &nbsp; <img src=\"../../../images/admin/warning/admin_save.gif\" style=\"vertical-align: middle; cursor: pointer;\" onclick=\"nearLenSave();\">&nbsp;&nbsp;<img src=\"../../../images/admin/warning/admin_all_save.gif\" style=\"vertical-align: middle; cursor: pointer;\" onclick=\"nearLenSaveAll();\"></td>\r\n");
      out.write("       </tr>\r\n");
      out.write("       ");
} 
      out.write(" \r\n");
      out.write("\r\n");

	} else {

      out.write("\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td>\r\n");
      out.write("\t\t<table width=\"100%\" height=\"200\" border=\"1\" cellspacing=\"0\" cellpadding=\"10\" bordercolor=\"#DDDDDD\">\r\n");
      out.write("\t\t  <tr >\r\n");
      out.write("\t\t    <td valign=\"top\">\r\n");
      out.write("\t\t    ");

		    	
		    	if(part.equals("upkg")){
		    	out.println("<table border='0' width='100%'>");
		    	out.println("	<tr>");
		    	out.println("	<td><table width='100%'><tr>");
		    	out.println("		<td><img src='../../../images/admin/warning/admin_ico03.gif' width='10' height='11' hspace='3'>사이트 그룹</td>");
		    	out.println("		<td align='right'><img src='../../../images/admin/warning/admin_save.gif' style='cursor:pointer;' onclick='saveSiteGroup();'></td>");
		    	out.println("	</tr></table></td>");
		    	out.println("	</tr>");
		    	out.println("	<tr><td><table width='100%'>");
		    	
		    	if(arSgSeq != null && arSgSeq.size() > 0){
		    		
		    		String[] arBean = null;
		    		for(int i = 0; i < arSgSeq.size(); i++){
		    			arBean = (String[])arSgSeq.get(i);
		    			
		    			if(i % 2 == 0){
		    				out.println("<tr>");
		    			}
		    			
		    			out.println("<td>");
		    			out.println("<input style='border:0px;' type='checkbox' name='sgSeq' value='"+arBean[0]+"' "+arBean[2]+">" + arBean[1]);
		    			out.println("</td>");
		    			
		    			if(i % 2 == 1){
		    				out.println("</tr>");
		    			}
		    		}
		    	
		    	}
		    	out.println("</table></td></tr>");
		    	
		    		
		    	out.print("</table>");	
		    	}else{
		    
      out.write("\r\n");
      out.write("\t\t\t\t<strong>키워드그룹 설정안내</strong><br><br>\r\n");
      out.write("\t\t\t\t<b>추가</b> - 좌측메뉴에서 선택한 뒤 하단의 추가버튼을 누르시면 하위 그룹 및 키워드가 추가됩니다.<br>\r\n");
      out.write("\t\t\t\t<b>수정</b> - 좌측메뉴에서 수정을 원하시는 그룹 및 키워드를 선택한뒤 우측메뉴에서 수정하시면 됩니다.<br>\r\n");
      out.write("\t\t\t\t<b>삭제</b> - 대분류,소분류,키워드의 삭제는 좌측메뉴에서 제외키워드의 삭제는 우측메뉴에서 가능합니다.<br>\r\n");
      out.write("\t\t\t\t<b>위치변경</b> - 변경을 원하시는 대분류를 선택한 뒤 좌측메뉴 하단의 상,하 버튼을 누르시면 됩니다.\t\t    \r\n");
      out.write("\t\t    \r\n");
      out.write("\t\t    ");
		
		    	}
		    
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t  </tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("      </tr>\r\n");

	}

      out.write("\r\n");
      out.write("    </table></td>\r\n");
      out.write("  </tr>\r\n");
      out.write("</form>\r\n");
      out.write("</table>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
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
