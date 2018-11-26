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
package net.reflxction.namemodifier.listeners;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.reflxction.namemodifier.commons.Settings;

/**
 * Listener which modifies the name in messages
 */
public class ModifyingListener {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent event) {
        if (!Settings.ENABLED.get()) return;
        String name = Minecraft.getMinecraft().getSession().getUsername();
        String message = event.message.getFormattedText();
        if (message.contains(name)) {
            message = message.replace(name, Settings.NAME.get());
            event.message = new ChatComponentText(message).setChatStyle(event.message.getChatStyle());
        }
    }
}
