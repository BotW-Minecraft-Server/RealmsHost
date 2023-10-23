package link.botwmcs.samchai.realmshost.client.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

public class ColorButton extends Button {
    int color;
    public ColorButton(int x, int y, int buttonWidth, int buttonHeight, Component component, int color, OnPress onPress) {
        super(x, y, buttonWidth, buttonHeight, component, onPress, Supplier::get);
        this.color = color;
    }
}
