package com.webzon.ApiClass;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class WebServiceHandler {

    // private OkHttpClient client = new OkHttpClient();
    private RequestBody requestBody;
    private OkHttpClient okHttpClient;
    private Request request;
    private Context context;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public CustomProgressDialog progressDialog;
    public WebServiceListener serviceListener;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public WebServiceHandler(Context context) {
        this.context = context;
       /* CustomTrust customTrust = new CustomTrust(context);
        OkHttpClient client = customTrust.getClient();*/
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                /*.protocols(Util.immutableList(Protocol.HTTP_1_1))*/
                .build();
        AndroidNetworking.initialize(context, okHttpClient);
        try {
            progressDialog = new CustomProgressDialog(context);
//            progressDialog.setMessage(context.getResources().getString(R.string.please_wait));
//            progressDialog.setCancelable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
        /*try {
            ProviderInstaller.installIfNeeded(context);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLSv1.2");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, null, null);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        SSLEngine engine = sslContext.createSSLEngine();*/
    }





    public void POST(HashMap<String,String> hashMap, String url)
        {
            Log.w("URL",url);
            Log.w("Key Pair",hashMap+"");
            try {
                progressDialog.showCustomDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
            AndroidNetworking.post(url)

                    .addBodyParameter(hashMap)
                    .setTag(this)

                    .setPriority(Priority.MEDIUM)
                    .build()
                    .setAnalyticsListener(new AnalyticsListener() {
                        @Override
                        public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        }
                    })
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response Success", response);
                            if (progressDialog.isDialogShowing())
                                progressDialog.dissmissDialog();
                            serviceListener.onResponse(response);
                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.d("ERROR",  error.getErrorBody());
                            error.printStackTrace();
                            // handle error
                            if (progressDialog.isDialogShowing())
                                progressDialog.dissmissDialog();
                        }

                    });

        }

    public void NewPost(HashMap<String,String> hashMap, String url)
    {
        Log.w("URL",url);
        Log.w("Key Pair",hashMap+"");
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(url)

                .addBodyParameter(hashMap)
                .setTag(this)

                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                    }
                })
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response Success", response);

                        serviceListener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("ERROR",  error.getErrorBody());
                        error.printStackTrace();
                        // handle error

                    }

                });

    }

    public void extraPost(JSONObject hashMap, String url)
        {
            Log.w("URL",url);
            Log.w("Key Pair",hashMap+"");
            try {
                progressDialog.showCustomDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
            AndroidNetworking.post(url)

                    .addBodyParameter(hashMap)
                    .setTag(this)

                    .setPriority(Priority.MEDIUM)
                    .build()
                    .setAnalyticsListener(new AnalyticsListener() {
                        @Override
                        public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        }
                    })


                    .getAsOkHttpResponse(new OkHttpResponseListener() {
                        @Override
                        public void onResponse(Response response) {

                            if (progressDialog.isDialogShowing())
                                progressDialog.dissmissDialog();

                            ResponseBody data=response.body();
                            String json=data.toString();
                              serviceListener.onResponse(json.toString());

                            Log.w("Error",response.code()+"@@@@@");
                            Log.w("body",response.body()+"@@@@@");
                            Log.w("data",data.toString()+"@@@@@");


                            System.out.println(response.code());

                        }

                 /*   .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response Success", response);

                            if (progressDialog.isDialogShowing())
                                progressDialog.dissmissDialog();
                            serviceListener.onResponse(response);
                        }
                 */       @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.d("ERROR",  error.getErrorBody());
                            error.printStackTrace();
                            // handle error
                            if (progressDialog.isDialogShowing())
                                progressDialog.dissmissDialog();
                        }

                    });

        }


        public void GET(HashMap<String,String> hashMap,String url)
        {
            Log.w("urlrulrul",url);
            try {
                progressDialog.showCustomDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
            AndroidNetworking.get(url)
                    .addHeaders(hashMap)
                    .setTag(this)
                    .setPriority(Priority.MEDIUM)

                    .setPriority(Priority.MEDIUM)
                    .build()
                    .setAnalyticsListener(new AnalyticsListener() {
                        @Override
                        public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        }
                    })
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response Success", response);
                            if (progressDialog.isDialogShowing())
                                progressDialog.dissmissDialog();
                            serviceListener.onResponse(response);
                        }

               /*     .getAsOkHttpResponse(new OkHttpResponseListener() {
                        @Override
                        public void onResponse(Response response) {

                            if (progressDialog.isDialogShowing())
                                progressDialog.dissmissDialog();

                            ResponseBody data=response.body();
                            serviceListener.onResponse(data.toString());

                            Log.w("Error",response.code()+"@@@@@");
                            Log.w("body",response.body()+"@@@@@");


                            System.out.println(response.code());

                        }

*/
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.d("ERROR",  error.getErrorBody());
                            error.printStackTrace();
                            // handle error
                            if (progressDialog.isDialogShowing())
                                progressDialog.dissmissDialog();
                        }

                    });

        }
        public void GETDirect(String url)
        {
            Log.w("urlrulrul",url);
            try {
                progressDialog.showCustomDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
            AndroidNetworking.get(url)
                    .setTag(this)
                    .setPriority(Priority.MEDIUM)

                    .setPriority(Priority.MEDIUM)
                    .build()
                    .setAnalyticsListener(new AnalyticsListener() {
                        @Override
                        public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        }
                    })
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response Success", response);
                            if (progressDialog.isDialogShowing())
                                progressDialog.dissmissDialog();
                            serviceListener.onResponse(response);
                        }

               /*     .getAsOkHttpResponse(new OkHttpResponseListener() {
                        @Override
                        public void onResponse(Response response) {

                            if (progressDialog.isDialogShowing())
                                progressDialog.dissmissDialog();

                            ResponseBody data=response.body();
                            serviceListener.onResponse(data.toString());

                            Log.w("Error",response.code()+"@@@@@");
                            Log.w("body",response.body()+"@@@@@");


                            System.out.println(response.code());

                        }

*/
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.d("ERROR",  error.getErrorBody());
                            error.printStackTrace();
                            // handle error
                            if (progressDialog.isDialogShowing())
                                progressDialog.dissmissDialog();
                        }

                    });

        }


    public void DELETE(String url)
        {
            Log.w("urlrulrul",url);
            try {
                progressDialog.showCustomDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
            AndroidNetworking.delete(url)
                    .setTag(this)
                    .setPriority(Priority.MEDIUM)

                    .setPriority(Priority.MEDIUM)
                    .build()
                    .setAnalyticsListener(new AnalyticsListener() {
                        @Override
                        public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        }
                    })
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response Success", response);
                            if (progressDialog.isDialogShowing())
                                progressDialog.dissmissDialog();
                            serviceListener.onResponse(response);
                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.d("ERROR",  error.getErrorBody());
                            error.printStackTrace();
                            // handle error
                            if (progressDialog.isDialogShowing())
                                progressDialog.dissmissDialog();
                        }

                    });

        }


}
