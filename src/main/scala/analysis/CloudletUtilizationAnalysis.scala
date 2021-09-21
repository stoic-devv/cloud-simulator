package analysis

import constants.BasicSimulationConstants
import simulations.GenericSimulation
import util.CreateLogger

class CloudletUtilizationAnalysis

object CloudletUtilizationAnalysis:

  val logger = CreateLogger(classOf[CloudletUtilizationAnalysis])
  
  def Start() = 

    logger.info("ANALYZE WITH VARYING CLOUDLET UTILIZATION RATIO")
    logger.info("==== Cloudlet ratio 20% ====")
    var gs = new GenericSimulation("cloudlet-util-01", "cloudlet-util")
    gs.start()

    logger.info("==== Cloudlet ratio 40% ====")
    gs = new GenericSimulation("cloudlet-util-02", "cloudlet-util")
    gs.start()

    logger.info("==== Cloudlet ratio 60% ====")
    gs = new GenericSimulation("cloudlet-util-03", "cloudlet-util")
    gs.start()

    logger.info("==== Cloudlet ratio 75% ====")
    gs = new GenericSimulation("cloudlet-util-04", "cloudlet-util")
    gs.start()

    logger.info("==== Cloudlet ratio 100% ====")
    gs = new GenericSimulation("cloudlet-util-05", "cloudlet-util")
    gs.start()


