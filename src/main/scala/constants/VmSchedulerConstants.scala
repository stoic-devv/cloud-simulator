package constants

/**
 * Enumeration of different VM scheduling policies used
 **/
object VmSchedulerConstants extends Enumeration {
  type String = Value
  val SPACESHARED = "space-shared"
  val TIMESHARED = "time-shared"
}
