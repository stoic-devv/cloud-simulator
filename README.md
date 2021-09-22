<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->

- [Homework 1](#homework-1)
  - [About](#about)
  - [Build and Run configurations](#build-and-run-configurations)
  - [Experiments and Observations](#experiments-and-observations)
    - [Scheduling with Cloudlet Utlization model](#scheduling-with-cloudlet-utlization-model)
      - [Observations: Part I](#observations-part-i)
      - [Inference: Part I](#inference-part-i)
      - [Observations: Part II](#observations-part-ii)
      - [Inference: Part II](#inference-part-ii)
    - [Resource Allocation](#resource-allocation)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Homework 1

## About

## Build and Run configurations

## Experiments and Observations

We try out different scenarios to understand how the cost and performance varies by using different
scheduling (cloudlets and VM) and VM allocation models.

### Scheduling with Cloudlet Utlization model

We have taken the base simulation of a single Datacenter, 4 Hosts, 8 VMs and 15 cloudlets. We analyze
the performance and costs of the simulation with varying utilization powers of the cloudlets.

#### Observations: Part I

For `time-shared` cloudlet scheduler, `space-shared` VM scheduler and `round-robin` VM allocation policy:

| Metric vs Util ratio    | 20%    | 40%    | 60%   | 75%    | 100%        |
|-------------------------|--------|--------|-------|--------|-------------|
| Total time              | 10.32  | 5.32   | 29.65 | 71.99  | 9.2 x 10^9  |
| Avg time per cloudlet   | 9.77   | 4.94   | 15.42 | 34.68  | 4.3 x 10^8  |
| Total cost              | 229.04 | 120.23 | 356.1 | 789.51 | 9.68 x 10^9 |
| Avg cost per cloudlet | 15.27  | 8.02   | 23.74 | 52.63  | 6.45 x 10^8 |

#### Inference: Part I 
2. Cloudlets with high utilization ratio are overusing the platform resources, and thus the cost and time to completion go exponential. From logs:
    ```
   17:17:32.547 [main] WARN  CloudletScheduler - 1.10: CloudletSchedulerTimeShared: Cloudlet 8 requested 4096 MB of Ram but no amount is available.. Using Virtual Memory, which delays Cloudlet processing.
   17:17:32.548 [main] WARN  CloudletScheduler - 1.10: CloudletSchedulerTimeShared: Cloudlet 8 requested 1000 Mbps of Bandwidth but no amount is available., which delays Cloudlet processing.
   ```
3. Cloudlets with less utilization ratio are underusing the platform resources. This too leads to higher cost and resources because of frequent context switching. 
4. Since the `time-shared` cloudlet scheduler is not pre-emptive (does not make room for the cloudlets already in the waiting list), there is a time lag between consecutive cloudlet execution. This time lag increases with higher utilization of the cloudlets. From logs: <br />
   For 20% 
   ```
   17:17:32.144 [main] INFO  DatacenterBroker - 5.11: DatacenterBrokerSimple2: Cloudlet 7 finished in Vm 7 and returned to broker.
   17:17:32.152 [main] INFO  DatacenterBroker - 10.21: DatacenterBrokerSimple2: Cloudlet 0 finished in Vm 0 and returned to broker.
   ```
   For 100%
    ```
   17:17:32.568 [main] INFO  DatacenterBroker - 2.21: DatacenterBrokerSimple2: Cloudlet 3 finished in Vm 3 and returned to broker.
   17:17:32.570 [main] INFO  DatacenterBroker - 922337206.58: DatacenterBrokerSimple2: Cloudlet 8 finished in Vm 0 and returned to broker.
   ```
5. The (short) wait-time of the cloudlets the queue is because `space-shared` scheduler allows only one cloudlet per VM
6. The optimal usage of resources of the platform by the cloudlets (with our given configuration) happens near 40%. This means dynamic allocation policy for cloudlets with a fixed CPU utilization must happen with a thorough knowledge of the product domain and configuration. Multiple iterations of simulations must happen before the code is deployed in production. This might not be a good strategy with unpredictable traffic.

#### Observations: Part II

For `space-shared` cloudlet scheduler, `time-shared` VM scheduler and `round-robin` VM allocation policy:

| Metric vs Util ratio       | 20%    | 40%   | 60%   | 75%  | 100%  |
|----------------------------|--------|-------|-------|------|-------|
| Total time                 | 10.44  | 5.44  | 3.77  | 3.11 | 2.43  |
| Avg time per cloudlet      | 5.06   | 2.56  | 1.72  | 1.39 | 1.05  |
| Avg wait time per cloudlet | 2.39   | 1.22  | 0.83  | 0.68 | 0.51  |
| Total cost                 | 122.99 | 66.74 | 47.99 | 40.5 | 32.87 |
| Avg cost per cloudlet      | 8.2    | 4.45  | 3.12  | 2.7  | 2.19  |

 `time-shared` cloudlet scheduler, `time-shared` VM scheduler has similar performance as  [Part I](#observations-part-i) 
and `space-shared` cloudlet scheduler, `space-shared` VM scheduler has similar performance as [Part II](#observations-part-ii) hence
are not included it in the observations and configuration, and also don't provide the performance matrix.

#### Inference: Part II
1. Since the cloudlets run asynchronously and have same configuration, all of them have similar time to completion
2. There has been a significant improvement in the cost and performance with having `space-shared` cloudlet scheduler. This is because
3. Unlike in  [Part I](#observations-part-i) of the experiment, the resources are distributed equitably and the cloudlets don't wait in the execution queue for long.
4. The wait-time is because `space-shared` scheduler allows only one cloudlet per VM

### Resource Allocation
1. `best-fit` VM allocation policy
Allocates VM to a host based on the number of PEs in the host. This policy is computationally inefficient as the next VM is allocated in `O(n)` time. The simulation wasn't able

3. `round-robin` allocation policy
Allocates VM in a circular way. It's time efficient `O(1)` but increases the number of active hosts and hence power consumption



