
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
				$.get("http://localhost:27777/storall/static?home", function(data, status){
				$( "#main" ).empty();
	    		$( "#main" ).html(data);
	  			});
		})

/*--------------------------- Experiment Section -------------------------------------*/

	  
	$("#expAddBtm").click(function(){
			$.get("http://localhost:27777/storall/forms/experiment?addexperiment", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  			});
		})


	$("#expAddAnaBtm").click(function(){
			$.get("http://localhost:27777/storall/forms/experiment?addanalysis", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  			});
		})


	$("#expAddNotesBtm").click(function(){
			$.get("http://localhost:27777/storall/forms/experiment?addexperimentnotes", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  			});
		})


	$("#expFind").click(function(){
			$.get("http://localhost:27777/storall/forms/experiment?findexperiment", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  			});
		})


/*------------------------------- Project Section -----------------------------*/
	

  
	$("#proAddBtm").click(function(){
			$.get("http://localhost:27777/storall/forms/project?addproject", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  			});			
		})


	$("#proAddNotesBtm").click(function(){
			$.get("http://localhost:27777/storall/forms/project?addprojectnotes", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  			});			
		})


	$("#proFind").click(function(){
		$.get("http://localhost:27777/storall/forms/project?findproject", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  			});
		})


/*-------------------------------------- Read Section ----------------------------------------------*/
	

	$("#readAddBtm").click(function(){
		$.get("http://localhost:27777/storall/forms/read?addread", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  		});
	})



	$("#readAddNotesBtm").click(function(){
		$.get("http://localhost:27777/storall/forms/read?addreadnotes", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  		});
	})



	$("#readFind").click(function(){
		$.get("http://localhost:27777/storall/forms/read?findread", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);
  		});
	})


	/*-------------------------------------- System Section ----------------------------------------------*/


	$("#systemPageBtm").click(function(){
		$.get("http://localhost:27777/storall/system?page", function(data, status){
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
		$.get("http://localhost:27777/storall/help?colltype", function(data, status){
			$( "#main" ).empty();
    		$( "#main" ).html(data);	
  		});
	})

	$("#howToBtn").click(function(){
		$.get("http://localhost:27777/storall/help?howto", function(data, status){
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

		$.post("http://localhost:27777/storall/Experiment?json", JSON.stringify(jsonObj))
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
		jsonObj["dataLocation"] = aloc;	

		$.post("http://localhost:27777/storall/Experiment/" + expID + "?json", JSON.stringify(jsonObj))
			.done(function(){
				$("#expAnaSuccess").show();
			}).fail(function(){
				$("#expAnaFail").show();
		});
	}

	function addExperimentNotes(){
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
	}

	function findExperiment(){
		var expID = $("input[id=expIDf]").val();

		$.get("http://localhost:27777/storall/Experiment/" + expID + "?json", function(data, status){

    		alert(JSON.stringify(data, undefined, '\t'));
  		});
	}

/*------------------------------- Project Section -----------------------------*/

	function addProject(){
		var owner = $("input[id=owner]").val();

		var jsonObj = {};
		jsonObj["owner"] = owner;

		$.post("http://localhost:27777/storall/Project?json", JSON.stringify(jsonObj))
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

		$.post("http://localhost:27777/storall/Project/" + pID + "?note", JSON.stringify(jsonObj))
			.done(function(){
				$("#expAnaSuccess").show();
			}).fail(function(){
				$("#expAnaFail").show();
		});	
	}

	function findProject(){
		var pID = $("input[id=pIDf]").val();

		$.get("http://localhost:27777/storall/Project/" + pID + "?json", function(data, status){

    		alert(JSON.stringify(data, undefined, '\t'));
  		});
	}

/*-------------------------------------- Read Section ----------------------------------------------*/

	function addRead(){

		var location = $("input[id=rloc]").val();

		var jsonObj = {};
		jsonObj["locationOfReadData"] = location;

		$.post("http://localhost:27777/storall/Read?json", JSON.stringify(jsonObj))
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

		$.post("http://localhost:27777/storall/Read/" + rID + "?note", JSON.stringify(jsonObj))
			.done(function(){
				$("#expAnaSuccess").show();
			}).fail(function(){
				$("#expAnaFail").show();
		});
	}

	function findRead(){
		var rID = $("input[id=rIDf]").val();

		$.get("http://localhost:27777/storall/Read/" + rID + "?json", function(data, status){

    		alert(JSON.stringify(data, undefined, '\t'));
  		});
	}
	

	function updateSystemInfo(){
		$.get("http://localhost:27777/storall/system?allinfo", function(data, status){
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

		updateSystemInfo();

		var t = setTimeout(function(){continuousUpdate()},30000);
	}