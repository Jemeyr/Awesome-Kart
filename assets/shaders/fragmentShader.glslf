#version 150

uniform vec3 camDir;
out vec4 outColor;

in vec2 texCoordPS;
in vec3 posPS;

smooth in vec3 norm;

uniform sampler2D tex;

void main() // 
{
	float normWeight = 0.75;
	float light = 1.0 - normWeight;
	
	//crappy psuedolighting
	light += normWeight * dot(normalize(norm), normalize(vec3(1.0, 1.0, 0.0)));
	
	//light += max(0.0, dot(normalize(norm), normalize(camDir)));
	//light += pow(max(0.0, dot(normalize(norm), normalize(camDir))),32);
	
	outColor = texture(tex, texCoordPS) * light;
	
}

