#version 150

const vec2 os = vec2(0.5, 0.5);

in vec2 position;

out vec2 vTexCoord;

void main() {

	vTexCoord = position * os + os;
	gl_Position = vec4(position.xy, 0, 1);
}