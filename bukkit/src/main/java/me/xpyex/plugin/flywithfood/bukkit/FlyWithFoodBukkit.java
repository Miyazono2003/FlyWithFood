package me.xpyex.plugin.flywithfood.bukkit;

import me.xpyex.plugin.flywithfood.bukkit.api.FlyWithFoodAPIBK;
import me.xpyex.plugin.flywithfood.bukkit.implementation.BukkitSender;
import me.xpyex.plugin.flywithfood.common.FlyWithFood;
import me.xpyex.plugin.flywithfood.common.command.FWFCmdExecutor;
import me.xpyex.plugin.flywithfood.common.config.FWFConfig;
import me.xpyex.plugin.flywithfood.common.utils.Util;
import org.bukkit.plugin.java.JavaPlugin;

public class FlyWithFoodBukkit extends JavaPlugin {
    private static FlyWithFoodBukkit INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        new FlyWithFood(new FlyWithFoodAPIBK());
    
        FWFConfig.ROOT = FlyWithFoodBukkit.INSTANCE.getDataFolder();

        FlyWithFood.getInstance().enable();
    
        INSTANCE.getCommand("FlyWithFood").setExecutor((sender, cmd, label, args) -> {
            FWFCmdExecutor.onCmd(new BukkitSender(sender), label, args);
            return true;
        });
    }

    @Override
    public void onDisable() {
        INSTANCE = null;

        FlyWithFood.getInstance().getAPI().stopTasks();
        FlyWithFood.getLogger().info("已取消所有任务");
        FlyWithFood.getLogger().info("已卸载");
    }
    
    public static FlyWithFoodBukkit getInstance() {
        if (Util.checkNull(INSTANCE)) throw new IllegalStateException("插件尚未加载完成");
        
        return INSTANCE;
    }
}
