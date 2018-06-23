<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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

<form:form action="examclass/administrator/edit.do"
	modelAttribute="examClass">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="admin.id" />

	<h5>
		<acme:select code="newspaper" items="${newspapers}" path="newspaper"
			itemLabel="title" css="formInput" readonly="${finalMode || !edit}" />
	</h5>
	<br />
	<h5>
		<acme:textbox code="examclass.ticker" path="ticker" css="formInput"
			readonly="true" />
	</h5>
	<br />
	<h5>
		<acme:textbox code="examclass.title" path="title" css="formInput"
			readonly="${finalMode || !edit}" />
	</h5>
	<br />
	<h5>
		<acme:textarea code="examclass.description" path="description"
			css="formTextArea" readonly="${finalMode || !edit}" />
		<br />
	</h5>
	<h5>
		<acme:input type="number" code="examclass.gauge" path="gauge"
			css="formNumber" readonly="${finalMode || !edit}" min="1" max="3"/>

		<br />
	</h5>
	<h5>
		<spring:message code="examclass.moment" />
	</h5>
	<spring:message code="moment.pattern" var="momentPattern" />
	<div class="formInput">
		<fmt:formatDate value="${examClass.moment}" pattern="${momentPattern}" />
	</div>

	<h5>
		<acme:textbox code="examclass.owner" path="admin.userAccount.username"
			css="formInput" readonly="true" />
		<br />
	</h5>
	<br>

	<acme:checkBox code="examclass.draft" path="draftMode" css="formCheck"
		readonly="${finalMode || !edit}" />
	<br />
	<jstl:if test="${edit}">
		<security:authorize access="hasRole('ADMIN')">
			<acme:submit name="delete" code="followup.delete"
				css="formButton toLeft" />
			<jstl:if test="${!finalMode}">
				<acme:submit name="save" code="followup.save"
					css="formButton toLeft" />
			</jstl:if>

		</security:authorize>
	</jstl:if>

</form:form>


