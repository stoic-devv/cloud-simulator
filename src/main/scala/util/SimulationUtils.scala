package util

import org.cloudbus.cloudsim.cloudlets.Cloudlet

import scala.collection.mutable.Buffer

class SimulationUtils
object SimulationUtils:
  
  val logger = CreateLogger(classOf[SimulationUtils])

  def checkConfig(configFile: String, configEntry: String) = {
    ObtainConfigReference(configFile, configEntry) match {
      case Some(value) => value
      case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
    }
  }
  
  def getSimulationCost(finishedCloudlets: Buffer[Cloudlet]): Double = {
    var simCost = 0.0
    finishedCloudlets.foreach(cloudlet => {
      simCost = simCost + cloudlet.getTotalCost()
    })
    simCost
  }
  
  def getAvgCostPerCloudlet(finishedCloudlets: Buffer[Cloudlet]): Double = {
    var totalCost = getSimulationCost(finishedCloudlets)
    totalCost/finishedCloudlets.length
  }

  def getAvgCompletionTime(finishedCloudlets: Buffer[Cloudlet]): Double = {
    var cpuTimeTotal = 0.0
    finishedCloudlets.foreach(cloudlet => {
      cpuTimeTotal = cpuTimeTotal + cloudlet.getActualCpuTime()
    })
    cpuTimeTotal/finishedCloudlets.length
  }
  
  def getAvgWaitTime(finishedCloudlets: Buffer[Cloudlet]): Double = {
    var waitTimeTotal = 0.0
    finishedCloudlets.foreach(cloudlet => {
      waitTimeTotal = waitTimeTotal + cloudlet.getWaitingTime()
    })
    waitTimeTotal/finishedCloudlets.length
  }
  
  def logAnalysisForCloudlets(finishedCloudlets: Buffer[Cloudlet]): Unit = {
    logger.info("Total cost for simulation: " + getSimulationCost(finishedCloudlets))
    logger.info("Average cost for per cloudlet: " + getAvgCostPerCloudlet(finishedCloudlets))
    logger.info("Average time to completion for cloudlets: " + getAvgCompletionTime(finishedCloudlets))
    logger.info("Average waiting time for cloudlets: " + getAvgWaitTime(finishedCloudlets))
  }
  
