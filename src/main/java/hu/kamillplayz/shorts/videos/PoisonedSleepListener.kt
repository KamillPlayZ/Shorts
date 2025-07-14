package hu.kamillplayz.shorts.videos

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerBedEnterEvent
import org.bukkit.event.player.PlayerBedLeaveEvent
import org.bukkit.potion.PotionEffectType
import java.util.*

class PoisonedSleepListener : Listener {

    private val sleepingPlayers = mutableSetOf<UUID>()

    @EventHandler
    fun onPlayerBedEnter(event: PlayerBedEnterEvent) {
        if (event.bedEnterResult == PlayerBedEnterEvent.BedEnterResult.OK) {
            sleepingPlayers.add(event.player.uniqueId)
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerBedLeave(event: PlayerBedLeaveEvent) {
        val player = event.player
        if (event.player.isDeeplySleeping) return

        if (sleepingPlayers.contains(player.uniqueId)) {
            if (player.hasPotionEffect(PotionEffectType.POISON)) {
                event.isCancelled = true
                return
            }
        }

        sleepingPlayers.remove(player.uniqueId)
    }

    @EventHandler
    fun onPlayerQuit(event: org.bukkit.event.player.PlayerQuitEvent) {
        sleepingPlayers.remove(event.player.uniqueId)
    }
}