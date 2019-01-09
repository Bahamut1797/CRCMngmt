
# CR - Clan Management

CR - Clan Management is a clan management for Clash Royale to get a full view of the stats of their members and watch who is or isn't an active player. This app uses unofficial public API for Clash Royale, [*RoyaleAPI*](https://royaleapi.com/).

## Features

- Show the member of a clan, their donations at week, current trophies and skipped wars* count.
*If the member participated in Collection Day and on War Day the member no participate on war it count as Skipped War. It reset every month.
- Order the member list by Trophies, Skipped Wars or Donations.
- Search for other clans you want to manage and switch between them.
- [**Next update**] Show individual information for every member of the clan.
- [**Next update**] Optimize the app for tables.
- [**Future update**] Show statistics from every member of your clan.

## Install

Donwload the lastest [Release](https://github.com/Bahamut1797/CRCMngmt/releases/latest) and install* the APK.

*The minimum API Level for this app is 21, Lollipop (Android 5.0) and actually is not optimized for tablets or bigger screens.

## Building

Source runs in Android Studio.
If you want to build by your own you need to generate your API Key in order to work.

### RoyaleAPI key

Read the [Authentication](https://docs.royaleapi.com/#/authentication?id=key-management) topic from **RoyaleAPI** docs [page]((https://docs.royaleapi.com/)) in order to get your own development key.

### Setting API key on code

Open the files **\CRClanManagement\app\src\main\res\values\strings.xml** and **\CRClanManagement\app\src\main\res\values-es\strings.xml** and change the value for the first String "*HERE YOUR API KEY*" for your API KEY.

    <string name="key" tools:ignore="TypographyDashes">HERE YOUR API KEY</string>

## Getting Help

To report any bug or specific problem you find or request a feature, feel free to post an issue. For questions, suggestions, or anything else, email me.
