package simulations.provisioning

import util.{CreateLogger, ObtainConfigReference}

object RoundRobinVmProvisioning {

  val config = ObtainConfigReference("basic-simulation", "cloudSimulator") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }

  val logger = CreateLogger(classOf[RoundRobinVmProvisioning])

}
class RoundRobinVmProvisioning
