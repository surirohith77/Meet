package com.programmingly.meetmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialButton btnLaw, btnMedicine, btnActors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

    }

    private void initialize() {

        btnLaw = findViewById(R.id.btnLaw);
        btnMedicine = findViewById(R.id.btnMedicine);
        btnActors = findViewById(R.id.btnActors);

        btnLaw.setOnClickListener(this);
        btnMedicine.setOnClickListener(this);
        btnActors.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

          /* 1.Law - 1
        2.Medicine - 2
        3.Actors - 3
        4.Hairdressers - 4
        5.Coder - 5
        6.Hospitality - 6
        7.Marketing - 7
        8.Sales - 8
        9.Finance - 9*/

        switch (v.getId()){

           case  R.id.btnLaw:

               Intent intent = new Intent(this,PostActivity.class);
               intent.putExtra("category_id","1");
               startActivity(intent);
               break;

            case  R.id.btnMedicine:

                Intent intent2 = new Intent(this,PostActivity.class);
                intent2.putExtra("category_id","2");
                startActivity(intent2);
                break;

            case  R.id.btnActors:

                Intent intent3 = new Intent(this,PostActivity.class);
                intent3.putExtra("category_id","3");
                startActivity(intent3);
                break;
        }
    }
}