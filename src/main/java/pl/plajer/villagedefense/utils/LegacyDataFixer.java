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

package pl.plajer.villagedefense.utils;

import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;

import pl.plajer.villagedefense.ConfigPreferences;
import pl.plajer.villagedefense.Main;
import pl.plajer.villagedefense.api.StatsStorage;
import pl.plajer.villagedefense.utils.constants.Constants;
import pl.plajerlair.commonsbox.minecraft.configuration.ConfigUtils;

/**
 * @author Plajer
 * <p>
 * Created at 14.12.2018
 */
public class LegacyDataFixer {

  public static final int DATA_VERSION = 1;
  private Main plugin;

  public LegacyDataFixer(Main plugin) {
    this.plugin = plugin;
    initiate();
  }

  private void initiate() {
    FileConfiguration config = ConfigUtils.getConfig(plugin, Constants.Files.STATS.getName());
    if (config.getInt("data-version", 0) >= DATA_VERSION || plugin.getConfigPreferences().getOption(ConfigPreferences.Option.DATABASE_ENABLED)) {
      return;
    }
    Debugger.debug(Level.WARNING, "Legacy fixer started, fixing player data for yaml storage...");

    int migrated = 0;

    for (String key : config.getKeys(false)) {
      if (key.equals("data-version")) {
        continue;
      }

      int migratedLocal = 0;

      for (StatsStorage.StatisticType stat : StatsStorage.StatisticType.values()) {
        if (!config.isSet(key + "." + stat)) {
          continue;
        }
        int value = config.getInt(key + "." + stat);
        config.set(key + "." + stat.getName(), config.getInt(key + "." + stat.getName(), 0) + value);
        config.set(key + "." + stat, null);
        migratedLocal++;
      }
      Debugger.debug(Level.WARNING, "[Legacy fixer] Migrated new record {0} records fixed", migratedLocal);
      migrated++;
    }
    config.set("data-version", DATA_VERSION);
    ConfigUtils.saveConfig(plugin, config, Constants.Files.STATS.getName());
    Debugger.debug(Level.WARNING, "[Legacy fixer] Fixed and migrated {0} records. Data scheme fixed.", migrated);
  }

}
