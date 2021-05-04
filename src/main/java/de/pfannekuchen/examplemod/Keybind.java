package de.pfannekuchen.examplemod;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import de.pfannekuchen.mcloader.ReflectionHelper;

/**
 * Since you don't have access to Minecraft Classes, you need to recreate them using Reflection and Javassist.
 * Here is such an example with a Keybind.
 * @author Pancake
 */
public class Keybind {
	
	/**
	 * This is where the magic happens. Here, we are creating
	 * a new Keybind using Reflection, and then put it into the Array of all Keybinds in GameSettings.
	 * @author Pancake
	 */
	public Keybind(String name, int keycode, String category) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, ClassNotFoundException, NoSuchFieldException {
		/* Create a new Keybind */
		Object self = Class.forName("bhy").getConstructors()[0].newInstance(name, keycode, category); // Use the first Constructor which takes 3 arguments. These being the Name, Keycode and Category of the Keybind.
		
		/* Obtain the Keybind Array */
		final Object minecraftClient = ReflectionHelper.getStaticInstance(ReflectionHelper.getDeclaredField("bib", "R")); // Get Minecraft from the Private Singleton(R) in the Minecraft(bib) Class.
		Object gameSettings = ReflectionHelper.getField("bib", "t").get(minecraftClient); // Get the GameSettings from Minecraft(bib) which are saved in gameSettings(t)
		Object[] keybindArray = (Object[]) ReflectionHelper.getField("bid", "as").get(gameSettings); // Get the Array with all Keybinds from the GameSettings(bid) which is saved in keybinds(as)
		
		/* Extend the Array and add the Keybind to it */
		int N = keybindArray.length;
		keybindArray = Arrays.copyOf(keybindArray, N + 1); // Copy Array and set length 1 more.
		keybindArray[N] = self; // Set the last index to the Keybind
		
		/* Reinsert the new Keybind Array */
		ReflectionHelper.getField("bid", "as").set(gameSettings, keybindArray); // Put the Copy of the Array to keybinds(as) in GameSettings(bid)
	}
	
}
