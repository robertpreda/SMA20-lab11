package com.upt.cti.smartwallet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppState {
    private static AppState singletonObject;
    private String uid;
    public static synchronized AppState get(){
        if(singletonObject == null){
            singletonObject = new AppState();
        }
        return singletonObject;
    }
    private DatabaseReference databaseReference;
    private Payment currentPayment;
    public DatabaseReference getDatabaseReference(){
        return this.databaseReference;
    }
    public void setDatabaseReference(DatabaseReference databaseReference){
        this.databaseReference = databaseReference;
    }
    public void setCurrentPayment(Payment currentPayment){
        this.currentPayment = currentPayment;
    }
    public Payment getCurrentPayment(){
        return this.currentPayment;
    }
    public static String getCurrentTimeDate(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return sdfDate.format(now);
    }

    public String getUid(){
        return this.uid;
    }
    public void setUid(String uid){
        this.uid = uid;
    }

    public void updateLocalBackup(Context context, Payment payment, boolean toAdd){
        String fileName = payment.timestamp;
        try{
            if(toAdd)
            {
                FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(payment);
                os.close();
                fos.close();
            }else{
                context.deleteFile(fileName);
            }
        }catch (IOException e){
            Toast.makeText(context, "Cannot access local data", Toast.LENGTH_LONG).show();
        }
    }

    public boolean hasLocalStorage(Context context){
        return context.getFilesDir().listFiles().length > 0;
    }
    public List<Payment> loadFromLocalBackup(Context context, String month){
        try{
            List<Payment> payments = new ArrayList<>();
            for(File file: context.getFilesDir().listFiles()){
                try{
                    FileInputStream fis = context.openFileInput(file.getName());
                    ObjectInputStream is = new ObjectInputStream(fis);
                    Payment payLoaded = (Payment)is.readObject();
                    is.close();
                    fis.close();

                    if(ListActivity.Month.intToMonthName(Integer.parseInt(payLoaded.timestamp.substring(5,7))) == ListActivity.Month.valueOf(month)){
                            payments.add(payLoaded);
                    }



                }catch(Exception e ){

                }
                return payments;
            }
        }catch (Exception e){

        }
        return null;
    }

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager conMan =
                (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = conMan.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
