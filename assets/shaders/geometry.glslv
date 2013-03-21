#version 150

uniform mat4 worldMatrix;
uniform mat4 vpMatrix;

in vec3 position;
in vec3 normal;
in vec2 texCoord;

out vec3 normFS;
out vec4 posFS;
out vec2 texCoordFS;

void main() {

	vec4 pos = vpMatrix * worldMatrix * vec4(position.xyz, 1.0);
	
	vec4 normF = worldMatrix * vec4(normal.xyz, 0.0);
	
	normFS = normF.xyz;
	posFS = worldMatrix * vec4(position, 1);
	texCoordFS = texCoord;
	
	gl_Position = pos;
}