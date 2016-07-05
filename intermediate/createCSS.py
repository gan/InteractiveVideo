#!/usr/bin/env python
# encoding: utf-8

execfile('intermediate/parser.py')


os.system("rm -rf output/InteractiveVideo.css")
f2 = open('output/InteractiveVideo.css', 'w')

edit(f2, 0, '#myVideo {')
edit(f2, 1, 'width : ' + scene.get('width') + 'px;')
edit(f2, 1, 'height : ' + scene.get('height') + 'px;')
edit(f2, 1, 'border:1px black solid;')
edit(f2, 0, '}')

edit(f2, 0, '#section1 {')
edit(f2, 1, 'position:absolute;')
edit(f2, 1, 'margin-top:10px;')
edit(f2, 1, 'margin-left:10px;')
edit(f2, 0, '}')

edit(f2, 0, '#section2 {')
edit(f2, 1, 'position:absolute;')
edit(f2, 1, 'margin-top:100px;')
edit(f2, 1, 'margin-left:50%;')
edit(f2, 0, '}')

for canvas in canvases:
    pos = canvases.get(canvas)[1].split()
    size = canvases.get(canvas)[0].split()
    edit(f2, 0, '#' + canvas + '{')
    edit(f2, 1, 'position:absolute;')
    edit(f2, 1, 'left: ' + pos[0] + 'px;')
    edit(f2, 1, 'top: ' + pos[1] + 'px;')
    edit(f2, 1, 'width: ' + size[0] + 'px;')
    edit(f2, 1, 'height: ' + size[1] + 'px;')
    edit(f2, 0, '}')

