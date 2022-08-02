<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList,
				risk.admin.ex_site.ExSiteMng,
				risk.util.ParseRequest" 
%>

<%
	System.out.println("■-------------popUpContents_ex_site.jsp----------------■");
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	String CheckGroup = pr.getString("selectGroup"); // 'S' 나 'SG' 를 받아옴.
	String sg_seq = pr.getString("sg_seq","");
	String s_seq = pr.getString("s_seq","");
	String mode = pr.getString("mode","");
	
	System.out.println("CheckGroup : "+ CheckGroup);
	ExSiteMng eSMng = new ExSiteMng();
	ArrayList arSgSiteList = new ArrayList(); 
	ArrayList arSSiteList  = new ArrayList();
	
	
	//DB 쿼리
	if(CheckGroup.equals("SG")){
		arSgSiteList = eSMng.getSgSitetList();
	}else if(CheckGroup.equals("S") || CheckGroup.equals("site_URL")){
		arSSiteList = eSMng.getSitetList(sg_seq, s_seq, mode);	
	}
	
%>

<%
if(CheckGroup.equals("SG")){//SG 일때
	
%>

   <select style="width: 70px" name="<%=CheckGroup%>_SITE" id="<%=CheckGroup%>_SITE" onchange="selectSite(name, value)">
   <%	
   String sgTmp[];
   	for (int i=0 ; i < arSgSiteList.size() ; i++){
   		sgTmp = new String[3];
   		sgTmp = (String[])arSgSiteList.get(i);
   %>
   	<option title="<%=sgTmp[0] %>" value="<%=sgTmp[0]%>"><%=sgTmp[1]%></option>
   <%
   		sgTmp = null;
	}	
   %> 
   </select>

<%
}else if(CheckGroup.equals("S")){//S 일때

%>  	
	<select style="width: 100px;" name="<%=CheckGroup%>_SITE" id="<%=CheckGroup%>_SITE" onchange="selectSite(name, value)" >
   <%	
   String sTmp[];
   	for (int i=0 ; i < arSSiteList.size() ; i++){
   		sTmp = new String[5];
   		sTmp = (String[])arSSiteList.get(i);
   %>
   	<option value="<%=sTmp[1]%>"><%=sTmp[2]%></option>
   <%
   		sTmp = null;
	}	
   %> 
   </select>

<%
}else if(CheckGroup.equals("site_URL")){
	String urlTmp[] = new String[5];
	if(arSSiteList.size() > 0 ){
		urlTmp= (String[])arSSiteList.get(0);
%>
	
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="23%" ><strong>사이트 URL</strong></td>
		<td colspan="2" id="siteUrl" style="text-align: left"><%=urlTmp[3] %></td>
	</tr>
	<%-- <tr>
		<td width="23%"><strong>사이트 번호</strong></td>
		<td colspan="2" style="text-align: left"><%=urlTmp[1] %></td>
	</tr> --%>
	<tr>
		<td width="23%" style="padding: 3px 0px 0px 3px;" valign="top"><strong>제외 URL</strong></td>
		<td colspan="4"><textarea style="width: 100%;height: 100px;" id="text_include" style="text-align: left"></textarea></td>
	</tr>
</table>

<%
	}
}
%>

 
    
    