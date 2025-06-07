package hu.kamillplayz.shorts.videos

import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class CrystalSkeletonListener : Listener {

    @EventHandler
    fun onCrystalPlace(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return

        val item = event.item
        if (item?.type != Material.END_CRYSTAL) return
        if (event.clickedBlock == null) return

        val player = event.player
        val direction = player.location.direction

        val location = event.clickedBlock!!.location.clone().add(direction.x * 2, 1.0, direction.z * 2)

        player.world.spawnEntity(location, EntityType.SKELETON)

        player.world.spawnParticle(Particle.CRIT, location, 10, 0.0, 0.0, 0.0, 0.0)
        player.world.playSound(location, Sound.ENTITY_PARROT_IMITATE_SKELETON, 1f, 1f)
    }
}