package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * This class is used to connect to the derby embedded database
 * 
 * @author zoe
 *
 */
public class ConnectMethod {

    private static boolean ignoreSQLException(String sqlState) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	private String url;

	/**
	 * 
	 * @param url
	 *            the database address
	 */
	public ConnectMethod(String url) {
		this.url = url;
	}
        public void createDateBase()  {
            
		try  {Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                    Connection connect = DriverManager.getConnection(url+";create=true");
                

			System.out.println("Successfully connect to the database");
		} catch (SQLException e) {
			System.out.println("SQL Exception" + e);
		} catch (ClassNotFoundException e){
                System.out.println("ClassNotFoundException" + e);
                }
        }
	/**
	 * This method is used to get the connection to the embedded database
	 * 
	 * @throws ClassNotFoundException
	 */
        
        public void loadtxtStudent(String path) {
		ArrayList<String[]> storedata = new ArrayList<String[]>();

		storedata = Readtxt.read(path);
		System.out.println(storedata.size());
		for (String[] s : storedata) {
			insertRowStudent(Integer.parseInt(s[0]), s[1],s[2],s[3],s[4],s[5],s[6],Integer.parseInt(s[7]));
		}
	}
	public void getCon() throws ClassNotFoundException {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		try (Connection connect = DriverManager.getConnection(url)) {

			System.out.println("Successfully connect to the database");
		} catch (SQLException e) {
			System.out.println("SQL Exception" + e);
                        e.printStackTrace();
		}
	}
        
       


	/**
	 * This method is used to create table in the database
	 * 
	 * @param sql
	 *            the sql query to create the database
	 */
	public void createTable(String sql) {
		try (Connection connect = DriverManager.getConnection(url); Statement statement = connect.createStatement();) {

			statement.executeUpdate(sql);
			System.out.println("successfullly create new table");

		} catch (SQLException e) {
			System.out.println("SQL Exception" + e);
		}

	}

	/**
	 * This method is used to insert one row in the table
	 * 
	 * @param lastname
	 * @param firstname
	 * @param position
	 * @param salary
	 * @param age
	 */
        
        // 每一个table的insertrow method都是不一样的
 
// primaryID INT,andrewNumID INT,andrewStringID VARCHAR(50),Firstname VARCHAR(50),Lastname VARCHAR(50),gender VARCHAR(50),nationality VARCHAR(50),program VARCHAR(50))
	public void insertRowStudent(int andrewNumID ,String andrewStringID,String firstname,String lastname, String gender, String nationality,String program,int status) {
		String insertTableSQL = "INSERT INTO Student" + "(andrewNumID, andrewStringID, Firstname, Lastname,gender,nationality,program,status) VALUES"
				+ "(?,?,?,?,?,?,?,?)";
                String query="select * from Student where andrewNumID= "+andrewNumID;
                try (Connection connect1 = DriverManager.getConnection(url);
				Statement stmt = connect1.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			if (!rs.next()) {
                           
		try {
			Connection connect = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connect.prepareStatement(insertTableSQL);
			preparedStatement.setInt(1, andrewNumID);
			preparedStatement.setString(2, andrewStringID);
			preparedStatement.setString(3, firstname);
			preparedStatement.setString(4, lastname);
			preparedStatement.setString(5, gender);
                        preparedStatement.setString(6, nationality);
                        preparedStatement.setString(7, program);
                        preparedStatement.setInt(8, status);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}}else{
                            System.out.println("andrew id already exsit");
                        }
                }catch(SQLException e){
                        System.out.println(e.getMessage());
                        
                        }}
	

	/**
	 * This method is to display all the result after executing a sql
	 * 
	 * @param query
	 */
       // every table is different
        
        // primaryID INT,andrewNumID INT,andrewStringID VARCHAR(50),Firstname VARCHAR(50),Lastname VARCHAR(50),gender VARCHAR(50),nationality VARCHAR(50),program VARCHAR(50))

	public  void displayStudent(String query) {
		try (Connection connect = DriverManager.getConnection(url);
				Statement stmt = connect.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
                    int i=0;
                  
			while (rs.next()) {
                            i+=1;
				int primaryID = rs.getInt("primaryID");
				int andrewNumID = rs.getInt("andrewNumID");
                                String andrewStringID = rs.getString("andrewStringID");
				String Firstname = rs.getString("Firstname");
				String Lastname = rs.getString("Lastname");
				String gender = rs.getString("gender");
                                String nationality = rs.getString("nationality");
                                 String program = rs.getString("program");
                                 int status=rs.getInt("status");
                                
                                
                                

				System.out.println("andrewNumID: "+andrewNumID+"  andrewStringID: "+andrewStringID+" Firstname: " + Firstname + " " +"Lastname: "+ Lastname + "\n" + "program: " + program+" status: "+ status
						);
			} // end of while
                        if(i==0){
                        System.out.print("This table is empty");}
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e);
		}

	}
        public  void displayVisit(String query) {
		try (Connection connect = DriverManager.getConnection(url);
				Statement stmt = connect.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
                    int i=0;
                  
			while (rs.next()) {
                            i+=1;
				int primaryID = rs.getInt("primaryID");
				int andrewNumID = rs.getInt("andrewNumID");
                                int reason=rs.getInt("reason");
                                Timestamp visitTime=rs.getTimestamp("visitTime");
                                
                                
                                

				System.out.println("andrewNumID: "+andrewNumID+" reason: "+reason+" visitTime: "+visitTime);
			} // end of while
                        if(i==0){
                        System.out.print("This table is empty");}
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e);
		}

	}
        	public  Student getStudent(String query) {
                    Student student=null;
		try (Connection connect = DriverManager.getConnection(url);
				Statement stmt = connect.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
      int i=0;
			while (rs.next()) {
				int primaryID = rs.getInt("primaryID");
				int andrewNumID = rs.getInt("andrewNumID");
                                String andrewStringID = rs.getString("andrewStringID");
				String Firstname = rs.getString("Firstname");
				String Lastname = rs.getString("Lastname");
				String gender = rs.getString("gender");
                                String nationality = rs.getString("nationality");
                                String program = rs.getString("program");
                                int status=rs.getInt("status");
                                student=new Student(primaryID,andrewNumID,andrewStringID,Firstname,Lastname,gender,nationality,program,status);
                                i+=1;
                                
                                

				
			} // end of while
                        if(i>1){
                            System.out.println("Warning: more than one row are selected");
                        }
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e);
		}
            return student;

	}
                
                public void updateStudent(int andrewNumID,String andrewStringID,String firstname,String lastname, String gender, String nationality,String program) {
                    String updateTableSQL = "UPDATE Student" + " SET andrewStringID = ?, firstname = ?,  lastname = ?,  gender = ? , nationality = ?, program = ? WHERE andrewNumID = ?";
//                    System.out.print(updateTableSQL);
		try {
			Connection connect = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connect.prepareStatement(updateTableSQL);
			preparedStatement.setString(1, andrewStringID);
			preparedStatement.setString(2, firstname);
			preparedStatement.setString(3, lastname);
			preparedStatement.setString(4, gender);
                        preparedStatement.setString(5, nationality);
                        preparedStatement.setString(6, program);
                        preparedStatement.setInt(7, andrewNumID);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			System.out.print(e.getMessage());

		}
                    }
                public void refreshTable(String tablename,String createtablequery){
                    String sql="DROP TABLE  "+tablename;
                    try {
			Connection connect = DriverManager.getConnection(url);
				Statement stmt = connect.createStatement();
                                
				stmt.executeUpdate(sql);
                                
                                

		} catch (SQLException e) {

			System.out.print(e.getMessage());

		}
                    createTable(createtablequery);
                               
                    
                }
                
                //primaryID,andrewNumID INT,reason VARCHAR(50),visitTime DATE
                public void insertRowVisit(int andrewNumID,int reason){
                    String insertTableSQL = "INSERT INTO Visit" + "(andrewNumID, reason, visitTime) VALUES"
				+ "(?,?,CURRENT_TIMESTAMP)";
                    try {
			Connection connect = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connect.prepareStatement(insertTableSQL);
			preparedStatement.setInt(1, andrewNumID);
                        preparedStatement.setInt(2, reason);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.getMessage());
                    
                }
              
            }
        
    
                    
}              




