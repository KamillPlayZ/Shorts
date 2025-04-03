package hu.kamillplayz.shorts

import hu.kamillplayz.shorts.data.ConfigJson
import hu.kamillplayz.shorts.utils.JsonLoader
import hu.kamillplayz.shorts.videos.CauldronWaterListener
import hu.kamillplayz.shorts.videos.DiamondStripListener
import hu.kamillplayz.shorts.videos.StriderBookListener
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
        instance = this
    }

    override fun onEnable() {
        config = JsonLoader.loadOrDefault(dataFolder, "config.json", ConfigJson::class.java)

        if (config.isDiamondStrip) Bukkit.getPluginManager().registerEvents(DiamondStripListener(), this)
        if (config.isStriderBook) Bukkit.getPluginManager().registerEvents(StriderBookListener(), this)
        if (config.isCauldronWater) Bukkit.getPluginManager().registerEvents(CauldronWaterListener(), this)
    }

    override fun onDisable() {
    }

}