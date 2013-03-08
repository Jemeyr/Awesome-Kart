#version 150

out vec4 outColor;

smooth in vec3 norm;

void main()
{
	outColor = vec4(norm, 1.0);
}

