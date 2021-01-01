package fi.johvu.motimusic;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public final class MotiMusic extends JavaPlugin {

    public ConcurrentHashMap<Player, Song> playingSongs = new ConcurrentHashMap<>();

    HashMap<String, Float> notes;

    public void onEnable() {
    	loadConfig();
        this.notes = new HashMap<>();
        this.notes.put("C", Float.valueOf(0.5F));
        this.notes.put("C#", Float.valueOf(0.53F));
        this.notes.put("D", Float.valueOf(0.56F));
        this.notes.put("D#", Float.valueOf(0.6F));
        this.notes.put("E", Float.valueOf(0.63F));
        this.notes.put("F", Float.valueOf(0.67F));
        this.notes.put("F#", Float.valueOf(0.7F));
        this.notes.put("G", Float.valueOf(0.76F));
        this.notes.put("G#", Float.valueOf(0.8F));
        this.notes.put("A", Float.valueOf(0.84F));
        this.notes.put("A#", Float.valueOf(0.9F));
        this.notes.put("H", Float.valueOf(0.94F));
        this.notes.put("C^", Float.valueOf(1.0F));
        this.notes.put("C^#", Float.valueOf(1.06F));
        this.notes.put("D^", Float.valueOf(1.12F));
        this.notes.put("D^#", Float.valueOf(1.18F));
        this.notes.put("E^", Float.valueOf(1.26F));
        this.notes.put("F^", Float.valueOf(1.34F));
        this.notes.put("F^#", Float.valueOf(1.42F));
        this.notes.put("G^", Float.valueOf(1.5F));
        this.notes.put("G^#", Float.valueOf(1.6F));
        this.notes.put("A^", Float.valueOf(1.68F));
        this.notes.put("A^#", Float.valueOf(1.78F));
        this.notes.put("H^", Float.valueOf(1.88F));
        this.notes.put("C^^", Float.valueOf(2.0F));
        getCommand("violin").setExecutor(new Violin(this));
        (new BukkitRunnable() {
            public void run() {
                for (Player player : MotiMusic.this.playingSongs.keySet()) {
                    if (((Song)MotiMusic.this.playingSongs.get(player)).isEnded()) {
                        MotiMusic.this.playingSongs.remove(player);
                        continue;
                    }
                    ((Song)MotiMusic.this.playingSongs.get(player)).tick();
                }
            }
        }).runTaskTimer((Plugin)this, 1L, 1L);
    }

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

    public void onDisable() {}
}

