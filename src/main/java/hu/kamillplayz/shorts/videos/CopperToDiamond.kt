package hu.kamillplayz.shorts.videos

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockFormEvent

class CopperToDiamond : Listener {

    @EventHandler
    fun onOxidize(event: BlockFormEvent) {
        if (event.newState.type != Material.OXIDIZED_COPPER) return

        event.newState.type = Material.DIAMOND_BLOCK
    }
}