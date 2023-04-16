package net.diamonddev.ddvorigins.util;

import net.diamonddev.libgenetics.common.api.v1.config.chromosome.ChromosomeConfigFile;


public class DDVOriginsConfig {
    public static ServerCfg SERVER = new ServerCfg();
    public static ClientCfg CLIENT = new ClientCfg();

    public static class ServerCfg implements ChromosomeConfigFile {
        @Override
        public String getFilePathFromConfigDirectory() {
            return ".diamonddev/ddvorigins/server.json";
        }

        public Mod modConfig = new Mod();
        public OriginCfg originCfg = new OriginCfg();

        public static class Mod {
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
    }
}
