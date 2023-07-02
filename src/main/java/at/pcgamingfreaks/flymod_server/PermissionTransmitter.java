package main.java.at.pcgamingfreaks.flymod_server;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PermissionTransmitter extends JavaPlugin implements Listener {
    private static final String FLY_MOD_PERMISSIONS_IDENTIFIER = "flymod:permissions";


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, FLY_MOD_PERMISSIONS_IDENTIFIER);

        getLogger().info("fly-mod server finished loading.");
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll((Plugin) this);
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, FLY_MOD_PERMISSIONS_IDENTIFIER);

        getServer().getScheduler().cancelTasks(this);

        getLogger().info("All running tasks have been stopped. Plugin disabled.");
    }


    // Event needed to periodically sends update of getAllowFlight
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        getServer().getScheduler().scheduleSyncDelayedTask(this, () -> sendPermissionsToClient(event.getPlayer()), 100);

    }

    private void sendPermissionsToClient(Player player) {
        getLogger().info("Sending fly-permissions (allowed: " + player.getAllowFlight()+ ") to " + player.getName());
        player.sendPluginMessage(this, FLY_MOD_PERMISSIONS_IDENTIFIER, "true".getBytes());
        getLogger().info("Sent fly-permissions to " + player.getName());
    }
}
