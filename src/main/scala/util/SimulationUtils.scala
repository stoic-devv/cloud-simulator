package util

import org.cloudbus.cloudsim.cloudlets.Cloudlet

import scala.collection.mutable.Buffer

class SimulationUtils
object SimulationUtils:
  
  def getSimulationCost(finishedCloudlets: Buffer[Cloudlet]): Double = {
    var simCost = 0.0
    finishedCloudlets.foreach(cloudlet => {
      simCost = simCost + cloudlet.getTotalCost()
    })
    simCost
  }
  
