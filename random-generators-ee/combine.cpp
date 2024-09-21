#include <iostream>
#include <fstream>
#include <vector>
#include <map>
#include <set>
#include <string>
#include <sstream>
#include <iomanip>
#include <algorithm>

bool readNumbers(const std::string& filename, std::map<double, int>& counts) {
    std::ifstream infile(filename);
    if (!infile.is_open()) {
        std::cerr << "Error: Cannot open file '" << filename << "'." << std::endl;
        return false;
    }

    double number;
    while (infile >> number) {
        counts[number]++;
    }

    infile.close();
    return true;
}

int main(int argc, char* argv[]) {
    if (argc != 4) {
        std::cerr << "Usage: " << argv[0] << " <input_file1> <input_file2> <input_file3>" << std::endl;
        return 1;
    }

    std::map<double, int> counts1;
    std::map<double, int> counts2;
    std::map<double, int> counts3;

    if (!readNumbers(argv[1], counts1)) {
        return 1;
    }
    if (!readNumbers(argv[2], counts2)) {
        return 1;
    }
    if (!readNumbers(argv[3], counts3)) {
        return 1;
    }

    std::set<double> uniqueNumbers;
    for (const auto& pair : counts1) {
        uniqueNumbers.insert(pair.first);
    }
    for (const auto& pair : counts2) {
        uniqueNumbers.insert(pair.first);
    }
    for (const auto& pair : counts3) {
        uniqueNumbers.insert(pair.first);
    }

    std::ofstream outfile("output.csv");
    if (!outfile.is_open()) {
        std::cerr << "Error: Cannot create 'output.csv'." << std::endl;
        return 1;
    }

    outfile << "Number,LCG,MRG,CLCG\n";

    for (const auto& number : uniqueNumbers) {
        outfile << number << ",";

        int cnt1 = counts1.count(number) ? counts1[number] : 0;
        int cnt2 = counts2.count(number) ? counts2[number] : 0;
        int cnt3 = counts3.count(number) ? counts3[number] : 0;

        outfile << cnt1 << "," << cnt2 << "," << cnt3 << "\n";
    }

    outfile.close();

    return 0;
}