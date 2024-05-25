#include <iostream>
#include <vector>

class MultipleRecursiveGenerator {
private:
    std::vector<unsigned long> a;
    unsigned long m;
    std::vector<unsigned long> X;
    int k;
    int index;

public:
    MultipleRecursiveGenerator(std::vector<unsigned long> multipliers, unsigned long modulus, std::vector<unsigned long> seed)
        : a(multipliers), m(modulus), X(seed), k(multipliers.size()), index(0) {}

    unsigned long next() {
        unsigned long next_val = 0;
        for (int i = 0; i < k; i++) {
            next_val += a[i] * X[(index - i + k) % k];
        }
        next_val %= m;
        X[index] = next_val;
        index = (index + 1) % k;
        return next_val;
    }
};

int main() {
    std::vector<unsigned long> multipliers;
    multipliers.push_back(150);
    multipliers.push_back(300);
    unsigned long modulus = 2147483647;
    std::vector<unsigned long> seed;
    seed.push_back(123456);
    seed.push_back(654321);

    MultipleRecursiveGenerator mrg(multipliers, modulus, seed);

    for (int i = 0; i < 10; i++) {
        std::cout << mrg.next() << std::endl;
    }

    return 0;
}
