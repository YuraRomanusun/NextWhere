package com.puma.nextwhere.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.widget.Toast;

import com.puma.nextwhere.model.LoginApiResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rajesh on 17/8/17.
 */

public class Utils {

    public static void showToast(Context context, String message) {
        if (context == null) {
            return;
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean validateList(Object data) {
        return data instanceof List && ((List) data).size() > 0 && ((List) data).get(0) != null;
    }

    public static double parseDouble(String value) {
        double defaultValue = 0;
        try {
            defaultValue = Double.parseDouble(value);
        } catch (Exception e) {
            //
        }
        return defaultValue;
    }

    public static String extractVideoIdFromUrl(String url) {

        String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);
        final String[] videoIdRegex = {"\\?vi?=([^&]*)", "watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};
        for (String regex : videoIdRegex) {
            Pattern compiledPattern = Pattern.compile(regex);
            Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);

            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        return null;
    }

    private static String youTubeLinkWithoutProtocolAndDomain(String url) {
        final String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
        Pattern compiledPattern = Pattern.compile(youTubeUrlRegEx);
        Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find()) {
            return url.replace(matcher.group(), "");
        }
        return url;
    }

    public static int parseInt(String value) {
        int defaultValue = 0;
        try {
            defaultValue = Integer.parseInt(value);
        } catch (Exception e) {
            //
        }
        return defaultValue;
    }

    public static boolean isObjectNotNull(LoginApiResponse userInfo) {
        return userInfo != null;
    }

    public static int getMenuHeight(Activity activity, int dp_180) {
        Point point = new Point();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getSize(point);
        return point.y - dp_180;
    }

    public static Date getDateFromString(String dtStart, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static boolean isWithInGiveDate(Date toCompare, Date start, Date end) {
        return !(toCompare == null || start == null || end == null)
                && start.compareTo(toCompare) * toCompare.compareTo(end) >= 0;
    }

}
