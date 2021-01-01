package fi.johvu.motimusic;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.EntitySongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Listeners implements Listener {

	private final MotiMusic main;

	public Listeners(MotiMusic main) {
		this.main = main;
	}

	@EventHandler
	public void instrumentInventoryClick(InventoryClickEvent e){
		if(main.getConfig().getStringList("instruments").contains(e.getView().getTitle())){
			e.setCancelled(true);
			e.getWhoClicked().closeInventory();
			e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes("&".charAt(0), main.getConfig().getString("music-start").replace("&", "§")));
			Song song = NBSDecoder.parse(new File(main.getDataFolder(), "/songs/" + e.getView().getTitle() +"/" + e.getCurrentItem().getItemMeta().getDisplayName() + ".nbs"));
			EntitySongPlayer esp = new EntitySongPlayer(song);
			esp.setEntity(e.getWhoClicked());
			esp.setDistance(main.getConfig().getInt("distance"));
			for (Player p : Bukkit.getOnlinePlayers()) {
				esp.addPlayer(p);
			}
			esp.setPlaying(true);
			new BukkitRunnable() {
				@Override
				public void run() {
					if(!Customitemhandler.getitemlore(e.getWhoClicked().getInventory().getItemInMainHand()).equals(ChatColor.BLACK + e.getView().getTitle())){
						this.cancel();
						esp.destroy();
						e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes("&".charAt(0), main.getConfig().getString("music-noitem").replace("&", "§")));
						return;
					}
					if(esp.isPlaying()){
						if(main.getConfig().getBoolean("particles")){
							e.getWhoClicked().getWorld().spawnParticle(Particle.NOTE, e.getWhoClicked().getLocation().add(0.0D, 2.0D, 0.0D), 1, 0.3D, 0.0D, 0.3D);
						}
					}
				}
			}.runTaskTimer(main, 0, 10L);
		}
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e){
		Player p = e.getPlayer();

		if(p.hasPermission("music.start")) {
			if (Customitemhandler.getitemlore(p.getInventory().getItemInOffHand()).equals(ChatColor.BLACK + "item:soundbook")) {
				if (main.getConfig().getStringList("instruments").contains(Customitemhandler.getitemlore(e.getItem()).replace("§0", ""))) {
					main.openMusicInv(Customitemhandler.getitemlore(e.getItem()).replace("§0", ""), p);
				}
			}
		}

		}


}
