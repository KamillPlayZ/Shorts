package hu.kamillplayz.shorts

import hu.kamillplayz.shorts.videos.DiamondStripListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Shorts : JavaPlugin() {

    companion object {
        private lateinit var instance: Shorts

        fun getInstance(): Shorts {
            return instance
        }
    }

    override fun onEnable() {
        instance = this

        Bukkit.getPluginManager().registerEvents(DiamondStripListener(), this)
    }

    override fun onDisable() {

    }


}