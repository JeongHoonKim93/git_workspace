
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList
				,risk.util.ParseRequest
				,risk.util.DateUtil
				,risk.util.StringUtil
				,java.net.URLDecoder
				,risk.issue.IssueExcel
				"%>
<%@include file="../inc/sessioncheck.jsp"%>


<%
		ParseRequest pr = new ParseRequest(request);	
		pr.printParams();
		
		ConfigUtil cu = new ConfigUtil(); 
		DateUtil du = new DateUtil();
		StringUtil su = new StringUtil();
		IssueExcel exc = new IssueExcel();
		
		String sDateFrom =  pr.getString("sDateFrom");
		String sDateTo =  pr.getString("sDateTo");
		String ir_stime =  pr.getString("ir_stime");
		String ir_etime =  pr.getString("ir_etime");
		
		String ra_same = pr.getString("ra_same");
		
		int diffCnt = pr.getInt("diffCnt");
		int idx = pr.getInt("idx");
		
		
		String wordType = pr.getString("wordType");
		String selTypeName = pr.getString("selTypeName"); 
		
		if(selTypeName.equals("")){
			selTypeName = "D0,번호";	
		}else{
			selTypeName = "D0,번호@" + selTypeName;
		}
		
		String searchKey = URLDecoder.decode(pr.getString("encodingSearchKey",""),"UTF-8");
		String i_seq = pr.getString("i_seq","");
		String it_seq = pr.getString("it_seq","");
		String typeCode = pr.getString("typeCode");
		String typeCode_Etc = pr.getString("typeCode_Etc");
		String relationKey = pr.getString("relKeyword");
		String register = pr.getString("register");
		String m_seq = pr.getString("m_seq",SS_M_NO);
		String check_no = pr.getString("check_no","");
		String language = pr.getString("language","");
		String keyType = pr.getString("keyType");
		//스트림에서 2차월배열로 변환
		String[][] full_typeName = su.split2(selTypeName, "@", ",");
		String[][] arr_typeCode = su.split2(typeCode, "@", ",");
		String[][] arr_typeCodeDetail = su.split2(typeCode_Etc, "@", ",");
		
		String filePath = cu.getConfig("FILEPATH");
		String excelPath = filePath + "excelData/issueData/" + SS_M_NO + "/";
		String fileName = pr.getString("fileName", "issueExcel_" + du.getDate("yyyy") + du.getDate("MMdd") + du.getDate("HHmmss") + "_0_" + ".xlsx");
		
		String setSDate = du.addDay_v2(sDateFrom, idx - 1);
		String setEDate = du.addDay_v2(sDateFrom, idx - 1);
		String setStime = "";
		String setEtime = "";

		if(idx == 1){
			setStime = ir_stime + ":00:00";
		}else{
			setStime = "00:00:00";
		}
		if(idx == diffCnt){
			if(ir_etime.equals("24")){
				setEtime = "23:59:59";	
			}else{
				setEtime = ir_etime + ":59:59";
			}
		}else{
			setEtime = "23:59:59";
		}
		
		if(ra_same.equals("2")){
			setSDate =  sDateFrom;
			setEDate = sDateTo;
			idx = 1;
			diffCnt = 1;
		}
		
		//데이터 가져오기~
		ArrayList arData = new ArrayList();
		if(wordType.equals("A")){
			arData = exc.getExcelData_A(setSDate, setEDate, setStime, setEtime, full_typeName, searchKey, keyType, relationKey, register, check_no, arr_typeCode, arr_typeCodeDetail, ra_same);	
		}else if(wordType.equals("B")){
			arData = exc.getExcelData_B(setSDate, setEDate, setStime, setEtime, full_typeName, searchKey, keyType, relationKey, register, check_no, arr_typeCode, arr_typeCodeDetail, ra_same);
		}
	    
		String[] arFileName = fileName.split("_");
		String reFileName = arFileName[0] + "_" + arFileName[1] + "_" + idx + "_" + arFileName[3];
		exc.addExcel_v4(excelPath, reFileName, arData);
		
		//마지막에 엑셀 합치기
		if(idx == diffCnt){
			//ArrayList fullData = exc.SumExcel_v4(excelPath, fileName, diffCnt);
			
			arFileName = fileName.split("_");
			reFileName = arFileName[0] + "_" + arFileName[1] + "_0_" + arFileName[3];
			boolean result = exc.CreateExcel_v4(excelPath, reFileName, full_typeName, diffCnt);

		}
		
		
		out.println("Y|" + reFileName );
		
%>

