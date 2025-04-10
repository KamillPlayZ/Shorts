package hu.kamillplayz.shorts.videos

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAttemptPickupItemEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class DiamondEatListener : Listener {

    @EventHandler
    fun onPickup(event: PlayerAttemptPickupItemEvent) {
        val itemStack = event.item.itemStack

        if (itemStack.type != Material.DIAMOND) return
        if (itemStack.amount < 1) return

        itemStack.editMeta({
            val foodComponent = it.food

            foodComponent.addEffect(PotionEffect(PotionEffectType.INSTANT_DAMAGE, 1, 20), 1.0f)
            foodComponent.setNutrition(4)
            foodComponent.setSaturation(0.0f)
            foodComponent.setEatSeconds(0.8f)
            foodComponent.setCanAlwaysEat(true)

            it.setFood(foodComponent)
        })
    }
}