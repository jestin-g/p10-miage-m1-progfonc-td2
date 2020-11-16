package exercice3;

import exercice3.universite.Annee;
import exercice3.universite.Etudiant;
import exercice3.universite.Matiere;
import exercice3.universite.UE;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class App<T> {
    /* Question 1 */
    static Predicate<Etudiant> numEtuPair = etudiant -> Integer.parseInt(etudiant.numero()) % 2 == 0;

    /* Question 2 */
    static Predicate<Etudiant> aNoteEliminatoire = etudiant -> {
        for (Map.Entry<Matiere, Double> resultat : etudiant.notes().entrySet()) {
            if (resultat.getValue() < 6)
                return true;
        }
        return false;
    };

    /* Question 3 */
    static Predicate<Etudiant> estDefaillant = etudiant -> {
        Set<Matiere> matieres = App.touteLesMatieresDeLAnnee(etudiant.annee());
        for (Matiere matiere : matieres) {
            if (!etudiant.notes().containsKey(matiere))
                return true;
        }
        return false;
    };

    /* Question 5 */
    static Predicate<Etudiant> naPasLaMoyenneV1 = etudiant -> {
        return moyenneEtu(etudiant) < 10;
    };

    /* Question 6 */
    static Predicate<Etudiant> naPasLaMoyenneV2 = etudiant -> {
        try {
            return moyenneEtu(etudiant) < 10;
        } catch (NullPointerException e) {
            System.err.printf("La moyenne de l'étudiant %s %s n'a pas pu être calculée%n", etudiant.prenom(), etudiant.nom());
            e.printStackTrace();
            return false;
        }
    };

    /* Question 7 */
    static Predicate<Etudiant> session2v1 = aNoteEliminatoire.or(estDefaillant).or(naPasLaMoyenneV1);

    /* Question 8 */
    static Function<Etudiant, String> afficherEtu = new Function<Etudiant, String>() {
        @Override
        public String apply(Etudiant etudiant) {
            Double moyenneEtu = moyenneEtuIndicative(etudiant);
            return String.format("%s %s : %.2f", etudiant.prenom(), etudiant.nom(), Objects.requireNonNullElse(moyenneEtu, "défaillant"));
        }
    };

    public static void main(String[] args) {
        Matiere m1 = new Matiere("MAT1");
        Matiere m2 = new Matiere("MAT2");
        UE ue1 = new UE("UE1", Map.of(m1, 2, m2, 2));
        Matiere m3 = new Matiere("MAT3");
        UE ue2 = new UE("UE2", Map.of(m3, 1));
        Annee a1 = new Annee(Set.of(ue1, ue2));
        Etudiant e1 = new Etudiant("39001", "Alice", "Merveille", a1);
        e1.noter(m1, 12.0);
        e1.noter(m2, 14.0);
        e1.noter(m3, 10.0);
        Etudiant e2 = new Etudiant("39002", "Bob", "Eponge", a1);
        e2.noter(m1, 14.0);
        e2.noter(m3, 14.0);
        Etudiant e3 = new Etudiant("39003", "Charles", "Chaplin", a1);
        e3.noter(m1, 18.0);
        e3.noter(m2, 5.0);
        e3.noter(m3, 14.0);

        /* Question 1 */
        afficherSi("**ETUDIANTS NUM PAIR", numEtuPair, a1);
        afficherSi("**ETUDIANTS NUM IMPAIR", numEtuPair.negate(), a1);

        /* Question 2 */
        afficherSi("**ETUDIANTS DEF", estDefaillant, a1);

        /* Question 3 */
        afficherSi("**ETUDIANTS AVEC NOTE ELIMINATOIRE", aNoteEliminatoire, a1);

        /* Question 4 */
        System.out.println(moyenneEtu(e1));

        /* Question 5 */
        // afficherSi("**ETUDIANTS N'AYANT PAS LA MOYENNE", naPasLaMoyenneV1, a1);

        /* Question 6 */
        afficherSi("**ETUDIANTS N'AYANT PAS LA MOYENNE", naPasLaMoyenneV2, a1);

        /* Question 7 */
        afficherSi("**ETUDIANTS EN SESSION 2 (v1)", session2v1, a1);

        /* Question 8 */
        afficherSiv2("**TOUT LES ETUDIANTS", x -> true, a1, afficherEtu);
    }

    public static void afficherSi(String enTete, Predicate<Etudiant> condition, Annee annee) {
        System.out.println(enTete);
        annee.etudiants().forEach(etudiant -> {
            if (condition.test(etudiant))
                System.out.println(etudiant);
        });
    }

    public static Set<Matiere> touteLesMatieresDeLAnnee(Annee annee) {
        Set<Matiere> matieres = new HashSet<>();
        annee.ues().forEach(ue -> ue.ects().forEach((matiere, integer) -> matieres.add(matiere)));
        return matieres;
    }

    public static Double moyenneEtu(Etudiant etudiant) {
        if (estDefaillant.test(etudiant))
            return null;

        double moyenne = 0.0;
        int coefficient = 0;
        for (UE ue : etudiant.annee().ues()) {
            for (Map.Entry<Matiere, Integer> ects : ue.ects().entrySet()) {
                Matiere matiere = ects.getKey();
                Integer credits = ects.getValue();
                Double note = etudiant.notes().get(matiere);
                moyenne += note * credits;
                coefficient += credits;
            }
        }
        moyenne /= coefficient;
        return moyenne;
    }

    public static void afficherSiv2(String enTete, Predicate<Etudiant> condition, Annee annee, Function<Etudiant, String> foncAffichage) {
        System.out.println(enTete);
        annee.etudiants().forEach(etudiant -> {
            if (condition.test(etudiant))
                System.out.println(foncAffichage.apply(etudiant));
        });
    }

    public static Double moyenneEtuIndicative(Etudiant etudiant) {
        double moyenne = 0.0;
        int coefficient = 0;
        for (UE ue : etudiant.annee().ues()) {
            for (Map.Entry<Matiere, Integer> ects : ue.ects().entrySet()) {
                Matiere matiere = ects.getKey();
                Integer credits = ects.getValue();
                if (etudiant.notes().containsKey(matiere)) {
                    Double note = etudiant.notes().get(matiere);
                    moyenne += note * credits;
                }
                coefficient += credits;
            }
        }
        moyenne /= coefficient;
        return moyenne;
    }

}
