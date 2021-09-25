package factory

import config.{DatacenterConfig, HostConfig}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.Datacenter
import testconstants.TestConstants
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.*

import scala.collection.mutable.ArrayBuffer

class DatacenterFactorySpec extends FactoryBaseSpec with MockitoSugar {

  val cloudSim = mock[CloudSim]
  val datacenterConfig = DatacenterConfig(TestConstants.TEST_CONFIG_FILE,
    TestConstants.TEST_CONFIG_ENTRY)
  val hostConfig = HostConfig(TestConstants.TEST_CONFIG_FILE,
    TestConstants.TEST_CONFIG_ENTRY)

  behavior of "Datacenter factory"

  it should "create a datacenter from configuration" in {
    val dc = DatacenterFactory.createDatacenter(datacenterConfig, hostConfig, cloudSim)
    dc shouldBe a [Datacenter]
    assert(checkCharacteristics(dc) == true)
  }

  it should "create a datacenter with custom host activation flags" in {
    val dc = DatacenterFactory.createDatacenter(datacenterConfig, hostConfig, cloudSim,
      Some(ArrayBuffer.fill(datacenterConfig.hosts)(false)))
    dc shouldBe a [Datacenter]
    dc.getHostList().forEach(host => {
      assert(host.isActive == false)
    })
    assert(checkCharacteristics(dc))
  }
  
  def checkCharacteristics(dc: Datacenter): Boolean = {
    dc.getCharacteristics.getArchitecture() == datacenterConfig.arch &&
    dc.getCharacteristics.getOs() == datacenterConfig.os &&
    dc.getCharacteristics.getVmm() == datacenterConfig.vmm &&
    dc.getCharacteristics.getCostPerSecond() == datacenterConfig.costPerSec &&
    dc.getCharacteristics.getCostPerMem() == datacenterConfig.costPerMem &&
    dc.getCharacteristics.getCostPerStorage() == datacenterConfig.costPerStorage &&
    dc.getCharacteristics.getCostPerBw() == datacenterConfig.costPerBw
  }
}
