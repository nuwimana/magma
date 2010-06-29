package org.obiba.magma.datasource.nul;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.obiba.magma.Value;
import org.obiba.magma.ValueTable;
import org.obiba.magma.ValueTableWriter;
import org.obiba.magma.Variable;
import org.obiba.magma.VariableEntity;
import org.obiba.magma.support.AbstractDatasource;

/**
 * A null {@code Datasource}. This datasource will return a {@code ValueTableWriter} that does nothing. Instances of
 * this class can be useful during testing or for performance debugging.
 */
public class NullDatasource extends AbstractDatasource {

  public NullDatasource(String name) {
    super(name, "null");
  }

  @Override
  protected Set<String> getValueTableNames() {
    return Collections.emptySet();
  }

  @Override
  protected ValueTable initialiseValueTable(String tableName) {
    return null;
  }

  public ValueTableWriter createWriter(String name, String entityType) {
    return new NullValueTableWriter();
  }

  private class NullValueTableWriter implements ValueTableWriter {

    @Override
    public ValueSetWriter writeValueSet(VariableEntity entity) {
      return new ValueSetWriter() {

        @Override
        public void close() throws IOException {
        }

        @Override
        public void writeValue(Variable variable, Value value) {
        }
      };
    }

    @Override
    public VariableWriter writeVariables() {
      return new VariableWriter() {

        @Override
        public void close() throws IOException {
        }

        @Override
        public void writeVariable(Variable variable) {
        }
      };
    }

    @Override
    public void close() throws IOException {
    }

  }

}