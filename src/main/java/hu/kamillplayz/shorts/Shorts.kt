package hu.kamillplayz.shorts

import hu.kamillplayz.shorts.data.ConfigJson
import hu.kamillplayz.shorts.utils.JsonLoader
import hu.kamillplayz.shorts.videos.*
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
        if (config.isRedstoneDye) Bukkit.getPluginManager().registerEvents(RedstoneDyeListener(), this)
        if (config.isTreeGrow) Bukkit.getPluginManager().registerEvents(TreeGrowListener(), this)
        if (config.isDiamondEat) Bukkit.getPluginManager().registerEvents(DiamondEatListener(), this)
        if (config.isBedrockGrow) Bukkit.getPluginManager().registerEvents(BedrockGrowListener(), this)
        if (config.isMobEgg) Bukkit.getPluginManager().registerEvents(MobEggListener(), this)
        if (config.isRename) Bukkit.getPluginManager().registerEvents(RenameListener(), this)
        if (config.isGymAnimals) Bukkit.getPluginManager().registerEvents(GymAnimalsListener(), this)
        if (config.isAutoSmelt) Bukkit.getPluginManager().registerEvents(AutoSmeltListener(), this)
        if (config.isCopperToDiamond) Bukkit.getPluginManager().registerEvents(CopperToDiamond(), this)
        if (config.isCrystalSkeleton) Bukkit.getPluginManager().registerEvents(CrystalSkeletonListener(), this)
        if (config.isBedrockBurn) Bukkit.getPluginManager().registerEvents(BedrockBurnListener(), this)
    }

    override fun onDisable() {
    }

}