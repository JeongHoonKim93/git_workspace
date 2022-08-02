<%@page import="risk.dashboard.main.MainMgr"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.HashMap"%>
<%@page import="risk.util.Paging"%>
<%@ page import="risk.util.ConfigUtil" %>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();	
	MainMgr mgr = new MainMgr();
	ConfigUtil cu = new ConfigUtil();
	
	String sDate = pr.getString("sDate");
	String eDate = pr.getString("eDate");
	String sg_seqs = pr.getString("sg_seqs");
	String mode = pr.getString("mode");
	String company_code = pr.getString("company_code");
	String keyword_type = pr.getString("keyword_type");
	String search_keyword = pr.getString("search_keyword");

	//이미지 경로
	String siteUrl = cu.getConfig("URL");
//	String imgUrl = siteUrl+"riskv3/report/img/";
	String imgUrl = siteUrl+"dashboard/asset/img/";
	int dataSize = 5; 
	int pageNum = pr.getInt("pageNum",1);			//페이지번호
	int sLimit = (pageNum-1) * dataSize;			//시작 limit	
	int totalCount = mgr.getVocSentiCount(sDate, eDate, mode, sg_seqs, company_code, keyword_type, search_keyword);
	ArrayList<HashMap<String, String>> dataList = mgr.getSentiList(sDate, eDate, mode, sg_seqs, company_code, sLimit, keyword_type, search_keyword, dataSize);
	Paging p = new Paging(pageNum, totalCount, dataSize);
%>
   <div class="ui_brd_list">
       <div class="header">
           <div class="lc">
           <% if(mode.equals("pos")){ %>
               <h5 class="fc_postive">긍정</h5>
           <%} else if(mode.equals("neg")){%>
               <h5 class="fc_negative">부정</h5>
           <%} else if(mode.equals("neu")){%>
               <h5 class="fc_neutral">중립</h5>           
           <%}%>
           </div>
<!--       <div class="rc">
               <button type="button" class="ui_btn is-only-web" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
           </div>		-->
       </div>
       <div class="wrap">
           <table>
               <colgroup>
                  <col>
                  <col style="width: 42px">
                  <col style="width: 140px">
                  <col style="width: 90px">
                  <col style="width: 100px">
                  <col style="width: 80px">
               </colgroup>
               <thead>
                   <tr>
                       <th><span>제목</span></th>
                       <th><span>링크</span></th>                                                                            
                       <th><span>출처</span></th>
                       <th><span>주제 유형</span></th>
                       <th><span>제품 유형</span></th>
                       <th><span>일시</span></th>
                   </tr>
               </thead>
               <tbody>
                    <%if(totalCount == 0){%>	
                        <tr><td colspan="6" class="no_over"></td></tr>
                        <tr><td colspan="6" class="no_over"></td></tr>
                        <tr><td colspan="6" class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td></tr>
                        <tr><td colspan="6" class="no_over"></td></tr>
                        <tr><td colspan="6" class="no_over"></td></tr>
                    <%}else{
                    	HashMap<String, String> map = new HashMap<String, String>();
                    	String eTitle ="";
                    	String title ="";
                    	String s_seq ="";
                    	for(int i=0; i< dataSize; i++){
						  if( i < dataList.size()){ 
                    		map = dataList.get(i);   
                    	%>
                   <tr>                   
		           <% if(map.get("S_SEQ").equals("3555") || map.get("S_SEQ").equals("4943") || map.get("S_SEQ").equals("5032785")
		        		   || map.get("MD_SITE_NAME").contains("네이버카페") || map.get("MD_SITE_NAME").contains("다음카페") 
		        	) {%>    
		               <td class="title">
 	                   <a href="<%=map.get("URL")%>" style="color: #333333; text-decoration: none !important" target="_blank" class="lnk" title="원문 글 연결은 PC에서는 PC버전 링크 아이콘, 모바일에서는 Mobile버전 링크 아이콘을 클릭하세요." onclick="showAlert(); return false;"><span class="txt"><%=map.get("TITLE")%></span></a></td>            
		               <td>
				       <a href="<%=map.get("URL")%>" class="btn_cafe_p" title="PC버전 링크" onclick="portalSearch_pc('<%=map.get("S_SEQ")%>','<%=map.get("TITLE")%>','<%=map.get("MD_SITE_NAME")%>'); return false;"><img src="<%=imgUrl %>btn_cafe_p.gif" alt="카페 바로가기"></a>
		               <a href="<%=map.get("URL")%>" class="btn_cafe_m" title="Mobile버전 링크" onclick="portalSearch_mobile('<%=map.get("S_SEQ")%>','<%=map.get("TITLE")%>','<%=map.get("MD_SITE_NAME")%>'); return false;"><img src="<%=imgUrl %>btn_cafe_m.gif" alt="카페 바로가기"></a>           
		               </td>
		               <%} else {%>  
		               <td class="title">
		               <a href="<%=map.get("URL")%>" target="_blank" class="lnk" title="<%=map.get("TITLE")%>" onclick="goToUrl('<%=map.get("URL")%>'); return false;"><span class="txt"><%=map.get("TITLE")%></span></a></td>
		               <td>
		               </td>
		               <%} %> 
                       <td><span title="<%=map.get("MD_SITE_NAME")%>"><%=map.get("MD_SITE_NAME")%></span></td>
                   <% if(map.get("SUBJECT").equals("")){ %>   
                       <td><span title="<%=map.get("SUBJECT")%>"> - </span></td>
                   <%} else {%>    
                       <td><span title="<%=map.get("SUBJECT")%>"><%=map.get("SUBJECT")%></span></td>
                   <%} 
                   	  if(map.get("PRODUCT").equals("")){ %>   
                       <td><span title="<%=map.get("PRODUCT")%>"> - </span></td>
                   <%} else {%>    
                       <td><span title="<%=map.get("PRODUCT")%>"><%=map.get("PRODUCT")%></span></td>
                   <%} %>                   
                       <td><span><%=map.get("DATE")%></span></td>
                   </tr>
                    <%} else if( i >= dataList.size()){ %>
                   <tr><td colspan="6" class="no_over"></td></tr>         
                    <%}
                     }
                    }
                    %>                 
               </tbody>
           </table>
       </div>
       <script>
	   	 var chkOriginal = 1;
		 //*****pc버전*****//
		 function portalSearch_pc(s_seq, md_title, s_name){
		 	if(s_seq == '3555'){
		 		//네이버까페
		 		//*****pc버전*****//
		 		url = "https://cafe.naver.com/ca-fe/home/search/articles?pr=2&se=1&et="+ md_title;
		 		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
		 	}else if(s_seq == '4943'){
		 		//다음까페
		 		url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
		 		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
		 	}else if(s_seq == '5032785'){
		 		//네이버카페 - 신용카드박물관
		 		url = "https://cafe.naver.com/ca-fe/home/search/articles?pr=2&se=1&et="+ md_title;
		 		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
		 		//네이버카페    			
			}else if(s_name.includes('네이버카페')){
				url = "https://cafe.naver.com/ca-fe/home/search/articles?pr=2&se=1&et="+ md_title;
		 		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');	
			
			//다음카페    			
			}else if(s_name.includes('다음카페')){
				url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
		 		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
			}
		 	chkOriginal ++;
		 }
		 
		 //*****모바일버전*****//
		 function portalSearch_mobile(s_seq, md_title, s_name){
		 	if(s_seq == '3555'){
		 		//네이버까페
		 		url = "https://m.cafe.naver.com/ca-fe/home/search/articles?q="+ encodeURIComponent(md_title);
		 		window.open('http://hub.buzzms.co.kr?url=' + url,'hrefPop'+chkOriginal,'');
		 	}else if(s_seq == '4943'){
		 		//다음까페
		 		url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
		 		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
		 	}else if(s_seq == '5032785'){
		 		//네이버카페 - 신용카드박물관
		 		url = "https://m.cafe.naver.com/ca-fe/home/search/articles?q="+ encodeURIComponent(md_title);
		 		window.open('http://hub.buzzms.co.kr?url=' + url,'hrefPop'+chkOriginal,'');
		 		//네이버카페    			
			}else if(s_name.includes('네이버카페')){
				url = "https://m.cafe.naver.com/ca-fe/home/search/articles?q="+ encodeURIComponent(md_title);
		 		window.open('http://hub.buzzms.co.kr?url=' + url,'hrefPop'+chkOriginal,'');	
			
			//다음카페    			
			}else if(s_name.includes('다음카페')){
				url = "http://search.daum.net/search?w=cafe&nil_search=btn&enc=utf8&ASearchType=1&lpp=10&rlang=0&q=" + md_title;
		 		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
			}
		 	chkOriginal ++;
		 }	    
		 
     	function showAlert() {
        	alert("원문 글 연결은 PC에서는 PC버전 링크 아이콘, \n모바일에서는 Mobile버전 링크 아이콘을 클릭하세요.");
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
					if(mode.equals("pos")){
						scriptFuntionName = "main.updateAjax_con_61";
					} else if(mode.equals("neg")){
						scriptFuntionName = "main.updateAjax_con_62";
					} else if(mode.equals("neu")){
						scriptFuntionName = "main.updateAjax_con_63";
					}
					%>
					
					<%=p.isPrevPageGroup() ? "<a href=\"#\" onclick=\""+scriptFuntionName+"( "+p.getPrevPageNo()+");return false;\" class=\"page_prev\">" : "<a href=\"#\" class=\"page_prev ui_disabled\">이전페이지</a>" %>
				<%for(int i = p.getPageGroupStartNo(); i <= p.getPageGroupEndNo(); i++){
					String styleClassName = pageNum == i ? "active":"";		
					%>
				<%="<a href=\"#\" class=\""+styleClassName+"\"  onclick=\""+scriptFuntionName+"("+i+");return false;\">"+i+"</a>"%>
				<%}%>
				<%=p.isNextPageGroup() ? "<a href=\"#\" onclick=\""+scriptFuntionName+"( "+p.getNextPageNo()+" );return false;\" class=\"page_next disabled\">" : "<a href=\"#\" class=\"page_next ui_disabled\">다음페이지</a>"%>                                      

<!--                    <a href="#" class="page_prev ui_disabled" onclick="return false">이전페이지</a>
                   <a href="#" class="" onclick="return false;">1</a>
                   <a href="#" class="active" onclick="return false;">2</a>
                   <a href="#" onclick="return false;">3</a>
                   <a href="#" onclick="return false;">4</a>
                   <a href="#" onclick="return false;">5</a>
                   <a href="#" class="page_next disabled" onclick="return false;">다음페이지</a>
 -->               </div>
           </div>
       </div>
   </div>