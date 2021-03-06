<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:t="http://myfaces.apache.org/tomahawk" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" version="2.0">
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
<title>Upload Assessment</title>
</head>
<body>
<f:view>
<h:form>
<h:outputText value="Login Time:" style="color:green" />
<h:outputText value="#{loginBean.login_date}" style="color:green" />
<br/>
<h:outputText value="Machine IP Address:" style="color:green" />
<h:outputText value="#{loginBean.clientIp}" style="color:green" />			
<div id="title" align="center"><h1>Upload Assessment</h1></div>
<div id="Back" align="center"><h4><a href="Instructor.jsp">Home</a></h4></div>
</h:form>

<h:form enctype="multipart/form-data">
 <h:messages styleClass="errorMessage" style="color:red" />
 <h:panelGrid columns="2">
<h:commandButton type="submit" action= "#{actionBeanFile.displayCourses}" value="View Courses" />
   
    <h:selectOneMenu value="#{actionBeanFile.selected_course}">
    <f:selectItems value="#{actionBeanFile.courselist}" />
	</h:selectOneMenu>
</h:panelGrid> 

<br/>
<br/>
<h:panelGrid columns="2">
<h:outputLabel value="Select Assessment to upload:" style="color:blue" />
<t:inputFileUpload id="fileUpload" label="File to upload"
storage="default" value="#{actionBeanFile.uploadedFile}"
size="60"/>
</h:panelGrid>
<br/>

<h:panelGrid columns="2">
<h:outputLabel value="File label:" style="color:blue" />
<h:inputText id="fileLabel"
value= "#{actionBeanFile.fileLabel}"
size="60" />
</h:panelGrid>

<br/>
 
<h:outputLabel value=" "/>
<h:commandButton id="upload"
action= "#{actionBeanFile.processUploadAssessment}"
value="Submit"/>

<br/>
<h:outputText value='Assessment Upload Successfull' style="color:blue" rendered='#{actionBeanFile.uploadAssessment}'/>
<br/>
<br/>

 <h:panelGrid columns="4" cellspacing = "2" cellpadding = "10" >
<h:commandButton type="submit" action= "#{actionBeanFile.displayAssessments}" value="View Assessments" />
   
    <h:selectOneMenu value="#{actionBeanFile.selected_assess}">
    <f:selectItems value="#{actionBeanFile.assessNames}" />
	</h:selectOneMenu>

<h:commandButton type="submit" action= "#{actionBeanFile.processAssessDisplay}" value="View Selected Assessment" />
 
<h:commandButton type="submit" action= "#{actionBeanFile.resetAssessment}" value="Reset" />
</h:panelGrid>
<br/>
<br/>
</h:form>

<t:dataTable id = "dataTable" value="#{actionBeanFile.assessList}" var="rowNumber" rendered="#{actionBeanFile.renderAssessList}"
		border="1" cellspacing="0" cellpadding="1"
		columnClasses="columnClass1 border"
		headerClass="headerClass"
		footerClass="footerClass"
		rowClasses="rowClass2"
		styleClass="dataTableEx"
		width="800">
	<h:column>
		<f:facet name="header">
			<h:outputText>Question Id</h:outputText>
		</f:facet>
		<h:outputText value="#{rowNumber.questionId}"/>
	</h:column>
<h:column>
<f:facet name="header">
<h:outputText>Question Type</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.questionType}"/>
</h:column>
<h:column>
<f:facet name="header">
<h:outputText>Question String</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.questionString}"/>
</h:column>
<h:column>
<f:facet name="header">
<h:outputText>Answer</h:outputText>
</f:facet>
<h:outputText value="#{rowNumber.answer}"/>
</h:column>
<h:column>
<f:facet name="header">
<h:outputText>Answer Error</h:outputText>
</f:facet>
<h:outputText
value="#{rowNumber.answerError}"/>
</h:column>

<h:column>
<f:facet name="header">
<h:outputText>Course</h:outputText>
</f:facet>
<h:outputText
value="#{rowNumber.courseName}"/>
</h:column>

</t:dataTable>
</f:view>
</body>
</html>
</jsp:root>