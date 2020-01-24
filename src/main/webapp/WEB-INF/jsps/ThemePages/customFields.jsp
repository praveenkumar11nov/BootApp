<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="themes/easydropdown.css" />

<style>
.popupscroll {
	max-height: 386px;
	max-width: 100%;
	overflow-x: scroll;
	margin-left: -171px;
	padding-left: 0px;
	margin-top: 12px;
	margin-right: 25px;
	margin-left: 0px;
}
</style>

<script>
	$(document).ready(function() {
		setSchema();
	});
/* 
	function setCustomFields(schema){
		$.ajax({
			url : "./getCustomFieldsFromSchema?schema=" + schema,
			method : "POST",
			success : function(response) {
				if (response.length > 0) {
					var html="";
					var passResponse="";
					for (var i = 0; i < response.length; i++) {
						if(response[i][0]!='id'){
							if(response[i][0].includes('table')){
								html+="<input id='"+response[i][0]+"' name='"+response[i][0]+"' placeholder='"+response[i][0]+"' readonly='readonly' style='width: 220px;'><br>"
							}else{
								html+="<input id='"+response[i][0]+"' name='"+response[i][0]+"' placeholder='"+response[i][0]+"' style='width: 220px;'><br>"
							}
							passResponse+=response[i][0]+"-";	
						}
					}
					html+="<br>	<input id='savecolumn' type='button' value='ADD' onclick='saveCustomFiled(\""+passResponse+"\");' style='width: 220px;'>";
					$("#formfield").html(html);
				}
			}
		});
	}
 */
	function setSchema(){
		$.ajax({
			url : "./getAllSchema",
			method : "POST",
			success : function(response) {
				if (response.length > 0) {
					var html = "<option value='select'>Select Schema</option>";
					for (var i = 0; i < response.length; i++) {
						html += "<option value='"+response[i]+"'>" + response[i] + "</option>";
					}
					$("#allschema").html(html);
				}
			}
		});
	}
	
	function getAllTablesFromSchema(schema) {
		//setCustomFields(schema);
		$("#allColumns").html("select a table");
		$.ajax({
			url : "./getAllTablesFromSchema?schema=" + schema,
			method : "POST",
			success : function(response) {
				if (response.length > 0) {
					var html = "<option value='select'>Select Table</option>";
					for (var i = 0; i < response.length; i++) {
						html += "<option value='"+response[i]+"'>" + response[i] + "</option>";
					}
					$("#alltables").html(html);
				}else{
					$("#alltables").html("<option value='select'>Select Table</option>");
				}
			}
		});
	}

	function getAllColumnsFromTables(table) {
		if(table=='select'){
			$("#allColumns").html("select a table");
			$("#addColumnDiv").html("Add a Column");
		}
		else{
			getTabledetails($("#allschema").val(),table);
			$("#tablename").val(table);
			$.ajax({
				url : "./getAllColumnsFromTable?schema=" + $("#allschema").val() + "&table=" + table,
				method : "POST",
				success : function(response) {
					if (response.length > 0) {
						var html="";
						for (var i = 0; i < response.length; i++) {
							html+=(i+1)+". " + response[i] + "</br>";
						}
						$("#addColumnDiv").html("Add Column in <b style='font-size: large;'>" + table + "</b>");
						$("#allColumns").html(html);
					}
				}
			});
		}
	}

	function saveCustomFiled(){
		var schema=$("#allschema").val();
		var table=$("#tablename").val();
		var column=$("#columnname").val();
		var datatype=$("#datatype").val();
		var length=$("#length").val();
		//alert(schema +"-"+ table +"-"+ column +"-"+ datatype +"-"+ length);
		$.ajax({
			url : "./saveCustomField?schema="+schema+"&table="+table+"&column="+column+"&datatype="+datatype+"&length="+length,
			method : "POST",
			success : function(response) {
				getAllColumnsFromTables(table);
				$("#columnname").val("");
				$("#length").val("");
				alert(response);
				getTabledetails(schema,table);
			}
		});
	}

	function getTabledetails(schema,table){
		var html="<table class='table table-bordered'><tr>";
		$.ajax({
			url : "./getAllColumnsFromTable?schema=" + schema + "&table=" + table,
			method : "POST",
			async : false,
			success : function(response) {
				if (response.length > 0) {
					for (var i = 0; i < response.length; i++) {
						html+="<td>"+response[i].split(" [")[0]+"</td>";
					}
					html+="</tr>";
				}
				else{
					alert("columns not available");
					return false;
				}
			}
		});
		
		$.ajax({
			url : "./getTableData?schema="+schema+"&table="+table,
			method : "POST",
			success : function(response) {
				for(var i=0;i<response.length;i++){
					html+="<tr>";
					for(var j=0;j<response[i].length;j++){
						html+="<td>"+response[i][j]+"</td>";
					}
					html+="</tr>";
				}
				html+="</table>";
				$("#tabledetails").html(html);
			}
		});
	}

	function showDynamicForm() {
		var html="<div class='modal-header'>"+
					"<h4 class='modal-title'>Entry</h4>"+
					"<button type='button' class='close' data-dismiss='modal'>&times;</button>"+
				 "</div>"+
				 "<div class='modal-body'>"+
					"<table id='fillForm'>";

		//var html="<table id='fillForm'>";
		$.ajax({
			url : "./getAllColumnsFromTable?schema=" + $("#allschema").val() + "&table=" + $("#alltables").val(),
			method : "POST",
			async : false,
			success : function(response) {
				var allIds="";
				if (response.length > 0) {
					for (var i = 0; i < response.length; i++) {
						if(response[i].split(" [")[0]!='id'){
							var nameAndId = (response[i].split(" [")[0]).trim();
							allIds+=nameAndId+"-";
							
							html+="<tr><td align='center'>"+nameAndId+"</td><td><input id="+nameAndId+
							" type='text' style='padding-left: 70px;padding-left: 70px;margin-left: 10px;'></td></tr>";
						}
					}
					  html+="</table>"+
							"</div>"+
							"<div class='modal-footer'>"+
								"<button type='button' class='btn btn-default' onclick='saveInTable(\""+allIds+"\");'>Submit</button>"+
								"<button type='button' class='btn btn-default' data-dismiss='modal'>Close</button>"+
							"</div>";
					$("#modalContents").html(html);
					$('#dynamicForm').modal('show');
				}
				else{
					alert("columns not available");
					return false;
				}
			}
		});
	}

	function saveInTable(allIds){
		var paramList = '{ "schema" : "'+$("#allschema").val()+'" , "table" : "'+$("#alltables").val()+'" , ';
		var ids=allIds.split("-");
		for(var i=0;i<ids.length;i++){
			var name=ids[i];
			if(name.length>0){
				var value=$("#"+ids[i]).val();
				paramList+=' "'+name+'" : "'+value+'",';
			}
		}
		paramList=paramList.replace(/.$/,"}");
		
		$.ajax({
			url : "./saveDataInTable",
			method : "POST",
			data : {paramList:paramList},
			async : false,
			success : function(response) {
				getAllColumnsFromTables($("#alltables").val());
				//getTabledetails();
				$('#dynamicForm').modal('hide');
				alert(response);
			}
		});
	}
</script>

<div class="container">
	<br><br>
	<div class="row">
		<div class="col-xl-4 col-md-6 mb-4">
			<div class="card border-left-primary shadow h-100 py-2">
				<div class="card-body">
					<div class="row no-gutters align-items-center">
						<div class="col mr-2">
							<div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
								Schema(s)
							</div>
							<div class="h5 mb-0 font-weight-bold text-gray-800">
								<select id="allschema" name="allschema" style="width: 220px;" onchange="getAllTablesFromSchema(this.value);">
								</select>
							</div>
							</br>
							<div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
								Table(s)
							</div>
							<div class="h5 mb-0 font-weight-bold text-gray-800">
								<select id="alltables" name="alltables" style="width: 220px;" onchange="getAllColumnsFromTables(this.value);">
								</select>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-xl-4 col-md-6 mb-4">
			<div class="card border-left-success shadow h-100 py-2">
				<div class="card-body">
					<div class="row no-gutters align-items-center">
						<div class="col mr-2">
							<div class="text-xs font-weight-bold text-success text-uppercase mb-1">
								Existing Column(s)
							</div>
							<div class="h5 mb-0 font-weight-bold text-gray-800" id="allColumns">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-xl-4 col-md-6 mb-4">
			<div class="card border-left-info shadow h-100 py-2">
				<div class="card-body">
					<div class="row no-gutters align-items-center">
						<div class="col mr-2">
							<div class="text-xs font-weight-bold text-info text-uppercase mb-1" id="addColumnDiv">
								Add a Column
							</div>
							<div class="row no-gutters align-items-center">
								<div class="col-auto">
									<div class="h5 mb-0 mr-3 font-weight-bold text-gray-800" id="formfield">
									
										<input id='tablename' name='' placeholder='' readonly='readonly' style='width: 220px;'>
										<input id="columnname" name="columnname" placeholder="Column Name" style="width: 220px;">
										<select id="datatype" style="width: 220px;">										
											<option value="select">Select Data Type</option>
											<option value="VARCAHR">VARCAHR</option>
											<option value="NUMERIC">NUMERIC</option>
											<option value="DATE">DATE</option>
											<option value="TIMESTAMP">TIMESTAMP</option>
											<option value="INT">INT</option>
										</select>
										<input id="length" name="length" placeholder="Length" style="width: 220px;">
										<br><br>
										<input id="savecolumn" onclick="saveCustomFiled();" type="button" value="ADD" style="width: 220px;">
									
									</div>
								</div>
								<div class="col"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		</div>
		<div class="row">
		<div class="col-xl-12 col-md-12 mb-12">
			<div class="card border-left-info shadow h-100 py-2">
				<div class="card-body">
					<div class="row no-gutters align-items-center">
						<div class="col mr-2">
							<div class="text-xs font-weight-bold text-info text-uppercase mb-1" id="addColumnDiv">
								<u>TABLE DETAILS</u>&nbsp;&nbsp;&nbsp;<a href="#" onclick="showDynamicForm();">ADD ENTRY</a>
							</div>
							<div class="row no-gutters align-items-center">
								<div class="col-auto">
									<div class="h5 mb-0 mr-3 font-weight-bold text-gray-800" id="tabledetails">								
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="dynamicForm" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content" id="modalContents">
			</div>
		</div>
	</div>
</div>