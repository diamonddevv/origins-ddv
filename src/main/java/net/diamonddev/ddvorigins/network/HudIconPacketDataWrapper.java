package net.diamonddev.ddvorigins.network;

@FunctionalInterface
public interface HudIconPacketDataWrapper {
    SendHudIcon.Data create();
}
