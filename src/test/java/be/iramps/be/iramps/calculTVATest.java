package be.iramps.be.iramps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import Pret.Projet;

public class calculTVATest {
	
	private static Projet projet;
	
	@BeforeAll
	static void setup() {
		
			 calculTVATest.projet = new Projet();
	}
	
	@Test
	public void calculTVAFraisTransformationSimple() {
	    // Test de valeurs simples, le test doit les vérifier toutes:
	    Assertions.assertAll(
	        () -> {
	            projet.setFraisTransformation(90_000.00);
	            Assertions.assertEquals(5_400.00, projet.calculTVAFraisTransformation());
	        },
	        () -> {
	            projet.setFraisTransformation(0.00);
                Assertions.assertEquals(0.00, projet.calculTVAFraisTransformation());
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



}
