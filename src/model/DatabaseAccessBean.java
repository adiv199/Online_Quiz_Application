package model;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

@ManagedBean (name="databaseAccessBean")
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
	List<String> displayRows;
	
	List<String> tables;
	List<String> selectedTables;
	List<String>columns;
	boolean renderTableList;
	boolean renderQuery;
	static int uin;
	
	boolean userStatus;
	
	@PostConstruct
	public void init() {
	FacesContext context = FacesContext.getCurrentInstance();
	Map <String, Object> m =
	context.getExternalContext().getSessionMap();
	databaseBean = (DatabaseBean) m.get("databaseBean");
	renderQuery=false;
	userStatus=false;
	connectToDatabase();
	/******* display table list on startup*********/
	String mt = listTables();
	/**********************************************************/
	}
	
	
	
	public boolean isUserStatus() {
		return userStatus;
	}



	public void setUserStatus(boolean userStatus) {
		this.userStatus = userStatus;
	}



	public static Connection getConnection() {
		return connection;
	}



	public static void setConnection(Connection connection) {
		DatabaseAccessBean.connection = connection;
	}



	public DatabaseMetaData getDatabaseMetaData() {
		return databaseMetaData;
	}



	public void setDatabaseMetaData(DatabaseMetaData databaseMetaData) {
		this.databaseMetaData = databaseMetaData;
	}



	public Statement getStatement() {
		return statement;
	}



	public void setStatement(Statement statement) {
		this.statement = statement;
	}



	public ResultSet getResultSet() {
		return resultSet;
	}



	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}



	public ResultSetMetaData getResultSetMetaData() {
		return resultSetMetaData;
	}



	public void setResultSetMetaData(ResultSetMetaData resultSetMetaData) {
		this.resultSetMetaData = resultSetMetaData;
	}



	public ResultSet getResult() {
		return result;
	}



	public void setResult(ResultSet result) {
		this.result = result;
	}



	public static int getUin() {
		return uin;
	}



	public static void setUin(int uin) {
		DatabaseAccessBean.uin = uin;
	}



	public ResultSet getRs() {
		return rs;
	}



	public void setRs(ResultSet rs) {
		this.rs = rs;
	}



	public DatabaseBean getDatabaseBean() {
		return databaseBean;
	}



	public void setDatabaseBean(DatabaseBean databaseBean) {
		this.databaseBean = databaseBean;
	}



	public List<String> getDisplayRows() {
		return displayRows;
	}



	public void setDisplayRows(List<String> displayRows) {
		this.displayRows = displayRows;
	}



	public List<String> getSelectedTables() {
		return selectedTables;
	}



	public void setSelectedTables(List<String> selectedTables) {
		this.selectedTables = selectedTables;
	}



	public List<String> getColumns() {
		return columns;
	}



	public void setColumns(List<String> columns) {
		this.columns = columns;
	}



	public boolean isRenderTableList() {
		return renderTableList;
	}



	public void setRenderTableList(boolean renderTableList) {
		this.renderTableList = renderTableList;
	}



	public boolean isRenderQuery() {
		return renderQuery;
	}



	public void setRenderQuery(boolean renderQuery) {
		this.renderQuery = renderQuery;
	}



	public List<String> getTables() {
		return tables;
	}



	public void setTables(List<String> tables) {
		this.tables = tables;
	}



	public String getQuery() {
		return query;
	}



	public void setQuery(String query) {
		this.query = query;
	}



	public void connectToDatabase() {
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
			connection = DriverManager.getConnection(url,daib.getUserName(),daib.getPassword());
		} catch (SQLException e) {
			/******* FOR connection failure*********/	
            FacesContext.getCurrentInstance().addMessage
            (null, new FacesMessage("Connection to Database Failed!"));
            /**********************************************************/
			e.printStackTrace();
		} 
	   // return connection;
	    }
	  
	  
	  public String createTables()
	  {  
		  try
          {
			  statement = connection.createStatement();
			  
			  String createRosterActgTableQuery = "create table if not exists f15g116.f15g116_rosteractg ( "+
			          "uin integer not null ," +
					  "netid char(7) not null ,"+
					  "lastname varchar(50),"+
					  "firstname varchar(50),"+
					  "role varchar(20),"+
					  "primary key (uin)"+
					  ")";
			  
			  String createRosterAdbmsTableQuery = "create table if not exists f15g116.f15g116_rosteradbms ( "+
			          "uin integer not null ," +
					  "netid char(7) not null ,"+
					  "lastname varchar(50),"+
					  "firstname varchar(50),"+
					  "role varchar(20),"+
					  "primary key (uin)"+
					  ")";
			  
			  String createRosterStatsTableQuery = "create table if not exists f15g116.f15g116_rosterstats ( "+
			          "uin integer not null ," +
					  "netid char(7) not null ,"+
					  "lastname varchar(50),"+
					  "firstname varchar(50),"+
					  "role varchar(20),"+
					  "primary key (uin)"+
					  ")";
			  
			  String createCourseTableQuery = "create table if not exists f15g116.f15g116_course ( "+
					  "courseid char(6) not null unique,"+
					  "courseid varchar(20),"+
					  "section varchar(50)"+
					  ")";
			  
			  String createEnrollTableQuery = "create table if not exists f15g116.f15g116_enroll ( "+
					  "uin integer not null,"+
					  "courseid varchar(20) not null,"+
					  "primary key (uin,courseid)"+
					  ")";
			  	  
			  String createAssessmentTableQuery = "create table if not exists f15g116.f15g116_assessment ("+
			          "assessmentid char(2) not null," +
					  "questionid integer not null,"+
					  "questiontype varchar(20),"+
					  "questionstring TEXT,"+
					  "answer TEXT,"+
					  "answererror double precision,"+
					  "courseid varchar(20),"+
					  "primary key(questionid,assessmentid)" +
					  ")";
			  
			  String createUserTableQuery = "create table if not exists f15g116.f15g116_user ("+
					  "userName varchar(50),"+
					  "password varchar(50),"+
					  "lastname varchar(50),"+
					  "firstname varchar(50),"+
					  "role varchar(20),"+
					  "uin integer not null unique,"+
					  "primary key (uin)"+
					  ")";
			  
			  String createResultTableQuery = "create table if not exists f15g116.f15g116_result ("+
					  "uin integer not null,"+
					  "assessmentid char(2) not null,"+
					  "courseid varchar(20),"+
					  "score integer,"+
					  "primary key(uin,assessmentid,courseid)"+
					  ")";
			  
			  String createTrackerTableQuery = "create table if not exists f15g116.f15g116_loginTracker ("+
					  "ipaddress varchar(20),"+
					  "timestamp varchar(30))";
		
			  		statement.executeUpdate(createRosterActgTableQuery);
			  		statement.executeUpdate(createRosterAdbmsTableQuery);
			  		statement.executeUpdate(createRosterStatsTableQuery);
			  		statement.executeUpdate(createUserTableQuery);
			  		statement.executeUpdate(createCourseTableQuery);
			  		statement.executeUpdate(createAssessmentTableQuery);
			  		statement.executeUpdate(createEnrollTableQuery);
			  		statement.executeUpdate(createResultTableQuery);
			  		statement.executeUpdate(createTrackerTableQuery);
			
			  		
               //   statement.close();
                  return "SUCCESS";
          }
          catch (Exception e)
          {
              FacesContext.getCurrentInstance().addMessage
              (null, new FacesMessage("One or more tables could not be created!"));
                  e.printStackTrace();
                  return "FALSE";
          }
	  
	  }
	  
	  public List<Roster> fetchAllData(String course) {
	        List<Roster> rosterTableData = new ArrayList<Roster> ();
	            try {
					statement = connection.createStatement();
					rs=statement.executeQuery("select * from f15g116_roster"+course);
					if (rs==null)
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There is no data currently!"));					}
					else
					{
					while (rs.next()){
						Roster rosterRecord = new Roster();
					
						rosterRecord.setUin(rs.getInt("uin"));
						rosterRecord.setNetId(rs.getString("netid"));
						rosterRecord.setLastName(rs.getString("lastname"));
						rosterRecord.setFirstName(rs.getString("firstname"));
						rosterRecord.setRole(rs.getString("role"));
						
						rosterTableData.add(rosterRecord);
						
					}
					}
					
				}
	            catch (SQLException e) {
	                FacesContext.getCurrentInstance().addMessage
	                (null, new FacesMessage("Some roster record could not be fetched!"));
					e.printStackTrace();
				}
	          /*  finally{
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
	            	
	            }*/
	            return rosterTableData;
	            
		}
	  
	  
	  public ResultSet execQuery(String query) {
			// TODO Auto-generated method stub
			try {
				
				statement = connection.createStatement();
				
				rs=statement.executeQuery(query);
				
			} catch (SQLException e) {
                FacesContext.getCurrentInstance().addMessage
                (null, new FacesMessage("Some error occured in executing the query!"));
				e.printStackTrace();
			}
			return rs;
		}
	  
	  public List<Assess> fetchAssess(String id) {
	        List<Assess> assessTableData = new ArrayList<Assess>();
	            try {
	                                statement = connection.createStatement();
	                                rs=statement.executeQuery("select questionid,questiontype,questionstring,answer,answererror,courseid from f15g116_assessment where assessmentid = '"+ id +"'");
	                                if (rs==null)
	                                {
	                                	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There are no assessments currently!"));	                                }
	                                else
	                                {
	                                	 while (rs.next()){
	                                         Assess assessRecord = new Assess();
	                                 
	                                         assessRecord.setQuestionId(rs.getInt("questionId"));
	                                         assessRecord.setQuestionType(rs.getString("questionType"));
	                                         assessRecord.setQuestionString(rs.getString("questionString"));
	                                         assessRecord.setAnswer(rs.getString("answer"));
	                                         assessRecord.setAnswerError(rs.getDouble("answerError"));
	                                         assessRecord.setCourseName(rs.getString("courseid"));
	                                         
	                                         assessTableData.add(assessRecord);
	                                         
	                                 }
	                            }
	                                	
	                                
	                                
	                        }
	            catch (SQLException e) {
	                FacesContext.getCurrentInstance().addMessage
	                (null, new FacesMessage("Some error occured in fetching assessments!"));
	                                e.printStackTrace();
	                        }
	            /*finally{
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
	                    
	            }*/
	            return assessTableData;
	            
	        }
	  
	  public List<Assess> fetchStudentAssess(String id) {
	        List<Assess> assessTableData = new ArrayList<Assess>();
	            try {
	                                statement = connection.createStatement();
	                                rs=statement.executeQuery("select assessmentid,questionid,questiontype,questionstring from f15g116_assessment where assessmentid = '"+ id +"'");
	                                if (rs==null)
	                                {
	                                	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There are no student access currently!"));	                                }
	                                else
	                                {
	                                	 while (rs.next()){
	                                         Assess assessRecord = new Assess();
	                                 
	                                         assessRecord.setAssessmentId(rs.getString("assessmentid"));
	                                         assessRecord.setQuestionId(rs.getInt("questionId"));
	                                         assessRecord.setQuestionType(rs.getString("questionType"));
	                                         assessRecord.setQuestionString(rs.getString("questionString"));
	                                         	                                         
	                                         assessTableData.add(assessRecord);
	                                         
	                                 }
	                            }
	                                	
	                                
	                                
	                        }
	            catch (SQLException e) {
	                FacesContext.getCurrentInstance().addMessage
	                (null, new FacesMessage("Some error occured in fetching student assessments!"));
	                                e.printStackTrace();
	                        }
	          /*  finally{
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
	                    
	            }*/
	            return assessTableData;
	            
	        }
	  
	  
	  
	  public ArrayList<String> fetchAllCourses() {
		  
		  ArrayList<String> courses = new ArrayList<String>();
	            try {
	                                statement = connection.createStatement();
	                                rs=statement.executeQuery("select distinct courseid from f15g116_course");
	                                if (rs==null)
	                                {
	                                	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There are no courses currently!"));	                                }
	                                else
	                                {
	                                	 while (rs.next()){
	                                        // Assess assessRecord = new Assess();
	                                 
	                                         courses.add(rs.getString("courseid"));
	                                       
	                                         
	                                	 }
	                                	 
	                                }                             	                        
	            }
	            catch (SQLException e) {
	                FacesContext.getCurrentInstance().addMessage
	                (null, new FacesMessage("Some error occured in fetching courses!"));
	                                e.printStackTrace();
	                        }
	            catch (Exception e)
	            {
	            	e.printStackTrace();
	            }
	           /* finally{
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
	                    
	            }*/
	            
	            //Courses c = new Courses();
	           
	            return courses;
	            
	            //return "SUCCESS";
	     }

 public ArrayList<String> fetchMyCourses() {
		  
		  ArrayList<String> courses = new ArrayList<String>();
	            try {
	                                statement = connection.createStatement();
	                          
	                                rs=statement.executeQuery("select courseid from f15g116_enroll where uin ='" + uin +"'");
	                                if (rs==null)
	                                {
	                                	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There are no courses currently!"));	                                }
	                                else
	                                {
	                                	 while (rs.next()){
	                                        // Assess assessRecord = new Assess();
	                                 
	                                         courses.add(rs.getString("courseid"));
	                                       
	                                         
	                                	 }
	                                	 
	                                }                             	                        
	            }
	            catch (SQLException e) {
	                FacesContext.getCurrentInstance().addMessage
	                (null, new FacesMessage("Some error occured in fetching my course!"));
	                                e.printStackTrace();
	                        }
	            catch (Exception e)
	            {
	            	e.printStackTrace();
	            }
	            finally{
	                  /*  try {
	                                        //rs.close();
	                                } catch (SQLException e) {
	                                        // TODO Auto-generated catch block
	                                        e.printStackTrace();
	                                }
	                    try {
	                                       // statement.close();
	                                } catch (SQLException e) {
	                                        // TODO Auto-generated catch block
	                                        e.printStackTrace();
	                                }
	                    */
	            }
	            
	            //Courses c = new Courses();
	           
	            return courses;
	            
	            //return "SUCCESS";
	     }
	  
public ArrayList<String> fetchAllAssess() {
		  
		 ArrayList<String> assessments = new ArrayList<String>();
	            try {
	                                statement = connection.createStatement();
	                                rs=statement.executeQuery("select distinct assessmentid from f15g116_assessment");
	                                if (rs==null)
	                                {
	                                	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There are no assessments currently!"));	                                }
	                                else
	                                {
	                                	 while (rs.next()){
	                                        // Assess assessRecord = new Assess();
	                                 
	                                         assessments.add(rs.getString("assessmentid"));
	                                       
	                                         
	                                	 }
	                   

	                                }
	                                	
	                                
	                                
	            }
	            catch (SQLException e) {
	                FacesContext.getCurrentInstance().addMessage
	                (null, new FacesMessage("Some error occured in fetching all assessments!"));
	                                e.printStackTrace();
	                        }
	            catch (Exception e)
	            {
	            	e.printStackTrace();
	            }
	           /* finally{
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
	                    
	            }*/
	            
	            //Courses c = new Courses();
	           
	            return assessments;
	            
	            //return "SUCCESS";
	     }
	
public ArrayList<String> fetchMyAssess(String course) {
	  
	 ArrayList<String> assessments = new ArrayList<String>();
           try {
                               statement = connection.createStatement();
                               rs=statement.executeQuery("select distinct assessmentid from f15g116_assessment where courseid ='" + course + "'");
                               if (rs==null)
                               {
                            	   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There are no assessments currently!"));                               
                               }
                               else
                               {
                               	 while (rs.next()){
                                       // Assess assessRecord = new Assess();
                                 assessments.add(rs.getString("assessmentid"));
                                             
                               }
                               	 
                               }
                               	
                               
                               
           }
           catch (SQLException e) {
               FacesContext.getCurrentInstance().addMessage
               (null, new FacesMessage("Some error occured in fetching my Assessment!"));
                               e.printStackTrace();
                       }
           catch (Exception e)
           {
           	e.printStackTrace();
           }
         /*  finally{
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
                   
           }*/
           
           //Courses c = new Courses();
          
           return assessments;
           
           //return "SUCCESS";
    }

public ArrayList<Integer> fetchUINs(String course) {
	  
	 ArrayList<Integer> uin = new ArrayList<Integer>();
          try {
                              statement = connection.createStatement();
                              rs=statement.executeQuery("select distinct uin from f15g116_roster" + course);
                              if (rs==null)
                              {
                           	   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There are students currently!"));                               
                              }
                              else
                              {
                              	 while (rs.next()){
                                      // Assess assessRecord = new Assess();
                                uin.add(rs.getInt("uin"));
                                            
                              }
                              	 
                              }
                              	
                              
                              
          }
          catch (SQLException e) {
              FacesContext.getCurrentInstance().addMessage
              (null, new FacesMessage("Some error occured in fetching Students!"));
                              e.printStackTrace();
                      }
          catch (Exception e)
          {
          	e.printStackTrace();
          }
          /*finally{
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
                  
          }*/
          
          //Courses c = new Courses();
         
          return uin;
          
          //return "SUCCESS";
   }
	
 





	  //--------------------------------------------------------
	  
public List<Roster> fetchAllEnrolledStudents(String selected_course) {
		  
	        List<Roster> enrolledstudents = new ArrayList<Roster> ();

        try {
	                                statement = connection.createStatement();
	                                rs=statement.executeQuery("select * from f15g116_roster" + selected_course);        
	                                if (rs==null)
	                                {
	                                	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There are no enrolled students!"));	                                }
	                                else
	                                {
	                                	while (rs.next()){
	                						Roster enrolleddetails = new Roster();
	                					
	                						enrolleddetails.setUin(rs.getInt("uin"));
	                						enrolleddetails.setNetId(rs.getString("netid"));
	                						enrolleddetails.setLastName(rs.getString("lastname"));
	                						enrolleddetails.setFirstName(rs.getString("firstname"));
	                						enrolleddetails.setRole(rs.getString("role"));
	                							                						
	                						enrolledstudents.add(enrolleddetails);
	                						
	                					}	  

	                                }	                                                               
	            }
	            catch (SQLException e) {
	                FacesContext.getCurrentInstance().addMessage
	                (null, new FacesMessage("Some error occured in fetching all students!"));
	                                e.printStackTrace();
	                        }
	        /*    finally{
	                    try {
	                                        rs.close();
	                        } 
	                    catch (SQLException e) {
	                                        // TODO Auto-generated catch block
	                                        e.printStackTrace();
	                    }
	                    try {
	                                        statement.close();
	                    } 
	                    catch (SQLException e) {
	                                        // TODO Auto-generated catch block
	                                        e.printStackTrace();
	                    }
	                    
	            }*/
	            
	            //Courses c = new Courses();
	           
	            return enrolledstudents;
	            
	            //return "SUCCESS";
	 }
	  
	  
	  
//---------------------------------------------------------------
	  
	  
	  public String listTables()
	  {
		  String catalog=null;
		  String schemaPattern=null;
		  String tableNamePattern=null;
		  String[] types=null;
		  tables = new ArrayList<String>();

		try {
			databaseMetaData = connection.getMetaData();
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
            FacesContext.getCurrentInstance().addMessage
            (null, new FacesMessage("Some error occured in listing tables!"));
			e.printStackTrace();
		}
		  renderTableList = true;
		  return "SUCCESS";
	  }
	  
	  public String listColumns()
	  {
		  String catalog=null;
		  String schemaPattern=null;
		  String columnNamePattern=null;
		  columns = new ArrayList<String> ();
		try {
			databaseMetaData = connection.getMetaData();
			for (String table_name : selectedTables)
			{
				rs = databaseMetaData.getColumns(catalog,schemaPattern,table_name,columnNamePattern);
				
				while (rs.next()) {
					columns.add(rs.getString("COLUMN_NAME"));
					            }

			}
		} catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage
            (null, new FacesMessage("Some columns could not be listed!"));
			e.printStackTrace();
		}
		  
		  return "SUCCESS";
	  }
	  
	  /********************* Modified the entire process Query method**********************/	  
	  public String processSQLQuery() {
		  renderQuery=false;
		  userStatus=false;
          try {
          	
				statement = connection.createStatement();
				rs=statement.executeQuery(query);
				resultSetMetaData = rs.getMetaData();
				int no_of_columns = resultSetMetaData.getColumnCount();
				displayRows = new ArrayList<String> ();
				
				
				if (rs==null)
				{
					 renderQuery=false;
					 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There are no rows to display!"));
				}
				else
				{
					

					StringBuilder sb1 = new StringBuilder();
					sb1.append("\t<tr>\n");
					for (int i=1;i<=no_of_columns;i++)
					{
						sb1.append("\t\t<td><b>"+resultSetMetaData.getColumnName(i)+"</b></td>\n");
					}
					sb1.append("\t</tr>\n");
					displayRows.add(sb1.toString());
					while (rs.next()){

						StringBuilder sb = new StringBuilder();
						sb.append("\t<tr>\n");
						for (int i=1;i<=no_of_columns;i++)
						{	
							sb.append("\t\t<td>"+rs.getString(i)+"</td>\n");
						}
						sb.append("\t</tr>\n");
						displayRows.add(sb.toString());
					}
					
				}
				  renderQuery=true;
				
			}
          catch (Exception e) {
				// TODO Auto-generated catch block
        	  renderQuery=false;
				e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There is some error in your query!"));
                

			}
         /* finally{
          	try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
          }*/
        
          return "SUCCESS";
		}
	  
	  public String dropTables()
	  {
		  try {
			statement = connection.createStatement();
			statement.executeUpdate("drop table f15g116.f15g116_rosteractg");
	  		statement.executeUpdate("drop table f15g116.f15g116_rosteradbms");
	  		statement.executeUpdate("drop table f15g116.f15g116_rosterstats");
			statement.executeUpdate("drop table f15g116.f15g116_course");
	  		statement.executeUpdate("drop table f15g116.f15g116_assessment");
	  		statement.executeUpdate("drop table f15g116.f15g116_user");
	  		statement.executeUpdate("drop table f15g116.f15g116_enroll");
	  		statement.executeUpdate("drop table f15g116.f15g116_result");
	  		
	  		
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
			  statement = connection.createStatement();
				        
                  
                  statement.executeUpdate(query);
                 // statement.close();
                  return true;
          }
          catch (Exception e)
          {
                  e.printStackTrace();
                  return false;
          }
	  }
	  
	/*  public static void main(String args[])
	  {
		  DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		  String x;
		  x = dbaccess.dropTables();
		  x = dbaccess.createTables();
	  }
	  
	  */
	 
	  public int calculateScore(List<Assess> assessList, String assessmentid)
	  {
		  try
		  {
		  String query="select questiontype,answer,answererror from f15g116_assessment where assessmentid ='"+assessmentid + "'";
			
		  rs=execQuery(query);
		  int score =0;
		  
		  rs.next();
			
		  for(int i = 0; i < assessList.size(); i++){
				
			  String qType = rs.getString(1);
			  String ans =rs.getString(2);
			  double ansErr = rs.getDouble(3);  
			  	
			 String studentAns = assessList.get(i).getStudentAnswer(); 
			
			 if(studentAns.equals(""))
			 {
				 rs.next();
				 continue;
			 }
				
			 if(qType.equals("Categorical"))
			 {
				
				 if(ans.equalsIgnoreCase(studentAns))
				 {
					 score++; 
				 }
				 rs.next();
			 }
			 else
			 {
				 double d_ans = Double.parseDouble(ans);
				 double d_studentAns = Double.parseDouble(studentAns);
				 
				 if (Math.abs(d_ans - d_studentAns) <= ansErr)
				 {
					 score++;	
				 }
				 rs.next();
			 }	  
		  } 

		  return score;
		  }
		  catch(SQLException e)
		  {
			  e.printStackTrace();
			  return 0;
		  }
	  }
	  public ArrayList<Result> fetchAllResult() {
		  
	        ArrayList<Result> resultTableData = new ArrayList<Result>();
	        
	            try {
					statement = connection.createStatement();
					
					rs=statement.executeQuery("select * from f15g116_result where uin = '"+uin +"'");
					if (rs==null)
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No results to display!"));					}
					else
					{
					while (rs.next()){
						
						Result resultRecord = new Result();
						resultRecord.setUin(rs.getInt("uin"));
						resultRecord.setCourseName(rs.getString("courseid"));
						resultRecord.setAssessmentId(rs.getString("assessmentid"));
						resultRecord.setScore(rs.getInt("score"));
						resultTableData.add(resultRecord);	
					}
					}
				
					
				}
	            catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          /*  finally{
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
	            	
	            }*/
	            return resultTableData;
	            
		}

	  public ArrayList<Result> fetchAllStudentResult(String course) {
		  
	        ArrayList<Result> resultTableData = new ArrayList<Result>();
	        
	            try {
					statement = connection.createStatement();
					
					rs=statement.executeQuery("select * from f15g116_result where courseid = '"+course +"'");
					if (rs==null)
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There are not results currently to display!"));					}
					else
					{
					while (rs.next()){
						
						Result resultRecord = new Result();
						resultRecord.setUin(rs.getInt("uin"));
						resultRecord.setCourseName(rs.getString("courseid"));
						resultRecord.setAssessmentId(rs.getString("assessmentid"));
						resultRecord.setScore(rs.getInt("score"));
						resultTableData.add(resultRecord);	
					}
					}
					
					
				}
	            catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         /*   finally{
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
	            	
	            }*/
	            return resultTableData;
	            
		}

	  public Numerical fetchNumerical(String course, String assessmentid)
	  {
		  int i;
		  Numerical num = new Numerical();
		  
		  ArrayList<Integer> scores = new ArrayList<Integer>();
		  try
		  {
		  String query="select score from f15g116_result where courseid ='"+course + "' and assessmentid ='"+assessmentid+"'";
			
		  rs=execQuery(query);
		  
		  while (rs.next()){
			  scores.add(rs.getInt("score"));
			  
		  }
		
		// Get a DescriptiveStatistics instance
		  DescriptiveStatistics stats = new DescriptiveStatistics();

		  // Add the data from the array
		  for( i = 0; i < scores.size(); i++) {
		          stats.addValue(scores.get(i));
		  }
		  		  
		  num.setMean(stats.getMean());
		  num.setMin(stats.getMin());
		  num.setMax(stats.getMax());
		  num.setQ1(stats.getPercentile(25));
		  num.setQ3(stats.getPercentile(75));
		  num.setMedian(stats.getPercentile(50));
		  num.setVariance(stats.getVariance());
		  num.setStd(stats.getStandardDeviation());
		  
		  return num;
		
		  }
		  
		  catch(Exception e)
		  {
			  return num;
			  //ChartUtilities.saveChartAsPNG(file, chart, width, height);
		  }
	  
	  }
	  
	  public String fetchGraphicalForInstructor_grades(String course, String assessmentid)
	  {
		  int countA,countB,countC,countD, countE;
		  countA = countB = countC = countD = countE = 0;
		  ChartBean charts = new ChartBean();
		  File tempFile;
		  JFreeChart pieChart;
		//  Numerical num = new Numerical();
		  
		  ArrayList<Integer> numStudents = new ArrayList<Integer>();
		  try
		  {
			  String query="select score from f15g116_result where courseid ='"+course + "' and assessmentid ='"+assessmentid+"'";
			  rs=execQuery(query);
		  
		  while (rs.next()){
			  
			  if(rs.getInt("score") >= 18)
				  countA++;
			  else if(rs.getInt("score") >= 16)
				  countB++;
			  else if(rs.getInt("score") >= 14)
				  countC++;
			  else if(rs.getInt("score") >= 12)
				  countD++;
			  else
				  countE++;		  
		  }
		  
		  numStudents.add(countA);	
		  numStudents.add(countB);	
		  numStudents.add(countC);	
		  numStudents.add(countD);	
		  numStudents.add(countE);	
		   
		  pieChart = charts.createPieChart(numStudents);
		  FacesContext context = FacesContext.getCurrentInstance();
		  String path = context.getExternalContext().getRealPath("/charts");
		  tempFile = new File(path + "/PieChart.png");
		  ChartUtilities.saveChartAsPNG(tempFile, pieChart, 600, 450);
		
		  return "TRUE";
		
		  }
		  
		  catch(Exception e)
		  {
			return "FALSE";
			  //return num;
			  //ChartUtilities.saveChartAsPNG(file, chart, width, height);
		  }
	  
	  }
	  public String fetchGraphicalForInstructor_grades_hist(String course, String assessmentid)
	  {
		
		  ChartBean charts = new ChartBean();
		  File tempFile;
		  JFreeChart histogram;
		//  Numerical num = new Numerical();
		  
		 
		  double[] numStudents = new double[5];
		  try
		  {
			  String query="select score from f15g116_result where courseid ='"+course + "' and assessmentid ='"+assessmentid+"'";
			  rs=execQuery(query);
			  int i=0;
		  
		  while (rs.next()){
			  
			  numStudents[i]=rs.getInt("score");
			  i++;
			  
	
		  }
		    
		  histogram = charts.createHistChart(numStudents);
		  FacesContext context = FacesContext.getCurrentInstance();
		  String path = context.getExternalContext().getRealPath("/charts");
		  tempFile = new File(path + "/Histogram.png");
		  ChartUtilities.saveChartAsPNG(tempFile, histogram, 600, 450);
		
		  return "TRUE";
		
		  }
		  
		  catch(Exception e)
		  {
			return "FALSE";
			  //return num;
			  //ChartUtilities.saveChartAsPNG(file, chart, width, height);
		  }
	  
	  }
	  
	  public String fetchGraphicalForInstructor_studentMarks(String course,int uin)
	  {
		 
		  ChartBean charts = new ChartBean();
		  File tempFile;
		  JFreeChart barChart;
		//  Numerical num = new Numerical();
		  
		  ArrayList<Integer> scores = new ArrayList<Integer>();
		  ArrayList<String> assessNames = new ArrayList<String>();
		  
		  try
		  {
			  String query="select assessmentid,score from f15g116_result where courseid ='"+course + "' and uin ="+uin;
			  rs=execQuery(query);
		  
		  while (rs.next()){
			  
			  scores.add(rs.getInt("score"));	
			  assessNames.add(rs.getString("assessmentid"));	
			  		  
		  }
		  
		  barChart = charts.createBarChart(scores,assessNames);
		  FacesContext context = FacesContext.getCurrentInstance();
		  String path = context.getExternalContext().getRealPath("/charts");
		  tempFile = new File(path + "/BarChart.png");
		  ChartUtilities.saveChartAsPNG(tempFile, barChart, 600, 450);
		
		  return "TRUE";
		  }
		  
		  catch(Exception e)
		  {
			return "FALSE";
			  //return num;
			  //ChartUtilities.saveChartAsPNG(file, chart, width, height);
		  }
	  }

	  
	
public String fetchGraphicalForStudent(String course)
	{
	 
	  ChartBean charts = new ChartBean();
	  File tempFile;
	  JFreeChart barChart;
	//  Numerical num = new Numerical();
	  
	  ArrayList<Integer> scores = new ArrayList<Integer>();
	  ArrayList<String> assessNames = new ArrayList<String>();
	  
	  try
	  {
		  String query="select assessmentid,score from f15g116_result where courseid ='"+course + "' and uin ="+uin;
		  rs=execQuery(query);
	  
	  while (rs.next()){
		  
		  scores.add(rs.getInt("score"));	
		  assessNames.add(rs.getString("assessmentid"));	
		  		  
	  }
	  
	  barChart = charts.createBarChart(scores,assessNames);
	  FacesContext context = FacesContext.getCurrentInstance();
	  String path = context.getExternalContext().getRealPath("/charts");
	  tempFile = new File(path + "/BarChartStudent.png");
	  ChartUtilities.saveChartAsPNG(tempFile, barChart, 600, 450);
	
	  return "TRUE";
	  }
	  
	  catch(Exception e)
	  {
		return "FALSE";
		  //return num;
		  //ChartUtilities.saveChartAsPNG(file, chart, width, height);
	  }
	}

	public String addUser()
	{
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
			userStatus=true;
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		return "SUCCESS";
	}

	public void closeConnection()
	{
		try {
			//resultSet.close();
			userStatus=false;
			renderQuery=false;
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void DeleteRoster(String selected_course)
	{
		try
		{
		 String query1="delete from f15g116_roster"+selected_course;
		 statement.executeUpdate(query1);
		 
		 String query2="delete from f15g116_enroll where courseid='"+selected_course+"'";
		 statement.executeUpdate(query2);
		 
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
}
