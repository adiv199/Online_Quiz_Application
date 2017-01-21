<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" version="2.0">
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
<title>Insert title here</title>
</head>
<body>
<f:view>
<h:form>
<div id="title" align="center"><h1>Students' Graphical Analysis</h1></div>
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

<div id="title" align="left"><h3>Grades</h3></div>

<h:panelGrid columns="2" cellspacing = "2" cellpadding = "10">
<h:commandButton type="submit" action= "#{actionBeanFile.displayMyAssessments}" value="View Assessments" />


<h:selectOneMenu value="#{actionBeanFile.selected_assess}">
  <f:selectItems value="#{actionBeanFile.assessNames}" />
</h:selectOneMenu>

</h:panelGrid> 
<br/>

<h:panelGrid columns="4" cellspacing = "2" cellpadding = "10">
<h:commandButton type="submit" action= "#{actionBeanFile.processGraphicalSummaryInstructorGrade}" value="Pie Chart, Histogram" />
<h:commandButton type="submit" action= "#{actionBeanFile.resetPie}" value="Reset" />

</h:panelGrid>

<br/>

<div id="title" align="left"><h3>Student Marks</h3></div>

<h:panelGrid columns="2" cellspacing = "2" cellpadding = "10">
<h:commandButton type="submit" action= "#{actionBeanFile.displayUINs}" value="View Students" />


<h:selectOneMenu value="#{actionBeanFile.selected_uin}">
  <f:selectItems value="#{actionBeanFile.studentUINs}" />
</h:selectOneMenu>

</h:panelGrid> 
<br/>

<h:panelGrid columns="2" cellspacing = "2" cellpadding = "10">
<h:commandButton type="submit" action= "#{actionBeanFile.processGraphicalSummaryInstructorStudentMarks}" value="Bar Chart" />
<h:commandButton type="submit" action= "#{actionBeanFile.resetBar}" value="Reset" />
</h:panelGrid>

<h:graphicImage value="/charts/PieChart.png" height="450" width="600" rendered="#{actionBeanFile.renderPieChart}"/>
<h:graphicImage value="/charts/Histogram.png" height="450" width="600" rendered="#{actionBeanFile.renderHistogram}"/>
<h:graphicImage value="/charts/BarChart.png" height="450" width="600" rendered="#{actionBeanFile.renderBarChart}"/>


</h:form>

</f:view>
</body>
</html>
</jsp:root>