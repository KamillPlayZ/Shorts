package hu.kamillplayz.shorts.videos

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.EntityType
import org.bukkit.entity.ExperienceOrb
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent

class DiamondStripListener : Listener {

    @EventHandler
    fun onClick(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        if (event.clickedBlock == null) return

        val block: Block = event.clickedBlock!!
        if (block.type != Material.DEEPSLATE_DIAMOND_ORE) return

        val item = event.item
        if (item?.type != Material.GOLDEN_PICKAXE) return

        block.type = Material.DIAMOND_ORE
    }

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        if (event.block.type != Material.DIAMOND_ORE) return

        for (item in event.block.drops) {
            val drop = item.clone()
            for (i in 0..24) {
                drop.amount = 100
                event.block.world.dropItem(event.block.location, drop)
            }
        }

        event.isDropItems = false

        val location = event.block.location.toCenterLocation()

        for (i in 0..20) {
            val xOffset: Double = Math.random() * 0.5 - 0.25
            val yOffset: Double = Math.random() * 0.5 - 0.25
            val zOffset: Double = Math.random() * 0.5 - 0.25

            val experienceOrb: ExperienceOrb =
                event.block.world.spawnEntity(location.clone().add(xOffset, yOffset, zOffset), EntityType.EXPERIENCE_ORB) as ExperienceOrb

            experienceOrb.experience = 500
        }
    }
}