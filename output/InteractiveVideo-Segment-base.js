'use strict'
// global Parameters for video
var chosen = false;
// global Parameters from .mpd file
var file;
var type;
var codecs;
var width;
var height;
// elements
var videoElement = document.getElementById('myVideo');
var playButton = document.getElementById("load");

// description of initialization segment, and approx segment lengths
var initialization;
var segDuration;
var vidDuration;
// video parameters
var bandwidth;
// parameters to drive segment loop
var index = 0;
var segments;
var curIndex = document.getElementById("curIndex");
var segLength = document.getElementById("segLength");
// source and buffers
var mediaSource;
var videoSource;
// parameters to drive fetch loop
var segCheck;
var lastTime = 0;
var bufferUpdate = false;
// flags to keep things going
var lastMpd = "";
var vTime = document.getElementById("curTime");
var requestId = 0;
var segmentsBuffered = [];
var chose = false;
var locked = false;

playButton.addEventListener("click", function() {
    var curMpd = document.getElementById("filename").value;
    getData(curMpd);
})

// gets the mpd file and parses it
function getData(url) {
    if(url != "") {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", url, true);
        xhr.responseType = "text";
        xhr.send();
        // Asynchronously wait for the data to return
        xhr.onreadystatechange = function(){
            if(xhr.readyState == xhr.DONE) {
                var parser = new DOMParser();
                var xmlData = parser.parseFromString(xhr.response, "text/xml", 0);

                getFileType(xmlData);
                setupVideo();
                clearVars();
            }
        }
        xhr.addEventListener("error", function(e) {
            log("Error: " + e + " Could not load url.");
        }, false);
    }
}

function getFileType(data) {
    try {
        file = data.querySelectorAll("BaseURL")[0].textContent.toString();
        var rep = data.querySelectorAll("Representation");
        type = rep[0].getAttribute("mimeType");
        codecs = rep[0].getAttribute("codecs");
        width = rep[0].getAttribute("width");
        height = rep[0].getAttribute("height");
        bandwidth = rep[0].getAttribute("bandwidth");
        var ini = data.querySelectorAll("Initialization");
        initialization = ini[0].getAttribute("range");
        segments = data.querySelectorAll("SegmentURL");

        var period = data.querySelectorAll("Period");
        var vidTempDuration = period[0].getAttribute("duration");
        vidDuration = parseDuration(vidTempDuration);
        var segList = data.querySelectorAll("SegmentList");
        segDuration = segList[0].getAttribute("duration");
        for(var i = 0; i < segments.length; i++)
            segmentsBuffered[i] = false;
    } catch(e) {
        log(e);
        return;
    }
    showTypes();
}

// Display parameters from the .mpd file
 function showTypes() {
   var display = document.getElementById("myspan");
   var spanData;
   spanData = "<h3>Reported values:</h3><ul><li>Media file: " + file + "</li>";
   spanData += "<li>Type: " + type + "</li>";
   spanData += "<li>Codecs: " + codecs + "</li>";
   spanData += "<li>Width: " + width + " -- Height: " + height + "</li>";
   spanData += "<li>Bandwidth: " + bandwidth + "</li>";
   spanData += "<li>Initialization Range: " + initialization + "</li>";
   spanData += "<li>Segment length: " + segDuration / 1000 + " seconds</li>";
   spanData += "<li>" + vidDuration + "</li>";
   spanData += "</ul>";
   display.innerHTML = spanData;
   document.getElementById("numIndexes").innerHTML = segments.length;
 }

 function render() {
    vTime.innerText = formatTime(videoElement.currentTime);
    requestId = window.requestAnimationFrame(render);
 }

// create mediaSource and initialize video
 function setupVideo() {
     clearLog();
     //create the media source
     mediaSource = new MediaSource();
     var url = window.URL.createObjectURL(mediaSource);
     videoElement.pause();
     videoElement.src = url;
     videoElement.width = width;
     videoElement.height = height;
     // wait for event that tells us that our media source object is ready
     // for a buffer to be added
     mediaSource.addEventListener('sourceopen', function(e) {
        try {
            videoSource = mediaSource.addSourceBuffer('video/mp4; codecs="avc1.4d4028,mp4a.40.2"');
            loadSegment(0 ,initialization, file);
            loadSegment(index ,segments[0].getAttribute("mediaRange").toString(), file);
            index++;
        } catch (e) {
            log('Exception calling addSourceBuffer for initialize video', e);
            return;
        }
     });

     // remove th handler for the timeupdate event
     videoElement.addEventListener("ended", function () {
        videoElement.removeEventListener("timeupdate", fileChecks);
     }, false);

     videoElement.addEventListener("timeupdate", fileChecks, false);
 }

// Load segment
 function loadSegment(index, range, url) {
   var xhr = new XMLHttpRequest();
   if(!locked){
       locked = true;
   if (range || url) { // make sure we've got incoming params
      //  set the desired range of bytes we want from the mp4 video file
     xhr.open('GET', url);
     xhr.setRequestHeader("Range", "bytes=" + range);
     xhr.send();
     xhr.responseType = 'arraybuffer';
     try {
       xhr.addEventListener("readystatechange", function () {
          if (xhr.readyState == xhr.DONE) { // wait for video to load
            segCheck = (timeToDownload(range) * .3).toFixed(3); // use .8 as fudge factor
            lastTime = videoElement.currentTime;
            log("load segments :" + index );
           // add response to buffer
           try {
                videoSource.appendBuffer(new Uint8Array(xhr.response));
            } catch (e) {
                segmentsBuffered[index] = false;
                index--;
                log('Exception while appending content', e);
            }
            }
        }, false);
    } catch (e) {
    log(e);
    }
   }
   else {
     return // No value for range or url
   }
   locked = false;
 }
 }

 function updateFunct() {
    bufferUpdated = true;
    videoSource.removeEventListener("update", updateFunct);
 }

 function log(s) {
    console.log(s);
 }

 function clearLog() {
    console.clear();
 }

 function clearVars() {
    index = 0;
    lastTime = 0;
 }

 function timeToDownload(range) {
    var vidDur = range.split("-");
    return (((vidDur[1] - vidDur[0]) * 8) / bandwidth);
 }

 // converts mpd time to human time
 function parseDuration(pt) {
   // parse time from format "PT#H#M##.##S"
   var ptTemp = pt.split("T")[1];
   ptTemp = ptTemp.split("H")
   var hours = ptTemp[0];
   var minutes = ptTemp[1].split("M")[0];
   var seconds = ptTemp[1].split("M")[1].split("S")[0];
   var hundredths = seconds.split(".");
   //  Display the length of video (taken from .mpd file, since video duration is infinate)
   return "Video length: " + hours + ":" + pZ(minutes, 2) + ":" + pZ(hundredths[0], 2) + "." + hundredths[1];
 }
  //  Converts time in seconds into a string HH:MM:SS.ss
 function formatTime(timeSec) {
   var seconds = timeSec % 60;                                  //  get seconds portion
   var minutes = ((timeSec - seconds) / 60) % 60;              //  get minutes portion
   var hours = ((timeSec - seconds - (minutes * 60))) / 3600;  //  get hours portion
   seconds = seconds.toFixed(2);   // Restrict to 2 places (hundredths of seconds)
   var dispSeconds = seconds.toString().split(".");
   return (pZ(hours, 2) + ":" + pZ(minutes, 2) + ":" + pZ(dispSeconds[0], 2) + "." + pZ(dispSeconds[1], 2));
}

//  Pad digits with zeros if needed
function pZ(value, padCount) {
   var tNum = value + '';
   while (tNum.length < padCount) {
     tNum = "0" + tNum;
   }
   return tNum;
}



