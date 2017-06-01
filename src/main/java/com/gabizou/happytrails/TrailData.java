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

    public static final TypeToken<Trail> TRAIL_TOKEN = new TypeToken<Trail>() {};
    public static final TypeToken<Value<Trail>> TRAIL_VALUE_TOKEN = new TypeToken<Value<Trail>>() {};

    public static final Key<Value<Trail>> TRAIL = KeyFactory.makeSingleKey(TRAIL_TOKEN, TRAIL_VALUE_TOKEN, DataQuery.of("trail"), "happytrail:trail", "Trail");

    public TrailData(Trail value) {
        super(value, TRAIL);
    }

    @Override
    public Optional<TrailData> fill(DataHolder dataHolder, MergeFunction overlap) {
        return dataHolder.get(TrailData.class).map(data -> this.setValue(data.getValue()));
    }

    @Override
    public Optional<TrailData> from(DataContainer container) {
        if (!container.contains(TRAIL.getQuery())) {
            return Optional.empty();
        }
        final Trail trail = container.getCatalogType(TRAIL.getQuery(), Trail.class).orElse(Trail.HEART);
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

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
            .set(TRAIL.getQuery(), this.getValue().getId());
    }

    public Trail getTrail() {
        return this.getValue();
    }

    public TrailData setTrail(Trail trail) {
        this.setValue(trail);
        return this;
    }

    public static final class Immutable
        extends AbstractImmutableSingleCatalogData<Trail, Immutable, TrailData>
        implements ImmutableDataManipulator<Immutable, TrailData> {

        public Immutable(Trail value) {
            super(value, Trail.HEART, TRAIL);
        }

        @Override
        public TrailData asMutable() {
            return new TrailData(this.value);
        }

        @Override
        public int getContentVersion() {
            return 1;
        }

        @Override
        public DataContainer toContainer() {
            return super.toContainer()
                .set(TrailData.TRAIL.getQuery(), this.value.getId());
        }
    }

    public static final class Builder extends AbstractDataBuilder<TrailData> implements DataManipulatorBuilder<TrailData, Immutable> {

        public Builder() {
            super(TrailData.class, 1);
        }

        @Override
        public TrailData create() {
            return new TrailData(Trail.HEART);
        }

        @Override
        public Optional<TrailData> createFrom(DataHolder dataHolder) {
            final Optional<TrailData> trailData = dataHolder.get(TrailData.class);
            return trailData.isPresent() ? trailData : Optional.of(new TrailData(Trail.HEART));
        }

        @Override
        protected Optional<TrailData> buildContent(DataView container) throws InvalidDataException {
            if (container.contains(TRAIL.getQuery())) {
                final Trail trail = container.getCatalogType(TRAIL.getQuery(), Trail.class).orElse(TrailRegistry.getInstance().DEFAULT_TRAIL);
                return Optional.of(new TrailData(trail));
            }
            return Optional.empty();
        }
    }
}
