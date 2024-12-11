package ar.com.kaijusoftware.lotto_mania.repositories;

import ar.com.kaijusoftware.lotto_mania.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
