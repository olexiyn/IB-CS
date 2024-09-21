#include <iostream>
#include <vector>
#include <fstream>
#include <cmath>
#include <algorithm>
#include <cstdlib>

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

double spec_test(const std::vector<double>& input_data, double& p_value) {
    int N = input_data.size() - 1;
    if (N <= 0) {
        std::cerr << "No data error" << std::endl;
        p_value = -1.0;
        return -1.0;
    }

    double min_data = *std::min_element(input_data.begin(), input_data.end());
    double max_data = *std::max_element(input_data.begin(), input_data.end());

    std::vector<double> data;
    double range = max_data - min_data;
    for (const auto& i : input_data) {
        double norm_value = (i - min_data) / range;
        data.push_back(norm_value);
    }

    std::vector<std::pair<double, double>> points;
    for (int i = 0; i < N; ++i) {
        points.emplace_back(data[i], data[i + 1]);
    }

    std::vector<double> dists;
    for (int i = 0; i < N - 1; ++i) {
        double dx = points[i + 1].first - points[i].first;
        double dy = points[i + 1].second - points[i].second;
        double dist = std::sqrt(dx * dx + dy * dy);
        dists.push_back(dist);
    }

    double sum = 0.0;
    for (double d : dists)
        sum += d;
    double mean = sum / dists.size();

    double var_sum = 0.0;
    for (double d : dists)
        var_sum += (d - mean) * (d - mean);
    double variance = var_sum / (dists.size() - 1);

    double exp_mean = 0.5214;
    double exp_var = 0.0370;

    double z = (mean - exp_mean) / std::sqrt(exp_var / dists.size());

    p_value = std::erfc(std::abs(z) / std::sqrt(2.0));

    return z;
}

int main(int argc, char* argv[]) {
    if (argc < 2) {
        std::cerr << "Usage ./spectral_test" << std::endl;
        return 1;
    }

    std::string filename = argv[1];

    std::vector<double> data = read_rn(filename);
    if (data.empty()) {
        std::cerr << "No data error " << filename << std::endl;
        return 1;
    }

    double p_value;
    double z = spec_test(data, p_value);

    if (z == -1.0) {
        std::cerr << "Error during test" << std::endl;
        return 1;
    }

    std::cout << "Spectral Test Statistic z: " << z << std::endl;
    std::cout << "P-value: " << p_value << std::endl;

    double alpha = 0.05;
    if (p_value < alpha) {
        std::cout << "P-value is less than " << alpha << std::endl;
        std::cout << "Sequence shows significant deviations from randomness at alpha = " << alpha << std::endl;
    } else {
        std::cout << "P-value is greater than or equal to " << alpha << std::endl;
        std::cout << "Sequence does not show significant deviations from randomness at alpha = " << alpha << std::endl;
    }

    return 0;
}