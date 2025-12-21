package org.amsey.express_logistique.service;

import org.amsey.express_logistique.entity.Colis;
import org.amsey.express_logistique.repository.ColisRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ColisService {

    private final ColisRepository colisRepository;

    // Design Pattern : Injection de Dépendances (IoC).
    // Spring se charge de fournir le repository automatiquement.
    public ColisService(ColisRepository colisRepository) {
        this.colisRepository = colisRepository;
    }

    public Colis creerColis(String nomDestinataire) {
        Colis nouveauColis = new Colis();
        nouveauColis.setNomDestinataire(nomDestinataire);

        // Règle métier 1 : On génère un numéro de suivi unique (ex: EXP-A1B2C3D4)
        String numeroGenere = "EXP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        nouveauColis.setNumeroSuivi(numeroGenere);

        // Règle métier 2 : Le statut par défaut
        nouveauColis.setStatutGlobal("EN_PREPARATION");

        // On sauvegarde en base de données et on retourne le résultat
        return colisRepository.save(nouveauColis);
    }
}
