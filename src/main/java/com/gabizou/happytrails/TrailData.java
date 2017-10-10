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

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleCatalogData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractSingleCatalogData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class TrailData extends AbstractSingleCatalogData<Trail, TrailData, TrailData.Immutable> implements DataManipulator<TrailData, TrailData.Immutable> {

    private static final TypeToken<Trail> TRAIL_TOKEN = new TypeToken<Trail>() {};
    private static final TypeToken<Value<Trail>> TRAIL_VALUE_TOKEN = new TypeToken<Value<Trail>>() {};

    static final Key<Value<Trail>> TRAIL = KeyFactory.makeSingleKey(TRAIL_TOKEN, TRAIL_VALUE_TOKEN, DataQuery.of("trail"), "happytrail:trail", "Trail");

    TrailData(Trail value) {
        super(value, TRAIL);
    }

    @Override
    public Optional<TrailData> fill(DataHolder dataHolder, MergeFunction overlap) {
        final Optional<TrailData> trailData = dataHolder.get(TrailData.class);
        return trailData.isPresent() ? trailData.map(data -> this.setValue(data.getValue())) : Optional.of(this);
    }

    @Override
    public Optional<TrailData> from(DataContainer container) {
        if (!container.contains(TRAIL.getQuery())) {
            return Optional.empty();
        }
        final Trail trail = container.getCatalogType(TRAIL.getQuery(), Trail.class).orElse(TrailRegistry.getInstance().getDefaultTrail());
        this.setValue(trail);
        return Optional.of(this);
    }

    @Override
    public TrailData copy() {
        return new TrailData(this.getValue());
    }

    @Override
    public Immutable asImmutable() {
        return new Immutable(this.getValue());
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    Trail getTrail() {
        return this.getValue();
    }

    void setTrail(Trail trail) {
        this.setValue(trail);
    }

    public static final class Immutable
        extends AbstractImmutableSingleCatalogData<Trail, Immutable, TrailData>
        implements ImmutableDataManipulator<Immutable, TrailData> {

        Immutable(Trail value) {
            super(value, TrailRegistry.getInstance().getDefaultTrail(), TRAIL);
        }

        @Override
        public TrailData asMutable() {
            return new TrailData(this.value);
        }

        @Override
        public int getContentVersion() {
            return 1;
        }

    }

    public static final class Builder extends AbstractDataBuilder<TrailData> implements DataManipulatorBuilder<TrailData, Immutable> {

        Builder() {
            super(TrailData.class, 1);
        }

        @Override
        public TrailData create() {
            return new TrailData(TrailRegistry.getInstance().getDefaultTrail());
        }

        @Override
        public Optional<TrailData> createFrom(DataHolder dataHolder) {
            final Optional<TrailData> trailData = dataHolder.get(TrailData.class);
            return trailData.isPresent() ? trailData : Optional.of(new TrailData(TrailRegistry.getInstance().getDefaultTrail()));
        }

        @Override
        protected Optional<TrailData> buildContent(DataView container) throws InvalidDataException {
            if (container.contains(TRAIL.getQuery())) {
                final Trail trail = container.getCatalogType(TRAIL.getQuery(), Trail.class).orElse(TrailRegistry.getInstance().getDefaultTrail());
                return Optional.of(new TrailData(trail));
            }
            return Optional.empty();
        }
    }
}
