#version 150

uniform mat4 worldMatrix;
uniform mat4 vpMatrix;

in vec3 position;

void main() {

	vec4 pos = vpMatrix * worldMatrix * vec4(position.xyz, 1.0);
	
	gl_Position = pos.yxzw;
}