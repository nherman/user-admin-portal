package com.nherman.useradminportal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({PostgresTestConfiguration.class, MongoTestConfiguration.class})
class UserAdminPortalApplicationTests {

	@Test
	void contextLoads() {
	}

}
