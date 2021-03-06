package org.obiba.magma.filter;

import org.obiba.magma.Initialisable;
import org.obiba.magma.Value;
import org.obiba.magma.ValueSet;
import org.obiba.magma.js.JavascriptValueSource;
import org.obiba.magma.type.BooleanType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("script")
public class JavaScriptFilter extends AbstractFilter<ValueSet> implements Initialisable {

  private static final String SCRIPT_NAME = "JAVASCRIPT_FILTER_SCRIPT";

  private final String javascript;

  @XStreamOmitField
  private JavascriptValueSource javascriptSource;

  @XStreamOmitField
  private boolean initialised;

  JavaScriptFilter(String javascript) {
    this.javascript = javascript;
    initialise();
  }

  @Override
  public synchronized void initialise() {
    if(initialised) return;
    validateArguments(javascript);
    javascriptSource = new JavascriptValueSource(BooleanType.get(), javascript);
    javascriptSource.setScriptName(SCRIPT_NAME);
    javascriptSource.initialise();
    initialised = true;
  }

  private void validateArguments(String javascript) {
    if(javascript == null) throw new IllegalArgumentException("The argument [javascript] cannot be null.");
  }

  @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "NP_BOOLEAN_RETURN_NULL",
      justification = "Clients expect null as a valid value. It's possible for JavaScript to return null.")
  @Override
  protected Boolean runFilter(ValueSet item) {
    initialise();
    Value value = javascriptSource.getValue(item);
    // JavaScript can return null.
    if(value.equals(BooleanType.get().nullValue())) return null;
    return (Boolean) value.getValue();
  }

  public static class Builder extends AbstractFilter.Builder {

    private String javascript;

    public static Builder newFilter() {
      return new Builder();
    }

    public Builder javascript(String javascript) {
      this.javascript = javascript;
      return this;
    }

    public JavaScriptFilter build() {
      JavaScriptFilter filter = new JavaScriptFilter(javascript);
      filter.setType(type);
      return filter;
    }

    @Override
    public Builder exclude() {
      super.exclude();
      return this;
    }

    @Override
    public Builder include() {
      super.include();
      return this;
    }
  }

  @Override
  public String toString() {
    return SCRIPT_NAME + "[" + javascript + "]";
  }
}
