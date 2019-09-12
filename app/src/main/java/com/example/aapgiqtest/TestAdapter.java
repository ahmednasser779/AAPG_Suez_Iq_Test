package com.example.aapgiqtest;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TestAdapter extends ArrayAdapter<TestObject> {

    public TestAdapter (Activity context , ArrayList<TestObject> tests){
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for one TextView and 4 Radio buttons, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, tests);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.test_list, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        TestObject currentTest = getItem(position);

        TextView question = listItemView.findViewById(R.id.question);
        question.setText(currentTest.getmQuestion());

        ImageView questionImage = listItemView.findViewById(R.id.question_image);
        if(currentTest.getmImageQuestion().isEmpty()){
            questionImage.setVisibility(View.GONE);
            question.setVisibility(View.VISIBLE);
        }else {
            Picasso.get().load(currentTest.getmImageQuestion()).fit().into(questionImage);
            questionImage.setVisibility(View.VISIBLE);
            question.setVisibility(View.GONE);
        }


        RadioButton answer1 = listItemView.findViewById(R.id.answer1);
        answer1.setText(currentTest.getmAnswer1());

        RadioButton answer2 = listItemView.findViewById(R.id.answer2);
        answer2.setText(currentTest.getmAnswer2());

        RadioButton answer3 = listItemView.findViewById(R.id.answer3);
        answer3.setText(currentTest.getmAnswer3());

        RadioButton answer4 = listItemView.findViewById(R.id.answer4);
        answer4.setText(currentTest.getmAnswer4());


        return listItemView;
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

}
