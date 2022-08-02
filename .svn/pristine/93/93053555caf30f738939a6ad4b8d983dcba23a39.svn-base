<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList
				,risk.util.ParseRequest
				,risk.util.DateUtil
				,risk.util.StringUtil
				,java.net.URLDecoder
				,risk.issue.IssueCodeMgr
				,risk.issue.IssueExcel
				"
%>
<%@include file="../inc/sessioncheck.jsp"%>

<%
		ParseRequest pr = new ParseRequest(request);	
		pr.printParams();
		
		ConfigUtil cu = new ConfigUtil(); 
		DateUtil du = new DateUtil();
		IssueCodeMgr icMgr = new IssueCodeMgr();
		String searchKey = URLDecoder.decode(pr.getString("encodingSearchKey",""),"UTF-8");
		String keyType = pr.getString("keyType");
		String i_seq = pr.getString("i_seq","");
		String it_seq = pr.getString("it_seq","");
		String sDateFrom = pr.getString("sDateFrom");
		String sDateTo = pr.getString("sDateTo");
		
		String typeCode = pr.getString("typeCode");
		String typeCode_Etc = pr.getString("typeCode_Etc");
		String relationKey = pr.getString("relKeyword");
		String register = pr.getString("register");
		
		String m_seq = pr.getString("m_seq",SS_M_NO);
		String check_no = pr.getString("check_no","");
		String language = pr.getString("language","");
		
		String ir_stime = pr.getString("ir_stime");
		String ir_etime = pr.getString("ir_etime");
		
		String excel_type = pr.getString("excel_type");
		
		String diffCnt = Long.toString(du.DateDiff("yyyy-MM-dd",sDateTo, sDateFrom) + 1 );
		//ArrayList arData = new IssueExcel().getTypeName();
		//System.out.println(arData);
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=SS_TITLE%></title>

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.input_text02 {height:200px; LINE-HEIGHT: 20px; padding-left:5px;padding-right:5px; padding-top:5px;border-top: 1px #CFCFCF solid; border-left: 1px #CFCFCF solid; border-right: 1px #CFCFCF solid; border-bottom: 1px #CFCFCF solid;      background:#FFFFFF; font-size:12px; color:#666666;}

-->
</style>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/common.js"  type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>

<!-- axisj 컨트롤러 -->
<link rel="stylesheet" type="text/css" href="../../axisj/ui/arongi/AXJ.min.css"/>
<script type="text/javascript" src="../../axisj/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../../axisj/dist/AXJ.min.js"></script>


<link rel="stylesheet" type="text/css" href="../../css/base.css" />


<script language="javascript">


var idx = 0;
var diffCnt = <%=diffCnt%>;


/**
 * Require Files for AXISJ UI Component...
 * Based		: jQuery
 * Javascript 	: AXJ.js, AXProgress.js
 * CSS			: AXJ.css, AXProgress.css
 */	
var pageID = "AXProgress";
var myProgress = new AXProgress();	
var fnObj = {
		
   /*pageStart: function(){
		myProgress.setConfig({
			theme:"AXlineProgress",
			totalCount:10, 
			width:400, 
			top:100, 
			title:"AXProgress BAR",
			duration:299 // 프로세스바의 애니메이션 속도 값 입니다.
		});
	},*/
	
	progress2: {
		start: function(){
			mask.open();
			myProgress.start(function(){
			
				if(this.isEnd){
					idx = 0;
					myProgress.close();
					mask.close();
					fileDown();
					
				}else{
					// 무언가 처리를 해줍니다.	대부분 비동기 AJAX 통신 처리 구문을 수행합니다.
					idx++;
					insertExcel("idx="+idx);
					
				}	
			}, 
			{
				
				totalCount: diffCnt,
				/*totalCount:10,*/
				width:300, 
				top:200, 
				title:"다운로드 중... 잠시만 기다려 주세요.",
				cancel: {
					confirmMsg:"정말 취소하시겠습니까?",
					oncancel:function(){
						idx = 0;
						//myProgress.restart(); //다시 시작하기
						myProgress.close();
						mask.close();
					}
				}
			});
		}
	}	
};
//jQuery(document).ready(fnObj.pageStart.delay(0.1));

function insertExcel(subParm){

	var parm = "";
	parm = $("#fSend").serialize();
	
	var selType = "";
	
	
	$("#list_select option").each(function(){
		if(selType == ""){
			selType = $(this).val() + "," + $(this).text();
		}else{
			selType += "@" + $(this).val() + "," + $(this).text();
		}
	});
	
	if(parm == ""){
		parm = "selTypeName=" + selType;
	}else{
		parm += "&selTypeName=" + selType;
	}
	
	
	if(subParm){
		if(parm == ""){
			parm = subParm; 
		}else{
			parm += "&" + subParm; 
		}	
	}

	$.ajax({ type : "POST"
		   , async : true
		   , url : "inc_insert_excel.jsp"
		   , timeout : 60000 * 5
		   , dataType : "text"
		   , data : parm
		   , success : function(data){
			
					if(data.trim().split("|")[0] == "Y"){
						$("#fileName").val(data.trim().split("|")[1]);					
						myProgress.update();	
					}
					
					//$("#div_list").html(data);
			   }
	       ,beforeSend : function(){
	    	   	//$("#div_list").html("<img src='images/bigrotation2.gif' alt='' />");		    	   
	  					 }
	});
}

String.prototype.trim = function(){
	return this.replace(/^\s\s*/,'').replace(/\s\s*$/,'');
}

function fileDown(){
	var f = document.fSend;
	f.target = '';
	f.action = 'fileDownLoad.jsp';
	f.submit();
}

function colRight(){
	$("#list_exception option:selected").each(function(){
		$("#list_select").append("<option value='"+ $(this).val() +"'>"+$(this).text()+"</option>");
		$(this).remove();
	});
}

function colLeft(){
	$("#list_select option:selected").each(function(){
		$("#list_exception").append("<option value='"+ $(this).val() +"'>"+$(this).text()+"</option>");
		$(this).remove();
	});
}

function checkSame(){
	$('input[name=ra_same]').each(function(){
		if($(this).is(':checked')){
			same = $(this).val();
		}
	});
	if(same == '2'){
		diffCnt = 1;
	}
}

</script>
</head>
<body>

<form name="fSend" id="fSend" method="post">
<input type="hidden" name="encodingSearchKey" value="<%=searchKey%>">
<input type="hidden" name="typeCode" value="<%=typeCode %>">
<input type="hidden" name="typeCode_Etc" value="<%=typeCode_Etc %>">
<input type="hidden" name="relKeyword" value="<%=relationKey %>">
<input type="hidden" name="keyType" value="<%=keyType %>">
<input type="hidden" name="register" value="<%=register %>">
<input type="hidden" name="check_no" value="<%=check_no%>">
<input type="hidden" name="itseq" value="<%=it_seq%>">
<input type="hidden" name="sDateFrom" value="<%=sDateFrom%>">
<input type="hidden" name="sDateTo" value="<%=sDateTo%>">
<input type="hidden" name="ir_stime" value="<%=ir_stime%>">
<input type="hidden" name="ir_etime" value="<%=ir_etime%>">
<input type="hidden" name="check_no" value="<%=check_no%>">
<input type="hidden" name="diffCnt" value="<%=diffCnt%>">
<input type="hidden" id="fileName" name="fileName" value="">

<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td id="pop_head">
			<p>엑셀다운</p>
			<span><a href="javascript:close();"><img src="../../images/search/pop_tit_close.gif"></span>
		</td>
	</tr>
	
	<tr>
		<td>
		<!-- 게시판 시작 -->
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
			<tr>
			  <td height="5"></td>
		  </tr>
			<tr>
				<td><table border="0" cellpadding="0" cellspacing="0">
				  <tr>
				    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td height="24"><span class="sub_tit">제외열</span></td>
			          </tr>
				      <tr>
				        <td height="5"></td>
			          </tr>
				      <tr>
				        <td>
                        
                        <select name="list_exception" id="list_exception" style="width: 280px; height: 200px;" multiple="multiple" ondblclick="colRight()">
				        	<option value="D1">일자(yyyy-mm-dd)</option>
				        	<!-- <option value="D7">일자(yy.mm)</option>
				        	<option value="D8">일자(yyyy)</option> -->
				        	<option value="D2">사이트</option>
				        	<option value="D3">제목</option>
				        	<option value="D4">URL</option>
				        	<option value="D5">본문</option>
				        	<%
				        	if("1".equals(excel_type)){
				        	%>
				        	<option value="T_8">TM 상세</option>
				        	<option value="S_9_2">TM 성향</option>
				        	<option value="S_14_2">TM 정보속성</option>
				        	<option value="S_20_2">TM 과거성향</option>
				        	<option value="R_2">TM 연관키워드</option>
				        	
				        	<option value="T_10">Family 상세</option>
				        	<option value="S_9_3">Family 성향</option>
				        	<option value="S_14_3">Family 정보속성</option>
				        	<option value="S_20_3">Family 과거성향</option>
				        	<option value="R_3">Family 연관키워드</option>
				        	
				        	<option value="T_11">인물 상세</option>
				        	<option value="S_9_4">인물 성향</option>
				        	<option value="S_14_4">인물 정보속성</option>
				        	<option value="S_20_4">인물 과거성향</option>
				        	<option value="R_4">인물 연관키워드</option>
				        	
				        	<option value="T_12">SK그룹 상세</option>
				        	<option value="S_9_5">SK그룹 성향</option>
				        	<option value="S_15_5">SK그룹 정보속성</option>
				        	<option value="S_20_5">SK그룹 과거성향</option>
				        	<option value="R_5">SK그룹 연관키워드</option>
				        	
				        	<option value="T_13">관계사 상세</option>
				        	<option value="S_9_7">관계사 성향</option>
				        	<option value="S_15_7">관계사 정보속성</option>
				        	<option value="S_20_7">관계사 과거성향</option>
				        	<option value="R_7">관계사 연관키워드</option>
				        	<%
				        	} else {
				        	%>
				        	<option value="NEW_0">상세</option>
				        	<option value="NEW_9">성향</option>
				        	<option value="NEW_INFO">정보속성</option>
				        	<option value="NEW_20">과거성향</option>
				        	<option value="NEW_REL">연관키워드</option>
				        	<%
				        	}
				        	%>
				        	<option value="E_16">영향력구분</option>
				        	<option value="E_6">출처구분</option>
				        	<option value="E_17">정보유형구분</option>
				        	<option value="E_18">단순언급</option>
				        	<option value="E_19">주요이슈</option>
				        	
				        	<option value="D6">등록자</option>
				        	<%-- <%
				        		if(SS_M_ORGVIEW_USEYN.equals("Y")){
				        			out.println("<option value='D5'>본문</option>");
				        		}

				        		String[] arBean = null;
				        		for(int i = 0; i < arData.size(); i++){
				        			arBean = ((String)arData.get(i)).split("\\|");
				        			out.println("<option value='T"+ arBean[0] +"'>"+arBean[1]+"</option>");
				        		}
				        	%> --%>
				        </select>
                        
                        
                        </td>
			          </tr>
			        </table></td>
				    <td width="40" style="text-align:center;">
				    	<img src="../../images/issue/pop_excel/btn_left.gif" alt="" width="18" height="18" style="cursor: pointer;" onclick="colLeft();"><br>
                    	<img src="../../images/issue/pop_excel/btn_right.gif" alt="" width="18" height="18" vspace="5" style="cursor: pointer;" onclick="colRight();">
                    </td>
				    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td height="24"><span class="sub_tit">삽입열</span></td>
			          </tr>
				      <tr>
				        <td height="5"></td>
			          </tr>
				      <tr>
				        <td>
                        
                        <select name="list_select" id="list_select" style="width: 280px; height: 200px;" multiple="multiple" ondblclick="colLeft()">
				        </select>
                        
                        </td>
			          </tr>
			        </table></td>
			      </tr>
				  </table></td>
			</tr>
			<tr>
			  <td height="25">&nbsp;</td>
		  </tr>
			<tr>
			  <td height="30"><table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
			    <tr>
			      <td valign="top" style="width:340px;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td height="24"><span class="sub_tit">출력형식(복수형 분류체계 선택시)
			          </span></td>
		            </tr>
			        <tr>
			          <td height="5"></td>
		            </tr>
			        <tr>
			          <td><table border="0" cellpadding="0" cellspacing="0">
			            <tr>
			              <td width="270" valign="top"><table width="270" border="0" cellspacing="0" cellpadding="0">
			                <tr>
			                  <td height="24"><input type="radio" name="wordType" id="wordType1" value="A" checked="checked"><strong>A형</strong></td>
			                  </tr>
			                <tr>
			                  <td height="5"></td>
			                  </tr>
			                <tr>
			                  <td width="270" valign="top"><img src="../../images/issue/pop_excel/excel_01.png" alt="" width="270" height="120"></td>
			                  </tr>
			                </table></td>
			              <td width="40" style="text-align:center;"></td>
			              <td width="272" valign="top"><table width="272" border="0" cellspacing="0" cellpadding="0">
			                <tr>
			                  <td height="24"><input type="radio" name="wordType" id="wordType2" value="B"><strong>B형</strong></td>
			                  </tr>
			                <tr>
			                  <td height="5"></td>
			                  </tr>
			                <tr>
			                  <td><img src="../../images/issue/pop_excel/excel_02.png" alt="" width="272" height="187"></td>
			                  </tr>
			                </table></td>
			              </tr>
		              </table></td>
		            </tr>
			        </table></td>
		        </tr>
		      </table></td>
		  </tr>
		  
		  <tr>
			  <td height="30"><table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
			    <tr>
			      <td valign="top" style="width:340px;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td height="24"><span class="sub_tit">대표유사 형식
			          </span></td>
		            </tr>
			        <tr>
			          <td height="5"></td>
		            </tr>
			        <tr>
			          <td>
			          	<input type="radio" name="ra_same" value="1" checked="checked"><strong>전체 보기</strong>
			          	<input type="radio" name="ra_same" value="2"><strong>대표유사만 보기</strong>
			          </td>
		            </tr>
			        
			        
			        
			        
			        </table></td>
		        </tr>
		      </table></td>
		  </tr>
		  
			<tr>
			  <td height="30">&nbsp;</td>
		  </tr>
			<tr>
			  <td align="center"><img src="../../images/issue/pop_excel/btn_exceldown.gif" alt="" style="cursor: pointer;" onclick="checkSame();fnObj.progress2.start();"></td>
		  </tr>
		  </table>
		<!-- 게시판 끝 -->		</td>
	</tr>
	
</table>
</form>
</body>
</html>