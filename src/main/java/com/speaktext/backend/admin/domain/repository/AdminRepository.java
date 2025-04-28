package com.speaktext.backend.admin.domain.repository;

import com.speaktext.backend.admin.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByIdentifier(String identifier);

}
