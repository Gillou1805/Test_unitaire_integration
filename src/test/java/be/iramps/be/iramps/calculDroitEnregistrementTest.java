package be.iramps.be.iramps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import Pret.Projet;

public class calculDroitEnregistrementTest {
	
	private static Projet projet;
    private static Projet mockedProjet;


	@BeforeAll
	static void setup() {
		
		calculDroitEnregistrementTest.projet = new Projet();
		calculDroitEnregistrementTest.mockedProjet = Mockito.spy(projet);
	}
	
	 @Test
     public void testCalculDroitEnregistrementCasNormaux() {
         // Cas 1 : Revenu cadastral = 750, prixHabitation = 300,000, calculAbattement = 35,000
         mockedProjet.setRevenuCadastral(750);
         mockedProjet.setPrixHabitation(300000.00);
         Mockito.doReturn(35_000.00).when(mockedProjet).calculAbattement();
         Assertions.assertEquals(33_125.00, mockedProjet.calculDroitEnregistrement());

         // Cas 2 : Revenu cadastral = 800, prixHabitation = 450,000, calculAbattement = 25,000
         mockedProjet.setRevenuCadastral(800);
         mockedProjet.setPrixHabitation(450_000);
         Mockito.doReturn(25_000.0).when(mockedProjet).calculAbattement();
         Assertions.assertEquals(53_125.00, mockedProjet.calculDroitEnregistrement());
     }
     @Test
     public void testCalculDroitEnregistrementCasLimites() {
         // Cas 3 : Revenu cadastral = 745, prixHabitation = 450,000, calculAbattement = 25,000
         mockedProjet.setRevenuCadastral(745);
         mockedProjet.setPrixHabitation(450_000);
         Mockito.doReturn(25_000.0).when(mockedProjet).calculAbattement();
         Assertions.assertEquals(25_500.00, mockedProjet.calculDroitEnregistrement());
     }

     @Test
     public void testCalculDroitEnregistrementValeursInvalides() {
         // Cas 4 : Revenu cadastral négatif
         mockedProjet.setRevenuCadastral(-800);
         mockedProjet.setPrixHabitation(300_000);
         Mockito.doReturn(35_000.0).when(mockedProjet).calculAbattement();
         IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> mockedProjet.calculDroitEnregistrement());
         Assertions.assertEquals("Le revenu cadastral doit être strictement positif et non nul.", exception.getMessage());

         // Cas 5 : Prix de l'habitation négatif
         mockedProjet.setRevenuCadastral(800);
         mockedProjet.setPrixHabitation(-150_000);
         Mockito.doReturn(20_000.0).when(mockedProjet).calculAbattement();
         exception = Assertions.assertThrows(IllegalArgumentException.class, () -> mockedProjet.calculDroitEnregistrement());
         Assertions.assertEquals("Le prix de l'habitation doit être strictement positif et non nul.", exception.getMessage());
     }

     @Test
     public void testCalculDroitEnregistrementValeursZero() {
         // Cas 6 : Revenu cadastral à 0
         mockedProjet.setRevenuCadastral(0);
         mockedProjet.setPrixHabitation(300_000);
         Mockito.doReturn(35_000.0).when(mockedProjet).calculAbattement();
         IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> mockedProjet.calculDroitEnregistrement());
         Assertions.assertEquals("Le revenu cadastral doit être strictement positif et non nul.", exception.getMessage());

         // Cas 7 : Prix de l'habitation à 0
         mockedProjet.setRevenuCadastral(800);
         mockedProjet.setPrixHabitation(0);
         Mockito.doReturn(20_000.0).when(mockedProjet).calculAbattement();
         exception = Assertions.assertThrows(IllegalArgumentException.class, () -> mockedProjet.calculDroitEnregistrement());
         Assertions.assertEquals("Le prix de l'habitation doit être strictement positif et non nul.", exception.getMessage());
     }

     @Test
     public void testCalculDroitEnregistrementValeursDecimales() {
         // Cas 8 : Revenu cadastral = 750, prixHabitation = 300,000,456, calculAbattement = 35,000
         mockedProjet.setRevenuCadastral(800); // Cast pour simuler un revenu proche de la limite
         mockedProjet.setPrixHabitation(300_000.456);
         Mockito.doReturn(35_000.0).when(mockedProjet).calculAbattement();
         Assertions.assertEquals(33_125.06, mockedProjet.calculDroitEnregistrement());

        
     }
     
     
     
     


}
