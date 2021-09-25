package factory

import config.CloudletConfig
import constants.CloudletUtilizationConstants
import org.cloudbus.cloudsim.utilizationmodels.{UtilizationModelDynamic, UtilizationModelFull, UtilizationModelStochastic}
import testconstants.TestConstants

class CloudletUtilizationFactorySpec extends FactoryBaseSpec {

  val cloudletConfig = CloudletConfig(TestConstants.TEST_CONFIG_FILE,
    TestConstants.TEST_CONFIG_ENTRY)

  behavior of "Cloudlet utilization model factory"

  it should "create a full utilization model cloudlet from configuration" in {
    CloudletUtilizationFactory.createCloudletUtilModel(cloudletConfig) shouldBe a [UtilizationModelFull]
  }

  it should "create a full utilization model" in {
    CloudletUtilizationFactory.createCloudletUtilModel(CloudletUtilizationConstants.FULL, 1.0) shouldBe a [UtilizationModelFull]
  }

  it should "create a full utilization model cloudlet" in {
    CloudletUtilizationFactory.createCloudletUtilModel(CloudletUtilizationConstants.STOCHASTIC, 1.0) shouldBe a [UtilizationModelStochastic]
  }

  it should "create a dynamic utilization model with given utilization ratio cloudlet" in {
    val utilRatio = 0.50
    val cloudlet = CloudletUtilizationFactory.createCloudletUtilModel(CloudletUtilizationConstants.DYNAMIC, utilRatio)
    cloudlet shouldBe a [UtilizationModelDynamic]
    assert(cloudlet.getUtilization() == utilRatio)
  }

  it should "create a full utilization model cloudlet by default" in {
    CloudletUtilizationFactory.createCloudletUtilModel("", 1.0) shouldBe a [UtilizationModelFull]
  }
}
