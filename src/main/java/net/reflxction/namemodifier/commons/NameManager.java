/*
 * * Copyright 2018-2019 github.com/ReflxctionDev
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

import net.reflxction.simplejson.configuration.select.SelectKey;
import net.reflxction.simplejson.configuration.select.SelectionHolder;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class which manages the custom names for other people
 */
public class NameManager {

    @SelectKey("NamesMap")
    private static final SelectionHolder<Map<String, String>> MAP =
            new SelectionHolder<>(new LinkedHashMap<>());

    public static final NameManager MANAGER = new NameManager();

    /**
     * Overrides the original name and sets its colored form to the one specified
     *
     * @param original Original name
     * @param colored  New colored name
     */
    void set(String original, String colored) {
        MAP.get().remove(original);
        MAP.get().put(colored, original);
    }

    /**
     * Returns the colored name assigned with the original one, or the original name if none is assigned
     *
     * @param original Original name
     * @return The colored name
     */
    public String get(String original) {
        return hasColoredForm(original) ? ChatColor.format(MAP.get().get(original)) : original;
    }

    /**
     * Returns whether the given name has a colored form or not
     *
     * @param original Original name to check
     * @return {@code true} if it has a colored form, {@code false} if otherwise.
     */
    private boolean hasColoredForm(String original) {
        return MAP.get().get(original) != null;
    }

}
