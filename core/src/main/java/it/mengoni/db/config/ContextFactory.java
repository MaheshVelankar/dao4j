package it.mengoni.db.config;

//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 
 * @author T800111
 * 
 */
public class ContextFactory {

	// private static ApplicationContext context = null;

	// private static final String configLocation = "/config/context.xml";

	// public static ApplicationContext getContext(){
	// if (context==null){
	// context = new FileSystemXmlApplicationContext(configLocation);
	// }
	// return context;
	// }

	public static Object getBean(String beanName) {
		return null; // getContext().getBean(beanName);
	}

	public static <T> T getBean(String beanName, Class<T> beanClass) {
		return null; // getContext().getBean(beanName, beanClass);
	}

	public static <T> T getBean(Class<T> beanClass) {
		return null; // getContext().getBean(beanClass);
	}

}
