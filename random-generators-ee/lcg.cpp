#include <iostream>

class LinearCongruentialGenerator {
private:
    unsigned long a; 
    unsigned long c; 
    unsigned long m;
    unsigned long seed;

public:
    LinearCongruentialGenerator(unsigned long multiplier, unsigned long increment, unsigned long modulus, unsigned long initialSeed)
        : a(multiplier), c(increment), m(modulus), seed(initialSeed) {}
    
    unsigned long next () {
        seed = (a * seed + c) % m;
        return seed;
    }
};

int main() {
    LinearCongruentialGenerator lcg(1664525, 1013904223, 4294967296, 123456789);

    for (int i = 0; i < 1000000; i++) {
        std::cout << lcg.next() << std::endl;
    }

    return 0;
}