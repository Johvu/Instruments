package fi.johvu.motimusic;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class MotiMusic extends JavaPlugin {

    public void onEnable() {
		loadConfig();

		File tmpDir = new File(getDataFolder() + "/songs");
		boolean exists = tmpDir.exists();

		if(!exists){
			File directory = new File(getDataFolder() + "/songs");
			directory.mkdir();
		}

		File exampledir = new File(getDataFolder() + "/songs/example");
		boolean existsexample = exampledir.exists();

		if(!existsexample){
			File directory = new File(getDataFolder() + "/songs/example");
			directory.mkdir();
		}

		getServer().getPluginManager().registerEvents(new Listeners(this), this);
        getCommand("music").setExecutor(new Music(this));
    }


	public void openMusicInv(String instrument, Player p){
		Inventory instrumentinv = Bukkit.createInventory(null, 45, instrument);
		File[] files = new File(this.getDataFolder(), "/songs/" + instrument).listFiles();
		int slot = 0;
		if (files != null) {
			for(File file : files) {
				for (int i = 0; i < 1; ) {
					ItemStack is = new ItemStack(Material.PAPER, 1);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName(ChatColor.RESET + file.getName().replace(".nbs", ""));
					is.setItemMeta(im);
					instrumentinv.setItem(slot, is);
					i++;
					slot++;
				}
			}
		}
		p.openInventory(instrumentinv);
	}

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

    public void onDisable() {}
}


