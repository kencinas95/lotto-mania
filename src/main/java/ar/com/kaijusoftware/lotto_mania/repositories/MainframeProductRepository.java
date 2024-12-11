package ar.com.kaijusoftware.lotto_mania.repositories;

import ar.com.kaijusoftware.lotto_mania.models.MainframeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainframeProductRepository extends JpaRepository<MainframeProduct, String> {
}
