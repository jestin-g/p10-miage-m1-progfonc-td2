package exercice2;

import java.util.function.Predicate;

public class App {

    public static void question1() {
        Predicate<Pair<Integer, Double>> tailleTropPetite = pair -> pair.premier < 100;
        Predicate<Pair<Integer, Double>> tailleTropGrande = pair -> pair.premier > 200;
        //Predicate<Pair<Integer, Double>> tailleCorrecte = ;
        Predicate<Pair<Integer, Double>> poidsTropLourd = pair -> pair.second > 150.0;
        //Predicate<Pair<Integer, Double>> poidsCorrecte = ;

        Predicate<Pair<Integer, Double>> tailleIncorrecte = tailleTropPetite.or(tailleTropGrande);
        Predicate<Pair<Integer, Double>> tailleCorrecte = (tailleTropPetite.and(tailleTropGrande)).negate();

        //Predicate<Pair<Integer, Double>> accesAutorise = tailleCorrecte.and()


        /* Création personnes */
        Pair<Integer, Double> simba = new Pair<>(90, 1.0);
        Pair<Integer, Double> mufasa = new Pair<>(210, 100.0);
        Pair<Integer, Double> pumba = new Pair<>(110, 200.0);
        Pair<Integer, Double> timon = new Pair<>(101, 50.0);


        /* Tests tailleTropPetite */
        System.out.println("Simba est trop petit: " + tailleTropPetite.test(simba));
        System.out.println("Mufase est trop petit: " + tailleTropPetite.test(mufasa));

        /* Tests tailleTropGrande */
        System.out.println("\nTimon est trop grand: " + tailleTropGrande.test(timon));
        System.out.println("Mufase est trop grand: " + tailleTropGrande.test(mufasa));

        /* Tests poids trop lourd */
        System.out.println("\nSimba est trop lourd: " + poidsTropLourd.test(simba));
        System.out.println("Pumba est trop lourd: " + poidsTropLourd.test(pumba));

        /* Tests tailleIncorrecte */
        System.out.println("Mufasa a une taille incorrecte: " + tailleIncorrecte.test(mufasa));
        System.out.println("Simba a une taille incorrecte: " + tailleIncorrecte.test(simba));

        /* Tests tailleIncorrecte */
        System.out.println("Pumba a une taille correcte: " + tailleCorrecte.test(mufasa));
        System.out.println("Timon a une taille correcte: " + tailleCorrecte.test(simba));

    }

    public static void main(String[] args) {
        App.question1();
    }
}
