#version 150

uniform mat4 worldMatrix;
uniform mat4 vpMatrix;
uniform float radius;
uniform vec3 center;

in vec3 position;
in vec3 camDir;

out vec3 posFS;

void main() {

	posFS = center;	//TODO worldmatrix messes things up
	gl_Position = vpMatrix * worldMatrix * vec4(radius * position.xzy, 1.0);
}