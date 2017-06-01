package com.gabizou.happytrails;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleTypes;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class TrailConfig {

    public static final TypeToken<TrailConfig> TYPE_TOKEN = new TypeToken<TrailConfig>() {};

    @Setting public List<Trail> trails = generateDefaultList();

    @Setting public String defaultTrail;


    private List<Trail> generateDefaultList() {
        final ArrayList<Trail> trails = new ArrayList<>();
        trails.add(new Trail("happytrails:hearts", "Hearts", 10, 30, ParticleEffect.builder()
        .type(ParticleTypes.HEART)
            .quantity(7)
            .option(ParticleOptions.VELOCITY, Trail.DEFAULT_VELOCITY)
            .build()));
        this.defaultTrail = "happytrails:hearts";
        trails.add(new Trail("happytrails:villager_happy", "Happy Villager", 10, 30, ParticleEffect.builder()
            .type(ParticleTypes.HAPPY_VILLAGER)
            .quantity(13)
            .option(ParticleOptions.VELOCITY, Trail.DEFAULT_VELOCITY)
            .option(ParticleOptions.OFFSET, Trail.DEFAULT_VELOCITY)
            .build()));
        return trails;
    }
}
