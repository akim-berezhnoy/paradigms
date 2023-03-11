package expression.generic.evaluators;

public abstract class Evaluator<T> {
    boolean checked;
    public final Evaluator<T> setChecks(boolean checked) {
        this.checked = checked;
        return this;
    }

    public T castT(int value) {throw new UnsupportedOperationException();}
    public T add(T a, T b) {throw new UnsupportedOperationException();}
    public T sub(T a, T b) {throw new UnsupportedOperationException();}
    public T mul(T a, T b) {throw new UnsupportedOperationException();}
    public T div(T a, T b) {throw new UnsupportedOperationException();}
    public T neg(T a) {throw new UnsupportedOperationException();}
    public T abs(T a) {throw new UnsupportedOperationException();}
    public T square(T a) {throw new UnsupportedOperationException();}
    public T mod(T a, T b) {throw new UnsupportedOperationException();}
    public T set (T a, T b) {throw new UnsupportedOperationException();}
    public T clear(T a, T b) {throw new UnsupportedOperationException();}
    public T count(T a) {throw new UnsupportedOperationException();}
}
