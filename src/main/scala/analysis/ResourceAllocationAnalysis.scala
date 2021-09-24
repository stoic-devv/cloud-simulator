package analysis

import analysis.CloudletUtilizationRatioAnalysis.logger
import config.ConfigStruct
import simulations.GenericSimulation
import util.CreateLogger

class ResourceAllocationAnalysis

object ResourceAllocationAnalysis:

  val logger = CreateLogger(classOf[ResourceAllocationAnalysis])

  def Start() =

    logger.info("ANALYZING VM SCHEDULING POLICIES")

    /*logger.info("==== Worst-Fit Policy ====")
    var gs = new GenericSimulation(ConfigStruct("vm-allocation-worst-fit", "vm-allocation"))
    gs.start()

    logger.info("==== Uniform Random Policy ====")
    gs = new GenericSimulation(ConfigStruct("vm-allocation-uniform", "vm-allocation"))
    gs.start()

    logger.info("==== Gaussian Random Policy ====")
    gs = new GenericSimulation(ConfigStruct("vm-allocation-gaussian", "vm-allocation"))
    gs.start()

    logger.info("==== Round-Robin Policy ====")
    gs = new GenericSimulation(ConfigStruct("vm-allocation-round-robin", "vm-allocation"))
    gs.start()

    logger.info("==== Best-Fit Policy ====")
    gs = new GenericSimulation(ConfigStruct("vm-allocation-best-fit", "vm-allocation"))
    gs.start()
*/
    


