# Computer Science EE on Random Number Generators

## Files
- **lcg.cpp** : *Linear Congruential Generator source in C++*
- **mrg.cpp** : *Multiple Recursive Generator source in C++*
- **clcg.cpp** : *Combined Generator source in C++*
- **lcg_results** : *Results of generation for LCG*
- **mrg_results** : *Results of generation for MRG*
- **clcg_results** : *Results of generation for CLCG*
- **combine.cpp** : *Combines results of the generation into a table*
- **output.csv** : *Resultant table of combine*
- **chisquare.cpp** : *Chi-Square Test*
    + **run** : *g++ -o chisquare -I /opt/homebrew/Cellar/boost/1.86.0/include/ -std=c++14  chisquare.cpp*
- **kstest.cpp** : *Kolmogorov-Smirnov Test*
    + **run** : *g++ -o kstest kstest.cpp && ./kstest lcg_results.txt*
- **autocorrelation.cpp** : *Autocorrelation Test*
    + **run** : *g++ -o autocorrelationtest -std=c++14 autocorrelationtest.cpp && ./autocorrelationtest lcg_results.txt*
- **spectral.cpp** : *Spectral Test*
    + **run** : *g++ -o spectraltest -std=c++14 spectraltest.cpp && ./spectraltest lcg_results.txt*