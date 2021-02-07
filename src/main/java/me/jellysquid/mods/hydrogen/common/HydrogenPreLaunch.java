package me.jellysquid.mods.hydrogen.common;

import me.jellysquid.mods.hydrogen.client.HydrogenClientPreLaunch;
import me.jellysquid.mods.hydrogen.common.jvm.ClassConstructors;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class HydrogenPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        ClassConstructors.init();

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            HydrogenClientPreLaunch.init();
        }
    }
}
