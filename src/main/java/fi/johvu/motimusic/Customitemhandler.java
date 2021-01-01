package fi.johvu.motimusic;

import org.bukkit.inventory.ItemStack;

public class Customitemhandler {
	public static String getitemlore(ItemStack i) {
		if (i != null && i.getItemMeta() != null) {
			if (i.getItemMeta().hasLore())
				return i.getItemMeta().getLore().get(0);
			return "";
		}
		return "";
	}
}
