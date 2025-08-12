package hu.kamillplayz.shorts.videos

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

class OneChunkListener : Listener {

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        val block = event.block
        val world = event.block.world

        for (x in -16..16) {
            for (z in -16..16) {
                if (x == 0 && z == 0) {
                    for (drop in block.drops) {
                        world.dropItem(block.location, drop)
                    }
                    return
                }

                val location = block.location.clone().add(x * 16.0, 0.0, z * 16.0)

                world.getBlockAt(location).setType(Material.AIR)
            }
        }
    }

    @EventHandler
    fun onPlace(event: BlockPlaceEvent) {
        val block = event.block
        val world = event.block.world

        for (x in -16..16) {
            for (z in -16..16) {
                if (x == 0 && z == 0) continue

                val location = block.location.clone().add(x * 16.0, 0.0, z * 16.0)

                world.getBlockAt(location).setType(block.type)
            }
        }
    }
}