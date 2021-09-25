package util

import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.core.Identifiable
import org.cloudsimplus.builders.tables.{CloudletsTableBuilder, Table, TableBuilderAbstract, TableColumn}

import scala.collection.mutable.Buffer
import scala.jdk.CollectionConverters.*

/**
 * Adds columns like cost, runtime, efficiency  to the default data representation.
 **/
class CloudletsTableDecorator(cloudlets: Buffer[Cloudlet])
extends CloudletsTableBuilder(cloudlets.asJava) {

  /**
   * Adds column for cost of running for each cloudlet
   **/
  def addCostColumn(): Unit = {
    addColumnDataFunction(getTable.addColumn("Cost ", "USD "), (cloudlet: Cloudlet) => cloudlet.getTotalCost)
  }
}