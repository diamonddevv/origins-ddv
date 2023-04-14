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

        public static class Mod {
            public boolean canObtainLayeredOrbs = false;
        }
    }

    public static class ClientCfg implements ChromosomeConfigFile {
        @Override
        public String getFilePathFromConfigDirectory() {
            return ".diamonddev/ddvorigins/client.json";
        }

        public GuiElements guiElementConfig = new GuiElements();
        public ShaderEffects shaderEffectConfig = new ShaderEffects();

        public static class ShaderEffects {
            public boolean useTemporalAccelerationShader = true;
            public boolean useTemporalDecelerationShader = true;
        }

        public static class GuiElements {
            public boolean displayIconsOnRight = true;
        }
    }
}
