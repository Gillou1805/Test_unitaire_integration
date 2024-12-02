package be.iramps.be.iramps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Pret.Pret;
import Pret.Projet;

@DisplayName("Tests d'intégration: ensemble des composants")
public class ITApp {
    @Test
    @DisplayName("Validation du meilleur scénario")
    public void happyPath (){
        Projet projet = new Projet();
        projet.setPrixHabitation(300_000);
        projet.setFraisNotaireAchat(5_000);
        projet.setFraisTransformation(50_000);
        projet.setRevenuCadastral(700); 

        // Calculer les données à partir de la classe Projet
        double apportMinimal = projet.calculApportMinimal(); // Doit calculer le montant d'apport nécessaire
        double montantEmprunt = projet.calculResteAEmprunter(); // Total du projet - apport minimal

        // Vérifier les résultats intermédiaires 
        Assertions.assertEquals(55_900.00, apportMinimal, 0.01, "Apport minimal incorrect");
        Assertions.assertEquals(317_700.00, montantEmprunt, 0.01, "Montant emprunté incorrect");

       //prêt avec les données calculées du projet
        Pret pret = new Pret();
        pret.setFraisDossierBancaire(500);
        pret.setFraisNotaireCredit(5_475);
        pret.setNombreAnnees(15); // Prêt sur 15 ans
        pret.setTauxAnnuel(0.04); // Taux annuel de 4 %

        // Calculer les données à partir de la classe Pret
        double mensualites = pret.calculMensualites(montantEmprunt);
        double totalInterets = pret.calculTotalInterets(montantEmprunt);
        double montantRestantDu = pret.calculRestantDu(montantEmprunt);

        // Vérifier les résultats finaux
        Assertions.assertEquals(2338.62, mensualites);
        Assertions.assertEquals(103251.6, totalInterets);
        Assertions.assertEquals(5_975.00, montantRestantDu);
    }

}