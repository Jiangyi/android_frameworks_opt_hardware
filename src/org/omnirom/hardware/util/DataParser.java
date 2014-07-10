/*
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

package org.omnirom.hardware.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

public class DataParser {

/*
I imagine it to look something like: 

<array name="hwf_featurename>
    <item>supported</item>
    <item>type of setting (bool, slider, etc.)</item>
    <item>path</item> <-- Path will always be the last, since some functions will require more than one path
</array>
*/
    private static Context mContext;

    // Everything needs a context )-:
    public DataParser(Context context) {
        mContext = context;
    }

    public class Data {
        public boolean supported;
        public String type;
        public String[] value;
    }

    private static final int SUPPORTED = 0;
    private static final int TYPE = 1;
    private static Data data;

    // Components call this method to 
    public static Data getData(int id) {
        Resources res = mContext.getResources();
        TypedArray ta = res.obtainTypedArray(id);

        // Parse data and store in object
        data.supported = ta.getBoolean(SUPPORTED, false);
        data.type = ta.getString(TYPE);

        int numOfPaths = ta.length() - 2; //Whatever is left in the array would be paths
        data.value = new String[numOfPaths];

        for (int i = 0; i < numOfPaths; i++) {
            data.value[i] = ta.getString(i + 2);
        }

        // Cleanup and return
        ta.recycle();
        return data;
    }
}
