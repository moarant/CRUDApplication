package io.zipcoder.crudapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=CRUDApplication.class)
@SpringBootTest
public class CRUDApplicationTests {

	@Autowired
	PersonController personController;

	@Test
	public void contextLoads() {
		assertThat(personController).isNotNull();
	}


}
