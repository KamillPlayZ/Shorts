package hu.kamillplayz.shorts.videos

import hu.kamillplayz.shorts.Shorts
import org.bukkit.*
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import kotlin.math.cos
import kotlin.math.sin

class GymAnimalsListener : Listener {

    @EventHandler
    fun onFeed(event: PlayerInteractAtEntityEvent) {
        val entity = event.rightClicked

        val item = event.player.inventory.getItem(event.hand)
        if (item.type == Material.AIR) return

        val itemType = item.type
        val entityType = entity.type

        if (itemType == Material.SUGAR && entityType == EntityType.PIG) {
            transformMob(entity as LivingEntity, EntityType.HOGLIN)
            decrementItem(event.player, event.hand)
        } else if (itemType == Material.COCOA_BEANS && entityType == EntityType.COW) {
            transformMob(entity as LivingEntity, EntityType.RAVAGER)
            decrementItem(event.player, event.hand)
        }
    }

    private fun decrementItem(player: Player, slot: EquipmentSlot) {
        val item = player.inventory.getItem(slot)
        if (item.amount > 1) {
            item.amount--
            player.inventory.setItem(slot, item)
        } else {
            player.inventory.setItem(slot, ItemStack(Material.AIR))
        }
    }

    private fun playTransformEffect(location: Location, originalMob: Entity? = null, newMob: Entity? = null) {
        val world = location.world

        val particleCount = 150
        val radius = 1.0
        val height = 2.0

        for (i in 0 until particleCount) {
            val ratio = i.toDouble() / particleCount
            val angle = ratio * Math.PI * 8 // 4 full rotations
            val x = radius * cos(angle)
            val y = ratio * height
            val z = radius * sin(angle)

            val particleLoc = location.clone().add(x, y, z)

            world.spawnParticle(
                Particle.WITCH,
                particleLoc,
                1,
                0.0, 0.0, 0.0,
                0.0
            )

            if (i % 3 == 0) {
                world.spawnParticle(
                    Particle.ELECTRIC_SPARK,
                    particleLoc,
                    2,
                    0.1, 0.1, 0.1,
                    0.05
                )
            }
        }

        world.spawnParticle(
            Particle.EXPLOSION,
            location.clone().add(0.0, 1.0, 0.0),
            1,
            0.0, 0.0, 0.0,
            0.0
        )

        world.spawnParticle(
            Particle.LARGE_SMOKE,
            location,
            20,
            0.4, 0.4, 0.4,
            0.05
        )

        world.playSound(location, Sound.ENTITY_ILLUSIONER_PREPARE_MIRROR, 0.5f, 0.8f)
        world.playSound(location, Sound.BLOCK_BEACON_POWER_SELECT, 0.5f, 1.2f)

        Bukkit.getScheduler().runTaskLater(Shorts.getInstance(), Runnable {
            world.playSound(location, Sound.ENTITY_EVOKER_CAST_SPELL, 0.5f, 1.0f)
        }, 10L)
    }

    fun transformMob(original: LivingEntity, newType: EntityType): LivingEntity? {
        if (!newType.isAlive || !newType.isSpawnable) {
            return null
        }

        val location = original.location
        val world = location.world

        playTransformEffect(location, original, null)
        original.remove()

        val newEntity = world.spawnEntity(location, newType)
        if (newEntity is LivingEntity) {
            playTransformEffect(location, null, newEntity)
            return newEntity
        }

        return null
    }
}