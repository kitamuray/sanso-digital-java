# 吸着プロセスの解析

Gradle のビルドタスクにより二つの JAR ファイルが構築される.

- `adsorption-`_version_`.jar` - 通常の JAR ファイルで, 実行時にライブラリパスの指定が必要
- `adsorption-`_version_`-all.jar` - FAT/Uber JAR ファイルで, 実行時にライブラリパス指定は不要

なお, _version_ はバージョン情報を指す.

ユーザは Java API を呼び出すような Javascript コードを記述し,
JAR ファイルに内蔵された Javascript インタープリタから実行する.
現在, Javascript コードのサンプルとして以下のファイルが用意されている.

- `sampleNeonPurifier.js` - ネオン精製プロセスのサンプルコード

バージョン情報が例えば `1.1.0-SNAPSHOT` である場合,
FAT/Uber JAR ファイルを用いて下記のように実行する.

```shell
java -jar adsorption-1.1.0-SNAPSHOT-all.jar sampleNeonPurifier.js
```

## 設計

基本的なロジックは Java で実装し,
最上位の制御は Javascript で記述する.
Javascript から利用する API を公開 API と見なし,
Java の実装内部でのみ利用する API は非公開 API とする.

吸着筒毎に行列計算の対象となるため,
CPU キャッシュを意識した実装とする.

並列計算は細密に実装せず,
粒度は例えば吸着筒毎などとする.
