package com.gabizou.happytrails;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.UUID;

public class PlayerWrapper {

    WeakReference<Player> playerReference;
    UUID playerId;
    int cooldown;

    public PlayerWrapper(Player player) {
        this.playerReference = new WeakReference<Player>(player);
        this.playerId = player.getUniqueId();
    }

    public Player getPlayer() {
        if (this.playerReference.get() != null) {
            return this.playerReference.get();
        }
        final Optional<Player> player = Sponge.getServer().getPlayer(this.playerId);
        if (!player.isPresent()) {
            throw new IllegalStateException("Outdated player wrapper reference!");
        }
        this.playerReference = new WeakReference<Player>(player.get());
        return player.get();
    }

}
