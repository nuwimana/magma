package org.obiba.magma.type;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.Assert;

import org.junit.Test;
import org.obiba.magma.Value;
import org.obiba.magma.ValueType;

public class DateTimeTypeTest extends BaseValueTypeTest {

  @Override
  ValueType getValueType() {
    return DateTimeType.get();
  }

  @Override
  Object getObjectForType() {
    return new Date();
  }

  @Test
  public void testValueOfSqlDateInstance() {
    DateTimeType dt = DateTimeType.get();

    Assert.assertTrue(dt.acceptsJavaClass(java.sql.Date.class));

    Date dateValue = new Date();
    Value value = dt.valueOf(new java.sql.Date(dateValue.getTime()));
    Assert.assertEquals(dateValue, value.getValue());

    // Make sure the type was normalized
    Assert.assertTrue(value.getValue().getClass().equals(dt.getJavaClass()));
  }

  @Test
  public void testValueOfSqlTimestampInstance() {
    DateTimeType dt = DateTimeType.get();

    Assert.assertTrue(dt.acceptsJavaClass(Timestamp.class));

    Date dateValue = new Date();
    Value value = dt.valueOf(new Timestamp(dateValue.getTime()));
    Assert.assertEquals(dateValue, value.getValue());

    // Make sure the type was normalized
    Assert.assertTrue(value.getValue().getClass().equals(dt.getJavaClass()));
  }

  @Test
  public void testValueOfCalendarInstance() {
    DateTimeType dt = DateTimeType.get();

    Assert.assertTrue(dt.acceptsJavaClass(Calendar.class));
    Assert.assertTrue(dt.acceptsJavaClass(GregorianCalendar.class));

    Calendar calendar = GregorianCalendar.getInstance();
    Date dateValue = calendar.getTime();
    Value value = dt.valueOf(calendar);
    Assert.assertEquals(dateValue, value.getValue());
  }

  @Test
  public void test_valueOfISODateFormatString() {
    assertValueOfUsingDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
  }

  @Test
  public void test_valueOfISODateFormatNoMillisecondsString() {
    // seconds precision
    assertValueOfUsingDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", 1000);
  }

  @Test
  public void test_valueOfISODateFormatNoSecondsString() {
    // minutes precision
    assertValueOfUsingDateFormat("yyyy-MM-dd'T'HH:mmZ", 1000 * 60);
  }

  @Test
  public void test_valueOfIncorrectISODateFormatString() {
    assertValueOfUsingDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSzzz");
  }

  private void assertValueOfUsingDateFormat(String dateFormat) {
    assertValueOfUsingDateFormat(dateFormat, 0);
  }

  private void assertValueOfUsingDateFormat(String dateFormat, int precision) {
    Date dateValue = new Date();
    Value value = getValueType().valueOf(new SimpleDateFormat(dateFormat).format(dateValue));
    if(precision == 0) {
      Assert.assertEquals(dateValue, value.getValue());
    } else {
      // asserts that times are equivalent within "precision" from each other
      Assert.assertTrue(Math.abs(dateValue.getTime() - ((Date) value.getValue()).getTime()) < precision);
    }
  }
}
