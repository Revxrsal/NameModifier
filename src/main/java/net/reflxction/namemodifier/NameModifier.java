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
package net.reflxction.namemodifier;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.reflxction.namemodifier.commons.NameManager;
import net.reflxction.namemodifier.commons.Settings;
import net.reflxction.namemodifier.proxy.IProxy;
import net.reflxction.namemodifier.updater.UpdateManager;
import net.reflxction.namemodifier.updater.VersionChecker;
import net.reflxction.namemodifier.utils.Reference;
import net.reflxction.simplejson.configuration.select.SelectableConfiguration;
import net.reflxction.simplejson.json.JsonFile;

import java.io.File;

/**
 * NameModifier: A flexible mod for applying fake names
 */
@Mod(
        modid = Reference.MOD_ID,
        name = Reference.NAME,
        version = Reference.VERSION,
        acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS
)
public class NameModifier {

    public static final NameModifier INSTANCE = new NameModifier();

    // Config for saving data
    private static final SelectableConfiguration CONFIGURATION = SelectableConfiguration.of(
            JsonFile.of(Minecraft.getMinecraft().mcDataDir + File.separator + "config" + File.separator + "name-modifier.cfg"));

    static {
        CONFIGURATION.register(Settings.class, NameManager.class);
        CONFIGURATION.associate();
        Runtime.getRuntime().addShutdownHook(new Thread(CONFIGURATION::save));
    }

    // Assign proxies of the mod
    @SidedProxy(

            // Client side proxy
            clientSide = Reference.CLIENT_PROXY,

            // Server side proxy
            serverSide = Reference.SERVER_PROXY
    )
    private static IProxy proxy;

    // The update manager
    private UpdateManager updateManager = new UpdateManager(true);

    // The version checker
    private VersionChecker checker = new VersionChecker();

    /**
     * Called before the mod is fully initialized
     * <p>
     * Registries: Initiate variables and client command registries
     *
     * @param event Forge's pre-init event
     */
    @EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    /**
     * Called when the mod has been fully initialized
     * <p>
     * Registries: Events and client-server command registries
     *
     * @param event Forge's init event
     */
    @EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
        proxy.init(event);
    }

    /**
     * Called after the mod has been successfully initialized
     * <p>
     * Registries: Nothing
     *
     * @param event Forge's post init event
     */
    @EventHandler
    public void onFMLPostInitialization(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void onFMLServerStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

    /**
     * The mod update manager
     *
     * @return An instance of the mod update manager
     */
    public UpdateManager getUpdateManager() {
        return updateManager;
    }

    public VersionChecker getChecker() {
        return checker;
    }

}
