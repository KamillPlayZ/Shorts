package hu.kamillplayz.shorts.videos

import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Particle.DustTransition
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class RedstoneDyeListener : Listener {

    @EventHandler
    fun onRedstoneDye(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        if (event.item?.type != Material.LIGHT_BLUE_DYE) return

        val block = event.clickedBlock ?: return

        if (!(block.type == Material.REDSTONE_ORE || block.type == Material.DEEPSLATE_REDSTONE_ORE)) return

        event.item!!.amount--
        event.isCancelled = true

        when (block.type) {
            Material.REDSTONE_ORE -> block.type = Material.DIAMOND_ORE
            Material.DEEPSLATE_REDSTONE_ORE -> block.type = Material.DEEPSLATE_DIAMOND_ORE
            else -> return
        }

        event.player.playSound(block.location, Sound.BLOCK_AMETHYST_CLUSTER_PLACE, 1f, 1f)

        displayEdgeParticles(block)
    }

    private fun displayEdgeParticles(block: Block) {
        val world = block.world
        val loc = block.location

        // Get the coordinates of the block
        val x = loc.blockX.toDouble()
        val y = loc.blockY.toDouble()
        val z = loc.blockZ.toDouble()

        val primaryColor = Color.fromRGB(127, 204, 255)
        val secondaryColor = Color.fromRGB(95, 155, 228)
        val dustTransition = DustTransition(primaryColor, secondaryColor, 1.0f)

        // Number of particles per edge
        val particlesPerEdge = 5

        // Display particles on all 12 edges of the block
        for (i in 0..particlesPerEdge) {
            val progress = i.toDouble() / particlesPerEdge

            // Bottom edges (y)
            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, x + progress, y, z, 1, 0.0, 0.0, 0.0, 0.0, dustTransition)
            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, x + progress, y, z + 1, 1, 0.0, 0.0, 0.0, 0.0, dustTransition)
            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, x, y, z + progress, 1, 0.0, 0.0, 0.0, 0.0, dustTransition)
            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, x + 1, y, z + progress, 1, 0.0, 0.0, 0.0, 0.0, dustTransition)

            // Top edges (y + 1)
            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, x + progress, y + 1, z, 1, 0.0, 0.0, 0.0, 0.0, dustTransition)
            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, x + progress, y + 1, z + 1, 1, 0.0, 0.0, 0.0, 0.0, dustTransition)
            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, x, y + 1, z + progress, 1, 0.0, 0.0, 0.0, 0.0, dustTransition)
            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, x + 1, y + 1, z + progress, 1, 0.0, 0.0, 0.0, 0.0, dustTransition)

            // Vertical edges
            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, x, y + progress, z, 1, 0.0, 0.0, 0.0, 0.0, dustTransition)
            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, x + 1, y + progress, z, 1, 0.0, 0.0, 0.0, 0.0, dustTransition)
            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, x, y + progress, z + 1, 1, 0.0, 0.0, 0.0, 0.0, dustTransition)
            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, x + 1, y + progress, z + 1, 1, 0.0, 0.0, 0.0, 0.0, dustTransition)
        }
    }
}