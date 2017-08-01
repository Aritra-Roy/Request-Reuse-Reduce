package webtek.cse.uem.biswajit.com.requestresuereduce;

import android.content.Context;

import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Biswajit Paul on 31-03-2017.
 */
public class TwitterAuth {

    private static final String TWITTER_KEY = "oQZHf3ff7dLSgS27eTs1JiIk9";
    private static final String TWITTER_SECRET = "lXvT3GqpwFidnOUviUuZltBVXDjgvs6MO23cyXzPGePFyyFxVU";

    public static void IntializeTwitter(Context context) {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(context, new TwitterCore(authConfig), new Digits.Builder().build());
    }
}
