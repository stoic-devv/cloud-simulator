package constants

object VmSchedulerConstants extends Enumeration {
  type String = Value
  val SPACESHARED = "space-shared"
  val ROUNDROBIN  = "round-robin"
}
