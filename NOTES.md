Очень плохая идея делать такую мапу в парсере. Сейчас, для добавления новой операции, тебе надо прописать все в нескольких местах (не ошибиться) + код разросся раза в 2-3. Ведь для добавленя синуса, по факту, надо только это:
```js
const Sin = operationConstructorCreator(Math.sin, 'sin', Math.cos);
```
Ну, может, немного видоизменить правило дифференциации -- ок. Но точно не прописывать где-то там `{'sin': f: Sin, args: 2},`. Потому что все это и так уже задано самой операцией синус, зачем еще раз прописывать?

А если захочется поменять? Придется по всему коду бегать искать где указаны разные значения 
