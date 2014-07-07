/*
 * Copyright (C) 2014 The CyanogenMod Project
 *                    The OmniROM Project
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
 * Support for displaying a different serial number in Settings -> About Phone
 * than ro.serialno, which may be different than the actual serial number
 * sticker on the hardware.
 */
public class SerialNumber {

    private static Data data = DataParser.getData(R.array.hwf_altSerialNumber);

    private static final String PATH = data.value[0];

    /**
     * Whether device requires an alternative serial number.
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
     * Returns the alternative serial number to be displayed.
     */
    public static String getSerialNumber() {
        return FileUtils.readOneLine(PATH);
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
