
<%@page import="org.springframework.test.web.ModelAndViewAssert"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<jstl:if test="${show}">
		<a href="${advertisement.targetURL}"><img src="${advertisement.bannerURL}" class="AdvertisementImg"/></a>
	</jstl:if>
	<br/> 
	<br/>
	<div class="toRight">
		<fmt:formatDate value="${article.publicationMoment}" pattern="yyyy-MM-dd" var="publicationDate" />
		<jstl:out value="${publicationDate}"/>
	</div>
</div>
<div>
	<h5>
		<jstl:out value="${article.title}" />
	</h5>
</div>

<p>
	<acme:textarea code="article.summary" path="article.summary" readonly="true" css="formTextArea" />
</p>
<br>
<!-- Carrusel se fotos  -->
<spring:message code="article.picture" var="picture" />
<div class="slideshow-container">
	<jstl:set var="count" scope="application" value="${0}" />
	<jstl:forEach items="${article.pictures}" var="picture">
		<jstl:set var="count" scope="application" value="${count + 1}" />
		<div class="mySlides fade">
			<div class="numbertext">${count}/ ${article.pictures.size()}</div>
			<img src="${picture}" style="width: 100%">
			<div class="text">${article.title}</div>
		</div>
	</jstl:forEach>

	<a class="prev" onclick="plusSlides(-1)">&#10094;</a> <a class="next"
		onclick="plusSlides(1)">&#10095;</a>
</div>
<br>
<jstl:set var="count" scope="application" value="${0}" />
<div style="text-align: center">
	<jstl:forEach items="${article.pictures}" var="picture">
		<jstl:set var="count" scope="application" value="${count + 1}" />
		<span class="dot" onclick="currentSlide(${count})"></span>
	</jstl:forEach>
</div>
<br />
<!-- FIN Carrusel se fotos  -->
<p>
	<acme:textarea code="article.body" path="article.body" readonly="true"
		css="formTextArea" />
</p>


<br />

<acme:historyBackButton text="msg.back" css="formButton toLeft" />
<security:authorize access="hasRole('ADMIN')">
	<acme:button text="article.delete"
		url="article/administrator/delete.do?id=${article.id}"
		css="formButton toLeft" />
</security:authorize>


<script>
showSlides(-1);
</script>
