#!/usr/bin/env python
# encoding: utf-8

import os
import xml.etree.ElementTree as ET
import logging as log, sys
log.basicConfig(stream=sys.stderr, level=log.DEBUG)

tree = ET.parse("intermediate/InteractiveVideo.xml")
root = tree.getroot()

scene = {}
for video in root.iter('video'):
    width = video.get('width')
    height = video.get('height')
    scene.update({"width" : width})
    scene.update({"height" :  height})
log.debug("scene : " + str(scene))

chapters = {}
for video in root.iter('video'):
    videoName = video.get('stream')
    log.debug("video name : " + videoName)
    for chapter in video.iter('chapter'):
        chapterName = chapter.get('name')
        chapterStart = chapter.get('start')
        chapters.update({chapterName : chapterStart})
log.debug("chapters : " + str(chapters))

canvases = {}
for canvas in root.iter('canvas'):
    canvasId = canvas.get('id')
    for rectangle in canvas.iter('rectangle'):
        size = rectangle.get('size')
        pos = rectangle.get('pos')
    canvases.update({canvasId : [size, pos]})
log.debug("canvases : \n" + str(canvases))

menus = {}
for menu in root.iter('menu'):
    menuId = menu.get('id')
    menuNum = menu.get('menuNum')
    loop = menu.get('loop')
    start = menu.get('start')
    end = menu.get('end')
    buttons = []
    for button in menu.iter("mbutton"):
        canvasId = button.get('canvas')
        bType = button.get('type')
        actionArg = button.get('actionArg')
        buttons.append([canvasId, bType, actionArg])
    menus.update({menuId : [menuNum, loop, start, end, buttons]})
log.debug("menus : \n" + str(menus))

actions = {}
for action in root.iter('action'):
    actionId = action.get('id')
    time = action.get('goto')
    actions.update({actionId : time})
log.debug("actions : \n" + str(actions))

def startJS(file, title, argments=[""]):
    args = [""]
    if len(argments) <= 1:
        args = argments[0]
    else:
        for arg in argments:
            args = args + ","
        args.pop()
    file.write("function " + title + "(" + args + ") {\n" )

def endJS(file):
    file.write("}\n\n")

def editJS(file, tab, content):
    for x in range(0, tab):
        file.write("\t")
    file.write(content + "\n")

def edit(file, tab, content):
    for x in range(0, tab):
        file.write("\t")
    file.write(content + "\n")

def editJSAddEventListener(file, tab, target, function):
    editJS(file, 2, target + '.addEventListener' + '("mousedown", ' + function + ');')

def editJSRemoveEventListener(file, tab, target, function):
    editJS(file, 2, target + '.removeEventListener' + '("mousedown", ' + function + ');')

def editJSShowMenu(file, tab, numMenu, total):
    for x in range(0, total):
        for i in range(0, tab):
            file.write('\t')
        if numMenu != str(x+1) :
            file.write('showMenu' + str(x) + '(false);\n')
        else:
			showMenuNum = x;
    for i in range(0, tab):
        file.write('\t')
    file.write('showMenu' + str(showMenuNum) + '(true);\n')




