 package fi.johvu.motimusic;

 import com.xxmicloxx.NoteBlockAPI.NoteBlockAPI;
 import com.xxmicloxx.NoteBlockAPI.model.Song;
 import com.xxmicloxx.NoteBlockAPI.songplayer.NoteBlockSongPlayer;
 import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
 import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
 import org.bukkit.block.data.type.NoteBlock;
 import org.bukkit.command.Command;
 import org.bukkit.command.CommandExecutor;
 import org.bukkit.command.CommandSender;
 import org.bukkit.entity.Player;

 import java.io.File;

 public class Violin implements CommandExecutor {
    private final MotiMusic main;

    private String song = "F# 0 0 0 A 0 0 F# 0 F# 0 G# 0 F# 0 E 0 F# 0 0 0 C# 0 0 F# 0 F# 0 D 0 C# 0 C 0 A 0 F# 0 C# 0 F# 0 E 0 E 0 C# 0 G# 0 F# 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 F# 0 0 0 A 0 0 F# 0 F# B 0 F# 0 E 0 D# 0 C 0 A 0 F# 0 C# 0 A 0  ";

    public Violin(MotiMusic main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;

    	if(args.length == 1){
    		if(args[0] == null){
    			return false;
			}
    		if(args[0].equals("alone")){
				Song song = NBSDecoder.parse(new File(main.getDataFolder(), "2.nbs"));
				RadioSongPlayer rsp = new RadioSongPlayer(song);;
				player.sendMessage("Soitetaan");
				rsp.addPlayer(player);
				rsp.setPlaying(true);
			}
			if(args[0].equals("panther")){
				Song song = NBSDecoder.parse(new File(main.getDataFolder(), "3.nbs"));
				RadioSongPlayer rsp = new RadioSongPlayer(song);;
				player.sendMessage("Soitetaan");
				rsp.addPlayer(player);
				rsp.setPlaying(true);
			}

			return false;
		}
		return false;

	}
}
