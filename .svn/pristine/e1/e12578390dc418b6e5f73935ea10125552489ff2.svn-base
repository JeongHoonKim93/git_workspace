<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList
				,risk.admin.relation.RelationMgr
				,risk.util.ParseRequest,java.util.HashMap,java.net.*"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	RelationMgr rMgr = new RelationMgr();
	String mode = pr.getString("mode", "");				//ins:추가 , mod:수정
	String rk_seq = pr.getString("rk_seq", "");			//연관키워드 단일
	String rk_name = pr.getString("rk_name", "");		//연관키워드 단일
	String rk_seqs= pr.getString("rk_seqs", "");		//연관키워드 복수
	String rk_names = pr.getString("rk_names", "");		//연관키워드 복수
	
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<style type="text/css">
.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}

</style>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script language="javascript">
</script>
</head>
<body>
<form id="editForm" name="editForm" method="post">
 
 <table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td id="pop_head">
			<p>제외키워드수정</p>
			<span><a href="javascript:close();"><img src="../../../images/admin/aekeyword/pop_tit_close.gif"></span>
		</div>		</td>
	</tr>
	<tr>
		<td>
		<!-- 게시판 시작 -->
		<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
			<tr>
			  <td style="height:30px;"><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#E0E0E0">
                <tr>
                  <td bgcolor="#F6F6F6" style="padding:10px 10px 10px 10px"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="20"><img src="../../../images/admin/aekeyword/arrow.gif" width="6" height="7"><span class="blue_text"><strong>제외키워드 : </strong></span></td>
                    </tr>
                    <tr>
                      <td height="20"><img src="../../../images/admin/aekeyword/arrow.gif" width="6" height="7">등록자 : </td>
                    </tr>
                    <tr>
                      <td height="20"><img src="../../../images/admin/aekeyword/arrow.gif" width="6" height="7">입력일자 : </td>
                    </tr>
                  </table></td>
                </tr>
              </table></td>
		  </tr>
			<tr>
			  <td height="5"></td>
		  </tr>
			<tr>
				<td style="height:40px;"><span class="sub_tit">제외 허용 키워드 추가</span></td>
			</tr>
			<tr>
				<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="47%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="../../../images/admin/aekeyword/admin_ico03.gif" width="10" height="11"> 전체 키워드 </td>
                      </tr>
                      <tr>
                        <td height="5"></td>
                      </tr>
                      <tr>
                        <td>
                        	<div id="div_kl">
                        	</div>
                        </td>
                      </tr>
                    </table></td>
                    <td width="6%" align="center"><img src="../../../images/admin/aekeyword/btn_left.gif" style="cursor: pointer;" onclick="updateKeyword('keyword','del');"><br>
                    <img src="../../../images/admin/aekeyword/btn_right.gif" width="18" height="18" vspace="5" style="cursor: pointer;" onclick="updateKeyword('keyword','add');"></td>
                    <td width="47%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="../../../images/admin/aekeyword/admin_ico03.gif" width="10" height="11"> 제외 허용 키워드 </td>
                      </tr>
                      <tr>
                        <td height="5"></td>
                      </tr>
                      <tr>
                        <td>
                        	<div id="div_kr">
                        	</div>
                        </td>
                      </tr>
                    </table></td>
                  </tr>
                </table></td>
			</tr>
			<tr>
				<td style="padding-top:15px;height:55px;"><span class="sub_tit">제외 허용 사이트 추가</span></td>
			</tr>
			<tr>
				<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="47%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img src="../../../images/admin/aekeyword/admin_ico03.gif" width="10" height="11"> 전체 사이트</td>
                        </tr>
                        <tr>
                          <td height="5"></td>
                        </tr>
                        <tr>
                          <td>
                          	<div id="div_sl">
                        	</div>
                          </td>
                        </tr>
                    </table></td>
                    <td width="6%" align="center"><img src="../../../images/admin/aekeyword/btn_left.gif" width="18" height="18" style="cursor: pointer;" onclick="updateKeyword('site','del');"><br>
                        <img src="../../../images/admin/aekeyword/btn_right.gif" width="18" height="18" vspace="5" style="cursor: pointer;" onclick="updateKeyword('site','add');"></td>
                    <td width="47%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td><img src="../../../images/admin/aekeyword/admin_ico03.gif" width="10" height="11"> 제외 허용 사이트</td>
                        </tr>
                        <tr>
                          <td height="5"></td>
                        </tr>
                        <tr>
                          <td>
                          	<div id="div_sr">
                        	</div>
                          </td>
                        </tr>
                    </table></td>
                  </tr>
                </table></td>
			</tr>
		</table>
		<!-- 게시판 끝 -->		</td>
	</tr>
</table>
</form>
<iframe name="processFrm" height="0" border="0" style="display: none;"></iframe>
</body>
</html>