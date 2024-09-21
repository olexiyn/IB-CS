#include <iostream>
#include <vector>
#include <fstream>
#include <cmath>

#include <boost/math/distributions/chi_squared.hpp>

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

double chsq_test(const std::vector<double>& data, int binsnum, double& p_value) {
    int N = data.size();
    std::vector<int> o_frq(binsnum, 0);

    for (double i : data) {
        int bin = static_cast<int>(i);
        if (bin == binsnum) bin = binsnum - 1;
        o_frq[bin]++;
    }
    double e_frq = static_cast<double>(N) / binsnum;

    double chsq = 0.0;
    for (int i = 0; i < binsnum; ++i) {
        double obs = o_frq[i];
        double exp = e_frq;
        double diff = obs - exp;
        chsq += (diff * diff) / exp;
    }

    int dof = binsnum - 1;

    boost::math::chi_squared_distribution<double> chsq_d(dof);
    p_value = boost::math::cdf(boost::math::complement(chsq_d, chsq));

    return chsq;
}

int main(int argc, char* argv[]) {
    std::string filename = argv[1];
    std::vector<double> data = read_rn(filename);
    if (data.empty()) {
        std::cerr << "No data error" << std::endl;
        return 1;
    }

    int binsnum = 100;

    double p_value;
    double chsq_stat = chsq_test(data, binsnum, p_value);

    std::cout << "Chi-Square Statistic: " << chsq_stat << std::endl;
    std::cout << "Degrees of Freedom: " << binsnum - 1 << std::endl;
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