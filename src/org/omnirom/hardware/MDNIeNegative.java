/*
 * Copyright (C) 2013 The CyanogenMod Project
 * Copyright (C) 2014 The OmniROM Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.omnirom.hardware;

import org.omnirom.hardware.util.FileUtils;
import org.omnirom.hardware.util.DataParser;
import org.omnirom.hardware.util.DataParser.Data;

import java.io.File;

/**
 * mDNIe (Mobile Digital Natural Image Engine) negative mode support for mainly Samsung devices
 */
public class MDNIeMode {

    private static Data data = DataParser.getData(R.array.hwf_mDNIeNegative);

    private static final String PATH = data.value[0];

    /**
     * Whether device supports mDNIe modes.
     *
     * @return boolean Supported devices must return always true
     */
    public static boolean isSupported() {
        if (data.supported && PATH != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Is mDNIe negative mode currently enabled?
     */
    public static boolean isEnabled() {
        if (PATH != null) {
            return (Integer.parseInt(FileUtils.readOneLine(PATH)) == 1);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Enable/disable mDNIe negative mode.
     *
     * @return boolean Must return true if the operation succeeded, false otherwise
     */
    public static boolean setEnabled(boolean state) {
        if (PATH != null) {
            return FileUtils.writeLine(PATH, state ? "1" : "0");
        } else {
            return false;
        }
    }

    /**
     * This method returns the type of setting this is
     *
     * @return String Used to determine what kind of UI setup this is going to use.
     */
    public static String getType() {
        return data.type;
    }
}
