<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="risk.util.*
				 ,risk.search.*           
                 "
%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	String m_seq = pr.getString("m_seq");
	String md_seq = pr.getString("md_seq");
	String mode = pr.getString("mode");
	String subMode = pr.getString("subMode","");
	
	//삭제 전용
	String md_seqs = pr.getString("md_seqs");
	
	MetaMgr mgr = new MetaMgr();
	DomainKeywordMgr dkmgr = new DomainKeywordMgr();
	
	if(mode.equals("insert")){
		if(subMode.equals("domain")) {
			dkmgr.insertComfirm(m_seq, md_seq);	
		} else {
			mgr.insertComfirm(m_seq, md_seq);	
		}
	
	
	
%>
<script language="JavaScript" type="text/JavaScript">
var obj = parent.document.getElementById("list<%=md_seq%>");
obj.color = '0000CC';
</script>
<%
	}else{
		String html = "";
		String result = "";
		if(subMode.equals("domain")) {
			result = dkmgr.deleteComfirm(m_seq, md_seqs);
		} else {
			result = mgr.deleteComfirm(m_seq, md_seqs);	
		}
		
		String[] arrMdSeq = md_seqs.split(",");
		for(int i =0; i < arrMdSeq.length; i++){
			if(result.indexOf(arrMdSeq[i]) >= 0){
				//html += "var obj"+i+" = parent.document.getElementById('list"+arrMdSeq[i]+"'); obj"+i+".color = '977d46'; \n";
				html += "var obj"+i+" = parent.document.getElementById('list"+arrMdSeq[i]+"'); obj"+i+".color = ''; \n";
			}else{
				html += "var obj"+i+" = parent.document.getElementById('list"+arrMdSeq[i]+"'); obj"+i+".color = ''; \n";
			}
		}
		
		html += "var objChk = parent.document.getElementsByName('chkData'); for(var i =0; i < objChk.length; i++){ objChk[i].checked = false;} \n";
		System.out.println(html);	 	
 %>
 <script language="JavaScript" type="text/JavaScript">
 <%=html%>
 </script>
 <%		
	}
%>