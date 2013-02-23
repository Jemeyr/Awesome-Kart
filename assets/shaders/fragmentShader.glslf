#version 150

uniform vec3 camDir;
out vec4 outColor;

in vec2 texCoordPS;
in vec3 posPS;

smooth in vec4 norm;

uniform sampler2D tex;

void main() // 
{
	float light = 0.725;
	
	//crappy psuedolighting
	light += 0.275 * dot(normalize(norm.xyz), normalize(vec3(1.0, 1.0, 0.0)));
	
	//light += max(0.0, dot(normalize(norm), normalize(camDir)));
	//light += pow(max(0.0, dot(normalize(norm), normalize(camDir))),32);
	
	outColor = texture(tex, texCoordPS) * light;
	
}

