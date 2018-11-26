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

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.reflxction.namemodifier.NameModifier;
import net.reflxction.namemodifier.commons.Settings;
import net.reflxction.namemodifier.utils.Reference;
import net.reflxction.namemodifier.utils.SimpleSender;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Listener which sends the player a notification update
 */
public class NotificationSender {

    // Whether the notification was already sent or not
    private boolean sent;

    @SubscribeEvent
    public void onFMLNetworkClientConnectedToServer(ClientConnectedToServerEvent event) {
        if (!sent && Settings.SEND_UPDATES.get()) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (NameModifier.INSTANCE.getChecker().isUpdateAvailable()) {
                        SimpleSender.send("&eAn update is available for &b" + Reference.NAME + "&e! To update, do &a/nm update&e.");
                        sent = true;
                    }
                }
            }, 2000);
        }
    }
}
