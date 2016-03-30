# VintageChroma
A Beautiful Material color picker view for Android.

Identical remake of [Chroma by Priyesh Patel](https://github.com/ItsPriyesh/chroma) in Java instead of Kotlin, 
so it is much more lightweight for using in kotlin-less apps :D

If your app is written in kotlin, I recommend you using original [lib](https://github.com/ItsPriyesh/chroma) instad.

Screenshots
--

<img src="https://raw.githubusercontent.com/MrBIMC/VintageChroma/master/art/screen1.png" width="250">
<img src="https://raw.githubusercontent.com/MrBIMC/VintageChroma/master/art/screen2.png" width="250">
<img src="https://raw.githubusercontent.com/MrBIMC/VintageChroma/master/art/screen3.png" width="500">

Download
--------
```
compile 'com.pavelsikun:vintage-chroma:1.1'
```

Usage
-----
To display an color picker `DialogFragment`:

``` java
new ChromaDialog.Builder()
    .initialColor(Color.GREEN)
    .colorMode(ColorMode.ARGB) // RGB, ARGB, HVS
    .indicatorMode(IndicatorMode.HEX) //HEX or DECIMAL; Note that ColorMode.HSV && IndicatorMode.HEX is a bad idea
    .onColorSelected(color -> /* do your stuff */)
    .create()
    .show(getFragmentManager(), "ChromaDialog");
```

Check out the [sample project](sample) for more details.

License
-------
    Copyright 2016 Pavel Sikun.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
