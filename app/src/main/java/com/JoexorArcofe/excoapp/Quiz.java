package com.JoexorArcofe.excoapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.JoexorArcofe.excoapp.Model.Preguntes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.sql.SQLOutput;

public class Quiz extends AppCompatActivity {
    Button r1, r2, r3, r4;
    TextView pregunta;
    int total = 1;
    int correct = 0;
    int wrong = 0;
    DatabaseReference reference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        r1 = (Button) findViewById(R.id.respuesta1);
        r2 = (Button) findViewById(R.id.respuesta2);
        r3 = (Button) findViewById(R.id.respuesta3);
        r4 = (Button) findViewById(R.id.respuesta4);

        pregunta = (TextView) findViewById(R.id.preguntes);
        System.out.println("Antes del Update");
        updateQuestions();

    }

    private void updateQuestions() {
        System.out.println("Dins del Update");
        if (total > 10) {
            Intent intent = new Intent(this, Inici.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            reference = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(total));
            System.out.println("Aqui BD: " + reference);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Preguntes question = snapshot.getValue(Preguntes.class);

                    /*pregunta.setText(question.getQuestion());
                    r1.setText(question.getOption1());
                    r2.setText(question.getOption2());
                    r3.setText(question.getOption3());
                    r4.setText(question.getOption4());
*/
                    String p = snapshot.child("Question").getValue().toString();
                    String op1 = snapshot.child("Option1").getValue().toString();
                    String op2 = snapshot.child("Option2").getValue().toString();
                    String op3 = snapshot.child("Option3").getValue().toString();
                    String op4 = snapshot.child("Option4").getValue().toString();
                    String r = snapshot.child("Answere").getValue().toString();
                    pregunta.setText(p);
                    r1.setText(op1);
                    r2.setText(op2);
                    r3.setText(op3);
                    r4.setText(op4);
                    System.out.println(question.getOption1());
                    r1.setOnClickListener(v -> {
                        if (r1.getText().toString().equals(question.getAnswer())) {
                            r1.setBackgroundColor(Color.GREEN);
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                Toast.makeText(Quiz.this, "Resposta Correcte", Toast.LENGTH_SHORT).show();
                                correct++;
                                r1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                updateQuestions();
                            }, 1500);
                        } else {
                            wrong++;
                            r1.setBackgroundColor(Color.RED);

                            if (r2.getText().toString().equals(question.getAnswer())) {

                                r2.setBackgroundColor(Color.GREEN);

                            } else if (r3.getText().toString().equals(question.getAnswer())) {

                                r3.setBackgroundColor(Color.GREEN);

                            } else if (r4.getText().toString().equals(question.getAnswer())) {

                                r4.setBackgroundColor(Color.GREEN);

                            }

                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                correct++;
                                r1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                updateQuestions();
                            }, 1500);

                        }

                    });

                    r2.setOnClickListener(v -> {
                        //if (r2.getText().toString().equals(question.getAnswer())) {
                        if (r2.getText().toString().equals(r)) {
                            r2.setBackgroundColor(Color.GREEN);
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                Toast.makeText(Quiz.this, "Resposta Correcte", Toast.LENGTH_SHORT).show();
                                correct++;
                                r2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                updateQuestions();
                            }, 1500);
                        } else {
                            wrong++;
                            r2.setBackgroundColor(Color.RED);

                            if (r1.getText().toString().equals(question.getAnswer())) {

                                r1.setBackgroundColor(Color.GREEN);

                            } else if (r3.getText().toString().equals(question.getAnswer())) {

                                r3.setBackgroundColor(Color.GREEN);

                            } else if (r4.getText().toString().equals(question.getAnswer())) {

                                r4.setBackgroundColor(Color.GREEN);

                            }

                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                correct++;
                                r1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                updateQuestions();
                            }, 1500);

                        }
                    });

                    r3.setOnClickListener(v -> {
                        if (r3.getText().toString().equals(question.getAnswer())) {
                            r3.setBackgroundColor(Color.GREEN);
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                Toast.makeText(Quiz.this, "Resposta Correcte", Toast.LENGTH_SHORT).show();
                                correct++;
                                r3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                updateQuestions();
                            }, 1500);
                        } else {
                            wrong++;
                            r3.setBackgroundColor(Color.RED);

                            if (r1.getText().toString().equals(question.getAnswer())) {

                                r1.setBackgroundColor(Color.GREEN);

                            } else if (r2.getText().toString().equals(question.getAnswer())) {

                                r2.setBackgroundColor(Color.GREEN);

                            } else if (r4.getText().toString().equals(question.getAnswer())) {

                                r4.setBackgroundColor(Color.GREEN);

                            }

                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                correct++;
                                r1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                updateQuestions();
                            }, 1500);

                        }
                    });

                    r4.setOnClickListener(v -> {
                        if (r4.getText().toString().equals(question.getAnswer())) {
                            r4.setBackgroundColor(Color.GREEN);
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                Toast.makeText(Quiz.this, "Resposta Correcte", Toast.LENGTH_SHORT).show();
                                correct++;
                                r4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                updateQuestions();
                            }, 1500);
                        } else {
                            wrong++;
                            r4.setBackgroundColor(Color.RED);

                            if (r1.getText().toString().equals(question.getAnswer())) {

                                r1.setBackgroundColor(Color.GREEN);

                            } else if (r2.getText().toString().equals(question.getAnswer())) {

                                r2.setBackgroundColor(Color.GREEN);

                            } else if (r3.getText().toString().equals(question.getAnswer())) {

                                r3.setBackgroundColor(Color.GREEN);

                            }

                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                correct++;
                                r1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                updateQuestions();
                            }, 1500);

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
