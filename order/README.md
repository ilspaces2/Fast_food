### Микросервис Order.

Сервис предназначен для работы с заказами.

Для взаимодействия с сервисом используется Rest API:

- POST /order - добавить заказ.
- DELETE /order/id - удалить заказ.
- GET /order/id - найти заказ.
- GET /order - найти все заказы.
- GET /getStatus/id - получить статус заказа.
- PATCH /setStatus/id?status=statusName - изменить статус заказа.