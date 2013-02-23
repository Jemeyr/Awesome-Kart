#version 150

uniform mat4 wvpMatrix;

in vec3 position;
in vec3 normal;
in vec2 texCoord;

smooth out vec4 norm;
out vec2 texCoordPS;

void main() {

	vec4 pos = vec4(position.xyz, 1.0);
	vec4 normal4 = vec4(normal.xyz, 1.0);
	
	
	norm = wvpMatrix * normal4;

	texCoordPS = texCoord;
	gl_Position = wvpMatrix * pos;
}