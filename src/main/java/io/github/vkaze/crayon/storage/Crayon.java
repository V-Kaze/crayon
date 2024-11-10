package io.github.vkaze.crayon.storage;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.ColorIcon;
import com.intellij.util.ui.EmptyIcon;

import java.awt.Color;

@SuppressWarnings("ImmutableEnumChecker")
public enum Crayon {
    BLUE(new JBColor(0xeaf6ff, 0x4f556b)),
    GREEN(new JBColor(0xeffae7, 0x49544a)),
    ORANGE(new JBColor(0xf6e9dc, 0x806052)),
    ROSE(new JBColor(0xf2dcda, 0x6e535b)),
    VIOLET(new JBColor(0xe6e0f1, 0x534a57)),
    YELLOW(new JBColor(0xffffe4, 0x4f4b41));
    private final ColorIcon colorIcon;
    private final String colorName;

    Crayon(Color color) {
        this.colorIcon = new ColorIcon(EmptyIcon.ICON_16.getIconWidth(), color);
        this.colorName = StringUtil.capitalize(name().toLowerCase());
    }

    public ColorIcon getColorIcon() {
        return colorIcon;
    }

    public String getColorName() {
        return colorName;
    }
}
