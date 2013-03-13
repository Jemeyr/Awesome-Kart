#version 150

uniform mat4 wvpMatrix;

in vec3 position;
in vec3 normal;
in vec2 texCoord;

smooth out vec3 norm;
out vec2 texCoordPS;

void main() {

	vec4 pos = vec4(position.xyz, 1.0);
	
	vec4 normF = wvpMatrix * vec4(normal.xyz, 0.0);
	
	norm = normF.xyz;
	
	texCoordPS = texCoord;
	gl_Position = wvpMatrix * pos;
}