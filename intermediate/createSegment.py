#!/usr/bin/env python
# encoding: utf-8

execfile('./intermediate/parser.py')

os.system("rm -rf output/InteractiveVideo-Segment.js")
f = open("output/InteractiveVideo-Segment.js", "w")

for canvas in canvases:
    canvasId = str(canvas)
    targetTime = str(canvases.get(canvas))
    f.write('var my' + canvas + ' = ' + 'document.getElementById("'+ canvas +'");\n')


editJS(f, 0, "function fileChecks() {")
editJS(f, 1, "if(index < segments.length) {")
countAction = 1
for action in actions:
    seg = "seg" + str(countAction);
    tTime = str(actions.get(action))
    editJS(f, 2, "var " + seg + " = Math.round(" + str(tTime) + "/10);")
    countAction += 1
editJS(f, 2, "if((videoElement.currentTime - lastTime) <= segCheck) {")
countAction = 1
for action in actions:
    seg = "seg" + str(countAction);
    editJS(f, 3, "if(!segmentsBuffered["+ seg +"]){")
    editJS(f, 4, "segmentsBuffered["+ seg +"] = true;")
    editJS(f, 4, "loadSegment("+ seg +", segments[" + seg + "].getAttribute('mediaRange').toString(), file);")
    editJS(f, 3, "}")
    countAction += 1
editJS(f, 2, "}")
editJS(f, 2, "else{")
editJS(f, 3, "if(!segmentsBuffered[index]){")
editJS(f, 4, "segmentsBuffered[index] = true;")
editJS(f, 4, "loadSegment(index, segments[index].getAttribute('mediaRange').toString(), file);")
editJS(f, 3, "}")
editJS(f, 3, "else{")
editJS(f, 4, "index++;")
editJS(f, 3, "}")
editJS(f, 2, "}")
editJS(f, 1, "}")
editJS(f, 0, "}")




