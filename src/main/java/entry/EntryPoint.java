package entry;

import java.lang.instrument.Instrumentation;

import de.pfannekuchen.mcloader.Mod;

/**
 * The Entry-Point of the Mod
 * 
 * This Class is forced to be in package entry with this Name!
 * @author Pancake
 */
public class EntryPoint implements Mod {
	
	/**
	 * Create a new Keybind and display a Message in the Console of Minecraft.
	 */
	@Override
	public void onInitialization(String args, Instrumentation instrumentation) {
		System.out.println("Hello from the Minecraft Console!"); // This Message will be printed in the Minecraft Console
	}
	
}
