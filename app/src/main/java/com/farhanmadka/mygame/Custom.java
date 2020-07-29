package com.farhanmadka.mygame;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class Custom {
    Dialog dialog, dialog1;
    ImageView imgTitle,imageView,imgfacebook, imgxarunta;
    TextView txtTitle,footer,subCon, txtMessage, txtxarunta, txtxarunttile;
    Button btnCall;
    CardView cardView;
    GridLayout mainGrid;


    public void customDialog(Context context, int icon, String Title, String message,
                             String subContent, String foot){
       /* RelativeLayout rl;
        rl = dialog.findViewById(R.id.relative);
        ZoomControls zoomControls;
        zoomControls = new ZoomControls(context);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.bottomMargin=40;
        zoomControls.setLayoutParams(params);
        */
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.customalert);
        dialog.setVolumeControlStream(icon);
        footer =dialog.findViewById(R.id.footer);
        imageView = dialog.findViewById(R.id.imgClose);
        txtTitle = dialog.findViewById(R.id.txtTitle);
        footer.setText(foot);
        imgTitle = dialog.findViewById(R.id.imageTitle);
        txtMessage = dialog.findViewById(R.id.txtMessageInfo);
        subCon = dialog.findViewById(R.id.subContent);
        subCon.setText(subContent);
        imgTitle.setImageResource(icon);
        txtTitle.setText(Title);
        txtMessage.setText(message);
        dialog.setCancelable(false);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }
}
