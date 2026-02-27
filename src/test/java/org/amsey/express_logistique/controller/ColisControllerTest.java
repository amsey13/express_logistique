package org.amsey.express_logistique.controller;

import org.amsey.express_logistique.entity.Colis;
import org.amsey.express_logistique.service.ColisService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

// L'IMPORT CORRIGÉ SPÉCIFIQUE À SPRING BOOT 4
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
// LE NOUVEAU STANDARD POUR LE MOCK (Remplace @MockBean)
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
}