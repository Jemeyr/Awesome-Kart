#version 150

out vec4 outColor;
out vec4 outNormal;
out vec4 outPos;



in vec2 texCoordPS;
in vec3 norm;

uniform sampler2D modelTexture;

void main()
{
	vec4 col = texture(modelTexture, texCoordPS);

	//MRT
	outColor = 	vec4(col);
	outNormal = vec4(norm, 1.0);
	outPos = 	vec4(0.0, 0.0, 0.0, 1.0);
	
}

