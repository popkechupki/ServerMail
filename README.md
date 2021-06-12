# ServerMail
A Nukkit plugin to mail other players. Using SQLite or MySQL to store all data (optional).
**Support over Java8. (from ver1.0.3)**
### Language
Support three languages. English, Japanese, and Korean. This plugin use default server language that configured on nukkit.yml.
 - [English](#Usage)
 - [日本語](#使い方)
 - [한국어](#사용법)
## Usage
You can control this plugin with command, config.yml and lang.properties. Descriptions each files are written in it.
When you update this plugin, the plugin will replace config.yml to the latest version. Therefore, old configuration file is renamed to old_config.yml. You may have to setting up the new configuration file. Also, we suggest to delete lang.properties file as well because the plugin doesn't update that file automatically.
### Command
| Command | Description |
|--|--|
| /sendmail {target} {subject} {message. you can use space key.} | Send mail to entered player. |
| /send | Confirm send new mail. |
| /listmail {page} | See your mail box. |
| /readmail {ID} | Read specified ID mail.(Confirmable /listmail) |
| /deletemail {ID} | Delete specified ID mail.(Confirmable /listmail) |
## 使い方
このプラグインはコマンド、コンフィグファイル、言語ファイルを操作できます。それぞれのファイルの説明はファイル内に記載されています。
プラグインを更新する際には、古いコンフィグファイルの内容はold_config.ymlに保存されるので、新しく生成されたconfig.ymlの編集の参考にしてください。また、更新時にはlang.propertiesファイルの削除をお勧めします。プラグインは、このファイルについては更新があっても自動で置き換えることはありません。
### コマンド
| Command | Description |
|--|--|
| /sendmail {送信先} {件名} {内容(スペースが使えます。)} | 指定したプレイヤーにメッセージを送信します。 |
| /send | メールの送信を確定します。 |
| /listmail {ページ} | メールボックスを確認します。 |
| /readmail {ID} | 指定したIDのメールを読みます。(/listmailで確認できます) |
| /deletemail {ID} | 指定したIDのメールを削除します。(/listmailで確認できます。) |
## 사용법
이 플러그인은 명령어, config.yml, lang.properties으로 조작할 수 있습니다. 각각의 파일에 대한 설명은 파일에 적혀있습니다.
### 명령어
| 명령어 | 설명 |
|--|--|
| /sendmail {타켓} {주제} {메시지 (띄어쓰기가 가능합니다.)} | 참가한 플레이어에게 메일을 전송합니다. |
| /send | 새 메일 전송을 확인합니다. |
| /listmail {페이지} | 메일함을 보여줍니다. |
| /readmail {아이디} | 특정한 아이디로 메일을 읽습니다. (/listmail로 확인 가능) |
| /deletemail {아이디} | 특정한 아이디로 메일을 삭제합니다. (/listmail로 확인 가능) |
