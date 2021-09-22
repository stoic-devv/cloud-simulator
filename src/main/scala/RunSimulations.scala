import util.{CreateLogger, ObtainConfigReference}
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory
import analysis.{CloudletUtilizationRatioAnalysis, CloudletUtilizationModelAnalysis}

object RunSimulations:
  val logger = CreateLogger(classOf[RunSimulations])

  @main def runSimulation =

    CloudletUtilizationRatioAnalysis.Start()
    CloudletUtilizationModelAnalysis.Start()

class RunSimulations