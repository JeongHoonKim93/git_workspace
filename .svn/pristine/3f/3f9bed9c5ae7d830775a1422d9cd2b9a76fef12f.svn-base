<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@include file="../../inc/sessioncheck.jsp" %>
<%@ page import="risk.admin.classification.classificationMgr,
				 risk.admin.classification.clfBean,
				 risk.util.DateUtil,
                 risk.util.ParseRequest,
                 risk.issue.IssueMgr,
                 risk.issue.IssueCodeMgr,
                 risk.issue.IssueCodeBean,
                 java.util.List,
                 java.util.ArrayList" 
%>
<%
		
 DateUtil        du = new DateUtil();
 ParseRequest    pr = new ParseRequest(request);
 
// pr.printParams();
 
 classificationMgr cm = new classificationMgr();
 List cmList = null;
 
 ArrayList arrICB = new ArrayList();
 IssueCodeMgr icm;// = new IssueCodeMgr();
 String typename[];
 
 String delFlag = "Y";
	
	int itype = pr.getInt("type",0);
	int icode = pr.getInt("code",0);
	int iptype = pr.getInt("ptype",0);
	int ipcode = pr.getInt("pcode",0);
	int level = pr.getInt("level",0);
	int ic_seq = pr.getInt("ic_seq",0);
	
	System.out.println("itype = "+itype);
	System.out.println("icode = "+icode);
	System.out.println("iptype = "+iptype);
	System.out.println("ipcode = "+ipcode);
	System.out.println("level = "+level);
	System.out.println("ic_seq = "+ic_seq);
	
	
	//코드 값을 가져와 상단 타이틀을 작성한다.
	 
	icm = new IssueCodeMgr();	
	icm.init(0);
	
	arrICB = icm.GetType(itype);
	IssueCodeBean ICBean = (IssueCodeBean) arrICB.get(0);
	
	typename = icm.getIssueCodeName(itype,icode).split(",");
	System.out.println(typename[0]);
	

	if( itype > 0 ) {
		cmList = cm.GetDetailList_test( itype, icode);
	}
	
	// 하위 분류를 추가할수 있는 항목인지 체크 한다.
	if(level < 100){
	//if( cm.GetSubClf( itype, icode) > 0 ) {
 
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../basic.css" type="text/css">
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script type="text/javascript">

	var allcheck = 0;
	
	function addClf()
	{
		if( frm_clf.itype.value > 0 ) {
			if( frm_clf.clf_name.value ) {
				frm_clf.mode.value = 'add';
				frm_clf.action = 'classification_test_prc.jsp';
				frm_clf.target = '';
				frm_clf.submit();
			}else {
				alert('분류명을 입력하세요');
			}
		}else {
			alert('분류를 선택하세요');
		}
		
	}
	
	function allselect()
	{
		var frm = document.frm_clf;
		if( frm.icSeq ) {
			if( allcheck == 0 ) {
				if( frm.icSeq.length > 1 ) {
					for( i=0; i< frm.icSeq.length; i++ )
			   		{
			   			 frm.icSeq[i].checked = true;
			   		}
			   	}else {
			   		frm.icSeq.checked = true;
			   	}
		   		allcheck = 1;
		   	}else {
		   		if( frm.icSeq.length > 1 ) {
			   		for( i=0; i< frm.icSeq.length; i++ )
			   		{
			   			 frm.icSeq[i].checked = false;
			   		}
			   	}else {
			   		frm.icSeq.checked = false;
			   	}
		   		allcheck = 0;
		   	}
		}
	}
	
	function delList()
	{
		var tmp_list = '';
    	var i = 0;
    	
    	var frm = document.frm_clf;
    	
    	if ( confirm("삭제 하시겠습니까?" ) ) {
    	
    		if( frm.icSeq ) {
	    		if( frm.icSeq.length > 1 ) {
		    		for( i=0; i< frm.icSeq.length; i++ )
		    		{
		    			if( frm.icSeq[i].checked == true ) {
		    				if( tmp_list.length > 0 ) {
		    					tmp_list = tmp_list+','+frm.icSeq[i].value;
		    				}else {
		    					tmp_list = frm.icSeq[i].value;
		    				}
		    			}
		    		}
		    	}else {
		    		if( frm.icSeq.checked == true ) {
		    			tmp_list = frm.icSeq.value;
		    		}
		    	}
			}
		    
		    if( tmp_list.length > 0 ) {
		    	frm.icSeqList.value = tmp_list;
		    	frm.mode.value = 'del';
		    	frm.target = '';
		    	frm.action = 'classification_test_prc.jsp';
		    	frm.submit();
		    }else {
		   		alert('분류 항목을 선택 하세요');
		   	}
		}
	}

	function up_code()
	{
		if( frm_clf.icode.value == 0 ) {
			alert('위치변경은 속성만 가능합니다.');
		} else if( !frm_clf.icode.value ){
			alert('대상을 선택하십시요.');
		} else {
			frm_clf.mode.value = 'up';
			frm_clf.target = '';
			frm_clf.action = 'classification_test_prc.jsp';
			frm_clf.submit();
		}
	}

	function down_code()
	{
		if( frm_clf.icode.value == 0 ) {
			alert('위치변경은 속성만 가능합니다.');
		} else if( !frm_clf.icode.value ){
			alert('대상을 선택하십시요.');
		} else {
			frm_clf.mode.value = 'down';
			frm_clf.target = '';
			frm_clf.action = 'classification_test_prc.jsp';
			frm_clf.submit();
		}
	}

	function showModify(ic_seq, ic_name){

		frm_clf.ic_seq.value = ic_seq; 
		frm_clf.ic_name.value = ic_name;
		
		
		popup.openByPost('frm_clf','pop_classification_test_modify.jsp',400,150,false,false,false,'trendPop');
	}

</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form id="frm_clf" name="frm_clf" action="" method="post" onsubmit="return false;">
<input type="hidden" name="itype" value="<%=itype%>">
<input type="hidden" name="icode" value="<%=icode%>">
<input type="hidden" name="iptype" value="<%=iptype%>">
<input type="hidden" name="ipcode" value="<%=ipcode%>">
<input type="hidden" name="level" value="<%=level%>">
<input type="hidden" name="ic_seq" value="<%=ic_seq%>">
<input type="hidden" name="icSeqList" value="">
<input type="hidden" name="mode" value="">
<input type="hidden" name="ic_name">

<table width="380" height="100%" border="0" cellspacing="0" cellpadding="0">


	<tr>
		<td align="center" bgcolor="#F8F8F8">
		<table width="350" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="35" style="padding: 5px 0px 0px 0px;"><img src="../../../images/admin/classification/ico_arrow05.gif" width="16" height="14" align="absmiddle"><strong><%=typename[0]%></strong> - 분류체계</td>
			</tr>
		<%
		if(level <= 5){%>			  						
			<tr>
				<td background="../../../images/admin/classification/report_line01.gif"><img src="../../../images/admin/classification/brank.gif" width="1" height="2"></td>
			</tr>
			<tr>
				<td height="35">
				<table width="345" border="0" cellspacing="0" cellpadding="0">
				  <tr>
					<td style="padding: 3px 0px 0px 0px;"><img src="../../../images/admin/classification/ico_arrow06.gif" width="11" height="9" align="absmiddle">분류항목관리 : </td>
					<td align="center"><input name="clf_name" type="text" class="txtbox" size="29" OnKeyDown="Javascript:if (event.keyCode == 13) {addClf();}"></td>
					<td align="right"><img src="../../../images/admin/classification/btn_add.gif" id="addbtn" width="57" height="19" align="absmiddle" onclick="addClf();" style="cursor:hand;"></td>
				  </tr>
				</table></td>
		  </tr>
		<%				  
		}
		%>		  		  
		  <tr>
			<td background="../../../images/admin/classification/report_line01.gif"><img src="../../../images/admin/classification/brank.gif" width="1" height="2"></td>
		  </tr>
		  <tr>
			<td height="30"><img src="../../../images/admin/classification/btn_del02.gif" width="57" height="19" onclick="delList();" style="cursor:hand;"></td>
		  </tr>
		  <tr>
			<td bgcolor="#C6C6C6">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
			  <tr>
				<td width="72" height="25" align="center" background="../../../images/admin/classification/report_tbg01.gif" style="padding: 4px 0px 0px 0px;"><input type="checkbox" name="allCheck" value="checkbox" onclick="allselect();"></td>
				<td width="278" align="center" background="../../../images/admin/classification/report_tbg01.gif" style="padding: 4px 0px 0px 0px;"><strong>분류항목</strong></td>
			  </tr>
			  <tr>
				<td colspan="2" bgcolor="#FFFFFF">
				<div name="aa" style="width:346;height:120;overflow-y:auto">
				<table width="326" border="0" cellpadding="0" cellspacing="0">
				<%
				if( cmList != null ) {
					for( int i=0; i<cmList.size(); i++ ) {
						clfBean clfb = (clfBean)cmList.get(i);
						%>
				  <tr>
					<td width="76" height="25" align="center" style="padding: 3px 0px 0px 0px;"><input type="checkbox" name="icSeq" value="<%=clfb.getIc_seq()%>"></td>
					<td width="200" style="padding: 3px 0px 0px 10px;" background="../../../images/admin/classification/report_tline01.gif"><%=clfb.getIc_name()%></td>
					<td width="60" style="padding: 3px 0px 0px 10px;" background="../../../images/admin/classification/report_tline01.gif"><img src="../../../images/admin/classification/btn_modify_001.gif" style="cursor: pointer;" onclick="showModify('<%=clfb.getIc_seq()%>','<%=clfb.getIc_name()%>');"></td>
				  </tr>
				  <tr>
					<td colspan="3" bgcolor="#E5E5E5"><img src="../../../images/admin/classification/brank.gif" width="1" height="1"></td>
				  </tr>
						<%
					}
				}
				%>
				</table>
				</div></td>
			  </tr>
			</table></td>
	  </tr>
	  <tr>
		<td height="36"><img src="../../../images/admin/classification/btn_del02.gif" width="57" height="19" onclick="delList();" style="cursor:hand;"></td>
	  </tr>
	  <%
	  if(level > 5){%>			  
	  <tr height="35">
	  </tr>
	  <%}%>	  	  
	  <tr>
		<td height="20">&nbsp;</td>
	  </tr>
	  </table>
	   </td>
	</tr>
	</table>
</form>

</body>
</html>
<%
	}else {
		String msg_str = "";
		if( itype == 0 && icode == 0 ) {
			msg_str = "분류를 선택하세요";
		}else {
			msg_str = "하위 분류항목이 없습니다.";
		}
		%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../css/basic.css" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--
	function up_code()
	{
		if( frm_clf.icode.value == 0 ) {
			alert('위치변경은 속성만 가능합니다.');
		} else if( !frm_clf.icode.value ){
			alert('대상을 선택하십시요.');
		} else {
			frm_clf.mode.value = 'up';
			frm_clf.target = '';
			frm_clf.action = 'classification_prc.jsp';
			frm_clf.submit();
		}
	}
	
	function down_code()
	{
		if( frm_clf.icode.value == 0 ) {
			alert('위치변경은 속성만 가능합니다.');
		} else if( !frm_clf.icode.value ){
			alert('대상을 선택하십시요.');
		} else {
			frm_clf.mode.value = 'down';
			frm_clf.target = '';
			frm_clf.action = 'classification_prc.jsp';
			frm_clf.submit();
		}
	}
-->
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="frm_clf" action="" method="post" onsubmit="return false;">
<input type="hidden" name="itype" value="<%=itype%>">
<input type="hidden" name="icode" value="<%=icode%>">
<input type="hidden" name="icSeqList" value="">
<input type="hidden" name="mode" value="">
<table width="380" height="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td align="center" bgcolor="#F8F8F8">
		<table width="350" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="35" style="padding: 5px 0px 0px 0px;"><img src="../../../images/admin/classification/ico_arrow05.gif" width="16" height="14" align="absmiddle"><strong><%=msg_str%></strong></td>
			</tr>
			<tr>
				<td background="../../../images/admin/classification/report_line01.gif"><img src="../../../images/admin/classification/brank.gif" width="1" height="2"></td>
			</tr>
			<tr>
				<td height="273">&nbsp;</td>
			</tr>
	  	</table>
	   </td>
	</tr>
</table>
</form>
</body>
</html>
		<%
	
	}
%>