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

class XasaServiceSimulationSpec extends SimulationBaseSpec with MockitoSugar {

  val saasConfigStruct = new ConfigStruct(TestConstants.SAAS_CONFIG_FILE, TestConstants.SAAS_CONFIG_ENTRY)
  val paasConfigStruct = new ConfigStruct(TestConstants.PAAS_CONFIG_FILE, TestConstants.PAAS_CONFIG_ENTRY)
  val iaasConfigStruct = new ConfigStruct(TestConstants.IAAS_CONFIG_FILE, TestConstants.IAAS_CONFIG_ENTRY)
  val xaas = new XasaServiceSimulation(saasConfigStruct, paasConfigStruct, iaasConfigStruct)

  behavior of "X as a service simulation"

  it should "start the simulation with given configuration and print the analysis" in {
    xaas.Start()
  }
}
