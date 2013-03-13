#version 150

out vec4 outColor;
out vec4 outNormal;
out vec4 outPos;



smooth in vec3 norm;

void main()
{
	//MRT
	outColor = 	vec4(norm.x,	0.0, 	0.0, 	1.0);
	outNormal = vec4(0.0, 		norm.y, 0.0, 	1.0);
	outPos = 	vec4(0.0, 		0.0, 	norm.z, 1.0);
	
}

