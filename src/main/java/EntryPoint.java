import java.lang.instrument.Instrumentation;

import de.pfannekuchen.mcloader.Mod;

public class EntryPoint implements Mod {

	@Override
	public void onInitialization(String args, Instrumentation instrumentation) {
		System.out.println("Hello from the Minecraft Console!");
	}
	
}
