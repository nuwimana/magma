package org.obiba.magma.js.methods;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.mozilla.javascript.Context;
import org.obiba.magma.Value;
import org.obiba.magma.ValueType;
import org.obiba.magma.js.AbstractJsTest;
import org.obiba.magma.js.ScriptableValue;
import org.obiba.magma.type.BooleanType;
import org.obiba.magma.type.DateTimeType;
import org.obiba.magma.type.DateType;
import org.obiba.magma.type.IntegerType;
import org.obiba.magma.type.TextType;

public class DateTimeMethodTest extends AbstractJsTest {

  @Test
  public void test_year_acceptsDateTime() {
    testCalendarFieldMethod("year()", Calendar.YEAR, DateTimeType.get());
  }

  @Test
  public void test_year_acceptsDate() {
    testCalendarFieldMethod("year()", Calendar.YEAR, DateType.get());
  }

  @Test
  public void test_month_acceptsDateTime() {
    testCalendarFieldMethod("month()", Calendar.MONTH, DateTimeType.get());
  }

  @Test
  public void test_month_acceptsDate() {
    testCalendarFieldMethod("month()", Calendar.MONTH, DateType.get());
  }

  @Test
  public void test_dayOfMonth_acceptsDateTime() {
    testCalendarFieldMethod("dayOfMonth()", Calendar.DAY_OF_MONTH, DateTimeType.get());
  }

  @Test
  public void test_dayOfMonth_acceptsDate() {
    testCalendarFieldMethod("dayOfMonth()", Calendar.DAY_OF_MONTH, DateType.get());
  }

  @Test
  public void test_dayOfWeek_acceptsDateTime() {
    testCalendarFieldMethod("dayOfWeek()", Calendar.DAY_OF_WEEK, DateTimeType.get());
  }

  @Test
  public void test_dayOfWeek_acceptsDate() {
    testCalendarFieldMethod("dayOfWeek()", Calendar.DAY_OF_WEEK, DateType.get());
  }

  @Test
  public void test_dayOfYear_acceptsDateTime() {
    testCalendarFieldMethod("dayOfYear()", Calendar.DAY_OF_YEAR, DateTimeType.get());
  }

  @Test
  public void test_dayOfYear_acceptsDate() {
    testCalendarFieldMethod("dayOfYear()", Calendar.DAY_OF_YEAR, DateType.get());
  }

  @Test
  public void test_weekend() {
    testWeekendOrWeekdayMethod("weekend()", Calendar.SATURDAY, true);
    testWeekendOrWeekdayMethod("weekend()", Calendar.SUNDAY, true);

    testWeekendOrWeekdayMethod("weekend()", Calendar.MONDAY, false);
    testWeekendOrWeekdayMethod("weekend()", Calendar.TUESDAY, false);
    testWeekendOrWeekdayMethod("weekend()", Calendar.WEDNESDAY, false);
    testWeekendOrWeekdayMethod("weekend()", Calendar.THURSDAY, false);
    testWeekendOrWeekdayMethod("weekend()", Calendar.FRIDAY, false);
  }

  @Test
  public void test_weekend_acceptsNull() {
    ScriptableValue value = evaluate("weekend()", DateType.get().nullValue());
    Assert.assertNotNull(value);
    Assert.assertThat(value.getValue(), is(BooleanType.get().nullValue()));

    value = evaluate("weekend()", DateTimeType.get().nullValue());
    Assert.assertNotNull(value);
    Assert.assertThat(value.getValue(), is(BooleanType.get().nullValue()));
  }

  @Test
  public void test_weekday() {
    testWeekendOrWeekdayMethod("weekday()", Calendar.SATURDAY, false);
    testWeekendOrWeekdayMethod("weekday()", Calendar.SUNDAY, false);

    testWeekendOrWeekdayMethod("weekday()", Calendar.MONDAY, true);
    testWeekendOrWeekdayMethod("weekday()", Calendar.TUESDAY, true);
    testWeekendOrWeekdayMethod("weekday()", Calendar.WEDNESDAY, true);
    testWeekendOrWeekdayMethod("weekday()", Calendar.THURSDAY, true);
    testWeekendOrWeekdayMethod("weekday()", Calendar.FRIDAY, true);
  }

  @Test
  public void test_weekday_acceptsNull() {
    ScriptableValue value = evaluate("weekday()", DateType.get().nullValue());
    Assert.assertNotNull(value);
    Assert.assertThat(value.getValue(), is(BooleanType.get().nullValue()));

    value = evaluate("weekday()", DateTimeType.get().nullValue());
    Assert.assertNotNull(value);
    Assert.assertThat(value.getValue(), is(BooleanType.get().nullValue()));
  }

  @Test
  public void test_weekOfYear_acceptsDateTime() {
    testCalendarFieldMethod("weekOfYear()", Calendar.WEEK_OF_YEAR, DateTimeType.get());
  }

  @Test
  public void test_weekOfYear_acceptsDate() {
    testCalendarFieldMethod("weekOfYear()", Calendar.WEEK_OF_YEAR, DateType.get());
  }

  @Test
  public void test_weekOfMonth_acceptsDateTime() {
    testCalendarFieldMethod("weekOfMonth()", Calendar.WEEK_OF_MONTH, DateTimeType.get());
  }

  @Test
  public void test_weekOfMonth_acceptsDate() {
    testCalendarFieldMethod("weekOfMonth()", Calendar.WEEK_OF_MONTH, DateType.get());
  }

  @Test
  public void test_hourOfDay_acceptsDateTime() {
    testCalendarFieldMethod("hourOfDay()", Calendar.HOUR_OF_DAY, DateTimeType.get());
  }

  @Test
  public void test_hour_acceptsDateTime() {
    testCalendarFieldMethod("hour()", Calendar.HOUR, DateTimeType.get());
  }

  @Test
  public void test_minute_acceptsDateTime() {
    testCalendarFieldMethod("minute()", Calendar.MINUTE, DateTimeType.get());
  }

  @Test
  public void test_second_acceptsDateTime() {
    testCalendarFieldMethod("second()", Calendar.SECOND, DateTimeType.get());
  }

  @Test
  public void test_millisecond_acceptsDateTime() {
    testCalendarFieldMethod("millisecond()", Calendar.MILLISECOND, DateTimeType.get());
  }

  @Test
  public void test_format_acceptsDateTime() {
    testFormatMethod("dd/MM/yyyy HH:mm", DateTimeType.get());
  }

  @Test
  public void test_format_acceptsDate() {
    testFormatMethod("dd/MM/yyyy", DateType.get());
  }

  @Test
  public void testAfterNullArgumentReturnsNull() throws Exception {
    ScriptableValue now = newValue(DateTimeType.get().valueOf(new Date()));
    ScriptableValue nullDate = newValue(DateTimeType.get().nullValue());
    ScriptableValue result = (ScriptableValue) DateTimeMethods.after(Context.getCurrentContext(), now, new ScriptableValue[] { nullDate }, null);
    Assert.assertNotNull(result);
    Assert.assertEquals(BooleanType.get().nullValue(), result.getValue());
  }

  @Test
  public void testAfterNullCallerReturnsNull() throws Exception {
    ScriptableValue now = newValue(DateTimeType.get().valueOf(new Date()));
    ScriptableValue nullDate = newValue(DateTimeType.get().nullValue());
    ScriptableValue result = (ScriptableValue) DateTimeMethods.after(Context.getCurrentContext(), nullDate, new ScriptableValue[] { now }, null);
    Assert.assertNotNull(result);
    Assert.assertEquals(BooleanType.get().nullValue(), result.getValue());
  }

  @Test
  public void test_after() throws Exception {
    ScriptableValue value = evaluate("after(newValue('2011-11-01', 'date'))", DateType.get().valueOf(new Date()));
    Assert.assertNotNull(value);
    Assert.assertEquals(BooleanType.get().trueValue(), value.getValue());

    value = evaluate("after(newValue('2011-11-01', 'date'), newValue('2011-11-01T10:00:00-0000', 'datetime'))", DateType.get().valueOf(new Date()));
    Assert.assertNotNull(value);
    Assert.assertEquals(BooleanType.get().trueValue(), value.getValue());
  }

  private Value makeDayOfWeek(ValueType valueType, int dayOfWeek) {
    Calendar calendar = Calendar.getInstance();
    while(calendar.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
      calendar.add(Calendar.DAY_OF_WEEK, 1);
    }
    return valueType.valueOf(calendar);
  }

  private void testWeekendOrWeekdayMethod(String script, int dayOfWeek, boolean expected) {
    ScriptableValue value = evaluate(script, makeDayOfWeek(DateType.get(), dayOfWeek));
    Assert.assertNotNull(value);
    Assert.assertThat(value.getValue(), is(BooleanType.get().valueOf(expected)));

    value = evaluate(script, makeDayOfWeek(DateTimeType.get(), dayOfWeek));
    Assert.assertNotNull(value);
    Assert.assertThat(value.getValue(), is(BooleanType.get().valueOf(expected)));
  }

  /**
   * Executes {@code script} with {@code testValue} as its scope and asserts that the returned value is
   * 
   * @param script
   * @param field
   * @param testValue
   */
  private void testCalendarFieldMethod(String script, int field, ValueType testType) {
    Calendar testValue = Calendar.getInstance();
    ScriptableValue result = evaluate(script, testType.valueOf(testValue));
    assertIntegerResult(result, testValue.get(field));
  }

  private void assertIntegerResult(ScriptableValue result, int expectedValue) {
    assertThat(result, notNullValue());
    assertThat(result.getValue(), notNullValue());
    assertThat(result.getValueType(), is((ValueType) IntegerType.get()));
    assertThat((Long) result.getValue().getValue(), is((long) expectedValue));
  }

  private void testFormatMethod(String pattern, ValueType testType) {
    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat(pattern);
    String expected = format.format(now);

    Value result = evaluate("format('" + pattern + "')", testType.valueOf(now)).getValue();
    Assert.assertNotNull(result);
    Assert.assertEquals(TextType.get(), result.getValueType());
    Assert.assertEquals(expected, result.toString());

    result = evaluate("format(newValue('" + pattern + "'))", testType.valueOf(now)).getValue();
    Assert.assertNotNull(result);
    Assert.assertEquals(TextType.get(), result.getValueType());
    Assert.assertEquals(expected, result.toString());
  }
}
