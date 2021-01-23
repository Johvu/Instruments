package fi.johvu.instruments;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

	public String prefix = org.bukkit.ChatColor.translateAlternateColorCodes("&".charAt(0), getConfig().getString("prefix").replace("&", "§"));


	public void onEnable() {
		loadConfig();

		File tmpDir = new File(getDataFolder() + "/songs");

		if(!tmpDir.exists()){
			File directory = new File(getDataFolder() + "/songs");
			directory.mkdir();
		}
		CreateFile("example");
		CreateFile("12345");

		getServer().getPluginManager().registerEvents(new Listeners(this), this);
        getCommand("music").setExecutor(new Music(this));
        getCommand("music").setTabCompleter(new TabComplete());
    }

    public void CreateFile(String string){
		File exampledir = new File(getDataFolder() + "/songs/" + string);

		if(!exampledir.exists()){
			File directory = new File(getDataFolder() + "/songs/" + string);
			directory.mkdir();
		}
	}

	public void openMusicInv(String instrument, Player p){
		Inventory instrumentinv = Bukkit.createInventory(null, 45, instrument);
		File[] files = new File(this.getDataFolder(), "/songs/" + instrument).listFiles();
		int slot = 0;
		if (files != null) {
			for(File file : files) {
					ItemStack is = new ItemStack(Material.PAPER, 1);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName(ChatColor.RESET + file.getName().replace(".nbs", ""));
					is.setItemMeta(im);
					instrumentinv.setItem(slot, is);
					slot++;
			}
		}
		p.openInventory(instrumentinv);
	}

	@Override
	public void reloadConfig() {
		super.reloadConfig();
		saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
}
