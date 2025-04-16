
# Boring Browser

![](app/src/main/res/mipmap-hdpi/ic_launcher.png)

A barebones browser for Android that blocks all media type except audio, that is: images, videos, gifs, and iframes. You can still listen to music sites like Youtube or SoundCloud. It aims to be text-only while preserving overall page structure.

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

Webpages to test visual media:
[image](https://lantoniaina.github.io/Best/),
[video](https://www.w3schools.com/html/html5_video.asp),
[iframe](https://www.w3schools.com/html/tryit.asp?filename=tryhtml_youtubeiframe_autoplay),
[canvas](https://www.w3schools.com/graphics/game_sound.asp),
[canvas image](https://www.w3schools.com/graphics/canvas_images.asp).
