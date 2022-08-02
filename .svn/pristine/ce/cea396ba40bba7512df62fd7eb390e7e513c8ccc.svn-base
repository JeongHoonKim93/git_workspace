<%@page import="risk.util.StringUtil"%>
<%@page import="risk.dashboard.view.press_release.PressReleaseMgr"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.HashMap"%>
<%@page import="risk.util.Paging"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();	
	PressReleaseMgr mgr = new PressReleaseMgr();
	StringUtil su = new StringUtil();
	
	String sDate = pr.getString("i_sdate");
	String eDate = pr.getString("i_edate");
	String date = pr.getString("date");
	String mode = pr.getString("mode");

	String ic_code = "";
	
	int dataSize = 10; 
	int pageNum = pr.getInt("pageNum",1);			//페이지번호
	int sLimit = (pageNum-1) * dataSize;			//시작 limit
	
	
	int totalCount = mgr.getPressDataListCount(sDate,eDate);
	ArrayList<HashMap<String, String>> dataList = mgr.getPressDataList(sDate, eDate, sLimit);
	Paging p = new Paging(pageNum, totalCount, dataSize);
	
	
	String f_md_seq = "";
	String f_title = "";
	String f_site_name = "";
	
%>
          <div class="header is-pad-t-7 no_bg">
              <div class="lc">
                  <%-- <span class="page_info"><strong><span><%=p.getTotalDataCount()%></span>건</strong>, <span><span><%=p.getPageNo()%></span>/<span><%=p.getTotalPage()%></span> Page</span></span> --%>
                  <span class="page_info">Total: <span class="cnt"><%=p.getTotalDataCount() %></span>
                  <span class="page">(<em><%=p.getPageNo() %></em>/<%=p.getTotalPage() %>p)</span></span>
              </div>
          </div>
          <div class="wrap">
              <table id="data">
                  <colgroup>
	                  <col style="width: 88px;">
	                  <col style="width: 109px;">
	                  <col>
	                  <col style="width: 136px;">
	                  <col style="width: 88px;">
                  </colgroup>
                  <thead>
                      <tr>
                          <th>담당부서</th>
                          <th>배포일자</th>
                          <th>보도자료명</th>
                          <th>최초출처</th>
                          <th>확산량</th>
                      </tr>
                  </thead>
                  <tbody>
                   <%if(totalCount == 0){%>	
                      <!--  데이터 없는 경우 --> 
                      <tr><td colspan="5" class="no_over"></td></tr>
                      <tr><td colspan="5" class="no_over"></td></tr>
                      <tr><td colspan="5" class="no_over"></td></tr>
                      <tr><td colspan="5" class="no_over"></td></tr>
                      <tr><td colspan="5" class="no_over"></td></tr>
                      <tr><td colspan="5" class="no_over no_data in_list">키워드가 없습니다.</td></tr>
                      <tr><td colspan="5" class="no_over"></td></tr>
                      <tr><td colspan="5" class="no_over"></td></tr>
                      <tr><td colspan="5" class="no_over"></td></tr>
                      <tr><td colspan="5" class="no_over"></td></tr>
                   <%}else{
                	   HashMap<String, String> map = new HashMap<String, String>();
	                   String part ="";
	                   String eTitle ="";
	                   String title ="";
	                   
	                   for(int i=0; i<dataList.size(); i++){
	                   		 
	                   		map = dataList.get(i);
                   			eTitle = URLEncoder.encode(map.get("IC_NAME").toString(),"UTF-8").replaceAll("\\+", "%20");
   							title = map.get("IC_NAME").toString().replaceAll("\"", "").replaceAll("'", "");
   							
   							if(map.get("PART") != null && map.get("PART") != "") {
   								part = map.get("PART");
   							}
   							
   							String style="";
   							if(i==0) {
   								style = "active";	
   							}
	                	   
                	%>
	                	  <tr class="<%=style%>">
		                      <td><%=part %></td>
		                      <td><%=map.get("REG_DATE") %></td>
		                      <td class="ui_al"><a href="#" class="lnk" onclick="trActive( this); press_release.getIc_code(); return false;"><span class="txt"><%=title %></span>
		                      	<input type="hidden" name="ic_code" id="ic_code" value="<%=map.get("IC_CODE") %>">
		                      </a></td>
		                      <td><%=map.get("MD_SITE_NAME") %></td>
		                      <td><%=map.get("CNT") %></td>
                      	  </tr>
                	<% }
	                      for(int i=0; i < 10 - dataList.size(); i++) {
	                      %>
	                      <tr><td colspan="5" class="no_over"></td></tr>
	                      <%	  
	                      }
                  	 }
                	%>
                  </tbody>
              </table>
          </div>
          <script>
              function trActive( el){
                  $( el ).parents( "table" ).find( "tr" ).removeClass( "active" );
                  $( el ).parents( "tr" ).addClass( "active" );
              }
              
          </script>
          <div class="footer">
              <div class="ui_paginate">
                  <div class="in_wrap">
                    <%	
                  	String scriptFuntionName="";
					if(mode.equals("dataList")){
						scriptFuntionName = "press_release.updateAjax_con_10";
					}
					%>
					<%=p.getTotalPage() > 0 && p.getPageNo() != 1  ? "<a href=\"#\" onclick=\""+scriptFuntionName+"("+1+");return false;\" class=\"page_prev prev\">" : "<a href=\"#\" class=\"page_prev prev ui_disabled\"></a>" %>
					<%=p.isPrevPageGroup() ? "<a href=\"#\" onclick=\""+scriptFuntionName+"("+p.getPrevPageNo()+");return false;\" class=\"page_prev\">" : "<a href=\"#\" class=\"page_prev ui_disabled\"></a>" %>
						<%for(int i = p.getPageGroupStartNo(); i <= p.getPageGroupEndNo(); i++){
							String styleClassName = p.getPageNo() == i ? "active":"";
							%>
							<%="<a href=\"#\" class=\""+styleClassName+"\"  onclick=\""+scriptFuntionName+"("+i+");return false;\">"+i+"</a>"%>
						<%}%>
					<%=p.isNextPageGroup() ? "<a href=\"#\" onclick=\""+scriptFuntionName+"("+p.getNextPageNo()+");return false;\" class=\"page_next\">" : "<a href=\"#\" class=\"page_next ui_disabled\"></a>"%>
                  	<%=p.getTotalPage() > 0 && p.getPageNo() != p.getTotalPage() ? "<a href=\"#\" onclick=\""+scriptFuntionName+"("+p.getTotalPage()+");return false;\" class=\"page_next next\">" : "<a href=\"#\" class=\"page_next next ui_disabled\"></a>" %>
                      <!-- <a href="#" class="page_prev prev ui_disabled" onclick="return false"></a>
                      <a href="#" class="page_prev ui_disabled" onclick="return false"></a>
                      <a href="#" class="" onclick="return false;">1</a>
                      <a href="#" class="active" onclick="return false;">2</a>
                      <a href="#" onclick="return false;">3</a>
                      <a href="#" onclick="return false;">4</a>
                      <a href="#" onclick="return false;">5</a>
                      <a href="#" onclick="return false;">6</a>
                      <a href="#" onclick="return false;">7</a>
                      <a href="#" onclick="return false;">8</a>
                      <a href="#" onclick="return false;">9</a>
                      <a href="#" onclick="return false;">10</a>
                      <a href="#" class="page_next disabled" onclick="return false;"></a>
                      <a href="#" class="page_next next disabled" onclick="return false;"></a> -->
                  </div>
              </div>
          </div>
