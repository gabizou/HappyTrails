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
