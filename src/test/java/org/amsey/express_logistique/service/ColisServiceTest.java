package org.amsey.express_logistique.service;

import org.amsey.express_logistique.entity.Colis;
// Ces deux imports vont être en rouge, c'est le but !
import org.amsey.express_logistique.entity.EtapeLivraison;
import org.amsey.express_logistique.repository.ColisRepository;
import org.amsey.express_logistique.repository.EtapeLivraisonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ColisServiceTest {

    @Mock
    private ColisRepository colisRepository; // Simule la base de données

    @Mock
    private EtapeLivraisonRepository etapeLivraisonRepository;

    @InjectMocks
    private ColisService colisService; // Le service métier qu'on veut tester

    @Test
    public void doitCreerUnNouveauColisAvecStatutEnPreparation() {
        // 1. Arrange (Préparation)
        Colis colisSimule = new Colis(1L, "EXP-12345", "Client Test", "EN_PREPARATION");
        Mockito.when(colisRepository.save(any(Colis.class))).thenReturn(colisSimule);

        // 2. Act (Exécution de la méthode métier qui n'existe pas encore)
        Colis resultat = colisService.creerColis("Client Test");

        // 3. Assert (Vérifications)
        assertNotNull(resultat, "Le colis retourné ne doit pas être null");
        assertEquals("EXP-12345", resultat.getNumeroSuivi(), "Le numéro de suivi doit correspondre");
        assertEquals("EN_PREPARATION", resultat.getStatutGlobal(), "Le statut initial doit être EN_PREPARATION");

        // Vérifie qu'on a bien appelé la sauvegarde en base de données une fois
        Mockito.verify(colisRepository, Mockito.times(1)).save(any(Colis.class));
    }

    @Test
    public void doitAjouterUneEtapeEtMettreAJourStatutColis() {
        // 1. Arrange : Préparation des données
        Colis colisExistant = new Colis(1L, "EXP-123", "Destinataire Test", "EN_PREPARATION");
        Mockito.when(colisRepository.findById(1L)).thenReturn(Optional.of(colisExistant));

        EtapeLivraison etapeSimulee = new EtapeLivraison(colisExistant, "Agence Conakry", "EN_TRANSIT");
        Mockito.when(etapeLivraisonRepository.save(any(EtapeLivraison.class))).thenReturn(etapeSimulee);

        // 2. Act : On exécute la vraie méthode métier
        EtapeLivraison resultat = colisService.ajouterEtape(1L, "Agence Conakry", "EN_TRANSIT");

        // 3. Assert : Vérifications critiques
        assertNotNull(resultat);
        // Règle métier la plus importante : le colis a-t-il bien changé de statut global ?
        assertEquals("EN_TRANSIT", colisExistant.getStatutGlobal());

        // On vérifie que les méthodes de sauvegarde ont bien été appelées exactement une fois
        Mockito.verify(colisRepository, Mockito.times(1)).save(colisExistant);
        Mockito.verify(etapeLivraisonRepository, Mockito.times(1)).save(any(EtapeLivraison.class));
    }

    @Test
    public void doitLancerExceptionSiColisIntrouvable() {
        // 1. Arrange : On simule une base de données qui ne trouve pas le colis 99
        Mockito.when(colisRepository.findById(99L)).thenReturn(Optional.empty());

        // 2 & 3. Act & Assert : On s'attend à ce qu'une erreur précise explose
        Exception exception = assertThrows(RuntimeException.class, () -> {
            colisService.ajouterEtape(99L, "Kankan", "LIVRÉ");
        });

        assertEquals("Erreur : Ce colis n'existe pas dans la base.", exception.getMessage());
    }
}
