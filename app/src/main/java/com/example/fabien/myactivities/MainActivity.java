package com.example.fabien.myactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final String KEY_MAKE = "keyMake";
    public static final String KEY_Fuel = "keyFuel";
    public static final String KEY_YEAR = "keyYear";
    public static final String KEY_COLOR = "keyColor";
    public static final String KEY_NEW = "keyNew";                  // On crée les toutes les clés necessaire pour intent.putExtra
    public static final String KEY_RIGHT_HAND = "keyRightHand";
    public static final String KEY_NOTE = "keyNote";
    private static final String TAG_LIFECYCLE = "TagLifecycle";
    private static final int REQUEST_EDIT = 1234;
    private static final int REQUEST_DOWNLOAD = 5678;

    private Spinner spinnerMaker;
    private Switch switchFuel;
    private EditText editTextYear;
    private RadioGroup radioGroupColor;     // On crée les objets qui font référence aux inputs du activity_mains.xml
    private CheckBox checkBoxNew;
    private CheckBox checkBoxRightHand;
    private Button buttonImage;
    private EditText editTextNote;

    private String[] carMaker;
    private String make;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG_LIFECYCLE, "In the onCreate() event");

        spinnerMaker = (Spinner) findViewById(R.id.spinnerMake);
        switchFuel = (Switch) findViewById(R.id.switchFuel);
        editTextYear = (EditText) findViewById(R.id.inputYear);
        radioGroupColor = (RadioGroup) findViewById(R.id.radioColor);
        checkBoxNew = (CheckBox) findViewById(R.id.isNew);              // On affect des " valeurs" au objets en les faisant pointer sur les ID respectifs du XML
        checkBoxRightHand = (CheckBox) findViewById(R.id.isRightHand);
        buttonImage = (Button) findViewById(R.id.buttonImage);
        editTextNote = (EditText) findViewById(R.id.inputNote);
        carMaker = getResources().getStringArray(R.array.car_maker);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.car_maker, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaker.setAdapter(adapter);
        spinnerMaker.setOnItemSelectedListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG_LIFECYCLE, "In the onStart() event");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(TAG_LIFECYCLE, "In the onRestart() event");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG_LIFECYCLE, "In the onResume() event");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG_LIFECYCLE, "In the onPause() event");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG_LIFECYCLE, "In the onStop() event");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG_LIFECYCLE, "In the onDestroy() event");
    }

    public void goEdit(View v) {                                                // En activant cette méthode, on start l'acitivité " NoteEditingActivity"
        Intent intentEdit = new Intent(this, NoteEditingActivity.class);
        startActivityForResult(intentEdit, REQUEST_EDIT);                           // Avec cette fonction
    }

    public void goDisplay(View v) {
        Intent intentDisplay = new Intent();
        intentDisplay.setAction("com.example.jianhuayang.myactivities.ThirdActivity");
        intentDisplay.putExtra(KEY_MAKE,make);
        intentDisplay.putExtra(KEY_Fuel,switchFuel.isChecked());
        intentDisplay.putExtra(KEY_YEAR, Integer.parseInt(editTextYear.getText().toString()));              //On envoie les donné un par un avec putExtra
        Bundle bundle = new Bundle();
        String color=((RadioButton)findViewById(radioGroupColor.getCheckedRadioButtonId())).getText().toString();
        bundle.putString(KEY_COLOR, color);
        bundle.putBoolean(KEY_NEW,checkBoxNew.isChecked());
        bundle.putBoolean(KEY_RIGHT_HAND,checkBoxRightHand.isChecked());
        bundle.putString(KEY_NOTE, editTextNote.getText().toString());
        intentDisplay.putExtras(bundle);                                    // On PUT ( envoie ) toutes les données du bundles avec putExtras
        startActivity(intentDisplay);                   // Puis on start l'activity
    }

    public void goDownload(View v) {
        Intent aIntent = new Intent(this, DownloadActivity.class);
        startActivityForResult(aIntent, REQUEST_DOWNLOAD);
    }

    //The code above parse the data collected using the app UI and pass them to the next activity.
    // Here, all input-collecting widgets are being used.

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_EDIT && resultCode == RESULT_OK) {
            editTextNote.setText(data.getData().toString());
        }

        if (requestCode == REQUEST_DOWNLOAD && resultCode == RESULT_OK) {
            buttonImage.setText("");
            buttonImage.setBackgroundResource(data.getIntExtra(DownloadActivity.KEY_DRAWABLE, R.mipmap.ic_launcher));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Ces deux méthodes permettent d'effectuer des actions en fonction de quel item à été selectionné couplé avec le ArrayAdapter créer dans le onCreate()
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        make = carMaker[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        make = "No maker selected";
    }
}
