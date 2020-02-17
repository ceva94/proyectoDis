
$(document).ready(function(){ 
	$("select#tipo").change( seleccionar);
	$("#preview").hover(
		function(){
			$("#file-select").fadeIn();
		},function(){
			$("#file-select").fadeOut();
		}
	);
	
	$("#file-select").click(function(){
		$("#cargarImagen").click();
	});
	
	$("#cargarImagen").change(function(){
		var nombreArchivo = (this.files[0].name).toString();
		var reader = new FileReader();
		
		$("#file-info").text('');
		$("#file-info").text(nombreArchivo);
		
		reader.onload = function(e){
			$('#preview img').attr('src',e.target.result);
		};
		
		reader.readAsDataURL(this.files[0]);
		
	});
	
	$("#file-select2").click(function(){
		$("#cargarVideo").click();
	});
	
	$("#cargarVideo").change(function(){
		var nombreArchivo = (this.files[0].name).toString();
		var reader = new FileReader();
		
		$("#file-info2").text('');
		$("#file-info2").text(nombreArchivo);
		
		
		reader.onload = function(e){
			$('#preview2 video').attr('src',e.target.result);
		};
		
		reader.readAsDataURL(this.files[0]);
		
	});
	
	$("#file-select3").click(function(){
		$("#cargarAudio").click();
	});
	$("#cargarAudio").change(function(){
		var nombreArchivo = (this.files[0].name).toString();
		var reader = new FileReader();
		
		$("#file-info3").text('');
		$("#file-info3").text(nombreArchivo);
		
		
		reader.onload = function(e){
			$('#preview3 audio').attr('src',e.target.result);
		};
		
		reader.readAsDataURL(this.files[0]);
		
	});
	
	
	//Empiezan llamadas a lambda
	$("#convertirAudio").click(function(){
		var obj = new Object();
		obj.data = $("#audio").attr("src").split(",")[1];
		obj.formato = $("#formatoAudio").val();
		var datos= JSON.stringify(obj);
		$.ajax({
            url: "https://fx0m4tb6mf.execute-api.us-east-2.amazonaws.com/pr/",
			type:"POST",
            dataType: "json",
            data: datos,
            contentType: false,
            processData: false,
        })
		.done(function(response){
			console.log(response);
			console.log(response["respuesta"]);
			var a = document.createElement("a");
			a.href = "data:audio/"+ obj.formato +";base64," + response["respuesta"];
			a.download = "respuesta."+ obj.formato;
			a.click();
		})
		.fail(function(e,textStatus,error){  
            console.log(e.responseText);
			console.log(textStatus);
			console.log(error);
        });
		
	})
	
	$("#convertirVideo").click(function(){
		var obj = new Object();
		obj.data = $("#video").attr("src").split(",")[1];
		obj.formato = $("#formatoVideo").val();
		console.log(obj);
		var datos= JSON.stringify(obj);
		$.ajax({
            url: "https://asq366t192.execute-api.us-east-2.amazonaws.com/prod/",
			type:"POST",
            dataType: "json",
            data: datos,
            contentType: false,
            processData: false,
        })
		.done(function(response){
			console.log(response);
			console.log(response["respuesta"]);
			var a = document.createElement("a");
			a.href = "data:audio/"+ obj.formato +";base64," + response["respuesta"];
			a.download = "respuesta."+ obj.formato;
			a.click();
		})
		.fail(function(e,textStatus,error){  
            console.log(e.responseText);
			console.log(textStatus);
			console.log(error);
        });
	})
	
	$("#convertirImagen").click(function(){
		var obj = new Object();
		obj.data = $("#imagen").attr("src").split(",")[1];
		obj.formato = $("#formatoImagen").val();
		console.log(obj);
		var datos= JSON.stringify(obj);
		$.ajax({
            url: "https://k8d7fj990h.execute-api.us-east-2.amazonaws.com/pro/",
			type:"POST",
            dataType: "json",
            data: datos,
            contentType: false,
            processData: false,
        })
		.done(function(response){
			console.log(response);
			console.log(response["respuesta"]);
			var a = document.createElement("a");
			a.href = "data:audio/"+ obj.formato +";base64," + response["respuesta"];
			a.download = "respuesta."+ obj.formato;
			a.click();
		})
		.fail(function(e,textStatus,error){  
            console.log(e.responseText);
			console.log(textStatus);
			console.log(error);
        });
	})
	
	
})

function seleccionar(){
	var valor = $(this).val();
	switch(valor){
		case "0":
			$("#convertidorImagen").css("display", "none");
			$("#convertidorAudio").css("display", "none");
			$("#convertidorVideo").css("display", "none");
			break;
		case "1":
			$("#convertidorImagen").css("display", "block");
			$("#convertidorAudio").css("display", "none");
			$("#convertidorVideo").css("display", "none");
			break;
		case "2":
			$("#convertidorAudio").css("display", "block");
			$("#convertidorImagen").css("display", "none");
			$("#convertidorVideo").css("display", "none");
			break;
		case "3":
			$("#convertidorVideo").css("display", "block");
			$("#convertidorImagen").css("display", "none");
			$("#convertidorAudio").css("display", "none");
			break;
	}
};
