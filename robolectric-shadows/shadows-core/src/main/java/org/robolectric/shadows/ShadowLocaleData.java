package org.robolectric.shadows;

import java.util.Locale;

import android.os.Build;
import libcore.icu.LocaleData;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.Implementation;
import org.robolectric.internal.Shadow;
import org.robolectric.util.ReflectionHelpers;

/**
 * Shadow for {@link libcore.icu.LocaleData}.
 *
 * <p>This class only supports en_US regardless of the default locale set in the JVM.</p>
 */
@Implements(value = LocaleData.class, isInAndroidSdk = false)
public class ShadowLocaleData {
  public static final String REAL_CLASS_NAME = "libcore.icu.LocaleData";

  @Implementation
  public static LocaleData get(Locale locale) {
    LocaleData localeData = (LocaleData) Shadow.newInstanceOf(REAL_CLASS_NAME);
    if (locale == null) {
      locale = Locale.getDefault();
    }
    setEnUsLocaleData(localeData);
    return localeData;
  }

  private static void setEnUsLocaleData(LocaleData localeData) {
    localeData.amPm = new String[]{"AM", "PM"};
    localeData.eras = new String[]{"BC", "AD"};

    localeData.firstDayOfWeek = 1;
    localeData.minimalDaysInFirstWeek = 1;

    localeData.longMonthNames = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    localeData.shortMonthNames = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      localeData.tinyMonthNames = new String[]{"J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D"};
      localeData.tinyStandAloneMonthNames = localeData.tinyMonthNames;
      localeData.tinyWeekdayNames = new String[]{"", "S", "M", "T", "W", "T", "F", "S"};
      localeData.tinyStandAloneWeekdayNames = localeData.tinyWeekdayNames;

      localeData.yesterday = "Yesterday";
      localeData.today = "Today";
      localeData.tomorrow = "Tomorrow";
    }

    localeData.longStandAloneMonthNames = localeData.longMonthNames;
    localeData.shortStandAloneMonthNames = localeData.shortMonthNames;

    localeData.longWeekdayNames = new String[]{"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    localeData.shortWeekdayNames = new String[]{"", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    localeData.longStandAloneWeekdayNames = localeData.longWeekdayNames;
    localeData.shortStandAloneWeekdayNames = localeData.shortWeekdayNames;

    localeData.fullTimeFormat = "h:mm:ss a zzzz";
    localeData.longTimeFormat = "h:mm:ss a z";
    localeData.mediumTimeFormat = "h:mm:ss a";
    localeData.shortTimeFormat = "h:mm a";

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      localeData.timeFormat_hm = "h:mm a";
      localeData.timeFormat_Hm = "HH:mm";
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
      ReflectionHelpers.setField(localeData, "timeFormat12", "h:mm a");
      ReflectionHelpers.setField(localeData, "timeFormat24", "HH:mm");
    }

    localeData.fullDateFormat = "EEEE, MMMM d, y";
    localeData.longDateFormat = "MMMM d, y";
    localeData.mediumDateFormat = "MMM d, y";
    localeData.shortDateFormat = "M/d/yy";

    localeData.zeroDigit = '0';
    localeData.decimalSeparator = '.';
    localeData.groupingSeparator = ',';
    localeData.patternSeparator = ';';

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
      // Lollipop MR1 uses a String
      localeData.percent = "%";
    } else {
      // Upto Lollipop was a char
      ReflectionHelpers.setField(localeData, "percent", '%');
    }

    localeData.perMill = 0x2030;
    localeData.monetarySeparator = '.';

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      // Lollipop uses a String
      localeData.minusSign = "-";
    } else {
      // Upto KitKat was a char
      ReflectionHelpers.setField(localeData, "minusSign", '-');
//      localeData.minusSign = '-';
    }

    localeData.exponentSeparator = "E";
    localeData.infinity = "\u221E";
    localeData.NaN = "NaN";

    localeData.currencySymbol = "$";
    localeData.internationalCurrencySymbol = "USD";

    localeData.numberPattern = "\u0023,\u0023\u00230.\u0023\u0023\u0023";
    localeData.integerPattern = "\u0023,\u0023\u00230";
    localeData.currencyPattern = "\u00A4\u0023,\u0023\u00230.00;(\u00A4\u0023,\u0023\u00230.00)";
    localeData.percentPattern = "\u0023,\u0023\u00230%";
  }
}
