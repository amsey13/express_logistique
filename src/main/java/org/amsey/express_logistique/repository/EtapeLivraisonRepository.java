package org.amsey.express_logistique.repository;

import org.amsey.express_logistique.entity.EtapeLivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtapeLivraisonRepository extends JpaRepository<EtapeLivraison, Long> {
    // Spring Boot va générer automatiquement la requête SQL pour trouver les étapes d'un colis spécifique
    List<EtapeLivraison> findByColisIdOrderByDateEtapeDesc(Long colisId);
}