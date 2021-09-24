package simulations

import config.{ConfigData, ConfigStruct}
import util.SimulationUtils.checkConfig
import util.{CreateLogger, ObtainConfigReference}

class XasaServiceSimulator(saasConfStruct: ConfigStruct, paasConfStruct: ConfigStruct,
                           iaasConfStruct: ConfigStruct){

  val logger = CreateLogger(classOf[XasaServiceSimulator])

  // checking config file correctness
  checkConfig(saasConfStruct.configFile, saasConfStruct.configEntry)
  checkConfig(saasConfStruct.configFile, saasConfStruct.configEntry)
  checkConfig(saasConfStruct.configFile, saasConfStruct.configEntry)


  logger.info("Getting configurations ")

  val config = new ConfigData(saasConfStruct.configFile, saasConfStruct.configEntry)

}

case class SaaSConf(var saasConfStruct: ConfigStruct)