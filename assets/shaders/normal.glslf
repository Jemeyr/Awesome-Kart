#version 150

out vec4 outColor;
out vec4 outNormal;


smooth in vec3 norm;

void main()
{
	//gl_FragData[0] = vec4(norm, 1.0); //MRT having these commented out fixes things for no gldrawbuffers call
	//gl_FragData[1] = outNormal;
	
	outColor = vec4(0.0, 0.0, norm.z, 1.0);
	outNormal = vec4(norm.x, 0.0, 0.0, 1.0);
	
	
	
}

