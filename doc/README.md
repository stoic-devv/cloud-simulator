<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->

- [About](#about)
- [Build and Run configurations](#build-and-run-configurations)
  - [Improvements](#improvements)
    - [Functional improvements](#functional-improvements)
    - [Technical debts](#technical-debts)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## About

## Build and Run configurations

### Improvements

#### Functional improvements
1. Explore network topology for VMs and hosts
2. Explore VM allocation policies. Comparison done but insufficient observations to make inference; refer [ResourceAllocationAnalysis](./src/main/scala/analysis/ResourceAllocationAnalysis.scala)).
#### Technical debts
1. Add more exception handling
2. Config parser to make config files more flexible
3. Incorporate a more functional design so that we have behavior driven design.This will improve testing too.

