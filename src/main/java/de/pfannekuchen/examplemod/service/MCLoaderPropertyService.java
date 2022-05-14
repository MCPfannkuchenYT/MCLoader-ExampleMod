package de.pfannekuchen.examplemod.service;

import java.util.HashMap;

import org.spongepowered.asm.service.IGlobalPropertyService;
import org.spongepowered.asm.service.IPropertyKey;

public class MCLoaderPropertyService implements IGlobalPropertyService {

    public class Key implements IPropertyKey {
        
        private final String key;

        Key(String key) {
            this.key = key;
        }
        
        @Override
        public String toString() {
            return this.key;
        }
    }
	
    HashMap<String, Object> values = new HashMap<>();
    
	@Override
	public IPropertyKey resolveKey(String name) {
		return new Key(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProperty(IPropertyKey key) {
		return (T) values.get(key.toString());
	}

	@Override
	public void setProperty(IPropertyKey key, Object value) {
		values.put(key.toString(), value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProperty(IPropertyKey key, T defaultValue) {
		return (T) values.getOrDefault(key.toString(), defaultValue);
	}

	@Override
	public String getPropertyString(IPropertyKey key, String defaultValue) {
		return values.getOrDefault(key.toString(), defaultValue).toString();
	}
	
}