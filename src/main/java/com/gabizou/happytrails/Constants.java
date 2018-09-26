/*
 * This file is part of HappyTrails, licensed under the MIT License (MIT).
 *
 * Copyright (c) Gabriel Harris-Rouquette <https://gabizou.com/>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.gabizou.happytrails;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.data.DataQuery;

public class Constants {

    public static final String MOD_ID = "happytrails";
    public static final String MOD_NAME = "HappyTrails";

    public static final String HEARTS_ID = MOD_ID + ":hearts";
    public static final String DEFAULT_TRAIL_ID = HEARTS_ID;
    public static final String VILLAGER_HAPPY_ID = MOD_ID + ":villager_happy";
    public static final String VILLAGER_STORM_ID = MOD_ID + ":villager_storm";
    public static final String CRIT_STRIKE_ID = MOD_ID + ":crit_strike";
    public static final String CLOUD_ID = MOD_ID + ":cloud";
    public static final String HEARTS_NAME = "Hearts";
    public static final String HAPPY_VILLAGER_NAME = "Happy Villager";
    public static final String STORMY_VILLAGER_NAME = "Stormy Villager";
    public static final String CRITICAL_STRIKE_NAME = "Critical Strike";
    public static final String CLOUDS_NAME = "Clouds";

    public static final DataQuery ID_QUERY = DataQuery.of("id");
    public static final DataQuery NAME_QUERY = DataQuery.of("name");
    public static final DataQuery PERIOD = DataQuery.of("period");
    public static final DataQuery RADIUS = DataQuery.of("radius");
    public static final DataQuery PARTICLE_EFFECT = DataQuery.of("particle_effect");

    public static final Vector3d DEFAULT_VELOCITY = new Vector3d(0.5, 1, 0.4);

    public static final int DEFAULT_PERIOD = 10;
    public static final int DEFAULT_RADIUS = 30;
    public static final String KEY_ID = "trail";
    public static final DataQuery KEY_QUERY = DataQuery.of("trail");

    public static final class DataVersions {

        public static final class Manipulator {

            public static final int TRAIL_DATA_VERSION = 1;
        }

    }
}
