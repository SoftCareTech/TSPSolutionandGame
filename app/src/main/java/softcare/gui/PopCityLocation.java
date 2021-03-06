package softcare.gui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import softcare.game.R;

public class PopCityLocation extends StyleDialog implements View.OnKeyListener {
	private boolean ok=false;
	private String x;
	private String y;
	public boolean isOkay() {
		return this.ok;
	}
	EditText x_input ;
	EditText y_input ;
	EditText city ;
	public TextView msg;
	private  Context context;
	public PopCityLocation(AppCompatActivity  activity) {
		super(activity);
		this.context= activity.getApplicationContext();
	} 
	public void addCity(String name, int index) {
		setContentView(R.layout.activity_add_city_lactivity);
		show();
		x_input = findViewById(R.id.city_lat);
		y_input = findViewById(R.id.city_log);
		city = findViewById(R.id.city_name);
		msg = findViewById(R.id.msg);
		x = "Lat ";
		y = "Log ";
 x_input.setOnKeyListener(this);
 y_input.setOnKeyListener(this);
 city.setOnKeyListener(this);
		msg.setText("Empty fields");
		msg.setTextColor(context.getResources().getColor(R.color.red));


	}
	void callTest( String x, String y ) {
		if(city.getText().toString().isEmpty()) {
			ok=false;
			msg.setText(" City name is empty");
			return;
		}else {
			ok= true;
			msg.setText("okay");

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
				msg.setText("Empty "+msgText);
			return false;
			}else {
			Double.parseDouble(numS.getText().toString());
			 msg.setTextColor(context.getResources().getColor(R.color.green));
				msg.setText("okay");
			 return true;
			
			}
		}catch(Exception e) {
			msg.setTextColor(context.getResources().getColor(R.color.red));
			msg.setText("Not a number");
			return false;
		} 
  }
		
		
		
	public double getX() {
		if(ok) {
			try {  return	Double.parseDouble(x_input.getText().toString());
				  }catch(Exception e) {
				 e.printStackTrace(); } 
		}
		
		return -1;
	}
public double getY() {
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
msg.setText("Empty fields");
msg.setTextColor(context.getResources().getColor(R.color.red));
	
}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		callTest( x,y);
		return false;
	}









/*

	protected void addCityXY( List<String> cities) {

		add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (control.isOkay()) {
					pointXY.add(new PointXY(control.getX(), control.getY()));
					cities.add(control.getName());
					countDistancesAndUpdateMatrix();
					stage.close();
				} else {
					S.notice(stage, "Error", "Wrong inputs", control.msg.getText());

				}
			}
		});
		clear.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				control.clear();
			}
		});

		cancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});
		stage.showAndWait();
		;

	}
*/

}