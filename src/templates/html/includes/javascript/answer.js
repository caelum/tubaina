function toogleAnswer(id){
	var aux = document.getElementById(id);

	if (aux.style.display=="none"){
		aux.style.display = "block";
	}
	else {
		aux.style.display = "none";
	}
}
