import util.{CreateLogger, ObtainConfigReference}
import simulations.BasicCloudSimPlusExample
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory
import simulations.scheduling.RoundRobinVmScheduler

object RunSimulations:
  val logger = CreateLogger(classOf[RunSimulations])

  @main def runSimulation =
    logger.info("Constructing a cloud model...")
    BasicCloudSimPlusExample.Start()
    logger.info("Finished cloud simulation...")

    logger.info("Constructing a cloud model...")
    RoundRobinVmScheduler.Start()
    logger.info("Finished cloud simulation...")

class RunSimulations