package hu.kamillplayz.shorts.videos

import hu.kamillplayz.shorts.Shorts
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.BlockFace
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.min
import kotlin.random.Random

class SugarCaneListener {

    init {
        startGrowthTask()
    }

    private fun startGrowthTask() {
        val tickingWorld: World = Bukkit.getWorld("world")!!

        object : BukkitRunnable() {
            override fun run() {
                for (player in tickingWorld.players) {
                    for (x in -10..10) {
                        for (z in -10..10) {
                            for (y in -10..10) {
                                val block = player.location.clone().add(x.toDouble(), y.toDouble(), z.toDouble()).block
                                if (block.type == Material.SUGAR_CANE) {
                                    checkSugarCaneGrowth(block)
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(Shorts.getInstance(), 20L, 20L)
    }

    private fun checkSugarCaneGrowth(block: org.bukkit.block.Block) {
        val baseBlock = findBaseBlock(block)
        if (baseBlock.getRelative(BlockFace.DOWN).type != Material.SAND) return

        val randomTickSpeed = baseBlock.world.getGameRuleValue(GameRule.RANDOM_TICK_SPEED)!!

        if (Random.nextDouble() < min(0.2 + randomTickSpeed / 2000, (0.01 / 3 * randomTickSpeed))) {
            val topBlock = findTopBlock(block)
            val above = topBlock.getRelative(BlockFace.UP)

            val height = getSugarCaneHeight(topBlock)
            val hasWater = hasWaterNearby(baseBlock)

            if (height < 4 && above.type == Material.AIR && hasWater) {
                Bukkit.getScheduler().runTaskLater(Shorts.getInstance(), Runnable {
                    above.type = Material.SUGAR_CANE
                }, 1L)
            }
        }
    }

    private fun findBaseBlock(block: org.bukkit.block.Block): org.bukkit.block.Block {
        var current = block
        while (current.getRelative(BlockFace.DOWN).type == Material.SUGAR_CANE) {
            current = current.getRelative(BlockFace.DOWN)
        }
        return current
    }

    private fun findTopBlock(block: org.bukkit.block.Block): org.bukkit.block.Block {
        var current = block
        while (current.getRelative(BlockFace.UP).type == Material.SUGAR_CANE) {
            current = current.getRelative(BlockFace.UP)
        }
        return current
    }

    private fun getSugarCaneHeight(topBlock: org.bukkit.block.Block): Int {
        var height = 1
        var current = topBlock
        while (current.getRelative(BlockFace.DOWN).type == Material.SUGAR_CANE) {
            current = current.getRelative(BlockFace.DOWN)
            height++
        }
        return height
    }

    private fun hasWaterNearby(baseBlock: org.bukkit.block.Block): Boolean {
        val blockBelow = baseBlock.getRelative(BlockFace.DOWN)
        val directions = arrayOf(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST)

        for (direction in directions) {
            if (blockBelow.getRelative(direction).type == Material.WATER) {
                return true
            }
        }
        return false
    }
}