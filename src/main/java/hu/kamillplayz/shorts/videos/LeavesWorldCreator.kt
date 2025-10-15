package hu.kamillplayz.shorts.videos

import org.bukkit.Bukkit
import org.bukkit.WorldCreator
import org.bukkit.WorldType

class LeavesWorldCreator {

    init {
        val creator = WorldCreator("leaves_flat")
            .type(WorldType.FLAT)
            .generatorSettings("""
                {
                    "layers": [
                        {
                            "block": "minecraft:flowering_azalea_leaves",
                            "height": 1
                        },
                        {
                            "block": "minecraft:azalea_leaves",
                            "height": 1
                        },
                        {
                            "block": "minecraft:oak_leaves",
                            "height": 1
                        }
                    ],                    
                    "biome": "minecraft:plains"
                }
            """.trimIndent())

        if (Bukkit.getWorld("leaves_flat") == null) {
            Bukkit.createWorld(creator)
        }
    }
}