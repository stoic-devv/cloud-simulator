import util.{CreateLogger, ObtainConfigReference}
import simulations.BasicCloudSimPlusExample
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

object Simulation:
  val logger = CreateLogger(classOf[Simulation])

  @main def runSimulation =
    logger.info("Constructing a cloud model...")
    BasicCloudSimPlusExample.Start()
    logger.info("Finished cloud simulation...")

class Simulation