package hu.kamillplayz.shorts.videos

import hu.kamillplayz.shorts.Shorts
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.EntityType
import org.bukkit.entity.ExperienceOrb
import org.bukkit.entity.Item
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPistonExtendEvent
import org.bukkit.event.block.BlockPistonRetractEvent
import org.bukkit.inventory.FurnaceRecipe
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.max

class AutoSmeltListener : Listener {

    private val plugin = Shorts.getInstance()

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand
        val lore = item.itemMeta.lore ?: return

        if (!lore.contains("§7Égetés I")) return

        var smeltedItem: ItemStack? = null
        var xp = 0

        val iterator = Bukkit.recipeIterator()
        while (iterator.hasNext()) {
            val recipe = iterator.next()
            if (recipe !is FurnaceRecipe) continue

            if (recipe.input.type != event.block.type) continue

            xp = ceil(recipe.experience).toInt()
            smeltedItem = recipe.result

            break
        }

        if (smeltedItem == null) return

        event.isDropItems = false
        event.block.world.dropItem(event.block.location, smeltedItem)
        val orb = event.block.world.spawnEntity(event.block.location, EntityType.EXPERIENCE_ORB) as ExperienceOrb
        orb.experience = xp

        player.playSound(event.block.location, Sound.BLOCK_LAVA_EXTINGUISH, 1f, 1f)
        event.block.world.spawnParticle(Particle.FLAME, event.block.location, 10, 0.0, 0.0, 0.0, 0.0)
    }

    // ===== PISTON ITEM COMBINATION SYSTEM =====

    @EventHandler
    fun onPistonExtend(event: BlockPistonExtendEvent) {
        if (event.isCancelled) return

        val pushedBlocks = event.blocks
        handlePistonMovement(event.block.location, pushedBlocks, event.direction)
    }

    @EventHandler
    fun onPistonRetract(event: BlockPistonRetractEvent) {
        if (event.isCancelled) return

        val pulledBlocks = event.blocks
        handlePistonMovement(event.block.location, pulledBlocks, event.direction)
    }

    private fun handlePistonMovement(pistonLocation: Location, affectedBlocks: List<Block>, direction: BlockFace) {
        // Store initial item positions
        val nearbyItems = mutableListOf<ItemData>()

        // Find all items in the affected area (expanded to catch items that might be pushed)
        val searchCenter = pistonLocation.clone()
        val searchRadius = max(affectedBlocks.size + 2, 5).toDouble() // Dynamic radius based on piston reach

        pistonLocation.world?.getNearbyEntitiesByType(Item::class.java, searchCenter, searchRadius)?.forEach { item ->
            nearbyItems.add(ItemData(item, item.location.clone()))
        }

        // Check after piston movement completes (1 tick delay)
        object : BukkitRunnable() {
            override fun run() {
                checkItemProximity(nearbyItems, pistonLocation, direction)
            }
        }.runTaskLater(plugin, 1L)
    }

    private fun checkItemProximity(itemsToCheck: List<ItemData>, pistonLocation: Location, direction: BlockFace) {
        val currentItems = itemsToCheck
            .filter { it.item.isValid && !it.item.isDead }
            .map { it.item }

        // Check each item against every other item
        for (i in currentItems.indices) {
            val item1 = currentItems[i]
            val loc1 = item1.location

            for (j in i + 1 until currentItems.size) {
                val item2 = currentItems[j]
                val loc2 = item2.location

                val distance = loc1.distance(loc2)

                // Check if items are close (within 1.5 blocks)
                if (distance <= EffectConfig.combineDistance) {
                    // Check if one item is on top of another (Y difference)
                    val yDifference = abs(loc1.y - loc2.y)

                    when {
                        yDifference <= 0.5 -> {
                            // Items are approximately on the same level
                            onItemsMovedNearEachOther(item1, item2)
                        }
                    }
                }
            }
        }
    }

    private fun onItemsMovedNearEachOther(
        item1: Item,
        item2: Item,
    ) {
        // Check if items can be combined
        if (item1.canCombineWith(item2)) {
            createCombineEffect(item1, item2)
            combineItems(item1, item2)
        }
    }

    /**
     * Creates a spectacular combine effect when two compatible items are near each other
     */
    private fun createCombineEffect(item1: Item, item2: Item) {
        val midpoint = item1.location.clone().add(item2.location).multiply(0.5)
        val world = midpoint.world

        // Create a massive particle explosion with multiple layers
        for (i in 0..5) {
            plugin.server.scheduler.runTaskLater(plugin, Runnable {
                // Expanding ring of particles
                val radius = i * 0.5
                for (angle in 0..360 step 15) {
                    val radians = Math.toRadians(angle.toDouble())
                    val x = Math.cos(radians) * radius
                    val z = Math.sin(radians) * radius
                    val particleLoc = midpoint.clone().add(x, i * 0.2, z)

                    world.spawnParticle(
                        Particle.DRAGON_BREATH,
                        particleLoc,
                        5,
                        0.1, 0.1, 0.1,
                        0.02
                    )

                    world.spawnParticle(
                        Particle.END_ROD,
                        particleLoc,
                        2,
                        0.05, 0.05, 0.05,
                        0.01
                    )
                }

                // Lightning effect on the final ring
                if (i == 5) {
                    world.strikeLightningEffect(midpoint)
                    world.playSound(
                        midpoint,
                        Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,
                        SoundCategory.BLOCKS,
                        2.0f * EffectConfig.soundVolume,
                        0.8f
                    )
                }
            }, (i * 3).toLong())
        }

        // Play epic sound sequence
        world.playSound(midpoint, Sound.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.BLOCKS, 1.5f * EffectConfig.soundVolume, 1.2f)

        plugin.server.scheduler.runTaskLater(plugin, Runnable {
            world.playSound(midpoint, Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.BLOCKS, 2.0f * EffectConfig.soundVolume, 1.0f)
        }, 10L)

        return
    }

    /**
     * Combines two compatible items into one
     */
    private fun combineItems(item1: Item, item2: Item) {
        if (!item1.canCombineWith(item2)) return

        val midpoint = item1.location.clone().add(item2.location).multiply(0.5)

        item1.remove()
        item2.remove()

        // Remove original items with a delay for effect
        object : BukkitRunnable() {
            override fun run() {

                val newStack = ItemStack(Material.DIAMOND_PICKAXE)
                newStack.editMeta {
                    it.setDisplayName("§dHot Csákány :3")
                    it.lore = listOf("§7Égetés I")
                }
                val newItem = item1.world.dropItem(midpoint, newStack)

                // Add glowing effect to the new item
                newItem.isGlowing = true

                // Remove glow after 3 seconds
                object : BukkitRunnable() {
                    override fun run() {
                        if (newItem.isValid) {
                            newItem.isGlowing = false
                        }
                    }
                }.runTaskLater(plugin, 60L)

                // Final celebration effect
                newItem.world.spawnParticle(
                    Particle.TOTEM_OF_UNDYING,
                    newItem.location,
                    (10 * EffectConfig.effectIntensity).toInt(),
                    0.2, 0.2, 0.2,
                    0.1
                )
            }
        }.runTaskLater(plugin, 12L) // Slight delay for effect timing
    }
}

// Data class to store item information
data class ItemData(
    val item: Item,
    val originalLocation: Location
)

// Extension functions for item compatibility
fun Item.canCombineWith(other: Item): Boolean {
    return EffectConfig.isCombination(this, other)
}

// Configuration object for customizing effects
object EffectConfig {
    var effectIntensity = 1.0 // Multiplier for particle counts
    var soundVolume = 1.0f
    var combineDistance = 1.5 // Maximum distance for combining

    val itemCombinations = setOf(
        setOf("FURNACE", "IRON_PICKAXE")
    )

    fun isCombination(item1: Item, item2: Item): Boolean {
        val type1 = item1.itemStack.type.name
        val type2 = item2.itemStack.type.name

        return itemCombinations.any { combo ->
            combo.any { it in type1 } && combo.any { it in type2 }
        }
    }
}