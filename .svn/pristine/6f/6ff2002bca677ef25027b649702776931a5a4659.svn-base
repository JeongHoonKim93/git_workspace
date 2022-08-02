<%@page import="risk.util.StringUtil"%>
<%@page import="risk.dashboard.main.MainMgr"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.HashMap"%>
<%@page import="risk.util.Paging"%>
<%@page import="risk.util.DateUtil"%>
<%@ page import="risk.util.ConfigUtil" %>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();	
	MainMgr mgr = new MainMgr();
	DateUtil du = new DateUtil();
	StringUtil su = new StringUtil();
	ConfigUtil cu = new ConfigUtil();

 	String issue_text = pr.getString("issue_text", "");
	String issue_opt = pr.getString("issue_opt", "");
	String action_yn = pr.getString("action_yn", "");
	String select_id_seq = pr.getString("select_id_seq", "");
 
	String sDate = pr.getString("sDate");
	String eDate = pr.getString("eDate");
	String sg_seqs = pr.getString("sg_seqs");
	String keyword_type = pr.getString("keyword_type");
	String search_keyword = pr.getString("search_keyword");
	//String issue_name = pr.getString("issue_name", "");
	
	String code = pr.getString("code");
	String company_code = pr.getString("company_code");
	int rowLimit = pr.getInt("rowLimit");
	
	int interval = (int)du.DateDiff("yyyy-MM-dd",eDate, sDate )+1;
	du.setDate(sDate);
	du.addDay( - interval);
	String psDate = du.getDate("yyyy-MM-dd");
	du.setDate(eDate);
	du.addDay( - interval);
	String peDate = du.getDate("yyyy-MM-dd");
	
	//이미지 경로
	String siteUrl = cu.getConfig("URL");
//	String imgUrl = siteUrl+"riskv3/report/img/";
	String imgUrl = siteUrl+"dashboard/asset/img/";
	
	ArrayList<HashMap<String, String>> voc_list = mgr.get_DailyVOC_TopList(company_code, code, sDate , eDate, sg_seqs, rowLimit, keyword_type, search_keyword);
	String topic_name = mgr.getTopicName(company_code, code);
	topic_name = topic_name.replace("&", "<br>&<br>");	
	
%>
               <h4>
  				<%=topic_name %>
               <span class="icon"></span>
               </h4>
               <table>
               <tbody id="top5_content_<%=code%>">
	<% if(voc_list.size() == 0){%>
   			   <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
  			   <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
 			   <tr><td class="ff_uni_hr"></td><td></td><td class="no_over no_data in_list"><span class="ui_no_data_txt">데이터가 없습니다.</span></td><td colspan="6"></td></tr>
 			   <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>
		       <tr><td class="ff_uni_hr"></td><td></td><td class="no_over"></td><td colspan="6"></td></tr>		
	<% } else{
			for(int i=0; i < rowLimit; i++){%>    

			<%if(i < voc_list.size()) {
			%>
	           <tr>
	            <td class="ff_uni_hr"><%=i+1%></td>
			<% if(voc_list.get(i).get("SENTI_CODE").equals("1")) {%>
	            <td><span class="ui_status_box is-positive">긍정</span></td>
	        <%} else if(voc_list.get(i).get("SENTI_CODE").equals("2")){ %>
	            <td><span class="ui_status_box is-negative">부정</span></td>
	        <%} else if(voc_list.get(i).get("SENTI_CODE").equals("3")){ %>
	            <td><span class="ui_status_box is-neutral">중립</span></td>
	            <%} %>                
	        <% if(voc_list.get(i).get("S_SEQ").equals("3555") || voc_list.get(i).get("S_SEQ").equals("4943") || voc_list.get(i).get("S_SEQ").equals("5032785")
	        		|| voc_list.get(i).get("MD_SITE_NAME").contains("네이버카페")  ||  voc_list.get(i).get("MD_SITE_NAME").contains("다음카페")
	        	) {%>    
	            <td class="title">
	            	<a href="<%=voc_list.get(i).get("ID_URL")%>" style="color: #333333; text-decoration: none !important" target="_blank" class="lnk" title="원문 글 연결은 PC에서는 PC버전 링크 아이콘, 모바일에서는 Mobile버전 링크 아이콘을 클릭하세요." onclick="showAlert(); return false;"><span class="txt"><%=voc_list.get(i).get("ID_TITLE")%></span></a></td>         
                <td>
				    <a href="<%=voc_list.get(i).get("ID_URL")%>" class="btn_cafe_p" title="PC버전 링크" onclick="portalSearch_pc('<%=voc_list.get(i).get("S_SEQ")%>','<%=su.toHtmlString2(voc_list.get(i).get("ID_TITLE"))%>','<%=voc_list.get(i).get("MD_SITE_NAME")%>'); return false;"><img src="<%=imgUrl %>btn_cafe_p.gif" alt="카페 바로가기"></a>
		            <a href="<%=voc_list.get(i).get("ID_URL")%>" class="btn_cafe_m" title="Mobile버전 링크" onclick="portalSearch_mobile('<%=voc_list.get(i).get("S_SEQ")%>','<%=su.toHtmlString2(voc_list.get(i).get("ID_TITLE"))%>','<%=voc_list.get(i).get("MD_SITE_NAME")%>'); return false;"><img src="<%=imgUrl %>btn_cafe_m.gif" alt="카페 바로가기"></a>           
                </td>    	            
	            <%} else {%>  
	            <td class="title">
	            <a href="<%=voc_list.get(i).get("ID_URL")%>" target="_blank" class="lnk" title="<%=voc_list.get(i).get("ID_TITLE")%>" onclick="goToUrl('<%=voc_list.get(i).get("ID_URL")%>'); return false;"><span class="txt"><%=voc_list.get(i).get("ID_TITLE")%></span></a></td>
	            <td>
	            </td>
	            <%} %>
	            <td class="ui_ar"><%=voc_list.get(i).get("MD_SITE_NAME")%></td>
			<% if(voc_list.get(i).get("LEVEL").equals("1")) {%>                                                            
	            <td><span class="ui_status_box is-lv1">Lv.1</span></td>
	        <%} else if(voc_list.get(i).get("LEVEL").equals("2")){ %>
	            <td><span class="ui_status_box is-lv2">Lv.2</span></td>
	        <%} else if(voc_list.get(i).get("LEVEL").equals("3")){ %>
	            <td><span class="ui_status_box is-lv3">Lv.3</span></td>
	        <%} else if(voc_list.get(i).get("LEVEL").equals("4")){ %>
	            <td><span class="ui_status_box is-lv4">Lv.4</span></td>
	        <%} else if(voc_list.get(i).get("LEVEL").equals("")){ %>
	            <td><span></span></td> 
	        <%} %>                         
	            <td><%=voc_list.get(i).get("H_DATE")%></td>
	            <td>
	             <div class="ui_in_search is-confirm is-only-web" style="width:120px">
	        <% if(!voc_list.get(i).get("COMMENT").equals("")){ %>   
	             <input id="voc_brd_txt_0<%=voc_list.get(i).get("IC_CODE")%>_0<%=i+1%>" type="text" onkeydown="javascript:if(event.keyCode == 13){comment_save(<%=voc_list.get(i).get("ID_SEQ")%>,<%=company_code%>,<%=voc_list.get(i).get("IC_CODE")%>,<%=i+1%>);}" placeholder="대응내역" value="<%=voc_list.get(i).get("COMMENT")%>">
	        <%} else {%>  
	             <input id="voc_brd_txt_0<%=voc_list.get(i).get("IC_CODE")%>_0<%=i+1%>" type="text" onkeydown="javascript:if(event.keyCode == 13){comment_save(<%=voc_list.get(i).get("ID_SEQ")%>,<%=company_code%>,<%=voc_list.get(i).get("IC_CODE")%>,<%=i+1%>);}" placeholder="대응내역">
	        <%} %>      
	             <label for="voc_brd_txt_0<%=voc_list.get(i).get("IC_CODE")%>_0<%=i+1%>" class="ui_invisible">대응내역 입력</label>
	             <button title="저장" onmousedown="comment_save(<%=voc_list.get(i).get("ID_SEQ")%>,<%=company_code%>,<%=voc_list.get(i).get("IC_CODE")%>,<%=i+1%>);return false;" ><span>저장</span></button></div> 
	        <% if(!voc_list.get(i).get("COMMENT").equals("")){ %>   	             
	             <span class="res_txt is-only-pdf" id="comment_txt_0<%=voc_list.get(i).get("IC_CODE")%>_0<%=i+1%>"><%=voc_list.get(i).get("COMMENT")%></span>
	        <%} else {%>  
	             <span class="res_txt is-only-pdf" id="comment_txt_0<%=voc_list.get(i).get("IC_CODE")%>_0<%=i+1%>"></span>	        
	        <%} %>                  
	            </td>
	            <td><input id="voc_brd_issue_0<%=voc_list.get(i).get("IC_CODE")%>_0<%=i+1%>" type="checkbox" class="toggle_issue" onchange="action_yn(<%=voc_list.get(i).get("IC_CODE")%>,<%=i+1%>,<%=voc_list.get(i).get("ID_SEQ")%>)" <%if( voc_list.get(i).get("ACTION").equals("1")) { out.print("checked");} %>><label for="voc_brd_issue_0<%=voc_list.get(i).get("IC_CODE")%>_0<%=i+1%>"></label></td>
	            <td class="ui_ar">
	             <div class="dcp ui_ac is-only-web" style="width:80px">
	              
	             
	               <select id="voc_brd_state_0<%=voc_list.get(i).get("IC_CODE")%>_0<%=i+1%>" onchange="change_issue(<%=voc_list.get(i).get("IC_CODE")%>,<%=i+1%>,<%=voc_list.get(i).get("ID_SEQ")%>)" style="width:80px">
	                  <option value="0" <%if( voc_list.get(i).get("PROCEED").equals("0")) { out.print("selected");} %>>진행상황</option>
	                  <%-- <option value="0" <%if(issue_opt.equals("0")) { %> selected <%} %>>진행상황</option> --%>
	                  <option value="1" <%if( voc_list.get(i).get("PROCEED").equals("1")) { out.print("selected");} %>>개선완료</option>
	                  <option value="2" <%if( voc_list.get(i).get("PROCEED").equals("2")) { out.print("selected");} %>>검토중</option>
	                  <option value="3" <%if( voc_list.get(i).get("PROCEED").equals("3")) { out.print("selected");} %>>Drop</option>
	               </select> <label for="voc_brd_state_0<%=voc_list.get(i).get("IC_CODE")%>_0<%=i+1%>"></label>
	              </div>
			<%if( voc_list.get(i).get("PROCEED").equals("1")){%>     
	              <span class="result_state is-only-pdf" id="issue_state_0<%=voc_list.get(i).get("IC_CODE")%>_0<%=i+1%>">개선완료</span>       
	        <%} else if( voc_list.get(i).get("PROCEED").equals("2")){%>   
	              <span class="result_state is-only-pdf" id="issue_state_0<%=voc_list.get(i).get("IC_CODE")%>_0<%=i+1%>">검토중</span>        
	        <%} else if( voc_list.get(i).get("PROCEED").equals("3")){%>       
	              <span class="result_state is-only-pdf" id="issue_state_0<%=voc_list.get(i).get("IC_CODE")%>_0<%=i+1%>">Drop</span>            
	        <%} else{%>
	              <span class="result_state is-only-pdf" id="issue_state_0<%=voc_list.get(i).get("IC_CODE")%>_0<%=i+1%>"></span>	        
	        <%} %>  
	            </td>
	           </tr>
		<%}else if(i >= voc_list.size()) {%>
	              <tr><td class="ff_uni_hr"><%=i+1%></td><td></td><td class="title"></td><td colspan="6"></td></tr>
		<% }
	} }%>
            </tbody>
            </table>
            <script>
         	 
         	if( !$tg ) var $tg = $( "body" );
         // Design SelectBox 셋팅
        	$tg.find( ".dcp > select" ).each( function(){
        		var tg = $( this );
        		tg.change( selectSet );
        		selectSet();
        		new MutationObserver( function( $e ) {
        			if( tg.attr( "value" ) ) {
        				tg[ 0 ].value = tg.attr( "value" );
        				tg.removeAttr( "value" );
        				tg.trigger( "change" );
        			} else {
        				selectSet();
        			}
        		}).observe( tg[ 0 ], { attributes: true, childList: true, characterData: true, subtree: true, attributeOldValue: true, characterDataOldValue: true });

        		function selectSet() {
        			tg.find( "+ label" ).html( tg.find( "> option:selected" ).html() );
        		}
        	});
	            
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
    				//*****pc버전*****//
        			url = "https://cafe.naver.com/ca-fe/home/search/articles?pr=2&se=1&et="+ md_title;
        			window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkOriginal,'');
    			//다음카페    			
    			}else if(s_name.includes('다음카페')){
    				//다음까페
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
        	
        	function goToUrl(url){
        		
        		var openUrl = "http://hub.buzzms.co.kr?url=" + encodeURIComponent(url);
        		window.open(openUrl);
        	}
        	
        	function change_issue(code, number, select_id_seq){
        		var mode = "proceed";
        		var issue_text = $("#voc_brd_txt_0"+code+"_0"+number+"").val();
        		var proceed_code = ($("#voc_brd_state_0"+code+"_0"+number+" option:selected").val());
        		var action_code = "";

        		if(proceed_code == "1"){
        			$("#issue_state_0"+code+"_0"+number).text("개선완료");	
        		}else if(proceed_code == "2"){
        			$("#issue_state_0"+code+"_0"+number).text("검토중");	
        		}else if(proceed_code == "3"){
        			$("#issue_state_0"+code+"_0"+number).text("Drop");	  			
        		}else{
        			$("#issue_state_0"+code+"_0"+number).text("");	       			
        		}

        		if($("input:checkbox[id=voc_brd_issue_0"+code+"_0"+number+"]").is(":checked") == true){
        			action_code = "1";
        		} else if($("input:checkbox[id=voc_brd_issue_0"+code+"_0"+number+"]").is(":checked") == false) {
        			action_code = "2";	
        		}
        		main.updateAjax_con_10(issue_text, proceed_code, action_code, select_id_seq, mode);     		   		
        	}
        	
        	function comment_save(select_id_seq, company_code, code, number){
        		var mode = "comment";
        		var issue_text = $("#voc_brd_txt_0"+code+"_0"+number+"").val();
        		var proceed_code = ($("#voc_brd_state_0"+code+"_0"+number+" option:selected").val());
        		var action_code = "";

        		$("#comment_txt_0"+code+"_0"+number).text(issue_text);

        		if($("input:checkbox[id=voc_brd_issue_0"+code+"_0"+number+"]").is(":checked") == true){
        			action_code = "1";
        		} else if($("input:checkbox[id=voc_brd_issue_0"+code+"_0"+number+"]").is(":checked") == false) {
        			action_code = "2";	
        		}
        		main.updateAjax_con_10(issue_text, proceed_code, action_code, select_id_seq, mode);
        		   		
        	}        	
        
        	function action_yn(code, number, select_id_seq){
        		var mode = "action";
        		var issue_text = $("#voc_brd_txt_0"+code+"_0"+number+"").val();
        		var proceed_code = ($("#voc_brd_state_0"+code+"_0"+number+" option:selected").val());
        		var action_code = "";

        		if($("input:checkbox[id=voc_brd_issue_0"+code+"_0"+number+"]").is(":checked") == true){
        			action_code = "1";
        		} else if($("input:checkbox[id=voc_brd_issue_0"+code+"_0"+number+"]").is(":checked") == false) {
        			action_code = "2";	
        		}       			
        		main.updateAjax_con_10(issue_text, proceed_code, action_code, select_id_seq, mode);
        		   		
        		}
        	
        	function showAlert() {
	        	alert("원문 글 연결은 PC에서는 PC버전 링크 아이콘, \n모바일에서는 Mobile버전 링크 아이콘을 클릭하세요.");
	        }        	
        	
           </script>