#version 150

uniform vec3 cam;
out vec4 outColor;

in vec2 texCoordPS;
in vec3 posPS;

smooth in vec3 norm;

uniform sampler2D tex;

void main() // 
{
	float light = 0.2;
	light += max(0.0, dot(normalize(norm), normalize(cam)));
	light += pow(max(0.0, dot(normalize(norm), normalize(cam))),32);
	
	outColor = texture(tex, texCoordPS) * light;
	
}

