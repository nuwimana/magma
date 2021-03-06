package org.obiba.magma.datasource.limesurvey;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.obiba.magma.Attribute;
import org.obiba.magma.Category;
import org.obiba.magma.Value;
import org.obiba.magma.ValueTable;
import org.obiba.magma.Variable;
import org.obiba.magma.VariableEntity;
import org.obiba.magma.datasource.limesurvey.LimesurveyValueTable.LimesurveyQuestionVariableValueSource;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class DisplayHelper {
  public static void display(LimesurveyDatasource datasource) {
    int nbVariable = 0;
    for(final ValueTable table : datasource.getValueTables()) {
      nbVariable += DisplayHelper.display((LimesurveyValueTable) table);
    }
    System.out.println(nbVariable);
    System.out.println(datasource.getValueTables().size());
  }

  public static int display(final LimesurveyValueTable table) {
    List<LimesurveyQuestionVariableValueSource> variables = Lists.newArrayList(Lists
        .transform(Lists.newArrayList(table.getVariables()),
            new Function<Variable, LimesurveyQuestionVariableValueSource>() {

              @Override
              public LimesurveyQuestionVariableValueSource apply(Variable input) {
                return (LimesurveyQuestionVariableValueSource) table.getVariableValueSource(input.getName());
              }
            }));
    Collections.sort(variables, new Comparator<LimesurveyQuestionVariableValueSource>() {

      @Override
      public int compare(LimesurveyQuestionVariableValueSource o1, LimesurveyQuestionVariableValueSource o2) {
        return o1.getVariable().getName().compareTo(o2.getVariable().getName());
      }
    });
    TreeSet<VariableEntity> variableEntities = Sets.newTreeSet(table.getVariableEntities());
    for(LimesurveyQuestionVariableValueSource lvv : variables) {
      Variable v = lvv.getVariable();
      System.out.print("Var '" + v.getName() + "' " + v.getValueType().getName() + " ");
      for(Attribute attr : v.getAttributes()) {
        System.out.print(
            attr.getName() + (attr.getLocale() != null ? attr.getLocale().toString() : "") + "=" + attr.getValue() +
                ", ");
      }
      System.out.println();
      for(Category c : v.getCategories()) {
        System.out.print("    Cat '" + c.getName() + "' ");
        for(Attribute attr : c.getAttributes()) {
          System.out.print(" " + attr.getName() + (attr.getLocale() != null ? attr.getLocale().toString() : "") + "=" +
              attr.getValue() + ", ");
        }
        System.out.println();
      }

      Iterable<Value> values = lvv.getValues(variableEntities);

      for(Value value : values) {
        System.out.println(value);
      }
    }
    return variables.size();
  }
}
