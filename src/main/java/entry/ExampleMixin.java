 package entry;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "bib")
public class ExampleMixin {

	@Inject(at = @At("HEAD"), method = "Lbib;t()V")
	public void injectTick(CallbackInfo ci) {
		System.out.println("Hello from the Minecraft Tick");
	}
	
}
