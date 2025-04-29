package hu.kamillplayz.shorts.videos

import io.papermc.paper.event.player.PlayerNameEntityEvent
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Ageable
import org.bukkit.entity.Chicken
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class RenameListener : Listener {

    @EventHandler
    fun onRename(event: PlayerNameEntityEvent) {
        val entity = event.entity
        val name = event.name ?: return

        val nameString = LegacyComponentSerializer.legacyAmpersand().serialize(name).replaceFirst("&o", "")

        if (nameString.equals("Baby", true)) {
            if (entity !is Ageable) return
            entity.setBaby()

            entity.world.spawnParticle(Particle.HEART, entity.location, 10, 0.0, 0.0, 0.0, 0.0)
            entity.world.playSound(entity.location, Sound.ENTITY_CHICKEN_EGG, 1f, 1f)
        } else if (nameString.equals("Villám", true)) {
            event.isCancelled = true
            entity.world.strikeLightning(entity.location)
        } else if (nameString.equals("Jockey", true)) {
            val chicken = entity.world.spawnEntity(entity.location, EntityType.CHICKEN) as Chicken
            chicken.setIsChickenJockey(true)
            chicken.addPassenger(entity)

            entity.world.playSound(entity.location, Sound.ENTITY_CHICKEN_EGG, 1f, 1f)
            entity.world.spawnParticle(Particle.CLOUD, entity.location, 10, 0.0, 0.0, 0.0, 0.0)
        }
    }
}