package com.alexis.matatu.Network;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.View;

import com.alexis.matatu.R;
import com.geniusforapp.fancydialog.FancyAlertDialog;

public class CheckInternetConnection {
    Context ctx;

    public CheckInternetConnection(Context context) {
        ctx = context;
    }

    public void checkConnection() {

        if (!isInternetConnected()) {

            final FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(ctx)
                    .setBackgroundColor(R.color.colorPrimaryWhite)
                    .setimageResource(R.drawable.internetconnection)
                    .setTextTitle("No Internet")
                    .setTextSubTitle("Cannot connect to our servers")
                    .setBody(R.string.noconnection)
                    .setPositiveButtonText("Connect Now")
                    .setPositiveColor(R.color.colorAccentGreen)
                    .setOnPositiveClicked((view, dialog) -> {

                        if (isInternetConnected()) {

                            dialog.dismiss();

                        } else {

                            Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(dialogIntent);
                        }
                    })
                    .setBodyGravity(FancyAlertDialog.TextGravity.CENTER)
                    .setTitleGravity(FancyAlertDialog.TextGravity.CENTER)
                    .setSubtitleGravity(FancyAlertDialog.TextGravity.CENTER)
                    .setCancelable(false)
                    .build();
            alert.show();
        }
    }

    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnectedOrConnecting();

    }
}
