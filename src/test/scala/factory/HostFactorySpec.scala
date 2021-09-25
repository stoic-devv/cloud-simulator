package factory

import config.{DatacenterConfig, HostConfig}
import org.cloudbus.cloudsim.hosts.HostSimple
import org.cloudbus.cloudsim.resources.PeSimple
import org.cloudbus.cloudsim.schedulers.vm.VmSchedulerTimeShared
import testconstants.TestConstants

class HostFactorySpec extends FactoryBaseSpec {

  val hostConfig = HostConfig(TestConstants.TEST_CONFIG_FILE,
    TestConstants.TEST_CONFIG_ENTRY)
  val datacenterConfig = DatacenterConfig(TestConstants.TEST_CONFIG_FILE,
    TestConstants.TEST_CONFIG_ENTRY)

  behavior of "host factory"

  it should "create a list of PEs based on host configuration" in {
    var pes = HostFactory.createPes(hostConfig)
    assert(pes.length == hostConfig.numPes)
    pes.foreach(pe => {
      pe shouldBe a [PeSimple]
    })
  }

  it should "create a host based on host configuration and activation flag" in {
    var host = HostFactory.createHost(hostConfig, false)
    host shouldBe a [HostSimple]
    assert(host.getStorage().getCapacity() == hostConfig.storage)
    assert(host.getRam().getCapacity() == hostConfig.ram)
    assert(host.getBw().getCapacity() == hostConfig.bw)
    assert(host.getPeList().size() == hostConfig.numPes)
    host.getPeList().forEach(pe => {
      pe shouldBe a [PeSimple]
    })
    host.getVmScheduler() shouldBe a [VmSchedulerTimeShared]

  }
}
