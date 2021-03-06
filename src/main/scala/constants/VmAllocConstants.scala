package constants

/**
* Enumerations of different VM allocation policies used.
 **/
object VmAllocConstants extends Enumeration {
  type String = Value
  val BESTFIT = "best-fit"
  val WORSTFIT = "worst-fit"
  val ROUNDROBIN = "round-robin"
  val UNIFORMRAND = "uniform"
  val GAUSSIAN = "gaussian"
}
