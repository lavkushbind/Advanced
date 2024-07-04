package com.example.payment;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
public class CurrencyUtils {
    private static final Map<String, Double> conversionRates = new HashMap<>();
    static {
        conversionRates.put("USD", 83.0);
        conversionRates.put("EUR", 90.0);
        conversionRates.put("GBP", 105.0);
        conversionRates.put("AUD", 55.0);
        conversionRates.put("CAD", 60.0);
        conversionRates.put("SGD", 61.0);
        conversionRates.put("JPY", 0.75);
        conversionRates.put("CHF", 90.0);
        conversionRates.put("CNY", 12.0);
        conversionRates.put("SEK", 8.0);
        conversionRates.put("NZD", 50.0);
        conversionRates.put("KRW", 0.07);
        conversionRates.put("NOK", 9.0);
        conversionRates.put("MXN", 4.0);
        conversionRates.put("TRY", 10.0);
        conversionRates.put("RUB", 1.1);
        conversionRates.put("BRL", 15.0);
        conversionRates.put("INR", 1.0);
        conversionRates.put("ZAR", 5.0);
        conversionRates.put("AED", 22.0);
        conversionRates.put("HKD", 10.0);
        conversionRates.put("ISK", 0.6);
        conversionRates.put("THB", 2.5);
        conversionRates.put("DKK", 12.0);
        conversionRates.put("MYR", 20.0);
        conversionRates.put("IDR", 0.006);
        conversionRates.put("HUF", 0.28);
        conversionRates.put("CZK", 3.5);
        conversionRates.put("ILS", 25.0);
        conversionRates.put("PLN", 22.0);
        conversionRates.put("PHP", 1.6);
        conversionRates.put("EGP", 5.0);
        conversionRates.put("ARS", 1.0);
        conversionRates.put("SAR", 22.0);
        conversionRates.put("QAR", 22.0);
        conversionRates.put("VND", 0.0035);
        conversionRates.put("KWD", 270.0);
        conversionRates.put("BHD", 220.0);
        conversionRates.put("OMR", 210.0);
        conversionRates.put("COP", 0.022);
        conversionRates.put("CLP", 0.12);
        conversionRates.put("TWD", 3.0);
        conversionRates.put("UAH", 3.0);
        conversionRates.put("NGN", 0.2);
        conversionRates.put("UGX", 0.02);
        conversionRates.put("BDT", 1.0);
        conversionRates.put("RON", 18.0);
        conversionRates.put("ZMW", 6.0);
        conversionRates.put("MAD", 9.0);
        conversionRates.put("DZD", 0.6);
        conversionRates.put("KES", 0.75);
        conversionRates.put("GHS", 13.0);
        conversionRates.put("MVR", 5.0);
        conversionRates.put("AFN", 1.0);
        conversionRates.put("MMK", 0.06);
        conversionRates.put("LKR", 0.5);
        conversionRates.put("IQD", 0.06);
        conversionRates.put("KHR", 0.02);
        conversionRates.put("PYG", 0.00012);
    }

    public static String formatPrice(double priceInInr) {
        Locale locale = Locale.getDefault();
        Currency currency = Currency.getInstance(locale);

        double convertedPrice = convertPrice(priceInInr, currency.getCurrencyCode());

        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        return format.format(convertedPrice);
    }

    private static double convertPrice(double priceInInr, String currencyCode) {
        Double rate = conversionRates.get(currencyCode);
        if (rate != null) {
            return priceInInr / rate;
        } else {
            // Default to INR if no conversion rate is found
            return priceInInr;
        }
    }
}

