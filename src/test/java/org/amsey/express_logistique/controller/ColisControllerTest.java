package org.amsey.express_logistique.controller;

import org.amsey.express_logistique.entity.Colis;
import org.amsey.express_logistique.entity.EtapeLivraison;
import org.amsey.express_logistique.service.ColisService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.ArgumentMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ColisController.class)
public class ColisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ColisService colisService;

    @Test
    public void doitCreerUnColisEtRetournerStatut201() throws Exception {
        Colis colisSimule = new Colis(1L, "EXP-9999", "Client API", "EN_PREPARATION");
        Mockito.when(colisService.creerColis("Client API")).thenReturn(colisSimule);

        mockMvc.perform(post("/api/colis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nomDestinataire\": \"Client API\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroSuivi").value("EXP-9999"))
                .andExpect(jsonPath("$.statutGlobal").value("EN_PREPARATION"));
    }

    @Test
    public void doitAjouterUneEtapeEtRetournerStatut201() throws Exception {
        // 1. Préparation des fausses données (Mock)
        Colis colisSimule = new Colis(1L, "EXP-9999", "Client API", "EN_TRANSIT");
        EtapeLivraison etapeSimulee = new EtapeLivraison(colisSimule, "Agence Conakry", "EN_TRANSIT");

        // 2. On dit au faux service (Mock) comment réagir
        Mockito.when(colisService.ajouterEtape(
                ArgumentMatchers.eq(1L),
                ArgumentMatchers.eq("Agence Conakry"),
                ArgumentMatchers.eq("EN_TRANSIT")
        )).thenReturn(etapeSimulee);

        // 3. Exécution de la requête HTTP simulée et vérifications
        mockMvc.perform(post("/api/colis/1/etapes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"localisation\": \"Agence Conakry\", \"statut\": \"EN_TRANSIT\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.localisation").value("Agence Conakry"))
                .andExpect(jsonPath("$.statut").value("EN_TRANSIT"));
    }
}