<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="risk.search.MetaMgr
				 ,risk.search.MetaBean
				 ,risk.search.ShortenUrlGoogle
                 ,risk.util.ParseRequest
                 ,risk.sms.AddressBookDao
               	 ,risk.sms.AddressBookGroupBean
               	 ,java.util.Iterator
               	 ,java.util.List                    
                 ,java.util.ArrayList"                  
%>
<%@ include file="../inc/sessioncheck.jsp" %>			 
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	MetaMgr mtMgr = new MetaMgr();
	ShortenUrlGoogle sUrlGgle = new ShortenUrlGoogle();
	
	ArrayList arrMtBean = null;
	
	//String m_seq = pr.getString("m_seq",SS_M_NO);
	String check_no = pr.getString("check_no","");
	String mode = pr.getString("mode","");
		
	//관련정보 리스트
	MetaBean mtBean = null;
	arrMtBean = mtMgr.getImportantNewsList(check_no,mode);
	
	//시스템 멤버 그룹 
	AddressBookDao abDao = new AddressBookDao();
	AddressBookGroupBean abgBean = new AddressBookGroupBean();
	List abgGroupList = new ArrayList();
	abgGroupList = abDao.getAdressBookGroup();
	Iterator it = abgGroupList.iterator();
	Iterator it_copy = abgGroupList.iterator();
	
	//마지막 수신자 정보
	ArrayList lastAddressArry = new ArrayList();
	lastAddressArry = abDao.getlastAddress();
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>RISK</title>
<link rel="stylesheet" type="text/css" href="<%=SS_URL%>riskv3/issue/css/design.css">
<script type="text/javascript" src="<%=SS_URL%>riskv3/issue/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="<%=SS_URL%>riskv3/issue/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=SS_URL%>riskv3/issue/js/jquery.multi_selector.js"></script>
<script type="text/javascript" src="<%=SS_URL%>riskv3/issue/js/design.js"></script>
<script type="text/javascript" src="<%=SS_URL%>js/ajax.js"></script>
<!--[if (gte IE 6)&(lte IE 8)]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<script type="text/javascript" src="../asset/js/selectivizr-min.js"></script>
<![endif]-->
<script language="javascript">
$(function(){
	getMailReceiverList();	//메일 수신자 전체 목록 가져오기
	//loading();
});

function getMailReceiverList(){
	var param = $("#frm").serialize();
	$.post("aj_getMailReceiverList.jsp"
			,param
			,function(data){
				$("#ui_multi_selector_00").html(data);
				selectDataMove_Append();
				selectDataMove_Remove();
			});
}

function getLastAddressee(){
	var tmparr = <%=lastAddressArry%>;
	var items = getDataAddress(tmparr);
	
	$( "#ui_multi_selector_00" ).data( "multi_selector" ).removeItems( items );
	$( "#ui_multi_selector_01" ).data( "multi_selector" ).appendItems( items );
	
	$.each( items, function(){
		//appendItems
		//$( this ).appendTo( "#ui_multi_selector_01" );
	})
	//$( "#ui_multi_selector_00 li" ).eq( 0 ).prependTo( "#ui_multi_selector_01" );
	
	function getDataAddress( $arr ){
		var result = [];
		$( "#ui_multi_selector_00 li" ).each( function(){
			if( $arr.indexOf( parseInt( $( this ).attr( "user_data_value" ) ) ) >= 0 ){
				//console.log("@this : " + this.innerHTML);
				result.push( $( this ) );
			}
		});
		return result;
	}
			
}

function copyToClipboard(clickMdSeq){
	  var copyText = document.getElementById("news_sUrl_"+clickMdSeq);
	  copyText.select();
	  document.execCommand("Copy");
	  alert("복사되었습니다.");
}

function sendTomail(){
	var abSeq = "";
	$("#ui_multi_selector_01 > li").not(".disable").each(function(){
		if(abSeq == ""){
			abSeq += $(this).attr("user_data_value");	
		}else{
			abSeq += ","+$(this).attr("user_data_value");
		}
	});
	
	var check_no = $("#md_seq").val().split('@$$@');
	var urlArea = "";
	var contentArea = "";
	for(var i = 0; i < check_no.length; i++){
		if(i == 0){
			urlArea += $("#news_sUrl_"+check_no[i]).val()
			contentArea += $("#news_content_"+check_no[i]).val()
		}else{
			urlArea += '@$$@' + $("#news_sUrl_"+check_no[i]).val()
			contentArea += '@$$@' + $("#news_content_"+check_no[i]).val()
		}
	}
	
	if(abSeq == ""){
		alert("메일 수신 대상자를 선택해주세요.");
		return;
	}else{
		$("[name=urlArea]").val(urlArea);
		$("[name=contentArea]").val(contentArea);
		$("[name=mailreceiver]").val(abSeq);
		var formData = $("#frm").serialize();
		$.ajax({
			type : "POST"
			,url: "meta_data_prc.jsp"
			,timeout: 30000
			,data : formData
			,dataType : 'text'
			,async: true
			,success : function(data){
						if(data == "fail" ){
							alert("메일 발송에 실패하였습니다.");
							window.close();
						}else{
							alert("메일을 발송하였습니다.");
							window.close();
						}
					
					  }
			,beforeSend : function(){
						$("#sending").css("display", "");
					}			
			});	
	}
	
}

/**
* URL열기
*/
function hrefPop(url){
	window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url));
}

</script>
</head>
<body>
	<div id="popup_wrap">
		<h1 class="invisible">SK그룹</h1>
		<h2>기사 공유</h2>
		<a href="javascript:close();" class="close">닫기</a>
	<form name="frm" id="frm" method="post">
	<input type="hidden" name="mode" value="news_mail"><!-- 메일발송 모드 -->
	<input type="hidden" name="mr_type" value="S"><!-- 공유메일 타입-->
	<input type="hidden" name="urlArea" value=""> <!-- 단축 URL -->
	<input type="hidden" name="contentArea" value=""> <!-- 내용 요약 -->
	<input type="hidden" name="mailreceiver" value=""><!-- 메일 대상자 -->
	<input type="hidden" name="asSeq" /><!-- 메일수신자 그룹 -->
		<!-- 공유 이슈 -->
		<div class="article m_t_10">
			<h3><span class="icon">-</span>공유 이슈</h3>
			<div class="ui_table_02">
				<table summary="공유 이슈">
				<caption>공유 이슈</caption>
				<colgroup>
					<col style="width:50px">
					<col style="width:80px">
					<col style="width:250px">
					<col style="width:90px">
					<col style="width:60px">
				</colgroup>
				<thead>
					<tr>
						<th><span>구분</span></th>
						<th><span>출처</span></th>
						<th><span>제목</span></th>
						<th><span>수집일시</span></th>
						<th><span></span></th>
					</tr>
				</thead>
				<tbody>
				<%
				//md_seqs
				String checks = "";
				String titles = "";
				String sites = "";
				String dates = "";
				String urls = "";
				//String importants = "";
				if(arrMtBean.size() > 0){
					for(int i = 0; i < arrMtBean.size(); i++){
						mtBean = (MetaBean)arrMtBean.get(i);
						if(i == 0){
							checks += mtBean.getMd_seq();
							titles += mtBean.getMd_title().replaceAll("\"", "'");
							sites += mtBean.getMd_site_name();
							dates += mtBean.getMd_date();
							urls += mtBean.getMd_url();
							//importants += mtBean.getMd_important();
						}else{
							checks += "@$$@"+mtBean.getMd_seq();
							titles += "@$$@"+mtBean.getMd_title().replaceAll("\"", "'");
							sites += "@$$@"+mtBean.getMd_site_name();
							dates += "@$$@"+mtBean.getMd_date();
							urls += "@$$@"+mtBean.getMd_url();
							//importants += "@$$@"+mtBean.getMd_important();
						}
				%>
					<tr>
						<td><%if(mtBean.getMd_important().equals("1")){%><strong style="color:#F41B2F">[중요]</strong><%}%></td>
						<td><%=mtBean.getMd_site_name()%></td>
						<%-- <td><input type="hidden" name="ir_title" value="<%=mtBean.getMd_title()%>" /><%=mtBean.getMd_title()%></td> --%>
						<td><%=mtBean.getMd_title()%></td>
						<td><%=mtBean.getMd_date().substring(0, 10)%></td>
						<td><img src="../../images/search/btn_Shortcut.gif" style="cursor:pointer" onclick="hrefPop('<%=mtBean.getMd_url()%>')"></img></td>
					</tr>
				<%
					}
				}
				%>
				</tbody>
				</table>
				<input type="hidden" id="md_seq" name="md_seq" value="<%=checks%>"> <!-- 기사 -->
				<input type="hidden" id="md_title" name="md_title" value="<%=titles%>"> <!-- 제목 -->
				<input type="hidden" id="md_site" name="md_site" value="<%=sites%>"> <!-- 출처 -->
				<input type="hidden" id="md_date" name="md_date" value="<%=dates%>"> <!-- 수집날짜 -->
				<input type="hidden" id="md_url" name="md_url" value="<%=urls%>"> <!-- url -->
				<%-- <input type="hidden" id="md_important" name="md_important" value="<%=importants%>"> --%> <!-- 중요도 -->
			</div>
		</div>
	</form>
		<!-- // 공유 이슈 -->
		
		<%
			if(arrMtBean.size() > 0){
				for(int i = 0; i < arrMtBean.size(); i++){
					String sUrl = "";
					String uAreaText = "";
					mtBean = (MetaBean)arrMtBean.get(i);
					sUrl = sUrlGgle.getShortenUrl(mtBean.getMd_url());
					if(mtBean.getMd_important().equals("1")) uAreaText += "[중요]";
					uAreaText += mtBean.getMd_site_name()+", "+mtBean.getMd_title()+"("+mtBean.getMd_date().substring(0, 19)+")\r\n"+sUrl;
		%>
		<!-- 단축 URL -->
		<div class="article m_t_20">
			<h3><span class="icon">-</span>단축 URL</h3>
			<div class="ui_table">
				<table width="100%">
				<caption>단축 URL</caption>
				<colgroup>
					<col>
					<col style="width:60px">
				</colgroup>
				<tbody>
					<tr>
						<td><textarea style="width:100%;height:53px;padding:8px;border:1px solid #dbdbdb;" id="news_sUrl_<%=mtBean.getMd_seq()%>"><%=uAreaText%></textarea></td>
						<td style="padding-left:6px;"><img src="../../images/search/btn_copy.gif" style="cursor:pointer" onclick="copyToClipboard(<%=mtBean.getMd_seq()%>)"></img></td>
					</tr>
				</tbody>
				</table>
			</div>
		</div>
		<!-- // 단축 URL  -->

		<!-- 내용 요약 -->
		<div class="article m_t_20">
			<h3><span class="icon">-</span>내용 요약</h3>
			<textarea style="width:100%;height:180px;padding:8px;border:1px solid #dbdbdb;" id="news_content_<%=mtBean.getMd_seq()%>"><%=mtBean.getMd_content()%></textarea>
		</div>
		<!-- // 내용 요약  -->
		<%
				}
			}
		%>
		
		<!-- 메일 발송 대상 -->
		<div class="article m_t_20">
			<h3><span class="icon">-</span>메일 발송 설정</h3>
			<table class="ui_multiselector_box_00" summary="메일 수신자/대상자 설정">
			<caption>메일 수신자/대상자 설정</caption>
			<colgroup>
				<col>
				<col style="width:60px">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<td>
						<div class="ui_box_00">
							<h4>메일 수신자 그룹</h4>
							<div class="floatbox grouping_box">
								<div class="floatbox_left"><h5><span class="icon">-</span>그룹선택</h5></div>
								<div class="floatbox_right">
									<select id="input_select_grouping_00" class="ui_select_03" name="groupId">
										<option value="all">전체</option>
					<%
						while(it.hasNext()){
							abgBean = new AddressBookGroupBean();
							abgBean = (AddressBookGroupBean)it.next();
					%>
										<option value="<%=abgBean.getAg_seq()%>"><%=abgBean.getAg_name()%></option>
					<%
						}
					%>										
									</select><label for="input_select_grouping_00" class="invisible">라벨</label>
								</div>
							</div>
							<div class="m_s_box">
								<ul id="ui_multi_selector_00" class="ui_select_multi_00" style="height:200px">
									<!-- 메일 수신자 그룹 -->
								</ul>
							</div>
							<div class="infos"><span class="icon">-</span>Ctrl, Shift + 마우스 클릭으로 복수 선택이 가능합니다.</div>
						</div>
					</td>
					<td>
						<a href="javascript:selectDataMove_Append()" class="btn_left">추가</a>
						<a href="javascript:selectDataMove_Remove()" class="btn_right">삭제</a>
					</td>
					<td>
						<div class="ui_box_00">
							<h4>메일 대상자 그룹</h4>
							<div class="floatbox grouping_box">
								<div class="floatbox_left"><h5><span class="icon">-</span>그룹선택</h5></div>
								<div class="floatbox_right">
									<img src="../../images/search/btn_useradd2.gif" style="cursor:pointer; margin-top: -4px;" onclick="getLastAddressee();"></img>
									<select id="input_select_grouping_01" class="ui_select_03">
										<option value="all">전체</option>
										<%
											while(it_copy.hasNext()){
												abgBean = new AddressBookGroupBean();
												abgBean = (AddressBookGroupBean)it_copy.next();
										%>
										<option value="<%=abgBean.getAg_seq()%>"><%=abgBean.getAg_name()%></option>
										<%
											}
										%>
									</select><label for="input_select_grouping_01" class="invisible">라벨</label>
								</div>
							</div>
							<div class="m_s_box">
								<ul id="ui_multi_selector_01" class="ui_select_multi_00" style="height:200px">
								<!-- 메일 대상자 그룹 -->
								</ul>
							</div>
							<div class="infos"><span class="icon">-</span>Ctrl, Shift + 마우스 클릭으로 복수 선택이 가능합니다.</div>
						</div>
					</td>
				</tr>
			</tbody>
			</table>

			<script type="text/javascript">
				if( $( ".ui_select_multi_00" ).length > 0 ) $( ".ui_select_multi_00" ).multi_selector({});
				$( "#input_select_grouping_00" ).change(function(){
					$( "#ui_multi_selector_00" ).data( "multi_selector" ).sortGroup( "data-role-value", this.value );	// Group으로 정렬할 경우에 사용 - ( 커스텀 속성명, 속성값 )2가지 파라미터 전달
				});
				$( "#input_select_grouping_01" ).change(function(){
					$( "#ui_multi_selector_01" ).data( "multi_selector" ).sortGroup( "data-role-value", this.value );	// Group으로 정렬할 경우에 사용 - ( 커스텀 속성명, 속성값 )2가지 파라미터 전달
				});

				function selectDataMove_Append(){
					var datas = $( "#ui_multi_selector_00" ).data( "multi_selector" ).getSelectedItems();
					$( "#ui_multi_selector_00" ).data( "multi_selector" ).removeItems();
					$( "#ui_multi_selector_01" ).data( "multi_selector" ).appendItems(datas);
				}
				function selectDataMove_Remove(){
					var datas = $( "#ui_multi_selector_01" ).data( "multi_selector" ).getSelectedItems();
					$( "#ui_multi_selector_01" ).data( "multi_selector" ).removeItems();
					$( "#ui_multi_selector_00" ).data( "multi_selector" ).appendItems(datas);
					$( "#ui_multi_selector_00" ).data( "multi_selector" ).sortTxts();
					$( "#ui_multi_selector_00 input[type=checkbox]" ).prop( "checked", false );
				}
			</script>
		</div>
		<!-- // 메일 발송 대상 -->
		<div class="bot_btns">
			<button class="ui_btn_05 ui_shadow_00" onclick="sendTomail();"><span class="icon mail">-</span>발송하기</button>
		</div>
	</div>
	<img id="sending" src="../../images/issue/sending.gif" style="position: absolute;left:50%;top:50%;margin:-65px 0 0 -100px;display:none;" >
</body>
</html>