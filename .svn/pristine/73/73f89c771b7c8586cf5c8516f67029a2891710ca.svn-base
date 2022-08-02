<%@page import="risk.issue.IssueExcel"%>
<%@page import="risk.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.BufferedOutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.util.*"%>
<%@page import="risk.util.ConfigUtil
				,risk.util.ParseRequest
				,risk.issue.IssueMgr
				"
				%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);	
	pr.printParams();
	ConfigUtil cu = new ConfigUtil();
	StringUtil su = new StringUtil();
	String fileName = pr.getString("fileName");
	String excelPath = cu.getConfig("FILEPATH") + "excelData/issueData/" + SS_M_NO + "/";
	int diffCnt = pr.getInt("diffCnt");
	
	System.out.println(excelPath + fileName);
	File file = new File(excelPath + fileName);		 
	
	String viewFileName = "";
	StringTokenizer st = new  StringTokenizer(fileName.trim(),"/");
	if(st.countTokens() >=2){
		st.nextToken(); 
		viewFileName = st.nextToken();											
	}else{
		viewFileName = fileName.trim();
	}
	
	viewFileName = viewFileName.replaceAll(";", "");
	
	response.setHeader("Content-Type", "application/octet-stream; charset=euc-kr");
	response.setHeader("Content-Disposition", "attachment;filename=" +new String(viewFileName.getBytes("euc-kr"),"ISO8859_1"));
	//response.setHeader("Content-Disposition", "attachment;filename=" +viewFileName);
	
	
	response.setHeader("Content-Transfer-Encoding", "binary");
	BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
	
	//아웃스트림에러나서 2줄 추가 ~
	out.clear();
	out = pageContext.pushBody();
	
	BufferedOutputStream os = new BufferedOutputStream(response.getOutputStream());
	
	int read = 0;
	while((read = in.read()) != -1) {
		os.write(read);
	}
	
	in.close();
	os.close();
	
	new IssueExcel().DelExcelFile(excelPath, fileName, diffCnt);
%>