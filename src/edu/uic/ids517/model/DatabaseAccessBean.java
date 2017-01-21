package edu.uic.ids517.model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import edu.uic.ids517.model.DatabaseBean;


@ManagedBean
@SessionScoped
public class DatabaseAccessBean {

	static Connection connection;
	DatabaseMetaData databaseMetaData;
	Statement statement;
	ResultSet resultSet, rs;
	ResultSetMetaData resultSetMetaData;
	ResultSet result;
	DatabaseBean databaseBean;
	String query;
	List<String> displayR;
	
	@PostConstruct
	public void init() {
	FacesContext context = FacesContext.getCurrentInstance();
	Map <String, Object> m =
	context.getExternalContext().getSessionMap();
	databaseBean = (DatabaseBean) m.get("databaseBean");
	}
	
	
	
	public String getQuery() {
		return query;
	}



	public void setQuery(String query) {
		this.query = query;
	}



	public List<String> getDisplayR() {
		return displayR;
	}



	public void setDisplayR(List<String> displayR) {
		this.displayR = displayR;
	}



	public Connection connectToDatabase() {
	    Connection conn = null;
	    DatabaseBean daib = new DatabaseBean();
	    String jdbcDriver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://" +
		daib.getDbmsHost() + ":3306/" +
		daib.getDatabaseSchema();
	    	try {
				Class.forName(jdbcDriver);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      try {
			conn = DriverManager.getConnection(url,daib.getUserName(),daib.getPassword());
			System.out.println("Connection succeeded");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    return conn;
	    }
	  
	  
	  public String createTables()
	  {  
		  try

          {
			  statement = connectToDatabase().createStatement();
			  
			  String createRosterTableQuery = "create table if not exists f15g116.roster ( "+
					  "netid integer not null unique,"+
					  "lastname varchar(50),"+
					  "firstname varchar(50),"+
					  "role varchar(20),"+
					  "status varchar(2),"+
					  "courseid varchar(7),"+
					  "primary key (netid)"+
					  ")";
			  
			  String createCourseTableQuery = "create table if not exists f15g116.course ( "+
					  "courseid varchar(7) not null unique,"+
					  "coursename varchar(7),"+
					  "section varchar(50),"+
					  ")";
			  
			  String createAssessmentTableQuery = "create table if not exists f15g116.assessment"+
					  "assessmentid integer not null unique,"+
					  "pointsperquestion integer,"+
					  "startdate DATE,"+
					  "enddate DATE"+
					  "timeinminutes integer,"+
					  "courseid varchar(7),"+
					  "primary key (assessmentid)"+
					  ")";
			  
			  String createQuestionTableQuery = "create table if not exists f15g116.question"+
					  "questionid integer not null unique,"+
					  "questiontype varchar(20),"+
					  "questionstring TEXT,"+
					  "answer TEXT"+
					  "answererror double precision,"+
					  "primary key (questionid)"+
					  ")";
					  
			  		statement.executeUpdate(createRosterTableQuery);
			  		statement.executeUpdate(createCourseTableQuery);
			  		statement.executeUpdate(createAssessmentTableQuery);
			  		statement.executeUpdate(createQuestionTableQuery);
                  statement.close();
                  return "SUCCESS";
          }
          catch (Exception e)
          {
                  e.printStackTrace();
                  return "FALSE";
          }
	  
	  }
	  
	  public List<Roster> fetchAllData(String table_name) {
	        List<Roster> rosterTableData = new ArrayList<Roster> ();
	            try {
					statement = connectToDatabase().createStatement();
					rs=statement.executeQuery("select * from "+table_name);
					if (rs==null)
					{
						System.out.println("No records to display");
					}
					else
					{
					while (rs.next()){
						Roster rosterRecord = new Roster();
					
						rosterRecord.setNetId(rs.getInt("netid"));
						rosterRecord.setLastName(rs.getString("lastname"));
						rosterRecord.setFirstName(rs.getString("firstname"));
						rosterRecord.setCourseId(rs.getString("courseid"));
						rosterRecord.setRole(rs.getString("role"));
						rosterRecord.setStatus(rs.getString("status"));
						
						rosterTableData.add(rosterRecord);
						
					}
					}
					
				}
	            catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            finally{
	            	try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	try {
						statement.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	
	            }
	            return rosterTableData;
	            
		}
	  
	  public ArrayList<String> listTables()
	  {
		  String catalog=null;
		  String schemaPattern=null;
		  String tableNamePattern=null;
		  String[] types=null;
		  ArrayList<String> tables = new ArrayList<String>(); 

		try {
			databaseMetaData = connectToDatabase().getMetaData();
			rs = databaseMetaData.getTables(catalog,schemaPattern,tableNamePattern,types);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  try {
			while(rs.next()) {
				    tables.add(rs.getString("TABLE_NAME"));
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return tables;
	  }
	  
	  public ArrayList<String> listColumns(ArrayList<String> tables)
	  {
		  String catalog=null;
		  String schemaPattern=null;
		  String columnNamePattern=null;
		  ArrayList<String> columns = new ArrayList<String> ();
		try {
			databaseMetaData = connectToDatabase().getMetaData();
			for (String table_name : tables)
			{
				rs = databaseMetaData.getColumns(catalog,schemaPattern,table_name,columnNamePattern);
				
				while (rs.next()) {
					columns.add(rs.getString("COLUMN_NAME") +" "
					                        + rs.getString("TYPE_NAME") + " "
					                        + rs.getString("COLUMN_SIZE"));
					            }

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  return columns;
	  }
	  
	  public String processSQLQuery() {
		  
	            try {
					statement = connectToDatabase().createStatement();
					rs=statement.executeQuery(query);
					resultSetMetaData = rs.getMetaData();
					int no_of_columns = resultSetMetaData.getColumnCount();
					
					for (int i=1;i<=no_of_columns;i++)
					{
						displayR.add(resultSetMetaData.getColumnName(i));
					}
					if (rs==null)
					{
						System.out.println("No records to display");
					}
					else
					{
					while (rs.next()){
						for (int i=1;i<=no_of_columns;i++)
						{
						displayR.add(rs.getString(i));
						
						}
						
					}
					}
					
					Iterator i = displayR.iterator();
					while (i.hasNext())
					{
						System.out.println(i.next());
					}
				}
	            catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            finally{
	            	try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	try {
						statement.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	
	            }
	          
	            return "SUCCESS";
		}
	  
	  public String dropTables()
	  {
		  try {
			statement = connectToDatabase().createStatement();
			statement.executeUpdate("drop table course");
	  		statement.executeUpdate("drop table roster");
	  		statement.executeUpdate("drop table assessment");
	  		statement.executeUpdate("drop table question");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return "SUCCESS";
	  	
	  }
	  
	  public boolean deleteFromDatabase(String query)
	  {
		  try

          {
			  statement = connectToDatabase().createStatement();
				        
                  System.out.println(query);
                  
                  statement.executeUpdate(query);
                  statement.close();
                  return true;
          }
          catch (Exception e)
          {
                  e.printStackTrace();
                  return false;
          }
	  
	  }
	  
	  public static void main(String args[])
	  {
		  
	  }
}
	  
	
