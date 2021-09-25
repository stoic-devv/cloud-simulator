
## Index
- [Experiments and Observations](#experiments-and-observations)
  - [I Analyzing cloudlet scheduling vs CPU usage](#i-analyzing-cloudlet-scheduling-vs-cpu-usage)
    - [Observations: Part I](#observations-part-i)
    - [Inference: Part I](#inference-part-i)
    - [Observations: Part II](#observations-part-ii)
    - [Inference: Part II](#inference-part-ii)
  - [II Analyzing cloudlet utilization models](#ii-analyzing-cloudlet-utilization-models)
    - [Observations](#observations)
    - [Inference](#inference)
  - [III SaaS, PaaS and IaaS Analysis](#iii-saas-paas-and-iaas-analysis)
    - [Inference](#inference-1)
    - [Supplementary inference](#supplementary-inference)
  - [IV Multiple datacenters in a network](#iv-multiple-datacenters-in-a-network)
    - [Observations](#observations-1)
    - [Inference](#inference-2)
  - [~~Resource Allocation~~ ---In progress - DO NOT EVALUATE ---](#resource-allocation----in-progress---do-not-evaluate----)
  - [References](#references)


## Experiments and Observations

We try out different scenarios to understand how the cost and performance varies by using different
scheduling (cloudlets and VM) techniques and VM allocation models. We also explore SaaS, PaaS and IaaS model of cloud computing, and how datacenters perform and communicate in a network topology.

### I Analyzing cloudlet scheduling vs CPU usage

We have taken the base simulation of a single Datacenter, 4 Hosts, 8 VMs and 15 cloudlets. We analyze
the performance and costs of the simulation with varying utilization powers of the cloudlets. For brevity, we capture the results in a tabular form.

#### Observations: Part I

For `time-shared` cloudlet scheduler, `space-shared` VM scheduler and `round-robin` VM allocation policy:

| Metric vs Util ratio    | 20%    | 40%    | 60%   | 75%    | 100%        |
|-------------------------|--------|--------|-------|--------|-------------|
| Total time              | 10.32  | 5.32   | 29.65 | 71.99  | 9.2 x 10^9  |
| Avg time per cloudlet   | 9.77   | 4.94   | 15.42 | 34.68  | 4.3 x 10^8  |
| Avg wait time per cloudlet | 0.00   | 0.00  | 0.00  | 0.00 | 0.00  |
| Total cost              | 229.04 | 120.23 | 356.1 | 789.51 | 9.68 x 10^9 |
| Avg cost per cloudlet | 15.27  | 8.02   | 23.74 | 52.63  | 6.45 x 10^8 |

#### Inference: Part I
1. Cloudlets with high utilization ratio are overusing the platform resources, and thus the cost and time to completion go exponential (see point 3 for more details). From logs:
    ```
   17:17:32.547 [main] WARN  CloudletScheduler - 1.10: CloudletSchedulerTimeShared: Cloudlet 8 requested 4096 MB of Ram but no amount is available.. Using Virtual Memory, which delays Cloudlet processing.
   17:17:32.548 [main] WARN  CloudletScheduler - 1.10: CloudletSchedulerTimeShared: Cloudlet 8 requested 1000 Mbps of Bandwidth but no amount is available., which delays Cloudlet processing.
   ```
2. Cloudlets with less utilization ratio are underusing the platform resources. This too leads to higher cost and resources because of frequent context switching as we are using `time-shared` cloudlet scheduler.
3. Since the `time-shared` cloudlet scheduler is not pre-emptive (does not make room for the cloudlets already in the waiting list), there is a time lag between consecutive cloudlet execution. This time lag increases with higher utilization of the cloudlets. See [[1]](#cite-1) and [[2]](#cite-2). From logs: <br />
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
4. There is no wait-time for cloudlets because we have `time-shared` cloudlet scheduler which ensures cloudlets have similar runtime (here we have same configuration for all cloudlets; time may differ for cloudlets with different configurations).
5. The optimal usage of resources of the platform by the cloudlets (with our given configuration) happens near 40%. However, keeping the utilization ratio fixed might not be a good strategy.
We explore this point more in our experiment of analyzing utilization models for cloudlets.

#### Observations: Part II

For `space-shared` cloudlet scheduler, `time-shared` VM scheduler and `round-robin` VM allocation policy:

| Metric vs Util ratio       | 20%    | 40%   | 60%   | 75%  | 100%  |
|----------------------------|--------|-------|-------|------|-------|
| Total time                 | 10.44  | 5.44  | 3.77  | 3.11 | 2.43  |
| Avg time per cloudlet      | 5.06   | 2.56  | 1.72  | 1.39 | 1.05  |
| Avg wait time per cloudlet | 2.39   | 1.22  | 0.83  | 0.68 | 0.51  |
| Total cost                 | 122.99 | 66.74 | 47.99 | 40.5 | 32.87 |
| Avg cost per cloudlet      | 8.2    | 4.45  | 3.12  | 2.7  | 2.19  |

`time-shared` cloudlet scheduler, `time-shared` VM scheduler has similar performance as  [Part I](#observations-part-i). See more in [log](/log) directory.
and `space-shared` cloudlet scheduler, `space-shared` VM scheduler has similar performance as [Part II](#observations-part-ii) hence
are not included it in the observations and configuration, and also don't provide the performance matrix.

#### Inference: Part II
1. There has been a significant improvement in the cost and performance with having `space-shared` cloudlet scheduler. This is because unlike in  [Part I](#observations-part-i) of the experiment, the resources are distributed equitably and the cloudlets don't wait in the execution queue for long. The _pre-emptiveness_ is in our scheduling algorithm itself.
2. The wait-time is because `space-shared` scheduler allows only one cloudlet per VM. [source](https://javadoc.io/static/org.cloudsimplus/cloudsim-plus/4.3.5/org/cloudbus/cloudsim/schedulers/cloudlet/CloudletSchedulerSpaceShared.html)

### II Analyzing cloudlet utilization models
We have taken the base simulation of a single Datacenter, 200 Hosts, 400 VMs and 1500 cloudlets. We analyze
the performance and costs of the simulation with varying utilization models: dynamic, full and stochastic (uniformly random). Here too we tabulate our observations.

#### Observations

| Metric vs Utilization Model | Dynamic | Full   | Stochastic |
|-----------------------------|---------|--------|------------|
| Total time                  | 10.77   | 4.65   | 11         |
| Avg time per cloudlet       | 1.75    | 1.03   | 2.1        |
| Avg wait time per cloudlet  | 2.57    | 1.6    | 3.1        |
| Total cost                  | 4859.0  | 3237.6 | 5640.16    |
| Avg cost per cloudlet       | 3.24    | 2.16   | 3.76       |

#### Inference
1. We see the performance Full > Dynamic > Stochastic cloudlet utilization model.
2. Since this is a simulation where cloudlets consume resources in a _uniform_ way, there isn't sufficient evidence to conclude that Full is the best utilization model and Stochastic the worst.
3. In real world, applications don't consume resources uniformly hence with Full utilization model we may be over-provisioning our cloudlet. Cloudlet utilization models
are designed in retrospect with the data of previous runtimes (usually stochastic). [[3]](#cite-3)

### III SaaS, PaaS and IaaS Analysis
Following are the details of the experiment:
1. We analyze 3 models of cloud computing: `saas`, `paas`, `iaas`
   1. `SaaS model`: We do not have control over the resources, its allocation, and provision. We deploy our cloudlets with the cloud provider as a black box.
   2. `PaaS model`: We have some control over the cloudlets that we host. We can configure their scheduling and the number of instances. We still don't have access to the underlying hardware/infrastructure, resources and bandwidth.
   3. `IaaS model`: We have complete control: hardwares (their specifications and numbers), resource allocation and provision, network and bandwidth.
2. We analyze these simulations with same cloudlet configuration and customize various resource allocation and provision techniques based upon the access to them in the computation model
3. We observe cost and performances, and evaluate when to use which model.
4. For our experiment, we simulate runtimes of `15` cloudlets each having `file size = 512MB`, `instructions = 10000 mips`, `dynamic` utilization model using `2 PEs`.
5. We observe:

SaaS results</br>
![image](/doc/img/saas.png)

PaaS results</br>
![image](/doc/img/paas.png)

IaaS results</br>
![image](/doc/img/iaas.png)

#### Inference
1. We see that with increasing level of access over the resources and its allocation:
   1. cost decreases
   2. time to completion and wait time decreases
2. In SaaS, we work with default schedulers maintained by the cloud provider: `time-shared` cloudlet scheduler and `time-shared` VM scheduler. With Paas, we can customize our cloudlet scheduler; we use `space-shared` cloudlet scheduler based upon our [inference](#inference-part-ii) of experiment 1.1. There are significant performance and cost gains with this customization.
3. In IaaS, since we have complete control of resource provisioning and allocations, we increase the number of VMs. From the images in the observations above, we now have each VM running a single cloudlet, unlike SaaS and PaaS, where each VM runs atmost 2 cloudlets.

#### Supplementary inference
1. Increasing VMs in IaaS may not be cost-effective since in IaaS we are responsible for paying and maintaining the hardware
2. The improvement from PaaS to IaaS may look insignificant at a cursory glance. But improvement of 0.08 cost per cloudlet and 0.05 time completion scales if we have millions of cloudlets running. This simulation is there in our [future scope](/doc/README.md#improvements)
3. While deciding which model to use, we need to keep in mind:
   1. if we are a business having just a few _types_ of cloudlets (applications) SaaS would allow us to focus more on our business use case while improving our time and cost to delivery.
   2. if we have complex workflows involving cloudlets which rely heavily on each other, we may consider a PaaS computing model. However, most cloud providers have a lot of customizations available and are still generic enough; using these may reduce our time and cost significantly.

### IV Multiple datacenters in a network
Here we explore multiple datacenters (4) in a BRITE network topology. We compare its performance with a single datacenter having resources total of all the resources of the datacenters connected in a network.
In the network, each datacenter has 1 host with a single PE and 2 VMs. We run 2 cloudlets in both the scenarios
#### Observations
Datacenters in a network:
![image](/doc/img/datacenter-network.png)

```
01:37:10.634 [main] WARN  VmAllocationPolicy - 3.00: VmAllocationPolicySimple: No suitable host found for Vm 1 in Datacenter 2
01:37:10.637 [main] INFO  DatacenterBroker - 6.10: DatacenterBrokerSimple1: Trying to create Vm 1 in DatacenterSimple3
01:37:10.638 [main] INFO  VmAllocationPolicy - 10.20: VmAllocationPolicySimple: Vm 1 has been allocated to Host 0/DC 3
```

Solo datacenter:</br>
![image](/doc/img/solo-datacenter.png)</br>
_Note_: The total resources allocated to this datacenter are twice the resources of a single datacenter in the previous scenario. This is because only two datacenters were utilized while performing the network topology experiment.
#### Inference
1. From the logs of network simulation, we see that since host has a single PE, the next VM of the datacenter has no PE available and hence has to connected to the host of other datacenter.
2. The cloudlets have `40000 mips` and are expected to complete, when served by a VM of `250 mips`, in `40000/250 = 160 sec`. In network simulation the cloudlets run to completion with a higher time (`177-178 sec`). This is because of the network latency of hosting the VM of `Datacenter 2` on the host of `Datacenter 3`.
3. Despite the higher time to completion (due to latency) in network simulation, we have a same cost in both the cases. This is because we are still using same computation power to serve the cloudlets (above mips calculations). This reassures the pay per use quality of cloud computing.
4. From point 2 above, we also reaffirm the dynamic allocation of resources in cloud computing.

### ~~Resource Allocation~~ ---In progress - DO NOT EVALUATE --- 

1. `best-fit` VM allocation policy
   Allocates VM to a host based on the number of PEs in the host. This policy is computationally inefficient as the next VM is allocated in `O(n)` time. The simulation wasn't able

3. `round-robin` allocation policy
   Allocates VM in a circular way. It's time efficient `O(1)` but increases the number of active hosts and hence power consumption

### References
<a id="cite-1">[1]</a>
https://www.javadoc.io/doc/org.cloudsimplus/cloudsim-plus/1.2.4/org/cloudbus/cloudsim/schedulers/cloudlet/CloudletSchedulerTimeShared.html

<a id="cite-2">[2]</a>
https://github.com/manoelcampos/cloudsimplus/issues/170

<a id="cite-3">[3]</a>
CS 441 UIC MS Teams
