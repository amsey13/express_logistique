package org.amsey.express_logistique.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class EtapeLivraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateEtape;
    private String localisation; // ex: "Agence Conakry", "En transit"
    private String statut; // ex: "REÇU", "EXPÉDIÉ"

    // Relation : Plusieurs étapes appartiennent à Un colis
    @ManyToOne
    @JoinColumn(name = "colis_id", nullable = false)
    @JsonIgnore // Crucial : évite la boucle infinie lors de la génération du JSON
    private Colis colis;

    public EtapeLivraison() {
        this.dateEtape = LocalDateTime.now(); // Date automatique à la création
    }

    public EtapeLivraison(Colis colis, String localisation, String statut) {
        this.colis = colis;
        this.localisation = localisation;
        this.statut = statut;
        this.dateEtape = LocalDateTime.now();
    }

    // --- GETTERS ET SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDateEtape() { return dateEtape; }
    public void setDateEtape(LocalDateTime dateEtape) { this.dateEtape = dateEtape; }

    public String getLocalisation() { return localisation; }
    public void setLocalisation(String localisation) { this.localisation = localisation; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public Colis getColis() { return colis; }
    public void setColis(Colis colis) { this.colis = colis; }
}