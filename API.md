# TodoApp API

## User
### 發送OTP
```
POST http://domain/otps/
X-TODO-TOKEN (AppToken)
X-TODO-SIGNATURE (AppSign)
```
#### Body參數
```
{
    "ts": "1566618441000",
    "email": "ryan@todoapp.com"
}
```
#### 回應
```
Status Code : 200
X-TODO-SIGNATURE (ServerSign)
```
#### 回應參數
```
{
    "otp_id"
}
```
***
### 註冊
```
POST http://domain/users/
X-TODO-TOKEN (AppToken)
X-TODO-SIGNATURE (AppSign)
```
#### Body參數
```
{
    "ts": "1566618441000",
    "email": "ryan@todoapp.com",
    "password": "xxx",
    "user_sign_pub_key": "xxx",
    "otp_id": "xxx",
    "otp": "123456"
}
```
#### 回應
```
Status Code : 201 Created
X-TODO-SIGNATURE (ServerSign)
```
#### 回應參數
```
{
    "user_id": "xxx",
    "user_token": "xxx",
}
```
***
### 登入
```
POST http://domain/users/login/
X-TODO-TOKEN (AppToken)
X-TODO-SIGNATURE (AppSign)
```
#### Body參數
```
{
    "ts": "1566618441000",
    "email": "ryan@todoapp.com",
    "password": "xxx",
    "user_sign_pub_key": "xxx"
}
```
#### 回應
```
Status Code : 200 Created
X-TODO-SIGNATURE (ServerSign)
```
#### 回應參數
```
{
    "user_id": "xxx",
    "user_token": "xxx",
}
```
***
### 登出
```
DELETE http://domain/users/user_token/
X-TODO-TOKEN (UserToken)
X-TODO-SIGNATURE (UserSign)
```
#### Url參數
```
http://domain/users/user_token/?ts=1566618441000
```
#### 回應
```
Status Code : 204 No Content
```
***
### 取得使用者資訊
```
GET http://domain/users/<user_id>/
X-TODO-TOKEN (UserToken)
X-TODO-SIGNATURE (UserSign)
```
#### Url參數
```
http://domain/users/<user_id>/?ts=1566618441000
```
#### 回應
```
Status Code : 200 Created
X-TODO-SIGNATURE (ServerSign)
```
#### 回應參數
```
{
    "user_id": "xxx",
    "email": "xxx",
}
```
***
### 重設密碼
```
PUT http://domain/users/reset_password/
X-TODO-TOKEN (AppToken)
X-TODO-SIGNATURE (AppSign)
```
#### Body參數
```
{
    "ts": "1566618441000",
    "password": "xxx",
    "otp_id": "xxx",
    "otp": "123456"
}
```
#### 回應
```
Status Code : 204 No Content
X-TODO-SIGNATURE (ServerSign)
```
***
### 修改密碼
```
PUT http://domain/users/<user_id>/password/
X-TODO-TOKEN (UserToken)
X-TODO-SIGNATURE (UserSign)
```
#### Body參數
```
{
    "ts": "1566618441000",
    "password": "xxx",
    "otp_id": "xxx",
    "otp": "123456"
}
```
#### 回應
```
Status Code : 200
X-TODO-SIGNATURE (ServerSign)
```
#### 回應參數
```
{
    "user_token": "xxx"
}
```
***

## Todo
### 取得Todo列表
```
GET http://domain/todo/
X-TODO-TOKEN (UserToken)
X-TODO-SIGNATURE (UserSign)
```
#### Url參數
```
http://domain/todo/?ts=1566618441000
```
#### 回應
```
Status Code : 200
X-TODO-SIGNATURE (ServerSign)
```
#### 回應參數
```
{
    "todo_list":[
        {
            "todo_id": "xxx",
            "content": "xxxxxxxxxxxxx",
            "done": true,
            "create_time": "2019-08-24T12:02:30Z",
            "update_time": "2019-08-24T12:02:30Z"
        },
        {
            "todo_id": "xxx",
            "content": "xxxxxxxxxxxxx",
            "done": false,
            "create_time": "2019-08-24T12:02:30Z",
            "update_time": "2019-08-24T12:02:30Z"
        }
    ]
}
```
***
### 取得單一Todo
```
GET http://domain/todo/<todo_id>/
X-TODO-TOKEN (UserToken)
X-TODO-SIGNATURE (UserSign)
```
#### Url參數
```
http://domain/todo/<todo_id>/?ts=1566618441000
```
#### 回應
```
Status Code : 200
X-TODO-SIGNATURE (ServerSign)
```
#### 回應參數
```
{
    "todo_id": "xxx",
    "content": "xxxxxxxxxxxxx",
    "done": true,
    "create_time": "2019-08-24T12:02:30Z",
    "update_time": "2019-08-24T12:02:30Z"
}
```
***
### 新增Todo
```
POST http://domain/todo/
X-TODO-TOKEN (UserToken)
X-TODO-SIGNATURE (UserSign)
```
#### Body參數
```
{
    "ts": "1566618441000"
    "content": "xxxxxxxxx"
}
```
#### 回應
```
Status Code : 201 Created
X-TODO-SIGNATURE (ServerSign)
```
#### 回應參數
```
{
    "todo_id": "xxx",
    "content": "xxxxxxxxxxxxx",
    "done": true,
    "create_time": "2019-08-24T12:02:30Z",
    "update_time": "2019-08-24T12:02:30Z"
}
```
***
### 刪除Todo
```
DELETE http://domain/todo/<todo_id>/
X-TODO-TOKEN (UserToken)
X-TODO-SIGNATURE (UserSign)
```
#### Url參數
```
http://domain/todo/<todo_id>/?ts=1566618441000
```
#### 回應
```
Status Code : 204 No Content
X-TODO-SIGNATURE (ServerSign)
```