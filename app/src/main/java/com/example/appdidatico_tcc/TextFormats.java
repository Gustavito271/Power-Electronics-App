package com.example.appdidatico_tcc;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;

public class TextFormats {

    Context context;

    public TextFormats(Context context) {
        this.context = context;
    }

  /**
   * Major function to be called when setting the notation for a desired value. It will verifiy if
   * the absolute value of the provided value is between 1 and 1000. If so, the return will not
   * contain a prefix in unit. If don't, either <i>smallValues</i> or <i>bigValue</i> will called.
   * @param value - Raw value to be formatted
   * @param unit  - Unit of measurement
   * @return Formatted value with the desired decimal cases
   */
    public String notationValue(double value, String unit) {
        if (Double.isNaN(value)) {
            return "-";
        } else if (Math.abs(value) < 1 && Math.abs(value) > 0) {
            return smallValues(value*1000, unit, 0);
        } else if (Math.abs(value) >= 1000) {
            return bigValues(value/1000, unit, 0);
        } else {
            return (String.format("%.2f", value) + unit);
        }
    }

    /**
     * Major function to be called when setting the notation for a desired value. It will verifiy if
     * the absolute value of the provided value is between 1 and 1000. If so, the return will not
     * contain a prefix in unit. If don't, either <i>smallValues</i> or <i>bigValue</i> will called.
     * @param value - Raw value to be formatted
     * @return Formatted value with the desired decimal cases
     */
    public String cientificNotationValue(double value) {
        if (Math.abs(value) < 1 && Math.abs(value) > 0) {
            return cientificSmallValues(value*1000, 0);
        } else if (Math.abs(value) >= 1000) {
            return cientificBigValues(value/1000, 0);
        } else {
            return (String.format("%.2f", value));
        }
    }

    /**
     * Function to be called in cascate when setting the notation for a desired value. This one works
     * for values lesser than zero. <p>
     * Given the nature of this application, only the following will be considered:
     * <ul>
     *     <li>mili (10^-3)</li>
     *     <li>micro (10^-6)</li>
     *     <li>nano (10^-9)</li>
     *     <li>pico (10^-12)</li>
     * </ul>
     *
     * @param value    - Raw value to be formatted
     * @param unit     - Unit of measurement
     * @param position - Position to get from the array
     * @return Formatted value with the desired decimal cases
     */
    private String smallValues(double value, String unit, int position) {
        String[] units = new String[]{"m", context.getString(R.string.micro), "n", "p"};
        int temp_value = (int) value;

        if (Math.abs(temp_value) == 0) {
            return smallValues(value * 1000, unit, position + 1);
        } else {
            return (String.format("%.2f", value) + units[position] + unit);
        }
    }

    /**
     * Function to be called in cascate when setting the notation for a desired value. This one works
     * for values lesser than zero. <p>
     * Given the nature of this application, only the following will be considered:
     * <ul>
     *     <li>10^-3</li>
     *     <li>10^-6</li>
     *     <li>10^-9</li>
     *     <li>10^-12</li>
     * </ul>
     *
     * @param value    - Raw value to be formatted
     * @param position - Position to get from the array
     * @return Formatted value with the desired decimal cases
     */
    private String cientificSmallValues(double value, int position) {
        String[] units = new String[]{context.getString(R.string.miliNotation), context.getString(R.string.microNotation), context.getString(R.string.nanoNotation), context.getString(R.string.picoNotation)};
        int temp_value = (int) value;

        if (Math.abs(temp_value) == 0) {
            return cientificSmallValues(value * 1000, position + 1);
        } else {
            return (String.format("%.2f", value) + units[position]);
        }
    }

  /**
   * Function to be called in cascate when setting the notation for a desired value. This one works
   * for values bigger than zero. <p>
   * Given the nature of this application, only the following will be considered:
   *     <ul>
   *    <li>kilo - 10^3</li>
   *    <li>mega - 10^6</li>
   *    <li>giga - 10^9</li>
   *    <li>tera - 10^12</li>
   *     </ul>
   *
   * @param value    - Raw value to be formatted
   * @param unit     - Unit of measurement
   * @param position - Position to get from the array
   * @return Formatted value with the desired decimal cases
   */
    private String bigValues(double value, String unit, int position) {
        String[] units = new String[]{"k", "M", "G", "T"};
        int temp_value = (int) (value / 1000);

        if (Math.abs(temp_value) > 0) {
            return smallValues(value / 1000, unit, position + 1);
        } else {
            return (String.format("%.2f", value) + units[position] + unit);
        }
    }

    /**
     * Function to be called in cascate when setting the notation for a desired value. This one works
     * for values bigger than zero. <p>
     * Given the nature of this application, only the following will be considered:
     *     <ul>
     *    <li>10^3</li>
     *    <li>10^6</li>
     *    <li>10^9</li>
     *    <li>10^12</li>
     *     </ul>
     *
     * @param value    - Raw value to be formatted
     * @param position - Position to get from the array
     * @return Formatted value with the desired decimal cases
     */
    private String cientificBigValues(double value, int position) {
        String[] units = new String[]{context.getString(R.string.kiloNotation), context.getString(R.string.megaNotation), context.getString(R.string.gigaNotation), context.getString(R.string.teraNotation)};
        int temp_value = (int) (value / 1000);

        if (Math.abs(temp_value) > 0) {
            return cientificBigValues(value / 1000, position + 1);
        } else {
            return (String.format("%.2f", value) + units[position]);
        }
    }

    /**
     * Formats a Complex value to match in string format: Real + j Imaginary. If the imaginary part
     * is zero, it will not be showed.
     * @param value Complex value containg real and imaginary parts
     * @return String in the right format.
     */
    public String formatComplexValues(ComplexValue value) {
        String realPart = cientificNotationValue(value.getRealPart());
        String imaginaryPart = cientificNotationValue(Math.abs(value.getImaginaryPart()));
        String operation = (value.getImaginaryPart() < 0 ? " - " : " + ");

        return  (!Double.isNaN(value.getRealPart()) ? realPart : "") +
                (value.getImaginaryPart() != 0 || !Double.isNaN(value.getImaginaryPart()) ? (operation + "j" + imaginaryPart) : "");
    }

    /**
     * Applies small leters (subscript) in text based on an interval.
     * @param text Full text
     * @param start Interval's start
     * @param end Interval's end
     * @return Formatted string
     */
    public SpannableString formatString(String text, int start, int end) {
        SpannableString t = new SpannableString(text);
        t.setSpan(new RelativeSizeSpan(0.65f), start, end, 0);
        return t;
    }

    /**
     * Applies small leters (subscript) in text based on an interval.
     * @param text Full text
     * @param start1 First Interval's start
     * @param end1 First Interval's end
     * @param start2 Second Interval's start
     * @param end2 Second Interval's end
     * @return Formatted string
     */
    public SpannableString formatString(String text, int start1, int end1, int start2, int end2) {
        SpannableString t = new SpannableString(text);
        t.setSpan(new RelativeSizeSpan(0.65f), start1, end1, 0);
        t.setSpan(new RelativeSizeSpan(0.65f), start2, end2, 0);
        return t;
    }

    /**
     * Applies small leters (subscript) in text based on an interval.
     * @param text Full text
     * @param start1 First Interval's start
     * @param end1 First Interval's end
     * @param start2 Second Interval's start
     * @param end2 Second Interval's end
     * @param start3 Third Interval's start
     * @param end3 Third Interval's end
     * @return Formatted string
     */
    public SpannableString formatString(String text, int start1, int end1, int start2, int end2, int start3, int end3) {
        SpannableString t = new SpannableString(text);
        t.setSpan(new RelativeSizeSpan(0.65f), start1, end1, 0);
        t.setSpan(new RelativeSizeSpan(0.65f), start2, end2, 0);
        t.setSpan(new RelativeSizeSpan(0.65f), start3, end3, 0);
        return t;
    }
}
