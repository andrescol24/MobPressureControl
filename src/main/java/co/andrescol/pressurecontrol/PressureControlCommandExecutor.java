package co.andrescol.pressurecontrol;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author AndresFernando
 */
public class PressureControlCommandExecutor implements CommandExecutor {
	PressureControl plugin;

	public PressureControlCommandExecutor(PressureControl m) {
		plugin = m;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player;
		if (sender instanceof Player) {
			player = (Player) sender;
			if (command.getName().equalsIgnoreCase("PressureControl")) {
				switch(args[0]) {
				case "help":
					this.subCommandoHelp(player);
					break;
				case "reload":
					this.subCommandoReload(player);
					break;
				case "put":
					this.subCommandoPut(player);
					break;
				case "list":
					this.subCommandoList(player);
					break;
				default:
					return false;
				}
			}
		}
		return true;
	}
	
	private void subCommandoHelp(Player player) {
		if (player.hasPermission("pressurecontrol.pressurecontrol")) {
			player.sendMessage(Tools.MESSAGE_HELP);
		} else {
			player.sendMessage(Tools.NOT_PERMISSION);
		}
	}
	
	private void subCommandoReload(Player player) {
		if (player.hasPermission("pressurecontrol.reload")) {
			plugin.reload();
			player.sendMessage(Tools.MESSAGE_RELOAD);
		} else {
			player.sendMessage(Tools.NOT_PERMISSION);
		}
	}
	
	private void subCommandoPut(Player player) {
		if (player.hasPermission("pressurecontrol.put")) {
			plugin.toPut = true;
			player.sendMessage(Tools.MESSAGE_TOPUT);
		} else {
			player.sendMessage(Tools.NOT_PERMISSION);
		}
	}
	
	private void subCommandoList(Player player) {
		if (player.hasPermission("pressurecontrol.list")) {
			player.sendMessage(plugin.list());
		} else {
			player.sendMessage(Tools.NOT_PERMISSION);
		}
	}
}
