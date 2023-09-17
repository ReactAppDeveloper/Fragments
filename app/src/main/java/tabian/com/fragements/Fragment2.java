package tabian.com.fragements;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by User on 4/9/2017.
 */

public class Fragment2 extends Fragment{
    private static final String TAG = "Fragment2";
    ImageView ShowSelectedImage;
    Bitmap FixBitmap;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray;
    String ConvertImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_layout, container, false);
        Log.d(TAG, "onCreateView: started.");
        ShowSelectedImage = (ImageView) view.findViewById(R.id.register_image);
//        UploadButton = (Button) view.findViewById(R.id.buttonUpload);
//        UploadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Register();
//            }
//        });
        byteArrayOutputStream = new ByteArrayOutputStream();
        ShowSelectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 0);

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);
        switch (RC) {
            case 0: // Do your stuff here...
                if (RC == 0 && RQC == getActivity().RESULT_OK && I != null && I.getData() != null) {
                    Uri uri = I.getData();
                    try {
                        FixBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                        ShowSelectedImage.setImageBitmap(FixBitmap);
                        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byteArray = byteArrayOutputStream.toByteArray();
                        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        class UpdateEmployee extends AsyncTask<Void, Void, String> {
                            ProgressDialog loading;

                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                                loading = ProgressDialog.show(getActivity(), "REGISTERING...", "Please Wait...", false, false);
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                super.onPostExecute(s);
                                loading.dismiss();
                                Toast.makeText(getActivity(), "Request Sent Successfully! We will contact you soon. Thankyou!", Toast.LENGTH_LONG).show();
                                getActivity().finish();
                            }

                            @Override
                            protected String doInBackground(Void... params) {
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put(Config.KEY_TU_IMAGE, ConvertImage);
                                RequestHandler rh = new RequestHandler();
                                String s = rh.sendPostRequest(Config.REGISTER_TUTOR, hashMap);
                                return s;
                            }
                        }
                        UpdateEmployee ue = new UpdateEmployee();
                        ue.execute();


                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
                break;
//            case 1: // Do your other stuff here...
//                if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {
//                    uri = I.getData();
//                    SelectButton.setText("PDF is Selected");
//                    }
//                break;
        }
    }

}