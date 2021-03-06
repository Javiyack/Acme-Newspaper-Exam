<%--
 * textbox.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty"%>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Attributes --%>

<%@ attribute name="property" required="true"%>
<%@ attribute name="title" required="true"%>

<%@ attribute name="css" required="false"%>
<%@ attribute name="url" required="false"%>
<%@ attribute name="sortable" required="false"%>
<%@ attribute name="format" required="false"%>

<jstl:if test="${url == null}">
	<jstl:set var="url" value="" />
</jstl:if>
<%--  <jstl:if test="${sotable == null}">
	<jstl:set var="sortable" value="false" />
</jstl:if>--%>

<%-- <jstl:if test="${format == null}">
	<jstl:set var="format" value="" />
</jstl:if>  --%>


<%-- Definition --%>
<%-- <jstl:if test="${format != null}"> --%>
<%-- 	<spring:message code="${format }" var="formatVar" /> --%>
<%-- </jstl:if> --%>
<jstl:if test="${url!=''}">
<spring:message code="${title }" var="titleVar" />
<display:column property="${property }" title="${ titleVar}"
	sortable="${sortable }" format="${format}" href="${url}" class="${css}"/>

</jstl:if>

<jstl:if test="${url==''}">
<spring:message code="${title }" var="titleVar" />
<display:column property="${property }" title="${ titleVar}"
	sortable="${sortable }" format="${format}" class="${css}"/>

</jstl:if>


