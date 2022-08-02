<%@ page contentType="text/html; charset=EUC-KR" %>
<%@include file="../inc/sessioncheck.jsp" %>
<%@ page import="risk.util.Log,
                 risk.util.StringUtil,
                 risk.util.ParseRequest,
                 risk.search.userEnvMgr,
                 risk.search.userEnvInfo,
                 risk.search.GetKGMenu
                 "
%>
    
<%
	ParseRequest pr = new ParseRequest(request);
	GetKGMenu kg = new GetKGMenu();

    //사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.
    userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");

    String sInit = (String)session.getAttribute("INIT");

    //Top메뉴에서 로고나 정보검색 메뉴 클릭시에는 검색조건을 초기화 시킨다.
    if ( sInit != null  && sInit.equals("INIT") ) {
        userEnvMgr uem = new userEnvMgr();
        uei = uem.getUserEnv( SS_M_ID );
        session.removeAttribute("INIT");
    }

    if ( uei.getSearchMode().equals("") ) {
        uei.setSearchMode("ALLKEY");
    }

    //지금까지 설정 내역을 세션에 저장
    session.removeAttribute("ENV");
    session.setAttribute("ENV",uei);

%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../css/basic.css" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--

	var selectedObj = "";

    function changeAction(value, obj)
    {
    	selectedObj = obj;
    	
    	var f = document.fSend;
    	f.target= 'contentsFrame';
    	
    	if(value=='issue'){			
    		chageColor(value);
    		f.action= 'issue_manager.jsp';    		
    		f.submit();
        }else if(value=='issueData' ){
        	chageColor(value);
        	f.action= 'issue_data_list.jsp';    		
    		f.submit();
        }else if(value=='report'){
        	//chageColor(value);
        	//f.action= 'issue_report_list.jsp?ir_type=E';    		
    		//f.submit();
		}else if(value=='statistics'){
			chageColor(value);
        	f.action= '../statistics/statistics_analysis.jsp';    		
    		f.submit();
		}else if(value=='issueHandle' ){
        	chageColor(value);
        	f.action= 'issueHandle.jsp';    		
    		f.submit();
        }
	}
/*
	function subChangeAction(contents,value)
	{
		if(contents=='issue')
		{

			document.all.issue.background = '../../images/left/top_left_offbg.gif';
			document.all.issueData.background = '../../images/left/top_left_offbg.gif';
			document.all.report.background = '../../images/left/top_left_onbg.gif';

			document.all.issueText.style.color='#000000';     
			document.all.issueDataText.style.color='#000000';     
			document.all.reportText.style.color='#ffffff';	

			selectedObj = document.all.report;
						
			//document.all.statistics.style.backgroundColor = '6F7373';				

			var f = document.fSend;
			f.target= 'contentsFrame';		
			f.action= 'issue_report_list.jsp?ir_type='+value;    		
			f.submit();
		}else if(contents=='statistics'){
			if(value=='A')
			{
				alert('변경 중 입니다.');
				
				//var f = document.fSend;
				//f.target= 'contentsFrame';		
				//f.action= '../statistics/statistics_issue.jsp';  		
				//f.submit();
				
			}else if(value=='B'){
				var f = document.fSend;
				f.target= 'contentsFrame';		
				f.action= '../statistics/statistics_analysis.jsp';    		
				f.submit();		
			}else if(value=='C'){							
				var f = document.fSend;
				f.target= 'contentsFrame';		
				f.action= '../statistics/statistics_collect_keyword.jsp';    		
				f.submit();				
			}else if(value=='D'){				
				var f = document.fSend;
				f.target= 'contentsFrame';		
				f.action= '../statistics/statistics_collect_site.jsp';    		
				f.submit();
			}
		}
	}
*/
	function chageColor( value ) {
		var f = document.fSend;


		//../../images/left/top_left_onbg.gif
		
	    if ( value == 'issue' ){
			//document.all.issue.background = '../../images/left/top_left_onbg.gif';
			document.all.issueData.background = '../../images/left/top_left_offbg.gif';
			//document.all.issueHandle.background = '../../images/left/top_left_offbg.gif';				
			//document.all.report.background = '../../images/left/top_left_offbg.gif';
			//document.all.statistics.style.backgroundColor = '6F7373';
			//document.all.issueText.style.color='#ffffff';     
			document.all.issueDataText.style.color='#000000';     
			//document.all.reportText.style.color='#000000';	
			//document.all.statisticsText.style.color='#000000';
			//document.all.issueHandleText.style.color='#000000';	
					
	    }else if( value == 'issueData' ){
	    	document.all.issueData.background = '../../images/left/top_left_onbg.gif';
	    	document.all.issue.background = '../../images/left/top_left_offbg.gif';
			document.all.issueHandle.background = '../../images/left/top_left_offbg.gif';				
	    	//document.all.report.background = '../../images/left/top_left_offbg.gif';	
			//document.all.statistics.style.backgroundColor = '6F7373';			
			document.all.issueDataText.style.color='#ffffff';
			document.all.issueText.style.color='#000000';          
			//document.all.reportText.style.color='#000000';
			//document.all.statisticsText.style.color='#000000';
			document.all.issueHandleText.style.color='#000000';	
							
	    }else if( value == 'report' ){
		    /*
			document.all.report.background = '../../images/left/top_left_onbg.gif';
			document.all.issue.background = '../../images/left/top_left_offbg.gif';
			document.all.issueData.background =	'../../images/left/top_left_offbg.gif';
			//document.all.statistics.style.backgroundColor = '6F7373';		
			document.all.reportText.style.color='#ffffff';
			document.all.issueText.style.color='#000000';
			document.all.issueDataText.style.color='#000000';
			//document.all.statisticsText.style.color='#000000';	
			*/			
		}else if(value == 'statistics'){
			/*
			document.all.statistics.background = '../../images/left/top_left_onbg.gif';	
			//document.all.report.background = '../../images/left/top_left_offbg.gif';
			document.all.issue.background = '../../images/left/top_left_offbg.gif';
			document.all.issueData.background =	'../../images/left/top_left_offbg.gif';		
			document.all.issueHandle.background = '../../images/left/top_left_offbg.gif';				
			document.all.statisticsText.style.color='#ffffff';
			//document.all.reportText.style.color='#000000';
			document.all.issueText.style.color='#000000';
			document.all.issueDataText.style.color='#000000';
			document.all.issueHandleText.style.color='#000000';	
			*/
		}
		else if(value == 'issueHandle'){
			document.all.issueHandle.background = '../../images/left/top_left_onbg.gif';	
			//document.all.report.background = '../../images/left/top_left_offbg.gif';
			document.all.issue.background = '../../images/left/top_left_offbg.gif';
			document.all.issueData.background =	'../../images/left/top_left_offbg.gif';		
			//document.all.statisticsText.style.color='#000000';
			//document.all.reportText.style.color='#000000';
			document.all.issueText.style.color='#000000';
			document.all.issueDataText.style.color='#000000';
			document.all.issueHandleText.style.color='#ffffff';
	}	
}
	
	function saveEnv()
	{
		if( parent.contentsFrame.fCheck )
		{
			parent.contentsFrame.env_save();
		}
	}

	/*
	function goReportList(type)
	{
		var f = document.fSend;		
		chageColor('report');
		f.action = 'issue_report_list.jsp';
		f.target = 'contentsFrame';
		f.submit();
	
	}
	*/

	function onMs(obj){
		obj.background='../../images/left/top_left_onbg.gif';
	}
	function outMs(obj){
		obj.background='../../images/left/top_left_offbg.gif';
		selectedObj.background='../../images/left/top_left_onbg.gif';
	}
	
/*
	function igList_resize(){
  		var ch = document.body.clientHeight;
  		var ifmObj = document.all.ifr_menu.style;
		var newH = ch - 230;
  		if (newH <100) newH = 100;
  		ifmObj.height = newH;
  	}
*/
//-->
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="fSend" action="" method="post">
<input type="hidden" name="type" id="type" value="">
</form>
<table width="300" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
<!--    <td align="right" valign="top" background="../../images/left/top_left_mbg.gif">-->
    <td align="right" valign="top">
      <table width="295" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="../../images/left/left_title02.gif"></td>
	      </tr>
	      <tr>
	        <td bgcolor="#FFFFFF"><img src="../../images/left/brank.gif" width="1" height="4"></td>
	      </tr>
      </table>	  
      
      <!-- 
	  <table width="205" height="34" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="right" style="padding: 0px 5px 0px 0px;" id="issue" background="../../images/left/top_left_offbg.gif"  onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="190" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico01.gif" width="16" height="15"></td>
              <td class="leftM_on" style="padding: 2px 0px 0px 3px;"><strong><a  id="issueText" style="color:000000;" href="javascript:changeAction('issue', document.all.issue);">이슈관리</a></strong></td>
            </tr>
          </table></td>
        </tr>
      </table>
       
      <table width="205" border="0" cellpadding="0" cellspacing="0">
        <tr>
        	<td height="1" bgcolor="B7B7B7"></td>
        </tr>
      </table>
      -->
      <table width="295" height="34" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="right" style="padding: 0px 5px 0px 0px;" id="issueData" background="../../images/left/top_left_onbg.gif"  onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="280" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico01.gif" width="16" height="15"></td>
              <td class="leftM_on" style="padding: 2px 0px 0px 3px;"><strong><a  id="issueDataText" style="color:ffffff;" href="javascript:changeAction('issueData', document.all.issueData);">관련정보</a></strong></td>
            </tr>
          </table></td>
        </tr>
      </table>
      
      <table width="295" border="0" cellpadding="0" cellspacing="0">
        <tr>
        	<td height="1" bgcolor="B7B7B7"></td>
        </tr>
      </table>
      <!--
      <table width="205" height="34" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="right" style="padding: 0px 5px 0px 0px;" id="issueHandle" background="../../images/left/top_left_offbg.gif"  onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="190" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20"><img src="../../images/left/left_mico01.gif" width="16" height="15"></td>
              <td class="leftM_on" style="padding: 2px 0px 0px 3px;"><strong><a  id="issueHandleText" style="color:000000;" href="javascript:changeAction('issueHandle', document.all.issueHandle);">이슈처리</a></strong></td>
            </tr>
          </table></td>
        </tr>
      </table>
        -->
<!--      <table width="205" border="0" cellpadding="0" cellspacing="0">-->
<!--        <tr>-->
<!--        	<td height="1" bgcolor="B7B7B7"></td>-->
<!--        </tr>-->
<!--      </table>-->
<!--      -->
<!--	  <table width="205" height="34" border="0" cellpadding="0" cellspacing="0">-->
<!--        <tr>-->
<!--          <td align="right" id="report" class="leftM_on" style="padding: 0px 5px 0px 0px;" background="../../images/left/top_left_offbg.gif"  onMouseover="onMs(this);" onmouseout="outMs(this);"><table width="190" border="0" cellspacing="0" cellpadding="0">-->
<!--              <tr>-->
<!--                <td width="20"><img src="../../images/left/left_mico02.gif" width="16" height="15"></td>-->
<!--                <td class="leftM_off" style="padding: 2px 0px 0px 3px; font-weight: bold;"><a  id="reportText" style="color:000000;" href="javascript:changeAction('report', document.all.report);">보고서 관리</a></td>-->
<!--              </tr>-->
<!--          </table></td>-->
<!--        </tr>-->
<!--      </table>      		        -->
<!--	  <table width="205" border="0" cellspacing="0" cellpadding="0">-->
<!--        <tr>-->
<!--          <td width="28" height="30" align="right"><img src="../../images/left/left_mico04.gif" width="14" height="11"></td>-->
<!--          <td width="177" style="padding: 3px 0px 0px 5px;"><a href="javascript:subChangeAction('issue','E');"><strong>긴급보고서</strong></a></td>-->
<!--        </tr>-->
<!--        <tr>-->
<!--          <td colspan="2" background="../../images/left/left_menu_linebg.gif"><img src="../../images/left/brank.gif" width="1" height="1"></td>-->
<!--        </tr>-->
<!--		<tr>-->
<!--          <td width="28" height="30" align="right"><img src="../../images/left/left_mico04.gif" width="14" height="11"></td>-->
<!--          <td width="177" style="padding: 3px 0px 0px 5px;"><a href="javascript:subChangeAction('issue','D');"><strong>일일보고서</strong></a></td>-->
<!--        </tr>-->
<!--        <tr>-->
<!--          <td colspan="2" background="../../images/left/left_menu_linebg.gif"><img src="../../images/left/brank.gif" width="1" height="1"></td>-->
<!--        </tr>-->
<!--        <tr>-->
<!--          <td width="28" height="30" align="right"><img src="../../images/left/left_mico04.gif" width="14" height="11"></td>-->
<!--          <td width="177" style="padding: 3px 0px 0px 5px;"><a href="javascript:subChangeAction('issue','P');"><strong>기간보고서</strong></a></td>-->
<!--        </tr>-->
<!--        <tr>-->
<!--          <td colspan="2" background="../../images/left/left_menu_linebg.gif"><img src="../../images/left/brank.gif" width="1" height="1"></td>-->
<!--        </tr>-->
<!--		<tr>-->
<!--          <td width="28" height="30" align="right"><img src="../../images/left/left_mico05.gif" width="14" height="14"></td>-->
<!--          <td width="177" style="padding: 3px 0px 0px 5px;"><a onclick="javascript:return;" href="issue_report_creater.jsp" target="contentsFrame"><strong>보고서 작성</strong></a></td>-->
<!--        </tr>-->
<!--        <tr>-->
<!--          <td colspan="2" ><img src="../../images/left/brank.gif" width="1" height="1"></td>-->
<!--        </tr>-->
<!--      </table>-->


      <!-- table width="205" height="34" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="right" id="statistics" background="../../images/left/top_left_offbg.gif" class="leftM_on" style="padding: 0px 5px 0px 0px;"><table width="190" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../images/left/left_mico02.gif" width="16" height="15"></td>
                <td class="leftM_off" style="padding: 2px 0px 0px 3px; font-weight: bold;"><a  id="statisticsText" style="color:000000;" href="javascript:changeAction('statistics');">통계 분석</a></td>
              </tr>
          </table></td>
        </tr>
      </table>      		        
	  <table width="205" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="28" height="30" align="right"><img src="../../images/left/left_mico04.gif" width="14" height="11"></td>
          <td width="177" style="padding: 3px 0px 0px 5px;"><a href="javascript:subChangeAction('statistics','A');"><strong>이슈통계</strong></a></td>
        </tr>
        <tr>
          <td colspan="2" background="../../images/left/left_menu_linebg.gif"><img src="../../images/left/brank.gif" width="1" height="1"></td>
        </tr>
		<tr>
          <td width="28" height="30" align="right"><img src="../../images/left/left_mico04.gif" width="14" height="11"></td>
          <td width="177" style="padding: 3px 0px 0px 5px;"><a href="javascript:subChangeAction('statistics','B');"><strong>분류정보통계</strong></a></td>
        </tr>
        <tr>
          <td colspan="2" background="../../images/left/left_menu_linebg.gif"><img src="../../images/left/brank.gif" width="1" height="1"></td>
        </tr>
        <tr>
          <td width="28" height="30" align="right"><img src="../../images/left/left_mico04.gif" width="14" height="11"></td>
          <td width="177" style="padding: 3px 0px 0px 5px;"><a href="javascript:subChangeAction('statistics','C');"><strong>키워드통계</strong></a></td>
        </tr>       
        <tr>
          <td colspan="2" background="../../images/left/left_menu_linebg.gif"><img src="../../images/left/brank.gif" width="1" height="1"></td>
        </tr>
        <tr>
          <td width="28" height="30" align="right"><img src="../../images/left/left_mico04.gif" width="14" height="11"></td>
          <td width="177" style="padding: 3px 0px 0px 5px;"><a href="javascript:subChangeAction('statistics','D');"><strong>수집사이트통계</strong></a></td>
        </tr>       
        <tr>
          <td colspan="2" ><img src="../../images/left/brank.gif" width="1" height="1"></td>
        </tr>
      </table-->
      </td>
  </tr>
</table>

</body>
</html>
<script>
if (selectedObj==""){
	selectedObj=document.all.issueData;
	selectedObj.background='../../images/left/top_left_onbg.gif';
}
</script>
