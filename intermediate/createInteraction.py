#!/usr/bin/env python
# encoding: utf-8

execfile('./intermediate/parser.py')

os.system("rm -rf output/InteractiveVideo-Interaction.js")
f = open('output/InteractiveVideo-Interaction.js', 'w')
for action in actions:
    log.debug(str(action))
    log.debug(str(actions.get(action)))
    actionId = str(action)
    targetTime = str(actions.get(action))
    f.write("function " + actionId + "(){\n")
    f.write("\tlastTime = videoElement.currentTime;\n")
    f.write("\tindex = Math.round(" + targetTime + "/10);\n")
    f.write("\tchosen = true;\n")
    f.write("\tvideoElement.currentTime = " + targetTime + ";\n}\n\n")

for menu in menus:
    #log.dubug(str(menu))
    menuId = str(menu)
    argments = menus.get(menu)
    buttons = argments[4]
    #function menu
    startJS(f, "show" + menuId, ["active"])
    editJS(f, 1, "if(active){")
    for button in buttons:
        editJSAddEventListener(f, 2, "my" + str(button[0]), str(button[2]))
    editJS(f, 1, "}")
    editJS(f, 1, "else{")
    for button in buttons:
        editJSRemoveEventListener(f, 2, "my" + str(button[0]), str(button[2]))
    editJS(f, 1, "}")
    endJS(f)

## function timeupdate
f.write('videoElement.addEventListener("timeupdate", \n')
f.write('\tfunction(event){\n')
editJS(f, 1, "var curTime = videoElement.currentTime;")
editJS(f, 1, 'document.getElementById("time").innerHTML = "time : " + curTime;')
for menu in menus:
    menuId = str(menu)
    argments = menus.get(menu)
    menuNum = argments[0]
    isLoop = argments[1]
    startTime = argments[2]
    endTime = argments[3]
    editJS(f, 1, 'if((curTime >= ' + startTime + ' ) && (curTime < ' + endTime + ')){')
    editJS(f, 2, 'chosen = false;')
    editJS(f, 2, 'document.getElementById("demo").innerHTML = "menu activer" + curTime;');
    editJSShowMenu(f, 2, menuNum, len(menus))
    if(isLoop == 'true'):
        editJS(f, 2, 'if((curTime >= ' + str(int(endTime) - 1) + ') && (!chosen)){')
        editJS(f, 3, 'videoElement.currentTime = ' + startTime +';')
        editJS(f, 2, '}')
    editJS(f, 1, '}')
editJS(f, 0, '});\n')
