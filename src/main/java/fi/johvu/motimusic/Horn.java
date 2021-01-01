package fi.johvu.motimusic;

import com.xxmicloxx.NoteBlockAPI.NoteBlockAPI;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.PositionSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class Horn implements CommandExecutor {

	private final MotiMusic main;


	public Horn(MotiMusic main) {
		this.main = main;
	}


	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;

		if (args.length == 1) {
			if (args[0] == null) {
				return false;
			}
			if (args[0].equals("menu")) {
				main.openMusicInv("horn", player);
			}

			if (args[0].equals("jones")) {
				com.xxmicloxx.NoteBlockAPI.model.Song song = NBSDecoder.parse(new File(main.getDataFolder(), "/songs/horn/4.nbs"));
				player.sendMessage("Soitetaan");
				PositionSongPlayer psp = new PositionSongPlayer(song);
				psp.setTargetLocation(player.getLocation());
				psp.setDistance(16); // Default: 16
				psp.addPlayer(player);
				psp.setPlaying(true);
				do {
					psp.setTargetLocation(player.getLocation());

				} while (psp.isPlaying());
			}
			if (args[0].equals("stop")) {
				NoteBlockAPI.stopPlaying(player.getUniqueId());
			}

			return false;

		}
		return false;
	}
}
