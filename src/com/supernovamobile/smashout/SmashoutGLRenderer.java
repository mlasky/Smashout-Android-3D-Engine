package com.supernovamobile.smashout;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.content.Context;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class SmashoutGLRenderer implements Renderer {

    private SMGroup     mScene;
    private SMModel     mCube;
    private SMModel 	mCube2;
    private Context 	mContext;
    
    private boolean 	light = false;

    private float[] 	mLightAmbient = {0.5f, 0.5f, 0.5f, 1.0f};
	private float[] 	mLightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
	private float[] 	mLightPosition = {0.0f, 0.0f, 2.0f, 1.0f};
		
	private FloatBuffer mLightAmbientBuffer;
	private FloatBuffer mLightDiffuseBuffer;
	private FloatBuffer mLightPositionBuffer;
	
    public SmashoutGLRenderer(Context context) 
        throws IOException, ParserConfigurationException, SAXException 
    {
        mContext = context;
        
        mScene = new SMGroup();
        mCube = new SMModel(R.raw.cube, context, "Cube_1");
        //mCube2 = new Model(R.raw.sub, context, "Cube_2");
        mScene.add(mCube);
        //mScene.add(mCube2);
        mCube.setRx(45.0f);
        
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(mLightAmbient.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mLightAmbientBuffer = byteBuf.asFloatBuffer();
		mLightAmbientBuffer.put(mLightAmbient);
		mLightAmbientBuffer.position(0);
		
		byteBuf = ByteBuffer.allocateDirect(mLightDiffuse.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mLightDiffuseBuffer = byteBuf.asFloatBuffer();
		mLightDiffuseBuffer.put(mLightDiffuse);
		mLightDiffuseBuffer.position(0);
		
		byteBuf = ByteBuffer.allocateDirect(mLightPosition.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mLightPositionBuffer = byteBuf.asFloatBuffer();
		mLightPositionBuffer.put(mLightPosition);
		mLightPositionBuffer.position(0);
        
    }
     
    @Override
    public void onDrawFrame(GL10 gl) {
        
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -7.0f);
        
         //Check if the light flag has been set to enable/disable lighting
        light = true;
        if(light) {
            gl.glEnable(GL10.GL_LIGHTING);
        } else {
            gl.glDisable(GL10.GL_LIGHTING);
        }
        
        
        //mCube.setRx(mCube.getRx() + 1);
        mCube.setRy(mCube.getRy() + 5);
        
        gl.glPushMatrix();
        //mCube2.setX(5.0f);
       
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
    	
    	gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        
    	gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, mLightAmbientBuffer);		//Setup The Ambient Light 
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, mLightDiffuseBuffer);		//Setup The Diffuse Light 
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, mLightPositionBuffer);	//Position The Light 
		gl.glEnable(GL10.GL_LIGHT0);
		
        //Settings
        gl.glDisable(GL10.GL_DITHER);               //Disable dithering 
        gl.glEnable(GL10.GL_TEXTURE_2D);            //Enable Texture Mapping
        
        
        gl.glShadeModel(GL10.GL_SMOOTH);            	
        gl.glClearColor(0.0f, 0.0f, 0.5f, 0.5f);    //Black Background
        gl.glClearDepthf(1.0f);                     //Depth Buffer Setup
        gl.glEnable(GL10.GL_DEPTH_TEST);            //Enables Depth Testing
        gl.glDepthFunc(GL10.GL_LEQUAL);             //The Type Of Depth Testing To Do
        
        //Really Nice Perspective Calculations
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 
        
        try {
            mScene.loadTextures(gl, mContext);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    } 
}
