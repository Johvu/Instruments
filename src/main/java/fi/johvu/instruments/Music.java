 package fi.johvu.instruments;

 import com.xxmicloxx.NoteBlockAPI.NoteBlockAPI;
 import net.md_5.bungee.api.ChatColor;
 import org.bukkit.command.Command;
 import org.bukkit.command.CommandExecutor;
 import org.bukkit.command.CommandSender;
 import org.bukkit.entity.Player;

 public class Music implements CommandExecutor {
    private final Main main;

    public Music(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if(!(sender instanceof Player)){
    		return false;
		}
		Player player = (Player) sender;
    	if(args.length == 1){
    		if(args[0] == null){
    			return false;
			}
    		if(args[0].equals("about")){
    			if(player.hasPermission("music.about")) {
					player.sendMessage(ChatColor.GREEN + "Instruments");
					player.sendMessage(ChatColor.GREEN + "Authors " + main.getDescription().getAuthors());
					player.sendMessage(ChatColor.GREEN + "Current version " + main.getDescription().getVersion());
					return false;
				}
			}
    		if(args[0].equals("reload")){
				if(player.hasPermission("music.reload")) {
					main.reloadConfig();
					return false;
				}
    		}
			if(args[0].equals("stop")){
				if(sender.hasPermission("music.stop")){
					player.sendMessage(ChatColor.translateAlternateColorCodes("&".charAt(0), main.getConfig().getString("music-stop").replace("&", "§")));
					NoteBlockAPI.stopPlaying(player);
				}
			}
		}
		return false;
	}


}
