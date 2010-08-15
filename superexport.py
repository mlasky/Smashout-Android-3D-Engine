#!BPY

"""
Name: 'SuperExport'
Blender: 249
Group: 'Export'
Tooltip: 'Supernova Mobile Exporter'
"""

import Blender
import bpy

def write_obj(filePath):
	out = file(filePath, 'w')
	sce = bpy.data.scenes.active
	obj = sce.objects.active
	mesh = obj.getData(mesh=1)
	
	out.write('<model>\n')
	out.write('\t<verts>\n')
	
	for vert in mesh.verts:
		out.write('\t\t<vert>%f</vert>\n' % (vert.co.x))
		out.write('\t\t<vert>%f</vert>\n' % (vert.co.y))
		out.write('\t\t<vert>%f</vert>\n\n' % (vert.co.z))
	
	out.write('\t</verts>\n')
	out.write('\t<indices>\n')

	for face in mesh.faces:
		for vert in face.v:
			out.write('\t\t<index>%i</index>\n' % (vert.index))

	out.write('\t</indices>\n')
	out.write('</model>')
	out.close()

Blender.Window.FileSelector(write_obj, "Export")		
	
