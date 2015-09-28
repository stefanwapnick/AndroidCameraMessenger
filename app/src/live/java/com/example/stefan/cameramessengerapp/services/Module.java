package com.example.stefan.cameramessengerapp.services;

import android.util.Log;

import com.example.stefan.cameramessengerapp.infrastructure.Auth;
import com.example.stefan.cameramessengerapp.infrastructure.CameraMessengerApplication;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

public class Module{

    /**
     * Register method called to hookup otto event bus
     * Only called once at application startup
     */
    public static void register(CameraMessengerApplication application) {
        WebService api = createWebService(application);
        new LiveAccountService(api, application);
        new LiveContactsService(api, application);
        new LiveMessagesService(api, application);
    }

    /**
     * Web Service class used to invoke retrofit api calls:  ex:  WebService.getUsers()
     */
    private static WebService createWebService(CameraMessengerApplication application){

        Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .registerTypeAdapter(Calendar.class, new DateDeserializer())
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .create();

        OkHttpClient client = new OkHttpClient();

        // 1. Add client interceptor (For RESPONSE)
        client.interceptors().add(new AuthInterceper(application.getAuth()));

        // 2. Add adapter interceptor (for REQUEST)
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(CameraMessengerApplication.API_ENDPOINT.toString())
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(client))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("X-Student", CameraMessengerApplication.STUDENT_TOKEN);
                    }
                })
                .build();

        return adapter.create(WebService.class);
    }


    private static class AuthInterceper implements Interceptor {

        private final Auth auth;

        public AuthInterceper(Auth auth){
            this.auth = auth;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            // If have auth token, send it along with response
            if(auth.hasAuthToken()){
                request = request.newBuilder().addHeader("Authorization", "Bearer " + auth.getAuthToken()).build();
            }

            // Proceed with the normal Retrofit get / post chain and get response
            Response response = chain.proceed(request);

            // If response successful, just return response for normal processing
            if(response.isSuccessful()){
                return response;
            }

            // If response code is unauthorized (401) then clear auth token
            if(response.code() == 401 && auth.hasAuthToken()){
                auth.setAuthToken(null);
            }

            // Return response for normal processing in Retrofit callback
            return response;
        }
    }

    private static final String[] DATE_FORMATS = new String[]{
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mmZ",
            "yyyy-MM-dd'T'HH:mm",
            "yyyy-MM-dd"
    };

    private static class DateDeserializer implements JsonDeserializer<Calendar> {

        @Override
        public Calendar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            for(String format : DATE_FORMATS){
                try{
                    SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
                    dateFormat.setTimeZone(TimeZone.getDefault());
                    Date date = dateFormat.parse(json.getAsString());
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTimeInMillis(date.getTime() + TimeZone.getDefault().getOffset(0));
                    return calendar;
                }catch (ParseException ignored){}
            }

            throw new JsonParseException("Cannot parse date " + json.getAsString());
        }
    }
}


