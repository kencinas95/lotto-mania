package ar.com.kaijusoftware.lotto_mania.repositories;

import ar.com.kaijusoftware.lotto_mania.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
