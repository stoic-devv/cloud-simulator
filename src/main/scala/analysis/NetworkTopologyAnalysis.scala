package analysis

import config.ConfigStruct
import simulations.{GenericSimulation, NetworkTopologySimulation}
import util.CreateLogger

class NetworkTopologyAnalysis

object NetworkTopologyAnalysis:

  val logger = CreateLogger(classOf[NetworkTopologyAnalysis])

  def Start() =

    logger.info("==== Starting simulation with multiple datacenters in a network topology ===")
    var nts = new NetworkTopologySimulation(ConfigStruct("network-topology", "network-topology"), "topology.brite")
    nts.start()

    logger.info("==== Start simulation with single datacenter with resources same as all datacenters combines ====")
    var gs = new GenericSimulation(ConfigStruct("datacenter-solo", "datacenter-solo"))
    gs.start()


