<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:f="http://java.sun.com/jsf/core" xmlns:t="http://myfaces.apache.org/tomahawk" xmlns:h="http://java.sun.com/jsf/html" version="2.0">
    <jsp:directive.page language="java"
        contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" />
    <jsp:text>
        <![CDATA[ <?xml version="1.0" encoding="ISO-8859-1" ?> ]]>
    </jsp:text>
    <jsp:text>
        <![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
    </jsp:text>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Enrol Students</title>
</head>
<body>
<f:view>

<h:form>
<h:outputText value="Login Time:" style="color:green" />
<h:outputText value="#{loginBean.login_date}" style="color:green" />
<br/>
<h:outputText value="Machine IP Address:" style="color:green" />
<h:outputText value="#{loginBean.clientIp}" style="color:green" />			
<div id="title" align="center"><h1>View Registered Students</h1></div>
<div id="Back" align="center"><h4><a href="Instructor.jsp">Home</a></h4></div>
</h:form>

<h:form>
		<h:messages styleClass="errorMessage" style="color:red" />

<h:panelGrid columns="2" cellspacing = "2" cellpadding = "10" >

<h:commandButton type="submit" action= "#{actionBeanFile.displayCourses}" value="View Courses" />
   
    <h:selectOneMenu value="#{actionBeanFile.selected_course}">
    <f:selectItems value="#{actionBeanFile.courselist}" />
	</h:selectOneMenu>
</h:panelGrid> 
<br/>
<br/>

<h:panelGrid columns="2" cellspacing = "2" cellpadding = "10" >
<h:commandButton type="submit" action= "#{actionBeanFile.displayEnrolledStudents}" value="View Enrolled Students" />

<h:commandButton type="submit" action= "#{actionBeanFile.resetRoster}" value="Reset" />
</h:panelGrid>

</h:form>
	<t:dataTable id = "dataTable" value="#{actionBeanFile.rosterList}" var="rowNumber" rendered="#{actionBeanFile.renderList}"
		border="1" cellspacing="0" cellpadding="1"
		columnClasses="columnClass1 border"
		headerClass="headerClass"
		footerClass="footerClass"
		rowClasses="rowClass2"
		styleClass="dataTableEx"
		width="800">

<h:column>
	<f:facet name="header">
	<h:outputText>UIN</h:outputText>
	</f:facet>
	<h:outputText value="#{rowNumber.uin}"/>
</h:column>

<h:column>
	<f:facet name="header">
	<h:outputText>Netid</h:outputText>
	</f:facet>
	<h:outputText value="#{rowNumber.netId}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>Lastname</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.lastName}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>Firstname</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.firstName}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>Role</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.role}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>Status</h:outputText>
</f:facet>
<h:outputText
value="#{rowNumber.status}"/>
</h:column>

</t:dataTable>
    
</f:view>
</body>
</html>
</jsp:root>