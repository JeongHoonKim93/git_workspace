<%@page import="risk.util.StringUtil"%>
<%@page import="risk.dashboard.main.MainMgr"%>
<%@page import="risk.util.DateUtil"%>
<%@page import="risk.json.JSONArray"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="risk.json.JSONObject"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();	
	MainMgr mgr = new MainMgr();
	DateUtil du = new DateUtil();
	StringUtil su = new StringUtil();
	
	String sg_seqs = pr.getString("sg_seqs");
	String company_type = pr.getString("company_type");
	String company_code = pr.getString("company_code");
	String sDate = pr.getString("sDate");		//시작일
	String eDate = pr.getString("eDate");		//종료일
	String keyword_type = pr.getString("keyword_type");
	String search_keyword = pr.getString("search_keyword");
	int rowLimit = pr.getInt("rowLimit",5);
	
	String master_type = pr.getString("master_type","7"); //유형1
	String target_type = pr.getString("target_type","8"); //유형2
	
	
	int interval = (int)du.DateDiff("yyyy-MM-dd",eDate, sDate )+1;
	du.setDate(sDate);
	du.addDay( - interval);
	String psDate = du.getDate("yyyy-MM-dd");
	
	du.setDate(eDate);
	du.addDay( - interval);
	String peDate = du.getDate("yyyy-MM-dd");

	ArrayList<HashMap<String, String>> data_list = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> data_map = new HashMap<String , String>();
	
	data_list = mgr.get_RelationKeywordList(company_type, company_code, sDate, eDate, sg_seqs, rowLimit, keyword_type, search_keyword);
		
%>

	<% 
	
		if(data_list.size()>0){
			for(int i=0; i<data_list.size(); i++){
				data_map = data_list.get(i);
	%>
	     <tr>
             <td class="rank"><span><%=i+1%></span></td>
             <td><span title="<%=data_map.get("WORD")%>"><%=data_map.get("WORD")%></span></td>
             <td><button type="button" class="lnk" target="_blank" title="새창에서 링크 열기" onclick="showPopup('rowCnt=10&sDate=<%=sDate%>&eDate=<%=eDate%>&company_code=<%=company_code%>&sg_seqs=<%=sg_seqs%>&keyword_type=<%=keyword_type%>&search_keyword=<%=search_keyword%>&rk_seq=<%=data_map.get("RK_SEQ")%>&color=<%=data_map.get("WORD_COLOR")%>&rel_cnt=(<%=data_map.get("CNT")%>건)&Dtitle=연관어 분석&head_title=<%=data_map.get("WORD")%>');	return false; "><span><%=data_map.get("CNT")%></span></button></td>
         </tr>        
    
    <% 		}
		} else if(data_list.size() == 0){
			
	%>		
             <tr><td class="rank"><span>1</span></td><td>-</td><td></td></tr>
             <tr><td class="rank"><span>2</span></td><td>-</td><td></td></tr>
             <tr><td class="rank"><span>3</span></td><td>-</td><td></td></tr>
             <tr><td class="rank"><span>4</span></td><td>-</td><td></td></tr>
             <tr><td class="rank"><span>5</span></td><td>-</td><td></td></tr>
             <tr><td class="rank"><span>6</span></td><td>-</td><td></td></tr>
             <tr><td class="rank"><span>7</span></td><td>-</td><td></td></tr>
             <tr><td class="rank"><span>8</span></td><td>-</td><td></td></tr>
             <tr><td class="rank"><span>9</span></td><td>-</td><td></td></tr>
             <tr><td class="rank"><span>10</span></td><td>-</td><td></td></tr>
	
	
	
	<% 		
		}
		
		//빈칸 채우기
		int tmp_length = rowLimit - data_list.size();
		if(data_list.size()>0){
		if(tmp_length>0){
			for(int i=0; i<tmp_length; i++){
				//if( i== ((tmp_length/2)) ) {
				%>
					<!-- <tr><td colspan="4" class="no_over no_data in_list"><span class="ui_no_data_txt">키워드가 없습니다.</span></td></tr> -->
				<%	
				//}else{
				%>	
            	 <tr><td class="rank"><span></span></td><td>-</td><td></td></tr>
				<%
				//}
			}
		  }	
		}
	
		%>


