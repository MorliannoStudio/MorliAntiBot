# MorliAntiBot

Плагин, который запрещает вход ботам, по типу BareAPI и т.д.

## Скачать
[Modrinth](https://modrinth.com/project/morliantibot/versions)\
[GitHub Releases](https://github.com/MorliannoStudio/MorliAntiBot/releases)\
[Telegram бот](https://t.me/morliannostudiobot)

## Конфиг
```yaml
#  __  __            _ _                         _____ _             _ _
# |  \/  |          | (_)                       / ____| |           | (_)
# | \  / | ___  _ __| |_  __ _ _ __  _ __   ___| (___ | |_ _   _  __| |_  ___
# | |\/| |/ _ \| '__| | |/ _` | '_ \| '_ \ / _ \\___ \| __| | | |/ _` | |/ _ \
# | |  | | (_) | |  | | | (_| | | | | | | | (_) |___) | |_| |_| | (_| | | (_) |
# |_|  |_|\___/|_|  |_|_|\__,_|_| |_|_| |_|\___/_____/ \__|\__,_|\__,_|_|\___/

# Плагин был создан в MorliannoStudio
# Telegram: @MorliannoStudio

# Наказание
# Возможные наказания: tempban, tempbanip, ban, banip, deny
punishment: "banip"
punishment-duration: 60 # Время наказания в минутах (60 по умолчанию) (не применяется на ban, banip и deny)
punishment-reason: "Бот" # Причина наказания

disallowed-contains: # Содержимое ников, например если ник из этого списка содержит "BareAPI_", то будет применено наказание
  - "BareAPI_"
```
