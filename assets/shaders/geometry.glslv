#version 150

uniform mat4 worldMatrix;
uniform mat4 vpMatrix;

in vec3 position;
in vec3 normal;
in vec2 texCoord;

out vec3 normFS;
out vec3 posFS;
out vec2 texCoordFS;

void main() {

	normFS = (worldMatrix * vec4(normal, 0.0)).xyz;
	posFS = (worldMatrix * vec4(position, 1)).xyz;
	texCoordFS = texCoord;
	
	gl_Position = vpMatrix * worldMatrix * vec4(position.xyz, 1.0);
}