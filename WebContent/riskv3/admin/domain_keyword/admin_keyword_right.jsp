<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="risk.util.*" %>
<%@ page import="java.util.List" %>
<%@ page import="risk.admin.domain_keyword.DomainKeywordMng" %>
<%@ page import="risk.admin.domain_keyword.DomainKeywordBean" %>
				 

<%@ include file="../../inc/sessioncheck.jsp" %>
<%
    ParseRequest    pr = new ParseRequest(request);

    String xp          = pr.getString("xp");
    String yp          = pr.getString("yp");
    String zp          = pr.getString("zp");

	String value = "";
	String part = "total";
	String k_op = "";
	String near_len = "";
	String partname = "전체키워드그룹";
	List exkeylist = null;
	
	ArrayList arSgSeq = null;

	if( xp.equals("") ) xp = "0";
	if( yp.equals("") ) yp = "0";
	if( zp.equals("") ) zp = "0";

	DomainKeywordMng fkmng = DomainKeywordMng.getInstance();
	
	String search_range = pr.getString("search_range", "1");
	String lan = pr.getString("lan");
	String group = pr.getString("group");
	

	if( xp.equals("0") || xp.equals("") ) {
	} else if( yp.equals("0") || yp.equals("") ) {
		part = "upkg";
		partname = "대분류";
		
		String[] arValue =  fkmng.getKG(xp); 
		
		value = arValue[0];
		//arSgSeq = fkmng.getSgSeq(arValue[1]);
		
	} else if( zp.equals("0") || zp.equals("") ) {
		part = "downkg";
		partname = "영역지정";
		value = fkmng.getKG(xp,yp);
	} else {
		part = "kg";
		partname = "키워드";
		String[] arValue  = fkmng.getKG(xp,yp,zp);
		value = arValue[0];
		k_op = arValue[1];
		near_len = arValue[2];
		exkeylist = fkmng.getEXKG(xp,yp,zp);
		
	}
%>

<%@page import="java.util.ArrayList"%><html>
<head>
<title>RISK V3 - RSN</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- <link rel="stylesheet" href="css/basic.css" type="text/css"> -->
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<style>
	<!--
	td { font-size:12px; color:#333333; font-family:"dotum"; ; line-height: 18px}
    input { font-size:12px; border:1px solid #CFCFCF; height:16px; color:#767676; }
	.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}
    .tCopy { font-family: "Tahoma"; font-size: 12px; color: #000000; font-weight: bold}
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #F3F3F3;
}
	-->
	</style>
<script language="JavaScript" type="text/JavaScript">
<!--
	function edit_kg()
	{
		if( !keygroup.val.value )
		{
			alert('<%=partname%>명을 입력하십시요. ');
		} else {
			keygroup.mode.value = 'edit';
			keygroup.submit();
		}
	}

	function del_kg()
	{
		if( !keygroup.xp.value )
		{
			alert('대상을 선택하십시요.');
		} else {
			keygroup.mode.value = 'del';
			keygroup.submit();
		}
	}

	function up_kg()
	{
		if( keygroup.yp.value > 0 ) {
			alert('위치변경은 대분류만 가능합니다.');
		} else if( !keygroup.xp.value ){
			alert('대상을 선택하십시요.');
		} else {
			keygroup.mode.value = 'up';
			keygroup.submit();
		}
	}

	function down_kg()
	{
		if( keygroup.yp.value > 0 ) {
			alert('위치변경은 대분류만 가능합니다.');
		} else if( !keygroup.xp.value ){
			alert('대상을 선택하십시요.');
		} else {
			keygroup.mode.value = 'down';
			keygroup.submit();
		}
	}

	function del_exkg()
	{
		if( !keygroup.type.value )
		{
			alert('대상을 선택하십시요.');
		} else {
			keygroup.tg.value = 'exkey';
			keygroup.mode.value = 'del';
			keygroup.submit();
		}
	}

	function del_exkg()
	{
		if( !keygroup.type.value )
		{
			alert('대상을 선택하십시요.');
		} else {
			keygroup.tg.value = 'exkey';
			keygroup.mode.value = 'del';
			keygroup.submit();
		}
	}


	function saveSiteGroup()
	{

		var f = document.keygroup;

		var strTmp = '';
		if(f.sgSeq){
			if(f.sgSeq.length){
				for(var i = 0; i < f.sgSeq.length; i++){
					if(f.sgSeq[i].checked == true){
						if(strTmp == ''){
							strTmp = f.sgSeq[i].value;
						}else{
							strTmp += ',' + f.sgSeq[i].value;
						}
					}
				} 
			}else{
				if(f.sgSeq.checked == true){
					strTmp = f.sgSeq.value;		
				}	
			}
		}		
		f.sg_seqs.value = strTmp;		

		keygroup.mode.value = 'sitegroup';
		keygroup.submit();

	}

	function nearLenSave()
	{
		keygroup.mode.value = 'near';
		keygroup.submit();
	}
	function nearLenSaveAll()
	{
		keygroup.mode.value = 'near_all';
		keygroup.submit();
	}

	function onlyNumber(event) {
	    var key = window.event ? event.keyCode : event.which;    

	    if ((event.shiftKey == false) && ((key  > 47 && key  < 58) || (key  > 95 && key  < 106)
	    || key  == 35 || key  == 36 || key  == 37 || key  == 39  // 방향키 좌우,home,end  
	    || key  == 8  || key  == 46 ) // del, back space
	    ) {
	        return true;
	    }else {
	        return false;
	    }    
	};
	
	$( document ).ready( function(){
		detail_range(1, '', 0);
	});
	
	function detail_range(val, lan, group){
		var page = 'detail_search_list.jsp';
		var div = 'detail_search';
		var type = "text";
		
		param = "detail_type=" + val;
		if(lan != '') {
			param += "&lan=" + lan;
		} 
		if(group != '') {
			param += "&group=" + group;
		}
		
		$.ajax({ type : "POST"
				, async : true
				, url : page
				, timeout : 30000
				, dataType : type
				, data : param
				, success : function(data){	
					var list = data.trim().split("/@/")[0];
					var list2 = data.trim().split("/@/")[1];
					var temp = JSON.parse(list);
					var temp2 = JSON.parse(list2);
					var innerHtml = '';
					
					if(val == 1){
						$(temp.list).each(function(i) {
							if(i % 2 == 0) {
								innerHtml += '<tr>';
							}
							
							innerHtml += '<td>';
							innerHtml += '<input style="border:0px;" type="checkbox" name="sgSeq" value="'+temp.list[i].SG_SEQ+'">'+temp.list[i].SG_NAME+'</td>';
							
							if(i % 2 == 1) {
								innerHtml += '</tr>';
							}
						});
					} else if(val == 2) {
						
						innerHtml += '<table border="0" cellspacing="0" cellpadding="0">';
						innerHtml += '<tr>';
						innerHtml += '<td>';
						innerHtml += '<img src="../../../images/admin/site/admin_ico03.gif"  hspace="3">언어선택';
						innerHtml += '</td>';
						innerHtml += '<td width="30"></td>';
						innerHtml += '<td>';
						innerHtml += '<img src="../../../images/admin/site/admin_ico03.gif"  hspace="3">사이트 그룹 선택';
						innerHtml += '</td>';
						innerHtml += '</tr>';
						innerHtml += '<tr height="1"><td></td></tr>';

						innerHtml += '<tr>';
						innerHtml += '<td width="50%" height="10px">';
						innerHtml += '<select name="language" id="language" class="t" style="width:130px;" onchange="detail_range('+val+',this.value, '+group+');">';
						var lan_value = "-전체,KOR-한국어,ENG-영어";
						var selected = '';
						for(var i = 0; i < lan_value.split(",").length; i++) {
							if(lan_value.split(",")[i].split("-")[0] == lan) {
								selected = 'selected';
							} else {
								selected = '';
							}
							innerHtml += '<option value="'+lan_value.split(",")[i].split("-")[0]+'" '+selected+'>'+lan_value.split(",")[i].split("-")[1]+'</option>';
						}
						innerHtml += '</select>';
						innerHtml += '</td>';
						innerHtml += '<td width="10"></td>';
						
						innerHtml += '<td width="50%" height="10px">';
						innerHtml += '<select name="sg_seq" id="sg_seq" class="t"  style="width:130px;" onchange="detail_range('+val+',\''+lan+'\', this.value);">';
						innerHtml += '<option value="0">전체</option>';
						$(temp2.list).each(function(i) {
							var selected = '';
							if(temp2.list[i].SG_SEQ == group) {
								selected = 'selected';
							} else {
								selected = '';
							}
							innerHtml += '<option value="'+temp2.list[i].SG_SEQ+'" '+selected+'>'+temp2.list[i].SG_NAME+'</option>';
						});
						innerHtml += '</select>';
						innerHtml += '</td>';
						innerHtml += '</tr>';
						innerHtml += '<tr height="5"><td></td></tr>';
						innerHtml += '</table>';
						
						innerHtml += '<tr>';
						innerHtml += '<td>';
						innerHtml += '<div style="width:100%; height:185px; overflow:auto;">'
						innerHtml += '<table border="0" cellspacing="0" cellpadding="0" width="100%" bgcolor="ffffff">';
						$(temp.list).each(function(i) {
							innerHtml += '<tr>';
							innerHtml += '<td>';
							innerHtml += '<input style="border:0px;" type="checkbox" name="sSeq" value="'+temp.list[i].S_SEQ+'">'+temp.list[i].S_NAME+'</td>';
							innerHtml += '</tr>';
						});
						
						innerHtml += '</table>';
						innerHtml += '</div>';
						innerHtml += '</td>';
						innerHtml += '</tr>';
					} 
					
					$("#" + div).empty().html(innerHtml);
			   }
		 });
	}
	
	
	function insertSECTION_KEY_RELATION() {
		if($("#search_section option:selected").val() != 0 && $("#search_word").val() == '') {
			alert("검색 키워드를 입력하세요.");
		} else {
			var f = document.keygroup;
			f.mode.value = 'insertSECTION_KEY_RELATION';
			f.k_section_type.value = $("#search_range option:selected").val();
			
			if(f.k_section_type.value == 1) {
				f.sg_seqs.value = getCheckBoxValue('sgSeq');
				f.s_seqs.value = '';
			} else if(f.k_section_type.value == 2) {
				f.sg_seqs.value = '';
				f.s_seqs.value = getCheckBoxValue('sSeq');
			}
			
			f.skr_type.value =  $("#search_section option:selected").val();
			f.skr_keyword.value = $("#search_word").val();
			
			f.submit();
		}
		
	}
	
	//체크박스 체크된 데이터 가져오는 펑션
	function getCheckBoxValue(checkBoxName){
		var checkValue = "";
		
		$('input:checkbox[name='+checkBoxName+']:checked').each(function() {
			if(checkValue == ""){
				checkValue = $(this).val();	
			}else{
				checkValue += ","+ $(this).val();
			}
		});
		return checkValue;	
	}
//-->
</script>
</head>

<body>
<table width="315" border="0" cellspacing="0" cellpadding="0">
<form name="keygroup" action="admin_keyword_prc.jsp" method="post">
<input type="hidden" name="tg" value="<%=part%>">
<input type="hidden" name="mode">
<input type="hidden" name="skr_seq">	<!--SKR_SEQ  -->
<input type="hidden" name="k_section_type">	<!--K_SECTION_TYPE  -->
<input type="hidden" name="xp" value="<%=xp%>"> <!--K_XP  -->
<input type="hidden" name="yp" value="<%=yp%>"> <!--K_YP  -->
<input type="hidden" name="zp" value="<%=zp%>"> <!--K_ZP  -->
<input type="hidden" name="sg_seqs"> <!-- SG_SEQS -->
<input type="hidden" name="s_seqs"> <!--S_SEQS  -->
<input type="hidden" name="skr_type"> <!--SKR_TYPE  -->
<input type="hidden" name="skr_keyword"> <!--SKR_KEYWORD  -->
  <tr>
    <td height="20" style="padding-left:7px"><img src="../../../images/admin/keyword/admin_ico02.gif" width="8" height="5" hspace="3" align="absmiddle"><span class="menu_black"><strong><%=value%><%if(!part.equals("total")) out.print(" - ");%><%=partname%></strong></span></td>
  </tr>
</table>
<table width="315" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" style="padding-left:10px">
	<table width="300" border="0" cellspacing="0" cellpadding="0">
      <tr height="10">
        <!-- <td><img src="../../../images/admin/keyword/brank.gif" width="1" height="5"></td> -->
      </tr>
<%
	if( part.equals("downkg") )
	{
%>
      <tr>
        <td><img src="../../../images/admin/keyword/admin_ico03.gif" width="10" height="11" hspace="3">검색 범위</td>
      </tr>
      <tr>
        <td height="25">
	   		<select name="search_range" id="search_range" style="width:150px; margin-right: 10px" onchange="detail_range(this.value,'',0);"> 
				<option value="1">채널</option>
				<option value="2">사이트</option>
			</select>	
      </tr>
      
      <tr>
        <td>
		<table width="100%" height="200" border="1" cellspacing="0" cellpadding="10" bordercolor="#DDDDDD">
		  <tr >
		    <td valign="top">
		    	<table border='0' width='100%'>
		    		<tr>
		    			<td>
		    				<table width='100%'>
		    					<tr>
		    						<td><img src='../../../images/admin/keyword/admin_ico03.gif' width='10' height='11' hspace='3'>상세 범위</td>
		    					</tr>
		    				</table>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>
		    				<table width='100%' id="detail_search" name="detail_search">
		    					<tr>
		    						<td>
		    							<input style='border:0px;' type='checkbox' name='sgSeq' value="">
		    						</td>
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
      <tr>
      	<td height="5">
      </tr>
      
      
      <tr>
        <td><img src="../../../images/admin/keyword/admin_ico03.gif" width="10" height="11" hspace="3">검색 영역</td>
      </tr>
      <tr>
        <td height="25">
	   		<select name="search_section" id="search_section" style="width:150px; margin-right: 10px"> 
				<option value="0">전체</option>
				<option value="1">사이트명</option>
				<option value="2">수집원명</option>
				<option value="3">URL</option>
				<option value="4">수집원번호</option>
			</select>	
        </td>
      </tr>
      <tr>
      	<td height="5">
      </tr>
      
      <tr>
        <td><img src="../../../images/admin/keyword/admin_ico03.gif" width="10" height="11" hspace="3">검색 키워드</td>
      </tr>
      <tr>
        	<td>
        	<input name="search_word" id="search_word" type="text" size="36" value="" OnKeyDown="Javascript:if (event.keyCode == 13) {}">
          <img src="../../../images/admin/keyword/admin_save.gif" width="45" height="18" align="absmiddle" onclick="insertSECTION_KEY_RELATION();" style="cursor:hand;"></td>
      </tr>
      
<%
	}
%>
<%
	if( part.equals("kg") )
	{
%>
      <tr>
        <td><img src="../../../images/admin/keyword/admin_ico03.gif" width="10" height="11" hspace="3">제외단어 리스트</td>
      </tr>
      <tr height="3">
        <td></td>
      </tr>
      <tr>
        <td valign="top">
		<select name="type" multiple style="width:225px;height:180px;" class="t">
<%
		for(int i=0; i < exkeylist.size();i++) {
			DomainKeywordBean exkeyinfo = (DomainKeywordBean)exkeylist.get(i);
%>
			<option value="<%=exkeyinfo.getKGtype()%>"><%=exkeyinfo.getKGvalue()%></option>
<%
		}
%>
		</select>&nbsp;<img src="../../../images/admin/keyword/admin_del.gif" width="45" height="18" border="0" onclick="del_exkg();" style="cursor:pointer; vertical-align: bottom">
		</td>
      </tr>
      
      <%
      	if(k_op.equals("2")){
      %>
      
      <tr>
      	<td style="padding-top: 5px"><img src="../../../images/admin/keyword/admin_ico03.gif" width="10" height="11" hspace="3">최대 인접 자릿수 </td>
      </tr>
      <tr height="5">
        <td></td>
      </tr>
      <tr>
        <td valign="top" style="padding-left: 20px">최대 <input type="text" name="nearLen" style="width: 35px;text-align: right;" maxlength="4"  onkeydown="return onlyNumber(event)" value="<%=near_len%>"> 자 &nbsp; <img src="../../../images/admin/keyword/admin_save.gif" style="vertical-align: middle; cursor: pointer;" onclick="nearLenSave();">&nbsp;&nbsp;<img src="../../../images/admin/keyword/admin_all_save.gif" style="vertical-align: middle; cursor: pointer;" onclick="nearLenSaveAll();"></td>
       </tr>
       <%} %> 

<%
	} else if(!part.equals("downkg")) {
%>
      <tr>
        <td>
		<table width="100%" height="200" border="1" cellspacing="0" cellpadding="10" bordercolor="#DDDDDD">
		  <tr >
		    <td valign="top">
		    <%
		    	if(!part.equals("downkg")){
		    %>
				<strong>키워드그룹 설정안내</strong><br><br>
				<b>추가</b> - 좌측메뉴에서 선택한 뒤 하단의 추가버튼을 누르시면 하위 그룹 및 키워드가 추가됩니다.<br>
				<b>수정</b> - 좌측메뉴에서 수정을 원하시는 그룹 및 키워드를 선택한뒤 우측메뉴에서 수정하시면 됩니다.<br>
				<b>삭제</b> - 대분류,소분류,키워드의 삭제는 좌측메뉴에서 제외키워드의 삭제는 우측메뉴에서 가능합니다.<br>
				<b>위치변경</b> - 변경을 원하시는 대분류를 선택한 뒤 좌측메뉴 하단의 상,하 버튼을 누르시면 됩니다.		    
		    
		    <%		
		    	}
		    %>

			</td>
		  </tr>
		</table>
		</td>
      </tr>
<%
	}
%>
    <tr>
    	<td height="7">
    </tr>
    </table></td>
  </tr>
</form>
</table>
</body>
</html>