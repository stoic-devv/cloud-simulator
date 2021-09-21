package constants

object BasicSimulationConstants extends Enumeration {
  type String
  val CONFIG_FILE = "basic-simulation"
  val CONFIG_ENTRY = "basic-simulator"
}

object RoundRobinVmAllocationConstants extends Enumeration {
  type String
  val CONFIG_FILE = "round-robin"
  val CONFIG_ENTRY = "round-robin"
}
