<%@page import="risk.util.StringUtil"%>
<%@page import="risk.dashboard.main.MainMgr"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.HashMap"%>
<%@page import="risk.util.Paging"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="risk.util.DateUtil"%>

<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
DateUtil du = new DateUtil();
String currentDate = du.getCurrentDate("yyyy-MM-dd");

	ParseRequest pr = new ParseRequest(request);
	pr.printParams();	
	MainMgr mgr = new MainMgr();
	StringUtil su = new StringUtil();
	String eDate = currentDate;

	//String sDate = pr.getString("sDate");
	//String eDate = pr.getString("eDate");
	String date = pr.getString("date");
	String mode = pr.getString("mode");
	String keyword_type = pr.getString("keyword_type");
	String search_keyword = pr.getString("search_keyword");
	String company_code = pr.getString("company_code");

	
	int dataSize = 5; 
	int pageNum = pr.getInt("pageNum",1);			//페이지번호
	int sLimit = (pageNum-1) * dataSize;			//시작 limit
	
	int totalCount = mgr.getPortalReplyCount(date, company_code, keyword_type, search_keyword);
	ArrayList<HashMap<String, String>> dataList = mgr.getPortalReplyList(date, sLimit, keyword_type, search_keyword, company_code, dataSize);
	Paging p = new Paging(pageNum, totalCount, dataSize);
	
	String f_md_seq = "";
	String f_title = "";
	String f_site_name = "";
	
%>
    <div class="wrap">                             
        <table>
            <colgroup>
                <col>
                <col style="width: 15%"> 
                <col style="width: 15%">
                <col style="width: 10%">
            </colgroup>
            <thead>
              <tr>
                <th><span>제목</span></th>
                <th><span>댓글</span></th>
                <th><span>포탈구분</span></th>
                <th><span>일시</span></th>
              </tr>
            </thead>
              <tbody id="potalreply_list">
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
   						
   						if(i ==0){
   							f_md_seq = map.get("MD_SEQ");
   		                    f_title = title;
   		                    f_site_name = map.get("SITE_NAME");
   						}
	            	   
            	%>
                      <tr id="reply_<%=map.get("MD_SEQ")%>" class="title">
                          <td class="title">
                              <button type="button" onclick="main.updateAjax_con_800('<%=map.get("MD_SEQ")%>','<%=title%>','<%=map.get("SITE_NAME")%>');return false;" class="ui_btn small btn_anal" title="댓글 분석">&#xe063;</button>
                              <a href="<%=map.get("URL")%>" class="lnk" target="_blank" title="새창에서 링크 열기" onclick="goToUrl('<%=map.get("URL")%>'); return false;"><span><%=map.get("TITLE")%></span></a>
                          </td>
                          <td>
                              <span>
                                  <button type="button" class= "reply_popup" name="reply_popup" value="<%=map.get("MD_SEQ")%>" class="lnk" target="_blank" title="<%=su.digitFormat(map.get("TOTAL_CNT")) %>" onclick="popupMngr.open( '#popup_rel_info' ); return false;"><span><%=su.digitFormat(map.get("TOTAL_CNT")) %></span></button>
                                  <span>(<span class="fc_postive"><%=su.digitFormat(map.get("POS_CNT")) %></span>/<span class="fc_negative"><%=su.digitFormat(map.get("NEG_CNT")) %></span>/<span class="fc_neutral"><%=su.digitFormat(map.get("NEU_CNT")) %></span>)</span>
                              </span>
                          </td>
                          <td><span title="<%=map.get("SITE_NAME")%>"><%=map.get("SITE_NAME")%></span></td>
                          <td><span><%=map.get("DATE")%></span></td>
                      </tr>	            	
 				<%}else if(i >= dataList.size()){%>
                  	  <tr><td colspan="4" class="no_over"></td></tr>						                      	
            	<%	}
            	   }
              	 }
            	%>
          
              </tbody>
          </table>
          <script>
	      	function goToUrl(url){
	    		
	    		var openUrl = "http://hub.buzzms.co.kr?url=" + encodeURIComponent(url);
	    		window.open(openUrl);
	    	}          
          </script>
			</div>        
          <input type="hidden" name="reply_md_seq" id="reply_md_seq" value="<%=f_md_seq%>"/>
  	 	  <input type="hidden" name="reply_title" id="reply_title" value="<%=f_title%>"/>
  	      <input type="hidden" name="reply_site_name" id="reply_site_name" value="<%=f_site_name%>"/>
          
          <div class="footer is-only-web">
              <div class="ui_paginate">
                  <div class="in_wrap">
                  <%	
                  	String scriptFuntionName="";
					if(mode.equals("portalReply")){
						scriptFuntionName = "main.updateAjax_con_80";
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
	                      <a href="#" class="page_next disabled" onclick="return false;">다음페이지</a>-->
                  </div>
              </div>
          </div>