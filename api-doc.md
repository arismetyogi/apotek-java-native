---
title: Default module
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - java: Java
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.30"
---

# API Documentation

🔙 [Main Readme File](./README.md)

Base URLs:

- <a href="http://localhost:8080">Development Env: http://localhost:8080</a>

# Medicines

## GET getAll

GET /medicines

> Response Examples

> 200 Response

```json
{
  "content": [
    {
      "id": 1,
      "name": "Panadol Tablet",
      "unit": "TAB",
      "price": 2000,
      "stock": 100,
      "minStock": 30,
      "expiryDate": "2026-12-31",
      "status": null,
      "createdAt": 1763129478950,
      "updatedAt": 0
    },
    {
      "id": 2,
      "name": "Viagra",
      "unit": "TAB",
      "price": 180000,
      "stock": 10,
      "minStock": 2,
      "expiryDate": "2028-10-31",
      "status": null,
      "createdAt": 1763130106051,
      "updatedAt": 0
    },
    {
      "id": 3,
      "name": "Alco Plus DMP",
      "unit": "BTL",
      "price": 62000,
      "stock": 10,
      "minStock": 4,
      "expiryDate": "2027-10-11",
      "status": null,
      "createdAt": 1763130187702,
      "updatedAt": 0
    },
    {
      "id": 4,
      "name": "Alco Plus Exp",
      "unit": "BTL",
      "price": 62000,
      "stock": 12,
      "minStock": 4,
      "expiryDate": "2027-10-11",
      "status": null,
      "createdAt": 1763130202364,
      "updatedAt": 0
    },
    {
      "id": 5,
      "name": "Test Obat",
      "unit": "STR",
      "price": 30000,
      "stock": 10,
      "minStock": 5,
      "expiryDate": "2027-10-11",
      "status": null,
      "createdAt": 1763130231873,
      "updatedAt": 0
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 5,
  "totalPages": 1
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | none        | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name            | Type     | Required | Restrictions | Title | description |
| --------------- | -------- | -------- | ------------ | ----- | ----------- |
| » content       | [object] | true     | none         |       | none        |
| »» id           | integer  | true     | none         |       | none        |
| »» name         | string   | true     | none         |       | none        |
| »» unit         | string   | true     | none         |       | none        |
| »» price        | integer  | true     | none         |       | none        |
| »» stock        | integer  | true     | none         |       | none        |
| »» minStock     | integer  | true     | none         |       | none        |
| »» expiryDate   | string   | true     | none         |       | none        |
| »» status       | null     | true     | none         |       | none        |
| »» createdAt    | integer  | true     | none         |       | none        |
| »» updatedAt    | integer  | true     | none         |       | none        |
| » page          | integer  | true     | none         |       | none        |
| » size          | integer  | true     | none         |       | none        |
| » totalElements | integer  | true     | none         |       | none        |
| » totalPages    | integer  | true     | none         |       | none        |

## POST create

POST /medicines

> Body Parameters

```json
{
  "name": "Nutrimax B Complex",
  "unit": "BTL",
  "price": 120000,
  "stock": 4,
  "minStock": 6,
  "expiryDate": "2025-11-30",
  "status": "ACTIVE"
}
```

### Params

| Name | Location | Type   | Required | Description |
| ---- | -------- | ------ | -------- | ----------- |
| body | body     | object | yes      | none        |

> Response Examples

> 201 Response

```json
{
  "id": 1,
  "name": "Nutrimax B Complex",
  "unit": "BTL",
  "price": 120000,
  "stock": 4,
  "minStock": 6,
  "expiryDate": "2025-11-30",
  "status": "ACTIVE",
  "createdAt": 1763130231873,
  "updatedAt": 0
}
```

### Responses

| HTTP Status Code | Meaning                                                      | Description | Data schema |
| ---------------- | ------------------------------------------------------------ | ----------- | ----------- |
| 201              | [Created](https://tools.ietf.org/html/rfc7231#section-6.3.2) | none        | Inline      |

### Responses Data Schema

HTTP Status Code **201**

| Name         | Type    | Required | Restrictions | Title | description |
| ------------ | ------- | -------- | ------------ | ----- | ----------- |
| » id         | integer | true     | none         |       | none        |
| » name       | string  | true     | none         |       | none        |
| » unit       | string  | true     | none         |       | none        |
| » price      | integer | true     | none         |       | none        |
| » stock      | integer | true     | none         |       | none        |
| » minStock   | integer | true     | none         |       | none        |
| » expiryDate | string  | true     | none         |       | none        |
| » status     | string  | true     | none         |       | none        |
| » createdAt  | integer | true     | none         |       | none        |
| » updatedAt  | integer | true     | none         |       | none        |

## GET getOne

GET /medicines/4

> Response Examples

> 200 Response

```json
{
  "id": 4,
  "name": "Alco Plus Exp",
  "unit": "BTL",
  "price": 62000,
  "stock": 12,
  "minStock": 4,
  "expiryDate": "2027-10-11",
  "status": null,
  "createdAt": 1763130202364,
  "updatedAt": 0
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | none        | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name         | Type    | Required | Restrictions | Title | description |
| ------------ | ------- | -------- | ------------ | ----- | ----------- |
| » id         | integer | true     | none         |       | none        |
| » name       | string  | true     | none         |       | none        |
| » unit       | string  | true     | none         |       | none        |
| » price      | integer | true     | none         |       | none        |
| » stock      | integer | true     | none         |       | none        |
| » minStock   | integer | true     | none         |       | none        |
| » expiryDate | string  | true     | none         |       | none        |
| » status     | null    | true     | none         |       | none        |
| » createdAt  | integer | true     | none         |       | none        |
| » updatedAt  | integer | true     | none         |       | none        |

## PUT update

PUT /medicines/5

> Body Parameters

```json
{
  "name": "Procold Tab",
  "unit": "STR",
  "price": 5500,
  "stock": 12,
  "minStock": 6,
  "expiryDate": "2028-10-31",
  "status": "ACTIVE"
}
```

### Params

| Name | Location | Type   | Required | Description |
| ---- | -------- | ------ | -------- | ----------- |
| body | body     | object | yes      | none        |

> Response Examples

> 200 Response

```json
{
  "id": 5,
  "name": "Procold Tab",
  "unit": "STR",
  "price": 5500,
  "stock": 12,
  "minStock": 6,
  "expiryDate": "2028-10-31",
  "status": "ACTIVE",
  "createdAt": 1763130231873,
  "updatedAt": 1763130251873
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | none        | Inline      |

### Responses Data Schema

## DELETE delete

DELETE /medicines/5

> Response Examples

> 200 Response

```json
{
  "status": "DELETED"
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | none        | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name     | Type   | Required | Restrictions | Title | description |
| -------- | ------ | -------- | ------------ | ----- | ----------- |
| » status | string | true     | none         |       | none        |

## GET getLowStocks

GET /medicines/low-stock

> Response Examples

> 200 Response

```json
{
  "content": [
    {
      "id": 6,
      "name": "Nutrimax C plus",
      "unit": "BTL",
      "price": 360000,
      "stock": 4,
      "minStock": 6,
      "expiryDate": "2027-10-11",
      "status": null,
      "createdAt": 1763133687796,
      "updatedAt": 0
    },
    {
      "id": 7,
      "name": "Nutrimax B Complex",
      "unit": "BTL",
      "price": 120000,
      "stock": 4,
      "minStock": 6,
      "expiryDate": "2025-11-30",
      "status": null,
      "createdAt": 1763135445393,
      "updatedAt": 0
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 2,
  "totalPages": 1
}
```

> 400 Response

```json
{
  "error": "string",
  "message": "string"
}
```

### Responses

| HTTP Status Code | Meaning                                                          | Description | Data schema |
| ---------------- | ---------------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)          | none        | Inline      |
| 400              | [Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1) | none        | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name            | Type     | Required | Restrictions | Title | description |
| --------------- | -------- | -------- | ------------ | ----- | ----------- |
| » content       | [object] | true     | none         |       | none        |
| »» id           | integer  | true     | none         |       | none        |
| »» name         | string   | true     | none         |       | none        |
| »» unit         | string   | true     | none         |       | none        |
| »» price        | integer  | true     | none         |       | none        |
| »» stock        | integer  | true     | none         |       | none        |
| »» minStock     | integer  | true     | none         |       | none        |
| »» expiryDate   | string   | true     | none         |       | none        |
| »» status       | null     | true     | none         |       | none        |
| »» createdAt    | integer  | true     | none         |       | none        |
| »» updatedAt    | integer  | true     | none         |       | none        |
| » page          | integer  | true     | none         |       | none        |
| » size          | integer  | true     | none         |       | none        |
| » totalElements | integer  | true     | none         |       | none        |
| » totalPages    | integer  | true     | none         |       | none        |

HTTP Status Code **400**

| Name      | Type   | Required | Restrictions | Title | description |
| --------- | ------ | -------- | ------------ | ----- | ----------- |
| » error   | string | true     | none         |       | none        |
| » message | string | true     | none         |       | none        |

## GET getNearExpiry

GET /medicines/near-expiry

> Response Examples

> 200 Response

```json
{
  "content": [
    {
      "id": 7,
      "name": "Nutrimax B Complex",
      "unit": "BTL",
      "price": 120000,
      "stock": 4,
      "minStock": 6,
      "expiryDate": "2025-11-30",
      "status": null,
      "createdAt": 1763135445393,
      "updatedAt": 0
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1
}
```

### Responses

| HTTP Status Code | Meaning                                                          | Description | Data schema |
| ---------------- | ---------------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)          | none        | Inline      |
| 400              | [Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1) | none        | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name            | Type     | Required | Restrictions | Title | description |
| --------------- | -------- | -------- | ------------ | ----- | ----------- |
| » content       | [object] | true     | none         |       | none        |
| »» id           | integer  | false    | none         |       | none        |
| »» name         | string   | false    | none         |       | none        |
| »» unit         | string   | false    | none         |       | none        |
| »» price        | integer  | false    | none         |       | none        |
| »» stock        | integer  | false    | none         |       | none        |
| »» minStock     | integer  | false    | none         |       | none        |
| »» expiryDate   | string   | false    | none         |       | none        |
| »» status       | null     | false    | none         |       | none        |
| »» createdAt    | integer  | false    | none         |       | none        |
| »» updatedAt    | integer  | false    | none         |       | none        |
| » page          | integer  | true     | none         |       | none        |
| » size          | integer  | true     | none         |       | none        |
| » totalElements | integer  | true     | none         |       | none        |
| » totalPages    | integer  | true     | none         |       | none        |

HTTP Status Code **400**

| Name      | Type   | Required | Restrictions | Title | description |
| --------- | ------ | -------- | ------------ | ----- | ----------- |
| » error   | string | true     | none         |       | none        |
| » message | string | true     | none         |       | none        |

# Patients

## GET getAll

GET /patients

> Response Examples

> 200 Response

```json
{
  "content": [
    {
      "id": 1,
      "fullName": "John Doe",
      "phone": "02193102930291",
      "address": "Jl. Budi Utomo 1",
      "createdAt": 1763130231873,
      "updatedAt": 1763130241873
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1
}
```

### Responses

| HTTP Status Code | Meaning                                                          | Description | Data schema |
| ---------------- | ---------------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)          | none        | Inline      |
| 400              | [Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1) | none        | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name            | Type    | Required | Restrictions | Title | description |
| --------------- | ------- | -------- | ------------ | ----- | ----------- |
| » content       | [any]   | true     | none         |       | none        |
| » page          | integer | true     | none         |       | none        |
| » size          | integer | true     | none         |       | none        |
| » totalElements | integer | true     | none         |       | none        |
| » totalPages    | integer | true     | none         |       | none        |

HTTP Status Code **400**

| Name      | Type   | Required | Restrictions | Title | description |
| --------- | ------ | -------- | ------------ | ----- | ----------- |
| » error   | string | true     | none         |       | none        |
| » message | string | true     | none         |       | none        |

## POST create

POST /patients

> Body Parameters

```json
{
  "fullName": "John Doe",
  "phone": "02193102930291",
  "address": "Jl. Budi Utomo 1"
}
```

### Params

| Name | Location | Type   | Required | Description |
| ---- | -------- | ------ | -------- | ----------- |
| body | body     | object | yes      | none        |

> Response Examples

> 201 Response

```json
{
  "id": 1,
  "fullName": "John Doe",
  "phone": "02193102930291",
  "address": "Jl. Budi Utomo 1",
  "createdAt": 1763130231873,
  "updatedAt": 0
}
```

### Responses

| HTTP Status Code | Meaning                                                      | Description | Data schema |
| ---------------- | ------------------------------------------------------------ | ----------- | ----------- |
| 201              | [Created](https://tools.ietf.org/html/rfc7231#section-6.3.2) | none        | Inline      |

### Responses Data Schema

HTTP Status Code **201**

| Name        | Type    | Required | Restrictions | Title | description |
| ----------- | ------- | -------- | ------------ | ----- | ----------- |
| » id        | integer | true     | none         |       | none        |
| » fullName  | string  | true     | none         |       | none        |
| » phone     | string  | true     | none         |       | none        |
| » address   | string  | true     | none         |       | none        |
| » createdAt | integer | true     | none         |       | none        |
| » updatedAt | integer | true     | none         |       | none        |

## GET getOne

GET /patients/1

> Response Examples

> 200 Response

```json
{
  "id": 1,
  "fullName": "John Doe",
  "phone": "02193102930291",
  "address": "Jl. Budi Utomo 1",
  "createdAt": 1763130231873,
  "updatedAt": 1763130241873
}
```

### Responses

| HTTP Status Code | Meaning                                                  | Description | Data schema |
| ---------------- | -------------------------------------------------------- | ----------- | ----------- |
| 200              | [200](https://tools.ietf.org/html/rfc7231#section-6.3.2) | none        | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name        | Type    | Required | Restrictions | Title | description |
| ----------- | ------- | -------- | ------------ | ----- | ----------- |
| » id        | integer | true     | none         |       | none        |
| » fullName  | string  | true     | none         |       | none        |
| » phone     | string  | true     | none         |       | none        |
| » address   | string  | true     | none         |       | none        |
| » createdAt | integer | true     | none         |       | none        |
| » updatedAt | integer | true     | none         |       | none        |

## PUT update

PUT /patients/2

> Body Parameters

```json
{
  "fullName": "John Cena",
  "phone": "0989128898",
  "address": "Jl. Ketapang 1"
}
```

### Params

| Name | Location | Type   | Required | Description |
| ---- | -------- | ------ | -------- | ----------- |
| body | body     | object | yes      | none        |

> Response Examples

> 201 Response

```json
{
  "id": 1,
  "fullName": "John Cena",
  "phone": "0989128898",
  "address": "Jl. Ketapang 1",
  "createdAt": 1763130231873,
  "updatedAt": 1763130241873
}
```

### Responses

| HTTP Status Code | Meaning                                                  | Description | Data schema |
| ---------------- | -------------------------------------------------------- | ----------- | ----------- |
| 200              | [200](https://tools.ietf.org/html/rfc7231#section-6.3.2) | none        | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name        | Type    | Required | Restrictions | Title | description |
| ----------- | ------- | -------- | ------------ | ----- | ----------- |
| » id        | integer | true     | none         |       | none        |
| » fullName  | string  | true     | none         |       | none        |
| » phone     | string  | true     | none         |       | none        |
| » address   | string  | true     | none         |       | none        |
| » createdAt | integer | true     | none         |       | none        |
| » updatedAt | integer | true     | none         |       | none        |

## DELETE delete

DELETE /patients/2

> Response Examples

> 200 Response

```json
{
  "status": "DELETED"
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | none        | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name     | Type   | Required | Restrictions | Title | description |
| -------- | ------ | -------- | ------------ | ----- | ----------- |
| » status | string | true     | none         |       | none        |

# Data Schema

<h2 id="tocS_Medicines">Medicines</h2>

<a id="schemamedicines"></a>
<a id="schema_Medicines"></a>
<a id="tocSmedicines"></a>
<a id="tocsmedicines"></a>

```json
{
  "id": "nextval('medicines_id_seq'",
  "name": "string",
  "unit": "string",
  "price": 0,
  "stock": "0",
  "min_stock": "10",
  "expiry_date": "2019-08-24",
  "status": "'ACTIVE'",
  "created_at": "CURRENT_TIMESTAMP",
  "updated_at": "CURRENT_TIMESTAMP"
}
```

### Attribute

| Name        | Type         | Required | Restrictions | Title | Description |
| ----------- | ------------ | -------- | ------------ | ----- | ----------- |
| id          | integer      | true     | none         |       | none        |
| name        | string       | true     | none         |       | none        |
| unit        | string¦null  | false    | none         |       | none        |
| price       | number       | true     | none         |       | none        |
| stock       | integer      | true     | none         |       | none        |
| min_stock   | integer      | true     | none         |       | none        |
| expiry_date | string(date) | true     | none         |       | none        |
| status      | string¦null  | false    | none         |       | none        |
| created_at  | string¦null  | false    | none         |       | none        |
| updated_at  | string¦null  | false    | none         |       | none        |

<h2 id="tocS_Patients">Patients</h2>

<a id="schemapatients"></a>
<a id="schema_Patients"></a>
<a id="tocSpatients"></a>
<a id="tocspatients"></a>

```json
{
  "id": "nextval('patients_id_seq'",
  "full_name": "string",
  "phone": "string",
  "address": "string",
  "created_at": "CURRENT_TIMESTAMP",
  "updated_at": "CURRENT_TIMESTAMP"
}
```

### Attribute

| Name       | Type        | Required | Restrictions | Title | Description |
| ---------- | ----------- | -------- | ------------ | ----- | ----------- |
| id         | integer     | true     | none         |       | none        |
| full_name  | string      | true     | none         |       | none        |
| phone      | string¦null | false    | none         |       | none        |
| address    | string¦null | false    | none         |       | none        |
| created_at | string¦null | false    | none         |       | none        |
| updated_at | string¦null | false    | none         |       | none        |
