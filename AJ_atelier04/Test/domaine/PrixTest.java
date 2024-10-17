package domaine;

import exceptions.QuantiteNonAutoriseeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PrixTest {

    private Prix prixAucune;
    private Prix prixPub;
    private Prix prixSolde;

    @BeforeEach
    @DisplayName("Initialise les objets Prix avant chaque test")
    void setUp() {
        prixAucune = new Prix();
        prixAucune.definirPrix(1, 20);
        prixPub = new Prix(TypePromo.PUB, 15);
        prixSolde = new Prix(TypePromo.SOLDE, 12);
    }

    @Test
    @DisplayName("Vérifie que le constructeur lance une exception si le type de promo est null")
    void testConstructeurTypePromoNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Prix(null, 10);
        });
        assertEquals("Le type de promotion ne peut pas être null", thrown.getMessage());
    }

    @Test
    @DisplayName("Vérifie que le constructeur lance une exception si la valeur de la promo est <= 0")
    void testConstructeurValeurPromoInvalide() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            ;
            new Prix(TypePromo.PUB, -5);
        });
        assertEquals("La valeur de la promotion doit être supérieure à 0", thrown.getMessage());
    }

    @Test
    @DisplayName("ALL TEST GETTERS")
    void testGetters(){
        assertAll("Getters",
                () -> assertEquals(0, prixAucune.getValeurPromo()),
                () -> assertEquals(15, prixPub.getValeurPromo()),
                () -> assertNull(prixAucune.getTypePromo()),
                () -> assertEquals(TypePromo.SOLDE, prixSolde.getTypePromo())
        );
    }

    @Test
    @DisplayName("Test combiné des différents cas pour la méthode definirPrix")
    void testDefinirPrixCombiné() {
        assertAll("DefinirPrix",
                () -> {
                    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
                        prixAucune.definirPrix(0, 10); // quantité invalide
                    });
                    assertEquals("La quantité doit être supérieure à 0", thrown.getMessage());
                },
                () -> {
                    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
                        prixAucune.definirPrix(10, 0); // prix invalide
                    });
                    assertEquals("Le prix unitaire doit être supérieur à 0", thrown.getMessage());
                },
                () -> {
                    prixAucune.definirPrix(10, 6); // Définir un prix valide
                    assertEquals(6, prixAucune.getPrix(10), "Le prix pour 10 unités devrait être 6 euros après redéfinition.");
                }
        );
    }

    @Test
    @DisplayName("Test de la méthode getPrix")
    void testGetPrix(){
        assertAll("GETPRIX",
                () -> {IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
                    prixAucune.definirPrix(0, 10); // quantité invalide
                });
                    assertEquals("La quantité doit être supérieure à 0", thrown.getMessage());
    },
                () -> {
                    assertAll("GETPRIX",
                            () -> assertEquals(20, prixAucune.getPrix(1), "Le prix pour 1 unité devrait être 20 euros."),
                            () -> assertEquals(20, prixAucune.getPrix(5), "Le prix pour 5 unités devrait être 20 euros."),
                            () -> assertEquals(20, prixAucune.getPrix(9), "Le prix pour 9 unités devrait être 20 euros."),
                            () -> assertEquals(6, prixAucune.getPrix(10), "Le prix pour 10 unités devrait être 6 euros."),
                            () -> assertEquals(6, prixAucune.getPrix(15), "Le prix pour 15 unités devrait être 6 euros."),
                            () -> assertEquals(6, prixAucune.getPrix(20), "Le prix pour 20 unités devrait être 6 euros."),
                            () -> assertEquals(6, prixAucune.getPrix(25), "Le prix pour 25 unités devrait être 6 euros.")
                    );
                },
                () ->{
                    QuantiteNonAutoriseeException thrown = assertThrows(QuantiteNonAutoriseeException.class, () -> {
                        prixPub.getPrix(2);
                    });
                    assertEquals("Quantité non autorisée", thrown.getMessage());

                },
                ()-> {
                    QuantiteNonAutoriseeException thrown = assertThrows(QuantiteNonAutoriseeException.class, () -> {
                        prixSolde.getPrix(1);
                    });
                    assertEquals("Quantité non autorisée", thrown.getMessage());
                }
        );
    }
}

