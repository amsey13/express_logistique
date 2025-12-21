package org.amsey.express_logistique.service;

import org.amsey.express_logistique.entity.Colis;
// Ces deux imports vont être en rouge, c'est le but !
import org.amsey.express_logistique.repository.ColisRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ColisServiceTest {

    @Mock
    private ColisRepository colisRepository; // Simule la base de données

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
}
