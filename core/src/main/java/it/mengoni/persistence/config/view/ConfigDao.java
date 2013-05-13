package it.mengoni.persistence.config.view;

public interface ConfigDao {
	
	public ViewConfig readConfig();

	public void writeConfig(ViewConfig viewConfig);
	
}
