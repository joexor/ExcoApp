package com.JoexorArcofe.excoapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.JoexorArcofe.excoapp.Model.Preguntes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.google.protobuf.StringValue;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class Quiz extends AppCompatActivity {

    final int MAX_PREGUNTES = 10;

    Button r1, r2, r3, r4;
    TextView pregunta, user, numQuestion;
    ImageButton ajustes;
    int total = 1;
    int correct = 0;
    int wrong = 0;
    DatabaseReference reference;
    String tipo = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        user = (TextView) findViewById(R.id.usuario);
        ajustes = (ImageButton) findViewById(R.id.ajustesTema);
        r1 = (Button) findViewById(R.id.respuesta1);
        r2 = (Button) findViewById(R.id.respuesta2);
        r3 = (Button) findViewById(R.id.respuesta3);
        r4 = (Button) findViewById(R.id.respuesta4);
        pregunta = (TextView) findViewById(R.id.preguntes);
        numQuestion = (TextView) findViewById(R.id.numPregunta);

        FirebaseUser email = FirebaseAuth.getInstance().getCurrentUser();

        String mail = email.getEmail();
        String[] usuario = mail.split(String.valueOf('@'));
        user.setText(usuario[0]);

        r1.setBackgroundColor(Color.parseColor("#03A9F4"));
        r2.setBackgroundColor(Color.parseColor("#03A9F4"));
        r3.setBackgroundColor(Color.parseColor("#03A9F4"));
        r4.setBackgroundColor(Color.parseColor("#03A9F4"));
        Intent i = getIntent();
        tipo = i.getStringExtra("type");
        updateQuestions();
        ajustes.setOnClickListener(v->{
            Intent intent = new Intent(this, Ajustes.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("type",tipo);
            startActivity(intent);
        });

    }

    private void updateQuestions() {
        Intent i = getIntent();
        String tema = i.getStringExtra("Tema");
        if (total > MAX_PREGUNTES) {
            Intent intent = new Intent(this, Resultats.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("correcte", String.valueOf(correct));
            intent.putExtra("incorrecte",String.valueOf(wrong));
            intent.putExtra("Tema",tema);
            intent.putExtra("type",tipo);
            startActivity(intent);
        } else {
            if(tema.equals("Historia")) {
                reference = FirebaseDatabase.getInstance().getReference().child("Questions").child("Historia").child(String.valueOf(total));
            }else if(tema.equals("Entretenimiento")) {
                reference = FirebaseDatabase.getInstance().getReference().child("Questions").child("Entretenimiento").child(String.valueOf(total));
            }else if(tema.equals("Ciencia")) {
                reference = FirebaseDatabase.getInstance().getReference().child("Questions").child("Ciencia").child(String.valueOf(total));
            }//else{
                //reference = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(total));
            //}
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //Preguntes question = snapshot.getValue(Preguntes.class);
                    Preguntes question = new Preguntes(snapshot.child("Question").getValue().toString(),
                            snapshot.child("Option1").getValue().toString(), snapshot.child("Option2").getValue().toString(),
                            snapshot.child("Option3").getValue().toString(), snapshot.child("Option4").getValue().toString(),
                            snapshot.child("Answer").getValue().toString());

                    numQuestion.setText(total+"/"+MAX_PREGUNTES);
                    pregunta.setText(question.getQuestion());
                    r1.setText(question.getOption1());
                    r2.setText(question.getOption2());
                    r3.setText(question.getOption3());
                    r4.setText(question.getOption4());

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
                            }, 1000);
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
                                r1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                updateQuestions();
                            }, 1000);

                        }

                    });

                    r2.setOnClickListener(v -> {
                        if (r2.getText().toString().equals(question.getAnswer())) {
                            r2.setBackgroundColor(Color.GREEN);
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                Toast.makeText(Quiz.this, "Resposta Correcte", Toast.LENGTH_SHORT).show();
                                correct++;
                                r2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                updateQuestions();
                            }, 1000);
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
                                r1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                updateQuestions();
                            }, 1000);

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
                            }, 1000);
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
                                r1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                updateQuestions();
                            }, 1000);

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
                            }, 1000);
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
                                r1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                r4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                total++;
                                updateQuestions();
                            }, 1000);

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
