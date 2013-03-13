#version 150

out vec4 outColor;
out vec4 outNormal;


smooth in vec3 norm;

void main()
{
	 //MRT having these commented out fixes things for no gldrawbuffers call
	
	//MRT
	outColor = vec4(0.0, 0.0, norm.z, 1.0);
	outNormal = vec4(norm.xy, 0.0, 1.0);
	
	
	
}

