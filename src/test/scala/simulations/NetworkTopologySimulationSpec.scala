package simulations

import config.{ConfigData, ConfigStruct}
import factory.CloudletFactory
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.core.CloudSim
import org.scalamock.scalatest.MockFactory
import util.SimulationUtils
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.*
import testconstants.TestConstants

import collection.JavaConverters.*
import java.util

class NetworkTopologySimulationSpec extends SimulationBaseSpec with MockitoSugar {

  val configStruct = ConfigStruct(TestConstants.NETWORK_CONFIG_FILE,
    TestConstants.NETWORK_CONFIG_ENTRY)
  val config = new ConfigData(configStruct.configFile, configStruct.configEntry)
  val nts = new NetworkTopologySimulation(configStruct, TestConstants.TOPOLOGY_FILE)

  behavior of "Network topology simulation"

  it should "start the simulation with given configuration and print the analysis" in {
    nts.start()
  }
}

