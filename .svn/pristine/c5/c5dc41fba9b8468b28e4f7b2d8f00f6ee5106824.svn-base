<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="	risk.issue.IssueDataBean
					,risk.issue.IssueMgr
					,java.util.ArrayList
					,java.util.List
					,risk.util.ParseRequest
					,risk.admin.member.MemberGroupBean
					,risk.admin.member.MemberDao
					,risk.issue.IssueCodeMgr
					,risk.issue.IssueCodeBean
					,risk.search.MetaMgr
					,risk.search.userEnvMgr
                 	,risk.search.userEnvInfo
                 	,risk.util.DateUtil	
                 	,risk.sms.AddressBookDao
                 	,risk.sms.AddressBookGroupBean
                 	,risk.admin.membergroup.membergroupBean              
                 	,java.util.Iterator
                 	,risk.admin.keyword.KeywordMng
                 	,risk.admin.info.*
                 	,risk.admin.site.SiteBean
                 	,risk.admin.site.SiteMng
                 	,risk.admin.keyword.KeywordBean
                 	"%>
<%@page import="risk.admin.site.SitegroupBean"%>
<%
	DateUtil 		du 		= new DateUtil();
	ParseRequest pr = new ParseRequest(request);
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();	
	IssueCodeBean ICBean = null;
	MemberDao mDao = new MemberDao();
	AddressBookDao abDao = new AddressBookDao();
	AddressBookGroupBean abgBean = new AddressBookGroupBean();
	
	//정보 그룹 관련
	ArrayList igArr = new ArrayList();
   	InfoGroupMgr igMgr = new InfoGroupMgr();
   	InfoGroupBean igBean = new InfoGroupBean();
	
	//사이트 관련
	List siteGroup = new ArrayList();
	SiteMng siteMng = new SiteMng();
	SitegroupBean sBean = new SitegroupBean();
	
	//키워드 관련
	KeywordMng keMng = new KeywordMng();
	KeywordBean kBean = new KeywordBean();
	ArrayList keywordGroup = new  ArrayList();	

	pr.printParams();
	String sDate = null;		
	String eDate = null;
	String ir_type = null;
	String ir_title = null;
	String issueDataListVisable = "";
	String infoGroupVisable ="";
	String keywordVisable = "";
	String siteGroupVisable = "";
	
	//이슈선택 코드
	String selected = "";
	String codeTypeName = "";
	ArrayList arrIcBean = null;
	IssueCodeBean icBean = null;
	String typeCode = pr.getString("typeCode");
	
	//이슈 코드
	icMgr.init(0);
	
	ir_type = pr.getString("ir_type","ID");

	sDate    = du.addDay(du.getCurrentDate(),-30,"yyyy-MM-dd");
	eDate    = du.addDay(du.getCurrentDate(),0,"yyyy-MM-dd");		
	
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>RIS-K</title>
<link rel="stylesheet" type="text/css" href="../../css/base.css" />
<style>
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}
</style>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/reportCalendar.js" type="text/javascript"></script>
<script src="<%=SS_URL%>riskv3/report/js/amcharts.js"></script>
<script src="<%=SS_URL%>riskv3/report/js/serial.js"></script>
<script src="<%=SS_URL%>riskv3/report/js/pie.js"></script>
<script src="<%=SS_URL%>riskv3/report/js/xy.js"></script>
<script src="<%=SS_URL%>riskv3/report/js/plugins/export/export.js"></script>

<script>
					
var ir_type = '<%=ir_type%>';
var tempDateTime = 0;

/* 	$(function(){
	if(ir_type == "D"){
		$("#img_im").css("display", "");
	}else{
		$("#img_im").css("display", "none");
	}
}); */

$(function(){
	
	$('input[name=replyCount]').click(function(){
		$(this).val('');
	});
	
	$('input[name=replyCount]').blur(function(){
		if($(this).val() == '') $(this).val('0');
	});
});

function loadList()
{		
	hideSetDate(checkIrTypeValue());
	ilu.sendRequest();
}

function java_all_trim(a) {
    for (; a.indexOf(' ') != -1 ;) {
      a = a.replace(' ','');
    }
    return a;
}

function changeParam(param, param_val){
	$("#"+param).val(param_val);
	//alert($("#"+param).val());
}

function AddDate( day ) {

    var newdate = new Date();
	var resultDateTime;

	if(tempDateTime==0)
	{
		tempDateTime = newdate.getTime();
	}  
	
          
	resultDateTime = tempDateTime + ( day * 24 * 60 * 60 * 1000);      
    newdate.setTime(resultDateTime);
	
    last_ndate = newdate.toLocaleString();

    last_date = java_all_trim(last_ndate);
    last_year = last_date.substr(0,4);
    last_month = last_date.substr(5,2);
    last_mon = last_month.replace('월','');

    if(last_mon < 10) {

        last_m = 0+last_mon;
        last_day = last_date.substr(7,2);
        last_da = last_day.replace('일','');

        if(last_da < 10) {
            last_d = 0+last_da;
        }else{
            last_d = last_da;
        }

    }else{
        last_m = last_mon;

        last_day = last_date.substr(8,2);
        last_da = last_day.replace("일","");

        if(last_da < 10) {
            last_d = 0+last_da;
        }else{
            last_d = last_da;
        }

    }

    last_time = last_year + '-' + last_m + '-' + last_d;

    return last_time;
    
    
}

<%-- 	function changeType(ir_type)
{
	var f = document.fSend;
		
	if(ir_type=='D'){	

		$("#img_im").css('display', '');
		
		f.ir_type.value = 'D';
		f.ir_sdate.value=AddDate(-1);
		f.ir_edate.value=AddDate(0);
		f.ir_title.readOnly=false;
		f.ir_title.value='일일보고서(<%=du.getCurrentDate("MM/dd")%>)';
		f.action = 'issue_report_creater.jsp';	
		f.target = '';
		f.submit();							
		
	}else if(ir_type=='W'){	
		
		$("#img_im").css('display', 'none');			
		
		f.ir_type.value = 'W';
		f.ir_sdate.value=AddDate(-7);
		f.ir_edate.value=AddDate(0);
		f.ir_title.readOnly=false;
		f.ir_title.value='주간보고서(<%=du.getCurrentDate("MM/dd")%>)';
		f.action = 'issue_report_creater.jsp';	
		f.target = '';
		f.submit();							
		
	}
} --%>

//관련 정보 리스트 관련	
$(document).ready(issueListload);

function issueListload()
{
	var f = document.fSend;
	var type = f.ir_type.value;
	var url = '';
	
	if(type == 'D2'){ // 신규 일일 보고서
		OwnPressurl = 'aj_own_press_list.jsp';
		OwnSocialurl = 'aj_own_social_list.jsp';
		RivalPressurl = 'aj_rival_press_list.jsp';
		f.ir_title.value = '[정보] <%=du.getCurrentDate("yyyy년 MM월 dd일")%> Daily Report';
	}
	
	ajax.post2(OwnPressurl, 'fSend', 'OwnPressList', 'ajax-loader2.gif');	//자사 언론 정보
	ajax.post2(OwnSocialurl, 'fSend', 'OwnSocialList', 'ajax-loader2.gif');	//자사 소셜 정보
	ajax.post2(RivalPressurl, 'fSend', 'RivalPressList', 'ajax-loader2.gif');	//업계 언론 정보

	//f.issueDataList.html('');
}
//Url 링크
	var chkPop = 1;
	
function hrefPop(url){
	//window.open(url,'hrefPop'+chkPop,'');
	window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkPop,'');
	chkPop++;
}


//체크박스
function checkAll(chk) {
	var chkChild = $('input[name="'+chk+'"]').attr("name");
	chkChild = chkChild.replace('all','');
	
	if($('input:checkbox[name="'+chk+'"]').is(":checked")){
		$('input:checkbox[name="'+chkChild+'"]').each(function(){
			this.checked = true;
		});
	}else{
		$('input:checkbox[name="'+chkChild+'"]').each(function(){
			this.checked = false;
		});
	}
	}

	function checkSetting()
	{
		var f = document.fSend;
		var temp = '';
		var i = 0; // 체크박스 데이터들 idx
		var j = 0; // 첫 번째 체크박스 데이터
		$('#tm_id_seqs').val('');
		//$('#group_id_seqs').val('');
		//$('#rel_id_seqs').val('');
		
		
		
		$('input:checkbox[name="TMCheck"]').each(function(){

		if($('#TMCheck'+i+'').is(":checked")){
			if(j!=0){
				temp += ','+$('#TMCheck'+i+'').val();
			}else{
				temp += $('#TMCheck'+i+'').val();
			}
			j++;
		}
		i++;
	});		
	$('#tm_id_seqs').val(temp);
	console.log(temp);
	//GROUP
	/*
	temp='';
	i=0;
	j=0;
	$('input:checkbox[name="groupCheck"]').each(function(){

		if($('#groupCheck'+i+'').is(":checked")){
			if(j!=0){
				temp += ','+$('#groupCheck'+i+'').val();
			}else{
				temp += $('#groupCheck'+i+'').val();
			}
			j++;
		}
		i++;
	});		
	$('#group_id_seqs').val(temp);
	*/
	//RELATION
	/*
	temp='';
	i=0;
	j=0;
	$('input:checkbox[name="relCheck"]').each(function(){

		if($('#relCheck'+i+'').is(":checked")){
			if(j!=0){
				temp += ','+$('#relCheck'+i+'').val();
			}else{
				temp += $('#relCheck'+i+'').val();
			}
			j++;
		}
		i++;
	});		
	$('#rel_id_seqs').val(temp);
	*/
	}
	
	var reportList = new Array();
	var reportList2 = "";
	var ReportlistCnt = document.getElementsByName("chkSeq");
	var inputList = "";
	
function getListCnt( index ) {
	var arr_TMCheck = document.getElementsByName("TMCheck");
	var arr_listCnt = document.getElementsByName("chkSeq");
	var checkBoxArr = [];
	
	/*
	$("input[name=TMCheck]:checked").each(function(i){
		checkBoxArr.push($(this).val());
		
	});
	*/
	//체크박스 체크할때
	if (arr_TMCheck[index].checked == true) {	//여기가 문제
		//reportList에 체크한 이슈의 idseq값 저장
		 if(reportList2 == ''){
			 reportList2 = arr_TMCheck[index].value;
          }else{
        	  reportList2 += "," + arr_TMCheck[index].value;
          }
		
		//이슈 체크한 순서 구하기(순번)
		var max = 0;
		inputList = getReportlistCnt2().split(",");
		for (var h = 0; h < inputList.length; h++) {
			if(max < inputList[h]) {
				max = Number(inputList[h]);
            }
		}
		max++;
		arr_listCnt[index].value = max;
		
	//체크박스 체크 해제할때	
	} else {
		//지울 순번
		var delCnt = arr_listCnt[index].value;		
		//temp list
		var result = ""; 
		var tmp_report_list = reportList2.split(',');
		
		//선택해제한 값들 빼고 tmp배열에 저장
		for(var i = 0; i < tmp_report_list.length; i++){
		 	if(arr_TMCheck[index].value != tmp_report_list[i]){
				if(result == ''){
					result = tmp_report_list[i];
			 	}else{
			 		result += "," + tmp_report_list[i];
			 	}	
		 	}
		}
		//reportList에 다시 tmp배열 저장
		reportList2 = result;
		
		//해제한 순번 이후의 순번들은 -1
		for (var i = 0; i < arr_listCnt.length; i++) {
			if (Number(arr_listCnt[i].value) > delCnt) {						
				arr_listCnt[i].value =  Number(arr_listCnt[i].value) - 1;
			}
		}
		//해제한 순번은 빈칸
		arr_listCnt[index].value = "";
		//체크한 이슈가 하나도 없으면 reportList비우기
		if (checkBoxArr.length == 0) {
			reportList2 = "";
		}
	}

} 	

var temp;
var chkSeqs = "";
var group = "";
/*
function getReportlistCnt() {
	chkSeqs2 = "";
	reportList = new Array();
	var f = document.fSend;
	if(ReportlistCnt) {
        for(var i=0; i<ReportlistCnt.length; i++) {
            if(f.chkSeq[i].value != '') {
                if(group == ''){
                	chkSeqs = f.chkSeq[i].value;
                	group = f.chkSeq[i].value + "-" + f.chkIdx[i].value;
                }else{
                	chkSeqs += "," + f.chkSeq[i].value;
                	group += "," + f.chkSeq[i].value + "-" + f.chkIdx[i].value;
                }
            }
        }
    }
	var chkSeqs2 = group.split(",");
	for (var i = 0; i < chkSeqs2.length-1; i++) {			
		for (var j = i+1; j < chkSeqs2.length; j++) {
			if(Number(chkSeqs2[i].split("-")[0]) > Number(chkSeqs2[j].split("-")[0])) {
				var temp = chkSeqs2[i];
				chkSeqs2[i] = chkSeqs2[j];
				chkSeqs2[j] = temp;
			} 
		}
	}
	for (var k = 0; k < chkSeqs2.length; k++) {
		reportList.push(chkSeqs2[k].split("-")[1]);
	}
}
*/

function getReportlistCnt2() {
	var f = document.fSend;
	chkSeqs = "";
	if(ReportlistCnt) {
        for(var i=0; i<ReportlistCnt.length; i++) {
            if(f.chkSeq[i].value != '') {
                if(chkSeqs == ''){
                	chkSeqs = f.chkSeq[i].value;
                }else{
                	chkSeqs += "," + f.chkSeq[i].value;
                }
            }
        }
    }
	return chkSeqs;
}

	//체크박스 전체체크
    function checkAll2(chk) {
    	//reportList = "";
		//var arr_TMCheck = document.getElementsByName("TMCheck");
		var chkChild = $('input[name="'+chk+'"]').attr("name");
		chkChild = chkChild.replace('all','');
				
		if($('input:checkbox[name="'+chk+'"]').is(":checked")){
			$('input:checkbox[name="'+chkChild+'"]').each(function(){
				this.checked = true;
			});
		}else{
			$('input:checkbox[name="'+chkChild+'"]').each(function(){
				this.checked = false;
			});
		}
	}
	
	function checkSetting2(){
		
		group = "";
		reportList = "";
		inputList = "";
		//getReportlistCnt();
 		$('#tm_id_seqs').val(reportList);
 	}
	
	function checkSetting3(){
		
		var check_idseq = "";
		var arr_OPCheck = document.getElementsByName("OPCheck");
		var arr_OSCheck = document.getElementsByName("OSCheck");
		var arr_RPCheck = document.getElementsByName("RPCheck");
				
		for (var i = 0; i < arr_OPCheck.length; i++) {
			if($('input:checkbox[id="OPCheck'+i+'"]').is(":checked")){
				if(check_idseq == ''){
					check_idseq = arr_OPCheck[i].value;
				}else{
					check_idseq += "," + arr_OPCheck[i].value;
				}
			}
		}
		
		for (var i = 0; i < arr_OSCheck.length; i++) {
			if($('input:checkbox[id="OSCheck'+i+'"]').is(":checked")){
				if(check_idseq == ''){
					check_idseq = arr_OSCheck[i].value;
				}else{
					check_idseq += "," + arr_OSCheck[i].value;
				}
			}
		}
		
		for (var i = 0; i < arr_RPCheck.length; i++) {
			if($('input:checkbox[id="RPCheck'+i+'"]').is(":checked")){
				if(check_idseq == ''){
					check_idseq = arr_RPCheck[i].value;
				}else{
					check_idseq += "," + arr_RPCheck[i].value;
				}
			}
		}
		
 		$('#tm_id_seqs').val(check_idseq);
 	}
	
	function clickIdx(index) {
		var arr_TMCheck = document.getElementsByName("TMCheck");
		for (var i = 0; i < arr_TMCheck.length; i++) {
			arr_TMCheck[index].checked =true;
		}
	}
 	
 	function setMajorIssue(){
 		
 		var f = document.fSend;
 		var temp = '';
 		var i = 0; // 체크박스 데이터들 idx
 		var j = 0; // 첫 번째 체크박스 데이터
		$('input:checkbox[name="majorCheck"]').each(function(){

			if($('#majorCheck'+i+'').is(":checked")){
				if(j!=0){
					temp += ','+$('#majorCheck'+i+'').val();
				}else{
					temp += $('#majorCheck'+i+'').val();
				}
				j++;
			}
			i++;
		});		
 		$('#major_ic_code').val(temp);
 	}
 	////////////////////////////////////////AJAX 수신자 설정///////////////////////////////////////
	
	////////////////////수신자 리스트 //////////////////////
	//$(document).ready(pageInit);

 	var kk = 0;
	function preview(reportType)
	{	
		//settingReplyCount();
		var f = document.fSend;
		var ir_type = f.ir_type.value;	
		var chkIdxs = '';
		var chkSeqs = '';
		f.reportType.value = reportType;
		
		if(ir_type=='VT' ){
			$("#message").css("display", "");
			$("#chart1").val("");
			$("#chart2").val("");
			
			getChartImg('chart1', 300, 188);			
			getChartImg('chart2', 300, 188);
			
			var tryCount = 20;
			setTimeout(waitImage, 500);
			function waitImage(){
				if($("#chart1").val() == "" || $("#chart2").val() == ""){
					if(tryCount <= 0){
						return;
					}
					tryCount--;
					
					setTimeout(waitImage, 500);
				}else{
					$("#chartBinary").val("");
					action();
					$(".hiddenChart").css("opacity", 0 );
					$("#previewBtn").css("display", "");
					$("#message").css("display", "none");
				}
			}
			
		}else if(ir_type=='FVT'){
			$("#message").css("display", "");
			$("#IssueChart1_1").val("");
			$("#IssueChart1_2").val("");
			$("#IssueChart1_3").val("");
			$("#IssueChart2_1").val("");
			$("#IssueChart2_2").val("");
			$("#IssueChart2_3").val("");
			$("#IssueChart3").val("");
			$("#IssueChart4").val("");
			
			getIssueChartImg('IssueChart1_1', 214, 182);			
			getIssueChartImg('IssueChart1_2', 214, 182);
			getIssueChartImg('IssueChart1_3', 214, 182);
			getIssueTypeChart('2','IssueChart2_1,IssueChart2_2,IssueChart2_3',214,182); 
			getIssueChartImg('IssueChart3', 664, 187);			
			getIssueChartImg('IssueChart4', 550, 301);
			
			var tryCount = 20;
			setTimeout(waitImage, 500);
			
		 	function waitImage(){
				if( $("#IssueChart1_1").val() == "" 
					|| $("#IssueChart1_2").val() == ""
					|| $("#IssueChart1_3").val() == ""
					|| $("#IssueChart2_1").val() == ""
					|| $("#IssueChart2_2").val() == ""
					|| $("#IssueChart2_3").val() == ""
					|| $("#IssueChart3").val() == ""
					|| $("#IssueChart4").val() == "" 
				){
					if(tryCount <= 0){
						return;
					}
					tryCount--;
					
					setTimeout(waitImage, 500);
				}else{
					$("#chartBinary").val("");
					action();
					$(".hiddenIssueChartBox").css("opacity", 0 );
					$("#previewBtn").css("display", "");
					$("#message").css("display", "none");
				}
			}  
		
		}else if(ir_type=='D' || ir_type=='ID'){	
			checkSetting();
			settingReplyCount();
			action();
		}else if(ir_type == 'D2') {
			$("#message").css("display", "");
			$("#chart1").val("");
			$("#chart2").val("");
			
			getDailyChartImg('chart1', 696, 333);			
			getDailyChartImg('chart2', 696, 333);
			
			var tryCount = 20;
			setTimeout(waitImage, 500);
			function waitImage(){
				if($("#chart1").val() == "" || $("#chart2").val() == ""){
					if(tryCount <= 0){
						return;
					}
					tryCount--;
					
					setTimeout(waitImage, 500);
				}else{
					$("#chartBinary").val("");
					//checkSetting2();
					checkSetting3();				
					//settingReplyCount();
					action();
					$(".hiddenChart").css("opacity", 0 );
					$("#previewBtn").css("display", "");
					$("#message").css("display", "none");
				}
			}
		}else{
			setMajorIssue();
			action();
		}	
	}
	
	function action(){
		var f = document.fSend;
		
		window.open('about:blank', 'PopUp', 'width=820,height=850,scrollbars=yes,status=no,resizable=no');		
		f.action = 'pop_report_editform.jsp';
		f.target = 'PopUp';
		f.method= 'post';
		f.submit(); 
	}
	
	function getChartImg(id, width, height){
		
		$("#chartType").val(id);
		var param = $("#fSend").serialize();
		
		$.ajax({
			type : "POST"
			,async : true
			,url: "aj_getChartData.jsp"
			,timeout: 30000
			,data : param
			,dataType : 'json'
			,async: true
			,success : function(data){				
				if(id == 'chart1'){
					getChart1(id, width, height, data);
				}else if(id == 'chart2'){
					getChart2(id, width, height, data);
				}
			}
		});	
	}
		
	function getChartImg2(id, width, height, ic_code, sDate, eDate, Diff){		
		$("#chartType").val(id);
		var param = $("#fSend").serialize();
		param += "&ic_code="+ic_code+"&sDate="+sDate+"&eDate="+eDate+"&Diff="+Diff;
		
		$.ajax({
			type : "POST"
			,async : true
			,url: "aj_getIssueChartData.jsp"
			,timeout: 30000
			,data : param
			,dataType : 'json'
			,async: true
			,success : function(data){				
				if(id == 'IssueChart2_1' || id == 'IssueChart2_2' || id == 'IssueChart2_3'){					
					getIssueChartType2(id, width, height, data);
				}
			}
		});	
	}
	
	function getDailyChartImg(id, width, height){
		
		$("#chartType").val(id);
		var param = $("#fSend").serialize();
		
		$.ajax({
			type : "POST"
			,async : true
			,url: "aj_getDailyChartData.jsp"
			,timeout: 30000
			,data : param
			,dataType : 'json'
			,async: true
			,success : function(data){				
				if(id == 'chart1'){
					getDailyChart1(id, width, height, data);
				}else if(id == 'chart2'){
					getDailyChart2(id, width, height, data);
				}
			}
		});	
	}		
	
	function getIssueTypeChart(chartType_num, id_list, width, height){
		var id_arr = id_list.split(',');
		var param = $("#fSend").serialize();
		param += "&Type="+chartType_num;
		var ic_code;
		var result;
		$.ajax({
			type : "POST"
			,async : true
			,url: "aj_getIssueTypeData.jsp"
			,timeout: 30000
			,data : param
			,dataType : 'json'
			,async: true
			,success : function(data){
				$("#section_2_category").val(data.Top3Code); //section2 상단 타입명 호출 위한 코드 반환	
				if(data.Top3Code !=""){
					ic_code = data.Top3Code.split(',');
					var tmp_idx = ic_code.length;
					if(ic_code.length<3){						
						var tmp_size = Number(3) - Number(ic_code.length);
						for(var i =0; i<tmp_size; i++){		
							ic_code[tmp_idx++] = 'null';
						};	
					}
						
					
				//데이터 없을 경우 오류 방지
				}else{
					ic_code = [ 'null', 'null', 'null' ];	
				}								
						
				$.each(id_arr,function(idx){					
					getChartImg2(id_arr[idx], width, height, ic_code[idx], data.sDate, data.eDate, data.Diff);
				});					
			}
		});	
		return result;
	}
	
	function getIssueChartImg(id, width, height){
		
		$("#chartType").val(id);
		var param = $("#fSend").serialize();
		
		$.ajax({
			type : "POST"
			,async : true
			,url: "aj_getIssueChartData.jsp"
			,timeout: 30000
			,data : param
			,dataType : 'json'
			,async: true
			,success : function(data){	
				if(id == 'IssueChart1_1' || id == 'IssueChart1_2' || id == 'IssueChart1_3'){
					getIssueChartType1(id, width, height, data);	
				}else if(id == 'IssueChart3'){
					getIssueChartType3(id, width, height, data);
				}else if(id == 'IssueChart4'){
					getIssueChartType4(id, width, height, data);
				}
			}
		});	
	}
	
	
	function getIssueChartType1(id, width, height, data){
		
		var chartOption = {
				"type": "serial",
                "categoryField": "category",
                "rotate": true,
                "marginBottom": 10,
                "marginLeft": 10,
                "marginRight": 10,
                "marginTop": 10,
                "colors": [
                           "#a50021",
                           "#c80026",
                           "#ff0505",
                           "#ff5050",
                           "#ff7c80",
                           "#ff9999",
                           "#ffcccc"
                ],
                "color": "#666666",
                "categoryAxis": {
                    "gridPosition": "start",
                    "axisThickness": 0,
                    "gridCount": 0,
                    "gridThickness": 0,
                    "tickLength": 0
                },
                "trendLines": [],
                "graphs": [
                    {
                        "balloonText": "",
                        "color": "#000000",
                        "fillAlphas": 1,
                        "fixedColumnWidth": 22,                       
                        "labelText": "[[value]]",
                        "title": "이물",
                        "type": "column",
                        "valueField": "column-1",
                        "showAllValueLabels": true
                    },
                    {
                        "balloonText": "",
                        "color": "#000000",
                        "fillAlphas": 1,
                        "fixedColumnWidth": 22,                       
                        "labelText": "[[value]]",
                        "title": "변질",
                        "type": "column",
                        "valueField": "column-2",
                        "showAllValueLabels": true
                    },
                    {
                        "balloonText": "",
                        "color": "#000000",
                        "fillAlphas": 1,
                        "fixedColumnWidth": 22,                       
                        "labelText": "[[value]]",
                        "title": "품질",
                        "type": "column",
                        "valueField": "column-3",
                        "showAllValueLabels": true
                        
                    },
                    {
                        "balloonText": "",
                        "color": "#000000",
                        "fillAlphas": 1,
                        "fixedColumnWidth": 22,                        
                        "labelText": "[[value]]",
                        "title": "포장",
                        "type": "column",
                        "valueField": "column-4",
                        "showAllValueLabels": true
                    },
                    {
                        "balloonText": "",
                        "color": "#000000",
                        "fillAlphas": 1,
                        "fixedColumnWidth": 22,                       
                        "labelText": "[[value]]",
                        "title": "물류",
                        "type": "column",
                        "valueField": "column-5",
                        "showAllValueLabels": true
                    },
                    {
                        "balloonText": "",
                        "color": "#000000",
                        "fillAlphas": 1,
                        "fixedColumnWidth": 22,                        
                        "labelText": "[[value]]",
                        "title": "영업",
                        "type": "column",
                        "valueField": "column-6",
                        "showAllValueLabels": true
                    },
                    {
                        "balloonText": "",
                        "color": "#000000",
                        "fillAlphas": 1,
                        "fixedColumnWidth": 22,                       
                        "labelText": "[[value]]",
                        "title": "취급(건강)",
                        "type": "column",
                        "valueField": "column-7",
                        "showAllValueLabels": true
                    }
                ],
                "guides": [],
                "valueAxes": [
                    {
                        "id": "ValueAxis-1",
                        "stackType": "regular",
                        "zeroGridAlpha": 0,
                        "axisThickness": 0,
                        "gridThickness": 0,
                        "autoGridCount": false,
                        "labelsEnabled": false,
                        "tickLength": 0,
                        "strictMinMax": true,
                        "maximum": data.MAX_LENGTH,         // 가장 높은 이슈의 정보량 합
                        "title": ""
                    }
                ],
                "export": {
            		"enabled": true					
				},
                "allLabels": [],
                "balloon": {},
                "titles": [],
                "dataProvider": data.LIST
			};
		
		var targetId = "hiddenIssueChart"+id.replace("IssueChart", "");
		console.log("targetId:"+targetId);
		
		
				
		$("#"+targetId).css("width", width);
		$("#"+targetId).css("height", height);
		$("#hiddenIssueChartBox").css("height", height);

		var chart = AmCharts.makeChart(targetId, chartOption);	
		chart_to_imgBinary(id, chart);
	}
	
	function getIssueChartType2(id, width, height, data){
		var chartOption = {
				 "type": "serial",
	                "categoryField": "category",
	                "marginBottom": 10,
	                "marginLeft": 10,
	                "marginRight": 10,
	                "marginTop": 10,
	                "colors": [
							"#ffc5c5",
							"#71daff",
							"#92d050",
							"#fecb2e",
							"#609ed6"
	                ],
	                "addClassNames": true,
	                "color": "#666666",
	                "handDrawThickness": 0,
	                "categoryAxis": {
	                    "gridPosition": "start",
	                    "axisColor": "#CCCCCC",
	                    "gridThickness": 0,
	                    "minHorizontalGap": 0,
	                    "tickLength": 0
	                },
	                "trendLines": [],
	                "graphs": [
	                    {
	                        "balloonText": "",
	                        "bullet": "round",
	                        "bulletSize": 5,
	                        "id": "AmGraph-1",
	                        "title": "CJ",
	                        "type": "smoothedLine",
	                        "valueField": "column-1"
	                    },
	                    {
	                        "balloonText": "",
	                        "bullet": "round",
	                        "bulletSize": 5,
	                        "id": "AmGraph-2",
	                        "title": "대상",
	                        "type": "smoothedLine",
	                        "valueField": "column-2"
	                    },
	                    {
	                        "balloonText": "",
	                        "bullet": "round",
	                        "bulletSize": 5,
	                        "id": "AmGraph-3",
	                        "title": "풀무원",
	                        "type": "smoothedLine",
	                        "valueField": "column-3"
	                    },
	                    {
	                        "balloonText": "",
	                        "bullet": "round",
	                        "bulletSize": 5,
	                        "id": "AmGraph-4",
	                        "title": "오뚜기",
	                        "type": "smoothedLine",
	                        "valueField": "column-4"
	                    },
	                    {
	                        "balloonText": "",
	                        "bullet": "round",
	                        "bulletSize": 5,
	                        "id": "AmGraph-5",
	                        "title": "동원",
	                        "type": "smoothedLine",
	                        "valueField": "column-5"
	                    }
	                ],
	                "guides": [],
	                "valueAxes": [
	                    {
	                        "id": "ValueAxis-1",
	                        "zeroGridAlpha": 0,
	                        "axisThickness": 0,
	                        "color": "#CCCCCC",
	                        "dashLength": 3,
	                        "fillColor": "#CCCCCC",
	                        "gridAlpha": 1,
	                        "gridColor": "#CACACA",
	                        "gridThickness": 0,
	                        "labelsEnabled": false,
	                        "tickLength": 0,
	                        "title": ""
	                    }
	                ],
	                "export": {
	            		"enabled": true					
					},
	                "allLabels": [],
	                "balloon": {},
	                "titles": [],
	                "dataProvider":data
			};
		
		var targetId = "hiddenIssueChart"+id.replace("IssueChart", "");
		
		$("#"+targetId).css("width", width);
		$("#"+targetId).css("height", height);
		$("#hiddenIssueChartBox").css("height", height);

		var chart = AmCharts.makeChart(targetId, chartOption);	
		chart_to_imgBinary(id, chart);
	}
	
	function getIssueChartType3(id, width, height, data){
		
		var chartOption = {
				 "type": "serial",
	                "categoryField": "category",
	                "columnWidth": 0.39,
	                "marginBottom": 10,
	                "marginLeft": 10,
	                "marginRight": 10,
	                "marginTop": 20,
	                "colors": [
							"#ffd966",
							"#6fac46",
							"#9db8fa",
							"#e0e0e0"
	                ],
	                "color": "#666666",
	                "categoryAxis": {
	                    "gridPosition": "start",
	                    "axisColor": "#CCCCCC",
	                    "gridCount": 0,
	                    "gridThickness": 0,
	                    "tickLength": 0
	                },
	                "trendLines": [],
	                "graphs": [
	                    {
	                        "balloonText": "",
	                        "color": "#000000",
	                        "fillAlphas": 1,
	                        "id": "AmGraph-1",
	                        "labelText": "[[value]]",
	                        "title": "언론",
	                        "type": "column",
	                        "valueField": "column-1",
	                        "showAllValueLabels": true
	                    },
	                    {
	                        "balloonText": "",
	                        "color": "#000000",
	                        "fillAlphas": 1,
	                        "id": "AmGraph-2",
	                        "labelText": "[[value]]",
	                        "title": "커뮤니티",
	                        "type": "column",
	                        "valueField": "column-2",
	                        "showAllValueLabels": true
	                    },
	                    {
	                        "balloonText": "",
	                        "color": "#000000",
	                        "fillAlphas": 1,
	                        "id": "AmGraph-3",
	                        "labelText": "[[value]]",
	                        "title": "SNS",
	                        "type": "column",
	                        "valueField": "column-3",
	                        "showAllValueLabels": true
	                    },
	                    {
	                        "balloonText": "",
	                        "color": "#000000",
	                        "fillAlphas": 1,
	                        "id": "AmGraph-4",
	                        "labelText": "[[value]]",
	                        "title": "기타",
	                        "type": "column",
	                        "valueField": "column-4",
	                        "showAllValueLabels": true
	                    }
	                ],
	                "guides": [],
	                "valueAxes": [
	                    {
	                        "id": "ValueAxis-1",
	                        "stackType": "regular",
	                        "synchronizationMultiplier": 0,
	                        "zeroGridAlpha": 0,
	                        "autoGridCount": false,
	                        "axisThickness": 0,
	                        "color": "#CCCCCC",
	                        "strictMinMax": true,
	                        "maximum": data.MAX_LENGTH,         // 가장 높은 이슈의 정보량 합
	                        "dashLength": 3,
	                        "tickLength": 0,
	                        "title": ""
	                    }
	                ],
	                "export": {
	            		"enabled": true					
					},
	                "allLabels": [],
	                "balloon": {},
	                "titles": [],	                  
					"dataProvider": data.LIST
			};
		
		var targetId = "hiddenIssueChart"+id.replace("IssueChart", "");
		console.log("targetId:"+targetId);
		
		
				
		$("#"+targetId).css("width", width);
		$("#"+targetId).css("height", height);
		$("#hiddenIssueChartBox").css("height", height);

		var chart = AmCharts.makeChart(targetId, chartOption);	
		chart_to_imgBinary(id, chart);
	}
	
	function getIssueChartType4(id, width, height, data){
		
		var chartOption = {
				 "type": "serial",
	                "categoryField": "category",
	                "columnWidth": 0.39,
	                "rotate": true,
	                "marginBottom": 10,
	                "marginLeft": 10,
	                "marginRight": 20,
	                "marginTop": 10,
	                "colors": [
                           "#ffd966",
                           "#f4b084",
                           "#ed7727",
                           "#a9d08e",
                           "#6fac46",
                           "#9bc2e6",
                           "#2f75b5",
                           "#9999ff",
                           "#e60000",
                           "#808080",
                           "#bfbfbf"
	                ],
	                "color": "#666666",
	                "categoryAxis": {
	                    "gridPosition": "start",
	                    "axisColor": "#CCCCCC",
	                    "gridCount": 0,
	                    "gridThickness": 0,
	                    "labelsEnabled": false,
	                    "tickLength": 0
	                },
	                "trendLines": [],
	                "graphs": [
	                    {
	                        "balloonText": "",
	                        "color": "#000000",
	                        "fillAlphas": 1,
	                        "id": "AmGraph-1",
	                        "labelText": "[[value]]",
	                        "title": "언론",
	                        "type": "column",
	                        "valueField": "column-1",
	                        "showAllValueLabels": true
	                    },
	                    {
	                        "balloonText": "",
	                        "color": "#000000",
	                        "fillAlphas": 1,
	                        "id": "AmGraph-2",
	                        "labelText": "[[value]]",
	                        "title": "블로그",
	                        "type": "column",
	                        "valueField": "column-2",
	                        "showAllValueLabels": true
	                    },
	                    {
	                        "balloonText": "",
	                        "color": "#000000",
	                        "fillAlphas": 1,
	                        "id": "AmGraph-3",
	                        "labelText": "[[value]]",
	                        "title": "카페",
	                        "type": "column",
	                        "valueField": "column-3",
	                        "showAllValueLabels": true
	                    },
	                    {
	                        "balloonText": "",
	                        "color": "#000000",
	                        "fillAlphas": 1,
	                        "id": "AmGraph-4",
	                        "labelText": "[[value]]",
	                        "title": "지식인",
	                        "type": "column",
	                        "valueField": "column-4",
	                        "showAllValueLabels": true
	                    },
	                    {
	                        "balloonText": "",
	                        "color": "#000000",
	                        "fillAlphas": 1,
	                        "id": "AmGraph-5",
	                        "labelText": "[[value]]",
	                        "title": "커뮤니티",
	                        "type": "column",
	                        "valueField": "column-5",
	                        "showAllValueLabels": true
	                    },
	                    {
	                        "balloonText": "",
	                        "color": "#000000",
	                        "fillAlphas": 1,
	                        "id": "AmGraph-6",
	                        "labelText": "[[value]]",
	                        "title": "트위터",
	                        "type": "column",
	                        "valueField": "column-6",
	                        "showAllValueLabels": true
	                    },
	                    {
	                        "balloonText": "",
	                        "color": "#000000",
	                        "fillAlphas": 1,
	                        "id": "AmGraph-7",
	                        "labelText": "[[value]]",
	                        "title": "페이스북",
	                        "type": "column",
	                        "valueField": "column-7",
	                        "showAllValueLabels": true
	                    },
	                    {
	                        "balloonText": "",
	                        "color": "#000000",
	                        "fillAlphas": 1,
	                        "id": "AmGraph-8",
	                        "labelText": "[[value]]",
	                        "title": "인스타",
	                        "type": "column",
	                        "valueField": "column-8",
	                        "showAllValueLabels": true
	                    },
	                    {
	                        "balloonText": "",
	                        "color": "#000000",
	                        "fillAlphas": 1,
	                        "id": "AmGraph-9",
	                        "labelText": "[[value]]",
	                        "title": "유튜브",
	                        "type": "column",
	                        "valueField": "column-9",
	                        "showAllValueLabels": true
	                    },
	                    {
	                        "balloonText": "",
	                        "color": "#000000",
	                        "fillAlphas": 1,
	                        "id": "AmGraph-10",
	                        "labelText": "[[value]]",
	                        "title": "해외사이트",
	                        "type": "column",
	                        "valueField": "column-10",
	                        "showAllValueLabels": true
	                    },
	                    {
	                        "balloonText": "",
	                        "color": "#000000",
	                        "fillAlphas": 1,
	                        "id": "AmGraph-11",
	                        "labelText": "[[value]]",
	                        "title": "기관",
	                        "type": "column",
	                        "valueField": "column-11",
	                        "showAllValueLabels": true
	                    }
	                ],
	                "guides": [],
	                "valueAxes": [
	                    {
	                        "id": "ValueAxis-1",
	                        "stackType": "100%",
	                        "synchronizationMultiplier": 0,
	                        "zeroGridAlpha": 0,
	                        "autoGridCount": false,
	                        "axisThickness": 0,
	                        "color": "#CCCCCC",
	                        "dashLength": 3,
	                        "gridCount": 10,
	                        "tickLength": 0,
	                        "title": ""
	                    }
	                ],
	                "export": {
	            		"enabled": true					
					},
	                "allLabels": [],
	                "balloon": {},
	                "titles": [],
	                "dataProvider": data
			};
		
		var targetId = "hiddenIssueChart"+id.replace("IssueChart", "");
		console.log("targetId:"+targetId);
		
		
				
		$("#"+targetId).css("width", width);
		$("#"+targetId).css("height", height);
		$("#hiddenIssueChartBox").css("height", height);

		var chart = AmCharts.makeChart(targetId, chartOption);	
		chart_to_imgBinary(id, chart);
	}
	
	function getChart1(id, width, height, data){
		
		var chartOption = {
				"type": "serial",
                "categoryField": "category",
                "marginTop": 30,
                "colors": [
                    "#ff7575"
                ],
                "color": "#666666",
                "categoryAxis": {
                    "gridPosition": "start",
                    //"fontSize": 8,
                    "axisThickness": 0,
                    //"color": "#808080",
                    "gridThickness": 0,
            		//"labelRotation": 45,
                    "tickLength": 0,
                    //"autoWrap": true
                },
                "trendLines": [],
                "graphs": [
                    {
                        "balloonText": "",
                        "fillAlphas": 1,    
                        "id": "AmGraph-1",
                        "labelText": "[[value]]",
                        "color": "#000000",
                        "title": "graph 1",
                        "type": "column",
                        "valueField": "column-1",
                        "fixedColumnWidth": 22,
                        "showAllValueLabels": true
                    }
                ],
                "guides": [],
                "valueAxes": [
                    {
                    	"id": "ValueAxis-1",
                    	"axisThickness": 0,
                        "gridThickness": 0,
                        "axisThickness": 0,                        
                        "labelsEnabled": false,
                        "tickLength": 0,
                        "title": "",
                        "strictMinMax": true,
                        "maximum": data.MAX_LENGTH         // 가장 높은 정보량
                    }
                ],
            	"export": {
            		"enabled": true					
				},
                "allLabels": [],
                "balloon": {},
                "titles": [],
				"dataProvider": data.LIST
			};
		
		var targetId = "hiddenChart"+id.replace("chart", "");
		console.log("targetId:"+targetId);
		
		
				
		$("#"+targetId).css("width", width);
		$("#"+targetId).css("height", height);
		$("#hiddenChartBox").css("height", height);

		var chart = AmCharts.makeChart(targetId, chartOption);	
		chart_to_imgBinary(id, chart);
	}
	
	function getChart2(id, width, height, data){
		
		var chartOption = {
				 "type": "serial",
                 "categoryField": "category",
                 "marginTop": 30,
                 "colors": [
                     "#5bb9ff"
                 ],
                 "color": "#666666",
                 "categoryAxis": {
                     "gridPosition": "start",
                     //"fontSize": 8,
                     "axisThickness": 0,
                     //"color": "#808080",
                     "gridThickness": 0,
             		 //"labelRotation": 45,
                     "tickLength": 0,
                     //"autoWrap": true
                 },
                 "trendLines": [],
                 "graphs": [
                     {
                         "balloonText": "",
                         "fillAlphas": 1,                         
                         "labelText": "[[value]]",
                         "color": "#000000",
                         "title": "graph 1",
                         "type": "column",
                         "valueField": "column-1",
                         "fixedColumnWidth": 22,
                         "showAllValueLabels": true
                     }
                 ],
                 "guides": [],
                 "valueAxes": [
                     {
                    	 "id": "ValueAxis-1",
                    	 "axisThickness": 0,
                         "gridThickness": 0,
                         "labelsEnabled": false,
                         "tickLength": 0,
                         "title": "",
                         "strictMinMax": true,
                         "maximum": data.MAX_LENGTH         // 가장 높은 정보량
                     }
                 ],
                 "export": {
 					"enabled": true
 				 },
                 "allLabels": [],
                 "balloon": {},
                 "titles": [],
                 "dataProvider": data.LIST
			};

		var targetId = "hiddenChart"+id.replace("chart", "");

				
		$("#"+targetId).css("width", width);
		$("#"+targetId).css("height", height);
		$("#hiddenChartBox").css("height", height);

		var chart = AmCharts.makeChart(targetId, chartOption);	
		chart_to_imgBinary(id, chart);
	}
	
	function getDailyChart1(id, width, height, data){

		var chartOption = {
				"type": "pie",
				"balloonText": "<strong>[[category]]</strong> : [[value]]건 ([[percents]]%)",
				"innerRadius": "45%",
				"labelRadius": -35,
				"labelText": "[[value]] ([[percents]]%)",
				// "labelText": "[[category]]<br>[[value]]건<br>([[percents]]%)",
				"pullOutRadius": "100%",
				"radius": 135,
				"startAngle": -155,
				"startRadius": "0%",
				"colors": [
					"#385593",		// 언론
					"#555555",		// 포탈
					"#7ac35c",		// 블로그
					"#f2d301",		// 카페
					"#ec8e56",		// 커뮤니티
					"#75cae5",		// 트위터
					"#458eed",		// 페이스북
					"#e57fb6",		// 인스타그램
					"#ce553f",		// 유튜브
					"#cccdcd",		// 기타
				],
				"fontSize": 12,
				// "hideLabelsPercent": 0,
				"marginBottom": 10,
				"marginLeft": 0,
				"marginRight": 0,
				"marginTop": 20,
				"maxLabelWidth": 100,
				"outlineThickness": 3,
				"pullOutDuration": 0,
				"startDuration": 0,
				"titleField": "category",
				"valueField": "column-1",
				"classNameField": "class",
				"accessible": false,
				"addClassNames": true,
				"autoResize": false,
				"color": "#ffffff",
				"percentPrecision": 1,
				"allLabels": [],
				"balloon": {},						
				"legend": {
					"enabled": true,
					"align": "center",
					"autoMargins": true,
					"color": "#999999",
					"marginLeft": 0,
					"marginRight": 0,
					"marginTop": 10,
					"markerLabelGap": 5,
					"markerSize": 8,
					"markerType": "circle",
					"periodValueText": "",
					"spacing": 20,
					"valueText": "",
					"equalWidths": false,
					"fontSize": 11,
					// "valueWidth": 0,
					"verticalGap": 10
				},
                "export": {
 					"enabled": true
 				 },
				"titles": [],
				"dataProvider": data.LIST
			};
		
		var targetId = "hiddenChart"+id.replace("chart", "");
		console.log("targetId:"+targetId);
				
		$("#"+targetId).css("width", width);
		$("#"+targetId).css("height", height);
		$("#hiddenChartBox").css("height", height);
		var chart = AmCharts.makeChart(targetId, chartOption);	
		chart_to_imgBinary(id, chart);
	}
	
	function getDailyChart2(id, width, height, data){
		
		var chartOption = {
            "type": "serial",
            "categoryField": "category",
            "columnWidth": 0.39,
            "marginBottom": 0,
            "marginLeft": 0,
            "marginRight": 0,
            "marginTop": -45,
			// "autoMargins": false,
            "colors": [
                "#5ba1e0",
                "#ea7070",
                "#808080"
            ],
			"fontSize": 12,
            "color": "#666666",
            "categoryAxis": {
                "gridPosition": "start",
                "axisColor": "#ececec",
                "gridCount": 0,
                "gridThickness": 0,
                "tickLength": 0
            },
            "trendLines": [],
            "graphs": [
                {
                    "balloonText": "",
                    "color": "#000000",
                    "fillAlphas": 1,
                    "id": "AmGraph-1",
                    "labelText": "[[value]]",
                    "title": "긍정",
                    "type": "column",
                    "valueField": "column-1",
                    "showAllValueLabels": true
                },
                {
                    "balloonText": "",
                    "color": "#000000",
                    "fillAlphas": 1,
                    "id": "AmGraph-2",
                    "labelText": "[[value]]",
                    "title": "부정",
                    "type": "column",
                    "valueField": "column-2",
                    "showAllValueLabels": true
                },
                {
                    "balloonText": "",
                    "color": "#000000",
                    "fillAlphas": 1,
                    "id": "AmGraph-3",
                    "labelText": "[[value]]",
                    "title": "중립",
                    "type": "column",
                    "valueField": "column-3",
                    "showAllValueLabels": true
                }
            ],
            "guides": [],
            "valueAxes": [
                {
                    "id": "ValueAxis-1",
                    "stackType": "regular",
                    "synchronizationMultiplier": 0,
                    "zeroGridAlpha": 0,
                    "autoGridCount": false,
                    "axisThickness": 0,
                    "color": "#999999",
                	"axisColor": "#ececec",
					"fontSize": 10,
                    "strictMinMax": true,
                    "maximum": 26,         // 가장 높은 이슈의 정보량 합
                    "dashLength": 3,
                    "tickLength": 0,
                    "title": ""
                }
            ],
			"legend": {
				"enabled": true,
				"align": "center",
				"autoMargins": false,
				"color": "#999999",
				"marginLeft": 0,
				"marginRight": 0,
				"marginTop": -10,
				"markerLabelGap": 5,
				"markerSize": 8,
				"markerType": "circle",
				"periodValueText": "",
				"spacing": 12,
				"valueText": "",
				"fontSize": 11,
				// "valueWidth": 0,
				"verticalGap": 10
			},
            "export": {
					"enabled": true
				 },
            "allLabels": [],
            "balloon": {},
            "titles": [],
            "dataProvider": data.LIST
		};
		
		var targetId = "hiddenChart"+id.replace("chart", "");
				
		$("#"+targetId).css("width", width);
		$("#"+targetId).css("height", height);
		$("#hiddenChartBox").css("height", height);

		var chart = AmCharts.makeChart(targetId, chartOption);	
		chart_to_imgBinary(id, chart);
	}
	
	function chart_to_imgBinary(id, chart){
		setTimeout( tmp, 1000);
		function tmp(){
			if ( window.fabric ) {
				chart["export"].capture( {}, function(){
					this.toPNG( {}, function( base64 ) {
						saveChartImg(id, base64);	
						
					} );
				});
			}else{
				setTimeout( tmp, 1000);						
			}
		}
	}
	
	
	function saveChartImg(id, chartBinary){
		
		$("#chartType").val(id);
		$("#chartBinary").val(chartBinary);
		var param = $("#fSend").serialize();
		$.ajax({
			type : "POST"
			,async : true
			,url: "aj_saveChartData.jsp"
			,timeout: 30000
			,data : param
			,dataType : 'text'
			,async: true
			,success : function(data){
				$("#"+id).val(data.trim());
			}
		});	
	}
	/*
	function settingReplyCount(){
		
		var replyId = '';
		var replyCount = '';
		var idx = 0;
		
		$('input[name=TMReplyCount]').each(function(){
			
			if($('#TMCheck'+idx).attr('checked')){
				if(replyId == '') replyId = $(this).attr('id'); 
				else replyId += ',' + $(this).attr('id');
				
				var this_val = $(this).val();
				if(this_val == '') this_val = '0';
				if(replyCount == '') replyCount = this_val; 
				else replyCount += ',' + this_val;
			}
			idx++;
		});
		
		idx = 0;
		$('input[name=GroupReplyCount]').each(function(){
			
			if($('#groupCheck'+idx).attr('checked')){
				if(replyId == '') replyId = $(this).attr('id'); 
				else replyId += ',' + $(this).attr('id');
				
				var this_val = $(this).val();
				if(this_val == '') this_val = '0';
				if(replyCount == '') replyCount = this_val; 
				else replyCount += ',' + this_val;
			}
			idx++;
		});	
		
		idx = 0;
		$('input[name=RelReplyCount]').each(function(){
			
			if($('#relCheck'+idx).attr('checked')){
				if(replyId == '') replyId = $(this).attr('id'); 
				else replyId += ',' + $(this).attr('id');
				
				var this_val = $(this).val();
				if(this_val == '') this_val = '0';
				if(replyCount == '') replyCount = this_val; 
				else replyCount += ',' + this_val;
			}
			idx++;
		});

		$('input[name=reply_id_seqs]').val(replyId);
		$('input[name=reply_count]').val(replyCount);
	}	
	*/
	function numbersonly(obj, e, decimal){
		
	    var key;
	    var keychar;

	    if (window.event) {
	        key = window.event.keyCode;
	    } else if (e) {
	        key = e.which;
	    } else {
	        return true;
	    }
	    keychar = String.fromCharCode(key);

	    if ((key == null) || (key == 0) || (key == 8) || (key == 9) || (key == 13)
	            || (key == 27)) {
	        return true;
	    } else if ((("0123456789").indexOf(keychar) > -1)) {
	        return true;
	    } else if (decimal && (keychar == ".")) {
	        return true;
	    } else
	        return false;	
	}

	function checkInputKey(obj){
		if (/[^0-9]/g.test(obj.value))
	    {	    	
	        obj.value='';
	        obj.focus();
	        alert('숫자만 입력 가능합니다.');
	        return false;
	    }else{
	        return true;
	    }
	}
</script>
</head>
<body style="margin-left: 15px">
<form name="fSend" id="fSend" method="post">

<input name="ir_seq" type="hidden" value="">
<input name="mode" type="hidden" value="">
<input name="reportType" type="hidden">
<input id="tm_id_seqs" name="tm_id_seqs" type="hidden" value="">
<!-- <input id="group_id_seqs" name="group_id_seqs" type="hidden" value=""> -->
<!-- <input id="rel_id_seqs" name="rel_id_seqs" type="hidden" value=""> -->
<!-- <input id="major_ic_code" name="major_ic_code" type="hidden" value=""> -->
<!-- <input name="reply_id_seqs" type="hidden" value=""> -->
<!-- <input name="reply_count" type="hidden" value=""> -->

<!-- Voc Trend Report --> 
<input id="category_sortBy" name="category_sortBy" type="hidden" value="1">
<input id="detail_subject" name="detail_subject" type="hidden" value="1">
<input id="detail_sortBy" name="detail_sortBy" type="hidden" value="1">
<input id="realtionkey_sortBy" name="realtionkey_sortBy" type="hidden" value="1">

<!-- 식품업계 Issue Trend Report --> 
<input id="section_2_category" name="section_2_category" type="hidden" value="">

<!-- 식품업계 Issue Trend Report --> 
<input type="hidden" name="chkIdxs" id="chkIdxs" value="">

<!-- 차트 -->
<input name="chartType"  id="chartType"  value="" type="hidden" />
<input name="chartWidth"  id="chartWidth"  value="" type="hidden" />
<input name="chartHeight" id="chartHeight" value="" type="hidden" />
<input name="chartBinary"  id="chartBinary"  value="" type="hidden" />

<input id="chart1" name="chart1" type="hidden" value="">
<input id="chart2" name="chart2" type="hidden" value="">

<input id="IssueChart1_1" name="IssueChart1_1" type="hidden" value="">
<input id="IssueChart1_2" name="IssueChart1_2" type="hidden" value="">
<input id="IssueChart1_3" name="IssueChart1_3" type="hidden" value="">
<input id="IssueChart2_1" name="IssueChart2_1" type="hidden" value="">
<input id="IssueChart2_2" name="IssueChart2_2" type="hidden" value="">
<input id="IssueChart2_3" name="IssueChart2_3" type="hidden" value="">
<input id="IssueChart3" name="IssueChart3" type="hidden" value="">
<input id="IssueChart4" name="IssueChart4" type="hidden" value="">

<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../images/report/tit_icon.gif" /><img src="../../images/report/tit_0301.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">보고서관리</td>
								<td class="navi_arroW">보고서작성</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="15"></td>
			</tr>
			<!-- 타이틀 끝 -->
			<!-- 검색 시작 -->
			<tr>
				<td style="height:30px;"><span class="sub_tit">보고서 설정</span></td>
			</tr>
			
			<tr>
				    <td height="1" bgcolor="#D3D3D3"></td>
				  </tr>
			<tr>
				    <td><table width="820" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="1" bgcolor="#D3D3D3"></td>
				        <td width="818" bgcolor="#F7F7F7" style="padding:10px 0px 5px 0px"><table width="798" border="0" align="center" cellpadding="0" cellspacing="0">
				          <tr>
				            <td height="24" style="padding:0px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="110" class="b_text"><img src="../../images/report/icon_search_bullet.gif" width="9" height="9" /> <strong>보고서 유형</strong></td>
				                <td ><table style="color:#2f5065;" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td style="padding-right:10px;">
										<%-- <input type="radio" name="ir_type" value="D" onclick="changeType('D');" <%if(ir_type.equals("D")){out.println("checked");} %>>일일 보고서 --%>
										<select name="ir_type" id="ir_type" name="ir_type" class="textbox3" style="width: 205px" onchange="issueListload();">
				                 			<option value="D2" <%if("D".equals(ir_type)){out.print("selected");} %>>일일보고서</option>
				                 			<!-- <option value="MI">온라인/SNS 동향 보고 (주요이슈)</option>
				                 			<option value="D">온라인/SNS 동향 보고 (일반)</option>
				                 			<option value="ID" >온라인/SNS 동향 보고 (종합)</option> -->
				                  		</select>
										</td>
									</tr>
								</table></td>
				              </tr>
				            </table></td>
				          </tr>
				          <tr>
				            <td><img src="../../images/report/dotline.gif" width="798" height="3" /></td>
				          </tr>
				          <tr>
				            <td style="padding:5px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
				              <tr>			              
				                <td width="110" class="b_text"><img src="../../images/report/icon_search_bullet.gif" width="9" height="9" /> <strong>보고서 제목</strong></td>
				                <td ><input style="width:460px;" class="textbox" type="text" name="ir_title" value="<%=ir_title%>"></td>			                
				                
				              </tr>
				            </table></td>
				          </tr>
				          <tr>
				            <td><img src="../../images/report/dotline.gif" width="798" height="3" /></td>
				          </tr>
				          <tr>
				            <td style="padding:5px 0px 5px 7px"><table width="790" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="110" class="b_text"><img src="../../images/report/icon_search_bullet.gif" width="9" height="9" /> <strong>기  간</strong></td>
				                <td><table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td><input style="width:90px;" class="textbox" type="text" name="ir_sdate" id="ir_sdate" value="<%=eDate%>"></td>
											<td><img src="../../images/report/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('ir_sdate'))"/></td>
											<td>
											<select name="ir_stime" onchange="issueListload();"><%for(int i=0; i<24; i++){ if(i==17){out.print("<option value="+i+" selected>"+i+"시</option>");}else{out.print("<option value="+i+">"+i+"시</option>");}} %></select>
											</td>
											<td>~</td>
											<td><input style="width:90px;" class="textbox" type="text" name="ir_edate" id="ir_edate" value="<%=eDate%>"></td>
											<td><img src="../../images/report/btn_calendar.gif" style="cursor:pointer" onclick="calendar_sh(document.getElementById('ir_edate'))"/></td>
											<td>
											<select name="ir_etime" onchange="issueListload();"><%for(int i=0; i<24; i++){ if(i==17){out.print("<option value="+i+" selected>"+i+"시</option>");}else{out.print("<option value="+i+">"+i+"시</option>");}} %></select>
											</td>
											<td style="padding-left:10px;">※ 수집시간 기준으로 작성됩니다.</td>
										</tr>
									</table></td>			                
				             
				              </tr>
				            </table></td>
				          </tr>
				        </table></td>
				        <td width="1" bgcolor="AEC6CE"></td>
				      </tr>
				    </table></td>
				  </tr>
				  <tr>
				    <td height="1" bgcolor="AEC6CE"></td>
				  </tr>
			<!-- 검색 끝 -->

			<!-- 게시판 시작 -->
			
			<!-- 자사 언론 정보 -->
			<tr>
				<td id="OwnPressList"></td>
			</tr>
			<!-- 자사 언론 정보 끝 -->
			
			<!-- 자사 소셜 정보 -->
			<tr>
				<td id="OwnSocialList"></td>
			</tr>
			<!-- 자사 소셜 정보  끝-->
			
			<!-- 업계 언론 정보 -->
			<tr>
				<td id="RivalPressList"></td>
			</tr>
			<!-- 업계 언론 정보  끝-->
			
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;" align="center">
					<!-- <img src="../../images/report/btn_report_write.gif" onclick="preview(1);" style="cursor:pointer;"/> -->
					<img src="../../images/report/btn_report_write.gif" onclick="preview(2);" style="cursor:pointer; margin-top: 10px; margin-bottom: 10px;"/>
					<!-- <img id="img_im" src="../../images/report/btn_report_write_01.gif" onclick="preview_infor();" style="cursor:pointer;display: none;"/> -->
					<div id="message" style="width:100%;display: none;" align="center">
						<strong>보고서에 사용할 차트를 그리고 있습니다.<br>잠시만 기다려주세요.</strong>
					</div>
					<div id="hiddenChartBox" style="position:relative;width: 820px;" align="center">
						<div id="hiddenChart1" class="hiddenChart" align="center" style="position:absolute;top:0;left:0;opacity:0"></div>
						<div id="hiddenChart2" class="hiddenChart" align="center" style="position:absolute;top:0;left:0;opacity:0"></div>
					</div>
					<div id="hiddenIssueChartBox" style="position:relative;width: 820px;" align="center">
						<div id="hiddenIssueChart1_1" class="hiddenIssueChart" align="center" style="position:absolute;top:0;left:0;opacity:0"></div>
						<div id="hiddenIssueChart1_2" class="hiddenIssueChart" align="center" style="position:absolute;top:0;left:0;opacity:0"></div>
						<div id="hiddenIssueChart1_3" class="hiddenIssueChart" align="center" style="position:absolute;top:0;left:0;opacity:0"></div>
						<div id="hiddenIssueChart2_1" class="hiddenIssueChart" align="center" style="position:absolute;top:0;left:0;opacity:0"></div>
						<div id="hiddenIssueChart2_2" class="hiddenIssueChart" align="center" style="position:absolute;top:0;left:0;opacity:0"></div>
						<div id="hiddenIssueChart2_3" class="hiddenIssueChart" align="center" style="position:absolute;top:0;left:0;opacity:0"></div>
						<div id="hiddenIssueChart3" class="hiddenIssueChart" align="center" style="position:absolute;top:0;left:0;opacity:0"></div>
						<div id="hiddenIssueChart4" class="hiddenIssueChart" align="center" style="position:absolute;top:0;left:0;opacity:0"></div>
					</div>
				</td>				
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
<%-- 달력 테이블 --%>
<table width="162" border="0" cellspacing="0" cellpadding="0" id="calendar_conclass" style="position:absolute;display:none;">
	<tr>
		<td><img src="../../images/calendar/menu_bg_004.gif" width="162" height="2"></td>
	</tr>
	<tr>
		<td align="center" background="../../images/calendar/menu_bg_005.gif"><table width="148" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="6"></td>
			</tr>                                		
			<tr>
				<td id="calendar_calclass">
				</td>
			</tr>
			<tr>
				<td height="5"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td><img src="../../images/calendar/menu_bg_006.gif" width="162" height="2"></td>
	</tr>
</table>
<iframe name="prc" id="prc" width="0" height="0" style="display: none;"></iframe>
</body>
</html>