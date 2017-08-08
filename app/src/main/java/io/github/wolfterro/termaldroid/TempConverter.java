/*
MIT License

Copyright (c) 2017 Wolfgang Almeida

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package io.github.wolfterro.termaldroid;

import android.content.Context;
import android.net.ParseException;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Wolfterro on 06/08/2017.
 */

public class TempConverter {
    // Propriedades privadas
    // =====================
    private Context c = null;
    private String value = "";
    private String from = "";
    private String to = "";

    private String result = "";

    private String TAG = "TempConverter.java";

    // Construtor da classe
    // ====================
    public TempConverter(Context c, String value, String from, String to) {
        this.c = c;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    // Métodos privados da classe
    // ==========================

    // Iniciando cálculo
    // =================
    private void convert() {
        Double dValue;
        Double valueToCelsius, valueToFahrenheit, valueToKelvin;

        try {
            dValue = Double.parseDouble(value);

            // Calculando os valores
            // ---------------------
            if(from.equals(c.getString(R.string.celsius))) {
                valueToCelsius = dValue;
                valueToFahrenheit = dValue * 9/5 + 32;
                valueToKelvin = dValue + 273.15;
            }
            else if(from.equals(c.getString(R.string.fahrenheit))) {
                valueToCelsius = (dValue - 32) * 5/9;
                valueToFahrenheit = dValue;
                valueToKelvin = (dValue + 459.67) * 5/9;
            }
            else if(from.equals(c.getString(R.string.kelvin))) {
                valueToCelsius = dValue - 273.15;
                valueToFahrenheit = dValue * 9/5 - 459.67;
                valueToKelvin = dValue;
            }
            else {
                // "Blah Blah Blah não inicializado..."
                // Este erro nunca irá acontecer, eu acho...
                // -----------------------------------------
                valueToCelsius = 0.0;
                valueToFahrenheit = 0.0;
                valueToKelvin = 0.0;

                result = String.format(Locale.getDefault(),
                        "%s",
                        c.getString(R.string.zero));

                String errorMsg = String.format("%s\n%s %s",
                        c.getString(R.string.errorCouldNotGetResults),
                        c.getString(R.string.error),
                        "UNKNOWN_FROM_VALUE");

                Toast.makeText(c, errorMsg, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "UNKNOWN_FROM_VALUE");
            }

            // Retornando os valores calculados
            // --------------------------------
            if(to.equals(c.getString(R.string.celsius))) {
                result = String.format(Locale.US,
                        "%.1f %s",
                        valueToCelsius,
                        c.getString(R.string.degreeCelsius));
            }
            else if(to.equals(c.getString(R.string.fahrenheit))) {
                result = String.format(Locale.US,
                        "%.1f %s",
                        valueToFahrenheit,
                        c.getString(R.string.degreeFahrenheit));
            }
            else if(to.equals(c.getString(R.string.kelvin))) {
                result = String.format(Locale.US,
                        "%.1f %s",
                        valueToKelvin,
                        c.getString(R.string.degreeKelvin));
            }
        }
        catch(ParseException e) {
            result = String.format(Locale.getDefault(),
                    "%s",
                    c.getString(R.string.zero));

            String errorMsg = String.format("%s\n%s %s",
                    c.getString(R.string.errorCouldNotGetResults),
                    c.getString(R.string.error),
                    "PARSE_EXCEPTION");

            Toast.makeText(c, errorMsg, Toast.LENGTH_SHORT).show();
            Log.e(TAG, e.toString());
        }
    }

    // Métodos públicos da classe
    // ==========================

    // Resgatando o valor calculado
    // ============================
    public String getResult() {
        convert();
        return result;
    }
}
