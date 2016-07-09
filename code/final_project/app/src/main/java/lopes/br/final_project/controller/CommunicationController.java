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
package lopes.br.final_project.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;

import java.io.IOException;

import lopes.br.final_project.R;
import lopes.br.final_project.communication.ApiRequest;
import lopes.br.final_project.communication.Request;
import lopes.br.final_project.communication.model.MessageResponse;
import retrofit.Response;

/**
 * The type Communication controller.
 */
public class CommunicationController {

    private static final String TAG = "request";

    private static CommunicationController sInstance;

    private static Object lockObj = new Object();

    private CommunicationController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CommunicationController getsInstance() {
        synchronized (lockObj) {
            if (sInstance == null) {
                sInstance = new CommunicationController();
            }
            return sInstance;
        }
    }

    /**
     * Get message from web server
     *
     * @return The message received
     */
    @WorkerThread
    public String GetMessageFromWebServer(@NonNull Context context) {
        String responseMessage = "";
        ApiRequest apiRequest = Request.createRequests();

        try {
            final String typeOfFlavor = context.getString(R.string.id_message_request);
            Response<MessageResponse> request = apiRequest.GETMessage(typeOfFlavor).execute();
            if(request != null && request.body() != null) {
                MessageResponse bodyRequest = request.body();
                responseMessage = bodyRequest.getMessage();

                Log.d(TAG, "result:: "+responseMessage);

            }
        } catch (IOException e) {
            Log.d(TAG, "error on request:: "+e.getMessage());
        } catch (Exception e) {
            Log.d(TAG, "Generic error::"+ e.getMessage());
        }
        return responseMessage;
    }
}
