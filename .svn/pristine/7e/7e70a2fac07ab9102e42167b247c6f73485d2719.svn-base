<%@page import="java.util.HashMap"%>
<%@page import="risk.dashboard.main.MainMgr"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.Calendar" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.util.*"%>
<%
	MainMgr  mgr = new MainMgr();
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	DateUtil du = new DateUtil();
	Calendar c = Calendar.getInstance();

	String info_sDate = "";						//정보추이 그래프 관련 시작일
	String info_eDate = "";						//정보추이 그래프 관련 종료일
	String sDate = pr.getString("sDate");		//시작일
	String eDate = pr.getString("eDate");		//종료일
	
	String department = pr.getString("department");
	String company_code = pr.getString("company_code");
	String title = pr.getString("subject");
	
	String type = pr.getString("type");					//집계할 코드의 타입
	String ptype = pr.getString("ptype");	
	String type2 = pr.getString("type2");	
	String mode = pr.getString("mode");
	String date = pr.getString("date");
	String code = pr.getString("code");
	String pcode = pr.getString("pcode");
	String md_seq = pr.getString("md_seq");
	String sg_seqs = pr.getString("sg_seqs");
	String keyword_type = pr.getString("keyword_type");
	String search_keyword = pr.getString("search_keyword");
	String issue_code = pr.getString("issue_code");
	String product_code = pr.getString("product_code", "");

	int rowLimit = pr.getInt("rowLimit");

	
	String fileName = "HYCARD_MAIN_DATA_"+du.getDate("yyyyMMdd_HHmmss")+".xlsx";
	ConfigUtil cu = new ConfigUtil();
	String URL = cu.getConfig("URL");
	String  filePath = cu.getConfig("EXCELPATH");
	
	//전월 일자
	int interval = (int)du.DateDiff("yyyy-MM-dd",eDate, sDate )+1;
	du.setDate(sDate);
	du.addDay( - interval);
	String psDate = du.getDate("yyyy-MM-dd");
	
	du.setDate(eDate);
	du.addDay( - interval);
	String peDate = du.getDate("yyyy-MM-dd");

	
	String[] sheetNameArr = null;
	String[] titleArray = null;
	HashMap<Integer, ArrayList> dataMap = new HashMap<Integer, ArrayList>(); 
	
	POIExcelAdd poiAdd = new POIExcelAdd();			
	
	ArrayList excelList = new ArrayList();
	String[] titleArr = null;
	ArrayList<String[]> total_title = new ArrayList<String[]>() ;
	String subject ="";
	String dayDiff = mgr.getDayDiff(sDate, eDate);
	
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
	
	c.setFirstDayOfWeek(Calendar.SUNDAY);
	String[] dates = eDate.split("-");
	
	int year = Integer.parseInt(dates[0]);
	int month = Integer.parseInt(dates[1])-1;
	int day = Integer.parseInt(dates[2]);
	
	c.set(year, month - 1, day);
	c.set(Calendar.YEAR,year);
	c.set(Calendar.MONTH,month);
	c.set(Calendar.WEEK_OF_MONTH,c.get(Calendar.WEEK_OF_MONTH));
	c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
	
 	if(formatter.format(c.getTime()).compareTo(eDate) < 0){
		c.add(c.DATE,7);
	}
 	
	if(mode.equals("weekly")){		
		info_eDate = eDate;
		du.setDate(info_eDate);
		du.addDay( - 6);
		info_sDate = du.getDate("yyyy-MM-dd");
	} else if(mode.equals("monthly")){
		info_eDate = formatter.format(c.getTime());
		du.setDate(info_eDate);
		du.addDay( - 48);
		info_sDate = du.getDate("yyyy-MM-dd");		
	}

	if("0".equals(dayDiff) && !mode.equals("weekly") && !mode.equals("monthly")){
		dayDiff="1";
	}else if(mode.equals("weekly") || mode.equals("monthly")){
		dayDiff="7";	
	}
	
//	if("0".equals(dayDiff)){dayDiff="1";}
	
	int idx = 0;
	//전체 현황
	if("navi_01".equals(mode)){
		idx = 0;
		if(company_code.equals("1")){
			sheetNameArr = new String []{"상품&서비스 TOP5","영업 TOP5","디지털 TOP5","Operation TOP5", "금소법 TOP5", "발급_한도 TOP5", "FRAUD TOP5", "기타 TOP5"};
		}else if(company_code.equals("2")){
			sheetNameArr = new String []{"상품&서비스 TOP5","영업 TOP5","디지털 TOP5","Operation TOP5", "금소법 TOP5", "한도 TOP5", "기타 TOP5"};
		}else if(company_code.equals("3")){
			sheetNameArr = new String []{"상품&서비스 TOP5","영업 TOP5","디지털 TOP5","Operation TOP5", "금소법 TOP5", "발급_한도 TOP5", "기타 TOP5"};			
		}
	 	
		//상품&서비스 TOP5
		titleArr = new String[]{"제목","출처","대응등급","감성","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.get_DailyVOC_TopList_excel(company_code, "1", sDate , eDate, sg_seqs, rowLimit, keyword_type, search_keyword, titleArr.length);
		dataMap.put(idx, excelList);
		idx++;

 		//영업 TOP5
		titleArr = new String[]{"제목","출처","대응등급","감성","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.get_DailyVOC_TopList_excel(company_code, "2", sDate , eDate, sg_seqs, rowLimit, keyword_type, search_keyword, titleArr.length);
		dataMap.put(idx, excelList);
		idx++;

		//디지털 TOP5
		titleArr = new String[]{"제목","출처","대응등급","감성","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.get_DailyVOC_TopList_excel(company_code, "3", sDate , eDate, sg_seqs, rowLimit, keyword_type, search_keyword, titleArr.length);
		dataMap.put(idx, excelList);
		idx++;

		//Operation TOP5
		titleArr = new String[]{"제목","출처","대응등급","감성","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.get_DailyVOC_TopList_excel(company_code, "4", sDate , eDate, sg_seqs, rowLimit, keyword_type, search_keyword, titleArr.length);
		dataMap.put(idx, excelList);
		idx++;

		//금소법 TOP5
		titleArr = new String[]{"제목","출처","대응등급","감성","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.get_DailyVOC_TopList_excel(company_code, "5", sDate , eDate, sg_seqs, rowLimit, keyword_type, search_keyword, titleArr.length);
		dataMap.put(idx, excelList);
		idx++;

		//발급한도 TOP5
		titleArr = new String[]{"제목","출처","대응등급","감성","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.get_DailyVOC_TopList_excel(company_code, "7", sDate , eDate, sg_seqs, rowLimit, keyword_type, search_keyword, titleArr.length);
		dataMap.put(idx, excelList);
		idx++;
		
		if(company_code.equals("1")){
			//FRAUD TOP5
			titleArr = new String[]{"제목","출처","대응등급","감성","일시"};
			total_title.add(idx, titleArr);
			excelList = mgr.get_DailyVOC_TopList_excel(company_code, "8", sDate , eDate, sg_seqs, rowLimit, keyword_type, search_keyword, titleArr.length);
			dataMap.put(idx, excelList);
			idx++;
		}
		
		//기타 TOP5
		titleArr = new String[]{"제목","출처","대응등급","감성","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.get_DailyVOC_TopList_excel(company_code, "6", sDate , eDate, sg_seqs, rowLimit, keyword_type, search_keyword, titleArr.length);
		dataMap.put(idx, excelList);
 		 	
		subject = title +" ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		//poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);

	}   else if("navi_02".equals(mode)){
		
		sheetNameArr = new String []{"연관어 분석"};
		
		//연관어 분석
		titleArr = new String[]{"순위","키워드","정보량"};
		total_title.add(idx, titleArr);
		excelList = mgr.get_RelationKeywordList_excel("1", company_code, sDate, eDate, sg_seqs, rowLimit, keyword_type, search_keyword, titleArr.length);
				
		dataMap.put(idx, excelList);	

		subject = title +"   ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
		
	}   else if("weekly".equals(mode) || "monthly".equals(mode)){
		
		sheetNameArr = new String []{"정보 추이"};
		
		//정보 추이
		titleArr = new String[]{"일시","정보량","부정률"};
		total_title.add(idx, titleArr);
		excelList = mgr.get_InformationGraph_excel(mode, info_sDate, info_eDate, company_code, sg_seqs, dayDiff, keyword_type, search_keyword, titleArr.length);			
		dataMap.put(idx, excelList);	

		subject = title +"   ["+info_sDate+"~"+info_eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
		
	}   else if("navi_04".equals(mode)){
		idx = 0;
		sheetNameArr = new String []{"주제별 현황 - 항목별", "주제별 현황 - 성향별"};
		
		//주제별 현황 - 항목별
		titleArr = new String[]{"항목","건수","비중(%)"};
		total_title.add(idx, titleArr);
		excelList = mgr.get_TitleCnt_excel(sDate, eDate, company_code, sg_seqs, keyword_type, search_keyword, titleArr.length);
		dataMap.put(idx, excelList);
		idx++;

		//주제별 현황 - 감성별
		titleArr = new String[]{"항목","긍정 건수","부정 건수","중립 건수"};
		total_title.add(idx, titleArr);
		excelList = mgr.get_SentiCnt_excel(sDate, eDate, company_code, sg_seqs, keyword_type, search_keyword, titleArr.length);
		dataMap.put(idx, excelList);
		 
		subject = title +" ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		//poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
		
	}   else if("navi_05".equals(mode)){
		idx = 0;
		sheetNameArr = new String []{"VOC 속성정보 - 유형별", "VOC 속성정보 - 성향별"};
		
		//VOC 속성정보
		titleArr = new String[]{"항목","기사 건수","클레임 건수","칭찬/제안 건수","후기 건수","문의 건수","단순언급 건수","기타 건수","전체 건수"};
		total_title.add(idx, titleArr);
		excelList = mgr.get_VOCInformationGraph_excel(sDate, eDate, company_code, product_code, sg_seqs, keyword_type, search_keyword, titleArr.length);			
		dataMap.put(idx, excelList);	
		idx++;

		//VOC 속성정보 - 성향별
		titleArr = new String[]{"항목","긍정 건수","부정 건수","중립 건수","전체 건수"};
		total_title.add(idx, titleArr);
		excelList = mgr.get_VOCInformationSentiGraph_excel(sDate, eDate, company_code, product_code, sg_seqs, keyword_type, search_keyword, titleArr.length);					
		dataMap.put(idx, excelList);

		subject = title +"   ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		//poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
		
	}   else if("navi_06".equals(mode)){
		idx = 0;
		sheetNameArr = new String []{"전체 VOC 리스트 : 전체"};
		
		//VOC 긍정 리스트
		titleArr = new String[]{"제목","출처","주제 유형","제품 유형","성향","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.getSentiList_excel(sDate, eDate, mode, sg_seqs, company_code, keyword_type, search_keyword, titleArr.length);
				
		dataMap.put(idx, excelList);	

		subject = title +"   ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
		
	}	else if("voc_pos".equals(mode)){
		idx = 0;
		sheetNameArr = new String []{"전체 VOC 리스트 : 긍정"};
		
		//VOC 긍정 리스트
		titleArr = new String[]{"제목","출처","주제 유형","제품 유형","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.getSentiList_excel(sDate, eDate, mode, sg_seqs, company_code, keyword_type, search_keyword, titleArr.length);
				
		dataMap.put(idx, excelList);	

		subject = title +"   ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
		
	}   else if("voc_neg".equals(mode)){
		idx = 0;
		sheetNameArr = new String []{"전체 VOC 리스트 : 부정"};
		
		//VOC 부정 리스트
		titleArr = new String[]{"제목","출처","주제 유형","제품 유형","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.getSentiList_excel(sDate, eDate, mode, sg_seqs, company_code, keyword_type, search_keyword, titleArr.length);
				
		dataMap.put(idx, excelList);	

		subject = title +"   ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
		
	}   else if("voc_neu".equals(mode)){
		idx = 0;
		sheetNameArr = new String []{"전체 VOC 리스트 : 부정"};
		
		//VOC 부정 리스트
		titleArr = new String[]{"제목","출처","주제 유형","제품 유형","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.getSentiList_excel(sDate, eDate, mode, sg_seqs, company_code, keyword_type, search_keyword, titleArr.length);
				
		dataMap.put(idx, excelList);	

		subject = title +"   ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
		
	}   else if("navi_07".equals(mode)){
		
		sheetNameArr = new String []{"실시간 포탈 TOP 노출 현황"};
		
		//실시간 포탈 TOP 노출 현황
		titleArr = new String[]{"제목","출처","영역","url","최초노출일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.getPortalDataList_excel(sDate, eDate, company_code, titleArr.length );
		dataMap.put(idx, excelList);	


		subject = title +"   ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
		
	}   else if("navi_08".equals(mode)){
		
		sheetNameArr = new String []{"포탈 뉴스 댓글 현황"};
		
		//포탈 댓글 현황
		titleArr = new String[]{"출처","댓글 총 수량","긍정 댓글 수량","부정 댓글 수량","중립 댓글 수량","제목","url","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.getPortalReplyList_excel(date, company_code, keyword_type, search_keyword, titleArr.length );
		dataMap.put(idx, excelList);	


		subject = title;
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
		
	}   else if("senti_count".equals(mode)){
		
		//기사 댓글 분석
		//상황별 점유율
		titleArr = new String[]{"감성", "수량"};
		total_title.add(idx, titleArr);
		excelList = mgr.getTopic_PortalReplyPer_excel(md_seq, titleArr.length );
		dataMap.put(idx, excelList);	


		subject = title;
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
		
	}	else if("rel_cloud".equals(mode)){
		
		//기사 댓글 분석
		//연관어 클라우드
		titleArr = new String[]{"일련번호","연관어","건수","감성"};
		total_title.add(idx, titleArr);
		excelList = mgr.getRelatedKeywordList_excel(md_seq, titleArr.length );
		dataMap.put(idx, excelList);	


		subject = title;
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
		
	}   else if("issue_info".equals(mode)){
		
		sheetNameArr = new String []{"주요 이슈 - 정보추이"};
		
		//주요 이슈 - 정보추이
		titleArr = new String[]{"일시","언론 건수","블로그 건수","카페 건수","커뮤니티 건수","트위터 건수","페이스북 건수","유튜브 건수","총 건수"};
		total_title.add(idx, titleArr);
		excelList = mgr.get_IssueInformationGraph_excel(sDate, eDate, company_code, issue_code, dayDiff, keyword_type, search_keyword, titleArr.length);
		dataMap.put(idx, excelList);	


		subject = title +"   ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
		
	}   else if("rel_info".equals(mode)){
		
		sheetNameArr = new String []{"주요 이슈 - 관련정보"};
		
		//주요 이슈 - 관련정보
		titleArr = new String[]{"제목","출처","확산건수","url","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.getIssueDataList_excel(sDate, eDate, company_code, issue_code, sg_seqs, keyword_type, search_keyword, titleArr.length);
		dataMap.put(idx, excelList);	


		subject = title +"   ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
		//sheet 여러개일 경우
		//poiAdd.sheet_Excel2(filePath, fileName, sheetNameArr, subject, total_title, dataMap);
		
	}
	
	String realPath = URL+"excel_file/"+fileName;
	out.print(realPath);
	
	
%>	