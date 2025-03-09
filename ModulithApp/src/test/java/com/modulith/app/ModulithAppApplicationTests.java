package com.modulith.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class ModulithAppApplicationTests {

	ApplicationModules modules = ApplicationModules.of(ModulithAppApplication.class);

	@Test
	void verifiedModularity() {
		System.out.println(modules.toString());	}

}


