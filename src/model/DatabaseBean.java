package model;

public class DatabaseBean {
	private String userName;
	private String password;
	private String dbms;
	private String dbmsHost;
	private String databaseSchema;
	
	
	
	public DatabaseBean() {
		super();
		this.userName = "root";
		this.password = "uiceadmysql";
		this.dbms = "mysql";
		this.dbmsHost = "localhost";
		this.databaseSchema = "f15g116";
	}

	public DatabaseBean(String userName, String password, String dbms, String dbmsHost, String databaseSchema) {
		super();
		this.userName = userName;
		this.password = password;
		this.dbms = dbms;
		this.dbmsHost = dbmsHost;
		this.databaseSchema = databaseSchema;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDbms() {
		return dbms;
	}
	public void setDbms(String dbms) {
		this.dbms = dbms;
	}
	public String getDbmsHost() {
		return dbmsHost;
	}
	public void setDbmsHost(String dbmsHost) {
		this.dbmsHost = dbmsHost;
	}
	public String getDatabaseSchema() {
		return databaseSchema;
	}
	public void setDatabaseSchema(String databaseSchema) {
		this.databaseSchema = databaseSchema;
	}
	
	

}
