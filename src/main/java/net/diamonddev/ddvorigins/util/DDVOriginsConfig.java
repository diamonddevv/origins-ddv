package net.diamonddev.ddvorigins.util;

import net.diamonddev.libgenetics.common.api.v1.config.chromosome.ChromosomeConfigFile;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.serializer.ConfigSerializer;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.serializer.JsonConfigSerializer;


public class DDVOriginsConfig {
    public static ServerCfg SERVER = new ServerCfg();
    public static ClientCfg CLIENT = new ClientCfg();

    public static class ServerCfg implements ChromosomeConfigFile {
        @Override
        public String getFilePathFromConfigDirectory() {
            return ".diamonddev/ddvorigins/server.json";
        }

        @Override
        public ConfigSerializer getSerializer() {
            return new JsonConfigSerializer();
        }

        public ModCfg modConfig = new ModCfg();
        public OriginCfg originConfig = new OriginCfg();

        public static class ModCfg {
            public boolean canObtainLayeredOrbs = false;
        }

        public static class OriginCfg {
            public double vaiRelocationLogicDensity = 0.125;
            public double vaiRelocateIntoEntityLeeway = 0;
        }
    }

    public static class ClientCfg implements ChromosomeConfigFile {
        @Override
        public String getFilePathFromConfigDirectory() {
            return ".diamonddev/ddvorigins/client.json";
        }

        @Override
        public ConfigSerializer getSerializer() {
            return new JsonConfigSerializer();
        }

        public GuiCfg guiConfig = new GuiCfg();

        public static class GuiCfg {
            public boolean renderHudIconTextures = true;
            public boolean renderHudIconMessages = true;
        }
    }
}
