package org.obiba.meta.xstream.mapper;

import org.obiba.meta.Attribute;
import org.obiba.meta.Category;
import org.obiba.meta.Variable;

import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class MetaMapper extends MapperWrapper {

  public MetaMapper(Mapper wrapped) {
    super(wrapped);
  }

  @Override
  @SuppressWarnings("unchecked")
  public String serializedClass(Class type) {
    if(Variable.class.isAssignableFrom(type)) {
      return "variable";
    }
    if(Attribute.class.isAssignableFrom(type)) {
      return "attribute";
    }
    if(Category.class.isAssignableFrom(type)) {
      return "category";
    }
    return super.serializedClass(type);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Class realClass(String elementName) {
    if("variable".equals(elementName)) {
      return Variable.class;
    }
    if("attribute".equals(elementName)) {
      return Attribute.class;
    }
    if("category".equals(elementName)) {
      return Category.class;
    }
    return super.realClass(elementName);
  }

}