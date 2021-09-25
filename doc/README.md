
## Index
- [About](#about)
- [Build and Run configurations](#build-and-run-configurations)
- [Topics](#topics)
  - [Improvements](#improvements)
    - [Functional improvements](#functional-improvements)
    - [Technical debts](#technical-debts)


## About
We use [cloudsimplus](https://github.com/manoelcampos/cloudsimplus) framework to simulate various resource allocation and provisioning scenarios. We also simulate the SaaS, PaaS and IaaS models of cloud computing.
Lastly, we explore how multiple datacenters in a network topology communicate and serve the cloudlets.

## Build and Run configurations

1. This project was developed using `IntelliJ` and is written in `Scala`. We use sbt build tool for compiling, testing and managing dependencies.
2. [RunSimulations.scala](/src/main/scala/RunSimulations.scala) is the starting point of this project where our main class is defined
3. You can build this project using sbt [plugin](https://plugins.jetbrains.com/plugin/5007-sbt) for IntelliJ or clone and import this repository as an sbt project. Alternatively, you can download and [install](https://www.scala-sbt.org/download.html) the sbt build tool, and run from your terminal.
4. Sbt commands for this project:

`> compile` and `> test` should run successfully with following results:
```
[info] Run completed in 3 seconds, 423 milliseconds.
[info] Total number of tests run: 30
[info] Suites: completed 13, aborted 0
[info] Tests: succeeded 30, failed 0, canceled 0, ignored 0, pending 0
```

## Topics
1. [Experiments and analysis](/doc/analysis.md)
2. [Project structure and design](/doc/design.md)
3. [Configuration Grammar](/doc/configuration-grammar.md)

### Improvements

#### Functional improvements
1. Explore network topology for VMs and hosts too.
2. Explore VM allocation policies. Comparison done but insufficient observations to make [inference](/doc/analysis.md#resource-allocation).
3. Experiment with large (millions) of cloudlets to observe for performance gains between PaaS and SaaS.
4. Introduce jobs to simulate:
   1. network switch in topology with algorithms like map-reduce
   2. broadcast storm
   3. missing or lost packets of information

#### Technical debts
1. Add more exception handling.
2. Config parser to make config files more flexible.
3. Incorporate a more functional design so that we have behavior driven design.This will improve testing too.

