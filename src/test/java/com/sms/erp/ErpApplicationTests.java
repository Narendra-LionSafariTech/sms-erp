package com.sms.erp;

import com.sms.erp.repository.AdminSchoolRepository;
import com.sms.erp.repository.SchoolClassRepository;
import com.sms.erp.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mock;

@SpringBootTest
class ErpApplicationTests {
	@Mock
	private AdminSchoolRepository adminSchoolRepository;

	@Mock
	private SchoolClassRepository schoolClassRepository;

	@Mock
	private AdminRepository staffRepository;

	@Test
	void contextLoads() {
	}

}
