/*
 * * Copyright 2018 github.com/ReflxctionDev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.reflxction.namemodifier.commons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.reflxction.namemodifier.utils.SimpleSender;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;

public class NameGUI extends GuiScreen {

    private GuiTextField originalNameField;
    private GuiTextField coloredNameField;

    private static final int AQUA = Color.CYAN.getRGB();
    private static final int GREEN = Color.GREEN.getRGB();

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        try {
            drawDefaultBackground();
            originalNameField.drawTextBox();
            coloredNameField.drawTextBox();
            drawString(Minecraft.getMinecraft().fontRendererObj, "Colored Form", width / 2 - 25, height / 2 - 36, GREEN);
            drawString(Minecraft.getMinecraft().fontRendererObj, "Original Player Name", width / 2 - 25, height / 2 - 80, AQUA);
            super.drawScreen(mouseX, mouseY, partialTicks);
        } catch (Throwable ignored) {
        }
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button.id == 2) {
            if (originalNameField.getText().isEmpty()) {
                SimpleSender.send("&cThe command field is required!");
            }
            if (coloredNameField.getText().isEmpty()) {
                SimpleSender.send("&cThe alias field is required!");
            }
            if (!coloredNameField.getText().isEmpty() && !originalNameField.getText().isEmpty()) {
                String name = originalNameField.getText();
                String colored = coloredNameField.getText();

                NameManager.MANAGER.set(name, colored);
                SimpleSender.send("&Name &b" + name + " will be colored as &b" + colored);
                coloredNameField.setText("");
                originalNameField.setText("");
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        Mouse.setGrabbed(false);

        // Text fields
        coloredNameField = new GuiTextField(6, fontRendererObj, width / 2 - 70, height / 2 - 70, 140, 20);
        originalNameField = new GuiTextField(7, fontRendererObj, width / 2 - 70, height / 2 - 26, 140, 20);

        // Buttons
        buttonList.add(new GuiButton(2, width / 2 - 70, height / 2, 140, 20, ChatColor.format("&aColor!")));
        Mouse.setGrabbed(false);

    }

    @Override
    protected void keyTyped(char par1, int par2) throws IOException {
        super.keyTyped(par1, par2);
        originalNameField.textboxKeyTyped(par1, par2);
        coloredNameField.textboxKeyTyped(par1, par2);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        try {
            this.originalNameField.updateCursorCounter();
            this.coloredNameField.updateCursorCounter();
        } catch (Throwable ignored) {
        }
    }

    @Override
    protected void mouseClicked(int x, int y, int btn) throws IOException {
        super.mouseClicked(x, y, btn);
        this.originalNameField.mouseClicked(x, y, btn);
        this.coloredNameField.mouseClicked(x, y, btn);
    }
}
