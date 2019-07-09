/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.andrescol.pressurecontrol;

import org.bukkit.ChatColor;

/**
 *
 * @author AndresFernando
 */
public abstract class Tools {
	
	private Tools() {}
    
    protected static final String[] PERMS = {"*","put","list", "pressurecontrol", "reload", "help", "break"};
    protected static final String ERROR_READ_PLATES_FILE = "Error al leer el archivo PressureControl.txt";
    protected static final String MESSAGE_HELP = 
                            ChatColor.RED + "----Help para PressureControl Plugin---- \n"+ 
                            ChatColor.DARK_GREEN + "/prec put:" +ChatColor.YELLOW+ " poner placa de presiÃ³n que no se activa en presencia de mobs.\n"+ 
                            ChatColor.DARK_GREEN+"/prec list:" + ChatColor.YELLOW + " ver las ubicaciones de las placas de presiÃ³n.\n"+
                            ChatColor.DARK_GREEN+"/prec reload:" + ChatColor.YELLOW + " Volver a cargar la configuraciÃ³n del plugin.\n";
    protected static final String NOT_PERMISSION = ChatColor.RED + "[PressureCotrol]: No tienes el permiso de usar este comando.";
    protected static final String MESSAGE_RELOAD = "[PressureControl]: Plugin cargado correctamente.";
    protected static final String MESSAGE_TOPUT = "[PressureControl]: Pon una placa de presión.";
    protected static final String ON_PLATE_PUT = "[PressureControl]: Placa puesta correctamente";
    protected static final String ON_PLATE_KICK = "[PressureControl]: Placa quitada correctamente.";
    
    protected static String infoOnClick(PressurePlate plate){
        return "[PressureControl]: " + ChatColor.DARK_GREEN + "placa: " 
                + ChatColor.YELLOW + plate.x + "/" + plate.y + "/" + plate.z + " mundo: " + plate.world +"\n";
    }
}
