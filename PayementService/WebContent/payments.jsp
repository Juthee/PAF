<%@page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
	
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payments Management</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/payment.js"></script>

</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Payments Management V10.1</h1>
				
				<form id="formPayment" name="formPayment">
					Appointment: <input id="Appointment" name="Appointment" type="text"
						class="form-control form-control-sm"> <br> 
					Payment Amount: <input id="PaymentAmount" name="PaymentAmount" type="text"
						class="form-control form-control-sm"> <br> 
					Patient ID: <input id="PatientID" name="PatientID" type="text"
						class="form-control form-control-sm"> <br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidPaymentIDSave" name="hidPaymentIDSave" value="">
				</form>
				
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				
				<div id="divPaymentsGrid">
					<%
						Payment PaymentObj = new Payment();
					out.print(PaymentObj.readPaymentsTable());
					%>
				</div>
				
				
			</div>
		</div>
	</div>
</body>
</html>