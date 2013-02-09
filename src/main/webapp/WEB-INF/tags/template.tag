<?xml version="1.0" encoding="UTF-8" ?>
<%@ include file="/WEB-INF/includes/init.jsp" %>
<%@ tag dynamic-attributes="templateDetails" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="bakehouse" uri="http://www.mangofactory.com/bakehouse" %>
<%@ attribute name="head" required="false" fragment="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	
	<title>
		<%-- Set the title based on the presence of a title attribute on the template tag --%> 
		<c:choose>
			<c:when test="${not empty templateDetails.title}">
				<c:out value="${templateDetails.title}"/> : <c:out value="${siteName}"/> 
			</c:when>
			<c:otherwise>
				<c:out value="${siteName}"/>
			</c:otherwise>
		</c:choose>
	</title>
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	
	<%-- Stylesheets --%>
	<link href="${_baseUrl}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
	<link href="${_baseUrl}/resources/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
	<link href="${_baseUrl}/resources/project.css" rel="stylesheet">
	
	<%-- HTML5 shim for old IE --%>
	<!--[if lt IE 9]>
	  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
	
	<%-- JavaScript --%>
	<bakehouse:resource src="angular.js" cdn="http://ajax.googleapis.com/ajax/libs/angularjs/1.0.3/angular.min.js"/>
	<bakehouse:resources configuration="javascript" type="text/javascript">
	    <bakehouse:resource src="resources/jquery-1.7.1.min.js"/>
	    <bakehouse:resource src="resources/bootstrap/js/bootstrap.js"/>
	</bakehouse:resources>
	<%-- 
	<script src="${_baseUrl}/resources/jquery-1.7.1.min.js"></script>
	<script src="${_baseUrl}/resources/bootstrap/js/bootstrap.js"></script>
	 --%>
	 
	<%-- Insert "head" content from the calling page, if any --%>
	<jsp:invoke fragment="head"/>
	  
</head>

<body>
	<%@ include file="/WEB-INF/includes/nav.jsp" %>

	<div class="container-fluid">
  		<%-- Insert the body content from the page --%>
		<jsp:doBody />
	</div>
</body>
</html>