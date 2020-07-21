<%@page import="com.hiepnx.cms.server.CookieUtils"%>
<%@page import="com.hiepnx.cms.shared.Config"%>
<%@page import="com.hiepnx.cms.shared.model.UserInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
var Config = {
	LOGIN_FAILED:					<%=Config.LOGIN_FAILED%>,
	LOGIN_SUCCESS:					<%=Config.LOGIN_SUCCESS%>,
	LOGIN_ACCOUNT_IS_USED:			<%=Config.LOGIN_ACCOUNT_IS_USED%>,
	LOGIN_ACCOUNT_NOT_EXIST:		<%=Config.LOGIN_ACCOUNT_NOT_EXIST%>,
	LOGIN_WRONG_PASSWORD:			<%=Config.LOGIN_WRONG_PASSWORD%>,
	LOGIN_WRONG_PROVIDER:			<%=Config.LOGIN_WRONG_PROVIDER%>,
	LOGIN_ACCOUNT_NOT_ACTIVATED:	<%=Config.LOGIN_ACCOUNT_NOT_ACTIVATED%>,
	LOGIN_MOBILE_IS_USED:			<%=Config.LOGIN_MOBILE_IS_USED%>,
	COOKIE_SESSION_KEY:				'<%=CookieUtils.COOKIE_SESSION_KEY%>',
	EXPIRED_TIME_LOGIN:				<%=CookieUtils.EXPIRED_TIME_LOGIN%>,
}
</script>