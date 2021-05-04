package de.pfannekuchen.examplemod;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import de.pfannekuchen.mcloader.ReflectionHelper;

public class Keybind {

	public Object self;
	String name;
	int keycode;
	String category;
	
	public Keybind(String name, int keycode, String category) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, ClassNotFoundException, NoSuchFieldException {
		this.name = name;
		this.keycode = keycode;
		this.category = category;
		self = Class.forName("bhy").getConstructors()[0].newInstance(name, keycode, category);
		
		final Object minecraftClient = ReflectionHelper.getStaticInstance(ReflectionHelper.getDeclaredField("bib", "R"));
		Object gameSettings = ReflectionHelper.getField("bib", "t").get(minecraftClient);
		Object[] keybindArray = (Object[]) ReflectionHelper.getField("bid", "as").get(gameSettings);
		
		int N = keybindArray.length;
		keybindArray = Arrays.copyOf(keybindArray, N + 1);
		keybindArray[N] = self;
		ReflectionHelper.getField("bid", "as").set(gameSettings, keybindArray);
	}
	
	public String getName() {
		return name;
	}

	public int getKeycode() {
		return keycode;
	}

	public String getCategory() {
		return category;
	}
	
}
