package example.com.customtouch;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class TouchActivity extends Activity {
    RelativeLayout touchLayout;
    ImageButton[] circleView = new ImageButton[6];
    TextView positionText;
    float[] xCoordinate = new float[6];
    float[] yCoordinate = new float[6];
    float[] distanceArray = new float[6];
    int count;
    String position;
    int previousCircle = 7;
    boolean[] color = new boolean[7];
    float radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
        count = 0;
        touchLayout = (RelativeLayout) findViewById(R.id.relative_layout);
        positionText = (TextView) findViewById(R.id.tv_position);

        //Circles initialization:-
        for (int i = 0; i < touchLayout.getChildCount(); i++) {
            if (touchLayout.getChildAt(i) instanceof ImageButton) {
                circleView[i] = (ImageButton) touchLayout.getChildAt(i);
            }
        }
        for (int i = 0; i < 6; i++) {
            color[i] = true;
        }

        touchLayout.setOnTouchListener(new TouchClass());

    }


    class TouchClass implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            float x = event.getX();
            float y = event.getY();

            //Centres and radius of circles calculated:-
            for (int i = 0; i < 6; i++) {
                radius = circleView[0].getWidth() / 2;
                xCoordinate[i] = circleView[i].getX() + radius;
                yCoordinate[i] = circleView[i].getY() + radius;
                distanceArray[i] = (float) Math.sqrt((Math.pow(x - xCoordinate[i], 2) + (Math.pow(y - yCoordinate[i], 2))));
            }
            for (int i = 0; i < 6; i++) {
                if (distanceArray[i] < radius) {
                    count = i;
                    position = "You are inside circle " + (i + 1);
                    break;
                } else {
                    count = 6;
                    position = "You are outside the circle";
                }
            }

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    positionText.setText(position);
                    if (count < 6) {
                        if (color[count]) {
                            circleView[count].setBackgroundResource(R.drawable.round_button_green);
                            color[count] = false;
                        } else {
                            circleView[count].setBackgroundResource(R.drawable.round_button_red);
                            color[count] = true;
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    positionText.setText(position);
                    if (count < 6 && count != previousCircle) {
                        if (color[count]) {
                            circleView[count].setBackgroundResource(R.drawable.round_button_green);
                            color[count] = false;
                        } else {
                            circleView[count].setBackgroundResource(R.drawable.round_button_red);
                            color[count] = true;
                        }
                        previousCircle = count;
                    } else {
                        previousCircle = count;
                    }
                    break;
            }

            return true;


        }
    }
}
