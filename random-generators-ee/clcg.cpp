#include <iostream>

class CombiningGenerator {
private:
    unsigned long a1, c1, m1, seed1;
    unsigned long a2, c2, m2, seed2;

public:
    CombiningGenerator(unsigned long a1, unsigned long c1, unsigned long m1, unsigned long initialSeed1,
                       unsigned long a2, unsigned long c2, unsigned long m2, unsigned long initialSeed2)
        : a1(a1), c1(c1), m1(m1), seed1(initialSeed1),
          a2(a2), c2(c2), m2(m2), seed2(initialSeed2) {}

    unsigned long next() {
        seed1 = (a1 * seed1 + c1) % m1;
        seed2 = (a2 * seed2 + c2) % m2;
        return (seed1 + seed2) % m1;
    }
};

int main() {
    CombiningGenerator gen(1664525, 8017204223, 4248967296, 123456789, 
                           22695477, 1, 4294967296, 987654321);

    for (int i = 0; i < 10; i++) {
        std::cout << gen.next() << std::endl;
    }

    return 0;
}