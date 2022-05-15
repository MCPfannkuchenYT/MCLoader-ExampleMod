package de.pfannekuchen.examplemod.service;

import java.util.HashMap;
import java.util.Map;

import org.spongepowered.asm.service.IGlobalPropertyService;
import org.spongepowered.asm.service.IPropertyKey;

public class MCLoaderPropertyService implements IGlobalPropertyService {

	class Key implements IPropertyKey {

		private final String key;

		Key(String key) {
			this.key = key;
		}

		@Override
		public String toString() {
			return this.key;
		}
	}
	
	public static Map<String,Object> blackboard = new HashMap<>();

	public MCLoaderPropertyService() {
		MCLoaderMixinClassLoader.instance.hashCode();
	}

	@Override
	public IPropertyKey resolveKey(String name) {
		return new Key(name);
	}

	/**
	 * Get a value from the blackboard and duck-type it to the specified type
	 * 
	 * @param key blackboard key
	 * @return value
	 * @param <T> duck type
	 */
	@Override
	@SuppressWarnings("unchecked")
	public final <T> T getProperty(IPropertyKey key) {
		return (T)blackboard.get(key.toString());
	}

	/**
	 * Put the specified value onto the blackboard
	 * 
	 * @param key blackboard key
	 * @param value new value
	 */
	@Override
	public final void setProperty(IPropertyKey key, Object value) {
		blackboard.put(key.toString(), value);
	}

	/**
	 * Get the value from the blackboard but return <tt>defaultValue</tt> if the
	 * specified key is not set.
	 * 
	 * @param key blackboard key
	 * @param defaultValue value to return if the key is not set or is null
	 * @return value from blackboard or default value
	 * @param <T> duck type
	 */
	@Override
	@SuppressWarnings("unchecked")
	public final <T> T getProperty(IPropertyKey key, T defaultValue) {
		Object value = blackboard.get(key.toString());
		return value != null ? (T)value : defaultValue;
	}

	/**
	 * Get a string from the blackboard, returns default value if not set or
	 * null.
	 * 
	 * @param key blackboard key
	 * @param defaultValue default value to return if the specified key is not
	 *      set or is null
	 * @return value from blackboard or default
	 */
	@Override
	public final String getPropertyString(IPropertyKey key, String defaultValue) {
		Object value = blackboard.get(key.toString());
		return value != null ? value.toString() : defaultValue;
	}

}