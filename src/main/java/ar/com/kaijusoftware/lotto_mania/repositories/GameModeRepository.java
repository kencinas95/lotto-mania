package ar.com.kaijusoftware.lotto_mania.repositories;

import ar.com.kaijusoftware.lotto_mania.models.GameMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameModeRepository extends JpaRepository<GameMode, Long> {
}
