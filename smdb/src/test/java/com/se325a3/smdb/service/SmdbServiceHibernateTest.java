package com.se325a3.smdb.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:dataSource-context.xml", "classpath:dispatcher-servlet.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("hibernate")
public class SmdbServiceHibernateTest extends AbstractSmdbServiceTest {

}
