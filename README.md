# Homework 1

## About

## Build and Run configurations

## Provisioning analysis

### VM Allocation
1. `best-fit` VM allocation policy
Allocates VM to a host based on the number of PEs in the host. This policy is computationally inefficient as the next VM is allocated in `O(n)` time. 

3. `round-robin` allocation policy
Allocates VM in a circular way. It's time efficient `O(1)` but increases the number of active hosts and hence power consumption

Cost analysis : 



