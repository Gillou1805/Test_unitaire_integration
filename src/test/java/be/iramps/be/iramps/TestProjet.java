package be.iramps.be.iramps;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import Pret.Projet;

public class TestProjet {

	 private static Projet projet;
	    private static Projet mockedProjet;

	    @BeforeAll
	    static void setup() {
	        TestProjet.projet = new Projet();
	        // Préparation d'un objet mocked pour les fonctions intégrées.
	        TestProjet.mockedProjet = Mockito.spy(projet);
	    }

	    @Nested
	    @DisplayName("Calcul de la tva sur les frais de transformation")
	    class calculTVAFraisTransformation {

	        @DisplayName("Calcul de la tva sur les frais de transformation: Validation simple")
	        @Test
	        public void calculTVAFraisTransformationSimple() {
	            // Test de valeurs simples, le test doit les verifier toutes:

	            Assertions.assertAll(
	                    () -> {
	                        projet.setFraisTransformation(90_000.00);
	                        Assertions.assertEquals(5_400.00, projet.calculTVAFraisTransformation());
	                    },
	                    () -> {
	                        projet.setFraisTransformation(-200_000.00);
	                        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> projet.calculTVAFraisTransformation());
	                        Assertions.assertEquals("Les frais de transformation ne peuvent pas être négatifs.", exception.getMessage());
	                    },
	                    () -> {
	                        projet.setFraisTransformation(100_000.00);
	                        Assertions.assertEquals(6_000.00, projet.calculTVAFraisTransformation());
	                    },
	                    () -> {
	                        projet.setFraisTransformation(59_595.00);
	                        Assertions.assertEquals(3_575.70, projet.calculTVAFraisTransformation());
	                    },
	                    () -> {
	                        projet.setFraisTransformation(1.00);
	                        Assertions.assertEquals(0.06, projet.calculTVAFraisTransformation());
	                    }

	            );
	        }

	       // @Disabled
	        @Test
	        public void calculTVAFraisTransformationArrondi() {
	            // Test de problème d'arrondis
	            projet.setFraisTransformation(92_123.89);
	            Assertions.assertEquals(5_527.43, projet.calculTVAFraisTransformation());
	        }

	        // Plutôt que refaire une lambda dans un assertAll.
	        //@Disabled
	       // @ParameterizedTest
	        //@ValueSource(doubles = { -90_000.00, -25_000.00 })
	      /*  @Test
	        @DisplayName("Calcul de la tva sur les frais de transformation: Validation des frais negatifs")
	        public void calculTVAFraisTransformationNegatif(double fraisTransformation) {
	            // Test de valeurs négatives
	            projet.setFraisTransformation(fraisTransformation);
	            IllegalArgumentException.exception = Assertions.assertThrows(IllegalArgumentException.class, () -> mockedProjet.calculTVAFraisTransformation());
	            Assertions.assertEquals("Le revenu cadastral doit être strictement positif et non nul.", exception.getMessage());
	        }*/
	    }

	   // @Disabled
	    @Nested
	    @DisplayName("Calcul de l'abattement fiscal")
	    class calculAbattement {
	        @Test
	        public void calculAbattementInferieur350_000() {
	            projet.setPrixHabitation(350_000.00);
	            Assertions.assertEquals(40_000.00, projet.calculAbattement());

	            projet.setPrixHabitation(0.00);
	            Assertions.assertEquals(0.00, projet.calculAbattement());

	            projet.setPrixHabitation(19_999.00);
	            Assertions.assertEquals(40_000.00, projet.calculAbattement());
	        }

	        @Test
	        public void calculAbattementSuperieur500_000() {
	            projet.setPrixHabitation(500_000.00);
	            Assertions.assertEquals(20_000.00, projet.calculAbattement());
	        }

	        @Test
	        public void calculAbattementEntreDeuxPositif() {
	            projet.setPrixHabitation(365_000.00);
	            Assertions.assertEquals(38_000.00, projet.calculAbattement());

	            projet.setPrixHabitation(363_363.36);
	            Assertions.assertEquals(38_218.22, projet.calculAbattement());

	            projet.setPrixHabitation(420_000.00);
	            Assertions.assertEquals(30_666.67, projet.calculAbattement());

	            projet.setPrixHabitation(419_999.99);
	            Assertions.assertEquals(30_666.67, projet.calculAbattement());

	            projet.setPrixHabitation(375_623.59);
	            Assertions.assertEquals(36_583.52, projet.calculAbattement());

	            projet.setPrixHabitation(478_987.65);
	            Assertions.assertEquals(22_801.65, projet.calculAbattement());

	        }

	       // @Disabled
	        @Test
	        public void calculAbattementEntreDeuxNegatif() {
	            // Test de valeurs négatives
	        	mockedProjet.setFraisTransformation(-350_000.00);
                Mockito.doReturn(40_000.00).when(mockedProjet).calculTVAFraisTransformation();
	        }
	    }

	   
	            
	            
	            
	              
	               
	        
	        
	        
	       
	       
	    
	    }
	    
	

