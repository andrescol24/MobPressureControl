package co.andrescol.pressurecontrol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author AndresMorales
 * @version 1.1
 */
public class PressureControl extends JavaPlugin {

	protected Logger log;
	protected Set<PressurePlate> plates;
	protected File file;
	protected boolean toPut;
	protected PressurePlateListener plateListener;

	@Override
	public void onLoad() {
		file = new File(getDataFolder(), "PressureControl.txt");
		save();
	}

	@Override
	public void onEnable() {
		plates = new HashSet<>();
		log = getLogger();
		toPut = false;
		plateListener = new PressurePlateListener(this);
		PluginManager pluginManager = getServer().getPluginManager();
		PressureControlCommandExecutor commandExecutor = new PressureControlCommandExecutor(this);
		loadPlates();
		getCommand("PressureControl").setExecutor(commandExecutor);
		pluginManager.registerEvents(plateListener, this);
		// Permissions
		for (String p : Tools.PERMS) {
			String perm = "PressureControl.".concat(p);
			if (pluginManager.getPermission(perm) == null)
				pluginManager.addPermission(new Permission(perm, Permission.DEFAULT_PERMISSION));
		}
	}

	@Override
	public void onDisable() {
		EntityInteractEvent.getHandlerList().unregister(plateListener);
		BlockPlaceEvent.getHandlerList().unregister(plateListener);
		BlockBreakEvent.getHandlerList().unregister(plateListener);
		plates.clear();
		plates = null;
		log = null;
		file = null;
	}

	public void reload() {
		onDisable();
		onLoad();
		onEnable();
	}

	public boolean isPressureControl(Block block) {
		PressurePlate pc = new PressurePlate(block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
		return plates.contains(pc);
	}

	public void save() {
		if (!file.exists())
			this.saveResource("PressureControl.txt", false);
	}

	public boolean savePlate(PressurePlate x) {

		try (FileWriter fw = new FileWriter(file, true); PrintWriter pw = new PrintWriter(fw)) {
			pw.println(x.toString());
		} catch (Exception e) {
			getServer().getLogger().info(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean savePlates() {
		PressurePlate[] aux = plates.toArray(new PressurePlate[0]);
		try (FileWriter fw = new FileWriter(file, true); PrintWriter pw = new PrintWriter(fw)) {
			if (!plates.isEmpty()) {
				for (PressurePlate a : aux)
					pw.println(a.toString());
			}
		} catch (Exception e) {
			getServer().getLogger().info(e.getMessage());
			return false;
		}
		return true;
	}

	public String list() {
		StringBuilder mensaje = new StringBuilder();
		mensaje.append(ChatColor.DARK_GREEN + "------PressureControl------\n");
		PressurePlate[] bloques = plates.toArray(new PressurePlate[0]);
		if (bloques.length >= 1) {
			for (PressurePlate a : bloques) {
				mensaje.append(ChatColor.DARK_GREEN);
				mensaje.append("placa en: ");
				mensaje.append(ChatColor.YELLOW);
				mensaje.append(String.format("%d / %d / %d", a.x, a.y, a.z));
			}
		} else {
			mensaje.append(ChatColor.YELLOW + "No hay placas puestas con PressureControl");
		}
		return mensaje.toString();
	}

	public void loadPlates() {
		String line;
		String[] posiciones;
		Block block;
		World world;
		try (BufferedReader br = new BufferedReader(new FileReader(file))){
			while ((line = br.readLine()) != null) {
				posiciones = line.split(" ");
				world = getServer().getWorld(posiciones[3]);
				block = world.getBlockAt(Integer.parseInt(posiciones[0]), Integer.parseInt(posiciones[1]),
						Integer.parseInt(posiciones[2]));
				if (PressurePlate.isPlate(block))
					plates.add(new PressurePlate(block.getX(), block.getY(), block.getZ(), posiciones[3]));
				else {
					log.log(Level.INFO,
							"la placa en: {0}/{1}/{2}/{3} no es una placa de presion. Debe de eliminarla en PressureControl.txt .",
							new Object[] { block.getX(), block.getY(), block.getZ(), block.getWorld().getName() });
				}
			}
		} catch (Exception e) {
			log.log(Level.WARNING, Tools.ERROR_READ_PLATES_FILE + "Causado por:\n{0}", e.getCause().toString());
		}
	}
}
