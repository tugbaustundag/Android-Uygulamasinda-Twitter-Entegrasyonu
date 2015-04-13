package com.tugbaustundag.twitter_entegrasyonu;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends BaseAdapter   {
    private Activity activity;
    private static LayoutInflater inflater=null;
    public Resources res;

    int i=0;

    ArrayList<HashMap<String, Object>> veri = new ArrayList<HashMap<String, Object>>();

    public CustomAdapter(Activity a, ArrayList<HashMap<String, Object>> veriler ) {
        activity = a;
        veri = veriler;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {

        if(veri.size()<=0)
            return 0;
        return veri.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{

        public TextView nickname;
        public TextView name;
        public TextView tweetContent;
        public ImageView profil_image;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi=convertView;
        ViewHolder holder;

        if(convertView==null){


            vi = inflater.inflate(R.layout.tweetitem, null);

            //Tweet içeriklerinin gösterilceği arayüz elemanlarını tanımladım
            holder=new ViewHolder();
            holder.name=(TextView)vi.findViewById(R.id.name);
            holder.nickname=(TextView)vi.findViewById(R.id.nickname);
            holder.tweetContent=(TextView)vi.findViewById(R.id.tweetContent);
            holder.profil_image=(ImageView)vi.findViewById(R.id.imageView);

            vi.setTag(holder);
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(veri.size()<=0)
        {
            //holder.text.setText("İcerik bulunmuyor");

        }
        else
        {

            //HashMap array objesindeki tweet iceriklerini alıp, arayüz elemanlarına aktardım
            String friendProfileImageUrl=veri.get(position).get("friendProfileImageUrl").toString();

            URL url = null;
            try {
                url = new URL(friendProfileImageUrl);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                holder.profil_image.setImageBitmap(bmp);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            holder.name.setText(veri.get(position).get("friendName").toString());
            holder.nickname.setText(veri.get(position).get("friendNickname").toString());
            holder.tweetContent.setText(veri.get(position).get("TweetContent").toString());

        }
        return vi;
    }





}
