package hu.kamillplayz.shorts.videos

import org.bukkit.Material
import org.bukkit.block.data.Levelled
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class CauldronWaterListener : Listener {

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        if (event.block.type != Material.WATER_CAULDRON) return

        if (event.block.blockData !is Levelled) return

        val cauldron = event.block.blockData as Levelled
        if (cauldron.level != 3) return

        event.block.type = Material.WATER
    }
}