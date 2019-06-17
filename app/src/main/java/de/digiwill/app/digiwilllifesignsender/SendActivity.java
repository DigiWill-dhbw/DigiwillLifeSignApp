package de.digiwill.app.digiwilllifesignsender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class SendActivity extends AppCompatActivity {

    private Button btSendLifeSign;
    private TextView txtInstruct;
    private DigiWillService d = new DigiWillService();
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        final Intent i = getIntent();
        username = i.getStringExtra("username");
        password = i.getStringExtra("password");
        btSendLifeSign = findViewById(R.id.send_life_sign);
        txtInstruct = findViewById(R.id.txtinstruct);
        Runnable r = new Runnable() {
            @Override
            public void run(){
                try {
                    if(d.login(username, password)) {
                        SendActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btSendLifeSign.setVisibility(View.VISIBLE);
                                txtInstruct.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        i.putExtra("error", true);
                        finish();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread( r ).start();
        btSendLifeSign.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Runnable r = new Runnable() {

                    @Override
                    public void run(){

                        try {
                            d.sendLifeSign();
                            SendActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SendActivity.this,
                                            getString(R.string.life_sign_success), Toast.LENGTH_LONG).show();
                                }
                            });

                        } catch (IOException e) {
                            SendActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SendActivity.this,
                                            getString(R.string.life_sign_no_success), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                };

                new Thread( r ).start();
            }
        });
    }
}
