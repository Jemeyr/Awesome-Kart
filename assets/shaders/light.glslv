#version 150

uniform mat4 worldMatrix;
uniform mat4 vpMatrix;
uniform float radius;

in vec3 position;
in vec3 camDir;
out vec3 posFS;


void main() {

	vec4 pos = vpMatrix * worldMatrix * vec4(radius * position.xyz, 1.0);
	posFS = radius * position.xyz;
	gl_Position = pos;
}