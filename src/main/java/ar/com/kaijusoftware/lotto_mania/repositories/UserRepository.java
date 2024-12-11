package ar.com.kaijusoftware.lotto_mania.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.kaijusoftware.lotto_mania.models.Employee;

@Repository
public interface UserRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUsernameAndPassword(String username, String password);

    Optional<Employee> findByUsername(String username);
    
}
