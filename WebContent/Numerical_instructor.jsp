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
<title>Numericals</title>
</head>
<body>
<f:view>
<h:form>	
<h:outputText value="Login Time:" style="color:green" />
<h:outputText value="#{loginBean.login_date}" style="color:green" />
<br/>
<h:outputText value="Machine IP Address:" style="color:green" />
<h:outputText value="#{loginBean.clientIp}" style="color:green" />		
<div id="title" align="center"><h1>Students' Numerical Analysis</h1></div>
<div id="Back" align="center"><h4><a href="Instructor.jsp">Home</a></h4></div>
</h:form>

<h:form>
		<h:messages styleClass="errorMessage" style="color:red" />
<h:panelGrid columns="2">
<h:commandButton type="submit" action= "#{actionBeanFile.displayCourses}" value="View Courses" />
   
    <h:selectOneMenu value="#{actionBeanFile.selected_course}">
    <f:selectItems value="#{actionBeanFile.courselist}" />
	</h:selectOneMenu>
</h:panelGrid> 
<br/>

<h:panelGrid columns="2" cellspacing = "2" cellpadding = "10">
<h:commandButton type="submit" action= "#{actionBeanFile.displayMyAssessments}" value="View Assessments" />


<h:selectOneMenu value="#{actionBeanFile.selected_assess}">
  <f:selectItems value="#{actionBeanFile.assessNames}" />
</h:selectOneMenu>

</h:panelGrid> 
<br/>

<h:panelGrid columns="2" cellspacing = "2" cellpadding = "10">
<h:commandButton type="submit" action= "#{actionBeanFile.processNumericalSummary}" value="Data" />

<h:commandButton type="submit" action= "#{actionBeanFile.resetNumerical}" value="Reset" />
</h:panelGrid>

<div id="T1" align="Left"><h4>5 Number Summary :</h4></div>
<t:dataTable id = "dataTable" value="#{actionBeanFile.numbers}" var="rowNumber" rendered="#{actionBeanFile.renderNumbers}"
		border="1" cellspacing="0" cellpadding="1"
		columnClasses="columnClass1 border"
		headerClass="headerClass"
		footerClass="footerClass"
		rowClasses="rowClass2"
		styleClass="dataTableEx"
		width="800">

<h:column>
	<f:facet name="header">
	<h:outputText>Minimum</h:outputText>
	</f:facet>
	<h:outputText value="#{rowNumber.min}"/>
</h:column>

<h:column>
	<f:facet name="header">
	<h:outputText>Quartile 1</h:outputText>
	</f:facet>
	<h:outputText value="#{rowNumber.q1}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>Median</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.median}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>Quartile 3</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.q3}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>Maximum</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.max}"/>
</h:column>

</t:dataTable>

<div id="T2" align="Left"><h4>Measures of Central Tendency :</h4></div>
<t:dataTable id = "dataTable2" value="#{actionBeanFile.numbers}" var="rowNumber" rendered="#{actionBeanFile.renderNumbers}"
		border="1" cellspacing="0" cellpadding="1"
		columnClasses="columnClass1 border"
		headerClass="headerClass"
		footerClass="footerClass"
		rowClasses="rowClass2"
		styleClass="dataTableEx"
		width="800">


<h:column>
<f:facet name="header">
<h:outputText>Mean</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.mean}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>Median</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.median}"/>
</h:column>

</t:dataTable>

<div id="T3" align="Left"><h4>Measures of Variation :</h4></div>
<t:dataTable id = "dataTable3" value="#{actionBeanFile.numbers}" var="rowNumber" rendered="#{actionBeanFile.renderNumbers}"
		border="1" cellspacing="0" cellpadding="1"
		columnClasses="columnClass1 border"
		headerClass="headerClass"
		footerClass="footerClass"
		rowClasses="rowClass2"
		styleClass="dataTableEx"
		width="800">

<h:column>
<f:facet name="header">
<h:outputText>Variance</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.variance}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>Standard Deviation</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.std}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>Range</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.max-rowNumber.min}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>IQR</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.q3 - rowNumber.q1}"/>
</h:column>

</t:dataTable>

</h:form>

</f:view>
</body>
</html>
</jsp:root>