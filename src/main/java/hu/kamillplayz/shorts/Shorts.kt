package hu.kamillplayz.shorts

import hu.kamillplayz.shorts.data.ConfigJson
import hu.kamillplayz.shorts.utils.JsonLoader
import hu.kamillplayz.shorts.videos.DiamondStripListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Shorts : JavaPlugin() {

    companion object {
        @JvmStatic
        private lateinit var instance: Shorts

        @JvmStatic
        fun getInstance(): Shorts {
            return instance
        }
    }

    private lateinit var config: ConfigJson

    init {
        instance = this  // This will work with @JvmStatic
    }

    override fun onEnable() {
        config = JsonLoader.loadOrDefault(dataFolder, "config.json", ConfigJson::class.java)

        if (config.isDiamondStrip) Bukkit.getPluginManager().registerEvents(DiamondStripListener(), this)
    }

    override fun onDisable() {

    }
}