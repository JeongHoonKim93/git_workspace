<%@page import="risk.report.ReportMgrMain"%>
<%@page import="risk.issue.IssueDataBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="risk.util.ParseRequest" %>
<%@ page import="risk.util.ConfigUtil" %>
<%@ page import="risk.util.DateUtil" %>
<%@ page import="risk.util.StringUtil" %>

<%
	// 생성자
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	ConfigUtil cu = new ConfigUtil();
	DateUtil du = new DateUtil();
	StringUtil su = new StringUtil();
	ReportMgrMain reptMgr = new ReportMgrMain();
	
	//이미지 경로
	String siteUrl = cu.getConfig("URL");
	String imgUrl = siteUrl+"riskv3/report/img/";
	
	//차트 경로
	String filePath = cu.getConfig("CHARTPATH");
	String chartUrl = cu.getConfig("CHARTURL");
	
	//차트 이미지
	String chart1 =  pr.getString("chart1");
	String chart2 =  pr.getString("chart2");
	
	//해당 id_seq
	String id_seqs = pr.getString("id_seqs");

	//보고서 날짜
	String ir_sdate = pr.getString("ir_sdate");
	String ir_edate = pr.getString("ir_edate");
	String ir_stime = pr.getString("ir_stime");
	String ir_etime = pr.getString("ir_etime");
	
	// CJ 보고서는 마지막 시간이 15:59:59 일경우 16시로 표기 하도록 한다.
	// N일 16시 ~ M일 16시 정보량은 N일으로 여긴다.
	int tmp_ir_etime = Integer.parseInt(ir_etime.substring(0, 2)) + 1;
	String display_ir_etime = tmp_ir_etime+":00:00";
	
	int diff = 10;
	int diffDate = diff * 24; // 9일 * 24시간 (순수 기간) 날짜로는 10일
	int diffDate_Month = 180;

	String ten_days_ago = du.addDay_v2(ir_edate, (diff-1)*(-1) -1 );
	String a_month_ago = du.addDay_v2(ir_edate, (diffDate_Month-1)*(-1));
	
	//일일 보고서 날짜 포맷 지정
	String today = du.getCurrentDate("yyyy-MM-dd");	
	String formatSdate = du.getDate(ir_sdate+" "+ir_stime,"yyyy.MM.dd HH시");
	String formatEdate = du.getDate(ir_edate+" "+display_ir_etime,"yyyy.MM.dd HH시");
	
	String category_sortBy = pr.getString("category_sortBy");
	String detail_subject = pr.getString("detail_subject");
	String detail_sortBy = pr.getString("detail_sortBy");
	String realtionkey_sortBy = pr.getString("realtionkey_sortBy");
	
	System.out.println("formatSdate : " + formatSdate);
	System.out.println("formatEdate : " + formatEdate);
	
	//기준 명
	HashMap<String, String> sortName = new HashMap<String, String>();
	sortName.put("1", "수집 건수");
	sortName.put("2", "증감률");
	sortName.put("3", "증감 건수");
	
	//ArrayList<HashMap<String, String>> ch_CntList = reptMgr.getChannelCount(ir_sdate, ir_edate, ir_stime, ir_etime);	//유통 경로별 정보 현황 - 채널별
	//ArrayList<HashMap<String, String>> ch_senti_CntList = reptMgr.getChannelSentiCount(ir_sdate, ir_edate, ir_stime, ir_etime);	//유통 경로별 정보 현황 - 채널별(긍부정)
	
	ArrayList<HashMap<String, String>> own_press_infoList = null;
	ArrayList<HashMap<String, String>> own_social_infoList = null;
	ArrayList<HashMap<String, String>> rival_press_infoList = null;
	
	if(!"".equals(id_seqs)){
		own_press_infoList = reptMgr.getDailyInformationList(id_seqs, "1", "1");	//자사 언론 정보
		own_social_infoList = reptMgr.getDailyInformationList(id_seqs, "1", "2");	//자사 소셜 정보
		rival_press_infoList = reptMgr.getDailyInformationList(id_seqs, "2", "1");	//업계 언론 정보		
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>빙그레 일일보고서</title>

	<script src="./js/jquery-1.12.4.min.js"></script>
	<script src="./js/amcharts.js"></script>
	<script src="./js/serial.js"></script>
	<script src="./js/pie.js"></script>
	<script src="./js/xy.js"></script>
</head>
<body style="margin:0;padding:0;">
    <table border="0" cellpadding="0" cellspacing="0" width="750" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:12px; empty-cells:show;">
        <tr>
            <td style="width: 1000px;">
                <table border="0" cellpadding="0" cellspacing="0" width="750" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">

					<!-- header -->
                    <tr>
                        <td style="height: 48px; background: #fff59b; border-bottom: 1px solid #fff59b; vertical-align: middle;">
							<table border="0" cellpadding="0" cellspacing="0" width="750" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show; vertical-align: middle;">
								<tr>
									<td style="border-bottom: 1px solid #ffffff;">
										<table border="0" cellpadding="0" cellspacing="0" width="750" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show; vertical-align: middle;">
											<tr>
												<td style="width: 71px; height: 48px; vertical-align: middle;"><img src="https://design.realsn.com/email/2022/binggrae/daily/img/logo.gif" alt="빙그레" border="0" width="86" height="24" style="width:86px; height: 24px; vertical-align: middle"></td>
												<td style="text-align: right;">
													<span style="color: #212121; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 18px; font-weight: bold; vertical-align: bottom;">일일 Report</span>
												</td>
												<td style="width:110px; padding-left: 9px; text-align: right; vertical-align: middle;">
													<table border="0" cellpadding="0" cellspacing="0" height="20" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show; vertical-align: middle;">
														<tr>
															<td style="vertical-align: bottom; text-align: right;">
																<span style="color: #d52532; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px; font-weight: bold;">Date. </span>
																<span style="color: #535353; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px; font-weight: bold;"><%=today%></span>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>

                        </td>
                    </tr>
					<!-- // header -->

					<!-- content -->
					<tr>
						<td style="padding-bottom: 20px; border-bottom: 1px solid #fff59b;">
							<table border="0" cellpadding="0" cellspacing="0" width="750" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">

								<!-- 금일 이슈 현황 -->
								<tr>
									<td>
										<table border="0" cellpadding="0" cellspacing="0" width="750" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
											<!-- con_header -->
											<tr>
												<td style="padding: 30px 0 10px 0;">
													<table border="0" cellpadding="0" cellspacing="0" width="750" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
														<tr>
															<td style="width: 30px; height: 40px; vertical-align: top;">
																<img src="https://design.realsn.com/email/2022/binggrae/daily/img/header_01.gif" alt="빙그레_01_금일 이슈 현황" border="0" width="30" height="40" style="width:30px; height: 40px; vertical-align: middle">
															</td>
															<td style="height: 40px; width: 10px; background: #fbfbfb;">&nbsp;</td>
															<td style="height: 40px; background: #fbfbfb;">
																<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 16px; font-weight: bold;">금일 이슈 현황</span>
															</td>
															<td style="width: 25px; height: 40px; vertical-align: top;">
																<img src="https://design.realsn.com/email/2022/binggrae/daily/img/header_end.gif" alt="" border="0" width="25" height="40" style="width:25px; height: 40px; vertical-align: middle">
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- // con_header -->
											<!-- con -->
											<tr>
												<td>
													<table border="0" cellpadding="0" cellspacing="0" width="700" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
														<tr>
															<td style="border-top: 1px solid #212121; border-bottom: 1px solid #212121;">
																<table border="0" cellpadding="0" cellspacing="0" width="700" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
																	<tr>
																		<td style="height: 38px; padding-left: 10px;"><span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12.5px; font-weight: bold;">1. 언론</span></td>
																	</tr>
																	<tr>
																		<td style="padding-bottom: 12px; border-bottom: 1px solid #e1e1e1;">
																			<table border="0" cellpadding="0" cellspacing="0" width="700" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
																				<tr>
																					<td style="width: 24px; height: 18px; text-align: center; vertical-align: middle;"><img src="https://design.realsn.com/email/2022/binggrae/daily/img/dot.gif" alt="" border="0" width="4" height="4" style="width:4px; height: 4px; vertical-align: middle"></td>
																					<td><span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">입력하세요.</span></td>
																				</tr>
																				<tr>
																					<td style="width: 24px; height: 18px; text-align: center; vertical-align: middle;"><img src="https://design.realsn.com/email/2022/binggrae/daily/img/dot.gif" alt="" border="0" width="4" height="4" style="width:4px; height: 4px; vertical-align: middle"></td>
																					<td><span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">입력하세요.</span></td>
																				</tr>
																				<tr>
																					<td style="width: 24px; height: 18px; text-align: center; vertical-align: middle;"><img src="https://design.realsn.com/email/2022/binggrae/daily/img/dot.gif" alt="" border="0" width="4" height="4" style="width:4px; height: 4px; vertical-align: middle"></td>
																					<td><span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">입력하세요.</span></td>
																				</tr>
																				<tr>
																					<td style="width: 24px; height: 18px; text-align: center; vertical-align: middle;"><img src="https://design.realsn.com/email/2022/binggrae/daily/img/dot.gif" alt="" border="0" width="4" height="4" style="width:4px; height: 4px; vertical-align: middle"></td>
																					<td><span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">입력하세요.</span></td>
																				</tr>
																			</table>
																		</td>
																	</tr>
																	<tr>
																		<td style="height: 38px; padding-left: 10px;"><span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12.5px; font-weight: bold;">2. 개인미디어</span></td>
																	</tr>
																	<tr>
																		<td style="padding-bottom: 12px;">
																			<table border="0" cellpadding="0" cellspacing="0" width="700" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
																				<tr>
																					<td style="width: 24px; height: 18px; text-align: center; vertical-align: middle;"><img src="https://design.realsn.com/email/2022/binggrae/daily/img/dot.gif" alt="" border="0" width="4" height="4" style="width:4px; height: 4px; vertical-align: middle"></td>
																					<td><span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">입력하세요.</span></td>
																				</tr>
																				<tr>
																					<td style="width: 24px; height: 18px; text-align: center; vertical-align: middle;"><img src="https://design.realsn.com/email/2022/binggrae/daily/img/dot.gif" alt="" border="0" width="4" height="4" style="width:4px; height: 4px; vertical-align: middle"></td>
																					<td><span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">입력하세요.</span></td>
																				</tr>
																				<tr>
																					<td style="width: 24px; height: 18px; text-align: center; vertical-align: middle;"><img src="https://design.realsn.com/email/2022/binggrae/daily/img/dot.gif" alt="" border="0" width="4" height="4" style="width:4px; height: 4px; vertical-align: middle"></td>
																					<td><span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">입력하세요.</span></td>
																				</tr>
																				<tr>
																					<td style="width: 24px; height: 18px; text-align: center; vertical-align: middle;"><img src="https://design.realsn.com/email/2022/binggrae/daily/img/dot.gif" alt="" border="0" width="4" height="4" style="width:4px; height: 4px; vertical-align: middle"></td>
																					<td><span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">입력하세요.</span></td>
																				</tr>
																			</table>
																		</td>
																	</tr>


																	<!-- tbody -->
																	<!-- <tr>
																		<td style="width: 194px; height: 34px; border-bottom: 1px solid #f6f6f6; border-right: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																			<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">빙그레</span>
																		</td>
																		<td style="width: 139px; height: 34px; border-bottom: 1px solid #f6f6f6; border-right: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																			<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">800</span>
																		</td>
																		<td style="width: 139px; height: 34px; border-bottom: 1px solid #f6f6f6; border-right: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																			<table border="0" cellpadding="0" cellspacing="0" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
																				<tr>
																					<td style="width: 11px; text-align: left;">
																						<img src="https://design.realsn.com/email/2022/binggrae/daily/img/fluc_up.gif" alt="빙그레" border="0" width="9" height="8" style="width:9px; height: 8px;">
																					</td>
																					<td>
																						<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">99.9%</span>
																					</td>
																				</tr>
																			</table>
																		</td>
																		<td style="width: 139px; height: 34px; border-bottom: 1px solid #f6f6f6; border-right: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																			<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">800</span>
																		</td>
																		<td style="width: 139px; height: 34px; border-bottom: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																			<table border="0" cellpadding="0" cellspacing="0" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
																				<tr>
																					<td style="width: 11px; text-align: left;">
																						<img src="https://design.realsn.com/email/2022/binggrae/daily/img/fluc_dn.gif" alt="빙그레" border="0" width="9" height="8" style="width:9px; height: 8px;">
																					</td>
																					<td>
																						<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">99.9%</span>
																					</td>
																				</tr>
																			</table>
																		</td>
																	</tr>
																	<tr>
																		<td style="width: 194px; height: 34px; border-bottom: 1px solid #f6f6f6; border-right: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																			<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">롯데</span>
																		</td>
																		<td style="width: 139px; height: 34px; border-bottom: 1px solid #f6f6f6; border-right: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																			<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">2,306</span>
																		</td>
																		<td style="width: 139px; height: 34px; border-bottom: 1px solid #f6f6f6; border-right: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																			<table border="0" cellpadding="0" cellspacing="0" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
																				<tr>
																					<td style="width: 11px; text-align: left;">
																						<img src="https://design.realsn.com/email/2022/binggrae/daily/img/fluc_up.gif" alt="빙그레" border="0" width="9" height="8" style="width:9px; height: 8px;">
																					</td>
																					<td>
																						<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">99.9%</span>
																					</td>
																				</tr>
																			</table>
																		</td>
																		<td style="width: 139px; height: 34px; border-bottom: 1px solid #f6f6f6; border-right: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																			<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">2,306</span>
																		</td>
																		<td style="width: 139px; height: 34px; border-bottom: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																			<table border="0" cellpadding="0" cellspacing="0" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
																				<tr>
																					<td style="width: 11px; text-align: left;">
																						<img src="https://design.realsn.com/email/2022/binggrae/daily/img/fluc_dn.gif" alt="빙그레" border="0" width="9" height="8" style="width:9px; height: 8px;">
																					</td>
																					<td>
																						<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">99.9%</span>
																					</td>
																				</tr>
																			</table>
																		</td>
																	</tr>
																	<tr>
																		<td style="width: 194px; height: 34px; border-bottom: 1px solid #e8e8e8; border-right: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																			<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">ETC</span>
																		</td>
																		<td style="width: 139px; height: 34px; border-bottom: 1px solid #e8e8e8; border-right: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																			<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">40,306</span>
																		</td>
																		<td style="width: 139px; height: 34px; border-bottom: 1px solid #e8e8e8; border-right: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																			<table border="0" cellpadding="0" cellspacing="0" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
																				<tr>
																					<td style="width: 11px; text-align: left;">
																						<img src="https://design.realsn.com/email/2022/binggrae/daily/img/fluc_up.gif" alt="빙그레" border="0" width="9" height="8" style="width:9px; height: 8px;">
																					</td>
																					<td>
																						<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">99.9%</span>
																					</td>
																				</tr>
																			</table>
																		</td>
																		<td style="width: 139px; height: 34px; border-bottom: 1px solid #e8e8e8; border-right: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																			<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">40,306</span>
																		</td>
																		<td style="width: 139px; height: 34px; border-bottom: 1px solid #e8e8e8; background: #ffffff; text-align: center;">
																			<table border="0" cellpadding="0" cellspacing="0" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
																				<tr>
																					<td style="width: 11px; text-align: left;">
																						<img src="https://design.realsn.com/email/2022/binggrae/daily/img/fluc_dn.gif" alt="빙그레" border="0" width="9" height="8" style="width:9px; height: 8px;">
																					</td>
																					<td>
																						<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">99.9%</span>
																					</td>
																				</tr>
																			</table>
																		</td>
																	</tr> -->
																	<!-- // tbody -->
																</table>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- // con -->
										</table>
									</td>
								</tr>

								<!-- 유통 경로별 정보 현황 -->
								<tr>
									<td>
										<table border="0" cellpadding="0" cellspacing="0" width="750" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
											<!-- con_header -->
											<tr>
												<td style="padding: 30px 0 10px 0;">
													<table border="0" cellpadding="0" cellspacing="0" width="750" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
														<tr>
															<td style="width: 30px; height: 40px; vertical-align: top;">
																<img src="https://design.realsn.com/email/2022/binggrae/daily/img/header_02.gif" alt="빙그레_02_유통 경로별 정보 현황" border="0" width="30" height="40" style="width:30px; height: 40px; vertical-align: middle">
															</td>
															<td style="height: 40px; width: 10px; background: #fbfbfb;">&nbsp;</td>
															<td style="height: 40px; background: #fbfbfb;">
																<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 16px; font-weight: bold;">유통 경로별 정보 현황</span>
															</td>
															<td style="width: 25px; height: 40px; vertical-align: top;">
																<img src="https://design.realsn.com/email/2022/binggrae/daily/img/header_end.gif" alt="" border="0" width="25" height="40" style="width:25px; height: 40px; vertical-align: middle">
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- // con_header -->
											<!-- con -->
											<tr>
												<td>
													<table border="0" cellpadding="0" cellspacing="0" width="700" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
														<tr>
															<td style="height: 333px; text-align: center;">
																<img src="<%=chart1%>" alt="" border="0" width="696" height="333" style="width:696px; height: 333px; vertical-align: middle">
															</td>
														</tr>
														<tr>
															<td style="height: 333px; text-align: center;">
																<img src="<%=chart2%>" alt="" border="0" width="696" height="333" style="width:696px; height: 333px; vertical-align: middle">
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- // con -->
										</table>
									</td>
								</tr>
<!-- 								
								own_press_infoList =
								own_social_infoList 
								rival_press_infoList
 -->								
								
							<% 
							if(!"".equals(id_seqs)){
								if(own_press_infoList.size() > 0 || own_social_infoList.size() > 0){ %>
								<!-- 자사 언론/소셜 정보 -->
								<tr>
									<td>
										<table border="0" cellpadding="0" cellspacing="0" width="750" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
											<!-- con_header -->
											<tr>
												<td style="padding: 30px 0 10px 0;">
													<table border="0" cellpadding="0" cellspacing="0" width="750" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
														<tr>
															<td style="width: 30px; height: 40px; vertical-align: top;">
																<img src="https://design.realsn.com/email/2022/binggrae/daily/img/header_03.gif" alt="빙그레_03_금일 이슈 현황" border="0" width="30" height="40" style="width:30px; height: 40px; vertical-align: middle">
															</td>
															<td style="height: 40px; width: 10px; background: #fbfbfb;">&nbsp;</td>
															<td style="height: 40px; background: #fbfbfb;">
																<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 16px; font-weight: bold;">자사 언론/소셜 정보</span>
															</td>
															<td style="width: 25px; height: 40px; vertical-align: top;">
																<img src="https://design.realsn.com/email/2022/binggrae/daily/img/header_end.gif" alt="" border="0" width="25" height="40" style="width:25px; height: 40px; vertical-align: middle">
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- // con_header -->
											<!-- con -->
											<tr>
												<td>
													<table border="0" cellpadding="0" cellspacing="0" width="700" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
														<%if(own_press_infoList.size() > 0){ %>
														<!-- 언론 현황 -->
														<tr>
															<td style="border-top: 1px solid #636363; padding-bottom: 20px;">
																<table border="0" cellpadding="0" cellspacing="0" width="700" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
																	<tr>
																		<td style="height: 35px; padding-left: 10px;"><span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12.5px; font-weight: bold;">1. 빙그레</span></td>
																	</tr>
																	<tr>
																		<td style="border-top: 2px solid #000000; ">
																			<table border="0" cellpadding="0" cellspacing="0" width="700" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
																				<!-- thead -->
																				<tr>
																					<td style="width: 70px; border-right: 1px solid #ffffff; background: #efefef; text-align: center;">
																						<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">성향</span>
																					</td>
																					<td style="				border-right: 1px solid #ffffff; background: #efefef; text-align: center;">
																						<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">제목</span>
																					</td>
																					<td style="width: 89px; height: 25px; border-right: 1px solid #ffffff; background: #efefef; text-align: center;">
																						<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">출처</span>
																					</td>
																					<td style="width: 89px; height: 25px; border-right: 1px solid #ffffff; background: #efefef; text-align: center;">
																						<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">날짜</span>
																					</td>
																					<td style="width: 50px; background: #efefef; text-align: center;">
																						<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">확산</span>
																					</td>
																				</tr>
																				<!-- // thead -->
																				<!-- tbody -->
																				<% 
															                   	HashMap<String, String> own_press = new HashMap<String, String>();
															                   	for(int i=0; i<own_press_infoList.size(); i++){
															                   		own_press = own_press_infoList.get(i);
																				%>
																					<tr>
																						<td style="height: 34px; border-right: 1px solid #fbfbfb; border-bottom: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																							<%if(own_press.get("ID_SENTI").equals("1")){ %>
																								<img src="https://design.realsn.com/email/2022/binggrae/daily/img/posi.gif" alt="긍정" border="0" width="51" height="22" style="width:51px; height: 22px;">
																							<%}else if(own_press.get("ID_SENTI").equals("2")){ %>
																								<img src="https://design.realsn.com/email/2022/binggrae/daily/img/nega.gif" alt="부정" border="0" width="51" height="22" style="width:51px; height: 22px;">
																							<%}else if(own_press.get("ID_SENTI").equals("3")){ %>
																								<img src="https://design.realsn.com/email/2022/binggrae/daily/img/neut.gif" alt="중립" border="0" width="51" height="22" style="width:51px; height: 22px;">
																							<%} %>
																						</td>
																						<td style="height: 34px; border-right: 1px solid #fbfbfb; border-bottom: 1px solid #f6f6f6; background: #ffffff; padding-left: 10px; text-align: left;">
																							<a href="<%=own_press.get("ID_URL")%>" style="color: #666666;" target="_blank"><span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;"><%=own_press.get("ID_TITLE")%></span></a>
																						</td>
																						<td style="height: 34px; border-right: 1px solid #fbfbfb; border-bottom: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																							<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;"><%=own_press.get("MD_SITE_NAME")%></span>
																						</td>
																						<td style="height: 34px; border-right: 1px solid #fbfbfb; border-bottom: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																							<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;"><%=own_press.get("MD_DATE")%></span>
																						</td>
																						<td style="height: 34px; border-bottom: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																							<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;"><%=own_press.get("MD_SAME_CT")%></span>
																						</td>
																					</tr>
																				<%
															                   	}
																				%>
																				<!-- // tbody -->
																			</table>
																		</td>
																	</tr>

																</table>
															</td>
														</tr>
														<%} %>
														<%if(own_social_infoList.size() > 0){ %>
														<!-- 개인미디어 현황 -->
														<tr>
															<td style="border-top: 1px solid #636363;">
																<table border="0" cellpadding="0" cellspacing="0" width="700" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
																	<%if(own_press_infoList.size() > 0){ %>
																	<tr>
																		<td style="height: 35px; padding-left: 10px;"><span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12.5px; font-weight: bold;">2. 개인미디어 현황</span></td>
																	</tr>
																	<%}else{ %>
																	<tr>
																		<td style="height: 35px; padding-left: 10px;"><span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12.5px; font-weight: bold;">1. 개인미디어 현황</span></td>
																	</tr>
																	<%} %>
																	<tr>
																		<td style="border-top: 2px solid #000000; ">
																			<table border="0" cellpadding="0" cellspacing="0" width="700" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
																				<!-- thead -->
																				<tr>
																					<td style="width: 70px; border-right: 1px solid #ffffff; background: #efefef; text-align: center;">
																						<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">성향</span>
																					</td>
																					<td style="				border-right: 1px solid #ffffff; background: #efefef; text-align: center;">
																						<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">제목</span>
																					</td>
																					<td style="width: 89px; height: 25px; border-right: 1px solid #ffffff; background: #efefef; text-align: center;">
																						<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">출처</span>
																					</td>
																					<td style="width: 89px; height: 25px; border-right: 1px solid #ffffff; background: #efefef; text-align: center;">
																						<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">날짜</span>
																					</td>
																					<td style="width: 50px; background: #efefef; text-align: center;">
																						<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">확산</span>
																					</td>
																				</tr>
																				<!-- // thead -->
																				<!-- tbody -->
																				<% 
															                   	HashMap<String, String> own_social = new HashMap<String, String>();
															                   	for(int i=0; i<own_social_infoList.size(); i++){
															                   		own_social = own_social_infoList.get(i);
																				%>
																					<tr>
																						<td style="height: 34px; border-right: 1px solid #fbfbfb; border-bottom: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																							<%if(own_social.get("ID_SENTI").equals("1")){ %>
																								<img src="https://design.realsn.com/email/2022/binggrae/daily/img/posi.gif" alt="긍정" border="0" width="51" height="22" style="width:51px; height: 22px;">
																							<%}else if(own_social.get("ID_SENTI").equals("2")){ %>
																								<img src="https://design.realsn.com/email/2022/binggrae/daily/img/nega.gif" alt="부정" border="0" width="51" height="22" style="width:51px; height: 22px;">
																							<%}else if(own_social.get("ID_SENTI").equals("3")){ %>
																								<img src="https://design.realsn.com/email/2022/binggrae/daily/img/neut.gif" alt="중립" border="0" width="51" height="22" style="width:51px; height: 22px;">
																							<%} %>
																						</td>
																						<td style="height: 34px; border-right: 1px solid #fbfbfb; border-bottom: 1px solid #f6f6f6; background: #ffffff; padding-left: 10px; text-align: left;">
																							<a href="<%=own_social.get("ID_URL")%>" style="color: #666666;" target="_blank"><span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;"><%=own_social.get("ID_TITLE")%></span></a>
																						</td>
																						<td style="height: 34px; border-right: 1px solid #fbfbfb; border-bottom: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																							<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;"><%=own_social.get("MD_SITE_NAME")%></span>
																						</td>
																						<td style="height: 34px; border-right: 1px solid #fbfbfb; border-bottom: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																							<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;"><%=own_social.get("MD_DATE")%></span>
																						</td>
																						<td style="height: 34px; border-bottom: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																							<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;"><%=own_social.get("MD_SAME_CT")%></span>
																						</td>
																					</tr>
																				<%
															                   	}
																				%>
																				<!-- // tbody -->
																			</table>
																		</td>
																	</tr>

																</table>
															</td>
														</tr>
														<%} %>
													</table>
												</td>
											</tr>
											<!-- // con -->
										</table>
									</td>
								</tr>
								<%} %>
								<% if(rival_press_infoList.size() > 0){ %>
								<!-- 업계 언론 정보 -->
								<tr>
									<td>
										<table border="0" cellpadding="0" cellspacing="0" width="750" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
											<!-- con_header -->
											<tr>
												<td style="padding: 30px 0 10px 0;">
													<table border="0" cellpadding="0" cellspacing="0" width="750" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
														<tr>
															<% if(own_press_infoList.size() > 0 || own_social_infoList.size() > 0){ %>
															<td style="width: 30px; height: 40px; vertical-align: top;">
																<img src="https://design.realsn.com/email/2022/binggrae/daily/img/header_04.gif" alt="빙그레_04_업계 언론 정보" border="0" width="30" height="40" style="width:30px; height: 40px; vertical-align: middle">
															</td>
															<%}else{ %>
															<td style="width: 30px; height: 40px; vertical-align: top;">
																<img src="https://design.realsn.com/email/2022/binggrae/daily/img/header_03.gif" alt="빙그레_03_업계 언론 정보" border="0" width="30" height="40" style="width:30px; height: 40px; vertical-align: middle">
															</td>
															<%} %>
															<td style="height: 40px; width: 10px; background: #fbfbfb;">&nbsp;</td>
															<td style="height: 40px; background: #fbfbfb;">
																<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 16px; font-weight: bold;">업계 언론 정보</span>
															</td>
															<td style="width: 25px; height: 40px; vertical-align: top;">
																<img src="https://design.realsn.com/email/2022/binggrae/daily/img/header_end.gif" alt="" border="0" width="25" height="40" style="width:25px; height: 40px; vertical-align: middle">
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- // con_header -->
											<!-- con -->
											<tr>
												<td>
													<table border="0" cellpadding="0" cellspacing="0" width="700" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">

														<!-- 빙그레 -->
														<tr>
															<td style="padding-bottom: 20px;">
																<table border="0" cellpadding="0" cellspacing="0" width="700" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
																	<tr>
																		<td style="border-top: 2px solid #000000; ">
																			<table border="0" cellpadding="0" cellspacing="0" width="700" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show;">
																				<!-- thead -->
																				<tr>
																					<td style="width: 70px; border-right: 1px solid #ffffff; background: #efefef; text-align: center;">
																						<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">성향</span>
																					</td>
																					<td style="				border-right: 1px solid #ffffff; background: #efefef; text-align: center;">
																						<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">제목</span>
																					</td>
																					<td style="width: 89px; height: 25px; border-right: 1px solid #ffffff; background: #efefef; text-align: center;">
																						<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">출처</span>
																					</td>
																					<td style="width: 89px; background: #efefef; text-align: center;">
																						<span style="color: #333333; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;">날짜</span>
																					</td>
																				</tr>
																				<!-- // thead -->
																				<!-- tbody -->
																				<% 
															                   	HashMap<String, String> rival_press = new HashMap<String, String>();
															                   	for(int i=0; i<rival_press_infoList.size(); i++){
															                   		rival_press = rival_press_infoList.get(i);
																				%>
																					<tr>
																						<td style="height: 34px; border-right: 1px solid #fbfbfb; border-bottom: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																							<%if(rival_press.get("ID_SENTI").equals("1")){ %>
																								<img src="https://design.realsn.com/email/2022/binggrae/daily/img/posi.gif" alt="긍정" border="0" width="51" height="22" style="width:51px; height: 22px;">
																							<%}else if(rival_press.get("ID_SENTI").equals("2")){ %>
																								<img src="https://design.realsn.com/email/2022/binggrae/daily/img/nega.gif" alt="부정" border="0" width="51" height="22" style="width:51px; height: 22px;">
																							<%}else if(rival_press.get("ID_SENTI").equals("3")){ %>
																								<img src="https://design.realsn.com/email/2022/binggrae/daily/img/neut.gif" alt="중립" border="0" width="51" height="22" style="width:51px; height: 22px;">
																							<%} %>
																						</td>
																						<td style="height: 34px; border-right: 1px solid #fbfbfb; border-bottom: 1px solid #f6f6f6; background: #ffffff; padding-left: 10px; text-align: left;">
																							<a href="<%=rival_press.get("ID_URL")%>" style="color: #666666;" target="_blank"><span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;"><%=rival_press.get("ID_TITLE")%></span></a>
																						</td>
																						<td style="height: 34px; border-right: 1px solid #fbfbfb; border-bottom: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																							<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;"><%=rival_press.get("MD_SITE_NAME")%></span>
																						</td>
																						<td style="height: 34px; border-right: 1px solid #fbfbfb; border-bottom: 1px solid #f6f6f6; background: #ffffff; text-align: center;">
																							<span style="color: #666666; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px;"><%=rival_press.get("MD_DATE")%></span>
																						</td>
																					</tr>
																				<%
															                   	}
																				%>
																				<!-- // tbody -->
																			</table>
																		</td>
																	</tr>

																</table>
															</td>
														</tr>
														
													</table>
												</td>
											</tr>
											<!-- // con -->
										</table>
									</td>
								</tr>
								<%} 
							} %>
							</table>
						</td>
					</tr>
					<!-- // content -->

                </table>
            </td>
        </tr>
    </table>
</body>
</html>

