package hu.kamillplayz.shorts.videos

import hu.kamillplayz.shorts.Shorts
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.block.data.Directional

class MelonGrower {

    init {
        Bukkit.getScheduler().runTaskTimer(Shorts.getInstance(), Runnable {
            for (player in Bukkit.getOnlinePlayers()) {

                val location = player.location
                val x = location.blockX
                val y = location.blockY
                val z = location.blockZ

                for (xOffset in -2..2) {
                    for (yOffset in -2..2) {
                        for (zOffset in -2..2) {
                            val stemBlock = player.world.getBlockAt(x + xOffset, y + yOffset, z + zOffset)
                            if (stemBlock.type != Material.MELON_STEM) continue

                            val nextBlock = player.world.getBlockAt(x + xOffset, y + yOffset, z + zOffset)
                                .getRelative(BlockFace.SOUTH)
                            if (nextBlock.type != Material.AIR) continue
                            nextBlock.type = Material.MELON

                            stemBlock.type = Material.ATTACHED_MELON_STEM

                            val attachedStemData = stemBlock.blockData as Directional
                            attachedStemData.facing = BlockFace.SOUTH
                            stemBlock.blockData = attachedStemData

                            return@Runnable
                        }
                    }
                }
            }
        }, 1L, 1L)
    }
}