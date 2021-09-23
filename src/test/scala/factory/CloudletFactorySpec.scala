package factory

import config.{CloudletConfig, DatacenterConfig}
import org.cloudbus.cloudsim.cloudlets.CloudletSimple
import org.scalatest.BeforeAndAfter
import testconstants.TestConstants

class CloudletFactorySpec extends FactoryBaseSpec {

  val datacenterConfig = DatacenterConfig(TestConstants.TEST_CONFIG_FILE,
    TestConstants.TEST_CONFIG_ENTRY)
  val cloudletConfig = CloudletConfig(TestConstants.TEST_CONFIG_FILE,
    TestConstants.TEST_CONFIG_ENTRY)

  it should "create cloudlet from configuration" in {
    CloudletFactory.createCloudlet(cloudletConfig) shouldBe a [CloudletSimple]
  }

  it should "create cloudlets from configuration" in {
    val cloudletBuffer = CloudletFactory.createCloudlets(cloudletConfig, datacenterConfig)
    assert(cloudletBuffer.length == datacenterConfig.cloudlets)
    cloudletBuffer.foreach(cloudlet => {
      cloudlet shouldBe a [CloudletSimple]
    })
  }
}
