#include <iostream>
#include <vector>
#include <fstream>
#include <algorithm>
#include <cmath>

std::vector<double> read_rn(const std::string& filename) {
    std::vector<double> data;
    std::ifstream infile(filename);
    if (!infile.is_open()) {
        std::cerr << "Open error " << filename << std::endl;
        return data;
    }
    double num;
    while (infile >> num)
        data.push_back(num);
    infile.close();
    return data;
}

double ks_test(const std::vector<double>& data, double& p_value, double a, double b) {
    int N = data.size();
    std::vector<double> sorted_data = data;
    std::sort(sorted_data.begin(), sorted_data.end());

   double D = 0.0;
    for (int i = 0; i < N; ++i) {
        double x = sorted_data[i];
        double e_cdf = (i + 1) / static_cast<double>(N);
        double t_cdf = (x - a) / (b - a + 1);

        double diff = std::abs(e_cdf - t_cdf);
        if (diff > D)
            D = diff;
    }

    double sqrt_N = std::sqrt(N);
    double lambda = (sqrt_N + 0.12 + 0.11 / sqrt_N) * D;
    p_value = 2 * std::exp(-2 * lambda * lambda);
    std::cout<<p_value<<std::endl;
    if (p_value > 1.0)
        p_value = 1.0;

    return D;
}

int main(int argc, char* argv[]) {
    std::string filename = argv[1];
    std::vector<double> data = read_rn(filename);
    if (data.empty()) {
        std::cerr << "No data error" << std::endl;
        return 1;
    }

    double seq_min=0.0;
    double seq_max=99.0;
    double p_value;
    double D = ks_test(data, p_value, seq_min, seq_max);

    std::cout << "Kolmogorov-Smirnov Statistic D: " << D << std::endl;
    std::cout << "P-value: " << p_value << std::endl;

    double alpha = 0.05;
    if (p_value < alpha) {
        std::cout << "P-value is less than " << alpha << std::endl;
        std::cout << "Distribution is not uniform at alpha = " << alpha << std::endl;
    } else {
        std::cout << "P-value is greater than or equal to " << alpha << std::endl;
        std::cout << "Distribution is uniform at alpha = " << alpha << std::endl;
    }

    return 0;
}