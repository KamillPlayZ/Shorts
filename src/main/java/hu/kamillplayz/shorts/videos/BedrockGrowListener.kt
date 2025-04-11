package hu.kamillplayz.shorts.videos

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class BedrockGrowListener : Listener {

    @EventHandler
    fun onPlace(event: BlockPlaceEvent) {
        if (event.player.gameMode != GameMode.SURVIVAL) return

        val player = event.player
        val world = player.world
        val location = event.block.location

        var bedrockCount = 0

        for (x in -1..1) {
            for (y in -1..1) {
                for (z in -1..1) {
                    val block = world.getBlockAt(location.blockX + x, location.blockY + y, location.blockZ + z)
                    if (block.type == Material.BEDROCK) {
                        bedrockCount++
                    }
                }
            }
        }

        if (bedrockCount >= 3) {
            world.getBlockAt(location.clone().add(1.0, 2.0, 0.0)).type = Material.BEDROCK
            world.getBlockAt(location.clone().add(0.0, 2.0, 1.0)).type = Material.BEDROCK
            world.getBlockAt(location.clone().add(-1.0, 2.0, 0.0)).type = Material.BEDROCK
            world.getBlockAt(location.clone().add(0.0, 2.0, -1.0)).type = Material.BEDROCK

            player.playSound(location, Sound.BLOCK_BASALT_BREAK, 1f, 1f)
        }
    }
}