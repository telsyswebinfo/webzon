package com.webzon.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.webzon.R;



public abstract class ServerRequest<T> {

    private Context mContext;
    private Call<T> call;
    private Dialog dialog;
    private TextView txtMsgTV;

    public ServerRequest(final Context mContext, Call<T> call, boolean showProgress) {
        this.mContext = mContext;
        this.call = call;
        progressDialog();

        if (Utils.isNetworkConnected(mContext)) {
            // final Dialog pd = new Dialog(mContext);
            if (showProgress) {
                //  pd.show();
                startProgressDialog();
            }

            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    try {
//                        if (pd.isShowing()) {
//                            pd.dismiss();
                        stopProgressDialog();
                        //   }
                    } catch (Exception e) {

                    }

                     afterResponse(mContext, call, response);
                }

                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    try {
                       /* if (pd.isShowing()) {
                            pd.dismiss();*/
                        stopProgressDialog();
                        ////}
                    } catch (Exception e) {

                    }

                    Utils.showLog("Exp : " + t.getMessage());
                    Utils.showMessageDialog(mContext, mContext.getString(R.string.app_says), mContext.getString(R.string.something_went_wrong));
                }
            });
        } else {
            stopProgressDialog();
            Utils.showMessageDialog(mContext, mContext.getString(R.string.app_says), mContext.getString(R.string.no_internet_message));
        }
    }

    public ServerRequest(final Context mContext, Call<T> call, final SwipeRefreshLayout mRefresh) {

        if (Utils.isNetworkConnected(mContext)) {

            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    mRefresh.setRefreshing(false);
                    afterResponse(mContext, call, response);
                }

                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    mRefresh.setRefreshing(false);
                    Utils.showLog("Exp : " + t.getMessage());
                    Utils.showMessageDialog(mContext, mContext.getString(R.string.app_says), mContext.getString(R.string.something_went_wrong));
                }
            });
        } else {
            mRefresh.setRefreshing(false);
            Utils.showMessageDialog(mContext, mContext.getString(R.string.app_says), mContext.getString(R.string.no_internet_message));
        }
    }


    private void progressDialog() {
        dialog = new Dialog(mContext);
        View view = View.inflate(mContext, R.layout.progress_dialog, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        txtMsgTV = (TextView) view.findViewById(R.id.txtMsgTV);
        dialog.setCancelable(false);
    }

    public void startProgressDialog() {
        if (dialog != null && !dialog.isShowing()) {
            try {
                dialog.show();
            } catch (Exception e) {
            }
        }
    }

    public void stopProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
        }
    }

    private void afterResponse(Context mContext, Call<T> call, Response<T> response) {
        if (response.body() != null)
            onCompletion(call, response);
    }

    public abstract void onCompletion(Call<T> call, Response<T> response);

}
