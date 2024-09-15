#include <iostream>

class LCG {
private:
    unsigned long a;
    unsigned long c;
    unsigned long m;
    unsigned long seed;

public:
    LCG(unsigned long multi, unsigned long incr, unsigned long modl, unsigned long iseed)
        : a(multi), c(incr), m(modl), seed(iseed) {}

    unsigned long next() {
        seed = (a * seed + c) % m;
        return seed;
    }
};

int main() {
    LCG lcg(1664525, 1013904223, 4294967296, 123456789);

    for (int i = 0; i < 1000000; i++) {
        std::cout << (lcg.next()%100) << std::endl;
    }

    return 0;
}