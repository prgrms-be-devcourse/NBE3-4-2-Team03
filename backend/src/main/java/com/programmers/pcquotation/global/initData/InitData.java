package com.programmers.pcquotation.global.initData;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.pcquotation.domain.admin.entitiy.Admin;
import com.programmers.pcquotation.domain.admin.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class InitData {
	private final AdminService adminService;
	private final PasswordEncoder passwordEncoder;
	@Autowired
	@Lazy
	private InitData self;

	@Bean
	public ApplicationRunner baseInitDataApplicationRunner() {
		return args -> {
			self.work1();
		};
	}
	public void work1() {
		Admin admin = Admin
			.builder()
			.username("admin")
			.password(passwordEncoder.encode("password"))
			.build();
		admin.setApiKey(UUID.randomUUID().toString());
		adminService.create(admin);
	}
}
