package com.lorgen.calculator.util;

import com.google.common.collect.Lists;

import java.util.List;

public class UtilNumber {
    public static boolean isPrime(long number) {
        if ((number != 2 && number % 2 == 0) || number <= 1) {
            return false;
        }

        for (int i = 3; i * i <= number; i += 2) {
            if (number % i != 0) {
                continue;
            }

            return false;
        }

        return true;
    }

    public static boolean isInteger(double number) {
        return number == (int) number;
    }

    public static boolean isLong(double number) {
        return number == (long) number;
    }

    public static boolean isNatural(double number) {
        return isInteger(number) && number > 0;
    }

    public static List<Long> findPrimes(long to) {
        return UtilNumber.findPrimes(0, to);
    }

    public static List<Long> findPrimes(long from, long to) {
        List<Long> primes = Lists.newLinkedList();
        if (from <= 2) {
            primes.add(2L);
        }

        if (from % 2 == 0) {
            from++;
        }

        for (long i = from; i <= to; i += 2) {
            if (!UtilNumber.isPrime(i)) {
                continue;
            }

            primes.add(i);
        }

        return primes;
    }

    public static long estimatedPrimeAmount(long to) {
        return (long) (to / Math.log(to));
    }

    public static long estimatedPrimeAmount(long from, long to) {
        return UtilNumber.estimatedPrimeAmount(to) - UtilNumber.estimatedPrimeAmount(from);
    }

    public static Long[] factorize(long number) {
        if (UtilNumber.isPrime(number)) {
            return new Long[]{number};
        }

        List<Long> factors = Lists.newLinkedList();
        long left = number;

        while (left != 1) {
            if (UtilNumber.isPrime(left)) {
                factors.add(left);
                left = 1;
                continue;
            }

            for (Long prime : UtilNumber.findPrimes((long) Math.sqrt(left))) {
                if (UtilNumber.isInteger((double) left / (double) prime)) {
                    factors.add(prime);
                    left = left / prime;
                    break;
                }
            }
        }

        return factors.toArray(new Long[factors.size()]);
    }
}
