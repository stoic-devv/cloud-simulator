package simulations

import config.{ConfigData, ConfigStruct}
import factory.CloudletFactory
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.core.CloudSim
import org.scalamock.scalatest.MockFactory
import util.SimulationUtils
//import org.scalamock.scalatest.GeneratedMockFactory
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.*
import testconstants.TestConstants

import collection.JavaConverters.*
import java.util

class GenericSimulationSpec extends SimulationBaseSpec with MockitoSugar {

  val configStruct = ConfigStruct(TestConstants.TEST_CONFIG_FILE,
    TestConstants.TEST_CONFIG_ENTRY)
  val config = new ConfigData(configStruct.configFile, configStruct.configEntry)
  val gc = new GenericSimulation(configStruct)

  behavior of "Generic Simulation"

  it should "start the simulation with given configuration and print the analysis" in {
    gc.start()
  }
}
