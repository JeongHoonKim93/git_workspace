<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.statistics_pop.StatisticsPopMgr"%>
<%@page import="risk.issue.IssueMgr"%>
<%@page import="risk.issue.IssueCodeMgr"%>
<%@page import="risk.issue.IssueCodeBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="risk.util.DateUtil"%>
<%@page import="risk.util.ParseRequest"%>
<%@include file="../inc/sessioncheck.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>Dashboard</title>
<meta charset="utf-8">
<jsp:include page="./inc/inc_page_top.jsp" flush="false" />
<!-- Include Devels -->
<jsp:include page="./inc/inc_devels.jsp" flush="false" />
<!-- // Include Devels -->

<%
ParseRequest    pr = new ParseRequest(request);
//기간설정
DateUtil du = new DateUtil();
String currentDate = du.getCurrentDate("yyyy-MM-dd");
//currentDate = du.addDay_v2(currentDate, -1);


String preWeekDay = du.addDay_v2(currentDate, -6);
String sDate = preWeekDay;
String eDate = currentDate;

String peDate = du.addDay_v2(sDate, -1); //비교기간 종료일
String psDate = du.addDay_v2(peDate, -6); //비교기간 시작일

//IssueCodeMgr 인스턴스 생성
IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
icMgr.init(0);

//검색 조건에 있는 issue_code의 ic_type
String main_type_list = "1,11,6,9,20,21,5";

IssueMgr issueMgr = new IssueMgr();
HashMap<String, ArrayList<String[]>> subDepthList = issueMgr.getSubdepthList(main_type_list); //하위뎁스리스트

String ex_seq = "103,104"; //포탈, 포탈댓글
StatisticsPopMgr staticsMgr = new StatisticsPopMgr();
ArrayList<String[]> site_group_list =  staticsMgr.getSiteGroupList(ex_seq);

ArrayList arrIcBean = null;
IssueCodeBean icBean = null;
IssueCodeBean subIcBean = null;
String tmp_Pcode = "";
String tmp_depth = "";

ArrayList<String[]> tmp_code_list = null;
String[] tmp_sub_info = null;	

int tmp_chk_td = 0;



%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<body>
	<div id="wrap">
		<script type="text/javascript" src="./index.js"></script> 
		<script>
		
		function getSubTypeCode(selectType, depth){
			var targetDepth =  parseInt(depth)+parseInt(1);
			var selected_value = $("[name=typeCodeSelect_"+selectType+"_"+depth+"] option:selected").val();	
			if(selected_value == '') return false;	
			var split_value = selected_value.split(",");
			
			checkCloseChildDepth(selectType, depth); 
			ajaxSelectBox(split_value[0], split_value[1], selectType, $("#typeCode_"+selectType+"_"+targetDepth), targetDepth);
		}	
		
		
		function ajaxSelectBox(selectType, selectCode, mainType, $target, targetDepth){	
			$.ajax({
				type : "POST"
				,url: "./data/aj_selectbox_type.jsp"
				,timeout: 30000
				,data : "selectType="+selectType+"&selectCode="+selectCode+"&mainType="+mainType+"&depth="+targetDepth
				,dataType : 'html'
				,success : function( data ){
					$target.empty().append( data );			
				}
			});
		}
		
	
		function checkCloseChildDepth(selectType, depth){
			var innerHtml = "<option value=''>선택하세요</option>";
			$(".subType_"+selectType).each(function(idx){
				if($(this).data(["data-depth"]).context.dataset['depth']>depth){
					$(this).empty().append(innerHtml);
				}		
			});
			
		}
			
		</script>
		<header style="padding:5px;">
		<img src="./asset/img/h1_logo.gif" alt="cj cheiljedang">
		</header>
		<div id="container">
			<h2 id="page_title" class="ui_invisible">&nbsp;</h2>
			<div id="content" class="page_main">

				<!-- Search -->
				<section id="top_searchs" class="ui_searchs">
					<h3 class="ui_invisible">검색조건</h3>
					<div class="wrap">
						<!-- 검색조건 설정 -->
						<div class="searchs_inputs">
							<form id="searchs_frm">
							<input type="hidden" name="settingCode" value="" id="settingCode">
							<input type="hidden" name="mainCode" value="<%=main_type_list%>" id="mainCode">
							<input type="hidden" name="section1_type" value="" id="section1_type">
							<input type="hidden" name="section1_code" value="" id="section1_code">
							<input type="hidden" name="section1_name" value="" id="section1_name">
							<input type="hidden" name="section2_type" value="" id="section2_type">
							<input type="hidden" name="section2_code" value="" id="section2_code">
							<input type="hidden" name="section2_name" value="" id="section2_name"> 
							<table>
									<caption>입력/수정</caption>
									<colgroup>
                                        <col style="width:90px">
                                        <col style="width:250px">
                                        <col style="width:95px">
                                        <col style="width:250px">
                                        <col style="width:95px">
                                        <col style="width:200px">
                                        <col>
									</colgroup>
									<tbody>
                                        <tr>
                                            <th><span>검색기간</span></th>
                                            <td>
                                                <div class="ui_datepicker_range" data-range="5Y">		<!-- 기간제한(최종날짜 기준) : Y : 년, M : 월, 숫자만 : 일 -->
                                                    <div class="input_wrap">
                                                        <div class="input">
                                                            <input id="searchs_date_range_01" type="text" class="date_result" readonly value=""><label for="searchs_date_range_01">검색기간</label>		<!-- 선택시 input에 'active'클래스 추가 -->
                                                        </div>
                                                    </div>
                                                    <div class="calendars">
                                                        <div class="calendars_container">
                                                            <div class="wrap">
                                                                <div id="searchs_date_range_01_cal_s" class="dp_wrap searchs_dp_start" data-date="<%=sDate %>" data-mindate="2010-01-10"></div>
                                                                <div id="searchs_date_range_01_cal_e" class="dp_wrap searchs_dp_end" data-date="<%=eDate %>" data-maxdate="<%=eDate%>"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <th><span>비교기간</span></th>
                                            <td colspan="3">
                                                <div class="ui_datepicker_range" data-range="5Y">		<!-- 기간제한(최종날짜 기준) : Y : 년, M : 월, 숫자만 : 일 -->
                                                    <div class="input_wrap">
                                                        <div class="input">
                                                            <input id="searchs_date_range_02" type="text" class="date_result" readonly value=""><label for="searchs_date_range_02">검색기간</label>		<!-- 선택시 input에 'active'클래스 추가 -->
                                                        </div>
                                                    </div>
                                                    <div class="calendars">
                                                        <div class="calendars_container">
                                                            <div class="wrap">
                                                                <div id="searchs_date_range_02_cal_s" class="dp_wrap searchs_dp_start" data-date="<%=psDate%>" data-mindate="2010-01-10"></div>
                                                                <div id="searchs_date_range_02_cal_e" class="dp_wrap searchs_dp_end" data-date="<%=peDate%>" data-maxdate="<%=peDate%>"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <button id="btn_search" type="button" class="ui_btn hl_black ui_wid_100p" onclick="searching();"><span class="icon search before">-</span><span>검색</span></button>
                                            </td>
                                        </tr>
                                        
                                        <tr>
                                        	<%
                                        	/* 회사구분1 */
                                        	tmp_Pcode = "1";
                                            tmp_depth = "1";                		
                                       		arrIcBean = new ArrayList();
                   			    			arrIcBean = icMgr.GetType(Integer.parseInt(tmp_Pcode));			    			
                   			    			icBean = (IssueCodeBean) arrIcBean.get(0);
                   			    			tmp_chk_td = 0; 
                                        	%>
                                            <th><span><%=icBean.getIc_name()%></span></th>
                                            <td>
                                                <div class="dcp">
                                                    <select id="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>" data-depth="<%=tmp_depth%>" name="typeCodeSelect_<%=tmp_Pcode%>_<%=tmp_depth%>" class="subType_<%=tmp_Pcode%>" style="width:200px;" onchange="getSubTypeCode(<%=tmp_Pcode%>,<%=tmp_depth%>);">
                                                 	   <option value="">선택하세요</option>
                                                        <%
                                                        for(int i=1; i<arrIcBean.size(); i++){
                                                        	icBean = (IssueCodeBean) arrIcBean.get(i);
                                                        	%>
                                                        		 <option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>"><%=icBean.getIc_name() %></option>
                                                        	<%
                                                        }
                                                        %>
                                                    </select><label for="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>"></label>
                                                </div>
                                            </td>
                                            
                                          <%
                                            /* 회사구분2 */
                                            tmp_code_list = subDepthList.get(tmp_Pcode);
                                            tmp_sub_info = tmp_code_list.get(0);
                                            tmp_depth = "2";   		
                                            %>
                                            <th><span><%=tmp_sub_info[1]%></span></th>
                                            <td>
                                                <div class="dcp">
                                                    <select id="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>" data-depth="<%=tmp_depth%>" name="typeCodeSelect_<%=tmp_Pcode%>_<%=tmp_depth%>"  class="subType_<%=tmp_Pcode%>" style="width: 200px" onchange="getSubTypeCode(<%=tmp_Pcode%>,<%=tmp_depth%>);">
                                                        <option value="">선택하세요.</option>
                                                    </select><label for="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>"></label>
                                                </div>
                                            </td>
                                            
                                            <%
                                            /* 회사구분3 */
                                            tmp_code_list = subDepthList.get(tmp_Pcode);
                                            tmp_sub_info = tmp_code_list.get(1);
                                            tmp_depth = "3";  
                                            %>
                                            <th><span><%=tmp_sub_info[1]%></span></th>
                                            <td>
                                                <div class="dcp">
                                                    <select id="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>" data-depth="<%=tmp_depth%>" name="typeCodeSelect_<%=tmp_Pcode%>_<%=tmp_depth%>"  class="subType_<%=tmp_Pcode%>" style="width: 200px">
                                                        <option value="">선택하세요.</option>
                                                    </select><label for="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>"></label>
                                                </div>
                                            </td>
                                        </tr>
                                        
                                        <tr>
                                            <%
                                        	/* 제품군1 */
                                        	tmp_Pcode = "11";
                                            tmp_depth = "1";  
                                       		arrIcBean = new ArrayList();
                   			    			arrIcBean = icMgr.GetType(Integer.parseInt(tmp_Pcode));			    			
                   			    			icBean = (IssueCodeBean) arrIcBean.get(0);
                   			    			tmp_chk_td = 0; 
                                        	%>
                                            <th><span>제품군1</span></th>
                                            <td>
                                                <div class="dcp">
                                                    <select id="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>" data-depth="<%=tmp_depth%>" name="typeCodeSelect_<%=tmp_Pcode%>_<%=tmp_depth%>" class="subType_<%=tmp_Pcode%>" style="width:200px;" onchange="getSubTypeCode(<%=tmp_Pcode%>,<%=tmp_depth%>);">
                                                 	   <option value="">선택하세요</option>
                                                        <%
                                                        for(int i=1; i<arrIcBean.size(); i++){
                                                        	icBean = (IssueCodeBean) arrIcBean.get(i);
                                                        	%>
                                                        		 <option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>"><%=icBean.getIc_name() %></option>
                                                        	<%
                                                        }
                                                        %>
                                                    </select><label for="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>"></label>
                                                </div>
                                            </td>
                                            
                                           <%
                                            /* 제품군2 */
                                            tmp_code_list = subDepthList.get(tmp_Pcode);
                                            tmp_sub_info = tmp_code_list.get(0);
                                            tmp_depth = "2";  
                                            %>
                                            <th><span><%=tmp_sub_info[1]%></span></th>
                                            <td>
                                                <div class="dcp">
                                                    <select id="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>" data-depth="<%=tmp_depth%>" name="typeCodeSelect_<%=tmp_Pcode%>_<%=tmp_depth%>"  class="subType_<%=tmp_Pcode%>" style="width: 200px" onchange="getSubTypeCode(<%=tmp_Pcode%>,<%=tmp_depth%>);">
                                                        <option value="">선택하세요.</option>
                                                    </select><label for="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>"></label>
                                                </div>
                                            </td>
                                            
                                            <%
                                            /* 제품군3 */
                                            tmp_code_list = subDepthList.get(tmp_Pcode);
                                            tmp_sub_info = tmp_code_list.get(1);
                                            tmp_depth = "3";  
                                            %>
                                            <th><span><%=tmp_sub_info[1]%></span></th>
                                            <td>
                                                <div class="dcp">
                                                    <select id="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>" data-depth="<%=tmp_depth%>" name="typeCodeSelect_<%=tmp_Pcode%>_<%=tmp_depth%>"  class="subType_<%=tmp_Pcode%>" style="width: 200px">
                                                        <option value="">선택하세요.</option>
                                                    </select><label for="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>"></label>
                                                </div>
                                            </td>
                                                                                    
                                        </tr>
                                        
                                        <tr>
                                             <%
                                        	/* 게시물 유형 */
                                        	tmp_Pcode = "6";
                                            tmp_depth = "1";  
                                       		arrIcBean = new ArrayList();
                   			    			arrIcBean = icMgr.GetType(Integer.parseInt(tmp_Pcode));			    			
                   			    			icBean = (IssueCodeBean) arrIcBean.get(0);
                   			    			tmp_chk_td = 0; 
                                        	%>
                                            <th><span><%=icBean.getIc_name()%></span></th>
                                            <td>
                                                <div class="dcp">
												 <select id="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>" data-depth="<%=tmp_depth%>" name="typeCodeSelect_<%=tmp_Pcode%>_<%=tmp_depth%>" class="subType_<%=tmp_Pcode%>" style="width:200px;" onchange="getSubTypeCode(<%=tmp_Pcode%>,<%=tmp_depth%>);">
                                                 	   <option value="">선택하세요</option>
                                                        <%
                                                        for(int i=1; i<arrIcBean.size(); i++){
                                                        	icBean = (IssueCodeBean) arrIcBean.get(i);
                                                        	%>
                                                        		 <option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>"><%=icBean.getIc_name() %></option>
                                                        	<%
                                                        }
                                                        %>
                                                    </select><label for="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>"></label>
                                                </div>
                                            </td>
                                            <%
                                            /* 게시물유형2 */
                                            tmp_code_list = subDepthList.get(tmp_Pcode);
                                            tmp_sub_info = tmp_code_list.get(0);
                                            tmp_depth = "2";  
                                            %>
                                            <th><span><%=tmp_sub_info[1]%></span></th>
                                            <td>
                                                <div class="dcp">
                                                    <select id="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>" data-depth="<%=tmp_depth%>" name="typeCodeSelect_<%=tmp_Pcode%>_<%=tmp_depth%>"  class="subType_<%=tmp_Pcode%>" style="width: 200px" onchange="getSubTypeCode(<%=tmp_Pcode%>,<%=tmp_depth%>);">
                                                        <option value="">선택하세요.</option>
                                                    </select><label for="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>"></label>
                                                </div>
                                            </td>
                                            
                                            <%
                                            /* 게시물유형3 */
                                            tmp_code_list = subDepthList.get(tmp_Pcode);
                                            tmp_sub_info = tmp_code_list.get(1);
                                            tmp_depth = "3";  
                                            %>
                                            <th><span><%=tmp_sub_info[1]%></span></th>
                                            <td>
                                                <div class="dcp">
                                                    <select id="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>" data-depth="<%=tmp_depth%>" name="typeCodeSelect_<%=tmp_Pcode%>_<%=tmp_depth%>"  class="subType_<%=tmp_Pcode%>" style="width: 200px">
                                                        <option value="">선택하세요.</option>
                                                    </select><label for="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>"></label>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <%
                                        	/* 온라인 이슈 클레임 인입여부 */
                                        	tmp_Pcode = "9";
                                       		arrIcBean = new ArrayList();
                   			    			arrIcBean = icMgr.GetType(Integer.parseInt(tmp_Pcode));			    			
                   			    			icBean = (IssueCodeBean) arrIcBean.get(0);
                   			    		 	tmp_depth = "1"; 
                                        	%>
                                            <th><span><%=icBean.getIc_name()%></span></th>
                                            <td>
                                                <div class="dcp">
                                                    <select id="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>" data-depth="<%=tmp_depth%>" name="typeCodeSelect_<%=tmp_Pcode%>_<%=tmp_depth%>" class="subType_<%=tmp_Pcode%>" style="width:200px;">
                                                 	   <option value="">선택하세요</option>
                                                        <%
                                                        for(int i=1; i<arrIcBean.size(); i++){
                                                        	icBean = (IssueCodeBean) arrIcBean.get(i);
                                                        	%>
                                                        		 <option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>"><%=icBean.getIc_name() %></option>
                                                        	<%
                                                        }
                                                        %>
                                                    </select><label for="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>"></label>
                                                </div>
                                            </td>
                                            
                                            <%
                                        	/*온라인 이슈 유포언급*/
                                        	tmp_Pcode = "20";
                                       		arrIcBean = new ArrayList();
                   			    			arrIcBean = icMgr.GetType(Integer.parseInt(tmp_Pcode));			    			
                   			    			icBean = (IssueCodeBean) arrIcBean.get(0);
                   			    			tmp_depth = "1";
                                        	%>
                                            <th><span><%=icBean.getIc_name()%></span></th>
                                            <td>
                                                <div class="dcp">
                                                    <select id="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>" data-depth="<%=tmp_depth%>" name="typeCodeSelect_<%=tmp_Pcode%>_<%=tmp_depth%>" class="subType_<%=tmp_Pcode%>" style="width:200px;">
                                                 	   <option value="">선택하세요</option>
                                                       <% for(int i=1; i<arrIcBean.size(); i++){
                                                        	icBean = (IssueCodeBean) arrIcBean.get(i);
                                                        	%>
                                                        		 <option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>"><%=icBean.getIc_name() %></option>
                                                        
                                                        <%} %>
                                                    </select><label for="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>"></label>
                                                </div>
                                            </td>
                                            
                                            <%
                                        	/* 온라인 이슈 이슈 등급 */
                                        	tmp_Pcode = "21";
                                       		arrIcBean = new ArrayList();
                   			    			arrIcBean = icMgr.GetType(Integer.parseInt(tmp_Pcode));			    			
                   			    			icBean = (IssueCodeBean) arrIcBean.get(0);
                   			    			tmp_depth = "1";
                                        	%>
                                            <th><span><%=icBean.getIc_name()%></span></th>
                                            <td>
                                                <div class="dcp">
                                                    <select id="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>" data-depth="<%=tmp_depth%>" name="typeCodeSelect_<%=tmp_Pcode%>_<%=tmp_depth%>" class="subType_<%=tmp_Pcode%>" style="width:200px;">
                                                 	   <option value="">선택하세요</option>
                                                       <% for(int i=1; i<arrIcBean.size(); i++){
                                                        	icBean = (IssueCodeBean) arrIcBean.get(i);
                                                        	%>
                                                        		 <option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>"><%=icBean.getIc_name() %></option>
                                                        	<%
                                                        }
                                                        %>
                                                    </select><label for="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>"></label>
                                                </div>
                                            </td>
                                            
                                        </tr>
                                        <tr>
                                            <th><span>출처</span></th>
                                            <td>
                                                <div class="dcp">
                                                    <select id="site_group_code" style="width: 200px" name="site_group_code">
                                                      	<option value="">선택하세요</option>	
                                                         <%
                                                         	String[] sg_info = new String[2];
                                                         	for(int i=0; i<site_group_list.size(); i++){
                                                         		sg_info = site_group_list.get(i);
                                                         %>
																<option value="<%=sg_info[0]%>"><%=sg_info[1]%></option>						
														<%}%>                                        	   			
                                            			                                                        
                                                    </select><label for="site_group_code"></label>
                                                </div>
                                            </td>
                                            <%
                                        	/* 부서 */
                                        	tmp_Pcode = "5";
                                       		arrIcBean = new ArrayList();
                   			    			arrIcBean = icMgr.GetType(Integer.parseInt(tmp_Pcode));			    			
                   			    			icBean = (IssueCodeBean) arrIcBean.get(0);
                   			    			tmp_depth = "1"; 
                                        	%>
                                            <th><span><%=icBean.getIc_name()%></span></th>
                                            <td>
                                                <div class="dcp">
                                                    <select id="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>" data-depth="<%=tmp_depth%>" name="typeCodeSelect_<%=tmp_Pcode%>_<%=tmp_depth%>" class="subType_<%=tmp_Pcode%>" style="width:200px;" >
                                                 	   <option value="">선택하세요</option>
                                                        <%
                                                        for(int i=1; i<arrIcBean.size(); i++){
                                                        	icBean = (IssueCodeBean) arrIcBean.get(i);
                                                        		 %><option value="<%=icBean.getIc_type()%>,<%=icBean.getIc_code()%>"><%=icBean.getIc_name() %></option><%
                                                        }
                                                        %>
                                                    </select><label for="typeCode_<%=tmp_Pcode%>_<%=tmp_depth%>"></label>
                                                </div>
                                            </td>
                                        </tr>
									</tbody>
								</table>
							</form>
						</div>
						<!-- // 검색조건 설정 -->
					</div>

					<script type="text/javascript">
						function searching(){
							searchResultMngr.destroy();						// 검색 결과 초기화
							searchResultMngr.addItem( "2018-01-01 ~ 2018-01-31", "검색기간" );		// 검색 결과 아이템 추가
						}
					</script>
				</section>
				<!-- // Search -->

				<!-- Content -->
				<div class="wrap">

                    <!-- 제품 순위 / 세부내용 -->
					<div class="ui_row on_pad">

                        <!-- 제품 순위 -->
                        <div class="ui_col w4">
                            <section class="ui_box" id="section_10">
                                <div class="box_header">
                                    <h3>제품 순위</h3>
                                    <div class="box_header_rc">
                                        <button type="button" class="ui_btn" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                    </div>
                                </div>
                                <div class="box_content_header">
                                    <div class="lc">
                                        <strong>분류</strong>
                                        <div class="dcp">
                                            <select id="depart_product" style="width: 120px">                                                
                                            </select><label for="depart_product"></label>
                                        </div>
                                    </div>
                                    <div class="rc">
                                        <strong>정렬</strong>
                                        <div class="dcp">
                                            <select id="depart_product_orderBy" style="width: 120px">
                                                <option value="0" selected>건수</option>
                                                <option value="1">증가율</option>
                                            </select><label for="depart_product_orderBy"></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="box_content no_pad ui_loader_container"  id="ajax_con_10">
                                    <div class="ui_brd_list not_bot_brd">
                                        <div class="wrap">
                                            <table>
                                                <caption>목록</caption>
                                                <colgroup>
                                                    <col style="width:40px">
                                                    <col>
                                                    <col style="width:60px">
                                                    <col style="width:60px">
                                                    <col style="width:77px">
                                                </colgroup>
                                                <thead>
                                                    <tr>
                                                        <th><span>순위</span></th>
                                                        <th><span>제품</span></th>
                                                        <th><span>비교일</span></th>
                                                        <th><span>기준일</span></th>
                                                        <th><span>변화량</span></th>
                                                    </tr>
                                                </thead>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="ui_brd_list is-scroll not_top_brd" style="height:1158px;">
                                        <div class="wrap">
                                            <table>
                                                <caption>목록</caption>
                                                <colgroup>
                                                    <col style="width:40px">
                                                    <col>
                                                    <col style="width:60px">
                                                    <col style="width:60px">
                                                    <col style="width:60px">
                                                </colgroup>
                                                <tbody id="product_list">
                                                        <!-- 데이터 없는 경우
                                                        <tr><td colspan="5" class="no_over no_data in_list" style="height:1151px"><span class="ui_no_data_txt">데이터가 없습니다.</span></td></tr>
                                                        // 데이터 없는 경우 -->
                                                    <tr>
                                                        <td>1</td>
                                                        <td><a href="#" class="lnk"><strong class="txt" title="건강">건강</strong></a></td>
                                                        <td><span title="123,456,789">123,456k</span></td>
                                                        <td><span title="123,456,789">123,456k</span></td>
                                                        <td><span class="ui_fluc before up">99.1K</span></td>
                                                    </tr>
                                                    <tr class="active">
                                                        <td>2</td>
                                                        <td><a href="#" class="lnk"><strong class="txt" title="건강2">건강2</strong></a></td>
                                                        <td><span title="123,456,789">123,456k</span></td>
                                                        <td><span title="123,456,789">123,456k</span></td>
                                                        <td><span class="ui_fluc before up">99.1K</span></td>
                                                    </tr>
                                                    <tr>
                                                        <td>3</td>
                                                        <td><a href="#" class="lnk"><strong class="txt" title="건강">건강</strong></a></td>
                                                        <td><span title="123,456,789">123,456k</span></td>
                                                        <td><span title="123,456,789">123,456k</span></td>
                                                        <td><span class="ui_fluc before up">99.1K</span></td>
                                                    </tr>
                                                    <tr>
                                                        <td>4</td>
                                                        <td><a href="#" class="lnk"><strong class="txt" title="건강">건강</strong></a></td>
                                                        <td><span title="123,456,789">123,456k</span></td>
                                                        <td><span title="123,456,789">123,456k</span></td>
                                                        <td><span class="ui_fluc before up">99.1K</span></td>
                                                    </tr>
                                                    <tr>
                                                        <td>5</td>
                                                        <td><a href="#" class="lnk"><strong class="txt" title="건강">건강</strong></a></td>
                                                        <td><span title="123,456,789">123,456k</span></td>
                                                        <td><span title="123,456,789">123,456k</span></td>
                                                        <td><span class="ui_fluc before up">99.1K</span></td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </section>
                        </div>
                        <!-- // 제품 순위 -->

                        <!-- 세부 내용 -->
                        <div class="ui_col w8">
                            <section class="ui_box ui_loader_container">
                                <div class="box_header">
                                    <h3>세부 내용<em class="detail_hearder">건강2</em></h3>
                                    <!-- <div class="box_header_rc">
                                        <button type="button" class="ui_btn" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                    </div> -->
                                </div>
                                <div class="box_content">

                                    <!-- 분류 / 연관 키워드 -->
                                    <div class="ui_row on_pad">

                                        <!-- 분류 -->
                                        <div class="ui_col w6">
                                            <section class="ui_box dims" id="section_11">
                                                <div class="box_header">
                                                    <h4>분류</h4>
                                                    <div class="box_header_rc">
                                                        <button type="button" class="ui_btn" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                                    </div>
                                                </div>
                                                <div class="box_content_header">
                                                    <div class="lc">
                                                        <strong>분류</strong>
                                                        <div class="dcp">
                                                            <select id="depart_case" style="width: 120px">
                                                                <option value="">선택하세요.</option>                                                                
                                                            </select><label for="depart_case"></label>
                                                        </div>
                                                    </div>
                                                    <div class="rc">
                                                        <strong>정렬</strong>
                                                        <div class="dcp">
                                                            <select id="depart_case_orderBy" style="width: 120px">
                                                                <option value="0">건수</option>
                                                                <option value="1">증가율</option>
                                                            </select><label for="depart_case_orderBy"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="box_content no_pad ui_loader_container" id="ajax_con_11">
                                                    <div class="ui_brd_list not_bot_brd">
                                                        <div class="wrap">
                                                            <table>
                                                                <caption>목록</caption>
                                                                <colgroup>
                                                                    <col style="width:40px">
                                                                    <col>
                                                                    <col style="width:60px">
                                                                    <col style="width:60px">
                                                                    <col style="width:77px">
                                                                </colgroup>
                                                                <thead>
                                                                    <tr>
                                                                        <th><span>순위</span></th>
                                                                        <th><span>분류</span></th>
                                                                        <th><span>비교일</span></th>
                                                                        <th><span>기준일</span></th>
                                                                        <th><span>변화량</span></th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    <div class="ui_brd_list is-scroll not_top_brd not_bot_brd" style="height:289px;">
                                                        <div class="wrap">
                                                            <table>
                                                                <caption>목록</caption>
                                                                <colgroup>
                                                                    <col style="width:40px">
                                                                    <col>
                                                                    <col style="width:60px">
                                                                    <col style="width:60px">
                                                                    <col style="width:60px">
                                                                </colgroup>
                                                                <tbody id="case_list">
                                                                    <!-- 데이터 없는 경우
                                                                        <tr><td colspan="4" class="no_over no_data in_list" style="height: 283px"><span class="ui_no_data_txt">데이터가 없습니다.</span></td></tr>
                                                                    데이터 없는 경우 -->
                                                                    <tr class="active">
                                                                        <td>1</td>
                                                                        <td><a href="#" class="lnk"><strong class="txt" title="건강">건강</strong></a></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span class="ui_fluc before up">99.1K</span></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>2</td>
                                                                        <td><a href="#" class="lnk"><strong class="txt" title="건강">건강</strong></a></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span class="ui_fluc before dn">99.1K</span></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>3</td>
                                                                        <td><a href="#" class="lnk"><strong class="txt" title="건강">건강</strong></a></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span class="ui_fluc before none">0</span></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>4</td>
                                                                        <td><a href="#" class="lnk"><strong class="txt" title="건강">건강</strong></a></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span class="ui_fluc before up">99.1K</span></td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </section>
                                        </div>
                                        <!-- // 분류 -->

                                        <!-- 연관 키워드 -->
                                        <div class="ui_col w6">
                                            <section class="ui_box dims" id="section_12">
                                                <div class="box_header">
                                                    <h4>연관 키워드</h4>
                                                    <div class="box_header_rc">
                                                        <button type="button" class="ui_btn" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                                    </div>
                                                </div>
                                                <div class="box_content_header">
                                                    <div class="rc">
                                                        <strong>정렬</strong>
                                                        <div class="dcp">
                                                            <select id="reltaion_keyword_orderBy" style="width: 120px">
                                                                <option value="0">건수</option>
                                                                <option value="1">증가율</option>
                                                            </select><label for="reltaion_keyword_orderBy"></label>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="box_content no_pad ui_loader_container" id="ajax_con_12">
                                                    <div class="ui_brd_list not_bot_brd">
                                                        <div class="wrap">
                                                            <table>
                                                                <caption>목록</caption>
                                                                <colgroup>
                                                                    <col style="width:40px">
                                                                    <col>
                                                                    <col style="width:60px">
                                                                    <col style="width:60px">
                                                                    <col style="width:77px">
                                                                </colgroup>
                                                                <thead>
                                                                    <tr>
                                                                        <th><span>순위</span></th>
                                                                        <th><span>분류</span></th>
                                                                        <th><span>비교일</span></th>
                                                                        <th><span>기준일</span></th>
                                                                        <th><span>변화량</span></th>
                                                                    </tr>
                                                                </thead>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    <div class="ui_brd_list is-scroll not_top_brd not_bot_brd" style="height:289px;">
                                                        <div class="wrap">
                                                            <table>
                                                                <caption>목록</caption>
                                                                <colgroup>
                                                                    <col style="width:40px">
                                                                    <col>
                                                                    <col style="width:60px">
                                                                    <col style="width:60px">
                                                                    <col style="width:60px">
                                                                </colgroup>
                                                                <tbody id="relation_list">
                                                                    <tr><td colspan="5" class="no_over no_data in_list" style="height: 283px"><span class="ui_no_data_txt">데이터가 없습니다.</span></td></tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </section>
                                        </div>
                                        <!-- // 연관 키워드 -->

                                    </div>
                                    <!-- 분류 / 연관 키워드 -->

                                    <!-- 기간별 분석 -->
                                    <div class="ui_row">
                                        <div class="ui_col">
                                            <section class="ui_box dims" id="section_21">
                                                <div class="box_header">
                                                    <h4>기간별 분석<em class="detail_hearder">건강</em><em class="detail_hearder2">건강</em></h4>
                                                    <div class="box_header_rc">
                                                        <button type="button" class="ui_btn" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                                    </div>
                                                </div>
                                                <div class="box_content">
                                                    <div id="ajax_con_21" class="ui_chart_wrap is-not-hand-cursor ui_loader_container" style="height: 220px"></div>
                                                    <script type="text/javascript">
                                                        (function(){
                                                            var chartOption = {
                                                            	"id":"ajax_con_21",
                                                                "type": "serial",
                                                                "categoryField": "category",
                                                                "columnSpacing": 0,
                                                                "columnWidth": 0.5,
                                                                "autoMarginOffset": 0,
                                                                "marginRight": 10,
                                                                "marginTop": 5,
                                                                "colors": [
                                                                    "#ef151e",
                                                                    "#8a7256",
                                                                    "#8dc63f",
                                                                    "#fec500",
                                                                    "#0078c2"
                                                                ],
                                                                "addClassNames": true,
                                                                "pathToImages": "http://design.realsn.com/design_asset/img/amchart/",
                                                                "percentPrecision": 1,
                                                                "categoryAxis": {
                                                                    "autoRotateAngle": 0,
                                                                    "autoRotateCount": 4,
                                                                    "autoWrap": true,
                                                                    "dateFormats": [
                                                                        {
                                                                            "period": "fff",
                                                                            "format": "JJ:NN:SS"
                                                                        },
                                                                        {
                                                                            "period": "ss",
                                                                            "format": "JJ:NN:SS"
                                                                        },
                                                                        {
                                                                            "period": "mm",
                                                                            "format": "JJ:NN"
                                                                        },
                                                                        {
                                                                            "period": "hh",
                                                                            "format": "JJ:NN"
                                                                        },
                                                                        {
                                                                            "period": "DD",
                                                                            "format": "MM-DD"
                                                                        },
                                                                        {
                                                                            "period": "WW",
                                                                            "format": "MMM DD"
                                                                        },
                                                                        {
                                                                            "period": "MM",
                                                                            "format": "MMM"
                                                                        },
                                                                        {
                                                                            "period": "YYYY",
                                                                            "format": "YYYY"
                                                                        }
                                                                    ],
                                                                    "equalSpacing": true,
                                                                    "gridPosition": "start",
                                                                    "parseDates": true,
                                                                    "twoLineMode": true,
                                                                    "axisColor": "#D8D8D8",
                                                                    "centerLabels": true,
                                                                    "centerRotatedLabels": true,
                                                                    "color": "#444444",
                                                                    "gridAlpha": 0,
                                                                    "labelFrequency": 0,
                                                                    "markPeriodChange": false,
                                                                    "minHorizontalGap": 10,
                                                                    "minVerticalGap": 0,
                                                                    "tickLength": 0,
                                                                    "titleFontSize": 12,
                                                                    "titleRotation": 0
                                                                },
                                                                "chartCursor": {
                                                                    "enabled": true,
                                                                    "categoryBalloonDateFormat": "YYYY-MM-DD",
                                                                    "cursorColor": "#000000"
                                                                },
                                                                "chartScrollbar": {
                                                                    "enabled": true,
                                                                    "backgroundColor": "#ADADAD",
                                                                    "dragIconHeight": 14,
                                                                    "dragIconWidth": 14,
                                                                    "graphFillAlpha": 0,
                                                                    "gridAlpha": 0,
                                                                    "offset": 15,
                                                                    "scrollbarHeight": 4,
                                                                    "selectedBackgroundColor": "#D8D8D8",
                                                                    "selectedGraphLineColor": "",
                                                                    "updateOnReleaseOnly": true
                                                                },
                                                                "trendLines": [],
                                                                "graphs": [
                                                                    {
                                                                        "balloonText": "<strong>[[title]]</strong> : [[value]]건",
                                                                        "bullet": "round",
                                                                        "bulletSize": 10,
                                                                        "id": "AmGraph-1",
                                                                        "title": "이물",
                                                                        "type": "smoothedLine",
                                                                        "valueField": "column-1"
                                                                    }
                                                                ],
                                                                "guides": [],
                                                                "valueAxes": [
                                                                    {
                                                                        "id": "ValueAxis-1",
                                                                        "autoGridCount": false,
                                                                        "axisThickness": 0,
                                                                        "color": "#c0c0c0",
                                                                        "dashLength": 2,
                                                                        "gridAlpha": 1,
                                                                        "gridColor": "#D8D8D8",
                                                                        "tickLength": 0,
                                                                        "title": ""
                                                                    }
                                                                ],
                                                                "allLabels": [],
                                                                "balloon": {},
                                                                "legend": {
                                                                    "enabled": true,
                                                                    "align": "center",
                                                                    "autoMargins": false,
                                                                    "color": "#444444",
                                                                    "equalWidths": false,
                                                                    "marginLeft": 0,
                                                                    "marginRight": 0,
                                                                    "marginTop": 10,
                                                                    "markerLabelGap": 6,
                                                                    "markerSize": 11,
                                                                    "markerType": "circle",
                                                                    "periodValueText": "",
                                                                    "spacing": 20,
                                                                    "valueText": "",
                                                                    "valueWidth": 0,
                                                                    "verticalGap": 3
                                                                },
                                                                "titles": [],
                                                                "dataProvider": [
                                                                    {
                                                                        "category": "2019-01-01",
                                                                        "column-1": "5"
                                                                    },
                                                                    {
                                                                        "category": "2019-01-02",
                                                                        "column-1": "3"
                                                                    },
                                                                    {
                                                                        "category": "2019-01-03",
                                                                        "column-1": "1"
                                                                    },
                                                                    {
                                                                        "category": "2019-01-04",
                                                                        "column-1": "5"
                                                                    },
                                                                    {
                                                                        "category": "2019-01-05",
                                                                        "column-1": "1"
                                                                    },
                                                                    {
                                                                        "category": "2019-01-06",
                                                                        "column-1": "8"
                                                                    },
                                                                    {
                                                                        "category": "2019-01-07",
                                                                        "column-1": "7"
                                                                    }
                                                                ]
                                                            }
                                                            var chart = AmCharts.makeChart( "ajax_con_21", chartOption );
                                                        })();
                                                    </script>
                                                </div>
                                            </section>
                                        </div>
                                    </div>
                                    <!-- 기간별 분석 -->

                                    <!-- 데이터 리스트 -->
                                    <div class="ui_row">
                                        <div class="ui_col">
                                            <section class="ui_box dims" id="section_31">
                                                <div class="box_header no_bd">
                                                    <h4>데이터 리스트<em class="detail_hearder">건강</em><em class="detail_hearder2">건강</em></h4>
                                                    <div class="box_header_rc">
                                                        <button type="button" class="ui_btn" title="Excel Download"><span class="icon download">-</span><span class="in_loader">Loading</span></button>
                                                    </div>
                                                </div>
                                                <div class="box_content no_pad ui_loader_container" id="ajax_con_31">
                                                    <div class="ui_brd_list">
                                                        <div class="wrap" style="overflow: auto">
                                                            <table>
                                                                <caption>목록</caption>
                                                                <colgroup>
                                                                    <col style="width:50px">
                                                                    <col style="width:100px">
                                                                    <col style="width:250px">
                                                                    <col style="width:150px">
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
                                                                    <!-- 데이터 없는 경우
                                                                        <tr><td colspan="4" class="no_over no_data in_list" style="height: 283px"><span class="ui_no_data_txt">데이터가 없습니다.</span></td></tr>
                                                                    데이터 없는 경우 -->
                                                                    <tr>
                                                                        <td>1</td>
                                                                        <td><span title="네이버블로그">네이버블로그</span></td>
                                                                        <td class="ui_al"><a href="#" class="lnk"><strong class="txt" title="제목 1 어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구">제목 1 어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구</strong></a></td>
                                                                        <td class="ui_al"><a href="https://realsn.com" class="lnk"><span class="txt" title="https://realsn.com">https://realsn.com</span></a></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span title="123,456,789">123,456k</span></td>
                                                                        <td><span title="홍길동">홍길동</span></td>
                                                                    </tr>
                                                                    <tr><td colspan="16" class="no_over"></td></tr>
                                                                    <tr><td colspan="16" class="no_over"></td></tr>
                                                                    <tr><td colspan="16" class="no_over"></td></tr>
                                                                    <tr><td colspan="16" class="no_over"></td></tr>
                                                                    <tr><td colspan="16" class="no_over"></td></tr>
                                                                    <tr><td colspan="16" class="no_over"></td></tr>
                                                                    <tr><td colspan="16" class="no_over"></td></tr>
                                                                    <tr><td colspan="16" class="no_over"></td></tr>
                                                                    <tr><td colspan="16" class="no_over"></td></tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <div class="footer">
                                                            <div class="ui_paginate">
                                                                <div class="in_wrap">
                                                                    <a href="#" class="page_prev ui_disabled" onclick="return false">이전페이지</a>
                                                                    <a href="#" class="" onclick="return false;">1</a>
                                                                    <a href="#" class="active" onclick="return false;">2</a>
                                                                    <a href="#" onclick="return false;">3</a>
                                                                    <a href="#" onclick="return false;">4</a>
                                                                    <a href="#" onclick="return false;">5</a>
                                                                    <a href="#" class="page_next disabled" onclick="return false;">다음페이지</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </section>
                                        </div>
                                    </div>
                                    <!-- 데이터 리스트 -->

                                </div>
                            </section>
                        </div>
                        <!-- // 세부 내용 -->
                    </div>
                    <!-- // 제품 순위 / 세부내용 -->

				</div>
				<!-- // Content -->

			</div>
		</div>

		<!-- Include FOOTER -->
		<jsp:include page="./inc/inc_footer.jsp" flush="false" />	
		<!-- // Include FOOTER -->
	</div>
<script type="text/javascript" src="../common/js/devel.js"></script>
<jsp:include page="./inc/inc_page_bot.jsp" flush="false" />
