package io.github.hsyyid.randomspawn.utils;

import io.github.hsyyid.randomspawn.Main;

import java.io.IOException;
import java.util.ArrayList;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;


public class Utils {
    
    public static ArrayList<String> getUsers()
    {
        ConfigurationNode valueNode = Main.config.getNode((Object[]) ("users.users").split("\\."));
        String list = valueNode.getString();

        ArrayList<String> users = new ArrayList<String>();
        boolean finished = false;

        // Add all homes to homeList
        if (finished != true)
        {
            int endIndex = list.indexOf(",");
            if (endIndex != -1)
            {
                String substring = list.substring(0, endIndex);
                users.add(substring);

                // If they Have More than 1
                while (finished != true)
                {
                    int startIndex = endIndex;
                    endIndex = list.indexOf(",", startIndex + 1);
                    if (endIndex != -1)
                    {
                        String substrings = list.substring(startIndex + 1, endIndex);
                        users.add(substrings);
                    }
                    else
                    {
                        finished = true;
                    }
                }
            }
            else
            {
                users.add(list);
                finished = true;
            }
        }

        return users;
    }
    
    public static void addUser(String UUID)
    {
        ConfigurationLoader<CommentedConfigurationNode> configManager = Main.getConfigManager();
        ConfigurationNode valueNode = Main.config.getNode((Object[]) ("users.users").split("\\."));
        
        if (valueNode.getString() != null)
        {
            String items = valueNode.getString();
            if (items.contains(UUID + ","));
            else
            {
                String formattedItem = (UUID + ",");
                valueNode.setValue(items + formattedItem);
            }
        }
        else
        {
            valueNode.setValue(UUID + ",");
        }

        try
        {
            configManager.save(Main.config);
            configManager.load();
        }
        catch (IOException e)
        {
            System.out.println("[RandomSpawn]: Failed to add user!");
        }
    }
}
