package exercice1;

import java.util.List;

public class App {

    public static void question1() {
        System.out.println("-Question 1");

        /* Float */
        Somme<Float> floatSomme = new Somme<Float>() {
            @Override
            public Float somme(Float premier, Float second) {
                return premier + second;
            }
        };
        Float float1 = 14.65f;
        Float float2 = 2.267f;
        System.out.println("Float :" + floatSomme.somme(float1, float2));

        /* Integer */
        Somme<Integer> integerSomme = (premier, second) -> premier + second;
        Integer chiffre1 = 7;
        Integer chiffre2 = 10;
        System.out.println("Integer: " + integerSomme.somme(chiffre1, chiffre2));

        /* Double */
        Somme<Double> doubleSomme = Double::sum;
        Double double1 = 7.86;
        Double double2 = 10.43;
        System.out.println("Double: " + doubleSomme.somme(double1, double2));

        /* String */
        Somme<String> stringSomme = String::concat;
        String mot1 = "Hello";
        String mot2 = "World";
        System.out.println("String: " + stringSomme.somme(mot1, mot2));

        System.out.println();
    }

    public static void question2() {
        System.out.println("-Question 2");

        ToString<List<String>> listToString = premier -> {
            StringBuilder sb = new StringBuilder();
            for(String s : premier){
                sb.append(s);
            }
            return sb.toString();
        };

    }

    public static void main(String[] args) {
        App.question1();
        App.question2();
    }
}
