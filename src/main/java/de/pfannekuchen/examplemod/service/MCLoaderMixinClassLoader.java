package de.pfannekuchen.examplemod.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.IMixinTransformer;

public class MCLoaderMixinClassLoader extends ClassLoader {

	static MCLoaderMixinClassLoader instance;
	static IMixinTransformer transformer;
	private HashMap<String, byte[]> classes = new HashMap<>();
	private HashMap<String, URL> resources = new HashMap<>();
	
	public MCLoaderMixinClassLoader(File client, File libs) {
		super(ClassLoader.getSystemClassLoader());
		
		try {
			loadZip(client);
			for (File lib : libs.listFiles()) {
				loadZip(lib);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		instance = this;
	}
	
	private void loadZip(File zip) throws IOException {
		ZipInputStream stream = new ZipInputStream(new FileInputStream(zip));
		ZipEntry e;
		byte[] buffer = new byte[1024];
		int length;
		while ((e = stream.getNextEntry()) != null) {
			if (!e.isDirectory()) {
				if (e.getName().endsWith(".class")) {
					// Read and load class file
					ByteArrayOutputStream o = new ByteArrayOutputStream();
					while ((length = stream.read(buffer)) != -1) {
						o.write(buffer, 0, length);
					}
					o.close();
					byte[] file = o.toByteArray();
					classes.put(e.getName().replace('/', '.').replace(".class", ""), file);
				} else {
					// Add URL to resources map
					resources.put(e.getName(), new URL("jar:file:/" + zip.getAbsolutePath().replace('\\', '/') + "!/" + e.getName().replace('\\', '/')));
				}
			}
		}
		stream.close();
	}
	
	public byte[] readClassBytes(String name) {
		return classes.get(name);
	}
	
	@Override
	public InputStream getResourceAsStream(String name) {
		try {
			URL resource = this.getResource(name);
			return resource == null ? super.getResourceAsStream(name) : resource.openStream();
		} catch (IOException e) {
			return super.getResourceAsStream(name);
		}
	}
	
	@Override
	public URL getResource(String name) {
		if (this.resources.containsKey(name))
			return this.resources.get(name); 
		return super.getResource(name);
	}
	
	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		if (this.resources.containsKey(name))
			return Collections.enumeration(Arrays.asList(this.resources.get(name)));
		return super.getResources(name);
	}
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if (this.classes.containsKey(name)) {
			byte[] clazz = this.classes.remove(name);
			
			if (name.equals("net.minecraft.client.Minecraft")) System.out.println(clazz.length);
			
			clazz = transformer.transformClass(MixinEnvironment.getCurrentEnvironment(), name, clazz);
			
			if (name.equals("net.minecraft.client.Minecraft")) System.out.println(clazz.length);
			
			this.defineClass(name, clazz, 0, clazz.length);
		}
		return super.loadClass(name);
	}
	
	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		return super.findClass(name);
	}
	
}
