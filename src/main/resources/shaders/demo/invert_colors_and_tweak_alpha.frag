#version 330 core

in vec3 vertexColor;

out vec4 fragColor;

void main() {

    // Reverse colors
    fragColor.rgb = 1.0 - vertexColor;

    // Change alpha
    fragColor.a =  1.5 - (
        abs(vertexColor.r - vertexColor.g) +
        abs(vertexColor.g - vertexColor.b) +
        abs(vertexColor.b - vertexColor.r)
    );

}
