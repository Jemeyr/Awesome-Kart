#version 150

uniform vec3 camDir;
out vec4 outColor;

in vec2 texCoordPS;
in vec3 posPS;

smooth in vec3 norm;

uniform sampler2D tex;

void main() // 
{
	float light = 0.2;
	light += max(0.0, dot(normalize(norm), normalize(camDir)));
	light += pow(max(0.0, dot(normalize(norm), normalize(camDir))),32);
	
	outColor = vec4(1.0, 1.0, 0.0, 1.0);//texture(tex, texCoordPS) * light;
	
}

