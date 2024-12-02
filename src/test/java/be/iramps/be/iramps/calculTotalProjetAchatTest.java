package be.iramps.be.iramps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import Pret.Projet ;

public class calculTotalProjetAchatTest {
	
	private static Projet projet;
    private static Projet mockedProjet;


	@BeforeAll
	static void setup() {
		
		calculTotalProjetAchatTest.projet = new Projet();
		calculTotalProjetAchatTest.mockedProjet = Mockito.spy(projet);
	}

    @Test
    public void testCalculTotalProjetAchatValeursStandards() {
        // Cas 1 : Valeurs standards
        mockedProjet.setPrixHabitation(300_000);
        mockedProjet.setFraisNotaireAchat(5_000);
        mockedProjet.setFraisTransformation(10_000);

        // Simuler les méthodes dépendantes
        Mockito.doReturn(15_000.0).when(mockedProjet).calculDroitEnregistrement();
        Mockito.doReturn(600.0).when(mockedProjet).calculTVAFraisTransformation();

        // Vérification du résultat pour Cas 1
        Assertions.assertEquals(330_600.0, mockedProjet.calculTotalProjetAchat());

        // Cas 2 : Frais notaires élevés
        mockedProjet.setPrixHabitation(500_000);
        mockedProjet.setFraisNotaireAchat(25_000);
        mockedProjet.setFraisTransformation(50_000);

        // Simuler les méthodes dépendantes
        Mockito.doReturn(40_000.0).when(mockedProjet).calculDroitEnregistrement();
        Mockito.doReturn(3_000.0).when(mockedProjet).calculTVAFraisTransformation();

        // Vérification du résultat pour Cas 2
        Assertions.assertEquals(618_000.0, mockedProjet.calculTotalProjetAchat());
    }

    @Test
    public void testCalculTotalProjetAchatFraisTransformationZero() {
        // Cas 3 : Frais de transformation à 0
        mockedProjet.setPrixHabitation(250_000);
        mockedProjet.setFraisNotaireAchat(2_500);
        mockedProjet.setFraisTransformation(0);

        // Simuler les méthodes dépendantes
        Mockito.doReturn(10_000.0).when(mockedProjet).calculDroitEnregistrement();
        Mockito.doReturn(300.0).when(mockedProjet).calculTVAFraisTransformation();

        // Vérification du résultat
        Assertions.assertEquals(262_800.0, mockedProjet.calculTotalProjetAchat());
    }

    @Test
    public void testCalculTotalProjetAchatValeursNegatives() {
        // Cas 4 : Valeurs négatives
        mockedProjet.setPrixHabitation(-200_000);
        mockedProjet.setFraisNotaireAchat(-10_000);
        mockedProjet.setFraisTransformation(1_000);

        // Simuler les méthodes dépendantes
        Mockito.doReturn(10_000.0).when(mockedProjet).calculDroitEnregistrement();
        Mockito.doReturn(300.0).when(mockedProjet).calculTVAFraisTransformation();

        // Vérification de l'exception levée par calculTotalProjetAchat
        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class, 
            mockedProjet::calculTotalProjetAchat
        );
        Assertions.assertEquals("Le coût total doit être strictement positif", exception.getMessage());
    }


    @Test
    public void testCalculTotalProjetAchatValeursCentimes() {
        // Cas 5 : Valeurs avec centimes
        mockedProjet.setPrixHabitation(300_456.78);
        mockedProjet.setFraisNotaireAchat(5_500.99);
        mockedProjet.setFraisTransformation(20_100.75);

        // Simuler les méthodes dépendantes
        Mockito.doReturn(15_000.0).when(mockedProjet).calculDroitEnregistrement();
        Mockito.doReturn(1_200.0).when(mockedProjet).calculTVAFraisTransformation();

        // Vérification du résultat
        Assertions.assertEquals(342_258.52, mockedProjet.calculTotalProjetAchat());
    }
    @Test
    public void testCalculTotalProjetAchatExceptionCoutTotalNegatif() {
        // Cas où le coût total doit être strictement positif (valeurs qui provoquent un coût total nul ou négatif)
        mockedProjet.setPrixHabitation(1);  // Prix d'habitation négatif
        mockedProjet.setFraisTransformation(1);  // Frais de transformation à 0
        mockedProjet.setFraisNotaireAchat(1); // Frais notaires négatifs

        // Simuler les méthodes internes
        Mockito.doReturn(-9000.00).when(mockedProjet).calculTVAFraisTransformation(); // TVA à 0
        Mockito.doReturn(0.0).when(mockedProjet).calculDroitEnregistrement(); // Droit d'enregistrement à 0

        // Vérification que l'exception est levée pour coût total nul ou négatif
        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            mockedProjet::calculTotalProjetAchat
        );
        Assertions.assertEquals("Le coût total doit être strictement positif", exception.getMessage());
    }
}
