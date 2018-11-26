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
import net.reflxction.namemodifier.NameModifier;

/**
 * A class with all settings as constants
 */
public class Settings {

    // Whether the mod is enabled or not
    public static final Setting<Boolean> ENABLED = new Setting<>("Enabled", true);

    // Whether the mod should send updates or check for them
    public static final Setting<Boolean> SEND_UPDATES = new Setting<>("SendUpdates", true);

    // The fake name of the player
    public static final Setting<String> NAME = new Setting<>("Name", Minecraft.getMinecraft().getSession().getUsername());

    /**
     * Represents a setting
     *
     * @param <T> Setting type
     */
    public static class Setting<T> {

        // The actual value of the setting
        private Object value;

        // The config category of the setting
        private String category;

        // The config name of the setting
        private String configName;

        // The default value of the setting.
        private Object defaultValue;

        /**
         * Initiates a new Setting
         *
         * @param category     Category name
         * @param configName   Config name
         * @param defaultValue Default value of the setting
         */
        private Setting(String category, String configName, Object defaultValue) {
            this.category = category;
            this.configName = configName;
            this.defaultValue = defaultValue;
            if (defaultValue instanceof Boolean)
                value = NameModifier.INSTANCE.getConfig().get(category, configName, (boolean) defaultValue).getBoolean();
            else if (defaultValue instanceof Integer)
                value = NameModifier.INSTANCE.getConfig().get(category, configName, (int) defaultValue).getInt();
            else if (defaultValue instanceof Double)
                value = NameModifier.INSTANCE.getConfig().get(category, configName, (double) defaultValue).getDouble();
            else if (defaultValue instanceof String)
                value = NameModifier.INSTANCE.getConfig().get(category, configName, (String) defaultValue).getString();
        }

        /**
         * Initiates a new setting in the "Settings" category
         *
         * @param configName   Category name
         * @param defaultValue Default value of the setting
         */
        private Setting(String configName, Object defaultValue) {
            this("Settings", configName, defaultValue);
        }

        /**
         * Returns the value of the setting
         *
         * @return The value of the setting
         */
        public T get() {
            //noinspection unchecked
            return (T) value;
        }

        /**
         * Sets the value of the setting and updates in the config
         *
         * @param value Value to set
         */
        public void set(T value) {
            this.value = value;
            if (value instanceof Boolean)
                NameModifier.INSTANCE.getConfig().get(category, configName, (Boolean) defaultValue).set((Boolean) value);
            else if (value instanceof Integer)
                NameModifier.INSTANCE.getConfig().get(category, configName, (Integer) defaultValue).set((Integer) value);
            else if (value instanceof Double)
                NameModifier.INSTANCE.getConfig().get(category, configName, (Double) defaultValue).set((Double) value);
            else if (value instanceof String)
                NameModifier.INSTANCE.getConfig().get(category, configName, (String) defaultValue).set((String) value);
            else
                NameModifier.INSTANCE.getConfig().get(category, configName, defaultValue.toString()).set(value.toString());
            NameModifier.INSTANCE.getConfig().save();
        }
    }
}
