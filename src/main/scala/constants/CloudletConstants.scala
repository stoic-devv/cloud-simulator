package constants


object CloudletConfigConstants extends Enumeration {
    type String
    val IDENTIFIER = "cloudlet"
    val SIZE = "cloudlet.size"
    val PES = "cloudlet.PEs"
    val UTILRATIO = "cloudlet.utilizationRatio"
}

object CloudletSchedulerConstants extends Enumeration {
  type String
  val TIMESHARED = "time-shared"
}