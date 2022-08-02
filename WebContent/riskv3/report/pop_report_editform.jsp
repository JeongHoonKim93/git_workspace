<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="java.util.ArrayList
				,risk.util.ParseRequest
				,risk.util.StringUtil
				,risk.issue.IssueReportMgr
				,risk.issue.IssueReportBean							
				,risk.util.PageIndex
				,risk.issue.IssueReportBean
				,risk.util.*
				,java.net.URLEncoder
				,risk.namo.NamoMime"
%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	ConfigUtil cu = new ConfigUtil();
	DateUtil du = new DateUtil();
	StringUtil su = new StringUtil();
	NamoMime namoMime = new NamoMime();
	
	String mode = pr.getString("mode","insert");
	String reportType = pr.getString("reportType");
	String nowPage = pr.getString("nowPage","1");
	
	String url = "";
	String param = "";
	//브랜드, 사이트 출처관련 코드
	String typeCode = "";
	String codeName = "";
	String i_seq = "";
	String ir_seq = "";
	String mi_Report = ""; // 주요이슈
	String d_Report = ""; // 일반
	String ir_html = "";
	String ir_title = null;
	String ir_type = null;
	String ir_sdate = null;
	String ir_edate = null;
	String ir_stime = null;
	String ir_etime = null;
	String today = null;
	String id_seq = null;
	String check_no  = null;
	String[] arrI_seq = null;
	
	String tm_id_seqs = "";
	String group_id_seqs = "";
	String rel_id_seqs = "";
	String ic_codes = "";
	
	String reply_id_seqs = "";
	String reply_count = "";
	
	String sixMonthCount = "";
	
	//이미지 경로
	String siteUrl = cu.getConfig("URL");
	String imgUrl = siteUrl + "crosseditor/upload";
	
	if(mode.equals("update")){
		
		IssueReportMgr irMgr = new IssueReportMgr();
		IssueReportBean irBean = new IssueReportBean();
		
		ir_type = pr.getString("ir_type","");
		ir_seq = pr.getString("ir_seq");
		irBean = irMgr.getReportBean(ir_seq);	
		ir_html = irBean.getIr_html();
	}else{
						
		//이미지 경로
		//String imgUrl;
		//String siteUrl = cu.getConfig("URL");
				
		//보고서 종류
		ir_type = pr.getString("ir_type","");		
		
		id_seq = pr.getString("id_seq");	
		i_seq = pr.getString("i_seq","");
		ir_title = pr.getString("ir_title","");
		
		//사이트그룹 관련
		String sg_seq = pr.getString("sg_seq","");
			
		//보고서 날짜
		
		ir_sdate = pr.getString("ir_sdate");
		ir_edate = pr.getString("ir_edate");
		
		ir_stime = pr.getString("ir_stime");
		ir_etime = pr.getString("ir_etime");
		
		//시간값 자릿수에 따른 처리
		if(ir_stime.length()==1){
			ir_stime = "0"+ir_stime+":00:00";
		}else if(ir_stime.length()==2){
			ir_stime = ir_stime+":00:00";
		}
		
		if(ir_etime.length()==1){
			ir_etime = "0"+ir_etime+":59:59";
		}else if(ir_etime.length()==2){
			ir_etime = ir_etime+":59:59";
		}
		
		
		if(ir_type.equals("U")){
			url = siteUrl + "riskv3/report/issue_report_form_issue.jsp";
			param = "id_seq="+id_seq;
			System.out.println(url+"?"+param);
			ir_html = su.encodingRequestPageByPost(url +"?"+param ,"utf-8");
		}else if(ir_type.equals("D2")){
			
			tm_id_seqs = pr.getString("tm_id_seqs");
			/*
			group_id_seqs = pr.getString("group_id_seqs");
			rel_id_seqs = pr.getString("rel_id_seqs");
			reply_id_seqs = pr.getString("reply_id_seqs");
			reply_count = pr.getString("reply_count");
			sixMonthCount = pr.getString("sixMonthCount");
			*/
			String chart1 = pr.getString("chart1", "");
			String chart2 = pr.getString("chart2", "");

			url = siteUrl+"riskv3/report/report_form_daily_bing.jsp";
			param = "ir_type="+ir_type+"&ir_sdate="+ir_sdate+"&ir_edate="+ir_edate+"&ir_stime="+ir_stime+"&ir_etime="+ir_etime;
			param +="&id_seqs=" + tm_id_seqs;
			param +="&chart1="+chart1+"&chart2="+chart2;

			System.out.println(url+"?"+param);
			ir_html = su.encodingRequestPageByPost(url +"?"+param ,"utf-8");
		}
	}
	
	//최종 특수문자 태그 처리
	ir_html = su.ChangeString(ir_html.trim());	
	
%>

<html>
<title><%=SS_TITLE%></title>
<script type="text/javascript" src="../../crosseditor/js/namo_scripteditor.js"></script>
<script type="text/JavaScript">
	function saveReport()
	{
		var f = document.fSend;
		
		if(f.reportType.value == "1"){
			if(document.Wec.MIMEValue){
				f.ir_html.value = document.Wec.MIMEValue;
				f.IEuse.value = 'Y';
			}else{
				f.IEuse.value = 'N';
			}	
		}else if(f.reportType.value == "2"){
			
			f.ir_html.value = CrossEditor.GetValue();
			
		}
		
		f.action = 'issue_report_prc.jsp';
		f.target = 'processFrm';
		f.submit();
	}
</script>
<%
	if(reportType.equals("1")){
%>
<SCRIPT language="javascript" src="/namo/NamoWec7.js"></SCRIPT>			
<SCRIPT language="JScript" FOR="Wec" EVENT="OnInitCompleted()">
			  var form = document.fSend;				
			  var wec = document.Wec;
			  wec.Value = form.ir_html.value;				  	 
</SCRIPT>
<%
	}else if(reportType.equals("2")){
%>		
		<script type="text/javascript">

		var CrossEditor = new NamoSE("namo");
		CrossEditor.params.ImageSavePath =  '<%=imgUrl%>';
		CrossEditor.params.Font = {"Gulim":"굴림","Gungsuh":"궁서","Dotum":"돋움","Malgun Gothic":"맑은 고딕","Batang":"바탕"
				,"Arial":"Arial","Courier New":"Courier New","David":"David","MS PGothic":"MS PGothic","New MingLiu":"New MingLiu"
				,"Simplified Arabic":"Simplified Arabic","simsun":"simsun","Tahoma":"Tahoma","Times New Roman":"Times New Roman","Verdana":"Verdana"};
		CrossEditor.EditorStart();
		function OnInitCompleted(e){
		  e.editorTarget.SetValue(document.fSend.ir_html.value); // 컨텐츠 내용 에디터에 삽입
		}

		</script>
		
<%		
	}
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<iframe id="processFrm" name ="processFrm" width="0" height="0" ></iframe>
<form name="fSend" id="fSend" method="post">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="ir_title" value="<%=pr.getString("ir_title","")%>">
<input type="hidden" name="ir_type" value="<%=ir_type%>">
<input type="hidden" name="ir_html" value="<%=ir_html%>">
<input type="hidden" name="ir_seq" value="<%=ir_seq%>">
<input type="hidden" name="reportType" value="<%=reportType%>">
<input type="hidden" name="IEuse" value="Y">
<input type="hidden" name="reply_id_seqs" value="<%=ir_seq%>">
<input type="hidden" name="reply_count" value="<%=ir_seq%>">
<input type="hidden" name="id_seq" value="<%=id_seq%>">
<input type="hidden" name="nowPage" value="<%=nowPage%>">

<table width="730" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="10"></td>
	</tr>
	<tr>
		<td align="right">
			<img src="../../images/report/btn_save2.gif"  hspace="5" onclick="saveReport();" style="cursor:pointer;">&nbsp;<img src="../../images/report/btn_cancel.gif" onClick="window.close();" style="cursor:pointer;">&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td height="5"></td>
	</tr>
</table>
</form>
</body>    
</html>