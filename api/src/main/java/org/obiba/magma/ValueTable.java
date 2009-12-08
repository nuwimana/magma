package org.obiba.magma;


public interface ValueTable {

  public String getName();

  public Datasource getDatasource();

  public String getEntityType();

  public boolean isForEntityType(String entityType);

  public boolean hasValueSet(VariableEntity entity);

  public Iterable<ValueSet> getValueSets();

  public ValueSet getValueSet(VariableEntity entity) throws NoSuchValueSetException;

  public Iterable<Variable> getVariables();

  public Variable getVariable(String name) throws NoSuchVariableException;

  public Value getValue(Variable variable, ValueSet valueSet);

  public VariableValueSource getVariableValueSource(String name) throws NoSuchVariableException;

}