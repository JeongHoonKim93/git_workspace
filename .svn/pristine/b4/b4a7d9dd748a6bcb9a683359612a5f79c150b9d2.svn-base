<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList
                 ,risk.util.StringUtil
                 ,risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,risk.search.solr.SolrSearch
                 ,risk.search.solr.SearchForm
                 ,risk.search.userEnvInfo"
%>
<%@include file="../../inc/sessioncheck.jsp" %>

<%

	DateUtil du = new DateUtil();
    String sCurrDate    = du.getCurrentDate();

    //페이지에 사용할 변수 선언 부분
    StringUtil      su = new StringUtil();
    ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
    
    SolrSearch solr = new SolrSearch();
    SearchForm Smgr = new SearchForm();
	
    String reSearchKey = pr.getString("reSearchKey","");
    String searchDate = pr.getString("searchDate","").replaceAll("-","");
    String listSgroup = pr.getString("listSgroup","");
    String sort = pr.getString("sort","");
    String sort_order = pr.getString("sort_order","");
    
    ArrayList arData =  solr.getDataExcel(reSearchKey, searchDate, listSgroup, sort, sort_order, 1000, SS_M_ID);  
    	
%>


    <table width="650" border="1" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 출처     </strong></td>
        <td width="250" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 제목     </strong></td>
        <td width="200" align="center" bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> URL     </strong></td>
        <td width="100" align="center"  bgcolor="#C0C0C0"  style="padding: 3px 0px 0px 0px;"> <strong> 수집시간 </strong></td>
      </tr>
    </table>
    <%
    	//solr.getDataExcel();
   		for( int i = 0 ; i < arData.size() ; i++ ) {
   			SearchForm mi = (SearchForm) arData.get(i);
    %>
    <table width="800" border="1" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=mi.getName()%>
        </td>
        <td width="250" align="left" style="padding: 3px 0px 0px 5px;">
          <%=su.nvl(mi.getTitle(),"제목없음")%>
        </td>
        <td width="200" align="left" style="padding: 3px 0px 0px 5px;">
          <%=mi.getUrl()%>
        </td>
        <td width="100" align="center" class="menu_gray" style="padding: 3px 0px 0px 0px;">
          <%=DateUtil.addHour(mi.getSdate()+mi.getStime(),9,"yyyyMMddHHmmss","MM-dd HH:mm")%>
        </td>
      </tr>
    </table>
    <%  }%>
</html>