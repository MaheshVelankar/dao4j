package org.example.petstore.dao.test;
import org.example.petstore.dao.DaoFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public abstract class AbstractBaseT {
    private static ClassPathXmlApplicationContext applicationContext;
    private static DaoFactory daoFactory;
    @SuppressWarnings("unchecked")
    protected static <T> T getBean(String id){
        return (T)getApplicationContext().getBean(id);
    }
    private static AbstractApplicationContext getApplicationContext() {
    	if (applicationContext==null)
    		applicationContext = new ClassPathXmlApplicationContext("org.example.petstore.dao-applicationContext.xml");
    	return applicationContext;
    }
    public static DaoFactory getDaoFactory() {
        if (daoFactory==null)
            daoFactory = getBean("daoFactory");
        return daoFactory;
    }
}
