package Model;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.DatabaseMetaData;


/**
 * TimeLoader
 * 
 * this class is comprised of two String fields. The class loads the contents of the csv file passed into it into
 * a mySQL database, which then get populated into a JTable in the View.
 * 
 * @author deanbeebe
 *
 */
public class TimeLoader {


	private String date;

	/**
	 * TimeLoader - a two-argument constructor creating a TimeLoader object. Then, calls this class's "loadTimesToDB" method, which
	 * takes in the contents of the CSV file and loads each line into a MySQL database.
	 * @param date a string representing a date in the format YYYY-MM-DD.
	 * @param csvTimes a String in the form of a CSV file name(where tee times are kept).
	 * @throws IOException 
	 */
	public TimeLoader(String date) throws IOException {

		this.date = date;

		loadTimesToDB(readTimes());
	}


	

	public DefaultTableModel fillTable() throws SQLException {

		Connection connector;
		connector = DriverManager.getConnection("jdbc:mysql://localhost:3306/applications?allowPublicKeyRetrieval=true&useSSL=false", "root", "Patriots2020!");
		String[] columns = {"Date", "Times Available"};


		String query = "SELECT `Date`, `Times Available` FROM `" + this.date + "` WHERE `Available` = '1'";

		PreparedStatement ps = connector.prepareStatement(query);


		ResultSet rs = ps.executeQuery();
		DefaultTableModel dt = new DefaultTableModel(columns, 0);

		dt.setRowCount(0);

		while(rs.next()) {
			String[] line = {rs.getString("Date"), rs.getString("Times Available")};
			dt.addRow(line);

		}
		connector.close();

		return dt;

	}
	/**
	 * readTimes - this method reads this object's CSV file and places the contents into an ArrayList of type String.
	 * 
	 * @return List of Strings representing the line by line contents of the object's CSV file.
	 * @throws IOException 
	 */
	private List<String> readTimes() throws IOException {

		Path path = FileSystems.getDefault().getPath("/Users/deanbeebe/CS5004_beebed/Final/Final/times.txt");

		List<String> times = Files.readAllLines(path);

		return times;
	}

	/**
	 * loadTimesToDB - makes a connection to our MySQL database, iterates through the List and places each time into a column in the 
	 * database.
	 * 
	 * This function creates a new table for each date (if one has not been created already).
	 *  
	 * @param times List of Strings, gathered from the readTimes() method.
	 * 
	 * 
	 */ 
	private void loadTimesToDB(List<String> times) {

		try {

			Connection connector;
			connector = DriverManager.getConnection("jdbc:mysql://localhost:3306/applications?allowPublicKeyRetrieval=true&useSSL=false", "root", "Patriots2020!");	
			DatabaseMetaData data = (DatabaseMetaData) connector.getMetaData();
			ResultSet rs = data.getTables(null, null, this.date, new String[] {"TABLE"});

			if(rs.next()) {
				return;
			}
			else {
				String query = "CREATE TABLE `"+ this.date + "` LIKE TeeSheet";
				PreparedStatement ps = connector.prepareStatement(query);
				ps.executeUpdate();
				for (String time : times) {
					
					query = "INSERT INTO `" + this.date + "` (`Date`, `Times Available`, `Available`)"
							+ " VALUES ('"+ this.date + "', '" + time +"', '1')";
					ps = connector.prepareStatement(query);
					ps.executeUpdate();
					
				}
			}


			connector.close();
		} 
		catch (SQLException e) {
			return;
		}

	}


	public String getDate() {
		return this.date;
	}



}
