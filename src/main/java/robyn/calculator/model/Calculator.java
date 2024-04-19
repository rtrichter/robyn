package robyn.calculator.model;

/**
 * Calculator
 *
 * Used for testing of the cliTools package
 *
 * @author Ryan Richter
 */
public class Calculator {
    private double ans;

    public Calculator() {
        this.ans = 0;
    }

    public void add(double value) {
        ans += value;
    }

    public void subtract(double value) {
        ans -= value;
    }

    public void multiply(double value) {
        ans *= value;
    }

    public void divide(double value) {
        ans /= value;
    }

    public void raise(double value) {
        ans = Math.pow(ans, value);
    }

    public void fail() throws Exception {
        throw new Exception("Exception for test purposes");
    }

    public double getAns() {
        return ans;
    }

    public void resetAns() {
        ans = 0;
    }

}
