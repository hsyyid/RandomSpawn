package io.github.hsyyid.randomspawn;

import com.google.inject.Inject;
import io.github.hsyyid.randomspawn.utils.Utils;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.config.DefaultConfig;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.TeleportHelper;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;


@Plugin(id = "RandomSpawn", name = "RandomSpawn", version = "0.1")
public class Main
{
    public static Game game = null;
    public static ConfigurationNode config = null;
    public static ConfigurationLoader<CommentedConfigurationNode> configurationManager;
    public static TeleportHelper helper = null;

    @Inject
    private Logger logger;

    public Logger getLogger()
    {
        return logger;
    }

    @Inject
    @DefaultConfig(sharedRoot = true)
    private File dConfig;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> confManager;

    @Listener
    public void onServerStart(GameStartedServerEvent event)
    {
        getLogger().info("RandomSpawn loading...");

        game = event.getGame();
        helper = game.getTeleportHelper();

        // Config File
        try
        {
            if (!dConfig.exists())
            {
                dConfig.createNewFile();
                config = confManager.load();
                confManager.save(config);
            }
            configurationManager = confManager;
            config = confManager.load();

        }
        catch (IOException exception)
        {
            getLogger().error("The default configuration could not be loaded or created!");
        }

        getLogger().info("-----------------------------");
        getLogger().info("RandomSpawn was made by HassanS6000!");
        getLogger().info("Please post all errors on the Sponge Thread or on GitHub!");
        getLogger().info("Have fun, and enjoy! :D");
        getLogger().info("-----------------------------");
        getLogger().info("RandomSpawn loaded!");
    }

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event)
    {
        Player player = event.getTargetEntity();
        Location<World> playerLocation = player.getLocation();

        try
        {
            ArrayList<String> users = Utils.getUsers();

            if(!(users.contains(player.getUniqueId().toString())))
            {
                Random rand = new Random();
                int x = rand.nextInt(29999999);
                int y = rand.nextInt(256);
                int z = rand.nextInt(29999999);

                Location<World> randLocation = new Location<World>(playerLocation.getExtent(), x, y, z);
                TeleportHelper teleportHelper = helper;
                Optional<Location<World>> optionalLocation = teleportHelper.getSafeLocation(randLocation);

                if (optionalLocation.isPresent()) {
                    if (optionalLocation.get().getBlock().getType().equals(BlockTypes.WATER) || optionalLocation.get().getBlock().getType().equals(BlockTypes.FLOWING_WATER) || optionalLocation.get().getBlock().getType().equals(BlockTypes.LAVA) || optionalLocation.get().getBlock().getType().equals(BlockTypes.FLOWING_LAVA) || optionalLocation.get().getBlock().getType().equals(BlockTypes.FIRE)) {
                        boolean found = false;
                        while (!found) {
                            x = rand.nextInt(29999999);
                            y = rand.nextInt(256);
                            z = rand.nextInt(29999999);

                            randLocation = new Location<World>(playerLocation.getExtent(), x, y, z);
                            optionalLocation = teleportHelper.getSafeLocation(randLocation);
                            if (optionalLocation.isPresent() && optionalLocation.get().getBlock().getType() != BlockTypes.WATER && optionalLocation.get().getBlock().getType() != BlockTypes.LAVA && optionalLocation.get().getBlock().getType() != BlockTypes.FLOWING_LAVA && optionalLocation.get().getBlock().getType() != BlockTypes.FLOWING_WATER && optionalLocation.get().getBlock().getType() != BlockTypes.FIRE) {
                                found = true;
                            }
                        }
                    }
                    player.setLocation(optionalLocation.get());
                    player.sendMessage(Texts.of(TextColors.GREEN, "[RandomSpawn]: ", TextColors.YELLOW,
                            "Successfully spawned you at a random safe location!"));
                } else {
                    boolean found = false;
                    while (!found) {
                        x = rand.nextInt(29999999);
                        y = rand.nextInt(256);
                        z = rand.nextInt(29999999);

                        randLocation = new Location<World>(playerLocation.getExtent(), x, y, z);
                        optionalLocation = teleportHelper.getSafeLocation(randLocation);
                        if (optionalLocation.isPresent() && optionalLocation.get().getBlock().getType() != BlockTypes.WATER) {
                            found = true;
                        }
                    }
                    player.setLocation(optionalLocation.get());
                    player.sendMessage(Texts.of(TextColors.GREEN, "[RandomSpawn]: ", TextColors.YELLOW,
                            "Successfully spawned you at a random safe location!"));
                }

                Utils.addUser(player.getUniqueId().toString());
            }
        }
        catch(Exception e)
        {
            Random rand = new Random();
            int x = rand.nextInt(29999999);
            int y = rand.nextInt(256);
            int z = rand.nextInt(29999999);

            Location<World> randLocation = new Location<World>(playerLocation.getExtent(), x, y, z);
            TeleportHelper teleportHelper = helper;
            Optional<Location<World>> optionalLocation = teleportHelper.getSafeLocation(randLocation);

            if (optionalLocation.isPresent()) {
                if (optionalLocation.get().getBlock().getType().equals(BlockTypes.WATER) || optionalLocation.get().getBlock().getType().equals(BlockTypes.FLOWING_WATER) || optionalLocation.get().getBlock().getType().equals(BlockTypes.LAVA) || optionalLocation.get().getBlock().getType().equals(BlockTypes.FLOWING_LAVA) || optionalLocation.get().getBlock().getType().equals(BlockTypes.FIRE)) {
                    boolean found = false;
                    while (!found) {
                        x = rand.nextInt(29999999);
                        y = rand.nextInt(256);
                        z = rand.nextInt(29999999);

                        randLocation = new Location<World>(playerLocation.getExtent(), x, y, z);
                        optionalLocation = teleportHelper.getSafeLocation(randLocation);
                        if (optionalLocation.isPresent() && optionalLocation.get().getBlock().getType() != BlockTypes.WATER && optionalLocation.get().getBlock().getType() != BlockTypes.LAVA && optionalLocation.get().getBlock().getType() != BlockTypes.FLOWING_LAVA && optionalLocation.get().getBlock().getType() != BlockTypes.FLOWING_WATER && optionalLocation.get().getBlock().getType() != BlockTypes.FIRE) {
                            found = true;
                        }
                    }
                }
                player.setLocation(optionalLocation.get());
                player.sendMessage(Texts.of(TextColors.GREEN, "[RandomSpawn]: ", TextColors.YELLOW,
                        "Successfully spawned you at a random safe location!"));
            } else {
                boolean found = false;
                while (!found) {
                    x = rand.nextInt(29999999);
                    y = rand.nextInt(256);
                    z = rand.nextInt(29999999);

                    randLocation = new Location<World>(playerLocation.getExtent(), x, y, z);
                    optionalLocation = teleportHelper.getSafeLocation(randLocation);
                    if (optionalLocation.isPresent() && optionalLocation.get().getBlock().getType() != BlockTypes.WATER) {
                        found = true;
                    }
                }
                player.setLocation(optionalLocation.get());
                player.sendMessage(Texts.of(TextColors.GREEN, "[RandomSpawn]: ", TextColors.YELLOW,
                        "Successfully spawned you at a random safe location!"));
            }

            Utils.addUser(player.getUniqueId().toString());
        }
    }

    public static ConfigurationLoader<CommentedConfigurationNode> getConfigManager()
    {
        return configurationManager;
    }
}
