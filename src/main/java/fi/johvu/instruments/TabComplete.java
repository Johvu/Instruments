package fi.johvu.instruments;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {

	List<String> arguments1 = new ArrayList<String>();



	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (arguments1.isEmpty()) {
			arguments1.add("stop");
			arguments1.add("about");
			arguments1.add("reload");
			return arguments1;
		}



		List<String> result1 = new ArrayList<String>();
		if (args.length == 1) {
			for (String a : arguments1) {
				if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
					result1.add(a);
				}
			}
			return result1;
		}
		return null;
	}

}
