package hu.kamillplayz.shorts.videos

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareAnvilEvent
import kotlin.math.max

class EnchantCombineListener : Listener {

    val validItems: List<String> = listOf("_HELMET", "_CHESTPLATE", "_LEGGINGS", "_BOOTS", "_SWORD", "_AXE", "_PICKAXE", "_SHOVEL", "_HOE", "_BOW", "_CROSSBOW")

    @EventHandler
    fun onAnvilCombine(event: PrepareAnvilEvent) {
        val inventory = event.inventory
        if (inventory.firstItem == null || inventory.secondItem == null) return

        val firstItem = inventory.firstItem
        val secondItem = inventory.secondItem

        val isValidItem = validItems.any { firstItem!!.type.name.contains(it) && secondItem!!.type == firstItem.type }
        if (!isValidItem) return

        val allEnchants = firstItem!!.enchantments.toMutableMap()
        for (enchant in secondItem!!.enchantments.keys) {
            if (allEnchants.containsKey(enchant)) {
                allEnchants[enchant] = max(allEnchants[enchant]!!, secondItem.enchantments[enchant]!!)
            } else {
                allEnchants[enchant] = secondItem.enchantments[enchant]!!
            }
        }

        event.result = firstItem.clone()
        event.view.maximumRepairCost = 100
        event.view.repairCost = 1

        for (enchant in allEnchants.keys) {
            event.result!!.addUnsafeEnchantment(enchant, allEnchants[enchant]!!)
        }
    }
}