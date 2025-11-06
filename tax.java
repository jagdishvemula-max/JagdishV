// File: TaxCalculator.java
// Simple progressive tax calculator. Edit brackets and rates as needed.

import java.util.Scanner;

public class TaxCalculator {
    public static void main(String[] args) {
        // Define progressive brackets (upper limits) and corresponding rates.
        // The last bracket's limit should be Double.POSITIVE_INFINITY.
        double[] limits = {10000.0, 40000.0, Double.POSITIVE_INFINITY};
        double[] rates  = {0.10, 0.20, 0.30};

        if (limits.length != rates.length) {
            System.err.println("Bracket limits and rates must have the same length.");
            System.exit(1);
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter taxable income: ");
        double income;
        try {
            income = Double.parseDouble(sc.nextLine().trim());
        } catch (Exception e) {
            System.err.println("Invalid input.");
            sc.close();
            return;
        }
        sc.close();

        if (income <= 0) {
            System.out.println("No taxable income.");
            return;
        }

        double remaining = income;
        double lower = 0.0;
        double totalTax = 0.0;

        System.out.printf("Income: %.2f%n", income);
        System.out.println("Tax breakdown by bracket:");
        for (int i = 0; i < limits.length; i++) {
            double upper = limits[i];
            double taxableInBracket = Math.max(0.0, Math.min(upper - lower, remaining));
            double taxForBracket = taxableInBracket * rates[i];
            if (taxableInBracket > 0) {
                System.out.printf("  %.2f - %.2f @ %.2f%% : Taxable = %.2f, Tax = %.2f%n",
                        lower,
                        Double.isInfinite(upper) ? Double.POSITIVE_INFINITY : upper,
                        rates[i] * 100.0,
                        taxableInBracket,
                        taxForBracket);
            }
            totalTax += taxForBracket;
            remaining -= taxableInBracket;
            lower = upper;
            if (remaining <= 0) break;
        }

        double effectiveRate = (totalTax / income) * 100.0;
        System.out.printf("Total tax: %.2f%nEffective tax rate: %.2f%%%n", totalTax, effectiveRate);
    }
}