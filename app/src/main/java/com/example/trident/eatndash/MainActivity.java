package com.example.trident.eatndash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    Button customerButton, adminButton;
    CallbackManager callbackManager;
    SharedPreferences sharedPre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customerButton = (Button) findViewById(R.id.button_login);
        adminButton = (Button) findViewById(R.id.admin_button);
        //Handle customer button
        customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                customerButton.setTextColor(getResources().getColor(android.R.color.white));

                Intent intent = new Intent(getApplicationContext(), CustomerMainActivity.class);
                startActivity(intent);

                adminButton.setBackgroundColor(getResources().getColor(android.R.color.white));
                adminButton.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });
        //Handle Admin button
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                adminButton.setTextColor(getResources().getColor(android.R.color.white));

                Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                startActivity(intent);

                customerButton.setBackgroundColor(getResources().getColor(android.R.color.white));
                customerButton.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });

        //Handle facebook button
        Button buttonLogin = (Button) findViewById(R.id.facebook);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(AccessToken.getCurrentAccessToken() == null)
                  {
                   LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile", "email"));
                /*}else {
                    ColorDrawable customerbuttonColor = (ColorDrawable) customerButton.getBackground();
                    if (customerbuttonColor.getColor() == getResources().getColor(R.color.colorAccent)) {
                        loginToServer(AccessToken.getCurrentAccessToken().getToken(), "Customer");
                    } else {
                        loginToServer(AccessToken.getCurrentAccessToken().getToken(), "Admin");
                    }
                */}
            }
        });


        callbackManager = CallbackManager.Factory.create();
        //sharedPre = getSharedPreferences("KEY", Context.MODE_PRIVATE);

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("FACEBOOK TOKEN", loginResult.getAccessToken().getToken());

                       /* GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        Log.d("FACEBOOK DETAILS", object.toString());
                                        SharedPreferences.Editor editor = sharedPre.edit();

                                        try {
                                            editor.putString("name", object.getString("name"));
                                            editor.putString("email", object.getString("email"));
                                            editor.putString("avatar", object.getJSONObject("picture").getJSONObject("info").getString("url"));
                                        } catch(JSONException e){
                                            e.printStackTrace();
                                        }
                                        editor.commit();
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,picture");
                        request.setParameters(parameters);
                        request.executeAsync();

                        ColorDrawable customerbuttonColor = (ColorDrawable) customerButton.getBackground();
                        if (customerbuttonColor.getColor() == getResources().getColor(R.color.colorAccent)) {
                            loginToServer(AccessToken.getCurrentAccessToken().getToken(), "Customer");
                        } else {
                            loginToServer(AccessToken.getCurrentAccessToken().getToken(), "Admin");
                        }
                    */}

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException exception) {

                    }
                });

        //if(AccessToken.getCurrentAccessToken() != null){
            //Log.d( "USER", sharedPre.getAll().toString());
            //buttonLogin.setText("Continue as " + sharedPre.getString("email", ""));
        //}

        // Example of a call to a native method
        //TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

   /*private void loginToServer(String facebookAcessToken, final String userType){
        String url = "http://144.37.233.31:8000/api/social/convert-token";

        JSONObject jsonBody = new JSONObject();
        try{
            jsonBody.put("grant_type", "convert_token");
            jsonBody.put("client_id", "");
            jsonBody.put("client_secret", "");
            jsonBody.put("backend", "facebook");
            jsonBody.put("token", "facebookAccessToken");
            jsonBody.put("user_type", userType);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Login to Server", response.toString());
                        SharedPreferences.Editor editor = sharedPre.edit();

                        try{
                            editor.putString("token",response.getString("access token"));
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                        editor.commit();

                        if(userType.equals("Customer")){
                            Intent intent = new Intent(getApplicationContext(), CustomerMainActivity.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getApplicationContext(),AdminMainActivity.class);
                            startActivity(intent);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    */}

