package com.example.android.becktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.becktest.dict.Question;
import com.example.android.becktest.dict.Result;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Map<Integer, Integer> userAnswer = new HashMap<>();
    private Integer counter;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("tag", "state");
    }

    public void startTest(View view) {
        counter = 1;
        userAnswer.clear();
        setContentView(R.layout.question);
        TextView questionNumber = (TextView) findViewById(R.id.questionNumber);
        String text = getString(R.string.question_counter, counter, Question.values().length);
        questionNumber.setText(text);

        radioButton1 = (RadioButton) findViewById(R.id.option1);
        radioButton2 = (RadioButton) findViewById(R.id.option2);
        radioButton3 = (RadioButton) findViewById(R.id.option3);
        radioButton4 = (RadioButton) findViewById(R.id.option4);

        displayOption(radioButton1, Question.QUESTION1.getFirstOption());
        displayOption(radioButton2, Question.QUESTION1.getSecondOption());
        displayOption(radioButton3, Question.QUESTION1.getThirdOption());
        displayOption(radioButton4, Question.QUESTION1.getForthOption());

        radioButton1.setOnClickListener(selectRadioOption);
        radioButton2.setOnClickListener(selectRadioOption);
        radioButton3.setOnClickListener(selectRadioOption);
        radioButton4.setOnClickListener(selectRadioOption);
    }

    private void displayOption(RadioButton radioButton, String string) {
        radioButton.setText(string);
    }

    public void nextQuestion(View view) {
        //валидация
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            String text = getString(R.string.no_answer);
            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        counter++;

        TextView questionNumber = (TextView) findViewById(R.id.questionNumber);
        String text = getString(R.string.question_counter, counter, Question.values().length);
        questionNumber.setText(text);

        Question question = Question.getQuestionById(counter);

        radioButton1 = (RadioButton) findViewById(R.id.option1);
        radioButton2 = (RadioButton) findViewById(R.id.option2);
        radioButton3 = (RadioButton) findViewById(R.id.option3);
        radioButton4 = (RadioButton) findViewById(R.id.option4);

        displayOption(radioButton1, question.getFirstOption());
        displayOption(radioButton2, question.getSecondOption());
        displayOption(radioButton3, question.getThirdOption());
        displayOption(radioButton4, question.getForthOption());

        radioGroup.clearCheck();

        if (userAnswer.get(counter) != null) {
            Integer option = userAnswer.get(counter);
            switch (option) {
                case 0:
                    radioButton1.setChecked(true);
                    break;
                case 1:
                    radioButton2.setChecked(true);
                    break;
                case 2:
                    radioButton3.setChecked(true);
                    break;
                case 3:
                    radioButton4.setChecked(true);
                    break;
                default:
                    break;
            }
        }

        Button nextButton = (Button) findViewById(R.id.nextButton);
        Button previousButton = (Button) findViewById(R.id.previousButton);
        Button resultsButton = (Button) findViewById(R.id.resultsButton);
        if (counter > 1) {
            previousButton.setVisibility(View.VISIBLE);
        }
        if (counter == Question.values().length) {
            nextButton.setVisibility(View.GONE);
            resultsButton.setVisibility(View.VISIBLE);
        }
    }

    public void previousQuestion(View view) {
        radioButton1 = (RadioButton) findViewById(R.id.option1);
        radioButton2 = (RadioButton) findViewById(R.id.option2);
        radioButton3 = (RadioButton) findViewById(R.id.option3);
        radioButton4 = (RadioButton) findViewById(R.id.option4);

        counter--;

        TextView questionNumber = (TextView) findViewById(R.id.questionNumber);
        String text = getString(R.string.question_counter, counter, Question.values().length);
        questionNumber.setText(text);

        Question question = Question.getQuestionById(counter);
        displayOption(radioButton1, question.getFirstOption());
        displayOption(radioButton2, question.getSecondOption());
        displayOption(radioButton3, question.getThirdOption());
        displayOption(radioButton4, question.getForthOption());

        Button nextButton = (Button) findViewById(R.id.nextButton);
        Button previousButton = (Button) findViewById(R.id.previousButton);
        Button resultsButton = (Button) findViewById(R.id.resultsButton);

        if (counter == 1) {
            previousButton.setVisibility(View.GONE);
        }
        if (counter < Question.values().length) {
            nextButton.setVisibility(View.VISIBLE);
            resultsButton.setVisibility(View.GONE);

        }
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.clearCheck();

        if (userAnswer.get(counter) != null) {
            Integer option = userAnswer.get(counter);
            switch (option) {
                case 0:
                    radioButton1.setChecked(true);
                    break;
                case 1:
                    radioButton2.setChecked(true);
                    break;
                case 2:
                    radioButton3.setChecked(true);
                    break;
                case 3:
                    radioButton4.setChecked(true);
                    break;
                default:
                    break;
            }
        }
    }

    View.OnClickListener selectRadioOption = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton) v;
            switch (rb.getId()) {
                case R.id.option1:
                    userAnswer.put(counter, 0);
                    break;
                case R.id.option2:
                    userAnswer.put(counter, 1);
                    break;
                case R.id.option3:
                    userAnswer.put(counter, 2);
                    break;
                case R.id.option4:
                    userAnswer.put(counter, 3);
                    break;
                default:
                    break;
            }
        }
    };

    public void calculateResults(View view) {
        //валидация
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            String text = getString(R.string.no_answer);
            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        setContentView(R.layout.results);
        Integer result = 0;
        for (Map.Entry<Integer, Integer> entry: userAnswer.entrySet()) {
            result += entry.getValue();
        }
        TextView resultText = (TextView) findViewById(R.id.user_result);
        if (result <= 9) {
            resultText.setText(Result.RESULT_0_9.getValue());
        } else if (result <= 15) {
            resultText.setText(Result.RESULT_10_15.getValue());
        } else if (result <= 19) {
            resultText.setText(Result.RESULT_16_19.getValue());
        } else if (result <= 29) {
            resultText.setText(Result.RESULT_20_29.getValue());
        } else {
            resultText.setText(Result.RESULT_30_63.getValue());
        }
    }
}