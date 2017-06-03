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

    public static TrailRegistry getInstance() {
        return INSTANCE;
    }

    private Trail defaultTrail = new Trail(HappyTrails.PLUGIN_ID + ":hearts", "Hearts", 10, 30, ParticleEffect.builder()
        .type(ParticleTypes.HEART)
        .quantity(7)
        .option(ParticleOptions.VELOCITY, Trail.DEFAULT_VELOCITY)
        .build());

    public Trail getDefaultTrail() {
        return this.defaultTrail;
    }

    @Override
    public Optional<Trail> getById(String id) {
        String key = checkNotNull(id).toLowerCase(Locale.ENGLISH);
        if (!key.contains(":")) {
            key = HappyTrails.PLUGIN_ID + ":" + key;
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
