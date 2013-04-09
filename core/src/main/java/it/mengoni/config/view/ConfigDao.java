package it.mengoni.config.view;

public interface ConfigDao {
	
	public ViewConfig readConfig();

	public void writeConfig(ViewConfig viewConfig);
	
}
