package org.obiba.magma.js;

import org.junit.After;
import org.junit.Before;
import org.mozilla.javascript.Context;
import org.obiba.magma.Value;

public abstract class AbstractScriptableValueTest extends AbstractJsTest {

  @Before
  public void enterContext() {
    Context.enter();
  }

  @After
  public void exitContext() {
    Context.exit();
  }

  public ScriptableValue newValue(Value value) {
    return new ScriptableValue(getSharedScope(), value);
  }

}
