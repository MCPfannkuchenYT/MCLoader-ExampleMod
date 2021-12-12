package de.pfannekuchen.examplemod;

import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment.Option;
import org.spongepowered.asm.mixin.MixinEnvironment.Side;
import org.spongepowered.asm.mixin.transformer.MixinTransformerAccessor;

import net.minecraft.client.main.Main;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;

/**
 * Entry Point of the MCLoader Plugin. Since MCLoader is only a loader you have to prepare stuff like mixin and ASM yourself.
 * @author Pancake
 */
public class EntryPoint {
	
	/**
	 * Main Class that loads Minecraft after hijacking some classes using Mixin and ASM.
	 * @param args Arguments Minecraft is being launched with
	 */
	public static void main(String[] args) {
		// Initialize Legacy Launch Wrapper even when using the newer Launcher
		Launch.classLoader = new LaunchClassLoader(((URLClassLoader) EntryPoint.class.getClassLoader()).getURLs());
		try {
			ReflectionHelper.getDeclaredField("java.lang.ClassLoader", "parent").set(Launch.classLoader, EntryPoint.class.getClassLoader());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		Launch.blackboard = new HashMap<String,Object>();
		Thread.currentThread().setContextClassLoader(Launch.classLoader);
		Launch.blackboard.put("Tweaks", new ArrayList<ITweaker>(1));
		Launch.blackboard.put("ArgumentList", new ArrayList<String>());
		Launch.blackboard.put("TweakClasses", new ArrayList<String>());
		// Load Mixin
		MixinBootstrap.init();
		MixinEnvironment.getDefaultEnvironment().setSide(Side.CLIENT);
		MixinEnvironment.getDefaultEnvironment().setOption(Option.DISABLE_REFMAP, true);
		// Configure Mixin and Transformer
		/*instrumentation.addTransformer(*/new MixinTransformerAccessor("mcloader.mixin.json")/*, true)*/;
		
		System.out.println("ExampleMod was successfully loaded, starting minecraft now!");
		Main.main(args); // Load Minecraft
	}
	
}
