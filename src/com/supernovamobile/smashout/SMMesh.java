package com.supernovamobile.smashout;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

public class SMMesh extends SMGroup {
	private FloatBuffer mVerticesBuffer = null;
	private FloatBuffer mColorBuffer 	= null;
	private FloatBuffer mTextureBuffer  = null;
	private FloatBuffer mNormalsBuffer  = null;
	private FloatBuffer mVNormalsBuffer = null;
	private ShortBuffer mIndicesBuffer 	= null;
	
	private int 	mNumIndices 	= -1;
	protected int[] mTextures 		= new int[1];
	
	private float[] mRGBA = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
	
	protected String  mTextureString 	= null;
	
	protected Vector<SMFace> mFaces = new Vector<SMFace>();
	
	@Override
	public void draw(GL10 gl) throws IOException {
		super.draw(gl);
		if (mVerticesBuffer != null) {
			
			gl.glFrontFace(GL10.GL_CCW);
			gl.glEnable(GL10.GL_CULL_FACE);
			gl.glCullFace(GL10.GL_BACK);
			
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);	
			gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalsBuffer);
			
			if (mTextureBuffer != null && mTextureString != null) {
				gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
				gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
			}
			
			gl.glColor4f(mRGBA[0], mRGBA[1], mRGBA[2], mRGBA[3]);
			
			if (mColorBuffer != null) {
				gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
				gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
			}
			
			gl.glPushMatrix();
			
			gl.glTranslatef(mX, mY, mZ);
			gl.glRotatef(mRx, 1, 0, 0);
			gl.glRotatef(mRy, 0, 1, 0);
			gl.glRotatef(mRz, 0, 0, 1);
			
			gl.glDrawElements(GL10.GL_TRIANGLES, mNumIndices, GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
			
			gl.glPopMatrix();
			
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
			
			if (mTextureString != null) {
				gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			}
			gl.glDisable(GL10.GL_CULL_FACE);
		}
	}
	
	public void setVertices(float[] vertices) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVerticesBuffer = vbb.asFloatBuffer();
		mVerticesBuffer.put(vertices);
		mVerticesBuffer.position(0);
	}
	
	public void setNormals(float[] normals) {
		ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length * 4);
		nbb.order(ByteOrder.nativeOrder());
		mNormalsBuffer = nbb.asFloatBuffer();
		mNormalsBuffer.put(normals);
		mNormalsBuffer.position(0);
	}
	
	public void setVNormals(float[] vnormals) {
		ByteBuffer nbb = ByteBuffer.allocateDirect(vnormals.length * 4);
		nbb.order(ByteOrder.nativeOrder());
		mVNormalsBuffer = nbb.asFloatBuffer();
		mVNormalsBuffer.put(vnormals);
		mVNormalsBuffer.position(0);		
	}
	
	public void setIndices(short[] indices) {
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		mIndicesBuffer = ibb.asShortBuffer();
		mIndicesBuffer.put(indices);
		mIndicesBuffer.position(0);
		mNumIndices = indices.length;
	}
	
	public void setTextureCoords(float[] coords) {
		ByteBuffer tbb = ByteBuffer.allocateDirect(coords.length * 4);
		tbb.order(ByteOrder.nativeOrder());
		mTextureBuffer = tbb.asFloatBuffer();
		mTextureBuffer.put(coords);
		mTextureBuffer.position(0);
	}
	
	
	protected void setColor(float red, float green, float blue, float alpha) {
		mRGBA[0] = red;
		mRGBA[0] = green;
		mRGBA[0] = blue;
		mRGBA[0] = alpha;
	}
	
	protected void setColors(float[] colors) {
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		mColorBuffer = cbb.asFloatBuffer();
		mColorBuffer.put(colors);
		mColorBuffer.position(0);
	}
	
	public void setTextureStr(String texture) {
		mTextureString = texture;
	}
	
	public void addFace(SMFace f) {
		mFaces.add(f);
	}
	
	public String getTextureStr() {
		return mTextureString;
	}
}