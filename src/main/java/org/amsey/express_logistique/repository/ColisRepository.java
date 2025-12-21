package org.amsey.express_logistique.repository;

import org.amsey.express_logistique.entity.Colis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ColisRepository extends JpaRepository<Colis, Long> {
}
