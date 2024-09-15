#include <iostream>
#include <vector>

class MRG {
private:
    std::vector<unsigned long> a;
    unsigned long m;
    std::vector<unsigned long> X;
    int k;
    int index;

public:
    MRG(std::vector<unsigned long> multi, unsigned long modl, std::vector<unsigned long> seed)
        : a(multi), m(modl), X(seed), k(multi.size()), index(0) {}

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
    std::vector<unsigned long> multi;
    multi.push_back(150);
    multi.push_back(300);
    
    unsigned long modl = 2147483647;
    
    std::vector<unsigned long> seed;
    seed.push_back(123456789);
    seed.push_back(987654321);

    MRG mrg(multi, modl, seed);

    for (int i = 0; i < 1000000; i++) {
        std::cout << (mrg.next()%100) << std::endl;
    }

    return 0;
}
