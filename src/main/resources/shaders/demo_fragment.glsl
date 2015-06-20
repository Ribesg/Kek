#version 330 core

in vec3 vertexColor;

layout(location = 0) out vec4 fragColor;

void main() {
    float a = 2 - abs(gl_PointCoord.x - 0.5) - abs(gl_PointCoord.y - 0.5);
    fragColor = vec4(vertexColor, a);
    fragColor = vec4(1.0, 0.0, 0.0, 1.0); // XXX Debug Line
}
