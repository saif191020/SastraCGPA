package com.sba.sastracgpa;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;

    String[] Title = new String[15];
    String[] Dates = new String[15];
    String[] Links = new String[15];
    private static final String TAG = "NEWS FARG";
    ProgressDialog progressDialog;
    int itemCount = 0;
    public static final String LOGTAG = "com.sba.MyLog";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.newsfragment, container, false);
        recyclerView = view.findViewById(R.id.news_Recycler);
        new Newses().execute();

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    public class Newses extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loding Content...");
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
               /* final Document document = Jsoup.connect("https://www.sastra.edu").get();
                Log.i(LOGTAG,"Connected");

                for (Element row : document.select("div.gn_news")) {
                    Links[itemCount] = (row.select("a").first()).attr("abs:href");
                    Title[itemCount++] = row.text();
                    Log.i(LOGTAG,itemCount+ Links[itemCount-1]);
                    if(itemCount==12)
                        break;
                }
*/
                final Document document = Jsoup.connect("https://www.sastra.edu/running-news.html").get();
                Log.i(LOGTAG, "Connected");
                int i = -1;
                for (Element row : document.select("table.table.table-striped.table-bordered.table-hover tr")) {
                    if (++i == 0)
                        continue;
                    Title[itemCount] = row.select(".list-title a").text();
                    Dates[itemCount] = (row.select(".list-title").text()).replace(Title[itemCount], " ").trim();
                    Links[itemCount++] = (row.select("a").first()).attr("abs:href");
                    Log.i(LOGTAG, Dates[itemCount - 1]);
                }
            } catch (org.jsoup.HttpStatusException e) {
                if (e.getStatusCode() == 404) {
                    final Document document;
                    try {
                        document = Jsoup.connect("https://www.sastra.edu/home/running-news.html").get();

                        Log.i(LOGTAG, "Connected");
                        int i = -1;
                        for (Element row : document.select("table.table.table-striped.table-bordered.table-hover tr")) {
                            if (++i == 0)
                                continue;
                            Title[itemCount] = row.select(".list-title a").text();
                            Dates[itemCount] = (row.select(".list-title").text()).replace(Title[itemCount], " ").trim();
                            Links[itemCount++] = (row.select("a").first()).attr("abs:href");
                            Log.i(LOGTAG, Dates[itemCount - 1]);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            NewsAddapter newsAddapter = new NewsAddapter(getActivity(), Title, Links, Dates, itemCount);
            progressDialog.dismiss();
            Log.i(LOGTAG, itemCount + "");
            recyclerView.setAdapter(newsAddapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }
}
