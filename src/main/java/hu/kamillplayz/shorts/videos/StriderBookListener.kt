package hu.kamillplayz.shorts.videos

import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack

class StriderBookListener : Listener {

    @EventHandler
    fun onKill(event: EntityDeathEvent) {
        if (event.entity.killer !is Player) return

        val entity = event.entity
        if (entity.type != EntityType.STRIDER) return

        val book = ItemStack(Material.ENCHANTED_BOOK)

        book.editMeta({
            it.lore = listOf("§7Lávajáró I")
        })

        event.drops.add(book)

        val location = entity.location.toCenterLocation()

        for (i in 0..20) {
            val xOffset = Math.random() * 0.5 - 0.25
            val yOffset = Math.random() * 0.5 - 0.25
            val zOffset = Math.random() * 0.5 - 0.25

            location.world.spawnParticle(Particle.WITCH, location, 10, xOffset, yOffset, zOffset)
        }
    }
}