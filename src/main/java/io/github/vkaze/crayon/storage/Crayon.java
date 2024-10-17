package io.github.vkaze.crayon.storage;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.JBColor;

@SuppressWarnings("ImmutableEnumChecker")
public enum Crayon {
    BLUE(new JBColor(0xeaf6ff, 0x4f556b)),
    GREEN(new JBColor(0xeffae7, 0x49544a)),
    ORANGE(new JBColor(0xf6e9dc, 0x806052)),
    ROSE(new JBColor(0xf2dcda, 0x6e535b)),
    VIOLET(new JBColor(0xe6e0f1, 0x534a57)),
    YELLOW(new JBColor(0xffffe4, 0x4f4b41));
    private final JBColor color;
    private final String colorName;

    Crayon(JBColor color) {
        this.color = color;
        this.colorName = StringUtil.capitalize(name().toLowerCase());
    }

    public JBColor getColor() {
        return color;
    }

    public String getColorName() {
        return colorName;
    }
}
