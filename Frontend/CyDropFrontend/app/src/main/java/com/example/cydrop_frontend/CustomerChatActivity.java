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
   // int fragCount = 0;


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
        WebSocketManager2.getInstance().connectWebSocket("ws://coms-3090-038.class.las.iastate.edu:8080/users/" + "1" + "/conversations/" + "2");
        WebSocketManager2.getInstance().setWebSocketListener(CustomerChatActivity.this);

        /* send button listener */
        sendBtn.setOnClickListener(v -> {
            try {
                // send message
                WebSocketManager2.getInstance().sendMessage(msgEtx.getText().toString());
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

//    public void addMessage(String username, String content) {
//        fragCount++;
//        FragmentManager fragMan = getSupportFragmentManager();
//        FragmentTransaction fragTransaction = fragMan.beginTransaction();
//        //Fragment f = MessageFragment.newInstance(username + ": ", content);
//         Fragment f = MessageFragment.newInstance(VolleySingleton.email + ": ", content);
//        fragTransaction.add(msgTv.getId(), f, "frag" + fragCount);
//        fragTransaction.commit();
//    }

//    @Override
//    public void onWebSocketMessage(String message) {
//       runOnUiThread(()->{
//            String[] messageSplit = message.split(":");
//            if (messageSplit.length > 1) {
//                String[] usernameSplit = messageSplit[0].split("@");
//                String finalUsername = usernameSplit[0].substring(0, 1).toUpperCase() +
//                        usernameSplit[0].substring(1);
//                addMessage(finalUsername, messageSplit[1].trim());
//            } else {
//                String[] systemMessageSplit = message.split(" ");
//                String finalMessage = "";
//                for (int i = 0; i < systemMessageSplit.length; i++) {
//                    String[] tempArr = systemMessageSplit[i].split("@");
//                    if (tempArr.length > 1) {
//                        String finalUsername = tempArr[0].substring(0, 1).toUpperCase() +
//                                tempArr[0].substring(1);
//                        finalMessage += finalUsername + " ";
//                    } else {
//                        finalMessage += tempArr[0] + " ";
//                    }
//                }
//                addMessage("System", finalMessage.trim());
//            }
//        });
//    }

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
            // Create a new TextView for each incoming message
            TextView messageTextView = new TextView(this);
            messageTextView.setText(message);

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
