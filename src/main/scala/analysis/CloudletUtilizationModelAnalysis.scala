package analysis

import simulations.GenericSimulation
import config.ConfigStruct
import util.CreateLogger

class CloudletUtilizationModelAnalysis

object CloudletUtilizationModelAnalysis:

  val logger = CreateLogger(classOf[CloudletUtilizationModelAnalysis])

  def Start() =

    logger.info("==== ANALYZE CLOUDLET UTILIZATION MODELS ====")
    
    logger.info("==== FULL ====")
    var gs = new GenericSimulation(ConfigStruct("cloudlet-util-full", "cloudlet-util"))
    gs.start()

    logger.info("==== DYNAMIC ====")
    gs = new GenericSimulation(ConfigStruct("cloudlet-util-dynamic", "cloudlet-util"))
    gs.start()

    logger.info("==== STOCHASTIC ====")
    gs = new GenericSimulation(ConfigStruct("cloudlet-util-stochastic", "cloudlet-util"))
    gs.start()



