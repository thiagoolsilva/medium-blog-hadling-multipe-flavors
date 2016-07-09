/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package lopes.br.final_project.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import lopes.br.final_project.R;
import lopes.br.final_project.communication.util.Constants;
import lopes.br.final_project.controller.CommunicationController;

public class MainActivity extends AppCompatActivity {

    private Button btnRequest;

    private Button btnConstant;

    private AsyncRequest asyncRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        //Set the listener and hold the reference to Request Button
        this.btnRequest = (Button)findViewById(R.id.btn_resource_flavor);
        this.btnRequest.setOnClickListener(requestClickListener);

        //Set the listener and hold the reference to Java Set Button
        this.btnConstant = (Button)findViewById(R.id.btn_java_set_flavor);
        this.btnConstant.setOnClickListener(javaSetClickListener);
    }

    private class AsyncRequest extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return CommunicationController.getsInstance().GetMessageFromWebServer(MainActivity.this);
        }

        @Override
        protected void onPostExecute(String resultRequest) {
            super.onPostExecute(resultRequest);

            //Show the result of request.
            //If the request came from production apk than it will show the message "This request came from flavor named production"
            //else it will show the message "This request came from flavor named developed"
            Toast.makeText(MainActivity.this, resultRequest, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Avoid problem when the Activity is destroyed
        if(asyncRequest != null) {
            asyncRequest.cancel(true);
        }
        asyncRequest = null;
    }

    private void startNewRequest() {
        if(asyncRequest == null || asyncRequest.getStatus() != AsyncTask.Status.RUNNING) {
            asyncRequest = new AsyncRequest();
            asyncRequest.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Listener
    ///////////////////////////////////////////////////////////////////////////
    private View.OnClickListener requestClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startNewRequest();
        }
    };

    private View.OnClickListener javaSetClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final String javaSetFlavourMessage = Constants.BUILD_NAME;
            Toast.makeText(MainActivity.this, javaSetFlavourMessage, Toast.LENGTH_SHORT).show();
        }
    };
}
