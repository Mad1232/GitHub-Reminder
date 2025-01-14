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

/**
 * VetChatActivity manages the chat interface for vets, allowing them to connect to the Websocket server,
 * send and receive messages, and handle user interface updates.
 *
 * @author Madison Vosburg
 */
public class VetChatActivity extends AppCompatActivity implements WebSocketListener{

    /**
     * Button to return to the previous activity.
     */
    private Button returnButton;

    /**
     * Button to send a message.
     */
    private Button sendBtn;

    /**
     * EditText for inputting a message to be sent.
     */
    private EditText msgEtx;

    /**
     * LinearLayout container to display messages in the chat.
     */
    private LinearLayout msgTv;

    /**
     * Initializes the chat interface, connects to the websocket, and sets up button listeners for send and return.
     * Send button captures user input and sends through websocket.
     * Return button navigates to VetNavbarMainActivity.
     *
     * @param savedInstanceState Bundle containing the saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_chat);            // link to Login activity XML

            /* initialize UI elements */
            returnButton = findViewById(R.id.btn_return);    // link to return button in the Login activity XML

            /* initialize UI elements */
            sendBtn = (Button) findViewById(R.id.sendBtn);
            msgEtx = (EditText) findViewById(R.id.msgEdt);
            msgTv = (LinearLayout) findViewById(R.id.vet_questions_linear_layout);


            /* connect this activity to the websocket instance */
            WebSocketManager2.getInstance().connectWebSocket("ws://coms-3090-038.class.las.iastate.edu:8080/vet/" + "1" + "/conversations/" + "2");
            WebSocketManager2.getInstance().setWebSocketListener(VetChatActivity.this);

            /* send button listener */
            sendBtn.setOnClickListener(v -> {
                try {
                    // send message
                    WebSocketManager2.getInstance().sendMessage(msgEtx.getText().toString());

                    String message = "vet: " + msgEtx.getText().toString() + "\n";

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
                    Intent intent = new Intent(VetChatActivity.this, VetNavbarMainActivity.class);
                    startActivity(intent);
                }
            });

        }

    /**
     * Callback method for receiving Websocket messages.
     * Formats and displays incoming messsages on the chat UI.
     *
     * @param message The received WebSocket message.
     */
    @Override
    public void onWebSocketMessage(String message) {
        /**
         * In Android, all UI-related operations must be performed on the main UI thread
         * to ensure smooth and responsive user interfaces. The 'runOnUiThread' method
         * is used to post a runnable to the UI thread's message queue, allowing UI updates
         * to occur safely from a background or non-UI thread.
         */
        runOnUiThread(() -> {
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

    /**
     * Callback method for Websocket closure.
     * Handles the closure of Websocket connection.
     *
     * @param code   The status code indicating the reason for closure.
     * @param reason A human-readable explanation for the closure.
     * @param remote Indicates whether the closure was initiated by the remote endpoint.
     */
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    /**
     * Callback method for Websocket opening.
     * Handles actions when Websocket connection is opened.
     *
     * @param handshakedata Information about the server handshake.
     */
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    /**
     * Callback method for Websocket errors.
     * Logs or handles any errors that occur.
     *
     * @param ex The exception that describes the error.
     */
    @Override
    public void onWebSocketError(Exception ex) {

    }

}
