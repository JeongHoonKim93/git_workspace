
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList
                 ,risk.util.StringUtil
                 ,risk.util.ParseRequest
				 ,risk.util.DateUtil
				 ,risk.search.MetaMgr
				 ,risk.search.DomainKeywordMgr
				 ,risk.search.MetaBean
				 ,risk.search.userEnvInfo"
%>
<%@include file="../inc/sessioncheck.jsp" %>
<%
	//페이지에 사용할 변수 선언 부분
    StringUtil      su = new StringUtil();
    ParseRequest    pr = new ParseRequest(request);
    MetaMgr     smgr = new MetaMgr();
    DomainKeywordMgr     dkmgr = new DomainKeywordMgr();
    DateUtil 	    du = new DateUtil();

    String nowpage = pr.getString("nowpage");
    String md_seqs = pr.getString("SaveList");
    String st_name = pr.getString("st_name","");
    String subMode = pr.getString("subMode","");
    String comment = "";
    
   	pr.printParams();  
   	
	if(subMode.equals("domain")) {
		dkmgr.updateTempIssueReg(md_seqs);
	} else {
		smgr.updateTempIssueReg(md_seqs);
	}
	
	String script_str = "";
	
	String[] md_seq_list = md_seqs.split(",");
	
	for(int i=0; i<md_seq_list.length; i++){
		script_str += "var obj = parent.document.getElementById('issue_menu_icon"+md_seq_list[i]+"'); \n obj.src='../../images/search/btn_manage_temp.gif';\n obj.title='임시 이슈로 등록된 정보입니다.';\n obj.setAttribute(\"onClick\",\"send_issue('insert','"+md_seq_list[i]+"')\");\n";
	}
%>
<!-- 
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"> -->
<script>
  		<%-- parent.document.location = "search_main_contents.jsp?nowpage=<%=nowpage%>"; --%>
  		<%=script_str%>
</script>
<!-- </body>
</html> -->