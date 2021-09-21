import util.{CreateLogger, ObtainConfigReference}
import simulations.BasicCloudSimPlusExample
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory
import simulations.analysis.CloudletUtilizationAnalysis
import simulations.scheduling.RoundRobinVmScheduler

object RunSimulations:
  val logger = CreateLogger(classOf[RunSimulations])

  @main def runSimulation =

    CloudletUtilizationAnalysis.Start()

class RunSimulations