package com.example.opm;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.BrentSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;

public class EquationSolver {

    public static double solveEquation(String equation, double xValue) {
        UnivariateSolver solver = new BrentSolver();
        UnivariateFunction function = x -> {
            switch (equation) {
                case "sin(x)":
                    return Math.sin(x);
                case "cos(x)":
                    return Math.cos(x);
                case "tan(x)":
                    return Math.tan(x);
                default:
                    throw new IllegalArgumentException("Данная функция не поддерживается");
            }
        };

        return solver.solve(100, function, -10, 10);
    }

    public static void main(String[] args) {
        String equation = "cos(x)";
        double xValue = 0.5;
        double result = solveEquation(equation, xValue);
        System.out.println("Уравнение: " + equation);
        System.out.println("y при x = " + xValue + " равен " + result);
    }
}