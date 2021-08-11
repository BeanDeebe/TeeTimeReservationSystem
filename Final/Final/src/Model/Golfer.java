/*
 * CS5004
 * Dean Beebe
 * Final Project: Tee Time Registration System
 * 
 * This file contains the class and methods for registering a tee time with a golfer in the database system.
 */

package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Golfer {
	
	private String name;
	private String phoneNum;
	
	public Golfer(String name, String phoneNum) {
		this.name = name;
		this.phoneNum = phoneNum;
		
	}
	
	public void registerTime(String date, String time) {
		
		try {
			
			Connection connector = DriverManager.getConnection("jdbc:mysql://localhost:3306/applications?allowPublicKeyRetrieval=true&useSSL=false", 
					"root", "Patriots2020!");
		
			String query = "INSERT INTO `registration` (`phone`, `name`, `date registered`, `time registered`)"
					+ " VALUES ('"+ this.phoneNum + "', '" + this.name +"', '"+ date +"', '" + time + "')";
			
			PreparedStatement ps1 = connector.prepareStatement(query);
			
			ps1.executeUpdate();
			
			query = "UPDATE `" + date + "` SET `Available` = 0 WHERE `Times Available` = '"+ time +"'";
			ps1 = connector.prepareStatement(query);
			ps1.executeUpdate();
			
			connector.close();
			
			}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean cancelReservation(String date, String time) {
		try {
			Connection connector = DriverManager.getConnection("jdbc:mysql://localhost:3306/applications?allowPublicKeyRetrieval=true&useSSL=false", 
					"root", "Patriots2020!");
			
			String query = "UPDATE `" + date + "` SET `Available` = 1 WHERE `Times Available` = '" + time + "'";
			String query2 = "DELETE FROM registration WHERE `date registered` = '" + date + "' AND "
					+ "`time registered` = '" + time + "'";
			
			PreparedStatement ps = connector.prepareStatement(query);
			PreparedStatement ps2 = connector.prepareStatement(query2);
			ps.executeUpdate();
			ps2.executeUpdate();
			
			connector.close();
			
			return true;
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	public DefaultTableModel findUserTeeTimes() throws SQLException {
		Connection connector;
		connector = DriverManager.getConnection("jdbc:mysql://localhost:3306/applications?allowPublicKeyRetrieval=true&useSSL=false", "root", "Patriots2020!");
		
		String[] columns = {"Date", "Tee Time"};
		
		String query = "SELECT `date registered`, `time registered` FROM `registration` WHERE `phone` = '" + phoneNum +"'";
		PreparedStatement ps = connector.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		DefaultTableModel table = new DefaultTableModel(columns, 0);
		
		while(rs.next()) {
			String[] line = {rs.getString("date registered"), rs.getString("time registered")};
			table.addRow(line);
		}
		
		return table;
		
	}
	
	public String getPhone() {
		return this.phoneNum;
	}
	
	
	public String getName() {
		return this.name;
	}
	
	

}







//
//
//if (rs > 1) {
//	insert
//}
//else {
//	update
//}
//
//
//
//
