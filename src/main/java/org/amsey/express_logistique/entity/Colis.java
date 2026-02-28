package org.amsey.express_logistique.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;


@Entity
@Table(name = "colis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Colis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "colis", cascade = CascadeType.ALL)
    private List<EtapeLivraison> etapes = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String numeroSuivi;

    @Column(nullable = false)
    private String nomDestinataire;

    @Column(nullable = false)
    private String statutGlobal;

    public Colis(Long id, String numeroSuivi, String nomDestinataire, String statutGlobal) {
        this.id = id;
        this.numeroSuivi = numeroSuivi; // C'est souvent cette ligne qui est oubliée !
        this.nomDestinataire = nomDestinataire;
        this.statutGlobal = statutGlobal;
    }

    public List<EtapeLivraison> getEtapes() { return etapes; }
    public void setEtapes(List<EtapeLivraison> etapes) { this.etapes = etapes; }
    public String getNumeroSuivi() {
        return numeroSuivi;
    }
}
