package de.pfannekuchen.examplemod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;

@Mixin(Minecraft.class)
public class MixinMinecraft {

	@Inject(method = "init", at = @At("HEAD"))
	public void onInit(CallbackInfo ci) {
		System.out.println("Hello, world!");
	}
	
}
