package dev.mv.ptk.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class HeadUtils {
    /**EXPENSIVE DONT CALL OFTEN**/
    public static ItemStack getHead(String url, String itemTitle) {
        return getHead(url, itemTitle, 1);
    }

    /**EXPENSIVE DONT CALL OFTEN**/
    public static ItemStack getHead(String url, String itemTitle, int count) {
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();
        URL urlObject;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException exception) {
            throw new RuntimeException("Invalid URL", exception);
        }
        textures.setSkin(urlObject);
        profile.setTextures(textures);
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, count);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwnerProfile(profile);
        head.setItemMeta(meta);
        ItemMeta itemMeta = head.getItemMeta();
        itemMeta.setDisplayName(itemTitle);
        head.setItemMeta(itemMeta);
        return head;
    }
}
