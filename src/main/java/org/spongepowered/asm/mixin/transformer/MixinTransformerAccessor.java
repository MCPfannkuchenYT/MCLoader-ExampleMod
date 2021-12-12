package org.spongepowered.asm.mixin.transformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.List;

import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;

import de.pfannekuchen.examplemod.ReflectionHelper;

public class MixinTransformerAccessor implements ClassFileTransformer {

	private MixinTransformer transformer;
	
	public MixinTransformerAccessor(String config) {
		try {
			transformer = (MixinTransformer) ReflectionHelper.getStaticInstance(ReflectionHelper.getDeclaredField("org.spongepowered.asm.mixin.transformer.Proxy", "transformer"));
			MixinProcessor p = ((MixinProcessor) ReflectionHelper.getDeclaredField(MixinTransformer.class.getName(), "processor").get(transformer));
			@SuppressWarnings("unchecked") List<MixinConfig> configs = (List<MixinConfig>) ReflectionHelper.getDeclaredField("org.spongepowered.asm.mixin.transformer.MixinProcessor", "configs").get(p);
			configs.add(MixinConfig.create(config, MixinEnvironment.getDefaultEnvironment()).get());
			configs.get(0).prepare((Extensions) ReflectionHelper.getDeclaredField(p.getClass().getName(), "extensions").get(p));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		return transformer.transformClassBytes(null, className, classfileBuffer);
	}
	
}