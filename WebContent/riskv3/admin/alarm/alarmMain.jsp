<%@ page contentType = "text/html; charset=utf-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.util.PageIndex"%>
<%@ page import="risk.admin.alarm.alarmMgr"%>
<%@ page import="risk.admin.alarm.alarmBean"%>
<%@ page import="risk.admin.keyword.KeywordMng"%>
<%@ page import="risk.admin.keyword.KeywordBean"%>
<%
	
	ParseRequest pr = new ParseRequest(request);
	alarmMgr am = new alarmMgr();
	alarmBean ab = new alarmBean();
	KeywordMng km = new KeywordMng();
	KeywordBean kb = new KeywordBean();

	ArrayList alarmList = new ArrayList();

	int rowCnt = 10;
	int nowpage  = pr.getInt("nowpage",1);
	
	alarmList = am.getAlarmList(nowpage, rowCnt, request);
	
	int iTotalCnt= Integer.parseInt(String.valueOf(request.getAttribute("totalcnt")));
	int iTotalPage      = iTotalCnt / rowCnt;
    if ( ( iTotalCnt % rowCnt ) > 0 ) iTotalPage++;
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RISK</title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script language="javascript">
	//radio Type 전체 체크
	function allCheck(id, check){
	    var obj = document.getElementsByName(id);
	    if(check == 'img'){
	        var allObj = document.getElementById('all_'+id);
	        allObj.click();
	    }else{
	        if(check){
	            if(obj){
	                if(obj.length){
	                    for(var i = 0; i < obj.length; i++){
	                        obj[i].checked = true;
	                    }
	                }else{
	                	obj.checked = true;
	                }
	            }
	        }else{
	            if(obj){
	                if(obj.length){
	                    for(var i = 0; i < obj.length; i++){
	                        obj[i].checked = false;
	                    }
	                }else{
	                	obj.checked = false;
	                }
	            }
	        }
	    }
	}

	function alarmDel(){
		$('input[name=ar_seq]').each(
			function(){
				if(this.checked){
					$('#ar_seqs').val() == '' ? $('#ar_seqs').val(this.value) : $('#ar_seqs').val($('#ar_seqs').val()+','+this.value);
				}
			}
		);

		var f = document.getElementById('fSend');
		f.mode.value = 'delete';
		f.action = 'alarmPrc.jsp';
		f.target = 'prc';
		f.submit();
	}

	function alarmAdd(){
		document.location.href = "alarmAdd.jsp";
	}

	function alarm_detail(ar_seq){
		var f = document.getElementById('fSend');
		f.ar_seqs.value = ar_seq;
		f.action = 'alarmDetail.jsp';
		f.target = '';
		f.submit();
	}

	function pageClick( paramUrl ) {
		var f = document.getElementById('fSend');
		f.nowpage.value = paramUrl.substr(paramUrl.indexOf("=")+1, paramUrl.length);
		f.action='alarmMain.jsp';
		f.submit();
    }
</script>
</head>
<body style="margin-left: 15px">
<form name="fSend" id="fSend" method="post">
<input type="hidden" name="ar_seqs" id="ar_seqs">
<input type="hidden" name="mode" id="mode">
<input type="hidden" name="nowpage" value="<%=nowpage%>">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/alarm/tit_icon.gif" /><img src="../../../images/admin/alarm/tit_01.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">키워드 알람</td>
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
			<tr>
				<td style="height:40px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:76px;"><img src="../../../images/admin/alimi/btn_allselect.gif" onclick="allCheck('ar_seq', 'img');" style="cursor:pointer;"/></td>
						<td><img src="../../../images/admin/alimi/btn_del.gif" onclick="alarmDel();" style="cursor:pointer;"/></td>
						<td style="width:52px;"><img src="../../../images/admin/alimi/btn_alrimiadd.gif" onclick="alarmAdd();" style="cursor:pointer;"/></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td>       
				<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
				<col width="5%"><col width="20%"><col width="35%"><col width="15%"><col width="10%"><col width="15%">
					<tr>           
						<th><input type="checkbox" name="all_ar_seq" id="all_ar_seq" onclick="allCheck('ar_seq', this.checked);"></th>
						<th>키워드</th>
						<th>분석방법</th>
						<th>수신방법</th>
						<th>수신자</th>
						<th>등록일</th>
					</tr>
<%
	
	if(alarmList.size()>0){
	  	for(int i=0; i < alarmList.size(); i++)
		{
	  		ab =  (alarmBean)alarmList.get(i);
%>
					<tr>
						<td><input type="checkbox" name="ar_seq" id="ar_seq" value="<%=ab.getAr_seq()%>"></td>
						<td><span style="cursor:pointer" onclick="alarm_detail('<%=ab.getAr_seq()%>')"><%=km.getKeywordInfo(ab.getK_seq()).getK_value()%></span></td>
						<td>
<%
			if(ab.getAr_type().equals("1")){
				out.print("전체 평균대비 "+ab.getAr_value()+"%");
			}else if(ab.getAr_type().equals("2")){
				out.print("최근 "+ab.getAr_time()+"분간 "+ab.getAr_value()+"%");
			}else if(ab.getAr_type().equals("3")){
				out.print("앞 수량 구간 가중치");
			}
%>
						</td>
						<td>
<%
			if(ab.getAr_sendtype().equals("1")){
				out.print("mail");
			}else if(ab.getAr_sendtype().equals("2")){
				out.print("sms");
			}else if(ab.getAr_sendtype().equals("3")){
				out.print("mail+sms");
			}
%>
						</td>
						<td><%=ab.getAr_receiver().split(",").length%></td>
						<td><%=ab.getAr_regdate().substring(0, 16)%></td>
					</tr>
<%
		}
	}else{
%>
					<tr>
						<td colspan="6" style="font-weight:bold;height:40px" align="center">조건에 맞는 데이터가 없습니다.</td>
					</tr>
<%		
	}
%>
				</table>
				</td>
			</tr>
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;" align="center">
				<table style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td>
							<table id="paging" border="0" cellpadding="0" cellspacing="2">
								<tr>
									<%=PageIndex.getPageIndex(nowpage, iTotalPage,"","" )%>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<iframe name="prc" id="prc" style="display:none"></iframe>
</form>
</body>
</html>