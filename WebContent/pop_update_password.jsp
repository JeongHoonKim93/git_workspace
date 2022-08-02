<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
                 risk.util.ParseRequest,
                 risk.admin.member.MemberDao" 
%>
<%
	ParseRequest pr = new ParseRequest(request);
	MemberDao mdao = new MemberDao();	
	pr.printParams();
	
	String m_seq = (String)session.getAttribute("SS_M_NO");

	String[] result = null;
	result = mdao.checkPasswordUpdateDate(m_seq).split("/");
	
%>

<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<script type="text/javascript" src="../../../axisj/jquery/jquery.min.js"></script>
<style>
	
	.t {  font-family: "Tahoma"; font-size: 11px; color: #666666}
	input::placeholder {color: #A4A4A4}

</style>
<script language="javascript">

	var str = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'; // 문자열만을 포함하는 변수
	var chr = '1234567890'; // 숫자를 포함하는 변수
	var schr = '!@#$%^&*()-_=+'; // 특수문자열을 포함하는 변수
	var str1 = 'qwertyuiop';
	var str2 = 'asdfghjkl';
	var str3 = 'zxcvbnm';

	function updatePw()
	{
		var f = document.editForm2;
		var id = document.all.m_id.value;
		var pwkey = document.all.present_pwkey.value;
		var pw = document.all.present_pw.value;
		var pw1 = document.all.update_pw.value;
		var pw2 = document.all.update_pw_check.value;
		
		if(f.present_pw.value=='')
		{	
			alert('현재 비밀번호를 입력해주세요.'); 
			return false;
		} else if(f.update_pw.value == '') 
		{
			alert('새 비밀번호를 입력해주세요.'); 
			return false;	
		} else if(f.update_pw_check.value == '') 
		{
			alert('새 비밀번호를 확인해주세요.'); 
			return false;
		}

		// 패스워드 반복입력 체크
 		if(pw1!=pw2) {
		alert('새 비밀번호를 똑같이 입력해주세요.');
		return false;
		}
		
		/*
		// 기존 패스워드 동일한지 체크
 		if(pwkey!=SHA256(pw)) {
		alert('현재 비밀번호를 다시 입력해주세요.');
		return false;
		}
		*/
		
 		// 기존 패스워드 동일한지 체크
		if(pwkey == 'rsn1234!') {
			if(pwkey!=pw) {
				alert('현재 비밀번호를 다시 입력해주세요.');
				return false;
			}
		} else {
			if(pwkey!=SHA256(pw)) {
				alert('현재 비밀번호를 다시 입력해주세요.');
				return false;
			}
		}
		
		// 기존 패스워드와 새 패스워드가 다른지 체크
		if(pwkey==pw1 || pwkey==pw2) {
		alert('현재 비밀번호와 다른 새 비밀번호를 입력해주세요.');
		return false;
		}

		// 패스워드 입력 체크
  		if(pw1.length<8 || pw2.length<8) {
			alert('비밀번호는 최소 8자리 이상 입력해주세요.');
			return false;
		}
		
		// id와 패스워드 동일하면 false
		if(id==pw1) {
		alert('아이디와 비밀번호는 다르게 설정해주세요.');
		return false;		
		} 
		
		for(var i=0; i<id.length-3; i++){
			for(var x=0; x<pw1.length-3; x++) {
				if(id.charAt(i)==pw1.charAt(x) && id.charAt(i+1)==pw1.charAt(x+1) && id.charAt(i+2)==pw1.charAt(x+2) && id.charAt(i+3)==pw1.charAt(x+3)) {
					alert('비밀번호는 아이디의 연속된 4글자를 포함할 수 없습니다.');
					return false;							
				}
			}
		} 
		for(var i=0; i<pw1.length-2; i++){
			for(var x=0; x<str1.length-2; x++) {
				if(pw1.charAt(i)==str1.charAt(x) && pw1.charAt(i+1)==str1.charAt(x+1) && pw1.charAt(i+2)==str1.charAt(x+2)) {
					alert('3자리 이상의 연속된 키보드 자판으로 비밀번호를 설정하실 수 없습니다.');
					return false;							
				}
			}
			for(var y=0; y<str2.length-2; y++) {
				if(pw1.charAt(i)==str2.charAt(y) && pw1.charAt(i+1)==str2.charAt(y+1) && pw1.charAt(i+2)==str2.charAt(y+2)) {
					alert('3자리 이상의 연속된 키보드 자판으로 비밀번호를 설정하실 수 없습니다.');
					return false;							
				}
			}
			for(var z=0; z<str3.length-2; z++) {
				if(pw1.charAt(i)==str3.charAt(z) && pw1.charAt(i+1)==str3.charAt(z+1) && pw1.charAt(i+2)==str3.charAt(z+2)) {
					alert('3자리 이상의 연속된 키보드 자판으로 비밀번호를 설정하실 수 없습니다.');
					return false;							
				}
			}		
			for(var k=0; k<chr.length-2; k++) {
				if(pw1.charAt(i)==chr.charAt(k) && pw1.charAt(i+1)==chr.charAt(k+1) && pw1.charAt(i+2)==chr.charAt(k+2)) {
					alert('3자리 이상의 연속된 키보드 자판으로 비밀번호를 설정하실 수 없습니다.');
					return false;							
				}
			}			
		}

		var res_1 = 0; var res_2 = 0; var res_3 = 0; // 조건1과 조건2와 조건3의 결과를 저장할 변수
	
		// 조건1: 같은 문자열이 연속으로 3개 이상일 경우 제한합니다.
  		for(var i=0; i<=pw1.length-3; i++) {
			if(pw1.charAt(i)==pw1.charAt(i+1) && pw1.charAt(i)==pw1.charAt(i+2)) {
				alert('같은 문자열을 3개 이상 연속으로 사용할 수 없습니다.');
				return false;
			}
		}
  	
		// 조건2: 문자는 최소 2개 이상이며, 숫자 또는 특수문자가 1개 이상 포함되어야 합니다.
  		for(var x=0; x<pw1.length; x++) {
			for(var y=0; y<str.length; y++) {
				if(pw1.charAt(x)==str.charAt(y)) res_1++;
			}
			for(var z=0; z<chr.length; z++) {
				if(pw1.charAt(x)==chr.charAt(z)) res_2++;
			}
			for(var k=0; k<schr.length; k++) {
				if(pw1.charAt(x)==schr.charAt(k)) res_3++;
			}
		}
		
 		if(res_1 == 0 || res_2 == 0 || res_3 == 0){
			alert('비밀번호는 영문자,숫자,특수문자 혼용으로 설정해야 합니다.');
			return false;
		}
 		
		//앞뒤 공백 제거
		f.present_pw.value = f.present_pw.value;
		f.update_pw.value = f.update_pw.value;
		f.update_pw_check.value = f.update_pw_check.value;
		f.mode.value = 'update';	
		
		f.target='';
		f.action='pop_update_password_prc.jsp';
		f.submit();
	}	
	
	function dalayUpdate() {
		var f = document.editForm2;
		f.mode.value = 'delay';
		
		f.target='';
		f.action='pop_update_password_prc.jsp';
		f.submit();
	}

	//SHA256 암호화
	/**
	*
	*  Secure Hash Algorithm (SHA256)
	*  http://www.webtoolkit.info/
	*
	*  Original code by Angel Marin, Paul Johnston.
	*
	**/
	function SHA256(s){

	    var chrsz   = 8;
	    var hexcase = 0;

	    function safe_add (x, y) {
	        var lsw = (x & 0xFFFF) + (y & 0xFFFF);
	        var msw = (x >> 16) + (y >> 16) + (lsw >> 16);

	        return (msw << 16) | (lsw & 0xFFFF);
	    }

	    function S (X, n) { return ( X >>> n ) | (X << (32 - n)); }

	    function R (X, n) { return ( X >>> n ); }

	    function Ch(x, y, z) { return ((x & y) ^ ((~x) & z)); }

	    function Maj(x, y, z) { return ((x & y) ^ (x & z) ^ (y & z)); }

	    function Sigma0256(x) { return (S(x, 2) ^ S(x, 13) ^ S(x, 22)); }

	    function Sigma1256(x) { return (S(x, 6) ^ S(x, 11) ^ S(x, 25)); }

	    function Gamma0256(x) { return (S(x, 7) ^ S(x, 18) ^ R(x, 3)); }

	    function Gamma1256(x) { return (S(x, 17) ^ S(x, 19) ^ R(x, 10)); }

	    function core_sha256 (m, l) {
	        var K = new Array(0x428A2F98, 0x71374491, 0xB5C0FBCF, 0xE9B5DBA5, 0x3956C25B, 0x59F111F1, 0x923F82A4, 0xAB1C5ED5, 0xD807AA98, 0x12835B01, 0x243185BE, 0x550C7DC3, 0x72BE5D74, 0x80DEB1FE, 0x9BDC06A7, 0xC19BF174, 0xE49B69C1, 0xEFBE4786, 0xFC19DC6, 0x240CA1CC, 0x2DE92C6F, 0x4A7484AA, 0x5CB0A9DC, 0x76F988DA, 0x983E5152, 0xA831C66D, 0xB00327C8, 0xBF597FC7, 0xC6E00BF3, 0xD5A79147, 0x6CA6351, 0x14292967, 0x27B70A85, 0x2E1B2138, 0x4D2C6DFC, 0x53380D13, 0x650A7354, 0x766A0ABB, 0x81C2C92E, 0x92722C85, 0xA2BFE8A1, 0xA81A664B, 0xC24B8B70, 0xC76C51A3, 0xD192E819, 0xD6990624, 0xF40E3585, 0x106AA070, 0x19A4C116, 0x1E376C08, 0x2748774C, 0x34B0BCB5, 0x391C0CB3, 0x4ED8AA4A, 0x5B9CCA4F, 0x682E6FF3, 0x748F82EE, 0x78A5636F, 0x84C87814, 0x8CC70208, 0x90BEFFFA, 0xA4506CEB, 0xBEF9A3F7, 0xC67178F2);
	        var HASH = new Array(0x6A09E667, 0xBB67AE85, 0x3C6EF372, 0xA54FF53A, 0x510E527F, 0x9B05688C, 0x1F83D9AB, 0x5BE0CD19);
	        var W = new Array(64);
	        var a, b, c, d, e, f, g, h, i, j;
	        var T1, T2;

	        m[l >> 5] |= 0x80 << (24 - l % 32);
	        m[((l + 64 >> 9) << 4) + 15] = l;

	        for ( var i = 0; i<m.length; i+=16 ) {
	            a = HASH[0];
	            b = HASH[1];
	            c = HASH[2];
	            d = HASH[3];
	            e = HASH[4];
	            f = HASH[5];
	            g = HASH[6];
	            h = HASH[7];

	            for ( var j = 0; j<64; j++) {
	                if (j < 16) W[j] = m[j + i];
	                else W[j] = safe_add(safe_add(safe_add(Gamma1256(W[j - 2]), W[j - 7]), Gamma0256(W[j - 15])), W[j - 16]);

	                T1 = safe_add(safe_add(safe_add(safe_add(h, Sigma1256(e)), Ch(e, f, g)), K[j]), W[j]);
	                T2 = safe_add(Sigma0256(a), Maj(a, b, c));
	                h = g;
	                g = f;
	                f = e;
	                e = safe_add(d, T1);
	                d = c;
	                c = b;
	                b = a;
	                a = safe_add(T1, T2);
	            }

	            HASH[0] = safe_add(a, HASH[0]);
	            HASH[1] = safe_add(b, HASH[1]);
	            HASH[2] = safe_add(c, HASH[2]);
	            HASH[3] = safe_add(d, HASH[3]);
	            HASH[4] = safe_add(e, HASH[4]);
	            HASH[5] = safe_add(f, HASH[5]);
	            HASH[6] = safe_add(g, HASH[6]);
	            HASH[7] = safe_add(h, HASH[7]);
	        }
	        return HASH;
	    }

	    function str2binb (str) {
	        var bin = Array();
	        var mask = (1 << chrsz) - 1;

	        for(var i = 0; i < str.length * chrsz; i += chrsz) {
	            bin[i>>5] |= (str.charCodeAt(i / chrsz) & mask) << (24 - i%32);
	        }
	        return bin;
	    }

	    function Utf8Encode(string) {
	        string = string.replace(/\r\n/g,"\n");
	        var utftext = "";

	        for (var n = 0; n < string.length; n++) {
	            var c = string.charCodeAt(n);

	            if (c < 128) {
	                utftext += String.fromCharCode(c);
	            }
	            else if((c > 127) && (c < 2048)) {
	                utftext += String.fromCharCode((c >> 6) | 192);
	                utftext += String.fromCharCode((c & 63) | 128);
	            }
	            else {
	                utftext += String.fromCharCode((c >> 12) | 224);
	                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
	                utftext += String.fromCharCode((c & 63) | 128);
	            }
	        }
	        return utftext;
	    }

	    function binb2hex (binarray) {

	        var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
	        var str = "";

	        for(var i = 0; i < binarray.length * 4; i++) {
	            str += hex_tab.charAt((binarray[i>>2] >> ((3 - i%4)*8+4)) & 0xF) +
	            hex_tab.charAt((binarray[i>>2] >> ((3 - i%4)*8  )) & 0xF);
	        }
	        return str;
	    }
	    s = Utf8Encode(s);
	    return binb2hex(core_sha256(str2binb(s), s.length * chrsz));
		}
</script>
</head>
<body>
<form name="editForm2" method="post" onsubmit="return false;">
<input type="hidden" name="mode" id="mode">
<input name="present_pwkey" id="present_pwkey" type="hidden" value="<%=result[0]%>">
<input name="m_id" id="m_id" type="hidden" value="<%=result[2]%>">
<table style="width:100%;" border="0" cellpadding="0" cellspacing="10">
	<tr>
		<td colspan="3" id="pop_head">
			<p>비밀번호 변경</p>
			<!-- <span><a href="javascript:close();"><img src="../../../images/search/pop_tit_close.gif"></a></span> -->
			<!-- <span><img src="../../../images/search/pop_tit_close.gif" onclick="dalayUpdate();"></span> -->
		</td>
	</tr>
</table>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding-left:10px"><table width="370" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="160" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>현재 비밀번호 </strong></td>
			<td width="291" align="left" style="padding: 3px 0px 0px 0px;"><input type="password" name="present_pw" size="35" placeholder="현재 비밀번호를 입력하세요." autofocus></td>
		</tr>
		
		<tr>
			<td width="160" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>새 비밀번호 </strong></td>
			<td width="291" align="left" style="padding: 3px 0px 0px 0px;"><input type="password" name="update_pw" size="35" placeholder="새 비밀번호를 입력하세요."></td>
		</tr> 
		
		<tr>
			<td width="160" align="left" style="padding: 3px 0px 0px 0px;" class="menu_gray"><strong>비밀번호 확인 </strong></td>
			<td width="291" align="left" style="padding: 3px 0px 0px 0px;"><input type="password" name="update_pw_check" size="35" placeholder="새 비밀번호를 한번 더 입력하세요."></td>
		</tr> 
	</table></td>
	</tr>
</table>
<%
	if(!result[0].equals("rsn1234!")) {
%>
<div style="padding-left:10px; padding-top:2px;font-family:Tahoma; font-size:11px; color:#666666">* 취소 선택 시 현재 비밀번호의 사용기한이 3개월 연장됩니다.</div>
<%
	}
%>
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="40" align="right" style="padding-right:10px;">
		<img src="../../../images/admin/member/btn_save2.gif"  onclick="updatePw();" style="cursor:pointer;">
		<%
			if(!result[0].equals("rsn1234!")) {
		%>
		<!-- <img src="../../../images/admin/member/btn_cancel.gif" onclick="window.close();" style="cursor:pointer;"></td> -->
		<img src="../../../images/admin/member/btn_cancel.gif" onclick="dalayUpdate();"style="cursor:pointer;"></td>
		<%
			}
		%>
	</tr>
</table>
</form>
</body>
</html>