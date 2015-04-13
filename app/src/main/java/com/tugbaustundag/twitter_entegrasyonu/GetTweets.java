package com.tugbaustundag.twitter_entegrasyonu;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;


public class GetTweets extends ActionBarActivity {
    ArrayList< HashMap< String, Object > > veriler;
    ListView list;
    CustomAdapter adapter;
    HashMap< String, Object > map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);

        //StrictMode kullanarak,ağ erişiminin güvenli bir şekilde yapılmasını sağlıyoruz...
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        list = ( ListView )findViewById( R.id.list );
        veriler = new ArrayList< HashMap< String, Object > >();
        //MainActivity sınıfından gelen ,twitter objesini  tanımladım
        Twitter twitter = (Twitter) getIntent().getSerializableExtra("twitter");
        try {
            //twitter objesinde bulunan , kullanıcı bilgilerini almak icin verifyCredentials metodunu kullandım
            User user = twitter.verifyCredentials();

            //twitter objesinde bulunan, kulanıcının twitter anasayfasında bulunan tweetleri aldık
            List<Status> statuses = twitter.getHomeTimeline();

            System.out.println("@" + user.getScreenName() + "isimli kullanıcının anasasayfa tweet leri...");

            //Tweetler  HashMap objesine aktarıldı..CustomAdapter sınıfında bu datalar cekilip arayüze aktardım
            for (Status status : statuses) {
                map = new HashMap<String, Object>();
                map.put("friendProfileImageUrl",status.getUser().getOriginalProfileImageURL());
                map.put("friendName", status.getUser().getName());
                map.put("friendNickname","@" + status.getUser().getScreenName());
                map.put("TweetContent",status.getText());
                veriler.add(map);
            }
            //Custom Listview kullanarak  tweetleri listeledim
            adapter = new CustomAdapter( this, veriler );
            list.setAdapter( adapter );


        } catch (TwitterException e) {
            e.printStackTrace();
            System.out.println("Timeline daki tweet lerle ilgili hata : " + e.getMessage());
            System.exit(-1);
        }


    }

}
