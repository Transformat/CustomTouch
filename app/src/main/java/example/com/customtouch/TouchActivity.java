package example.com.customtouch;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class TouchActivity extends Activity {
    RelativeLayout touchLayout;
    ImageButton circleView;
    TextView position;
    float centerY;
    float centerX;
    float radius;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
        circleView = (ImageButton) findViewById(R.id.circle_view);
        touchLayout = (RelativeLayout) findViewById(R.id.relative_layout);
        position = (TextView) findViewById(R.id.tv_position);
        touchLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                centerY = touchLayout.getMeasuredHeight() / 2;
                centerX = touchLayout.getMeasuredWidth() / 2;
            }
        });
        circleView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                radius = circleView.getHeight() / 2;
            }
        });

        touchLayout.setOnTouchListener(new TouchClass());

    }


    class TouchClass implements View.OnTouchListener {
        float distance;
        float xDistance;
        float yDistance;
        float xDistanceSquare;
        float yDistanceSquare;
        int flag;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDistance = event.getX() - centerX;
                    yDistance = event.getY() - centerY;
                    xDistanceSquare = (float) Math.pow(xDistance, 2);
                    yDistanceSquare = (float) Math.pow(yDistance, 2);
                    distance = (float) Math.sqrt(xDistanceSquare + yDistanceSquare);
                    if (distance == radius) {
                        position.setText("On Circle.");
                    } else {
                        if (distance < radius) {
                            position.setText("In circle.");
                            count++;
                            if (count % 2 == 0) {
                                circleView.setBackgroundResource(R.drawable.round_button_red);
                            } else {
                                circleView.setBackgroundResource(R.drawable.round_button_green);
                            }
                        } else position.setText("Outside circle.");
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    position.setText("Touch to check");
                    break;
                case MotionEvent.ACTION_MOVE:
                    xDistance = event.getX() - centerX;
                    yDistance = event.getY() - centerY;
                    xDistanceSquare = (float) Math.pow(xDistance, 2);
                    yDistanceSquare = (float) Math.pow(yDistance, 2);
                    distance = (float) Math.sqrt(xDistanceSquare + yDistanceSquare);
                    if (distance == radius) {
                        position.setText("On Circle.");
                    } else if (distance < radius) {
                        position.setText("In circle.");
                    } else
                        position.setText("Outside circle.");
                    break;


            }
            return true;
        }
    }
}
