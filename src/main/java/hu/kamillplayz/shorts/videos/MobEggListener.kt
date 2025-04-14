package hu.kamillplayz.shorts.videos

import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack

val Material.asItemStack: ItemStack
    get() = ItemStack(this)

class MobEggListener : Listener {

    @EventHandler
    fun onMobKill(event: EntityDeathEvent) {
        if (event.entity.killer !is Player) return

        val entityType = event.entity.type
        val killer = event.entity.killer as Player
        val itemType = killer.inventory.itemInMainHand.type

        if (itemType == Material.FEATHER && entityType == EntityType.CHICKEN) {
            event.drops.add(Material.CHICKEN_SPAWN_EGG.asItemStack)
        } else if (itemType == Material.ROTTEN_FLESH && entityType == EntityType.ZOMBIE) {
            event.drops.add(Material.ZOMBIE_SPAWN_EGG.asItemStack)
        } else if (itemType == Material.GOLD_NUGGET && entityType == EntityType.ZOMBIFIED_PIGLIN) {
            event.drops.add(Material.ZOMBIFIED_PIGLIN_SPAWN_EGG.asItemStack)
        } else if (itemType == Material.POTION && entityType == EntityType.ENDER_DRAGON) {
            event.drops.add(Material.ENDER_DRAGON_SPAWN_EGG.asItemStack)
        }
    }
}
