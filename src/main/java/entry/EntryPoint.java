package entry;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import org.javatuples.Triplet;

import de.pfannekuchen.examplemod.Keybind;
import de.pfannekuchen.mcloader.Mod;

public class EntryPoint implements Mod {
	
	public Keybind exampleKeybind;
	
	@Override
	public void onInitialization(String args, Instrumentation instrumentation) {
		System.out.println("Hello from the Minecraft Log!");
		try {
			exampleKeybind = new Keybind("Example Keybinding", 0x22, "key.categories.misc");
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | ClassNotFoundException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public LinkedHashMap<Triplet<Class<?>, String, Boolean>, Runnable> getCallbacks(LinkedHashMap<Triplet<Class<?>, String, Boolean>, Runnable> callbacks) throws Exception {
		callbacks.put(Triplet.with(Class.forName("bib"), "t", true), () -> {
			System.out.println("Hello from the Minecraft Tick!");
		});
		return callbacks;
	}
	
}
