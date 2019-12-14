package iot.hustler.io.easydictionary;

import android.support.annotation.NonNull;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sayi on 07-10-2017.
 */
/*   Copyright [2018] [Sayi Manoj Sugavasi]

   Licensed under the Apache License, Version 2.0 (the "License")
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.*/
public class JsonObjectRequestWithAuthHeader extends JsonObjectRequest {
    public JsonObjectRequestWithAuthHeader(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @NonNull
    @Override
    public Map<String, String> getHeaders() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("content-type", "application/json");
        hashMap.put("x-rapidapi-host", "wordsapiv1.p.rapidapi.com");
        hashMap.put("x-rapidapi-key", "WUUA78FYeTmshbpfAeJ5uKOFRhPgp1hacl3jsnhqLE6o4bqWf8");
        return hashMap;
    }
}
