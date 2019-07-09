package co.andrescol.pressurecontrol;

import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author AndresFernando
 */
public class PressurePlateListener implements Listener {

	private final PressureControl plugin;

	public PressurePlateListener(PressureControl p) {
		plugin = p;
	}

	@EventHandler
	public void onPressurePlate(EntityInteractEvent pe) {

		if (!pe.getEntityType().equals(EntityType.PLAYER) && PressurePlate.isPlate(pe.getBlock())
				&& plugin.isPressureControl(pe.getBlock())) {
			pe.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent ev) {
		Player player = ev.getPlayer();
		if (player.hasPermission("pressurecontrol.put")) {
			Block block = ev.getBlock();
			if (PressurePlate.isPlate(block) && plugin.toPut) {
				PressurePlate pc = new PressurePlate(block.getX(), block.getY(), block.getZ(),
						block.getWorld().getName());
				plugin.plates.add(pc);
				plugin.savePlate(pc);
				player.sendMessage(Tools.ON_PLATE_PUT);
			}
			plugin.toPut = false;
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent ev) {
		Player player = ev.getPlayer();
		Block block = ev.getBlock();
		if (plugin.isPressureControl(ev.getBlock())) {
			if (player.hasPermission("pressurecontrol.break")) {
				PressurePlate pc = new PressurePlate(block.getX(), block.getY(), block.getZ(),
						block.getWorld().getName());
				plugin.plates.remove(pc);
				plugin.savePlates();
				player.sendMessage(Tools.ON_PLATE_KICK);
			} else
				ev.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent ev) {
		Player player = ev.getPlayer();
		Block block = ev.getClickedBlock();
		if (plugin.isPressureControl(block) && player.hasPermission("pressurecontrol.list")) {
			PressurePlate pc = new PressurePlate(block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
			player.sendMessage(Tools.infoOnClick(pc));
		}
	}
}
