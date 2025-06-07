package hu.kamillplayz.shorts.videos

import hu.kamillplayz.shorts.Shorts
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class BedrockBurnListener : Listener {

    @EventHandler
    fun onBurn(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return

        val block = event.clickedBlock ?: return
        if (block.type != Material.BEDROCK) return

        val item = event.item ?: return
        if (item.type != Material.FLINT_AND_STEEL) return

        Bukkit.getScheduler().runTaskLater(Shorts.getInstance(), Runnable {
            block.type = Material.AIR
        }, 5*20L)
    }
}