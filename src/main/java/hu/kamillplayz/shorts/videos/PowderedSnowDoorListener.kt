package hu.kamillplayz.shorts.videos

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.BlockFace
import org.bukkit.block.data.Bisected
import org.bukkit.block.data.type.Door
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class PowderedSnowDoorListener : Listener {

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        if (event.hand != org.bukkit.inventory.EquipmentSlot.HAND) return

        if (event.clickedBlock?.type != Material.POWDER_SNOW) return
        if (event.player.inventory.itemInMainHand.type != Material.OAK_DOOR) return

        event.isCancelled = true

        val clickedBlock = event.clickedBlock!!
        val blockFace = event.blockFace ?: return
        val player = event.player

        val bottomBlock = clickedBlock.getRelative(blockFace)
        val topBlock = bottomBlock.getRelative(BlockFace.UP)

        val facing = player.facing
        val leftBlock = bottomBlock.getRelative(getLeftFace(facing))
        val rightBlock = bottomBlock.getRelative(getRightFace(facing))

        val hinge = when {
            isDoor(leftBlock) && (leftBlock.blockData as Door).facing == facing -> Door.Hinge.RIGHT
            isDoor(rightBlock) && (rightBlock.blockData as Door).facing == facing -> Door.Hinge.LEFT
            else -> Door.Hinge.LEFT
        }

        val bottomData = Material.OAK_DOOR.createBlockData() as Door
        bottomData.half = Bisected.Half.BOTTOM
        bottomData.facing = facing
        bottomData.hinge = hinge

        val topData = Material.OAK_DOOR.createBlockData() as Door
        topData.half = Bisected.Half.TOP
        topData.facing = facing
        topData.hinge = hinge

        bottomBlock.blockData = bottomData
        topBlock.blockData = topData

        player.playSound(clickedBlock.location, Sound.BLOCK_WOOD_PLACE, 1f, 1f)
        player.inventory.itemInMainHand.amount--
    }

    private fun isDoor(block: org.bukkit.block.Block): Boolean {
        return block.blockData is Door
    }

    private fun getLeftFace(facing: BlockFace): BlockFace {
        return when (facing) {
            BlockFace.NORTH -> BlockFace.WEST
            BlockFace.SOUTH -> BlockFace.EAST
            BlockFace.WEST -> BlockFace.SOUTH
            BlockFace.EAST -> BlockFace.NORTH
            else -> BlockFace.WEST
        }
    }

    private fun getRightFace(facing: BlockFace): BlockFace {
        return when (facing) {
            BlockFace.NORTH -> BlockFace.EAST
            BlockFace.SOUTH -> BlockFace.WEST
            BlockFace.WEST -> BlockFace.NORTH
            BlockFace.EAST -> BlockFace.SOUTH
            else -> BlockFace.EAST
        }
    }
}