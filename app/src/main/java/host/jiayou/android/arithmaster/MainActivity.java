package host.jiayou.android.arithmaster;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.Math;

import bsh.Interpreter;

public class MainActivity extends AppCompatActivity {

    private TextView mTextQuestion;
    private TextView mTextAnswer;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Change", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toast.setText("难度1");
                    toast.show();
                    difficulty = 20;
                    return true;
                case R.id.navigation_dashboard:
                    toast.setText("难度2");
                    toast.show();
                    difficulty = 50;
                    return true;
                case R.id.navigation_notifications:
                    toast.setText("难度3");
                    toast.show();
                    difficulty = 100;
                    return true;
            }
            return false;
        }
    };

    private int state=0;
    private int difficulty = 20;
    private String q="";
    private String a="";


    public boolean next() {
        state++;
        if (state % 2 == 0) {
            int result = -1;

            while (result<0) {
                q = question(difficulty);
                a = answer(q);
                result = Integer.parseInt(a);
            }

            mTextQuestion.setText(q + " = ");
            mTextAnswer.setText(a);
            mTextAnswer.setVisibility(View.INVISIBLE);
        }
        else
        {
            mTextAnswer.setVisibility(View.VISIBLE);
        }


        return false;
    }
    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextQuestion = (TextView) findViewById(R.id.question);
        mTextAnswer = (TextView) findViewById(R.id.answer);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.container);
        layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                next();
            }
        });

        mProgressBar=(ProgressBar)findViewById(R.id.progressbar);
        mProgressBar.setProgress(i);
        mCountDownTimer=new CountDownTimer(5000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
//                Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                i++;
                mProgressBar.setProgress((int)i*100/(5000/1000));

            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                mProgressBar.setProgress(100);
            }
        };
        mCountDownTimer.start();


    }

    private String question(int difficulty){
        String txt= "" + (int)Math.round(difficulty * Math.random() )
                + randomOp()
                + (int)Math.round(difficulty*Math.random() )
                + "";
        if (difficulty>20)
        {
            txt += randomOp() + (int)Math.round(difficulty*Math.random() );
        }
        return txt;
    }

    private String randomOp(){
        return Math.random() > 0.5 ? " + " : " - ";
    }

    private String answer(String question){
try{
        Interpreter bsh = new Interpreter();                  //声明Interpreter类
        Number result = (Number)bsh.eval(question);
        return result.toString();
    }catch (Exception e)
    {
        return e.toString();
    }


    }
}
