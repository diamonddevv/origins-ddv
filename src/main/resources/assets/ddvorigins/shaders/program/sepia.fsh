#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    vec4 tex = texture(DiffuseSampler, texCoord);

    fragColor = vec4(tex.rgb * mat3(
    .393, .769, .189,
    .349, .686, .168,
    .272, .534, .131), 1.0);
}