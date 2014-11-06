
function startTime() {
    var today=new Date();
    var h=today.getHours();
    var m=today.getMinutes();
    var s=today.getSeconds();
    m = checkTime(m);
    s = checkTime(s);
    document.getElementById('timeDisplay').innerHTML = h+":"+m+":"+s;
    var t = setTimeout(function(){startTime()},500);
}

function checkTime(i) {
    if (i<10) {i = "0" + i};  // add zero in front of numbers < 10
    return i;
}


function checkServerDetails(){
	if (localStorage.getItem("serverDetails") === null) {
		$('#serverDetailsModal').modal('show');
		$( "#serverForm" ).show();
	};
	var t = setTimeout(function(){checkServerDetails()},300000);
}


$(document).ready(function(){

	$( "form" ).hide();
	$( ".alert" ).hide();
	$( ".systemInfo" ).hide();
	
	checkServerDetails();

	$("button").click(function(){
			$( ".alert" ).hide();
		})


/*--------------------------- Experiment Section -------------------------------------*/

	  
	$("#expAddBtm").click(function(){
			$( ".systemInfo" ).hide();
			$( "form" ).hide();
			$("#AddExperiment").show();
		})

	$("#newExpSubBtm").click(function(){

		var pId = $("input[id=pIDe]").val();
		var rId = $("input[id=rIDe]").val();

		var jsonObj = {};
		jsonObj["projectID"] = pId;
		jsonObj["readID"] = rId;	

		$.post("http://localhost:27777/storall/Experiment?json", JSON.stringify(jsonObj))
			.done(function(){
				$("#expAddSuccess").show();
			}).fail(function(){
				$("#expAddFail").show();
		});	

		})

	$("#expAddAnaBtm").click(function(){
			$( ".systemInfo" ).hide();
			$( "form" ).hide();
			$("#AddAnalysis").show();
		})

	$("#addAnaSubBtm").click(function(){

		var expID = $("input[id=expIDa]").val();
		var info = $("input[id=info]").val();
		var aloc = $("input[id=aloc]").val();

		var jsonObj = {};
		jsonObj["expID"] = expID;
		jsonObj["info"] = info;	
		jsonObj["dataLocation"] = aloc;	

		$.post("http://localhost:27777/storall/Experiment/" + expID + "?json", JSON.stringify(jsonObj))
			.done(function(){
				$("#expAnaSuccess").show();
			}).fail(function(){
				$("#expAnaFail").show();
		});	

		})

	$("#expAddNotesBtm").click(function(){
			$( ".systemInfo" ).hide();
			$( "form" ).hide();
			$("#AddExperimentNotes").show();
		})

	$("#expAddNoteSubBtm").click(function(){

		var expID = $("input[id=expIDn]").val();
		var note = $("textarea[id=note]").val();

		var jsonObj = {};
		jsonObj["expID"] = expID;
		jsonObj["Note"] = note;	

		$.post("http://localhost:27777/storall/Experiment/" + expID + "?note", JSON.stringify(jsonObj))
			.done(function(){
				$("#expAnaSuccess").show();
			}).fail(function(){
				$("#expAnaFail").show();
		});	

		})




	$("#expFind").click(function(){
			$( ".systemInfo" ).hide();
			$( "form" ).hide();
			$("#FindExperiment").show();
		})

	$("#findExpSubBtm").click(function(){

		var expID = $("input[id=expIDf]").val();

		$.get("http://localhost:27777/storall/Experiment/" + expID + "?json", function(data, status){

    		alert(JSON.stringify(data, undefined, '\t'));
  		});

		})

/*------------------------------- Project Section -----------------------------*/
	

  
	$("#proAddBtm").click(function(){
			$( ".systemInfo" ).hide();
			$( "form" ).hide();
			$("#AddProject").show();			
		})

	$("#newProSubBtm").click(function(){

		var owner = $("input[id=owner]").val();

		var jsonObj = {};
		jsonObj["owner"] = owner;

		$.post("http://localhost:27777/storall/Project?json", JSON.stringify(jsonObj))
			.done(function(){
				$("#expAddSuccess").show();
			}).fail(function(){
				$("#expAddFail").show();
		});	

		})

	$("#proAddNotesBtm").click(function(){
			$( ".systemInfo" ).hide();
			$( "form" ).hide();
			$("#AddProjectNotes").show();			
		})

	$("#proAddNoteSubBtm").click(function(){

		var pID = $("input[id=pIDn]").val();
		var note = $("textarea[id=pnote]").val();

		var jsonObj = {};
		jsonObj["pID"] = pID;
		jsonObj["Note"] = note;	

		$.post("http://localhost:27777/storall/Project/" + pID + "?note", JSON.stringify(jsonObj))
			.done(function(){
				$("#expAnaSuccess").show();
			}).fail(function(){
				$("#expAnaFail").show();
		});	

		})

	$("#proFind").click(function(){
			$( ".systemInfo" ).hide();
			$( "form" ).hide();
			$("#FindProject").show();
		})

	$("#findProSubBtm").click(function(){

		var pID = $("input[id=pIDf]").val();

		$.get("http://localhost:27777/storall/Project/" + pID + "?json", function(data, status){

    		alert(JSON.stringify(data, undefined, '\t'));
  		});

		})

/*-------------------------------------- Read Section ----------------------------------------------*/
	

  
	$("#readAddBtm").click(function(){
			$( ".systemInfo" ).hide();
			$( "form" ).hide();
			$("#AddRead").show();
		})

	$("#newReadSubBtm").click(function(){

		var location = $("input[id=rloc]").val();

		var jsonObj = {};
		jsonObj["locationOfReadData"] = location;

		$.post("http://localhost:27777/storall/Read?json", JSON.stringify(jsonObj))
			.done(function(){
				$("#expAddSuccess").show();
			}).fail(function(){
				$("#expAddFail").show();
		});	

		})

	$("#readAddNotesBtm").click(function(){
			$( ".systemInfo" ).hide();
			$( "form" ).hide();
			$("#AddReadNotes").show();
		})

	$("#readAddNoteSubBtm").click(function(){

		var rID = $("input[id=rIDn]").val();
		var note = $("textarea[id=rnote]").val();

		var jsonObj = {};
		jsonObj["rID"] = rID;
		jsonObj["Note"] = note;	

		$.post("http://localhost:27777/storall/Read/" + rID + "?note", JSON.stringify(jsonObj))
			.done(function(){
				$("#expAnaSuccess").show();
			}).fail(function(){
				$("#expAnaFail").show();
		});	

		})

	$("#readFind").click(function(){
			$( ".systemInfo" ).hide();
			$( "form" ).hide();;
			$("#FindRead").show();
		})

	$("#findReadSubBtm").click(function(){

		var rID = $("input[id=rIDf]").val();

		$.get("http://localhost:27777/storall/Read/" + rID + "?json", function(data, status){

    		alert(JSON.stringify(data, undefined, '\t'));
  		});

		})

	/*-------------------------------------- System Section ----------------------------------------------*/


	$("#systemPageBtm").click(function(){
			$( "form" ).hide();
			$( ".systemInfo" ).show();
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

		
});



	function updateExpCount(){
		$.get("http://localhost:27777/storall/system?count", function(data, status){
    		var jsonObj = JSON.parse(JSON.stringify(data, undefined, '\t'));
    		$("#expCount").empty();
    		$("#expCount").append(jsonObj.numberOfExperiments);  		
  		});
	}

	function updateProCount(){
		$.get("http://localhost:27777/storall/system?count", function(data, status){
    		var jsonObj = JSON.parse(JSON.stringify(data, undefined, '\t'));
    		$("#proCount").empty();
    		$("#proCount").append(jsonObj.numberOfProjects);  		
  		});
	}

	function updateReadCount(){
		$.get("http://localhost:27777/storall/system?count", function(data, status){
    		var jsonObj = JSON.parse(JSON.stringify(data, undefined, '\t'));
    		$("#readCount").empty();
    		$("#readCount").append(jsonObj.numberOfReads);  		
  		});
	}

	function updateExpSize(){
		$.get("http://localhost:27777/storall/system?size", function(data, status){
    		var jsonObj = JSON.parse(JSON.stringify(data, undefined, '\t'));
    		$("#expSize").empty();
    		$("#expSize").append(jsonObj.expSize);  		
  		});
	}

	function updateProSize(){
		$.get("http://localhost:27777/storall/system?size", function(data, status){
    		var jsonObj = JSON.parse(JSON.stringify(data, undefined, '\t'));
    		$("#proSize").empty();
    		$("#proSize").append(jsonObj.proSize);  		
  		});
	}

	function updateReadSize(){
		$.get("http://localhost:27777/storall/system?size", function(data, status){
    		var jsonObj = JSON.parse(JSON.stringify(data, undefined, '\t'));
    		$("#readSize").empty();
    		$("#readSize").append(jsonObj.readSize);  		
  		});
	}

	function continuousUpdate(){
		updateExpCount();
		updateProCount();
		updateReadCount();

		updateExpSize();
		updateProSize();
		updateReadSize();

		var t = setTimeout(function(){continuousUpdate()},30000);
	}