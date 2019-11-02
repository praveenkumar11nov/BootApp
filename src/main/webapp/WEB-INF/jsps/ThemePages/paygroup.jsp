<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="themes/easydropdown.css"/>

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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		getPerson();
		getPersonforshareexp();
		getAllPayments();
		getGroupNames();
	});

	function loadSearchFilter(param,tableData,temp){
	    $('#'+param).dataTable().fnClearTable();
	    $('#'+param).dataTable().fnDestroy();
	    $('#'+temp).html(tableData);
	    $('#'+param).dataTable();
	}

	function getAllPayments(){
	    $.ajax({
		    url: "./getAllPayments",
		    success: function(result){
			    //alert("result = " + result);
		    	if(result.length>0){
		    		var htmls="";
					for(var i=0;i<result.length;i++){
						htmls+="<tr>"+
							  "<td>"+result[i].id+"</td>"+
							  "<td>"+result[i].personname+"</td>"+
							  "<td>"+result[i].groupname+"</td>"+
							  "<td>"+result[i].paydate+"</td>"+
							  "<td>"+result[i].amount+"</td>"+
							  "<td>"+result[i].remarks+"</td>"+
							  "</tr>";
					}
				    $("#paymententriesgrid").html(htmls);
				    loadSearchFilter('paymententriesdata',htmls,'paymententriesgrid');
				}
	    	}
	   });
	}

	function getGroupNames(){
		$.ajax({
		    url: "./getGroupNames",
		    success: function(result){
		    	if(result.length>0){
		    		var htmls="";
					for(var i=0;i<result.length;i++){
						htmls+="<option value="+result[i].groupname+">"+result[i].groupname+"</option>";
					}
				    $("#groupreportname").html(htmls);
				    $("#expsavegroupname").html(htmls);
				    $("#sharegroupreportname").html(htmls);
				    $("#addgroupname").html(htmls);
				    $("#savegroupname").html(htmls);
				}
	    	}
	   });
	}

	function creategroup(){
	    $.ajax({
		    url: "./createGroup?groupname=" + $("#grouppname").val(),
		    success: function(response){
		    	$('#creategroup').modal('hide');
		    	if(response.length>0){
			    	var html="";
			    	for(var i=0;i<response.length;i++){
						html+="<option value='"+response[i].groupname+"'>"+response[i].groupname+"</option>"
					}
					$("#addgroupname").html(html);
					$("#savegroupname").html(html);
					$("#expsavegroupname").html(html);
					$("#groupreportname").html(html);
					$("#sharegroupreportname").html(html);
				    alert("Group Created Successfully");
				}
	    	}
	   });
	}

	function addmembersingroup(){
		var groupname=$("#addgroupname").val();
		var personname=$("#addpersonname").val();
	    $.ajax({
		    url: "./addGroupMember?groupname="+groupname+"&personname="+personname,
		    success: function(result){
		    	$('#paygroup').modal('hide');
			    alert(result);
			    getPerson();
	    	}
	   });
	}

	function savepayment(){
		var groupname=$("#savegroupname").val();
		var personname=$("#savepersonname").val();
		var amount=$("#saveamount").val();
		var remarks=$("#saveremarks").val();
	    $.ajax({
		    url: "./addPaymentInGroup?groupname="+groupname+"&personname="+personname+"&amount="+amount+"&remarks="+remarks,
		    dataType : "json",
		    success: function(result){
			    if(result.length>0){
			    	$('#addpayments').modal('hide');
					var htmls="";
					for(var i=0;i<result.length;i++){
						htmls+="<tr>"+
							  "<td>"+result[i].id+"</td>"+
							  "<td>"+result[i].personname+"</td>"+
							  "<td>"+result[i].groupname+"</td>"+
							  "<td>"+result[i].paydate+"</td>"+
							  "<td>"+result[i].amount+"</td>"+
							  "<td>"+result[i].remarks+"</td>"+
							  "</tr>";
					}
				    $("#paymententriesgrid").html(htmls);
				    loadSearchFilter('paymententriesdata',htmls,'paymententriesgrid');
				    alert("Payment added successfully");
				}else{
					alert("Exception came while adding payment");
				}
	    	}
	   });
	}

	function seedatailsforthisgroup(){
		var groupname=$("#groupreportname").val();
	    $.ajax({
			type : "GET",
			url  : "./seeGroupWiseExpense?groupname="+groupname,
			dataType : "json",
			success : function(response) {
				var html="";
				for(var i=0;i<response.length;i++){
					html+="<tr><td>"+response[i][0]+"</td><td style='padding-left:35px;'>"+response[i][1]+"</td></tr>"
				}
				$("#groupreportsbody").html(html);
				$('#groupwisereport').modal('show');
			}
		});
	}

	function getPerson(){
		var groupname=$("#savegroupname").val();
	    $.ajax({
			type : "GET",
			url  : "./getPersonInThisGroup?groupname="+groupname,
			dataType : "json",
			success : function(response) {
				var html="";
				for(var i=0;i<response.length;i++){
					html+="<option value='"+response[i][1]+"'>"+response[i][1]+"</option>"
				}
				$("#savepersonname").html(html);
			}
		});
	}
	
	var perosnnamesChk="";
	function getPersonforshareexp(){
		$("#checkboxes").html("");
		$('#checkbox').prop('checked', false);
		var groupname=$("#expsavegroupname").val();
	    $.ajax({
			type : "GET",
			url  : "./getPersonInThisGroup?groupname="+groupname,
			dataType : "json",
			success : function(response) {
				perosnnamesChk=response;
				var html="";
				for(var i=0;i<response.length;i++){
					html+="<option value='"+response[i][1]+"'>"+response[i][1]+"</option>"
				}
				$("#expsavepersonname").html(html);
			}
		});
	}

	function boxDisable(e, t) {
	    if (t.is(':checked')) {
	     	var html="";
			for(var i=0;i<perosnnamesChk.length;i++){
				html+="<tr>"
						+"<td>"
							+"&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkbox' name='selectedpersons' value='"+perosnnamesChk[i][1]+"-"+i+"'>&nbsp;&nbsp;"+perosnnamesChk[i][1]
						+"</td>"
						+"<td>"
							+"<input type='number' name='shareexp"+i+"' id='shareexp"+i+"' style='width: 70%;'>"
						+"</td>"
					+"</tr>";
			}
			$("#checkboxes").html(html);
	    }else{
	    	$("#checkboxes").html("");
	    }
	}

	function shareexpSavepayment(){
		var groupname=$("#expsavegroupname").val();
		var personname=$("#expsavepersonname").val();
		var amount=$("#expsaveamount").val();
		var remarks=$("#expsaveremarks").val();
	//alert(groupname+" "+personname+" "+amount+" "+remarks);
	
	var array = [];
	var exppersonname = [];
	var expperson = [];
	var checkboxes = document.querySelectorAll('input[name=selectedpersons]:checked');

	if(checkboxes.length==0){
		var count=confirm("You have not filled individual expenses. Do you want to split equally ?");
		if(count==1){
			for(var i=0;i<perosnnamesChk.length;i++){
				//alert("NAME="+perosnnamesChk[i][1]+"  Amount=" + (amount/perosnnamesChk.length));
				exppersonname[i]=perosnnamesChk[i][1];
				expperson[i]=amount/perosnnamesChk.length;
			}
		}else{
			alert("please fill individual expences then submit");
			return false;
		}
	}
	else{

		for (var i = 0; i < checkboxes.length; i++) {
			array.push(checkboxes[i].value);
			var splitname=checkboxes[i].value;
			var code=splitname.split("-");
			exppersonname[i]=code[0];
			expperson[i]=$('#shareexp'+code[1]).val();
			//alert("PersonName = "+code[0]+"  Amount = " + $('#shareexp'+code[1]).val());
		}
	}
	

	    $.ajax({
		    url: "./addPaymentInGroupAndSharedExpense?groupname="+groupname+"&personname="+personname+"&amount="+amount+"&remarks="+remarks+"&exppersonname="+exppersonname+"&expperson="+expperson,
		    dataType : "json",
		    success: function(result){
			    //alert("result = " + result);
				
			    if(result.length>0){
			    	$('#sharelunchexp').modal('hide');
					var htmls="";
					for(var i=0;i<result.length;i++){
						htmls+="<tr>"+
							  "<td>"+result[i].id+"</td>"+
							  "<td>"+result[i].personname+"</td>"+
							  "<td>"+result[i].groupname+"</td>"+
							  "<td>"+result[i].paydate+"</td>"+
							  "<td>"+result[i].amount+"</td>"+
							  "<td>"+result[i].remarks+"</td>"+
							  "</tr>";
					}
				    $("#paymententriesgrid").html(htmls);
				    loadSearchFilter('paymententriesdata',htmls,'paymententriesgrid');
				    alert("Payment added successfully");


				}else{
					alert("Exception came while adding payment");
				}
	    	 
		    }
	   });
		 
	}

	function seedatailsforthissharedexp(){
		var groupname=$("#sharegroupreportname").val();
	    $.ajax({
			type : "GET",
			url  : "./viewsharedexpense?groupname="+groupname,
			dataType : "json",
			success : function(response) {
				var html="";
				for(var i=0;i<response.length;i++){
					html+="<tr>"+
							"<td align='center'>"+response[i][0]+"</td>"+
							"<td align='center'>"+response[i][1]+"</td>"+
							"<td align='center'>"+response[i][2]+"</td>"+
							"<td align='center'>"+response[i][3]+"</td>"+
						  "</tr>";
				}
				$("#viewsharedexpbody").html(html);
				$('#viewsharelunchexp').modal('show');
			}
		});
	}
</script>

<div class="container">
<br><br>

<div class="row">
	<!-- Earnings (Monthly) Card Example -->
	<div class="col-xl-3 col-md-6 mb-4">
		<div class="card border-left-primary shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col mr-2">
						<div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
							<a href="#" data-toggle="modal" data-target="#creategroup">Create a Group</a>
						</div>
						<div class="h5 mb-0 font-weight-bold text-gray-800"></div>
					</div>
					<div class="col-auto">
						<i class="fas fa-calendar fa-2x text-gray-300"></i>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Earnings (Monthly) Card Example -->
	<div class="col-xl-3 col-md-6 mb-4">
		<div class="card border-left-success shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col mr-2">
						<div class="text-xs font-weight-bold text-success text-uppercase mb-1">
							<a href="#" data-toggle="modal" data-target="#paygroup">Add Member</a>
						</div>
						<div class="h5 mb-0 font-weight-bold text-gray-800"></div>
					</div>
					<div class="col-auto">
						<i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Earnings (Monthly) Card Example -->
	<div class="col-xl-3 col-md-6 mb-4">
		<div class="card border-left-info shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col mr-2">
						<div class="text-xs font-weight-bold text-info text-uppercase mb-1">
							<a href="#" data-toggle="modal" data-target="#addpayments">Add Payment</a>	
						</div>
						<div class="row no-gutters align-items-center">
							<div class="col-auto">
								<div class="h5 mb-0 mr-3 font-weight-bold text-gray-800"></div>
							</div>
							<div class="col">
							<!-- 	<div class="progress progress-sm mr-2">
							 		<div class="progress-bar bg-info" role="progressbar"
										style="width: 50%" aria-valuenow="50" aria-valuemin="0"
										aria-valuemax="100"></div>
								</div> -->
							</div>
						</div>
					</div>
					<div class="col-auto">
						<i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Pending Requests Card Example -->
	<div class="col-xl-3 col-md-6 mb-4">
		<div class="card border-left-warning shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col mr-2">
						<div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
							<a href="#" data-toggle="modal" data-target="#viewpayments">Payment Entries</a>
						</div>
						<div class="h5 mb-0 font-weight-bold text-gray-800"></div>
					</div>
					<div class="col-auto">
						<i class="fas fa-comments fa-2x text-gray-300"></i>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="row">

	<!-- Earnings (Monthly) Card Example -->
	<div class="col-xl-3 col-md-6 mb-4">
		<div class="card border-left-primary shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col mr-2">
						<div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
							<a href="#" onclick="seedatailsforthisgroup();">See GroupWise Expense</a>
						</div>
						<div class="h5 mb-0 font-weight-bold text-gray-800">
							<select id="groupreportname" name="groupreportname" style="width: 148px;">
							<%-- 
								<c:forEach items="${paymentgroups}" var="element">
									<option value="${element.groupname}">${element.groupname}</option>
								</c:forEach>
							--%>
							</select>
						</div>
					</div>
					<div class="col-auto">
						<i class="fas fa-calendar fa-2x text-gray-300"></i>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Earnings (Monthly) Card Example -->
	<div class="col-xl-3 col-md-6 mb-4">
		<div class="card border-left-success shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col mr-2">
						<div class="text-xs font-weight-bold text-success text-uppercase mb-1">
							<a href="#" data-toggle="modal" data-target="#sharelunchexp">Enter Shared Expense</a>
						</div>
						<div class="h5 mb-0 font-weight-bold text-gray-800"></div>
					</div>
					<div class="col-auto">
						<i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Earnings (Monthly) Card Example -->
	<div class="col-xl-3 col-md-6 mb-4">
		<div class="card border-left-info shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col mr-2">
						<div class="text-xs font-weight-bold text-info text-uppercase mb-1">
							<a href="#" onclick="seedatailsforthissharedexp();">View Shared Expense</a>
						</div>
						<div class="row no-gutters align-items-center">
							<div class="col-auto">
								<div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">
									<select id="sharegroupreportname" name="sharegroupreportname" style="width: 148px;">
									<%-- 
										<c:forEach items="${paymentgroups}" var="element">
											<option value="${element.groupname}">${element.groupname}</option>
										</c:forEach>
										 --%>
									</select>	
								</div>
							</div>
							<div class="col">
								<!-- <div class="progress progress-sm mr-2">
									<div class="progress-bar bg-info" role="progressbar"
										style="width: 50%" aria-valuenow="50" aria-valuemin="0"
										aria-valuemax="100"></div>
								</div> -->
							</div>
						</div>
					</div>
					<div class="col-auto">
						<i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Pending Requests Card Example -->
	<div class="col-xl-3 col-md-6 mb-4">
		<div class="card border-left-warning shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col mr-2">
						<div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
							To be added soon...
						</div>
						<div class="h5 mb-0 font-weight-bold text-gray-800">18</div>
					</div>
					<div class="col-auto">
						<i class="fas fa-comments fa-2x text-gray-300"></i>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

 
	<!-- Modal -->
	<div class="modal fade" id="creategroup" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Create Group</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<table>
						<tr>
							<td>GroupName</td>
							<td style="padding-left: 15px;">
								<input type="text" placeholder="Enter Group Name" id="grouppname" name="remarks" style="width:100%">
							</td>
				 		</tr>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" onclick="creategroup();">Create Group</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>



	<!-- Modal -->
	<div class="modal fade" id="paygroup" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Add member in group</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<table>
						<tr>
							<td>GROUP NAME</td>
							<!-- <td style="padding-left: 15px;"><input type="text" placeholder="Enter Group Name"
								id="addgroupname" name="addgroupname" style="width: 250px;"></td> -->
								<td style="padding-left: 15px;">
								<select id="addgroupname" name="addgroupname" style="width:100%">
									<%-- 
									<c:forEach items="${paymentgroups}" var="element">
										<option value="${element.groupname}">${element.groupname}</option>
									</c:forEach> --%>
								</select>
								</td>
						</tr>
						<tr>
							<td>PERSON NAME</td>
							<td style="padding-left: 15px;"><input type="text" placeholder="Enter Person Name"
								id="addpersonname" name="addpersonname" style="width:100%"></td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" onclick="addmembersingroup();">AddMember</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>


	<div class="modal fade" id="addpayments" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Add a payment</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<table>
						<tr>
							<td>GROUP NAME</td>
							<td style="padding-left: 15px;">
							<!-- <input type="text" placeholder="Enter Group Name" id="savegroupname" name="savegroupname" style="width: 250px;"> -->
								<select id="savegroupname" name="savegroupname" style="width:100%" onchange="getPerson();">
									<%-- 
									<c:forEach items="${paymentgroups}" var="element">
										<option value="${element.groupname}">${element.groupname}</option>
									</c:forEach> --%>
								</select>	
								
							</td>
						</tr>
						<tr>
							<td>PERSON NAME</td>
							<td style="padding-left: 15px;">
							<!-- <input type="text" placeholder="Enter Person Name" id="savepersonname" name="savepersonname" style="width: 250px;"> -->
								<select id="savepersonname" name="savepersonname" style="width:100%">
								</select>
							</td>
						</tr>
						<tr>
							<td>AMOUNT</td>
							<td style="padding-left: 15px;"><input type="text" placeholder="Enter Amount"
								id="saveamount" name="saveamount" style="width:100%"></td>
						</tr>
						<tr>
							<td>REMARKS</td>
							<td style="padding-left: 15px;"><input type="text"
								placeholder="Enter Remarks(eg: Receipt No)" id="saveremarks"
								name="saveremarks" style="width:100%"></td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" onclick="savepayment();">SavePayment</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>


	<div class="modal fade" id="viewpayments" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">   <!-- 	style="width: 924px;"	 -->

				<!-- <div class="card shadow mb-4"> -->
					<div class="card-header py-3" style="height: 45px;">
						<h6 class="m-0 font-weight-bold text-primary">Payment Details
						<button type="button" class="close" data-dismiss="modal" style="font-size: x-large;font-weight: bolder;">
								&times;</button>
						</h6>
						
					</div>
					<div class="card-body">
						<div class="table-responsive popupscroll">
							<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0" id="paymententriesdata">
								<thead>
									<tr>
										<th>ID</th>
										<th>PersonName</th>
										<th>GroupName</th>
										<th>PayDate</th>
										<th>Amount</th>
										<th>Remark</th>
									</tr>
								</thead>
								<tbody id="paymententriesgrid">
								<%-- 
									<c:forEach var="pay" items="${payments}">
										<tr>
											<td>${pay.id}</td>
											<td>${pay.personname}</td>
											<td>${pay.groupname}</td>
											<td>${pay.paydate}</td>
											<td>${pay.amount}</td>
											<td>${pay.remarks}</td>
										</tr>
									</c:forEach>
									 --%>
								</tbody>
							</table>
						</div>
					</div>
				<!-- </div> -->

			</div>
		</div>
	</div>
	
<div class="modal fade" id="groupwisereport" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Group Wise Expense</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<table>
						<thead>
							<tr>
								<td>Name</td>
								<td style="padding-left: 22px;">Total(Credit/Debit)</td>
							</tr>
						</thead>
						<tbody id="groupreportsbody">
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>

<div class="modal fade" id="sharelunchexp" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Share Expense</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<table>
						<tr>
							<td>GROUP NAME</td>
							<td style="padding-left: 15px;">
							<!-- <input type="text" placeholder="Enter Group Name" id="savegroupname" name="savegroupname" style="width: 250px;"> -->
								<select id="expsavegroupname" name="expsavegroupname" style="width:100%" onchange="getPersonforshareexp();">
									<%-- 
									<c:forEach items="${paymentgroups}" var="element">
										<option value="${element.groupname}">${element.groupname}</option>
									</c:forEach>
									 --%>
								</select>	
								
							</td>
						</tr>
						<tr>
							<td>PERSON NAME</td>
							<td style="padding-left: 15px;">
							<!-- <input type="text" placeholder="Enter Person Name" id="savepersonname" name="savepersonname" style="width: 250px;"> -->
								<select id="expsavepersonname" name="expsavepersonname" style="width:100%">
								</select>
							</td>
						</tr>
						<tr>
							<td>AMOUNT</td>
							<td style="padding-left: 15px;"><input type="number" placeholder="Enter Amount"
								id="expsaveamount" name="expsaveamount" style="width:100%"></td>
						</tr>
						<tr>
							<td>REMARKS</td>
							<td style="padding-left: 15px;"><input type="text"
								placeholder="Enter Remarks(eg: Receipt No)" id="expsaveremarks"
								name="expsaveremarks" style="width:100%"></td>
						</tr>
					</table>
					<br>
					<div class="appTimes" id="appTimes">
						<table>
						<thead>
							<tr>
								<td align="left">
								<input type="checkbox" onclick="boxDisable(appTimes, $(this));" id="checkbox" name="checkbox">&nbsp;&nbsp;Split Individually</td>
							</tr>
							</thead>
							<tbody id="checkboxes">
							</tbody>
						</table>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" onclick="shareexpSavepayment();">SavePayment</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>
	
	<div class="modal fade" id="viewsharelunchexp" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">View Shared Expense</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<table style="width: 100%;" border='1'>
						<thead style="font-weight: bolder;font-size: 17px;">
							<tr>
								<td align="center">Name</td>
								<td align="center">Paid</td>
								<td align="center">Spent</td>
								<td align="center">Credit/Debit</td>
							</tr>
						</thead>
						<tbody id="viewsharedexpbody">
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>


</div>
