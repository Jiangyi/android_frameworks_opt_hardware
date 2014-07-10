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

import com.android.internal.R;

import org.omnirom.hardware.util.FileUtils;
import org.omnirom.hardware.util.DataParser;
import org.omnirom.hardware.util.DataParser.Data;

import java.io.File;

/**
 * mDNIe (Mobile Digital Natural Image Engine) mode support for mainly Samsung devices
 */
public class MDNIeMode {

    private static DataParser dp;
    private static Data data = dp.getData(com.android.internal.R.array.hwf_mDNIeMode);

    private static final String PATH = data.value[0];
    private static final String MODE_DEFAULT = data.value[1];

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
     * What is the current mDNIe mode?
     */
    public static int getCurrentMode() {
        if (PATH != null) {
            return Integer.parseInt(FileUtils.readOneLine(PATH));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * What is the default mDNIe mode?
     */
    public static int getDefaultMode() {
        if (MODE_DEFAULT != null) {
            return Integer.parseInt(MODE_DEFAULT);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Sets the mDNIe mode.
     *
     * @return boolean Must return true if the operation succeeded, false otherwise
     */
    public static boolean setMode(int mode) {
        File f = new File(PATH);

        if (f.exists()) {
            return FileUtils.writeLine(PATH, String.valueOf(mode));
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
