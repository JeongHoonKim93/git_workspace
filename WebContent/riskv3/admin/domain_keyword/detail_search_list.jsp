<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,java.util.ArrayList
                 ,java.util.HashMap
                 ,java.util.Map
                 ,java.text.DecimalFormat
                 ,risk.util.PageIndex
                 ,risk.util.StringUtil
                 ,risk.admin.domain_keyword.DomainKeywordMng
                 ,risk.admin.domain_keyword.DomainKeywordBean
                 ,risk.json.JSONArray
                 ,risk.json.JSONObject
                 ,java.util.Iterator
                 "
%>

<%
	ParseRequest pr = new ParseRequest(request);
	DateUtil du = new DateUtil();
	StringUtil	su 	= new StringUtil();
	DecimalFormat formatter = new DecimalFormat("###,###");
	
	JSONObject jsonObj = null;
	JSONObject jsonObj2 = null;
	JSONArray jsonArr = null;
	
	ArrayList arSgSeq = null;
	String detail_type = pr.getString("detail_type","1");
	String lan = pr.getString("lan","");
	int group = pr.getInt("group",0);
	boolean use_yn = false;	
	
	DomainKeywordMng wMgr = new DomainKeywordMng();
	if(detail_type.equals("1")) {
		//채널명 리스트 조회
		jsonObj = wMgr.getSgSeq(detail_type, use_yn);
	} else if(detail_type.equals("2")) {
		use_yn = true;
		jsonObj = wMgr.getSite(detail_type, lan, group);
		jsonObj2 = wMgr.getSgSeq(detail_type, use_yn);
	}

%>

<%out.println(jsonObj + "/@/" + jsonObj2); %>

