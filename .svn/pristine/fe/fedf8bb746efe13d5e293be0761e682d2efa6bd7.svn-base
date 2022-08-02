<%@page import="risk.dashboard.main.MainMgr"%>
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
	MainMgr mgr = new MainMgr();
	
	String sDate = pr.getString("sDate");
	String eDate = pr.getString("eDate");
	String mode = pr.getString("mode");
	String keyword_type = pr.getString("keyword_type");
	String search_keyword = pr.getString("search_keyword");
	String company_code = pr.getString("company_code");

	
	int dataSize = 5; 
	int pageNum = pr.getInt("pageNum",1);			//페이지번호
	int sLimit = (pageNum-1) * dataSize;			//시작 limit
	
	int totalCount = mgr.getPortalCount(sDate, eDate, keyword_type, company_code, search_keyword);
	ArrayList<HashMap<String, String>> dataList = mgr.getPortalDataList(sDate, eDate, sLimit, keyword_type, search_keyword, company_code, dataSize);
	Paging p = new Paging(pageNum, totalCount, dataSize);
%>
 <div class="ui_brd_list">
<%--        <div class="infos">
                <div class="rc">
                    <span class="page_info"><strong><span><%=p.getTotalDataCount()%></span>건</strong>, <span><span><%=p.getPageNo()%></span>/<span><%=p.getTotalPage()%></span> Page</span></span>
                </div>  
            </div>	--%>
            <div class="wrap">
                <table>
                    <caption>목록</caption>
                    <colgroup>
                          <col>
                          <col style="width: 15%">
                          <col style="width: 15%">
                          <col style="width: 14%">
                    </colgroup>
                    <thead>
                        <tr>
                          <th><span>제목</span></th>
                          <th><span>출처</span></th>
                          <th><span>영역</span></th>
                          <th><span>최초노출일시</span></th>
                        </tr>
                    </thead>
                    <tbody>
                    <%if(totalCount == 0){%>	
                          <tr><td colspan="4" class="no_over"></td></tr>
                          <tr><td colspan="4" class="no_over"></td></tr>
                          <tr><td colspan="4" class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td></tr>
                          <tr><td colspan="4" class="no_over"></td></tr>
                          <tr><td colspan="4" class="no_over"></td></tr>
                    <%}else{
                    	HashMap<String, String> map = new HashMap<String, String>();
                    	String eTitle ="";
                    	String title ="";
                    	String s_seq ="";
     	               for(int i=0; i< dataSize; i++){
    	            	   if(i < dataList.size()){
                    		map = dataList.get(i);
                    		eTitle = URLEncoder.encode(map.get("TITLE").toString(),"UTF-8").replaceAll("\\+", "%20");
    						title = map.get("TITLE").toString().replaceAll("\"", "").replaceAll("'", "");
    						s_seq = map.get("S_SEQ");
                    	%>                    		
                    	<tr>
                       		<td class="title">
<%--                        	<span style="display: <%="3555".equals(s_seq) ? "" : "4943".equals(s_seq) ? "" : "none" %>;">
								<a href="#" onclick="javascipt:devel.hrefPop('','<%=s_seq%>','<%=eTitle%>'); return false;" target="_blank" class="ui_bullet cafe" title="<%=map.get("TITLE")%>">cafe</a>
								</span> --%>
                       			<a href="<%=map.get("URL")%>" class="lnk" target="_blank" title="<%=map.get("TITLE")%>" onclick="goToUrl('<%=map.get("URL")%>'); return false;"><span><%=map.get("TITLE")%></span>        
                       			</a>
                       		</td>
                        	<td><span title="<%=map.get("SITE_NAME")%>"><%=map.get("SITE_NAME")%></span></td>
                      	  	<td><span title="<%=map.get("MENU_NAME")%>"><%=map.get("MENU_NAME")%></span></td>
                        	<td><%=map.get("DATE")%></td>
                   		</tr>	
 				    <%}else if(i >= dataList.size()){%>
                      	  <tr><td colspan="4" class="no_over"></td></tr>						                      	
            	    <%	}
            	       }
              	     }
            	    %>
                       
                   <!--  <tr>
                        <td><span title="한국전력공사">한국전력공사</span></td>
                        <td><span title=""></span></td>
                        <td class="ui_al"><a href="#" class="lnk" target="_blank" title="새창에서 링크 열기"><strong>D-N 변속시 충격/소음</strong></a></td>
                        <td>2019-01-01 15:34:05</td>
                    </tr>
                    <tr><td colspan="4" class="no_over"></td></tr>
                    <tr><td colspan="4" class="no_over"></td></tr>
                    <tr><td colspan="4" class="no_over"></td></tr>
                    <tr><td colspan="4" class="no_over"></td></tr>
                    <tr><td colspan="4" class="no_over"></td></tr>
                    <tr><td colspan="4" class="no_over"></td></tr>
                    <tr><td colspan="4" class="no_over"></td></tr>
                    <tr><td colspan="4" class="no_over"></td></tr>
                    <tr><td colspan="4" class="no_over"></td></tr> -->
                    </tbody>
                </table>
		        <script>
			      function goToUrl(url){
			    	var openUrl = "http://hub.buzzms.co.kr?url=" + encodeURIComponent(url);
			    	window.open(openUrl);
			       }          
		        </script>
            </div>
            <div class="footer is-only-web">
                <div class="ui_paginate">
                    <div class="in_wrap">
                    <%	String scriptFuntionName="";
					if(mode.equals("portalTop")){
						scriptFuntionName = "main.updateAjax_con_70";
					}
					%>
					
					<%=p.isPrevPageGroup() ? "<a href=\"#\" onclick=\""+scriptFuntionName+"( "+p.getPrevPageNo()+");return false;\" class=\"page_prev\">" : "<a href=\"#\" class=\"page_prev ui_disabled\">이전페이지</a>" %>
				<%for(int i = p.getPageGroupStartNo(); i <= p.getPageGroupEndNo(); i++){
					String styleClassName = p.getPageNo() == i ? "active":"";
					%>
				<%="<a href=\"#\" class=\""+styleClassName+"\"  onclick=\""+scriptFuntionName+"("+i+");return false;\">"+i+"</a>"%>
				<%}%>
				<%=p.isNextPageGroup() ? "<a href=\"#\" onclick=\""+scriptFuntionName+"( "+p.getNextPageNo()+" );return false;\" class=\"page_next disabled\">" : "<a href=\"#\" class=\"page_next ui_disabled\">다음페이지</a>"%>
                       <!--  <a href="#" class="page_prev ui_disabled" onclick="return false">이전페이지</a>
                        <a href="#" class="" onclick="return false;">1</a>
                        <a href="#" class="active" onclick="return false;">2</a>
                        <a href="#" onclick="return false;">3</a>
                        <a href="#" onclick="return false;">4</a>
                        <a href="#" onclick="return false;">5</a>
                        <a href="#" class="page_next disabled" onclick="return false;">다음페이지</a> -->
                    </div>
                </div>
            </div>
        </div>