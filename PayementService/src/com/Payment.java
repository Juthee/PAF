package com;

import java.sql.*;

public class Payment {

	public Connection connect() {

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf", "root", "");
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;

	}
	
	

	public String readPaymentsTable() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connectingto the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Payment ID</th><th>Appointment</th><th>Payment Amount</th>"
					+ "<th>Patient ID</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from PaymentsTable";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String PaymentID = Integer.toString(rs.getInt("PaymentID"));
				String Appointment = rs.getString("Appointment");
				String PaymentAmount = Double.toString(rs.getDouble("PaymentAmount"));
				String PatientID = rs.getString("PatientID");
				// Add into the html table
				output += "<tr><td><input id='hidPaymentIDUpdate'name='hidPaymentIDUpdate'type='hidden' value='" + PaymentID
						+ "'>" + "</td>";
				output += "<td>" + Appointment + "</td>";
				output += "<td>" + PaymentAmount + "</td>";
				output += "<td>" + PatientID + "</td>";
				// buttons
				output += "<td><input name='btnUpdate'type='button' value='Update'class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove'type='button' value='Remove'class='btnRemove btn btn-danger'data-Paymentid='"
						+ PaymentID + "'>" + "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the PaymentsTable.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	

	public String insertPayment( String appointment, String amount, String pid) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into PaymentsTable(`PaymentID`,`Appointment`,`PaymentAmount`,`PatientID`)"
					+ " values (?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, appointment);
			preparedStmt.setDouble(3, Double.parseDouble(amount));
			preparedStmt.setString(4, pid);
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newPaymentsTable = readPaymentsTable();
			output = "{\"status\":\"success\", \"data\": \"" + newPaymentsTable + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the Payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	
	public String updatePayment(String ID, String appointment, String amount, String pid) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE PaymentsTable SET Appointment=?,PaymentAmount=?,PatientID=? WHERE PaymentID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, appointment);
			preparedStmt.setDouble(2, Double.parseDouble(amount));
			preparedStmt.setString(3, pid);
			preparedStmt.setInt(4, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newPaymentsTable = readPaymentsTable();
			output = "{\"status\":\"success\", \"data\": \"" + newPaymentsTable + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the Payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	

	public String deletePayment(String PaymentID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from PaymentsTable where PaymentID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(PaymentID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newPaymentsTable = readPaymentsTable();
			output = "{\"status\":\"success\", \"data\": \"" + newPaymentsTable + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the Payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}