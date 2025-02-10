package com.programmers.pcquotation.domain.admin.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.programmers.pcquotation.domain.admin.entitiy.Admin;
import com.programmers.pcquotation.domain.admin.repository.AdminRepository;
import com.programmers.pcquotation.domain.seller.entitiy.Seller;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final AdminRepository adminRepository;
	public Optional<Admin> findAdminByUsername(String username) {
		return adminRepository.findByUsername(username);
	}

}
