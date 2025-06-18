package hu.kamillplayz.shorts.videos

import org.bukkit.Particle
import org.bukkit.entity.EntityType
import org.bukkit.entity.Wolf
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent

class SkeletonHorse : Listener {

    @EventHandler
    fun onInteract(event: PlayerInteractAtEntityEvent) {
        val entity = event.rightClicked
        if (entity !is Wolf) return

        val item = event.player.inventory.itemInMainHand
        if (item.type != org.bukkit.Material.BONE) return
        if (!item.itemMeta.displayName.contains("Ló")) return

        val world = entity.world
        val location = entity.location

        entity.remove()
        world.spawnEntity(location, EntityType.SKELETON_HORSE)

        for (i in 0..30) {
            val angle = i * 0.2
            val x = location.x + kotlin.math.cos(angle) * 2
            val z = location.z + kotlin.math.sin(angle) * 2
            val y = location.y + (i * 0.1)

            world.spawnParticle(
                org.bukkit.Particle.SOUL,
                x, y, z,
                3, 0.1, 0.1, 0.1, 0.02
            )
        }

        world.spawnParticle(
            org.bukkit.Particle.ENCHANT,
            location.clone().add(0.0, 1.0, 0.0),
            50, 2.0, 1.0, 2.0, 0.5
        )

        world.spawnParticle(
            org.bukkit.Particle.DRAGON_BREATH,
            location.clone().add(0.0, 1.5, 0.0),
            20, 0.5, 0.5, 0.5, 0.02
        )

        world.spawnParticle(
            Particle.WITCH,
            location.clone().add(0.0, 0.5, 0.0),
            15, 1.5, 0.5, 1.5, 0.1
        )

        world.playSound(
            location,
            org.bukkit.Sound.ENTITY_SKELETON_HORSE_AMBIENT,
            1.0f, 0.8f
        )

        world.playSound(
            location,
            org.bukkit.Sound.BLOCK_ENCHANTMENT_TABLE_USE,
            0.7f, 1.2f
        )


    }
}