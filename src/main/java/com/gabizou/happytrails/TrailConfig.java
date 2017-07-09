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
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.util.Color;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class TrailConfig {

    public static final TypeToken<TrailConfig> TYPE_TOKEN = new TypeToken<TrailConfig>() {};

    @Setting public List<Trail> trails = generateDefaultList();

    @Setting public String defaultTrail;


    private List<Trail> generateDefaultList() {
        final ArrayList<Trail> trails = new ArrayList<>();
        trails.add(new Trail(HappyTrails.PLUGIN_ID + ":hearts", "Hearts", 10, 30, ParticleEffect.builder()
        .type(ParticleTypes.HEART)
            .quantity(7)
            .option(ParticleOptions.VELOCITY, Trail.DEFAULT_VELOCITY)
            .build()));
        this.defaultTrail = "happytrails:hearts";
        trails.add(new Trail(HappyTrails.PLUGIN_ID + ":villager_happy", "Happy Villager", 10, 30, ParticleEffect.builder()
            .type(ParticleTypes.HAPPY_VILLAGER)
            .quantity(13)
            .option(ParticleOptions.VELOCITY, Trail.DEFAULT_VELOCITY)
            .option(ParticleOptions.OFFSET, Trail.DEFAULT_VELOCITY)
            .build()));
        trails.add(new Trail(HappyTrails.PLUGIN_ID + ":villager_storm", "Stormy Villager", 10, 30, ParticleEffect.builder()
            .type(ParticleTypes.ANGRY_VILLAGER)
            .quantity(5)
            .option(ParticleOptions.VELOCITY, new Vector3d(0, 0.1, 0))
            .option(ParticleOptions.OFFSET, new Vector3d(0, 3, 0))
            .build()
        ));
        trails.add(new Trail(HappyTrails.PLUGIN_ID + ":crit_strike", "Critical Strike", 5, 20, ParticleEffect.builder()
            .type(ParticleTypes.CRITICAL_HIT)
            .quantity(10)
            .option(ParticleOptions.OFFSET, new Vector3d(10, 3, 10))
            .option(ParticleOptions.COLOR, Color.DARK_CYAN)
            .build()
        ));
        trails.add(new Trail(HappyTrails.PLUGIN_ID + ":cloud", "Clouds", 2, 10, ParticleEffect.builder()
            .type(ParticleTypes.CLOUD)
            .quantity(2)
            .option(ParticleOptions.OFFSET, new Vector3d(0, 3, 0))
            .option(ParticleOptions.VELOCITY, new Vector3d(0.01, 0.01, 0.01))
            .build()
        ));
        return trails;
    }
}
