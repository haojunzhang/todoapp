from rest_framework import status


class InvalidSignature(BaseException):
    http_code = status.HTTP_400_BAD_REQUEST
    code = 'invalid_signature'
    msg = 'invalid_signature'


class InvalidToken(BaseException):
    http_code = status.HTTP_403_FORBIDDEN
    code = 'invalid_token'
    msg = 'invalid_token'


class InvalidTimeStamp(BaseException):
    http_code = status.HTTP_403_FORBIDDEN
    code = 'invalid_timestamp'
    msg = 'invalid_timestamp'
