# Math / Number Theory Approach Guide

---

## Pattern 1: GCD / LCM

### Euclidean Algorithm (GCD)
```
FUNCTION gcd(a, b):
    WHILE b != 0:
        a, b = b, a % b
    RETURN a
```

**Key insight:** gcd(a, b) = gcd(b, a % b). Recursive or iterative.

```
// LCM from GCD
lcm(a, b) = (a / gcd(a, b)) * b    // divide first to avoid overflow
```

---

## Pattern 2: Fast Power (Binary Exponentiation)

### Core Idea: x^n = (x^2)^(n/2) for even n, x * x^(n-1) for odd n

```
FUNCTION power(x, n):
    IF n == 0: RETURN 1
    IF n < 0: RETURN 1 / power(x, -n)
    
    IF n is even:
        half = power(x, n/2)
        RETURN half * half
    ELSE:
        RETURN x * power(x, n-1)
```

**Time:** O(log n) instead of O(n)

### Iterative version:
```
result = 1
base = x

WHILE n > 0:
    IF n is odd: result *= base
    base *= base
    n /= 2

RETURN result
```

---

## Pattern 3: Sieve of Eratosthenes (Count Primes)

```
isPrime = [true] * (n + 1)
isPrime[0] = isPrime[1] = false

FOR i = 2 to sqrt(n):
    IF isPrime[i]:
        FOR j = i*i to n step i:
            isPrime[j] = false    // mark multiples as not prime

RETURN count of true values
```

**Time:** O(n log log n), **Space:** O(n)

---

## Pattern 4: Digit Manipulation

### Reverse a number:
```
reversed = 0
WHILE n != 0:
    digit = n % 10
    reversed = reversed * 10 + digit
    n /= 10
```

### Check palindrome number:
```
Reverse second half and compare with first half (avoids overflow)

original = n, reversed = 0
WHILE n > reversed:
    reversed = reversed * 10 + n % 10
    n /= 10
RETURN n == reversed OR n == reversed / 10
```

---

## Pattern 5: Modular Arithmetic

### Why: Prevent overflow when results are huge, especially in DP.

```
// Basic rules:
(a + b) % m = ((a % m) + (b % m)) % m
(a * b) % m = ((a % m) * (b % m)) % m
```

### Modular inverse (for division under modulo):
```
// Only works when m is prime (Fermat's little theorem)
inverse(a) = power(a, m - 2, m)
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Count Primes | Medium | Sieve | Amazon, Google |
| Pow(x, n) | Medium | Fast Power | All FAANG |
| Reverse Integer | Medium | Digit Manipulation | All FAANG |
| Palindrome Number | Easy | Digit Manipulation | All FAANG |
| Excel Sheet Column Number | Easy | Base-26 Math | Amazon |
| Happy Number | Easy | Cycle Detection (Floyd) | Amazon, Google |
| GCD of Strings | Easy | GCD | Google |
