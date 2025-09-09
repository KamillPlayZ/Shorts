package hu.kamillplayz.shorts

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
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
    lateinit var protocolManager: ProtocolManager

    init {
        instance = this
    }

    override fun onEnable() {
        protocolManager = ProtocolLibrary.getProtocolManager()

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
        if (config.isSkeletonHorse) Bukkit.getPluginManager().registerEvents(SkeletonHorse(), this)
        if (config.isLichen) Bukkit.getPluginManager().registerEvents(LichenListener(), this)
        if (config.isPoisonedSleep) Bukkit.getPluginManager().registerEvents(PoisonedSleepListener(), this)
        if (config.isRandomMob) Bukkit.getPluginManager().registerEvents(RandomMobListener(), this)
        if (config.isOneChunk) Bukkit.getPluginManager().registerEvents(OneChunkListener(), this)
        if (config.isEnchantCombine) Bukkit.getPluginManager().registerEvents(EnchantCombineListener(), this)

        if (config.isSugarCane) SugarCaneListener()
        if (config.isMelonGrower) MelonGrower()
    }

    override fun onDisable() {
    }

}