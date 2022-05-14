package de.pfannekuchen.examplemod.mixin;

import org.lwjgl.LWJGLException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.client.Minecraft;

@Mixin(Minecraft.class)
public class MixinMinecraft {

	@Overwrite
	private void init() throws LWJGLException {
		System.out.println("ASD");
	}
	
}
