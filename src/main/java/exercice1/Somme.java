package exercice1;

@FunctionalInterface
public interface Somme<T> {
    T somme(T premier, T second);
}
