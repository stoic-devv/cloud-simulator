import util.{CreateLogger, ObtainConfigReference}
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory
import analysis.{CloudletUtilizationModelAnalysis, CloudletUtilizationRatioAnalysis, NetworkTopologyAnalysis, ResourceAllocationAnalysis, XasaServiceAnalysis}

object RunSimulations:
  val logger = CreateLogger(classOf[RunSimulations])

  @main def runSimulation =

    CloudletUtilizationRatioAnalysis.Start()
    CloudletUtilizationModelAnalysis.Start()
    //ResourceAllocationAnalysis.Start()
    XasaServiceAnalysis.Start()
    NetworkTopologyAnalysis.Start()

class RunSimulations