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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.registry.AdditionalCatalogRegistryModule;
import org.spongepowered.api.registry.RegistrationPhase;
import org.spongepowered.api.registry.util.DelayedRegistration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class TrailRegistry implements AdditionalCatalogRegistryModule<Trail> {

    private static final TrailRegistry INSTANCE = new TrailRegistry();

    private final Map<String, Trail> trails = new HashMap<>();

    private TrailRegistry() { }

    static TrailRegistry getInstance() {
        return INSTANCE;
    }

    private Trail defaultTrail = new Trail(Constants.HEARTS_ID, Constants.HEARTS_NAME, Constants.DEFAULT_PERIOD, Constants.DEFAULT_RADIUS, ParticleEffect.builder()
        .type(ParticleTypes.HEART)
        .quantity(7)
        .option(ParticleOptions.VELOCITY, Constants.DEFAULT_VELOCITY)
        .build());

    Trail getDefaultTrail() {
        return this.defaultTrail;
    }

    @Override
    public Optional<Trail> getById(String id) {
        String key = checkNotNull(id).toLowerCase(Locale.ENGLISH);
        if (!key.contains(":")) {
            key = Constants.MOD_ID + ":" + key;
        }
        return Optional.ofNullable(this.trails.get(key));
    }

    @Override
    public Collection<Trail> getAll() {
        return ImmutableList.copyOf(this.trails.values());
    }

    @Override
    public void registerAdditionalCatalog(Trail extraCatalog) {
        checkNotNull(extraCatalog, "CatalogType cannot be null");
        checkArgument(!extraCatalog.getId().isEmpty(), "Id cannot be empty");
        checkArgument(!this.trails.containsKey(extraCatalog.getId()), "Duplicate Id: " + extraCatalog.getId());
        this.trails.put(extraCatalog.getId().toLowerCase(Locale.ENGLISH), extraCatalog);
    }

    @DelayedRegistration(RegistrationPhase.INIT)
    @Override
    public void registerDefaults() {
    }

    void registerFromConfig(TrailConfig config) {
        config.trails.forEach(trail -> {
            if (!trail.validate()) {
                return;
            }
            final String key = trail.getId().toLowerCase(Locale.ENGLISH);
            if (this.trails.containsKey(key)) {
                HappyTrails.getInstance().logger.warn("Trail is already registered: " + key);
                return;
            }
            this.trails.put(key, trail);
        });

        Trail trail = this.trails.get(config.defaultTrail);
        if (trail == null) {
            config.defaultTrail = this.getDefaultTrail().getId();
        }

    }
}
