package be.iramps.be.iramps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import Pret.Projet;

public class calculApportMinimalTest {
	
	private static Projet projet;
    private static Projet mockedProjet;


	@BeforeAll
	static void setup() {
		
		calculApportMinimalTest.projet = new Projet();
		calculApportMinimalTest.mockedProjet = Mockito.spy(projet);
	}

    
    @Test
    public void testCalculApportMinimalAvecValeursStandardsEtElevees() {
        // Cas 1 : Montant standard pour l'apport minimal
        mockedProjet.setPrixHabitation(300_000);
        mockedProjet.setFraisTransformation(50_000);
        mockedProjet.setFraisNotaireAchat(5_000);

        // Simuler les méthodes internes
        Mockito.doReturn(3_000.0).when(mockedProjet).calculTVAFraisTransformation();
        Mockito.doReturn(33_125.0).when(mockedProjet).calculDroitEnregistrement();

        // Vérification du calcul pour Cas 1
        double apportMinimal = mockedProjet.calculApportMinimal();
        Assertions.assertEquals(73_425.00, apportMinimal, "Erreur dans le calcul pour Cas 1");

        // Cas 2 : Valeurs très élevées
        mockedProjet.setPrixHabitation(1_000_000);
        mockedProjet.setFraisTransformation(100_000);
        mockedProjet.setFraisNotaireAchat(15_000);

        Mockito.doReturn(6_000.0).when(mockedProjet).calculTVAFraisTransformation();
        Mockito.doReturn(122_500.0).when(mockedProjet).calculDroitEnregistrement();

        // Vérification du calcul pour Cas 2
        apportMinimal = mockedProjet.calculApportMinimal();
        Assertions.assertEquals(248_100.00, apportMinimal, "Erreur dans le calcul pour Cas 2");
    }

    @Test
    public void testCalculApportMinimalFraisTransformationZero() {
        // Cas 3 : Frais de transformation à 0
        mockedProjet.setPrixHabitation(250_000);
        mockedProjet.setFraisTransformation(0);
        mockedProjet.setFraisNotaireAchat(2_500);

        // Simuler les méthodes internes
        Mockito.doReturn(0.0).when(mockedProjet).calculTVAFraisTransformation();
        Mockito.doReturn(26_875.0).when(mockedProjet).calculDroitEnregistrement();

        // Vérification du calcul
        double apportMinimal = mockedProjet.calculApportMinimal();
        Assertions.assertEquals(54_375.00, apportMinimal);
    }

    @Test
    public void testCalculApportMinimalPrixHabitationNul() {
        // Cas 4 : Prix d'habitation nul (exception)
        mockedProjet.setPrixHabitation(0);
        mockedProjet.setFraisTransformation(10_000);
        mockedProjet.setFraisNotaireAchat(2_500);

        // Simuler les méthodes internes
        Mockito.doReturn(3_000.0).when(mockedProjet).calculTVAFraisTransformation();
        Mockito.doReturn(33_125.0).when(mockedProjet).calculDroitEnregistrement();

        // Vérification que l'exception est levée
        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class, 
            mockedProjet::calculApportMinimal
        );
        Assertions.assertEquals("Le prix de l'habitation doit être strictement positif.", exception.getMessage());
    }

    @Test
    public void testCalculApportMinimalValeursDecimales() {
        // Cas 5 : Valeurs avec des décimales
        mockedProjet.setPrixHabitation(300_456.78);
        mockedProjet.setFraisTransformation(12_345.67);
        mockedProjet.setFraisNotaireAchat(3_000);

        // Simuler les méthodes internes
        Mockito.doReturn(740.74).when(mockedProjet).calculTVAFraisTransformation();
        Mockito.doReturn(33_182.10).when(mockedProjet).calculDroitEnregistrement();

        // Vérification du calcul
        double apportMinimal = mockedProjet.calculApportMinimal();
        Assertions.assertEquals(67_536.42, apportMinimal);
    }

    @Test
    public void testCalculApportMinimalFraisTransformationNegatifs() {
        // Cas 6 : Frais de transformation négatifs (exception)
        mockedProjet.setPrixHabitation(300_000);
        mockedProjet.setFraisTransformation(-5_000);
        mockedProjet.setFraisNotaireAchat(5_000);

        // Simuler les méthodes internes
        Mockito.doReturn(1_200.0).when(mockedProjet).calculTVAFraisTransformation();
        Mockito.doReturn(15_000.0).when(mockedProjet).calculDroitEnregistrement();

        // Vérification que l'exception est levée
        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class, 
            mockedProjet::calculApportMinimal
        );
        Assertions.assertEquals("Les frais de transformation ne peuvent pas être négatifs.", exception.getMessage());
    }
    @Test
    void testCalculApportMinimalFraisNotaireNegatifs() {
        // Création de l'objet projet
        Projet projet = new Projet();
        
        // Définition des données d'entrée avec des frais de notaire négatifs
        projet.setPrixHabitation(300_000);
        projet.setFraisTransformation(50_000);
        projet.setRevenuCadastral(800);
        
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            projet.setFraisNotaireAchat(-1000); // Définir les frais de notaire à une valeur négative
            projet.calculApportMinimal(); // Appel de la méthode qui utilise ces frais
        });

        // Vérification du message d'exception
        Assertions.assertEquals("Les frais de notaire ne peuvent pas être négatifs.", exception.getMessage());
    }


}
