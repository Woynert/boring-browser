
# Boring Browser

![](app/src/main/res/mipmap-hdpi/ic_launcher.png)

A barebones browser for Android that blocks/hides all visual media types be it: images, videos, iframes, etc (see full list at the bottom). You can still listen to music sites like Youtube or SoundCloud. It aims to be text-only while preserving overall page structure. Pages that depend on iframes might break. Intended to be used along [detox app](https://play.google.com/store/apps/details?id=com.urbandroid.ddc).

## Building

For active debugging:

```console
NIXPKGS_ALLOW_UNFREE=1 nix-shell -p android-studio
```

Generate APK:

```console
gradle assembleRelease
ls app/app/build/outputs/apk/
```

[Manual signing](https://stackoverflow.com/a/40064149):

```console
keytool -genkeypair -keyalg RSA -keysize 2048 -validity 1000 -storetype pkcs12 -keystore mystore.jks -alias myalias 
zipalign -p 4 my.apk my-aligned.apk
apksigner sign --ks-key-alias myalias --ks mystore.jks my-aligned.apk
```

## Features

The following DOM features are either invisible or disabled, use these websites to test:

[image](https://lantoniaina.github.io/Best/),
[video](https://www.w3schools.com/html/html5_video.asp),
[frame](https://htmledit.squarefree.com/),
[iframe](https://www.w3schools.com/html/tryit.asp?filename=tryhtml_youtubeiframe_autoplay),
[canvas](https://www.w3schools.com/graphics/game_sound.asp),
[canvas image](https://www.w3schools.com/graphics/canvas_images.asp),
[shadow DOM](https://www.reddit.com/r/YoutubeVideos/comments/1dc9tp9/rick_astley_never_gonna_give_you_up_lyrics/).

Note: __shadow DOM__ links to a reddit post with youtube embed for as of 2026-07-15 reddit uses a shadow DOM for it's embeds. I should put together a simple page for demoing all of these.
