package hu.kamillplayz.shorts.videos

import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.TreeType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleSneakEvent

class TreeGrowListener : Listener {

    @EventHandler
    fun onTreeGrow(event: PlayerToggleSneakEvent) {
        if (!event.player.isSneaking) return

        val blockX = event.player.location.blockX
        val blockY = event.player.location.blockY
        val blockZ = event.player.location.blockZ

        for (x in -5..5) {
            for (y in -5..5) {
                for (z in -5..5) {
                    val block = event.player.world.getBlockAt(blockX + x, blockY + y, blockZ + z)
                    if (block.type == Material.AIR) continue

                    if (!block.type.toString().contains("_SAPLING")) continue
                    if (Math.random() > 0.3) continue

                    val location = block.location.toCenterLocation()

                    for (i in 0..10) {
                        val xOffset = Math.random() * 0.5 - 0.25
                        val yOffset = Math.random() * 0.5 - 0.25
                        val zOffset = Math.random() * 0.5 - 0.25
                        block.world.spawnParticle(Particle.HAPPY_VILLAGER, location, 10, xOffset, yOffset, zOffset, 0.0)
                    }
                    event.player.playSound(location, Sound.ITEM_BONE_MEAL_USE, 1f, 1f)

                    if (Math.random() > 0.2) continue

                    val treeType = getTreeType(block.type)
                    block.type = Material.AIR

                    block.world.generateTree(location, treeType)
                }
            }
        }
    }

    private fun getTreeType(material: Material): TreeType {
        return when (material) {
            Material.OAK_SAPLING -> TreeType.TREE
            Material.SPRUCE_SAPLING -> TreeType.REDWOOD
            Material.BIRCH_SAPLING -> TreeType.BIRCH
            Material.JUNGLE_SAPLING -> TreeType.JUNGLE
            Material.ACACIA_SAPLING -> TreeType.ACACIA
            Material.DARK_OAK_SAPLING -> TreeType.DARK_OAK
            Material.MANGROVE_PROPAGULE -> TreeType.MANGROVE
            Material.CHERRY_SAPLING -> TreeType.CHERRY
            else -> TreeType.TREE // Default to oak
        }
    }
}