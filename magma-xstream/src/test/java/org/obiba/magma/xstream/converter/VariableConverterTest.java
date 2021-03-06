package org.obiba.magma.xstream.converter;

import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.obiba.magma.Attribute;
import org.obiba.magma.Category;
import org.obiba.magma.Variable;
import org.obiba.magma.test.AbstractMagmaTest;
import org.obiba.magma.type.TextType;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.thoughtworks.xstream.XStream;

public class VariableConverterTest extends AbstractMagmaTest {

  @Test
  public void testBasicVariable() {
    XStream xstream = new XStream();
    xstream.registerConverter(new VariableConverter(xstream.getMapper()));

    Variable v = newVariable().build();
    String xml = xstream.toXML(v);
    Variable unmarshalled = (Variable) xstream.fromXML(xml);

    Assert.assertEquals(v.getName(), unmarshalled.getName());
    Assert.assertEquals(v.getValueType(), unmarshalled.getValueType());
    Assert.assertEquals(v.getEntityType(), unmarshalled.getEntityType());
  }

  @Test
  public void testVariableWithAttributes() {
    XStream xstream = new XStream();
    xstream.registerConverter(new VariableConverter(xstream.getMapper()));
    xstream.registerConverter(new AttributeConverter());

    Variable v = newVariable()//
        .addAttribute("firstAttribute", "firstValue")//
        .addAttribute("secondAttribute", "secondValue", Locale.ENGLISH)//
        .addAttribute(Attribute.Builder.newAttribute("namespaced").withNamespace("ns1").withValue("ns1").build())//
        .addAttribute(Attribute.Builder.newAttribute("namespaced").withNamespace("ns2").withLocale(Locale.ENGLISH)
            .withValue("ns2").build())//
        .build();

    String xml = xstream.toXML(v);
    Variable unmarshalled = (Variable) xstream.fromXML(xml);
    Assert.assertTrue(unmarshalled.hasAttribute("firstAttribute"));
    Assert.assertEquals("firstValue", unmarshalled.getAttribute("firstAttribute").getValue().toString());

    Assert.assertTrue(unmarshalled.hasAttribute("secondAttribute"));
    Assert.assertTrue(unmarshalled.getAttribute("secondAttribute").isLocalised());
    Assert.assertEquals("secondValue",
        unmarshalled.getAttribute("secondAttribute", Locale.ENGLISH).getValue().toString());

    Assert.assertTrue(unmarshalled.hasAttribute("ns1", "namespaced"));
    Assert.assertTrue(unmarshalled.hasAttribute("ns1", "namespaced"));
    Assert.assertEquals("ns1", unmarshalled.getAttribute("ns1", "namespaced").getValue().toString());
  }

  @Test
  public void testVariableWithCategories() {
    XStream xstream = new XStream();
    xstream.registerConverter(new VariableConverter(xstream.getMapper()));
    xstream.registerConverter(new AttributeConverter());
    xstream.registerConverter(new CategoryConverter(xstream.getMapper()));

    final Set<String> names = ImmutableSet.of("YES", "NO", "DNK", "PNA");

    Variable v = newVariable().addCategories("YES", "NO")
        .addCategory(Category.Builder.newCategory("DNK").withCode("8888").build()).addCategory(
            Category.Builder.newCategory("PNA").withCode("9999").addAttribute(
                Attribute.Builder.newAttribute("label").withValue(Locale.ENGLISH, "Prefer not to answer").build())
                .build()).build();
    String xml = xstream.toXML(v);
    Variable unmarshalled = (Variable) xstream.fromXML(xml);
    Assert.assertNotNull(unmarshalled.getCategories());

    Assert.assertTrue(Iterables.any(unmarshalled.getCategories(), new Predicate<Category>() {
      @Override
      public boolean apply(Category input) {
        return names.contains(input.getName());
      }
    }));
  }

  protected Variable.Builder newVariable() {
    return Variable.Builder.newVariable("Test.Variable", TextType.get(), "Participant");
  }

}
