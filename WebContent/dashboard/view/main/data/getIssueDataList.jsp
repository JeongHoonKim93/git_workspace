<%@page import="risk.util.StringUtil"%>
<%@page import="risk.dashboard.main.MainMgr"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.HashMap"%>
<%@ page import="risk.util.ConfigUtil" %>
<%@page import="risk.util.Paging"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();	
	MainMgr mgr = new MainMgr();
	ConfigUtil cu = new ConfigUtil();
	StringUtil su = new StringUtil();
		
	String sDate = pr.getString("sDate");
	String eDate = pr.getString("eDate");
	String mode = pr.getString("mode");
	String company_code = pr.getString("company_code");
	String issue_code = pr.getString("issue_code");
	String sg_seqs = pr.getString("sg_seqs");
	String keyword_type = pr.getString("keyword_type");
	String search_keyword = pr.getString("search_keyword");

	//이미지 경로
	String siteUrl = cu.getConfig("URL");
//	String imgUrl = siteUrl+"riskv3/report/img/";
	String imgUrl = siteUrl+"dashboard/asset/img/";
	
	int dataSize = 5; 
	int pageNum = pr.getInt("pageNum",1);			//페이지번호
	int sLimit = (pageNum-1) * dataSize;			//시작 limit
	
	int totalCount = mgr.getIssueCount(sDate, eDate, company_code, issue_code, sg_seqs, keyword_type, search_keyword);
	ArrayList<HashMap<String, String>> dataList = mgr.getIssueDataList(sDate, eDate, sLimit, company_code, issue_code, sg_seqs, keyword_type, search_keyword, dataSize);
	Paging p = new Paging(pageNum, totalCount, dataSize);
%>
 <div class="ui_brd_list">
     <div class="wrap">
         <table>
             <colgroup>
                 <col>
                 <col style="width: 42px">
                 <col style="width: 140px">
                 <col style="width: 100px">
                 <col style="width: 80px">
             </colgroup>
             <thead>
                 <tr>
                     <th><span>제목</span></th>
                     <th><span>링크</span></th>                                                                            
                     <th><span>출처</span></th>
                     <th><span>확산건수</span></th>
                     <th><span>일시</span></th>
                 </tr>
             </thead>
             <tbody>
             <%if(totalCount == 0){%>
                 <tr><td colspan="5" class="no_over"></td></tr>
                 <tr><td colspan="5" class="no_over"></td></tr>
                 <tr><td colspan="5" class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td></tr>
                 <tr><td colspan="5" class="no_over"></td></tr>
                 <tr><td colspan="5" class="no_over"></td></tr>
             <%}else{
             	HashMap<String, String> map = new HashMap<String, String>();
             	String eTitle ="";
             	String title ="";
             	String s_seq ="";
             	for(int i=0; i<dataSize; i++){
	              if(i < dataList.size()){	
             		map = dataList.get(i);
             		eTitle = URLEncoder.encode(map.get("TITLE").toString(),"UTF-8").replaceAll("\\+", "%20");
    				title = map.get("TITLE").toString().replaceAll("\"", "").replaceAll("'", "");
    			//	s_seq = map.get("S_SEQ");
           		  	%>                    		
                 <tr>
         <% if(map.get("S_SEQ").equals("3555") || map.get("S_SEQ").equals("4943") || map.get("S_SEQ").equals("5032785")
        	 || map.get("SITE_NAME").contains("네이버카페")  ||  map.get("SITE_NAME").contains("다음카페")        		 
        ) {%>    
            		 <td class="title"><a href="<%=map.get("URL")%>" style="color: #333333; text-decoration: none !important" target="_blank" class="lnk" title="원문 글 연결은 PC에서는 PC버전 링크 아이콘, 모바일에서는 Mobile버전 링크 아이콘을 클릭하세요." onclick="showAlert(); return false;"><span class="txt"><%=map.get("TITLE")%></span></a></td>                  
                     <td>
				         <a href="<%=map.get("URL")%>" class="btn_cafe_p" title="PC버전 링크" onclick="portalSearch_pc('<%=map.get("S_SEQ")%>','<%=su.toHtmlString2(map.get("TITLE"))%>','<%=map.get("SITE_NAME")%>'); return false;"><img src="<%=imgUrl %>btn_cafe_p.gif" alt="카페 바로가기"></a>
		                 <a href="<%=map.get("URL")%>" class="btn_cafe_m" title="Mobile버전 링크" onclick="portalSearch_mobile('<%=map.get("S_SEQ")%>','<%=map.get("TITLE")%>','<%=map.get("SITE_NAME")%>'); return false;"><img src="<%=imgUrl %>btn_cafe_m.gif" alt="카페 바로가기"></a>           
                     </td>                                                                            
              <%} else{ %>                 
                     <td class="title"><a href="<%=map.get("URL")%>" class="lnk" target="_blank" title="<%=map.get("TITLE")%>" onclick="goToUrl('<%=map.get("URL")%>'); return false;"><span><%=map.get("TITLE")%></span></a></td>
                     <td></td>
                     <%} %>
                     <td><span title="<%=map.get("SITE_NAME")%>"><%=map.get("SITE_NAME")%></span></td>
                     <td><span><%=map.get("SAME_CNT")%></span></td>
                     <td><span><%=map.get("DATE")%></span></td>
                 </tr>
                   <% }else if(i >= dataList.size()){%>
                 <tr><td colspan="5" class="no_over"></td></tr>        	   
                   <%}
             	  }
             	}
              %>
             </tbody>
         </table>
     </div>
     <script>
    	var chkOriginal = 1;
    	function portalSearch(s_seq, md_title, s_name){
    		if(s_seq == '3555'){
    			//네이버까페
    			//url = "https://section.cafe.naver.com/cafe-home/search/articles?query=\""+md_title + "\"";
    			url = "https://m.cafe.naver.com/ca-fe/home/search/articles?q="+ encodeURIComponent(md_title);
    			//window.open(url,'hrefPop'+chkOriginal,'');
    			window.open('http://hub.buzzms.co.kr?url=' + url,'hrefPop'+chkOriginal,'');
    			//window.open('http://hub.buzzms.co.kr?url=' + url,'hrefPop'+chkOriginal,'');
    			//window.open('http://hub.buzzms.co.kr?url=' + url,'hrefPop'+chkOriginal,'');
    		}else if(s_seq == '4943'){
    			//다음까페
    			url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
    			//url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
    			//window.open(url,'hrefPop'+chkOriginal,'');
    			window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
    		}else if(s_seq == '5032785'){
    			//네이버카페 - 신용카드박물관
    			url = "https://m.cafe.naver.com/ca-fe/home/search/articles?q="+ encodeURIComponent(md_title);
    			//url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
    			//window.open(url,'hrefPop'+chkOriginal,'');
    			window.open('http://hub.buzzms.co.kr?url=' + url,'hrefPop'+chkOriginal,'');
    			//네이버카페    			
			}else if(s_name.includes('네이버카페')){
				//네이버까페
    			url = "https://m.cafe.naver.com/ca-fe/home/search/articles?q="+ encodeURIComponent(md_title);
    			window.open('http://hub.buzzms.co.kr?url=' + url,'hrefPop'+chkOriginal,'');
			
			//다음카페    			
			}else if(s_name.includes('다음카페')){
				url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
    			window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
			}

    		chkOriginal ++;
    	}            

    	function goToUrl(url){
    		
    		var openUrl = "http://hub.buzzms.co.kr?url=" + encodeURIComponent(url);
    		window.open(openUrl);
    	}          
    	
     </script>    
     <div class="footer is-only-web">
         <div class="ui_paginate">
             <div class="in_wrap">
             <%	String scriptFuntionName="";
			if(mode.equals("relation_information")){
				scriptFuntionName = "main.updateAjax_con_92";
			}
			%>
			<%=p.isPrevPageGroup() ? "<a href=\"#\" onclick=\""+scriptFuntionName+"( "+p.getPrevPageNo()+");return false;\" class=\"page_prev\">" : "<a href=\"#\" class=\"page_prev ui_disabled\">이전페이지</a>" %>
		<%for(int i = p.getPageGroupStartNo(); i <= p.getPageGroupEndNo(); i++){
			String styleClassName = p.getPageNo() == i ? "active":"";
			%>
		<%="<a href=\"#\" class=\""+styleClassName+"\"  onclick=\""+scriptFuntionName+"("+i+");return false;\">"+i+"</a>"%>
		<%}%>
		<%=p.isNextPageGroup() ? "<a href=\"#\" onclick=\""+scriptFuntionName+"( "+p.getNextPageNo()+" );return false;\" class=\"page_next disabled\">" : "<a href=\"#\" class=\"page_next ui_disabled\">다음페이지</a>"%>
             </div>
         </div>
     </div>
 </div>