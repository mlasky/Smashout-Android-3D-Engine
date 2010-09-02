package com.supernovamobile.smashout;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.content.Context;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class SmashoutGLRenderer implements Renderer {

	private Group 	mScene;
	private Model 	mCube;
	private Model	mCube2;
	
	public SmashoutGLRenderer(Context context) throws IOException, ParserConfigurationException, SAXException {
		mScene = new Group();
		mCube = new Model(R.raw.cube, context, "Cube_1");
		mCube2 = new Model(R.raw.cube, context, "Cube_2");
		mScene.add(mCube);
		mScene.add(mCube2);
	}
	 
	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -50.0f);
		/*
		for (int i = 0; i < mScene.sizeMeshes(); i++) {
			Mesh childMesh = mScene.get(i);
			if (childMesh.getTextureStr() != null && !childMesh.mTexLoaded) {
				Log.e("Renderer", "TexutreString: " + childMesh.getTextureStr());
				
				try {
					childMesh.loadTexture(gl, mContext);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}*/
		
		gl.glPushMatrix();
		mCube.get(0).ry++;
		mCube.get(0).ry++;
		mCube.get(0).rz++;
		
		mCube2.get(0).x = 10.0f;
		
		try {
			mScene.draw(gl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gl.glPopMatrix();
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);	
	} 
}
