### Микросервис Dish.

Сервис предназначен для работы с блюдом.

Для взаимодействия с сервисом используется Rest API:

- POST /dish - добавить блюдо.
- PATCH /dish - обновить блюдо.
- DELETE /dish/id - удалить блюдо.
- GET /dish/id - найти блюдо.
- GET /dish - найти все блюда.

При запросах POST или PATCH в теле запроса используется сообщение вида json:

    {
        "name":"dish name",
        "ingredients":"your ingredients",
        "price":your price
    }