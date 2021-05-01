## MCLoader-ExampleMod

Hello! If you are a Mod Developer, starting out with MCLoader, this is the right Page for you!

This Mod is set-up for 1.12.2, there currently is no way to change that...
In Theory, the MCLoader will be able to modify all versions from 1.10.0 upwards.

## Quick Start

Because this is using a Java Agent, you do not have access to the Minecraft Source Code, and you will have to do everything via Reflection or ASM. Everything with Reflection will also be obfuscated.

## Example 

For example you want to add a new Keybind.
The Theoretical Code for Minecraft Version 1.0 would be:

```java
aby myKeybind = new aby("Test Keybind", 17); // Keybinding

/* Extend Array length by 1 */
aby[] keybindsArray = Minecraft.a.A.A;
aby[] newKeybindsArray = new aby[keybindsArray.length + 1];
System.arraycopy(keybindsArray, 0, newKeybindsArray, 0, keybindsArray.length);

newKeybindsArray[newKeybindsArray.length - 1] = myKeybind; // Set Last Index of Keybind Array to the new Keybind

Minecraft.a.A.A = newKeybindsArray
```

But actually it is:
```java
try {
	Field minecraftField = Class.forName("net.minecraft.client.Minecraft").getDeclaredField("a");
	minecraftField.setAccessible(true);
	 
	Object keybind = Class.forName("aby").getConstructors()[0].newInstance("Test Keybind", 50);
	
	Field gameOptionsField = Class.forName("net.minecraft.client.Minecraft").getField("A");
	gameOptionsField.setAccessible(true);
	Field keybindsField = Class.forName("ki").getField("A");
	keybindsField.setAccessible(true);
	Object gameOptions = gameOptionsField.get(minecraftField.get(null));
	
	Object[] keybindsArray = (Object[]) keybindsField.get(gameOptions);
	Object[] newKeybindsArray = new Object[keybindsArray.length + 1];
	System.arraycopy(keybindsArray, 0, newKeybindsArray, 0, keybindsArray.length);
	newKeybindsArray[newKeybindsArray.length - 1] = decrease;
	keybindsField.set(gameOptions, newKeybindsArray);
} catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
	e.printStackTrace();
	System.exit(0);
}
```
