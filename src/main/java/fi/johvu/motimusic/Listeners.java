package fi.johvu.motimusic;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
	public void invclick(InventoryClickEvent e){
		if(main.instruments.contains(e.getView().getTitle().toLowerCase())){
			e.setCancelled(true);
			e.getWhoClicked().sendMessage("Soitetaan");
			Song song = NBSDecoder.parse(new File(main.getDataFolder(), "/songs/" + e.getView().getTitle() +"/" + e.getCurrentItem().getItemMeta().getDisplayName() + ".nbs"));
			RadioSongPlayer rsp = new RadioSongPlayer(song);;
			rsp.addPlayer((Player) e.getWhoClicked());
			rsp.setPlaying(true);
		}
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e){
		Player p = e.getPlayer();

			if(p.getInventory().getItemInOffHand().getType().equals(Material.BOOK)){
				if (Customitemhandler.getitemlore(p.getInventory().getItemInMainHand()).equals(ChatColor.BLACK + "item:torvi")) {
				main.openMusicInv("horn", p);
			}
		}

		}


}
