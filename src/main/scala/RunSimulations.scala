import util.{CreateLogger, ObtainConfigReference}
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory
import analysis.{CloudletUtilizationModelAnalysis, CloudletUtilizationRatioAnalysis, NetworkTopologyAnalysis, ResourceAllocationAnalysis, XasaServiceAnalysis}

object RunSimulations:
  val logger = CreateLogger(classOf[RunSimulations])

  /**
   * Runs simulations. Entry point of this project.
   **/
  @main def runSimulation =

    logger.info("==== STARTING ANALYSIS FOR CLOUD COMPUTATION SIMULATIONS ====")
    CloudletUtilizationRatioAnalysis.Start()
    CloudletUtilizationModelAnalysis.Start()
    //ResourceAllocationAnalysis.Start()
    XasaServiceAnalysis.Start()
    NetworkTopologyAnalysis.Start()
    logger.info("==== ANALYSIS COMPLETED SUCCESSFULLY ====")
    

class RunSimulations