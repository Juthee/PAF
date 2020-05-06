$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validatePaymentForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "PaymentsAPI",
		type : type,
		data : $("#formPayment").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onPaymentSaveComplete(response.responseText, status);
		}
	});
	
});

//remove----
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "PaymentsAPI",
		type : "DELETE",
		data : "PaymentID=" + $(this).data("Paymentid"),
		dataType : "text",
		complete : function(response, status) {
			onPaymentDeleteComplete(response.responseText, status);
		}
	});
});


function onPaymentSaveComplete(response, status) {
	if (status == "success") {
		
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success") 
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divPaymentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
		
	} else if (status == "error") 
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	
	$("#hidPaymentIDSave").val("");
	$("#formPayment")[0].reset();
}


function onPaymentDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divPaymentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}


// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidPaymentIDSave").val(
					$(this).closest("tr").find('#hidPaymentIDUpdate').val());
			$("#Appointment").val($(this).closest("tr").find('td:eq(1)').text());
			$("#PaymentAmount").val($(this).closest("tr").find('td:eq(2)').text());
			$("#PatientID").val($(this).closest("tr").find('td:eq(3)').text());
		});




// CLIENTMODEL=========================================================================
function validatePaymentForm() {
	
	// Appointment
	if ($("#Appointment").val().trim() == "") {
		return "Insert Appointment.";
	}
	9
	// Amount
	if ($("#PaymentAmount").val().trim() == "") {
		return "Insert Payment Amount.";
	}
	// is numerical value
	var tmpPrice = $("#PaymentAmount").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for Payment Amount.";
	}
	// convert to decimal price
	
	$("#PaymentAmount").val(parseFloat(tmpPrice).toFixed(2));
	
	// Patient ID
	if ($("#PatientID").val().trim() == "") {
		return "Insert Patient ID.";
	}
	return true;
}