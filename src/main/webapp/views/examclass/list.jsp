<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<%@page import="org.apache.commons.lang.time.DateUtils"%>
<%@page import="org.hibernate.engine.spi.RowSelection"%>
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


<!-- LightYellow = 1, Moccasin = 2 Blue = 3 -->
<jstl:if test="${newspaper!=null}">
	<jstl:set var="requestURI" value=""/>
	<jstl:set var="pSize" value=""/>
	<jstl:set var="url" value="examclass/display.do?Id="/>
	<jstl:set var="included" value="true"/>
	
</jstl:if>
<jstl:if test="${newspaper==null}">
	<jstl:set var="requestURI" value="examclass/administrator/list.do"/>	
	<jstl:set var="pSize" value="5"/>
	<jstl:set var="url" value="examclass/administrator/edit.do?examClassId="/>
</jstl:if>
<display:table pagesize="${pSize}" requestURI="${requestURI}"
	name="examClasses" class="displaytag" id="rowExam">
	<acme:column property="gauge" title="examclass.gauge"
		css="examRowColor${rowExam.gauge}" sortable="true"/>
	<spring:message code="examclass.ticker" var="title" />
	<display:column title="${title}" class="examRowColor${rowExam.gauge}">
		<div>
			<a href="${url}${rowExam.id}">
				<jstl:out value="${rowExam.ticker}" />
			</a>
		</div>
	</display:column>

	<acme:column property="title" title="examclass.title"
		css="examRowColor${rowExam.gauge}" />
	<display:column title="${title}" class="examRowColor${rowExam.gauge} fixedSize40">
	<div class="ellipsis examRowColor${rowExam.gauge}" onclick="ellipsis(this);">
	<jstl:out value="${rowExam.description}"/>
	</div>
	</display:column>
	<acme:column property="admin.name" title="examclass.owner"
		css="examRowColor${rowExam.gauge}" />
	<acme:column property="newspaper.title" title="newspaper"
		css="examRowColor${rowExam.gauge}" />
	<spring:message code="examclass.moment.format" var="momentFormat" />
	<acme:column property="moment" title="examclass.moment"
		css="examRowColor${rowExam.gauge}" format="${momentFormat}" />
	<jstl:if test="${!included}">
	<spring:message code="msg.delete" var="titleDelete" />
	<security:authorize access="hasRole('ADMIN')">
		<display:column title="${titleDelete}"
			class="examRowColor${rowExam.gauge}">
			<div>
				<a
					href="examclass/administrator/delete.do?examClassId=${rowExam.id}">
					<jstl:out value="${titleDelete}" />
				</a>
			</div>
		</display:column>
	</security:authorize>
	</jstl:if>
</display:table>
<jstl:if test="${newspaper==null}">
	<security:authorize access="hasRole('ADMIN')">
		<acme:button url="examclass/administrator/create.do" text="msg.new"
			css="formButton toLeft" />
	</security:authorize>
</jstl:if>


