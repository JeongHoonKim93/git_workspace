<%@page import="java.net.URLEncoder"%>
<%@page import="risk.issue.IssueDataBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="risk.util.ParseRequest" %>
<%@ page import="risk.util.ConfigUtil" %>
<%@ page import="risk.util.DateUtil" %>
<%@ page import="risk.util.StringUtil" %>
<%@ page import="risk.report.ReportMgrNew" %>
<%@ page import="risk.report.ReportChartMgr" %>
<%@ page import="risk.issue.IssueMgr" %>


<%
	// 생성자
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	ConfigUtil cu = new ConfigUtil();
	DateUtil du = new DateUtil();
	StringUtil su = new StringUtil();
	IssueMgr iMgr = new IssueMgr();
	
	String id_seq = pr.getString("id_seq");
	
	IssueDataBean bean = iMgr.getOneIssueDataBean(id_seq);

	//이미지 경로
	String siteUrl = cu.getConfig("URL");
	String imgUrl = siteUrl+"riskv3/report/daily/img/";
	
	String eTitle = URLEncoder.encode(bean.getId_title_replace(),"UTF-8").replaceAll("\\+", "%20").replaceAll("\"", "").replaceAll("'", "");
	String title = bean.getId_title().replaceAll("\"", "").replaceAll("'", "");

	String url = "";
	if(bean.getId_url().length() > 80) {
		url = bean.getId_url().substring(0, 80) + "...";
	} else {
		url = bean.getId_url();
	}

	
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge"> 
    <title>Bing Issue Report</title>
</head>
<script src="../../js/devel.js" type="text/javascript"></script>

<body style="margin:0;padding:0;">
    <!-- 보고서 -->
    <table border="0" cellpadding="0" cellspacing="0" width="750" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:12px;empty-cells:show;">
        <tr>
	        <td style="height: 48px; background: #fff59b; border-bottom: 1px solid #fff59b; vertical-align: middle;">
				<table border="0" cellpadding="0" cellspacing="0" width="748" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show; vertical-align: middle;">
					<tr>
						<td style="border-bottom: 1px solid #ffffff;">
							<table border="0" cellpadding="0" cellspacing="0" width="748" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show; vertical-align: middle;">
								<tr>
									<td style="width: 71px; height: 48px; vertical-align: middle;"><img src="<%=imgUrl %>logo.gif" alt="빙그레" border="0" width="86" height="24" style="width:86px; height: 24px; vertical-align: middle"></td>
									<td style="text-align: center;">
										<span style="color: #212121; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 18px; font-weight: bold; vertical-align: bottom;"></span>
									</td>
									<td style="width:110px; padding-left: 9px; text-align: right; vertical-align: middle;">
										<table border="0" cellpadding="0" cellspacing="0" height="20" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show; vertical-align: middle;">
											<tr>
												<td style="vertical-align: bottom; text-align: right;">
													<span style="color: #d52532; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px; font-weight: bold;">Date. </span>
													<span style="color: #535353; font-family:맑은 고딕,Malgun Gothic,돋움,Dotum; font-size: 12px; font-weight: bold;"><%=du.getDate() %></span>
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
        <tr>
            <td>
                <table border="0" cellpadding="0" cellspacing="0" width="748" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum;font-size:14px;empty-cells:show; border:1px solid #fff59b;"">

                    <!-- Report Title -->
                    <tr>
                        <td>
                            <table border="0" cellpadding="0" cellspacing="0" width="748" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;">
                                <tr>
                                    <td>
                                        <table border="0" cellpadding="0" cellspacing="0" width="748" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; text-align:center;">
                                            <tr>
                                                <td style="padding: 10px 0 0; font-size:32px; font-weight:bold; font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum;">
                                                    <span style="color:#333333">Issue Report</span>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <!-- // Report Title -->

                    <tr>
                        <td style="padding: 15px 0; ">
                            <table border="0" cellpadding="0" cellspacing="0" width="718" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; border-top:4px solid #666666; border-bottom: 1px solid #666666; background: #ffffff">
                                <tr>
                                    <td style="padding: 15px 0">
                                        <table border="0" cellpadding="0" cellspacing="0" width="688" align="center" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt">
                                            <tr>
                                                <td >
                                                    <table border="0" cellpadding="0" cellspacing="0" valign="top" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; vertical-align: top;">
                                                        <tr>
                                                            <td>
                                                                <table border="0" cellpadding="0" cellspacing="0" valign="top" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">
                                                                    <tr>
                                                                        <td style="padding: 8px 20px 8px 0; font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum; color:#333333; font-size:16px; font-weight:bold; text-decoration: none; text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">
                                                                           <%=bean.getId_title()%>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="border-right: 1px solid #e6e6e6; border-left: 1px solid #e6e6e6; border-top: 1px solid #e6e6e6; background: #fbfbfb">
                                                    <table border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;">
                                                        <tr>
                                                            <td style="width: 100px; padding: 5px 0; border-bottom: 1px solid #e6e6e6; background: #e6e6e6; font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum; color:#999999; font-size:12px; text-align: center">수집시간</td>
                                                            <td style="width: 130px; padding: 7px 10px; border-bottom: 1px solid #e6e6e6;"><strong style="font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum; color:#666666; font-size:12px;"><%=bean.getId_regdate()%></strong></td>
                                                            <td style="width: 100px; padding: 5px 0; border-bottom: 1px solid #e6e6e6; background: #e6e6e6; font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum; color:#999999; font-size:12px; text-align: center">출처</td>
                                                            <td colspan="3" style="padding: 7px 10px; border-bottom: 1px solid #e6e6e6;"><strong style="font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum; color:#666666; font-size:12px;"><%=bean.getSg_name()%>(<%=bean.getMd_site_menu()%>)</strong></td>
                                                        </tr>
                                                        <tr>
                                                            <td style="padding: 5px 0; border-bottom: 1px solid #e6e6e6; background: #e6e6e6; font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum; color:#999999; font-size:12px; text-align: center">URL</td>
                                                            <td colspan="5" style="padding: 7px 10px; border-bottom: 1px solid #e6e6e6;">
                                                            <span style="display: <%="3555".equals(bean.getS_seq()) ? "" : "4943".equals(bean.getS_seq()) ? "" : "none" %>;">
															<a onclick="javascipt:devel.hrefPop('','<%=bean.getS_seq()%>','<%=eTitle%>')" style="cursor: pointer;" target="_blank"  >
																<img  style="vertical-align: middle; cursor:pointer" src="../../images/search/yellow_star.gif" >
															</a> 
															</span>
                                                            <a href="http://hub.buzzms.co.kr?url=<%=URLEncoder.encode(bean.getId_url(), "UTF-8") %>" target="_blank" style="text-decoration: none;"><strong style="font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum; color:#006ecd; font-size:12px;"><%=url%></strong></a></td>
                                                        </tr>
                                                        <tr>
                                                            <td style="padding: 5px 0; border-bottom: 1px solid #e6e6e6; background: #e6e6e6; font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum; color:#999999; font-size:12px; text-align: center">요약내용</td>
                                                              <td colspan="5" style="width: 566px; padding: 7px 10px; border-bottom: 1px solid #e6e6e6; color: #999999; font-size: 12px; word-break: keep-all;">
                                                              <%=bean.getId_content()%>
                                                            </td> 
                                                        </tr>
                                                        <tr>
                                                            <td style="padding: 5px 0; border-bottom: 1px solid #e6e6e6; background:#e6e6e6; font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum; color:#999999; font-size:12px; text-align: center">확산량</td>
                                                            <td colspan="5" style="padding: 7px 10px; border-bottom: 1px solid #e6e6e6;">
                                                                <span style="font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum; color:#999999; font-size:12px;">조회</span>
                                                                <strong style="font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum; color:#666666; font-size:12px;">0</strong>
                                                                <span style="font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum; color:#999999; font-size:12px;">건</span>
                                                                <strong style="padding:0 5px;font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum; color:#c8c8c8; font-size:12px;">|</strong>
                                                                <span style="font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum; color:#999999; font-size:12px;">댓글</span>
                                                                <strong style="font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum; color:#666666; font-size:12px;">0</strong>
                                                                <span style="font-family: 맑은 고딕,Malgun Gothic,돋움,Dotum; color:#999999; font-size:12px;">건</span>
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

                </table>
            </td>
        </tr>
    </table>
    <!-- // 보고서 -->
    <!-- // 메일 사용 영역 -->
	<div style="DISPLAY: none"><img src="report_receipt" /></div>
</body>
</html>