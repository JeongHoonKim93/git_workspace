<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList
				,risk.util.ParseRequest
				,risk.util.DateUtil
				,risk.util.StringUtil
				,java.net.URLDecoder
				, risk.statistics.StatisticsTwitterMgr
				, risk.statistics.StatisticsTwitterSuperBean
				"%>
				
<%@include file="../../inc/sessioncheck.jsp"%>
<%
	DateUtil   du = new DateUtil();
	ParseRequest  pr = new ParseRequest(request);	
	StringUtil su = new StringUtil();
	ConfigUtil cu = new ConfigUtil();
	
	String mode = pr.getString("mode");
	String user_id = pr.getString("user_id");
	String user_name = URLDecoder.decode(pr.getString("user_name"),"UTF-8");
	String profile_url = pr.getString("profile_url");
	String idx = pr.getString("idx");
	
	pr.printParams();

	
	StatisticsTwitterSuperBean.ImportTwitterBean tBean = null;
	if(mode.equals("update")){
		StatisticsTwitterMgr sMgr = new StatisticsTwitterMgr();
		tBean = sMgr.getInterest(user_id);
	}
	
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>POSCO</title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<style type="text/css">
<!--
.style1 {color: #FFFFFF}
-->
</style>
<script language="javascript">
<!--

	function save(){
		var f = document.fSend;
		f.target = 'processFrm';
		f.action = 'statistics_twitter_prc.jsp';
		f.submit();
	}	 


//-->
</script>
</head>
<body>
<form name="fSend" id="fSend" method="post" >
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="user_id" value="<%=user_id%>">
<input type="hidden" name="user_name" value="<%=su.toHtmlString(user_name)%>">
<input type="hidden" name="profile_url" value="<%=profile_url%>">
<input type="hidden" name="idx" value="<%=idx%>">
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td id="pop_head">
			<p>관심 트위터리안 등록</p>
			<span><a href="javascript:close();"><img src="../../../images/statistics/pop_tit_close.gif"></span>
		</div>
		</td>
	</tr>
	<tr>
		<td>
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
			
			<tr>
				<td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#E4E4E4">
                  <tr>
                    <td  rowspan="3" align="center" bgcolor="#999999"><span class="style1"><img src='<%=profile_url%>' width="120" height="120"></span></td>
                        <td width="152" height="36" bgcolor="#F7F7F7"><span class="board_write_tit"><strong>트위터 ID</strong></span></td>
                    <td bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=user_id%></td>
                  </tr>

                  <tr>
                    <td width="152" height="36" bgcolor="#F7F7F7"><span class="board_write_tit"><strong>트위터 명</strong></span></td>
                    <td bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=user_name%></td>
                  </tr>
                  <tr>
                    <td width="152" height="36" bgcolor="#F7F7F7"><span class="board_write_tit"><strong>직업 </strong></span></td>
                    <td bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><input name="input_job" type="text" class="textbox2" style="width:150px;" value="<%if(mode.equals("update")){out.print(tBean.getT_job());}%>"></td>
                  </tr>
                  <tr>
                        <td width="152" height="36" bgcolor="#F7F7F7"><span class="board_write_tit"><strong>E-mail</strong></span></td>
                    <td colspan="2" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><input type="text" name="input_mail" class="textbox2" style="width:250px;" value="<%if(mode.equals("update")){out.print(tBean.getT_mail());}%>"></td>
                  </tr>
                  <tr>
                   <td width="152" height="36" bgcolor="#F7F7F7"><span class="board_write_tit"><strong>관심도</strong></span></td>
                    <td colspan="2" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px">
                    <%
                    	
                    	String S ="";
                    	String L ="";
                    	String M ="";
                    	
                    	if(mode.equals("update")){
                    		if(tBean.getT_import().equals("1")){
                    			S = "checked";
                    		}else if(tBean.getT_import().equals("2")){
                    			L = "checked";
                    		}else if(tBean.getT_import().equals("3")){
                    			M = "checked";
                    		}
                    	}else{
                    		S ="checked";
                    	}
                    %>
                    
                    
                      <input type="radio" name="input_import" <%=S%> value="1">상
                      <input type="radio" name="input_import" <%=L%> value="2">중
                      <input type="radio" name="input_import" <%=M%> value="3">하
                    </td>
                  </tr>
                  <tr>
                   <td width="152" height="36" bgcolor="#F7F7F7"><span class="board_write_tit"><strong>비고</strong></span></td>
                    <td colspan="2" bgcolor="#FFFFFF" style="padding:5px 5px 5px 5px"><textarea name="memo" cols="45" rows="5" class="input_text02" id="memo" ><%if(mode.equals("update")){out.print(tBean.getT_memo());}%></textarea></td>
                  </tr>
                  
                </table></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="text-align:center;"><img src="../../../images/statistics/btn_save2.gif" onclick="save();" style="cursor: pointer;"><img src="../../../images/statistics/btn_cancel.gif" style="cursor: pointer;" onclick="window.close();"></td>
	</tr>
</table>
</form>
<iframe id="processFrm" name ="processFrm" width="0" height="0" style="display: none;" ></iframe>
</body>
</html>