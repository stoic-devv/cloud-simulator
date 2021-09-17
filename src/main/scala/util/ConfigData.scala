package util

import config.{CloudletConfig, DatacenterConfig, HostConfig, VMConfig}

case class ConfigData(configFile: String, configEntry: String) {
  
  val datacenterConfig =  DatacenterConfig(configFile, configEntry)
  val hostConfig = HostConfig(configFile, configEntry)
  val vmConfig = VMConfig(configFile, configEntry)
  val cloudletConfig = CloudletConfig(configFile, configEntry)    
}
