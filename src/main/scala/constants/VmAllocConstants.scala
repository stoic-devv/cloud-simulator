package constants

object VmAllocConstants extends Enumeration {
  type String = Value
  val BESTFIT = "best-fit"
  val ROUNDROBIN = "round-robin"
}
