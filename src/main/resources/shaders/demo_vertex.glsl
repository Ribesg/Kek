#version 330 core

layout(location = 0) in vec2 pos;
layout(location = 1) in vec3 color;

out vec3 vertexColor;

void main() {
    vertexColor = color;
    gl_Position = vec4(pos, 0.5, 1.0);
}
