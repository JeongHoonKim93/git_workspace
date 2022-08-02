<%@page import="risk.statistics_pop.StatisticsPopMgr"%>
<%@page import="risk.util.StringUtil"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.HashMap"%>
<%@page import="risk.util.Paging"%>
<%@page import="risk.util.DateUtil"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="risk.util.ParseRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();	
	StatisticsPopMgr mgr = new StatisticsPopMgr();
	DateUtil du = new DateUtil();
	StringUtil su = new StringUtil();
	
	String sDate = pr.getString("sDate");
	String eDate = pr.getString("eDate");
	String psDate =pr.getString("PsDate");
	String peDate = pr.getString("PeDate");
	
	String type = pr.getString("type");
	String settingCode = pr.getString("settingCode");
	String orderBy = pr.getString("orderBy"); //정렬	
	String sg_code = pr.getString("site_group_code");
	
	String mode = pr.getString("mode");
	String type2 = pr.getString("type2");
	String code = pr.getString("code");
	
	ArrayList<HashMap<String, String>> data_list = new ArrayList<HashMap<String ,String>>();	
	if("category".equals(mode)){		
		data_list = mgr.getDataList2(type, type2, code, sDate, eDate, psDate, peDate, settingCode, sg_code, orderBy);
	}
	
	
	
%>

	<%
	if(data_list.size()>0){
		String param_code = "";
		String param_name = "";
		
		for(int i=0; i<data_list.size(); i++){
				if(i==0){
 			        param_code = data_list.get(i).get("IC_CODE");
 			        param_name = data_list.get(i).get("IC_NAME");
 				}
		%>
              <tr>             	 
                 <td><%=i+1%></td>
                 <td><a href="#" class="lnk" onclick="statistics.updateAjax_con_21('<%=type%>','<%=data_list.get(i).get("IC_CODE")%>','<%=data_list.get(i).get("IC_NAME")%>');return false;"><strong class="txt" title="<%=data_list.get(i).get("IC_NAME")%>"><%=data_list.get(i).get("IC_NAME")%></strong></a></td>
                 <td><span title="<%=su.digitFormat( data_list.get(i).get("CNT_P"))%>"><%=su.digitFormat( data_list.get(i).get("CNT_P"))%></span></td>
                 <td><span title="<%=su.digitFormat( data_list.get(i).get("CNT_D"))%>"><%=su.digitFormat( data_list.get(i).get("CNT_D"))%></span></td>
                 <td><span class="ui_fluc before <%=data_list.get(i).get("FACTOR_POINT")%>">
				 <%if("1".equals(orderBy)){
					 out.print(data_list.get(i).get("FACTOR_PER"));
				 }else{
					out.print(data_list.get(i).get("FACTOR_CNT"));
				 } %>
				 </span></td>				  
             </tr>
    
    <%	 }%>
			 <input type="hidden" name="param_type2" id="param_type2" value="<%=type%>"/>
	 		 <input type="hidden" name="param_code2" id="param_code2" value="<%=param_code%>"/>
	      	 <input type="hidden" name="param_name2" id="param_name2" value="<%=param_name%>"/>
		
	<%}else{%>
		
         <tr><td colspan="5" class="no_over no_data in_list" style="height:1151px"><span class="ui_no_data_txt">데이터가 없습니다.</span></td></tr>
	<%}	
		
		
	%>