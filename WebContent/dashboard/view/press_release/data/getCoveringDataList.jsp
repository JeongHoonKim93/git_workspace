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
	String ic_code = pr.getString("ic_code");
	String sg_seq = pr.getString("sg_seq");
	String senti = pr.getString("senti");
	String mode = pr.getString("mode");
	
	String dayDiff = mgr.getDayDiff(sDate, eDate); 
	if("0".equals(dayDiff)){dayDiff="1";}	
	
	
	int dataSize = 10; 
	int pageNum = pr.getInt("pageNum",1);			//페이지번호
	int sLimit = (pageNum-1) * dataSize;			//시작 limit
	
	int totalCount = mgr.getCoverindDataCount(sDate, eDate, ic_code, sg_seq, senti);
	ArrayList<HashMap<String, String>> dataList = mgr.getCoverindDataList(sDate, eDate, ic_code, sg_seq, senti, sLimit);
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
              <table>
                  <colgroup>
                  <col style="width:110px">
                  <col style="width:118px">
                  <col>
                  <col style="width:128px">
                  <col style="width:92px">
                  <col style="width:100px">
                  </colgroup>
                  <thead>
                      <tr>
                          <th scope="col"><span>일자</span></th>
                          <th scope="col"><span>채널</span></th>
                          <th scope="col"><span>제목</span></th>
                          <th scope="col"><span>출처</span></th>
                          <th scope="col"><span>기자명</span></th>
                          <th scope="col"><span>성향</span></th>
                      </tr>
                  </thead>
                  <tbody>
                   <%if(totalCount == 0){%>	
                      <!-- 데이터 없는 경우  -->
                      <tr><td colspan="6" class="no_over"></td></tr>
                      <tr><td colspan="6" class="no_over"></td></tr>
                      <tr><td colspan="6" class="no_over"></td></tr>
                      <tr><td colspan="6" class="no_over"></td></tr>
                      <tr><td colspan="6" class="no_over no_data"><span>데이터가 없습니다.</span></td></tr>
                      <tr><td colspan="6" class="no_over"></td></tr>
                      <tr><td colspan="6" class="no_over"></td></tr>
                      <tr><td colspan="6" class="no_over"></td></tr>
                      <tr><td colspan="6" class="no_over"></td></tr>
                      <tr><td colspan="6" class="no_over"></td></tr> 
                   <%}else{
                	   HashMap<String, String> map = new HashMap<String, String>();
	                   String eTitle ="";
	                   String title ="";
	                   String s_seq ="";
	                   
	                   String senttStyle = "";
	                   for(int i=0; i<dataList.size(); i++){
	                   		 
	                   		map = dataList.get(i);
                   			eTitle = URLEncoder.encode(map.get("ID_TITLE").toString(),"UTF-8").replaceAll("\\+", "%20");
   							title = map.get("ID_TITLE").toString().replaceAll("\"", "").replaceAll("'", "");
   							
   							if(i ==0){
   								f_md_seq = map.get("MD_SEQ");
   			                    f_title = title;
   			                    f_site_name = map.get("SITE_NAME");
   							}
	                	   
   							if(map.get("ID_SENTI").equals("긍정")) {
   								senttStyle = "fc_positive"; 
   							} else if(map.get("ID_SENTI").equals("부정")) {
   								senttStyle = "fc_negative"; 
   							} else {
   								senttStyle = "fc_neutral"; 
   								
   							}
                	%>
		                  <tr>
	                          <td><%=map.get("MD_DATE") %></td>
	                          <td><%=map.get("CHANNEL") %></td>
	                          <td class="ui_al">
	                <%
	                			  if("3555".equals(map.get("S_SEQ")) || "4943".equals(map.get("S_SEQ"))) {
	                %>
	                			  <a href="#" onclick="javascipt:devel.hrefPop('','<%=map.get("S_SEQ")%>','<%=map.get("ID_TITLE").toString()%>'); return false;" class="lnk" target="_blank" title="<%=title %>">
	                			  	  <span><%=title%></span>
	                			  </a>	  
	                <%
	                			  } else {
	                %>
	                          	  <a href="http://hub.buzzms.co.kr?url=<%=URLEncoder.encode(map.get("ID_URL"),"UTF-8")%>" class="lnk " target="_blank" title="<%=title %>">
	                          	  	  <span><%=title%></span>
	                          	  </a>
	                
	                <%				  
	                			  }
	                %>          	 	
	                          </td>
	                          <td><%=map.get("MD_SITE_NAME") %></td>
	                          <td><%=map.get("MD_WRITER") %></td>
	                          <td><div class="ui_label <%=senttStyle%>"><span><%=map.get("ID_SENTI") %></span></div></td>
	                      </tr>
	                	
                	<% }
	                   
	                      for(int i=0; i < 10 - dataList.size(); i++) {
	                      %>
	                      <tr><td colspan="6" class="no_over"></td></tr>
	                      <%	  
	                      }
                  	 }
                	%>
                     
                  </tbody>
              </table>
          </div>
          <%--  <input type="hidden" name="reply_md_seq" id="reply_md_seq" value="<%=f_md_seq%>"/>
  	 	  <input type="hidden" name="reply_title" id="reply_title" value="<%=f_title%>"/>
  	      <input type="hidden" name="reply_site_name" id="reply_site_name" value="<%=f_site_name%>"/> --%>
          <script>
              function trActive( el ){
                  $( el ).parents( "table" ).find( "tr" ).removeClass( "active" );
                  $( el ).parents( "tr" ).addClass( "active" );
                  
                  press_release.updatePageDetail();
              }
          </script>
          <div class="footer">
              <div class="ui_paginate">
                  <div class="in_wrap">
                  <%	
                  	String scriptFuntionName="";
					if(mode.equals("coveringDataList")){
						scriptFuntionName = "press_release.updateAjax_con_40";
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
	                      
                     <!--  <a href="#" class="page_prev prev ui_disabled" onclick="return false"></a>
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
