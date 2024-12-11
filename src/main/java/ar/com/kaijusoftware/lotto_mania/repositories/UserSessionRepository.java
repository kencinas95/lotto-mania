package ar.com.kaijusoftware.lotto_mania.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.kaijusoftware.lotto_mania.models.Employee;
import ar.com.kaijusoftware.lotto_mania.models.UserSession;

public interface UserSessionRepository extends JpaRepository<UserSession, String> {

    Optional<UserSession> findByUser(Employee user);
}
