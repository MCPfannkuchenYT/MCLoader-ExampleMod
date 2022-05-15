package de.pfannekuchen.examplemod;

import java.io.File;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment.Side;
import org.spongepowered.asm.mixin.Mixins;

import de.pfannekuchen.examplemod.service.MCLoaderMixinClassLoader;

/**
 * Entry Point of the MCLoader Plugin. Since MCLoader is only a loader you have to prepare stuff like mixin and ASM yourself.
 * @author Pancake
 */
public class EntryPoint {
	
	/**
	 * Main Class that loads Minecraft after hijacking some classes using Mixin and ASM.
	 * @param args Arguments Minecraft is being launched with
	 * @throws Exception Security Manager
	 */
	public static void main(String[] args) throws Exception {
		final MCLoaderMixinClassLoader classloader = new MCLoaderMixinClassLoader(new File("C:/Users/games/.gradle/mcloader/minecraft_1.12.2/client/client-deobf.jar"), new File("C:/Users/games/.gradle/mcloader/minecraft_1.12.2/libraries"));
		MixinBootstrap.init();
		Mixins.addConfiguration("mcloader.mixin.json");
		MixinEnvironment.getCurrentEnvironment().setSide(Side.CLIENT);
		MixinBootstrap.getPlatform().getLaunchTarget();
		
		System.out.println("ExampleMod was successfully loaded, starting minecraft now!");
		Class.forName("net.minecraft.client.main.Main", true, classloader).getMethod("main", String[].class).invoke(null, new Object[] {args}); // Load Minecraft with custom classloader
	}
	
}