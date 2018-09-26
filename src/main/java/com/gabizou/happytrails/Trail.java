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

import com.google.common.base.MoreObjects;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.*;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.DataBuilder;
import org.spongepowered.api.data.persistence.DataTranslators;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Objects;
import java.util.Optional;

@ConfigSerializable
public class Trail implements CatalogType, DataSerializable {


    @Setting private String id;
    @Setting private String name;
    @Setting int period = 5;
    @Setting private int radius = 10;
    @Setting private ParticleEffect effect = ParticleEffect.builder()
        .type(ParticleTypes.HEART)
        .quantity(10)
        .option(ParticleOptions.VELOCITY, Constants.DEFAULT_VELOCITY)
        .option(ParticleOptions.SCALE, 1d)
        .build();

    // This is used for configurate.
    @SuppressWarnings("unused")
    Trail() {
        this.id = "";
        this.name = "";

    }

    Trail(String id, String name, int period, int radius, ParticleEffect effect) {
        this.id = id;
        this.name = name;
        this.period = period;
        this.radius = radius;
        this.effect = effect;
    }


    void playEffect(Player player) {
        player.getWorld().spawnParticles(this.effect, player.getLocation().getPosition(), this.radius);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Trail trail = (Trail) o;
        return this.period == trail.period
               && this.radius == trail.radius
               && Objects.equals(this.effect, trail.effect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.effect, this.period, this.radius);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("effect", this.effect)
            .add("period", this.period)
            .add("radius", this.radius)
            .add("id", getId())
            .add("name", getName())
            .toString();
    }

    boolean validate() {
        return this.id != null
               && this.name != null
               && this.effect != null
               && this.period > 0
               && !this.id.isEmpty()
               && this.id.contains(":")
               && !this.name.isEmpty()
               && this.radius > 0;
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return DataContainer.createNew()
            .set(Queries.CONTENT_VERSION, this.getContentVersion())
            .set(Constants.ID_QUERY, this.id)
            .set(Constants.NAME_QUERY, this.name)
            .set(Constants.PERIOD, this.period)
            .set(Constants.RADIUS, this.radius)
            .set(Constants.PARTICLE_EFFECT, this.effect);
    }

    public static final class Builder extends AbstractDataBuilder<Trail> implements DataBuilder<Trail> {

        Builder() {
            super(Trail.class, 1);
        }

        @Override
        protected Optional<Trail> buildContent(DataView container) throws InvalidDataException {
            if (!container.contains(Constants.ID_QUERY, Constants.NAME_QUERY, Constants.PARTICLE_EFFECT)) {
                return Optional.empty();
            }
            final String id = container.getString(Constants.ID_QUERY).get();
            final String name = container.getString(Constants.NAME_QUERY).get();
            final ParticleEffect effect = container.getSerializable(Constants.PARTICLE_EFFECT, ParticleEffect.class).get();
            final int period = container.getInt(Constants.PERIOD).orElse(10);
            final int radius = container.getInt(Constants.RADIUS).orElse(30);
            return Optional.of(new Trail(id, name, period, radius, effect));
        }
    }
}
