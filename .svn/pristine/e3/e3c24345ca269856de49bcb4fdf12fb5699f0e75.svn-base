<%@page import="java.util.HashMap"%>
<%@page import="risk.dashboard.popup.PopupMgr"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.Calendar" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="risk.util.*"%>
<%
	PopupMgr  mgr = new PopupMgr();
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	DateUtil du = new DateUtil();
	Calendar c = Calendar.getInstance();
	
	String info_sDate = "";						//정보추이 그래프 관련 시작일
	String info_eDate = "";						//정보추이 그래프 관련 종료일		
	String sDate = pr.getString("sDate");		//시작일
	String eDate = pr.getString("eDate");		//종료일
	String target_date = pr.getString("target_date","");
	
	String department = pr.getString("department");
	String company_code = pr.getString("company_code");
	String title = pr.getString("subject");
	
	title = title.replace("_", "&");
	
	String type = pr.getString("type");					//집계할 코드의 타입
	String mode = pr.getString("mode");
	String date = pr.getString("date");
	String code = pr.getString("code");
	String md_seq = pr.getString("md_seq");
	String sg_seqs = pr.getString("sg_seqs");
	String keyword_type = pr.getString("keyword_type","");
	String search_keyword = pr.getString("search_keyword","");
	String issue_code = pr.getString("issue_code");

	int rowLimit = pr.getInt("rowLimit");
	int nowPage = pr.getInt("nowPage", 1);
	int rowCnt = pr.getInt("rowCnt", 10);
	
	String senti = pr.getString("senti","");
	String search = pr.getString("search","");
	String select_sg_seq = pr.getString("select_sg_seq", "");
	
	String topic_type = "";
	String topic_code = pr.getString("topic_code","");
	String TypeCode = pr.getString("TypeCode");
	String rk_seq = pr.getString("rk_seq");
	String pat_seq = pr.getString("pat_seq","");
	String senti_option = pr.getString("senti_option","");
	String senti_option_voc = pr.getString("senti_option_voc","");
	String text_value = pr.getString("text_value","");
	String text_value_voc = pr.getString("text_value_voc","");
	String action_only = pr.getString("action_only","");
	if("voc_list".equals(mode)){
		
		if(company_code.equals("1")){
			topic_type = "12";
		} else if(company_code.equals("2")){
			topic_type = "22";
		} else if(company_code.equals("3")){
			topic_type = "32";
		} 
		
		TypeCode = topic_type + ":" + topic_code;
	}

	//String으로 온 파라미터 변환, Type Code 
	ArrayList<String[]> typeList = new ArrayList<String[]>();
	if(!"".equals(TypeCode)){
		String[] tmp_typeCode = null;
		String[] tmp_typeCode2 = null;
		//type code 여러개 일겨우
		if(TypeCode.contains("@")){			
			tmp_typeCode = TypeCode.split("@");
			for(int i=0; i<tmp_typeCode.length; i++){
				tmp_typeCode2 = null;
				if(tmp_typeCode[i].contains(":")){
					tmp_typeCode2 = tmp_typeCode[i].split(":");
					if(tmp_typeCode2.length>1){
						typeList.add(tmp_typeCode2);		
					}else{
						tmp_typeCode2[1] ="";
						typeList.add(tmp_typeCode2);
					}
							
				}
			}
		//type, code 하나일 경우
		}else{
			if(TypeCode.contains(":")){
				tmp_typeCode2 = TypeCode.split(":");
				
				if(tmp_typeCode2.length>1){
					typeList.add(tmp_typeCode2);		
				}else{
					tmp_typeCode2[1] ="";
					typeList.add(tmp_typeCode2);
				}
			}
		}
		
	}
	
	String fileName = "HYCARD_POPUP_DATA_"+du.getDate("yyyyMMdd_HHmmss")+".xlsx";
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

	
	if(mode.equals("weekly_cnt")){		
		info_eDate = eDate;
		du.setDate(info_eDate);
		du.addDay( - 6);
		info_sDate = du.getDate("yyyy-MM-dd");
	} else if(mode.equals("monthly_cnt")){
		info_eDate = formatter.format(c.getTime());
		du.setDate(info_eDate);
		du.addDay( - 48);
		info_sDate = du.getDate("yyyy-MM-dd");		
	}
	
	String[] sheetNameArr = null;
	String[] titleArray = null;
	HashMap<Integer, ArrayList> dataMap = new HashMap<Integer, ArrayList>(); 
	
	POIExcelAdd poiAdd = new POIExcelAdd();			
	
	ArrayList excelList = new ArrayList();
	String[] titleArr = null;
	ArrayList<String[]> total_title = new ArrayList<String[]>() ;
	String subject ="";
	String dayDiff = mgr.getDayDiff(sDate, eDate);
	
/* 	if(Integer.parseInt(dayDiff) < 7){		
		du.setDate(eDate);
		du.addDay( - 6);
		info_sDate = du.getDate("yyyy-MM-dd");
	}
 */	
	if("0".equals(dayDiff)){dayDiff="1";}
	
	int idx = 0;
	//전체 현황
	
	if("portal_reply".equals(mode)){
		
		sheetNameArr = new String []{"관련정보"};
		
		//관련정보 팝업창
		titleArr = new String[]{"제목","출처","감성","url","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.getPortalReplyPopDataList_excel(nowPage, rowCnt, senti, search, md_seq, target_date, senti_option, text_value, titleArr.length);
		dataMap.put(idx, excelList);	

		subject = title +"   ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
	}else if("relatedKey_reply".equals(mode)){
		
		sheetNameArr = new String []{"관련정보"};
		
		//관련정보 팝업창
		titleArr = new String[]{"제목","출처","감성","url","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.getRelatedKeywordReplyPopDataList_excel(nowPage, rowCnt, search, md_seq, pat_seq, senti_option, text_value, titleArr.length);
		dataMap.put(idx, excelList);	

		subject = title +"   ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);	
	}else if("relatedKey_reply_total".equals(mode)){

		sheetNameArr = new String []{"관련정보"};
		
		//관련정보 팝업창
		titleArr = new String[]{"제목","출처","감성","url","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.getRelatedKeywordReplyPopDataList_total_excel(nowPage, rowCnt, search, md_seq, pat_seq, senti_option, text_value, titleArr.length);
		dataMap.put(idx, excelList);	
		
		subject = title +"   ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
	}else if(!"".equals(rk_seq)){

		sheetNameArr = new String []{"관련정보"};
		
		//관련정보 팝업창
		titleArr = new String[]{"제목","출처","감성","url","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.getRelKeyPopDataList_excel(nowPage, rowCnt, target_date, sDate, eDate, company_code, typeList, select_sg_seq, sg_seqs, rk_seq, keyword_type, search_keyword, senti_option, text_value, titleArr.length);
		dataMap.put(idx, excelList);	
		
		subject = title +"   ["+sDate+"~"+eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);
	}else if("voc_list".equals(mode)){
		
		sheetNameArr = new String []{"관련정보"};
		
		//관련정보 팝업창
		titleArr = new String[]{"제목","출처","감성","대응내역", "대응여부","진행상황","url","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.get_Popuplist_excel(nowPage, rowCnt, search, target_date, sDate, eDate, company_code, typeList, select_sg_seq, sg_seqs, keyword_type, search_keyword, senti_option_voc, text_value_voc, action_only, mode, titleArr.length);
		dataMap.put(idx, excelList);	

		subject = title +"   ["+sDate+"~"+eDate+"]";   
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);	
	}else if(mode.equals("weekly_cnt") || mode.equals("monthly_cnt")){
		
		sheetNameArr = new String []{"관련정보"};
		
		//관련정보 팝업창
		titleArr = new String[]{"제목","출처","감성","url","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.get_Popuplist_excel(nowPage, rowCnt, search, target_date, info_sDate, info_eDate, company_code, typeList, select_sg_seq, sg_seqs, keyword_type, search_keyword, senti_option, text_value, action_only, mode, titleArr.length);
		dataMap.put(idx, excelList);	

		subject = title +"   ["+info_sDate+"~"+info_eDate+"]";
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);	
	}else{
		
		sheetNameArr = new String []{"관련정보"};
		
		//관련정보 팝업창
		titleArr = new String[]{"제목","출처","감성","url","일시"};
		total_title.add(idx, titleArr);
		excelList = mgr.get_Popuplist_excel(nowPage, rowCnt, search, target_date, sDate, eDate, company_code, typeList, select_sg_seq, sg_seqs, keyword_type, search_keyword, senti_option, text_value, action_only, mode,titleArr.length);
		dataMap.put(idx, excelList);	
		if(!target_date.equals("")){
			subject = title +"   ["+target_date+"~"+target_date+"]";
		}else {
			subject = title +"   ["+sDate+"~"+eDate+"]";			
		}
		//sheet 한개일 경우
		poiAdd.addExcel(filePath, fileName, subject, titleArr, excelList);	
	}
		
	String realPath = URL+"excel_file/"+fileName;
	out.print(realPath);
	
	
%>	