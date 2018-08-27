package com.bohemiamates.crcmngmt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bohemiamates.crcmngmt.models.Clan;
import com.bohemiamates.crcmngmt.models.ClanBattle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String URL = "https://api.royaleapi.com/";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTUzMCwi" +
            "aWRlbiI6IjQ3MjA3MTUzMjUxMjkzNTk1NiIsIm1kIjp7fSwidHMiOjE1MzQ4NjM2OTY4Mzh9.G391OKyRmW" +
            "bOZ1mZRaxz--rN7yaM1J6NaAV-tDb-TQ4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String test1 = "{\n" +
                "    \"tag\": \"9PJ82CRC\",\n" +
                "    \"name\": \"100T Alpha\",\n" +
                "    \"description\": \"Official clan of the Clash Royale League team @100ThievesCR. \\ud83d\\udcafJoin us at discord.gg/100T 4,900\\ud83c\\udfc6 and Legendary war cards!\",\n" +
                "    \"type\": \"invite only\",\n" +
                "    \"score\": 51236,\n" +
                "    \"memberCount\": 50,\n" +
                "    \"requiredScore\": 4600,\n" +
                "    \"donations\": 17237,\n" +
                "    \"badge\": {\n" +
                "        \"name\": \"A_Char_Rocket_02\",\n" +
                "        \"category\": \"03_Royale\",\n" +
                "        \"id\": 16000167,\n" +
                "        \"image\": \"https://royaleapi.github.io/cr-api-assets/badges/A_Char_Rocket_02.png\"\n" +
                "    },\n" +
                "    \"location\": {\n" +
                "        \"name\": \"North America\",\n" +
                "        \"isCountry\": false,\n" +
                "        \"code\": \"_NA\"\n" +
                "    },\n" +
                "    \"members\": [\n" +
                "        {\n" +
                "            \"name\": \"Blaze\",\n" +
                "            \"tag\": \"YJLJ98U\",\n" +
                "            \"rank\": 1,\n" +
                "            \"role\": \"elder\",\n" +
                "            \"expLevel\": 13,\n" +
                "            \"trophies\": 5399,\n" +
                "            \"donations\": 10,\n" +
                "            \"donationsReceived\": 40,\n" +
                "            \"donationsDelta\": -30,\n" +
                "            \"arena\": {\n" +
                "                \"name\": \"Master II\",\n" +
                "                \"arena\": \"League 5\",\n" +
                "                \"arenaID\": 17,\n" +
                "                \"trophyLimit\": 5200\n" +
                "            },\n" +
                "            \"donationsPercent\": 0.03\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Pablo\",\n" +
                "            \"tag\": \"2PQJJ0G\",\n" +
                "            \"rank\": 2,\n" +
                "            \"previousRank\": 1,\n" +
                "            \"role\": \"elder\",\n" +
                "            \"expLevel\": 13,\n" +
                "            \"trophies\": 5360,\n" +
                "            \"donations\": 54,\n" +
                "            \"donationsReceived\": 74,\n" +
                "            \"donationsDelta\": -20,\n" +
                "            \"arena\": {\n" +
                "                \"name\": \"Master II\",\n" +
                "                \"arena\": \"League 5\",\n" +
                "                \"arenaID\": 17,\n" +
                "                \"trophyLimit\": 5200\n" +
                "            },\n" +
                "            \"donationsPercent\": 0.16\n" +
                "        }\n" +
                "    ],\n" +
                "    \"tracking\": {\n" +
                "        \"active\": true,\n" +
                "        \"available\": true,\n" +
                "        \"snapshotCount\": 559\n" +
                "    }\n" +
                "}";

        String test = "[\n" +
                "    {\n" +
                "        \"type\": \"clanWarWarDay\",\n" +
                "        \"challengeType\": null,\n" +
                "        \"mode\": {\n" +
                "            \"id\": 72000066,\n" +
                "            \"name\": \"Showdown_Ladder\"\n" +
                "        },\n" +
                "        \"winCountBefore\": null,\n" +
                "        \"utcTime\": 1534888944,\n" +
                "        \"deckType\": \"slotDeck\",\n" +
                "        \"teamSize\": 1,\n" +
                "        \"winner\": -3,\n" +
                "        \"teamCrowns\": 0,\n" +
                "        \"opponentCrowns\": 3,\n" +
                "        \"team\": [\n" +
                "            {\n" +
                "                \"tag\": \"9GPYGV90\",\n" +
                "                \"name\": \"Luis\",\n" +
                "                \"crownsEarned\": 0,\n" +
                "                \"startTrophies\": 3788,\n" +
                "                \"clan\": {\n" +
                "                    \"tag\": \"RY8JVV\",\n" +
                "                    \"name\": \"M.P.W.A\",\n" +
                "                    \"badge\": {\n" +
                "                        \"name\": \"A_Char_King_02\",\n" +
                "                        \"category\": \"03_Royale\",\n" +
                "                        \"id\": 16000145,\n" +
                "                        \"image\": \"https://royaleapi.github.io/cr-api-assets/badges/A_Char_King_02.png\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"deckLink\": \"https://link.clashroyale.com/deck/en?deck=26000015;26000013;26000003;26000011;26000002;26000005;28000001;26000039\",\n" +
                "                \"deck\": [\n" +
                "                    {\n" +
                "                        \"name\": \"Baby Dragon\",\n" +
                "                        \"level\": 5,\n" +
                "                        \"maxLevel\": 8,\n" +
                "                        \"rarity\": \"Epic\",\n" +
                "                        \"requiredForUpgrade\": 50,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/cjC9n4AvEZJ3urkVh-rwBkJ-aRSsydIMqSAV48hAih0.png\",\n" +
                "                        \"key\": \"baby-dragon\",\n" +
                "                        \"elixir\": 4,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 0,\n" +
                "                        \"description\": \"Flying troop that deals area damage. Baby dragons hatch cute, hungry and ready for a barbeque.\",\n" +
                "                        \"id\": 26000015\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Bomber\",\n" +
                "                        \"level\": 10,\n" +
                "                        \"maxLevel\": 13,\n" +
                "                        \"rarity\": \"Common\",\n" +
                "                        \"requiredForUpgrade\": 1000,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/12n1CesxKIcqVYntjxcF36EFA-ONw7Z-DoL0_rQrbdo.png\",\n" +
                "                        \"key\": \"bomber\",\n" +
                "                        \"elixir\": 3,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 2,\n" +
                "                        \"description\": \"Small, lightly protected skeleton that throws bombs. Deals area damage that can wipe out a swarm of enemies.\",\n" +
                "                        \"id\": 26000013\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Giant\",\n" +
                "                        \"level\": 7,\n" +
                "                        \"maxLevel\": 11,\n" +
                "                        \"rarity\": \"Rare\",\n" +
                "                        \"requiredForUpgrade\": 200,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/Axr4ox5_b7edmLsoHxBX3vmgijAIibuF6RImTbqLlXE.png\",\n" +
                "                        \"key\": \"giant\",\n" +
                "                        \"elixir\": 5,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 0,\n" +
                "                        \"description\": \"Slow but durable, only attacks buildings. A real one-man wrecking crew!\",\n" +
                "                        \"id\": 26000003\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Valkyrie\",\n" +
                "                        \"level\": 7,\n" +
                "                        \"maxLevel\": 11,\n" +
                "                        \"rarity\": \"Rare\",\n" +
                "                        \"requiredForUpgrade\": 200,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/0lIoYf3Y_plFTzo95zZL93JVxpfb3MMgFDDhgSDGU9A.png\",\n" +
                "                        \"key\": \"valkyrie\",\n" +
                "                        \"elixir\": 4,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 2,\n" +
                "                        \"description\": \"Tough melee fighter, deals area damage around her. Swarm or horde, no problem! She can take them all out with a few spins.\",\n" +
                "                        \"id\": 26000011\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Goblins\",\n" +
                "                        \"level\": 10,\n" +
                "                        \"maxLevel\": 13,\n" +
                "                        \"rarity\": \"Common\",\n" +
                "                        \"requiredForUpgrade\": 1000,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/X_DQUye_OaS3QN6VC9CPw05Fit7wvSm3XegXIXKP--0.png\",\n" +
                "                        \"key\": \"goblins\",\n" +
                "                        \"elixir\": 2,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 1,\n" +
                "                        \"description\": \"Three fast, unarmored melee attackers. Small, fast, green and mean!\",\n" +
                "                        \"id\": 26000002\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Minions\",\n" +
                "                        \"level\": 11,\n" +
                "                        \"maxLevel\": 13,\n" +
                "                        \"rarity\": \"Common\",\n" +
                "                        \"requiredForUpgrade\": 2000,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/yHGpoEnmUWPGV_hBbhn-Kk-Bs838OjGzWzJJlQpQKQA.png\",\n" +
                "                        \"key\": \"minions\",\n" +
                "                        \"elixir\": 3,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 0,\n" +
                "                        \"description\": \"Three fast, unarmored flying attackers. Roses are red, minions are blue, they can fly, and will crush you!\",\n" +
                "                        \"id\": 26000005\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Arrows\",\n" +
                "                        \"level\": 10,\n" +
                "                        \"maxLevel\": 13,\n" +
                "                        \"rarity\": \"Common\",\n" +
                "                        \"requiredForUpgrade\": 1000,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/Flsoci-Y6y8ZFVi5uRFTmgkPnCmMyMVrU7YmmuPvSBo.png\",\n" +
                "                        \"key\": \"arrows\",\n" +
                "                        \"elixir\": 3,\n" +
                "                        \"type\": \"Spell\",\n" +
                "                        \"arena\": 0,\n" +
                "                        \"description\": \"Arrows pepper a large area, damaging all enemies hit. Reduced damage to Crown Towers.\",\n" +
                "                        \"id\": 28000001\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Mega Minion\",\n" +
                "                        \"level\": 7,\n" +
                "                        \"maxLevel\": 11,\n" +
                "                        \"rarity\": \"Rare\",\n" +
                "                        \"requiredForUpgrade\": 200,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/-T_e4YLbuhPBKbYnBwQfXgynNpp5eOIN_0RracYwL9c.png\",\n" +
                "                        \"key\": \"mega-minion\",\n" +
                "                        \"elixir\": 3,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 4,\n" +
                "                        \"description\": \"Flying, armored and powerful. What could be its weakness?! Cupcakes.\",\n" +
                "                        \"id\": 26000039\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"opponent\": [\n" +
                "            {\n" +
                "                \"tag\": \"8PQPCJJPC\",\n" +
                "                \"name\": \"EyeDidURMom\",\n" +
                "                \"crownsEarned\": 3,\n" +
                "                \"startTrophies\": 4221,\n" +
                "                \"clan\": {\n" +
                "                    \"tag\": \"82LVQCL\",\n" +
                "                    \"name\": \"DMVs Finest\",\n" +
                "                    \"badge\": {\n" +
                "                        \"name\": \"Traditional_Star_03\",\n" +
                "                        \"category\": \"01_Symbol\",\n" +
                "                        \"id\": 16000038,\n" +
                "                        \"image\": \"https://royaleapi.github.io/cr-api-assets/badges/Traditional_Star_03.png\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"deckLink\": \"https://link.clashroyale.com/deck/en?deck=26000004;26000017;26000052;28000001;26000022;28000011;26000000;26000012\",\n" +
                "                \"deck\": [\n" +
                "                    {\n" +
                "                        \"name\": \"P.E.K.K.A\",\n" +
                "                        \"level\": 5,\n" +
                "                        \"maxLevel\": 8,\n" +
                "                        \"rarity\": \"Epic\",\n" +
                "                        \"requiredForUpgrade\": 50,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/MlArURKhn_zWAZY-Xj1qIRKLVKquarG25BXDjUQajNs.png\",\n" +
                "                        \"key\": \"pekka\",\n" +
                "                        \"elixir\": 7,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 4,\n" +
                "                        \"description\": \"A heavily armored, slow melee fighter. Swings from the hip, but packs a huge punch.\",\n" +
                "                        \"id\": 26000004\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Wizard\",\n" +
                "                        \"level\": 9,\n" +
                "                        \"maxLevel\": 11,\n" +
                "                        \"rarity\": \"Rare\",\n" +
                "                        \"requiredForUpgrade\": 800,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/Mej7vnv4H_3p_8qPs_N6_GKahy6HDr7pU7i9eTHS84U.png\",\n" +
                "                        \"key\": \"wizard\",\n" +
                "                        \"elixir\": 5,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 5,\n" +
                "                        \"description\": \"The most awesome man to ever set foot in the Arena, the Wizard will blow you away with his handsomeness... and/or fireballs.\",\n" +
                "                        \"id\": 26000017\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Zappies\",\n" +
                "                        \"level\": 9,\n" +
                "                        \"maxLevel\": 11,\n" +
                "                        \"rarity\": \"Rare\",\n" +
                "                        \"requiredForUpgrade\": 800,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/QZfHRpLRmutZbCr5fpLnTpIp89vLI6NrAwzGZ8tHEc4.png\",\n" +
                "                        \"key\": \"zappies\",\n" +
                "                        \"elixir\": 4,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 11,\n" +
                "                        \"description\": \"Spawns a pack of miniature Zap machines. Who controls them...? Only the Master Builder knows.\",\n" +
                "                        \"id\": 26000052\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Arrows\",\n" +
                "                        \"level\": 11,\n" +
                "                        \"maxLevel\": 13,\n" +
                "                        \"rarity\": \"Common\",\n" +
                "                        \"requiredForUpgrade\": 2000,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/Flsoci-Y6y8ZFVi5uRFTmgkPnCmMyMVrU7YmmuPvSBo.png\",\n" +
                "                        \"key\": \"arrows\",\n" +
                "                        \"elixir\": 3,\n" +
                "                        \"type\": \"Spell\",\n" +
                "                        \"arena\": 0,\n" +
                "                        \"description\": \"Arrows pepper a large area, damaging all enemies hit. Reduced damage to Crown Towers.\",\n" +
                "                        \"id\": 28000001\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Minion Horde\",\n" +
                "                        \"level\": 11,\n" +
                "                        \"maxLevel\": 13,\n" +
                "                        \"rarity\": \"Common\",\n" +
                "                        \"requiredForUpgrade\": 2000,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/Wyjq5l0IXHTkX9Rmpap6HaH08MvjbxFp1xBO9a47YSI.png\",\n" +
                "                        \"key\": \"minion-horde\",\n" +
                "                        \"elixir\": 5,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 4,\n" +
                "                        \"description\": \"Six fast, unarmored flying attackers. Three's a crowd, six is a horde!\",\n" +
                "                        \"id\": 26000022\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"The Log\",\n" +
                "                        \"level\": 2,\n" +
                "                        \"maxLevel\": 5,\n" +
                "                        \"rarity\": \"Legendary\",\n" +
                "                        \"requiredForUpgrade\": 4,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/_iDwuDLexHPFZ_x4_a0eP-rxCS6vwWgTs6DLauwwoaY.png\",\n" +
                "                        \"key\": \"the-log\",\n" +
                "                        \"elixir\": 2,\n" +
                "                        \"type\": \"Spell\",\n" +
                "                        \"arena\": 6,\n" +
                "                        \"description\": \"A spilt bottle of Rage turned an innocent tree trunk into \\\"The Log\\\". Now, it seeks revenge by crushing anything in its path!\",\n" +
                "                        \"id\": 28000011\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Knight\",\n" +
                "                        \"level\": 11,\n" +
                "                        \"maxLevel\": 13,\n" +
                "                        \"rarity\": \"Common\",\n" +
                "                        \"requiredForUpgrade\": 2000,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/jAj1Q5rclXxU9kVImGqSJxa4wEMfEhvwNQ_4jiGUuqg.png\",\n" +
                "                        \"key\": \"knight\",\n" +
                "                        \"elixir\": 3,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 0,\n" +
                "                        \"description\": \"A tough melee fighter. The Barbarian's handsome, cultured cousin. Rumor has it that he was knighted based on the sheer awesomeness of his mustache alone.\",\n" +
                "                        \"id\": 26000000\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Skeleton Army\",\n" +
                "                        \"level\": 5,\n" +
                "                        \"maxLevel\": 8,\n" +
                "                        \"rarity\": \"Epic\",\n" +
                "                        \"requiredForUpgrade\": 50,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/fAOToOi1pRy7svN2xQS6mDkhQw2pj9m_17FauaNqyl4.png\",\n" +
                "                        \"key\": \"skeleton-army\",\n" +
                "                        \"elixir\": 3,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 0,\n" +
                "                        \"description\": \"Spawns an army of Skeletons. Meet Larry and his friends Harry, Gerry, Terry, Mary, etc.\",\n" +
                "                        \"id\": 26000012\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"arena\": {\n" +
                "            \"name\": \"Clan League\"\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"type\": \"clanWarWarDay\",\n" +
                "        \"challengeType\": null,\n" +
                "        \"mode\": {\n" +
                "            \"id\": 72000066,\n" +
                "            \"name\": \"Showdown_Ladder\"\n" +
                "        },\n" +
                "        \"winCountBefore\": null,\n" +
                "        \"utcTime\": 1534882982,\n" +
                "        \"deckType\": \"slotDeck\",\n" +
                "        \"teamSize\": 1,\n" +
                "        \"winner\": 1,\n" +
                "        \"teamCrowns\": 1,\n" +
                "        \"opponentCrowns\": 0,\n" +
                "        \"team\": [\n" +
                "            {\n" +
                "                \"tag\": \"8802222P\",\n" +
                "                \"name\": \"Jorge\",\n" +
                "                \"crownsEarned\": 1,\n" +
                "                \"startTrophies\": 4009,\n" +
                "                \"clan\": {\n" +
                "                    \"tag\": \"RY8JVV\",\n" +
                "                    \"name\": \"M.P.W.A\",\n" +
                "                    \"badge\": {\n" +
                "                        \"name\": \"A_Char_King_02\",\n" +
                "                        \"category\": \"03_Royale\",\n" +
                "                        \"id\": 16000145,\n" +
                "                        \"image\": \"https://royaleapi.github.io/cr-api-assets/badges/A_Char_King_02.png\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"deckLink\": \"https://link.clashroyale.com/deck/en?deck=26000011;28000009;28000011;26000002;26000003;26000005;26000039;26000015\",\n" +
                "                \"deck\": [\n" +
                "                    {\n" +
                "                        \"name\": \"Valkyrie\",\n" +
                "                        \"level\": 9,\n" +
                "                        \"maxLevel\": 11,\n" +
                "                        \"rarity\": \"Rare\",\n" +
                "                        \"requiredForUpgrade\": 800,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/0lIoYf3Y_plFTzo95zZL93JVxpfb3MMgFDDhgSDGU9A.png\",\n" +
                "                        \"key\": \"valkyrie\",\n" +
                "                        \"elixir\": 4,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 2,\n" +
                "                        \"description\": \"Tough melee fighter, deals area damage around her. Swarm or horde, no problem! She can take them all out with a few spins.\",\n" +
                "                        \"id\": 26000011\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Poison\",\n" +
                "                        \"level\": 5,\n" +
                "                        \"maxLevel\": 8,\n" +
                "                        \"rarity\": \"Epic\",\n" +
                "                        \"requiredForUpgrade\": 50,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/98HDkG2189yOULcVG9jz2QbJKtfuhH21DIrIjkOjxI8.png\",\n" +
                "                        \"key\": \"poison\",\n" +
                "                        \"elixir\": 4,\n" +
                "                        \"type\": \"Spell\",\n" +
                "                        \"arena\": 5,\n" +
                "                        \"description\": \"Covers the area in a deadly toxin, damaging enemy troops and buildings over time. Yet somehow leaves the grass green and healthy. Go figure!\",\n" +
                "                        \"id\": 28000009\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"The Log\",\n" +
                "                        \"level\": 1,\n" +
                "                        \"maxLevel\": 5,\n" +
                "                        \"rarity\": \"Legendary\",\n" +
                "                        \"requiredForUpgrade\": 2,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/_iDwuDLexHPFZ_x4_a0eP-rxCS6vwWgTs6DLauwwoaY.png\",\n" +
                "                        \"key\": \"the-log\",\n" +
                "                        \"elixir\": 2,\n" +
                "                        \"type\": \"Spell\",\n" +
                "                        \"arena\": 6,\n" +
                "                        \"description\": \"A spilt bottle of Rage turned an innocent tree trunk into \\\"The Log\\\". Now, it seeks revenge by crushing anything in its path!\",\n" +
                "                        \"id\": 28000011\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Goblins\",\n" +
                "                        \"level\": 9,\n" +
                "                        \"maxLevel\": 13,\n" +
                "                        \"rarity\": \"Common\",\n" +
                "                        \"requiredForUpgrade\": 800,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/X_DQUye_OaS3QN6VC9CPw05Fit7wvSm3XegXIXKP--0.png\",\n" +
                "                        \"key\": \"goblins\",\n" +
                "                        \"elixir\": 2,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 1,\n" +
                "                        \"description\": \"Three fast, unarmored melee attackers. Small, fast, green and mean!\",\n" +
                "                        \"id\": 26000002\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Giant\",\n" +
                "                        \"level\": 7,\n" +
                "                        \"maxLevel\": 11,\n" +
                "                        \"rarity\": \"Rare\",\n" +
                "                        \"requiredForUpgrade\": 200,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/Axr4ox5_b7edmLsoHxBX3vmgijAIibuF6RImTbqLlXE.png\",\n" +
                "                        \"key\": \"giant\",\n" +
                "                        \"elixir\": 5,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 0,\n" +
                "                        \"description\": \"Slow but durable, only attacks buildings. A real one-man wrecking crew!\",\n" +
                "                        \"id\": 26000003\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Minions\",\n" +
                "                        \"level\": 12,\n" +
                "                        \"maxLevel\": 13,\n" +
                "                        \"rarity\": \"Common\",\n" +
                "                        \"requiredForUpgrade\": 5000,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/yHGpoEnmUWPGV_hBbhn-Kk-Bs838OjGzWzJJlQpQKQA.png\",\n" +
                "                        \"key\": \"minions\",\n" +
                "                        \"elixir\": 3,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 0,\n" +
                "                        \"description\": \"Three fast, unarmored flying attackers. Roses are red, minions are blue, they can fly, and will crush you!\",\n" +
                "                        \"id\": 26000005\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Mega Minion\",\n" +
                "                        \"level\": 7,\n" +
                "                        \"maxLevel\": 11,\n" +
                "                        \"rarity\": \"Rare\",\n" +
                "                        \"requiredForUpgrade\": 200,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/-T_e4YLbuhPBKbYnBwQfXgynNpp5eOIN_0RracYwL9c.png\",\n" +
                "                        \"key\": \"mega-minion\",\n" +
                "                        \"elixir\": 3,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 4,\n" +
                "                        \"description\": \"Flying, armored and powerful. What could be its weakness?! Cupcakes.\",\n" +
                "                        \"id\": 26000039\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Baby Dragon\",\n" +
                "                        \"level\": 5,\n" +
                "                        \"maxLevel\": 8,\n" +
                "                        \"rarity\": \"Epic\",\n" +
                "                        \"requiredForUpgrade\": 50,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/cjC9n4AvEZJ3urkVh-rwBkJ-aRSsydIMqSAV48hAih0.png\",\n" +
                "                        \"key\": \"baby-dragon\",\n" +
                "                        \"elixir\": 4,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 0,\n" +
                "                        \"description\": \"Flying troop that deals area damage. Baby dragons hatch cute, hungry and ready for a barbeque.\",\n" +
                "                        \"id\": 26000015\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"opponent\": [\n" +
                "            {\n" +
                "                \"tag\": \"PY9GG8Q8\",\n" +
                "                \"name\": \"ibinist\",\n" +
                "                \"crownsEarned\": 0,\n" +
                "                \"startTrophies\": 4238,\n" +
                "                \"clan\": {\n" +
                "                    \"tag\": \"Q8G82\",\n" +
                "                    \"name\": \"french killer\",\n" +
                "                    \"badge\": {\n" +
                "                        \"name\": \"flag_k_01\",\n" +
                "                        \"category\": \"02_Flag\",\n" +
                "                        \"id\": 16000078,\n" +
                "                        \"image\": \"https://royaleapi.github.io/cr-api-assets/badges/flag_k_01.png\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"deckLink\": \"https://link.clashroyale.com/deck/en?deck=28000008;28000000;26000019;26000017;26000021;28000004;28000009;26000000\",\n" +
                "                \"deck\": [\n" +
                "                    {\n" +
                "                        \"name\": \"Zap\",\n" +
                "                        \"level\": 11,\n" +
                "                        \"maxLevel\": 13,\n" +
                "                        \"rarity\": \"Common\",\n" +
                "                        \"requiredForUpgrade\": 2000,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/7dxh2-yCBy1x44GrBaL29vjqnEEeJXHEAlsi5g6D1eY.png\",\n" +
                "                        \"key\": \"zap\",\n" +
                "                        \"elixir\": 2,\n" +
                "                        \"type\": \"Spell\",\n" +
                "                        \"arena\": 4,\n" +
                "                        \"description\": \"Zaps enemies, briefly stunning them and dealing damage inside a small radius. Reduced damage to Crown Towers.\",\n" +
                "                        \"id\": 28000008\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Fireball\",\n" +
                "                        \"level\": 9,\n" +
                "                        \"maxLevel\": 11,\n" +
                "                        \"rarity\": \"Rare\",\n" +
                "                        \"requiredForUpgrade\": 800,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/lZD9MILQv7O-P3XBr_xOLS5idwuz3_7Ws9G60U36yhc.png\",\n" +
                "                        \"key\": \"fireball\",\n" +
                "                        \"elixir\": 4,\n" +
                "                        \"type\": \"Spell\",\n" +
                "                        \"arena\": 0,\n" +
                "                        \"description\": \"Annnnnd... Fireball. Incinerates a small area, dealing high damage. Reduced damage to Crown Towers.\",\n" +
                "                        \"id\": 28000000\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Spear Goblins\",\n" +
                "                        \"level\": 9,\n" +
                "                        \"maxLevel\": 13,\n" +
                "                        \"rarity\": \"Common\",\n" +
                "                        \"requiredForUpgrade\": 800,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/FSDFotjaXidI4ku_WFpVCTWS1hKGnFh1sxX0lxM43_E.png\",\n" +
                "                        \"key\": \"spear-goblins\",\n" +
                "                        \"elixir\": 2,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 1,\n" +
                "                        \"description\": \"Three unarmored ranged attackers. Who the heck taught these guys to throw spears!? Who thought that was a good idea?!\",\n" +
                "                        \"id\": 26000019\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Wizard\",\n" +
                "                        \"level\": 8,\n" +
                "                        \"maxLevel\": 11,\n" +
                "                        \"rarity\": \"Rare\",\n" +
                "                        \"requiredForUpgrade\": 400,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/Mej7vnv4H_3p_8qPs_N6_GKahy6HDr7pU7i9eTHS84U.png\",\n" +
                "                        \"key\": \"wizard\",\n" +
                "                        \"elixir\": 5,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 5,\n" +
                "                        \"description\": \"The most awesome man to ever set foot in the Arena, the Wizard will blow you away with his handsomeness... and/or fireballs.\",\n" +
                "                        \"id\": 26000017\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Hog Rider\",\n" +
                "                        \"level\": 7,\n" +
                "                        \"maxLevel\": 11,\n" +
                "                        \"rarity\": \"Rare\",\n" +
                "                        \"requiredForUpgrade\": 200,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/Ubu0oUl8tZkusnkZf8Xv9Vno5IO29Y-jbZ4fhoNJ5oc.png\",\n" +
                "                        \"key\": \"hog-rider\",\n" +
                "                        \"elixir\": 4,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 1,\n" +
                "                        \"description\": \"Fast melee troop that targets buildings and can jump over the river. He followed the echoing call of \\\"Hog Riderrrrr\\\" all the way through the Arena doors.\",\n" +
                "                        \"id\": 26000021\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Goblin Barrel\",\n" +
                "                        \"level\": 4,\n" +
                "                        \"maxLevel\": 8,\n" +
                "                        \"rarity\": \"Epic\",\n" +
                "                        \"requiredForUpgrade\": 20,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/CoZdp5PpsTH858l212lAMeJxVJ0zxv9V-f5xC8Bvj5g.png\",\n" +
                "                        \"key\": \"goblin-barrel\",\n" +
                "                        \"elixir\": 3,\n" +
                "                        \"type\": \"Spell\",\n" +
                "                        \"arena\": 1,\n" +
                "                        \"description\": \"Spawns three Goblins anywhere in the Arena. It's going to be a thrilling ride, boys!\",\n" +
                "                        \"id\": 28000004\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Poison\",\n" +
                "                        \"level\": 5,\n" +
                "                        \"maxLevel\": 8,\n" +
                "                        \"rarity\": \"Epic\",\n" +
                "                        \"requiredForUpgrade\": 50,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/98HDkG2189yOULcVG9jz2QbJKtfuhH21DIrIjkOjxI8.png\",\n" +
                "                        \"key\": \"poison\",\n" +
                "                        \"elixir\": 4,\n" +
                "                        \"type\": \"Spell\",\n" +
                "                        \"arena\": 5,\n" +
                "                        \"description\": \"Covers the area in a deadly toxin, damaging enemy troops and buildings over time. Yet somehow leaves the grass green and healthy. Go figure!\",\n" +
                "                        \"id\": 28000009\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Knight\",\n" +
                "                        \"level\": 11,\n" +
                "                        \"maxLevel\": 13,\n" +
                "                        \"rarity\": \"Common\",\n" +
                "                        \"requiredForUpgrade\": 2000,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/jAj1Q5rclXxU9kVImGqSJxa4wEMfEhvwNQ_4jiGUuqg.png\",\n" +
                "                        \"key\": \"knight\",\n" +
                "                        \"elixir\": 3,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 0,\n" +
                "                        \"description\": \"A tough melee fighter. The Barbarian's handsome, cultured cousin. Rumor has it that he was knighted based on the sheer awesomeness of his mustache alone.\",\n" +
                "                        \"id\": 26000000\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"arena\": {\n" +
                "            \"name\": \"Clan League\"\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"type\": \"clanWarWarDay\",\n" +
                "        \"challengeType\": null,\n" +
                "        \"mode\": {\n" +
                "            \"id\": 72000066,\n" +
                "            \"name\": \"Showdown_Ladder\"\n" +
                "        },\n" +
                "        \"winCountBefore\": null,\n" +
                "        \"utcTime\": 1534872052,\n" +
                "        \"deckType\": \"slotDeck\",\n" +
                "        \"teamSize\": 1,\n" +
                "        \"winner\": -1,\n" +
                "        \"teamCrowns\": 0,\n" +
                "        \"opponentCrowns\": 1,\n" +
                "        \"team\": [\n" +
                "            {\n" +
                "                \"tag\": \"CY9JCU08\",\n" +
                "                \"name\": \"José Cano\",\n" +
                "                \"crownsEarned\": 0,\n" +
                "                \"startTrophies\": 4800,\n" +
                "                \"clan\": {\n" +
                "                    \"tag\": \"RY8JVV\",\n" +
                "                    \"name\": \"M.P.W.A\",\n" +
                "                    \"badge\": {\n" +
                "                        \"name\": \"A_Char_King_02\",\n" +
                "                        \"category\": \"03_Royale\",\n" +
                "                        \"id\": 16000145,\n" +
                "                        \"image\": \"https://royaleapi.github.io/cr-api-assets/badges/A_Char_King_02.png\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"deckLink\": \"https://link.clashroyale.com/deck/en?deck=26000011;28000001;26000045;26000034;28000009;26000005;26000009;28000011\",\n" +
                "                \"deck\": [\n" +
                "                    {\n" +
                "                        \"name\": \"Valkyrie\",\n" +
                "                        \"level\": 9,\n" +
                "                        \"maxLevel\": 11,\n" +
                "                        \"rarity\": \"Rare\",\n" +
                "                        \"requiredForUpgrade\": 800,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/0lIoYf3Y_plFTzo95zZL93JVxpfb3MMgFDDhgSDGU9A.png\",\n" +
                "                        \"key\": \"valkyrie\",\n" +
                "                        \"elixir\": 4,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 2,\n" +
                "                        \"description\": \"Tough melee fighter, deals area damage around her. Swarm or horde, no problem! She can take them all out with a few spins.\",\n" +
                "                        \"id\": 26000011\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Arrows\",\n" +
                "                        \"level\": 10,\n" +
                "                        \"maxLevel\": 13,\n" +
                "                        \"rarity\": \"Common\",\n" +
                "                        \"requiredForUpgrade\": 1000,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/Flsoci-Y6y8ZFVi5uRFTmgkPnCmMyMVrU7YmmuPvSBo.png\",\n" +
                "                        \"key\": \"arrows\",\n" +
                "                        \"elixir\": 3,\n" +
                "                        \"type\": \"Spell\",\n" +
                "                        \"arena\": 0,\n" +
                "                        \"description\": \"Arrows pepper a large area, damaging all enemies hit. Reduced damage to Crown Towers.\",\n" +
                "                        \"id\": 28000001\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Executioner\",\n" +
                "                        \"level\": 5,\n" +
                "                        \"maxLevel\": 8,\n" +
                "                        \"rarity\": \"Epic\",\n" +
                "                        \"requiredForUpgrade\": 50,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/9XL5BP2mqzV8kza6KF8rOxrpCZTyuGLp2l413DTjEoM.png\",\n" +
                "                        \"key\": \"executioner\",\n" +
                "                        \"elixir\": 5,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 9,\n" +
                "                        \"description\": \"He throws his axe like a boomerang, striking all enemies on the way out AND back. It's a miracle he doesn't lose an arm.\",\n" +
                "                        \"id\": 26000045\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Bowler\",\n" +
                "                        \"level\": 2,\n" +
                "                        \"maxLevel\": 8,\n" +
                "                        \"rarity\": \"Epic\",\n" +
                "                        \"requiredForUpgrade\": 4,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/SU4qFXmbQXWjvASxVI6z9IJuTYolx4A0MKK90sTIE88.png\",\n" +
                "                        \"key\": \"bowler\",\n" +
                "                        \"elixir\": 5,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 8,\n" +
                "                        \"description\": \"This big blue dude digs the simple things in life - Dark Elixir drinks and throwing rocks. His massive boulders roll through their target, hitting everything behind for a strike!\",\n" +
                "                        \"id\": 26000034\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Poison\",\n" +
                "                        \"level\": 5,\n" +
                "                        \"maxLevel\": 8,\n" +
                "                        \"rarity\": \"Epic\",\n" +
                "                        \"requiredForUpgrade\": 50,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/98HDkG2189yOULcVG9jz2QbJKtfuhH21DIrIjkOjxI8.png\",\n" +
                "                        \"key\": \"poison\",\n" +
                "                        \"elixir\": 4,\n" +
                "                        \"type\": \"Spell\",\n" +
                "                        \"arena\": 5,\n" +
                "                        \"description\": \"Covers the area in a deadly toxin, damaging enemy troops and buildings over time. Yet somehow leaves the grass green and healthy. Go figure!\",\n" +
                "                        \"id\": 28000009\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Minions\",\n" +
                "                        \"level\": 10,\n" +
                "                        \"maxLevel\": 13,\n" +
                "                        \"rarity\": \"Common\",\n" +
                "                        \"requiredForUpgrade\": 1000,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/yHGpoEnmUWPGV_hBbhn-Kk-Bs838OjGzWzJJlQpQKQA.png\",\n" +
                "                        \"key\": \"minions\",\n" +
                "                        \"elixir\": 3,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 0,\n" +
                "                        \"description\": \"Three fast, unarmored flying attackers. Roses are red, minions are blue, they can fly, and will crush you!\",\n" +
                "                        \"id\": 26000005\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Golem\",\n" +
                "                        \"level\": 4,\n" +
                "                        \"maxLevel\": 8,\n" +
                "                        \"rarity\": \"Epic\",\n" +
                "                        \"requiredForUpgrade\": 20,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/npdmCnET7jmVjJvjJQkFnNSNnDxYHDBigbvIAloFMds.png\",\n" +
                "                        \"key\": \"golem\",\n" +
                "                        \"elixir\": 8,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 3,\n" +
                "                        \"description\": \"Slow but durable, only attacks buildings. When destroyed, explosively splits into two Golemites and deals area damage!\",\n" +
                "                        \"id\": 26000009\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"The Log\",\n" +
                "                        \"level\": 2,\n" +
                "                        \"maxLevel\": 5,\n" +
                "                        \"rarity\": \"Legendary\",\n" +
                "                        \"requiredForUpgrade\": 4,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/_iDwuDLexHPFZ_x4_a0eP-rxCS6vwWgTs6DLauwwoaY.png\",\n" +
                "                        \"key\": \"the-log\",\n" +
                "                        \"elixir\": 2,\n" +
                "                        \"type\": \"Spell\",\n" +
                "                        \"arena\": 6,\n" +
                "                        \"description\": \"A spilt bottle of Rage turned an innocent tree trunk into \\\"The Log\\\". Now, it seeks revenge by crushing anything in its path!\",\n" +
                "                        \"id\": 28000011\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"opponent\": [\n" +
                "            {\n" +
                "                \"tag\": \"22YQR2LPV\",\n" +
                "                \"name\": \"リョウ\",\n" +
                "                \"crownsEarned\": 1,\n" +
                "                \"startTrophies\": 4168,\n" +
                "                \"clan\": {\n" +
                "                    \"tag\": \"2VQP2PPL\",\n" +
                "                    \"name\": \"ハツカネズミランド\",\n" +
                "                    \"badge\": {\n" +
                "                        \"name\": \"Moon_03\",\n" +
                "                        \"category\": \"01_Symbol\",\n" +
                "                        \"id\": 16000032,\n" +
                "                        \"image\": \"https://royaleapi.github.io/cr-api-assets/badges/Moon_03.png\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"deckLink\": \"https://link.clashroyale.com/deck/en?deck=28000008;28000000;26000004;26000011;28000010;26000030;26000019;26000015\",\n" +
                "                \"deck\": [\n" +
                "                    {\n" +
                "                        \"name\": \"Zap\",\n" +
                "                        \"level\": 11,\n" +
                "                        \"maxLevel\": 13,\n" +
                "                        \"rarity\": \"Common\",\n" +
                "                        \"requiredForUpgrade\": 2000,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/7dxh2-yCBy1x44GrBaL29vjqnEEeJXHEAlsi5g6D1eY.png\",\n" +
                "                        \"key\": \"zap\",\n" +
                "                        \"elixir\": 2,\n" +
                "                        \"type\": \"Spell\",\n" +
                "                        \"arena\": 4,\n" +
                "                        \"description\": \"Zaps enemies, briefly stunning them and dealing damage inside a small radius. Reduced damage to Crown Towers.\",\n" +
                "                        \"id\": 28000008\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Fireball\",\n" +
                "                        \"level\": 9,\n" +
                "                        \"maxLevel\": 11,\n" +
                "                        \"rarity\": \"Rare\",\n" +
                "                        \"requiredForUpgrade\": 800,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/lZD9MILQv7O-P3XBr_xOLS5idwuz3_7Ws9G60U36yhc.png\",\n" +
                "                        \"key\": \"fireball\",\n" +
                "                        \"elixir\": 4,\n" +
                "                        \"type\": \"Spell\",\n" +
                "                        \"arena\": 0,\n" +
                "                        \"description\": \"Annnnnd... Fireball. Incinerates a small area, dealing high damage. Reduced damage to Crown Towers.\",\n" +
                "                        \"id\": 28000000\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"P.E.K.K.A\",\n" +
                "                        \"level\": 6,\n" +
                "                        \"maxLevel\": 8,\n" +
                "                        \"rarity\": \"Epic\",\n" +
                "                        \"requiredForUpgrade\": 100,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/MlArURKhn_zWAZY-Xj1qIRKLVKquarG25BXDjUQajNs.png\",\n" +
                "                        \"key\": \"pekka\",\n" +
                "                        \"elixir\": 7,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 4,\n" +
                "                        \"description\": \"A heavily armored, slow melee fighter. Swings from the hip, but packs a huge punch.\",\n" +
                "                        \"id\": 26000004\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Valkyrie\",\n" +
                "                        \"level\": 7,\n" +
                "                        \"maxLevel\": 11,\n" +
                "                        \"rarity\": \"Rare\",\n" +
                "                        \"requiredForUpgrade\": 200,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/0lIoYf3Y_plFTzo95zZL93JVxpfb3MMgFDDhgSDGU9A.png\",\n" +
                "                        \"key\": \"valkyrie\",\n" +
                "                        \"elixir\": 4,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 2,\n" +
                "                        \"description\": \"Tough melee fighter, deals area damage around her. Swarm or horde, no problem! She can take them all out with a few spins.\",\n" +
                "                        \"id\": 26000011\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Graveyard\",\n" +
                "                        \"level\": 2,\n" +
                "                        \"maxLevel\": 5,\n" +
                "                        \"rarity\": \"Legendary\",\n" +
                "                        \"requiredForUpgrade\": 4,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/Icp8BIyyfBTj1ncCJS7mb82SY7TPV-MAE-J2L2R48DI.png\",\n" +
                "                        \"key\": \"graveyard\",\n" +
                "                        \"elixir\": 5,\n" +
                "                        \"type\": \"Spell\",\n" +
                "                        \"arena\": 5,\n" +
                "                        \"description\": \"Surprise! It's a party. A Skeleton party, anywhere in the Arena. Yay!\",\n" +
                "                        \"id\": 28000010\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Ice Spirit\",\n" +
                "                        \"level\": 11,\n" +
                "                        \"maxLevel\": 13,\n" +
                "                        \"rarity\": \"Common\",\n" +
                "                        \"requiredForUpgrade\": 2000,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/lv1budiafU9XmSdrDkk0NYyqASAFYyZ06CPysXKZXlA.png\",\n" +
                "                        \"key\": \"ice-spirit\",\n" +
                "                        \"elixir\": 1,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 8,\n" +
                "                        \"description\": \"Spawns one lively little Ice Spirit to freeze a group of enemies. Stay frosty.\",\n" +
                "                        \"id\": 26000030\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Spear Goblins\",\n" +
                "                        \"level\": 10,\n" +
                "                        \"maxLevel\": 13,\n" +
                "                        \"rarity\": \"Common\",\n" +
                "                        \"requiredForUpgrade\": 1000,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/FSDFotjaXidI4ku_WFpVCTWS1hKGnFh1sxX0lxM43_E.png\",\n" +
                "                        \"key\": \"spear-goblins\",\n" +
                "                        \"elixir\": 2,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 1,\n" +
                "                        \"description\": \"Three unarmored ranged attackers. Who the heck taught these guys to throw spears!? Who thought that was a good idea?!\",\n" +
                "                        \"id\": 26000019\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Baby Dragon\",\n" +
                "                        \"level\": 5,\n" +
                "                        \"maxLevel\": 8,\n" +
                "                        \"rarity\": \"Epic\",\n" +
                "                        \"requiredForUpgrade\": 50,\n" +
                "                        \"icon\": \"https://api-assets.clashroyale.com/cards/300/cjC9n4AvEZJ3urkVh-rwBkJ-aRSsydIMqSAV48hAih0.png\",\n" +
                "                        \"key\": \"baby-dragon\",\n" +
                "                        \"elixir\": 4,\n" +
                "                        \"type\": \"Troop\",\n" +
                "                        \"arena\": 0,\n" +
                "                        \"description\": \"Flying troop that deals area damage. Baby dragons hatch cute, hungry and ready for a barbeque.\",\n" +
                "                        \"id\": 26000015\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"arena\": {\n" +
                "            \"name\": \"Clan League\"\n" +
                "        }\n" +
                "    }]";

        Gson gson = new GsonBuilder().create();
        Clan clan = gson.fromJson(test1, Clan.class);

        Type collectionType = new TypeToken<Collection<ClanBattle>>(){}.getType();
        Collection<ClanBattle> battles = gson.fromJson(test, collectionType);
        Log.w("*** CLAN INFO ***", clan.toString());
        Log.w("*** CLAN BATTLES ***", battles.toString());

        Api api = new Api(MainActivity.this);
        api.getJSON("RY8JVV", Api.CLAN);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
