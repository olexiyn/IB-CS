#include <iostream>
#include <vector>
#include <fstream>
#include <cmath>

double mean_calc(const std::vector<double>& data) {
    double sum = 0.0;
    for (double num : data)
        sum += num;
    return sum / data.size();
}

double autocorrelation(const std::vector<double>& data, int lag) {
    double mean = mean_calc(data);
    double numer = 0.0;
    double denom = 0.0;
    int N = data.size();

    for (int i = 0; i < N - lag; ++i)
        numer += (data[i] - mean) * (data[i + lag] - mean);

    for (int i = 0; i < N; ++i)
        denom += (data[i] - mean) * (data[i] - mean);

    return numer / denom;
}

int main(int argc, char* argv[]) {
    std::vector<double> data;
    std::string filename = argv[1];
    std::ifstream infile(filename);
    double num;
    while (infile >> num)
        data.push_back(num);
    infile.close();

    if (data.empty()) {
        std::cerr << "No data error" << std::endl;
        return 1;
    }

    int lag = 1;

    double r = autocorrelation(data, lag);

    std::cout << "Autocorrelation coefficient at lag " << lag << " is " << r << std::endl;

    int N = data.size();
    double crit_value = 1.96 / sqrt(N);
    if (std::abs(r) > crit_value)
        std::cout << "Autocorrelation is significant at the 95% confidence level" << std::endl;
    else
        std::cout << "Autocorrelation is not significant at the 95% confidence level" << std::endl;

    return 0;
}