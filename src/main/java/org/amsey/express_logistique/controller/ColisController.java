package org.amsey.express_logistique.controller;

import org.amsey.express_logistique.dto.EtapeCreationDTO;
import org.amsey.express_logistique.entity.Colis;
import org.amsey.express_logistique.dto.ColisCreationDTO;
import org.amsey.express_logistique.entity.EtapeLivraison;
import org.amsey.express_logistique.service.ColisService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController // Indique à Spring que cette classe gère des requêtes web et renvoie du JSON
@RequestMapping("/api/colis") // Préfixe l'URL pour toutes les méthodes de cette classe
public class ColisController {

    private final ColisService colisService;

    // Injection de dépendance de ton service métier
    public ColisController(ColisService colisService) {
        this.colisService = colisService;
    }

    @PostMapping // Réagit aux requêtes HTTP de type POST
    @ResponseStatus(HttpStatus.CREATED) // Règle stricte REST : une création = code 201, pas 200
    public Colis creerColis(@RequestBody ColisCreationDTO dto) {
        // On extrait uniquement ce dont on a besoin en toute sécurité
        return colisService.creerColis(dto.nomDestinataire());
    }
    @PostMapping("/{id}/etapes")
    @ResponseStatus(HttpStatus.CREATED)
    public EtapeLivraison ajouterEtapeAuColis(@PathVariable Long id, @RequestBody EtapeCreationDTO dto) {
        // @PathVariable permet de récupérer le "id" directement dans l'URL
        return colisService.ajouterEtape(id, dto.localisation(), dto.statut());
    }
}
