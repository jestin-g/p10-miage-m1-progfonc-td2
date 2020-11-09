package exercice2;

public class Pair<T, U> {
    public T premier;
    public U second;

    public Pair(T premier, U second) {
        this.premier = premier;
        this.second = second;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", premier.toString(), second.toString());
    }
}
