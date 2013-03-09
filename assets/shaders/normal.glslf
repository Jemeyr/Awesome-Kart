#version 150

out vec4 outColor;
out vec4 outNormal;


smooth in vec3 norm;

void main()
{
	
	outColor = vec4(1.0, 0.0, 0.0, 1.0);
	outNormal = vec4(0.0, 1.0, 0.0, 1.0);
	
}

