# 権兵衛オーダー

対象OS：Android  
開発環境/言語：Android Studio Bumblebee | 2021.1.1 Patch 2  /  Kotlin  

機能概要：実際に注文は出来ないが、居酒屋権兵衛のテイクアウトがスマホで出来る。  
・ログイン機能  
・個数カウント機能  
・ショッピングカート的機能  

画面概要：
![Order画面1](https://user-images.githubusercontent.com/87113276/196512093-6f692d52-4a05-41be-bf5e-83aab90c69c1.jpg)
![Order画面2](https://user-images.githubusercontent.com/87113276/196512106-48b694fc-85c3-481b-acf6-ac4cc9bd70b6.jpg). 
使用API：  
    platform('com.google.firebase:firebase-bom:30.3.2')  
    'com.google.firebase:firebase-analytics-ktx'  
    'com.github.bumptech.glide:glide:4.12.0'  
    'com.google.code.gson:gson:2.8.6'  
    
使用SDK：Pixel 4 XL API 32  

コンセプト：家でも楽しめる権兵衛の味

こだわったポイント：Firebase Authenticationを使い、ログイン機能を実装出来た。

デザイン面でこだわったポイント：すべてのページで同じような色を使い、統一性を出した。また、個数の追加・削除ボタンなども実装出来た。

自己評価：自分で考えたり動画を参考にしたりしながら、自分の作りたい機能をほとんど実装できて良かった。しかし、完全に解決しきれないエラーもあったため、もっと完成度を上げたい。

<課題>  
1,メニューの数が一定以上超えるとカートへの追加ボタンがバグる。(menulist2.jsonを読み込んだ場合)  
2,上の画像の２つ目の画面で食べ物のジャンルを選択出来るようにしたかったが、ページを戻るとカートがリセットされてしまう。(gonbe.jsonを読み込んだ場合)  
3,スプラッシュ画面を作っていたが、ログイン画面と一緒に使えなかった。
