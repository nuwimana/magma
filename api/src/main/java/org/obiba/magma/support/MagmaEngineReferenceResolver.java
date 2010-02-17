package org.obiba.magma.support;

import org.obiba.magma.Datasource;
import org.obiba.magma.MagmaEngine;
import org.obiba.magma.NoSuchDatasourceException;
import org.obiba.magma.NoSuchValueTableException;
import org.obiba.magma.ValueSet;
import org.obiba.magma.ValueTable;

/**
 * Contains common elements of all MagmaEngineReferenceResovler classes.
 */
public abstract class MagmaEngineReferenceResolver {

  private String datasourceName;

  private String tableName;

  private String variableName;

  /**
   * Resolves a reference to a {@code ValueTable} using the specified {@code ValueSet} as a context.
   */
  public ValueTable resolveTable(ValueSet context) throws NoSuchDatasourceException, NoSuchValueTableException {
    if(tableName == null) {
      if(context == null) {
        throw new IllegalStateException("cannot resolve table without a context.");
      }
      return context.getValueTable();
    }

    Datasource ds = null;
    if(datasourceName == null) {
      if(context == null) {
        throw new IllegalStateException("cannot resolve datasource without a context.");
      }
      ds = context.getValueTable().getDatasource();
    } else {
      ds = MagmaEngine.get().getDatasource(datasourceName);
    }
    return ds.getValueTable(tableName);
  }

  /**
   * Returns true if the specified {@code ValueSet} is within a different table than the referenced {@code ValueTable}
   * @param context
   * @return
   */
  public boolean isJoin(ValueSet context) {
    ValueTable table = resolveTable(context);
    return table != context.getValueTable();
  }

  public ValueSet join(ValueSet context) {
    ValueTable table = resolveTable(context);
    return table.getValueSet(context.getVariableEntity());
  }

  public String getDatasourceName() {
    return datasourceName;
  }

  public String getTableName() {
    return tableName;
  }

  String getVariableName() {
    return variableName;
  }

  void setDatasourceName(String datasourceName) {
    this.datasourceName = datasourceName;
  }

  void setTableName(String tableName) {
    this.tableName = tableName;
  }

  void setVariableName(String variableName) {
    this.variableName = variableName;
  }
}
