package softcare.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class AddCityLActivity extends AppCompatActivity implements View.OnKeyListener {


    private boolean ok=false;
    private String x;
    private String y;

    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city_lactivity);
        context=this;
        addCity();
            findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    go();
                }
            });
    }







    private  void  go(){
        if(isOkay()){
            Intent intent =new Intent(this, SolutionActivity.class);
            intent.putExtra("cityName", getName());
            intent.putExtra("x",  getPointX());
            intent.putExtra("y",  getPointY());
            intent.setFlags(RESULT_OK);
            setResult(RESULT_OK,intent);
            finish();
        }else{
            Snackbar.make(x_input, "Please file data currently", Snackbar.LENGTH_LONG) .setAction("Action", null).show();

        }
    }




    public boolean isOkay() {
        return this.ok;
    }
    EditText x_input ;
    EditText y_input ;
    EditText city ;
    public TextView msg;
    private Context context;

    public void addCity() {
        x_input = findViewById(R.id.city_lat);
        y_input = findViewById(R.id.city_log);
        city = findViewById(R.id.city_name);
        msg = findViewById(R.id.msg);
        x = getString(R.string.latitude);
        y = getString(R.string.longitude);
        x_input.setOnKeyListener(this);
        y_input.setOnKeyListener(this);
        city.setOnKeyListener(this);
        msg.setText(getString(R.string.empty_fields));
        msg.setTextColor(context.getResources().getColor(R.color.red));


    }
    private void callTest( String x, String y ) {
        if(city.getText().toString().isEmpty()) {
            ok=false;
            msg.setText(getString(R.string.name_is_empty));
            return;
        }else {
            ok= true;
            msg.setText( getString(R.string.okay));

            ok=	test(x_input,  x);
            if(ok)ok=	test(y_input, y);
        }
    }

    private boolean test( EditText numS, String msgText){
        Intent i= new Intent();
        double d [][]= null;

        try {
            if(numS.getText().toString().isEmpty()) {
                msg.setTextColor(context.getResources().getColor(R.color.red));
                msg.setText(getString(R.string.empty)+msgText);
                return false;
            }else {
                Double.parseDouble(numS.getText().toString());
                msg.setTextColor(context.getResources().getColor(R.color.green));
                msg.setText(getString(R.string.okay));
                return true;

            }
        }catch(Exception e) {
            msg.setTextColor(context.getResources().getColor(R.color.red));
            msg.setText(getString(R.string.not_a_number));
            return false;
        }
    }


    public double getPointX() {
        if(ok) {
            try {  return	Double.parseDouble(x_input.getText().toString());
            }catch(Exception e) {
                e.printStackTrace(); }
        }

        return -1;
    }
    public double getPointY() {
        if(ok) {
            try {  return	Double.parseDouble(y_input.getText().toString());
            }catch(Exception e) {
                e.printStackTrace(); }
        }

        return -1;
    }
    public String getName() {
        return city.getText().toString();
    }
    public void clear( Context context) {
        city.setText("");
        y_input.setText("");
        x_input.setText("");
        msg.setText(getString(R.string.empty_fields));
        msg.setTextColor(context.getResources().getColor(R.color.red));

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        callTest( x,y);
        return false;
    }






}