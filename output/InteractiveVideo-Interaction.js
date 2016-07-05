function DaggerAttack(){
	lastTime = videoElement.currentTime;
	index = Math.round(52/10);
	chosen = true;
	videoElement.currentTime = 52;
}

function FreezeTimer(){
	lastTime = videoElement.currentTime;
	index = Math.round(88/10);
	chosen = true;
	videoElement.currentTime = 88;
}

function RatBomb(){
	lastTime = videoElement.currentTime;
	index = Math.round(139/10);
	chosen = true;
	videoElement.currentTime = 139;
}

function showMenu0(active) {
	if(active){
		myCanvas2.addEventListener("mousedown", FreezeTimer);
		myCanvas0.addEventListener("mousedown", RatBomb);
		myCanvas1.addEventListener("mousedown", DaggerAttack);
	}
	else{
		myCanvas2.removeEventListener("mousedown", FreezeTimer);
		myCanvas0.removeEventListener("mousedown", RatBomb);
		myCanvas1.removeEventListener("mousedown", DaggerAttack);
	}
}

function showMenu1(active) {
	if(active){
		myCanvas1.addEventListener("mousedown", RatBomb);
		myCanvas0.addEventListener("mousedown", DaggerAttack);
		myCanvas2.addEventListener("mousedown", FreezeTimer);
	}
	else{
		myCanvas1.removeEventListener("mousedown", RatBomb);
		myCanvas0.removeEventListener("mousedown", DaggerAttack);
		myCanvas2.removeEventListener("mousedown", FreezeTimer);
	}
}

videoElement.addEventListener("timeupdate", 
	function(event){
	var curTime = videoElement.currentTime;
	document.getElementById("time").innerHTML = "time : " + curTime;
	if((curTime >= 44 ) && (curTime < 55)){
		chosen = false;
		document.getElementById("demo").innerHTML = "menu activer" + curTime;
				showMenu1(false);
		showMenu0(true);
		if((curTime >= 54) && (!chosen)){
			videoElement.currentTime = 44;
		}
	}
	if((curTime >= 75 ) && (curTime < 88)){
		chosen = false;
		document.getElementById("demo").innerHTML = "menu activer" + curTime;
		showMenu0(false);
				showMenu1(true);
		if((curTime >= 87) && (!chosen)){
			videoElement.currentTime = 75;
		}
	}
});

