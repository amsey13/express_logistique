package org.amsey.express_logistique.service;

import org.amsey.express_logistique.entity.Colis;
import org.amsey.express_logistique.entity.EtapeLivraison;
import org.amsey.express_logistique.repository.ColisRepository;
import org.amsey.express_logistique.repository.EtapeLivraisonRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ColisService {
    private final EtapeLivraisonRepository etapeLivraisonRepository;
    private final ColisRepository colisRepository;

    // Design Pattern : Injection de Dépendances (IoC).
    // Spring se charge de fournir le repository automatiquement.

    public ColisService(EtapeLivraisonRepository etapeLivraisonRepository, ColisRepository colisRepository) {
        this.etapeLivraisonRepository = etapeLivraisonRepository;
        this.colisRepository = colisRepository;
    }

    public Colis creerColis(String nomDestinataire) {
        String numeroSuivi = "EXP-" + System.currentTimeMillis();
        Colis colis = new Colis(null, numeroSuivi, nomDestinataire, "EN_PREPARATION");
        return colisRepository.save(colis);
    }
    public EtapeLivraison ajouterEtape(Long colisId, String localisation, String statut) {
        // 1. On cherche le colis de manière sécurisée
        Colis colis = colisRepository.findById(colisId)
                .orElseThrow(() -> new RuntimeException("Erreur : Ce colis n'existe pas dans la base."));

        // 2. On crée la nouvelle étape
        EtapeLivraison nouvelleEtape = new EtapeLivraison(colis, localisation, statut);

        // 3. Règle métier : On met à jour le statut global du colis avec le statut de la nouvelle étape
        colis.setStatutGlobal(statut);
        colisRepository.save(colis);

        // 4. On sauvegarde et on retourne l'étape
        return etapeLivraisonRepository.save(nouvelleEtape);
    }
}
