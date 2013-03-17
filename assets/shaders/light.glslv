#version 150

uniform mat4 worldMatrix;
uniform mat4 vpMatrix;
uniform float radius;

in vec3 position;

void main() {

	vec4 pos = vpMatrix * worldMatrix * vec4(radius * position.xyz, 1.0);
	
	gl_Position = pos;
}