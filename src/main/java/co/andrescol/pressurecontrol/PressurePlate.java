/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.andrescol.pressurecontrol;

import java.util.Objects;

import org.bukkit.block.Block;

/**
 *
 * @author Andres Morales
 */
public class PressurePlate {

	protected int x;
	protected int y;
	protected int z;
	protected String world;

	public PressurePlate() {
	}

	public PressurePlate(int x0, int y0, int z0, String w) {
		x = x0;
		y = y0;
		z = z0;
		world = w;
	}

	@Override
	public String toString() {
		return x + " " + y + " " + z + " " + world;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof PressurePlate) {
			PressurePlate pc = (PressurePlate) o;
			if (world.equals(pc.world) && x == pc.x && y == pc.y && z == pc.z)
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 23 * hash + this.x;
		hash = 23 * hash + this.y;
		hash = 23 * hash + this.z;
		hash = 23 * hash + Objects.hashCode(this.world);
		return hash;
	}

	public static boolean isPlate(Block bloque) {
		switch (bloque.getType()) {
		case ACACIA_PRESSURE_PLATE:
		case BIRCH_PRESSURE_PLATE:
		case DARK_OAK_PRESSURE_PLATE:
		case HEAVY_WEIGHTED_PRESSURE_PLATE:
		case JUNGLE_PRESSURE_PLATE:
		case LIGHT_WEIGHTED_PRESSURE_PLATE:
		case OAK_PRESSURE_PLATE:
		case SPRUCE_PRESSURE_PLATE:
		case STONE_PRESSURE_PLATE:
			return true;
		default:
			return false;

		}
	}
}
