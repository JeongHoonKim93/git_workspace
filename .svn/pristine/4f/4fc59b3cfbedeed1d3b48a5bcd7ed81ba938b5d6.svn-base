<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="risk.issue.IssueDataBean
                ,risk.issue.IssueMgr
                ,java.util.ArrayList
                ,risk.util.ParseRequest
                ,risk.issue.IssueCodeMgr
                ,risk.issue.IssueCodeBean
                ,risk.issue.IssueBean                
                ,risk.search.MetaMgr
                ,risk.search.userEnvMgr
                ,risk.search.userEnvInfo
                ,risk.report.ReportDataSuperBean
                ,risk.report.ReportMgrMain  
                ,risk.util.StringUtil
                ,risk.util.DateUtil
                ,risk.util.PageIndex" %>
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	ReportMgrMain reptMgr = new ReportMgrMain();
	String ir_type = pr.getString("ir_type");
	//보고서 날짜
	String ir_sdate = pr.getString("ir_sdate");
	String ir_edate = pr.getString("ir_edate");
	
	String ir_stime = pr.getString("ir_stime");
	String ir_etime = pr.getString("ir_etime");
	
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
	
	ArrayList arData1 = new ArrayList();
	arData1 = reptMgr.getDailyReport("2", "1", ir_sdate, ir_stime, ir_edate, ir_etime);
	
%>
<!-- 업계 언론 정보 -->
<table>
	<tr>
		<td style="height:50px;"><span class="sub_tit">업계 언론 정보</span></td>
	</tr>
</table>
<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
<col width="5%"><col width="12%"><col width="38%"><col width="5%"><col width="9%"><col width="9%"><col width="7%">
	<tr>
		<th><input type="checkbox" name="RPCheckall" value="" id="tt" onclick="checkAll2(this.name);"></th>
		<th>성향</th>
		<th>제목</th>
		<th></th>
		<th>출처</th>
		<th>날짜</th>
		<th>확산</th>
	</tr>
<%
		String sunghyang = "";
		String title = "";
		String site_name = "";
		ReportDataSuperBean.issueBean idBean = null;	
		if(arData1.size() > 0){
			for(int i = 0; i < arData1.size(); i++){
				idBean = (ReportDataSuperBean.issueBean)arData1.get(i);
				sunghyang = idBean.getTemp1();

				title = idBean.getId_title();
				title = title.replaceAll("&#39;", "'").replaceAll("&#34;", "\"").replaceAll("&lt;","<").replaceAll("&gt;", ">");				
				title = su.cutString(title, 28, "...");
				//title = su.cutString(idBean.getId_title(), 33, "...");

%>				

	<tr>
		<%-- <td><input type="checkbox" id="RPCheck<%=i %>" name="RPCheck" value="<%=idBean.getId_seq()%>" onclick="getListCnt(<%=i%>);"></td> --%>
		<td><input type="checkbox" id="RPCheck<%=i %>" name="RPCheck" value="<%=idBean.getId_seq()%>"></td>
		<td><p class="tendency_0<%=sunghyang%>"><%if(sunghyang.equals("1")){out.print("긍정");}else if(sunghyang.equals("2")){out.print("부정");}else{out.print("중립");}%></p></td>
		<td><p style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board_01_tit_0<%= idBean.getMd_type()%>" title="<%=idBean.getId_title()%>"><%=title%></p></td>
		<td><a onClick="hrefPop('<%=idBean.getId_url()%>');" href="javascript:void(0);" ><img src="../../images/issue/ico_original.gif" align="absmiddle" /></a></td>
		<td><p style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board01_category" title="<%=idBean.getMd_site_name()%>"><%=idBean.getMd_site_name()%></p></td>
		<td><%=idBean.getFormatMd_date("yyyy-MM-dd")%></td>
		<td><%=idBean.getMd_same_ct()%></td>
	</tr>
<%
			}
		}
%>
</table>

<style>
<!--
.gray {
	font-size: 12px;
	color: #666666;
	line-height: normal;
	font-family: Dotum;
}
	
.input_text {height:60px; border-top: 1px #CFCFCF solid; border-left: 1px #CFCFCF solid; border-right: 1px #CFCFCF solid; border-bottom: 1px #CFCFCF solid; background:#FFFFFF; font-size:12px; color:#666666;line-height: 16px;}
-->
</style>
<div id="summary" style="position: absolute; display: none;">
<input type="hidden" id="save_id_seq" name="save_id_seq">
<table width="430" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="62"><img src="../../images/report/popup/pop_top_01.gif" width="62" height="27" /></td>
        <td background="../../images/report/popup/pop_top_02.gif">&nbsp;</td>
        <td width="8"><img src="../../images/report/popup/pop_top_03.gif" width="8" height="27" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="430" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="7"><img src="../../images/report/popup/pop_top_04.gif" width="7" height="110" /></td>
        <td valign="top" background="../../images/report/popup/pop_top_05.gif" style="padding-top:6px; padding-bottom:6px;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><textarea name="txt_summary" cols="70" rows="5" class="input_text" id="txt_summary"></textarea></td>
          </tr>
          <tr>
            <td height="7"></td>
          </tr>
          <tr>
            <td align="center">
            	<img src="../../images/report/popup/btn_save.gif" width="35" height="20" style="cursor: pointer;" onclick="popSave();">
            	<img src="../../images/report/popup/btn_cancel.gif" width="35" height="20" hspace="5" style="cursor: pointer;" onclick="popClose()">
            </td>
          </tr>
        </table></td>
        <td width="9"><img src="../../images/report/popup/pop_top_06.gif" width="9" height="110" /></td>
      </tr>
    </table></td>
  </tr>
</table>
</div>
<script>

	var pre_seq;
	function showSummary(id_seq){
		var obj = document.getElementById("summary");
		var summary = document.getElementById("txt_summary");
		var save_id_seq = document.getElementById("save_id_seq"); 
		var img = document.getElementById("cafe" + id_seq).getBoundingClientRect();
		
		
		var content = document.getElementById("id_content" + id_seq);
		 
		save_id_seq.value = id_seq;
		summary.scrollTop = 0;
		summary.value = content.value;
		obj.style.top = document.body.scrollTop + img.top + 20;
		obj.style.left = 200;
		
		
		
		if(pre_seq != id_seq){
			obj.style.display = '';	
		}else{
			if(obj.style.display == 'none'){
				obj.style.display = '';
			}else{
				obj.style.display = 'none';
			}
		}
		
		pre_seq = id_seq;  
	}
	
	function popSave(){
		var f = document.fSend;
		f.mode.value = "summary";
		f.target = 'prc';
		f.action = 'issue_report_prc.jsp';
		f.submit();
	}
	
	function popClose(){
		var obj = document.getElementById("summary").style.display = 'none';
	}
	
	function MoveContent(){
		
		document.getElementById("id_content" + document.getElementById("save_id_seq").value).value = document.getElementById("txt_summary").value;
		popClose();
	}
	
</script>