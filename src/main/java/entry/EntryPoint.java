package entry;

import java.lang.instrument.Instrumentation;
import java.util.LinkedHashMap;

import org.javatuples.Triplet;

import de.pfannekuchen.examplemod.Keybind;
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
		try {
			new Keybind("Example Keybinding", 0x22, "key.categories.misc");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Print a Message every Tick
	 */
	@Override
	public LinkedHashMap<Triplet<Class<?>, String, Boolean>, Runnable> getCallbacks(LinkedHashMap<Triplet<Class<?>, String, Boolean>, Runnable> callbacks) throws Exception {
		// Create a Callback everytime runTick(t) is being called in Minecraft(bib)
		callbacks.put(Triplet.with(Class.forName("bib"), "t", true), () -> {
			System.out.println("Hello from the Minecraft Tick!"); // Print a Message everytime the Method is being executed.
		});
		return callbacks;
	}
	
}
