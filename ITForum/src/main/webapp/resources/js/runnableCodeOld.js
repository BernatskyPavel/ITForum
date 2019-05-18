/**
 * 
 */
function runCode(event){
		var parent = event.parentElement;
		var childs = parent.children;
		var to_compile = {
		    "LanguageChoice": childs[1].value,
		    "Program": childs[0].value,
		    "Input": "",
		    "CompilerArgs" : ""
	    };

	    $.ajax ({
		        url: "https://rextester.com/rundotnet/api",
		        type: "POST",
		        data: to_compile
		    }).done(function(data) {
		        alert(data.Result);
		    }).fail(function(data, err) {
		        alert("fail " + JSON.stringify(data) + " " + JSON.stringify(err));
	        });
    }