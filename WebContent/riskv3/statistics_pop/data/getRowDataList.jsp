<%@page import="risk.statistics_pop.StatisticsPopMgr"%>
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
	StatisticsPopMgr mgr = new StatisticsPopMgr();
	
	String sDate = pr.getString("sDate");
	String eDate = pr.getString("eDate");
	String psDate =pr.getString("PsDate");
	String peDate = pr.getString("PeDate");
	
	String settingCode = pr.getString("settingCode");
	String orderBy = pr.getString("orderBy"); //정렬	
	String sg_code = pr.getString("site_group_code");
	
	String type = pr.getString("type");
	String code = pr.getString("code");
	String type2 = pr.getString("type2");
	String code2 = pr.getString("code2");
	
	int dataSize = 10; 
	int pageNum = pr.getInt("pageNum",1);			//페이지번호
	int sLimit = (pageNum-1) * dataSize;			//시작 limit
	
	String[] printType = new String[]{"11","12","13","14","6","7","8","9","20","21"}; //제품군-11,제품군2-12, 제품군3-13, 제품군4-14,게시1-6,게시2-7,게시3-8,온라인클레임-9,온라인유포언급-20,온라인이슈등급-21
	
	int totalCount = mgr.getRowDataCount( type,  code, type2, code2, sDate,  eDate,  psDate, peDate, settingCode,  sg_code);
	ArrayList<HashMap<String, String>> dataList = mgr.getRowDataList( type,  code, type2, code2, sDate,  eDate,  psDate, peDate, settingCode,  sg_code, sLimit, printType);
	Paging p = new Paging(pageNum, totalCount, dataSize);
	
	int colSize = 5+printType.length;
%>
 <div class="ui_brd_list">
            <%-- <div class="infos">
                <div class="rc">
                    <span class="page_info"><strong><span><%=p.getTotalDataCount()%></span>건</strong>, <span><span><%=p.getPageNo()%></span>/<span><%=p.getTotalPage()%></span> Page</span></span>
                </div>
            </div> --%>
            <div class="wrap" style="overflow: auto">
                <table>
                    <caption>목록</caption>
                    <colgroup>
                     <col style="width:50px">
                     <col style="width:100px">
                     <col style="width:250px">
                     <col style="width:150px">
                     <col style="width:100px">
                     <col style="width:80px">
                     <col style="width:80px">
                     <col style="width:80px">
                     <col style="width:80px">
                     <col style="width:80px">
                     <col style="width:80px">
                     <col style="width:80px">
                     <col style="width:80px">
                     <col style="width:80px">
                     <col style="width:80px">
                     <col style="width:80px">
                    </colgroup>
                    <thead>
                        <tr>
                           <th><strong></strong></th>
                           <th><strong>출처</strong></th>
                           <th><strong>제목</strong></th>
                           <th><strong>URL</strong></th>
                           <th><strong>수집시간</strong></th>
                           <th><strong>제품군 1</strong></th>
                           <th><strong>제품군 2</strong></th>
                           <th><strong>제품군 3</strong></th>
                           <th><strong>제품군 4</strong></th>
                           <th><strong>게시물<br>유형 1</strong></th>
                           <th><strong>게시물<br>유형 2</strong></th>
                           <th><strong>게시물<br>유형 3</strong></th>
                           <th><strong>온라인이슈<br>클레임인입</strong></th>
                           <th><strong>온라인이슈<br>유포언급</strong></th>
                           <th><strong>온라인이슈<br>이슈등급</strong></th>
                           <th><strong>등록자</strong></th>
                        </tr>
                    </thead>
                    <tbody>
                    <%if(totalCount == 0){%>	
                    	<tr><td colspan="<%=colSize%>" class="no_over"></td></tr>
                        <tr><td colspan="<%=colSize%>" class="no_over"></td></tr>
                        <tr><td colspan="<%=colSize%>" class="no_over"></td></tr>
                        <tr><td colspan="<%=colSize%>" class="no_over"></td></tr>
                        <tr><td colspan="<%=colSize%>" class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td></tr>
                        <tr><td colspan="<%=colSize%>" class="no_over"></td></tr>
                        <tr><td colspan="<%=colSize%>" class="no_over"></td></tr>
                        <tr><td colspan="<%=colSize%>" class="no_over"></td></tr>
                        <tr><td colspan="<%=colSize%>" class="no_over"></td></tr>
                        <tr><td colspan="<%=colSize%>" class="no_over"></td></tr>
                        
                        
                    <%}else{
                    	HashMap<String, String> map = new HashMap<String, String>();
                    	String eTitle ="";
                    	String title ="";
                    	String s_seq ="";
                    	for(int i=0; i<dataList.size(); i++){
                    		map = dataList.get(i);
                    		eTitle = URLEncoder.encode(map.get("TITLE").toString(),"UTF-8").replaceAll("\\+", "%20");
    						title = map.get("TITLE").toString().replaceAll("\"", "").replaceAll("'", "");
    						s_seq = map.get("S_SEQ");
                    	%>          
                    	          		
                   		<tr>
                   		<td><%=i+1%></td>
                        <td><span title="<%=map.get("MD_SITE_NAME")%>"><%=map.get("MD_SITE_NAME")%></span></td>
                        <td class="ui_al">
                       			<span style="display: <%="3555".equals(s_seq) ? "" : "4943".equals(s_seq) ? "" : "none" %>;">
								<a href="#" onclick="javascipt:devel.hrefPop('','<%=s_seq%>','<%=eTitle%>'); return false;" target="_blank" class="ui_bullet cafe" title="새창에서 카페 열기">cafe</a>
								</span>
                       			<a href="<%=map.get("URL")%>" class="lnk" target="_blank" title="<%=map.get("TITLE")%>"><strong><%=map.get("TITLE")%></strong>
                       			</a>
                       	</td>
                        <td class="ui_al">
                       			<span style="display: <%="3555".equals(s_seq) ? "" : "4943".equals(s_seq) ? "" : "none" %>;">
								<a href="#" onclick="javascipt:devel.hrefPop('','<%=s_seq%>','<%=eTitle%>'); return false;" target="_blank" class="ui_bullet cafe" title="새창에서 카페 열기">cafe</a>
								</span>
                       			<a href="<%=map.get("URL")%>" class="lnk" target="_blank" title="새창에서 링크 열기"><strong><%=map.get("URL")%></strong>
                       			</a>
                       	</td>
                        <td><span title="<%=map.get("MD_DATE")%>"><%=map.get("SIMPLE_DATE")%></span></td>
                        <%for(int s=0; s<printType.length; s++){%>
                        	<td><span title="<%=map.get("TYPE"+printType[s])%>"><%=map.get("TYPE"+printType[s])%></span></td>	
                        <%} %>
                        <td><span title="<%=map.get("WRITTER")%>"><%=map.get("WRITTER")%></span></td>
                        </tr>
                   <% }
                    	int tmp_row = dataSize - dataList.size();
                    	if(tmp_row>0){
                    		for(int t=0; t<tmp_row; t++){
                    			%>
                    			<tr><td colspan="<%=colSize%>" class="no_over"></td></tr>
                    			<%
                    		}
                    	}
                    	
                    	
                    }
                    %>
                    
                    </tbody>
                </table>
            </div>
            <div class="footer">
                <div class="ui_paginate">
                    <div class="in_wrap">
                    <%	String scriptFuntionName="";
						scriptFuntionName = "statistics.updateAjax_con_31";
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