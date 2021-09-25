package config

/**
 * data structure to hold config data for datacenter, hosts, vms and cloudlets.
 **/
class ConfigData(configFile: String, configEntry: String) {

  val datacenterConfig = DatacenterConfig(configFile, configEntry)
  val hostConfig = HostConfig(configFile, configEntry)
  val vmConfig = VmConfig(configFile, configEntry)
  val cloudletConfig = CloudletConfig(configFile, configEntry)
}
