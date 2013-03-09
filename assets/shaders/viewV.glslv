#version 150


uniform vec2 pos;// = vec2(-1,-1);
uniform vec2 size;// = vec2(1,1);


const vec2 os = vec2(0.5, 0.5);

in vec2 position;

out vec2 vTexCoord;

void main() {

	//position is -1 -> 1, map to pos
	
	vec2 newPosition = (position + vec2(1,1)) * size * 0.5 + pos;
	

	vTexCoord = position * os + os;
	gl_Position = vec4(newPosition.xy, 0, 1);
}