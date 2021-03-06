/*
 * Village Defense - Protect villagers from hordes of zombies
 * Copyright (C) 2019  Plajer's Lair - maintained by Plajer and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pl.plajer.villagedefense.api.event.game;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import pl.plajer.villagedefense.api.event.VillageEvent;
import pl.plajer.villagedefense.arena.Arena;

/**
 * @author Plajer
 * @since 3.0.0-ALPHA build 13
 * <p>
 * Called when player is attempting to leave arena.
 */
public class VillageGameLeaveAttemptEvent extends VillageEvent {

  private static final HandlerList HANDLERS = new HandlerList();
  private final Player player;

  public VillageGameLeaveAttemptEvent(Player player, Arena arena) {
    super(arena);
    this.player = player;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }

  public Player getPlayer() {
    return player;
  }

  public HandlerList getHandlers() {
    return HANDLERS;
  }

}
