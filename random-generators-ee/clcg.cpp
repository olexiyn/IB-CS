#include <iostream>

class CLCG {
private:
    unsigned long a1, c1, m1, seed1;
    unsigned long a2, c2, m2, seed2;

public:
    CLCG(unsigned long a1, unsigned long c1, unsigned long m1, unsigned long initialSeed1,
                       unsigned long a2, unsigned long c2, unsigned long m2, unsigned long initialSeed2)
        : a1(a1), c1(c1), m1(m1), seed1(initialSeed1),
          a2(a2), c2(c2), m2(m2), seed2(initialSeed2) {}

    unsigned long next() {
        seed1 = (a1 * seed1 + c1) % m1;
        seed2 = (a2 * seed2 + c2) % m2;
        return (seed1 + seed2) % (m1 < m2 ? m1 : m2);
    }
};

int main() {
    CLCG gen(1664525, 1013904223, 4294967291, 123456789, 22695477, 1, 4294967296, 123456789);

    for (int i = 0; i < 1000000; i++) {
        std::cout << (gen.next() % 100) << std::endl;
    }

    return 0;
}