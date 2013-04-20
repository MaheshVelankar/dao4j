package org.example.petstore.persistence.test;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestAccountDao.class, TestBannerdataDao.class,
		TestCategoryDao.class, TestInventoryDao.class, TestItemDao.class,
		TestLineitemDao.class, TestOrdersDao.class, TestOrderstatusDao.class,
		TestProductDao.class, TestProfileDao.class, TestSequenceDao.class,
		TestSignonDao.class, TestSupplierDao.class })
public class AllTests {
}
