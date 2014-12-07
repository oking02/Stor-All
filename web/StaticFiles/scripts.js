
function startTime() {
    var today=new Date();
    var h=today.getHours();
    var m=today.getMinutes();
    var s=today.getSeconds();
    m = checkTime(m);
    s = checkTime(s);
    document.getElementById("timeDisplay").innerHTML = h+":"+m+":"+s;
    var t = setTimeout(function(){startTime()},500);
}

function checkTime(i) {
    if (i<10) {i = "0" + i};  // add zero in front of numbers < 10
    return i;
}

var details = localStorage.getItem('serverDetails');


function checkServerDetails(){
	if (localStorage.getItem("serverDetails") === null) {
		$('#serverDetailsModal').modal('show');
		$( "#serverForm" ).show();
	};
	var t = setTimeout(function(){checkServerDetails()},300000);
}


$(document).ready(function(){

	checkServerDetails();
	startTime();



	$("#home").click(function(){
				$.get(details + "storall?home", function(data, status){
				$( "#main" ).empty();
	    		$( "#main" ).html(data);
	  			});
		})

/*--------------------------- Experiment Section -------------------------------------*/

	  
	$("#expAddBtm").click(function(){
			$.get(details + "storall/forms/experiment?addexperiment", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  			});
		})


	$("#expAddAnaBtm").click(function(){
			$.get(details + "storall/forms/experiment?addanalysis", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
    		populateToolDropdown();
    		$("#locationTextBox").hide();
    		$("#knownLocationTextBox").hide();
  			});
		})


	$("#expAddNotesBtm").click(function(){
			$.get(details + "storall/forms/experiment?addexperimentnotes", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  			});
		})


	$("#expFind").click(function(){
			$.get(details + "storall/forms/experiment?findexperiment", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  			});
		})

	/*-----Analysis Tool Section-----*/ 

	$("#analysisTool").click(function(){
			$.get(details + "storall/Tools/Page?toolpage", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  			});
  			buildToolTable();

		})
	
/*------------------------------- Project Section -----------------------------*/
	

  
	$("#proAddBtm").click(function(){
			$.get(details + "storall/forms/project?addproject", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  			});			
		})


	$("#proAddNotesBtm").click(function(){
			$.get(details + "storall/forms/project?addprojectnotes", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  			});			
		})


	$("#proFind").click(function(){
		$.get(details + "storall/forms/project?findproject", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  			});
		})


/*-------------------------------------- Read Section ----------------------------------------------*/
	

	$("#readAddBtm").click(function(){
		$.get(details + "storall/forms/read?addread", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  		});
	})



	$("#readAddNotesBtm").click(function(){
		$.get(details + "storall/forms/read?addreadnotes", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  		});
	})



	$("#readFind").click(function(){
		$.get(details + "storall/forms/read?findread", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  		});
	})


	/*-------------------------------------- System Section ----------------------------------------------*/


	$("#systemPageBtm").click(function(){
		$.get(details + "storall/system?page", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
    		updateSystemInfo();	
  		});
	})

	continuousUpdate();

	/*-------------------------------------- Server Details ----------------------------------------------*/
	
	$("#serverDetailsSubBtm").click(function(){
		var serverDetails = $("input[id=serverDetailsInput]").val();
		localStorage.setItem("serverDetails", serverDetails);
		alert(localStorage.getItem("serverDetails"));
	})

	$("#changeServerDetailsBtm").click(function(){
		$('#serverDetailsModal').modal('show');
		$( "#serverForm" ).show();
	})

	/*-------------------------------------- Help Section ----------------------------------------------*/

	$("#collTypeBtn").click(function(){
		$.get(details + "storall/help?colltype", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);	
  		});
	})

	$("#howToBtn").click(function(){
		$.get(details + "storall/help?howto", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);	
  		});
	})
		
});

/* --------------------- JavaScript Functions -----------------------------------*/


/*------------------------------- Experiment Section -----------------------------*/

	function addExperiment(){
		var pId = $("input[id=pIDe]").val();
		var rId = $("input[id=rIDe]").val();

		var jsonObj = {};
		jsonObj["projectID"] = pId;
		jsonObj["readID"] = rId;	

		$.post(details + "storall/Experiment?json", JSON.stringify(jsonObj))
			.done(function(){
				$("#expAddSuccess").show();
			}).fail(function(){
				$("#expAddFail").show();
		});
	}

	function addAnalysis(){
		var expID = $("input[id=expIDa]").val();
		var info = $("input[id=info]").val();
		var aloc = $("input[id=aloc]").val();

		var jsonObj = {};
		jsonObj["expID"] = expID;
		jsonObj["info"] = info;	
		//jsonObj["dataLocation"] = aloc;	
		if ($("#locationTextBox").css("display") == "none") {
			jsonObj["dataLocation"] = $("#toolLocation").text();
		} else{
			jsonObj["dataLocation"] = aloc;	
		};

		$.post(details + "storall/Experiment/" + expID + "?json", JSON.stringify(jsonObj))
			.done(function(){
				$("#expAnaSuccess").show();
			}).fail(function(){
				$("#expAnaFail").show();
		});
	}

	function populateToolDropdown(){
		$.get(details + "storall/Tools?json", function(data, status){
    		var jsonObj = eval ("(" + JSON.stringify(data, undefined, '\t') + ")");

    		for (var i = jsonObj.length - 1; i >= 0; i--) {
    			var toolName = jsonObj[i].name;
    			var toolLocaiton = jsonObj[i].outputLocation;
    			//var listItem = '<li onclick="createTextBox()" role="presentation"><a role="menuitem" tabindex="-1" href="#">' + toolName + '</a></li>';
				//$("#toolDropdown").prepend(listItem);

				var optionItem = '<option onclick="createTextBox(this)" value="' + toolLocaiton + '">' + toolName + '</option>';
				$("#toolSelect").append(optionItem);
    		}; 
    		var otherItem = '<option value="" onclick="otherChoice()">Other</option>';
    		$("#toolSelect").append(otherItem);

  		});
	}

	function otherChoice(){
		$("#knownLocationTextBox").hide();
		$("#locationTextBox").show();
	}

	function createTextBox(location){
		$("#locationTextBox").hide();
		$("#knownLocationTextBox").show();
		$("#toolLocation").empty();					
    	$("#toolLocation").text($(location).val());			
	}

	function addExperimentNotes(){
		var expID = $("input[id=expIDn]").val();
		var note = $("textarea[id=note]").val();

		var jsonObj = {};
		jsonObj["expID"] = expID;
		jsonObj["Note"] = note;	

		$.post(details + "storall/Experiment/" + expID + "?note", JSON.stringify(jsonObj))
			.done(function(){
				$("#expAnaSuccess").show();
			}).fail(function(){
				$("#expAnaFail").show();
		});
	}

	function findExperiment(){
		var expID = $("input[id=expIDf]").val();

		$.get(details + "storall/Experiment/" + expID + "?json", function(data, status){

    		//alert(JSON.stringify(data, undefined, '\t')); 	
    		$("#experimentRepresentationArea").empty();	
    		$("#experimentRepresentationArea").append(JSON.stringify(data, undefined, '\t'));
    	
  		});
	}

	function findAllExperiment(){

		$.get(details + "storall/Experiment?json", function(data, status){

    		//alert(JSON.stringify(data, undefined, '\t')); 	
    		$("#experimentRepresentationArea").empty();	
    		$("#experimentRepresentationArea").append(JSON.stringify(data, undefined, '\t'));
    	
  		});
	}

	function buildToolTable(){
		$("tbody").empty();
		$.get(details + "storall/Tools?json", function(data, status){
    		var jsonObj = eval ("(" + JSON.stringify(data, undefined, '\t') + ")");

    		for (var i = jsonObj.length - 1; i >= 0; i--) {
    			addToToolTable(jsonObj[i].name, jsonObj[i].outputLocation, jsonObj[i].useCount)
    		}; 		 		
  		});

	}

	function addToToolTable(name, outputLocation, count){
		var row = "<tr><td>" + name + "</td><td>" + outputLocation + "</td><td>";
		var numbutton = ' <button class="btn btn-info" title="Use Count" ><span class="badge">' + count + '</span></button>';
		var editbutton = ' <button value="' + name + '" id="" type="button" onclick="showUpdateToolModal(this)" class="btn btn-warning" title="Edit"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></button>';
		var deletebutton = ' <button value="' + name + '" id="" type="button" class="btn btn-danger" onclick="deleteAnalysisTool(this)" title="Delete"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>';
		$("#analysisToolTable").append(row + numbutton + editbutton + deletebutton + "</td></tr>");
	}

	function showToolModal(){
		$('#toolmodal').modal('show');
		$( "#toolForm" ).show();
	}	

	function addAnalysisTool(){
		var name = $("input[id=toolNameInput]").val();
		var outputLocation = $("input[id=toolOutputPathInput]").val();
		var jsonObj = {};
		jsonObj["name"] = name;
		jsonObj["outputLocation"] = outputLocation;

		$.post(details + "storall/Tools?json", JSON.stringify(jsonObj));
	}

	function updateAnalysisTool(){
		var name = $("div[id=updatetoolmodal]").val();
		var outputLocation = $("input[id=toolOutputPathUpdateInput]").val();
		var jsonObj = {};
		jsonObj["name"] = name;
		jsonObj["outputLocation"] = outputLocation;

		$.post(details + "storall/Tools/Tool?json", JSON.stringify(jsonObj));
	}

	function showUpdateToolModal(name){
		$('#updatetoolmodal').val($(name).val());
		$('#updatetoolmodal').modal('show');
		$( "#updatetoolmodal" ).show();
	}

	function deleteAnalysisTool(name){
		var name = $(name).val();
		var outputLocation = "placeholder";
		var jsonObj = {};
		jsonObj["name"] = name;
		jsonObj["outputLocation"] = outputLocation;

		$.ajax({
    		url: details + "storall/Tools/Tool?json",
    		type: 'DELETE',
    		dataType: 'json',
    		data: JSON.stringify(jsonObj)   
		});
	}
	

/*------------------------------- Project Section -----------------------------*/

	function addProject(){
		var owner = $("input[id=owner]").val();

		var jsonObj = {};
		jsonObj["owner"] = owner;

		$.post(details + "storall/Project?json", JSON.stringify(jsonObj))
			.done(function(){
				$("#expAddSuccess").show();
			}).fail(function(){
				$("#expAddFail").show();
		});	
	}

	function addProjectNotes(){
		var pID = $("input[id=pIDn]").val();
		var note = $("textarea[id=pnote]").val();

		var jsonObj = {};
		jsonObj["pID"] = pID;
		jsonObj["Note"] = note;	

		$.post(details + "storall/Project/" + pID + "?note", JSON.stringify(jsonObj))
			.done(function(){
				$("#expAnaSuccess").show();
			}).fail(function(){
				$("#expAnaFail").show();
		});	
	}

	function findProject(){
		var pID = $("input[id=pIDf]").val();

		$.get(details + "storall/Project/" + pID + "?json", function(data, status){

    		//alert(JSON.stringify(data, undefined, '\t'));
    		$("#projectRepresentationArea").empty();
    		$("#projectRepresentationArea").append(JSON.stringify(data, undefined, '\t'));
  		});
	}

	function findAllProject(){

		$.get(details + "storall/Project?json", function(data, status){

    		$("#projectRepresentationArea").empty();
    		$("#projectRepresentationArea").append(JSON.stringify(data, undefined, '\t'));
  		});
	}

	function findExpIn(){
		var pID = $("input[id=pIDf]").val();

		$.get(details + "storall/Project/" + pID + "/Experiments?json", function(data, status){

    		$("#projectRepresentationArea").empty();
    		$("#projectRepresentationArea").append(JSON.stringify(data, undefined, '\t'));
  		});
	}

/*-------------------------------------- Read Section ----------------------------------------------*/

	function addRead(){

		var location = $("input[id=rloc]").val();

		var jsonObj = {};
		jsonObj["locationOfReadData"] = location;

		$.post(details + "storall/Read?json", JSON.stringify(jsonObj))
			.done(function(){
				$("#expAddSuccess").show();
			}).fail(function(){
				$("#expAddFail").show();
		});	

	}

	function addReadNotes(){

		var rID = $("input[id=rIDn]").val();
		var note = $("textarea[id=rnote]").val();

		var jsonObj = {};
		jsonObj["rID"] = rID;
		jsonObj["Note"] = note;	

		$.post(details + "storall/Read/" + rID + "?note", JSON.stringify(jsonObj))
			.done(function(){
				$("#expAnaSuccess").show();
			}).fail(function(){
				$("#expAnaFail").show();
		});
	}

	function findRead(){
		var rID = $("input[id=rIDf]").val();

		$.get(details + "storall/Read/" + rID + "?json", function(data, status){

    		//alert(JSON.stringify(data, undefined, '\t'));
    		$("#readRepresentationArea").empty();
    		$("#readRepresentationArea").append(JSON.stringify(data, undefined, '\t'));
  		});
	}

	function findAllRead(){

		$.get(details + "storall/Read?json", function(data, status){

    		//alert(JSON.stringify(data, undefined, '\t'));
    		$("#readRepresentationArea").empty();
    		$("#readRepresentationArea").append(JSON.stringify(data, undefined, '\t'));
  		});
	}
	

	function updateSystemInfo(){
		$.get(details + "storall/system?allinfo", function(data, status){
    		var jsonObj = JSON.parse(JSON.stringify(data, undefined, '\t'));
    		$("#expCount").empty();
    		$("#expCount").append(jsonObj.numberOfExperiments);  
    		$("#proCount").empty();
    		$("#proCount").append(jsonObj.numberOfProjects);	
    		$("#readCount").empty();
    		$("#readCount").append(jsonObj.numberOfReads);  
    		$("#expSize").empty();
    		$("#expSize").append(jsonObj.expSize);
    		$("#proSize").empty();
    		$("#proSize").append(jsonObj.proSize);	
    		$("#readSize").empty();
    		$("#readSize").append(jsonObj.readSize);
  		});
	}

	function updateExpCount(){
		$.get(details + "storall/system?count", function(data, status){
    		var jsonObj = JSON.parse(JSON.stringify(data, undefined, '\t'));
    		$("#expCount").empty();
    		$("#expCount").append(jsonObj.numberOfExperiments);  		
  		});
	}

	function updateProCount(){
		$.get(details + "storall/system?count", function(data, status){
    		var jsonObj = JSON.parse(JSON.stringify(data, undefined, '\t'));
    		$("#proCount").empty();
    		$("#proCount").append(jsonObj.numberOfProjects);  		
  		});
	}

	function updateReadCount(){
		$.get(details + "storall/system?count", function(data, status){
    		var jsonObj = JSON.parse(JSON.stringify(data, undefined, '\t'));
    		$("#readCount").empty();
    		$("#readCount").append(jsonObj.numberOfReads);  		
  		});
	}

	function updateExpSize(){
		$.get(details + "storall/system?size", function(data, status){
    		var jsonObj = JSON.parse(JSON.stringify(data, undefined, '\t'));
    		$("#expSize").empty();
    		$("#expSize").append(jsonObj.expSize);  		
  		});
	}

	function updateProSize(){
		$.get(details + "storall/system?size", function(data, status){
    		var jsonObj = JSON.parse(JSON.stringify(data, undefined, '\t'));
    		$("#proSize").empty();
    		$("#proSize").append(jsonObj.proSize);  		
  		});
	}

	function updateReadSize(){
		$.get(details + "storall/system?size", function(data, status){
    		var jsonObj = JSON.parse(JSON.stringify(data, undefined, '\t'));
    		$("#readSize").empty();
    		$("#readSize").append(jsonObj.readSize);  		
  		});
	}

	function continuousUpdate(){

		updateSystemInfo();

		var t = setTimeout(function(){continuousUpdate()},30000);
	}

	

	

