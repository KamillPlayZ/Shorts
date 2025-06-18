package hu.kamillplayz.shorts.videos

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class LichenListener : Listener {

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        if (event.block.type != Material.GLOW_LICHEN) return

        event.block.world.dropItem(event.block.location, Material.DIAMOND.asItemStack)
    }
}