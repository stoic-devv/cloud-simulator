package constants

/**
 * Enumeration of configuration file params
 **/
object CloudletConfigConstants extends Enumeration {
    type String
    val IDENTIFIER = "cloudlet"
    val LENGTH = "cloudlet.length"
    val SIZE = "cloudlet.size"
    val PES = "cloudlet.PEs"
    val UTILMODEL = "cloudlet.utilizationModel"
    val UTILRATIO = "cloudlet.utilizationRatio"
}

/**
 * Enumeration of Cloudlet schedulers
 **/
object CloudletSchedulerConstants extends Enumeration {
  type String
  val TIMESHARED = "time-shared"
  val SPACESHARED = "space-shared"
}

/**
 * Enumeration of Cloudlet utilization models
 **/
object CloudletUtilizationConstants extends Enumeration {
  type String
  val DYNAMIC = "dynamic"
  val FULL = "full"
  val STOCHASTIC = "stochastic"
}