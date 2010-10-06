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
        out.write('\t\t<vx>%f</vx>\n' % (vert.co.x))
        out.write('\t\t<vy>%f</vy>\n' % (vert.co.y))
        out.write('\t\t<vz>%f</vz>\n\n' % (vert.co.z))
    
    out.write('\t</verts>\n')
    out.write('\t<indices>\n')

    for face in mesh.faces:
        for vert in face.v:
            out.write('\t\t<i>%i</i>\n' % (vert.index))
        
    out.write('\t</indices>\n')
    if mesh.faceUV:
        out.write('\t<uv>\n')
        
        for face in mesh.faces:
            for uv in face.uv:
                u, v = uv
                out.write('\t\t<u>%f</u>\n' % (u))
                out.write('\t\t<v>%f</v>\n' % (v))

        out.write('\t</uv>\n')
    # Texture                                                                                    #
    ####################################################
    mats = Blender.Material.get()
    if (len(mats) > 0):
        for m in mats:
            if (type(m.textures[0]).__name__ != "NoneType" and type(m.textures[0].tex.getImage()).__name__ != "NoneType"):
                out.write("\t<tex>" + m.textures[0].tex.getImage().getName() + "</tex>\n")
    out.write('</model>')
    out.close()

Blender.Window.FileSelector(write_obj, "Export")