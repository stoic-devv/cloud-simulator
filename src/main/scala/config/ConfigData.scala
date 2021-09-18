package config

case class ConfigData(configFile: String, configEntry: String) {

  val datacenterConfig = DatacenterConfig(configFile, configEntry)
  val hostConfig = HostConfig(configFile, configEntry)
  val vmConfig = VmConfig(configFile, configEntry)
  val cloudletConfig = CloudletConfig(configFile, configEntry)
}
