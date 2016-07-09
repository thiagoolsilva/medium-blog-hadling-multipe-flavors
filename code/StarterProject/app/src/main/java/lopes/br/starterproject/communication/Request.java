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
package lopes.br.starterproject.communication;

import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * The type Request.
 */
public class Request {

    //1.
    //The URL of an APK of production will be http://www.mocky.io/v2/57806d2e1000003c1f62fd31
    //2.
    //The URL of an APK of developed will be http://www.mocky.io/v2/57806d521000002b1f62fd33

    //NOTE: Using the retrofit.You can use the @Path instead of flavor. On this moment, only for study we will use the flavor strategy.
    //NOTE: If you want more details about retrofit. Check this link http://square.github.io/retrofit/

    private final static String API_BASE_URL = "http://www.mocky.io";

    private static OkHttpClient client = new OkHttpClient();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());


    /**
     * Create requests api request.
     *
     * @return the api request
     */
    public static ApiRequest createRequests() {
        Retrofit retrofit = builder.client(client)
                .build();
        return retrofit.create(ApiRequest.class);
    }
}
