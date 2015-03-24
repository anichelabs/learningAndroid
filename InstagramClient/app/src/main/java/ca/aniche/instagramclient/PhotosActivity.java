package ca.aniche.instagramclient;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ca.aniche.instagramclient.managers.InstagramService;
import ca.aniche.instagramclient.managers.InstagramServiceManager;
import ca.aniche.instagramclient.models.instagram.InstagramItem;
import ca.aniche.instagramclient.models.instagram.PopularItemResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class PhotosActivity extends ActionBarActivity {

    private static final String TAG = "InstagramPhotosActivity";
    StickyListHeadersListView stickyLVPhotos;
    List<InstagramItem> instagramItems;
    InstagramPhotosAdapter photosAdapter;

    private SwipeRefreshLayout swipeContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        instagramItems = new ArrayList<>();
        photosAdapter = new InstagramPhotosAdapter(this, instagramItems);
        stickyLVPhotos = (StickyListHeadersListView) findViewById(R.id.lv_photos);
        stickyLVPhotos.setAdapter(photosAdapter);
        fetchPopularItems();
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularItems();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



    }

    private void fetchPopularItems() {

        InstagramService instagramService = new InstagramServiceManager().getServiceInterface();
        instagramService.getPopularItemsAsync(InstagramServiceManager.CLIENT_ID, new Callback<PopularItemResponse>() {
            @Override
            public void success(PopularItemResponse popularItemResponse, Response response) {
                Log.i(InstagramServiceManager.TAG, "Received response from service" + response.getHeaders());
                swipeContainer.setRefreshing(false);
                instagramItems.addAll(popularItemResponse.getItems());
                photosAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(InstagramServiceManager.TAG, "Error occurred while retrieving photos", error);
            }
        });
    }

    private void fetchPopularItemsOffline() {
        Gson gson = new Gson();
        PopularItemResponse response = gson.fromJson(instagramPopularJson, PopularItemResponse.class);
        instagramItems = response.getItems();
        photosAdapter.notifyDataSetChanged();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static final String instagramPopularJson = "{\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"attribution\": null,\n" +
            "            \"caption\": {\n" +
            "                \"created_time\": \"1423103035\",\n" +
            "                \"from\": {\n" +
            "                    \"full_name\": \"Golden State Warriors\",\n" +
            "                    \"id\": \"31358558\",\n" +
            "                    \"profile_picture\": \"https://igcdn-photos-d-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10903716_398478926978195_1376617213_a.jpg\",\n" +
            "                    \"username\": \"warriors\"\n" +
            "                },\n" +
            "                \"id\": \"913381314960792828\",\n" +
            "                \"text\": \"Hallway 3ptr = pregame #WarriorsGround tradition.\"\n" +
            "            },\n" +
            "            \"comments\": {\n" +
            "                \"count\": 205,\n" +
            "                \"data\": [\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106072\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"\\ud83c\\udfc0Cristian Chavez\\ud83d\\udcaf\",\n" +
            "                            \"id\": \"284616662\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-b-a.akamaihd.net/hphotos-ak-xfp1/t51.2885-19/1688869_1629912190574025_1990079671_a.jpg\",\n" +
            "                            \"username\": \"c_chavez5\"\n" +
            "                        },\n" +
            "                        \"id\": \"913402397512408286\",\n" +
            "                        \"text\": \"@bgoings5\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106106\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"Lance Gross' Twin Brother\",\n" +
            "                            \"id\": \"23827702\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-b-a.akamaihd.net/hphotos-ak-xfa1/t51.2885-19/1662274_706352506119289_119290017_a.jpg\",\n" +
            "                            \"username\": \"terrencej_\"\n" +
            "                        },\n" +
            "                        \"id\": \"913402680191720713\",\n" +
            "                        \"text\": \"@will_g24 @og_bj34 @szaboroland1991 @geaux_dev\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106129\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"tengee_igo\",\n" +
            "                            \"id\": \"13078287\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-h-a.akamaihd.net/hphotos-ak-xap1/t51.2885-19/10724143_753330458084279_1337648721_a.jpg\",\n" +
            "                            \"username\": \"tengee_igo\"\n" +
            "                        },\n" +
            "                        \"id\": \"913402876627754269\",\n" +
            "                        \"text\": \"@cieligun aiiimshgiin sdag haraaa hahaha\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106177\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"cieligun\",\n" +
            "                            \"id\": \"39314567\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-h-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10957311_405923969571071_465149814_a.jpg\",\n" +
            "                            \"username\": \"cieligun\"\n" +
            "                        },\n" +
            "                        \"id\": \"913403279423544660\",\n" +
            "                        \"text\": \"Uzehjinuu @tengee_igo\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106184\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"Norma Repollo-Regala\",\n" +
            "                            \"id\": \"1339144432\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-h-a.akamaihd.net/hphotos-ak-xpa1/t51.2885-19/10311311_472019386277479_403777209_a.jpg\",\n" +
            "                            \"username\": \"normarregala\"\n" +
            "                        },\n" +
            "                        \"id\": \"913403333890776411\",\n" +
            "                        \"text\": \"Go Warriors !!!\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106201\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"Jack Habe\",\n" +
            "                            \"id\": \"1051260623\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-e-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10852693_1544463522462748_1252629277_a.jpg\",\n" +
            "                            \"username\": \"22_habe_22\"\n" +
            "                        },\n" +
            "                        \"id\": \"913403478292274544\",\n" +
            "                        \"text\": \"@lsulll\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106265\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"tuckcolt\",\n" +
            "                            \"id\": \"184631267\",\n" +
            "                            \"profile_picture\": \"https://instagramimages-a.akamaihd.net/profiles/profile_184631267_75sq_1381178409.jpg\",\n" +
            "                            \"username\": \"tuckcolt\"\n" +
            "                        },\n" +
            "                        \"id\": \"913404013720346023\",\n" +
            "                        \"text\": \"@ryan_quint @kotty910\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106327\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"Archie Santa Maria\",\n" +
            "                            \"id\": \"210397533\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-e-a.akamaihd.net/hphotos-ak-xfa1/t51.2885-19/10818038_401527793331836_179034953_a.jpg\",\n" +
            "                            \"username\": \"archie_sm\"\n" +
            "                        },\n" +
            "                        \"id\": \"913404536959770068\",\n" +
            "                        \"text\": \"@san_gio_ @jovangamez\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            \"created_time\": \"1423103035\",\n" +
            "            \"filter\": \"Vesper\",\n" +
            "            \"id\": \"913376916545183421_31358558\",\n" +
            "            \"images\": {\n" +
            "                \"low_resolution\": {\n" +
            "                    \"height\": 306,\n" +
            "                    \"url\": \"http://scontent-a.cdninstagram.com/hphotos-xaf1/t51.2885-15/s306x306/e15/10956858_354843038046614_340623260_n.jpg\",\n" +
            "                    \"width\": 306\n" +
            "                },\n" +
            "                \"standard_resolution\": {\n" +
            "                    \"height\": 640,\n" +
            "                    \"url\": \"http://scontent-a.cdninstagram.com/hphotos-xaf1/t51.2885-15/e15/10956858_354843038046614_340623260_n.jpg\",\n" +
            "                    \"width\": 640\n" +
            "                },\n" +
            "                \"thumbnail\": {\n" +
            "                    \"height\": 150,\n" +
            "                    \"url\": \"http://scontent-a.cdninstagram.com/hphotos-xaf1/t51.2885-15/s150x150/e15/10956858_354843038046614_340623260_n.jpg\",\n" +
            "                    \"width\": 150\n" +
            "                }\n" +
            "            },\n" +
            "            \"likes\": {\n" +
            "                \"count\": 11771,\n" +
            "                \"data\": [\n" +
            "                    {\n" +
            "                        \"full_name\": \"Tyler La\",\n" +
            "                        \"id\": \"627994808\",\n" +
            "                        \"profile_picture\": \"https://igcdn-photos-g-a.akamaihd.net/hphotos-ak-xpa1/t51.2885-19/10499192_1529765257244446_511867740_a.jpg\",\n" +
            "                        \"username\": \"tylerla__\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"full_name\": \"Jayce Staggers\",\n" +
            "                        \"id\": \"1625886488\",\n" +
            "                        \"profile_picture\": \"https://igcdn-photos-a-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10914375_1595624970667576_1142264715_a.jpg\",\n" +
            "                        \"username\": \"jjstaggs16\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"full_name\": \"\\ud83d\\udc1f\",\n" +
            "                        \"id\": \"1366865693\",\n" +
            "                        \"profile_picture\": \"https://igcdn-photos-d-a.akamaihd.net/hphotos-ak-xfa1/t51.2885-19/10903472_1403255753306707_1469570991_a.jpg\",\n" +
            "                        \"username\": \"ffffffff.3\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"full_name\": \"KIANA!\",\n" +
            "                        \"id\": \"144361518\",\n" +
            "                        \"profile_picture\": \"https://igcdn-photos-h-a.akamaihd.net/hphotos-ak-xpa1/t51.2885-19/1530632_347134258826087_2111017319_a.jpg\",\n" +
            "                        \"username\": \"kgsus\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            \"link\": \"http://instagram.com/p/ys93dgt569/\",\n" +
            "            \"location\": {\n" +
            "                \"id\": 205934004,\n" +
            "                \"latitude\": 37.750544394,\n" +
            "                \"longitude\": -122.202786304,\n" +
            "                \"name\": \"Golden State Warriors Home Game\"\n" +
            "            },\n" +
            "            \"tags\": [\n" +
            "                \"warriorsground\"\n" +
            "            ],\n" +
            "            \"type\": \"video\",\n" +
            "            \"user\": {\n" +
            "                \"bio\": \"\",\n" +
            "                \"full_name\": \"Golden State Warriors\",\n" +
            "                \"id\": \"31358558\",\n" +
            "                \"profile_picture\": \"https://igcdn-photos-d-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10903716_398478926978195_1376617213_a.jpg\",\n" +
            "                \"username\": \"warriors\",\n" +
            "                \"website\": \"\"\n" +
            "            },\n" +
            "            \"users_in_photo\": [],\n" +
            "            \"videos\": {\n" +
            "                \"low_bandwidth\": {\n" +
            "                    \"height\": 480,\n" +
            "                    \"url\": \"http://scontent-a.cdninstagram.com/hphotos-xaf1/t50.2886-16/10964355_1590085194560723_1415501510_s.mp4\",\n" +
            "                    \"width\": 480\n" +
            "                },\n" +
            "                \"low_resolution\": {\n" +
            "                    \"height\": 480,\n" +
            "                    \"url\": \"http://scontent-a.cdninstagram.com/hphotos-xaf1/t50.2886-16/10964355_1590085194560723_1415501510_s.mp4\",\n" +
            "                    \"width\": 480\n" +
            "                },\n" +
            "                \"standard_resolution\": {\n" +
            "                    \"height\": 640,\n" +
            "                    \"url\": \"http://scontent-b.cdninstagram.com/hphotos-xfa1/t50.2886-16/10976693_789713341083533_1395348520_n.mp4\",\n" +
            "                    \"width\": 640\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"attribution\": null,\n" +
            "            \"caption\": null,\n" +
            "            \"comments\": {\n" +
            "                \"count\": 3866,\n" +
            "                \"data\": [\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106293\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"Milla Berntsen\",\n" +
            "                            \"id\": \"608716446\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-g-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10948899_304655363077366_1737669287_a.jpg\",\n" +
            "                            \"username\": \"millasciorra\"\n" +
            "                        },\n" +
            "                        \"id\": \"913404249068960441\",\n" +
            "                        \"text\": \"I want people to look at me like this\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106300\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"JohanR.\",\n" +
            "                            \"id\": \"374992479\",\n" +
            "                            \"profile_picture\": \"https://instagramimages-a.akamaihd.net/profiles/anonymousUser.jpg\",\n" +
            "                            \"username\": \"johanr_\"\n" +
            "                        },\n" +
            "                        \"id\": \"913404309919922879\",\n" +
            "                        \"text\": \"HI FROM HONDURASSS\\u2764\\ufe0f\\u2764\\ufe0f\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106311\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"Kyle Lajewski\",\n" +
            "                            \"id\": \"27894590\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-a-a.akamaihd.net/hphotos-ak-xfa1/t51.2885-19/10838696_769525576452544_626249759_a.jpg\",\n" +
            "                            \"username\": \"kylelajewski\"\n" +
            "                        },\n" +
            "                        \"id\": \"913404402714704583\",\n" +
            "                        \"text\": \"Babe\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106314\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"Keith Ranna\",\n" +
            "                            \"id\": \"1526608444\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-d-a.akamaihd.net/hphotos-ak-xpa1/t51.2885-19/10731446_719650414793907_1896260939_a.jpg\",\n" +
            "                            \"username\": \"keithranna\"\n" +
            "                        },\n" +
            "                        \"id\": \"913404426748066507\",\n" +
            "                        \"text\": \"that's Johnny Halliday!!!!!\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106328\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"davidpyle_\",\n" +
            "                            \"id\": \"1378554022\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-a-a.akamaihd.net/hphotos-ak-xfa1/t51.2885-19/10919507_1400734540229064_1879160590_a.jpg\",\n" +
            "                            \"username\": \"davidpyle_\"\n" +
            "                        },\n" +
            "                        \"id\": \"913404546931653336\",\n" +
            "                        \"text\": \"@juliet_vega me & daddy & daddy's side hoe\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106336\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"KAYLA\\ud83d\\udc19\",\n" +
            "                            \"id\": \"255921416\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-c-a.akamaihd.net/hphotos-ak-xfa1/t51.2885-19/10946294_978669135495650_1129241413_a.jpg\",\n" +
            "                            \"username\": \"kayla.gillen\"\n" +
            "                        },\n" +
            "                        \"id\": \"913404614208289499\",\n" +
            "                        \"text\": \"LANA I LOVE YOU\\u2764\\ufe0f\\u2764\\ufe0f\\u2764\\ufe0f\\u2764\\ufe0f\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106338\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"andreatndb\",\n" +
            "                            \"id\": \"1621616016\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-g-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10963779_1050724944954462_1230831362_a.jpg\",\n" +
            "                            \"username\": \"andreatndb\"\n" +
            "                        },\n" +
            "                        \"id\": \"913404630482189022\",\n" +
            "                        \"text\": \"AHHH TRU @alexa.fiore\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106346\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"oliviadestt\",\n" +
            "                            \"id\": \"1656208099\",\n" +
            "                            \"profile_picture\": \"https://instagramimages-a.akamaihd.net/profiles/anonymousUser.jpg\",\n" +
            "                            \"username\": \"oliviadestt\"\n" +
            "                        },\n" +
            "                        \"id\": \"913404697725270758\",\n" +
            "                        \"text\": \"okay tiesto is your \\\"god\\\" so I don't think I'm the moron @belliaaaa\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            \"created_time\": \"1423100613\",\n" +
            "            \"filter\": \"Normal\",\n" +
            "            \"id\": \"913356600181684240_305506437\",\n" +
            "            \"images\": {\n" +
            "                \"low_resolution\": {\n" +
            "                    \"height\": 306,\n" +
            "                    \"url\": \"http://scontent-b.cdninstagram.com/hphotos-xfa1/t51.2885-15/s306x306/e15/10817543_830408667020538_104749424_n.jpg\",\n" +
            "                    \"width\": 306\n" +
            "                },\n" +
            "                \"standard_resolution\": {\n" +
            "                    \"height\": 640,\n" +
            "                    \"url\": \"http://scontent-b.cdninstagram.com/hphotos-xfa1/t51.2885-15/e15/10817543_830408667020538_104749424_n.jpg\",\n" +
            "                    \"width\": 640\n" +
            "                },\n" +
            "                \"thumbnail\": {\n" +
            "                    \"height\": 150,\n" +
            "                    \"url\": \"http://scontent-b.cdninstagram.com/hphotos-xfa1/t51.2885-15/s150x150/e15/10817543_830408667020538_104749424_n.jpg\",\n" +
            "                    \"width\": 150\n" +
            "                }\n" +
            "            },\n" +
            "            \"likes\": {\n" +
            "                \"count\": 138937,\n" +
            "                \"data\": [\n" +
            "                    {\n" +
            "                        \"full_name\": \"Odalys\",\n" +
            "                        \"id\": \"20221273\",\n" +
            "                        \"profile_picture\": \"https://igcdn-photos-h-a.akamaihd.net/hphotos-ak-xfa1/t51.2885-19/10881974_404891089686615_2082765924_a.jpg\",\n" +
            "                        \"username\": \"odaza\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"full_name\": \"Emma Ellis\",\n" +
            "                        \"id\": \"22212471\",\n" +
            "                        \"profile_picture\": \"https://igcdn-photos-h-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10919716_1532877553628167_1237674169_a.jpg\",\n" +
            "                        \"username\": \"wanna_betts\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"full_name\": \"Shannon Forbes\",\n" +
            "                        \"id\": \"145911901\",\n" +
            "                        \"profile_picture\": \"https://igcdn-photos-f-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10899109_915309995170101_523746351_a.jpg\",\n" +
            "                        \"username\": \"shannforbes\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"full_name\": \"kitten les\",\n" +
            "                        \"id\": \"44962361\",\n" +
            "                        \"profile_picture\": \"https://igcdn-photos-b-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10954294_636026059859081_1267273556_a.jpg\",\n" +
            "                        \"username\": \"irl.friends\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            \"link\": \"http://instagram.com/p/ys5P0bFngQ/\",\n" +
            "            \"location\": null,\n" +
            "            \"tags\": [],\n" +
            "            \"type\": \"image\",\n" +
            "            \"user\": {\n" +
            "                \"bio\": \"\",\n" +
            "                \"full_name\": \"Lana Del Rey\",\n" +
            "                \"id\": \"305506437\",\n" +
            "                \"profile_picture\": \"https://igcdn-photos-e-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10903350_357896657744180_711074103_a.jpg\",\n" +
            "                \"username\": \"lanadelrey\",\n" +
            "                \"website\": \"\"\n" +
            "            },\n" +
            "            \"users_in_photo\": []\n" +
            "        },\n" +
            "        {\n" +
            "            \"attribution\": null,\n" +
            "            \"caption\": {\n" +
            "                \"created_time\": \"1423098761\",\n" +
            "                \"from\": {\n" +
            "                    \"full_name\": \"Aspen Mansfield\",\n" +
            "                    \"id\": \"206958136\",\n" +
            "                    \"profile_picture\": \"https://scontent-b.cdninstagram.com/hphotos-xaf1/l/t51.2885-19/10832097_1572781572957910_68961568_a.jpg\",\n" +
            "                    \"username\": \"slutfactory\"\n" +
            "                },\n" +
            "                \"id\": \"913341064984725081\",\n" +
            "                \"text\": \"I'll leave you dead in the bathroom\\ud83d\\udc30\"\n" +
            "            },\n" +
            "            \"comments\": {\n" +
            "                \"count\": 454,\n" +
            "                \"data\": [\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423105644\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"\\ud83d\\udc81 Jesse Scott \\ud83d\\udc81\",\n" +
            "                            \"id\": \"44674112\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-h-a.akamaihd.net/hphotos-ak-xfa1/t51.2885-19/10956599_1541217702809543_1502627791_a.jpg\",\n" +
            "                            \"username\": \"browneyedgirl26\"\n" +
            "                        },\n" +
            "                        \"id\": \"913398810266371269\",\n" +
            "                        \"text\": \"#wcw \\ud83d\\ude0d\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423105703\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"Anna Jones\",\n" +
            "                            \"id\": \"1512319413\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-g-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10932148_958170360859646_2048398502_a.jpg\",\n" +
            "                            \"username\": \"amjones_1\"\n" +
            "                        },\n" +
            "                        \"id\": \"913399299263497436\",\n" +
            "                        \"text\": \"@streetroyal_hobs this is me\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423105764\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"Achijimm Cleebeetcc\",\n" +
            "                            \"id\": \"1537179109\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-d-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10949016_911979485502243_2078726845_a.jpg\",\n" +
            "                            \"username\": \"achijimm\"\n" +
            "                        },\n" +
            "                        \"id\": \"913399815959807221\",\n" +
            "                        \"text\": \"Hey... i like your pic. May iknow you? I m jimm.. from indonesia how about you?\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423105822\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"mike_page_\",\n" +
            "                            \"id\": \"33444404\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-g-a.akamaihd.net/hphotos-ak-xap1/t51.2885-19/923841_628874903889902_592530029_a.jpg\",\n" +
            "                            \"username\": \"mike_page_\"\n" +
            "                        },\n" +
            "                        \"id\": \"913400296232781075\",\n" +
            "                        \"text\": \"Ya right... i would do bad things\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106118\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"lanekailey\",\n" +
            "                            \"id\": \"734552283\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-a-a.akamaihd.net/hphotos-ak-xfp1/t51.2885-19/1516929_336330836574520_352834217_a.jpg\",\n" +
            "                            \"username\": \"lanekailey\"\n" +
            "                        },\n" +
            "                        \"id\": \"913402785401210272\",\n" +
            "                        \"text\": \"oh my fuck\\ud83d\\ude29\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106212\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"maddie\",\n" +
            "                            \"id\": \"19302171\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-c-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10802994_1530804970499346_80513297_a.jpg\",\n" +
            "                            \"username\": \"maddiekirstenn\"\n" +
            "                        },\n" +
            "                        \"id\": \"913403571841598941\",\n" +
            "                        \"text\": \"i have the same ring. life accomplishments\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106265\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"\\ud83d\\udc51Charidy Kaiser\\ud83d\\udc51\",\n" +
            "                            \"id\": \"439346157\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-h-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/914511_455009601312871_857669133_a.jpg\",\n" +
            "                            \"username\": \"heyy_its_charidy_\"\n" +
            "                        },\n" +
            "                        \"id\": \"913404014810433020\",\n" +
            "                        \"text\": \"\\ud83d\\ude0d\\ud83d\\ude0d\\ud83d\\ude0d\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"created_time\": \"1423106283\",\n" +
            "                        \"from\": {\n" +
            "                            \"full_name\": \"Vincent R\",\n" +
            "                            \"id\": \"204272192\",\n" +
            "                            \"profile_picture\": \"https://igcdn-photos-e-a.akamaihd.net/hphotos-ak-xfa1/t51.2885-19/10914161_1586614338235940_766573851_a.jpg\",\n" +
            "                            \"username\": \"slemron\"\n" +
            "                        },\n" +
            "                        \"id\": \"913404168909161988\",\n" +
            "                        \"text\": \"or you could leave me alive, IN DA LIVING ROOM!\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            \"created_time\": \"1423098761\",\n" +
            "            \"filter\": \"Normal\",\n" +
            "            \"id\": \"913341064758232192_206958136\",\n" +
            "            \"images\": {\n" +
            "                \"low_resolution\": {\n" +
            "                    \"height\": 306,\n" +
            "                    \"url\": \"http://scontent-b.cdninstagram.com/hphotos-xfa1/t51.2885-15/s306x306/e15/10946432_362514323933807_780531849_n.jpg\",\n" +
            "                    \"width\": 306\n" +
            "                },\n" +
            "                \"standard_resolution\": {\n" +
            "                    \"height\": 640,\n" +
            "                    \"url\": \"http://scontent-b.cdninstagram.com/hphotos-xfa1/t51.2885-15/e15/10946432_362514323933807_780531849_n.jpg\",\n" +
            "                    \"width\": 640\n" +
            "                },\n" +
            "                \"thumbnail\": {\n" +
            "                    \"height\": 150,\n" +
            "                    \"url\": \"http://scontent-b.cdninstagram.com/hphotos-xfa1/t51.2885-15/s150x150/e15/10946432_362514323933807_780531849_n.jpg\",\n" +
            "                    \"width\": 150\n" +
            "                }\n" +
            "            },\n" +
            "            \"likes\": {\n" +
            "                \"count\": 30464,\n" +
            "                \"data\": [\n" +
            "                    {\n" +
            "                        \"full_name\": \"\\ud83d\\udd2e\\ud83c\\udfa4Ryan Asher \\ud83c\\udfbc\\ud83d\\udc7d\",\n" +
            "                        \"id\": \"29776434\",\n" +
            "                        \"profile_picture\": \"https://igcdn-photos-b-a.akamaihd.net/hphotos-ak-xfa1/t51.2885-19/914495_307641752778705_2128213864_a.jpg\",\n" +
            "                        \"username\": \"ryan_ux\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"full_name\": \"\\u0645\\u0631\\u0633\\u0644 \\u0646\\u0648\\u0631\\u064a\\ud83c\\udf39\",\n" +
            "                        \"id\": \"917804055\",\n" +
            "                        \"profile_picture\": \"https://igcdn-photos-f-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10963809_311733589036133_1249086715_a.jpg\",\n" +
            "                        \"username\": \"afghane_mursal\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"full_name\": \"ayy.tania\",\n" +
            "                        \"id\": \"216976084\",\n" +
            "                        \"profile_picture\": \"https://igcdn-photos-a-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10954377_694146197369008_567750254_a.jpg\",\n" +
            "                        \"username\": \"ayy.tania\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"full_name\": \"\\ud83d\\udc8c Madds\",\n" +
            "                        \"id\": \"206990159\",\n" +
            "                        \"profile_picture\": \"https://igcdn-photos-b-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10948734_1554378898155065_1522859521_a.jpg\",\n" +
            "                        \"username\": \"maddiefelice\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            \"link\": \"http://instagram.com/p/ys1tv7sQSA/\",\n" +
            "            \"location\": null,\n" +
            "            \"tags\": [],\n" +
            "            \"type\": \"image\",\n" +
            "            \"user\": {\n" +
            "                \"bio\": \"\",\n" +
            "                \"full_name\": \"Aspen Mansfield\",\n" +
            "                \"id\": \"206958136\",\n" +
            "                \"profile_picture\": \"https://scontent-b.cdninstagram.com/hphotos-xaf1/l/t51.2885-19/10832097_1572781572957910_68961568_a.jpg\",\n" +
            "                \"username\": \"slutfactory\",\n" +
            "                \"website\": \"\"\n" +
            "            },\n" +
            "            \"users_in_photo\": []\n" +
            "        }" +
            "   ]" +
            "}";
}
