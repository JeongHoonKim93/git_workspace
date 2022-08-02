<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>    
<%@ page import="	risk.util.ParseRequest
					
					" %>

<%
	try{
	ParseRequest  pr = new ParseRequest(request);
	pr.printParams();
	
	String selectedKey = pr.getString("selectedKey","");
	String selectedKeyName = pr.getString("selectedKeyName","");
	String delXyz = pr.getString("delXyz","");
	String kseqs = pr.getString("kseqs","");
	String selectedKeyBase = pr.getString("selectedKeyBase","");
	
	
	String html = "<span><b>* 선택 키워드</b><br></span>";
	
	String tmpKey[] = null;
	String tmpXyz[] = null;
	String tmpName[] = null;
	String tmpBase[] = null;
	String keyType = "";
	
	String tmpkseq[] = null;
	
	int cnt = 0;
	
	if(selectedKey.length()>0 && selectedKeyName.length()>0){
		tmpKey = selectedKey.split("@");
		tmpName = selectedKeyName.split("@");
		tmpBase = selectedKeyBase.split("@");
		
		tmpkseq = kseqs.split(",");
		int kseqcnt = 0;
		
		selectedKey = "";
		selectedKeyName = "";
		selectedKeyBase = "";
		
		if(tmpKey.length==tmpName.length){
			for(int i=0; i<tmpKey.length; i++){
				//삭제
				if(delXyz.length()>0 && delXyz.equals(tmpKey[i])){
					
				}else{
					cnt++;
					
					keyType = "[키워드]";
						tmpXyz = null;
						tmpXyz = tmpKey[i].split(",");
						if(tmpXyz.length>2){
							if(tmpXyz[1].equals("0")){keyType="[대분류]";}//대분류
							else if(tmpXyz[2].equals("0")){keyType="[소분류]";}//소분류
						}
					
					if(selectedKey.equals("")) selectedKey=tmpKey[i];
					else selectedKey+="@"+tmpKey[i];
					
					if(selectedKeyName.equals("")) selectedKeyName=tmpName[i];
					else selectedKeyName+="@"+tmpName[i];
					
					if(selectedKeyBase.equals("")) selectedKeyBase=tmpBase[i];
					else selectedKeyBase+="@"+tmpBase[i];
					
					html+="<span style=\"width:180px;height:18px\">"+ keyType+" "+tmpName[i]+"</span> <span style=\"width:120px;height:18px\"> *누적초기값:"+tmpBase[i]+" </span> <span style=\"width:50px;height:18px\"> <img style=\"cursor:hand;\" onclick=\"delKey("+tmpKey[i]+", "+ tmpkseq[kseqcnt] +");\" align=\"absmiddle\" src=\"images/admin_del.gif\" ></span><br>";
					kseqcnt++;
				}
			}
			
			
		}
		
	}
	System.out.println(html);
	//System.out.println("selectedKey:"+selectedKey+"/cnt:"+cnt);
	//System.out.println("selectedKeyName:"+selectedKeyName);
%>
<script>
var keyCnt = <%=cnt%>;

parent.document.all.selectedKey.value = '<%=selectedKey%>';
parent.document.all.selectedKeyName.value = '<%=selectedKeyName%>';
parent.document.all.selectedKeyBase.value = '<%=selectedKeyBase%>';
parent.document.all.delXyz.value = '';

parent.document.getElementById('selectKeyword').innerHTML = '<%=html%>';
if(keyCnt>0){	
	parent.document.getElementById('selectKeyTr').style.display='';
}else{
	parent.document.getElementById('selectKeyTr').style.display='none';
}


</script>



<%
	}catch(Exception ex){
		ex.printStackTrace();
	}
%>









      