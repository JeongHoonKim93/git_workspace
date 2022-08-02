<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList
                 ,risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,risk.util.StringUtil
                 ,risk.search.solr.SubForm
                 ,risk.search.solr.SolrSearch"
%>
<%@include file="../../inc/sessioncheck.jsp" %>
<%@page import="java.net.URLDecoder"%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	DateUtil du = new DateUtil();
	StringUtil su = new StringUtil();
	
	
	//String reSearchKey = new String(request.getParameter("reSearchKey").getBytes("iso-8859-1"), "UTF-8");
	String reSearchKey = pr.getString("reSearchKey", "");
	String xml = "";	
	String startDate  = pr.getString("startDate", "");
	String endDate = pr.getString("endDate", "");
	String sgroup = pr.getString("sgroup", "");
	String s_seq = pr.getString("s_seq", "");
	String searchDate = pr.getString("searchDate");
	String type=pr.getString("type");
	String chartType = pr.getString("chartType", "bar");
	String sg_name = pr.getString("sg_name", "");
	SolrSearch solr = new SolrSearch();
	
	
	if(sgroup.length() > 0){
		sgroup += " ";
	}
	
	//차트 XML가져오기
	if(chartType.equals("bar")){
		xml = solr.getChartXml(reSearchKey, startDate, endDate, searchDate, sgroup,s_seq, SS_M_ID);
	}else{
		xml = solr.getLineChartXml(reSearchKey, startDate, endDate, searchDate, sgroup,sg_name,s_seq, SS_M_ID);
	}
	System.out.println(xml);
	System.out.println("#########################################");
	System.out.println("##차트XML 가져오기 완료##");
	System.out.println("#########################################");
	
	//그래프 html5로 변환
	String ua=request.getHeader("User-Agent").toLowerCase();
	String script = "";
	if(ua.indexOf("compatible") > 0 || /*윈도우*/
       ua.indexOf("firefox") > 0    || /*파이어폭스*/
       (ua.indexOf("windows nt") > 0 && ua.indexOf("chrome") > 0)){/*크롬*/
		script = "";
	}else{
		script = "FusionCharts.setCurrentRenderer('javascript');";
	}
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link rel="stylesheet" href="css/basic.css" type="text/css">
<style type="text/css">
.selectBox1 {behavior:url('./selectbox.htc'); }
.line_change01 {  border:1px solid #FFFFFF; background-color:#FFFFFF;}
.line_change02{  border:1px solid #E4E4E4; background-color:#F7F7F7;}
</style>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<%if(script.equals("")){%>
	<script src="<%=SS_URL%>js/FusionCharts.js" type="text/javascript"></script>
<%	}else{%>
	<script src="<%=SS_URL%>js_trial/FusionCharts.js" type="text/javascript"></script>
<%	}%>
<script language="JavaScript" type="text/JavaScript">
<!--

<%=script%>

var chart_type = '<%=chartType%>';
var ChartXml = "<%=xml%>";
function responseXml()
{
	var f = document.fSolr;
	//var ChartXml = f.xml.value;
	
	if(ChartXml != ""){
		var swfName = '';
		if(chart_type == 'bar'){
			swfName = 'Column2D.swf';
		}else{
			swfName = 'MSLine.swf';
		}
		var chart = new FusionCharts("chart/charts/"+swfName, "ChartId", "820", "230");
		chart.setDataXML(ChartXml);
		chart.render("chartdiv");
		 
	}else{	
		var chart2 = document.getElementById("chartdiv");//첫로딩이미지
		chart2.innerHTML = "<img id=\"loadingBar\" src=\"../../../images/search/no_data.gif\" >";
	}
}

function chartView(check){
	var f = document.fSolr;
	var Obj = document.getElementById("chartview");
	 
	if(check){
		Obj.style.position = '';
		Obj.style.display = '';
		f.grfCheck.value = 1;	
	}else{
		Obj.style.position = 'absolute';
		Obj.style.display = 'none';
		f.grfCheck.value = 0;	
	}
}   



//-->
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >

<!-- 기사 분류 메뉴  -->

<form name="fSolr" action ="search_main_contents_solr.jsp" method="post">
<input type="hidden" name="nowpage" value ="1">
<!--<input type="hidden" name="xml" value="<%//=xml%>">-->
<table width="820" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td style="padding:0px 0px 0px 0px" align="left" valign="top">
		<table width="820" border="0" cellspacing="0" cellpadding="0">
        	<tr>
        		<td width="820" valign="top">
        		<div id="chartBox" >
				<table width="820" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<div id="chartview" >
							<table width="820" height="205" border="0" cellspacing="0" cellpadding="0">
				               	<tr>
				               		<td></td>
				              	</tr>
				               	<tr>
				               		<td align="center" style="padding:0px 0px 0px 0px">
				               			<div id="chartdiv" align="center">
				               			
				               			</div>
				               		</td>
				              		</tr>
				               	<tr>
				               		<td></td>
				              	</tr>
				               </table>
				              </div>
				        </td>
					</tr>
					<tr>
						<td></td>
					</tr>
				</table>
				</div>	
        		</td>

       		</tr>
       	</table></td>
	</tr>
</table>

<%
	ArrayList arDate = null;
	if(solr.arr_list != null && solr.arr_list.size() > 0){
		arDate = (ArrayList)solr.arr_list.get(0);
		
		String re_sgroup = "";
    	String ar_sgroup[] = null;
    	ArrayList arTitle = new ArrayList();
		re_sgroup = sgroup.substring(sgroup.indexOf("s_code=(")+8, sgroup.length()-2);
 	  
 	  
 	 	ar_sgroup = re_sgroup.split(" OR ");
 	 
	 	 for(int i =0; i < ar_sgroup.length; i++){
	 		 if(ar_sgroup[i].equals("N")){
	 			arTitle.add("언론" + "," + "#AFD8F8" + "," + "#E7F3FD");
	 		 }else if(ar_sgroup[i].equals("K")){
	 			arTitle.add("커뮤니티" + "," + "#F6BD0F" + "," + "#FEF5DB"); 
	 		 }else if(ar_sgroup[i].equals("B")){
	 			arTitle.add("블로그" + "," + "#8BBA00" + "," + "#F3F8E5");
	 		 }else if(ar_sgroup[i].equals("C")){
	 			arTitle.add("까페" + "," + "#FF8E46" + "," + "#FFF3EC");
	 		 }else if(ar_sgroup[i].equals("T")){
	 			arTitle.add("트위터" + "," + "#109595" + "," + "#E5F3F3");
	 		 }else if(ar_sgroup[i].equals("E")){
	 			arTitle.add("기타" + "," + "#D85151" + "," + "#FBECEC");
	 		 }
	 	 }	
		
		
		
%>

<table width="820" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
  <tr>
    <td width="74" height="30" background="../../../images/search/td_bg.gif" bgcolor="#FFFFFF" style="padding:0px 0px 0px 10px"><strong>날짜</strong></td>
    
    <%
    	SubForm sf = null;
    	for(int i =0; i < 10; i++){
    		sf = (SubForm)arDate.get(i);
    %>
    <td width="74" align="center" background="../../../images/search/td_bg.gif" bgcolor="#FFFFFF"><strong><%=du.getDate(sf.getDate(),"MM/dd")%></strong></td>
    <%} %>
  </tr>
  
<%
	String getCnt = "";

 	if(solr.arr_list.size() > 1){
%> 	
 
 	<%
	  ArrayList arData = null;
 	  String[] master = null;
 	  
	  	for(int i =0; i < solr.arr_list.size(); i++){
	  		arData = (ArrayList)solr.arr_list.get(i);
	  		master = ((String)arTitle.get(i)).split(",");
	  		
	  %>	
	  <tr>
	    <td height="30" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="3" height="30" bgcolor="<%=master[1]%>"></td>
	        <td bgcolor="<%=master[2]%>" style="padding:0px 0px 0px 7px"><strong><%=master[0]%></strong></td>
	      </tr>
	    </table></td>
	    <%
	    
	    
	    	for(int j =0; j < 10; j++){
	    		
	    		sf = (SubForm)arData.get(j);
	    		if(sf.getDate().equals(searchDate) && sf.getName().equals(sg_name)){
	    			getCnt = "<strong>"+ su.digitFormat(sf.getCnt()) +"</strong>";
	    		}else{
	    			getCnt = su.digitFormat(sf.getCnt());
	    		}
	    		
	    %>
	    <td align="center" bgcolor="#FFFFFF" class="gray"><span style="cursor: pointer;" onclick="parent.searchDataList_source(<%=sf.getDate()%>,'<%=sf.getName()%>');"><%=getCnt%></span></td>
	    <%} %>
	  </tr>
  	<%} %>
 
 		
<% 		
 	}else{
%> 		
 
 	<%
	  	ArrayList arData = null;
 		arData = (ArrayList)solr.arr_list.get(0);
	%>	
	  <tr>
	    <td height="30" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="3" height="30" bgcolor="#AFD8F8"></td>
	        <td bgcolor="#E7F3FD" style="padding:0px 0px 0px 7px"><strong>합계</strong></td>
	      </tr>
	    </table></td>
	    <%
	    	
	    	for(int i =0; i < 10; i++){
	    		sf = (SubForm)arData.get(i);
	    	
	    		if(sf.getDate().equals(searchDate)){
	    			getCnt = "<strong>"+ su.digitFormat(sf.getCnt()) +"</strong>";
	    		}else{
	    			getCnt = su.digitFormat(sf.getCnt());
	    		}
	    	
	    %>
	    <td align="center" bgcolor="#FFFFFF" class="gray"><span style="cursor: pointer;" onclick="parent.searchDataList(<%=sf.getDate()%>);"><%=getCnt%></span></td>
	    <%} %>
	  </tr>
  	
 
 
 		
 		
 		
 		
 		
 		
 		
 		
<% 		
 	}
%>
</table>





<table width="820" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
  <tr>
    <%
    	sf = null;
    
    	for(int i =10; i < arDate.size(); i++){
    		sf = (SubForm)arDate.get(i);
    		if(i==10){
    %>
    			<td width="74" height="30" align="center" background="../../../images/search/td_bg.gif" bgcolor="#FFFFFF" style="padding:0px 0px 0px 9px"><strong><%=du.getDate(sf.getDate(),"MM/dd")%></strong></td>			
    <%			
    		}else{
    %>			
    			<td width="74" align="center" background="../../../images/search/td_bg.gif" bgcolor="#FFFFFF"><strong><%=du.getDate(sf.getDate(),"MM/dd")%></strong></td>
    	
    <%			
    		}
    	} 
    %>
  </tr>
  
<%
 	if(solr.arr_list.size() > 1){
%> 	
 
 	<%
	  ArrayList arData = null;
 	  String[] master = null;
	  	for(int i =0; i < solr.arr_list.size(); i++){
	  		arData = (ArrayList)solr.arr_list.get(i);
	  		master = ((String)arTitle.get(i)).split(",");
	  %>	
	  <tr>
	    <%
	    
	    	for(int j =10; j < arData.size(); j++){
	    		sf = (SubForm)arData.get(j);
	    		
	    		if(sf.getDate().equals(searchDate) && sf.getName().equals(sg_name)){
	    			getCnt = "<strong>"+ su.digitFormat(sf.getCnt()) +"</strong>";
	    		}else{
	    			getCnt = su.digitFormat(sf.getCnt());
	    		}
	    		
	    		
	    		//if(j==10){
	    %>
	    			<td height="30" align="center" bgcolor="#FFFFFF" class="gray"><span style="cursor: pointer;" onclick="parent.searchDataList_source(<%=sf.getDate()%>,'<%=sf.getName()%>');"><%=getCnt%></span></td>
	    
	    			
	    <%			
	    //		}else{
	    %>
<!--	    			<td align="center" bgcolor="#FFFFFF" class="gray"><span style="cursor: pointer;" onclick="parent.searchDataList_source(<%=sf.getDate()%>,'<%=sf.getName()%>');"><%=su.digitFormat(sf.getCnt())%></span></td>-->
	    
	    			
	    <%			
	    //		}
	    	} 
	    %>
	  </tr>
  	<%} %>
 
 		
<% 		
 	}else{
%> 		
 
 	<%
	  	ArrayList arData = null;
 		arData = (ArrayList)solr.arr_list.get(0);
	%>	
	  <tr>
	    <%
	    
	    	for(int i =10; i < arData.size(); i++){
	    		sf = (SubForm)arData.get(i);
	    		
	    		
	    		if(sf.getDate().equals(searchDate)){
	    			
	    			getCnt = "<strong>"+ su.digitFormat(sf.getCnt()) +"</strong>";
	    		}else{
	    			
	    			getCnt = su.digitFormat(sf.getCnt());
	    		}
	    		
	    		//if(i==10){
	    %>
	    
	    		<td height="30" align="center" bgcolor="#FFFFFF" class="gray"><span style="cursor: pointer;" onclick="parent.searchDataList(<%=sf.getDate()%>);"><%=getCnt%></span></td>			
	    <%			
	    		//}else{
	    %>
<!--	    		<td align="center" bgcolor="#FFFFFF" class="gray"><span style="cursor: pointer;" onclick="parent.searchDataList(<%=sf.getDate()%>);"><%=su.digitFormat(sf.getCnt())%></span></td>-->
	    
	    <%					
	    //		}
	    	} 
	    %>
	  </tr>
  	
 
 
 		
 		
 		
 		
 		
 		
 		
 		
<% 		
 	}
%>
</table>


		
<%		
	}
%>







</form>
</body>
</html>
<script>
responseXml();
</script>