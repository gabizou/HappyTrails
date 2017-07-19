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
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
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

    public static final TypeToken<Trail> TRAIL_TYPE_TOKEN;
    static {
        TRAIL_TYPE_TOKEN = new TypeToken<Trail>() {};
        TypeSerializers.getDefaultSerializers().registerType(TRAIL_TYPE_TOKEN, new TypeSerializer<Trail>() {
            @Override
            public Trail deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {

                Class<?> clazz = type.getRawType();
                return Sponge.getDataManager()
                    .deserialize(Trail.class, DataTranslators.CONFIGURATION_NODE.translate(value))
                    .orElseThrow(() -> new ObjectMappingException("Could not translate DataSerializable of type: " + clazz.getName()));
            }

            @Override
            public void serialize(TypeToken<?> type, Trail obj, ConfigurationNode value) throws ObjectMappingException {
                    value.setValue(DataTranslators.CONFIGURATION_NODE.translate(obj.toContainer()));
                }
            }
        );
    }

    public static final DataQuery ID_QUERY = DataQuery.of("id");
    public static final DataQuery NAME_QUERY = DataQuery.of("name");
    public static final DataQuery PERIOD = DataQuery.of("period");
    public static final DataQuery RADIUS = DataQuery.of("radius");
    public static final DataQuery PARTICLE_EFFECT = DataQuery.of("particle_effect");

    public static final Vector3d DEFAULT_VELOCITY = new Vector3d(0.5, 1, 0.4);

    @Setting private String id;
    @Setting private String name;
    @Setting public int period = 5;
    @Setting public int radius = 10;
    @Setting public ParticleEffect effect = ParticleEffect.builder()
        .type(ParticleTypes.HEART)
        .quantity(10)
        .option(ParticleOptions.VELOCITY, DEFAULT_VELOCITY)
        .option(ParticleOptions.SCALE, 1d)
        .build();

    Trail(String id, String name, int period, int radius, ParticleEffect effect) {
        this.id = id;
        this.name = name;
        this.period = period;
        this.radius = radius;
        this.effect = effect;
    }

    public void playEffect(Player player) {
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
        return new MemoryDataContainer() // In API 6, this is DataContainer.createNew()
            .set(Queries.CONTENT_VERSION, this.getContentVersion())
            .set(ID_QUERY, this.id)
            .set(NAME_QUERY, this.name)
            .set(PERIOD, this.period)
            .set(RADIUS, this.radius)
            .set(PARTICLE_EFFECT, this.effect);
    }

    public static final class Builder extends AbstractDataBuilder<Trail> implements DataBuilder<Trail> {

        public Builder() {
            super(Trail.class, 1);
        }

        @Override
        protected Optional<Trail> buildContent(DataView container) throws InvalidDataException {
            if (!container.contains(Trail.ID_QUERY, Trail.NAME_QUERY, Trail.PARTICLE_EFFECT)) {
                return Optional.empty();
            }
            final String id = container.getString(Trail.ID_QUERY).get();
            final String name = container.getString(Trail.NAME_QUERY).get();
            final ParticleEffect effect = container.getSerializable(Trail.PARTICLE_EFFECT, ParticleEffect.class).get();
            final int period = container.getInt(Trail.PERIOD).orElse(10);
            final int radius = container.getInt(Trail.RADIUS).orElse(30);
            return Optional.of(new Trail(id, name, period, radius, effect));
        }
    }
}
