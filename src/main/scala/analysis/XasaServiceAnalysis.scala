package analysis

import config.ConfigStruct
import simulations.XasaServiceSimulation
import util.CreateLogger

class XasaServiceAnalysis

object XasaServiceAnalysis:

  val logger = CreateLogger(classOf[XasaServiceAnalysis])

  def Start() = {

    // create config structs
    val saasConfStruct = new ConfigStruct("saas", "saas-default")
    val paasConfStruct = new ConfigStruct("paas", "paas-default")
    val iaasConfStruct = new ConfigStruct("iaas", "iaas-default")

    logger.info("==== Starting IaaS, PaaS, SaaS simulations ====")
    val xs = new XasaServiceSimulation(saasConfStruct, paasConfStruct, iaasConfStruct)
    xs.Start()
  }
