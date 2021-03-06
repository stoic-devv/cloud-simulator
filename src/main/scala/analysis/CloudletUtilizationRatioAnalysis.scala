package analysis

import config.ConfigStruct
import simulations.GenericSimulation
import util.CreateLogger

class CloudletUtilizationRatioAnalysis

object CloudletUtilizationRatioAnalysis:

  val logger = CreateLogger(classOf[CloudletUtilizationRatioAnalysis])
  
  def Start() = 
    
    logger.info("==== ANALYZE WITH VARYING CLOUDLET UTILIZATION RATIO, TIME-SHARED VM SCHEDULER AND SPACE-SHARED CLOUDLET SCHEDULER ====")
    logger.info("==== Cloudlet ratio 20% ====")
    var gs = new GenericSimulation(ConfigStruct("cloudlet-util-01", "cloudlet-util"))
    gs.start()

    logger.info("==== Cloudlet ratio 40% ====")
    gs = new GenericSimulation(ConfigStruct("cloudlet-util-02", "cloudlet-util"))
    gs.start()

    logger.info("==== Cloudlet ratio 60% ====")
    gs = new GenericSimulation(ConfigStruct("cloudlet-util-03", "cloudlet-util"))
    gs.start()

    logger.info("==== Cloudlet ratio 75% ====")
    gs = new GenericSimulation(ConfigStruct("cloudlet-util-04", "cloudlet-util"))
    gs.start()

    logger.info("==== Cloudlet ratio 100% ====")
    gs = new GenericSimulation(ConfigStruct("cloudlet-util-05", "cloudlet-util"))
    gs.start()

    logger.info("ANALYZE WITH VARYING CLOUDLET UTILIZATION RATIO")
    logger.info("==== Cloudlet ratio 20% ====")
    gs = new GenericSimulation(ConfigStruct("cloudlet-util-01-time-space", "cloudlet-util"))
    gs.start()

    logger.info("==== Cloudlet ratio 40% ====")
    gs = new GenericSimulation(ConfigStruct("cloudlet-util-02-time-space", "cloudlet-util"))
    gs.start()

    logger.info("==== Cloudlet ratio 60% ====")
    gs = new GenericSimulation(ConfigStruct("cloudlet-util-03-time-space", "cloudlet-util"))
    gs.start()

    logger.info("==== Cloudlet ratio 75% ====")
    gs = new GenericSimulation(ConfigStruct("cloudlet-util-04-time-space", "cloudlet-util"))
    gs.start()

    logger.info("==== Cloudlet ratio 100% ====")
    gs = new GenericSimulation(ConfigStruct("cloudlet-util-05-time-space", "cloudlet-util"))
    gs.start()