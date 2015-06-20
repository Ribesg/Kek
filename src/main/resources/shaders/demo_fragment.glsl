#version 330 core

in vec3 vertexColor;

out vec4 fragColor;

void main() {
    // Reverse color!
    fragColor = vec4(
        1.0 - vertexColor.r,
        1.0 - vertexColor.g,
        1.0 - vertexColor.b,
        1.0
    );
}
