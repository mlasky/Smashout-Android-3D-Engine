package com.supernovamobile.smashout;

public class Cube extends Mesh {
	
	public Cube(float width, float height, float depth) {
		width 	/= 2;
		height 	/= 2;
		depth 	/= 2;
		
		float vertices[] = { 	-width, -height, -depth,
								 width, -height, -depth,
								 width,  height, -depth,
								-width,  height, -depth,
								-width, -height,  depth,
								 width, -height,  depth,
								 width,  height,  depth,
								-width,  height,  depth,
		};
		
		short indices[]  = {	0, 4, 5,
								0, 5, 1,
								1, 5, 6,
								1, 6, 2,
								2, 6, 7,
								2, 7, 3,
								3, 7, 4,
								3, 4, 0,
								4, 7, 6,
								4, 6, 5,
								3, 0, 1,
								3, 1, 2,
		};
		
		setIndices(indices);
		setVertices(vertices);
	}
}
