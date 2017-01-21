package model;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.apache.commons.el.parser.ParseException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.io.*; 
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner; 

@ManagedBean
@SessionScoped
public class ActionBeanFile {

private UploadedFile uploadedFile;
private String fileLabel;
private Roster roster;
private List<Roster> rosterList;
private boolean renderList;
private boolean uploadRoster;

private Assess assess;
private List<Assess> assessList;
private boolean renderAssessList;
private boolean uploadAssessment;

private Numerical numbers;
private boolean renderNumbers;

private boolean renderPieChart;
private boolean renderBarChart;
private boolean renderHistogram;

DatabaseAccessBean dbaccess;

private ArrayList<Integer> studentUINs;
private int selected_uin;

private ArrayList<String> assessNames;
private String selected_assess;

private String selected_course;
private ArrayList<String> courselist;

private ArrayList<Result> resultList;
private boolean renderResultList;

public boolean isRenderHistogram() {
	return renderHistogram;
}
public void setRenderHistogram(boolean renderHistogram) {
	this.renderHistogram = renderHistogram;
}
public boolean isRenderPieChart() {
	return renderPieChart;
}
public void setRenderPieChart(boolean renderPieChart) {
	this.renderPieChart = renderPieChart;
}
public boolean isRenderBarChart() {
	return renderBarChart;
}
public void setRenderBarChart(boolean renderBarChart) {
	this.renderBarChart = renderBarChart;
}
public Numerical getNumbers() {
	return numbers;
}
public void setNumbers(Numerical numbers) {
	this.numbers = numbers;
}
public boolean isRenderNumbers() {
	return renderNumbers;
}
public void setRenderNumbers(boolean renderNumbers) {
	this.renderNumbers = renderNumbers;
}
public boolean isUploadRoster() {
	return uploadRoster;
}
public void setUploadRoster(boolean uploadRoster) {
	this.uploadRoster = uploadRoster;
}
public boolean isUploadAssessment() {
	return uploadAssessment;
}
public void setUploadAssessment(boolean uploadAssessment) {
	this.uploadAssessment = uploadAssessment;
}


public ArrayList<Integer> getStudentUINs() {
	return studentUINs;
}
public void setStudentUINs(ArrayList<Integer> studentUINs) {
	this.studentUINs = studentUINs;
}
public int getSelected_uin() {
	return selected_uin;
}
public void setSelected_uin(int selected_uin) {
	this.selected_uin = selected_uin;
}
public ArrayList<Result> getResultList() {
	return resultList;
}
public void setResultList(ArrayList<Result> resultList) {
	this.resultList = resultList;
}
public boolean isRenderResultList() {
	return renderResultList;
}
public void setRenderResultList(boolean renderResultList) {
	this.renderResultList = renderResultList;
}
public String getSelected_course() {
	return selected_course;
}
public void setSelected_course(String selected_course) {
	this.selected_course = selected_course;
}
public ArrayList<String> getCourselist() {
	return courselist;
}
public void setCourselist(ArrayList<String> courselist) {
	this.courselist = courselist;
}
public String getSelected_assess() {
	return selected_assess;
}
public void setSelected_assess(String selected_assess) {
	this.selected_assess = selected_assess;
}
public ArrayList<String> getAssessNames() {
	return assessNames;
}
public void setAssessNames(ArrayList<String> assessNames) {
	this.assessNames = assessNames;
}
public Assess getAssess() {
	return assess;
}
public void setAssess(Assess assess) {
	this.assess = assess;
}
public List<Assess> getAssessList() {
	return assessList;
}
public void setAssessList(List<Assess> assessList) {
	this.assessList = assessList;
}
public boolean isRenderAssessList() {
	return renderAssessList;
}
public void setRenderAssessList(boolean renderAssessList) {
	this.renderAssessList = renderAssessList;
}
public ActionBeanFile() {
	rosterList=new ArrayList<Roster>();
	renderList=false;
	assessList=new ArrayList<Assess>();
	renderAssessList=false;
	courselist = new ArrayList<String>();
	resultList=new ArrayList<Result>();
	renderResultList=false;
	uploadRoster = false;
	uploadAssessment = false;
	
}

public String resetPie()
{
	renderPieChart=false;
	
	return "SUCCESS";
}

public String resetBar()
{
	renderBarChart=false;
	
	return "SUCCESS";
}



public String resetNumerical()
{
	renderNumbers=false;
	
	return "SUCCESS";
}


public String resetRoster()
{
	rosterList.clear();
	renderList=false;
	uploadRoster = false;
	return "SUCCESS";
}

public String resetAssessment()
{
	renderAssessList=false;
	assessList.clear();
	uploadAssessment = false;
	return "SUCCESS";
}

public String resetResult()
{
	renderResultList=false;
	resultList.clear();
	return "SUCCESS";
}

public Roster getRoster() {
	return roster;
}

public void setRoster(Roster roster) {
	this.roster = roster;
}

public List<Roster> getRosterList() {
	return rosterList;
}

public void setRosterList(List<Roster> rosterList) {
	this.rosterList = rosterList;
}

public boolean isRenderList() {
	return renderList;
}

public void setRenderList(boolean renderList) {
	this.renderList = renderList;
}


public String getFileLabel() {
	return fileLabel;
}

public void setFileLabel(String fileLabel) {
	this.fileLabel = fileLabel;
}

public UploadedFile getUploadedFile() {
	return uploadedFile;
}


public void setUploadedFile(UploadedFile uploadedFile) {
	this.uploadedFile = uploadedFile;
}

@PostConstruct
public void init()
{
	FacesContext context = FacesContext.getCurrentInstance();
	Map <String,Object> m = context.getExternalContext().getSessionMap();
	roster=(Roster)m.get("roster");
	assess=(Assess)m.get("assess");
	dbaccess = new DatabaseAccessBean();
	dbaccess.connectToDatabase();
	displayCourses();
}

public List<String> parseCSV(String data)
{
	List<String> tokens=new ArrayList<String>();
	StringBuilder buffer=new StringBuilder();

	int parenthesesCounter=0;
	int quoteCounter=0;

	
	for (char c : data.toCharArray()){
		
	    if (c=='(') parenthesesCounter++;
	    if (c==')') parenthesesCounter--;
	    if (c=='"') quoteCounter++; 
	    if (c==',' && parenthesesCounter==0 && (quoteCounter==2 || quoteCounter==0)){
	        //lets add token inside buffer to our tokens
	        tokens.add(buffer.toString());
	        //now we need to clear buffer  
	        buffer.delete(0, buffer.length());
	    }
	    else 
	    {
	    	if (c != '"')
	    	{
	    	   
	    		buffer.append(c);
	    	}
	    }
	}
	//lets not forget about part after last comma
	tokens.add(buffer.toString());

	return tokens;
}

@SuppressWarnings("finally")
public String processUploadAssessment() {
	
	//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
	PreparedStatement preparedStatement = null; 
	Connection conn = DatabaseAccessBean.connection;
	FacesContext context = FacesContext.getCurrentInstance();
	String path = context.getExternalContext().getRealPath("/uploads");
	try{ 
		int i=0;
		        
		//CREATING A PREPARED STATEMENT 
		String insertTableSQL = "INSERT INTO f15g116_assessment" 
		+ "(assessmentid,questionid,questiontype,questionstring,answer,answererror,courseid) VALUES" 
		+ "(?,?,?,?,?,?,?)"; 

		preparedStatement = conn.prepareStatement(insertTableSQL); 

		//RETRIEVING INFORMATION FROM CSV FILE 

		//opening a file input stream 
	
		File tempFile;
		
		String fileName = FilenameUtils.getBaseName(uploadedFile.getName()); 
		String extension = FilenameUtils.getExtension(uploadedFile.getName());
		
       	tempFile = new File(path + "/" + fileName+"."+extension);
       	
       	FileOutputStream fos = new FileOutputStream(tempFile);
     // or uploaded to disk
       	fos.write(uploadedFile.getBytes());
       	fos.close();
       	
    	
		BufferedReader reader = new BufferedReader(new FileReader(tempFile)); 
		
		String line = "";
        //Create the file reader
		reader.readLine();
		List<String> tokens;
        //Read the file line by line
		
		
        while ((line = reader.readLine()) != null)
        {
        	tokens = parseCSV(line);
        	preparedStatement.setString(1,fileLabel);
        	preparedStatement.setString(7,selected_course);
         	for (i=0;i<tokens.size();i++)
    		{	
         		if(i==0)
    		    preparedStatement.setInt(2,Integer.parseInt(tokens.get(0))); 
         		if(i==1)
    			preparedStatement.setString(3,tokens.get(1)); 
         		if(i==2)
    			preparedStatement.setString(4,tokens.get(2));
         		if(i==3)
    			preparedStatement.setString(5,tokens.get(3));
         		if(i==4)
         		{
         			if(tokens.get(4).isEmpty())
         				preparedStatement.setDouble(6,0);
         			else
         				preparedStatement.setDouble(6,Double.parseDouble(tokens.get(4))); 
         		}		
       		}	
         	
         	

         	preparedStatement.executeUpdate(); 
        }

		preparedStatement.close(); 
		
		
		System.out.println("Data imported"); 

		reader.close(); //closing CSV reader 

		}
		
	    catch (SQLException e){ 
		e.printStackTrace(); 
		}
	    catch (IOException e){ 
		e.printStackTrace(); 
		}
	    catch(Exception e)
        {
	    	e.printStackTrace();
	    	return "FAIL";
        }
		//finally
		//{//CLOSING CONNECTION 
		//try
		//{ 
		//if(dbaccess.getStatement()!=null) 
		//conn.close(); 
		uploadAssessment = true;
		//return "SUCCESS";
		//}
		//catch(SQLException se){
		//}// do nothing 
		//try{ 
		//if(conn!=null) 
		//conn.close(); 
		uploadAssessment = true;
		return "SUCCESS";
		//}
		//catch(SQLException se){ 
		//se.printStackTrace();
		// return "FAIL";
		//} 
		
		//} 
		 
        }

@SuppressWarnings("finally")
public String processUploadRoster() {
	
	//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
	PreparedStatement preparedStatement = null; 
	PreparedStatement preparedStatement1 = null; 
	Connection conn = DatabaseAccessBean.connection;
	FacesContext context = FacesContext.getCurrentInstance();
	String path = context.getExternalContext().getRealPath("/uploads");
	
	try{ 
		
		dbaccess.DeleteRoster(selected_course);
			        
		//CREATING A PREPARED STATEMENT 
		String insertTableSQL = "INSERT INTO f15g116_roster"+selected_course 
		+ "(uin,netid,lastname,firstname,role) VALUES" 
		+ "(?,?,?,?,?)"; 

		preparedStatement = conn.prepareStatement(insertTableSQL); 

		String insertEnrollSQL = "INSERT INTO f15g116_enroll" 
				+ "(uin,courseid) VALUES" 
				+ "(?,?)"; 

		preparedStatement1 = conn.prepareStatement(insertEnrollSQL); 

		//RETRIEVING INFORMATION FROM CSV FILE 
		//opening a file input stream 
	
		File tempFile;
		
		String fileName = FilenameUtils.getBaseName(uploadedFile.getName()); 
		String extension = FilenameUtils.getExtension(uploadedFile.getName());
		
       	tempFile = new File(path + "/" + fileName+"."+extension);
    	FileOutputStream fos = new FileOutputStream(tempFile);
        // or uploaded to disk
          	fos.write(uploadedFile.getBytes());
          	fos.close();
      // 	FileOutputStream output = new FileOutputStream(tempFile);
        //IOUtils.copy(uploadedFile.getInputStream(), output);

        //IOUtils.closeQuietly(output);
		BufferedReader reader = new BufferedReader(new FileReader(tempFile)); 

		String line = null; //line read from csv 
		Scanner scanner = null; //scanned line 

		reader.readLine(); //omits the first line 

		//READING FILE LINE BY LINE AND UPLOADING INFORMATION TO DATABASE 
		while((line = reader.readLine()) != null){ 
		scanner = new Scanner(line); 
		scanner.useDelimiter(","); 
	
		while(scanner.hasNext()){ 
		preparedStatement.setInt(1,Integer.parseInt(scanner.next())); 
		preparedStatement.setString(2, scanner.next()); 
		preparedStatement.setString(3, scanner.next()); 
		preparedStatement.setString(4, scanner.next()); 
		preparedStatement.setString(5, scanner.next()); 
		
		}

		preparedStatement.executeUpdate(); 
		} 

		preparedStatement.close(); 

		BufferedReader reader1 = new BufferedReader(new FileReader(tempFile)); 

		reader1.readLine(); //omits the first line 
         
		//READING FILE LINE BY LINE AND UPLOADING INFORMATION TO DATABASE 
		while((line = reader1.readLine()) != null){ 
		scanner = new Scanner(line); 
		scanner.useDelimiter(","); 
	
		while(scanner.hasNext()){
			
		preparedStatement1.setInt(1,Integer.parseInt(scanner.next())); 
		preparedStatement1.setString(2,selected_course);
		
		while(scanner.hasNext())
		{
			//skip values
			scanner.next();
		}
		
		}

		preparedStatement1.executeUpdate(); 
		} 
		
		reader1.close(); //closing CSV reader 
		reader.close(); //closing CSV reader 

		}
		
	    catch (SQLException e){ 
		e.printStackTrace(); 
		}
	    catch (IOException e){ 
		e.printStackTrace(); 
		}
	    catch(Exception e)
        {
	    	e.printStackTrace();
	    	return "FAIL";
        }
		//finally
		//{//CLOSING CONNECTION 
		//try
		//{ 
		//if(dbaccess.getStatement()!=null) 
		//conn.close();
		uploadRoster = true;
		//return "SUCCESS";
		//}
		//catch(SQLException se){
		//}// do nothing 
		//try{ 
		//if(conn!=null) 
		//conn.close(); 
		uploadRoster = true;
		return "SUCCESS";
		//}
		//catch(SQLException se){ 
		//se.printStackTrace();
		 //return "FAIL";
		//} 
		
		//} 
		 
        }

@SuppressWarnings("finally")
public String processRosterDisplay() {
	
	//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
	
		try
		{
			rosterList = dbaccess.fetchAllData(selected_course);
			
			if(rosterList!=null)
			{
				renderList=true;
			}
			return "SUCCESS";
			
		}
		catch(Exception e)
		{
	        return "FALSE";
		}
		
}

@SuppressWarnings("finally")
public String processAssessDisplay() {
	
	//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
	String id = selected_assess;
		try
		{
			assessList = dbaccess.fetchAssess(id);
			
			if(assessList!=null)
			{
				renderAssessList=true;
			}
			return "SUCCESS";
			
		}
		catch(Exception e)
		{
	        return "FALSE";
		}
		
}

public String processStudentAssessDisplay() {
	
	//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
	String id = selected_assess;
		try
		{
			assessList = dbaccess.fetchStudentAssess(id);
			
			if(assessList!=null)
			{
				renderAssessList=true;
			}
			return "SUCCESS";
			
		}
		catch(Exception e)
		{
	        return "FALSE";
		}
		
}

public String displayAssessments()
{
		//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		setAssessNames(dbaccess.fetchAllAssess());
		return "SUCCESS";
}

public String displayCourses()
{
		//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		setCourselist(dbaccess.fetchAllCourses());
		return "SUCCESS";
}

public String myCourses()
{
		//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		setCourselist(dbaccess.fetchMyCourses());
		return "SUCCESS";
}

public String displayMyAssessments()
{
		//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		setAssessNames(dbaccess.fetchMyAssess(selected_course));
		return "SUCCESS";
}

public String displayUINs()
{
		//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		setStudentUINs(dbaccess.fetchUINs(selected_course));
		return "SUCCESS";
}

public String displayEnrolledStudents()
{
		//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		rosterList = dbaccess.fetchAllEnrolledStudents(selected_course);
		if(rosterList.size() >= 1)
		{
			renderList=true;
		}
		return "SUCCESS";
		
}




public String grade() {

	//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
	
	
	try{ 
			        
		
		int score = dbaccess.calculateScore(assessList,selected_assess);
		
		
		renderAssessList=false;
		
		PreparedStatement preparedStatement = null; 
		Connection conn = DatabaseAccessBean.connection;
		
		//CREATING A PREPARED STATEMENT 
		String insertResultTable = "INSERT INTO f15g116_result" 
		+ "(uin,assessmentid,courseid,score) VALUES" 
		+ "(?,?,?,?)"; 

		preparedStatement = conn.prepareStatement(insertResultTable); 
		
		
		preparedStatement.setInt(1,dbaccess.uin); 			
		preparedStatement.setString(2,selected_assess); 		
		preparedStatement.setString(3,selected_course); 
		preparedStatement.setInt(4,score); 
			
		preparedStatement.executeUpdate(); 
		
		preparedStatement.close(); 
			
		
	/*	preparedStatement.close(); 
		
		*/
		
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
		return "FALSE";
	}
	
	return "redirect";
}

public String processResultDisplay() {
	
	//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
	
	try
		{
			resultList = dbaccess.fetchAllResult();
			
			if(resultList!=null)
			{
				renderResultList=true;
			}
			return "SUCCESS";
			
		}
		catch(Exception e)
		{
	        return "FALSE";
		}
		
}

public String processStudentResultDisplay() {
	
	//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
	
	try
		{
			resultList = dbaccess.fetchAllStudentResult(selected_course);
			
			if(resultList!=null)
			{
				renderResultList=true;
			}
			return "SUCCESS";
			
		}
		catch(Exception e)
		{
	        return "FALSE";
		}
		
}

public String processNumericalSummary() {
	

	//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
	
	try
		{
			numbers = dbaccess.fetchNumerical(selected_course,selected_assess);
			renderNumbers = true;
			
		
			
			return "SUCCESS";
		}	
		catch(Exception e)
		{
	        return "FALSE";
		}
		
}

public String processGraphicalSummaryInstructorGrade() {
	

	//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
	
	try
		{
		
			String st = dbaccess.fetchGraphicalForInstructor_grades(selected_course,selected_assess);
			st = dbaccess.fetchGraphicalForInstructor_grades_hist(selected_course,selected_assess);
			
			renderPieChart = true;
			renderHistogram = true;
						return "SUCCESS";
			
		}
		catch(Exception e)
		{
	        return "FALSE";
		}
		
}

public String processGraphicalSummaryInstructorStudentMarks() {
	

	//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
	
	try
		{
		    
			String st = dbaccess.fetchGraphicalForInstructor_studentMarks(selected_course,selected_uin);
			//renderNumbers = true;
			//System.out.println("abcdbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
			
			
			
			//ChartBean charts = new ChartBean();
			//JFreeChart piechart = charts.createPieChart(scores);
		    renderBarChart = true;
			return "SUCCESS";
			
		}
		catch(Exception e)
		{
	        return "FALSE";
		}
		
}

public String processGraphicalSummaryStudent() {
	

	//DatabaseAccessBean dbaccess = new DatabaseAccessBean();
	
	try
		{
		   
		
			String st = dbaccess.fetchGraphicalForStudent(selected_course);
			//renderNumbers = true;
			//System.out.println("abcdbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
			
			
			
			//ChartBean charts = new ChartBean();
			//JFreeChart piechart = charts.createPieChart(scores);
		    renderBarChart = true;
			return "SUCCESS";
			
		}
		catch(Exception e)
		{
	        return "FALSE";
		}
		
}

}
