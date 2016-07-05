var myCanvas2 = document.getElementById("Canvas2");
var myCanvas1 = document.getElementById("Canvas1");
var myCanvas0 = document.getElementById("Canvas0");
function fileChecks() {
	if(index < segments.length) {
		var seg1 = Math.round(52/10);
		var seg2 = Math.round(88/10);
		var seg3 = Math.round(139/10);
		if((videoElement.currentTime - lastTime) <= segCheck) {
			if(!segmentsBuffered[seg1]){
				segmentsBuffered[seg1] = true;
				loadSegment(seg1, segments[seg1].getAttribute('mediaRange').toString(), file);
			}
			if(!segmentsBuffered[seg2]){
				segmentsBuffered[seg2] = true;
				loadSegment(seg2, segments[seg2].getAttribute('mediaRange').toString(), file);
			}
			if(!segmentsBuffered[seg3]){
				segmentsBuffered[seg3] = true;
				loadSegment(seg3, segments[seg3].getAttribute('mediaRange').toString(), file);
			}
		}
		else{
			if(!segmentsBuffered[index]){
				segmentsBuffered[index] = true;
				loadSegment(index, segments[index].getAttribute('mediaRange').toString(), file);
			}
			else{
				index++;
			}
		}
	}
}
