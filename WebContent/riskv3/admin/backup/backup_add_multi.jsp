<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
                 risk.util.ParseRequest,
                 risk.admin.backup.BackupListBean,
                 risk.admin.backup.BackupListMgr" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	String bl_seq = pr.getString("bl_seq");
	String bl_tbName = pr.getString("bl_tbName");
	String mode = pr.getString("mode");
	//String checkIds = pr.getString("checkIds");
	
	/*
	//데이터베이스명 조회
	BackupListMgr bm = new BackupListMgr();
	String dbname = null;
	dbname = bm.getDatabaseName();
	*/
%>

<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<script type="text/javascript" src="../../../axisj/jquery/jquery.min.js"></script>
<style>
<!--	
	.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}

-->
	</style>
<script language="javascript">

	function multi_updateBackupList()
	{
		var f = document.editForm2;
		f.mode.value = 'multi';
		if(f.bl_del_useYn.value == 'Y' && f.bl_day_term.value == '') 
		{
			alert('보관 일수를 입력해주세요.'); 
			return;	
		} else if(f.bl_op.value == '2' && f.bl_del_useYn.value == 'Y' && f.del_date_field_name.value == '') 
		{
			alert('수집일자 필드를 입력해주세요.'); 
			return;
		} else if(f.bl_op.value == '2' && f.ins_field_name.value == '') 
		{
			alert('증감기준 필드를 입력해주세요.'); 
			return;
		} else if(f.bl_op.value == '2' && f.bl_del_useYn.value == 'Y' && f.del_date_field_type.value == '') 
		{
			alert('수집일자 타입을 입력해주세요.'); 
			return;
		}else {
			//앞뒤 공백 제거
			f.bl_seq.value = f.bl_seq.value;
			f.bl_tbName.value = f.bl_tbName.value;
			f.bl_op.value = f.bl_op.value;
			f.bl_useYn.value = f.bl_useYn.value;
			f.bl_del_useYn.value = f.bl_del_useYn.value;
			f.bl_day_term.value = f.bl_day_term.value;	
			f.ins_field_name.value = f.ins_field_name.value;
			f.del_date_field_name.value = f.del_date_field_name.value;
			f.del_date_field_type.value = f.del_date_field_type.value;
		}	
		f.target='';
		f.action='backup_prc.jsp';
		f.submit();
	}	
	
	var eles = document.getElementsByClassName("showOption");
	var showField = document.getElementsByClassName("aboutOption");
	var showField2 = document.getElementsByClassName("aboutDel");
		
	function showOption(v) {
		if(v == "Y") {
			for (var i = 0; i < eles.length; i++) {
				eles[i].style.display="";
			}
		} else {			
			for (var i = 0; i < eles.length; i++) {
				eles[i].style.display="none";
			}
			for (var i = 0; i < showField.length; i++) {			
				showField[i].style.display="none";
			}
			for (var i = 0; i < showField2.length; i++) {			
				showField2[i].style.display="none";
			}
		}
	}
	
	function aboutOption(v) {
		if(v == "2") {
			for (var i = 0; i < showField.length; i++) {			
				showField[i].style.display="";
			}
		} else {
			for (var i = 0; i < showField.length; i++) {			
				showField[i].style.display="none";
			}
		}
	}
	
	function aboutDel(v) {
		var check_opt = document.getElementsByName("bl_op");
		var opt = '';
		for (var j = 0; j < check_opt.length; j++) {
			if(check_opt[j].checked == true) {
				opt = check_opt[j].value;
			}
		}
		
		if(opt == "2" && v == "Y") {
			for (var i = 0; i < showField2.length; i++) {			
				showField2[i].style.display="";
			}
		} else if(opt == "1" && v == "Y") {
			for (var i = 0; i < showField2.length; i++) {			
				showField2[0].style.display="";
				showField2[1].style.display="none";
			}
		} else {
			for (var i = 0; i < showField2.length; i++) {			
				showField2[i].style.display="none";
			}
		}	
	}
	
	function showList() {
		var op_chk = '';

		$("input[name=bl_op]").click(function() {
			$('input[name="bl_del_useYn"]').removeAttr('checked');
			
			for (var i = 0; i < showField2.length; i++) {
				showField2[i].style.display = "none";
			}
		})
		
		
		$("input[name=bl_useYn]:checked").each(function(){
			if($(this).val() == "Y" || $(this).val() == "N") {
				for (var i = 0; i < eles.length; i++) {
					eles[i].style.display="";
				}
			} 
     	});
		
		$("input[name=bl_op]:checked").each(function(){
			op_chk = $(this).val();
			if($(this).val() == "2") {
				for (var i = 0; i < showField.length; i++) {
					showField[i].style.display = "";
				}	
			} else {
				for (var i = 0; i < showField.length; i++) {
					showField[i].style.display = "none";
				}
			}
     	});
		$("input[name=bl_del_useYn]:checked").each(function(){
			if($(this).val() == "Y" && op_chk == "1") {
				for (var i = 0; i < showField2.length; i++) {
					showField2[0].style.display="";
					showField2[1].style.display="none";
				}	
			} else if($(this).val() == "Y" && op_chk == "2") {
				for (var i = 0; i < showField2.length; i++) {			
					showField2[i].style.display="";
				}
			} else {
				for (var i = 0; i < showField2.length; i++) {
					showField2[i].style.display = "none";
				}
			}
     	});
	}
	
	
</script>
</head>
<body onload="showList();">

<form name="editForm2" method="post" onsubmit="return false;">
<input type="hidden" name="mode">
<input type="hidden" name="bl_seq" value="<%=bl_seq%>">
<input type="hidden" name="bl_tbName" value="<%=bl_tbName %>">

<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td colspan="3" id="pop_head">
			<p>백업테이블 수정</p>
			<span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span>
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>테이블명 </strong></td>
			<td width="331" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="dbName" size="25" value="<%=bl_tbName%>" readonly></td>
		</tr>
		<tr>
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>백업 여부 </strong></td>
			<td width="331" align="left" style="padding: 3px 0px 0px 0px;">
				<input type="radio" name="bl_useYn" id="operate" value="Y" <%if(pr.getString("bl_useYn").equals("")) { %>onclick="showOption(this.value);"<%}%>>사용 &nbsp;&nbsp;&nbsp;
				<input type="radio" name="bl_useYn" id="stop" value="N" <%if(pr.getString("bl_useYn").equals("")) { %>onclick="showOption(this.value);"<%}%>>중지
			</td>
		</tr> 
		
		<tr class="showOption" style="display:none">
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>옵션 </strong></td>
			<td width="331" align="left" style="padding: 3px 0px 0px 0px;">
				<input type="radio" name="bl_op" id="bl_op1" value="1" onclick="aboutOption(this.value);">전체 &nbsp;&nbsp;&nbsp;
				<input type="radio" name="bl_op" id="bl_op2" value="2" onclick="aboutOption(this.value);">증감
			</td>
		</tr> 
		<tr class="aboutOption" style="display:none">
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray" ><strong>증감기준 필드 </strong></td>
			<td width="331" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="ins_field_name" size="25"></td>
		</tr>
		<tr class="showOption" id="deleteOpt" style="display:none">
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>데이터 삭제 </strong></td>
			<td width="331" align="left" style="padding: 3px 0px 0px 0px;">
				<input type="radio" name="bl_del_useYn" value="Y" onclick="aboutDel(this.value);">사용 &nbsp;&nbsp;&nbsp;
				<input type="radio" name="bl_del_useYn" value="N" onclick="aboutDel(this.value);">중지
			</td>
		</tr>
		<tr class="aboutDel" style="display:none">
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>보관 일수 </strong></td>
			<td width="331" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="bl_day_term" size="25"></td>
		</tr>
		<tr class="aboutDel" style="display:none">
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>수집일자 필드 </strong></td>
			<td width="331" align="left" style="padding: 3px 0px 0px 0px;"><input type="text" name="del_date_field_name" size="25"></td>
		</tr>
		<tr class="aboutDel" style="display:none">
			<td width="120" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>수집일자 타입 </strong></td>
			<td width="331" align="left" style="padding: 3px 0px 0px 0px;">
				<input type="radio" name="del_date_field_type" id="del_date_field_type" value="1">TIMESTAMP &nbsp;
				<input type="radio" name="del_date_field_type" id="del_date_field_type" value="2">DATETIME
			</td>
		</tr>
	</table></td>
	</tr>
</table>
<table width="378" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right">
		<!-- <img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="updateBackupList();" style="cursor:pointer;"> -->
		<img src="../../../images/admin/member/btn_save2.gif"  hspace="5" onclick="multi_updateBackupList();" style="cursor:pointer;">
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td>
	</tr>
</table>
</form>
</body>
</html>