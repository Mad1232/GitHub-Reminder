package com.example.cydrop_frontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.java_websocket.handshake.ServerHandshake;

public class CustomerChatActivity extends AppCompatActivity implements WebSocketListener {
    private Button returnButton;         // define return button variable
    private Button sendBtn;
    private EditText msgEtx;
    private LinearLayout msgTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_chat);            // link to Login activity XML

        /* initialize UI elements */
        returnButton = findViewById(R.id.btn_return);    // link to return button in the Login activity XML

        /* initialize UI elements */
        sendBtn = (Button) findViewById(R.id.sendBtn);
        msgEtx = (EditText) findViewById(R.id.msgEdt);
        msgTv = (LinearLayout) findViewById(R.id.customer_questions_linear_layout);

        /* connect this activity to the websocket instance */
        WebSocketManager2.getInstance().connectWebSocket("ws://coms-3090-038.class.las.iastate.edu:8080/users/" + "2" + "/conversations/" + "1");
        WebSocketManager2.getInstance().setWebSocketListener(CustomerChatActivity.this);

        /* send button listener */
        sendBtn.setOnClickListener(v -> {
            try {
                // send message
                WebSocketManager2.getInstance().sendMessage(msgEtx.getText().toString());

                String message = "user: " + msgEtx.getText().toString() + "\n";

                // Create a new TextView and set the message text
                TextView messageTextView = new TextView(this);
                messageTextView.setText(message);

                // Add the TextView to the LinearLayout
                msgTv.addView(messageTextView);

                // Clear the EditText after sending
                msgEtx.setText("");

            } catch (Exception e) {
                Log.d("ExceptionSendMessage:", e.getMessage().toString());
            }
        });

        // Button to return
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerChatActivity.this, ClientNavbarMainActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onWebSocketMessage(String message) {
        /**
         * In Android, all UI-related operations must be performed on the main UI thread
         * to ensure smooth and responsive user interfaces. The 'runOnUiThread' method
         * is used to post a runnable to the UI thread's message queue, allowing UI updates
         * to occur safely from a background or non-UI thread.
         */
        runOnUiThread(() -> {
            // String s = msgTv.getText().toString();
            // msgTv.setText(s + "\n"+message);
            String[] parts = message.split(" ", 3);

            // The second part should be the sender, and the rest is the message
            String formattedMessage = parts[1] + ": " + parts[2];

            // Create a new TextView for the formatted message
            TextView messageTextView = new TextView(this);
            messageTextView.setText(formattedMessage);

            // Add the TextView to the LinearLayout
            msgTv.addView(messageTextView);
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onWebSocketError(Exception ex) {

    }

}
