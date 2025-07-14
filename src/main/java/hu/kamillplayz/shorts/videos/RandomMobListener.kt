package hu.kamillplayz.shorts.videos

import hu.kamillplayz.shorts.Shorts
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import kotlin.math.min

class RandomMobListener : Listener {

    private val livingEntityTypes = EntityType.entries.filter { entityType ->
        entityType.entityClass?.let { LivingEntity::class.java.isAssignableFrom(it) } == true
    }.toCollection(arrayListOf())

    @EventHandler
    fun onMobHit(event: EntityDamageByEntityEvent) {
        if (event.damager !is Player) return
        if (event.entity !is LivingEntity) return

        val hitEntity = event.entity as LivingEntity

        val currentHealth = hitEntity.health
        val damage = event.finalDamage

        if (currentHealth - damage <= 0) return

        val maxHealth = hitEntity.maxHealth
        val totalDamage = maxHealth - currentHealth

        Bukkit.getScheduler().runTaskLater(Shorts.getInstance(), Runnable {
            hitEntity.remove()

            val spawned = hitEntity.world.spawnEntity(hitEntity.location, livingEntityTypes.random()) as LivingEntity
            spawned.health = min(0.1, spawned.maxHealth - totalDamage)
        }, 2L)
    }
}