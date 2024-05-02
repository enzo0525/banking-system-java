package bank_system;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseHandler {
	private Connection connection = null;
	private Statement statement = null;
	
	public DatabaseHandler() {
		try {
			Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS BANK"
           		 	  + "(ACCNUM 	TEXT PRIMARY KEY	NOT NULL,"
           		 	  + "FNAME 		TEXT		NOT NULL,"
           		 	  + "LNAME 		TEXT		NOT NULL,"
           		 	  + "GENDER 	TEXT 		NOT NULL,"
           		 	  + "BALANCE 	FLOAT		NOT NULL)";
            statement.executeUpdate(sql);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void createUserInDatabase(User user) {
	    String accNum = user.getAccountNumber();
	    String fname = user.fname;
	    String lname = user.lname;
	    String gender = user.gender;
	    double balance = user.getBalance();

	    try {
	        String newUserSQL = "INSERT INTO BANK (ACCNUM, FNAME, LNAME, GENDER, BALANCE)" +
	                "VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement preparedStatement = connection.prepareStatement(newUserSQL);
	        preparedStatement.setString(1, accNum);
	        preparedStatement.setString(2, fname);
	        preparedStatement.setString(3, lname);
	        preparedStatement.setString(4, gender);
	        preparedStatement.setDouble(5, balance);
	        preparedStatement.executeUpdate();
	        System.out.println("New user created successfully");
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    } finally {
	        closeResources(null, statement, null);
	    }
	}

	public User findUserByAccNum(String accNumInput) {
	    User user = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    try {
	        String query = "SELECT * FROM BANK WHERE ACCNUM = ?";
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, accNumInput);
	        resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            String accNum = resultSet.getString("ACCNUM");
	            String fname = resultSet.getString("FNAME");
	            String lname = resultSet.getString("LNAME");
	            String gender = resultSet.getString("GENDER");
	            double balance = resultSet.getDouble("BALANCE");

	            user = new User(accNum, fname, lname, gender, balance);
	        }
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    } finally {
	        closeResources(resultSet, preparedStatement, null);
	    }
	    return user;
	}

	public void deleteUserFromDatabase(User user) {
	    String accNum = user.getAccountNumber();
	    PreparedStatement preparedStatement = null;
	    try {
	        String query = "DELETE FROM BANK WHERE ACCNUM = ?";
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, accNum);
	        int rowsAffected = preparedStatement.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("User with account number " + accNum + " deleted from database.");
	        } else {
	            System.out.println("No user found with account number: " + accNum);
	        }
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    } finally {
	        closeResources(null, preparedStatement, null);
	    }
	}

	public void updateBalanceInDatabase(User user) {
	    String accNum = user.getAccountNumber();
	    double newBalance = user.getBalance();
	    PreparedStatement preparedStatement = null;
	    try {
	        String query = "UPDATE BANK SET BALANCE = ? WHERE ACCNUM = ?";
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setDouble(1, newBalance);
	        preparedStatement.setString(2, accNum);
	        preparedStatement.executeUpdate();
	        System.out.println("Balance updated successfully for account number " + accNum);
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    } finally {
	        closeResources(null, preparedStatement, null);
	    }
	}

	private void closeResources(ResultSet rs, Statement stmt, Connection conn) {
	    try {
	        if (rs != null) rs.close();
	        if (stmt != null) stmt.close();
	        if (conn != null) conn.close();
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
	}

}
