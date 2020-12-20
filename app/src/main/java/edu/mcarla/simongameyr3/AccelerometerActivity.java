package edu.mcarla.simongameyr3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {



//****************************************************************************************************

    private final double NORTH_MOVE_FORWARD = 9.0;     // upper mag limit // z cordiante less than 9
    private final double NORTH_MOVE_BACKWARD = 6.0;// lower mag limit // x cordinate more than 6

    private final double SOUTH_MOVE_FORWARD = 0.76;
    private final double SOUTH_MOVE_BACkWARD = -5.0;

    private final double WEST_MOVE_FORWARD = 0.04;
    private final double WEST_MOVE_BACKWARD = -0.65;

    //  private final double EAST_MOVE_FORWARD = 9.0;
    //  private final double EAST_MOVE_BACKWARD = 0.80;

//***********************************************************
    boolean highLimit = false;      // detect high limit
    boolean westhighlimit =false;
    boolean SouthHighLimit = false;
    //boolean EastHighLimit =false;

    boolean NorthTilt = false;
    boolean SouthTilt =false;
    boolean WestTilt =false;

 //*************************************************************

    int counter = 0; // step counter
    int SouthCount =0;
    int WestCount=0;
    //int EastCount =0;

//*********************************************************************
    TextView tvx, tvy, tvz, tvSteps, southsteps, weststeps, eaststeps;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    int[] GameSequence;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        int counter =0;


      Bundle extras = getIntent().getExtras();
      int[] SArray = extras.getIntArray("sequence");

      GameSequence = SArray;



      for(int item: GameSequence){
          Log.i("SecondACTIVITY",String.valueOf(item));



      }




        tvx = findViewById(R.id.tvX);
        tvy = findViewById(R.id.tvY);
        tvz = findViewById(R.id.tvZ);
        tvSteps = findViewById(R.id.tvSteps);
        southsteps = findViewById(R.id.SouthCount);
        weststeps = findViewById(R.id.WestCount);
        //eaststeps = findViewById(R.id.EastCount);





        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);    // turn off listener to save power
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        tvx.setText(String.valueOf(x));
        tvy.setText(String.valueOf(y));
        tvz.setText(String.valueOf(z));


//***********North Tilt********************************
        if ((x > NORTH_MOVE_FORWARD) && (highLimit == false)) {

            highLimit = true;
        }
        if ((x < NORTH_MOVE_BACKWARD) && (highLimit == true)) {
           counter++;
           tvSteps.setText(String.valueOf(counter));
            highLimit = false;

        }

//************South Tilt**************************************
        if ((z > SOUTH_MOVE_FORWARD) && (SouthHighLimit == false)) {
            SouthHighLimit = true;
        }
        if ((z < SOUTH_MOVE_BACkWARD) && (SouthHighLimit == true)) {

           SouthCount++;
           southsteps.setText(String.valueOf(SouthCount));
            SouthHighLimit = false;

        }


//***************************West Tilt*****************************
        if ((y > WEST_MOVE_FORWARD) && (westhighlimit == false)) {

            westhighlimit = true;
        }
        if ((y < WEST_MOVE_BACKWARD) && (westhighlimit == true)) {

           WestCount++;
         weststeps.setText(String.valueOf(WestCount));
            westhighlimit = false;

        }







    }



//*****************************East Tilt*****************************


      /*  if ((x > EAST_MOVE_FORWARD) && (EastHighLimit == false)) {
            EastHighLimit = true;
        }
        if ((y > EAST_MOVE_BACKWARD) && (EastHighLimit == true)) {
            // we have a tilt to the north
            EastCount++;
            eaststeps.setText(String.valueOf(EastCount));
             EastHighLimit= false;
        }*/





    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    public void doFinish(View view) {

        Intent finish = new Intent(AccelerometerActivity.this, DatabaseMain.class);
        startActivity(finish);
    }
}