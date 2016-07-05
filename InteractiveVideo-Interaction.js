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

function showMenu2(active) {
	if(active){
	}
	else{
	}
}

function showMenu0(active) {
	if(active){
	}
	else{
	}
}

videoElement.addEventListener("timeupdate", 
	function(event){
	var curTime = videoElement.currentTime;
	document.getElementById("time").innerHTML = "time : " + curTime;
	if((curTime >= 75 ) && (curTime < 88)){
		chosen = false;
		document.getElementById("demo").innerHTML = "menu activer" + curTime;
		showMenu0(false);
				showMenu1(true);
		if((curTime >= 87) && (!chosen)){
			videoElement.currentTime = 75;
		}
	}
	if((curTime >= 44 ) && (curTime < 2)){
		chosen = false;
		document.getElementById("demo").innerHTML = "menu activer" + curTime;
				showMenu1(false);
		showMenu0(true);
		if((curTime >= 1) && (!chosen)){
			videoElement.currentTime = 44;
		}
	}
});

