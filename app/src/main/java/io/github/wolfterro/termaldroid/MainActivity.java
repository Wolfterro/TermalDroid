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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Elementos da Activity principal
    // ===============================
    private EditText editTextValue;
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private TextView textViewResult;
    private Button buttonCalculate;

    // Menu de opções da activity principal
    // ====================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Selecionando opções no menu da activity principal
    // =================================================
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent about = new Intent(MainActivity.this, AboutActivity.class);
                MainActivity.this.startActivity(about);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Criando Activity principal do aplicativo
    // ========================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextValue = (EditText)findViewById(R.id.editTextTempValue);
        spinnerFrom = (Spinner)findViewById(R.id.spinnerFrom);
        spinnerTo = (Spinner)findViewById(R.id.spinnerTo);
        textViewResult = (TextView)findViewById(R.id.textViewResult);
        buttonCalculate = (Button)findViewById(R.id.button);

        // Iniciando o processo de conversão de temperatura
        // ------------------------------------------------
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextValue.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this,
                            getString(R.string.pleaseInsertValue),
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    TempConverter converter = new TempConverter(MainActivity.this,
                            editTextValue.getText().toString(),
                            spinnerFrom.getSelectedItem().toString(),
                            spinnerTo.getSelectedItem().toString());

                    textViewResult.setText(converter.getResult());
                }
            }
        });
    }
}
