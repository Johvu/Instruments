package fi.johvu.instruments;

import com.xxmicloxx.NoteBlockAPI.event.SongStoppedEvent;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.EntitySongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import jdk.internal.loader.Loader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.HashSet;
import java.util.UUID;

public class Listeners implements Listener {

	private final Main main;

	HashSet<UUID> songplayer = new HashSet();


	public Listeners(Main main) {
		this.main = main;
	}

	@EventHandler
	public void instrumentEnd(SongStoppedEvent e){
		for (int player = 0; player <= e.getSongPlayer().getPlayerUUIDs().size(); player++){
			if(songplayer.isEmpty()){
				return;
			}
			if(songplayer.contains(player)){
				songplayer.remove(player);
			}
		}
	}

	public Integer getIntFromString(String string){
		if (string.matches("[0-9]+") && string.length() > 2) {
			return Integer.valueOf(string);

		}
		return 0;
	}

	private String color(String message) {
		return message.replaceAll("&", "§");
	}

	@EventHandler
	public void instrumentInventoryClick(InventoryClickEvent e) {
		FileConfiguration config = main.getConfig();
		String title = e.getView().getTitle();
		if (e.getWhoClicked() instanceof Player && config.getStringList("intrument-check-lore").contains(color(title))
			|| config.getIntegerList("custom-model-data").contains(getIntFromString(title))) {
			e.setCancelled(true);

			Player p = (Player) e.getWhoClicked();
			String prefix = main.prefix;

			if (songplayer.contains(p.getUniqueId())) {
				p.sendMessage(prefix + color(config.getString("music-alredy")));
			} else {
				if(e.getCurrentItem() == null || !new File(main.getDataFolder(), "/songs/" + title + "/" + e.getCurrentItem().getItemMeta().getDisplayName() + ".nbs").exists())
					return;
				p.closeInventory();
				p.sendMessage(prefix + color(config.getString("music-start")));
				Song song = NBSDecoder.parse(new File(main.getDataFolder(), "/songs/" + title + "/" + e.getCurrentItem().getItemMeta().getDisplayName() + ".nbs"));
				EntitySongPlayer esp = new EntitySongPlayer(song);
				esp.setEntity(p);
				esp.setDistance(config.getInt("distance"));
				songplayer.add(p.getUniqueId());
				Bukkit.getOnlinePlayers().forEach(oPlayer -> esp.addPlayer(oPlayer));
				esp.setPlaying(true);

				new BukkitRunnable() {
					@Override
					public void run() {
						if (esp.isPlaying()) {
							if (config.getBoolean("particles")) {
								p.getWorld().spawnParticle(Particle.NOTE, p.getLocation().add(0.0D, 2.0D, 0.0D), 1, 0.3D, 0.0D, 0.3D);
							}
							ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
								if ( meta == null || !String.valueOf(meta.getCustomModelData()).equals(title) && (meta.getLore() != null && meta.getLore().get(0).equals(title.replaceAll("§", "&")))) {
									esp.destroy();
									songplayer.remove(p.getUniqueId());
									p.sendMessage(prefix + color(config.getString("music-noitem")));
									cancel();
									return;
								}
								return;
						}
						cancel();
					}
				}.runTaskTimer(main, 0, 10L);
			}
		}
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e){
		Player p = e.getPlayer();
		ItemStack i = e.getItem();

		if(p.hasPermission("music.start")) {
			if (i == null || !i.hasItemMeta() || !i.getItemMeta().hasCustomModelData()) {
				return;
			}
			ItemMeta meta = i.getItemMeta();

			if (main.getConfig().getIntegerList("custom-model-data").contains(meta.getCustomModelData())) {
				main.openMusicInv(String.valueOf(meta.getCustomModelData()), p);
				return;
			}
			
			if(meta.hasLore()){
				if (main.getConfig().getStringList("intrument-check-lore").contains(meta.getLore().get(0).replace("§", "&"))) {
					main.openMusicInv(meta.getLore().get(0).replace("§", "&"), p);
				}
			}
		}
	}


}
