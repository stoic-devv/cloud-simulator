# Project structure

We have the following project structure:

1. `doc`: where all the documentation resides. It also contains images and log files for each simulation for better readability and evaluation.
2. `src/main/resources`: is where our project configurations are present.
3. `src/main/scala` is where our code resides. In alphabetical order:
   1. `RunSimulations.scala`: this is the starting point of our project where the main method resides to run our experiments.
   2. `analysis`: this package contains files wherein we perform experiments.
   3. `config`: this package provides logic and data structures to parse and store our configurations.
   4. `constants`: our project constants to avoid changing strings at multiple places.
   5. `factory`: this package provides factory methods to create Datacenter, Host, VM, Cloudlets, and resource provision/allocation policy.
   6. `simulations`: this is where our (re-usable) simulations reside.
   7. `util`: this package provides helper methods to obtain/validate configuration and logger. It also provides a decorator to customize our simulation output.
4. `src/test/resources`: is where our configuration for test suites reside.
5. `src/test/scala`: is where our test suites are. This follows same package structure as `src/main/scala`.

Additional documentation can be found in the javadoc of the methods, comments within the codes and loggers.