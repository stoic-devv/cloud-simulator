package constants


object CloudletConfigConstants extends Enumeration {
    type String
    val IDENTIFIER = "cloudlet"
    val LENGTH = "cloudlet.length"
    val SIZE = "cloudlet.size"
    val PES = "cloudlet.PEs"
    val UTILMODEL = "cloudlet.utilizationModel"
    val UTILRATIO = "cloudlet.utilizationRatio"
}

object CloudletSchedulerConstants extends Enumeration {
  type String
  val TIMESHARED = "time-shared"
  val SPACESHARED = "space-shared"
}

object CloudletUtilizationConstants extends Enumeration {
  type String
  val DYNAMIC = "dynamic"
  val FULL = "full"
  val STOCHASTIC = "stochastic"
}